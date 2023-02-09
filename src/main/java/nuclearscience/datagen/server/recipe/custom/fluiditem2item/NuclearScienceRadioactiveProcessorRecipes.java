package nuclearscience.datagen.server.recipe.custom.fluiditem2item;

import java.util.function.Consumer;

import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.datagen.utils.recipe.AbstractElectrodynamicsFinishedRecipe.RecipeCategory;
import electrodynamics.datagen.utils.recipe.AbstractRecipeGenerator;
import electrodynamics.datagen.utils.recipe.FinishedRecipeItemOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import nuclearscience.References;
import nuclearscience.common.recipe.NuclearScienceRecipeInit;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.registers.NuclearScienceBlocks;
import nuclearscience.registers.NuclearScienceItems;

public class NuclearScienceRadioactiveProcessorRecipes extends AbstractRecipeGenerator {

	public static double RADIOACTIVEPROCESSOR_USAGE_PER_TICK = 480.0;
	public static int RADIOACTIVEPROCESSOR_REQUIRED_TICKS = 300;

	private final String modID;

	public NuclearScienceRadioactiveProcessorRecipes() {
		this(References.ID);
	}

	public NuclearScienceRadioactiveProcessorRecipes(String modID) {
		this.modID = modID;
	}

	@Override
	public void addRecipes(Consumer<FinishedRecipe> consumer) {

		newRecipe(new ItemStack(NuclearScienceItems.ITEM_PLUTONIUMOXIDE.get()), 0.0F, RADIOACTIVEPROCESSOR_REQUIRED_TICKS, RADIOACTIVEPROCESSOR_USAGE_PER_TICK, "plutonium_oxide")
				//
				.addItemTagInput(NuclearScienceTags.Items.DUST_FISSILE, 2)
				//
				.addFluidTagInput(ElectrodynamicsTags.Fluids.IRON_SULF, 3000)
				//
				.complete(consumer);

		newRecipe(new ItemStack(NuclearScienceItems.ITEM_THORIANITEDUST.get()), 0.0F, RADIOACTIVEPROCESSOR_REQUIRED_TICKS, RADIOACTIVEPROCESSOR_USAGE_PER_TICK, "thorianite_dust")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.RAW_ORE_THORIUM, 1)
				//
				.addFluidTagInput(ElectrodynamicsTags.Fluids.SULFURIC_ACID, 1000)
				//
				.complete(consumer);

		newRecipe(new ItemStack(Items.DIRT), 0.0F, RADIOACTIVEPROCESSOR_REQUIRED_TICKS, RADIOACTIVEPROCESSOR_USAGE_PER_TICK, "dirt_from_radioactivesoil")
				//
				.addItemStackInput(new ItemStack(NuclearScienceBlocks.blockRadioactiveSoil))
				//
				.addFluidTagInput(FluidTags.WATER, 100)
				//
				.complete(consumer);

	}

	public FinishedRecipeItemOutput newRecipe(ItemStack stack, float xp, int ticks, double usagePerTick, String name) {
		return FinishedRecipeItemOutput.of(NuclearScienceRecipeInit.RADIOACTIVE_PROCESSOR_SERIALIZER.get(), stack, xp, ticks, usagePerTick).name(RecipeCategory.FLUID_ITEM_2_ITEM, modID, "radioactive_processor/" + name);
	}

}
