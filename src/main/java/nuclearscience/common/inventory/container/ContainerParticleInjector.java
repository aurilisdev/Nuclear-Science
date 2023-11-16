package nuclearscience.common.inventory.container;

import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.inventory.container.slot.item.SlotGeneric;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotRestricted;
import electrodynamics.prefab.screen.component.types.ScreenComponentSlot.SlotType;
import electrodynamics.prefab.utilities.math.Color;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import nuclearscience.common.tile.TileParticleInjector;
import nuclearscience.prefab.screen.component.NuclearIconTypes;
import nuclearscience.registers.NuclearScienceItems;
import nuclearscience.registers.NuclearScienceMenuTypes;

public class ContainerParticleInjector extends GenericContainerBlockEntity<TileParticleInjector> {

	public ContainerParticleInjector(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainer(3), new SimpleContainerData(3));
	}

	public ContainerParticleInjector(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(NuclearScienceMenuTypes.CONTAINER_PARTICLEINJECTOR.get(), id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		playerInvOffset += 10;

		addSlot(new SlotGeneric(inv, nextIndex(), 98, 22).setIOColor(new Color(0, 240, 255, 255)));
		addSlot(new SlotRestricted(SlotType.NORMAL, NuclearIconTypes.FUEL_CELL_DARK, inv, nextIndex(), 98, 58).setRestriction(NuclearScienceItems.ITEM_CELLELECTROMAGNETIC.get()).setIOColor(new Color(0, 255, 30, 255)));
		addSlot(new SlotRestricted(SlotType.NORMAL, NuclearIconTypes.FUEL_CELL_DARK, inv, nextIndex(), 133, 40).setIOColor(new Color(255, 0, 0, 255)));
	}
}
