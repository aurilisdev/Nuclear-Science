package nuclearscience.common.inventory.container;

import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.inventory.container.slot.item.SlotGeneric;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotFluid;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotRestricted;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotUpgrade;
import electrodynamics.prefab.utilities.math.Color;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import nuclearscience.common.tile.TileRadioactiveProcessor;
import nuclearscience.registers.NuclearScienceMenuTypes;

public class ContainerRadioactiveProcessor extends GenericContainerBlockEntity<TileRadioactiveProcessor> {

	public static final SubtypeItemUpgrade[] VALID_UPGRADES = new SubtypeItemUpgrade[] { SubtypeItemUpgrade.basicspeed, SubtypeItemUpgrade.advancedspeed, SubtypeItemUpgrade.iteminput, SubtypeItemUpgrade.itemoutput, SubtypeItemUpgrade.experience };

	public ContainerRadioactiveProcessor(int id, PlayerInventory playerinv) {
		this(id, playerinv, new Inventory(6), new IntArray(3));
	}

	public ContainerRadioactiveProcessor(int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
		super(NuclearScienceMenuTypes.CONTAINER_RADIOACTIVEPROCESSOR.get(), id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(IInventory inv, PlayerInventory playerinv) {
		addSlot(new SlotGeneric(inv, nextIndex(), 74, 31).setIOColor(new Color(0, 240, 255, 255)));
		addSlot(new SlotRestricted(inv, nextIndex(), 128, 31).setIOColor(new Color(255, 0, 0, 255)));
		addSlot(new SlotFluid(inv, nextIndex(), 74, 51));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 14, VALID_UPGRADES));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 34, VALID_UPGRADES));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 54, VALID_UPGRADES));
	}
}