package nuclearscience.common.tile.reactor.logisticsnetwork;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.common.inventory.container.ContainerSupplyModule;
import nuclearscience.common.settings.NuclearConfig;
import nuclearscience.common.tile.reactor.logisticsnetwork.interfaces.GenericTileInterface;
import nuclearscience.common.tile.reactor.logisticsnetwork.util.GenericTileInterfaceBound;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.common.block.states.VoltaicBlockStates;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentInventory;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.RadiationUtils;

public class TileSupplyModule extends GenericTileInterfaceBound {

    private Direction relativeBack;

    public TileSupplyModule(BlockPos worldPos, BlockState blockState) {
	super(NuclearScienceTiles.TILE_SUPPLYMODULE.get(), worldPos, blockState);
	addComponent(new ComponentTickable(this).tickServer(this::tickServer));
	addComponent(new ComponentInventory(this, ComponentInventory.InventoryBuilder.newInv().inputs(9).outputs(9))
		//
		.setSlotsByDirection(BlockEntityUtils.MachineDirection.TOP, 0, 1, 2, 3, 4, 5, 6, 7, 8)
		//
		.setSlotsByDirection(BlockEntityUtils.MachineDirection.FRONT, 0, 1, 2, 3, 4, 5, 6, 7, 8)
		//
		.setSlotsByDirection(BlockEntityUtils.MachineDirection.BOTTOM, 9, 10, 11, 12, 13, 14, 15, 16, 17)
		//
		.setSlotsByDirection(BlockEntityUtils.MachineDirection.LEFT, 9, 10, 11, 12, 13, 14, 15, 16, 17)
		//
		.setSlotsByDirection(BlockEntityUtils.MachineDirection.RIGHT, 9, 10, 11, 12, 13, 14, 15, 16, 17)
		.valid(machineValidator()));
	addComponent(new ComponentContainerProvider("supplymodule", this)
		.createMenu((id, player) -> new ContainerSupplyModule(id, player,
			requireComponent(IComponentType.Inventory), getCoordsArray())));
	relativeBack = BlockEntityUtils.getRelativeSide(getFacing(), BlockEntityUtils.MachineDirection.BACK.mappedDir);
    }

    @Override
    public void tickServer(Level level, ComponentTickable tickable) {
	super.tickServer(level, tickable);
	RadiationUtils.handleRadioactiveItems(level, this,
		(ComponentInventory) requireComponent(IComponentType.Inventory),
		NuclearConfig.getInstance().RADIOACTIVE_PROCESSOR_RADIATION_RADIUS.get(), true, 30, true, false);
    }

    @Override
    public boolean checkLinkedPosition(GenericTileInterface inter) {
	return inter.supplyModuleLocation.getValue().equals(getBlockPos());
    }

    @Override
    public GenericTileInterface.InterfaceType[] getValidInterfaces() {
	return SUPPLIES;
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
    public void onInterfacePropChange(SingleProperty<BlockPos> prop, BlockPos old) {
	super.onInterfacePropChange(prop, old);

	BlockPos current = prop.getValue();
	boolean oldInvalid = old.equals(BlockEntityUtils.OUT_OF_REACH);
	boolean newInvalid = current.equals(BlockEntityUtils.OUT_OF_REACH);
	if (oldInvalid == newInvalid)
	    return;

	BlockPos interfacePos = newInvalid ? old : current;
	BlockPos supplyPos = newInvalid ? BlockEntityUtils.OUT_OF_REACH : getBlockPos();

	getNetworkCable(TileReactorLogisticsCable.class).map(TileReactorLogisticsCable::getNetwork)
		.map(network -> network.getInterface(interfacePos))
		.ifPresent(inter -> inter.supplyModuleLocation.setValue(supplyPos));
    }

    @Override
    public void onBlockDestroyed(Level level) {
	super.onBlockDestroyed(level);
	if (level.isClientSide())
	    return;

	getNetworkCable(TileReactorLogisticsCable.class).map(TileReactorLogisticsCable::getNetwork)
		.map(network -> network.getInterface(interfaceLocation.getValue()))
		.ifPresent(inter -> inter.supplyModuleLocation.setValue(BlockEntityUtils.OUT_OF_REACH));
    }
}
