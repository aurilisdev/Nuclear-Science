package nuclearscience.common.tile;

import electrodynamics.api.tile.processing.IO2OProcessor;
import electrodynamics.common.block.subtype.SubtypeOre;
import electrodynamics.common.tile.generic.GenericTileProcessor;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.inventory.container.ContainerChemicalBoiler;

public class TileChemicalBoiler extends GenericTileProcessor implements IO2OProcessor, IFluidHandler {
	public static final double REQUIRED_JOULES_PER_TICK = 750;
	public static final int REQUIRED_TICKS = 800;
	public static final int TANKCAPACITY = 5000;
	public static final int REQUIRED_WATER_CAP = 2400;
	public static final int[] SLOTS_UP = new int[] { 0 };
	public static final int[] SLOTS_SIDE = new int[] { 1 };

	public FluidStack tankWater = new FluidStack(Fluids.WATER, 0);
	public FluidStack tankU6F = new FluidStack(DeferredRegisters.fluidUraniumHexafluoride, 0);

	public TileChemicalBoiler() {
		super(DeferredRegisters.TILE_CHEMICALBOILER.get());
		addUpgradeSlots(2, 3, 4);
	}

	@Override
	public double getJoulesPerTick() {
		return REQUIRED_JOULES_PER_TICK * currentSpeedMultiplier;
	}

	@Override
	public double getVoltage() {
		return DEFAULT_BASIC_MACHINE_VOLTAGE * 2;
	}

	@Override
	public boolean canProcess() {
		BlockPos face = getPos().offset(getFacing().getOpposite());
		TileEntity faceTile = world.getTileEntity(face);
		if (faceTile instanceof IFluidHandler) {
			IFluidHandler handler = (IFluidHandler) faceTile;
			if (handler.isFluidValid(0, tankU6F)) {
				tankU6F.shrink(handler.fill(tankU6F, FluidAction.EXECUTE));
			}
		}
		ItemStack bucketStack = getStackInSlot(1);
		if (!bucketStack.isEmpty() && bucketStack.getCount() > 0 && bucketStack.getItem() == Items.WATER_BUCKET && tankWater.getAmount() <= TANKCAPACITY - 1000) {
			setInventorySlotContents(1, new ItemStack(Items.BUCKET));
			tankWater.setAmount(Math.min(tankWater.getAmount() + 1000, TANKCAPACITY));
		}
		int requiredWater = getRequiredWater();
		if (requiredWater <= 0) {
			return false;
		}
		int u6f = (int) (1500 + (2400 - requiredWater) / 2400.0f * 1500);
		return getJoulesStored() >= getJoulesPerTick() && !getStackInSlot(0).isEmpty() && getStackInSlot(0).getCount() > 0 && tankWater.getAmount() >= requiredWater && TANKCAPACITY >= tankU6F.getAmount() + u6f;
	}

	private int getRequiredWater() {
		ItemStack stack = getStackInSlot(0);
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

	@Override
	public void process() {
		ItemStack stack = getStackInSlot(0);
		int requiredWater = getRequiredWater();
		int createdU6F = (int) (1500 + (2400 - requiredWater) / 2400.0f * 1500);
		stack.setCount(stack.getCount() - 1);
		tankWater.shrink(requiredWater);
		tankU6F.grow(createdU6F);
	}

	@Override
	public int getRequiredTicks() {
		return REQUIRED_TICKS;
	}

	@Override
	public int getSizeInventory() {
		return 5;
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		return side == Direction.UP ? SLOTS_UP : side == Direction.DOWN ? SLOTS_EMPTY : SLOTS_SIDE;
	}

	@Override
	protected Container createMenu(int id, PlayerInventory player) {
		return new ContainerChemicalBoiler(id, player, this, inventorydata);
	}

	@Override
	public boolean canConnectElectrically(Direction direction) {
		return direction == Direction.DOWN;
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("container.chemicalboiler");
	}

	@Override
	public ItemStack getInput() {
		return ItemStack.EMPTY;
	}

	protected final IIntArray inventorydata = new IIntArray() {
		@Override
		public int get(int index) {
			switch (index) {
			case 0:
				return (int) currentOperatingTick;
			case 1:
				return (int) getVoltage();
			case 2:
				return (int) Math.ceil(getJoulesPerTick());
			case 3:
				return getRequiredTicks() == 0 ? 1 : getRequiredTicks();
			case 4:
				return (int) (tankU6F.getAmount() / (float) TANKCAPACITY * 100);
			case 5:
				return (int) (tankWater.getAmount() / (float) TANKCAPACITY * 100);
			default:
				return 0;
			}
		}

		@Override
		public void set(int index, int value) {
			switch (index) {
			case 0:
				currentOperatingTick = value;
				break;
			default:
				break;
			}

		}

		@Override
		public int size() {
			return 6;
		}
	};

	@Override
	public void setOutput(ItemStack stack) {
	}

	@Override
	public ItemStack getOutput() {
		return ItemStack.EMPTY;
	}

	@Override
	public FluidStack getFluidInTank(int tank) {
		return tank == 0 ? tankWater : tank == 1 ? tankU6F : FluidStack.EMPTY;
	}

	@Override
	public int getTanks() {
		return 2;
	}

	@Override
	public int getTankCapacity(int tank) {
		return TANKCAPACITY;
	}

	@Override
	public boolean isFluidValid(int tank, FluidStack stack) {
		return tank == 0 ? stack.getFluid() == Fluids.WATER : tank == 1 ? stack.getFluid() == DeferredRegisters.fluidUraniumHexafluoride : false;
	}

	@Override
	public int fill(FluidStack resource, FluidAction action) {
		FluidStack tank = resource.getFluid() == DeferredRegisters.fluidUraniumHexafluoride ? tankU6F : resource.getFluid() == Fluids.WATER ? tankWater : FluidStack.EMPTY;
		if (tank == FluidStack.EMPTY) {
			return 0;
		}
		int amount = Math.min(TANKCAPACITY - tank.getAmount(), resource.getAmount());
		if (action == FluidAction.EXECUTE) {
			tank.grow(amount);
		}
		return amount;
	}

	@Override
	public FluidStack drain(FluidStack resource, FluidAction action) {
		return drain(resource.getAmount(), action);
	}

	@Override
	public FluidStack drain(int maxDrain, FluidAction action) {
		int amount = Math.min(tankU6F.getAmount(), maxDrain);
		if (action == FluidAction.EXECUTE) {
			tankU6F.shrink(amount);
		}
		return amount == 0 ? FluidStack.EMPTY : new FluidStack(tankU6F.getFluid(), amount);
	}
}
