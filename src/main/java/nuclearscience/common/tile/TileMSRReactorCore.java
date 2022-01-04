package nuclearscience.common.tile;

import java.util.ArrayList;

import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import electrodynamics.prefab.utilities.object.Location;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.DeferredRegisters;
import nuclearscience.api.network.moltensalt.IMoltenSaltPipe;
import nuclearscience.api.radiation.RadiationSystem;
import nuclearscience.common.inventory.container.ContainerMSRReactorCore;
import nuclearscience.common.network.MoltenSaltNetwork;

//TODO: Rename from bloody MSRReactor everywhere to MSReactor as MSRReactor now means Molten-Salt-ReactorReactor
public class TileMSRReactorCore extends GenericTile {
	public static final int MELTDOWN_TEMPERATURE = 900;
	public static final double FUEL_CAPACITY = 1000;
	public static final double FUEL_USAGE_RATE = 0.01;
	public double temperature = TileReactorCore.AIR_TEMPERATURE;
	public int ticksOverheating = 0;
	public double currentFuel = 0;
	private CachedTileOutput outputCache;
	public CachedTileOutput plugCache;

	public TileMSRReactorCore(BlockPos pos, BlockState state) {
		super(DeferredRegisters.TILE_MSRREACTORCORE.get(), pos, state);
		addComponent(new ComponentDirection());
		addComponent(new ComponentTickable().tickServer(this::tickServer).tickClient(this::tickClient));
		addComponent(new ComponentPacketHandler().customPacketReader(this::readCustomPacket).customPacketWriter(this::writeCustomPacket)
				.guiPacketReader(this::readCustomPacket).guiPacketWriter(this::writeCustomPacket));
		addComponent(new ComponentContainerProvider("container.msrreactorcore")
				.createMenu((id, player) -> new ContainerMSRReactorCore(id, player, null, getCoordsArray())));
	}

	protected void writeCustomPacket(CompoundTag tag) {
		tag.putDouble("temperature", temperature);
		tag.putDouble("currentFuel", currentFuel);
	}

	protected void readCustomPacket(CompoundTag nbt) {
		temperature = nbt.getDouble("temperature");
		currentFuel = nbt.getDouble("currentFuel");
	}

	@Override
	public void saveAdditional(CompoundTag compound) {
		writeCustomPacket(compound);
		super.saveAdditional(compound);
	}

	@Override
	public void load(CompoundTag compound) {
		readCustomPacket(compound);
		super.load(compound);
	}

	protected void tickServer(ComponentTickable tick) {
		if (outputCache == null) {
			outputCache = new CachedTileOutput(level, new BlockPos(worldPosition).relative(Direction.UP));
		}
		if (plugCache == null) {
			plugCache = new CachedTileOutput(level, new BlockPos(worldPosition).relative(Direction.DOWN));
		}
		if (tick.getTicks() % 40 == 0) {
			outputCache.update();
			plugCache.update();
		}
		double change = (temperature - TileReactorCore.AIR_TEMPERATURE) / 3000.0 + (temperature - TileReactorCore.AIR_TEMPERATURE) / 5000.0;
		if (change != 0) {
			temperature -= change < 0.001 && change > 0 ? 0.001 : change > -0.001 && change < 0 ? -0.001 : change;
		}
		if (plugCache.valid() && plugCache.getSafe()instanceof TileFreezePlug freeze && freeze.isFrozen()) {
			if (currentFuel > FUEL_USAGE_RATE) {
				int insertion = 0;
				for (Direction dir : Direction.values()) {
					if (dir != Direction.UP && dir != Direction.DOWN) {
						BlockEntity tile = level.getBlockEntity(getBlockPos().relative(dir));
						if (tile instanceof TileControlRodAssembly cr) {
							TileControlRodAssembly control = cr;
							if (control.direction == dir.getOpposite()) {
								insertion += control.insertion;
							}
						}
					}
				}
				if (level.getLevelData().getGameTime() % 10 == 0) {
					double totstrength = temperature * Math.pow(3, Math.pow(temperature / MELTDOWN_TEMPERATURE, 9));
					double range = Math.sqrt(totstrength) / (5 * Math.sqrt(2)) * 2;
					RadiationSystem.emitRadiationFromLocation(level, new Location(worldPosition), range, totstrength);
				}
				double insertDecimal = (100 - insertion) / 100.0;
				currentFuel -= Math.min(currentFuel,
						FUEL_USAGE_RATE * insertDecimal * Math.pow(2, Math.pow(temperature / (MELTDOWN_TEMPERATURE - 100), 4)));
				temperature += (MELTDOWN_TEMPERATURE * insertDecimal * (1.2 + level.random.nextDouble() / 5.0) - temperature) / 200;
				if (outputCache.valid() && outputCache.getSafe() instanceof IMoltenSaltPipe) {
					MoltenSaltNetwork net = (MoltenSaltNetwork) outputCache.<IMoltenSaltPipe>getSafe().getNetwork();
					net.emit(temperature, new ArrayList<>(), false);
				}
			}
		}
		if (tick.getTicks() % 5 == 0) {
			this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
		}
	}

	protected void tickClient(ComponentTickable tick) {
		if (plugCache == null) {
			plugCache = new CachedTileOutput(level, new BlockPos(worldPosition).relative(Direction.DOWN));
		}
	}
}