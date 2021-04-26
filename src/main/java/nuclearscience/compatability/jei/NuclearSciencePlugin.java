package nuclearscience.compatability.jei;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import electrodynamics.compatability.jei.recipecategories.psuedorecipes.PsuedoSolAndLiqToLiquidRecipe;
import electrodynamics.compatability.jei.recipecategories.psuedorecipes.PsuedoSolAndLiqToSolidRecipe;
import electrodynamics.prefab.tile.processing.O2OProcessingRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import nuclearscience.client.screen.ScreenChemicalExtractor;
import nuclearscience.client.screen.ScreenGasCentrifuge;
import nuclearscience.client.screen.ScreenNuclearBoiler;
import nuclearscience.client.screen.ScreenParticleInjector;
import nuclearscience.client.screen.ScreenReactorCore;
import nuclearscience.compatability.jei.recipecategories.psuedorecipes.NuclearSciencePsuedoRecipes;
import nuclearscience.compatability.jei.recipecategories.psuedorecipes.PsuedoGasCentrifugeRecipe;
import nuclearscience.compatability.jei.recipecategories.specificmachines.nuclearscience.ChemicalExtractorRecipeCategory;
import nuclearscience.compatability.jei.recipecategories.specificmachines.nuclearscience.FissionReactorRecipeCategory;
import nuclearscience.compatability.jei.recipecategories.specificmachines.nuclearscience.GasCentrifugeRecipeCategory;
import nuclearscience.compatability.jei.recipecategories.specificmachines.nuclearscience.NuclearBoilerRecipeCategory;
import nuclearscience.compatability.jei.recipecategories.specificmachines.nuclearscience.ParticleAcceleratorAntiMatterRecipeCategory;
import nuclearscience.compatability.jei.recipecategories.specificmachines.nuclearscience.ParticleAcceleratorDarkMatterRecipeCategory;

@JeiPlugin
public class NuclearSciencePlugin implements IModPlugin {

    // public static final boolean isNuclearScienceLoaded =
    // ModList.get().isLoaded("nuclearscience");

