package nuclearscience.common.inventory.container;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import nuclearscience.common.tile.reactor.moltensalt.TileFreezePlug;
import nuclearscience.prefab.screen.component.NuclearIconTypes;
import nuclearscience.registers.NuclearScienceItems;
import nuclearscience.registers.NuclearScienceMenuTypes;
import voltaic.prefab.inventory.container.slot.item.type.SlotRestricted;
import voltaic.prefab.inventory.container.types.GenericContainerBlockEntity;
import voltaic.prefab.screen.component.types.ScreenComponentSlot;

public class ContainerFreezePlug extends GenericContainerBlockEntity<TileFreezePlug> {

	public ContainerFreezePlug(int id, PlayerInventory playerinv) {
		this(id, playerinv, new Inventory(1), new IntArray(5));
	}

	public ContainerFreezePlug(int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
		super(NuclearScienceMenuTypes.CONTAINER_FREEZEPLUG.get(), id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(IInventory inv, PlayerInventory playerinv) {
		addSlot(new SlotRestricted(ScreenComponentSlot.SlotType.NORMAL, NuclearIconTypes.PELLET_DARK, inv, nextIndex(), 15, 35).setRestriction(NuclearScienceItems.ITEM_FLINAK.get()));
	}
}
