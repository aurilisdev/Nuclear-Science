package nuclearscience.client;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
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
import nuclearscience.common.block.subtype.SubtypeElectromagent;
import nuclearscience.common.block.subtype.SubtypeNuclearMachine;
import nuclearscience.common.block.subtype.SubtypeRadiationShielding;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.client.VoltaicClientRegister;
import voltaic.client.guidebook.ScreenGuidebook;
import nuclearscience.registers.NuclearScienceBlocks;
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

	public static final ResourceLocation TEXTURE_JEIBLACKHOLE = NuclearScience.rl("block/custom/particleaccelerator_dmblackhole.png");
	public static final ResourceLocation TEXTURE_FUELCELL = NuclearScience.rl("block/custom/fuelcell");
	public static final ResourceLocation TEXTURE_GATEWAYLASER = NuclearScience.rl("block/custom/gatewaylaser");

	private static final HashMap<ResourceLocation, TextureAtlasSprite> CACHED_TEXTUREATLASSPRITES = new HashMap<>();
	private static final List<ResourceLocation> CUSTOM_TEXTURES = Arrays.asList(TEXTURE_FUELCELL, TEXTURE_GATEWAYLASER);

	public static void setup() {
		ScreenManager.register(NuclearScienceMenuTypes.CONTAINER_GASCENTRIFUGE.get(), ScreenGasCentrifuge::new);
		ScreenManager.register(NuclearScienceMenuTypes.CONTAINER_NUCLEARBOILER.get(), ScreenNuclearBoiler::new);
		ScreenManager.register(NuclearScienceMenuTypes.CONTAINER_CHEMICALEXTRACTOR.get(), ScreenChemicalExtractor::new);
		ScreenManager.register(NuclearScienceMenuTypes.CONTAINER_RADIOISOTOPEGENERATOR.get(), ScreenRadioisotopeGenerator::new);
		ScreenManager.register(NuclearScienceMenuTypes.CONTAINER_FREEZEPLUG.get(), ScreenFreezePlug::new);
		ScreenManager.register(NuclearScienceMenuTypes.CONTAINER_REACTORCORE.get(), ScreenFissionReactorCore::new);
		ScreenManager.register(NuclearScienceMenuTypes.CONTAINER_PARTICLEINJECTOR.get(), ScreenParticleInjector::new);
		ScreenManager.register(NuclearScienceMenuTypes.CONTAINER_QUANTUMTUNNEL.get(), ScreenQuantumTunnel::new);
		ScreenManager.register(NuclearScienceMenuTypes.CONTAINER_MSRFUELPREPROCESSOR.get(), ScreenMSRFuelPreProcessor::new);
		ScreenManager.register(NuclearScienceMenuTypes.CONTAINER_RADIOACTIVEPROCESSOR.get(), ScreenRadioactiveProcessor::new);
		ScreenManager.register(NuclearScienceMenuTypes.CONTAINER_MSRREACTORCORE.get(), ScreenMSReactorCore::new);
		ScreenManager.register(NuclearScienceMenuTypes.CONTAINER_MOLTENSALTSUPPLIER.get(), ScreenMoltenSaltSupplier::new);
		ScreenManager.register(NuclearScienceMenuTypes.CONTAINER_ATOMICASSEMBLER.get(), ScreenAtomicAssembler::new);
		ScreenManager.register(NuclearScienceMenuTypes.CONTAINER_TELEPORTER.get(), ScreenTeleporter::new);
		ScreenManager.register(NuclearScienceMenuTypes.CONTAINER_CLOUDCHAMBER.get(), ScreenCloudChamber::new);
		ScreenManager.register(NuclearScienceMenuTypes.CONTAINER_FALLOUTSCRUBBER.get(), ScreenFalloutScrubber::new);
		ScreenManager.register(NuclearScienceMenuTypes.CONTAINER_SUPPLYMODULE.get(), ScreenSupplyModule::new);
		ScreenManager.register(NuclearScienceMenuTypes.CONTAINER_CONTROLRODMODULE.get(), ScreenControlRodModule::new);
		ScreenManager.register(NuclearScienceMenuTypes.CONTAINER_MONITORMODULE.get(), ScreenMonitorModule::new);
		ScreenManager.register(NuclearScienceMenuTypes.CONTAINER_THERMOMETERMODULE.get(), ScreenThermometerModule::new);
		ScreenManager.register(NuclearScienceMenuTypes.CONTAINER_ELECTROMAGNETICGATEWAY.get(), ScreenElectromagneticGateway::new);
		
		ClientRegistry.bindTileEntityRenderer(NuclearScienceTiles.TILE_GASCENTRIFUGE.get(), RenderGasCentrifuge::new);
		ClientRegistry.bindTileEntityRenderer(NuclearScienceTiles.TILE_CHEMICALEXTRACTOR.get(), RenderChemicalExtractor::new);
		ClientRegistry.bindTileEntityRenderer(NuclearScienceTiles.TILE_CHEMICALBOILER.get(), RenderNuclearBoiler::new);
		ClientRegistry.bindTileEntityRenderer(NuclearScienceTiles.TILE_TURBINE.get(), RenderTurbine::new);
		ClientRegistry.bindTileEntityRenderer(NuclearScienceTiles.TILE_REACTORCORE.get(), RenderFissionReactorCore::new);
		ClientRegistry.bindTileEntityRenderer(NuclearScienceTiles.TILE_FUSIONREACTORCORE.get(), RenderFusionReactorCore::new);
		ClientRegistry.bindTileEntityRenderer(NuclearScienceTiles.TILE_QUANTUMCAPACITOR.get(), RenderQuantumTunnel::new);
		ClientRegistry.bindTileEntityRenderer(NuclearScienceTiles.TILE_TELEPORTER.get(), RenderTeleporter::new);
		ClientRegistry.bindTileEntityRenderer(NuclearScienceTiles.TILE_FISSIONCONTROLROD.get(), RenderFissionControlRod::new);
		ClientRegistry.bindTileEntityRenderer(NuclearScienceTiles.TILE_MSCONTROLROD.get(), RenderMSControlRod::new);
		ClientRegistry.bindTileEntityRenderer(NuclearScienceTiles.TILE_ATOMICASSEMBLER.get(), RenderAtomicAssembler::new);
		ClientRegistry.bindTileEntityRenderer(NuclearScienceTiles.TILE_RADIOACTIVEPROCESSOR.get(), RenderRadioactiveProcessor::new);
		ClientRegistry.bindTileEntityRenderer(NuclearScienceTiles.TILE_CLOUDCHAMBER.get(), RenderCloudChamber::new);
		ClientRegistry.bindTileEntityRenderer(NuclearScienceTiles.TILE_FALLOUTSCRUBBER.get(), RenderFalloutScrubber::new);
		ClientRegistry.bindTileEntityRenderer(NuclearScienceTiles.TILE_CONTROLRODMODULE.get(), RenderControlRodModule::new);
		ClientRegistry.bindTileEntityRenderer(NuclearScienceTiles.TILE_FISSIONINTERFACE.get(), RenderFissionInterface::new);
		ClientRegistry.bindTileEntityRenderer(NuclearScienceTiles.TILE_MSINTERFACE.get(), RenderMSInterface::new);
		ClientRegistry.bindTileEntityRenderer(NuclearScienceTiles.TILE_FUSIONINTERFACE.get(), RenderFusionInterface::new);
		ClientRegistry.bindTileEntityRenderer(NuclearScienceTiles.TILE_THERMOMETERMODULE.get(), RenderThermometerModule::new);
		ClientRegistry.bindTileEntityRenderer(NuclearScienceTiles.TILE_MONITORMODULE.get(), RenderMonitorModule::new);
		ClientRegistry.bindTileEntityRenderer(NuclearScienceTiles.TILE_ELECTROMAGNETICGATEWAY.get(), RenderElectromagneticGateway::new);

		EntityRendererManager manager = Minecraft.getInstance().getEntityRenderDispatcher();
		manager.register(NuclearScienceEntities.ENTITY_PARTICLE.get(), new RenderParticle(manager));
		
		ScreenGuidebook.addGuidebookModule(new ModuleNuclearScience());
		
		RenderTypeLookup.setRenderLayer(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.atomicassembler), NuclearScienceClientRegister::shouldMultilayerRender);
		RenderTypeLookup.setRenderLayer(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.chemicalextractor), NuclearScienceClientRegister::shouldMultilayerRender);
		RenderTypeLookup.setRenderLayer(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.cloudchamber), NuclearScienceClientRegister::shouldMultilayerRender);
		RenderTypeLookup.setRenderLayer(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fissioninterface), NuclearScienceClientRegister::shouldMultilayerRender);
		RenderTypeLookup.setRenderLayer(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fissionreactorcore), NuclearScienceClientRegister::shouldMultilayerRender);
		RenderTypeLookup.setRenderLayer(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fissioninterface), NuclearScienceClientRegister::shouldMultilayerRender);
		RenderTypeLookup.setRenderLayer(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.fusioninterface), NuclearScienceClientRegister::shouldMultilayerRender);
		RenderTypeLookup.setRenderLayer(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.logisticscontroller), NuclearScienceClientRegister::shouldMultilayerRender);
		RenderTypeLookup.setRenderLayer(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.msinterface), NuclearScienceClientRegister::shouldMultilayerRender);
		RenderTypeLookup.setRenderLayer(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.nuclearboiler), NuclearScienceClientRegister::shouldMultilayerRender);
		RenderTypeLookup.setRenderLayer(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.radioactiveprocessor), NuclearScienceClientRegister::shouldMultilayerRender);
		RenderTypeLookup.setRenderLayer(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.supplymodule), NuclearScienceClientRegister::shouldMultilayerRender);
		RenderTypeLookup.setRenderLayer(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.thermometermodule), NuclearScienceClientRegister::shouldMultilayerRender);
		RenderTypeLookup.setRenderLayer(NuclearScienceBlocks.BLOCK_ELECTORMAGNETICBOOSTER.get(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(NuclearScienceBlocks.BLOCK_ELECTROMAGNETICDIODE.get(), NuclearScienceClientRegister::shouldMultilayerRender);
		RenderTypeLookup.setRenderLayer(NuclearScienceBlocks.BLOCKS_ELECTROMAGENT.getValue(SubtypeElectromagent.electromagneticglass), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(NuclearScienceBlocks.BLOCK_PLASMA.get(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(NuclearScienceBlocks.BLOCKS_RADIATION_SHIELDING.getValue(SubtypeRadiationShielding.glass), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(NuclearScienceBlocks.BLOCKS_RADIATION_SHIELDING.getValue(SubtypeRadiationShielding.door), RenderType.cutout());
		RenderTypeLookup.setRenderLayer(NuclearScienceBlocks.BLOCKS_RADIATION_SHIELDING.getValue(SubtypeRadiationShielding.trapdoor), RenderType.cutout());
		
	}

	@SubscribeEvent
	public static void onModelEvent(ModelRegistryEvent event) {
		ModelLoader.addSpecialModel(MODEL_GASCENTRIFUGECENTER);
		ModelLoader.addSpecialModel(MODEL_TURBINECASING);
		ModelLoader.addSpecialModel(MODEL_TURBINEROTORLAYER);
		ModelLoader.addSpecialModel(MODEL_FISSIONCONTROLROD_ROD);
		ModelLoader.addSpecialModel(MODEL_MSCONTROLROD_ROD);
		ModelLoader.addSpecialModel(MODEL_FALLOUTSCRUBBER_FAN);
		ModelLoader.addSpecialModel(MODEL_CONTROLRODMODULE_ROD);
		ModelLoader.addSpecialModel(MODEL_FISSIONINTERFACE_ROD);
	}

	@SubscribeEvent
	public static void registerParticles(ParticleFactoryRegisterEvent event) {
		ParticleManager engine = Minecraft.getInstance().particleEngine;
		engine.register(NuclearScienceParticles.PARTICLE_SMOKE.get(), ParticleSmoke.Factory::new);
	}

	@SubscribeEvent
	public static void addCustomTextureAtlases(TextureStitchEvent.Pre event) {
		if (event.getMap().location().equals(AtlasTexture.LOCATION_BLOCKS)) {
			CUSTOM_TEXTURES.forEach(event::addSprite);
		}
	}

	@SubscribeEvent
	public static void cacheCustomTextureAtlases(TextureStitchEvent.Post event) {
		if (event.getMap().location().equals(AtlasTexture.LOCATION_BLOCKS)) {
			CACHED_TEXTUREATLASSPRITES.clear();
			for (ResourceLocation loc : CUSTOM_TEXTURES) {
				NuclearScienceClientRegister.CACHED_TEXTUREATLASSPRITES.put(loc, event.getMap().getSprite(loc));
			}
		}
	}

	public static TextureAtlasSprite getSprite(ResourceLocation sprite) {
		return CACHED_TEXTUREATLASSPRITES.getOrDefault(sprite, VoltaicClientRegister.whiteSprite());
	}
	
	public static boolean shouldMultilayerRender(RenderType type) {
		return type == RenderType.translucent() || type == RenderType.solid();
	}

}