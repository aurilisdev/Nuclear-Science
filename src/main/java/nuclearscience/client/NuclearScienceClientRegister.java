package nuclearscience.client;

import java.util.HashMap;
import java.util.List;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import nuclearscience.NuclearScience;
import nuclearscience.client.guidebook.ModuleNuclearScience;
import nuclearscience.client.particle.smoke.ParticleSmoke;
import nuclearscience.client.render.entity.RenderParticle;
import nuclearscience.client.render.tile.RenderAtomicAssembler;
import nuclearscience.client.render.tile.RenderChemicalExtractor;
import nuclearscience.client.render.tile.RenderCloudChamber;
import nuclearscience.client.render.tile.RenderControlRodModule;
import nuclearscience.client.render.tile.RenderElectromagneticGateway;
import nuclearscience.client.render.tile.RenderFalloutScrubber;
import nuclearscience.client.render.tile.RenderFissionControlRod;
import nuclearscience.client.render.tile.RenderFissionInterface;
import nuclearscience.client.render.tile.RenderFissionReactorCore;
import nuclearscience.client.render.tile.RenderFusionInterface;
import nuclearscience.client.render.tile.RenderFusionReactorCore;
import nuclearscience.client.render.tile.RenderGasCentrifuge;
import nuclearscience.client.render.tile.RenderMSControlRod;
import nuclearscience.client.render.tile.RenderMSInterface;
import nuclearscience.client.render.tile.RenderMonitorModule;
import nuclearscience.client.render.tile.RenderNuclearBoiler;
import nuclearscience.client.render.tile.RenderQuantumTunnel;
import nuclearscience.client.render.tile.RenderRadioactiveProcessor;
import nuclearscience.client.render.tile.RenderTeleporter;
import nuclearscience.client.render.tile.RenderThermometerModule;
import nuclearscience.client.render.tile.RenderTurbine;
import nuclearscience.client.screen.ScreenAtomicAssembler;
import nuclearscience.client.screen.ScreenChemicalExtractor;
import nuclearscience.client.screen.ScreenCloudChamber;
import nuclearscience.client.screen.ScreenControlRodModule;
import nuclearscience.client.screen.ScreenElectromagneticGateway;
import nuclearscience.client.screen.ScreenFalloutScrubber;
import nuclearscience.client.screen.ScreenFissionReactorCore;
import nuclearscience.client.screen.ScreenFreezePlug;
import nuclearscience.client.screen.ScreenGasCentrifuge;
import nuclearscience.client.screen.ScreenMSRFuelPreProcessor;
import nuclearscience.client.screen.ScreenMSReactorCore;
import nuclearscience.client.screen.ScreenMoltenSaltSupplier;
import nuclearscience.client.screen.ScreenMonitorModule;
import nuclearscience.client.screen.ScreenNuclearBoiler;
import nuclearscience.client.screen.ScreenParticleInjector;
import nuclearscience.client.screen.ScreenQuantumTunnel;
import nuclearscience.client.screen.ScreenRadioactiveProcessor;
import nuclearscience.client.screen.ScreenRadioisotopeGenerator;
import nuclearscience.client.screen.ScreenSupplyModule;
import nuclearscience.client.screen.ScreenTeleporter;
import nuclearscience.client.screen.ScreenThermometerModule;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.client.VoltaicClientRegister;
import voltaic.client.guidebook.ScreenGuidebook;
import nuclearscience.registers.NuclearScienceEntities;
import nuclearscience.registers.NuclearScienceMenuTypes;
import nuclearscience.registers.NuclearScienceParticles;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = NuclearScience.ID, bus = Bus.MOD, value = { Dist.CLIENT })
public class NuclearScienceClientRegister {

	public static final ResourceLocation MODEL_GASCENTRIFUGECENTER = NuclearScience.rl("block/gascentrifugecenter");
	public static final ResourceLocation MODEL_TURBINECASING = NuclearScience.rl("block/turbinecasing");
	public static final ResourceLocation MODEL_TURBINEROTORLAYER = NuclearScience.rl("block/turbinerotorlayer");
	public static final ResourceLocation MODEL_FISSIONCONTROLROD_ROD = NuclearScience.rl("block/fissioncontrolrodrod");
	public static final ResourceLocation MODEL_MSCONTROLROD_ROD = NuclearScience.rl("block/mscontrolrodrod");
	public static final ResourceLocation MODEL_FALLOUTSCRUBBER_FAN = NuclearScience.rl("block/falloutscrubberfan");
	public static final ResourceLocation MODEL_CONTROLRODMODULE_ROD = NuclearScience.rl("block/controlrodmodulerod");
	public static final ResourceLocation MODEL_FISSIONINTERFACE_ROD = NuclearScience.rl("block/fissioninterfacerods");

