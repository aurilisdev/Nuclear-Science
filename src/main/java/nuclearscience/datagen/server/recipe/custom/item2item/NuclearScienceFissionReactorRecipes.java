package nuclearscience.datagen.server.recipe.custom.item2item;

import java.util.function.Consumer;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;
import nuclearscience.NuclearScience;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.registers.NuclearScienceItems;
import nuclearscience.registers.NuclearScienceRecipies;
import voltaic.datagen.utils.server.recipe.AbstractRecipeGenerator;
import voltaic.datagen.utils.server.recipe.FinishedRecipeBase.RecipeCategory;
import voltaic.datagen.utils.server.recipe.FinishedRecipeItemOutput;

public class NuclearScienceFissionReactorRecipes extends AbstractRecipeGenerator {

	private final String modID;

	public NuclearScienceFissionReactorRecipes() {
		this(NuclearScience.ID);
	}

	public NuclearScienceFissionReactorRecipes(String modID) {
		this.modID = modID;
	}

	@Override
	public void addRecipes(Consumer<FinishedRecipe> consumer) {

		newRecipe(new ItemStack(NuclearScienceItems.ITEM_CELLTRITIUM.get()), 0.0F, 1, 1, "cell_tritium")
				//
				.addItemTagInput(NuclearScienceTags.Items.CELL_DEUTERIUM, 1)
				//
				.complete(consumer);

	}

	public FinishedRecipeItemOutput newRecipe(ItemStack stack, float xp, int ticks, double usagePerTick, String name) {
		return FinishedRecipeItemOutput.of(NuclearScienceRecipies.FISSION_REACTOR_SERIALIZER.get(), stack, xp, ticks, usagePerTick).name(RecipeCategory.ITEM_2_ITEM, modID, "fission_reactor/" + name);
	}

}
