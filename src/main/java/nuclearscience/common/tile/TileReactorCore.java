package nuclearscience.common.tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import electrodynamics.common.recipe.ElectrodynamicsRecipe;
import electrodynamics.common.recipe.categories.o2o.O2ORecipe;
import electrodynamics.common.recipe.recipeutils.CountableIngredient;
import electrodynamics.prefab.tile.GenericTileTicking;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.object.Location;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.Explosion.Mode;
import nuclearscience.DeferredRegisters;
import nuclearscience.api.radiation.RadiationSystem;
import nuclearscience.common.inventory.container.ContainerReactorCore;
import nuclearscience.common.recipe.NuclearScienceRecipeInit;
import nuclearscience.common.settings.Constants;

public class TileReactorCore extends GenericTileTicking {
    public static final int MELTDOWN_TEMPERATURE_ACTUAL = 5611;
    public static final int MELTDOWN_TEMPERATURE_CALC = 4407;
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

    public TileReactorCore() {
	super(DeferredRegisters.TILE_REACTORCORE.get());
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
	    ItemStack stack = inv.getStackInSlot(i);
	    fuelCount += stack.getItem() == DeferredRegisters.ITEM_FUELLEUO2.get() ? 2
		    : stack.getItem() == DeferredRegisters.ITEM_FUELHEUO2.get() ? 3 : 0;
	}
	hasDeuterium = !inv.getStackInSlot(4).isEmpty();

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
	    TileEntity tile = world.getTileEntity(pos.down());
	    int insertion = 0;
	    if (tile instanceof TileControlRodAssembly) {
		TileControlRodAssembly assembly = (TileControlRodAssembly) tile;
		insertion = assembly.insertion;
	    }
	    double insertDecimal = (100 - insertion) / 100.0;
	    if (world.rand.nextFloat() < insertDecimal) {
		for (int slot = 0; slot < 4; slot++) {
		    ItemStack fuelRod = inv.getStackInSlot(slot);
		    if (fuelRod.getDamage() >= fuelRod.getMaxDamage()) {
			inv.setInventorySlotContents(slot, new ItemStack(DeferredRegisters.ITEM_FUELSPENT.get()));
		    }
		    fuelRod.setDamage((int) (fuelRod.getDamage() + 1 + Math.round(temperature) / MELTDOWN_TEMPERATURE_CALC));
		}
	    }
	    temperature += (MELTDOWN_TEMPERATURE_CALC * insertDecimal * (0.25 * (fuelCount / 2.0) + world.rand.nextDouble() / 5.0) - temperature)
		    / (200 + 20 * (hasWater ? 4.0 : 1));
	    if (temperature > MELTDOWN_TEMPERATURE_ACTUAL + world.rand.nextInt(50) && fuelCount > 0) {
		ticksOverheating++;
		// Implement some alarm sounds at this time
		if (ticksOverheating > 10 * 20) {
		    meltdown();
		}
	    }
	    if (world.getWorldInfo().getGameTime() % 10 == 0) {
		Location source = new Location(pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f);
		double totstrength = temperature * 10;
		double range = Math.sqrt(totstrength) / (5 * Math.sqrt(2)) * 2;
		AxisAlignedBB bb = AxisAlignedBB.withSizeAtOrigin(range, range, range);
		bb = bb.offset(new Vector3d(source.x(), source.y(), source.z()));
		List<LivingEntity> list = world.getEntitiesWithinAABB(LivingEntity.class, bb);
		for (LivingEntity living : list) {
		    RadiationSystem.applyRadiation(living, source, totstrength);
		}
	    }
	} else {
	    ticksOverheating = 0;
	}
	temperature = Math.max(AIR_TEMPERATURE, temperature);
	if (fuelCount > 0 && world.rand.nextFloat() < 1 / (1200.0 * MELTDOWN_TEMPERATURE_CALC / temperature)) {
	    processFissReact(inv);
	}
    }

    protected void tickCommon(ComponentTickable tickable) {
	ticks = ticks > Integer.MAX_VALUE - 2 ? 0 : ticks + 1;
	if (ticks % 20 == 0) {
	    world.getLightManager().checkBlock(pos);
	}
	produceSteam();
    }

    public void meltdown() {
	if (!world.isRemote) {
	    int radius = STEAM_GEN_DIAMETER / 2;
	    world.setBlockState(pos, getBlockState().with(BlockStateProperties.WATERLOGGED, false));
	    for (int i = -radius; i <= radius; i++) {
		for (int j = -radius; j <= radius; j++) {
		    for (int k = -radius; k <= radius; k++) {
			BlockPos ppos = new BlockPos(pos.getX() + i, pos.getY() + j, pos.getZ() + k);
			BlockState state = world.getBlockState(ppos);
			if (state.getBlock() == Blocks.WATER) {
			    world.setBlockState(ppos, Blocks.AIR.getDefaultState());
			}
		    }
		}
	    }
	    world.setBlockState(pos, Blocks.AIR.getDefaultState());

	    Explosion actual = new Explosion(world, null, pos.getX(), pos.getY(), pos.getZ(), 20, new ArrayList<>());
	    radius = 3 * fuelCount;
	    for (int i = -radius; i <= radius; i++) {
		for (int j = -radius; j <= radius; j++) {
		    for (int k = -radius; k <= radius; k++) {
			BlockPos ppos = new BlockPos(pos.getX() + i, pos.getY() + j, pos.getZ() + k);
			BlockState state = world.getBlockState(ppos);
			if (state.getBlock().getExplosionResistance(state, world, ppos, actual) < radius) {
			    double distance = Math.sqrt(i * i + j * j + k * k);
			    if (distance < radius && world.rand.nextFloat() < 1 - 0.0001 * distance * distance * distance
				    && world.rand.nextFloat() < 0.9) {
				world.getBlockState(ppos).onBlockExploded(world, ppos, actual);
			    }
			}
		    }
		}
	    }
	    world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 20, Mode.DESTROY);
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
			if (!world.isRemote && world.rand.nextFloat() < temperature
				/ (MELTDOWN_TEMPERATURE_CALC * 20.0 * STEAM_GEN_DIAMETER * STEAM_GEN_DIAMETER * STEAM_GEN_HEIGHT)) {
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
				    turbine.addSteam(
					    (int) (Constants.FISSIONREACTOR_MAXENERGYTARGET
						    / (STEAM_GEN_DIAMETER * STEAM_GEN_DIAMETER * 20.0 * (MELTDOWN_TEMPERATURE_ACTUAL / temperature))),
					    (int) temperature);
				}
				if (world.rand.nextFloat() < temperature
					/ (MELTDOWN_TEMPERATURE_CALC * 20.0 * STEAM_GEN_DIAMETER * STEAM_GEN_DIAMETER * STEAM_GEN_HEIGHT)) {
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
			    } else if (world.isRemote && world.rand.nextFloat() < temperature / (MELTDOWN_TEMPERATURE_ACTUAL * 3)) {
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
	ComponentInventory inv = getComponent(ComponentType.Inventory);
	tag.putBoolean("hasDeuterium", hasDeuterium);
	tag.putDouble("temperature", temperature);
	tag.putInt("fuelCount", inv.count(DeferredRegisters.ITEM_FUELHEUO2.get()) + inv.count(DeferredRegisters.ITEM_FUELLEUO2.get()));
    }

    protected void readCustomPacket(CompoundNBT nbt) {
	hasDeuterium = nbt.getBoolean("hasDeuterium");
	temperature = nbt.getDouble("temperature");
	fuelCount = nbt.getInt("fuelCount");
    }

    public void processFissReact(ComponentInventory inv) {

	int inputSlot = 4;
	int outputSlot = 5;

	ItemStack input = inv.getStackInSlot(inputSlot);
	ItemStack output = inv.getStackInSlot(outputSlot);

	if (input != null && !input.equals(new ItemStack(Items.AIR), true)) {
	    Set<IRecipe<?>> recipes = ElectrodynamicsRecipe.findRecipesbyType(NuclearScienceRecipeInit.FISSION_REACTOR_TYPE, world);
	    for (IRecipe<?> iRecipe : recipes) {
		O2ORecipe recipe = (O2ORecipe) iRecipe;
		if (recipe.matchesRecipe(input)) {
		    if (output.isEmpty()) {
			inv.setInventorySlotContents(outputSlot, recipe.getRecipeOutput().copy());
			input.shrink(((CountableIngredient) recipe.getIngredients().get(0)).getStackSize());
		    } else if (output.getCount() <= output.getMaxStackSize() + recipe.getRecipeOutput().getCount()) {
			output.grow(recipe.getRecipeOutput().getCount());
			input.shrink(((CountableIngredient) recipe.getIngredients().get(0)).getStackSize());
		    }
		}
	    }
	}
    }
}
