package nuclearscience.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import electrodynamics.client.guidebook.ScreenGuidebook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import nuclearscience.References;
import nuclearscience.client.guidebook.ModuleNuclearScience;
import nuclearscience.client.render.entity.RenderParticle;
import nuclearscience.client.render.tile.RenderAtomicAssembler;
import nuclearscience.client.render.tile.RenderChemicalExtractor;
import nuclearscience.client.render.tile.RenderFissionReactorCore;
import nuclearscience.client.render.tile.RenderFusionReactorCore;
import nuclearscience.client.render.tile.RenderGasCentrifuge;
import nuclearscience.client.render.tile.RenderNuclearBoiler;
import nuclearscience.client.render.tile.RenderQuantumCapacitor;
import nuclearscience.client.render.tile.RenderRodAssembly;
import nuclearscience.client.render.tile.RenderTeleporter;
import nuclearscience.client.render.tile.RenderTurbine;
import nuclearscience.client.screen.ScreenAtomicAssembler;
import nuclearscience.client.screen.ScreenChemicalExtractor;
import nuclearscience.client.screen.ScreenFissionReactorCore;
import nuclearscience.client.screen.ScreenFreezePlug;
import nuclearscience.client.screen.ScreenGasCentrifuge;
import nuclearscience.client.screen.ScreenMSRFuelPreProcessor;
import nuclearscience.client.screen.ScreenMSReactorCore;
import nuclearscience.client.screen.ScreenMoltenSaltSupplier;
import nuclearscience.client.screen.ScreenNuclearBoiler;
import nuclearscience.client.screen.ScreenParticleInjector;
import nuclearscience.client.screen.ScreenQuantumCapacitor;
import nuclearscience.client.screen.ScreenRadioactiveProcessor;
import nuclearscience.client.screen.ScreenRadioisotopeGenerator;
import nuclearscience.registers.NuclearScienceBlockTypes;
import nuclearscience.registers.NuclearScienceBlocks;
import nuclearscience.registers.NuclearScienceEntities;
import nuclearscience.registers.NuclearScienceMenuTypes;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = References.ID, bus = Bus.MOD, value = { Dist.CLIENT })
public class ClientRegister {
	@SubscribeEvent
	public static void onModelEvent(ModelRegistryEvent event) {
		ModelLoader.addSpecialModel(MODEL_GASCENTRIFUGECENTER);
		ModelLoader.addSpecialModel(MODEL_TURBINECASING);
		ModelLoader.addSpecialModel(MODEL_TURBINEROTORLAYER);
		ModelLoader.addSpecialModel(MODEL_FISSIONREACTORCORE);
		ModelLoader.addSpecialModel(MODEL_FISSIONREACTORFUELROD);
		ModelLoader.addSpecialModel(MODEL_FISSIONREACTORDEUTERIUM);
		ModelLoader.addSpecialModel(MODEL_CONTROLRODASSEMBLYSTRUCTURE);
		ModelLoader.addSpecialModel(MODEL_CONTROLRODASSEMBLYSROD);
	}

	public static final ResourceLocation MODEL_GASCENTRIFUGECENTER = new ResourceLocation(References.ID + ":block/gascentrifugecenter");
	public static final ResourceLocation MODEL_TURBINECASING = new ResourceLocation(References.ID + ":block/turbinecasing");
	public static final ResourceLocation MODEL_TURBINEROTORLAYER = new ResourceLocation(References.ID + ":block/turbinerotorlayer");
	public static final ResourceLocation MODEL_FISSIONREACTORCORE = new ResourceLocation(References.ID + ":block/reactorcore");
	public static final ResourceLocation MODEL_FISSIONREACTORFUELROD = new ResourceLocation(References.ID + ":block/reactorfuelrod");
	public static final ResourceLocation MODEL_FISSIONREACTORDEUTERIUM = new ResourceLocation(References.ID + ":block/reactordeuterium");
	public static final ResourceLocation MODEL_CONTROLRODASSEMBLYSTRUCTURE = new ResourceLocation(References.ID + ":block/controlrodassemblystructure");
	public static final ResourceLocation MODEL_CONTROLRODASSEMBLYSROD = new ResourceLocation(References.ID + ":block/controlrodassemblyrod");
	public static final ResourceLocation TEXTURE_REACTORCOREEMPTY = new ResourceLocation(References.ID + ":textures/model/reactorcore.png");

	public static final ResourceLocation TEXTURE_JEIBLACKHOLE = new ResourceLocation(References.ID, "custom/particleaccelerator_dmblackhole");

	public static HashMap<ResourceLocation, TextureAtlasSprite> CACHED_TEXTUREATLASSPRITES = new HashMap<>();
	// for registration purposes only!
	private static final List<ResourceLocation> customTextures = new ArrayList<>();

