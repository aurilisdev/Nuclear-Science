package nuclearscience.datagen.server.recipe.custom.fluiditem2item;


import electrodynamics.common.item.subtype.SubtypeCrystal;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.ItemStack;
import nuclearscience.NuclearScience;
import nuclearscience.common.recipe.categories.fluiditem2item.MSRFuelPreProcessorRecipe;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.registers.NuclearScienceItems;
import voltaic.common.tags.VoltaicTags;
import voltaic.datagen.utils.server.recipe.AbstractRecipeGenerator;
import voltaic.datagen.utils.server.recipe.builders.BaseRecipeBuilder;
import voltaic.datagen.utils.server.recipe.builders.FluidItem2ItemBuilder;

public class NuclearScienceMSRFuelPreprocessorRecipes extends AbstractRecipeGenerator {

	public static double MSRFUELPREPROCESSOR_USAGE_PER_TICK = 1200.0;
	public static int MSRFUELPREPROCESSOR_REQUIRED_TICKS = 200;

	private final String modID;

	public NuclearScienceMSRFuelPreprocessorRecipes() {
		this(NuclearScience.ID);
	}

	public NuclearScienceMSRFuelPreprocessorRecipes(String modID) {
		this.modID = modID;
	}

	@Override
	public void addRecipes(RecipeOutput output) {

		newRecipe(new ItemStack(NuclearScienceItems.ITEM_FLINAK.get()), 0.0F, MSRFUELPREPROCESSOR_REQUIRED_TICKS, MSRFUELPREPROCESSOR_USAGE_PER_TICK, "flinak", modID)
				//
				.addItemTagInput(VoltaicTags.Items.DUST_SALT, 1)
				//
				.addItemTagInput(VoltaicTags.Items.DUST_LITHIUM, 1)
				//
				.addItemStackInput(new ItemStack(ElectrodynamicsItems.ITEMS_CRYSTAL.getValue(SubtypeCrystal.potassiumchloride)))
				//
				.addFluidTagInput(VoltaicTags.Fluids.HYDROFLUORIC_ACID, 1500)
				//
				.save(output);

		newRecipe(new ItemStack(NuclearScienceItems.ITEM_LIFHT4PUF3.get()), 0.0F, MSRFUELPREPROCESSOR_REQUIRED_TICKS, MSRFUELPREPROCESSOR_USAGE_PER_TICK, "lifthf4uf4", modID)
				//
				.addItemTagInput(VoltaicTags.Items.DUST_LITHIUM, 1)
				//
				.addItemTagInput(NuclearScienceTags.Items.DUST_THORIUM, 2)
				//
				.addItemTagInput(NuclearScienceTags.Items.YELLOW_CAKE, 2)
				//
				.addFluidTagInput(VoltaicTags.Fluids.HYDROFLUORIC_ACID, 2500)
				//
				.save(output);

	}

	public FluidItem2ItemBuilder<MSRFuelPreProcessorRecipe> newRecipe(ItemStack stack, float xp, int ticks, double usagePerTick, String name, String group) {
		return new FluidItem2ItemBuilder<>(MSRFuelPreProcessorRecipe::new, stack, BaseRecipeBuilder.RecipeCategory.FLUID_ITEM_2_ITEM, modID, "msr_fuel_preprocessor/" + name, group, xp, ticks, usagePerTick);
	}

}
