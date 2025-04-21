package nuclearscience.common.tile.reactor.moltensalt;

import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import nuclearscience.api.turbine.ISteamReceiver;
import nuclearscience.common.settings.NuclearConstants;
import nuclearscience.common.tile.reactor.fission.TileFissionReactorCore;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.type.ComponentPacketHandler;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.object.Location;

public class TileHeatExchanger extends GenericTile {
	public static final int STEAM_GEN_DIAMETER = 5;
	public static final int STEAM_GEN_HEIGHT = 2;
	private ISteamReceiver[][][] cachedReceivers = new ISteamReceiver[STEAM_GEN_DIAMETER][STEAM_GEN_HEIGHT][STEAM_GEN_DIAMETER];
	public SingleProperty<Double> temperature = property(new SingleProperty<>(PropertyTypes.DOUBLE, "temperature", 0.0));

	public TileHeatExchanger(BlockPos pos, BlockState state) {
		super(NuclearScienceTiles.TILE_HEATEXCHANGER.get(), pos, state);

		addComponent(new ComponentTickable(this).tickCommon(this::tickCommon).tickServer(this::tickServer));
		addComponent(new ComponentPacketHandler(this));
	}

	private void tickServer(ComponentTickable componentTickable) {

		temperature.setValue(temperature.getValue() * 0.9);

		if (temperature.getValue() > 100) {
			Location source = new Location(worldPosition.getX() + 0.5f, worldPosition.getY() + 0.5f, worldPosition.getZ() + 0.5f);
			AABB bb = AABB.ofSize(new Vec3(source.x(), source.y(), source.z()), 4, 4, 4);
			List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, bb);
			for (LivingEntity living : list) {
				if (!level.getBlockState(living.getOnPos()).getFluidState().is(FluidTags.WATER)) {
					continue;
				}
				living.hurt(living.damageSources().drown(), 3);
			}
		}
	}

	protected void tickCommon(ComponentTickable tickable) {
		if (temperature.getValue() > 100) {
			produceSteam();
		}

	}

	/**
	 * Mostly copied from {@link TileFissionReactorCore#produceSteam()} with some changes to fit the exchanger
	 */
	protected void produceSteam() {

		for (int i = 0; i < STEAM_GEN_DIAMETER; i++) {
			for (int j = 0; j < STEAM_GEN_HEIGHT; j++) {
				for (int k = 0; k < STEAM_GEN_DIAMETER; k++) {
					boolean isReactor2d = i - STEAM_GEN_DIAMETER / 2 == 0 && k - STEAM_GEN_DIAMETER / 2 == 0;
					if (isReactor2d && j == 0) {
						if (!level.isClientSide && level.random.nextFloat() < temperature.getValue() / (TileMSReactorCore.MELTDOWN_TEMPERATURE * 20.0 * STEAM_GEN_DIAMETER * STEAM_GEN_DIAMETER * STEAM_GEN_HEIGHT)) {
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

					if(!TileFissionReactorCore.isStillWater(getLevel(), offpos)) {
						continue;
					}

					boolean isFaceWater = TileFissionReactorCore.isStillWater(getLevel(), new BlockPos(offsetX, worldPosition.getY(), worldPosition.getZ())) || TileFissionReactorCore.isStillWater(getLevel(), new BlockPos(worldPosition.getX(), worldPosition.getY(), offsetZ)) || isReactor2d;

					if(!isFaceWater) {
						continue;
					}

					if (!level.isClientSide) {
						ISteamReceiver turbine = cachedReceivers[i][j][k];
						if (turbine != null) {
							if (turbine.isStillValid()) {
								cachedReceivers[i][j][k] = null;
							}
							turbine.receiveSteam((int) (NuclearConstants.MSRREACTOR_MAXENERGYTARGET / (STEAM_GEN_DIAMETER * STEAM_GEN_DIAMETER * 20.0 * (TileMSReactorCore.MELTDOWN_TEMPERATURE / temperature.getValue()))), temperature.getValue().intValue());
						}
						if (level.random.nextFloat() < temperature.getValue() / (TileMSReactorCore.MELTDOWN_TEMPERATURE * 20.0 * STEAM_GEN_DIAMETER * STEAM_GEN_DIAMETER * STEAM_GEN_HEIGHT)) {
							level.setBlockAndUpdate(offpos, Blocks.AIR.defaultBlockState());
							continue;
						}
						if (turbine == null || turbine.isStillValid()) {
							BlockEntity above = level.getBlockEntity(new BlockPos(offsetX, offsetY + 1, offsetZ));
							if (above instanceof ISteamReceiver ab) {
								cachedReceivers[i][j][k] = ab;
							} else {
								cachedReceivers[i][j][k] = null;
							}
						}
					} else if (level.isClientSide && level.random.nextFloat() < temperature.getValue() / (TileMSReactorCore.MELTDOWN_TEMPERATURE * 3)) {
						double offsetFX = offsetX + level.random.nextDouble() / 2.0 * (level.random.nextBoolean() ? -1 : 1);
						double offsetFY = offsetY + level.random.nextDouble() / 2.0 * (level.random.nextBoolean() ? -1 : 1);
						double offsetFZ = offsetZ + level.random.nextDouble() / 2.0 * (level.random.nextBoolean() ? -1 : 1);
						level.addParticle(ParticleTypes.BUBBLE, offsetFX + 0.5D, offsetFY + 0.20000000298023224D, offsetFZ + 0.5D, 0.0D, 0.0D, 0.0D);
						if (level.random.nextInt(3) == 0) {
							level.addParticle(ParticleTypes.SMOKE, offsetFX + 0.5D, offsetFY + 0.5D, offsetFZ + 0.5D, 0.0D, 0.0D, 0.0D);
						}
					}
				}
			}
		}
	}

	public Double receiveHeat(Double perReceiver) {
		temperature.setValue(perReceiver);
		return perReceiver;
	}

	@Override
	public InteractionResult useWithoutItem(Player player, BlockHitResult hit) {
		return InteractionResult.PASS;
	}

	@Override
	public ItemInteractionResult useWithItem(ItemStack used, Player player, InteractionHand hand, BlockHitResult hit) {
		return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	}
}
