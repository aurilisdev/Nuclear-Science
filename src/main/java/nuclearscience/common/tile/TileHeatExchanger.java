package nuclearscience.common.tile;

import electrodynamics.prefab.tile.GenericTileTicking;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.settings.Constants;

public class TileHeatExchanger extends GenericTileTicking {
    public static final int STEAM_GEN_DIAMETER = 5;
    public static final int STEAM_GEN_HEIGHT = 2;
    private TileTurbine[][][] cachedTurbines = new TileTurbine[STEAM_GEN_DIAMETER][STEAM_GEN_HEIGHT][STEAM_GEN_DIAMETER];
    private double temperature;

    public TileHeatExchanger() {
	super(DeferredRegisters.TILE_HEATEXCHANGER.get());
	addComponent(new ComponentTickable().tickCommon(this::tickCommon).tickServer(this::tickServer));
	addComponent(new ComponentPacketHandler().customPacketReader(this::readCustomPacket).customPacketWriter(this::writeCustomPacket)
		.guiPacketReader(this::readCustomPacket).guiPacketWriter(this::writeCustomPacket));
    }

    protected void tickServer(ComponentTickable tickable) {
	if (tickable.getTicks() % 10 == 0) {
	    this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendCustomPacket();
	}
    }

    protected void tickCommon(ComponentTickable tickable) {
	if (temperature > 100) {
	    produceSteam();
	}
	temperature *= 0.9;
    }

    /**
     * Mostly copied from {@link TileReactorCore#produceSteam()} with some changes
     * to fit the exchanger
     */
    protected void produceSteam() {
	for (int i = 0; i < STEAM_GEN_DIAMETER; i++) {
	    for (int j = 0; j < STEAM_GEN_HEIGHT; j++) {
		for (int k = 0; k < STEAM_GEN_DIAMETER; k++) {
		    boolean isReactor2d = i - STEAM_GEN_DIAMETER / 2 == 0 && k - STEAM_GEN_DIAMETER / 2 == 0;
		    if (isReactor2d && j == 0) {
			if (!level.isClientSide && level.random.nextFloat() < temperature
				/ (TileMSRReactorCore.MELTDOWN_TEMPERATURE * 20.0 * STEAM_GEN_DIAMETER * STEAM_GEN_DIAMETER * STEAM_GEN_HEIGHT)) {
			    if (level.getBlockState(worldPosition).hasProperty(BlockStateProperties.WATERLOGGED)) {
				level.setBlockAndUpdate(worldPosition, getBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
			    }
			}
			continue;
		    }
		    int offsetX = worldPosition.getX() + i - STEAM_GEN_DIAMETER / 2;
		    int offsetY = worldPosition.getY() + j;
		    int offsetZ = worldPosition.getZ() + k - STEAM_GEN_DIAMETER / 2;
		    BlockPos offpos = new BlockPos(offsetX, offsetY, offsetZ);
		    Block offset = level.getBlockState(offpos).getBlock();
		    if (offset == Blocks.WATER) {
			boolean isFaceWater = level.getBlockState(new BlockPos(offsetX, worldPosition.getY(), worldPosition.getZ()))
				.getBlock() == Blocks.WATER
				|| level.getBlockState(new BlockPos(worldPosition.getX(), worldPosition.getY(), offsetZ)).getBlock() == Blocks.WATER
				|| isReactor2d;
			if (isFaceWater) {
			    if (!level.isClientSide) {
				TileTurbine turbine = cachedTurbines[i][j][k];
				if (turbine != null) {
				    if (turbine.isRemoved()) {
					cachedTurbines[i][j][k] = null;
				    }
				    turbine.addSteam((int) (Constants.MSRREACTOR_MAXENERGYTARGET / (STEAM_GEN_DIAMETER * STEAM_GEN_DIAMETER * 20.0
					    * (TileMSRReactorCore.MELTDOWN_TEMPERATURE / temperature))), (int) temperature);
				}
				if (level.random.nextFloat() < temperature / (TileMSRReactorCore.MELTDOWN_TEMPERATURE * 20.0 * STEAM_GEN_DIAMETER
					* STEAM_GEN_DIAMETER * STEAM_GEN_HEIGHT)) {
				    level.setBlockAndUpdate(offpos, Blocks.AIR.defaultBlockState());
				    continue;
				}
				if (turbine == null || level.blockEntityList.contains(turbine)) {
				    BlockEntity above = level.getBlockEntity(new BlockPos(offsetX, offsetY + 1, offsetZ));
				    if (above instanceof TileTurbine) {
					cachedTurbines[i][j][k] = (TileTurbine) above;
				    } else {
					cachedTurbines[i][j][k] = null;
				    }
				}
			    } else if (level.isClientSide && level.random.nextFloat() < temperature / (TileMSRReactorCore.MELTDOWN_TEMPERATURE * 3)) {
				double offsetFX = offsetX + level.random.nextDouble() / 2.0 * (level.random.nextBoolean() ? -1 : 1);
				double offsetFY = offsetY + level.random.nextDouble() / 2.0 * (level.random.nextBoolean() ? -1 : 1);
				double offsetFZ = offsetZ + level.random.nextDouble() / 2.0 * (level.random.nextBoolean() ? -1 : 1);
				level.addParticle(ParticleTypes.BUBBLE, offsetFX + 0.5D, offsetFY + 0.20000000298023224D, offsetFZ + 0.5D, 0.0D, 0.0D,
					0.0D);
				if (level.random.nextInt(3) == 0) {
				    level.addParticle(ParticleTypes.SMOKE, offsetFX + 0.5D, offsetFY + 0.5D, offsetFZ + 0.5D, 0.0D, 0.0D, 0.0D);
				}
			    }
			}
		    }
		}
	    }
	}
    }

    protected void writeCustomPacket(CompoundTag tag) {
	tag.putDouble("temperature", temperature);
    }

    protected void readCustomPacket(CompoundTag nbt) {
	temperature = nbt.getDouble("temperature");
    }

    public Double receiveHeat(Double perReceiver) {
	temperature = perReceiver;
	return perReceiver;
    }
}
