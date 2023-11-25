package nuclearscience.client.screen;

import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.types.ScreenComponentProgress;
import electrodynamics.prefab.screen.component.types.ScreenComponentProgress.ProgressBars;
import electrodynamics.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.types.wrapper.InventoryIOWrapper;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import nuclearscience.common.inventory.container.ContainerAtomicAssembler;
import nuclearscience.common.settings.Constants;
import nuclearscience.common.tile.TileAtomicAssembler;

@OnlyIn(Dist.CLIENT)
public class ScreenAtomicAssembler extends GenericScreen<ContainerAtomicAssembler> {

	public ScreenAtomicAssembler(ContainerAtomicAssembler container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);
		addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2).wattage(Constants.ATOMICASSEMBLER_USAGE_PER_TICK * 20));
		imageHeight += 64;
		inventoryLabelY += 64;
		addComponent(new ScreenComponentProgress(ProgressBars.PROGRESS_ARROW_RIGHT, () -> {
			TileAtomicAssembler assembler = container.getHostFromIntArray();
			if (assembler != null) {
				return assembler.progress.get() / (double) Constants.ATOMICASSEMBLER_REQUIRED_TICKS;
			}
			return 0;
		}, 84, 71));

		new InventoryIOWrapper(this, -AbstractScreenComponentInfo.SIZE + 1, AbstractScreenComponentInfo.SIZE + 2, 75, 82 + 64, 8, 72 + 64);
	}
}