package nuclearscience.common.tile.accelerator;

import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.common.entity.EntityParticle;
import nuclearscience.common.inventory.container.ContainerParticleInjector;
import nuclearscience.common.settings.NuclearConstants;
import nuclearscience.registers.NuclearScienceBlocks;
import nuclearscience.registers.NuclearScienceItems;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.Voltaic;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.tile.components.type.ComponentInventory;
import voltaic.prefab.tile.components.type.ComponentPacketHandler;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.RadiationUtils;
import voltaic.prefab.utilities.object.Location;
import voltaic.registers.VoltaicCapabilities;

public class TileParticleInjector extends GenericTile {

	public static final int INPUT_SLOT = 0;
	public static final int ELECTRO_CELL_SLOT = 1;
	public static final int OUTPUT_SLOT = 2;

	public final EntityParticle[] particles = new EntityParticle[2];
	public int timeSinceSpawn = 0;

	public final SingleProperty<Boolean> usingGateway = property(new SingleProperty<>(PropertyTypes.BOOLEAN, "usinggateway", false));
	public final SingleProperty<Boolean> hasRedstoneSignal = property(new SingleProperty<>(PropertyTypes.BOOLEAN, "hasredstonesignal", false));


	public TileParticleInjector(BlockPos pos, BlockState state) {
		super(NuclearScienceTiles.TILE_PARTICLEINJECTOR.get(), pos, state);
		addComponent(new ComponentTickable(this).tickServer(this::tickServer).tickCommon(this::tickCommon));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentInventory(this, ComponentInventory.InventoryBuilder.newInv().inputs(2).outputs(1)).valid((index, stack, i) -> index != 1 || stack.getItem() == NuclearScienceItems.ITEM_CELLELECTROMAGNETIC.get()).setSlotsByDirection(BlockEntityUtils.MachineDirection.TOP, 0, 1)
				//
				.setSlotsByDirection(BlockEntityUtils.MachineDirection.RIGHT, 0, 1).setDirectionsBySlot(2, BlockEntityUtils.MachineDirection.BOTTOM, BlockEntityUtils.MachineDirection.LEFT));
		addComponent(new ComponentElectrodynamic(this, false, true).voltage(VoltaicCapabilities.DEFAULT_VOLTAGE * 8).setInputDirections(BlockEntityUtils.MachineDirection.BACK).maxJoules(NuclearConstants.PARTICLEINJECTOR_USAGE_PER_PARTICLE));
		addComponent(new ComponentContainerProvider("particleinjector", this).createMenu((id, player) -> new ContainerParticleInjector(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}

	private void tickCommon(ComponentTickable tickable) {

		if (particles[0] != null && !particles[0].isAlive()) {
			particles[0] = null;
		}
		if (particles[1] != null && !particles[1].isAlive()) {
			particles[1] = null;
		}

		if(particles[0] == null && particles[1] != null) {
			particles[0] = particles[1];
			particles[1] = null;
		}

	}

	private void tickServer(ComponentTickable componentTickable) {

		if(hasRedstoneSignal.getValue()) {
			return;
		}

		RadiationUtils.handleRadioactiveItems(this, (ComponentInventory) getComponent(IComponentType.Inventory), NuclearConstants.PARTICLE_INJECTOR_RADIATION_RADIUS, true, 1, true, false);

		ComponentElectrodynamic electro = getComponent(IComponentType.Electrodynamic);
		ComponentInventory inv = getComponent(IComponentType.Inventory);

		ItemStack input = inv.getItem(INPUT_SLOT);

		if(electro.getJoulesStored() < NuclearConstants.PARTICLEINJECTOR_USAGE_PER_PARTICLE || input.isEmpty()) {
			return;
		}

		if(timeSinceSpawn > 0) {
			timeSinceSpawn--;
			return;
		}

		if(usingGateway.getValue() && particles[0] != null && !particles[0].passedThroughGate) {
			return;
		} else if (!usingGateway.getValue() && !level.getBlockState(worldPosition.relative(getFacing())).is(NuclearScienceBlocks.BLOCK_ELECTORMAGNETICBOOSTER.get())) {
			return;
		}

		if(particles[0] != null && particles[1] != null) {
			return;
		}

		ItemStack resultStack = inv.getItem(OUTPUT_SLOT);

		if(resultStack.getCount() >= resultStack.getMaxStackSize()) {
			return;
		}

		timeSinceSpawn = NuclearConstants.DEFAULT_PARTICLE_COOLDOWN_TICKS;

		input.shrink(1);

		Direction dir = getFacing();

		EntityParticle particle = new EntityParticle(dir, level, new Location(worldPosition.getX() + 0.5f + dir.getStepX(), worldPosition.getY() + 0.5f + dir.getStepY(), worldPosition.getZ() + 0.5f + dir.getStepZ()), getBlockPos());

		addParticle(particle);

		level.addFreshEntity(particle);

		electro.setJoulesStored(electro.getJoulesStored() - NuclearConstants.PARTICLEINJECTOR_USAGE_PER_PARTICLE);


	}

	// returns if collision was successful or not
	// We can track particles on the client too to monitor things like speed and what not
	// in a GUI
	public boolean handleCollision() {

		ComponentInventory inv = getComponent(IComponentType.Inventory);

		ItemStack resultStack = inv.getItem(OUTPUT_SLOT);
		ItemStack cellStack = inv.getItem(ELECTRO_CELL_SLOT);

		if(particles[0] == null || particles[1] == null) {
			return false;
		}

		EntityParticle one = particles[0];
		EntityParticle two = particles[1];

		if(one.distanceTo(two) >= 1) {
			return false;
		}

		BlockPos pos = one.blockPosition();

		if(!level.isClientSide()) {

			level.playSound(null, pos, SoundEvents.END_PORTAL_SPAWN, SoundSource.BLOCKS, 1, 1);

			one.remove(RemovalReason.KILLED);
			two.remove(RemovalReason.KILLED);

			Random random = Voltaic.RANDOM;

			for(int i = 0; i < 50; i++) {
				double d0 = pos.getX() + random.nextDouble();
				double d1 = pos.getY() + random.nextDouble();
				double d2 = pos.getZ() + random.nextDouble();
				double d3 = (random.nextFloat() - 0.5) * 0.5;

				((ServerLevel) level).sendParticles(ParticleTypes.PORTAL, d0, d1, d2, 1, 0, 0, 0, d3);
			}

			if(!cellStack.isEmpty() && resultStack.getCount() < resultStack.getMaxStackSize() && one.speed >= EntityParticle.MIN_COLLISION_SPEED && two.speed >= EntityParticle.MIN_COLLISION_SPEED) {

				double speedOfMax = Math.pow((one.speed + two.speed) / 4.0, 2);

				if (speedOfMax > 0.999) { //Speed needs to be 1.999
					if (resultStack.getItem() == NuclearScienceItems.ITEM_CELLDARKMATTER.get()) {
						resultStack.setCount(resultStack.getCount() + 1);
						cellStack.shrink(1);
					} else if (resultStack.isEmpty()) {
						inv.setItem(2, new ItemStack(NuclearScienceItems.ITEM_CELLDARKMATTER.get()));
						cellStack.shrink(1);
					}
				} else if (speedOfMax > level.random.nextDouble()) {
					if (resultStack.getItem() == NuclearScienceItems.ITEM_CELLANTIMATTERSMALL.get()) {
						resultStack.setCount(resultStack.getCount() + 1);
						cellStack.shrink(1);
					} else if (resultStack.isEmpty()) {
						inv.setItem(2, new ItemStack(NuclearScienceItems.ITEM_CELLANTIMATTERSMALL.get()));
						cellStack.shrink(1);
					}
				}

			}

			particles[0] = particles[1] = null;

		}

		return true;

	}


	public void addParticle(EntityParticle particle) {

		if(particles[0] == null && particles[1] == null) {
			particles[0] = particle;
		}

		if(particles[0] != null) {
			if(particles[0].getUUID().equals(particle.getUUID())) {
				return;
			} else if (particles[1] == null) {
				particles[1] = particle;
			}
		}

		if(particles[1] != null) {
			if(particles[1].getUUID().equals(particle.getUUID())) {
				return;
			} else if(particles[0] == null) {
				particles[0] = particle;
			}
		}

	}

	@Override
	protected void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
		super.saveAdditional(compound, registries);
		compound.putInt("timesincespawn", timeSinceSpawn);
	}

	@Override
	protected void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
		super.loadAdditional(compound, registries);
		timeSinceSpawn = compound.getInt("timesincespawn");
	}

	@Override
	public void onNeightborChanged(BlockPos neighbor, boolean blockStateTrigger) {
		if(!level.isClientSide()) {
			hasRedstoneSignal.setValue(level.hasNeighborSignal(getBlockPos()));
		}
	}
}
