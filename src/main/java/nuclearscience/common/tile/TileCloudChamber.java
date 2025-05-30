package nuclearscience.common.tile;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import nuclearscience.client.render.event.levelstage.HandlerCloudChamber;
import nuclearscience.common.inventory.container.ContainerCloudChamber;
import nuclearscience.common.settings.NuclearConstants;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.api.radiation.RadiationSystem;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.ListProperty;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.tile.components.type.ComponentFluidHandlerSimple;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.registers.VoltaicCapabilities;

public class TileCloudChamber extends GenericTile {

    public static final int HORR_RADIUS = 30;
    private static final int VERT_RADIUS = 30;

    public final ListProperty<BlockPos> sources = property(new ListProperty<>(PropertyTypes.BLOCK_POS_LIST, "sources", new ArrayList<BlockPos>()));
    public final SingleProperty<Boolean> active = property(new SingleProperty<>(PropertyTypes.BOOLEAN, "active", false));
    public final SingleProperty<Boolean> sourcesDetected = property(new SingleProperty<>(PropertyTypes.BOOLEAN, "detectedsources", false));
    private final SingleProperty<Boolean> hasRedstoneSignal = property(new SingleProperty(PropertyTypes.BOOLEAN, "redstonesignal", false));

    public TileCloudChamber(BlockPos worldPos, BlockState blockState) {
        super(NuclearScienceTiles.TILE_CLOUDCHAMBER.get(), worldPos, blockState);

        addComponent(new ComponentTickable(this).tickServer(this::tickServer).tickClient(this::tickClient));
        addComponent(new ComponentElectrodynamic(this, false, true).setInputDirections(BlockEntityUtils.MachineDirection.BOTTOM).voltage(VoltaicCapabilities.DEFAULT_VOLTAGE).maxJoules(NuclearConstants.CLOUD_CHAMBER_ENERGY_USAGE_PER_TICK * 20));
        addComponent(new ComponentFluidHandlerSimple(100, fluidStack -> fluidStack.getFluid().is(NuclearScienceTags.Fluids.METHANOL), this, "methanolstorage").setInputDirections(BlockEntityUtils.MachineDirection.BACK));
        addComponent(new ComponentContainerProvider("cloudchamber", this).createMenu((id, player) -> new ContainerCloudChamber(id, player, new SimpleContainer(), getCoordsArray())));

    }

    private void tickClient(ComponentTickable tickable) {
        if(sourcesDetected.getValue()) {
            HandlerCloudChamber.addSources(this);
        } else {
            HandlerCloudChamber.removeSources(this);
        }
    }

    private void tickServer(ComponentTickable tickable) {

        this.sources.wipeList();

        if(hasRedstoneSignal.getValue()) {
            active.setValue(false);
            sourcesDetected.setValue(false);
            return;
        }

        ComponentElectrodynamic electro = getComponent(IComponentType.Electrodynamic);

        if(electro.getJoulesStored() < NuclearConstants.CLOUD_CHAMBER_ENERGY_USAGE_PER_TICK) {
            active.setValue(false);
            sourcesDetected.setValue(false);
            return;
        }

        ComponentFluidHandlerSimple fluid = getComponent(IComponentType.FluidHandler);

        if(fluid.isEmpty() || fluid.getFluidAmount() < NuclearConstants.CLOUD_CHAMBER_ENERGY_USAGE_PER_TICK) {
            active.setValue(false);
            sourcesDetected.setValue(false);
            return;
        }

        active.setValue(true);

        electro.setJoulesStored(electro.getJoulesStored() - NuclearConstants.CLOUD_CHAMBER_ENERGY_USAGE_PER_TICK);
        fluid.drain(NuclearConstants.CLOUD_CHAMBER_FLUID_USAGE_PER_TICK, IFluidHandler.FluidAction.EXECUTE);

        List<BlockPos> sources = RadiationSystem.getRadiationSources(getLevel());

        List<BlockPos> accepted = new ArrayList<>();

        BlockPos pos = getBlockPos();

        sources.forEach(source -> {

            int deltaX = source.getX() - pos.getX();
            int deltaY = source.getY() - pos.getY();
            int deltaZ = source.getZ() - pos.getZ();

            if(Math.abs(deltaY) > VERT_RADIUS || Math.abs(deltaX) > HORR_RADIUS || Math.abs(deltaZ) > HORR_RADIUS) {
                return;
            }

            accepted.add(source);

        });

        sourcesDetected.setValue(!accepted.isEmpty());

        if(accepted.isEmpty()) {
            //active.setValue(false);
            return;
        }

        //active.setValue(true);

        this.sources.addValues(accepted);
    }

    @Override
    public void onNeightborChanged(BlockPos neighbor, boolean blockStateTrigger) {
        if (!level.isClientSide) {
            hasRedstoneSignal.setValue(this.level.hasNeighborSignal(this.getBlockPos()));
        }
    }


}
