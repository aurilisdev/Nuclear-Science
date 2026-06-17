package nuclearscience.common.tile.reactor.fusion;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.api.turbine.ISteamReceiver;
import nuclearscience.common.block.subtype.SubtypeNuclearMachine;
import nuclearscience.common.settings.NuclearConstants;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.registers.NuclearScienceBlocks;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.object.CachedTileOutput;

public class TilePlasma extends GenericTile {

    public final SingleProperty<Integer> ticksExisted = property(
	    new SingleProperty<>(PropertyTypes.INTEGER, "existed", 0).setNoUpdateClient());
    public final SingleProperty<Integer> spread = property(
	    new SingleProperty<>(PropertyTypes.INTEGER, "spread", 6).setNoUpdateClient());

    private CachedTileOutput output;

    public TilePlasma(BlockPos pos, BlockState state) {
	super(NuclearScienceTiles.TILE_PLASMA.get(), pos, state);
	addComponent(new ComponentTickable(this).tickServer(this::tickServer));
    }

    public void tickServer(ComponentTickable tickable) {

	ticksExisted.setValue(ticksExisted.getValue() + 1);

	if (ticksExisted.getValue() > 80) {
	    level.setBlockAndUpdate(worldPosition, Blocks.AIR.defaultBlockState());
	    return;
	}

	if (ticksExisted.getValue() == 1 && spread.getValue() > 0) {
	    for (Direction dir : Direction.values()) {
		BlockPos offset = worldPosition.relative(dir);
		BlockState state = level.getBlockState(offset);
		boolean didntExist = false;
		if (state.getBlock() != getBlockState().getBlock()) {
		    didntExist = true;
		    if (state.getDestroySpeed(level, offset) != -1
			    && !state.is(NuclearScienceTags.Blocks.FUSION_CONTAINMENT)
			    && state.getBlock() != NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE
				    .getValue(SubtypeNuclearMachine.fusionreactorcore)) {
			level.setBlockAndUpdate(offset, NuclearScienceBlocks.BLOCK_PLASMA.get().defaultBlockState());
		    }
		}
		BlockEntity tile = level.getBlockEntity(offset);
		if (tile instanceof TilePlasma plasma) {
		    if (plasma.ticksExisted.getValue() > 1 && plasma.spread.getValue() < spread.getValue()) {
			plasma.ticksExisted.setValue(ticksExisted.getValue() - 1);
		    }
		    if (didntExist) {
			plasma.spread.setValue(spread.getValue() - 1);
		    }
		}
	    }
	}
	if (ticksExisted.getValue() > 1
		&& level.getBlockState(getBlockPos().relative(Direction.UP))
			.is(NuclearScienceTags.Blocks.FUSION_CONTAINMENT)
		&& level.getFluidState(getBlockPos().relative(Direction.UP, 2)).is(FluidTags.WATER)) {
	    if (output == null) {
		output = new CachedTileOutput(level, getBlockPos().relative(Direction.UP, 3));
	    } else if (output.getSafe() instanceof ISteamReceiver) {
		ISteamReceiver turbine = output.getSafe();
		turbine.receiveSteam(Integer.MAX_VALUE,
			(int) (NuclearConstants.FUSIONREACTOR_MAXENERGYTARGET / (113.0 * 20.0)));
	    }
	}
    }

}