    @Override
    public ResourceLocation getPluginUid() {
	return new ResourceLocation(nuclearscience.References.ID, "nucsci_jei_plugin");
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
	// Gas Centrifuge
	registration.addRecipeCatalyst(new ItemStack(nuclearscience.DeferredRegisters.blockGasCentrifuge), GasCentrifugeRecipeCategory.UID);

	// Nuclear Boiler
	registration.addRecipeCatalyst(new ItemStack(nuclearscience.DeferredRegisters.blockNuclearBoiler), NuclearBoilerRecipeCategory.UID);

	// Chemical Extractor
	registration.addRecipeCatalyst(new ItemStack(nuclearscience.DeferredRegisters.blockChemicalExtractor), ChemicalExtractorRecipeCategory.UID);

	// Fisison Reactor

	registration.addRecipeCatalyst(new ItemStack(nuclearscience.DeferredRegisters.blockReactorCore), FissionReactorRecipeCategory.UID);

	// Anti Matter

	registration.addRecipeCatalyst(new ItemStack(nuclearscience.DeferredRegisters.blockParticleInjector),
		ParticleAcceleratorAntiMatterRecipeCategory.UID);

	// Dark Matter

	registration.addRecipeCatalyst(new ItemStack(nuclearscience.DeferredRegisters.blockParticleInjector),
		ParticleAcceleratorDarkMatterRecipeCategory.UID);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
	NuclearSciencePsuedoRecipes.addNuclearScienceRecipes();

	// Gas Centrifuge
	Set<PsuedoGasCentrifugeRecipe> gasCentrifugeRecipes = new HashSet<>(NuclearSciencePsuedoRecipes.GAS_CENTRIFUGE_RECIPES);

	registration.addRecipes(gasCentrifugeRecipes, GasCentrifugeRecipeCategory.UID);

	// Nuclear Boiler
	Set<PsuedoSolAndLiqToLiquidRecipe> nuclearBoilerRecipes = new HashSet<>(NuclearSciencePsuedoRecipes.NUCLEAR_BOILER_RECIPES);

	registration.addRecipes(nuclearBoilerRecipes, NuclearBoilerRecipeCategory.UID);

	// Chemical Extractor
	Set<PsuedoSolAndLiqToSolidRecipe> chemicalExtractorRecipes = new HashSet<>(NuclearSciencePsuedoRecipes.CHEMICAL_EXTRACTOR_RECIPES);

	registration.addRecipes(chemicalExtractorRecipes, ChemicalExtractorRecipeCategory.UID);

	Set<O2OProcessingRecipe> fissionReactorRecipes = new HashSet<>(NuclearSciencePsuedoRecipes.FISSION_REACTOR_RECIPES);
	registration.addRecipes(fissionReactorRecipes, FissionReactorRecipeCategory.UID);

	Set<O2OProcessingRecipe> antiMatterRecipes = new HashSet<>(NuclearSciencePsuedoRecipes.ANTI_MATTER_RECIPES);
	registration.addRecipes(antiMatterRecipes, ParticleAcceleratorAntiMatterRecipeCategory.UID);

	Set<O2OProcessingRecipe> darkMatterRecipes = new HashSet<>(NuclearSciencePsuedoRecipes.DARK_MATTER_RECIPES);
	registration.addRecipes(darkMatterRecipes, ParticleAcceleratorDarkMatterRecipeCategory.UID);

	nuclearScienceInfoTabs(registration);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
	// Gas Centrifuge
	registration.addRecipeCategories(new GasCentrifugeRecipeCategory(registration.getJeiHelpers().getGuiHelper()));

	// Nuclear Boiler
	registration.addRecipeCategories(new NuclearBoilerRecipeCategory(registration.getJeiHelpers().getGuiHelper()));

	// Chemical Extractor
	registration.addRecipeCategories(new ChemicalExtractorRecipeCategory(registration.getJeiHelpers().getGuiHelper()));

	// Fision Reactor
	registration.addRecipeCategories(new FissionReactorRecipeCategory(registration.getJeiHelpers().getGuiHelper()));

	// Anti Matter
	registration.addRecipeCategories(new ParticleAcceleratorAntiMatterRecipeCategory(registration.getJeiHelpers().getGuiHelper()));

	// Dark Matter
	registration.addRecipeCategories(new ParticleAcceleratorDarkMatterRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registry) {
	int[] arrowLocation = { 97, 31, 22, 15 };

	// Nuclear Boiler

	registry.addRecipeClickArea(ScreenNuclearBoiler.class, arrowLocation[0], arrowLocation[1], arrowLocation[2], arrowLocation[3],
		NuclearBoilerRecipeCategory.UID);

	// Chemical Extractor
	registry.addRecipeClickArea(ScreenChemicalExtractor.class, arrowLocation[0], arrowLocation[1], arrowLocation[2], arrowLocation[3],
		ChemicalExtractorRecipeCategory.UID);

	// Gas Centrifuge
	registry.addRecipeClickArea(ScreenGasCentrifuge.class, 91, 22, 32, 41, GasCentrifugeRecipeCategory.UID);

	// Fission Reactor
	registry.addRecipeClickArea(ScreenReactorCore.class, 117, 43, 14, 13, FissionReactorRecipeCategory.UID);

	// Particle Accelerator
	registry.addRecipeClickArea(ScreenParticleInjector.class, 102, 33, 28, 14, ParticleAcceleratorAntiMatterRecipeCategory.UID,
		ParticleAcceleratorDarkMatterRecipeCategory.UID);

    }

    private static void nuclearScienceInfoTabs(IRecipeRegistration registration) {

	/*
	 * Machines currently with tabs:
	 * 
	 * Fission Reactor Core Fusion Reactor Core Chemical Boiler Chemical Extractor
	 * Electromagnet Electromagnetic Booster Electromagnetic Switch Gas Centrifuge
	 * Particle Injector Quantum Capacitor Radioisotope Generator Turbine
	 * 
	 */
	ArrayList<ItemStack> nsMachines = NuclearSciencePsuedoRecipes.NUCLEAR_SCIENCE_MACHINES;
	String temp;

	for (ItemStack itemStack : nsMachines) {
	    temp = itemStack.getItem().toString();
	    registration.addIngredientInfo(itemStack, VanillaTypes.ITEM, "info.jei.block." + temp);
	}

    }

}