	public static final ResourceLocation TEXTURE_JEIBLACKHOLE = NuclearScience.rl("block/custom/particleaccelerator_dmblackhole");
	public static final ResourceLocation TEXTURE_FUELCELL = NuclearScience.rl("block/custom/fuelcell");
	public static final ResourceLocation TEXTURE_GATEWAYLASER = NuclearScience.rl("block/custom/gatewaylaser");

	private static final HashMap<ResourceLocation, TextureAtlasSprite> CACHED_TEXTUREATLASSPRITES = new HashMap<>();
	private static final List<ResourceLocation> CUSTOM_TEXTURES = List.of(TEXTURE_FUELCELL, TEXTURE_GATEWAYLASER);

	public static void setup() {
		MenuScreens.register(NuclearScienceMenuTypes.CONTAINER_GASCENTRIFUGE.get(), ScreenGasCentrifuge::new);
		MenuScreens.register(NuclearScienceMenuTypes.CONTAINER_NUCLEARBOILER.get(), ScreenNuclearBoiler::new);
		MenuScreens.register(NuclearScienceMenuTypes.CONTAINER_CHEMICALEXTRACTOR.get(), ScreenChemicalExtractor::new);
		MenuScreens.register(NuclearScienceMenuTypes.CONTAINER_RADIOISOTOPEGENERATOR.get(), ScreenRadioisotopeGenerator::new);
		MenuScreens.register(NuclearScienceMenuTypes.CONTAINER_FREEZEPLUG.get(), ScreenFreezePlug::new);
		MenuScreens.register(NuclearScienceMenuTypes.CONTAINER_REACTORCORE.get(), ScreenFissionReactorCore::new);
		MenuScreens.register(NuclearScienceMenuTypes.CONTAINER_PARTICLEINJECTOR.get(), ScreenParticleInjector::new);
		MenuScreens.register(NuclearScienceMenuTypes.CONTAINER_QUANTUMTUNNEL.get(), ScreenQuantumTunnel::new);
		MenuScreens.register(NuclearScienceMenuTypes.CONTAINER_MSRFUELPREPROCESSOR.get(), ScreenMSRFuelPreProcessor::new);
		MenuScreens.register(NuclearScienceMenuTypes.CONTAINER_RADIOACTIVEPROCESSOR.get(), ScreenRadioactiveProcessor::new);
		MenuScreens.register(NuclearScienceMenuTypes.CONTAINER_MSRREACTORCORE.get(), ScreenMSReactorCore::new);
		MenuScreens.register(NuclearScienceMenuTypes.CONTAINER_MOLTENSALTSUPPLIER.get(), ScreenMoltenSaltSupplier::new);
		MenuScreens.register(NuclearScienceMenuTypes.CONTAINER_ATOMICASSEMBLER.get(), ScreenAtomicAssembler::new);
		MenuScreens.register(NuclearScienceMenuTypes.CONTAINER_TELEPORTER.get(), ScreenTeleporter::new);
		MenuScreens.register(NuclearScienceMenuTypes.CONTAINER_CLOUDCHAMBER.get(), ScreenCloudChamber::new);
		MenuScreens.register(NuclearScienceMenuTypes.CONTAINER_FALLOUTSCRUBBER.get(), ScreenFalloutScrubber::new);
		MenuScreens.register(NuclearScienceMenuTypes.CONTAINER_SUPPLYMODULE.get(), ScreenSupplyModule::new);
		MenuScreens.register(NuclearScienceMenuTypes.CONTAINER_CONTROLRODMODULE.get(), ScreenControlRodModule::new);
		MenuScreens.register(NuclearScienceMenuTypes.CONTAINER_MONITORMODULE.get(), ScreenMonitorModule::new);
		MenuScreens.register(NuclearScienceMenuTypes.CONTAINER_THERMOMETERMODULE.get(), ScreenThermometerModule::new);
		MenuScreens.register(NuclearScienceMenuTypes.CONTAINER_ELECTROMAGNETICGATEWAY.get(), ScreenElectromagneticGateway::new);

		ScreenGuidebook.addGuidebookModule(new ModuleNuclearScience());
	}

	@SubscribeEvent
	public static void onModelEvent(ModelEvent.RegisterAdditional event) {
		event.register(MODEL_GASCENTRIFUGECENTER);
		event.register(MODEL_TURBINECASING);
		event.register(MODEL_TURBINEROTORLAYER);
		event.register(MODEL_FISSIONCONTROLROD_ROD);
		event.register(MODEL_MSCONTROLROD_ROD);
		event.register(MODEL_FALLOUTSCRUBBER_FAN);
		event.register(MODEL_CONTROLRODMODULE_ROD);
		event.register(MODEL_FISSIONINTERFACE_ROD);
	}

