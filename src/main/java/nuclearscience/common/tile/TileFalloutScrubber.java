package nuclearscience.common.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import nuclearscience.common.inventory.container.ContainerFalloutScrubber;
import nuclearscience.common.settings.NuclearConstants;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.api.radiation.RadiationSystem;
import voltaic.api.radiation.util.BlockPosVolume;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.tile.components.type.ComponentFluidHandlerMulti;
import voltaic.prefab.tile.components.type.ComponentPacketHandler;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.registers.VoltaicCapabilities;

public class TileFalloutScrubber extends GenericTile {

    private static final int FLUID_USAGE_PER_TICK = 1;
    public static final int RANGE = 30;

    public static final double DISIPATION = 1.0;

    public final SingleProperty<Boolean> active = property(new SingleProperty<>(PropertyTypes.BOOLEAN, "active", false));
    private final SingleProperty<Boolean> hasRedstoneSignal = property(new SingleProperty(PropertyTypes.BOOLEAN, "redstonesignal", false));

    private final BlockPosVolume area;


    public TileFalloutScrubber(BlockPos worldPos, BlockState blockState) {
        super(NuclearScienceTiles.TILE_FALLOUTSCRUBBER.get(), worldPos, blockState);

        area = new BlockPosVolume(worldPos.offset(-RANGE, -RANGE, -RANGE), worldPos.offset(RANGE, RANGE, RANGE));

        addComponent(new ComponentPacketHandler(this));
        addComponent(new ComponentTickable(this).tickServer(this::tickServer));
        addComponent(new ComponentElectrodynamic(this, false, true).maxJoules(NuclearConstants.FALLOUT_SCRUBBER_USAGE_PER_TICK * 20).voltage(VoltaicCapabilities.DEFAULT_VOLTAGE).setInputDirections(BlockEntityUtils.MachineDirection.BOTTOM));
        addComponent(new ComponentFluidHandlerMulti(this).setInputTanks(2, 100, 100).setInputFluidTags(FluidTags.WATER, NuclearScienceTags.Fluids.DECONTAMINATION_FOAM).setInputDirections(BlockEntityUtils.MachineDirection.LEFT, BlockEntityUtils.MachineDirection.RIGHT));
        addComponent(new ComponentContainerProvider("falloutscrubber", this).createMenu((id, player) -> new ContainerFalloutScrubber(id, player, new SimpleContainer(), getCoordsArray())));
    }

    private void tickServer(ComponentTickable tickable) {

        if(hasRedstoneSignal.getValue()) {
            active.setValue(false);
            RadiationSystem.removeDisipation(getLevel(), area);
            return;
        }

        ComponentElectrodynamic electro = getComponent(IComponentType.Electrodynamic);

        if(electro.getJoulesStored() < NuclearConstants.FALLOUT_SCRUBBER_USAGE_PER_TICK) {
            active.setValue(false);
            RadiationSystem.removeDisipation(getLevel(), area);
            return;
        }

        ComponentFluidHandlerMulti multi = getComponent(IComponentType.FluidHandler);

        FluidTank[] tanks = multi.getInputTanks();

        if(tanks[0].isEmpty() || tanks[0].getFluidAmount() < FLUID_USAGE_PER_TICK || tanks[1].isEmpty() || tanks[1].getFluidAmount() < FLUID_USAGE_PER_TICK) {
            active.setValue(false);
            RadiationSystem.removeDisipation(getLevel(), area);
            return;
        }

        active.setValue(true);
        tanks[0].drain(FLUID_USAGE_PER_TICK, IFluidHandler.FluidAction.EXECUTE);
        tanks[1].drain(FLUID_USAGE_PER_TICK, IFluidHandler.FluidAction.EXECUTE);
        electro.setJoulesStored(electro.getJoulesStored() - NuclearConstants.FALLOUT_SCRUBBER_USAGE_PER_TICK);

        RadiationSystem.addDisipation(getLevel(), DISIPATION, area);

    }

    @Override
    public void onNeightborChanged(BlockPos neighbor, boolean blockStateTrigger) {
        if (!level.isClientSide) {
            hasRedstoneSignal.setValue(this.level.hasNeighborSignal(this.getBlockPos()));
        }
    }
}
