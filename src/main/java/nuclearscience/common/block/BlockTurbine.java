package nuclearscience.common.block;

import electrodynamics.api.IWrenchItem;
import electrodynamics.prefab.block.GenericEntityBlockWaterloggable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import nuclearscience.common.tile.TileTurbine;

public class BlockTurbine extends GenericEntityBlockWaterloggable {
	public static final BooleanProperty RENDER = BooleanProperty.create("render");

	public BlockTurbine() {
		super(Properties.of(Material.METAL).strength(3.5F).sound(SoundType.METAL).requiresCorrectToolForDrops().noOcclusion());
		registerDefaultState(stateDefinition.any().setValue(RENDER, true));
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TileTurbine(pos, state);
	}

	@Override
	public void onRotate(ItemStack stack, BlockPos pos, Player player) {
		super.onRotate(stack, pos, player);
		if(player.level.isClientSide()) {
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
	public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock() && !worldIn.isClientSide) {
			TileTurbine turbine = (TileTurbine) worldIn.getBlockEntity(pos);
			if (turbine != null) {
				turbine.deconstructStructure();
			}
		}
		super.onRemove(state, worldIn, pos, newState, isMoving);
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		if (!state.getValue(RENDER)) {
			return RenderShape.INVISIBLE;
		}
		return super.getRenderShape(state);
	}

	@Override
	public float getShadeBrightness(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return 1;
	}

	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
		if (worldIn.isClientSide) {
			return InteractionResult.SUCCESS;
		} 
		
		if(!(player.getItemInHand(handIn).getItem() instanceof IWrenchItem)) {
			return InteractionResult.CONSUME;
		}
		return InteractionResult.FAIL;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return super.getStateForPlacement(context).setValue(RENDER, true);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(RENDER);
	}
}
