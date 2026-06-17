package nuclearscience.common.block;

import nuclearscience.common.tile.accelerator.TileElectromagneticGateway;
import voltaic.common.block.voxelshapes.VoxelShapeProvider;
import voltaic.prefab.block.GenericMachineBlock;

public class BlockElectromagneticGateway extends GenericMachineBlock {

    public BlockElectromagneticGateway() {
	super(TileElectromagneticGateway::new, VoxelShapeProvider.DEFAULT);
    }
}
