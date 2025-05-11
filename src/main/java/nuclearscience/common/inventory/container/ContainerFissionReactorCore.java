package nuclearscience.common.inventory.container;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import nuclearscience.common.tile.reactor.fission.TileFissionReactorCore;
import nuclearscience.prefab.screen.component.NuclearIconTypes;
import nuclearscience.registers.NuclearScienceItems;
import nuclearscience.registers.NuclearScienceMenuTypes;
import voltaic.prefab.inventory.container.slot.item.type.SlotRestricted;
import voltaic.prefab.inventory.container.types.GenericContainerBlockEntity;
import voltaic.prefab.screen.component.types.ScreenComponentSlot;
import voltaic.prefab.utilities.math.Color;

public class ContainerFissionReactorCore extends GenericContainerBlockEntity<TileFissionReactorCore> {

	public ContainerFissionReactorCore(int id, PlayerInventory playerinv) {
		this(id, playerinv, new Inventory(6), new IntArray(3));
	}

	public ContainerFissionReactorCore(int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
		super(NuclearScienceMenuTypes.CONTAINER_REACTORCORE.get(), id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(IInventory inv, PlayerInventory playerinv) {
		setPlayerInvOffset(10);
		addSlot(new SlotRestricted(ScreenComponentSlot.SlotType.NORMAL, NuclearIconTypes.FUEL_CELL_DARK, inv, nextIndex(), 32, 21).setRestriction(NuclearScienceItems.ITEM_FUELHEUO2.get(), NuclearScienceItems.ITEM_FUELLEUO2.get(), NuclearScienceItems.ITEM_FUELPLUTONIUM.get()).setIOColor(new Color(0, 255, 30, 255)));
		addSlot(new SlotRestricted(ScreenComponentSlot.SlotType.NORMAL, NuclearIconTypes.FUEL_CELL_DARK, inv, nextIndex(), 128, 21).setRestriction(NuclearScienceItems.ITEM_FUELHEUO2.get(), NuclearScienceItems.ITEM_FUELLEUO2.get(), NuclearScienceItems.ITEM_FUELPLUTONIUM.get()).setIOColor(new Color(0, 255, 30, 255)));
		addSlot(new SlotRestricted(ScreenComponentSlot.SlotType.NORMAL, NuclearIconTypes.FUEL_CELL_DARK, inv, nextIndex(), 32, 57).setRestriction(NuclearScienceItems.ITEM_FUELHEUO2.get(), NuclearScienceItems.ITEM_FUELLEUO2.get(), NuclearScienceItems.ITEM_FUELPLUTONIUM.get()).setIOColor(new Color(0, 255, 30, 255)));
		addSlot(new SlotRestricted(ScreenComponentSlot.SlotType.NORMAL, NuclearIconTypes.FUEL_CELL_DARK, inv, nextIndex(), 128, 57).setRestriction(NuclearScienceItems.ITEM_FUELHEUO2.get(), NuclearScienceItems.ITEM_FUELLEUO2.get(), NuclearScienceItems.ITEM_FUELPLUTONIUM.get()).setIOColor(new Color(0, 255, 30, 255)));
		addSlot(new SlotRestricted(inv, nextIndex(), 55, 39).setRestriction(NuclearScienceItems.ITEM_CELLDEUTERIUM.get()).setIOColor(new Color(0, 240, 255, 255)));
		addSlot(new SlotRestricted(inv, nextIndex(), 105, 39).setIOColor(new Color(255, 0, 0, 255)));
	}
}
