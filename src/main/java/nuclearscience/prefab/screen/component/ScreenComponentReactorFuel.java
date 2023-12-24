package nuclearscience.prefab.screen.component;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.types.gauges.AbstractScreenComponentGauge;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.prefab.utilities.RenderingUtils;
import electrodynamics.prefab.utilities.math.Color;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import nuclearscience.common.tile.msreactor.TileMSReactorCore;

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
		TileMSReactorCore core = ((GenericContainerBlockEntity<TileMSReactorCore>) ((GenericScreen<?>) gui).getMenu()).getHostFromIntArray();
		if (core == null) {
			return 0;
		}

		return (int) ((GaugeTextures.BACKGROUND_DEFAULT.textureHeight() - 2) * (core.currentFuel.get()) / TileMSReactorCore.FUEL_CAPACITY);
	}

	@Override
	protected ResourceLocation getTexture() {
		return Fluids.LAVA.getAttributes().getStillTexture();
	}

	@Override
	protected List<? extends IReorderingProcessor> getTooltips() {
		List<IReorderingProcessor> list = new ArrayList<>();
		TileMSReactorCore core = ((GenericContainerBlockEntity<TileMSReactorCore>) ((GenericScreen<?>) gui).getMenu()).getHostFromIntArray();
		if (core == null) {
			return list;
		}
		list.add(ElectroTextUtils.ratio(ChatFormatter.formatFluidMilibuckets(core.currentFuel.get()), ChatFormatter.formatFluidMilibuckets(TileMSReactorCore.FUEL_CAPACITY)).withStyle(TextFormatting.GRAY).getVisualOrderText());
		return list;
	}

}