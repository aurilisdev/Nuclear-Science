package nuclearscience.common.inventory.container;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import nuclearscience.common.tile.TileNuclearBoiler;
import nuclearscience.registers.NuclearScienceMenuTypes;
import voltaic.common.item.subtype.SubtypeItemUpgrade;
import voltaic.prefab.inventory.container.slot.item.SlotGeneric;
import voltaic.prefab.inventory.container.slot.item.type.SlotFluid;
import voltaic.prefab.inventory.container.slot.item.type.SlotUpgrade;
import voltaic.prefab.inventory.container.types.GenericContainerBlockEntity;
import voltaic.prefab.utilities.math.Color;

public class ContainerNuclearBoiler extends GenericContainerBlockEntity<TileNuclearBoiler> {

	public static final SubtypeItemUpgrade[] VALID_UPGRADES = new SubtypeItemUpgrade[] { SubtypeItemUpgrade.basicspeed, SubtypeItemUpgrade.advancedspeed, SubtypeItemUpgrade.iteminput, SubtypeItemUpgrade.experience };

	public ContainerNuclearBoiler(int id, PlayerInventory playerinv) {
		this(id, playerinv, new Inventory(6), new IntArray(5));
	}

	public ContainerNuclearBoiler(int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
		super(NuclearScienceMenuTypes.CONTAINER_NUCLEARBOILER.get(), id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(IInventory inv, PlayerInventory playerinv) {
		addSlot(new SlotGeneric(inv, nextIndex(), 74, 31).setIOColor(new Color(0, 240, 255, 255)));
		addSlot(new SlotFluid(inv, this.nextIndex(), 38, 51));
		addSlot(new SlotFluid(inv, nextIndex(), 108, 51));
		addSlot(new SlotUpgrade(inv, nextIndex(), 150, 14, VALID_UPGRADES));
		addSlot(new SlotUpgrade(inv, nextIndex(), 150, 34, VALID_UPGRADES));
		addSlot(new SlotUpgrade(inv, nextIndex(), 150, 54, VALID_UPGRADES));
	}
}
