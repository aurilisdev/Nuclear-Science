package nuclearscience.common.inventory.container;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import nuclearscience.common.tile.TileRadioisotopeGenerator;
import nuclearscience.registers.NuclearScienceMenuTypes;
import voltaic.common.reloadlistener.RadioactiveItemRegister;
import voltaic.prefab.inventory.container.slot.item.type.SlotRestricted;
import voltaic.prefab.inventory.container.types.GenericContainerBlockEntity;
import voltaic.prefab.utilities.math.Color;

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
				return RadioactiveItemRegister.getValue(stack.getItem()).amount() > 0;
			}
		}.setIOColor(new Color(0, 240, 255, 255)));
	}
}
