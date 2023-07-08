package nuclearscience.common.tile;

import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.api.fusion.IElectromagnet;
import nuclearscience.api.turbine.ISteamReceiver;
import nuclearscience.common.settings.Constants;
import nuclearscience.registers.NuclearScienceBlockTypes;
import nuclearscience.registers.NuclearScienceBlocks;

public class TilePlasma extends GenericTile {
	public int ticksExisted;
	public int spread = 6;
	private CachedTileOutput output;

	public TilePlasma(BlockPos pos, BlockState state) {
		super(NuclearScienceBlockTypes.TILE_PLASMA.get(), pos, state);
		addComponent(new ComponentTickable(this).tickServer(this::tickServer));
	}

	protected void tickServer(ComponentTickable tickable) {
		ticksExisted++;
		if (ticksExisted > 80) {
			level.setBlockAndUpdate(worldPosition, Blocks.AIR.defaultBlockState());
		}
		if (ticksExisted == 1 && spread > 0) {
			for (Direction dir : Direction.values()) {
				BlockPos offset = worldPosition.relative(dir);
				BlockState state = level.getBlockState(offset);
				boolean didntExist = false;
				if (state.getBlock() != getBlockState().getBlock()) {
					didntExist = true;
					if (state.getDestroySpeed(level, offset) != -1 && !(state.getBlock() instanceof IElectromagnet) && state.getBlock() != NuclearScienceBlocks.blockFusionReactorCore) {
						level.setBlockAndUpdate(offset, NuclearScienceBlocks.blockPlasma.defaultBlockState());
					}
				}
				BlockEntity tile = level.getBlockEntity(offset);
				if (tile instanceof TilePlasma plasma) {
					if (plasma.ticksExisted > 1 && plasma.spread < spread) {
						plasma.ticksExisted = ticksExisted - 1;
					}
					if (didntExist) {
						plasma.spread = spread - 1;
					}
				}
			}
		}
		if (ticksExisted > 1 && level.getBlockState(getBlockPos().relative(Direction.UP)).getBlock() instanceof IElectromagnet && level.getBlockState(getBlockPos().relative(Direction.UP, 2)).getBlock() == Blocks.WATER) {
			if (output == null) {
				output = new CachedTileOutput(level, getBlockPos().relative(Direction.UP, 3));
			} else if (output.getSafe() instanceof ISteamReceiver) {
				ISteamReceiver turbine = output.getSafe();
				turbine.receiveSteam(Constants.FUSIONREACTOR_MAXENERGYTARGET / (113.0 * 20.0), Double.MAX_VALUE);
			}
		}
	}
}