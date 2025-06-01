package nuclearscience.common.tile.reactor.logisticsnetwork;

import net.minecraft.block.BlockState;
import net.minecraft.inventory.Inventory;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import nuclearscience.common.inventory.container.ContainerMonitorModule;
import nuclearscience.common.tile.reactor.logisticsnetwork.interfaces.GenericTileInterface;
import nuclearscience.common.tile.reactor.logisticsnetwork.util.GenericTileInterfaceBound;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.common.block.states.VoltaicBlockStates;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;

public class TileMonitorModule extends GenericTileInterfaceBound {

    private Direction relativeBack;

    public TileMonitorModule() {
        super(NuclearScienceTiles.TILE_MONITORMODULE.get());
        addComponent(new ComponentTickable(this).tickServer(this::tickServer).tickClient(this::tickClient));
        addComponent(new ComponentContainerProvider("monitormodule", this).createMenu((id, player) -> new ContainerMonitorModule(id, player, new Inventory(0), getCoordsArray())));
    }
    
    @Override
    public void tickServer(ComponentTickable tickable) {
    	if(relativeBack == null) {
    		relativeBack = BlockEntityUtils.getRelativeSide(getFacing(), BlockEntityUtils.MachineDirection.BACK.mappedDir);
    	}
    	super.tickServer(tickable);
    }
    
    public void tickClient(ComponentTickable tickable) {
    	if(relativeBack == null) {
    		relativeBack = BlockEntityUtils.getRelativeSide(getFacing(), BlockEntityUtils.MachineDirection.BACK.mappedDir);
    	}
    }

    @Override
    public Direction getCableLocation() {
        return relativeBack;
    }

    @Override
    public void onBlockStateUpdate(BlockState oldState, BlockState newState) {
        super.onBlockStateUpdate(oldState, newState);
        if (!level.isClientSide() && oldState.hasProperty(VoltaicBlockStates.FACING) && newState.hasProperty(VoltaicBlockStates.FACING) && oldState.getValue(VoltaicBlockStates.FACING) != newState.getValue(VoltaicBlockStates.FACING)) {
            relativeBack = BlockEntityUtils.getRelativeSide(getFacing(), BlockEntityUtils.MachineDirection.BACK.mappedDir);
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        compound.putInt("relativeback", relativeBack.ordinal());
        return super.save(compound);
    }

    @Override
	public void load(BlockState state, CompoundNBT compound) {
        super.load(state, compound);
        relativeBack = Direction.values()[compound.getInt("relativeback")];
    }

    @Override
    public boolean checkLinkedPosition(GenericTileInterface inter) {
        return true;
    }

    @Override
    public GenericTileInterface.InterfaceType[] getValidInterfaces() {
        return ALL;
    }
}
