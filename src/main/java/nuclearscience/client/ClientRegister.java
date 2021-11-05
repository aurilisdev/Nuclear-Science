package nuclearscience.client;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import nuclearscience.DeferredRegisters;
import nuclearscience.References;
import nuclearscience.client.render.entity.RenderParticle;
import nuclearscience.client.render.tile.RenderChemicalExtractor;
import nuclearscience.client.render.tile.RenderFuelReprocessor;
import nuclearscience.client.render.tile.RenderFusionReactorCore;
import nuclearscience.client.render.tile.RenderGasCentrifuge;
import nuclearscience.client.render.tile.RenderMoltenSaltSupplier;
import nuclearscience.client.render.tile.RenderNuclearBoiler;
import nuclearscience.client.render.tile.RenderQuantumCapacitor;
import nuclearscience.client.render.tile.RenderRadioactiveProcessor;
import nuclearscience.client.render.tile.RenderReactorCore;
import nuclearscience.client.render.tile.RenderRodAssembly;
import nuclearscience.client.render.tile.RenderTeleporter;
import nuclearscience.client.render.tile.RenderTurbine;
import nuclearscience.client.screen.ScreenChemicalExtractor;
import nuclearscience.client.screen.ScreenFreezePlug;
import nuclearscience.client.screen.ScreenGasCentrifuge;
import nuclearscience.client.screen.ScreenMSRFuelPreProcessor;
import nuclearscience.client.screen.ScreenMSRReactorCore;
import nuclearscience.client.screen.ScreenMoltenSaltSupplier;
import nuclearscience.client.screen.ScreenNuclearBoiler;
import nuclearscience.client.screen.ScreenParticleInjector;
import nuclearscience.client.screen.ScreenQuantumCapacitor;
import nuclearscience.client.screen.ScreenRadioactiveProcessor;
import nuclearscience.client.screen.ScreenRadioisotopeGenerator;
import nuclearscience.client.screen.ScreenReactorCore;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = References.ID, bus = Bus.MOD, value = { Dist.CLIENT })
public class ClientRegister {
    @SubscribeEvent
    public static void onModelEvent(ModelRegistryEvent event) {
	ModelLoader.addSpecialModel(MODEL_GASCENTRIFUGEFULL);
	ModelLoader.addSpecialModel(MODEL_GASCENTRIFUGEOUTLINE);
	ModelLoader.addSpecialModel(MODEL_GASCENTRIFUGECENTER);
	ModelLoader.addSpecialModel(MODEL_CHEMICALEXTRACTORWATER);
	ModelLoader.addSpecialModel(MODEL_CHEMICALBOILERWATER);
	ModelLoader.addSpecialModel(MODEL_CHEMICALBOILERHEXAFLUORIDE);
	ModelLoader.addSpecialModel(MODEL_TURBINEFULL);
	ModelLoader.addSpecialModel(MODEL_TURBINECASING);
	ModelLoader.addSpecialModel(MODEL_TURBINEROTORLAYER);
	ModelLoader.addSpecialModel(MODEL_REACTORCORE);
	ModelLoader.addSpecialModel(MODEL_REACTORFUELROD);
	ModelLoader.addSpecialModel(MODEL_REACTORDEUTERIUM);
	ModelLoader.addSpecialModel(MODEL_TELEPORTERON);
	ModelLoader.addSpecialModel(MODEL_TELEPORTER);
	ModelLoader.addSpecialModel(MODEL_CONTROLRODASSEMBLYSTRUCTURE);
	ModelLoader.addSpecialModel(MODEL_CONTROLRODASSEMBLYSROD);
	ModelLoader.addSpecialModel(MODEL_FUELREPROCESSOR);
	ModelLoader.addSpecialModel(MODEL_FUELREPROCESSOR_ON);
	ModelLoader.addSpecialModel(MODEL_RADIOACTIVEPROCESSOR);
	ModelLoader.addSpecialModel(MODEL_RADIOACTIVEPROCESSOR_ON);
	ModelLoader.addSpecialModel(MODEL_MOLTENSALTSUPPLIER);
	ModelLoader.addSpecialModel(MODEL_MOLTENSALTSUPPLIER_ON);
    }

