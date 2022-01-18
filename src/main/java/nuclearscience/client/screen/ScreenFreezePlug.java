package nuclearscience.client.screen;

import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.gui.ScreenComponentInfo;
import electrodynamics.prefab.screen.component.gui.type.ScreenComponentElectricInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import nuclearscience.common.inventory.container.ContainerFreezePlug;
import nuclearscience.common.settings.Constants;

@OnlyIn(Dist.CLIENT)
public class ScreenFreezePlug extends GenericScreen<ContainerFreezePlug> {

	public ScreenFreezePlug(ContainerFreezePlug container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);
		components.add(new ScreenComponentElectricInfo(this, -ScreenComponentInfo.SIZE + 1, 2).wattage(Constants.FREEZEPLUG_USAGE_PER_TICK * 20));
	}
}