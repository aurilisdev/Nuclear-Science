package nuclearscience.prefab.screen.component;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
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
	RenderingUtils.setShaderColor(new Color(IClientFluidTypeExtensions.of(Fluids.LAVA).getTintColor()));
    }

    @Override
    protected int getScaledLevel() {
	TileMSReactorCore core = ((GenericContainerBlockEntity<TileMSReactorCore>) ((GenericScreen<?>) gui).getMenu())
		.getSafeHost();
	if (core == null) {
	    return 0;
	}

	return (int) ((GaugeTextures.BACKGROUND_DEFAULT.textureHeight() - 2) * core.currentFuel.getValue()
		/ TileMSReactorCore.FUEL_CAPACITY);
    }

    @Override
    protected ResourceLocation getTexture() {
	return IClientFluidTypeExtensions.of(Fluids.LAVA).getStillTexture();
    }

    @Override
    protected List<? extends FormattedCharSequence> getTooltips() {
	List<FormattedCharSequence> list = new ArrayList<>();
	TileMSReactorCore core = ((GenericContainerBlockEntity<TileMSReactorCore>) ((GenericScreen<?>) gui).getMenu())
		.getSafeHost();
	if (core == null) {
	    return list;
	}
	list.add(VoltaicTextUtils
		.ratio(ChatFormatter.formatFluidMilibuckets(core.currentFuel.getValue()),
			ChatFormatter.formatFluidMilibuckets(TileMSReactorCore.FUEL_CAPACITY))
		.withStyle(ChatFormatting.GRAY).getVisualOrderText());
	return list;
    }

}
