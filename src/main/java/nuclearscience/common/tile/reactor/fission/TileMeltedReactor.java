package nuclearscience.common.tile.reactor.fission;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraftforge.common.Tags;
import nuclearscience.common.block.BlockIrradiated;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.api.radiation.RadiationSystem;
import voltaic.api.radiation.SimpleRadiationSource;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.type.ComponentTickable;

public class TileMeltedReactor extends GenericTile {
	
	public static final float RADIATION_RADIUS = 30;
	public static final float START_RADIATION = 8766000f * 5f;
	public int radiation = (int) START_RADIATION;
	public int temperature = 6000;

	public TileMeltedReactor() {
		super(NuclearScienceTiles.TILE_MELTEDREACTOR.get());
		addComponent(new ComponentTickable(this).tickServer(this::tickServer));
	}

	protected void tickServer(ComponentTickable tickable) {
		long ticks = tickable.getTicks();
		if (ticks % 3 == 0) {
			BlockState state = level.getBlockState(worldPosition.below());
			if (state.isAir(level, worldPosition.below()) || state.getBlock() instanceof FlowingFluidBlock) {
				level.setBlockAndUpdate(worldPosition.below(), getBlockState());
				level.setBlockAndUpdate(worldPosition, Blocks.AIR.defaultBlockState());
				TileEntity tile = level.getBlockEntity(worldPosition.below());
				if (tile instanceof TileMeltedReactor) {
					((TileMeltedReactor) tile).radiation = radiation;
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
				BlockPos pos = new BlockPos((int) Math.floor(x2), (int) Math.floor(y2), (int) Math.floor(z2));
				BlockState state = level.getBlockState(pos);
				if (state.isAir(level, pos)) {
					if (!level.getBlockState(pos.below()).isAir(level, pos.below())) {
						level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
					}
				} else if (state.is(BlockTags.BASE_STONE_OVERWORLD)) {
					if (temperature > 2100) {
						level.setBlockAndUpdate(pos, Blocks.COBBLESTONE.defaultBlockState());
					}
				} else if (state.is(Tags.Blocks.COBBLESTONE)) {
					level.setBlockAndUpdate(pos, Blocks.LAVA.defaultBlockState());
				} else if (level.getFluidState(pos).is(FluidTags.WATER)) {
					level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
				} else if (state.is(BlockTags.SAND)) {
					level.setBlockAndUpdate(pos, Blocks.GLASS.defaultBlockState());
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
				BlockPos pos = new BlockPos((int) Math.floor(x2), (int) Math.floor(y2), (int) Math.floor(z2));
				BlockState state = level.getBlockState(pos);
				if (BlockIrradiated.isValidPlacement(state)) {
					level.setBlockAndUpdate(pos, BlockIrradiated.getIrradiatedBlockstate(state));
				}
			}
		}
		double totstrength = 120000 * (radiation / START_RADIATION);
		int range = (int) (Math.sqrt(totstrength) / (5 * Math.sqrt(2)) * 2);
		RadiationSystem.addRadiationSource(getLevel(), new SimpleRadiationSource(totstrength, 1, range, true, 30, getBlockPos(), true, false));
	}
	
	@Override
	public ActionResultType use(PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		return ActionResultType.PASS;
	}

	@Override
	public CompoundNBT save(CompoundNBT compound) {
		compound.putInt("rads", radiation);
		compound.putInt("temp", temperature);
		return super.save(compound);
	}

	@Override
	public void load(BlockState state, CompoundNBT compound) {
		radiation = compound.getInt("rads");
		temperature = compound.getInt("temp");
		super.load(state, compound);
	}
}
