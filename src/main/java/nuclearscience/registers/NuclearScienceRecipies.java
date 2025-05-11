package nuclearscience.registers;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import nuclearscience.NuclearScience;
import nuclearscience.common.recipe.categories.fluiditem2fluid.NuclearBoilerRecipe;
import nuclearscience.common.recipe.categories.fluiditem2item.ChemicalExtractorRecipe;
import nuclearscience.common.recipe.categories.fluiditem2item.MSRFuelPreProcessorRecipe;
import nuclearscience.common.recipe.categories.fluiditem2item.RadioactiveProcessorRecipe;
import nuclearscience.common.recipe.categories.item2item.FissionReactorRecipe;
import nuclearscience.common.recipe.categories.item2item.FuelReprocessorRecipe;
import voltaic.common.recipe.VoltaicRecipeType;
import voltaic.common.recipe.categories.fluiditem2fluid.FluidItem2FluidRecipeSerializer;
import voltaic.common.recipe.categories.fluiditem2item.FluidItem2ItemRecipeSerializer;
import voltaic.common.recipe.categories.item2item.Item2ItemRecipeSerializer;

public class NuclearScienceRecipies {

	// Deferred Register
	public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, NuclearScience.ID);

	/* RECIPE TYPES */

	// ITEM2ITEM
	public static final IRecipeType<FissionReactorRecipe> FISSION_REACTOR_TYPE = registerType(FissionReactorRecipe.RECIPE_ID, new VoltaicRecipeType<>());
	public static final IRecipeType<FuelReprocessorRecipe> FUEL_REPROCESSOR_TYPE = registerType(FuelReprocessorRecipe.RECIPE_ID, new VoltaicRecipeType<>());

	// FLUIDITEM2FLUID
	public static final IRecipeType<NuclearBoilerRecipe> NUCLEAR_BOILER_TYPE = registerType(NuclearBoilerRecipe.RECIPE_ID, new VoltaicRecipeType<>());

	// FLUIDITEM2ITEM
	public static final IRecipeType<ChemicalExtractorRecipe> CHEMICAL_EXTRACTOR_TYPE = registerType(ChemicalExtractorRecipe.RECIPE_ID, new VoltaicRecipeType<>());
	public static final IRecipeType<RadioactiveProcessorRecipe> RADIOACTIVE_PROCESSOR_TYPE = registerType(RadioactiveProcessorRecipe.RECIPE_ID, new VoltaicRecipeType<>());
	public static final IRecipeType<MSRFuelPreProcessorRecipe> MSR_FUEL_PREPROCESSOR_TYPE = registerType(MSRFuelPreProcessorRecipe.RECIPE_ID, new VoltaicRecipeType<>());

	/* RECIPE SERIALIZERS */

	// O2O
	public static final RegistryObject<IRecipeSerializer<?>> FISSION_REACTOR_SERIALIZER = RECIPE_SERIALIZER.register(FissionReactorRecipe.RECIPE_GROUP, () -> new Item2ItemRecipeSerializer<>(FissionReactorRecipe::new));
	public static final RegistryObject<IRecipeSerializer<?>> FUEL_REPROCESSOR_SERIALIZER = RECIPE_SERIALIZER.register(FuelReprocessorRecipe.RECIPE_GROUP, () -> new Item2ItemRecipeSerializer<>(FuelReprocessorRecipe::new));

	// FLUIDITEM2GAS
	public static final RegistryObject<IRecipeSerializer<?>> NUCLEAR_BOILER_SERIALIZER = RECIPE_SERIALIZER.register(NuclearBoilerRecipe.RECIPE_GROUP, () -> new FluidItem2FluidRecipeSerializer<>(NuclearBoilerRecipe::new));

	// FLUIDITEM2ITEM
	public static final RegistryObject<IRecipeSerializer<?>> CHEMICAL_EXTRACTOR_SERIALIZER = RECIPE_SERIALIZER.register(ChemicalExtractorRecipe.RECIPE_GROUP, () -> new FluidItem2ItemRecipeSerializer<>(ChemicalExtractorRecipe::new));
	public static final RegistryObject<IRecipeSerializer<?>> RADIOACTIVE_PROCESSOR_SERIALIZER = RECIPE_SERIALIZER.register(RadioactiveProcessorRecipe.RECIPE_GROUP, () -> new FluidItem2ItemRecipeSerializer<>(RadioactiveProcessorRecipe::new));
	public static final RegistryObject<IRecipeSerializer<?>> MSR_FUEL_PREPROCESSOR_SERIALIZER = RECIPE_SERIALIZER.register(MSRFuelPreProcessorRecipe.RECIPE_GROUP, () -> new FluidItem2ItemRecipeSerializer<>(MSRFuelPreProcessorRecipe::new));
	
	private static <T extends IRecipeType<?>> T registerType(ResourceLocation recipeTypeId, VoltaicRecipeType<?> type) {
		return (T) Registry.register(Registry.RECIPE_TYPE, recipeTypeId, type);
	}
	
	public static void init() {
		
	}

}
