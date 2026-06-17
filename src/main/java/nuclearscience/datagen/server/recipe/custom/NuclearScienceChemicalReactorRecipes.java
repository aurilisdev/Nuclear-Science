package nuclearscience.datagen.server.recipe.custom;

import electrodynamics.common.item.subtype.SubtypeCrystal;
import electrodynamics.datagen.server.recipe.types.custom.ElectrodynamicsChemicalReactorRecipes;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import nuclearscience.NuclearScience;
import nuclearscience.registers.NuclearScienceFluids;
import voltaic.api.gas.Gas;
import voltaic.common.tags.VoltaicTags;
import voltaic.datagen.utils.server.recipe.builders.BaseRecipeBuilder;

public class NuclearScienceChemicalReactorRecipes extends ElectrodynamicsChemicalReactorRecipes {

    public NuclearScienceChemicalReactorRecipes() {
	super(NuclearScience.ID);
    }

    @Override
    public void addRecipes(RecipeOutput output) {

	newRecipe(0, 200, 1000, "methanol", modID)
		//
		.setFluidOutput(new FluidStack(NuclearScienceFluids.FLUID_METHANOL, 100))
		//
		.addGasTagInput(VoltaicTags.Gases.CARBON_DIOXIDE, new BaseRecipeBuilder.GasIngWrapper(200, 525, 128))
		//
		.addGasTagInput(VoltaicTags.Gases.HYDROGEN, new BaseRecipeBuilder.GasIngWrapper(200, 525, 128))
		//
		.save(output);

	newRecipe(0, 100, 1000, "decontamination_foam", modID)
		//
		.setFluidOutput(new FluidStack(NuclearScienceFluids.FLUID_DECONTAMINATIONFOAM, 1000))
		//
		.addGasTagInput(VoltaicTags.Gases.AMMONIA,
			new BaseRecipeBuilder.GasIngWrapper(1000, Gas.ROOM_TEMPERATURE, 4))
		//
		.addFluidTagInput(FluidTags.WATER, 2000)
		//
		.addItemStackInput(
			new ItemStack(ElectrodynamicsItems.ITEMS_CRYSTAL.getValue(SubtypeCrystal.potassiumchloride)))
		//
		.addItemTagInput(VoltaicTags.Items.DUST_SULFUR, 2)
		//
		.save(output);

    }
}
