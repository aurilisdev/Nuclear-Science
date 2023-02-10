package nuclearscience.common.tile;

import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import nuclearscience.common.inventory.container.ContainerAtomicAssembler;
import nuclearscience.common.settings.Constants;
import nuclearscience.registers.NuclearScienceBlockTypes;
import nuclearscience.registers.NuclearScienceBlocks;
import nuclearscience.registers.NuclearScienceItems;

public class TileAtomicAssembler extends GenericTile {
	public Property<Integer> progress = property(new Property<>(PropertyType.Integer, "progress", 0));

	public TileAtomicAssembler(BlockPos pos, BlockState state) {
		super(NuclearScienceBlockTypes.TILE_ATOMICASSEMBLER.get(), pos, state);
		addComponent(new ComponentDirection());
		addComponent(new ComponentTickable().tickCommon(this::tickCommon));
		addComponent(new ComponentPacketHandler());
		addComponent(new ComponentElectrodynamic(this).maxJoules(Constants.ATOMICASSEMBLER_USAGE_PER_TICK * 20).voltage(Constants.ATOMICASSEMBLER_VOLTAGE).input(Direction.DOWN));
		// The slot == 6 has to be there to allow items into the input slot.
		addComponent(new ComponentInventory(this).size(8).faceSlots(Direction.UP, 0, 1, 2, 3, 4, 5, 6).slotFaces(6, Direction.DOWN, Direction.WEST, Direction.SOUTH, Direction.NORTH, Direction.EAST).valid((slot, stack, i) -> slot == 6 || slot < 6 && stack.is(NuclearScienceItems.ITEM_CELLDARKMATTER.get())));
		addComponent(new ComponentContainerProvider("container.atomicassembler").createMenu((id, player) -> new ContainerAtomicAssembler(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	}

	private void tickCommon(ComponentTickable tickable) {
		ComponentInventory inv = getComponent(ComponentType.Inventory);
		ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
		ItemStack input = inv.getItem(6);
		ItemStack output = inv.getItem(7);
		boolean validItem = (ItemStack.isSame(input, output) && output.getCount() + 1 <= output.getMaxStackSize() || output.isEmpty()) && !input.isEmpty() && !ItemUtils.testItems(input.getItem(), NuclearScienceItems.ITEM_CELLDARKMATTER.get()) && !input.getCapability(ForgeCapabilities.ITEM_HANDLER).isPresent();
		validItem = validItem && !(input.getItem() instanceof BlockItem bitem && bitem.getBlock() instanceof ShulkerBoxBlock) && input.getItem() != NuclearScienceBlocks.blockQuantumCapacitor.asItem();
		boolean canProcess = electro.getJoulesStored() >= Constants.ATOMICASSEMBLER_USAGE_PER_TICK && validItem;
		if (canProcess) {
			for (int index = 0; index < 6; index++) {
				ItemStack dmSlot = inv.getItem(index);
				if (dmSlot.is(NuclearScienceItems.ITEM_CELLDARKMATTER.get())) {
					if (dmSlot.getDamageValue() >= dmSlot.getMaxDamage()) {
						inv.setItem(index, ItemStack.EMPTY);
					}
				} else {
					canProcess = false;
				}
			}
		} else {
			progress.set(0);
		}

		boolean canProduce = false;
		if (canProcess) {
			if (progress.set(progress.get() + 1).get() >= Constants.ATOMICASSEMBLER_REQUIRED_TICKS) {
				canProduce = true;
			}
			electro.joules(electro.getJoulesStored() - Constants.ATOMICASSEMBLER_USAGE_PER_TICK);
		}
		if (canProduce) {
			progress.set(0);
			for (int index = 0; index < 6; index++) {
				ItemStack dmSlot = inv.getItem(index);
				if (dmSlot.is(NuclearScienceItems.ITEM_CELLDARKMATTER.get())) {
					if (dmSlot.getDamageValue() >= dmSlot.getMaxDamage()) {
						inv.setItem(index, ItemStack.EMPTY);
					}
					dmSlot.setDamageValue(dmSlot.getDamageValue() + 1);
				}
			}
			if (output.isEmpty()) {
				inv.setItem(7, input.copy());
				inv.getItem(7).setCount(1);
			} else {
				output.setCount(output.getCount() + 1);
			}
		}
	}
}
