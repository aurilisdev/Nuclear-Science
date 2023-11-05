package nuclearscience.common.tile;

import java.util.stream.Stream;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.common.block.VoxelShapes;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentInventory.InventoryBuilder;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.object.Location;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import nuclearscience.common.entity.EntityParticle;
import nuclearscience.common.inventory.container.ContainerParticleInjector;
import nuclearscience.common.settings.Constants;
import nuclearscience.registers.NuclearScienceBlockTypes;
import nuclearscience.registers.NuclearScienceBlocks;
import nuclearscience.registers.NuclearScienceItems;

public class TileParticleInjector extends GenericTile {
	private EntityParticle[] particles = new EntityParticle[2];
	private long timeSinceSpawn = 0;

	public TileParticleInjector(BlockPos pos, BlockState state) {
		super(NuclearScienceBlockTypes.TILE_PARTICLEINJECTOR.get(), pos, state);
		addComponent(new ComponentTickable(this));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentInventory(this, InventoryBuilder.newInv().inputs(2).outputs(1)).valid((index, stack, i) -> index != 1 || stack.getItem() == NuclearScienceItems.ITEM_CELLELECTROMAGNETIC.get()).setSlotsByDirection(Direction.UP, 0, 1).setSlotsByDirection(Direction.WEST, 0, 1).setDirectionsBySlot(2, Direction.DOWN, Direction.EAST));
		addComponent(new ComponentElectrodynamic(this, false, true).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * 8).setInputDirections(Direction.NORTH).maxJoules(Constants.PARTICLEINJECTOR_USAGE_PER_PARTICLE * 10));
		addComponent(new ComponentProcessor(this).canProcess(this::canProcess).usage(Constants.PARTICLEINJECTOR_USAGE_PER_PARTICLE).process(this::process));
		addComponent(new ComponentContainerProvider("container.particleinjector", this).createMenu((id, player) -> new ContainerParticleInjector(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}

	protected boolean canProcess(ComponentProcessor processor) {
		if (particles[0] != null && !particles[0].isAlive()) {
			particles[0] = null;
		}
		if (particles[1] != null && !particles[1].isAlive()) {
			particles[1] = null;
		}
		ComponentInventory inv = getComponent(IComponentType.Inventory);
		ItemStack resultStack = inv.getItem(2);
		timeSinceSpawn--;

		/**
		 * This is a far simpler check to perform. It can use any item, so all you have to do is check if the thing in the input slot is
		 * an item and is not air.
		 */
		boolean isItem = false;
		ItemStack inputItem = inv.getItem(0);

		if (inputItem != null && !inputItem.isEmpty()) {
			isItem = true;
		}

		ComponentElectrodynamic electro = getComponent(IComponentType.Electrodynamic);

		return timeSinceSpawn < 0 && isItem && (particles[0] == null || particles[1] == null) && inv.getItem(0).getCount() > 0 && resultStack.getCount() < resultStack.getMaxStackSize() && electro.getJoulesStored() >= Constants.PARTICLEINJECTOR_USAGE_PER_PARTICLE;
	}

	public void checkCollision() {
		ComponentInventory inv = getComponent(IComponentType.Inventory);
		ItemStack resultStack = inv.getItem(2);
		ItemStack cellStack = inv.getItem(1);
		if (resultStack.getCount() < resultStack.getMaxStackSize() && cellStack.getCount() > 0 && particles[0] != null && particles[1] != null) {
			EntityParticle one = particles[0];
			EntityParticle two = particles[1];
			if (one.distanceTo(two) < 1) {
				double speedOfMax = Math.pow((one.speed + two.speed) / 4.0, 2);
				one.remove(RemovalReason.KILLED);
				two.remove(RemovalReason.KILLED);
				particles[0] = particles[1] = null;
				double mod = level.random.nextDouble();
				if (speedOfMax > 0.999) {
					if (resultStack.getItem() == NuclearScienceItems.ITEM_CELLDARKMATTER.get()) {
						resultStack.setCount(resultStack.getCount() + 1);
						cellStack.shrink(1);
					} else if (resultStack.isEmpty()) {
						inv.setItem(2, new ItemStack(NuclearScienceItems.ITEM_CELLDARKMATTER.get()));
						cellStack.shrink(1);
					}
				} else if (speedOfMax > mod) {
					if (resultStack.getItem() == NuclearScienceItems.ITEM_CELLANTIMATTERSMALL.get()) {
						resultStack.setCount(resultStack.getCount() + 1);
						cellStack.shrink(1);
					} else if (resultStack.isEmpty()) {
						inv.setItem(2, new ItemStack(NuclearScienceItems.ITEM_CELLANTIMATTERSMALL.get()));
						cellStack.shrink(1);
					}
				}
			}
		}
	}

	public void process(ComponentProcessor processor) {
		// ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
		// electro.joules(electro.getJoulesStored() - Constants.PARTICLEINJECTOR_USAGE_PER_PARTICLE);
		timeSinceSpawn = 100;
		Direction dir = getFacing();
		ItemStack stack = this.<ComponentInventory>getComponent(IComponentType.Inventory).getItem(0);
		stack.shrink(1);
		EntityParticle particle = new EntityParticle(dir, level, new Location(worldPosition.getX() + 0.5f + dir.getStepX() * 1.5f, worldPosition.getY() + 0.5f + dir.getStepY() * 1.5f, worldPosition.getZ() + 0.5f + dir.getStepZ() * 1.5f));
		addParticle(particle);
		level.addFreshEntity(particle);
	}

	public void addParticle(EntityParticle particle) {
		if (particles[0] != particle && particles[1] != particle) {
			particle.source = getBlockPos();
			if (particles[0] == null) {
				particles[0] = particle;
			} else if (particles[1] == null) {
				particles[1] = particle;
			}
		}
	}

	@Override
	public AABB getRenderBoundingBox() {
		return INFINITE_EXTENT_AABB;
	}

	static {

		VoxelShape shape = Stream.of(
				//
				Block.box(15, 4, 4, 16, 12, 12),
				//
				Block.box(12, 0, 0, 15, 16, 16),
				//
				Block.box(15, 0, 15, 16, 16, 16),
				//
				Block.box(15, 0, 0, 16, 16, 1),
				//
				Block.box(15, 15, 1, 16, 16, 15),
				//
				Block.box(15, 0, 1, 16, 1, 15),
				//
				Block.box(9, 1, 1, 12, 15, 15),
				//
				Block.box(4, 1, 1, 5, 15, 15),
				//
				Block.box(1.5, 3, 3, 4, 13, 13),
				//
				Block.box(0, 0, 0, 0.5, 16, 16),
				//
				Block.box(0.5, 0.5, 0.5, 1, 15.5, 15.25),
				//
				Block.box(1, 1.5, 1.5, 1.5, 14.5, 14.25),
				//
				Block.box(5, 0, 0, 9, 16, 16)
		//
		).reduce((v1, v2) -> Shapes.or(v1, v2)).get();
		
		VoxelShapes.registerShape(NuclearScienceBlocks.blockParticleInjector, shape, Direction.WEST);

	}

}
