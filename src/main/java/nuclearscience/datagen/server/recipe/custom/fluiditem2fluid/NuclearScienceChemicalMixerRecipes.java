package nuclearscience.datagen.server.recipe.custom.fluiditem2fluid;

import java.util.function.Consumer;

import electrodynamics.common.fluid.types.subtype.SubtypeSulfateFluid;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.datagen.server.recipe.types.custom.fluiditem2fluid.ElectrodynamicsChemicalMixerRecipes;
import electrodynamics.registers.ElectrodynamicsFluids;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.tags.FluidTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fluids.FluidStack;
import nuclearscience.References;
import nuclearscience.registers.NuclearScienceFluids;

public class NuclearScienceChemicalMixerRecipes extends ElectrodynamicsChemicalMixerRecipes {

	public NuclearScienceChemicalMixerRecipes() {
		super(References.ID);
	}

	@Override
	public void addRecipes(Consumer<IFinishedRecipe> consumer) {

		newRecipe(new FluidStack(NuclearScienceFluids.fluidAmmonia, 1000), 0, CHEMICALMIXER_REQUIRED_TICKS, CHEMICALMIXER_USAGE_PER_TICK, "ammonia")
				//
				.addFluidTagInput(FluidTags.WATER, 1000)
				//
				.addItemTagInput(ElectrodynamicsTags.Items.DUST_SALTPETER, 1)
				//
				.complete(consumer);

		newRecipe(new FluidStack(ElectrodynamicsFluids.getFluid(SubtypeSulfateFluid.iron), 1000), 0, CHEMICALMIXER_REQUIRED_TICKS, CHEMICALMIXER_USAGE_PER_TICK, "ironsulfate_from_ironblock")
				//
				.addFluidTagInput(ElectrodynamicsTags.Fluids.SULFURIC_ACID, 1000)
				//
				.addItemTagInput(Tags.Items.STORAGE_BLOCKS_IRON, 1)
				//
				.complete(consumer);

	}

}
