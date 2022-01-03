package nuclearscience.client.screen;

import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.ScreenComponentInfo;
import electrodynamics.prefab.screen.component.ScreenComponentProgress;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import nuclearscience.common.inventory.container.ContainerQuantumAssembler;
import nuclearscience.common.settings.Constants;
import nuclearscience.common.tile.TileQuantumAssembler;

@OnlyIn(Dist.CLIENT)
public class ScreenQuantumAssembler extends GenericScreen<ContainerQuantumAssembler> {

	public ScreenQuantumAssembler(ContainerQuantumAssembler container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);
		components.add(new ScreenComponentElectricInfo(this, -ScreenComponentInfo.SIZE + 1, 2)
				.wattage(Constants.QUANTUMASSEMBLER_USAGE_PER_TICK * 20));
		imageHeight += 64;
		inventoryLabelY += 64;
		components.add(new ScreenComponentProgress(() -> {
			TileQuantumAssembler assembler = container.getHostFromIntArray();
			if (assembler != null) {
				return assembler.progress / (double) Constants.QUANTUMASSEMBLER_REQUIRED_TICKS;
			}
			return 0;
		}, this, 84, 71));
	}
}