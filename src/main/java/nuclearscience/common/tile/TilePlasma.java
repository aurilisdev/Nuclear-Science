package nuclearscience.common.tile;

import electrodynamics.api.tile.ITickableTileBase;
import electrodynamics.api.utilities.CachedTileOutput;
import electrodynamics.common.tile.generic.GenericTileBase;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import nuclearscience.DeferredRegisters;
import nuclearscience.api.fusion.IElectromagnet;
import nuclearscience.common.settings.Constants;

public class TilePlasma extends GenericTileBase implements ITickableTileBase {

    public int ticksExisted;
    public int spread = 6;

    public TilePlasma() {
	super(DeferredRegisters.TILE_PLASMA.get());
    }

    private CachedTileOutput output;

    @Override
    public void tickServer() {
	ticksExisted++;
	if (ticksExisted > 80) {
	    world.setBlockState(pos, Blocks.AIR.getDefaultState());
	}
	if (ticksExisted == 1 && spread > 0) {
	    for (Direction dir : Direction.values()) {
		BlockPos offset = pos.offset(dir);
		BlockState state = world.getBlockState(offset);
		boolean didntExist = false;
		if (state.getBlock() != getBlockState().getBlock()) {
		    didntExist = true;
		    if (state.getBlockHardness(world, offset) != -1 && !(state.getBlock() instanceof IElectromagnet)
			    && state.getBlock() != DeferredRegisters.blockFusionReactorCore) {
			world.setBlockState(offset, DeferredRegisters.blockPlasma.getDefaultState());
		    }
		}
		TileEntity tile = world.getTileEntity(offset);
		if (tile instanceof TilePlasma) {
		    TilePlasma plasma = (TilePlasma) tile;
		    if (plasma.ticksExisted > 1 && plasma.spread < spread) {
			plasma.ticksExisted = ticksExisted - 1;
		    }
		    if (didntExist) {
			plasma.spread = spread - 1;
		    }
		}
	    }
	}
	if (ticksExisted > 1) {
	    if (world.getBlockState(getPos().offset(Direction.UP)).getBlock() instanceof IElectromagnet) {
		if (world.getBlockState(getPos().offset(Direction.UP, 2)).getBlock() == Blocks.WATER) {
		    if (output == null) {
			output = new CachedTileOutput(world, getPos().offset(Direction.UP, 3));
		    } else {
			if (output.get() instanceof TileTurbine) {
			    TileTurbine turbine = output.get();
			    // Turbine count for plasma with diameter 15 without center block is 113
			    turbine.addSteam((int) (Constants.FUSIONREACTOR_MAXENERGYTARGET / (113.0 * 20.0)),
				    Integer.MAX_VALUE);
			}
		    }
		}
	    }
	}
    }
}
