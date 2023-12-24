package nuclearscience.common.block;

import electrodynamics.prefab.block.GenericMachineBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import nuclearscience.common.tile.msreactor.TileMSRFuelPreProcessor;

public class BlockMSRFuelPreProcessor extends GenericMachineBlock {

	public BlockMSRFuelPreProcessor() {
		super(world -> new TileMSRFuelPreProcessor());
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader level, BlockPos pos, ISelectionContext context) {
		return Block.box(0, 0, 0, 16, 13, 16);
	}

}