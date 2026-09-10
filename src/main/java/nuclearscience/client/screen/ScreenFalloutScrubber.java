package nuclearscience.client.screen;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import nuclearscience.common.inventory.container.ContainerFalloutScrubber;
import voltaic.prefab.screen.GenericScreen;
import voltaic.prefab.screen.component.types.ScreenComponentProgress;
import voltaic.prefab.screen.component.types.gauges.ScreenComponentFluidGauge;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import voltaic.prefab.screen.component.utils.AbstractScreenComponentInfo;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentFluidHandlerMulti;

public class ScreenFalloutScrubber extends GenericScreen<ContainerFalloutScrubber> {
    public ScreenFalloutScrubber(ContainerFalloutScrubber container, Inventory inv, Component title) {
	super(container, inv, title);

	addComponent(new ScreenComponentFluidGauge(() -> container
		.getSafeHost().map(scrubber -> scrubber
			.<ComponentFluidHandlerMulti>requireComponent(IComponentType.FluidHandler).getInputTanks()[0])
		.orElse(null), 30, 18));
	addComponent(new ScreenComponentFluidGauge(() -> container
		.getSafeHost().map(scrubber -> scrubber
			.<ComponentFluidHandlerMulti>requireComponent(IComponentType.FluidHandler).getInputTanks()[1])
		.orElse(null), 132, 18));

	addComponent(new ScreenComponentProgress(ScreenComponentProgress.ProgressBars.FAN,
		() -> container.getSafeHost().map(scrubber -> scrubber.active.getValue() ? 1.0 : 0.0).orElse(0.0), 80,
		34));

	addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2));

    }
}
