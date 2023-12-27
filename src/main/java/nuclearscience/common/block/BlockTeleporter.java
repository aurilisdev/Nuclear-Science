package nuclearscience.common.block;

import electrodynamics.common.block.BlockMachine;
import electrodynamics.prefab.block.GenericMachineBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import nuclearscience.common.tile.TileTeleporter;

public class BlockTeleporter extends GenericMachineBlock {
	public BlockTeleporter() {
		super(world -> new TileTeleporter());
		registerDefaultState(stateDefinition.any().setValue(BlockMachine.ON, false));
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return super.getStateForPlacement(context).setValue(BlockMachine.ON, false);
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(BlockMachine.ON);
	}

	@Override
	public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {

		if (state.hasProperty(BlockMachine.ON) && state.getValue(BlockMachine.ON)) {
			return 15;
		}

		return super.getLightValue(state, world, pos);
	}

}