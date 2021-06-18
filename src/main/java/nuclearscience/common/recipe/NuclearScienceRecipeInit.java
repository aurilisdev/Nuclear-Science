package nuclearscience.common.recipe;

import electrodynamics.common.recipe.ElectrodynamicsRecipeInit;
import nuclearscience.common.recipe.categories.fluiditem2fluid.FluidItem2FluidRecipeTypes;
import nuclearscience.common.recipe.categories.fluiditem2fluid.specificmachines.NuclearBoilerRecipe;
import nuclearscience.common.recipe.categories.fluiditem2item.FluidItem2ItemRecipeTypes;
import nuclearscience.common.recipe.categories.fluiditem2item.specificmachines.ChemicalExtractorRecipe;
import nuclearscience.common.recipe.categories.o2o.O2ORecipeTypes;
import nuclearscience.common.recipe.categories.o2o.specificmachines.FissionReactorRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class NuclearScienceRecipeInit {

	//Deferred Register
	public static DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZER = 
			DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS,nuclearscience.References.ID);
	
	
	/*RECIPE TYPES*/
	
	//O2O
	public static final IRecipeType<FissionReactorRecipe> FISSION_REACTOR_TYPE = ElectrodynamicsRecipeInit.registerType(FissionReactorRecipe.RECIPE_ID);
	
	//FLUIDITEM2ITEM
	public static final IRecipeType<NuclearBoilerRecipe> NUCLEAR_BOILER_TYPE = ElectrodynamicsRecipeInit.registerType(NuclearBoilerRecipe.RECIPE_ID);
	
	//FLUIDITEM2ITEM
	public static final IRecipeType<ChemicalExtractorRecipe> CHEMICAL_EXTRACTOR_TYPE = ElectrodynamicsRecipeInit.registerType(ChemicalExtractorRecipe.RECIPE_ID);
	
	
	/*RECIPE SERIALIZERS*/
	
	//O2O
	public static final RegistryObject<IRecipeSerializer<?>> FISSION_REACTOR_SERIALIZER =
			RECIPE_SERIALIZER.register(FissionReactorRecipe.RECIPE_GROUP,() -> O2ORecipeTypes.FISSION_REACTOR_JSON_SERIALIZER);
	
	//FLUIDITEM2FLUID
	public static final RegistryObject<IRecipeSerializer<?>> NUCLEAR_BOILER_SERIALIZER = 
			RECIPE_SERIALIZER.register(NuclearBoilerRecipe.RECIPE_GROUP,() -> FluidItem2FluidRecipeTypes.NUCLEAR_BOILER_JSON_SERIALIZER);
	
	//FLUIDITEM2ITEM
	public static final RegistryObject<IRecipeSerializer<?>> CHEMICAL_EXTRACTOR_SERIALIZER = 
			RECIPE_SERIALIZER.register(ChemicalExtractorRecipe.RECIPE_GROUP,() -> FluidItem2ItemRecipeTypes.CHEMICAL_EXTRACTOR_JSON_SERIALIZER);
	
	
}
