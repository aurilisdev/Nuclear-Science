package nuclearscience.common.inventory.container;

import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotRestricted;
import electrodynamics.prefab.screen.component.types.ScreenComponentSlot.SlotType;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import nuclearscience.common.tile.msreactor.TileFreezePlug;
import nuclearscience.prefab.screen.component.NuclearIconTypes;
import nuclearscience.registers.NuclearScienceItems;
import nuclearscience.registers.NuclearScienceMenuTypes;

public class ContainerFreezePlug extends GenericContainerBlockEntity<TileFreezePlug> {

	public ContainerFreezePlug(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainer(1), new SimpleContainerData(3));
	}

	public ContainerFreezePlug(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(NuclearScienceMenuTypes.CONTAINER_FREEZEPLUG.get(), id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		addSlot(new SlotRestricted(SlotType.NORMAL, NuclearIconTypes.PELLET_DARK, inv, nextIndex(), 15, 35).setRestriction(NuclearScienceItems.ITEM_FLINAK.get()));
	}
}