    public static final ResourceLocation MODEL_GASCENTRIFUGEFULL = new ResourceLocation(References.ID + ":block/gascentrifuge");
    public static final ResourceLocation MODEL_GASCENTRIFUGEOUTLINE = new ResourceLocation(References.ID + ":block/gascentrifugeoutline");
    public static final ResourceLocation MODEL_GASCENTRIFUGECENTER = new ResourceLocation(References.ID + ":block/gascentrifugecenter");
    public static final ResourceLocation MODEL_CHEMICALEXTRACTORWATER = new ResourceLocation(References.ID + ":block/chemicalextractorwater");
    public static final ResourceLocation MODEL_CHEMICALBOILERWATER = new ResourceLocation(References.ID + ":block/chemicalboilerwater");
    public static final ResourceLocation MODEL_CHEMICALBOILERHEXAFLUORIDE = new ResourceLocation(References.ID + ":block/chemicalboilerhexafluoride");
    public static final ResourceLocation MODEL_TURBINEFULL = new ResourceLocation(References.ID + ":block/turbine");
    public static final ResourceLocation MODEL_TURBINECASING = new ResourceLocation(References.ID + ":block/turbinecasing");
    public static final ResourceLocation MODEL_TURBINEROTORLAYER = new ResourceLocation(References.ID + ":block/turbinerotorlayer");
    public static final ResourceLocation MODEL_REACTORCORE = new ResourceLocation(References.ID + ":block/reactorcore");
    public static final ResourceLocation MODEL_REACTORFUELROD = new ResourceLocation(References.ID + ":block/reactorfuelrod");
    public static final ResourceLocation MODEL_REACTORDEUTERIUM = new ResourceLocation(References.ID + ":block/reactordeuterium");
    public static final ResourceLocation TEXTURE_REACTORCOREEMPTY = new ResourceLocation(References.ID + ":textures/model/reactorcore.png");
    public static final ResourceLocation MODEL_TELEPORTERON = new ResourceLocation(References.ID + ":block/teleporteron");
    public static final ResourceLocation MODEL_TELEPORTER = new ResourceLocation(References.ID + ":block/teleporter");
    public static final ResourceLocation MODEL_CONTROLRODASSEMBLYSTRUCTURE = new ResourceLocation(
	    References.ID + ":block/controlrodassemblystructure");
    public static final ResourceLocation MODEL_CONTROLRODASSEMBLYSROD = new ResourceLocation(References.ID + ":block/controlrodassemblyrod");
    public static final ResourceLocation MODEL_FUELREPROCESSOR = new ResourceLocation(References.ID + ":block/fuelreprocessor");
    public static final ResourceLocation MODEL_FUELREPROCESSOR_ON = new ResourceLocation(References.ID + ":block/fuelreprocessoron");
    public static final ResourceLocation MODEL_RADIOACTIVEPROCESSOR = new ResourceLocation(References.ID + ":block/radioactiveprocessor");
    public static final ResourceLocation MODEL_RADIOACTIVEPROCESSOR_ON = new ResourceLocation(References.ID + ":block/radioactiveprocessoron");
    public static final ResourceLocation MODEL_MOLTENSALTSUPPLIER = new ResourceLocation(References.ID + ":block/moltensaltsupplier");
    public static final ResourceLocation MODEL_MOLTENSALTSUPPLIER_ON = new ResourceLocation(References.ID + ":block/moltensaltsupplieron");

