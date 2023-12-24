package nuclearscience.common.inventory.container;

import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotRestricted;
import electrodynamics.prefab.screen.component.types.ScreenComponentSlot.SlotType;
import electrodynamics.prefab.utilities.math.Color;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import nuclearscience.common.tile.fissionreactor.TileFissionReactorCore;
import nuclearscience.prefab.screen.component.NuclearIconTypes;
import nuclearscience.registers.NuclearScienceItems;
import nuclearscience.registers.NuclearScienceMenuTypes;

public class ContainerReactorCore extends GenericContainerBlockEntity<TileFissionReactorCore> {

	public ContainerReactorCore(int id, PlayerInventory playerinv) {
		this(id, playerinv, new Inventory(6), new IntArray(3));
	}

	public ContainerReactorCore(int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
		super(NuclearScienceMenuTypes.CONTAINER_REACTORCORE.get(), id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(IInventory inv, PlayerInventory playerinv) {
		playerInvOffset = 10;
		addSlot(new SlotRestricted(SlotType.NORMAL, NuclearIconTypes.FUEL_CELL_DARK, inv, nextIndex(), 80, 21).setRestriction(NuclearScienceItems.ITEM_FUELHEUO2.get(), NuclearScienceItems.ITEM_FUELLEUO2.get(), NuclearScienceItems.ITEM_FUELPLUTONIUM.get()).setIOColor(new Color(0, 255, 30, 255)));
		addSlot(new SlotRestricted(SlotType.NORMAL, NuclearIconTypes.FUEL_CELL_DARK, inv, nextIndex(), 152, 21).setRestriction(NuclearScienceItems.ITEM_FUELHEUO2.get(), NuclearScienceItems.ITEM_FUELLEUO2.get(), NuclearScienceItems.ITEM_FUELPLUTONIUM.get()).setIOColor(new Color(0, 255, 30, 255)));
		addSlot(new SlotRestricted(SlotType.NORMAL, NuclearIconTypes.FUEL_CELL_DARK, inv, nextIndex(), 80, 57).setRestriction(NuclearScienceItems.ITEM_FUELHEUO2.get(), NuclearScienceItems.ITEM_FUELLEUO2.get(), NuclearScienceItems.ITEM_FUELPLUTONIUM.get()).setIOColor(new Color(0, 255, 30, 255)));
		addSlot(new SlotRestricted(SlotType.NORMAL, NuclearIconTypes.FUEL_CELL_DARK, inv, nextIndex(), 152, 57).setRestriction(NuclearScienceItems.ITEM_FUELHEUO2.get(), NuclearScienceItems.ITEM_FUELLEUO2.get(), NuclearScienceItems.ITEM_FUELPLUTONIUM.get()).setIOColor(new Color(0, 255, 30, 255)));
		addSlot(new SlotRestricted(inv, nextIndex(), 116, 34).setRestriction(NuclearScienceItems.ITEM_CELLDEUTERIUM.get()).setIOColor(new Color(0, 240, 255, 255)));
		addSlot(new SlotRestricted(inv, nextIndex(), 116, 69).setIOColor(new Color(255, 0, 0, 255)));
	}
}