	public static void setup() {

		ClientRegistry.bindTileEntityRenderer(NuclearScienceBlockTypes.TILE_GASCENTRIFUGE.get(), RenderGasCentrifuge::new);
		ClientRegistry.bindTileEntityRenderer(NuclearScienceBlockTypes.TILE_CHEMICALEXTRACTOR.get(), RenderChemicalExtractor::new);
		ClientRegistry.bindTileEntityRenderer(NuclearScienceBlockTypes.TILE_CHEMICALBOILER.get(), RenderNuclearBoiler::new);
		ClientRegistry.bindTileEntityRenderer(NuclearScienceBlockTypes.TILE_TURBINE.get(), RenderTurbine::new);
		ClientRegistry.bindTileEntityRenderer(NuclearScienceBlockTypes.TILE_REACTORCORE.get(), RenderFissionReactorCore::new);
		ClientRegistry.bindTileEntityRenderer(NuclearScienceBlockTypes.TILE_FUSIONREACTORCORE.get(), RenderFusionReactorCore::new);
		ClientRegistry.bindTileEntityRenderer(NuclearScienceBlockTypes.TILE_QUANTUMCAPACITOR.get(), RenderQuantumCapacitor::new);
		ClientRegistry.bindTileEntityRenderer(NuclearScienceBlockTypes.TILE_TELEPORTER.get(), RenderTeleporter::new);
		ClientRegistry.bindTileEntityRenderer(NuclearScienceBlockTypes.TILE_CONTROLRODASSEMBLY.get(), RenderRodAssembly::new);
		ClientRegistry.bindTileEntityRenderer(NuclearScienceBlockTypes.TILE_ATOMICASSEMBLER.get(), RenderAtomicAssembler::new);


		EntityRendererManager manager = Minecraft.getInstance().getEntityRenderDispatcher();

		manager.register(NuclearScienceEntities.ENTITY_PARTICLE.get(), new RenderParticle(manager));

		ScreenManager.register(NuclearScienceMenuTypes.CONTAINER_GASCENTRIFUGE.get(), ScreenGasCentrifuge::new);
		ScreenManager.register(NuclearScienceMenuTypes.CONTAINER_NUCLEARBOILER.get(), ScreenNuclearBoiler::new);
		ScreenManager.register(NuclearScienceMenuTypes.CONTAINER_CHEMICALEXTRACTOR.get(), ScreenChemicalExtractor::new);
		ScreenManager.register(NuclearScienceMenuTypes.CONTAINER_RADIOISOTOPEGENERATOR.get(), ScreenRadioisotopeGenerator::new);
		ScreenManager.register(NuclearScienceMenuTypes.CONTAINER_FREEZEPLUG.get(), ScreenFreezePlug::new);
		ScreenManager.register(NuclearScienceMenuTypes.CONTAINER_REACTORCORE.get(), ScreenFissionReactorCore::new);
		ScreenManager.register(NuclearScienceMenuTypes.CONTAINER_PARTICLEINJECTOR.get(), ScreenParticleInjector::new);
		ScreenManager.register(NuclearScienceMenuTypes.CONTAINER_QUANTUMCAPACITOR.get(), ScreenQuantumCapacitor::new);
		ScreenManager.register(NuclearScienceMenuTypes.CONTAINER_MSRFUELPREPROCESSOR.get(), ScreenMSRFuelPreProcessor::new);
		ScreenManager.register(NuclearScienceMenuTypes.CONTAINER_RADIOACTIVEPROCESSOR.get(), ScreenRadioactiveProcessor::new);
		ScreenManager.register(NuclearScienceMenuTypes.CONTAINER_MSRREACTORCORE.get(), ScreenMSReactorCore::new);
		ScreenManager.register(NuclearScienceMenuTypes.CONTAINER_MOLTENSALTSUPPLIER.get(), ScreenMoltenSaltSupplier::new);
		ScreenManager.register(NuclearScienceMenuTypes.CONTAINER_ATOMICASSEMBLER.get(), ScreenAtomicAssembler::new);

		RenderTypeLookup.setRenderLayer(NuclearScienceBlocks.blockChemicalExtractor, ClientRegister::shouldMultilayerRender);
		RenderTypeLookup.setRenderLayer(NuclearScienceBlocks.blockNuclearBoiler, ClientRegister::shouldMultilayerRender);
		RenderTypeLookup.setRenderLayer(NuclearScienceBlocks.blockFissionReactorCore, ClientRegister::shouldMultilayerRender);
		RenderTypeLookup.setRenderLayer(NuclearScienceBlocks.blockAtomicAssembler, ClientRegister::shouldMultilayerRender);
		RenderTypeLookup.setRenderLayer(NuclearScienceBlocks.blockElectromagneticGlass, RenderType.cutout());
		RenderTypeLookup.setRenderLayer(NuclearScienceBlocks.blockElectromagneticBooster, RenderType.translucent());
		RenderTypeLookup.setRenderLayer(NuclearScienceBlocks.blockPlasma, RenderType.translucent());
		
		ScreenGuidebook.addGuidebookModule(new ModuleNuclearScience());
	}

	public static boolean shouldMultilayerRender(RenderType type) {
		return type == RenderType.translucent() || type == RenderType.solid();
	}

	static {
		customTextures.add(ClientRegister.TEXTURE_JEIBLACKHOLE);
	}

	@SubscribeEvent
	public static void addCustomTextureAtlases(TextureStitchEvent.Pre event) {
		if (event.getMap().location().equals(AtlasTexture.LOCATION_BLOCKS)) {
			customTextures.forEach(event::addSprite);
		}
	}

	@SubscribeEvent
	public static void cacheCustomTextureAtlases(TextureStitchEvent.Post event) {
		if (event.getMap().location().equals(AtlasTexture.LOCATION_BLOCKS)) {
			for (ResourceLocation loc : customTextures) {
				ClientRegister.CACHED_TEXTUREATLASSPRITES.put(loc, event.getMap().getSprite(loc));
			}
		}
	}

}