package nuclearscience.prefab.screen.component.quantumtunnel;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import nuclearscience.api.quantumtunnel.TunnelFrequency;
import nuclearscience.common.inventory.container.ContainerQuantumTunnel;
import nuclearscience.common.tile.TileQuantumTunnel;
import voltaic.api.screen.ITexture;
import voltaic.prefab.screen.GenericScreen;
import voltaic.prefab.screen.component.button.ScreenComponentButton;
import voltaic.prefab.screen.component.editbox.ScreenComponentEditBox;
import voltaic.prefab.utilities.RenderingUtils;
import voltaic.prefab.utilities.math.Color;

public class ButtonTunnelFrequency extends ScreenComponentButton<ButtonTunnelFrequency> {
    private TunnelFrequency frequency = null;

    private boolean isSelected = false;

    public ButtonTunnelFrequency(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void renderBackground(MatrixStack poseStack, int xAxis, int yAxis, int guiWidth, int guiHeight) {
        if (!isVisible()) {
            return;
        }

        GenericScreen<ContainerQuantumTunnel> screen = (GenericScreen<ContainerQuantumTunnel>) gui;

        TileQuantumTunnel tile = screen.getMenu().getSafeHost();

        if (tile == null) {
            return;
        }

        ITexture texture;

        if (frequency != null && (tile.frequency.getValue().equals(frequency) || isSelected || isHovered())) {

            texture = QuantumTunnelTextures.FREQUENCY_SELECTED;

        } else {

            texture = QuantumTunnelTextures.FREQUENCY;

        }

        RenderingUtils.bindTexture(texture.getLocation());
        ScreenComponentEditBox.drawExpandedBox(poseStack, x + guiWidth, y + guiHeight, width, height);

        if (frequency == null) {
            return;
        }

        screen.getFontRenderer().draw(poseStack, new StringTextComponent(frequency.getName()), guiWidth + x + 5, guiHeight + y + 5, Color.WHITE.color());

        PlayerEntity player = tile.getLevel().getPlayerByUUID(frequency.getCreatorId());

        screen.getFontRenderer().draw(poseStack, player != null ? player.getName() : new StringTextComponent(frequency.getCreatorFallbackName()), guiWidth + x + 5, guiHeight + y + 15, Color.TEXT_GRAY.color());


    }

    public void setFrequency(TunnelFrequency frequency) {
        this.frequency = frequency;
    }

    public TunnelFrequency getFrequency() {
        return frequency;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }

}
