package nuclearscience.common.tile;

import electrodynamics.api.tile.ITickableTileBase;
import electrodynamics.api.tile.electric.IElectricTile;
import electrodynamics.api.tile.electric.IPowerProvider;
import electrodynamics.api.tile.electric.IPowerReceiver;
import electrodynamics.api.utilities.CachedTileOutput;
import electrodynamics.api.utilities.TransferPack;
import electrodynamics.common.tile.generic.GenericTileBase;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.settings.Constants;

public class TileTurbine extends GenericTileBase implements ITickableTileBase, IPowerProvider, IElectricTile {

	public static final int MAX_STEAM = 3000000;
	protected CachedTileOutput output;
	protected int currentVoltage = 0;
	protected int steam;
	protected int wait = 30;
	public int spinSpeed = 0;

	public TileTurbine() {
		super(DeferredRegisters.TILE_TURBINE.get());
	}

	@Override
	public double getVoltage(Direction arg0) {
		return currentVoltage;
	}

	public void addSteam(int steam) {
		this.steam = Math.min(MAX_STEAM, this.steam + steam);
		double temp = 0.03846 * steam + 100;
		if (temp < 4300) {
			currentVoltage = 120;
		} else if (temp < 7000) {
			currentVoltage = 240;
		} else {
			currentVoltage = 480;
		}
	}

	@Override
	public void tickServer() {
		if (world.getWorldInfo().getDayTime() % 30 == 0) {
			sendUpdatePacket();
			spinSpeed = (int) (getVoltage(Direction.UP) / 120);
		}
		if (output == null) {
			output = new CachedTileOutput(world, new BlockPos(pos).offset(Direction.UP));
		}
		if (steam > 0) {
			wait = 30;
			TransferPack transfer = TransferPack.joulesVoltage(Constants.STEAMTOJOULESPERTICKRATIO * steam, getVoltage(Direction.UP));
			if (output.get() instanceof IPowerReceiver) {
				output.<IPowerReceiver>get().receivePower(transfer, getFacing(), false);
			}
			steam = Math.max(steam - Math.max(75, steam), 0);
		} else {
			if (wait <= 0) {
				currentVoltage = 0;
				wait = 30;
			}
			wait--;
		}

	}

	@Override
	public CompoundNBT createUpdateTag() {
		CompoundNBT tag = super.createUpdateTag();
		tag.putInt("spinSpeed", spinSpeed);
		return tag;
	}

	@Override
	public void handleUpdatePacket(CompoundNBT nbt) {
		spinSpeed = nbt.getInt("spinSpeed");
	}

	@Override
	public TransferPack extractPower(TransferPack transfer, Direction from, boolean debug) {
		return TransferPack.EMPTY;
	}

	@Override
	public boolean canConnectElectrically(Direction direction) {
		return direction == Direction.UP;
	}

}
