package nuclearscience.registers;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
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
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, NuclearScience.ID);
	public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, NuclearScience.ID);

	/* RECIPE TYPES */

	// ITEM2ITEM
	public static final RegistryObject<RecipeType<FissionReactorRecipe>> FISSION_REACTOR_TYPE = RECIPE_TYPES.register(FissionReactorRecipe.RECIPE_GROUP, VoltaicRecipeType::new);
	public static final RegistryObject<RecipeType<FuelReprocessorRecipe>> FUEL_REPROCESSOR_TYPE = RECIPE_TYPES.register(FuelReprocessorRecipe.RECIPE_GROUP, VoltaicRecipeType::new);

	// FLUIDITEM2GAS
	public static final RegistryObject<RecipeType<NuclearBoilerRecipe>> NUCLEAR_BOILER_TYPE = RECIPE_TYPES.register(NuclearBoilerRecipe.RECIPE_GROUP, VoltaicRecipeType::new);

	// FLUIDITEM2ITEM
	public static final RegistryObject<RecipeType<ChemicalExtractorRecipe>> CHEMICAL_EXTRACTOR_TYPE = RECIPE_TYPES.register(ChemicalExtractorRecipe.RECIPE_GROUP, VoltaicRecipeType::new);
	public static final RegistryObject<RecipeType<RadioactiveProcessorRecipe>> RADIOACTIVE_PROCESSOR_TYPE = RECIPE_TYPES.register(RadioactiveProcessorRecipe.RECIPE_GROUP, VoltaicRecipeType::new);
	public static final RegistryObject<RecipeType<MSRFuelPreProcessorRecipe>> MSR_FUEL_PREPROCESSOR_TYPE = RECIPE_TYPES.register(MSRFuelPreProcessorRecipe.RECIPE_GROUP, VoltaicRecipeType::new);

	/* RECIPE SERIALIZERS */

	// O2O
	public static final RegistryObject<RecipeSerializer<?>> FISSION_REACTOR_SERIALIZER = RECIPE_SERIALIZER.register(FissionReactorRecipe.RECIPE_GROUP, () -> new Item2ItemRecipeSerializer<>(FissionReactorRecipe::new));
	public static final RegistryObject<RecipeSerializer<?>> FUEL_REPROCESSOR_SERIALIZER = RECIPE_SERIALIZER.register(FuelReprocessorRecipe.RECIPE_GROUP, () -> new Item2ItemRecipeSerializer<>(FuelReprocessorRecipe::new));

	// FLUIDITEM2GAS
	public static final RegistryObject<RecipeSerializer<?>> NUCLEAR_BOILER_SERIALIZER = RECIPE_SERIALIZER.register(NuclearBoilerRecipe.RECIPE_GROUP, () -> new FluidItem2GasRecipeSerializer<>(NuclearBoilerRecipe::new));

	// FLUIDITEM2ITEM
	public static final RegistryObject<RecipeSerializer<?>> CHEMICAL_EXTRACTOR_SERIALIZER = RECIPE_SERIALIZER.register(ChemicalExtractorRecipe.RECIPE_GROUP, () -> new FluidItem2ItemRecipeSerializer<>(ChemicalExtractorRecipe::new));
	public static final RegistryObject<RecipeSerializer<?>> RADIOACTIVE_PROCESSOR_SERIALIZER = RECIPE_SERIALIZER.register(RadioactiveProcessorRecipe.RECIPE_GROUP, () -> new FluidItem2ItemRecipeSerializer<>(RadioactiveProcessorRecipe::new));
	public static final RegistryObject<RecipeSerializer<?>> MSR_FUEL_PREPROCESSOR_SERIALIZER = RECIPE_SERIALIZER.register(MSRFuelPreProcessorRecipe.RECIPE_GROUP, () -> new FluidItem2ItemRecipeSerializer<>(MSRFuelPreProcessorRecipe::new));

}
