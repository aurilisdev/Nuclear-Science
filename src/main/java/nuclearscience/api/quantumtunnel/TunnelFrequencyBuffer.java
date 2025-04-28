package nuclearscience.api.quantumtunnel;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import voltaic.api.codec.StreamCodec;
import voltaic.api.gas.GasAction;
import voltaic.api.gas.GasStack;
import voltaic.prefab.utilities.object.TransferPack;

public class TunnelFrequencyBuffer {

    public static final Codec<TunnelFrequencyBuffer> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            //
            TransferPack.CODEC.fieldOf("energy").forGetter(TunnelFrequencyBuffer::getBufferedEnergy),
            //
            FluidStack.CODEC.fieldOf("fluid").forGetter(TunnelFrequencyBuffer::getBufferedFluid),
            //
            GasStack.CODEC.fieldOf("gas").forGetter(TunnelFrequencyBuffer::getBufferedGas),
            //
            ItemStack.CODEC.fieldOf("item").forGetter(TunnelFrequencyBuffer::getBufferedItem)
            //
    ).apply(instance, TunnelFrequencyBuffer::new));

    public static final StreamCodec<FriendlyByteBuf, TunnelFrequencyBuffer> STREAM_CODEC = new StreamCodec<FriendlyByteBuf, TunnelFrequencyBuffer>() {
		
		@Override
		public void encode(FriendlyByteBuf buffer, TunnelFrequencyBuffer value) {
			TransferPack.STREAM_CODEC.encode(buffer, value.energyBuffer);
			StreamCodec.FLUID_STACK.encode(buffer, value.fluidBuffer);
			GasStack.STREAM_CODEC.encode(buffer, value.gasBuffer);
			StreamCodec.ITEM_STACK.encode(buffer, value.itemBuffer);
		}
		
		@Override
		public TunnelFrequencyBuffer decode(FriendlyByteBuf buffer) {
			return new TunnelFrequencyBuffer(TransferPack.STREAM_CODEC.decode(buffer), StreamCodec.FLUID_STACK.decode(buffer), GasStack.STREAM_CODEC.decode(buffer), StreamCodec.ITEM_STACK.decode(buffer));
		}
	};

    public static final TunnelFrequencyBuffer EMPTY = new TunnelFrequencyBuffer(TransferPack.EMPTY, FluidStack.EMPTY, GasStack.EMPTY, ItemStack.EMPTY);

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

        if (energyBuffer.getJoules() <= 0 || energyBuffer.getVoltage() <= 0 || extract.getVoltage() <= 0 || extract.getJoules() <= 0 || energyBuffer.getVoltage() != extract.getVoltage()) {
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

    public FluidStack addFluid(FluidAction action, FluidStack addition) {

        if (addition.isEmpty()) {
            return FluidStack.EMPTY;
        }

        if (fluidBuffer.isEmpty()) {

            FluidStack check = addition;

            if (check.getAmount() - MAX_FLUID_CAP > 0) {
                check = new FluidStack(addition.getFluid(), MAX_FLUID_CAP);
            }

            if (action == FluidAction.EXECUTE) {

                fluidBuffer = check.copy();
            }
            return check;
        }

        if (fluidBuffer.getAmount() != 0 && !fluidBuffer.getFluid().isSame(addition.getFluid())) {
            return FluidStack.EMPTY;
        }

        int accepted = Math.min(MAX_FLUID_CAP - fluidBuffer.getAmount(), addition.getAmount());

        if (action == FluidAction.EXECUTE) {
            fluidBuffer = new FluidStack(fluidBuffer.getFluid(), fluidBuffer.getAmount() + accepted);
        }

        return new FluidStack(addition.getFluid(), accepted);

    }

    public FluidStack extractFluid(FluidAction action, FluidStack extract) {

        if (fluidBuffer.isEmpty() || extract.isEmpty() || !fluidBuffer.getFluid().isSame(extract.getFluid())) {
            return FluidStack.EMPTY;
        }

        int taken = Math.min(extract.getAmount(), fluidBuffer.getAmount());

        if (action == FluidAction.EXECUTE) {
            if (taken >= fluidBuffer.getAmount()) {
                fluidBuffer = FluidStack.EMPTY;
            } else {
                fluidBuffer = new FluidStack(fluidBuffer.getFluid(), fluidBuffer.getAmount() - taken);
            }
        }

        return new FluidStack(extract.getFluid(), taken);

    }

    public GasStack addGas(GasAction action, GasStack addition) {

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

        if (gasBuffer.getAmount() != 0 && !gasBuffer.getGas().equals(addition.getGas())) {
            return GasStack.EMPTY;
        }

        if (gasBuffer.getTemperature() != addition.getTemperature() || gasBuffer.getPressure() != addition.getPressure()) {
            return GasStack.EMPTY;
        }

        int accepted = Math.min(MAX_GAS_CAP - gasBuffer.getAmount(), addition.getAmount());

        if (action == GasAction.EXECUTE) {
            gasBuffer = new GasStack(gasBuffer.getGas(), gasBuffer.getAmount() + accepted, gasBuffer.getTemperature(), gasBuffer.getPressure());
        }

        return new GasStack(addition.getGas(), accepted, addition.getTemperature(), addition.getPressure());

    }

    public GasStack extractGas(GasAction action, GasStack extract) {

        if (gasBuffer.isEmpty() || extract.isEmpty() || !gasBuffer.getGas().equals(extract.getGas()) || gasBuffer.getTemperature() != extract.getTemperature() || gasBuffer.getPressure() != extract.getPressure()) {
            return GasStack.EMPTY;
        }

        int taken = Math.min(extract.getAmount(), gasBuffer.getAmount());

        if (action == GasAction.EXECUTE) {
            if (taken >= gasBuffer.getAmount()) {
                gasBuffer = GasStack.EMPTY;
            } else {
                gasBuffer = new GasStack(gasBuffer.getGas(), gasBuffer.getAmount() - taken, gasBuffer.getTemperature(), gasBuffer.getPressure());
            }
        }

        return new GasStack(extract.getGas(), taken, extract.getTemperature(), extract.getPressure());

    }

    public ItemStack addItem(boolean simulate, ItemStack addition) {

        if (addition.isEmpty()) {
            return ItemStack.EMPTY;
        }

        if (itemBuffer.isEmpty()) {

            ItemStack check = addition;

            if (check.getCount() - MAX_ITEM_STACK_SIZE > 0) {
                check = addition.copy();
                check.setCount(MAX_ITEM_STACK_SIZE);
            }

            if (!simulate) {

                itemBuffer = check.copy();
            }
            return check;
        }

        if (itemBuffer.getCount() > 0 && addition.getItem() != itemBuffer.getItem()) {
            return ItemStack.EMPTY;
        }

        int accepted = Math.min(MAX_ITEM_STACK_SIZE - itemBuffer.getCount(), addition.getCount());

        if (!simulate) {
            itemBuffer.setCount(accepted + itemBuffer.getCount());
        }

        ItemStack returned = addition.copy();
        returned.setCount(accepted);

        return returned;

    }

    public ItemStack extractItem(boolean simulate, ItemStack extract) {

        if (itemBuffer.isEmpty() || extract.isEmpty() || extract.getItem() != itemBuffer.getItem()) {
            return ItemStack.EMPTY;
        }

        int taken = Math.min(extract.getCount(), itemBuffer.getCount());

        if (!simulate) {
            if (taken >= itemBuffer.getCount()) {
                itemBuffer = ItemStack.EMPTY;
            } else {
                itemBuffer.setCount(taken);
            }
        }

        ItemStack returned = extract.copy();
        returned.setCount(taken);

        return returned;

    }


}
