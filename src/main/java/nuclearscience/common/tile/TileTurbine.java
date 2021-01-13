package nuclearscience.common.tile;

import electrodynamics.api.tile.ITickableTileBase;
import electrodynamics.api.tile.electric.IElectricTile;
import electrodynamics.api.tile.electric.IPowerProvider;
import electrodynamics.api.tile.electric.IPowerReceiver;
import electrodynamics.api.utilities.CachedTileOutput;
import electrodynamics.api.utilities.TransferPack;
import electrodynamics.common.tile.generic.GenericTileBase;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.block.BlockTurbine;
import nuclearscience.common.settings.Constants;

public class TileTurbine extends GenericTileBase implements ITickableTileBase, IPowerProvider, IElectricTile {

	public static final int MAX_STEAM = 3000000;
	protected CachedTileOutput output;
	protected int currentVoltage = 0;
	protected int steam;
	protected int wait = 30;
	public int spinSpeed = 0;

	protected boolean hasCore;
	protected boolean isCore;
	protected BlockPos coreLocation = BlockPos.ZERO;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return isCore ? super.getRenderBoundingBox().grow(1, 0, 1) : super.getRenderBoundingBox();
	}

	public TileTurbine() {
		super(DeferredRegisters.TILE_TURBINE.get());
	}

	public void constructStructure() {
		boolean canConstruct = true;
		int radius = 1;
		for (int i = -radius; i <= radius; i++) {
			if (!canConstruct) {
				break;
			}
			for (int j = -radius; j <= radius; j++) {
				if (i != 0 || j != 0) {
					TileEntity tile = world.getTileEntity(new BlockPos(pos.getX() + i, pos.getY(), pos.getZ() + j));
					if (!(tile instanceof TileTurbine)) {
						canConstruct = false;
						break;
					}
					TileTurbine turbine = (TileTurbine) tile;
					if (turbine.hasCore()) {
						canConstruct = false;
						break;
					}
				}
			}
		}
		if (canConstruct) {
			isCore = true;
			for (int i = -radius; i <= radius; i++) {
				for (int j = -radius; j <= radius; j++) {
					BlockPos offset = new BlockPos(pos.getX() + i, pos.getY(), pos.getZ() + j);
					((TileTurbine) world.getTileEntity(offset)).addToStructure(this);
					BlockState state = world.getBlockState(offset);
					world.setBlockState(offset, state.with(BlockTurbine.RENDER, false));
				}
			}
		}
	}

	public void deconstructStructure() {
		if (isCore) {
			int radius = 1;
			for (int i = -radius; i <= radius; i++) {
				for (int j = -radius; j <= radius; j++) {
					if (i != 0 || j != 0) {
						BlockPos offset = new BlockPos(pos.getX() + i, pos.getY(), pos.getZ() + j);
						TileEntity tile = world.getTileEntity(offset);
						if (tile instanceof TileTurbine) {
							TileTurbine turbine = (TileTurbine) tile;
							turbine.hasCore = false;
							turbine.coreLocation = new BlockPos(0, 0, 0);
							BlockState state = world.getBlockState(offset);
							if (state.hasProperty(BlockTurbine.RENDER)) {
								world.setBlockState(offset, state.with(BlockTurbine.RENDER, true));
							}
						}
					}
				}
			}
			isCore = false;
			hasCore = false;
			coreLocation = new BlockPos(0, 0, 0);
			BlockState state = getBlockState();
			if (state.hasProperty(BlockTurbine.RENDER)) {
				world.setBlockState(pos, getBlockState().with(BlockTurbine.RENDER, true));
			}
		} else if (hasCore) {
			TileTurbine core = (TileTurbine) world.getTileEntity(coreLocation);
			if (core != null) {
				core.deconstructStructure();
			}
		}
		sendUpdatePacket();

	}

	protected void addToStructure(TileTurbine core) {
		coreLocation = core.pos;
		hasCore = true;
		sendUpdatePacket();
	}

	public boolean isCore() {
		return isCore;
	}

	public boolean hasCore() {
		return hasCore;
	}

	@Override
	public double getVoltage(Direction arg0) {
		return currentVoltage;
	}

	public void addSteam(int steam) {
		this.steam = Math.min(MAX_STEAM * (isCore ? 9 : 1), this.steam + steam);
		double temp = 1 / 3.0 * steam + 100;
		if (temp < 4300) {
			currentVoltage = 120;
		} else if (temp < 6000) {
			currentVoltage = 240;
		} else {
			currentVoltage = 480;
		}
		if (!isCore && hasCore) {
			TileEntity core = world.getTileEntity(coreLocation);
			if (core instanceof TileTurbine && ((TileTurbine) core).isCore()) {
				TileTurbine turbine = (TileTurbine) core;
				turbine.addSteam(this.steam);
				this.steam = 0;
			}
		}
	}

	@Override
	public void tickServer() {
		if (world.getWorldInfo().getDayTime() % 30 == 0) {
			sendUpdatePacket();
			spinSpeed = (int) (getVoltage(Direction.UP) / 120);
		}
		if (hasCore && !isCore) {
			currentVoltage = 0;
			return;
		}
		if (output == null) {
			output = new CachedTileOutput(world, new BlockPos(pos).offset(Direction.UP));
		}
		if (steam > 0) {
			wait = 30;
			TransferPack transfer = TransferPack.joulesVoltage(Constants.STEAMTOJOULESPERTICKRATIO * steam * (hasCore ? 1.111 : 1), getVoltage(Direction.UP));
			if (output.get() instanceof IPowerReceiver) {
				output.<IPowerReceiver>get().receivePower(transfer, Direction.UP, false);
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
		tag.putBoolean("hasCore", hasCore);
		tag.putBoolean("isCore", isCore);
		return tag;
	}

	@Override
	public void handleUpdatePacket(CompoundNBT nbt) {
		spinSpeed = nbt.getInt("spinSpeed");
		hasCore = nbt.getBoolean("hasCore");
		isCore = nbt.getBoolean("isCore");
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		compound.putBoolean("hasCore", hasCore);
		compound.putBoolean("isCore", isCore);
		compound.putInt("coreX", coreLocation.getX());
		compound.putInt("coreY", coreLocation.getY());
		compound.putInt("coreZ", coreLocation.getZ());
		return super.write(compound);
	}

	@Override
	public void read(BlockState state, CompoundNBT compound) {
		super.read(state, compound);
		hasCore = compound.getBoolean("hasCore");
		isCore = compound.getBoolean("isCore");
		coreLocation = new BlockPos(compound.getInt("coreX"), compound.getInt("coreY"), compound.getInt("coreZ"));
	}

	@Override
	public TransferPack extractPower(TransferPack transfer, Direction from, boolean debug) {
		return TransferPack.EMPTY;
	}

	@Override
	public boolean canConnectElectrically(Direction direction) {
		return direction == Direction.UP && (!hasCore || isCore);
	}

}
