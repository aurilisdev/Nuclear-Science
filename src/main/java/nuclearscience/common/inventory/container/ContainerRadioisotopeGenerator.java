package nuclearscience.common.inventory.container;

import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotRestricted;
import electrodynamics.prefab.utilities.math.Color;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import nuclearscience.api.radiation.RadiationRegister;
import nuclearscience.common.tile.TileRadioisotopeGenerator;
import nuclearscience.registers.NuclearScienceMenuTypes;

public class ContainerRadioisotopeGenerator extends GenericContainerBlockEntity<TileRadioisotopeGenerator> {

	public ContainerRadioisotopeGenerator(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainer(1), new SimpleContainerData(3));
	}

	public ContainerRadioisotopeGenerator(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(NuclearScienceMenuTypes.CONTAINER_RADIOISOTOPEGENERATOR.get(), id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		addSlot(new SlotRestricted(inv, nextIndex(), 25, 42) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return !RadiationRegister.get(stack.getItem()).isNull();
			}
		}.setIOColor(new Color(0, 240, 255, 255)));
	}
}
