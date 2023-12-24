package nuclearscience.common.block;

import electrodynamics.prefab.block.GenericMachineBlock;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import nuclearscience.common.tile.fissionreactor.TileFissionReactorCore;

public class BlockFissionReactorCore extends GenericMachineBlock {
	public BlockFissionReactorCore() {
		super(world -> new TileFissionReactorCore());
	}

	@Override
	public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() == newState.getBlock()) {
			worldIn.setBlocksDirty(pos, state, newState);
		} else {
			super.onRemove(state, worldIn, pos, newState, isMoving);
		}
	}

	@Override
	public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
		TileEntity core = world.getBlockEntity(pos);
		if (core instanceof TileFissionReactorCore) {
			return (int) Math.max(0, Math.min(((TileFissionReactorCore)core).temperature.get() / TileFissionReactorCore.MELTDOWN_TEMPERATURE_ACTUAL * 15, 15));
		}
		return 0;
	}

	@Override
	public void onBlockExploded(BlockState state, World world, BlockPos pos, Explosion explosion) {
		TileEntity core = world.getBlockEntity(pos);
		if (core instanceof TileFissionReactorCore) {
			((TileFissionReactorCore)core).meltdown();
		}
		super.onBlockExploded(state, world, pos, explosion);
	}

}