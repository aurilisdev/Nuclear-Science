package nuclearscience.common.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.CapabilityItemHandler;
import nuclearscience.common.inventory.container.ContainerAtomicAssembler;
import nuclearscience.common.reloadlistener.AtomicAssemblerBlacklistRegister;
import nuclearscience.common.reloadlistener.AtomicAssemblerWhitelistRegister;
import nuclearscience.common.settings.NuclearConstants;
import nuclearscience.registers.NuclearScienceItems;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.*;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.CapabilityUtils;
import voltaic.prefab.utilities.ItemUtils;
import voltaic.prefab.utilities.RadiationUtils;

public class TileAtomicAssembler extends GenericTile {

	public final SingleProperty<Integer> progress = property(new SingleProperty<>(PropertyTypes.INTEGER, "progress", 0));

	public TileAtomicAssembler(BlockPos pos, BlockState state) {
		super(NuclearScienceTiles.TILE_ATOMICASSEMBLER.get(), pos, state);

		addComponent(new ComponentTickable(this).tickCommon(this::tickServer));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentElectrodynamic(this, false, true).maxJoules(NuclearConstants.ATOMICASSEMBLER_USAGE_PER_TICK * 20).voltage(NuclearConstants.ATOMICASSEMBLER_VOLTAGE).setInputDirections(BlockEntityUtils.MachineDirection.BOTTOM));
		// The slot == 6 has to be there to allow items into the input slot.
		addComponent(new ComponentInventory(this, ComponentInventory.InventoryBuilder.newInv().inputs(7).outputs(1)).setSlotsByDirection(BlockEntityUtils.MachineDirection.TOP, 0, 1, 2, 3, 4, 5).setDirectionsBySlot(6, BlockEntityUtils.MachineDirection.RIGHT, BlockEntityUtils.MachineDirection.BACK)
				//
				.setDirectionsBySlot(7, BlockEntityUtils.MachineDirection.LEFT, BlockEntityUtils.MachineDirection.FRONT).valid((slot, stack, i) -> slot == 6 || slot < 6 && stack.is(NuclearScienceItems.ITEM_CELLDARKMATTER.get())));
		addComponent(new ComponentContainerProvider("atomicassembler", this).createMenu((id, player) -> new ContainerAtomicAssembler(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}

	private void tickServer(ComponentTickable tickable) {

		ComponentInventory inv = getComponent(IComponentType.Inventory);

		ItemStack input = inv.getItem(6);

		if (input.isEmpty()) {
			progress.setValue(0);
			return;
		}

		RadiationUtils.handleRadioactiveItems(this, inv, NuclearConstants.ATOMIC_ASSEMBLER_RADIATION_RADIUS, true, 1, true, false);

		ItemStack output = inv.getItem(7);

		boolean validItem = validateDupeItem(input) && (output.isEmpty() || ItemStack.isSameItemSameTags(input, output) && output.getCount() + 1 <= output.getMaxStackSize());

		if (!validItem) {
			progress.setValue(0);
			return;
		}

		for (int index = 0; index < 6; index++) {

			ItemStack dmCell = inv.getItem(index);

			if (dmCell.isEmpty() || dmCell.getItem() != NuclearScienceItems.ITEM_CELLDARKMATTER.get()) {
				progress.setValue(0);
				return;
			}

			if (dmCell.getDamageValue() >= dmCell.getMaxDamage()) {
				progress.setValue(0);
				inv.setItem(index, ItemStack.EMPTY);
				return;
			}

		}

		ComponentElectrodynamic electro = getComponent(IComponentType.Electrodynamic);

		if (electro.getJoulesStored() < NuclearConstants.ATOMICASSEMBLER_USAGE_PER_TICK) {
			return;
		}

		progress.setValue(progress.getValue() + 1);

		electro.joules(electro.getJoulesStored() - NuclearConstants.ATOMICASSEMBLER_USAGE_PER_TICK);

		if (progress.getValue() < NuclearConstants.ATOMICASSEMBLER_REQUIRED_TICKS) {
			return;
		}

		progress.setValue(0);

		for (int index = 0; index < 6; index++) {

			ItemStack dmCell = inv.getItem(index);

			dmCell.setDamageValue(dmCell.getDamageValue() + 1);

			if (dmCell.getDamageValue() >= dmCell.getMaxDamage()) {
				inv.setItem(index, ItemStack.EMPTY);
			}
		}

		if (output.isEmpty()) {

			inv.setItem(7, new ItemStack(input.getItem()));

		} else {

			output.setCount(output.getCount() + 1);

		}
	}

	private boolean validateDupeItem(ItemStack stack) {

		if(AtomicAssemblerWhitelistRegister.INSTANCE.isWhitelisted(stack.getItem())) {
			return true;
		}

		if (AtomicAssemblerBlacklistRegister.INSTANCE.isBlacklisted(stack.getItem())) {
			return false;
		}

		if (stack.hasTag()) { // this should filter out shulker boxes with items
			return false;
		}

		if (ItemUtils.testItems(stack.getItem(), NuclearScienceItems.ITEM_CELLDARKMATTER.get()) && stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(CapabilityUtils.EMPTY_ITEM_HANDLER) != CapabilityUtils.EMPTY_ITEM_HANDLER) {
			return false;
		}

		return true;

	}

}
