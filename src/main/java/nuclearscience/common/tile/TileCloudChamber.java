package nuclearscience.common.tile;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import nuclearscience.client.render.event.levelstage.HandlerCloudChamber;
import nuclearscience.common.inventory.container.ContainerCloudChamber;
import nuclearscience.common.settings.NuclearConfig;
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

    public final ListProperty<BlockPos> sources = property(new ListProperty<>(getPropertyManager(),
	    PropertyTypes.BLOCK_POS_LIST, "sources", new ArrayList<BlockPos>()));
    public final SingleProperty<Boolean> active = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.BOOLEAN, "active", false));
    public final SingleProperty<Boolean> sourcesDetected = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.BOOLEAN, "detectedsources", false));
    private final SingleProperty<Boolean> hasRedstoneSignal = property(
	    new SingleProperty(getPropertyManager(), PropertyTypes.BOOLEAN, "redstonesignal", false));

    public TileCloudChamber(BlockPos worldPos, BlockState blockState) {
	super(NuclearScienceTiles.TILE_CLOUDCHAMBER.get(), worldPos, blockState);

	addComponent(new ComponentTickable(this).tickServer(this::tickServer).tickClient(this::tickClient));
	addComponent(new ComponentElectrodynamic(this, false, true)
		.setInputDirections(BlockEntityUtils.MachineDirection.BOTTOM)
		.voltage(VoltaicCapabilities.DEFAULT_VOLTAGE)
		.maxJoules(NuclearConfig.getInstance().CLOUD_CHAMBER_ENERGY_USAGE_PER_TICK.get() * 20));
	addComponent(new ComponentFluidHandlerSimple(100,
		fluidStack -> fluidStack.getFluid().is(NuclearScienceTags.Fluids.METHANOL), this, "methanolstorage")
		.setInputDirections(BlockEntityUtils.MachineDirection.BACK));
	addComponent(new ComponentContainerProvider("cloudchamber", this).createMenu(
		(id, player) -> new ContainerCloudChamber(id, player, new SimpleContainer(), getCoordsArray())));

    }

    private void tickClient(Level level, ComponentTickable tickable) {
	if (sourcesDetected.getValue()) {
	    HandlerCloudChamber.addSources(this);
	} else {
	    HandlerCloudChamber.removeSources(this);
	}
    }

    private void tickServer(Level level, ComponentTickable tickable) {
	sources.wipeList();

	if (hasRedstoneSignal.getValue()) {
	    active.setValue(false);
	    sourcesDetected.setValue(false);
	    return;
	}

	ComponentElectrodynamic electro = requireComponent(IComponentType.Electrodynamic);

	if (electro.getJoulesStored() < NuclearConfig.getInstance().CLOUD_CHAMBER_ENERGY_USAGE_PER_TICK.get()) {
	    active.setValue(false);
	    sourcesDetected.setValue(false);
	    return;
	}

	ComponentFluidHandlerSimple fluid = requireComponent(IComponentType.FluidHandler);

	if (fluid.isEmpty()
		|| fluid.getFluidAmount() < NuclearConfig.getInstance().CLOUD_CHAMBER_ENERGY_USAGE_PER_TICK.get()) {
	    active.setValue(false);
	    sourcesDetected.setValue(false);
	    return;
	}

	active.setValue(true);

	electro.setJoulesStored(
		electro.getJoulesStored() - NuclearConfig.getInstance().CLOUD_CHAMBER_ENERGY_USAGE_PER_TICK.get());
	fluid.drain(NuclearConfig.getInstance().CLOUD_CHAMBER_FLUID_USAGE_PER_TICK.get(),
		IFluidHandler.FluidAction.EXECUTE);

	List<BlockPos> sources = RadiationSystem.getRadiationSources(level);

	List<BlockPos> accepted = new ArrayList<>();

	BlockPos pos = getBlockPos();

	sources.forEach(source -> {

	    int deltaX = source.getX() - pos.getX();
	    int deltaY = source.getY() - pos.getY();
	    int deltaZ = source.getZ() - pos.getZ();

	    if (Math.abs(deltaY) > VERT_RADIUS || Math.abs(deltaX) > HORR_RADIUS || Math.abs(deltaZ) > HORR_RADIUS)
		return;

	    accepted.add(source);

	});

	sourcesDetected.setValue(!accepted.isEmpty());

	if (accepted.isEmpty())
	    // active.setValue(false);
	    return;

	// active.setValue(true);

	this.sources.addValues(accepted);

	// this.sources.forceDirtyForManager();
    }

    @Override
    public void onNeighbourChanged(LevelReader reader, BlockPos neighbor, boolean blockStateTrigger) {
	if (!(reader instanceof ClientLevel)) {
	    hasRedstoneSignal.setValue(reader.hasNeighborSignal(getBlockPos()));
	}
    }

}
