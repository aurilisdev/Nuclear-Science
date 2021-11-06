package nuclearscience.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import nuclearscience.common.tile.TileElectromagneticSwitch;

public class BlockElectromagneticSwitch extends BlockElectromagnet {
    private static final VoxelShape shape = Shapes.box(0, 0, 0, 16.0 / 16.0, 2.0 / 16.0, 16.0 / 16.0);

    public BlockElectromagneticSwitch() {
	super(false);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
	return shape;
    }

    @Override
    public BlockEntity createBlockEntity(BlockState state, BlockGetter world) {
    	return new TileElectromagneticSwitch();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
	return true;
    }
}
