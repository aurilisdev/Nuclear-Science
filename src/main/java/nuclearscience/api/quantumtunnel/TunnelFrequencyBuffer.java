package nuclearscience.api.quantumtunnel;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import voltaic.api.gas.GasAction;
import voltaic.api.gas.GasStack;
import voltaic.prefab.utilities.object.TransferPack;

public class TunnelFrequencyBuffer {

    public static final Codec<TunnelFrequencyBuffer> CODEC = RecordCodecBuilder.create(instance -> instance.group(
	    //
	    TransferPack.CODEC.fieldOf("energy").forGetter(TunnelFrequencyBuffer::getBufferedEnergy),
	    //
	    FluidStack.OPTIONAL_CODEC.fieldOf("fluid").forGetter(TunnelFrequencyBuffer::getBufferedFluid),
	    //
	    GasStack.CODEC.fieldOf("gas").forGetter(TunnelFrequencyBuffer::getBufferedGas),
	    //
	    ItemStack.OPTIONAL_CODEC.fieldOf("item").forGetter(TunnelFrequencyBuffer::getBufferedItem)
    //
    ).apply(instance, TunnelFrequencyBuffer::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, TunnelFrequencyBuffer> STREAM_CODEC = StreamCodec
	    .composite(
		    //
		    TransferPack.STREAM_CODEC, TunnelFrequencyBuffer::getBufferedEnergy,
		    //
		    FluidStack.OPTIONAL_STREAM_CODEC, TunnelFrequencyBuffer::getBufferedFluid,
		    //
		    GasStack.STREAM_CODEC, TunnelFrequencyBuffer::getBufferedGas,
		    //
		    ItemStack.OPTIONAL_STREAM_CODEC, TunnelFrequencyBuffer::getBufferedItem,
		    //
		    TunnelFrequencyBuffer::new

	    );

    public static final TunnelFrequencyBuffer EMPTY = new TunnelFrequencyBuffer(TransferPack.EMPTY, FluidStack.EMPTY,
	    GasStack.EMPTY, ItemStack.EMPTY);

    public static final double MAX_JOULES_CAP = Double.MAX_VALUE;
    public static final int MAX_FLUID_CAP = Integer.MAX_VALUE;
    public static final int MAX_GAS_CAP = Integer.MAX_VALUE;
    public static final int MAX_ITEM_STACK_SIZE = 1000;

    private TransferPack energyBuffer = TransferPack.EMPTY;
    private FluidStack fluidBuffer = FluidStack.EMPTY;
    private GasStack gasBuffer = GasStack.EMPTY;
    private ItemStack itemBuffer = ItemStack.EMPTY;

    private TunnelFrequencyBuffer(TransferPack energy, FluidStack fluid, GasStack gas, ItemStack item) {
	energyBuffer = energy;
	fluidBuffer = fluid;
	gasBuffer = gas;
	itemBuffer = item;
    }

    public TunnelFrequencyBuffer() {

    }

    public TransferPack getBufferedEnergy() {
	return energyBuffer;
    }

    public FluidStack getBufferedFluid() {
	return fluidBuffer;
    }

    public GasStack getBufferedGas() {
	return gasBuffer;
    }

    public ItemStack getBufferedItem() {
	return itemBuffer;
    }

    public TransferPack addEnergy(boolean simulate, TransferPack addition) {

	if (addition.getJoules() <= 0) {
	    return TransferPack.EMPTY;
	}

	if (energyBuffer.getVoltage() <= 0 && energyBuffer.getJoules() <= 0) {

	    TransferPack check = addition;

	    if (check.getJoules() - MAX_JOULES_CAP > 0) {
		check = TransferPack.joulesVoltage(MAX_JOULES_CAP, addition.getVoltage());
	    }

	    if (!simulate) {

		energyBuffer = TransferPack.joulesVoltage(check.getJoules(), check.getVoltage());
	    }
	    return check;
	}

	if (energyBuffer.getVoltage() != 0 && energyBuffer.getVoltage() != addition.getVoltage()) {
	    return TransferPack.EMPTY;
	}

	double accepted = Math.min(MAX_JOULES_CAP - energyBuffer.getJoules(), addition.getJoules());

	if (!simulate) {
	    energyBuffer = TransferPack.joulesVoltage(energyBuffer.getJoules() + accepted, energyBuffer.getVoltage());
	}

	return TransferPack.joulesVoltage(accepted, addition.getVoltage());

    }

    public TransferPack extractEnergy(boolean simulate, TransferPack extract) {

	if (energyBuffer.getJoules() <= 0 || energyBuffer.getVoltage() <= 0 || extract.getVoltage() <= 0
		|| extract.getJoules() <= 0 || energyBuffer.getVoltage() != extract.getVoltage()) {
	    return TransferPack.EMPTY;
	}

	double taken = Math.min(extract.getJoules(), energyBuffer.getJoules());

	if (!simulate) {
	    if (taken >= energyBuffer.getJoules()) {
		energyBuffer = TransferPack.EMPTY;
	    } else {
		energyBuffer = TransferPack.joulesVoltage(energyBuffer.getJoules() - taken, energyBuffer.getVoltage());
	    }
	}

	return TransferPack.joulesVoltage(taken, extract.getVoltage());

    }

    public FluidStack receiveFluid(IFluidHandler.FluidAction action, FluidStack addition) {

	if (addition.isEmpty()) {
	    return FluidStack.EMPTY;
	}

	if (fluidBuffer.isEmpty()) {

	    FluidStack check = addition;

	    if (check.getAmount() - MAX_FLUID_CAP > 0) {
		check = addition.copyWithAmount(MAX_FLUID_CAP);
	    }

	    if (action == IFluidHandler.FluidAction.EXECUTE) {

		fluidBuffer = check.copy();
	    }
	    return check;
	}

	if (fluidBuffer.getAmount() != 0 && !FluidStack.isSameFluidSameComponents(fluidBuffer, addition)) {
	    return FluidStack.EMPTY;
	}

	int accepted = Math.min(MAX_FLUID_CAP - fluidBuffer.getAmount(), addition.getAmount());

	if (action == IFluidHandler.FluidAction.EXECUTE) {
	    fluidBuffer.grow(accepted);
	}

	return addition.copyWithAmount(accepted);
    }

    public FluidStack extractFluid(IFluidHandler.FluidAction action, FluidStack extract) {

	if (fluidBuffer.isEmpty() || extract.isEmpty() || !FluidStack.isSameFluidSameComponents(fluidBuffer, extract)) {
	    return FluidStack.EMPTY;
	}

	int taken = Math.min(extract.getAmount(), fluidBuffer.getAmount());
	FluidStack returned = fluidBuffer.copyWithAmount(taken);
	if (action == IFluidHandler.FluidAction.EXECUTE) {
	    if (taken >= fluidBuffer.getAmount()) {
		fluidBuffer = FluidStack.EMPTY;
	    } else {
		fluidBuffer.shrink(taken);
	    }
	}

	return returned;
    }

    public GasStack receiveGas(GasAction action, GasStack addition) {

	if (addition.isEmpty()) {
	    return GasStack.EMPTY;
	}

	if (gasBuffer.isEmpty()) {

	    GasStack check = addition;

	    if (check.getAmount() - MAX_GAS_CAP > 0) {
		check = new GasStack(addition.getGas(), MAX_GAS_CAP, addition.getTemperature(), addition.getPressure());
	    }

	    if (action == GasAction.EXECUTE) {

		gasBuffer = check.copy();
	    }
	    return check;
	}

	if ((gasBuffer.getAmount() != 0 && !gasBuffer.getGas().equals(addition.getGas()))
		|| gasBuffer.getTemperature() != addition.getTemperature()
		|| gasBuffer.getPressure() != addition.getPressure()) {
	    return GasStack.EMPTY;
	}

	int accepted = Math.min(MAX_GAS_CAP - gasBuffer.getAmount(), addition.getAmount());

	if (action == GasAction.EXECUTE) {
	    gasBuffer = new GasStack(gasBuffer.getGas(), gasBuffer.getAmount() + accepted, gasBuffer.getTemperature(),
		    gasBuffer.getPressure());
	}

	return new GasStack(addition.getGas(), accepted, addition.getTemperature(), addition.getPressure());

    }

    public GasStack extractGas(GasAction action, GasStack extract) {

	if (gasBuffer.isEmpty() || extract.isEmpty() || !gasBuffer.getGas().equals(extract.getGas())
		|| gasBuffer.getTemperature() != extract.getTemperature()
		|| gasBuffer.getPressure() != extract.getPressure()) {
	    return GasStack.EMPTY;
	}

	int taken = Math.min(extract.getAmount(), gasBuffer.getAmount());

	if (action == GasAction.EXECUTE) {
	    if (taken >= gasBuffer.getAmount()) {
		gasBuffer = GasStack.EMPTY;
	    } else {
		gasBuffer = new GasStack(gasBuffer.getGas(), gasBuffer.getAmount() - taken, gasBuffer.getTemperature(),
			gasBuffer.getPressure());
	    }
	}

	return new GasStack(extract.getGas(), taken, extract.getTemperature(), extract.getPressure());

    }

    public ItemStack receiveItem(boolean simulate, ItemStack addition) {

	if (addition.isEmpty()) {
	    return ItemStack.EMPTY;
	}

	/*
	 * insertItem must return the unaccepted remainder.
	 */
	if (!itemBuffer.isEmpty() && !ItemStack.isSameItemSameComponents(itemBuffer, addition)) {
	    return addition.copy();
	}

	int currentAmount = itemBuffer.isEmpty() ? 0 : itemBuffer.getCount();
	int accepted = Math.min(MAX_ITEM_STACK_SIZE - currentAmount, addition.getCount());

	if (accepted <= 0) {
	    return addition.copy();
	}

	if (!simulate) {
	    if (itemBuffer.isEmpty()) {
		itemBuffer = addition.copy();
		itemBuffer.setCount(accepted);
	    } else {
		itemBuffer.grow(accepted);
	    }
	}

	int remaining = addition.getCount() - accepted;

	if (remaining <= 0) {
	    return ItemStack.EMPTY;
	}

	ItemStack remainder = addition.copy();
	remainder.setCount(remaining);
	return remainder;
    }

    public ItemStack extractItem(boolean simulate, ItemStack extract) {

	if (itemBuffer.isEmpty() || extract.isEmpty() || !ItemStack.isSameItemSameComponents(itemBuffer, extract)) {
	    return ItemStack.EMPTY;
	}

	int taken = Math.min(extract.getCount(), itemBuffer.getCount());

	ItemStack returned = itemBuffer.copy();
	returned.setCount(taken);

	if (!simulate) {
	    itemBuffer.shrink(taken);

	    if (itemBuffer.isEmpty()) {
		itemBuffer = ItemStack.EMPTY;
	    }
	}

	return returned;
    }

}
