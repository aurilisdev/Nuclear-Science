package nuclearscience.datagen.server.recipe.custom.fluiditem2item;

import java.util.function.Consumer;

import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.FluidTags;
import nuclearscience.NuclearScience;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.registers.NuclearScienceItems;
import nuclearscience.registers.NuclearScienceRecipies;
import voltaic.common.tags.VoltaicTags;
import voltaic.datagen.utils.server.recipe.AbstractRecipeGenerator;
import voltaic.datagen.utils.server.recipe.FinishedRecipeBase.RecipeCategory;
import voltaic.datagen.utils.server.recipe.FinishedRecipeItemOutput;

public class NuclearScienceChemicalExtractorRecipes extends AbstractRecipeGenerator {

	public static double CHEMICALEXTRACTOR_USAGE_PER_TICK = 750.0;
	public static int CHEMICALEXTRACTOR_REQUIRED_TICKS = 400;

	private final String modID;

	public NuclearScienceChemicalExtractorRecipes() {
		this(NuclearScience.ID);
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
				.addItemTagInput(VoltaicTags.Items.RAW_ORE_URANIUM, 1)
				//
				.addFluidTagInput(FluidTags.WATER, 1600)
				//
				.complete(consumer);

	}

	public FinishedRecipeItemOutput newRecipe(ItemStack stack, float xp, int ticks, double usagePerTick, String name) {
		return FinishedRecipeItemOutput.of(NuclearScienceRecipies.CHEMICAL_EXTRACTOR_SERIALIZER.get(), stack, xp, ticks, usagePerTick).name(RecipeCategory.FLUID_ITEM_2_ITEM, modID, "chemical_extractor/" + name);
	}

}
