package nuclearscience.common.tile;

import electrodynamics.api.tile.processing.IO2OProcessor;
import electrodynamics.common.tile.generic.GenericTileProcessor;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.inventory.container.ContainerGasCentrifuge;
import nuclearscience.common.settings.Constants;

public class TileGasCentrifuge extends GenericTileProcessor implements IO2OProcessor, IFluidHandler {
    public static final int TANKCAPACITY = 5000;
    public static final float REQUIRED = 2500;
    public static final int[] SLOTS_UP = new int[] {};
    public static final int[] SLOTS_DOWN = new int[] { 0, 1 };

    public FluidStack tankU6F = new FluidStack(DeferredRegisters.fluidUraniumHexafluoride, 0);
    public int stored235 = 0;
    public int stored238 = 0;

    public TileGasCentrifuge() {
	super(DeferredRegisters.TILE_GASCENTRIFUGE.get());
	addUpgradeSlots(2, 3, 4);
    }

    @Override
    public double getJoulesPerTick() {
	return Constants.GASCENTRIFUGE_USAGE_PER_TICK * currentSpeedMultiplier;
    }

    @Override
    public double getVoltage() {
	return DEFAULT_BASIC_MACHINE_VOLTAGE * 2;
    }

    @Override
    public boolean canProcess() {
	boolean val = getJoulesStored() >= getJoulesPerTick() && tankU6F.getAmount() >= REQUIRED / 60.0
		&& getStackInSlot(0).getCount() < getStackInSlot(0).getMaxStackSize()
		&& getStackInSlot(1).getCount() < getStackInSlot(1).getMaxStackSize();
	if (!val && spinSpeed > 0) {
	    spinSpeed = 0;
	    sendUpdatePacket();
	}
	return val;
    }

    @Override
    public void process() {
	spinSpeed = (int) currentSpeedMultiplier;
	sendUpdatePacket();
	int processed = (int) (REQUIRED / 60.0);
	tankU6F.shrink(processed);
	stored235 += processed * 0.172;
	stored238 += processed * (1 - 0.172);
	if (stored235 > REQUIRED) {
	    ItemStack stack = getStackInSlot(0);
	    if (!stack.isEmpty()) {
		stack.setCount(stack.getCount() + 1);
	    } else {
		setInventorySlotContents(0, new ItemStack(DeferredRegisters.ITEM_URANIUM235.get()));
	    }
	    stored235 -= 2500;
	}
	if (stored238 > REQUIRED) {
	    ItemStack stack = getStackInSlot(1);
	    if (!stack.isEmpty()) {
		stack.setCount(stack.getCount() + 1);
	    } else {
		setInventorySlotContents(1, new ItemStack(DeferredRegisters.ITEM_URANIUM238.get()));
	    }
	    stored238 -= 2500;
	}
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
	CompoundNBT fluid = new CompoundNBT();
	tankU6F.writeToNBT(fluid);
	compound.put("tankU6F", fluid);
	compound.putFloat("stored235", stored235);
	compound.putFloat("stored238", stored238);
	return super.write(compound);
    }

    @Override
    public void read(BlockState state, CompoundNBT compound) {
	super.read(state, compound);
	tankU6F = FluidStack.loadFluidStackFromNBT(compound.getCompound("tankU6F"));
	stored235 = compound.getInt("stored235");
	stored238 = compound.getInt("stored238");
    }

    @Override
    public int getRequiredTicks() {
	return Constants.GASCENTRIFUGE_REQUIRED_TICKS_PER_PROCESSING;
    }

    @Override
    public int getSizeInventory() {
	return 5;
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
	return side == Direction.UP ? SLOTS_UP : side == Direction.DOWN ? SLOTS_DOWN : SLOTS_EMPTY;
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
	return new ContainerGasCentrifuge(id, player, this, inventorydata);
    }

    @Override
    public boolean compareCapabilityDirectionElectricity(Direction dir) {
	return dir == Direction.DOWN;
    }

    @Override
    public ITextComponent getDisplayName() {
	return new TranslationTextComponent("container.gascentrifuge");
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
		return (int) (stored235 / (float) TANKCAPACITY * 50);
	    case 6:
		return (int) (stored238 / (float) TANKCAPACITY * 50);
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
	    return 7;
	}
    };
    public int spinSpeed;

    @Override
    public CompoundNBT createUpdateTag() {
	CompoundNBT tag = super.createUpdateTag();
	tag.putInt("spinSpeed", spinSpeed);
	return tag;
    }

    @Override
    public void handleUpdatePacket(CompoundNBT nbt) {
	spinSpeed = nbt.getInt("spinSpeed");
    }

    @Override
    public ItemStack getOutput() {
	return ItemStack.EMPTY;
    }

    @Override
    public void setOutput(ItemStack stack) {
    }

    @Override
    public int getTanks() {
	return 1;
    }

    @Override
    public FluidStack getFluidInTank(int tank) {
	return tankU6F;
    }

    @Override
    public int getTankCapacity(int tank) {
	return TANKCAPACITY;
    }

    @Override
    public boolean isFluidValid(int tank, FluidStack stack) {
	return stack.getFluid() == DeferredRegisters.fluidUraniumHexafluoride;
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {

	int amount = Math.min(TANKCAPACITY - tankU6F.getAmount(), resource.getAmount());
	if (action == FluidAction.EXECUTE) {
	    tankU6F.grow(amount);
	}
	return amount;
    }

    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
	return drain(resource.getAmount(), action);
    }

    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
	return new FluidStack(tankU6F.getFluid(), Math.min(tankU6F.getAmount(), maxDrain));
    }
}
