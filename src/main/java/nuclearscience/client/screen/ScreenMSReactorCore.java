package nuclearscience.client.screen;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import nuclearscience.common.inventory.container.ContainerMSReactorCore;
import nuclearscience.common.tile.reactor.moltensalt.TileFreezePlug;
import nuclearscience.common.tile.reactor.moltensalt.TileMSReactorCore;
import nuclearscience.prefab.screen.component.ScreenComponentReactorFuel;
import nuclearscience.prefab.utils.NuclearTextUtils;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.prefab.screen.GenericScreen;
import voltaic.prefab.screen.component.types.ScreenComponentMultiLabel;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentTemperature;
import voltaic.prefab.screen.component.utils.AbstractScreenComponentInfo;
import voltaic.prefab.utilities.math.Color;

public class ScreenMSReactorCore extends GenericScreen<ContainerMSReactorCore> {

	public static final Color WARNING_COLOR = new Color(255, 0, 0, 255);

	public ScreenMSReactorCore(ContainerMSReactorCore container, PlayerInventory playerInventory, ITextComponent title) {
		super(container, playerInventory, title);
		addComponent(new ScreenComponentTemperature(() -> {

			List<IReorderingProcessor> list = new ArrayList<>();

			TileMSReactorCore core = menu.getSafeHost();
			if (core == null) {
				return list;
			}

			IFormattableTextComponent text = ChatFormatter.getChatDisplayShort(core.temperature.getValue(), DisplayUnits.TEMPERATURE_CELCIUS);

			if (core.temperature.getValue() > TileMSReactorCore.MELTDOWN_TEMPERATURE) {
				text = text.withStyle(TextFormatting.RED);
			} else if (core.temperature.getValue() > TileMSReactorCore.MELTDOWN_TEMPERATURE - 100) {
				text = text.withStyle(TextFormatting.YELLOW);
			} else {
				text = text.withStyle(TextFormatting.GREEN);
			}

			list.add(text.getVisualOrderText());

			return list;
		}, -AbstractScreenComponentInfo.SIZE + 1, 2));
		addComponent(new ScreenComponentMultiLabel(0, 0, poseStack -> {
			TileMSReactorCore core = menu.getSafeHost();
			if (core == null) {
				return;
			}

			font.draw(poseStack, NuclearTextUtils.gui("msreactor.status"), titleLabelX, titleLabelY + 14, Color.BLACK.color());

			if (!(core.clientPlugCache.getSafe() instanceof TileFreezePlug)) {
				font.draw(poseStack, NuclearTextUtils.gui("msreactor.status.nofreezeplug"), titleLabelX + 5, titleLabelY + 24, Color.TEXT_GRAY.color());
			} else if (core.wasteIsFull.getValue()) {
				font.draw(poseStack, NuclearTextUtils.gui("msreactor.status.wastefull"), titleLabelX + 5, titleLabelY + 24, Color.TEXT_GRAY.color());
			} else {
				font.draw(poseStack, NuclearTextUtils.gui("msreactor.status.good").withStyle(TextFormatting.GREEN), titleLabelX + 5, titleLabelY + 24, Color.WHITE.color());

			}
			font.draw(poseStack, NuclearTextUtils.gui("msreactor.warning"), titleLabelX, titleLabelY + 38, 0);

			if (core.temperature.getValue() > TileMSReactorCore.MELTDOWN_TEMPERATURE) {

				if (System.currentTimeMillis() % 1000 < 500) {
					font.draw(poseStack, NuclearTextUtils.gui("msreactor.warning.overheat"), titleLabelX + 5, titleLabelY + 48,WARNING_COLOR.color());
				} else {
					font.draw(poseStack, NuclearTextUtils.gui("msreactor.warning.overheat"), titleLabelX + 5, titleLabelY + 48, Color.TEXT_GRAY.color());
				}

			} else if (core.clientPlugCache.getSafe() instanceof TileFreezePlug && !((TileFreezePlug) core.clientPlugCache.getSafe()).isFrozen()) {
				font.draw(poseStack, NuclearTextUtils.gui("msreactor.warning.freezeoff").withStyle(TextFormatting.YELLOW), titleLabelX + 5, titleLabelY + 48, Color.WHITE.color());
			} else {
				font.draw(poseStack, NuclearTextUtils.gui("msreactor.warning.none").withStyle(TextFormatting.GREEN), titleLabelX + 5, titleLabelY + 48, Color.WHITE.color());
			}

		}));
		addComponent(new ScreenComponentReactorFuel(140, 20));
	}

}