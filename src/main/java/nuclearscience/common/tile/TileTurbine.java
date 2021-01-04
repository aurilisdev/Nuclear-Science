package nuclearscience.common.tile;

import electrodynamics.api.tile.ITickableTileBase;
import electrodynamics.api.tile.electric.IElectricTile;
import electrodynamics.api.tile.electric.IPowerProvider;
import electrodynamics.api.utilities.CachedTileOutput;
import electrodynamics.api.utilities.TransferPack;
import electrodynamics.common.tile.generic.GenericTileBase;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import nuclearscience.DeferredRegisters;

public class TileTurbine extends GenericTileBase implements ITickableTileBase, IPowerProvider, IElectricTile {

	protected CachedTileOutput output;

	public TileTurbine() {
		super(DeferredRegisters.TILE_TURBINE.get());
	}

	@Override
	public double getVoltage(Direction arg0) {
		return 120; // TODO: MAKE THIS DEPEND ON THE TURBINE ROTATION AND SCALE ACCORDINGLY. 120 IN LOW FISSION, 240 IN HIGH FISSION AND 480 IN FUSION OR MAYBE 960.
	}

	@Override
	public void tickServer() {
		if (output == null) {
			output = new CachedTileOutput(world, new BlockPos(pos).offset(Direction.UP));
		}
//		TransferPack transfer = some transfer wtf do i write idk yet. TODO: FINISH THIS?
//		if (output.get() instanceof IPowerReceiver) {
//			output.<IPowerReceiver>get().receivePower(transfer, getFacing(), false);
//		}
	}

	@Override
	public TransferPack extractPower(TransferPack transfer, Direction from, boolean debug) {
		return TransferPack.EMPTY;
	}

	@Override
	public boolean canConnectElectrically(Direction direction) {
		return direction == Direction.UP || direction == Direction.DOWN;
	}

}
