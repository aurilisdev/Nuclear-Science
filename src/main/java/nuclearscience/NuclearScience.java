package nuclearscience;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import nuclearscience.client.NuclearScienceClientRegister;
import nuclearscience.common.block.voxelshapes.NuclearScienceVoxelShapes;
import nuclearscience.common.packet.NetworkHandler;
import nuclearscience.common.reloadlistener.AtomicAssemblerBlacklistRegister;
import nuclearscience.common.reloadlistener.AtomicAssemblerWhitelistRegister;
import nuclearscience.common.settings.NuclearConstants;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.registers.UnifiedNuclearScienceRegister;
import voltaic.prefab.configuration.ConfigurationHandler;

@Mod(NuclearScience.ID)
@EventBusSubscriber(modid = NuclearScience.ID, bus = EventBusSubscriber.Bus.MOD)
public class NuclearScience {

    public static final String ID = "nuclearscience";
    public static final String NAME = "Nuclear Science";

    public NuclearScience() {
	ConfigurationHandler.registerConfig(NuclearConstants.class);
	NuclearScienceVoxelShapes.init();
	IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
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
	NetworkHandler.init();
	NuclearScienceTags.init();
	AtomicAssemblerBlacklistRegister.INSTANCE = new AtomicAssemblerBlacklistRegister()
		.subscribeAsSyncable(NetworkHandler.CHANNEL);
	AtomicAssemblerWhitelistRegister.INSTANCE = new AtomicAssemblerWhitelistRegister()
		.subscribeAsSyncable(NetworkHandler.CHANNEL);
    }

    public static final ResourceLocation rl(String path) {
	return new ResourceLocation(NuclearScience.ID, path);
    }

}
