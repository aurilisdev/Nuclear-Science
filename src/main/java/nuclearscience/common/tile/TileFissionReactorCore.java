package nuclearscience.common.tile;

import java.util.List;

import electrodynamics.common.block.VoxelShapes;
import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.common.recipe.categories.item2item.Item2ItemRecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentInventory.InventoryBuilder;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.object.Location;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import nuclearscience.api.radiation.RadiationSystem;
import nuclearscience.api.turbine.ISteamReceiver;
import nuclearscience.common.inventory.container.ContainerReactorCore;
import nuclearscience.common.recipe.NuclearScienceRecipeInit;
import nuclearscience.common.settings.Constants;
import nuclearscience.registers.NuclearScienceBlockTypes;
import nuclearscience.registers.NuclearScienceBlocks;
import nuclearscience.registers.NuclearScienceDamageTypes;
import nuclearscience.registers.NuclearScienceItems;

public class TileFissionReactorCore extends GenericTile {

	public static final int FUEL_ROD_COUNT = 4;

	public static final int DUETERIUM_SLOT = 4;
	public static final int OUTPUT_SLOT = 5;

	public static final int MELTDOWN_TEMPERATURE_ACTUAL = 5611;
	public static final int MELTDOWN_TEMPERATURE_CALC = 4407;
	// NB! THE VALUES ABOVE ARE USED FROM THE VERY OLD CALCULATION CODE I MADE BACK
	// IN EARLY 2019. READ LINE 51
	// TO SEE WHAT THE ACTUAL "TEMPERATURE" IS. THE VALUES ABOVE ARENT "REAL"
	// VALUES.
	public static final double WATER_TEMPERATURE = 10;
	public static final double AIR_TEMPERATURE = 15;
	public static final int MAX_FUEL_COUNT = 3 * 4;
	public static final int STEAM_GEN_DIAMETER = 5;
	public static final int STEAM_GEN_HEIGHT = 2;
	private ISteamReceiver[][][] cachedReceivers = new ISteamReceiver[STEAM_GEN_DIAMETER][STEAM_GEN_HEIGHT][STEAM_GEN_DIAMETER];
	public Property<Double> temperature = property(new Property<>(PropertyType.Double, "temperature", AIR_TEMPERATURE));
	public Property<Integer> fuelCount = property(new Property<>(PropertyType.Integer, "fuelCount", 0));
	public Property<Boolean> hasDeuterium = property(new Property<>(PropertyType.Boolean, "hasDeuterium", false));
	public int ticksOverheating = 0;
	public int ticks = 0;

	private List<ElectrodynamicsRecipe> cachedRecipes;

