package nuclearscience.common.tile;

import java.util.List;
import java.util.Set;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.common.recipe.categories.o2o.O2ORecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.object.Location;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import nuclearscience.DeferredRegisters;
import nuclearscience.api.radiation.DamageSourceRadiation;
import nuclearscience.api.radiation.RadiationSystem;
import nuclearscience.common.inventory.container.ContainerReactorCore;
import nuclearscience.common.recipe.NuclearScienceRecipeInit;
import nuclearscience.common.settings.Constants;

public class TileReactorCore extends GenericTile {
    public static final int MELTDOWN_TEMPERATURE_ACTUAL = 5611;
    public static final int MELTDOWN_TEMPERATURE_CALC = 4407;
    // NB! THE VALUES ABOVE ARE USED FROM THE VERY OLD CALCULATION CODE I MADE BACK
    // IN EARLY 2019. READ LINE 51
    // TO SEE WHAT THE ACTUAL "TEMPERATURE" IS. THE VALUES ABOVE ARENT "REAL"
    // VALUES.
    public static final int WATER_TEMPERATURE = 10;
    public static final int AIR_TEMPERATURE = 15;
    public static final int MAX_FUEL_COUNT = 3 * 4;
    public static final int STEAM_GEN_DIAMETER = 5;
    public static final int STEAM_GEN_HEIGHT = 2;
    private TileTurbine[][][] cachedTurbines = new TileTurbine[STEAM_GEN_DIAMETER][STEAM_GEN_HEIGHT][STEAM_GEN_DIAMETER];
    public double temperature = AIR_TEMPERATURE; // Actual real temperature is calculated by temp / 4 + 15 in the gui
    public boolean hasDeuterium = false;
    public int ticksOverheating = 0;
    public int fuelCount = 0;
    public int ticks = 0;

