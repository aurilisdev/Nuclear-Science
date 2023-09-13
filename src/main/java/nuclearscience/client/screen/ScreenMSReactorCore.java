package nuclearscience.client.screen;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.types.ScreenComponentMultiLabel;
import electrodynamics.prefab.screen.component.types.guitab.ScreenComponentTemperature;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import nuclearscience.common.inventory.container.ContainerMSRReactorCore;
import nuclearscience.common.tile.TileFreezePlug;
import nuclearscience.common.tile.TileMSReactorCore;
import nuclearscience.prefab.screen.component.ScreenComponentReactorFuel;
import nuclearscience.prefab.utils.NuclearTextUtils;

@OnlyIn(Dist.CLIENT)
public class ScreenMSReactorCore extends GenericScreen<ContainerMSRReactorCore> {

	public ScreenMSReactorCore(ContainerMSRReactorCore container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);
		addComponent(new ScreenComponentTemperature(() -> {

			List<FormattedCharSequence> list = new ArrayList<>();

			TileMSReactorCore core = menu.getHostFromIntArray();
			if (core == null) {
				return list;
			}

			MutableComponent text = ChatFormatter.getChatDisplayShort(core.temperature.get(), DisplayUnit.TEMPERATURE_CELCIUS);

			if (core.temperature.get() > TileMSReactorCore.MELTDOWN_TEMPERATURE) {
				text = text.withStyle(ChatFormatting.RED);
			} else if (core.temperature.get() > TileMSReactorCore.MELTDOWN_TEMPERATURE - 100) {
				text = text.withStyle(ChatFormatting.YELLOW);
			} else {
				text = text.withStyle(ChatFormatting.GREEN);
			}

			list.add(text.getVisualOrderText());

			return list;
		}, -AbstractScreenComponentInfo.SIZE + 1, 2));
		addComponent(new ScreenComponentMultiLabel(0, 0, graphics -> {
			TileMSReactorCore core = menu.getHostFromIntArray();
			if (core == null) {
				return;
			}

			graphics.drawString(font, NuclearTextUtils.gui("msreactor.status"), titleLabelX, titleLabelY + 14, 0, false);

			if (!(core.plugCache.getSafe() instanceof TileFreezePlug)) {
				graphics.drawString(font, NuclearTextUtils.gui("msreactor.status.nofreezeplug"), titleLabelX + 5, titleLabelY + 24, 4210752, false);
			} else if (core.wasteIsFull.get()) {
				graphics.drawString(font, NuclearTextUtils.gui("msreactor.status.wastefull"), titleLabelX + 5, titleLabelY + 24, 4210752, false);
			} else {
				graphics.drawString(font, NuclearTextUtils.gui("msreactor.status.good").withStyle(ChatFormatting.GREEN), titleLabelX + 5, titleLabelY + 24, -1, false);

			}
			graphics.drawString(font, NuclearTextUtils.gui("msreactor.warning"), titleLabelX, titleLabelY + 38, 0, false);

			if (core.temperature.get() > TileMSReactorCore.MELTDOWN_TEMPERATURE) {

				if (System.currentTimeMillis() % 1000 < 500) {
					graphics.drawString(font, NuclearTextUtils.gui("msreactor.warning.overheat"), titleLabelX + 5, titleLabelY + 48, 16711680, false);
				} else {
					graphics.drawString(font, NuclearTextUtils.gui("msreactor.warning.overheat"), titleLabelX + 5, titleLabelY + 48, 4210752, false);
				}

			} else if (core.plugCache.getSafe() instanceof TileFreezePlug plug && !plug.isFrozen()) {
				graphics.drawString(font, NuclearTextUtils.gui("msreactor.warning.freezeoff").withStyle(ChatFormatting.YELLOW), titleLabelX + 5, titleLabelY + 48, -1, false);
			} else {
				graphics.drawString(font, NuclearTextUtils.gui("msreactor.warning.none").withStyle(ChatFormatting.GREEN), titleLabelX + 5, titleLabelY + 48, -1, false);
			}

		}));
		addComponent(new ScreenComponentReactorFuel(140, 20));
	}

}