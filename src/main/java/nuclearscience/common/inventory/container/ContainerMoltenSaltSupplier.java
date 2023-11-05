package nuclearscience.common.inventory.container;

import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotRestricted;
import electrodynamics.prefab.screen.component.types.ScreenComponentSlot.SlotType;
import electrodynamics.prefab.utilities.math.Color;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import nuclearscience.common.tile.msreactor.TileMoltenSaltSupplier;
import nuclearscience.prefab.screen.component.NuclearIconTypes;
import nuclearscience.registers.NuclearScienceItems;
import nuclearscience.registers.NuclearScienceMenuTypes;

public class ContainerMoltenSaltSupplier extends GenericContainerBlockEntity<TileMoltenSaltSupplier> {

	public ContainerMoltenSaltSupplier(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainer(2), new SimpleContainerData(3));
	}

	public ContainerMoltenSaltSupplier(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(NuclearScienceMenuTypes.CONTAINER_MOLTENSALTSUPPLIER.get(), id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		addSlot(new SlotRestricted(SlotType.NORMAL, NuclearIconTypes.PELLET_DARK, inv, nextIndex(), 30, 35).setRestriction(NuclearScienceItems.ITEM_LIFHT4PUF3.get()).setIOColor(new Color(0, 240, 255, 255)));
		addSlot(new SlotRestricted(inv, nextIndex(), 130, 35).setIOColor(new Color(255, 0, 0, 255)));
	}
}
