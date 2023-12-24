package nuclearscience.datagen.server.recipe.custom.fluiditem2item;

import java.util.function.Consumer;

import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.datagen.utils.recipe.AbstractElectrodynamicsFinishedRecipe.RecipeCategory;
import electrodynamics.datagen.utils.recipe.AbstractRecipeGenerator;
import electrodynamics.datagen.utils.recipe.FinishedRecipeItemOutput;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.FluidTags;
import nuclearscience.References;
import nuclearscience.common.recipe.NuclearScienceRecipeInit;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.registers.NuclearScienceItems;

public class NuclearScienceChemicalExtractorRecipes extends AbstractRecipeGenerator {

	public static double CHEMICALEXTRACTOR_USAGE_PER_TICK = 750.0;
	public static int CHEMICALEXTRACTOR_REQUIRED_TICKS = 400;

	private final String modID;

	public NuclearScienceChemicalExtractorRecipes() {
		this(References.ID);
	}

	public NuclearScienceChemicalExtractorRecipes(String modID) {
		this.modID = modID;
	}

	@Override
	public void addRecipes(Consumer<IFinishedRecipe> consumer) {

		newRecipe(new ItemStack(NuclearScienceItems.ITEM_CELLDEUTERIUM.get()), 0.0F, CHEMICALEXTRACTOR_REQUIRED_TICKS, CHEMICALEXTRACTOR_USAGE_PER_TICK, "cell_deuterium")
				//
				.addItemTagInput(NuclearScienceTags.Items.CELL_HEAVYWATER, 1)
				//
				.addFluidTagInput(FluidTags.WATER, 4800)
				//
				.complete(consumer);

		newRecipe(new ItemStack(NuclearScienceItems.ITEM_CELLHEAVYWATER.get()), 0.0F, CHEMICALEXTRACTOR_REQUIRED_TICKS, CHEMICALEXTRACTOR_USAGE_PER_TICK, "cell_heavywater")
				//
				.addItemTagInput(NuclearScienceTags.Items.CELL_EMPTY, 1)
				//
				.addFluidTagInput(FluidTags.WATER, 4800)
				//
				.complete(consumer);

		newRecipe(new ItemStack(NuclearScienceItems.ITEM_YELLOWCAKE.get()), 0.0F, CHEMICALEXTRACTOR_REQUIRED_TICKS, CHEMICALEXTRACTOR_USAGE_PER_TICK, "yellowcake_from_rawuranium")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.ORE_URANIUM, 1)
				//
				.addFluidTagInput(FluidTags.WATER, 1600)
				//
				.complete(consumer);

	}

	public FinishedRecipeItemOutput newRecipe(ItemStack stack, float xp, int ticks, double usagePerTick, String name) {
		return FinishedRecipeItemOutput.of(NuclearScienceRecipeInit.CHEMICAL_EXTRACTOR_SERIALIZER.get(), stack, xp, ticks, usagePerTick).name(RecipeCategory.FLUID_ITEM_2_ITEM, modID, "chemical_extractor/" + name);
	}

}
