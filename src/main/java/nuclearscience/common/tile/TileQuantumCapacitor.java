package nuclearscience.common.tile;

import java.util.UUID;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.ElectricityUtils;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import nuclearscience.common.inventory.container.ContainerQuantumCapacitor;
import nuclearscience.common.world.QuantumCapacitorData;
import nuclearscience.registers.NuclearScienceBlockTypes;

public class TileQuantumCapacitor extends GenericTile implements IEnergyStorage {
	public static final double DEFAULT_MAX_JOULES = Double.MAX_VALUE;
	public static final double DEFAULT_VOLTAGE = 1920.0;
	public double outputJoules = 359.0;
	public int frequency = 0;
	public UUID uuid = UUID.randomUUID();
	private CachedTileOutput outputCache;
	private CachedTileOutput outputCache2;

	public TileQuantumCapacitor(BlockPos pos, BlockState state) {
		super(NuclearScienceBlockTypes.TILE_QUANTUMCAPACITOR.get(), pos, state);
		addComponent(new ComponentTickable().tickServer(this::tickServer));
		addComponent(new ComponentPacketHandler().guiPacketReader(this::readGUIPacket).guiPacketWriter(this::writeGUIPacket));
		addComponent(new ComponentElectrodynamic(this).voltage(16 * ElectrodynamicsCapabilities.DEFAULT_VOLTAGE).output(Direction.DOWN).output(Direction.UP).input(Direction.WEST).input(Direction.EAST).input(Direction.SOUTH).input(Direction.NORTH).receivePower(this::receivePower).setJoules(this::setJoulesStored).getJoules(this::getJoulesStored));
		addComponent(new ComponentInventory(this));
		addComponent(new ComponentContainerProvider("container.quantumcapacitor").createMenu((id, player) -> new ContainerQuantumCapacitor(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));

	}

	public double getOutputJoules() {
		return outputJoules;
	}

	public void tickServer(ComponentTickable tickable) {
		if (outputCache == null) {
			outputCache = new CachedTileOutput(level, new BlockPos(worldPosition).relative(Direction.UP));
		}
		if (outputCache2 == null) {
			outputCache2 = new CachedTileOutput(level, new BlockPos(worldPosition).relative(Direction.DOWN));
		}
		if (tickable.getTicks() % 40 == 0) {
			outputCache.update(new BlockPos(worldPosition).relative(Direction.UP));
			outputCache2.update(new BlockPos(worldPosition).relative(Direction.DOWN));
		}
		double joules = getJoulesStored();
		if (joules > 0 && outputCache.valid()) {
			double sent = ElectricityUtils.receivePower(outputCache.getSafe(), Direction.DOWN, TransferPack.joulesVoltage(Math.min(joules, outputJoules), DEFAULT_VOLTAGE), false).getJoules();
			QuantumCapacitorData.get(level).setJoules(uuid, frequency, getJoulesStored() - sent);
		}
		joules = getJoulesStored();
		if (joules > 0 && outputCache2.valid()) {
			double sent = ElectricityUtils.receivePower(outputCache2.getSafe(), Direction.UP, TransferPack.joulesVoltage(Math.min(joules, outputJoules), DEFAULT_VOLTAGE), false).getJoules();
			QuantumCapacitorData.get(level).setJoules(uuid, frequency, getJoulesStored() - sent);
		}
		if (tickable.getTicks() % 50 == 0) {
			this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
		}
	}

	public double joulesClient = 0;

	public void writeGUIPacket(CompoundTag nbt) {
		nbt.putDouble("joulesClient", getJoulesStored());
		nbt.putInt("frequency", frequency);
		nbt.putUUID("uuid", uuid);
		nbt.putDouble("outputJoules", outputJoules);
	}

	public void readGUIPacket(CompoundTag nbt) {
		joulesClient = nbt.getDouble("joulesClient");
		frequency = nbt.getInt("frequency");
		uuid = nbt.getUUID("uuid");
		outputJoules = nbt.getDouble("outputJoules");
	}

	@Override
	public void saveAdditional(CompoundTag compound) {
		super.saveAdditional(compound);
		compound.putInt("frequency", frequency);
		compound.putDouble("outputJoules", outputJoules);
		compound.putUUID("uuid", uuid);
	}

	@Override
	public void load(CompoundTag compound) {
		super.load(compound);
		outputJoules = compound.getDouble("outputJoules");
		frequency = compound.getInt("frequency");
		if (compound.hasUUID("uuid")) {
			uuid = compound.getUUID("uuid");
		}
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction facing) {
		if (capability == ForgeCapabilities.ENERGY) {
			lastDir = facing;
			return (LazyOptional<T>) LazyOptional.of(() -> this);
		}
		return super.getCapability(capability, facing);
	}

	private Direction lastDir = null;

	public TransferPack receivePower(TransferPack transfer, boolean debug) {
		double joules = getJoulesStored();
		if (lastDir != Direction.UP && lastDir != Direction.DOWN) {
			double received = Math.min(Math.min(DEFAULT_MAX_JOULES, transfer.getJoules()), DEFAULT_MAX_JOULES - joules);
			if (!debug) {
				if (transfer.getVoltage() == DEFAULT_VOLTAGE) {
					joules += received;
				}
				QuantumCapacitorData.get(level).setJoules(uuid, frequency, joules);
				if (transfer.getVoltage() > DEFAULT_VOLTAGE) {
					level.setBlockAndUpdate(worldPosition, Blocks.AIR.defaultBlockState());
					level.explode(null, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), (float) Math.log10(10 + transfer.getVoltage() / DEFAULT_VOLTAGE), BlockInteraction.DESTROY);
					return TransferPack.EMPTY;
				}
			}
			return TransferPack.joulesVoltage(received, transfer.getVoltage());
		}
		return TransferPack.EMPTY;
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		int calVoltage = 120;
		TransferPack pack = receivePower(TransferPack.joulesVoltage(maxReceive, calVoltage), simulate);
		return (int) Math.min(Integer.MAX_VALUE, pack.getJoules());
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		int calVoltage = 120;
		TransferPack pack = this.<ComponentElectrodynamic>getComponent(ComponentType.Electrodynamic).extractPower(TransferPack.joulesVoltage(maxExtract, calVoltage), simulate);
		return (int) Math.min(Integer.MAX_VALUE, pack.getJoules());
	}

	@Override
	public int getEnergyStored() {
		return (int) Math.min(Integer.MAX_VALUE, getJoulesStored());
	}

	@Override
	public int getMaxEnergyStored() {
		return (int) Math.min(Integer.MAX_VALUE, DEFAULT_MAX_JOULES);
	}

	@Override
	public boolean canExtract() {
		return true;
	}

	@Override
	public boolean canReceive() {
		return true;
	}

	public void setJoulesStored(double joules) {
		QuantumCapacitorData data = QuantumCapacitorData.get(level);
		if (data != null) {
			data.setJoules(uuid, frequency, joules);
		}
	}

	public double getJoulesStored() {
		QuantumCapacitorData data = QuantumCapacitorData.get(level);
		return data == null ? 0 : data.getJoules(uuid, frequency);
	}

	public double getMaxJoulesStored() {
		return DEFAULT_MAX_JOULES;
	}

}
