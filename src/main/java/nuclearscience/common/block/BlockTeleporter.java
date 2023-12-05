package nuclearscience.common.block;

import electrodynamics.common.block.BlockMachine;
import electrodynamics.prefab.block.GenericMachineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import nuclearscience.common.tile.TileTeleporter;

public class BlockTeleporter extends GenericMachineBlock {
	public BlockTeleporter() {
		super(TileTeleporter::new);
		registerDefaultState(stateDefinition.any().setValue(BlockMachine.ON, false));
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return super.getStateForPlacement(context).setValue(BlockMachine.ON, false);
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(BlockMachine.ON);
	}

	@Override
	public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {

		if (state.hasProperty(BlockMachine.ON) && state.getValue(BlockMachine.ON)) {
			return 15;
		}

		return super.getLightEmission(state, world, pos);
	}

}