package nuclearscience.common.inventory.container;

import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.inventory.container.slot.item.SlotGeneric;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotRestricted;
import electrodynamics.prefab.screen.component.types.ScreenComponentSlot.SlotType;
import electrodynamics.prefab.utilities.math.Color;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import nuclearscience.common.tile.TileAtomicAssembler;
import nuclearscience.prefab.screen.component.NuclearIconTypes;
import nuclearscience.registers.NuclearScienceItems;
import nuclearscience.registers.NuclearScienceMenuTypes;

public class ContainerAtomicAssembler extends GenericContainerBlockEntity<TileAtomicAssembler> {

	public ContainerAtomicAssembler(int id, PlayerInventory playerinv) {
		this(id, playerinv, new Inventory(8), new IntArray(3));
	}

	public ContainerAtomicAssembler(int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
		super(NuclearScienceMenuTypes.CONTAINER_ATOMICASSEMBLER.get(), id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(IInventory inv, PlayerInventory playerinv) {
		playerInvOffset = 64;
		addSlot(new SlotRestricted(SlotType.NORMAL, NuclearIconTypes.FUEL_CELL_DARK, inv, nextIndex(), 60 + 18 * 3 / 2, 40).setRestriction(NuclearScienceItems.ITEM_CELLDARKMATTER.get()).setIOColor(new Color(0, 255, 30, 255)));
		addSlot(new SlotRestricted(SlotType.NORMAL, NuclearIconTypes.FUEL_CELL_DARK, inv, nextIndex(), 33, 56).setRestriction(NuclearScienceItems.ITEM_CELLDARKMATTER.get()).setIOColor(new Color(0, 255, 30, 255)));
		addSlot(new SlotRestricted(SlotType.NORMAL, NuclearIconTypes.FUEL_CELL_DARK, inv, nextIndex(), 114 + 27, 56).setRestriction(NuclearScienceItems.ITEM_CELLDARKMATTER.get()).setIOColor(new Color(0, 255, 30, 255)));
		addSlot(new SlotRestricted(SlotType.NORMAL, NuclearIconTypes.FUEL_CELL_DARK, inv, nextIndex(), 33, 88).setRestriction(NuclearScienceItems.ITEM_CELLDARKMATTER.get()).setIOColor(new Color(0, 255, 30, 255)));
		addSlot(new SlotRestricted(SlotType.NORMAL, NuclearIconTypes.FUEL_CELL_DARK, inv, nextIndex(), 114 + 27, 88).setRestriction(NuclearScienceItems.ITEM_CELLDARKMATTER.get()).setIOColor(new Color(0, 255, 30, 255)));
		addSlot(new SlotRestricted(SlotType.NORMAL, NuclearIconTypes.FUEL_CELL_DARK, inv, nextIndex(), 60 + 18 * 3 / 2, 103).setRestriction(NuclearScienceItems.ITEM_CELLDARKMATTER.get()).setIOColor(new Color(0, 255, 30, 255)));

		addSlot(new SlotGeneric(inv, nextIndex(), 60, 72).setIOColor(new Color(0, 240, 255, 255)));
		addSlot(new SlotRestricted(inv, nextIndex(), 114, 72).setIOColor(new Color(255, 0, 0, 255)));
	}
}