package nuclearscience.common.tile.reactor.fission;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.Tags;
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

	public TileMeltedReactor(BlockPos pos, BlockState state) {
		super(NuclearScienceTiles.TILE_MELTEDREACTOR.get(), pos, state);
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
				if (state.isAir()) {
					if (!level.getBlockState(pos.below()).isAir()) {
						level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
					}
				} else if (state.is(BlockTags.BASE_STONE_OVERWORLD)) {
					if (temperature > 2100) {
						level.setBlockAndUpdate(pos, Blocks.COBBLESTONE.defaultBlockState());
					}
				} else if (state.is(Tags.Blocks.COBBLESTONES)) {
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
	public ItemInteractionResult useWithItem(ItemStack used, Player player, InteractionHand hand, BlockHitResult hit) {
		return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	}

	@Override
	public InteractionResult useWithoutItem(Player player, BlockHitResult hit) {
		return InteractionResult.PASS;
	}

	@Override
	protected void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
		compound.putInt("rads", radiation);
		compound.putInt("temp", temperature);
		super.saveAdditional(compound, registries);
	}

	@Override
	protected void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
		radiation = compound.getInt("rads");
		temperature = compound.getInt("temp");
		super.loadAdditional(compound, registries);
	}
}