	public TileFissionReactorCore(BlockPos pos, BlockState state) {
		super(NuclearScienceBlockTypes.TILE_REACTORCORE.get(), pos, state);
		addComponent(new ComponentDirection(this));
		addComponent(new ComponentTickable(this).tickCommon(this::tickCommon).tickServer(this::tickServer));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentInventory(this, InventoryBuilder.newInv().inputs(5).outputs(1)).faceSlots(Direction.UP, 0, 1, 2, 3, 4).faceSlots(Direction.DOWN, 5));
		addComponent(new ComponentContainerProvider("container.reactorcore", this).createMenu((id, player) -> new ContainerReactorCore(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	}

	protected void tickServer(ComponentTickable tickable) {

//		fuelCount.set(0);
//		for (int i = 0; i < 4; i++) {
//			ItemStack stack = inv.getItem(i);
//			fuelCount.set(fuelCount.get() + (stack.getItem() == NuclearScienceItems.ITEM_FUELLEUO2.get() ? 2 : stack.getItem() == NuclearScienceItems.ITEM_FUELHEUO2.get() ? 3 : stack.getItem() == NuclearScienceItems.ITEM_FUELPLUTONIUM.get() ? 2 : 0));
//		}
//		hasDeuterium.set(!inv.getItem(4).isEmpty());

		double decrease = (temperature.get() - AIR_TEMPERATURE) / 3000.0;

		if (fuelCount.get() == 0) {

			decrease *= 25;

		}

		boolean hasWater = !getBlockState().getFluidState().isEmpty();

		if (hasWater) {

			decrease += (temperature.get() - WATER_TEMPERATURE) / 5000.0;

		}

		if (decrease != 0) {

			temperature.set(temperature.get() - (decrease < 0.001 && decrease > 0 ? 0.001 : decrease > -0.001 && decrease < 0 ? -0.001 : decrease));

		}

		ComponentInventory inv = getComponent(ComponentType.Inventory);

		if (fuelCount.get() > 0 && ticks > 50) {

			BlockEntity tile = level.getBlockEntity(worldPosition.below());

			int insertion = 0;

			if (tile instanceof TileControlRodAssembly assembly) {

				insertion = assembly.isMSR.get() ? 0 : assembly.insertion.get();

			}

			double insertDecimal = (100 - insertion) / 100.0;

			if (level.random.nextFloat() < insertDecimal) {

				for (int slot = 0; slot < FUEL_ROD_COUNT; slot++) {

					ItemStack fuelRod = inv.getItem(slot);

					fuelRod.setDamageValue((int) (fuelRod.getDamageValue() + 1 + Math.round(temperature.get()) / MELTDOWN_TEMPERATURE_CALC));

					if (!fuelRod.isEmpty() && fuelRod.getDamageValue() >= fuelRod.getMaxDamage()) {

						inv.setItem(slot, new ItemStack(NuclearScienceItems.ITEM_FUELSPENT.get()));

					}

				}

			}

			temperature.set(temperature.get() + (MELTDOWN_TEMPERATURE_CALC * insertDecimal * (0.25 * (fuelCount.get() / 2.0) + level.random.nextDouble() / 5.0) - temperature.get()) / (200 + 20 * (hasWater ? 4.0 : 1)));

			if (temperature.get() > MELTDOWN_TEMPERATURE_ACTUAL + level.random.nextInt(50) && fuelCount.get() > 0) {

				ticksOverheating++;

				// Implement some alarm sounds at this time
				if (ticksOverheating > 10 * 20) {

					meltdown();

				}

			}

		} else {

			ticksOverheating = 0;

		}

		temperature.set(Math.max(AIR_TEMPERATURE, temperature.get()));

		if (hasDeuterium.get() && fuelCount.get() > 0 && level.random.nextFloat() < 1 / (1200.0 * MELTDOWN_TEMPERATURE_CALC / temperature.get())) {

			processFissReact(inv);

		}

	}

	protected void tickCommon(ComponentTickable tickable) {
		ticks = ticks > Integer.MAX_VALUE - 2 ? 0 : ticks + 1;
		if (ticks % 20 == 0) {
			level.getLightEngine().checkBlock(worldPosition);
		}
		if (fuelCount.get() > 0 && ticks > 50) {
			if (level.getLevelData().getGameTime() % 10 == 0) {
				Location source = new Location(worldPosition);
				double totstrength = temperature.get() * 10;
				double range = Math.sqrt(totstrength) / (5 * Math.sqrt(2)) * 2;
				if (level.getLevelData().getGameTime() % 10 == 0) {
					RadiationSystem.emitRadiationFromLocation(level, source, range, totstrength);
				}
				if (temperature.get() > 100) {
					AABB bb = AABB.ofSize(new Vec3(source.x(), source.y(), source.z()), 4, 4, 4);
					List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, bb);
					for (LivingEntity living : list) {
						FluidState state = level.getBlockState(living.getOnPos()).getFluidState();
						if (state.is(Fluids.WATER) || state.is(Fluids.FLOWING_WATER)) {
							living.hurt(living.damageSources().drown(), 3);
						}
					}
				}
			}
		}
		produceSteam();
	}

	public void meltdown() {
		if (!level.isClientSide) {
			int radius = STEAM_GEN_DIAMETER / 2;
			level.setBlockAndUpdate(worldPosition, getBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
			for (int i = -radius; i <= radius; i++) {
				for (int j = -radius; j <= radius; j++) {
					for (int k = -radius; k <= radius; k++) {
						BlockPos ppos = new BlockPos(worldPosition.getX() + i, worldPosition.getY() + j, worldPosition.getZ() + k);
						BlockState state = level.getBlockState(ppos);
						if (state.getBlock() == Blocks.WATER) {
							level.setBlockAndUpdate(ppos, Blocks.AIR.defaultBlockState());
						}
					}
				}
			}
			level.setBlockAndUpdate(worldPosition, Blocks.AIR.defaultBlockState());
			// Feel free to switch to a different damage source; I figured radiation fitted
			// the best
			// switch to false if you don't want fire
			// for the final null, you can pass in a ExplosionCalculator if you want to do a
			// custom explosion
			Explosion actual = new Explosion(level, null, level.damageSources().source(NuclearScienceDamageTypes.RADIATION), null, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), 20, true, BlockInteraction.KEEP);
			// Explosion actual = new Explosion(level, null, worldPosition.getX(),
			// worldPosition.getY(), worldPosition.getZ(), 20, new ArrayList<>());
			radius = 3 * fuelCount.get();
			for (int i = -radius; i <= radius; i++) {
				for (int j = -radius; j <= radius; j++) {
					for (int k = -radius; k <= radius; k++) {
						BlockPos ppos = new BlockPos(worldPosition.getX() + i, worldPosition.getY() + j, worldPosition.getZ() + k);
						BlockState state = level.getBlockState(ppos);
						if (state.getBlock().getExplosionResistance(state, level, ppos, actual) < radius) {
							double distance = Math.sqrt(i * i + j * j + k * k);
							if (distance < radius && level.random.nextFloat() < 1 - 0.0001 * distance * distance * distance && level.random.nextFloat() < 0.9) {
								level.getBlockState(ppos).onBlockExploded(level, ppos, actual);
							}
						}
					}
				}
			}
			level.explode(null, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), 20, ExplosionInteraction.BLOCK);
			level.setBlockAndUpdate(worldPosition, NuclearScienceBlocks.blockMeltedReactor.defaultBlockState());
		}
	}