    public static void setup() {
	MenuScreens.register(DeferredRegisters.CONTAINER_GASCENTRIFUGE.get(), ScreenGasCentrifuge::new);
	MenuScreens.register(DeferredRegisters.CONTAINER_NUCLEARBOILER.get(), ScreenNuclearBoiler::new);
	MenuScreens.register(DeferredRegisters.CONTAINER_CHEMICALEXTRACTOR.get(), ScreenChemicalExtractor::new);
	MenuScreens.register(DeferredRegisters.CONTAINER_RADIOISOTOPEGENERATOR.get(), ScreenRadioisotopeGenerator::new);
	MenuScreens.register(DeferredRegisters.CONTAINER_FREEZEPLUG.get(), ScreenFreezePlug::new);
	MenuScreens.register(DeferredRegisters.CONTAINER_REACTORCORE.get(), ScreenReactorCore::new);
	MenuScreens.register(DeferredRegisters.CONTAINER_PARTICLEINJECTOR.get(), ScreenParticleInjector::new);
	MenuScreens.register(DeferredRegisters.CONTAINER_QUANTUMCAPACITOR.get(), ScreenQuantumCapacitor::new);
	MenuScreens.register(DeferredRegisters.CONTAINER_MSRFUELPREPROCESSOR.get(), ScreenMSRFuelPreProcessor::new);
	MenuScreens.register(DeferredRegisters.CONTAINER_RADIOACTIVEPROCESSOR.get(), ScreenRadioactiveProcessor::new);
	MenuScreens.register(DeferredRegisters.CONTAINER_MSRREACTORCORE.get(), ScreenMSRReactorCore::new);
	MenuScreens.register(DeferredRegisters.CONTAINER_MOLTENSALTSUPPLIER.get(), ScreenMoltenSaltSupplier::new);

	ItemBlockRenderTypes.setRenderLayer(DeferredRegisters.blockChemicalExtractor, ClientRegister::shouldMultilayerRender);
	ItemBlockRenderTypes.setRenderLayer(DeferredRegisters.blockNuclearBoiler, ClientRegister::shouldMultilayerRender);
	ItemBlockRenderTypes.setRenderLayer(DeferredRegisters.blockReactorCore, ClientRegister::shouldMultilayerRender);
	ItemBlockRenderTypes.setRenderLayer(DeferredRegisters.blockElectromagneticGlass.getBlock(), RenderType.cutout());
	ItemBlockRenderTypes.setRenderLayer(DeferredRegisters.blockElectromagneticBooster.getBlock(), RenderType.translucent());
	ItemBlockRenderTypes.setRenderLayer(DeferredRegisters.blockPlasma, RenderType.translucent());
    }

    @SubscribeEvent
    public static void registerEntities(EntityRenderersEvent.RegisterRenderers event) {
	event.registerBlockEntityRenderer(DeferredRegisters.TILE_GASCENTRIFUGE.get(), RenderGasCentrifuge::new);
	event.registerBlockEntityRenderer(DeferredRegisters.TILE_CHEMICALEXTRACTOR.get(), RenderChemicalExtractor::new);
	event.registerBlockEntityRenderer(DeferredRegisters.TILE_CHEMICALBOILER.get(), RenderNuclearBoiler::new);
	event.registerBlockEntityRenderer(DeferredRegisters.TILE_TURBINE.get(), RenderTurbine::new);
	event.registerBlockEntityRenderer(DeferredRegisters.TILE_REACTORCORE.get(), RenderReactorCore::new);
	event.registerBlockEntityRenderer(DeferredRegisters.TILE_FUSIONREACTORCORE.get(), RenderFusionReactorCore::new);
	event.registerBlockEntityRenderer(DeferredRegisters.TILE_QUANTUMCAPACITOR.get(), RenderQuantumCapacitor::new);
	event.registerBlockEntityRenderer(DeferredRegisters.TILE_TELEPORTER.get(), RenderTeleporter::new);
	event.registerBlockEntityRenderer(DeferredRegisters.TILE_CONTROLRODASSEMBLY.get(), RenderRodAssembly::new);
	event.registerBlockEntityRenderer(DeferredRegisters.TILE_FUELREPROCESSOR.get(), RenderFuelReprocessor::new);
	event.registerBlockEntityRenderer(DeferredRegisters.TILE_RADIOACTIVEPROCESSOR.get(), RenderRadioactiveProcessor::new);
	event.registerBlockEntityRenderer(DeferredRegisters.TILE_MOLTENSALTSUPPLIER.get(), RenderMoltenSaltSupplier::new);

	event.registerEntityRenderer(DeferredRegisters.ENTITY_PARTICLE.get(), RenderParticle::new);

    }

    public static boolean shouldMultilayerRender(RenderType type) {
	return type == RenderType.translucent() || type == RenderType.solid();
    }

}
