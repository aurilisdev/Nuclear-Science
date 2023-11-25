package nuclearscience.common.block;

import electrodynamics.prefab.block.GenericMachineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.common.tile.fissionreactor.TileFissionReactorCore;

public class BlockFissionReactorCore extends GenericMachineBlock {
	public BlockFissionReactorCore() {
		super(TileFissionReactorCore::new);
	}

	@Override
	public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() == newState.getBlock()) {
			worldIn.setBlocksDirty(pos, state, newState);
		} else {
			super.onRemove(state, worldIn, pos, newState, isMoving);
		}
	}

	@Override
	public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
		BlockEntity core = world.getBlockEntity(pos);
		if (core instanceof TileFissionReactorCore rc) {
			return (int) Math.max(0, Math.min(rc.temperature.get() / TileFissionReactorCore.MELTDOWN_TEMPERATURE_ACTUAL * 15, 15));
		}
		return 0;
	}

	@Override
	public void onBlockExploded(BlockState state, Level world, BlockPos pos, Explosion explosion) {
		BlockEntity core = world.getBlockEntity(pos);
		if (core instanceof TileFissionReactorCore rc) {
			rc.meltdown();
		}
		super.onBlockExploded(state, world, pos, explosion);
	}

}
