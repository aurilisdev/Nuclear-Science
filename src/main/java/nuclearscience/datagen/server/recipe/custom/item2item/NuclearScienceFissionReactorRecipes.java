package nuclearscience.datagen.server.recipe.custom.item2item;

import java.util.function.Consumer;

import electrodynamics.datagen.utils.recipe.AbstractElectrodynamicsFinishedRecipe.RecipeCategory;
import electrodynamics.datagen.utils.recipe.AbstractRecipeGenerator;
import electrodynamics.datagen.utils.recipe.FinishedRecipeItemOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;
import nuclearscience.References;
import nuclearscience.common.recipe.NuclearScienceRecipeInit;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.registers.NuclearScienceItems;

public class NuclearScienceFissionReactorRecipes extends AbstractRecipeGenerator {

	private final String modID;

	public NuclearScienceFissionReactorRecipes() {
		this(References.ID);
	}

	public NuclearScienceFissionReactorRecipes(String modID) {
		this.modID = modID;
	}

	@Override
	public void addRecipes(Consumer<FinishedRecipe> consumer) {

		newRecipe(new ItemStack(NuclearScienceItems.ITEM_CELLDEUTERIUM.get()), 0.0F, 1, 1, "cell_tritium")
				//
				.addItemTagInput(NuclearScienceTags.Items.CELL_DEUTERIUM, 1)
				//
				.complete(consumer);

	}

	public FinishedRecipeItemOutput newRecipe(ItemStack stack, float xp, int ticks, double usagePerTick, String name) {
		return FinishedRecipeItemOutput.of(NuclearScienceRecipeInit.FISSION_REACTOR_SERIALIZER.get(), stack, xp, ticks, usagePerTick).name(RecipeCategory.ITEM_2_ITEM, modID, "fission_reactor/" + name);
	}

}
