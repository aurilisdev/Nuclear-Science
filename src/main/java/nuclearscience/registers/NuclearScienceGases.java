package nuclearscience.registers;

import electrodynamics.api.gas.Gas;
import electrodynamics.registers.ElectrodynamicsRegistries;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import nuclearscience.References;
import nuclearscience.prefab.utils.NuclearTextUtils;

public class NuclearScienceGases {

	public static final DeferredRegister<Gas> GASES = DeferredRegister.create(ElectrodynamicsRegistries.GAS_REGISTRY_KEY, References.ID);

	public static final RegistryObject<Gas> URANIUM_HEXAFLUORIDE = GASES.register("uraniumhexafluoride", () -> new Gas(() -> Items.AIR, null, NuclearTextUtils.gas("uraniumhexafluoride")));

}
