package nuclearscience.datagen.server.recipe.custom.fluiditem2item;

import java.util.function.Consumer;

import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tags.FluidTags;
import nuclearscience.NuclearScience;
import nuclearscience.common.block.subtype.SubtypeIrradiatedBlock;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.registers.NuclearScienceBlocks;
import nuclearscience.registers.NuclearScienceItems;
import nuclearscience.registers.NuclearScienceRecipies;
import voltaic.common.tags.VoltaicTags;
import voltaic.datagen.utils.server.recipe.AbstractRecipeGenerator;
import voltaic.datagen.utils.server.recipe.FinishedRecipeBase.RecipeCategory;
import voltaic.datagen.utils.server.recipe.FinishedRecipeItemOutput;

public class NuclearScienceRadioactiveProcessorRecipes extends AbstractRecipeGenerator {

	public static double RADIOACTIVEPROCESSOR_USAGE_PER_TICK = 480.0;
	public static int RADIOACTIVEPROCESSOR_REQUIRED_TICKS = 300;

	private final String modID;

	public NuclearScienceRadioactiveProcessorRecipes() {
		this(NuclearScience.ID);
	}

	public NuclearScienceRadioactiveProcessorRecipes(String modID) {
		this.modID = modID;
	}

	@Override
	public void addRecipes(Consumer<IFinishedRecipe> consumer) {

		newRecipe(new ItemStack(NuclearScienceItems.ITEM_PLUTONIUMOXIDE.get()), 0.0F, RADIOACTIVEPROCESSOR_REQUIRED_TICKS, RADIOACTIVEPROCESSOR_USAGE_PER_TICK, "plutonium_oxide")
				//
				.addItemTagInput(NuclearScienceTags.Items.DUST_FISSILE, 2)
				//
				.addFluidTagInput(VoltaicTags.Fluids.IRON_SULFATE, 3000)
				//
				.complete(consumer);

		newRecipe(new ItemStack(NuclearScienceItems.ITEM_THORIANITEDUST.get()), 0.0F, RADIOACTIVEPROCESSOR_REQUIRED_TICKS, RADIOACTIVEPROCESSOR_USAGE_PER_TICK, "thorianite_dust")
				//
				.addItemTagInput(VoltaicTags.Items.ORE_THORIUM, 1)
				//
				.addFluidTagInput(VoltaicTags.Fluids.SULFURIC_ACID, 1000)
				//
				.complete(consumer);

		newRecipe(new ItemStack(Items.DIRT), 0.0F, RADIOACTIVEPROCESSOR_REQUIRED_TICKS, RADIOACTIVEPROCESSOR_USAGE_PER_TICK, "dirt_from_irradiated_soil")
				//
				.addItemStackInput(new ItemStack(NuclearScienceBlocks.BLOCKS_IRRADIATED.getValue(SubtypeIrradiatedBlock.soil)))
				//
				.addFluidTagInput(FluidTags.WATER, 100)
				//
				.complete(consumer);

		newRecipe(new ItemStack(Items.COAL), 0.0F, RADIOACTIVEPROCESSOR_REQUIRED_TICKS, RADIOACTIVEPROCESSOR_USAGE_PER_TICK, "coal_from_petrified_wood")
				//
				.addItemStackInput(new ItemStack(NuclearScienceBlocks.BLOCKS_IRRADIATED.getValue(SubtypeIrradiatedBlock.petrifiedwood)))
				//
				.addFluidTagInput(FluidTags.WATER, 100)
				//
				.complete(consumer);

		newRecipe(new ItemStack(NuclearScienceItems.ITEM_ACTINIUM225.get()), 0.0F, RADIOACTIVEPROCESSOR_REQUIRED_TICKS, RADIOACTIVEPROCESSOR_USAGE_PER_TICK, "actinium225")
				//
				.addItemTagInput(NuclearScienceTags.Items.OXIDE_ACTINIUM, 1)
				//
				.addFluidTagInput(VoltaicTags.Fluids.HYDROFLUORIC_ACID, 100)
				//
				.complete(consumer);

	}

	public FinishedRecipeItemOutput newRecipe(ItemStack stack, float xp, int ticks, double usagePerTick, String name) {
		return FinishedRecipeItemOutput.of(NuclearScienceRecipies.RADIOACTIVE_PROCESSOR_SERIALIZER.get(), stack, xp, ticks, usagePerTick).name(RecipeCategory.FLUID_ITEM_2_ITEM, modID, "radioactive_processor/" + name);
	}

}
