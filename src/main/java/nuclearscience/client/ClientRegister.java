package nuclearscience.client;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import nuclearscience.DeferredRegisters;
import nuclearscience.References;
import nuclearscience.client.render.tile.GasCentrifugeRenderer;

@OnlyIn(Dist.CLIENT)
public class ClientRegister {
	public static final ResourceLocation MODEL_GASCENTRIFUGEFULL = new ResourceLocation(References.ID + ":block/gascentrifuge");
	public static final ResourceLocation MODEL_GASCENTRIFUGEOUTLINE = new ResourceLocation(References.ID + ":block/gascentrifuge1");
	public static final ResourceLocation MODEL_GASCENTRIFUGECENTER = new ResourceLocation(References.ID + ":block/gascentrifuge2");

	public static void setup() {
		ModelLoader.addSpecialModel(MODEL_GASCENTRIFUGEFULL);
		ModelLoader.addSpecialModel(MODEL_GASCENTRIFUGEOUTLINE);
		ModelLoader.addSpecialModel(MODEL_GASCENTRIFUGECENTER);
		ClientRegistry.bindTileEntityRenderer(DeferredRegisters.TILE_GASCENTRIFUGE.get(), GasCentrifugeRenderer::new);
	}
}
