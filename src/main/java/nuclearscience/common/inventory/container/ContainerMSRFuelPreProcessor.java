package nuclearscience.common.inventory.container;

import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.inventory.container.slot.GenericSlot;
import electrodynamics.prefab.inventory.container.slot.SlotRestricted;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.tile.TileMSRFuelPreProcessor;

public class ContainerMSRFuelPreProcessor extends GenericContainerBlockEntity<TileMSRFuelPreProcessor> {

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
		addSlot(new GenericSlot(inv, nextIndex(), 74, 20));
		addSlot(new GenericSlot(inv, nextIndex(), 74, 40));
		addSlot(new GenericSlot(inv, nextIndex(), 74, 60));
		addSlot(new FurnaceResultSlot(playerinv.player, inv, nextIndex(), 128, 40));
		addSlot(new SlotRestricted(inv, nextIndex(), 45, 50, 0, CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY));
		addSlot(new SlotRestricted(inv, nextIndex(), 153, 14,
				electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeItemUpgrade.basicspeed),
				electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeItemUpgrade.advancedspeed)));
		addSlot(new SlotRestricted(inv, nextIndex(), 153, 34,
				electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeItemUpgrade.basicspeed),
				electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeItemUpgrade.advancedspeed)));
		addSlot(new SlotRestricted(inv, nextIndex(), 153, 54,
				electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeItemUpgrade.basicspeed),
				electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeItemUpgrade.advancedspeed)));
	}
}
