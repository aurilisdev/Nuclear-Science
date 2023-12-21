package nuclearscience.compatibility.jei;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import electrodynamics.client.screen.tile.ScreenO2OProcessor;
import electrodynamics.compatibility.jei.ElectrodynamicsJEIPlugin;
import electrodynamics.compatibility.jei.recipecategories.utils.psuedorecipes.types.PsuedoItem2ItemRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import nuclearscience.client.screen.ScreenChemicalExtractor;
import nuclearscience.client.screen.ScreenFissionReactorCore;
import nuclearscience.client.screen.ScreenGasCentrifuge;
import nuclearscience.client.screen.ScreenMSRFuelPreProcessor;
import nuclearscience.client.screen.ScreenNuclearBoiler;
import nuclearscience.client.screen.ScreenParticleInjector;
import nuclearscience.client.screen.ScreenRadioactiveProcessor;
import nuclearscience.common.recipe.NuclearScienceRecipeInit;
import nuclearscience.common.recipe.categories.fluiditem2fluid.specificmachines.NuclearBoilerRecipe;
import nuclearscience.common.recipe.categories.fluiditem2item.specificmachines.ChemicalExtractorRecipe;
import nuclearscience.common.recipe.categories.fluiditem2item.specificmachines.MSRFuelPreProcessorRecipe;
import nuclearscience.common.recipe.categories.fluiditem2item.specificmachines.RadioactiveProcessorRecipe;
import nuclearscience.common.recipe.categories.item2item.specificmachines.FissionReactorRecipe;
import nuclearscience.common.recipe.categories.item2item.specificmachines.FuelReprocessorRecipe;
import nuclearscience.compatibility.jei.recipecategories.fluiditem2fluid.specificmachines.NuclearBoilerRecipeCategory;
import nuclearscience.compatibility.jei.recipecategories.fluiditem2item.specificmachines.ChemicalExtractorRecipeCategory;
import nuclearscience.compatibility.jei.recipecategories.fluiditem2item.specificmachines.MSRProcessorRecipeCategory;
import nuclearscience.compatibility.jei.recipecategories.fluiditem2item.specificmachines.RadioactiveProcessorRecipeCategory;
import nuclearscience.compatibility.jei.recipecategories.item2item.FissionReactorRecipeCategory;
import nuclearscience.compatibility.jei.recipecategories.item2item.FuelReprocessorRecipeCategory;
import nuclearscience.compatibility.jei.recipecategories.psuedo.specificmachines.GasCentrifugeRecipeCategory;
import nuclearscience.compatibility.jei.recipecategories.psuedo.specificmachines.ParticleAcceleratorAntiMatterRecipeCategory;
import nuclearscience.compatibility.jei.recipecategories.psuedo.specificmachines.ParticleAcceleratorDarkMatterRecipeCategory;
import nuclearscience.compatibility.jei.utils.psuedorecipes.NuclearSciencePsuedoRecipes;
import nuclearscience.compatibility.jei.utils.psuedorecipes.PsuedoGasCentrifugeRecipe;

@JeiPlugin
public class NuclearSciencePlugin implements IModPlugin {

