package nuclearscience.common.inventory.container;

import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import nuclearscience.common.tile.msreactor.TileMSReactorCore;
import nuclearscience.registers.NuclearScienceMenuTypes;

public class ContainerMSRReactorCore extends GenericContainerBlockEntity<TileMSReactorCore> {

	public ContainerMSRReactorCore(int id, PlayerInventory playerinv) {
		this(id, playerinv, new Inventory(), new IntArray(3));
	}

	public ContainerMSRReactorCore(int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
		super(NuclearScienceMenuTypes.CONTAINER_MSRREACTORCORE.get(), id, playerinv, new Inventory(), inventorydata);
	}

	@Override
	public void addInventorySlots(IInventory inv, PlayerInventory playerinv) {
	}
}