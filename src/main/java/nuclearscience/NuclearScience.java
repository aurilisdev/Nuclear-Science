package nuclearscience;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import nuclearscience.client.NuclearScienceClientRegister;
import nuclearscience.common.block.states.NuclearScienceBlockStates;
import nuclearscience.common.block.voxelshapes.NuclearScienceVoxelShapes;
import nuclearscience.common.reloadlistener.AtomicAssemblerBlacklistRegister;
import nuclearscience.common.reloadlistener.AtomicAssemblerWhitelistRegister;
import nuclearscience.common.settings.NuclearConfig;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.registers.UnifiedNuclearScienceRegister;

@Mod(NuclearScience.ID)
@EventBusSubscriber(modid = NuclearScience.ID, bus = EventBusSubscriber.Bus.MOD)
public final class NuclearScience {

    public static final String ID = "nuclearscience";
    public static final String NAME = "Nuclear Science";

    public NuclearScience(IEventBus bus, ModContainer container) {
	NuclearConfig.INSTANCE = new NuclearConfig();
	container.registerConfig(ModConfig.Type.COMMON, NuclearConfig.INSTANCE.SPEC);
	if (FMLEnvironment.dist == Dist.CLIENT) {
	    container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
	}
	NuclearScienceBlockStates.init();
	NuclearScienceVoxelShapes.init();
	UnifiedNuclearScienceRegister.register(bus);
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onClientSetup(FMLClientSetupEvent event) {
	event.enqueueWork(() -> {
	    NuclearScienceClientRegister.setup();
	});
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
	NuclearScienceTags.init();
	AtomicAssemblerBlacklistRegister.INSTANCE = new AtomicAssemblerBlacklistRegister().subscribeAsSyncable();
	AtomicAssemblerWhitelistRegister.INSTANCE = new AtomicAssemblerWhitelistRegister().subscribeAsSyncable();

    }

    public static final ResourceLocation rl(String path) {
	return ResourceLocation.fromNamespaceAndPath(NuclearScience.ID, path);
    }

}
