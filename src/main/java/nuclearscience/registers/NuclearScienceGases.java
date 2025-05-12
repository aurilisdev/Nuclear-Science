package nuclearscience.registers;

import net.minecraft.core.Holder;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import nuclearscience.NuclearScience;
import nuclearscience.prefab.utils.NuclearTextUtils;
import voltaic.api.gas.Gas;
import voltaic.prefab.utilities.math.Color;
import voltaic.registers.VoltaicGases;

public class NuclearScienceGases {

	public static final DeferredRegister<Gas> GASES = DeferredRegister.create(VoltaicGases.GAS_REGISTRY_KEY, NuclearScience.ID);

	public static final DeferredHolder<Gas, Gas> URANIUM_HEXAFLUORIDE = GASES.register("uraniumhexafluoride", () -> new Gas(Holder.direct(Items.AIR), NuclearTextUtils.gas("uraniumhexafluoride"), Color.WHITE));

}
