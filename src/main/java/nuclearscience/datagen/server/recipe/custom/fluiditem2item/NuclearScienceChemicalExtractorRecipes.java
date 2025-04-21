package nuclearscience.datagen.server.recipe.custom.fluiditem2item;

import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.ItemStack;
import nuclearscience.NuclearScience;
import nuclearscience.common.recipe.categories.fluiditem2item.ChemicalExtractorRecipe;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.registers.NuclearScienceItems;
import voltaic.common.tags.VoltaicTags;
import voltaic.datagen.utils.server.recipe.AbstractRecipeGenerator;
import voltaic.datagen.utils.server.recipe.builders.BaseRecipeBuilder;
import voltaic.datagen.utils.server.recipe.builders.FluidItem2ItemBuilder;

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
	public void addRecipes(RecipeOutput output) {

		newRecipe(new ItemStack(NuclearScienceItems.ITEM_CELLDEUTERIUM.get()), 0.0F, CHEMICALEXTRACTOR_REQUIRED_TICKS, CHEMICALEXTRACTOR_USAGE_PER_TICK, "cell_deuterium", this.modID)
				//
				.addItemTagInput(NuclearScienceTags.Items.CELL_HEAVYWATER, 1)
				//
				.addFluidTagInput(FluidTags.WATER, 4800)
				//
				.save(output);

		newRecipe(new ItemStack(NuclearScienceItems.ITEM_CELLHEAVYWATER.get()), 0.0F, CHEMICALEXTRACTOR_REQUIRED_TICKS, CHEMICALEXTRACTOR_USAGE_PER_TICK, "cell_heavywater", this.modID)
				//
				.addItemTagInput(NuclearScienceTags.Items.CELL_EMPTY, 1)
				//
				.addFluidTagInput(FluidTags.WATER, 4800)
				//
				.save(output);

		newRecipe(new ItemStack(NuclearScienceItems.ITEM_YELLOWCAKE.get()), 0.0F, CHEMICALEXTRACTOR_REQUIRED_TICKS, CHEMICALEXTRACTOR_USAGE_PER_TICK, "yellowcake_from_rawuranium", this.modID)
				//
				.addItemTagInput(VoltaicTags.Items.RAW_ORE_URANIUM, 1)
				//
				.addFluidTagInput(FluidTags.WATER, 1600)
				//
				.save(output);

	}

	public FluidItem2ItemBuilder<ChemicalExtractorRecipe> newRecipe(ItemStack stack, float xp, int ticks, double usagePerTick, String name, String group) {
		return new FluidItem2ItemBuilder<>(ChemicalExtractorRecipe::new, stack, BaseRecipeBuilder.RecipeCategory.FLUID_ITEM_2_ITEM, modID, "chemical_extractor/" + name, group, xp, ticks, usagePerTick);
	}

}
