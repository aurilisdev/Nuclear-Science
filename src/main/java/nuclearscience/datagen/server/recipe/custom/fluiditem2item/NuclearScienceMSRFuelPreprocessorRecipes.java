package nuclearscience.datagen.server.recipe.custom.fluiditem2item;

import java.util.function.Consumer;

import electrodynamics.datagen.utils.recipe.AbstractRecipeGenerator;
import electrodynamics.datagen.utils.recipe.FinishedRecipeItemOutput;
import electrodynamics.registers.ElectrodynamicsItems;
import electrodynamics.common.item.subtype.SubtypeCrystal;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.datagen.utils.recipe.AbstractElectrodynamicsFinishedRecipe.RecipeCategory;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;
import nuclearscience.References;
import nuclearscience.common.recipe.NuclearScienceRecipeInit;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.registers.NuclearScienceItems;

public class NuclearScienceMSRFuelPreprocessorRecipes extends AbstractRecipeGenerator {

	public static double MSRFUELPREPROCESSOR_USAGE_PER_TICK = 1200.0;
	public static int MSRFUELPREPROCESSOR_REQUIRED_TICKS = 200;

	private final String modID;

	public NuclearScienceMSRFuelPreprocessorRecipes() {
		this(References.ID);
	}

	public NuclearScienceMSRFuelPreprocessorRecipes(String modID) {
		this.modID = modID;
	}

	@Override
	public void addRecipes(Consumer<FinishedRecipe> consumer) {

		newRecipe(new ItemStack(NuclearScienceItems.ITEM_FLINAK.get()), 0.0F, MSRFUELPREPROCESSOR_REQUIRED_TICKS, MSRFUELPREPROCESSOR_USAGE_PER_TICK, "flinak")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.DUST_SALT, 1)
				//
				.addItemTagInput(ElectrodynamicsTags.Items.DUST_LITHIUM, 1)
				//
				.addItemStackInput(new ItemStack(ElectrodynamicsItems.getItem(SubtypeCrystal.potassiumchloride)))
				//
				.addFluidTagInput(ElectrodynamicsTags.Fluids.HYDROGEN_FLUORIDE, 1500)
				//
				.complete(consumer);

		newRecipe(new ItemStack(NuclearScienceItems.ITEM_LIFHT4PUF3.get()), 0.0F, MSRFUELPREPROCESSOR_REQUIRED_TICKS, MSRFUELPREPROCESSOR_USAGE_PER_TICK, "lifthf4uf4")
				//
				.addItemTagInput(ElectrodynamicsTags.Items.DUST_LITHIUM, 1)
				//
				.addItemTagInput(NuclearScienceTags.Items.DUST_THORIUM, 2)
				//
				.addItemTagInput(NuclearScienceTags.Items.YELLOW_CAKE, 2)
				//
				.addFluidTagInput(ElectrodynamicsTags.Fluids.HYDROGEN_FLUORIDE, 2500)
				//
				.complete(consumer);

	}

	public FinishedRecipeItemOutput newRecipe(ItemStack stack, float xp, int ticks, double usagePerTick, String name) {
		return FinishedRecipeItemOutput.of(NuclearScienceRecipeInit.MSR_FUEL_PREPROCESSOR_SERIALIZER.get(), stack, xp, ticks, usagePerTick).name(RecipeCategory.FLUID_ITEM_2_ITEM, modID, "msr_fuel_preprocessor/" + name);
	}

}
