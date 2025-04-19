package nuclearscience.common.inventory.container;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import nuclearscience.common.tile.accelerator.TileParticleInjector;
import nuclearscience.prefab.screen.component.NuclearIconTypes;
import nuclearscience.registers.NuclearScienceItems;
import nuclearscience.registers.NuclearScienceMenuTypes;
import voltaic.prefab.inventory.container.slot.item.SlotGeneric;
import voltaic.prefab.inventory.container.slot.item.type.SlotRestricted;
import voltaic.prefab.inventory.container.types.GenericContainerBlockEntity;
import voltaic.prefab.screen.component.types.ScreenComponentSlot;
import voltaic.prefab.utilities.math.Color;

public class ContainerParticleInjector extends GenericContainerBlockEntity<TileParticleInjector> {

	public ContainerParticleInjector(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainer(3), new SimpleContainerData(3));
	}

	public ContainerParticleInjector(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(NuclearScienceMenuTypes.CONTAINER_PARTICLEINJECTOR.get(), id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		setPlayerInvOffset(10);

		addSlot(new SlotGeneric(inv, nextIndex(), 98, 22).setIOColor(new Color(0, 240, 255, 255)));
		addSlot(new SlotRestricted(ScreenComponentSlot.SlotType.NORMAL, NuclearIconTypes.FUEL_CELL_DARK, inv, nextIndex(), 98, 58).setRestriction(NuclearScienceItems.ITEM_CELLELECTROMAGNETIC.get()).setIOColor(new Color(0, 255, 30, 255)));
		addSlot(new SlotRestricted(ScreenComponentSlot.SlotType.NORMAL, NuclearIconTypes.FUEL_CELL_DARK, inv, nextIndex(), 133, 40).setIOColor(new Color(255, 0, 0, 255)));
	}
}
