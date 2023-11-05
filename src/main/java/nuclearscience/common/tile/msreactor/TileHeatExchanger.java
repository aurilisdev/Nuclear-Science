package nuclearscience.common.tile.msreactor;

import java.util.List;

import electrodynamics.common.block.VoxelShapes;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.object.Location;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import nuclearscience.api.turbine.ISteamReceiver;
import nuclearscience.common.settings.Constants;
import nuclearscience.common.tile.fissionreactor.TileFissionReactorCore;
import nuclearscience.registers.NuclearScienceBlockTypes;
import nuclearscience.registers.NuclearScienceBlocks;

public class TileHeatExchanger extends GenericTile {
	public static final int STEAM_GEN_DIAMETER = 5;
	public static final int STEAM_GEN_HEIGHT = 2;
	private ISteamReceiver[][][] cachedReceivers = new ISteamReceiver[STEAM_GEN_DIAMETER][STEAM_GEN_HEIGHT][STEAM_GEN_DIAMETER];
	public Property<Double> temperature = property(new Property<>(PropertyType.Double, "temperature", 0.0));

	public TileHeatExchanger(BlockPos pos, BlockState state) {
		super(NuclearScienceBlockTypes.TILE_HEATEXCHANGER.get(), pos, state);

		addComponent(new ComponentTickable(this).tickCommon(this::tickCommon));
		addComponent(new ComponentPacketHandler(this));
	}

	protected void tickCommon(ComponentTickable tickable) {
		if (temperature.get() > 100) {
			produceSteam();
		}
		temperature.set(temperature.get() * 0.9);
	}

	/**
	 * Mostly copied from {@link TileFissionReactorCore#produceSteam()} with some changes to fit the exchanger
	 */
	protected void produceSteam() {
		if (temperature.get() > 100) {
			Location source = new Location(worldPosition.getX() + 0.5f, worldPosition.getY() + 0.5f, worldPosition.getZ() + 0.5f);
			AABB bb = AABB.ofSize(new Vec3(source.x(), source.y(), source.z()), 4, 4, 4);
			List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, bb);
			for (LivingEntity living : list) {
				FluidState state = level.getBlockState(living.getOnPos()).getFluidState();
				if (state.is(Fluids.WATER) || state.is(Fluids.FLOWING_WATER)) {
					living.hurt(living.damageSources().drown(), 3);
				}
			}
		}
		for (int i = 0; i < STEAM_GEN_DIAMETER; i++) {
			for (int j = 0; j < STEAM_GEN_HEIGHT; j++) {
				for (int k = 0; k < STEAM_GEN_DIAMETER; k++) {
					boolean isReactor2d = i - STEAM_GEN_DIAMETER / 2 == 0 && k - STEAM_GEN_DIAMETER / 2 == 0;
					if (isReactor2d && j == 0) {
						if (!level.isClientSide && level.random.nextFloat() < temperature.get() / (TileMSReactorCore.MELTDOWN_TEMPERATURE * 20.0 * STEAM_GEN_DIAMETER * STEAM_GEN_DIAMETER * STEAM_GEN_HEIGHT)) {
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
						boolean isFaceWater = level.getBlockState(new BlockPos(offsetX, worldPosition.getY(), worldPosition.getZ())).getBlock() == Blocks.WATER || level.getBlockState(new BlockPos(worldPosition.getX(), worldPosition.getY(), offsetZ)).getBlock() == Blocks.WATER || isReactor2d;
						if (isFaceWater) {
							if (!level.isClientSide) {
								ISteamReceiver turbine = cachedReceivers[i][j][k];
								if (turbine != null) {
									if (turbine.isStillValid()) {
										cachedReceivers[i][j][k] = null;
									}
									turbine.receiveSteam(Constants.MSRREACTOR_MAXENERGYTARGET / (STEAM_GEN_DIAMETER * STEAM_GEN_DIAMETER * 20.0 * (TileMSReactorCore.MELTDOWN_TEMPERATURE / temperature.get())), temperature.get());
								}
								if (level.random.nextFloat() < temperature.get() / (TileMSReactorCore.MELTDOWN_TEMPERATURE * 20.0 * STEAM_GEN_DIAMETER * STEAM_GEN_DIAMETER * STEAM_GEN_HEIGHT)) {
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
							} else if (level.isClientSide && level.random.nextFloat() < temperature.get() / (TileMSReactorCore.MELTDOWN_TEMPERATURE * 3)) {
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
		}
	}

	public Double receiveHeat(Double perReceiver) {
		temperature.set(perReceiver);
		return perReceiver;
	}

	@Override
	public InteractionResult use(Player arg0, InteractionHand arg1, BlockHitResult arg2) {
		return InteractionResult.FAIL;
	}

	static {

		VoxelShape shape = Shapes.empty();
		shape = Shapes.join(shape, Block.box(0, 1, 0, 16, 3, 16), BooleanOp.OR);
		shape = Shapes.join(shape, Block.box(1, 4, 1, 15, 16, 15), BooleanOp.OR);
		shape = Shapes.join(shape, Block.box(0.5, 3, 0.5, 15.5, 4, 15.5), BooleanOp.OR);
		shape = Shapes.join(shape, Block.box(4, 0, 4, 12, 1, 12), BooleanOp.OR);

		VoxelShapes.registerShape(NuclearScienceBlocks.blockHeatExchanger, shape, Direction.WEST);

	}

}
