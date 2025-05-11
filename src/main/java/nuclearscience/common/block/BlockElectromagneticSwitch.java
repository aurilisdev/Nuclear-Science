package nuclearscience.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class BlockElectromagneticSwitch extends Block {
	private static final VoxelShape SHAPE = VoxelShapes.box(0, 0, 0, 1.0, 2.0 / 16.0, 1.0);

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}

	public BlockElectromagneticSwitch() {
		super(Properties.copy(Blocks.IRON_BLOCK).strength(3.5f, 20).requiresCorrectToolForDrops().noOcclusion().isRedstoneConductor((p1, p2, p3) -> false));
	}

}
