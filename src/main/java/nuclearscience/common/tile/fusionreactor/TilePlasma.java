package nuclearscience.common.tile.fusionreactor;

import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import nuclearscience.api.fusion.IElectromagnet;
import nuclearscience.api.turbine.ISteamReceiver;
import nuclearscience.common.settings.Constants;
import nuclearscience.registers.NuclearScienceBlockTypes;
import nuclearscience.registers.NuclearScienceBlocks;

public class TilePlasma extends GenericTile {

	public final Property<Integer> ticksExisted = property(new Property<>(PropertyType.Integer, "existed", 0).setNoUpdateClient());
	public final Property<Integer> spread = property(new Property<>(PropertyType.Integer, "spread", 6).setNoUpdateClient());

	private CachedTileOutput output;

	public TilePlasma() {
		super(NuclearScienceBlockTypes.TILE_PLASMA.get());
		addComponent(new ComponentTickable(this).tickServer(this::tickServer));
	}

	public void tickServer(ComponentTickable tickable) {

		ticksExisted.set(ticksExisted.get() + 1);

		if (ticksExisted.get() > 80) {
			level.setBlockAndUpdate(worldPosition, Blocks.AIR.defaultBlockState());
			return;
		}

		if (ticksExisted.get() == 1 && spread.get() > 0) {
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
				TileEntity tile = level.getBlockEntity(offset);
				if (tile instanceof TilePlasma) {
					TilePlasma plasma = (TilePlasma) tile;
					if (plasma.ticksExisted.get() > 1 && plasma.spread.get() < spread.get()) {
						plasma.ticksExisted.set(ticksExisted.get() - 1);
					}
					if (didntExist) {
						plasma.spread.set(spread.get() - 1);
					}
				}
			}
		}
		if (ticksExisted.get() > 1 && level.getBlockState(getBlockPos().relative(Direction.UP)).getBlock() instanceof IElectromagnet && level.getBlockState(getBlockPos().relative(Direction.UP, 2)).getBlock() == Blocks.WATER) {
			if (output == null) {
				output = new CachedTileOutput(level, getBlockPos().relative(Direction.UP, 3));
			} else if (output.getSafe() instanceof ISteamReceiver) {
				ISteamReceiver turbine = output.getSafe();
				turbine.receiveSteam(Double.MAX_VALUE, Constants.FUSIONREACTOR_MAXENERGYTARGET / (113.0 * 20.0));
			}
		}
	}

	@Override
	public ActionResultType use(PlayerEntity arg0, Hand arg1, BlockRayTraceResult arg2) {
		return ActionResultType.PASS;
	}

}