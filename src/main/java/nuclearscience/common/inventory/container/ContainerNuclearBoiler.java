package nuclearscience.common.inventory.container;

import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.inventory.container.slot.item.SlotGeneric;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotFluid;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotUpgrade;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.tile.TileNuclearBoiler;

public class ContainerNuclearBoiler extends GenericContainerBlockEntity<TileNuclearBoiler> {

	public static final SubtypeItemUpgrade[] VALID_UPGRADES = new SubtypeItemUpgrade[] {SubtypeItemUpgrade.basicspeed, SubtypeItemUpgrade.advancedspeed, SubtypeItemUpgrade.iteminput, SubtypeItemUpgrade.experience};
	
	public ContainerNuclearBoiler(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainer(6), new SimpleContainerData(3));
	}

	public ContainerNuclearBoiler(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(DeferredRegisters.CONTAINER_NUCLEARBOILER.get(), id, playerinv, inventory, inventorydata);
	}

	public ContainerNuclearBoiler(MenuType<?> type, int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(type, id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		addSlot(new SlotGeneric(inv, nextIndex(), 74, 31));
		addSlot(new SlotFluid(inv, nextIndex(), 74, 51));
		addSlot(new SlotFluid(inv, nextIndex(), 108, 51));
		addSlot(new SlotUpgrade(inv, nextIndex(), 150, 14, VALID_UPGRADES));
		addSlot(new SlotUpgrade(inv, nextIndex(), 150, 34, VALID_UPGRADES));
		addSlot(new SlotUpgrade(inv, nextIndex(), 150, 54, VALID_UPGRADES));
	}
}
