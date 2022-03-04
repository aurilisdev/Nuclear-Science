package nuclearscience.common.inventory.container;

import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotUpgrade;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.tile.TileGasCentrifuge;

public class ContainerGasCentrifuge extends GenericContainerBlockEntity<TileGasCentrifuge> {

	public static final SubtypeItemUpgrade[] VALID_UPGRADES = new SubtypeItemUpgrade[] {SubtypeItemUpgrade.basicspeed, SubtypeItemUpgrade.advancedspeed, SubtypeItemUpgrade.itemoutput, SubtypeItemUpgrade.experience};
	
	public ContainerGasCentrifuge(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainer(6), new SimpleContainerData(3));
	}

	public ContainerGasCentrifuge(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(DeferredRegisters.CONTAINER_GASCENTRIFUGE.get(), id, playerinv, inventory, inventorydata);
	}

	public ContainerGasCentrifuge(MenuType<?> type, int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(type, id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		addSlot(new FurnaceResultSlot(playerinv.player, inv, nextIndex(), 129, 14));
		addSlot(new FurnaceResultSlot(playerinv.player, inv, nextIndex(), 129, 34));
		addSlot(new FurnaceResultSlot(playerinv.player, inv, nextIndex(), 129, 55));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 14, VALID_UPGRADES));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 34, VALID_UPGRADES));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 55, VALID_UPGRADES));
	}
}
