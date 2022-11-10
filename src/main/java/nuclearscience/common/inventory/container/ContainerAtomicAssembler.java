package nuclearscience.common.inventory.container;

import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.inventory.container.slot.item.SlotGeneric;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotRestricted;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import nuclearscience.common.tile.TileAtomicAssembler;
import nuclearscience.registers.NuclearScienceItems;
import nuclearscience.registers.NuclearScienceMenuTypes;

public class ContainerAtomicAssembler extends GenericContainerBlockEntity<TileAtomicAssembler> {

	public ContainerAtomicAssembler(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainer(8), new SimpleContainerData(3));
	}

	public ContainerAtomicAssembler(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(NuclearScienceMenuTypes.CONTAINER_ATOMICASSEMBLER.get(), id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		playerInvOffset = 64;
		addSlot(new SlotRestricted(inv, nextIndex(), 60 + 18 * 3 / 2, 40, NuclearScienceItems.ITEM_CELLDARKMATTER.get()));
		addSlot(new SlotRestricted(inv, nextIndex(), 33, 56, NuclearScienceItems.ITEM_CELLDARKMATTER.get()));
		addSlot(new SlotRestricted(inv, nextIndex(), 114 + 27, 56, NuclearScienceItems.ITEM_CELLDARKMATTER.get()));
		addSlot(new SlotRestricted(inv, nextIndex(), 33, 88, NuclearScienceItems.ITEM_CELLDARKMATTER.get()));
		addSlot(new SlotRestricted(inv, nextIndex(), 114 + 27, 88, NuclearScienceItems.ITEM_CELLDARKMATTER.get()));
		addSlot(new SlotRestricted(inv, nextIndex(), 60 + 18 * 3 / 2, 103, NuclearScienceItems.ITEM_CELLDARKMATTER.get()));

		addSlot(new SlotGeneric(inv, nextIndex(), 60, 72));
		addSlot(new SlotGeneric(inv, nextIndex(), 114, 72));
	}
}
