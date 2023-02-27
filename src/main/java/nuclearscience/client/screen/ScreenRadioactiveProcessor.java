package nuclearscience.client.screen;

import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.ScreenComponentFluidInput;
import electrodynamics.prefab.screen.component.ScreenComponentGeneric;
import electrodynamics.prefab.screen.component.ScreenComponentProgress;
import electrodynamics.prefab.screen.component.ScreenComponentProgress.ProgressBars;
import electrodynamics.prefab.screen.component.ScreenComponentProgress.ProgressTextures;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentFluidHandlerMulti;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import nuclearscience.common.inventory.container.ContainerRadioactiveProcessor;
import nuclearscience.common.tile.TileRadioactiveProcessor;

public class ScreenRadioactiveProcessor extends GenericScreen<ContainerRadioactiveProcessor> {

	public ScreenRadioactiveProcessor(ContainerRadioactiveProcessor container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);
		components.add(new ScreenComponentProgress(ProgressBars.PROGRESS_ARROW_RIGHT, () -> {
			GenericTile furnace = container.getHostFromIntArray();
			if (furnace != null) {
				ComponentProcessor processor = furnace.getComponent(ComponentType.Processor);
				if (processor.operatingTicks.get() > 0) {
					return Math.min(1.0, processor.operatingTicks.get() / (processor.requiredTicks.get() / 2.0));
				}
			}
			return 0;
		}, this, 42, 30));
		components.add(new ScreenComponentProgress(ProgressBars.PROGRESS_ARROW_RIGHT, () -> {
			GenericTile furnace = container.getHostFromIntArray();
			if (furnace != null) {
				ComponentProcessor processor = furnace.getComponent(ComponentType.Processor);
				if (processor.operatingTicks.get() > processor.requiredTicks.get() / 2.0) {
					return Math.min(1.0, (processor.operatingTicks.get() - processor.requiredTicks.get() / 2.0) / (processor.requiredTicks.get() / 2.0));
				}
			}
			return 0;
		}, this, 98, 30));
		components.add(new ScreenComponentGeneric(ProgressTextures.ARROW_LEFT_OFF, this, 46, 50));
		components.add(new ScreenComponentFluidInput(() -> {
			TileRadioactiveProcessor boiler = container.getHostFromIntArray();
			if (boiler != null) {
				return boiler.<ComponentFluidHandlerMulti>getComponent(ComponentType.FluidHandler).getInputTanks()[0];
			}
			return null;
		}, this, 21, 18));
		components.add(new ScreenComponentElectricInfo(this, -AbstractScreenComponentInfo.SIZE + 1, 2));
	}
}
