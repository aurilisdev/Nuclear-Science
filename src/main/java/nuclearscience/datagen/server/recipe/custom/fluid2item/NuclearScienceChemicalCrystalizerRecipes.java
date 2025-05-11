package nuclearscience.datagen.server.recipe.custom.fluid2item;

import java.util.function.Consumer;

import electrodynamics.datagen.server.recipe.types.custom.fluid2item.ElectrodynamicsChemicalCrystallizerRecipes;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.ItemStack;
import nuclearscience.NuclearScience;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.registers.NuclearScienceItems;

public class NuclearScienceChemicalCrystalizerRecipes extends ElectrodynamicsChemicalCrystallizerRecipes {

    public NuclearScienceChemicalCrystalizerRecipes() {
        super(NuclearScience.ID);
    }

    @Override
    public void addRecipes(Consumer<IFinishedRecipe> consumer) {

        newRecipe(new ItemStack(NuclearScienceItems.ITEM_IODINETABLET.get()), 0, CHEMICALCRYSTALLIZER_REQUIRED_TICKS, CHEMICALCRYSTALLIZER_USAGE_PER_TICK, "iodine_tablet_from_iodine_solution")
                //
                .addFluidTagInput(NuclearScienceTags.Fluids.IODINE_SOLUTION, 500)
                //
                .complete(consumer);


    }
}
