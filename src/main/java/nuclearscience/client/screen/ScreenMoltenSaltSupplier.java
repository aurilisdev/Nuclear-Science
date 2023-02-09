package nuclearscience.client.screen;

import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import nuclearscience.common.inventory.container.ContainerMoltenSaltSupplier;
import nuclearscience.common.settings.Constants;

@OnlyIn(Dist.CLIENT)
public class ScreenMoltenSaltSupplier extends GenericScreen<ContainerMoltenSaltSupplier> {

	public ScreenMoltenSaltSupplier(ContainerMoltenSaltSupplier container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);
		components.add(new ScreenComponentElectricInfo(this, -AbstractScreenComponentInfo.SIZE + 1, 2).wattage(Constants.MOLTENSALTSUPPLIER_USAGE_PER_TICK * 20));
	}
}