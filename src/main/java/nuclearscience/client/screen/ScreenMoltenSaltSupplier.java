package nuclearscience.client.screen;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import nuclearscience.common.inventory.container.ContainerMoltenSaltSupplier;
import nuclearscience.common.settings.NuclearConstants;
import nuclearscience.common.tile.reactor.moltensalt.TileMoltenSaltSupplier;
import nuclearscience.prefab.utils.NuclearTextUtils;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.prefab.screen.GenericScreen;
import voltaic.prefab.screen.component.types.ScreenComponentProgress;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import voltaic.prefab.screen.component.types.wrapper.WrapperInventoryIO;
import voltaic.prefab.screen.component.utils.AbstractScreenComponentInfo;
import voltaic.prefab.utilities.VoltaicTextUtils;

public class ScreenMoltenSaltSupplier extends GenericScreen<ContainerMoltenSaltSupplier> {

	public ScreenMoltenSaltSupplier(ContainerMoltenSaltSupplier container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);
		addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2).wattage(NuclearConstants.MOLTENSALTSUPPLIER_USAGE_PER_TICK * 20));
		addComponent(new ScreenComponentProgress(ScreenComponentProgress.ProgressBars.PROGRESS_ARROW_RIGHT, () -> {
			TileMoltenSaltSupplier supplier = menu.getSafeHost();
			if (supplier == null) {
				return 0;
			}
			return supplier.reactorWaste.getValue() / TileMoltenSaltSupplier.AMT_PER_WASTE;
		}, 77, 35) {
			@Override
			public void renderForeground(GuiGraphics graphics, int xAxis, int yAxis, int guiWidth, int guiHeight) {
				if (!isHoveredOrFocused()) {
					return;
				}
				TileMoltenSaltSupplier supplier = menu.getSafeHost();
				if (supplier == null) {
					return;
				}
				graphics.renderTooltip(font, NuclearTextUtils.gui("saltsupplier.wastecont", VoltaicTextUtils.ratio(ChatFormatter.formatFluidMilibuckets(supplier.reactorWaste.getValue()), ChatFormatter.formatFluidMilibuckets(TileMoltenSaltSupplier.AMT_PER_WASTE)).withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.GRAY), xAxis, yAxis);

			}
		});
		new WrapperInventoryIO(this, -AbstractScreenComponentInfo.SIZE + 1, AbstractScreenComponentInfo.SIZE + 2, 75, 82, 8, 72);
	}
}