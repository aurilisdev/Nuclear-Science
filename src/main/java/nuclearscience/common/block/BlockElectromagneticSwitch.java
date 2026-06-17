package nuclearscience.common.block;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockElectromagneticSwitch extends Block {
    private static final VoxelShape SHAPE = Shapes.box(0, 0, 0, 1.0, 2.0 / 16.0, 1.0);

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
	return SHAPE;
    }

    public BlockElectromagneticSwitch() {
	super(Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(3.5f, 20).requiresCorrectToolForDrops().noOcclusion()
		.isRedstoneConductor((p1, p2, p3) -> false));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
	return null;
    }
}
