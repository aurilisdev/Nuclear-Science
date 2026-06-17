package nuclearscience.common.inventory.container;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import nuclearscience.common.tile.reactor.moltensalt.TileFreezePlug;
import nuclearscience.prefab.screen.component.NuclearIconTypes;
import nuclearscience.registers.NuclearScienceItems;
import nuclearscience.registers.NuclearScienceMenuTypes;
import voltaic.prefab.inventory.container.slot.item.type.SlotRestricted;
import voltaic.prefab.inventory.container.types.GenericContainerBlockEntity;
import voltaic.prefab.screen.component.types.ScreenComponentSlot;

public class ContainerFreezePlug extends GenericContainerBlockEntity<TileFreezePlug> {

    public ContainerFreezePlug(int id, Inventory playerinv) {
	this(id, playerinv, new SimpleContainer(1), new SimpleContainerData(5));
    }

    public ContainerFreezePlug(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
	super(NuclearScienceMenuTypes.CONTAINER_FREEZEPLUG.get(), id, playerinv, inventory, inventorydata);
    }

    @Override
    public void addInventorySlots(Container inv, Inventory playerinv) {
	addSlot(new SlotRestricted(ScreenComponentSlot.SlotType.NORMAL, NuclearIconTypes.PELLET_DARK, inv, nextIndex(),
		15, 35).setRestriction(NuclearScienceItems.ITEM_FLINAK.get()));
    }
}
