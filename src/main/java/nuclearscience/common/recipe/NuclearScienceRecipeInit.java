package nuclearscience.common.recipe;

import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import nuclearscience.common.recipe.categories.fluid3items2item.Fluid3Items2ItemRecipeTypes;
import nuclearscience.common.recipe.categories.fluid3items2item.specificmachines.MSRFuelPreProcessorRecipe;
import nuclearscience.common.recipe.categories.fluiditem2fluid.FluidItem2FluidRecipeTypes;
import nuclearscience.common.recipe.categories.fluiditem2fluid.specificmachines.NuclearBoilerRecipe;
import nuclearscience.common.recipe.categories.fluiditem2item.FluidItem2ItemRecipeTypes;
import nuclearscience.common.recipe.categories.fluiditem2item.specificmachines.ChemicalExtractorRecipe;
import nuclearscience.common.recipe.categories.fluiditem2item.specificmachines.RadioactiveProcessorRecipe;
import nuclearscience.common.recipe.categories.o2o.O2ORecipeTypes;
import nuclearscience.common.recipe.categories.o2o.specificmachines.FissionReactorRecipe;
import nuclearscience.common.recipe.categories.o2o.specificmachines.FuelReprocessorRecipe;

public class NuclearScienceRecipeInit {

    // Deferred Register
    public static DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS,
	    nuclearscience.References.ID);

    /* RECIPE TYPES */

    // O2O
    public static final IRecipeType<FissionReactorRecipe> FISSION_REACTOR_TYPE = ElectrodynamicsRecipeInit
	    .registerType(FissionReactorRecipe.RECIPE_ID);
    public static final IRecipeType<FuelReprocessorRecipe> FUEL_REPROCESSOR_TYPE = ElectrodynamicsRecipeInit
	    .registerType(FuelReprocessorRecipe.RECIPE_ID);

    // FLUIDITEM2FLUID
    public static final IRecipeType<NuclearBoilerRecipe> NUCLEAR_BOILER_TYPE = ElectrodynamicsRecipeInit.registerType(NuclearBoilerRecipe.RECIPE_ID);

    // FLUIDITEM2ITEM
    public static final IRecipeType<ChemicalExtractorRecipe> CHEMICAL_EXTRACTOR_TYPE = ElectrodynamicsRecipeInit
	    .registerType(ChemicalExtractorRecipe.RECIPE_ID);
    public static final IRecipeType<RadioactiveProcessorRecipe> RADIOACTIVE_PROCESSOR_TYPE = ElectrodynamicsRecipeInit
	    .registerType(RadioactiveProcessorRecipe.RECIPE_ID);

    // FLUID3ITEMS2ITEM
    public static final IRecipeType<MSRFuelPreProcessorRecipe> MSR_FUEL_PREPROCESSOR_TYPE = ElectrodynamicsRecipeInit
	    .registerType(MSRFuelPreProcessorRecipe.RECIPE_ID);

    /* RECIPE SERIALIZERS */

    // O2O
    public static final RegistryObject<IRecipeSerializer<?>> FISSION_REACTOR_SERIALIZER = RECIPE_SERIALIZER
	    .register(FissionReactorRecipe.RECIPE_GROUP, () -> O2ORecipeTypes.FISSION_REACTOR_JSON_SERIALIZER);
    public static final RegistryObject<IRecipeSerializer<?>> FUEL_REPROCESSOR_SERIALIZER = RECIPE_SERIALIZER
	    .register(FuelReprocessorRecipe.RECIPE_GROUP, () -> O2ORecipeTypes.FUEL_REPROCESSOR_JSON_SERIALIZER);

    // FLUIDITEM2FLUID
    public static final RegistryObject<IRecipeSerializer<?>> NUCLEAR_BOILER_SERIALIZER = RECIPE_SERIALIZER.register(NuclearBoilerRecipe.RECIPE_GROUP,
	    () -> FluidItem2FluidRecipeTypes.NUCLEAR_BOILER_JSON_SERIALIZER);

    // FLUIDITEM2ITEM
    public static final RegistryObject<IRecipeSerializer<?>> CHEMICAL_EXTRACTOR_SERIALIZER = RECIPE_SERIALIZER
	    .register(ChemicalExtractorRecipe.RECIPE_GROUP, () -> FluidItem2ItemRecipeTypes.CHEMICAL_EXTRACTOR_JSON_SERIALIZER);
    public static final RegistryObject<IRecipeSerializer<?>> RADIOACTIVE_PROCESSOR_SERIALIZER = RECIPE_SERIALIZER
	    .register(RadioactiveProcessorRecipe.RECIPE_GROUP, () -> FluidItem2ItemRecipeTypes.RADIOACTIVE_PROCESSOR_JSON_SERIALIZER);

    // FLUID3ITEMS2ITEM
    public static final RegistryObject<IRecipeSerializer<?>> MSR_FUEL_PREPROCESSOR_SERIALIZER = RECIPE_SERIALIZER
	    .register(MSRFuelPreProcessorRecipe.RECIPE_GROUP, () -> Fluid3Items2ItemRecipeTypes.MSR_FUEL_PREPROCESSOR_JSON_SERIALIZER);

}
