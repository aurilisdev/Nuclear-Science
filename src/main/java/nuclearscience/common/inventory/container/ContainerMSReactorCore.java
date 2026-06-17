package nuclearscience.common.inventory.container;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import nuclearscience.common.tile.reactor.moltensalt.TileMSReactorCore;
import nuclearscience.registers.NuclearScienceMenuTypes;
import voltaic.prefab.inventory.container.types.GenericContainerBlockEntity;

public class ContainerMSReactorCore extends GenericContainerBlockEntity<TileMSReactorCore> {

    public ContainerMSReactorCore(int id, Inventory playerinv) {
	this(id, playerinv, new SimpleContainer(0), new SimpleContainerData(3));
    }

    public ContainerMSReactorCore(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
	super(NuclearScienceMenuTypes.CONTAINER_MSRREACTORCORE.get(), id, playerinv, new SimpleContainer(),
		inventorydata);
    }

    @Override
    public void addInventorySlots(Container inv, Inventory playerinv) {
    }
}
