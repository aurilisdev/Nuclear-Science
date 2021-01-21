package nuclearscience.client;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import nuclearscience.DeferredRegisters;
import nuclearscience.References;
import nuclearscience.client.render.tile.RenderFusionReactorCore;
import nuclearscience.client.render.tile.RenderGasCentrifuge;
import nuclearscience.client.render.tile.RenderReactorCore;
import nuclearscience.client.render.tile.RenderTurbine;
import nuclearscience.client.screen.ScreenChemicalBoiler;
import nuclearscience.client.screen.ScreenChemicalExtractor;
import nuclearscience.client.screen.ScreenGasCentrifuge;
import nuclearscience.client.screen.ScreenRadioisotopeGenerator;
import nuclearscience.client.screen.ScreenReactorCore;

@OnlyIn(Dist.CLIENT)
public class ClientRegister {
	public static final ResourceLocation MODEL_GASCENTRIFUGEFULL = new ResourceLocation(References.ID + ":block/gascentrifuge");
	public static final ResourceLocation MODEL_GASCENTRIFUGEOUTLINE = new ResourceLocation(References.ID + ":block/gascentrifugeoutline");
	public static final ResourceLocation MODEL_GASCENTRIFUGECENTER = new ResourceLocation(References.ID + ":block/gascentrifugecenter");
	public static final ResourceLocation MODEL_TURBINEFULL = new ResourceLocation(References.ID + ":block/turbine");
	public static final ResourceLocation MODEL_TURBINECASING = new ResourceLocation(References.ID + ":block/turbinecasing");
	public static final ResourceLocation MODEL_TURBINEROTORLAYER = new ResourceLocation(References.ID + ":block/turbinerotorlayer");
	public static final ResourceLocation MODEL_REACTORCORE = new ResourceLocation(References.ID + ":block/reactorcore");
	public static final ResourceLocation MODEL_REACTORFUELROD = new ResourceLocation(References.ID + ":block/reactorfuelrod");
	public static final ResourceLocation MODEL_REACTORDEUTERIUM = new ResourceLocation(References.ID + ":block/reactordeuterium");
	public static final ResourceLocation TEXTURE_REACTORCOREEMPTY = new ResourceLocation(References.ID + ":textures/model/reactorcore.png");

	public static void setup() {
		ModelLoader.addSpecialModel(MODEL_GASCENTRIFUGEFULL);
		ModelLoader.addSpecialModel(MODEL_GASCENTRIFUGEOUTLINE);
		ModelLoader.addSpecialModel(MODEL_GASCENTRIFUGECENTER);
		ModelLoader.addSpecialModel(MODEL_TURBINEFULL);
		ModelLoader.addSpecialModel(MODEL_TURBINECASING);
		ModelLoader.addSpecialModel(MODEL_TURBINEROTORLAYER);
		ModelLoader.addSpecialModel(MODEL_REACTORCORE);
		ModelLoader.addSpecialModel(MODEL_REACTORFUELROD);
		ModelLoader.addSpecialModel(MODEL_REACTORDEUTERIUM);

		ClientRegistry.bindTileEntityRenderer(DeferredRegisters.TILE_GASCENTRIFUGE.get(), RenderGasCentrifuge::new);
		ClientRegistry.bindTileEntityRenderer(DeferredRegisters.TILE_TURBINE.get(), RenderTurbine::new);
		ClientRegistry.bindTileEntityRenderer(DeferredRegisters.TILE_REACTORCORE.get(), RenderReactorCore::new);
		ClientRegistry.bindTileEntityRenderer(DeferredRegisters.TILE_FUSIONREACTORCORE.get(), RenderFusionReactorCore::new);

		ScreenManager.registerFactory(DeferredRegisters.CONTAINER_GASCENTRIFUGE.get(), ScreenGasCentrifuge::new);
		ScreenManager.registerFactory(DeferredRegisters.CONTAINER_CHEMICALBOILER.get(), ScreenChemicalBoiler::new);
		ScreenManager.registerFactory(DeferredRegisters.CONTAINER_CHEMICALEXTRACTOR.get(), ScreenChemicalExtractor::new);
		ScreenManager.registerFactory(DeferredRegisters.CONTAINER_RADIOISOTOPEGENERATOR.get(), ScreenRadioisotopeGenerator::new);
		ScreenManager.registerFactory(DeferredRegisters.CONTAINER_REACTORCORE.get(), ScreenReactorCore::new);

		RenderTypeLookup.setRenderLayer(DeferredRegisters.blockChemicalExtractor, ClientRegister::shouldMultilayerRender);
		RenderTypeLookup.setRenderLayer(DeferredRegisters.blockReactorCore, ClientRegister::shouldMultilayerRender);
		RenderTypeLookup.setRenderLayer(DeferredRegisters.blockElectromagneticGlass.getBlock(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(DeferredRegisters.blockElectromagneticBooster.getBlock(), RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(DeferredRegisters.blockPlasma, RenderType.getTranslucent());
	}

	public static boolean shouldMultilayerRender(RenderType type) {
		return type == RenderType.getTranslucent() || type == RenderType.getSolid();
	}
}
