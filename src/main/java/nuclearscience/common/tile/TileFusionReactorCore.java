package nuclearscience.common.tile;

import electrodynamics.api.tile.ITickableTileBase;
import electrodynamics.api.tile.electric.IElectricTile;
import electrodynamics.api.tile.electric.IPowerReceiver;
import electrodynamics.api.utilities.TransferPack;
import electrodynamics.common.tile.generic.GenericTileBase;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion.Mode;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.settings.Constants;

public class TileFusionReactorCore extends GenericTileBase implements ITickableTileBase, IPowerReceiver, IElectricTile {
	private double joules;
	private int timeLeft = 0;
	public int deuterium;
	public int tritium;

	public TileFusionReactorCore() {
		super(DeferredRegisters.TILE_FUSIONREACTORCORE.get());
	}

	@Override
	public void tickServer() {
		if (world.getWorldInfo().getDayTime() % 20 == 0) {
			sendUpdatePacket();
		}
		if (tritium > 0 && deuterium > 0 && timeLeft <= 0 && joules > Constants.FUSIONREACTOR_USAGE_PER_TICK) {
			deuterium -= 1;
			tritium -= 1;
			timeLeft = 15 * 20;
		}
		if (timeLeft > 0 && joules > Constants.FUSIONREACTOR_USAGE_PER_TICK) {
			for (Direction dir : Direction.values()) {
				if (dir != Direction.UP && dir != Direction.DOWN) {
					BlockPos offset = pos.offset(dir);
					BlockState state = world.getBlockState(offset);
					if (state.getBlock() == DeferredRegisters.blockPlasma) {
						TileEntity tile = world.getTileEntity(offset);
						if (tile instanceof TilePlasma && ((TilePlasma) tile).ticksExisted > 30) {
							((TilePlasma) tile).ticksExisted = 0;
						}
					} else if (state.getBlock() == Blocks.AIR) {
						world.setBlockState(offset, DeferredRegisters.blockPlasma.getDefaultState());
					}
				}
			}
			joules -= Constants.FUSIONREACTOR_USAGE_PER_TICK;
		}
		timeLeft--;
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
		compound.putInt("deuterium", deuterium);
		compound.putInt("tritium", tritium);
		compound.putDouble(JOULES_STORED_NBT, joules);
		return super.write(compound);
	}

	@Override
	public void read(BlockState state, CompoundNBT compound) {
		super.read(state, compound);
		joules = compound.getDouble(JOULES_STORED_NBT);
		deuterium = compound.getInt("deuterium");
		tritium = compound.getInt("tritium");
	}

	@Override
	public CompoundNBT createUpdateTag() {
		CompoundNBT nbt = super.createUpdateTag();
		nbt.putInt("deuterium", deuterium);
		nbt.putInt("tritium", tritium);
		return nbt;
	}

	@Override
	public void handleUpdatePacket(CompoundNBT nbt) {
		super.handleUpdatePacket(nbt);
		deuterium = nbt.getInt("deuterium");
		tritium = nbt.getInt("tritium");
	}

	private int getVoltage() {
		return 480;
	}

	private double getMaxJoulesStored() {
		return Constants.FUSIONREACTOR_USAGE_PER_TICK * 20.0;
	}

	@Override
	public boolean canConnectElectrically(Direction direction) {
		return direction == Direction.UP || direction == Direction.DOWN;
	}

}
