package nuclearscience.client.screen;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import nuclearscience.common.inventory.container.ContainerNuclearBoiler;
import voltaic.prefab.screen.component.types.ScreenComponentProgress;
import voltaic.prefab.screen.component.types.gauges.ScreenComponentFluidGauge;
import voltaic.prefab.screen.component.types.gauges.ScreenComponentGasGauge;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentGasPressure;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentGasTemperature;
import voltaic.prefab.screen.component.types.wrapper.WrapperInventoryIO;
import voltaic.prefab.screen.component.utils.AbstractScreenComponentInfo;
import voltaic.prefab.screen.types.GenericMaterialScreen;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentFluidHandlerMulti;
import voltaic.prefab.tile.components.type.ComponentGasHandlerMulti;
import voltaic.prefab.tile.components.type.ComponentProcessor;

public class ScreenNuclearBoiler extends GenericMaterialScreen<ContainerNuclearBoiler> {
    public ScreenNuclearBoiler(ContainerNuclearBoiler container, Inventory playerInventory, Component title) {
	super(container, playerInventory, title);
	addComponent(new ScreenComponentProgress(ScreenComponentProgress.ProgressBars.PROGRESS_ARROW_RIGHT,
		() -> container.getSafeHost().map(furnace -> {
		    ComponentProcessor processor = furnace.requireComponent(IComponentType.Processor);
		    if (processor.operatingTicks.getValue()[0] > 0)
			return Math.min(1.0,
				processor.operatingTicks.getValue()[0] / (processor.requiredTicks.getValue()[0] / 2.0));
		    return 0.0;
		}).orElse(0.0), 42, 30));
	addComponent(new ScreenComponentProgress(ScreenComponentProgress.ProgressBars.PROGRESS_ARROW_RIGHT,
		() -> container.getSafeHost().map(furnace -> {
		    ComponentProcessor processor = furnace.requireComponent(IComponentType.Processor);
		    if (processor.operatingTicks.getValue()[0] > processor.requiredTicks.getValue()[0] / 2.0)
			return Math.min(1.0,
				(processor.operatingTicks.getValue()[0] - processor.requiredTicks.getValue()[0] / 2.0)
					/ (processor.requiredTicks.getValue()[0] / 2.0));
		    return 0.0;
		}).orElse(0.0), 98, 30));
	addComponent(new ScreenComponentFluidGauge(() -> container
		.getSafeHost().map(boiler -> boiler
			.<ComponentFluidHandlerMulti>requireComponent(IComponentType.FluidHandler).getInputTanks()[0])
		.orElse(null), 21, 18));
	addComponent(new ScreenComponentGasGauge(() -> container
		.getSafeHost().map(boiler -> boiler
			.<ComponentGasHandlerMulti>requireComponent(IComponentType.GasHandler).getOutputTanks()[0])
		.orElse(null), 127, 18));
	addComponent(new ScreenComponentGasPressure(-AbstractScreenComponentInfo.SIZE + 1,
		2 + 3 * AbstractScreenComponentInfo.SIZE));
	addComponent(new ScreenComponentGasTemperature(-AbstractScreenComponentInfo.SIZE + 1,
		2 + 2 * AbstractScreenComponentInfo.SIZE));
	new WrapperInventoryIO(this, -AbstractScreenComponentInfo.SIZE + 1, AbstractScreenComponentInfo.SIZE + 2, 75,
		82, 8, 72);
	addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2));
    }

}