package nuclearscience.compatability.jei;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import electrodynamics.common.recipe.categories.fluiditem2fluid.FluidItem2FluidRecipe;
import electrodynamics.common.recipe.categories.fluiditem2item.FluidItem2ItemRecipe;
import electrodynamics.common.recipe.categories.o2o.O2ORecipe;
import electrodynamics.compatability.jei.recipecategories.psuedorecipes.PsuedoO2ORecipe;
import electrodynamics.compatability.jei.recipecategories.psuedorecipes.PsuedoRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import nuclearscience.client.screen.ScreenChemicalExtractor;
import nuclearscience.client.screen.ScreenGasCentrifuge;
import nuclearscience.client.screen.ScreenNuclearBoiler;
import nuclearscience.client.screen.ScreenParticleInjector;
import nuclearscience.client.screen.ScreenReactorCore;
import nuclearscience.common.recipe.NuclearScienceRecipeInit;
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

    @Override
    public ResourceLocation getPluginUid() {
	return new ResourceLocation(nuclearscience.References.ID, "nucsci_jei_plugin");
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
	// Gas Centrifuge
	registration.addRecipeCatalyst(GasCentrifugeRecipeCategory.INPUT_MACHINE, GasCentrifugeRecipeCategory.UID);

	// Nuclear Boiler
	registration.addRecipeCatalyst(NuclearBoilerRecipeCategory.INPUT_MACHINE, NuclearBoilerRecipeCategory.UID);

	// Chemical Extractor
	registration.addRecipeCatalyst(ChemicalExtractorRecipeCategory.INPUT_MACHINE, ChemicalExtractorRecipeCategory.UID);

	// Fisison Reactor
	registration.addRecipeCatalyst(FissionReactorRecipeCategory.INPUT_MACHINE, FissionReactorRecipeCategory.UID);

	// Anti Matter
	registration.addRecipeCatalyst(ParticleAcceleratorAntiMatterRecipeCategory.INPUT_MACHINE,ParticleAcceleratorAntiMatterRecipeCategory.UID);

	// Dark Matter
	registration.addRecipeCatalyst(ParticleAcceleratorDarkMatterRecipeCategory.INPUT_MACHINE,ParticleAcceleratorDarkMatterRecipeCategory.UID);

    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
	NuclearSciencePsuedoRecipes.addNuclearScienceRecipes();
	PsuedoRecipes.addElectrodynamicsRecipes();
	Minecraft mc = Minecraft.getInstance();
	ClientWorld world = Objects.requireNonNull(mc.world);
	RecipeManager recipeManager = world.getRecipeManager();

	// Gas Centrifuge
	Set<PsuedoGasCentrifugeRecipe> gasCentrifugeRecipes = new HashSet<>(NuclearSciencePsuedoRecipes.GAS_CENTRIFUGE_RECIPES);
	registration.addRecipes(gasCentrifugeRecipes, GasCentrifugeRecipeCategory.UID);

	// Nuclear Boiler
	Set<FluidItem2FluidRecipe> nuclearBoilerRecipes = ImmutableSet.copyOf(recipeManager.getRecipesForType(NuclearScienceRecipeInit.NUCLEAR_BOILER_TYPE));
	registration.addRecipes(nuclearBoilerRecipes, NuclearBoilerRecipeCategory.UID);

	// Chemical Extractor
	Set<FluidItem2ItemRecipe> chemicalExtractorRecipes = ImmutableSet.copyOf(recipeManager.getRecipesForType(NuclearScienceRecipeInit.CHEMICAL_EXTRACTOR_TYPE));
	registration.addRecipes(chemicalExtractorRecipes, ChemicalExtractorRecipeCategory.UID);

	Set<O2ORecipe> fissionReactorRecipes = ImmutableSet.copyOf(recipeManager.getRecipesForType(NuclearScienceRecipeInit.FISSION_REACTOR_TYPE));
	registration.addRecipes(fissionReactorRecipes, FissionReactorRecipeCategory.UID);

	Set<PsuedoO2ORecipe> antiMatterRecipes = new HashSet<>(NuclearSciencePsuedoRecipes.ANTI_MATTER_RECIPES);
	registration.addRecipes(antiMatterRecipes, ParticleAcceleratorAntiMatterRecipeCategory.UID);

	Set<PsuedoO2ORecipe> darkMatterRecipes = new HashSet<>(NuclearSciencePsuedoRecipes.DARK_MATTER_RECIPES);
	registration.addRecipes(darkMatterRecipes, ParticleAcceleratorDarkMatterRecipeCategory.UID);

	nuclearScienceInfoTabs(registration);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
    	IGuiHelper helper = registration.getJeiHelpers().getGuiHelper();
		// Gas Centrifuge
		registration.addRecipeCategories(new GasCentrifugeRecipeCategory(helper));
	
		// Nuclear Boiler
		registration.addRecipeCategories(new NuclearBoilerRecipeCategory(helper));
	
		// Chemical Extractor
		registration.addRecipeCategories(new ChemicalExtractorRecipeCategory(helper));
	
		// Fision Reactor
		registration.addRecipeCategories(new FissionReactorRecipeCategory(helper));
	
		// Anti Matter
		registration.addRecipeCategories(new ParticleAcceleratorAntiMatterRecipeCategory(helper));
	
		// Dark Matter
		registration.addRecipeCategories(new ParticleAcceleratorDarkMatterRecipeCategory(helper));

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
	 * Particle Injector Quantum Capacitor Radioisotope Generator Turbine Teleporter
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
