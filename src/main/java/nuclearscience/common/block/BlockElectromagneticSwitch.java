package nuclearscience.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import nuclearscience.common.tile.TileElectromagneticSwitch;

public class BlockElectromagneticSwitch extends BlockElectromagnet {
	private static final VoxelShape shape = VoxelShapes.create(0, 0, 0, 16.0 / 16.0, 2.0 / 16.0, 16.0 / 16.0);

	public BlockElectromagneticSwitch() {
		super(false);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return shape;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new TileElectromagneticSwitch();
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
}
