package nuclearscience.common.tile;

import java.util.List;

import electrodynamics.prefab.tile.GenericTileTicking;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.object.Location;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import nuclearscience.DeferredRegisters;
import nuclearscience.api.radiation.RadiationSystem;

public class TileMeltedReactor extends GenericTileTicking {
    public static final float RADIATION_RADIUS = 30;
    public static final float START_RADIATION = 8766000f * 5f;
    public int radiation = (int) START_RADIATION;
    public int temperature = 6000;

    public TileMeltedReactor() {
	super(DeferredRegisters.TILE_MELTEDREACTOR.get());
	addComponent(new ComponentTickable().tickServer(this::tickServer));
    }

    protected void tickServer(ComponentTickable tickable) {
	long ticks = tickable.getTicks();
	if (ticks % 3 == 0) {
	    BlockState state = level.getBlockState(worldPosition.below());
	    if (state.getMaterial() == Material.AIR || state.getBlock() instanceof LiquidBlock) {
		level.setBlockAndUpdate(worldPosition.below(), getBlockState());
		level.setBlockAndUpdate(worldPosition, Blocks.AIR.defaultBlockState());
		BlockEntity tile = level.getBlockEntity(worldPosition.below());
		if (tile instanceof TileMeltedReactor) {
		    TileMeltedReactor newTile = (TileMeltedReactor) tile;
		    newTile.radiation = radiation;
		    newTile.radiation = radiation;
		}
		return;
	    }
	}
	if (temperature > 0) {
	    temperature--;
	    double x2 = worldPosition.getX() + 0.5 + (level.random.nextDouble() - 0.5) * RADIATION_RADIUS / 2;
	    double y2 = worldPosition.getY() + 0.5 + (level.random.nextDouble() - 0.5) * RADIATION_RADIUS / 2;
	    double z2 = worldPosition.getZ() + 0.5 + (level.random.nextDouble() - 0.5) * RADIATION_RADIUS / 2;
	    double d3 = worldPosition.getX() - x2;
	    double d4 = worldPosition.getY() - y2;
	    double d5 = worldPosition.getZ() - z2;
	    double distanceSq = d3 * d3 + d4 * d4 + d5 * d5;
	    if (distanceSq < RADIATION_RADIUS * RADIATION_RADIUS && level.random.nextDouble() > distanceSq / (RADIATION_RADIUS * RADIATION_RADIUS)) {
		BlockPos p = new BlockPos(Math.floor(x2), Math.floor(y2), Math.floor(z2));
		BlockState st = level.getBlockState(p);
		Block block = st.getBlock();
		if (st.getMaterial() == Material.AIR) {
		    if (level.getBlockState(p.below()).getMaterial() != Material.AIR) {
			level.setBlockAndUpdate(p, Blocks.AIR.defaultBlockState());
		    }
		} else if (block == Blocks.STONE) {
		    if (temperature > 2100) {
			level.setBlockAndUpdate(p, Blocks.COBBLESTONE.defaultBlockState());
		    }
		} else if (block == Blocks.COBBLESTONE) {
		    level.setBlockAndUpdate(p, Blocks.LAVA.defaultBlockState());
		} else if (block == Blocks.WATER) {
		    level.setBlockAndUpdate(p, Blocks.AIR.defaultBlockState());
		} else if (block == Blocks.SAND) {
		    level.setBlockAndUpdate(p, Blocks.GLASS.defaultBlockState());
		}
	    }
	}
	if (radiation > 0) {
	    radiation--;
	    double x2 = worldPosition.getX() + 0.5 + (level.random.nextDouble() - 0.5) * RADIATION_RADIUS / 2;
	    double y2 = worldPosition.getY() + 0.5 + (level.random.nextDouble() - 0.5) * RADIATION_RADIUS / 2;
	    double z2 = worldPosition.getZ() + 0.5 + (level.random.nextDouble() - 0.5) * RADIATION_RADIUS / 2;
	    double d3 = worldPosition.getX() - x2;
	    double d4 = worldPosition.getY() - y2;
	    double d5 = worldPosition.getZ() - z2;
	    double distanceSq = d3 * d3 + d4 * d4 + d5 * d5;
	    if (distanceSq < RADIATION_RADIUS * RADIATION_RADIUS && level.random.nextDouble() > distanceSq / (RADIATION_RADIUS * RADIATION_RADIUS)) {
		BlockPos p = new BlockPos(Math.floor(x2), Math.floor(y2), Math.floor(z2));
		BlockState st = level.getBlockState(p);
		Block block = st.getBlock();
		if (block == Blocks.GRASS_BLOCK || block == Blocks.DIRT) {
		    level.setBlockAndUpdate(p, DeferredRegisters.blockRadioactiveSoil.defaultBlockState());
		}
	    }
	}
	if (level.getLevelData().getGameTime() % 10 == 0) {
	    Location source = new Location(worldPosition.getX() + 0.5f, worldPosition.getY() + 0.5f, worldPosition.getZ() + 0.5f);
	    double totstrength = 120000 * (radiation / START_RADIATION);
	    double range = Math.sqrt(totstrength) / (5 * Math.sqrt(2)) * 2;
	    AABB bb = AABB.ofSize(range, range, range);
	    bb = bb.move(new Vec3(source.x(), source.y(), source.z()));
	    List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, bb);
	    for (LivingEntity living : list) {
		RadiationSystem.applyRadiation(living, source, totstrength);
	    }
	}
    }
}
