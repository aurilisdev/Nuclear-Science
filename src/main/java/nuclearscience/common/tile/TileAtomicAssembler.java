package nuclearscience.common.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import nuclearscience.common.inventory.container.ContainerAtomicAssembler;
import nuclearscience.common.reloadlistener.AtomicAssemblerBlacklistRegister;
import nuclearscience.common.reloadlistener.AtomicAssemblerWhitelistRegister;
import nuclearscience.common.settings.NuclearConfig;
import nuclearscience.registers.NuclearScienceItems;
import nuclearscience.registers.NuclearScienceTiles;
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
import voltaic.prefab.utilities.ItemUtils;
import voltaic.prefab.utilities.RadiationUtils;

public class TileAtomicAssembler extends GenericTile {

	public final SingleProperty<Integer> progress = property(new SingleProperty<>(PropertyTypes.INTEGER, "progress", 0));

	public TileAtomicAssembler(BlockPos pos, BlockState state) {
		super(NuclearScienceTiles.TILE_ATOMICASSEMBLER.get(), pos, state);

		addComponent(new ComponentTickable(this).tickCommon(this::tickServer));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentElectrodynamic(this, false, true).maxJoules(NuclearConfig.INSTANCE.ATOMICASSEMBLER_USAGE_PER_TICK.get() * 20).voltage(NuclearConfig.INSTANCE.ATOMICASSEMBLER_VOLTAGE.get()).setInputDirections(BlockEntityUtils.MachineDirection.BOTTOM));
		// The slot == 6 has to be there to allow items into the input slot.
		addComponent(new ComponentInventory(this, ComponentInventory.InventoryBuilder.newInv().inputs(7).outputs(1)).setSlotsByDirection(BlockEntityUtils.MachineDirection.TOP, 0, 1, 2, 3, 4, 5).setDirectionsBySlot(6, BlockEntityUtils.MachineDirection.RIGHT, BlockEntityUtils.MachineDirection.BACK)
				//
				.setDirectionsBySlot(7, BlockEntityUtils.MachineDirection.LEFT, BlockEntityUtils.MachineDirection.FRONT).valid((slot, stack, i) -> slot == 6 || slot < 6 && stack.is(NuclearScienceItems.ITEM_CELLDARKMATTER.get())));
		addComponent(new ComponentContainerProvider("atomicassembler", this).createMenu((id, player) -> new ContainerAtomicAssembler(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
	}

	private void tickServer(ComponentTickable tickable) {

		ComponentInventory inv = getComponent(IComponentType.Inventory);

		RadiationUtils.handleRadioactiveItems(this, inv, NuclearConfig.INSTANCE.ATOMIC_ASSEMBLER_RADIATION_RADIUS.get(), true, 30, true, false);

		ItemStack input = inv.getItem(6);

		if (input.isEmpty()) {
			progress.setValue(0);
			return;
		}

		ItemStack output = inv.getItem(7);

		boolean validItem = validateDupeItem(input) && (output.isEmpty() || ItemStack.isSameItem(input, output) && output.getCount() + 1 <= output.getMaxStackSize());

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

		if (electro.getJoulesStored() < NuclearConfig.INSTANCE.ATOMICASSEMBLER_USAGE_PER_TICK.get()) {
			return;
		}

		progress.setValue(progress.getValue() + 1);

		electro.joules(electro.getJoulesStored() - NuclearConfig.INSTANCE.ATOMICASSEMBLER_USAGE_PER_TICK.get());

		if (progress.getValue() < NuclearConfig.INSTANCE.ATOMICASSEMBLER_REQUIRED_TICKS.get()) {
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

	private static boolean validateDupeItem(ItemStack stack) {

		if(AtomicAssemblerWhitelistRegister.INSTANCE.isWhitelist(stack.getItem())) {
			return true;
		}

		if (AtomicAssemblerBlacklistRegister.INSTANCE.isBlacklisted(stack.getItem())) {
			return false;
		}

		if (stack.has(DataComponents.CONTAINER)) { // this should filter out shulker boxes with items
			return false;
		}

		if (ItemUtils.testItems(stack.getItem(), NuclearScienceItems.ITEM_CELLDARKMATTER.get()) && stack.getCapability(Capabilities.ItemHandler.ITEM) != null) {
			return false;
		}

		return true;

	}

}
