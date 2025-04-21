package nuclearscience.datagen.server.recipe.custom.item2item;

import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.ItemStack;
import nuclearscience.NuclearScience;
import nuclearscience.common.recipe.categories.item2item.FissionReactorRecipe;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.registers.NuclearScienceItems;
import voltaic.datagen.utils.server.recipe.AbstractRecipeGenerator;
import voltaic.datagen.utils.server.recipe.builders.BaseRecipeBuilder;
import voltaic.datagen.utils.server.recipe.builders.Item2ItemBuilder;

public class NuclearScienceFissionReactorRecipes extends AbstractRecipeGenerator {

	private final String modID;

	public NuclearScienceFissionReactorRecipes() {
		this(NuclearScience.ID);
	}

	public NuclearScienceFissionReactorRecipes(String modID) {
		this.modID = modID;
	}

	@Override
	public void addRecipes(RecipeOutput output) {

		newRecipe(new ItemStack(NuclearScienceItems.ITEM_CELLTRITIUM.get()), 0.0F, 1, 1, "cell_tritium", modID)
				//
				.addItemTagInput(NuclearScienceTags.Items.CELL_DEUTERIUM, 1)
				//
				.save(output);

	}

	public Item2ItemBuilder<FissionReactorRecipe> newRecipe(ItemStack stack, float xp, int ticks, double usagePerTick, String name, String group) {
		return new Item2ItemBuilder<>(FissionReactorRecipe::new, stack, BaseRecipeBuilder.RecipeCategory.ITEM_2_ITEM, modID, "fission_reactor/" + name, group, xp, ticks, usagePerTick);
	}

}
