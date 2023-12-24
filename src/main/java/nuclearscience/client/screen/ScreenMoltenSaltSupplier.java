package nuclearscience.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.types.ScreenComponentProgress;
import electrodynamics.prefab.screen.component.types.ScreenComponentProgress.ProgressBars;
import electrodynamics.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.types.wrapper.InventoryIOWrapper;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import nuclearscience.common.inventory.container.ContainerMoltenSaltSupplier;
import nuclearscience.common.settings.Constants;
import nuclearscience.common.tile.msreactor.TileMoltenSaltSupplier;
import nuclearscience.prefab.utils.NuclearTextUtils;

@OnlyIn(Dist.CLIENT)
public class ScreenMoltenSaltSupplier extends GenericScreen<ContainerMoltenSaltSupplier> {

	public ScreenMoltenSaltSupplier(ContainerMoltenSaltSupplier container, PlayerInventory playerInventory, ITextComponent title) {
		super(container, playerInventory, title);
		addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2).wattage(Constants.MOLTENSALTSUPPLIER_USAGE_PER_TICK * 20));
		addComponent(new ScreenComponentProgress(ProgressBars.PROGRESS_ARROW_RIGHT, () -> {
			TileMoltenSaltSupplier supplier = menu.getHostFromIntArray();
			if (supplier == null) {
				return 0;
			}
			return supplier.reactorWaste.get() / TileMoltenSaltSupplier.AMT_PER_WASTE;
		}, 77, 35) {
			@Override
			public void renderForeground(MatrixStack stack, int xAxis, int yAxis, int guiWidth, int guiHeight) {
				if (!isHoveredOrFocused()) {
					return;
				}
				TileMoltenSaltSupplier supplier = menu.getHostFromIntArray();
				if (supplier == null) {
					return;
				}
				displayTooltip(stack, NuclearTextUtils.gui("saltsupplier.wastecont", ElectroTextUtils.ratio(ChatFormatter.formatFluidMilibuckets(supplier.reactorWaste.get()), ChatFormatter.formatFluidMilibuckets(TileMoltenSaltSupplier.AMT_PER_WASTE)).withStyle(TextFormatting.DARK_GRAY)).withStyle(TextFormatting.GRAY).getVisualOrderText(), xAxis, yAxis);

			}
		});
		new InventoryIOWrapper(this, -AbstractScreenComponentInfo.SIZE + 1, AbstractScreenComponentInfo.SIZE + 2, 75, 82, 8, 72);
	}
}