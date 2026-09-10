package nuclearscience.prefab.screen.component.quantumtunnel;

import javax.annotation.Nullable;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import nuclearscience.api.quantumtunnel.TunnelFrequency;
import nuclearscience.common.inventory.container.ContainerQuantumTunnel;
import nuclearscience.common.tile.TileQuantumTunnel;
import voltaic.api.screen.ITexture;
import voltaic.prefab.screen.GenericScreen;
import voltaic.prefab.screen.component.button.ScreenComponentButton;
import voltaic.prefab.screen.component.editbox.ScreenComponentEditBox;
import voltaic.prefab.utilities.math.Color;

public class ButtonTunnelFrequency extends ScreenComponentButton<ButtonTunnelFrequency> {
    @Nullable
    private TunnelFrequency frequency = null;

    private boolean isSelected = false;

    public ButtonTunnelFrequency(int x, int y, int width, int height) {
	super(x, y, width, height);
    }

    @Override
    public void renderBackground(GuiGraphics graphics, int xAxis, int yAxis, int guiWidth, int guiHeight) {
	if (!isVisible())
	    return;

	GenericScreen<ContainerQuantumTunnel> screen = (GenericScreen<ContainerQuantumTunnel>) requireScreen();

	TileQuantumTunnel tile = screen.getMenu().getSafeHost().orElse(null);
	if (tile == null)
	    return;

	Level level = tile.getLevel();
	if (level == null)
	    return;

	ITexture texture;
	TunnelFrequency parFrequency = frequency;
	if (parFrequency != null && (tile.frequency.getValue().equals(parFrequency) || isSelected || isHovered())) {
	    texture = QuantumTunnelTextures.FREQUENCY_SELECTED;
	} else {
	    texture = QuantumTunnelTextures.FREQUENCY;
	}

	ScreenComponentEditBox.drawExpandedBox(graphics, texture.getLocation(), xLocation + guiWidth,
		yLocation + guiHeight, width, height);
	if (parFrequency == null)
	    return;

	graphics.drawString(screen.getFontRenderer(), Component.literal(parFrequency.getName()),
		guiWidth + xLocation + 5, guiHeight + yLocation + 5, Color.WHITE.color(), false);

	Player player = level.getPlayerByUUID(parFrequency.getCreatorId());

	graphics.drawString(screen.getFontRenderer(),
		player != null ? player.getName() : Component.literal(parFrequency.getCreatorFallbackName()),
		guiWidth + xLocation + 5, guiHeight + yLocation + 15, Color.TEXT_GRAY.color(), false);

    }

    public void setFrequency(@Nullable TunnelFrequency frequency) {
	this.frequency = frequency;
    }

    @Nullable
    public TunnelFrequency getFrequency() {
	return frequency;
    }

    public void setSelected(boolean selected) {
	isSelected = selected;
    }

}