    public TileReactorCore(BlockPos pos, BlockState state) {
	super(DeferredRegisters.TILE_REACTORCORE.get(), pos, state);
	addComponent(new ComponentTickable().tickCommon(this::tickCommon).tickServer(this::tickServer));
	addComponent(new ComponentPacketHandler().customPacketReader(this::readCustomPacket).customPacketWriter(this::writeCustomPacket)
		.guiPacketReader(this::readCustomPacket).guiPacketWriter(this::writeCustomPacket));
	addComponent(new ComponentInventory(this).size(6).faceSlots(Direction.UP, 0, 1, 2, 3, 4).faceSlots(Direction.DOWN, 5));
	addComponent(new ComponentContainerProvider("container.reactorcore")
		.createMenu((id, player) -> new ContainerReactorCore(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
    }

    protected void tickServer(ComponentTickable tickable) {
	ComponentInventory inv = getComponent(ComponentType.Inventory);
	if (tickable.getTicks() % 10 == 0) {
	    this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendCustomPacket();
	}
	fuelCount = 0;
	for (int i = 0; i < 4; i++) {
	    ItemStack stack = inv.getItem(i);
	    fuelCount += stack.getItem() == DeferredRegisters.ITEM_FUELLEUO2.get() ? 2
		    : stack.getItem() == DeferredRegisters.ITEM_FUELHEUO2.get() ? 3
			    : stack.getItem() == DeferredRegisters.ITEM_FUELPLUTONIUM.get() ? 2 : 0;
	}
	hasDeuterium = !inv.getItem(4).isEmpty();

	double decrease = (temperature - AIR_TEMPERATURE) / 3000.0;
	if (fuelCount == 0) {
	    decrease *= 25;
	}
	boolean hasWater = !getBlockState().getFluidState().isEmpty();
	if (hasWater) {
	    decrease += (temperature - WATER_TEMPERATURE) / 5000.0;
	}
	if (decrease != 0) {
	    temperature -= decrease < 0.001 && decrease > 0 ? 0.001 : decrease > -0.001 && decrease < 0 ? -0.001 : decrease;
	}
	if (fuelCount > 0 && ticks > 50) {
	    BlockEntity tile = level.getBlockEntity(worldPosition.below());
	    int insertion = 0;
	    if (tile instanceof TileControlRodAssembly) {
		TileControlRodAssembly assembly = (TileControlRodAssembly) tile;
		insertion = assembly.isMSR ? 0 : assembly.insertion;
	    }
	    double insertDecimal = (100 - insertion) / 100.0;
	    if (level.random.nextFloat() < insertDecimal) {
		for (int slot = 0; slot < 4; slot++) {
		    ItemStack fuelRod = inv.getItem(slot);
		    if (fuelRod != ItemStack.EMPTY && fuelRod.getDamageValue() >= fuelRod.getMaxDamage()) {
			inv.setItem(slot, new ItemStack(DeferredRegisters.ITEM_FUELSPENT.get()));
		    }
		    fuelRod.setDamageValue((int) (fuelRod.getDamageValue() + 1 + Math.round(temperature) / MELTDOWN_TEMPERATURE_CALC));
		}
	    }
	    temperature += (MELTDOWN_TEMPERATURE_CALC * insertDecimal * (0.25 * (fuelCount / 2.0) + level.random.nextDouble() / 5.0) - temperature)
		    / (200 + 20 * (hasWater ? 4.0 : 1));
	    if (temperature > MELTDOWN_TEMPERATURE_ACTUAL + level.random.nextInt(50) && fuelCount > 0) {
		ticksOverheating++;
		// Implement some alarm sounds at this time
		if (ticksOverheating > 10 * 20) {
		    meltdown();
		}
	    }
	    if (level.getLevelData().getGameTime() % 10 == 0) {
		Location source = new Location(worldPosition.getX() + 0.5f, worldPosition.getY() + 0.5f, worldPosition.getZ() + 0.5f);
		double totstrength = temperature * 10;
		double range = Math.sqrt(totstrength) / (5 * Math.sqrt(2)) * 2;
		AABB bb = AABB.ofSize(new Vec3(source.x(), source.y(), source.z()), range, range, range);
		List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, bb);
		for (LivingEntity living : list) {
		    RadiationSystem.applyRadiation(living, source, totstrength);
		}
	    }
	} else {
	    ticksOverheating = 0;
	}
	temperature = Math.max(AIR_TEMPERATURE, temperature);
	if (fuelCount > 0 && level.random.nextFloat() < 1 / (1200.0 * MELTDOWN_TEMPERATURE_CALC / temperature)) {
	    processFissReact(inv);
	}
    }

    protected void tickCommon(ComponentTickable tickable) {
	ticks = ticks > Integer.MAX_VALUE - 2 ? 0 : ticks + 1;
	if (ticks % 20 == 0) {
	    level.getLightEngine().checkBlock(worldPosition);
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
	    Explosion actual = new Explosion(level, null, DamageSourceRadiation.INSTANCE, null, worldPosition.getX(), worldPosition.getY(),
		    worldPosition.getZ(), 20, true, BlockInteraction.BREAK);
	    // Explosion actual = new Explosion(level, null, worldPosition.getX(),
	    // worldPosition.getY(), worldPosition.getZ(), 20, new ArrayList<>());
	    // TODO: FIX THIS! THE LINE ABOVE IS CLIENT SIDE ONLY!
	    radius = 3 * fuelCount;
	    for (int i = -radius; i <= radius; i++) {
		for (int j = -radius; j <= radius; j++) {
		    for (int k = -radius; k <= radius; k++) {
			BlockPos ppos = new BlockPos(worldPosition.getX() + i, worldPosition.getY() + j, worldPosition.getZ() + k);
			BlockState state = level.getBlockState(ppos);
			if (state.getBlock().getExplosionResistance(state, level, ppos, actual) < radius) {
			    double distance = Math.sqrt(i * i + j * j + k * k);
			    if (distance < radius && level.random.nextFloat() < 1 - 0.0001 * distance * distance * distance
				    && level.random.nextFloat() < 0.9) {
				level.getBlockState(ppos).onBlockExploded(level, ppos, actual);
			    }
			}
		    }
		}
	    }
	    level.explode(null, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), 20, BlockInteraction.DESTROY);
	    level.setBlockAndUpdate(worldPosition, DeferredRegisters.blockMeltedReactor.defaultBlockState());
	}
    }

    protected void produceSteam() {
	if (temperature <= 400) {
	    return;
	}
	for (int i = 0; i < STEAM_GEN_DIAMETER; i++) {
	    for (int j = 0; j < STEAM_GEN_HEIGHT; j++) {
		for (int k = 0; k < STEAM_GEN_DIAMETER; k++) {
		    boolean isReactor2d = i - STEAM_GEN_DIAMETER / 2 == 0 && k - STEAM_GEN_DIAMETER / 2 == 0;
		    if (isReactor2d && j == 0) {
			if (!level.isClientSide && level.random.nextFloat() < temperature
				/ (MELTDOWN_TEMPERATURE_CALC * 20.0 * STEAM_GEN_DIAMETER * STEAM_GEN_DIAMETER * STEAM_GEN_HEIGHT)) {
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
				    turbine.addSteam(
					    (int) (Constants.FISSIONREACTOR_MAXENERGYTARGET
						    / (STEAM_GEN_DIAMETER * STEAM_GEN_DIAMETER * 20.0 * (MELTDOWN_TEMPERATURE_ACTUAL / temperature))),
					    (int) temperature);
				}
				if (level.random.nextFloat() < temperature
					/ (MELTDOWN_TEMPERATURE_CALC * 20.0 * STEAM_GEN_DIAMETER * STEAM_GEN_DIAMETER * STEAM_GEN_HEIGHT)) {
				    level.setBlockAndUpdate(offpos, Blocks.AIR.defaultBlockState());
				    continue;
				}
				if (turbine == null || turbine.isRemoved()) {
				    BlockEntity above = level.getBlockEntity(new BlockPos(offsetX, offsetY + 1, offsetZ));
				    if (above instanceof TileTurbine trb) {
					cachedTurbines[i][j][k] = trb;
				    } else {
					cachedTurbines[i][j][k] = null;
				    }
				}
			    } else if (level.isClientSide && level.random.nextFloat() < temperature / (MELTDOWN_TEMPERATURE_ACTUAL * 3)) {
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
	ComponentInventory inv = getComponent(ComponentType.Inventory);
	tag.putBoolean("hasDeuterium", hasDeuterium);
	tag.putDouble("temperature", temperature);
	tag.putInt("fuelCount", inv.countItem(DeferredRegisters.ITEM_FUELHEUO2.get()) + inv.countItem(DeferredRegisters.ITEM_FUELLEUO2.get()));
    }

    protected void readCustomPacket(CompoundTag nbt) {
	hasDeuterium = nbt.getBoolean("hasDeuterium");
	temperature = nbt.getDouble("temperature");
	fuelCount = nbt.getInt("fuelCount");
    }

    public void processFissReact(ComponentInventory inv) {

	int inputSlot = 4;
	int outputSlot = 5;

	ItemStack input = inv.getItem(inputSlot);
	ItemStack output = inv.getItem(outputSlot);

	if (input != null && !input.equals(new ItemStack(Items.AIR), true)) {
	    Set<Recipe<?>> recipes = ElectrodynamicsRecipe.findRecipesbyType(NuclearScienceRecipeInit.FISSION_REACTOR_TYPE, level);
	    for (Recipe<?> iRecipe : recipes) {
		O2ORecipe recipe = (O2ORecipe) iRecipe;
		if (recipe.matchesRecipe(input)) {
		    if (output.isEmpty()) {
			inv.setItem(outputSlot, recipe.getResultItem().copy());
			input.shrink(((CountableIngredient) recipe.getIngredients().get(0)).getStackSize());
		    } else if (output.getCount() <= output.getMaxStackSize() + recipe.getResultItem().getCount()) {
			output.grow(recipe.getResultItem().getCount());
			input.shrink(((CountableIngredient) recipe.getIngredients().get(0)).getStackSize());
		    }
		}
	    }
	}
    }
}
