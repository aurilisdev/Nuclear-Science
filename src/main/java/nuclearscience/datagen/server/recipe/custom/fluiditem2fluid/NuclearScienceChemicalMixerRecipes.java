package nuclearscience.datagen.server.recipe.custom.fluiditem2fluid;

import java.util.function.Consumer;

import electrodynamics.common.fluid.subtype.SubtypeSulfateFluid;
import electrodynamics.common.item.subtype.SubtypeCrystal;
import electrodynamics.datagen.server.recipe.types.custom.fluiditem2fluid.ElectrodynamicsChemicalMixerRecipes;
import electrodynamics.registers.ElectrodynamicsFluids;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fluids.FluidStack;
import nuclearscience.NuclearScience;
import nuclearscience.registers.NuclearScienceFluids;
import voltaic.common.tags.VoltaicTags;

public class NuclearScienceChemicalMixerRecipes extends ElectrodynamicsChemicalMixerRecipes {

	public NuclearScienceChemicalMixerRecipes() {
		super(NuclearScience.ID);
	}

	@Override
	public void addRecipes(Consumer<FinishedRecipe> consumer) {

		newRecipe(new FluidStack(ElectrodynamicsFluids.FLUID_AMMONIA.get(), 1000), 0, CHEMICALMIXER_REQUIRED_TICKS, CHEMICALMIXER_USAGE_PER_TICK, "ammonia")
				//
				.addFluidTagInput(FluidTags.WATER, 1000)
				//
				.addItemTagInput(VoltaicTags.Items.DUST_SALTPETER, 1)
				//
				.complete(consumer);

		newRecipe(new FluidStack(ElectrodynamicsFluids.FLUIDS_SULFATE.getValue(SubtypeSulfateFluid.iron), 1000), 0, CHEMICALMIXER_REQUIRED_TICKS, CHEMICALMIXER_USAGE_PER_TICK, "ironsulfate_from_ironblock")
				//
				.addFluidTagInput(VoltaicTags.Fluids.SULFURIC_ACID, 1000)
				//
				.addItemTagInput(Tags.Items.STORAGE_BLOCKS_RAW_IRON, 1)
				//
				.complete(consumer);

		newRecipe(new FluidStack(NuclearScienceFluids.FLUID_IODINESOLUTION.get(), 100), 0, CHEMICALMIXER_REQUIRED_TICKS, CHEMICALMIXER_USAGE_PER_TICK, "iodine_solution_from_eggs")
				//
				.addFluidTagInput(VoltaicTags.Fluids.SULFURIC_ACID, 200)
				//
				.addItemTagInput(Tags.Items.EGGS, 1)
				//
				.complete(consumer);

		newRecipe(new FluidStack(NuclearScienceFluids.FLUID_IODINESOLUTION.get(), 100), 0, CHEMICALMIXER_REQUIRED_TICKS, CHEMICALMIXER_USAGE_PER_TICK, "iodine_solution_from_kelp")
				//
				.addFluidTagInput(VoltaicTags.Fluids.SULFURIC_ACID, 200)
				//
				.addItemStackInput(new ItemStack(Items.DRIED_KELP_BLOCK))
				//
				.complete(consumer);

		newRecipe(new FluidStack(NuclearScienceFluids.FLUID_METHANOL.get(), 100), 0, 200, 1000, "methanol")
				//
				.addFluidTagInput(VoltaicTags.Fluids.HYDROGEN, 200)
				//
				.addItemTagInput(ItemTags.COALS, 2)
				//
				.complete(consumer);

		newRecipe(new FluidStack(NuclearScienceFluids.FLUID_DECONTAMINATIONFOAM.get(), 1000), 0, 100, 1000, "decontamination_foam")
				//
				.addFluidTagInput(VoltaicTags.Fluids.AMMONIA, 1000)
				//
				.addItemStackInput(new ItemStack(ElectrodynamicsItems.ITEMS_CRYSTAL.getValue(SubtypeCrystal.potassiumchloride), 2))
				//
				.complete(consumer);

	}

}
