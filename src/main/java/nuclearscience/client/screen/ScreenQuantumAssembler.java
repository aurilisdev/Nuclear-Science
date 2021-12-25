package nuclearscience.client.screen;

import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.ScreenComponentInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import nuclearscience.common.inventory.container.ContainerQuantumAssembler;
import nuclearscience.common.settings.Constants;

@OnlyIn(Dist.CLIENT)
public class ScreenQuantumAssembler extends GenericScreen<ContainerQuantumAssembler> {

	public ScreenQuantumAssembler(ContainerQuantumAssembler container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);
		components.add(new ScreenComponentElectricInfo(this, -ScreenComponentInfo.SIZE + 1, 2).tag("quantumassembler")
				.wattage(Constants.QUANTUMASSEMBLER_USAGE_PER_TICK * 20));
		imageHeight += 64;
		inventoryLabelY += 64;
	}
}