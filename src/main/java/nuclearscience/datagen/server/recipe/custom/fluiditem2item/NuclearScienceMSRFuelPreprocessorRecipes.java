package nuclearscience.datagen.server.recipe.custom.fluiditem2item;

import java.util.function.Consumer;

import electrodynamics.common.item.subtype.SubtypeCrystal;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;
import nuclearscience.NuclearScience;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.registers.NuclearScienceItems;
import nuclearscience.registers.NuclearScienceRecipies;
import voltaic.common.tags.VoltaicTags;
import voltaic.datagen.utils.server.recipe.AbstractRecipeGenerator;
import voltaic.datagen.utils.server.recipe.FinishedRecipeBase.RecipeCategory;
import voltaic.datagen.utils.server.recipe.FinishedRecipeItemOutput;

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
    public void addRecipes(Consumer<FinishedRecipe> consumer) {

	newRecipe(new ItemStack(NuclearScienceItems.ITEM_FLINAK.get()), 0.0F, MSRFUELPREPROCESSOR_REQUIRED_TICKS,
		MSRFUELPREPROCESSOR_USAGE_PER_TICK, "flinak")
		//
		.addItemTagInput(VoltaicTags.Items.DUST_SALT, 1)
		//
		.addItemTagInput(VoltaicTags.Items.DUST_LITHIUM, 1)
		//
		.addItemStackInput(
			new ItemStack(ElectrodynamicsItems.ITEMS_CRYSTAL.getValue(SubtypeCrystal.potassiumchloride)))
		//
		.addFluidTagInput(VoltaicTags.Fluids.HYDROFLUORIC_ACID, 1500)
		//
		.complete(consumer);

	newRecipe(new ItemStack(NuclearScienceItems.ITEM_LIFHT4PUF3.get()), 0.0F, MSRFUELPREPROCESSOR_REQUIRED_TICKS,
		MSRFUELPREPROCESSOR_USAGE_PER_TICK, "lifthf4uf4")
		//
		.addItemTagInput(VoltaicTags.Items.DUST_LITHIUM, 1)
		//
		.addItemTagInput(NuclearScienceTags.Items.DUST_THORIUM, 2)
		//
		.addItemTagInput(NuclearScienceTags.Items.YELLOW_CAKE, 2)
		//
		.addFluidTagInput(VoltaicTags.Fluids.HYDROFLUORIC_ACID, 2500)
		//
		.complete(consumer);

    }

    public FinishedRecipeItemOutput newRecipe(ItemStack stack, float xp, int ticks, double usagePerTick, String name) {
	return FinishedRecipeItemOutput
		.of(NuclearScienceRecipies.MSR_FUEL_PREPROCESSOR_SERIALIZER.get(), stack, xp, ticks, usagePerTick)
		.name(RecipeCategory.FLUID_ITEM_2_ITEM, modID, "msr_fuel_preprocessor/" + name);
    }

}
