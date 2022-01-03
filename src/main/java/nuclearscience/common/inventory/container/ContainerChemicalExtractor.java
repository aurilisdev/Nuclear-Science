package nuclearscience.common.inventory.container;

import electrodynamics.common.item.gear.tools.ItemCanister;
import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.inventory.container.slot.item.SlotGeneric;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotFluid;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotUpgrade;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.BucketItem;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.tile.TileChemicalExtractor;

public class ContainerChemicalExtractor extends GenericContainerBlockEntity<TileChemicalExtractor> {

	public ContainerChemicalExtractor(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainer(6), new SimpleContainerData(3));
	}

	public ContainerChemicalExtractor(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(DeferredRegisters.CONTAINER_CHEMICALEXTRACTOR.get(), id, playerinv, inventory, inventorydata);
	}

	public ContainerChemicalExtractor(MenuType<?> type, int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(type, id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		addSlot(new SlotGeneric(inv, nextIndex(), 74, 31));
		addSlot(new FurnaceResultSlot(playerinv.player, inv, nextIndex(), 128, 31));
		addSlot(new SlotFluid(inv, nextIndex(), 74, 51));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 14, SubtypeItemUpgrade.basicspeed,
				SubtypeItemUpgrade.advancedspeed));
				//electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeItemUpgrade.basicspeed),
				//electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeItemUpgrade.advancedspeed)));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 34, SubtypeItemUpgrade.basicspeed,
				SubtypeItemUpgrade.advancedspeed));
				//electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeItemUpgrade.basicspeed),
				//electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeItemUpgrade.advancedspeed)));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 54, SubtypeItemUpgrade.basicspeed,
				SubtypeItemUpgrade.advancedspeed));
				//electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeItemUpgrade.basicspeed),
				//electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeItemUpgrade.advancedspeed)));
	}
}
