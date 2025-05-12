package nuclearscience.registers;

import net.minecraft.world.item.Items;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import nuclearscience.NuclearScience;
import nuclearscience.prefab.utils.NuclearTextUtils;
import voltaic.api.gas.Gas;
import voltaic.prefab.utilities.math.Color;
import voltaic.registers.VoltaicRegistries;

public class NuclearScienceGases {

	public static final DeferredRegister<Gas> GASES = DeferredRegister.create(VoltaicRegistries.GAS_REGISTRY_KEY, NuclearScience.ID);

	public static final RegistryObject<Gas> URANIUM_HEXAFLUORIDE = GASES.register("uraniumhexafluoride", () -> new Gas(() -> Items.AIR, NuclearTextUtils.gas("uraniumhexafluoride"), Color.WHITE));

}
