package nuclearscience.common.tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import nuclearscience.api.quantumtunnel.FrequencyConnectionManager;
import nuclearscience.api.quantumtunnel.TunnelFrequency;
import nuclearscience.api.quantumtunnel.TunnelFrequencyBuffer;
import nuclearscience.api.quantumtunnel.TunnelFrequencyManager;
import nuclearscience.common.inventory.container.ContainerQuantumTunnel;
import nuclearscience.prefab.NuclearPropertyTypes;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.api.electricity.ICapabilityElectrodynamic;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentInventory;
import voltaic.prefab.tile.components.type.ComponentPacketHandler;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.CapabilityUtils;
import voltaic.prefab.utilities.object.CachedTileOutput;
import voltaic.prefab.utilities.object.TransferPack;
import voltaic.registers.VoltaicCapabilities;

public class TileQuantumTunnel extends GenericTile {

    // DUNSWE

    public static final int DOWN_MASK = 0b00000000000000000000000000001111;
    public static final int UP_MASK = 0b00000000000000000000000011110000;
    public static final int NORTH_MASK = 0b00000000000000000000111100000000;
    public static final int SOUTH_MASK = 0b00000000000000001111000000000000;
    public static final int WEST_MASK = 0b00000000000011110000000000000000;
    public static final int EAST_MASK = 0b00000000111100000000000000000000;


