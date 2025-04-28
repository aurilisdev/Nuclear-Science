package nuclearscience.prefab.screen.component.logisticsnetwork;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import nuclearscience.api.network.reactorlogistics.Interface;
import nuclearscience.client.screen.util.GenericInterfaceBoundScreen;
import nuclearscience.common.tile.reactor.logisticsnetwork.interfaces.GenericTileInterface;
import nuclearscience.common.tile.reactor.logisticsnetwork.util.GenericTileInterfaceBound;
import nuclearscience.prefab.screen.component.quantumtunnel.QuantumTunnelTextures;
import voltaic.api.screen.ITexture;
import voltaic.prefab.screen.component.button.ScreenComponentButton;
import voltaic.prefab.screen.component.editbox.ScreenComponentEditBox;
import voltaic.prefab.utilities.math.Color;

public class ButtonInterfaceType extends ScreenComponentButton<ButtonInterfaceType> {

    private Interface bound;

    private boolean isSelected = false;

    public ButtonInterfaceType(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void renderBackground(GuiGraphics graphics, int xAxis, int yAxis, int guiWidth, int guiHeight) {
        if(!isVisible()) {
            return;
        }

        GenericInterfaceBoundScreen<?> screen = (GenericInterfaceBoundScreen<?>) gui;

        GenericTileInterfaceBound tile = screen.getMenu().getSafeHost();

        if (tile == null) {
            return;
        }

        ITexture texture;

        if (bound != null && (tile.interfaceLocation.getValue().equals(bound.pos()) || isSelected || isHovered())) {

            texture = QuantumTunnelTextures.FREQUENCY_SELECTED;

        } else {

            texture = QuantumTunnelTextures.FREQUENCY;

        }

        ScreenComponentEditBox.drawExpandedBox(graphics, texture.getLocation(), xLocation + guiWidth, yLocation + guiHeight, width, height);

        if (bound == null) {
            return;
        }

        graphics.renderItem(GenericTileInterface.getItemFromType(bound.type()), guiWidth + xLocation + 2, guiHeight + yLocation + 2);

        Font font = screen.getFontRenderer();

        Component text = Component.literal(bound.pos().toShortString());

        int xOffset = 20;

        int maxWidth = width - xOffset - 2;

        int width = font.width(text);

        float scale = 1.0F;

        float addY = 0;

        if(width > maxWidth) {
            scale = (float) maxWidth / (float) width;
            addY = (font.lineHeight - font.lineHeight * scale) / 2.0F / scale;
        }

        graphics.pose().pushPose();

        graphics.pose().translate(guiWidth + xLocation, guiHeight + yLocation, 0);

        graphics.pose().scale(scale, scale, 0);

        graphics.drawString(font, text.getVisualOrderText(), xOffset / scale, 7.0F / scale + addY, Color.TEXT_GRAY.color(), false);

        graphics.pose().popPose();

    }

    public void setInterface(Interface bound) {
        this.bound = bound;
    }

    public Interface getInterface() {
        return bound;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }



}
