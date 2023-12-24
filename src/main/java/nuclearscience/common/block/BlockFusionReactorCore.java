package nuclearscience.common.block;

import electrodynamics.prefab.block.GenericMachineBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import nuclearscience.common.tile.fusionreactor.TileFusionReactorCore;

public class BlockFusionReactorCore extends GenericMachineBlock {

	public BlockFusionReactorCore() {
		super(world -> new TileFusionReactorCore());
	}

	@Override
	public BlockRenderType getRenderShape(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		VoxelShape reactor = Block.box(6, 0, 6, 10, 16, 10);

		reactor = VoxelShapes.or(reactor, Block.box(5, 1, 5, 11, 15, 11));

		reactor = VoxelShapes.or(reactor, Block.box(4, 2, 4, 12, 14, 12));

		reactor = VoxelShapes.or(reactor, Block.box(3, 4, 3, 13, 12, 13));

		reactor = VoxelShapes.or(reactor, Block.box(2, 6, 2, 14, 10, 14));

		return reactor;
	}

}