    public SingleProperty<TunnelFrequency> frequency = property(new SingleProperty<>(NuclearPropertyTypes.TUNNEL_FREQUENCY, "frequency", TunnelFrequency.NO_FREQUENCY));
    public SingleProperty<Integer> inputDirections = property(new SingleProperty<>(PropertyTypes.INTEGER, "inputdirections", 0)).onChange((prop, val) -> {
        if(level == null) {
            return;
        }
        if(level.isClientSide()){
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 8); //
        } else {
            refreshCapabilities();
        }
    });
    public SingleProperty<Integer> outputDirections = property(new SingleProperty<>(PropertyTypes.INTEGER, "outputdirections", 0)).onChange((prop, val) -> {
        if(level == null) {
            return;
        }
        if(level.isClientSide()){
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 8); //
        } else {
            refreshCapabilities();
        }
    });

    private CachedTileOutput[] outputCache = new CachedTileOutput[6];

    private ItemHandler[] itemHandlers = new ItemHandler[6];
    private FluidHandler[] fluidHandlers = new FluidHandler[6];
    private ElectrodynamicHandler[] electrodynamicHandlers = new ElectrodynamicHandler[6];
    private FEHandler[] feHandlers = new FEHandler[6];

    public HashMap<UUID, HashSet<TunnelFrequency>> clientFrequencies = new HashMap<>();
    public TunnelFrequencyBuffer clientBuffer = TunnelFrequencyBuffer.EMPTY;

    public TileQuantumTunnel(BlockPos pos, BlockState state) {
        super(NuclearScienceTiles.TILE_QUANTUMCAPACITOR.get(), pos, state);
        addComponent(new ComponentTickable(this).tickServer(this::tickServer));
        addComponent(new ComponentPacketHandler(this));
        addComponent(new ComponentInventory(this));
        addComponent(new ComponentContainerProvider("quantumcapacitor", this).createMenu((id, player) -> new ContainerQuantumTunnel(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));

    }

    public void tickServer(ComponentTickable tickable) {

        if(!TunnelFrequencyManager.doesFrequencyExist(frequency.getValue())) {
            frequency.setValue(TunnelFrequency.NO_FREQUENCY);
        }

        if(frequency.getValue().equals(TunnelFrequency.NO_FREQUENCY)) {
            return;
        }

        for(Direction direction : Direction.values()) {
            Direction dir = BlockEntityUtils.getRelativeSide(getFacing(), direction);
            if(outputCache[dir.ordinal()] == null) {
                outputCache[dir.ordinal()] = new CachedTileOutput(level, new BlockPos(worldPosition).relative(dir));
            }
        }

        for(Direction direction : Direction.values()) {
            Direction dir = BlockEntityUtils.getRelativeSide(getFacing(), direction);
            if(!outputCache[dir.ordinal()].valid()) {
                outputCache[dir.ordinal()].update(new BlockPos(worldPosition).relative(dir));
            }
        }

        if (frequency.getValue().equals(TunnelFrequency.NO_FREQUENCY)) {
            return;
        }

        for (Direction direction : readOutputDirections()) {

            Direction relative = BlockEntityUtils.getRelativeSide(getFacing(), direction);

            CachedTileOutput output = outputCache[relative.ordinal()];
            if (!output.valid()) {
                continue;
            }
            BlockEntity tile = output.getSafe();

            if(tile == null) {
                return;
            }

            IItemHandler itemCap = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, relative.getOpposite()).orElse(CapabilityUtils.EMPTY_ITEM_HANDLER);

            if (itemCap != CapabilityUtils.EMPTY_ITEM_HANDLER) {
                ItemStack bufferedItem = FrequencyConnectionManager.getBufferedItem(frequency.getValue()).copy();
                ItemStack formerBufferedItem = bufferedItem.copy();

                if (!bufferedItem.isEmpty()) {
                    for (int i = 0; i < itemCap.getSlots(); i++) {

                        bufferedItem.setCount(bufferedItem.getCount() - itemCap.insertItem(i, bufferedItem, false).getCount());
                        if (bufferedItem.getCount() <= 0) {
                            break;
                        }

                    }
                    int delta = formerBufferedItem.getCount() - bufferedItem.getCount();
                    if (delta > 0) {
                        formerBufferedItem.setCount(delta);
                        FrequencyConnectionManager.extractItem(frequency.getValue(), formerBufferedItem, false);
                    }
                }
            }

            IFluidHandler fluidCap = tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, relative.getOpposite()).orElse(CapabilityUtils.EMPTY_FLUID);

            if (fluidCap != CapabilityUtils.EMPTY_FLUID) {
                FluidStack bufferedFluid = FrequencyConnectionManager.getBufferedFluid(frequency.getValue()).copy();

                if (!bufferedFluid.isEmpty()) {
                    int taken = fluidCap.fill(bufferedFluid, IFluidHandler.FluidAction.EXECUTE);
                    if (taken > 0) {
                        bufferedFluid.setAmount(taken);
                        FrequencyConnectionManager.extractFluid(frequency.getValue(), bufferedFluid, IFluidHandler.FluidAction.EXECUTE);
                    }
                }
            }

            ICapabilityElectrodynamic electroCap = tile.getCapability(VoltaicCapabilities.CAPABILITY_ELECTRODYNAMIC_BLOCK, relative.getOpposite()).orElse(CapabilityUtils.EMPTY_ELECTRO);

            if (electroCap != CapabilityUtils.EMPTY_ELECTRO && electroCap.isEnergyReceiver()) {
                TransferPack bufferedEnergy = FrequencyConnectionManager.getBufferedEnergy(frequency.getValue());
                if (bufferedEnergy.getJoules() > 0) {
                    TransferPack taken = electroCap.receivePower(bufferedEnergy, false);
                    if (taken.getJoules() > 0) {
                        FrequencyConnectionManager.extractEnergy(frequency.getValue(), taken, false);
                    }
                }
            }

            IEnergyStorage feCap = tile.getCapability(CapabilityEnergy.ENERGY, relative.getOpposite()).orElse(CapabilityUtils.EMPTY_FE);

            if (feCap != CapabilityUtils.EMPTY_FE && feCap.canReceive()) {
                TransferPack bufferedEnergy = FrequencyConnectionManager.getBufferedEnergy(frequency.getValue());
                if (bufferedEnergy.getJoules() > 0 && bufferedEnergy.getVoltage() == VoltaicCapabilities.DEFAULT_VOLTAGE) {
                    int taken = feCap.receiveEnergy((int) bufferedEnergy.getJoules(), false);
                    if (taken > 0) {
                        FrequencyConnectionManager.extractEnergy(frequency.getValue(), TransferPack.joulesVoltage(taken, VoltaicCapabilities.DEFAULT_VOLTAGE), false);
                    }
                }
            }


        }

    }
    
    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side) {
    	if(side == null || frequency.getValue().equals(TunnelFrequency.NO_FREQUENCY)) {
    		return LazyOptional.empty();
    	}
    	if(cap == CapabilityEnergy.ENERGY) {
    		IEnergyStorage storage = feHandlers[side.ordinal()];
    		return storage == null ? LazyOptional.empty() : LazyOptional.of(() -> storage).cast();
    	}
    	if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
    		IItemHandler handler = itemHandlers[side.ordinal()];
    		return handler == null ? LazyOptional.empty() : LazyOptional.of(() -> handler).cast();
    	}
    	if(cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
    		IFluidHandler handler = fluidHandlers[side.ordinal()];
    		return handler == null ? LazyOptional.empty() : LazyOptional.of(() -> handler).cast();
    	}
    	if(cap == VoltaicCapabilities.CAPABILITY_ELECTRODYNAMIC_BLOCK) {
    		ICapabilityElectrodynamic electro = electrodynamicHandlers[side.ordinal()];
    		return electro == null ? LazyOptional.empty() : LazyOptional.of(() -> electro).cast();
    	}
    	return LazyOptional.empty();
    }
   
    @Override
    public void onLoad() {
        refreshCapabilities();
        super.onLoad();
    }

    private void refreshCapabilities() {
        itemHandlers = new ItemHandler[6];
        fluidHandlers = new FluidHandler[6];
        electrodynamicHandlers = new ElectrodynamicHandler[6];
        feHandlers = new FEHandler[6];

        for (Direction dir : readInputDirections()) {
            int index = BlockEntityUtils.getRelativeSide(getFacing(), dir).ordinal();
            itemHandlers[index] = new ItemHandler(true);
            fluidHandlers[index] = new FluidHandler(true);
            electrodynamicHandlers[index] = new ElectrodynamicHandler(true);
            feHandlers[index] = new FEHandler(true);
        }

        for (Direction dir : readOutputDirections()) {
            int index = BlockEntityUtils.getRelativeSide(getFacing(), dir).ordinal();
            itemHandlers[index] = new ItemHandler(false);
            fluidHandlers[index] = new FluidHandler(false);
            electrodynamicHandlers[index] = new ElectrodynamicHandler(false);
            feHandlers[index] = new FEHandler(false);
        }

    }

    public List<Direction> readInputDirections() {
        return readDirections(inputDirections.getValue(), 1);
    }

    public List<Direction> readOutputDirections() {
        return readDirections(outputDirections.getValue(), 2);
    }

    public void writeInputDirection(Direction dir) {
        inputDirections.setValue(writeDirection(inputDirections.getValue(), dir, 1));
    }

    public void writeOutputDirection(Direction dir) {
        outputDirections.setValue(writeDirection(outputDirections.getValue(), dir, 2));
    }

    public void removeInputDirection(Direction dir) {
        inputDirections.setValue(removeDirection(inputDirections.getValue(), dir));
    }

    public void removeOutputDirection(Direction dir) {
        outputDirections.setValue(removeDirection(outputDirections.getValue(), dir));
    }

    private List<Direction> readDirections(int directions, int checkValue) {
        List<Direction> values = new ArrayList<>();
        if ((directions & DOWN_MASK) >> Direction.DOWN.ordinal() * 4 == checkValue) {
            values.add(Direction.DOWN);
        }
        if ((directions & UP_MASK) >> Direction.UP.ordinal() * 4 == checkValue) {
            values.add(Direction.UP);
        }
        if ((directions & NORTH_MASK) >> Direction.NORTH.ordinal() * 4 == checkValue) {
            values.add(Direction.NORTH);
        }
        if ((directions & SOUTH_MASK) >> Direction.SOUTH.ordinal() * 4 == checkValue) {
            values.add(Direction.SOUTH);
        }
        if ((directions & WEST_MASK) >> Direction.WEST.ordinal() * 4 == checkValue) {
            values.add(Direction.WEST);
        }
        if ((directions & EAST_MASK) >> Direction.EAST.ordinal() * 4 == checkValue) {
            values.add(Direction.EAST);
        }
        return values;
    }

    private int writeDirection(int directions, Direction dir, int value) {

        int masked = switch (dir) {
	case DOWN -> directions & ~DOWN_MASK;
	case UP -> directions & ~UP_MASK;
	case NORTH -> directions & ~NORTH_MASK;
	case SOUTH -> directions & ~SOUTH_MASK;
	case WEST -> directions & ~WEST_MASK;
	case EAST -> directions & ~EAST_MASK;
	default -> 0;
	};

        return masked | value << dir.ordinal() * 4;

    }

    private int removeDirection(int directions, Direction dir) {
        return switch (dir) {
            case DOWN -> directions & ~DOWN_MASK;
            case UP -> directions & ~UP_MASK;
            case NORTH -> directions & ~NORTH_MASK;
            case SOUTH -> directions & ~SOUTH_MASK;
            case WEST -> directions & ~WEST_MASK;
            case EAST -> directions & ~EAST_MASK;
        };
    }

    private class ItemHandler implements IItemHandler {

        private final boolean isReciever;

        public ItemHandler(boolean isReciever) {
            this.isReciever = isReciever;
        }

        @Override
        public int getSlots() {
            return 1;
        }

        @Override
        public ItemStack getStackInSlot(int slot) {
            return FrequencyConnectionManager.getBufferedItem(frequency.getValue());
        }

        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            return isReciever ? FrequencyConnectionManager.recieveItem(frequency.getValue(), stack, simulate) : ItemStack.EMPTY;
        }

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            ItemStack buffered = getStackInSlot(0);
            if (buffered.isEmpty()) {
                return ItemStack.EMPTY;
            }
            buffered.setCount(amount);
            return isReciever ? ItemStack.EMPTY : FrequencyConnectionManager.extractItem(frequency.getValue(), buffered, simulate);
        }

        @Override
        public int getSlotLimit(int slot) {
            return TunnelFrequencyBuffer.MAX_ITEM_STACK_SIZE;
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return true;
        }
    }

    private class FluidHandler implements IFluidHandler {

        private final boolean isReciever;

        public FluidHandler(boolean isReciever) {
            this.isReciever = isReciever;
        }

        @Override
        public int getTanks() {
            return 1;
        }

        @Override
        public FluidStack getFluidInTank(int tank) {
            return FrequencyConnectionManager.getBufferedFluid(frequency.getValue());
        }

        @Override
        public int getTankCapacity(int tank) {
            return TunnelFrequencyBuffer.MAX_FLUID_CAP;
        }

        @Override
        public boolean isFluidValid(int tank, FluidStack stack) {
            return true;
        }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            return isReciever ? FrequencyConnectionManager.recieveFluid(frequency.getValue(), resource, action).getAmount() : 0;
        }

        @Override
        public FluidStack drain(FluidStack resource, FluidAction action) {
            return isReciever ? FluidStack.EMPTY : FrequencyConnectionManager.extractFluid(frequency.getValue(), resource, action);
        }

        @Override
        public FluidStack drain(int maxDrain, FluidAction action) {
            FluidStack buffered = FrequencyConnectionManager.getBufferedFluid(frequency.getValue());
            if (buffered.isEmpty()) {
                return FluidStack.EMPTY;
            }
            return drain(new FluidStack(buffered.getFluid(), maxDrain), action);
        }
    }

    private class ElectrodynamicHandler implements ICapabilityElectrodynamic {

        private final boolean isReciever;

        public ElectrodynamicHandler(boolean isReciever) {
            this.isReciever = isReciever;
        }

        @Override
        public double getJoulesStored() {
            return FrequencyConnectionManager.getBufferedEnergy(frequency.getValue()).getJoules();
        }

        @Override
        public double getMaxJoulesStored() {
            return TunnelFrequencyBuffer.MAX_JOULES_CAP;
        }

        @Override
        public void setJoulesStored(double v) {

        }

        @Override
        public double getVoltage() {
            return -1;
        }

        @Override
        public double getMinimumVoltage() {
            return -1;
        }

        @Override
        public double getMaximumVoltage() {
            return Double.MAX_VALUE;
        }

        @Override
        public double getAmpacity() {
            return -1;
        }

        @Override
        public boolean isEnergyReceiver() {
            return isReciever;
        }

        @Override
        public boolean isEnergyProducer() {
            return !isReciever;
        }

        @Override
        public TransferPack extractPower(TransferPack transfer, boolean debug) {
            return FrequencyConnectionManager.extractEnergy(frequency.getValue(), transfer, debug);
        }

        @Override
        public TransferPack receivePower(TransferPack transfer, boolean debug) {
            return FrequencyConnectionManager.recieveEnergy(frequency.getValue(), transfer, debug);
        }

        @Override
        public void overVoltage(TransferPack transfer) {

        }

        @Override
        public void onChange() {

        }

        @Override
        public TransferPack getConnectedLoad(LoadProfile loadProfile, Direction direction) {
            return isReciever ? TransferPack.joulesVoltage(TunnelFrequencyBuffer.MAX_JOULES_CAP - FrequencyConnectionManager.getBufferedEnergy(frequency.getValue()).getJoules(), -1) : TransferPack.EMPTY;
        }
    }

    private class FEHandler implements IEnergyStorage {

        private final boolean isReciever;

        public FEHandler(boolean isReciever) {
            this.isReciever = isReciever;
        }

        @Override
        public int receiveEnergy(int toReceive, boolean simulate) {
            return isReciever ? (int) FrequencyConnectionManager.recieveEnergy(frequency.getValue(), TransferPack.joulesVoltage(toReceive, VoltaicCapabilities.DEFAULT_VOLTAGE), simulate).getJoules() : 0;
        }

        @Override
        public int extractEnergy(int toExtract, boolean simulate) {
            return isReciever ? 0 : (int) FrequencyConnectionManager.recieveEnergy(frequency.getValue(), TransferPack.joulesVoltage(toExtract, VoltaicCapabilities.DEFAULT_VOLTAGE), simulate).getJoules();
        }

        @Override
        public int getEnergyStored() {
            return (int) FrequencyConnectionManager.getBufferedEnergy(frequency.getValue()).getJoules();
        }

        @Override
        public int getMaxEnergyStored() {
            return (int) TunnelFrequencyBuffer.MAX_JOULES_CAP;
        }

        @Override
        public boolean canExtract() {
            return !isReciever;
        }

        @Override
        public boolean canReceive() {
            return isReciever;
        }
    }


}
