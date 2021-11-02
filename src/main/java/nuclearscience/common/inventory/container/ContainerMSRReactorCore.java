package nuclearscience.common.inventory.container;

import electrodynamics.prefab.inventory.container.GenericContainer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.tile.TileMSRReactorCore;

public class ContainerMSRReactorCore extends GenericContainer<TileMSRReactorCore> {

    public ContainerMSRReactorCore(int id, Inventory playerinv) {
	this(id, playerinv, new SimpleContainer(0), new SimpleContainerData(3));
    }

    public ContainerMSRReactorCore(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
	super(DeferredRegisters.CONTAINER_MSRREACTORCORE.get(), id, playerinv, new SimpleContainer(), inventorydata);
    }

    public ContainerMSRReactorCore(MenuType<?> type, int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
	super(type, id, playerinv, inventory, inventorydata);
    }

    @Override
    public void addInventorySlots(Container inv, Inventory playerinv) {
    }
}
