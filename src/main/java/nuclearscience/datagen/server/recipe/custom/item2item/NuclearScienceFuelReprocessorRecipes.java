package nuclearscience.datagen.server.recipe.custom.item2item;

import java.util.function.Consumer;

import electrodynamics.common.item.subtype.SubtypeIngot;
import electrodynamics.common.recipe.recipeutils.ProbableItem;
import electrodynamics.datagen.utils.recipe.AbstractElectrodynamicsFinishedRecipe.RecipeCategory;
import electrodynamics.datagen.utils.recipe.AbstractRecipeGenerator;
import electrodynamics.datagen.utils.recipe.FinishedRecipeItemOutput;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.ItemStack;
import nuclearscience.References;
import nuclearscience.common.recipe.NuclearScienceRecipeInit;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.registers.NuclearScienceBlocks;
import nuclearscience.registers.NuclearScienceItems;

public class NuclearScienceFuelReprocessorRecipes extends AbstractRecipeGenerator {

	public static double FUELREPROCESSOR_USAGE_PER_TICK = 480;
	public static int FUELREPROCESSOR_REQUIRED_TICKS = 200;

	private final String modID;

	public NuclearScienceFuelReprocessorRecipes() {
		this(References.ID);
	}

	public NuclearScienceFuelReprocessorRecipes(String modID) {
		this.modID = modID;
	}

	@Override
	public void addRecipes(Consumer<IFinishedRecipe> consumer) {

		newRecipe(new ItemStack(NuclearScienceItems.ITEM_FISSILEDUST.get()), 0.0F, FUELREPROCESSOR_REQUIRED_TICKS, FUELREPROCESSOR_USAGE_PER_TICK, "fissile_dust")
				//
				.addItemTagInput(NuclearScienceTags.Items.FUELROD_SPENT, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(NuclearScienceItems.ITEM_POLONIUM210_CHUNK.get()), 0.5D))
				//
				.complete(consumer);

		newRecipe(new ItemStack(NuclearScienceItems.ITEM_PLUTONIUM239.get()), 0.0F, FUELREPROCESSOR_REQUIRED_TICKS, FUELREPROCESSOR_USAGE_PER_TICK, "plutonium239")
				//
				.addItemTagInput(NuclearScienceTags.Items.OXIDE_PLUTONIUM, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(NuclearScienceItems.ITEM_POLONIUM210_CHUNK.get(), 3), 1.0D))
				//
				.complete(consumer);

		newRecipe(new ItemStack(ElectrodynamicsItems.getItem(SubtypeIngot.steel), 2), 0.0F, FUELREPROCESSOR_REQUIRED_TICKS, FUELREPROCESSOR_USAGE_PER_TICK, "reactor_salvage")
				//
				.addItemStackInput(new ItemStack(NuclearScienceBlocks.blockMeltedReactor))
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(NuclearScienceItems.ITEM_PLUTONIUM239.get()), 1.0D))
				//
				.complete(consumer);

		newRecipe(new ItemStack(NuclearScienceItems.ITEM_ACTINIUMOXIDE.get()), 0.0F, FUELREPROCESSOR_REQUIRED_TICKS, FUELREPROCESSOR_USAGE_PER_TICK, "actinium_oxide")
				//
				.addItemTagInput(NuclearScienceTags.Items.SALT_FISSILE, 1)
				//
				.addItemBiproduct(new ProbableItem(new ItemStack(NuclearScienceItems.ITEM_POLONIUM210_CHUNK.get(), 3), 1.0D))
				//
				.complete(consumer);

	}

	public FinishedRecipeItemOutput newRecipe(ItemStack stack, float xp, int ticks, double usagePerTick, String name) {
		return FinishedRecipeItemOutput.of(NuclearScienceRecipeInit.FUEL_REPROCESSOR_SERIALIZER.get(), stack, xp, ticks, usagePerTick).name(RecipeCategory.ITEM_2_ITEM, modID, "fuel_reprocessor/" + name);
	}

}
