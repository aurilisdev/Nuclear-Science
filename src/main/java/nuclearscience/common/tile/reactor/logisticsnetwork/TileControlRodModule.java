package nuclearscience.common.tile.reactor.logisticsnetwork;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import nuclearscience.common.inventory.container.ContainerControlRodModule;
import nuclearscience.common.network.ReactorLogisticsNetwork;
import nuclearscience.common.tile.reactor.TileControlRod;
import nuclearscience.common.tile.reactor.logisticsnetwork.interfaces.GenericTileInterface;
import nuclearscience.common.tile.reactor.logisticsnetwork.util.GenericTileInterfaceBound;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.common.block.states.VoltaicBlockStates;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.registers.VoltaicItems;

public class TileControlRodModule extends GenericTileInterfaceBound {

    private Direction relativeBack;

    public final SingleProperty<Integer> insertion = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.INTEGER, "insertion", 0));
    public final SingleProperty<Integer> redstoneSignal = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.INTEGER, "redstonesignal", 0))
	    .onChange((prop, oldVal) -> {
		Level level = this.level;
		if (level == null || level.isClientSide || prop.getValue() == oldVal)
		    return;

		double perc = (double) prop.getValue() / 15.0;

		double tot = perc * TileControlRod.MAX_EXTENSION;

		int mult = (int) (tot / TileControlRod.EXTENSION_PER_CLICK);

		insertion.setValue(mult * TileControlRod.EXTENSION_PER_CLICK);
	    });

    public TileControlRodModule(BlockPos worldPos, BlockState blockState) {
	super(NuclearScienceTiles.TILE_CONTROLRODMODULE.get(), worldPos, blockState);
	addComponent(new ComponentTickable(this).tickServer(this::tickServer));
	addComponent(new ComponentContainerProvider("controlrodmodule", this).createMenu(
		(id, player) -> new ContainerControlRodModule(id, player, new SimpleContainer(0), getCoordsArray())));
	relativeBack = BlockEntityUtils.getRelativeSide(getFacing(), BlockEntityUtils.MachineDirection.BACK.mappedDir);
    }

    @Override
    public boolean checkLinkedPosition(GenericTileInterface inter) {
	return inter.controlRodLocation.getValue().equals(getBlockPos());
    }

    @Override
    public Direction getCableLocation() {
	return relativeBack;
    }

    @Override
    public void onBlockStateUpdate(Level level, BlockState oldState, BlockState newState) {
	super.onBlockStateUpdate(level, oldState, newState);
	if (!level.isClientSide() && oldState.hasProperty(VoltaicBlockStates.FACING)
		&& newState.hasProperty(VoltaicBlockStates.FACING)
		&& oldState.getValue(VoltaicBlockStates.FACING) != newState.getValue(VoltaicBlockStates.FACING)) {
	    relativeBack = BlockEntityUtils.getRelativeSide(getFacing(),
		    BlockEntityUtils.MachineDirection.BACK.mappedDir);
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

    @Override
    public void onNeighbourChanged(LevelReader reader, BlockPos neighbor, boolean blockStateTrigger) {
	super.onNeighbourChanged(reader, neighbor, blockStateTrigger);
	if (!(reader instanceof ClientLevel)) {
	    redstoneSignal.setValue(reader.getBestNeighborSignal(getBlockPos()));
	}
    }

    @Override
    public void onBlockDestroyed(Level level) {
	super.onBlockDestroyed(level);
	if (!level.isClientSide()) {

	    getNetworkCable(TileReactorLogisticsCable.class).ifPresent(cable -> {
		if (cable.isRemoved())
		    return;

		ReactorLogisticsNetwork network = cable.getNetwork();

		GenericTileInterface inter = network.getInterface(interfaceLocation.getValue());

		if (inter == null)
		    return;

		inter.controlRodLocation.setValue(BlockEntityUtils.OUT_OF_REACH);
	    });
	}
    }

    @Override
    public GenericTileInterface.InterfaceType[] getValidInterfaces() {
	return CONTROL_RODS;
    }

    @Override
    public ItemInteractionResult useWithItem(Level level, ItemStack used, Player player, InteractionHand hand,
	    BlockHitResult hit) {
	if (player.getItemInHand(hand).is(VoltaicItems.ITEM_WRENCH)) {
	    if (hasComponent(IComponentType.ContainerProvider)) {
		if (!level.isClientSide) {
		    player.openMenu(this.requireComponent(IComponentType.ContainerProvider));
		    player.awardStat(Stats.INTERACT_WITH_FURNACE);
		}

		return ItemInteractionResult.CONSUME;
	    }
	    return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	}
	return super.useWithItem(level, used, player, hand, hit);
    }

    @Override
    public int getComparatorSignal(Level level) {
	return (int) ((double) insertion.getValue() / (double) TileControlRod.MAX_EXTENSION * 15);
    }

    @Override
    public InteractionResult useWithoutItem(Level level, Player player, BlockHitResult hit) {
	if (level.isClientSide())
	    return InteractionResult.CONSUME;

	if (player.isShiftKeyDown()) {
	    insertion.setValue(insertion.getValue() - TileControlRod.TileFissionControlRod.EXTENSION_PER_CLICK);
	    if (insertion.getValue() < 0) {
		insertion.setValue(TileControlRod.TileFissionControlRod.MAX_EXTENSION);
	    }
	} else {
	    insertion.setValue(insertion.getValue() + TileControlRod.TileFissionControlRod.EXTENSION_PER_CLICK);
	    if (insertion.getValue() > TileControlRod.TileFissionControlRod.MAX_EXTENSION) {
		insertion.setValue(0);
	    }
	}

	return InteractionResult.CONSUME;
    }

    @Override
    public void onInterfacePropChange(SingleProperty<BlockPos> prop, BlockPos old) {

	super.onInterfacePropChange(prop, old);

	boolean oldInval = old.equals(BlockEntityUtils.OUT_OF_REACH);
	boolean newInval = prop.getValue().equals(BlockEntityUtils.OUT_OF_REACH);

	if (oldInval && newInval)
	    return;

	getNetworkCable(TileReactorLogisticsCable.class).ifPresent(cable -> {

	    if (cable.isRemoved())
		return;

	    ReactorLogisticsNetwork network = cable.getNetwork();

	    if (oldInval && !newInval) {
		GenericTileInterface inter = network.getInterface(prop.getValue());

		if (inter != null) {
		    inter.controlRodLocation.setValue(getBlockPos());
		}
	    } else if (!oldInval && newInval) {
		GenericTileInterface inter = network.getInterface(old);

		if (inter != null) {
		    inter.controlRodLocation.setValue(BlockEntityUtils.OUT_OF_REACH);
		}
	    }

	});

    }
}
