package nuclearscience.common.inventory.container;

import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotRestricted;
import electrodynamics.prefab.screen.component.types.ScreenComponentSlot.SlotType;
import electrodynamics.prefab.utilities.math.Color;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import nuclearscience.common.tile.msreactor.TileMoltenSaltSupplier;
import nuclearscience.prefab.screen.component.NuclearIconTypes;
import nuclearscience.registers.NuclearScienceItems;
import nuclearscience.registers.NuclearScienceMenuTypes;

public class ContainerMoltenSaltSupplier extends GenericContainerBlockEntity<TileMoltenSaltSupplier> {

	public ContainerMoltenSaltSupplier(int id, PlayerInventory playerinv) {
		this(id, playerinv, new Inventory(2), new IntArray(3));
	}

	public ContainerMoltenSaltSupplier(int id, PlayerInventory playerinv, IInventory inventory, IIntArray inventorydata) {
		super(NuclearScienceMenuTypes.CONTAINER_MOLTENSALTSUPPLIER.get(), id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(IInventory inv, PlayerInventory playerinv) {
		addSlot(new SlotRestricted(SlotType.NORMAL, NuclearIconTypes.PELLET_DARK, inv, nextIndex(), 30, 35).setRestriction(NuclearScienceItems.ITEM_LIFHT4PUF3.get()).setIOColor(new Color(0, 240, 255, 255)));
		addSlot(new SlotRestricted(inv, nextIndex(), 130, 35).setIOColor(new Color(255, 0, 0, 255)));
	}
}