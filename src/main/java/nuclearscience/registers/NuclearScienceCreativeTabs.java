package nuclearscience.registers;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import nuclearscience.NuclearScience;
import nuclearscience.prefab.utils.NuclearTextUtils;
import nuclearscience.common.block.subtype.SubtypeNuclearMachine;

public class NuclearScienceCreativeTabs {

	public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, NuclearScience.ID);

	public static final RegistryObject<CreativeModeTab> MAIN = CREATIVE_TABS.register("main", () -> CreativeModeTab.builder().title(NuclearTextUtils.creativeTab("main")).icon(() -> new ItemStack(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.gascentrifuge))).build());

}
