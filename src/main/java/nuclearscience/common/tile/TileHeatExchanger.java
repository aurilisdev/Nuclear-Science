package nuclearscience.common.tile;

import electrodynamics.prefab.tile.GenericTileTicking;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
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
	produceSteam();
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
			if (!world.isRemote && world.rand.nextFloat() < temperature
				/ (TileMSRReactorCore.MELTDOWN_TEMPERATURE * 20.0 * STEAM_GEN_DIAMETER * STEAM_GEN_DIAMETER * STEAM_GEN_HEIGHT)) {
			    world.setBlockState(pos, getBlockState().with(BlockStateProperties.WATERLOGGED, false));
			}
			continue;
		    }
		    int offsetX = pos.getX() + i - STEAM_GEN_DIAMETER / 2;
		    int offsetY = pos.getY() + j;
		    int offsetZ = pos.getZ() + k - STEAM_GEN_DIAMETER / 2;
		    BlockPos offpos = new BlockPos(offsetX, offsetY, offsetZ);
		    Block offset = world.getBlockState(offpos).getBlock();
		    if (offset == Blocks.WATER) {
			boolean isFaceWater = world.getBlockState(new BlockPos(offsetX, pos.getY(), pos.getZ())).getBlock() == Blocks.WATER
				|| world.getBlockState(new BlockPos(pos.getX(), pos.getY(), offsetZ)).getBlock() == Blocks.WATER || isReactor2d;
			if (isFaceWater) {
			    if (!world.isRemote) {
				TileTurbine turbine = cachedTurbines[i][j][k];
				if (turbine != null) {
				    if (turbine.isRemoved()) {
					cachedTurbines[i][j][k] = null;
				    }
				    turbine.addSteam((int) (Constants.MSRREACTOR_MAXENERGYTARGET / (STEAM_GEN_DIAMETER * STEAM_GEN_DIAMETER * 20.0
					    * (TileMSRReactorCore.MELTDOWN_TEMPERATURE / temperature))), (int) temperature);
				}
				if (world.rand.nextFloat() < temperature / (TileMSRReactorCore.MELTDOWN_TEMPERATURE * 20.0 * STEAM_GEN_DIAMETER
					* STEAM_GEN_DIAMETER * STEAM_GEN_HEIGHT)) {
				    world.setBlockState(offpos, Blocks.AIR.getDefaultState());
				    continue;
				}
				if (turbine == null || world.loadedTileEntityList.contains(turbine)) {
				    TileEntity above = world.getTileEntity(new BlockPos(offsetX, offsetY + 1, offsetZ));
				    if (above instanceof TileTurbine) {
					cachedTurbines[i][j][k] = (TileTurbine) above;
				    } else {
					cachedTurbines[i][j][k] = null;
				    }
				}
			    } else if (world.isRemote && world.rand.nextFloat() < temperature / (TileMSRReactorCore.MELTDOWN_TEMPERATURE * 3)) {
				double offsetFX = offsetX + world.rand.nextDouble() / 2.0 * (world.rand.nextBoolean() ? -1 : 1);
				double offsetFY = offsetY + world.rand.nextDouble() / 2.0 * (world.rand.nextBoolean() ? -1 : 1);
				double offsetFZ = offsetZ + world.rand.nextDouble() / 2.0 * (world.rand.nextBoolean() ? -1 : 1);
				world.addParticle(ParticleTypes.BUBBLE, offsetFX + 0.5D, offsetFY + 0.20000000298023224D, offsetFZ + 0.5D, 0.0D, 0.0D,
					0.0D);
				if (world.rand.nextInt(3) == 0) {
				    world.addParticle(ParticleTypes.SMOKE, offsetFX + 0.5D, offsetFY + 0.5D, offsetFZ + 0.5D, 0.0D, 0.0D, 0.0D);
				}
			    }
			}
		    }
		}
	    }
	}
    }

    protected void writeCustomPacket(CompoundNBT tag) {
	tag.putDouble("temperature", temperature);
    }

    protected void readCustomPacket(CompoundNBT nbt) {
	temperature = nbt.getDouble("temperature");
    }
}