	@Override
	public ResourceLocation getPluginUid() {
		return new ResourceLocation(nuclearscience.References.ID, "jei");
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(GasCentrifugeRecipeCategory.INPUT_MACHINE, GasCentrifugeRecipeCategory.RECIPE_TYPE);
		registration.addRecipeCatalyst(NuclearBoilerRecipeCategory.INPUT_MACHINE, NuclearBoilerRecipeCategory.RECIPE_TYPE);
		registration.addRecipeCatalyst(ChemicalExtractorRecipeCategory.INPUT_MACHINE, ChemicalExtractorRecipeCategory.RECIPE_TYPE);
		registration.addRecipeCatalyst(FissionReactorRecipeCategory.INPUT_MACHINE, FissionReactorRecipeCategory.RECIPE_TYPE);
		registration.addRecipeCatalyst(ParticleAcceleratorAntiMatterRecipeCategory.INPUT_MACHINE, ParticleAcceleratorAntiMatterRecipeCategory.RECIPE_TYPE);
		registration.addRecipeCatalyst(ParticleAcceleratorDarkMatterRecipeCategory.INPUT_MACHINE, ParticleAcceleratorDarkMatterRecipeCategory.RECIPE_TYPE);
		registration.addRecipeCatalyst(FuelReprocessorRecipeCategory.INPUT_MACHINE, FuelReprocessorRecipeCategory.RECIPE_TYPE);
		registration.addRecipeCatalyst(RadioactiveProcessorRecipeCategory.INPUT_MACHINE, RadioactiveProcessorRecipeCategory.RECIPE_TYPE);
		registration.addRecipeCatalyst(MSRProcessorRecipeCategory.INPUT_MACHINE, MSRProcessorRecipeCategory.RECIPE_TYPE);
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		NuclearSciencePsuedoRecipes.addNuclearScienceRecipes();
		Minecraft mc = Minecraft.getInstance();
		ClientLevel world = Objects.requireNonNull(mc.level);
		RecipeManager recipeManager = world.getRecipeManager();

		// Gas Centrifuge
		List<PsuedoGasCentrifugeRecipe> gasCentrifugeRecipes = new ArrayList<>(NuclearSciencePsuedoRecipes.GAS_CENTRIFUGE_RECIPES);
		registration.addRecipes(GasCentrifugeRecipeCategory.RECIPE_TYPE, gasCentrifugeRecipes);

		// Nuclear Boiler
		List<NuclearBoilerRecipe> nuclearBoilerRecipes = recipeManager.getAllRecipesFor(NuclearScienceRecipeInit.NUCLEAR_BOILER_TYPE.get());
		registration.addRecipes(NuclearBoilerRecipeCategory.RECIPE_TYPE, nuclearBoilerRecipes);

		// Chemical Extractor
		List<ChemicalExtractorRecipe> chemicalExtractorRecipes = recipeManager.getAllRecipesFor(NuclearScienceRecipeInit.CHEMICAL_EXTRACTOR_TYPE.get());
		registration.addRecipes(ChemicalExtractorRecipeCategory.RECIPE_TYPE, chemicalExtractorRecipes);

		// Fission Reactor
		List<FissionReactorRecipe> fissionReactorRecipes = recipeManager.getAllRecipesFor(NuclearScienceRecipeInit.FISSION_REACTOR_TYPE.get());
		registration.addRecipes(FissionReactorRecipeCategory.RECIPE_TYPE, fissionReactorRecipes);

		// Anti-Matter
		List<PsuedoItem2ItemRecipe> antiMatterRecipes = new ArrayList<>(NuclearSciencePsuedoRecipes.ANTI_MATTER_RECIPES);
		registration.addRecipes(ParticleAcceleratorAntiMatterRecipeCategory.RECIPE_TYPE, antiMatterRecipes);

		// Dark Mattere
		List<PsuedoItem2ItemRecipe> darkMatterRecipes = new ArrayList<>(NuclearSciencePsuedoRecipes.DARK_MATTER_RECIPES);
		registration.addRecipes(ParticleAcceleratorDarkMatterRecipeCategory.RECIPE_TYPE, darkMatterRecipes);

		// Fuel Reprocessor
		List<FuelReprocessorRecipe> fuelReprocessorRecipes = recipeManager.getAllRecipesFor(NuclearScienceRecipeInit.FUEL_REPROCESSOR_TYPE.get());
		registration.addRecipes(FuelReprocessorRecipeCategory.RECIPE_TYPE, fuelReprocessorRecipes);

		// Radioactive Processor
		List<RadioactiveProcessorRecipe> radioactiveProcessorRecipes = recipeManager.getAllRecipesFor(NuclearScienceRecipeInit.RADIOACTIVE_PROCESSOR_TYPE.get());
		registration.addRecipes(RadioactiveProcessorRecipeCategory.RECIPE_TYPE, radioactiveProcessorRecipes);

		// MSR Processor
		List<MSRFuelPreProcessorRecipe> msrProcessorRecipes = recipeManager.getAllRecipesFor(NuclearScienceRecipeInit.MSR_FUEL_PREPROCESSOR_TYPE.get());
		registration.addRecipes(MSRProcessorRecipeCategory.RECIPE_TYPE, msrProcessorRecipes);

		nuclearScienceInfoTabs(registration);
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {

		IGuiHelper helper = registration.getJeiHelpers().getGuiHelper();

		registration.addRecipeCategories(new GasCentrifugeRecipeCategory(helper));
		registration.addRecipeCategories(new NuclearBoilerRecipeCategory(helper));
		registration.addRecipeCategories(new ChemicalExtractorRecipeCategory(helper));
		registration.addRecipeCategories(new FissionReactorRecipeCategory(helper));
		registration.addRecipeCategories(new ParticleAcceleratorAntiMatterRecipeCategory(helper));
		registration.addRecipeCategories(new ParticleAcceleratorDarkMatterRecipeCategory(helper));
		registration.addRecipeCategories(new FuelReprocessorRecipeCategory(helper));
		registration.addRecipeCategories(new RadioactiveProcessorRecipeCategory(helper));
		registration.addRecipeCategories(new MSRProcessorRecipeCategory(helper));

	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registry) {
		registry.addRecipeClickArea(ScreenO2OProcessor.class, 48, 35, 22, 15, ElectrodynamicsJEIPlugin.O2O_CLICK_AREAS.toArray(new RecipeType<?>[ElectrodynamicsJEIPlugin.O2O_CLICK_AREAS.size()]));
		registry.addRecipeClickArea(ScreenNuclearBoiler.class, 97, 31, 22, 15, NuclearBoilerRecipeCategory.RECIPE_TYPE);
		registry.addRecipeClickArea(ScreenRadioactiveProcessor.class, 97, 31, 22, 15, RadioactiveProcessorRecipeCategory.RECIPE_TYPE);
		registry.addRecipeClickArea(ScreenChemicalExtractor.class, 97, 31, 22, 15, ChemicalExtractorRecipeCategory.RECIPE_TYPE);
		registry.addRecipeClickArea(ScreenGasCentrifuge.class, 105, 13, 20, 54, GasCentrifugeRecipeCategory.RECIPE_TYPE);
		registry.addRecipeClickArea(ScreenFissionReactorCore.class, 117, 53, 14, 13, FissionReactorRecipeCategory.RECIPE_TYPE);
		registry.addRecipeClickArea(ScreenParticleInjector.class, 102, 43, 28, 14, ParticleAcceleratorAntiMatterRecipeCategory.RECIPE_TYPE, ParticleAcceleratorDarkMatterRecipeCategory.RECIPE_TYPE);
		registry.addRecipeClickArea(ScreenMSRFuelPreProcessor.class, 98, 40, 16, 16, MSRProcessorRecipeCategory.RECIPE_TYPE);
	}

	private static void nuclearScienceInfoTabs(IRecipeRegistration registration) {

	}

}
