package nuclearscience.common.block;

import electrodynamics.prefab.block.GenericMachineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import nuclearscience.common.tile.fusionreactor.TileFusionReactorCore;

public class BlockFusionReactorCore extends GenericMachineBlock {

	public BlockFusionReactorCore() {
		super(TileFusionReactorCore::new);
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		VoxelShape reactor = Block.box(6, 0, 6, 10, 16, 10);

		reactor = Shapes.or(reactor, Block.box(5, 1, 5, 11, 15, 11));

		reactor = Shapes.or(reactor, Block.box(4, 2, 4, 12, 14, 12));

		reactor = Shapes.or(reactor, Block.box(3, 4, 3, 13, 12, 13));

		reactor = Shapes.or(reactor, Block.box(2, 6, 2, 14, 10, 14));

		return reactor;
	}

}
