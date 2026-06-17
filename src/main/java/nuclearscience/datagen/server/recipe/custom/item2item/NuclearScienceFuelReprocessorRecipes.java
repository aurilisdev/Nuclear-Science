package nuclearscience.datagen.server.recipe.custom.item2item;

import electrodynamics.common.item.subtype.SubtypeIngot;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.ItemStack;
import nuclearscience.NuclearScience;
import nuclearscience.common.recipe.categories.item2item.FuelReprocessorRecipe;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.registers.NuclearScienceBlocks;
import nuclearscience.registers.NuclearScienceItems;
import voltaic.common.recipe.recipeutils.ProbableItem;
import voltaic.datagen.utils.server.recipe.AbstractRecipeGenerator;
import voltaic.datagen.utils.server.recipe.builders.BaseRecipeBuilder;
import voltaic.datagen.utils.server.recipe.builders.Item2ItemBuilder;

public class NuclearScienceFuelReprocessorRecipes extends AbstractRecipeGenerator {

    public static double FUELREPROCESSOR_USAGE_PER_TICK = 480;
    public static int FUELREPROCESSOR_REQUIRED_TICKS = 200;

    private final String modID;

    public NuclearScienceFuelReprocessorRecipes() {
	this(NuclearScience.ID);
    }

    public NuclearScienceFuelReprocessorRecipes(String modID) {
	this.modID = modID;
    }

    @Override
    public void addRecipes(RecipeOutput output) {

	newRecipe(new ItemStack(NuclearScienceItems.ITEM_FISSILEDUST.get()), 0.0F, FUELREPROCESSOR_REQUIRED_TICKS,
		FUELREPROCESSOR_USAGE_PER_TICK, "fissile_dust", modID)
		//
		.addItemTagInput(NuclearScienceTags.Items.FUELROD_SPENT, 1)
		//
		.addItemBiproduct(
			new ProbableItem(new ItemStack(NuclearScienceItems.ITEM_POLONIUM210_CHUNK.get()), 0.5D))
		//
		.save(output);

	newRecipe(new ItemStack(NuclearScienceItems.ITEM_PLUTONIUM239.get()), 0.0F, FUELREPROCESSOR_REQUIRED_TICKS,
		FUELREPROCESSOR_USAGE_PER_TICK, "plutonium239", modID)
		//
		.addItemTagInput(NuclearScienceTags.Items.OXIDE_PLUTONIUM, 1)
		//
		.addItemBiproduct(
			new ProbableItem(new ItemStack(NuclearScienceItems.ITEM_POLONIUM210_CHUNK.get(), 3), 1.0D))
		//
		.save(output);

	newRecipe(new ItemStack(ElectrodynamicsItems.ITEMS_INGOT.getValue(SubtypeIngot.steel), 2), 0.0F,
		FUELREPROCESSOR_REQUIRED_TICKS, FUELREPROCESSOR_USAGE_PER_TICK, "reactor_salvage", modID)
		//
		.addItemStackInput(new ItemStack(NuclearScienceBlocks.BLOCK_MELTEDREACTOR.get()))
		//
		.addItemBiproduct(new ProbableItem(new ItemStack(NuclearScienceItems.ITEM_PLUTONIUM239.get()), 1.0D))
		//
		.save(output);

	newRecipe(new ItemStack(NuclearScienceItems.ITEM_ACTINIUMOXIDE.get()), 0.0F, FUELREPROCESSOR_REQUIRED_TICKS,
		FUELREPROCESSOR_USAGE_PER_TICK, "actinium_oxide", modID)
		//
		.addItemTagInput(NuclearScienceTags.Items.SALT_FISSILE, 1)
		//
		.addItemBiproduct(
			new ProbableItem(new ItemStack(NuclearScienceItems.ITEM_POLONIUM210_CHUNK.get(), 3), 1.0D))
		//
		.save(output);

    }

    public Item2ItemBuilder<FuelReprocessorRecipe> newRecipe(ItemStack stack, float xp, int ticks, double usagePerTick,
	    String name, String group) {
	return new Item2ItemBuilder<>(FuelReprocessorRecipe::new, stack, BaseRecipeBuilder.RecipeCategory.ITEM_2_ITEM,
		modID, "fuel_reprocessor/" + name, group, xp, ticks, usagePerTick);
    }

}
