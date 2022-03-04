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
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.tile.TileMSRFuelPreProcessor;

public class ContainerMSRFuelPreProcessor extends GenericContainerBlockEntity<TileMSRFuelPreProcessor> {

	public static final SubtypeItemUpgrade[] VALID_UPGRADES = new SubtypeItemUpgrade[] { SubtypeItemUpgrade.basicspeed, SubtypeItemUpgrade.advancedspeed, SubtypeItemUpgrade.iteminput, SubtypeItemUpgrade.itemoutput, SubtypeItemUpgrade.experience };

	public ContainerMSRFuelPreProcessor(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainer(8), new SimpleContainerData(3));
	}

	public ContainerMSRFuelPreProcessor(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(DeferredRegisters.CONTAINER_MSRFUELPREPROCESSOR.get(), id, playerinv, inventory, inventorydata);
	}

	public ContainerMSRFuelPreProcessor(MenuType<?> type, int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(type, id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		addSlot(new SlotGeneric(inv, nextIndex(), 74, 20));
		addSlot(new SlotGeneric(inv, nextIndex(), 74, 40));
		addSlot(new SlotGeneric(inv, nextIndex(), 74, 60));
		addSlot(new FurnaceResultSlot(playerinv.player, inv, nextIndex(), 128, 40));
		addSlot(new SlotFluid(inv, nextIndex(), 45, 50));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 14, VALID_UPGRADES));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 34, VALID_UPGRADES));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 54, VALID_UPGRADES));
	}
}
