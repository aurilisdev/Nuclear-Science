package nuclearscience.common.block;

import electrodynamics.prefab.block.GenericEntityBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;
import nuclearscience.api.fusion.IElectromagnet;
import nuclearscience.common.tile.TileElectromagneticSwitch;

public class BlockElectromagneticSwitch extends GenericEntityBlock implements IElectromagnet {
	private static final VoxelShape shape = VoxelShapes.box(0, 0, 0, 1.0, 2.0 / 16.0, 1.0);

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return shape;
	}

	public BlockElectromagneticSwitch() {
		super(Properties.copy(Blocks.IRON_BLOCK).strength(3.5f, 20).requiresCorrectToolForDrops().noOcclusion().isRedstoneConductor((p1, p2, p3) -> false).harvestLevel(1).harvestTool(ToolType.PICKAXE));
	}

	@Override
	public TileEntity createTileEntity(BlockState arg0, IBlockReader arg1) {
		return new TileElectromagneticSwitch();
	}

}