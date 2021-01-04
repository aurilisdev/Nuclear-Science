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
import nuclearscience.client.render.tile.GasCentrifugeRenderer;
import nuclearscience.client.render.tile.TurbineRenderer;
import nuclearscience.client.screen.ScreenChemicalBoiler;
import nuclearscience.client.screen.ScreenChemicalExtractor;
import nuclearscience.client.screen.ScreenGasCentrifuge;
import nuclearscience.client.screen.ScreenRadioisotopeGenerator;

@OnlyIn(Dist.CLIENT)
public class ClientRegister {
	public static final ResourceLocation MODEL_GASCENTRIFUGEFULL = new ResourceLocation(References.ID + ":block/gascentrifuge");
	public static final ResourceLocation MODEL_GASCENTRIFUGEOUTLINE = new ResourceLocation(References.ID + ":block/gascentrifugeoutline");
	public static final ResourceLocation MODEL_GASCENTRIFUGECENTER = new ResourceLocation(References.ID + ":block/gascentrifugecenter");
	public static final ResourceLocation MODEL_TURBINEFULL = new ResourceLocation(References.ID + ":block/turbine");
	public static final ResourceLocation MODEL_TURBINECASING = new ResourceLocation(References.ID + ":block/turbinecasing");
	public static final ResourceLocation MODEL_TURBINEROTORLAYER = new ResourceLocation(References.ID + ":block/turbinerotorlayer");

	public static void setup() {
		ModelLoader.addSpecialModel(MODEL_GASCENTRIFUGEFULL);
		ModelLoader.addSpecialModel(MODEL_GASCENTRIFUGEOUTLINE);
		ModelLoader.addSpecialModel(MODEL_GASCENTRIFUGECENTER);
		ModelLoader.addSpecialModel(MODEL_TURBINEFULL);
		ModelLoader.addSpecialModel(MODEL_TURBINECASING);
		ModelLoader.addSpecialModel(MODEL_TURBINEROTORLAYER);

		ClientRegistry.bindTileEntityRenderer(DeferredRegisters.TILE_GASCENTRIFUGE.get(), GasCentrifugeRenderer::new);
		ClientRegistry.bindTileEntityRenderer(DeferredRegisters.TILE_TURBINE.get(), TurbineRenderer::new);

		ScreenManager.registerFactory(DeferredRegisters.CONTAINER_GASCENTRIFUGE.get(), ScreenGasCentrifuge::new);
		ScreenManager.registerFactory(DeferredRegisters.CONTAINER_CHEMICALBOILER.get(), ScreenChemicalBoiler::new);
		ScreenManager.registerFactory(DeferredRegisters.CONTAINER_CHEMICALEXTRACTOR.get(), ScreenChemicalExtractor::new);
		ScreenManager.registerFactory(DeferredRegisters.CONTAINER_RADIOISOTOPEGENERATOR.get(), ScreenRadioisotopeGenerator::new);

		RenderTypeLookup.setRenderLayer(DeferredRegisters.blockChemicalExtractor, ClientRegister::shouldExtractorRenderInLayer);
	}

	public static boolean shouldExtractorRenderInLayer(RenderType type) {
		return type == RenderType.getTranslucent() || type == RenderType.getSolid();
	}
}
