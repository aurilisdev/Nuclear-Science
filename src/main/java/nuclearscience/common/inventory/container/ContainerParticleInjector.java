package nuclearscience.common.inventory.container;

import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.inventory.container.slot.item.SlotGeneric;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotRestricted;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.tile.TileParticleInjector;

public class ContainerParticleInjector extends GenericContainerBlockEntity<TileParticleInjector> {

	public ContainerParticleInjector(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainer(3), new SimpleContainerData(3));
	}

	public ContainerParticleInjector(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(DeferredRegisters.CONTAINER_PARTICLEINJECTOR.get(), id, playerinv, inventory, inventorydata);
	}

	public ContainerParticleInjector(MenuType<?> type, int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(type, id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		addSlot(new SlotGeneric(inv, nextIndex(), 98, 14));
		addSlot(new SlotRestricted(inv, nextIndex(), 98, 50, DeferredRegisters.ITEM_CELLELECTROMAGNETIC.get()));
		addSlot(new FurnaceResultSlot(playerinv.player, inv, nextIndex(), 133, 32));
	}
}
