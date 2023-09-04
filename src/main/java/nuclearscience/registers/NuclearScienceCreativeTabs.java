package nuclearscience.registers;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import nuclearscience.References;
import nuclearscience.prefab.utils.NuclearTextUtils;

public class NuclearScienceCreativeTabs {
	
	public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, References.ID);

	public static final RegistryObject<CreativeModeTab> MAIN = CREATIVE_TABS.register("main", () -> CreativeModeTab.builder().title(NuclearTextUtils.creativeTab("main")).icon(() -> new ItemStack(NuclearScienceBlocks.blockGasCentrifuge)).build());
	
}
