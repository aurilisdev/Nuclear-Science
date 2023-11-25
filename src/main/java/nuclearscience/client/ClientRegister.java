package nuclearscience.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import electrodynamics.client.guidebook.ScreenGuidebook;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.event.ModelEvent.RegisterAdditional;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import nuclearscience.References;
import nuclearscience.client.guidebook.ModuleNuclearScience;
import nuclearscience.client.render.entity.RenderParticle;
import nuclearscience.client.render.tile.RenderAtomicAssembler;
import nuclearscience.client.render.tile.RenderChemicalExtractor;
import nuclearscience.client.render.tile.RenderFusionReactorCore;
import nuclearscience.client.render.tile.RenderGasCentrifuge;
import nuclearscience.client.render.tile.RenderNuclearBoiler;
import nuclearscience.client.render.tile.RenderQuantumCapacitor;
import nuclearscience.client.render.tile.RenderFissionReactorCore;
import nuclearscience.client.render.tile.RenderRodAssembly;
import nuclearscience.client.render.tile.RenderTeleporter;
import nuclearscience.client.render.tile.RenderTurbine;
import nuclearscience.client.screen.ScreenAtomicAssembler;
import nuclearscience.client.screen.ScreenChemicalExtractor;
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
import nuclearscience.client.screen.ScreenFissionReactorCore;
import nuclearscience.registers.NuclearScienceBlockTypes;
import nuclearscience.registers.NuclearScienceEntities;
import nuclearscience.registers.NuclearScienceMenuTypes;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = References.ID, bus = Bus.MOD, value = { Dist.CLIENT })
public class ClientRegister {

	@SubscribeEvent
	public static void onModelEvent(RegisterAdditional event) {
		event.register(MODEL_GASCENTRIFUGECENTER);
		event.register(MODEL_TURBINECASING);
		event.register(MODEL_TURBINEROTORLAYER);
		event.register(MODEL_FISSIONREACTORCORE);
		event.register(MODEL_FISSIONREACTORFUELROD);
		event.register(MODEL_FISSIONREACTORDEUTERIUM);
		event.register(MODEL_CONTROLRODASSEMBLYSTRUCTURE);
		event.register(MODEL_CONTROLRODASSEMBLYSROD);
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
		MenuScreens.register(NuclearScienceMenuTypes.CONTAINER_GASCENTRIFUGE.get(), ScreenGasCentrifuge::new);
		MenuScreens.register(NuclearScienceMenuTypes.CONTAINER_NUCLEARBOILER.get(), ScreenNuclearBoiler::new);
		MenuScreens.register(NuclearScienceMenuTypes.CONTAINER_CHEMICALEXTRACTOR.get(), ScreenChemicalExtractor::new);
		MenuScreens.register(NuclearScienceMenuTypes.CONTAINER_RADIOISOTOPEGENERATOR.get(), ScreenRadioisotopeGenerator::new);
		MenuScreens.register(NuclearScienceMenuTypes.CONTAINER_FREEZEPLUG.get(), ScreenFreezePlug::new);
		MenuScreens.register(NuclearScienceMenuTypes.CONTAINER_REACTORCORE.get(), ScreenFissionReactorCore::new);
		MenuScreens.register(NuclearScienceMenuTypes.CONTAINER_PARTICLEINJECTOR.get(), ScreenParticleInjector::new);
		MenuScreens.register(NuclearScienceMenuTypes.CONTAINER_QUANTUMCAPACITOR.get(), ScreenQuantumCapacitor::new);
		MenuScreens.register(NuclearScienceMenuTypes.CONTAINER_MSRFUELPREPROCESSOR.get(), ScreenMSRFuelPreProcessor::new);
		MenuScreens.register(NuclearScienceMenuTypes.CONTAINER_RADIOACTIVEPROCESSOR.get(), ScreenRadioactiveProcessor::new);
		MenuScreens.register(NuclearScienceMenuTypes.CONTAINER_MSRREACTORCORE.get(), ScreenMSReactorCore::new);
		MenuScreens.register(NuclearScienceMenuTypes.CONTAINER_MOLTENSALTSUPPLIER.get(), ScreenMoltenSaltSupplier::new);
		MenuScreens.register(NuclearScienceMenuTypes.CONTAINER_ATOMICASSEMBLER.get(), ScreenAtomicAssembler::new);

		ScreenGuidebook.addGuidebookModule(new ModuleNuclearScience());
	}

	@SubscribeEvent
	public static void registerEntities(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(NuclearScienceBlockTypes.TILE_GASCENTRIFUGE.get(), RenderGasCentrifuge::new);
		event.registerBlockEntityRenderer(NuclearScienceBlockTypes.TILE_CHEMICALEXTRACTOR.get(), RenderChemicalExtractor::new);
		event.registerBlockEntityRenderer(NuclearScienceBlockTypes.TILE_CHEMICALBOILER.get(), RenderNuclearBoiler::new);
		event.registerBlockEntityRenderer(NuclearScienceBlockTypes.TILE_TURBINE.get(), RenderTurbine::new);
		event.registerBlockEntityRenderer(NuclearScienceBlockTypes.TILE_REACTORCORE.get(), RenderFissionReactorCore::new);
		event.registerBlockEntityRenderer(NuclearScienceBlockTypes.TILE_FUSIONREACTORCORE.get(), RenderFusionReactorCore::new);
		event.registerBlockEntityRenderer(NuclearScienceBlockTypes.TILE_QUANTUMCAPACITOR.get(), RenderQuantumCapacitor::new);
		event.registerBlockEntityRenderer(NuclearScienceBlockTypes.TILE_TELEPORTER.get(), RenderTeleporter::new);
		event.registerBlockEntityRenderer(NuclearScienceBlockTypes.TILE_CONTROLRODASSEMBLY.get(), RenderRodAssembly::new);
		event.registerBlockEntityRenderer(NuclearScienceBlockTypes.TILE_ATOMICASSEMBLER.get(), RenderAtomicAssembler::new);

		event.registerEntityRenderer(NuclearScienceEntities.ENTITY_PARTICLE.get(), RenderParticle::new);

	}

	public static boolean shouldMultilayerRender(RenderType type) {
		return type == RenderType.translucent() || type == RenderType.solid();
	}
	
	static {
		customTextures.add(ClientRegister.TEXTURE_JEIBLACKHOLE);
	}

	@SubscribeEvent
	public static void addCustomTextureAtlases(TextureStitchEvent.Pre event) {
		if (event.getAtlas().location().equals(TextureAtlas.LOCATION_BLOCKS)) {
			customTextures.forEach(event::addSprite);
		}
	}

	@SubscribeEvent
	public static void cacheCustomTextureAtlases(TextureStitchEvent.Post event) {
		if (event.getAtlas().location().equals(TextureAtlas.LOCATION_BLOCKS)) {
			for (ResourceLocation loc : customTextures) {
				ClientRegister.CACHED_TEXTUREATLASSPRITES.put(loc, event.getAtlas().getSprite(loc));
			}
		}
	}

}
