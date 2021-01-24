package nuclearscience.common.tile;

import electrodynamics.api.tile.ITickableTileBase;
import electrodynamics.api.tile.electric.IElectricTile;
import electrodynamics.api.tile.electric.IPowerReceiver;
import electrodynamics.api.utilities.TransferPack;
import electrodynamics.common.tile.generic.GenericTileBase;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.world.Explosion.Mode;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.settings.Constants;

public class TileParticleInjector extends GenericTileBase implements ITickableTileBase, IPowerReceiver, IElectricTile {
	private double joules;

	public TileParticleInjector() {
		super(DeferredRegisters.TILE_PARTICLEINJECTOR.get());
	}

	@Override
	public void tickServer() {
		if (world.getWorldInfo().getDayTime() % 20 == 0) {
			sendUpdatePacket();
		}
	}

	@Override
	public TransferPack receivePower(TransferPack transfer, Direction dir, boolean debug) {
		if (!canConnectElectrically(dir)) {
			return TransferPack.EMPTY;
		}
		double received = Math.min(transfer.getJoules(), getMaxJoulesStored() - joules);
		if (!debug) {
			if ((int) transfer.getVoltage() == getVoltage()) {
				joules += received;
			}
			if (transfer.getVoltage() > getVoltage()) {
				world.setBlockState(pos, Blocks.AIR.getDefaultState());
				world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), (float) Math.log10(10 + transfer.getVoltage() / getVoltage()), Mode.DESTROY);
				return TransferPack.EMPTY;
			}
		}
		return TransferPack.joulesVoltage(received, transfer.getVoltage());
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		compound.putDouble(JOULES_STORED_NBT, joules);
		return super.write(compound);
	}

	@Override
	public void read(BlockState state, CompoundNBT compound) {
		super.read(state, compound);
		joules = compound.getDouble(JOULES_STORED_NBT);
	}

	@Override
	public CompoundNBT createUpdateTag() {
		CompoundNBT nbt = super.createUpdateTag();
		return nbt;
	}

	@Override
	public void handleUpdatePacket(CompoundNBT nbt) {
		super.handleUpdatePacket(nbt);
	}

	private int getVoltage() {
		return 960;
	}

	private double getMaxJoulesStored() {
		return Constants.PARTICLEINJECTOR_REQUIREDPOWER;
	}

	@Override
	public boolean canConnectElectrically(Direction direction) {
		return direction == getFacing().getOpposite();
	}

}
