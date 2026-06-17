package nuclearscience.registers;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import nuclearscience.NuclearScience;
import nuclearscience.common.recipe.categories.fluiditem2gas.NuclearBoilerRecipe;
import nuclearscience.common.recipe.categories.fluiditem2item.ChemicalExtractorRecipe;
import nuclearscience.common.recipe.categories.fluiditem2item.MSRFuelPreProcessorRecipe;
import nuclearscience.common.recipe.categories.fluiditem2item.RadioactiveProcessorRecipe;
import nuclearscience.common.recipe.categories.item2item.FissionReactorRecipe;
import nuclearscience.common.recipe.categories.item2item.FuelReprocessorRecipe;
import voltaic.common.recipe.VoltaicRecipeType;
import voltaic.common.recipe.categories.fluiditem2gas.FluidItem2GasRecipeSerializer;
import voltaic.common.recipe.categories.fluiditem2item.FluidItem2ItemRecipeSerializer;
import voltaic.common.recipe.categories.item2item.Item2ItemRecipeSerializer;

public class NuclearScienceRecipies {

    // Deferred Register
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER = DeferredRegister
	    .create(Registries.RECIPE_SERIALIZER, NuclearScience.ID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE,
	    NuclearScience.ID);

    /* RECIPE TYPES */

    // ITEM2ITEM
    public static final DeferredHolder<RecipeType<?>, RecipeType<FissionReactorRecipe>> FISSION_REACTOR_TYPE = RECIPE_TYPES
	    .register(FissionReactorRecipe.RECIPE_GROUP, VoltaicRecipeType::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<FuelReprocessorRecipe>> FUEL_REPROCESSOR_TYPE = RECIPE_TYPES
	    .register(FuelReprocessorRecipe.RECIPE_GROUP, VoltaicRecipeType::new);

    // FLUIDITEM2GAS
    public static final DeferredHolder<RecipeType<?>, RecipeType<NuclearBoilerRecipe>> NUCLEAR_BOILER_TYPE = RECIPE_TYPES
	    .register(NuclearBoilerRecipe.RECIPE_GROUP, VoltaicRecipeType::new);

    // FLUIDITEM2ITEM
    public static final DeferredHolder<RecipeType<?>, RecipeType<ChemicalExtractorRecipe>> CHEMICAL_EXTRACTOR_TYPE = RECIPE_TYPES
	    .register(ChemicalExtractorRecipe.RECIPE_GROUP, VoltaicRecipeType::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<RadioactiveProcessorRecipe>> RADIOACTIVE_PROCESSOR_TYPE = RECIPE_TYPES
	    .register(RadioactiveProcessorRecipe.RECIPE_GROUP, VoltaicRecipeType::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<MSRFuelPreProcessorRecipe>> MSR_FUEL_PREPROCESSOR_TYPE = RECIPE_TYPES
	    .register(MSRFuelPreProcessorRecipe.RECIPE_GROUP, VoltaicRecipeType::new);

    /* RECIPE SERIALIZERS */

    // O2O
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> FISSION_REACTOR_SERIALIZER = RECIPE_SERIALIZER
	    .register(FissionReactorRecipe.RECIPE_GROUP,
		    () -> new Item2ItemRecipeSerializer<>(FissionReactorRecipe::new));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> FUEL_REPROCESSOR_SERIALIZER = RECIPE_SERIALIZER
	    .register(FuelReprocessorRecipe.RECIPE_GROUP,
		    () -> new Item2ItemRecipeSerializer<>(FuelReprocessorRecipe::new));

    // FLUIDITEM2GAS
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> NUCLEAR_BOILER_SERIALIZER = RECIPE_SERIALIZER
	    .register(NuclearBoilerRecipe.RECIPE_GROUP,
		    () -> new FluidItem2GasRecipeSerializer<>(NuclearBoilerRecipe::new));

    // FLUIDITEM2ITEM
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> CHEMICAL_EXTRACTOR_SERIALIZER = RECIPE_SERIALIZER
	    .register(ChemicalExtractorRecipe.RECIPE_GROUP,
		    () -> new FluidItem2ItemRecipeSerializer<>(ChemicalExtractorRecipe::new));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> RADIOACTIVE_PROCESSOR_SERIALIZER = RECIPE_SERIALIZER
	    .register(RadioactiveProcessorRecipe.RECIPE_GROUP,
		    () -> new FluidItem2ItemRecipeSerializer<>(RadioactiveProcessorRecipe::new));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> MSR_FUEL_PREPROCESSOR_SERIALIZER = RECIPE_SERIALIZER
	    .register(MSRFuelPreProcessorRecipe.RECIPE_GROUP,
		    () -> new FluidItem2ItemRecipeSerializer<>(MSRFuelPreProcessorRecipe::new));

}