	@SubscribeEvent
	public static void registerEntities(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(NuclearScienceTiles.TILE_GASCENTRIFUGE.get(), RenderGasCentrifuge::new);
		event.registerBlockEntityRenderer(NuclearScienceTiles.TILE_CHEMICALEXTRACTOR.get(), RenderChemicalExtractor::new);
		event.registerBlockEntityRenderer(NuclearScienceTiles.TILE_CHEMICALBOILER.get(), RenderNuclearBoiler::new);
		event.registerBlockEntityRenderer(NuclearScienceTiles.TILE_TURBINE.get(), RenderTurbine::new);
		event.registerBlockEntityRenderer(NuclearScienceTiles.TILE_REACTORCORE.get(), RenderFissionReactorCore::new);
		event.registerBlockEntityRenderer(NuclearScienceTiles.TILE_FUSIONREACTORCORE.get(), RenderFusionReactorCore::new);
		event.registerBlockEntityRenderer(NuclearScienceTiles.TILE_QUANTUMCAPACITOR.get(), RenderQuantumTunnel::new);
		event.registerBlockEntityRenderer(NuclearScienceTiles.TILE_TELEPORTER.get(), RenderTeleporter::new);
		event.registerBlockEntityRenderer(NuclearScienceTiles.TILE_FISSIONCONTROLROD.get(), RenderFissionControlRod::new);
		event.registerBlockEntityRenderer(NuclearScienceTiles.TILE_MSCONTROLROD.get(), RenderMSControlRod::new);
		event.registerBlockEntityRenderer(NuclearScienceTiles.TILE_ATOMICASSEMBLER.get(), RenderAtomicAssembler::new);
		event.registerBlockEntityRenderer(NuclearScienceTiles.TILE_RADIOACTIVEPROCESSOR.get(), RenderRadioactiveProcessor::new);
		event.registerBlockEntityRenderer(NuclearScienceTiles.TILE_CLOUDCHAMBER.get(), RenderCloudChamber::new);
		event.registerBlockEntityRenderer(NuclearScienceTiles.TILE_FALLOUTSCRUBBER.get(), RenderFalloutScrubber::new);
		event.registerBlockEntityRenderer(NuclearScienceTiles.TILE_CONTROLRODMODULE.get(), RenderControlRodModule::new);
		event.registerBlockEntityRenderer(NuclearScienceTiles.TILE_FISSIONINTERFACE.get(), RenderFissionInterface::new);
		event.registerBlockEntityRenderer(NuclearScienceTiles.TILE_MSINTERFACE.get(), RenderMSInterface::new);
		event.registerBlockEntityRenderer(NuclearScienceTiles.TILE_FUSIONINTERFACE.get(), RenderFusionInterface::new);
		event.registerBlockEntityRenderer(NuclearScienceTiles.TILE_THERMOMETERMODULE.get(), RenderThermometerModule::new);
		event.registerBlockEntityRenderer(NuclearScienceTiles.TILE_MONITORMODULE.get(), RenderMonitorModule::new);
		event.registerBlockEntityRenderer(NuclearScienceTiles.TILE_ELECTROMAGNETICGATEWAY.get(), RenderElectromagneticGateway::new);

		event.registerEntityRenderer(NuclearScienceEntities.ENTITY_PARTICLE.get(), RenderParticle::new);

	}

	@SubscribeEvent
	public static void registerParticles(RegisterParticleProvidersEvent event) {
		event.registerSpriteSet(NuclearScienceParticles.PARTICLE_SMOKE.get(), ParticleSmoke.Factory::new);
	}

	@SubscribeEvent
	public static void cacheCustomTextureAtlases(TextureStitchEvent.Post event) {
		if (event.getAtlas().location().equals(TextureAtlas.LOCATION_BLOCKS)) {
			CACHED_TEXTUREATLASSPRITES.clear();
			for (ResourceLocation loc : CUSTOM_TEXTURES) {
				NuclearScienceClientRegister.CACHED_TEXTUREATLASSPRITES.put(loc, event.getAtlas().getSprite(loc));
			}
		}
	}

	public static TextureAtlasSprite getSprite(ResourceLocation sprite) {
		return CACHED_TEXTUREATLASSPRITES.getOrDefault(sprite, VoltaicClientRegister.whiteSprite());
	}

}
