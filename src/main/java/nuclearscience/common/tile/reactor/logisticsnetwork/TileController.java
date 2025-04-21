package nuclearscience.common.tile.reactor.logisticsnetwork;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.common.network.ReactorLogisticsNetwork;
import nuclearscience.common.tile.reactor.logisticsnetwork.util.GenericTileLogisticsMember;
import nuclearscience.registers.NuclearScienceSounds;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.common.block.states.VoltaicBlockStates;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.sound.ITickableSound;
import voltaic.prefab.sound.SoundBarrierMethods;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.registers.VoltaicCapabilities;

public class TileController extends GenericTileLogisticsMember implements ITickableSound {

    public static final double USAGE = 100;

    public final SingleProperty<Boolean> active = property(new SingleProperty<>(PropertyTypes.BOOLEAN, "active", false));
    private Direction relativeBack;

    private boolean isSoundPlaying = false;

    public TileController(BlockPos worldPos, BlockState blockState) {
        super(NuclearScienceTiles.TILE_LOGISTICSCONTROLLER.get(), worldPos, blockState);
        addComponent(new ComponentTickable(this).tickServer(this::tickServer).tickClient(this::tickClient));
        addComponent(new ComponentElectrodynamic(this, false, true).setInputDirections(BlockEntityUtils.MachineDirection.BOTTOM).voltage(VoltaicCapabilities.DEFAULT_VOLTAGE).maxJoules(USAGE * 20));
        relativeBack = BlockEntityUtils.getRelativeSide(getFacing(), BlockEntityUtils.MachineDirection.BACK.mappedDir);
    }

    @Override
    public void tickServer(ComponentTickable tickable) {
        super.tickServer(tickable);

        ComponentElectrodynamic electro = getComponent(IComponentType.Electrodynamic);

        boolean canRun = electro.getJoulesStored() >= USAGE;

        if (BlockEntityUtils.isLit(this) ^ canRun) {
            BlockEntityUtils.updateLit(this, canRun);
        }

        if (canRun) {
            electro.setJoulesStored(electro.getJoulesStored() - USAGE);
            active.setValue(canRun);
        }

    }

    public void tickClient(ComponentTickable tickable) {
        if (!isSoundPlaying && shouldPlaySound()) {
            isSoundPlaying = true;
            SoundBarrierMethods.playTileSound(NuclearScienceSounds.SOUND_LOGISTICSCONTROLLER.get(), this, true);
        }
    }

    @Override
    public Direction getCableLocation() {
        return relativeBack;
    }

    @Override
    public boolean canConnect(ReactorLogisticsNetwork network) {
        return network.getController() == null || network.getController().getBlockPos().equals(getBlockPos());
    }

    @Override
    public void onBlockStateUpdate(BlockState oldState, BlockState newState) {
        super.onBlockStateUpdate(oldState, newState);
        if (!level.isClientSide() && oldState.hasProperty(VoltaicBlockStates.FACING) && newState.hasProperty(VoltaicBlockStates.FACING) && oldState.getValue(VoltaicBlockStates.FACING) != newState.getValue(VoltaicBlockStates.FACING)) {
            relativeBack = BlockEntityUtils.getRelativeSide(getFacing(), BlockEntityUtils.MachineDirection.BACK.mappedDir);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.saveAdditional(compound, registries);
        compound.putInt("relativeback", relativeBack.ordinal());
    }

    @Override
    protected void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.loadAdditional(compound, registries);
        relativeBack = Direction.values()[compound.getInt("relativeback")];
    }

    public boolean isActive() {
        return active.getValue();
    }

    @Override
    public void setNotPlaying() {
        isSoundPlaying = false;
    }

    @Override
    public boolean shouldPlaySound() {
        return active.getValue();
    }
}
