package nuclearscience.common.inventory.container;

import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.inventory.container.slot.item.SlotGeneric;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotFluid;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotRestricted;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotUpgrade;
import electrodynamics.prefab.utilities.math.Color;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import nuclearscience.common.tile.TileChemicalExtractor;
import nuclearscience.registers.NuclearScienceMenuTypes;

public class ContainerChemicalExtractor extends GenericContainerBlockEntity<TileChemicalExtractor> {

	public static final SubtypeItemUpgrade[] VALID_UPGRADES = new SubtypeItemUpgrade[] { SubtypeItemUpgrade.basicspeed, SubtypeItemUpgrade.advancedspeed, SubtypeItemUpgrade.iteminput, SubtypeItemUpgrade.itemoutput, SubtypeItemUpgrade.experience };

	public ContainerChemicalExtractor(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainer(6), new SimpleContainerData(3));
	}

	public ContainerChemicalExtractor(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(NuclearScienceMenuTypes.CONTAINER_CHEMICALEXTRACTOR.get(), id, playerinv, inventory, inventorydata);
	}

	public ContainerChemicalExtractor(MenuType<?> type, int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(type, id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		addSlot(new SlotGeneric(inv, nextIndex(), 74, 31).setIOColor(new Color(0, 240, 255, 255)));
		addSlot(new SlotRestricted(inv, nextIndex(), 128, 31).setIOColor(new Color(255, 0, 0, 255)));
		addSlot(new SlotFluid(inv, nextIndex(), 74, 51));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 14, VALID_UPGRADES));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 34, VALID_UPGRADES));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 54, VALID_UPGRADES));
	}
}
