package nuclearscience.common.inventory.container;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import nuclearscience.common.tile.TileTeleporter;
import nuclearscience.registers.NuclearScienceItems;
import nuclearscience.registers.NuclearScienceMenuTypes;
import voltaic.prefab.inventory.container.slot.item.type.SlotRestricted;
import voltaic.prefab.inventory.container.types.GenericContainerBlockEntity;

public class ContainerTeleporter extends GenericContainerBlockEntity<TileTeleporter> {

    public ContainerTeleporter(int id, PlayerInventory playerinv) {
        this(id, playerinv, new Inventory(1), new IntArray(5));
    }

    public ContainerTeleporter(int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
        super(NuclearScienceMenuTypes.CONTAINER_TELEPORTER.get(), id, playerinv, inventory, inventorydata);
    }

    @Override
    public void addInventorySlots(IInventory container, PlayerInventory inventory) {
        setPlayerInvOffset(50);
        addSlot(new SlotRestricted(container, nextIndex(), 31, 80).setRestriction(NuclearScienceItems.ITEM_FREQUENCYCARD.get()));
    }
}
