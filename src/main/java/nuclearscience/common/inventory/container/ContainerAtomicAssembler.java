package nuclearscience.common.inventory.container;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import nuclearscience.common.tile.TileAtomicAssembler;
import nuclearscience.prefab.screen.component.NuclearIconTypes;
import nuclearscience.registers.NuclearScienceItems;
import nuclearscience.registers.NuclearScienceMenuTypes;
import voltaic.prefab.inventory.container.slot.item.SlotGeneric;
import voltaic.prefab.inventory.container.slot.item.type.SlotRestricted;
import voltaic.prefab.inventory.container.types.GenericContainerBlockEntity;
import voltaic.prefab.screen.component.types.ScreenComponentSlot;
import voltaic.prefab.utilities.math.Color;

public class ContainerAtomicAssembler extends GenericContainerBlockEntity<TileAtomicAssembler> {

    public ContainerAtomicAssembler(int id, Inventory playerinv) {
	this(id, playerinv, new SimpleContainer(8), new SimpleContainerData(3));
    }

    public ContainerAtomicAssembler(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
	super(NuclearScienceMenuTypes.CONTAINER_ATOMICASSEMBLER.get(), id, playerinv, inventory, inventorydata);
    }

    @Override
    public void addInventorySlots(Container inv, Inventory playerinv) {
	setPlayerInvOffset(64);
	addSlot(new SlotRestricted(ScreenComponentSlot.SlotType.NORMAL, NuclearIconTypes.FUEL_CELL_DARK, inv,
		nextIndex(), 60 + 18 * 3 / 2, 40).setRestriction(NuclearScienceItems.ITEM_CELLDARKMATTER.get())
		.setIOColor(new Color(0, 255, 30, 255)));
	addSlot(new SlotRestricted(ScreenComponentSlot.SlotType.NORMAL, NuclearIconTypes.FUEL_CELL_DARK, inv,
		nextIndex(), 33, 56).setRestriction(NuclearScienceItems.ITEM_CELLDARKMATTER.get())
		.setIOColor(new Color(0, 255, 30, 255)));
	addSlot(new SlotRestricted(ScreenComponentSlot.SlotType.NORMAL, NuclearIconTypes.FUEL_CELL_DARK, inv,
		nextIndex(), 114 + 27, 56).setRestriction(NuclearScienceItems.ITEM_CELLDARKMATTER.get())
		.setIOColor(new Color(0, 255, 30, 255)));
	addSlot(new SlotRestricted(ScreenComponentSlot.SlotType.NORMAL, NuclearIconTypes.FUEL_CELL_DARK, inv,
		nextIndex(), 33, 88).setRestriction(NuclearScienceItems.ITEM_CELLDARKMATTER.get())
		.setIOColor(new Color(0, 255, 30, 255)));
	addSlot(new SlotRestricted(ScreenComponentSlot.SlotType.NORMAL, NuclearIconTypes.FUEL_CELL_DARK, inv,
		nextIndex(), 114 + 27, 88).setRestriction(NuclearScienceItems.ITEM_CELLDARKMATTER.get())
		.setIOColor(new Color(0, 255, 30, 255)));
	addSlot(new SlotRestricted(ScreenComponentSlot.SlotType.NORMAL, NuclearIconTypes.FUEL_CELL_DARK, inv,
		nextIndex(), 60 + 18 * 3 / 2, 103).setRestriction(NuclearScienceItems.ITEM_CELLDARKMATTER.get())
		.setIOColor(new Color(0, 255, 30, 255)));

	addSlot(new SlotGeneric(inv, nextIndex(), 60, 72).setIOColor(new Color(0, 240, 255, 255)));
	addSlot(new SlotRestricted(inv, nextIndex(), 114, 72).setIOColor(new Color(255, 0, 0, 255)));
    }
}
