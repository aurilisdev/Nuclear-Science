package nuclearscience.prefab.screen.component;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.types.gauges.AbstractScreenComponentGauge;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import nuclearscience.common.tile.TileMSReactorCore;

public class ScreenComponentReactorFuel extends AbstractScreenComponentGauge {

	public ScreenComponentReactorFuel(int x, int y) {
		super(x, y);
	}

	@Override
	protected void applyColor() {
		RenderingUtils.color(IClientFluidTypeExtensions.of(Fluids.LAVA).getTintColor());
	}

	@Override
	protected int getScaledLevel() {
		TileMSReactorCore core = ((GenericContainerBlockEntity<TileMSReactorCore>)((GenericScreen<?>) gui).getMenu()).getHostFromIntArray();
		if(core == null) {
			return 0;
		}
		
		return (int) ((GaugeTextures.BACKGROUND_DEFAULT.textureHeight() - 2) * (core.currentFuel.get()) / TileMSReactorCore.FUEL_CAPACITY);
	}

	@Override
	protected ResourceLocation getTexture() {
		return IClientFluidTypeExtensions.of(Fluids.LAVA).getStillTexture();
	}

	@Override
	protected List<? extends FormattedCharSequence> getTooltips() {
		List<FormattedCharSequence> list = new ArrayList<>();
		TileMSReactorCore core = ((GenericContainerBlockEntity<TileMSReactorCore>)((GenericScreen<?>) gui).getMenu()).getHostFromIntArray();
		if(core == null) {
			return list;
		}
		list.add(ElectroTextUtils.ratio(ChatFormatter.formatFluidMilibuckets(core.currentFuel.get()), ChatFormatter.formatFluidMilibuckets(TileMSReactorCore.FUEL_CAPACITY)).withStyle(ChatFormatting.GRAY).getVisualOrderText());
		return list;
	}

}
