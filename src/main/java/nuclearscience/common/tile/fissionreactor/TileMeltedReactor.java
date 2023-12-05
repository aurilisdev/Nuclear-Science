package nuclearscience.common.tile.fissionreactor;

import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.object.Location;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import nuclearscience.api.radiation.RadiationSystem;
import nuclearscience.registers.NuclearScienceBlockTypes;
import nuclearscience.registers.NuclearScienceBlocks;

public class TileMeltedReactor extends GenericTile {
	public static final float RADIATION_RADIUS = 30;
	public static final float START_RADIATION = 8766000f * 5f;
	public int radiation = (int) START_RADIATION;
	public int temperature = 6000;

	public TileMeltedReactor(BlockPos pos, BlockState state) {
		super(NuclearScienceBlockTypes.TILE_MELTEDREACTOR.get(), pos, state);
		addComponent(new ComponentTickable(this).tickServer(this::tickServer));
	}

	protected void tickServer(ComponentTickable tickable) {
		long ticks = tickable.getTicks();
		if (ticks % 3 == 0) {
			BlockState state = level.getBlockState(worldPosition.below());
			if (state.isAir() || state.getBlock() instanceof LiquidBlock) {
				level.setBlockAndUpdate(worldPosition.below(), getBlockState());
				level.setBlockAndUpdate(worldPosition, Blocks.AIR.defaultBlockState());
				BlockEntity tile = level.getBlockEntity(worldPosition.below());
				if (tile instanceof TileMeltedReactor newTile) {
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
				BlockPos p = new BlockPos((int) Math.floor(x2), (int) Math.floor(y2), (int) Math.floor(z2));
				BlockState st = level.getBlockState(p);
				Block block = st.getBlock();
				if (st.isAir()) {
					if (!level.getBlockState(p.below()).isAir()) {
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
				BlockPos p = new BlockPos((int) Math.floor(x2), (int) Math.floor(y2), (int) Math.floor(z2));
				BlockState st = level.getBlockState(p);
				Block block = st.getBlock();
				if (block == Blocks.GRASS_BLOCK || block == Blocks.DIRT) {
					level.setBlockAndUpdate(p, NuclearScienceBlocks.blockRadioactiveSoil.defaultBlockState());
				}
			}
		}
		if (level.getLevelData().getGameTime() % 10 == 0) {
			double totstrength = 120000 * (radiation / START_RADIATION);
			double range = Math.sqrt(totstrength) / (5 * Math.sqrt(2)) * 2;
			RadiationSystem.emitRadiationFromLocation(level, new Location(worldPosition), range, totstrength);
		}
	}

	@Override
	public InteractionResult use(Player arg0, InteractionHand arg1, BlockHitResult arg2) {
		return InteractionResult.FAIL;
	}
}