package nuclearscience.client.screen;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import nuclearscience.common.inventory.container.ContainerAtomicAssembler;
import nuclearscience.common.settings.NuclearConfig;
import nuclearscience.common.tile.TileAtomicAssembler;
import voltaic.prefab.screen.GenericScreen;
import voltaic.prefab.screen.component.types.ScreenComponentProgress;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import voltaic.prefab.screen.component.types.wrapper.WrapperInventoryIO;
import voltaic.prefab.screen.component.utils.AbstractScreenComponentInfo;

public class ScreenAtomicAssembler extends GenericScreen<ContainerAtomicAssembler> {

	public ScreenAtomicAssembler(ContainerAtomicAssembler container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);
		addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2).wattage(NuclearConfig.INSTANCE.ATOMICASSEMBLER_USAGE_PER_TICK.get() * 20));
		imageHeight += 64;
		inventoryLabelY += 64;
		addComponent(new ScreenComponentProgress(ScreenComponentProgress.ProgressBars.PROGRESS_ARROW_RIGHT, () -> {
			TileAtomicAssembler assembler = container.getSafeHost();
			if (assembler != null) {
				return assembler.progress.getValue() / (double) NuclearConfig.INSTANCE.ATOMICASSEMBLER_REQUIRED_TICKS.get();
			}
			return 0;
		}, 84, 71));

		new WrapperInventoryIO(this, -AbstractScreenComponentInfo.SIZE + 1, AbstractScreenComponentInfo.SIZE + 2, 75, 82 + 64, 8, 72 + 64);
	}
}