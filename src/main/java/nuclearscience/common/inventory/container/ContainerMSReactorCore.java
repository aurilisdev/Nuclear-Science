package nuclearscience.common.inventory.container;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import nuclearscience.common.tile.reactor.moltensalt.TileMSReactorCore;
import nuclearscience.registers.NuclearScienceMenuTypes;
import voltaic.prefab.inventory.container.types.GenericContainerBlockEntity;

public class ContainerMSReactorCore extends GenericContainerBlockEntity<TileMSReactorCore> {

	public ContainerMSReactorCore(int id, PlayerInventory playerinv) {
		this(id, playerinv, new Inventory(0), new IntArray(5));
	}

	public ContainerMSReactorCore(int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
		super(NuclearScienceMenuTypes.CONTAINER_MSRREACTORCORE.get(), id, playerinv, new Inventory(), inventorydata);
	}

	@Override
	public void addInventorySlots(IInventory inv, PlayerInventory playerinv) {
	}
}
