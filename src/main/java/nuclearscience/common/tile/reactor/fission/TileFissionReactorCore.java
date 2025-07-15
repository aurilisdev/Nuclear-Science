package nuclearscience.common.tile.reactor.fission;

import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import nuclearscience.api.turbine.ISteamReceiver;
import nuclearscience.common.inventory.container.ContainerFissionReactorCore;
import nuclearscience.registers.NuclearScienceRecipies;
import nuclearscience.common.settings.NuclearConstants;
import nuclearscience.common.tile.reactor.TileControlRod;
import nuclearscience.registers.NuclearScienceBlocks;
import nuclearscience.registers.NuclearScienceItems;
import nuclearscience.registers.NuclearScienceSounds;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.api.radiation.RadiationSystem;
import voltaic.api.radiation.SimpleRadiationSource;
import voltaic.common.recipe.VoltaicRecipe;
import voltaic.common.recipe.categories.item2item.Item2ItemRecipe;
import voltaic.common.recipe.recipeutils.CountableIngredient;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentInventory;
import voltaic.prefab.tile.components.type.ComponentPacketHandler;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.object.CachedTileOutput;
import voltaic.registers.VoltaicDamageTypes;

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
    public static final int STEAM_GEN_DIAMETER = 5;
    public static final int STEAM_GEN_HEIGHT = 2;
    private ISteamReceiver[][][] cachedReceivers = new ISteamReceiver[STEAM_GEN_DIAMETER][STEAM_GEN_HEIGHT][STEAM_GEN_DIAMETER];
    public SingleProperty<Double> temperature = property(new SingleProperty<>(PropertyTypes.DOUBLE, "temperature", AIR_TEMPERATURE));
    public SingleProperty<Integer> fuelCount = property(new SingleProperty<>(PropertyTypes.INTEGER, "fuelCount", 0));
    public SingleProperty<Boolean> hasDeuterium = property(new SingleProperty<>(PropertyTypes.BOOLEAN, "hasDeuterium", false));
    private CachedTileOutput controlRodCache;
    private int ticksOverheating = 0;

    private List<VoltaicRecipe> cachedRecipes;

    public TileFissionReactorCore(BlockPos pos, BlockState state) {
        super(NuclearScienceTiles.TILE_REACTORCORE.get(), pos, state);

        addComponent(new ComponentTickable(this).tickCommon(this::tickCommon).tickServer(this::tickServer));
        addComponent(new ComponentPacketHandler(this));
        addComponent(new ComponentInventory(this, ComponentInventory.InventoryBuilder.newInv().inputs(5).outputs(1)).setSlotsByDirection(BlockEntityUtils.MachineDirection.TOP, 0, 1, 2, 3, 4).setSlotsByDirection(BlockEntityUtils.MachineDirection.BOTTOM, 5));
        addComponent(new ComponentContainerProvider("reactorcore", this).createMenu((id, player) -> new ContainerFissionReactorCore(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));
    }

    protected void tickServer(ComponentTickable tickable) {
    	
    	double totstrength = temperature.getValue() * 10;

        int range = (int) (Math.sqrt(totstrength) / (5 * Math.sqrt(2)) * 2);

        if(range > 0 && totstrength > 0 && temperature.getValue() > AIR_TEMPERATURE) {
            RadiationSystem.addRadiationSource(getLevel(), new SimpleRadiationSource(totstrength, 1, range, true, 30, getBlockPos(), true, false));
        }

        double decrease = (temperature.getValue() - AIR_TEMPERATURE) / 3000.0;

        if (fuelCount.getValue() == 0) {

            decrease *= 25;

        }

        boolean hasWater = !getBlockState().getFluidState().isEmpty();

        if (hasWater) {

            decrease += (temperature.getValue() - WATER_TEMPERATURE) / 5000.0;

        }

        if (decrease != 0) {

            temperature.setValue(temperature.getValue() - (decrease < 0.001 && decrease > 0 ? 0.001 : decrease > -0.001 && decrease < 0 ? -0.001 : decrease));

        }

        ComponentInventory inv = getComponent(IComponentType.Inventory);

        if (fuelCount.getValue() > 0) {

            if(level.getRandom().nextFloat() < 0.01F) {
                SoundEvent sound = switch (level.random.nextIntBetweenInclusive(1, 6)) {
                    case 2 -> NuclearScienceSounds.SOUND_GEIGERCOUNTER_2.get();
                    case 3 -> NuclearScienceSounds.SOUND_GEIGERCOUNTER_3.get();
                    case 4 -> NuclearScienceSounds.SOUND_GEIGERCOUNTER_4.get();
                    case 5 -> NuclearScienceSounds.SOUND_GEIGERCOUNTER_5.get();
                    case 6 -> NuclearScienceSounds.SOUND_GEIGERCOUNTER_6.get();
                    default -> NuclearScienceSounds.SOUND_GEIGERCOUNTER_1.get();
                };
                level.playSound(null, getBlockPos(), sound, SoundSource.BLOCKS, 1.0F, 1.0F);
            }

            if (level.getLevelData().getGameTime() % 10 == 0 && temperature.getValue() > 100) {
                AABB bb = AABB.ofSize(new Vec3(getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ()), 4, 4, 4);
                List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, bb);
                for (LivingEntity living : list) {

                    if (!level.getBlockState(living.getOnPos()).getFluidState().is(FluidTags.WATER)) {
                        continue;
                    }

                    living.hurt(living.damageSources().drown(), 3);
                }
            }

            if(controlRodCache == null) {
                controlRodCache = new CachedTileOutput(getLevel(), worldPosition.below());
            }

            if(tickable.getTicks() % 10 == 0 && !controlRodCache.valid()) {
                controlRodCache.update(worldPosition.below());
            }

            int insertion = 0;

            if (controlRodCache.valid() && controlRodCache.getSafe() instanceof IFissionControlRod rod) {

                insertion = rod.getInsertion();

            }

            double insertDecimal = 1.0 - insertion / (double) TileControlRod.MAX_EXTENSION;

            if (level.random.nextFloat() < insertDecimal) {

                for (int slot = 0; slot < FUEL_ROD_COUNT; slot++) {

                    ItemStack fuelRod = inv.getItem(slot);

                    fuelRod.setDamageValue((int) (fuelRod.getDamageValue() + 1 + Math.round(temperature.getValue()) / MELTDOWN_TEMPERATURE_CALC));

                    if (!fuelRod.isEmpty() && fuelRod.getDamageValue() >= fuelRod.getMaxDamage()) {

                        inv.setItem(slot, new ItemStack(NuclearScienceItems.ITEM_FUELSPENT.get()));

                    }

                }

            }

            temperature.setValue(temperature.getValue() + (MELTDOWN_TEMPERATURE_CALC * insertDecimal * (0.25 * (fuelCount.getValue() / 2.0) + level.random.nextDouble() / 5.0) - temperature.getValue()) / (200 + 20 * (hasWater ? 4.0 : 1)));

            if (temperature.getValue() > MELTDOWN_TEMPERATURE_ACTUAL + level.random.nextInt(50) && fuelCount.getValue() > 0) {

                ticksOverheating++;

                // Implement some alarm sounds at this time
                if (ticksOverheating > 10 * 20) {

                    meltdown();

                }

            }

        } else {

            ticksOverheating = 0;

        }

        temperature.setValue(Math.max(AIR_TEMPERATURE, temperature.getValue()));

        if (hasDeuterium.getValue() && fuelCount.getValue() > 0 && level.random.nextFloat() < 1 / (1200.0 * MELTDOWN_TEMPERATURE_CALC / temperature.getValue())) {

            processFissReact(inv);

        }

    }

    protected void tickCommon(ComponentTickable tickable) {

        if (tickable.getTicks() % 20 == 0) {
            level.getLightEngine().checkBlock(worldPosition);
        }

        produceSteam();
    }

    public void meltdown() {

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

        Explosion actual = new Explosion(level, null, level.damageSources().source(VoltaicDamageTypes.RADIATION), null, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), 20, true, BlockInteraction.KEEP);

        radius = 3 * fuelCount.getValue();
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
        level.setBlockAndUpdate(worldPosition, NuclearScienceBlocks.BLOCK_MELTEDREACTOR.get().defaultBlockState());
    }

    protected void produceSteam() {
        if (temperature.getValue() <= 400) {
            return;
        }
        for (int i = 0; i < STEAM_GEN_DIAMETER; i++) {
            for (int j = 0; j < STEAM_GEN_HEIGHT; j++) {
                for (int k = 0; k < STEAM_GEN_DIAMETER; k++) {
                    boolean isReactor2d = i - STEAM_GEN_DIAMETER / 2 == 0 && k - STEAM_GEN_DIAMETER / 2 == 0;
                    if (isReactor2d && j == 0) {
                        if (!level.isClientSide && level.random.nextFloat() < temperature.getValue() / (MELTDOWN_TEMPERATURE_CALC * 20.0 * STEAM_GEN_DIAMETER * STEAM_GEN_DIAMETER * STEAM_GEN_HEIGHT)) {
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

                    if (!isStillWater(getLevel(), offpos)) {
                        continue;
                    }

                    boolean isFaceWater = isStillWater(level, new BlockPos(offsetX, worldPosition.getY(), worldPosition.getZ())) || isStillWater(level, new BlockPos(worldPosition.getX(), worldPosition.getY(), offsetZ)) || isReactor2d;
                    if (!isFaceWater) {
                        continue;
                    }

                    if (!level.isClientSide) {
                        ISteamReceiver turbine = cachedReceivers[i][j][k];
                        if (turbine != null) {
                            if (turbine.isStillValid()) {
                                cachedReceivers[i][j][k] = null;
                            }
                            double temp = temperature.getValue();
                            turbine.receiveSteam((int) temp, (int) (NuclearConstants.FISSIONREACTOR_MAXENERGYTARGET / (STEAM_GEN_DIAMETER * STEAM_GEN_DIAMETER * 20.0 * (MELTDOWN_TEMPERATURE_ACTUAL / temperature.getValue()))));
                        }
                        if (level.random.nextFloat() < temperature.getValue() / (MELTDOWN_TEMPERATURE_CALC * 20.0 * STEAM_GEN_DIAMETER * STEAM_GEN_DIAMETER * STEAM_GEN_HEIGHT)) {
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
                    } else if (level.isClientSide && level.random.nextFloat() < temperature.getValue() / (MELTDOWN_TEMPERATURE_ACTUAL * 3)) {
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

    public void processFissReact(ComponentInventory inv) {

        ItemStack input = inv.getItem(DUETERIUM_SLOT);
        ItemStack output = inv.getItem(OUTPUT_SLOT);

        if (input.isEmpty()) {

            return;

        }

        if (cachedRecipes == null || cachedRecipes.isEmpty()) {

            cachedRecipes = VoltaicRecipe.findRecipesbyType(NuclearScienceRecipies.FISSION_REACTOR_TYPE.get(), level);
        }

        for (VoltaicRecipe iRecipe : cachedRecipes) {

            Item2ItemRecipe recipe = (Item2ItemRecipe) iRecipe;

            for (CountableIngredient ing : recipe.getCountedIngredients()) {

                if (ing.test(input)) {

                    if (output.isEmpty()) {

                        inv.setItem(OUTPUT_SLOT, recipe.getItemRecipeOutput().copy());

                        input.shrink(recipe.getCountedIngredients().get(0).getStackSize());

                    } else if (output.getCount() <= output.getMaxStackSize() + recipe.getItemRecipeOutput().getCount()) {

                        output.grow(recipe.getItemRecipeOutput().getCount());

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

            fuelCount.setValue(0);

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

                fuelCount.setValue(fuelCount.getValue() + fuelValue);
            }

        }

        if (slot == -1 || slot == DUETERIUM_SLOT) {

            hasDeuterium.setValue(!inv.getItem(DUETERIUM_SLOT).isEmpty());

        }

    }

    public static boolean isStillWater(Level world, BlockPos pos) {

        FluidState fluidState = world.getFluidState(pos);

        return fluidState.is(FluidTags.WATER) && fluidState.isSource();

    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.putInt("ticksoverheating", ticksOverheating);
    }

    @Override
	public void load(CompoundTag compound) {
        super.load(compound);
        ticksOverheating = compound.getInt("ticksoverheating");
    }

    public static double getActualTemp(double temperature) {
        return temperature / 4.0 + AIR_TEMPERATURE;
    }
}
