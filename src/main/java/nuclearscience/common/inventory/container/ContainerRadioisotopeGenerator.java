package nuclearscience.common.inventory.container;

import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotRestricted;
import electrodynamics.prefab.utilities.math.Color;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import nuclearscience.api.radiation.RadiationRegister;
import nuclearscience.common.tile.TileRadioisotopeGenerator;
import nuclearscience.registers.NuclearScienceMenuTypes;

public class ContainerRadioisotopeGenerator extends GenericContainerBlockEntity<TileRadioisotopeGenerator> {

	public ContainerRadioisotopeGenerator(int id, PlayerInventory playerinv) {
		this(id, playerinv, new Inventory(1), new IntArray(3));
	}

	public ContainerRadioisotopeGenerator(int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
		super(NuclearScienceMenuTypes.CONTAINER_RADIOISOTOPEGENERATOR.get(), id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(IInventory inv, PlayerInventory playerinv) {
		addSlot(new SlotRestricted(inv, nextIndex(), 25, 42) {
			@Override
			public boolean mayPlace(ItemStack stack) {
				return !RadiationRegister.get(stack.getItem()).isNull();
			}
		}.setIOColor(new Color(0, 240, 255, 255)));
	}
}