package nuclearscience.common.inventory.container;

import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotRestricted;
import electrodynamics.prefab.screen.component.types.ScreenComponentSlot.SlotType;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import nuclearscience.common.tile.TileFissionReactorCore;
import nuclearscience.prefab.screen.component.NuclearIconTypes;
import nuclearscience.registers.NuclearScienceItems;
import nuclearscience.registers.NuclearScienceMenuTypes;

public class ContainerReactorCore extends GenericContainerBlockEntity<TileFissionReactorCore> {

	public ContainerReactorCore(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainer(6), new SimpleContainerData(3));
	}

	public ContainerReactorCore(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(NuclearScienceMenuTypes.CONTAINER_REACTORCORE.get(), id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		playerInvOffset = 10;
		addSlot(new SlotRestricted(SlotType.NORMAL, NuclearIconTypes.FUEL_CELL_DARK, inv, nextIndex(), 80, 21).setRestriction(NuclearScienceItems.ITEM_FUELHEUO2.get(), NuclearScienceItems.ITEM_FUELLEUO2.get(), NuclearScienceItems.ITEM_FUELPLUTONIUM.get()));
		addSlot(new SlotRestricted(SlotType.NORMAL, NuclearIconTypes.FUEL_CELL_DARK, inv, nextIndex(), 152, 21).setRestriction(NuclearScienceItems.ITEM_FUELHEUO2.get(), NuclearScienceItems.ITEM_FUELLEUO2.get(), NuclearScienceItems.ITEM_FUELPLUTONIUM.get()));
		addSlot(new SlotRestricted(SlotType.NORMAL, NuclearIconTypes.FUEL_CELL_DARK, inv, nextIndex(), 80, 57).setRestriction(NuclearScienceItems.ITEM_FUELHEUO2.get(), NuclearScienceItems.ITEM_FUELLEUO2.get(), NuclearScienceItems.ITEM_FUELPLUTONIUM.get()));
		addSlot(new SlotRestricted(SlotType.NORMAL, NuclearIconTypes.FUEL_CELL_DARK, inv, nextIndex(), 152, 57).setRestriction(NuclearScienceItems.ITEM_FUELHEUO2.get(), NuclearScienceItems.ITEM_FUELLEUO2.get(), NuclearScienceItems.ITEM_FUELPLUTONIUM.get()));
		addSlot(new SlotRestricted(inv, nextIndex(), 116, 34).setRestriction(NuclearScienceItems.ITEM_CELLDEUTERIUM.get()));
		addSlot(new SlotRestricted(inv, nextIndex(), 116, 69).setRestriction(NuclearScienceItems.ITEM_CELLDEUTERIUM.get()));
	}
}
