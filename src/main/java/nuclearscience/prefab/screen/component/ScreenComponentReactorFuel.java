package nuclearscience.prefab.screen.component;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.fluid.Fluids;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import nuclearscience.common.tile.reactor.moltensalt.TileMSReactorCore;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.prefab.inventory.container.types.GenericContainerBlockEntity;
import voltaic.prefab.screen.GenericScreen;
import voltaic.prefab.screen.component.types.gauges.AbstractScreenComponentGauge;
import voltaic.prefab.utilities.RenderingUtils;
import voltaic.prefab.utilities.VoltaicTextUtils;
import voltaic.prefab.utilities.math.Color;

public class ScreenComponentReactorFuel extends AbstractScreenComponentGauge {

	public ScreenComponentReactorFuel(int x, int y) {
		super(x, y);
	}

	@Override
	protected void applyColor() {
		RenderingUtils.setShaderColor(new Color(Fluids.LAVA.getAttributes().getColor()));
	}

	@Override
	protected int getScaledLevel() {
		TileMSReactorCore core = ((GenericContainerBlockEntity<TileMSReactorCore>) ((GenericScreen<?>) gui).getMenu()).getSafeHost();
		if (core == null) {
			return 0;
		}

		return (int) ((GaugeTextures.BACKGROUND_DEFAULT.textureHeight() - 2) * (core.currentFuel.getValue()) / TileMSReactorCore.FUEL_CAPACITY);
	}

	@Override
	protected ResourceLocation getTexture() {
		return Fluids.LAVA.getAttributes().getStillTexture();
	}

	@Override
	protected List<? extends IReorderingProcessor> getTooltips() {
		List<IReorderingProcessor> list = new ArrayList<>();
		TileMSReactorCore core = ((GenericContainerBlockEntity<TileMSReactorCore>) ((GenericScreen<?>) gui).getMenu()).getSafeHost();
		if (core == null) {
			return list;
		}
		list.add(VoltaicTextUtils.ratio(ChatFormatter.formatFluidMilibuckets(core.currentFuel.getValue()), ChatFormatter.formatFluidMilibuckets(TileMSReactorCore.FUEL_CAPACITY)).withStyle(TextFormatting.GRAY).getVisualOrderText());
		return list;
	}

}
