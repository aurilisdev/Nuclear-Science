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
import electrodynamics.common.tile.generic.component.type.ComponentProcessorType;
import electrodynamics.common.tile.generic.component.type.ComponentTickable;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.inventory.container.ContainerChemicalBoiler;
import nuclearscience.common.settings.Constants;

public class TileChemicalBoiler extends GenericTileTicking {
    public static final int TANKCAPACITY = 5000;
    public static final int REQUIRED_WATER_CAP = 2400;

    public TileChemicalBoiler() {
	super(DeferredRegisters.TILE_CHEMICALBOILER.get());
	addComponent(new ComponentTickable());
	addComponent(new ComponentDirection());
	addComponent(new ComponentPacketHandler());
	addComponent(new ComponentElectrodynamic(this).addInputDirection(Direction.DOWN)
		.setVoltage(CapabilityElectrodynamic.DEFAULT_VOLTAGE * 2));
	addComponent(new ComponentFluidHandler(this).addRelativeInputDirection(Direction.EAST)
		.addFluidTank(Fluids.WATER, TANKCAPACITY)
		.addFluidTank(DeferredRegisters.fluidUraniumHexafluoride, TANKCAPACITY));
	addComponent(new ComponentInventory().setInventorySize(5).addSlotOnFace(Direction.UP, 0)
		.addSlotOnFace(Direction.DOWN, 1));
	addComponent(new ComponentProcessor(this).addUpgradeSlots(2, 3, 4)
		.setJoulesPerTick(Constants.CHEMICALBOILER_USAGE_PER_TICK)
		.setType(ComponentProcessorType.ObjectToObject).setCanProcess(this::canProcess)
		.setProcess(this::process).setRequiredTicks(Constants.CHEMICALBOILER_REQUIRED_TICKS));
	addComponent(new ComponentContainerProvider("container.chemicalboiler")
		.setCreateMenuFunction((id, player) -> new ContainerChemicalBoiler(id, player,
			getComponent(ComponentType.Inventory), getCoordsArray())));

    }

    protected boolean canProcess(ComponentProcessor processor) {
	ComponentDirection direction = getComponent(ComponentType.Direction);
	ComponentInventory inv = getComponent(ComponentType.Inventory);
	ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
	ComponentFluidHandler tank = getComponent(ComponentType.FluidHandler);
	BlockPos face = getPos().offset(direction.getDirection().getOpposite().rotateY());
	TileEntity faceTile = world.getTileEntity(face);
	LazyOptional<IFluidHandler> cap = faceTile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY,
		direction.getDirection().getOpposite().rotateY().getOpposite());
	if (cap.isPresent()) {
	    IFluidHandler handler = cap.resolve().get();
	    if (handler.isFluidValid(0, tank.getStackFromFluid(DeferredRegisters.fluidUraniumHexafluoride))) {
		tank.getStackFromFluid(DeferredRegisters.fluidUraniumHexafluoride).shrink(handler
			.fill(tank.getStackFromFluid(DeferredRegisters.fluidUraniumHexafluoride), FluidAction.EXECUTE));
	    }
	}
	ItemStack bucketStack = inv.getStackInSlot(1);
	if (!bucketStack.isEmpty() && bucketStack.getCount() > 0 && bucketStack.getItem() == Items.WATER_BUCKET
		&& tank.getStackFromFluid(Fluids.WATER).getAmount() <= TANKCAPACITY - 1000) {
	    inv.setInventorySlotContents(1, new ItemStack(Items.BUCKET));
	    tank.getStackFromFluid(Fluids.WATER)
		    .setAmount(Math.min(tank.getStackFromFluid(Fluids.WATER).getAmount() + 1000, TANKCAPACITY));
	}
	int requiredWater = getRequiredWater(inv);
	int u6f = (int) (1500 + (2400 - requiredWater) / 2400.0f * 1500);
	if (requiredWater <= 0
		|| TANKCAPACITY < tank.getStackFromFluid(DeferredRegisters.fluidUraniumHexafluoride).getAmount()
			+ u6f) {
	    return false;
	}
	return electro.getJoulesStored() >= processor.getJoulesPerTick() && !processor.getInput().isEmpty()
		&& processor.getInput().getCount() > 0
		&& tank.getStackFromFluid(Fluids.WATER).getAmount() >= requiredWater;
    }

    protected int getRequiredWater(IInventory inv) {
	ItemStack stack = inv.getStackInSlot(0);
	Item item = stack.getItem();
	int requiredWater = -1;
	if (item == DeferredRegisters.ITEM_YELLOWCAKE.get()) {
	    requiredWater = REQUIRED_WATER_CAP / 3;
	} else if (item == DeferredRegisters.ITEM_URANIUM238.get()) {
	    requiredWater = REQUIRED_WATER_CAP / 3 * 2;
	} else if (item == electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeOre.uraninite)) {
	    requiredWater = REQUIRED_WATER_CAP;
	}
	return requiredWater;
    }

    public void process(ComponentProcessor processor) {
	ComponentFluidHandler handler = getComponent(ComponentType.FluidHandler);
	ItemStack stack = processor.getInput();
	int requiredWater = getRequiredWater(getComponent(ComponentType.Inventory));
	int createdU6F = (int) (1500 + (2400 - requiredWater) / 2400.0f * 1500);
	stack.setCount(stack.getCount() - 1);
	handler.getStackFromFluid(Fluids.WATER).shrink(requiredWater);
	handler.getStackFromFluid(DeferredRegisters.fluidUraniumHexafluoride).grow(createdU6F);
    }
}
