package nuclearscience.common.tile;

import electrodynamics.api.tile.electric.CapabilityElectrodynamic;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.tile.generic.GenericTileTicking;
import electrodynamics.common.tile.generic.component.ComponentType;
import electrodynamics.common.tile.generic.component.type.ComponentContainerProvider;
import electrodynamics.common.tile.generic.component.type.ComponentDirection;
import electrodynamics.common.tile.generic.component.type.ComponentElectrodynamic;
import electrodynamics.common.tile.generic.component.type.ComponentFluidHandler;
import electrodynamics.common.tile.generic.component.type.ComponentInventory;
import electrodynamics.common.tile.generic.component.type.ComponentPacketHandler;
import electrodynamics.common.tile.generic.component.type.ComponentProcessor;
import electrodynamics.common.tile.generic.component.type.ComponentTickable;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.inventory.container.ContainerChemicalExtractor;
import nuclearscience.common.settings.Constants;

public class TileChemicalExtractor extends GenericTileTicking {
    public static final int TANKCAPACITY = 5000;
    public static final int REQUIRED_WATER_CAP = 4800;

    public TileChemicalExtractor() {
	super(DeferredRegisters.TILE_CHEMICALEXTRACTOR.get());
	addComponent(new ComponentTickable());
	addComponent(new ComponentDirection());
	addComponent(new ComponentPacketHandler());
	addComponent(new ComponentElectrodynamic(this).enableUniversalInput()
		.setVoltage(CapabilityElectrodynamic.DEFAULT_VOLTAGE * 2));
	addComponent(new ComponentFluidHandler(this).addFluidTank(Fluids.WATER, TANKCAPACITY));
	addComponent(new ComponentInventory().setInventorySize(6).addSlotOnFace(Direction.UP, 0)
		.addSlotOnFace(Direction.DOWN, 1).addSlotOnFace(Direction.SOUTH, 2).addSlotOnFace(Direction.NORTH, 2)
		.addSlotOnFace(Direction.EAST, 2).addSlotOnFace(Direction.WEST, 2));
	addComponent(new ComponentProcessor(this).setJoulesPerTick(Constants.CHEMICALEXTRACTOR_USAGE_PER_TICK)
		.setRequiredTicks(Constants.CHEMICALEXTRACTOR_REQUIRED_TICKS).setCanProcess(this::canProcess)
		.addUpgradeSlots(3, 4, 5));
	addComponent(new ComponentContainerProvider("container.chemicalboiler")
		.setCreateMenuFunction((id, player) -> new ContainerChemicalExtractor(id, player,
			getComponent(ComponentType.Inventory), getCoordsArray())));
    }

    protected boolean canProcess(ComponentProcessor processor) {
	ComponentInventory inv = getComponent(ComponentType.Inventory);
	ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
	ComponentFluidHandler tank = getComponent(ComponentType.FluidHandler);
	ItemStack bucketStack = inv.getStackInSlot(2);
	if (!bucketStack.isEmpty() && bucketStack.getCount() > 0 && bucketStack.getItem() == Items.WATER_BUCKET
		&& tank.getStackFromFluid(Fluids.WATER).getAmount() <= TANKCAPACITY - 1000) {
	    inv.setInventorySlotContents(2, new ItemStack(Items.BUCKET));
	    tank.getStackFromFluid(Fluids.WATER).setAmount(Math.min(tank.getStackFromFluid(Fluids.WATER).getAmount() + 1000, TANKCAPACITY));
	}
	if (this.<ComponentTickable>getComponent(ComponentType.Tickable).getTicks() % 10 == 0) {
	    this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
	}
	int requiredWater = getRequiredWater(processor);
	return electro.getJoulesStored() >= processor.getJoulesPerTick() && !inv.getStackInSlot(0).isEmpty()
		&& inv.getStackInSlot(0).getCount() > 0 && tank.getStackFromFluid(Fluids.WATER).getAmount() >= requiredWater
		&& requiredWater > 0;
    }

    protected int getRequiredWater(ComponentProcessor processor) {
	ItemStack input = processor.getInput();
	ItemStack output = processor.getOutput();
	Item item = input.getItem();
	int requiredWater = -1;
	if (output.getCount() < output.getMaxStackSize() || output.isEmpty()) {
	    if (item == DeferredRegisters.ITEM_CELLEMPTY.get()) {
		if (output.getItem() == DeferredRegisters.ITEM_CELLHEAVYWATER.get() || output.isEmpty()) {
		    requiredWater = REQUIRED_WATER_CAP;
		}
	    } else if (item == DeferredRegisters.ITEM_CELLHEAVYWATER.get()) {
		if (output.getItem() == DeferredRegisters.ITEM_CELLDEUTERIUM.get() || output.isEmpty()) {
		    requiredWater = REQUIRED_WATER_CAP;
		}
	    } else if (item == electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeOre.uraninite)
		    && (output.getItem() == DeferredRegisters.ITEM_YELLOWCAKE.get() || output.isEmpty())) {
		requiredWater = REQUIRED_WATER_CAP / 3;
	    }
	}
	return requiredWater;
    }

    protected void process(ComponentProcessor processor) {
	ComponentInventory inv = getComponent(ComponentType.Inventory);
	int requiredWater = getRequiredWater(processor);
	ItemStack stack = processor.getInput();
	Item item = stack.getItem();
	ItemStack output = processor.getOutput();
	if (item == DeferredRegisters.ITEM_CELLEMPTY.get()) {
	    if (output.isEmpty()) {
		inv.setInventorySlotContents(1, new ItemStack(DeferredRegisters.ITEM_CELLHEAVYWATER.get()));
	    } else {
		output.setCount(output.getCount() + 1);
	    }
	} else if (item == DeferredRegisters.ITEM_CELLHEAVYWATER.get()) {
	    if (output.isEmpty()) {
		inv.setInventorySlotContents(1, new ItemStack(DeferredRegisters.ITEM_CELLDEUTERIUM.get()));
	    } else {
		output.setCount(output.getCount() + 1);
	    }
	} else if (item == electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeOre.uraninite)) {
	    if (output.isEmpty()) {
		inv.setInventorySlotContents(1, new ItemStack(DeferredRegisters.ITEM_YELLOWCAKE.get()));
	    } else {
		output.setCount(output.getCount() + 1);
	    }
	}
	stack.setCount(stack.getCount() - 1);
	ComponentFluidHandler tank = getComponent(ComponentType.FluidHandler);
	tank.getStackFromFluid(Fluids.WATER).shrink(requiredWater);
    }
}
