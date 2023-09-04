package nuclearscience.common.recipe;

import electrodynamics.common.recipe.ElectrodynamicsRecipeInit.CustomRecipeType;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import nuclearscience.References;
import nuclearscience.common.recipe.categories.fluiditem2gas.FluidItem2GasRecipeTypes;
import nuclearscience.common.recipe.categories.fluiditem2gas.specificmachines.NuclearBoilerRecipe;
import nuclearscience.common.recipe.categories.fluiditem2item.FluidItem2ItemRecipeTypes;
import nuclearscience.common.recipe.categories.fluiditem2item.specificmachines.ChemicalExtractorRecipe;
import nuclearscience.common.recipe.categories.fluiditem2item.specificmachines.MSRFuelPreProcessorRecipe;
import nuclearscience.common.recipe.categories.fluiditem2item.specificmachines.RadioactiveProcessorRecipe;
import nuclearscience.common.recipe.categories.item2item.Item2ItemRecipeTypes;
import nuclearscience.common.recipe.categories.item2item.specificmachines.FissionReactorRecipe;
import nuclearscience.common.recipe.categories.item2item.specificmachines.FuelReprocessorRecipe;

public class NuclearScienceRecipeInit {

	// Deferred Register
	public static DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, References.ID);
	public static DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, References.ID);

	/* RECIPE TYPES */

	// ITEM2ITEM
	public static final RegistryObject<CustomRecipeType<FissionReactorRecipe>> FISSION_REACTOR_TYPE = RECIPE_TYPES.register(FissionReactorRecipe.RECIPE_GROUP, CustomRecipeType::new);
	public static final RegistryObject<CustomRecipeType<FuelReprocessorRecipe>> FUEL_REPROCESSOR_TYPE = RECIPE_TYPES.register(FuelReprocessorRecipe.RECIPE_GROUP, CustomRecipeType::new);

	// FLUIDITEM2GAS
	public static final RegistryObject<CustomRecipeType<NuclearBoilerRecipe>> NUCLEAR_BOILER_TYPE = RECIPE_TYPES.register(NuclearBoilerRecipe.RECIPE_GROUP, CustomRecipeType::new);

	// FLUIDITEM2ITEM
	public static final RegistryObject<CustomRecipeType<ChemicalExtractorRecipe>> CHEMICAL_EXTRACTOR_TYPE = RECIPE_TYPES.register(ChemicalExtractorRecipe.RECIPE_GROUP, CustomRecipeType::new);
	public static final RegistryObject<CustomRecipeType<RadioactiveProcessorRecipe>> RADIOACTIVE_PROCESSOR_TYPE = RECIPE_TYPES.register(RadioactiveProcessorRecipe.RECIPE_GROUP, CustomRecipeType::new);
	public static final RegistryObject<CustomRecipeType<MSRFuelPreProcessorRecipe>> MSR_FUEL_PREPROCESSOR_TYPE = RECIPE_TYPES.register(MSRFuelPreProcessorRecipe.RECIPE_GROUP, CustomRecipeType::new);

	/* RECIPE SERIALIZERS */

	// O2O
	public static final RegistryObject<RecipeSerializer<?>> FISSION_REACTOR_SERIALIZER = RECIPE_SERIALIZER.register(FissionReactorRecipe.RECIPE_GROUP, () -> Item2ItemRecipeTypes.FISSION_REACTOR_JSON_SERIALIZER);
	public static final RegistryObject<RecipeSerializer<?>> FUEL_REPROCESSOR_SERIALIZER = RECIPE_SERIALIZER.register(FuelReprocessorRecipe.RECIPE_GROUP, () -> Item2ItemRecipeTypes.FUEL_REPROCESSOR_JSON_SERIALIZER);

	// FLUIDITEM2GAS
	public static final RegistryObject<RecipeSerializer<?>> NUCLEAR_BOILER_SERIALIZER = RECIPE_SERIALIZER.register(NuclearBoilerRecipe.RECIPE_GROUP, () -> FluidItem2GasRecipeTypes.NUCLEAR_BOILER_JSON_SERIALIZER);

	// FLUIDITEM2ITEM
	public static final RegistryObject<RecipeSerializer<?>> CHEMICAL_EXTRACTOR_SERIALIZER = RECIPE_SERIALIZER.register(ChemicalExtractorRecipe.RECIPE_GROUP, () -> FluidItem2ItemRecipeTypes.CHEMICAL_EXTRACTOR_JSON_SERIALIZER);
	public static final RegistryObject<RecipeSerializer<?>> RADIOACTIVE_PROCESSOR_SERIALIZER = RECIPE_SERIALIZER.register(RadioactiveProcessorRecipe.RECIPE_GROUP, () -> FluidItem2ItemRecipeTypes.RADIOACTIVE_PROCESSOR_JSON_SERIALIZER);
	public static final RegistryObject<RecipeSerializer<?>> MSR_FUEL_PREPROCESSOR_SERIALIZER = RECIPE_SERIALIZER.register(MSRFuelPreProcessorRecipe.RECIPE_GROUP, () -> FluidItem2ItemRecipeTypes.MSR_FUEL_PREPROCESSOR_JSON_SERIALIZER);

}
