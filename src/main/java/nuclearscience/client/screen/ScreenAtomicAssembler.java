package nuclearscience.client.screen;

import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.gui.ScreenComponentInfo;
import electrodynamics.prefab.screen.component.gui.type.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.gui.type.ScreenComponentProgress;
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
		components
				.add(new ScreenComponentElectricInfo(this, -ScreenComponentInfo.SIZE + 1, 2).wattage(Constants.ATOMICASSEMBLER_USAGE_PER_TICK * 20));
		imageHeight += 64;
		inventoryLabelY += 64;
		components.add(new ScreenComponentProgress(() -> {
			TileAtomicAssembler assembler = container.getHostFromIntArray();
			if (assembler != null) {
				return assembler.progress / (double) Constants.ATOMICASSEMBLER_REQUIRED_TICKS;
			}
			return 0;
		}, this, 84, 71));
	}
}