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
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.inventory.container.ContainerChemicalExtractor;

public class TileChemicalExtractor extends GenericTileProcessor implements IO2OProcessor, IFluidHandler {
	public static final double REQUIRED_JOULES_PER_TICK = 750;
	public static final int REQUIRED_TICKS = 400;
	public static final int TANKCAPACITY = 5000;
	public static final int REQUIRED_WATER_CAP = 4800;
	public static final int[] SLOTS_UP = new int[] { 0 };
	public static final int[] SLOTS_SIDE = new int[] { 2 };
	public static final int[] SLOTS_DOWN = new int[] { 1 };

	public FluidStack tankWater = new FluidStack(Fluids.WATER, 0);

	public TileChemicalExtractor() {
		super(DeferredRegisters.TILE_CHEMICALEXTRACTOR.get());
		addUpgradeSlots(3, 4, 5);
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
		ItemStack bucketStack = getStackInSlot(2);
		if (!bucketStack.isEmpty() && bucketStack.getCount() > 0 && bucketStack.getItem() == Items.WATER_BUCKET && tankWater.getAmount() <= TANKCAPACITY - 1000) {
			setInventorySlotContents(2, new ItemStack(Items.BUCKET));
			tankWater.setAmount(Math.min(tankWater.getAmount() + 1000, TANKCAPACITY));
		}
		int requiredWater = getRequiredWater();
		return getJoulesStored() >= getJoulesPerTick() && !getStackInSlot(0).isEmpty() && getStackInSlot(0).getCount() > 0 && tankWater.getAmount() >= requiredWater && requiredWater > 0;
	}

	private int getRequiredWater() {
		ItemStack input = getInput();
		Item item = input.getItem();
		ItemStack output = getOutput();
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
			} else if (item == electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeOre.uraninite)) {
				if (output.getItem() == DeferredRegisters.ITEM_YELLOWCAKE.get() || output.isEmpty()) {
					requiredWater = REQUIRED_WATER_CAP / 3;
				}
			}
		}
		return requiredWater;
	}

	@Override
	public void process() {
		int requiredWater = getRequiredWater();
		ItemStack stack = getInput();
		Item item = stack.getItem();
		ItemStack output = getOutput();
		if (item == DeferredRegisters.ITEM_CELLEMPTY.get()) {
			if (output.isEmpty()) {
				setInventorySlotContents(1, new ItemStack(DeferredRegisters.ITEM_CELLHEAVYWATER.get()));
			} else {
				output.setCount(output.getCount() + 1);
			}
		} else if (item == DeferredRegisters.ITEM_CELLHEAVYWATER.get()) {
			if (output.isEmpty()) {
				setInventorySlotContents(1, new ItemStack(DeferredRegisters.ITEM_CELLDEUTERIUM.get()));
			} else {
				output.setCount(output.getCount() + 1);
			}
		} else if (item == electrodynamics.DeferredRegisters.SUBTYPEITEM_MAPPINGS.get(SubtypeOre.uraninite)) {
			if (output.isEmpty()) {
				setInventorySlotContents(1, new ItemStack(DeferredRegisters.ITEM_YELLOWCAKE.get()));
			} else {
				output.setCount(output.getCount() + 1);
			}
		}
		stack.setCount(stack.getCount() - 1);
		tankWater.shrink(requiredWater);
	}

	@Override
	public int getRequiredTicks() {
		return REQUIRED_TICKS;
	}

	@Override
	public int getSizeInventory() {
		return 6;
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		return side == Direction.UP ? SLOTS_UP : side == Direction.DOWN ? SLOTS_EMPTY : SLOTS_SIDE;
	}

	@Override
	protected Container createMenu(int id, PlayerInventory player) {
		return new ContainerChemicalExtractor(id, player, this, inventorydata);
	}

	@Override
	public boolean canConnectElectrically(Direction direction) {
		return direction == Direction.DOWN;
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("container.chemicalextractor");
	}

	@Override
	public ItemStack getInput() {
		return getStackInSlot(0);
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
			return 5;
		}
	};

	@Override
	public void setOutput(ItemStack stack) {
		setInventorySlotContents(1, stack);
	}

	@Override
	public ItemStack getOutput() {
		return getStackInSlot(1);
	}

	@Override
	public FluidStack getFluidInTank(int tank) {
		return tankWater;
	}

	@Override
	public int getTanks() {
		return 1;
	}

	@Override
	public int getTankCapacity(int tank) {
		return TANKCAPACITY;
	}

	@Override
	public boolean isFluidValid(int tank, FluidStack stack) {
		return stack.getFluid() == Fluids.WATER;
	}

	@Override
	public int fill(FluidStack resource, FluidAction action) {
		int amount = Math.min(TANKCAPACITY - tankWater.getAmount(), resource.getAmount());
		if (action == FluidAction.EXECUTE) {
			tankWater.grow(amount);
		}
		return amount;
	}

	@Override
	public FluidStack drain(FluidStack resource, FluidAction action) {
		return drain(resource.getAmount(), action);
	}

	@Override
	public FluidStack drain(int maxDrain, FluidAction action) {
		int amount = Math.min(tankWater.getAmount(), maxDrain);
		if (action == FluidAction.EXECUTE) {
			tankWater.shrink(amount);
		}
		return amount == 0 ? FluidStack.EMPTY : new FluidStack(tankWater.getFluid(), amount);
	}
}