	protected void produceSteam() {
		if (temperature.get() <= 400) {
			return;
		}
		for (int i = 0; i < STEAM_GEN_DIAMETER; i++) {
			for (int j = 0; j < STEAM_GEN_HEIGHT; j++) {
				for (int k = 0; k < STEAM_GEN_DIAMETER; k++) {
					boolean isReactor2d = i - STEAM_GEN_DIAMETER / 2 == 0 && k - STEAM_GEN_DIAMETER / 2 == 0;
					if (isReactor2d && j == 0) {
						if (!level.isClientSide && level.random.nextFloat() < temperature.get() / (MELTDOWN_TEMPERATURE_CALC * 20.0 * STEAM_GEN_DIAMETER * STEAM_GEN_DIAMETER * STEAM_GEN_HEIGHT)) {
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
									turbine.receiveSteam(temperature.get(), Constants.FISSIONREACTOR_MAXENERGYTARGET / (STEAM_GEN_DIAMETER * STEAM_GEN_DIAMETER * 20.0 * (MELTDOWN_TEMPERATURE_ACTUAL / temperature.get())));
								}
								if (level.random.nextFloat() < temperature.get() / (MELTDOWN_TEMPERATURE_CALC * 20.0 * STEAM_GEN_DIAMETER * STEAM_GEN_DIAMETER * STEAM_GEN_HEIGHT)) {
									level.setBlockAndUpdate(offpos, Blocks.AIR.defaultBlockState());
									continue;
								}
								if (turbine == null || turbine.isStillValid()) {
									BlockEntity above = level.getBlockEntity(new BlockPos(offsetX, offsetY + 1, offsetZ));
									if (above instanceof ISteamReceiver trb) {
										cachedReceivers[i][j][k] = trb;
									} else {
										cachedReceivers[i][j][k] = null;
									}
								}
							} else if (level.isClientSide && level.random.nextFloat() < temperature.get() / (MELTDOWN_TEMPERATURE_ACTUAL * 3)) {
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

	public void processFissReact(ComponentInventory inv) {

		ItemStack input = inv.getItem(DUETERIUM_SLOT);
		ItemStack output = inv.getItem(OUTPUT_SLOT);

		if (input.isEmpty()) {

			return;

		}

		if (cachedRecipes == null || cachedRecipes.isEmpty()) {

			cachedRecipes = ElectrodynamicsRecipe.findRecipesbyType(NuclearScienceRecipeInit.FISSION_REACTOR_TYPE.get(), level);
		}

		for (ElectrodynamicsRecipe iRecipe : cachedRecipes) {

			Item2ItemRecipe recipe = (Item2ItemRecipe) iRecipe;

			for (CountableIngredient ing : recipe.getCountedIngredients()) {

				if (ing.testStack(input)) {

					if (output.isEmpty()) {

						inv.setItem(OUTPUT_SLOT, recipe.getItemOutputNoAccess().copy());

						input.shrink(recipe.getCountedIngredients().get(0).getStackSize());

					} else if (output.getCount() <= output.getMaxStackSize() + recipe.getItemOutputNoAccess().getCount()) {

						output.grow(recipe.getItemOutputNoAccess().getCount());

						input.shrink(recipe.getCountedIngredients().get(0).getStackSize());

					}

				}

			}

		}

	}

	@Override
	public void onInventoryChange(ComponentInventory inv, int slot) {
		if (level.isClientSide()) {
			return;
		}

		if (slot == -1 || slot < FUEL_ROD_COUNT) {

			fuelCount.set(0);

			for (int i = 0; i < FUEL_ROD_COUNT; i++) {

				ItemStack stack = inv.getItem(i);

				int fuelValue = 0;

				if (stack.getItem() == NuclearScienceItems.ITEM_FUELLEUO2.get()) {

					fuelValue = 2;

				} else if (stack.getItem() == NuclearScienceItems.ITEM_FUELHEUO2.get()) {

					fuelValue = 3;

				} else if (stack.getItem() == NuclearScienceItems.ITEM_FUELPLUTONIUM.get()) {

					fuelValue = 2;

				}

				fuelCount.set(fuelCount.get() + fuelValue);
			}

		}

		if (slot == -1 || slot == DUETERIUM_SLOT) {

			hasDeuterium.set(!inv.getItem(DUETERIUM_SLOT).isEmpty());

		}

	}

	static {
		VoxelShape shape = Shapes.empty();

		shape = Shapes.join(shape, Shapes.box(0.40625, 0.115675, 0.40625, 0.59375, 0.7088875, 0.59375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.25, 0.06565, 0.125, 0.75, 0.1148875, 0.1875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.84375, 0.7878, 0.25, 0.90625, 0.8370375, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.8125, 0.06565, 0.25, 0.875, 0.1148875, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.0625, 0.72215, 0.125, 0.125, 0.7878, 0.875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.1875, 0.06565, 0.875, 0.25, 0.72215, 0.9375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.1875, 0.06565, 0.0625, 0.25, 0.72215, 0.125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.25, 0.06565, 0.25, 0.3125, 0.72215, 0.3125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.25, 0.06565, 0.6875, 0.3125, 0.72215, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.6875, 0.06565, 0.25, 0.75, 0.72215, 0.3125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.6875, 0.06565, 0.6875, 0.75, 0.72215, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.75, 0.06565, 0.875, 0.8125, 0.72215, 0.9375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.75, 0.06565, 0.0625, 0.8125, 0.72215, 0.125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.0625, 0.06565, 0.75, 0.125, 0.72215, 0.8125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.0625, 0.06565, 0.1875, 0.125, 0.72215, 0.25), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.875, 0.06565, 0.75, 0.9375, 0.72215, 0.8125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.875, 0.06565, 0.1875, 0.9375, 0.72215, 0.25), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.125, 0.125, 0.06565, 0.875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.09375, 0.7878, 0.25, 0.15625, 0.8370375, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.125, 0.06565, 0.25, 0.1875, 0.1148875, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.875, 0, 0.125, 0.9375, 0.06565, 0.875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.125, 0, 0.0625, 0.875, 0.06565, 0.9375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.125, 0.72215, 0.0625, 0.875, 0.7878, 0.9375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.15625, 0.7878, 0.15625, 0.84375, 0.8370375, 0.84375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.1875, 0.06565, 0.1875, 0.8125, 0.1148875, 0.8125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.1875, 0.8370375, 0.1875, 0.8125, 0.886275, 0.8125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.25, 0.886275, 0.25, 0.75, 0.9355125, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.25, 0.7878, 0.84375, 0.75, 0.8370375, 0.90625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.25, 0.06565, 0.8125, 0.75, 0.1148875, 0.875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.875, 0.72215, 0.125, 0.9375, 0.7878, 0.875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.25, 0.7878, 0.09375, 0.75, 0.8370375, 0.15625), BooleanOp.OR);
		VoxelShapes.registerShape(NuclearScienceBlocks.blockFissionReactorCore, shape, Direction.NORTH);
	}

}
