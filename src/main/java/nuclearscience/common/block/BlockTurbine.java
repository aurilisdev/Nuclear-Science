package nuclearscience.common.block;

import electrodynamics.prefab.block.GenericEntityBlockWaterloggable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;
import nuclearscience.common.tile.TileTurbine;

public class BlockTurbine extends GenericEntityBlockWaterloggable {

	private static final VoxelShape SHAPE = VoxelShapes.or(Block.box(1.25, 2.5, 6, 14.75, 13.5, 10), Block.box(6, 2.5, 1.25, 10, 13.5, 14.75), Block.box(2, 2.5, 4, 14, 13.5, 12), Block.box(4, 2.5, 2, 12, 13.5, 14), Block.box(3, 2.5, 3, 13, 13.5, 13), Block.box(4.65, 0.75, 4.65, 11.35, 2.5, 11.35), Block.box(4.3, 13.5, 4.3, 11.7, 15, 11.7), Block.box(5.7, 15, 5.7, 10.3, 16, 10.3));

	public static final BooleanProperty RENDER = BooleanProperty.create("render");

	public BlockTurbine() {
		super(Properties.copy(Blocks.IRON_BLOCK).strength(3.5F).sound(SoundType.METAL).requiresCorrectToolForDrops().noOcclusion().harvestLevel(1).harvestTool(ToolType.PICKAXE));
		registerDefaultState(stateDefinition.any().setValue(RENDER, true));
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new TileTurbine();
	}

	@Override
	public void onRotate(ItemStack stack, BlockPos pos, PlayerEntity player) {
		super.onRotate(stack, pos, player);
		if (player.level.isClientSide()) {
			return;
		}
		TileTurbine turbine = (TileTurbine) player.level.getBlockEntity(pos);
		if (turbine != null) {
			if (turbine.isCore.get()) {
				turbine.deconstructStructure();
			} else {
				turbine.constructStructure();
			}
		}
	}

	@Override
	public BlockRenderType getRenderShape(BlockState state) {
		if (!state.getValue(RENDER)) {
			return BlockRenderType.INVISIBLE;
		}
		return super.getRenderShape(state);
	}

	@Override
	public float getShadeBrightness(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return 1;
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return super.getStateForPlacement(context).setValue(RENDER, true);
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(RENDER);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader level, BlockPos pos, ISelectionContext context) {
		if (state.getValue(RENDER)) {
			return SHAPE;
		}

		return VoxelShapes.block();
	}
}