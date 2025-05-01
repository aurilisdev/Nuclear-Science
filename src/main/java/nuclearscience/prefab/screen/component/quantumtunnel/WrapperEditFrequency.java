package nuclearscience.prefab.screen.component.quantumtunnel;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import nuclearscience.api.quantumtunnel.TunnelFrequency;
import nuclearscience.client.screen.ScreenQuantumTunnel;
import nuclearscience.common.packet.NetworkHandler;
import nuclearscience.common.packet.type.server.PacketEditFrequency;
import nuclearscience.common.tile.TileQuantumTunnel;
import nuclearscience.prefab.utils.NuclearTextUtils;
import voltaic.prefab.screen.component.button.ScreenComponentButton;
import voltaic.prefab.screen.component.editbox.ScreenComponentEditBox;
import voltaic.prefab.screen.component.types.ScreenComponentSimpleLabel;
import voltaic.prefab.utilities.math.Color;

public class WrapperEditFrequency {

    private ScreenComponentSimpleLabel titleLabel;

    private ScreenComponentSimpleLabel nameLabel;
    public ScreenComponentEditBox nameEditBox;
    private ScreenComponentButton<?> saveButton;
    private ScreenComponentButton<?> cancelButton;

    private TunnelFrequency currFrequency;

    public WrapperEditFrequency(ScreenQuantumTunnel screen, int x, int y) {

        screen.addComponent(titleLabel = new ScreenComponentSimpleLabel(x + 15, y + 20, 10, Color.TEXT_GRAY, NuclearTextUtils.gui("quantumtunnel.editfrequency")));

        screen.addComponent(nameLabel = new ScreenComponentSimpleLabel(x + 15, y + 40, 10, Color.TEXT_GRAY, NuclearTextUtils.gui("quantumtunnel.name")));
        screen.addEditBox(nameEditBox = new ScreenComponentEditBox(x + 15, y + 50, 120, 15, screen.getFontRenderer()).setTextColor(Color.WHITE).setTextColorUneditable(Color.WHITE).setMaxLength(50));

        screen.addComponent(saveButton = new ScreenComponentButton<>(x + 13, y + 70, 70, 20).setOnPress(button -> {

            if (nameEditBox.getValue().isEmpty() || nameEditBox.getValue().isBlank() || currFrequency == null) {
                return;
            }

            Player player = Minecraft.getInstance().player;

            if (player == null) {
                return;
            }

            TileQuantumTunnel tile = screen.getMenu().getSafeHost();

            if(tile == null) {
                return;
            }

            String name = nameEditBox.getValue();

            if(!currFrequency.getName().equals(name) && currFrequency.getCreatorId().equals(player.getUUID())) {

                currFrequency.setName(name);

                if(tile.frequency.getValue().equals(currFrequency)) {
                    tile.frequency.setValue(currFrequency);
                    tile.frequency.forceDirtyForManager();
                }

                NetworkHandler.CHANNEL.sendToServer(new PacketEditFrequency(player.getUUID(), currFrequency));


            }

            updateVisibility(false);

            nameEditBox.setValue("");


            screen.frequencyWrapper.updateVisibility(true);

        }).setLabel(NuclearTextUtils.gui("quantumtunnel.save")));

        screen.addComponent(cancelButton = new ScreenComponentButton<>(x + 93, y + 70, 70, 20).setOnPress(button -> {

            screen.frequencyWrapper.updateVisibility(true);

            updateVisibility(false);

            nameEditBox.setValue("");

            screen.slider.setVisible(true);

        }).setLabel(NuclearTextUtils.gui("quantumtunnel.cancel")));

        updateVisibility(false);
    }

    public void updateVisibility(boolean show) {
        titleLabel.setVisible(show);
        nameLabel.setVisible(show);
        nameEditBox.setVisible(show);
        nameEditBox.setActive(show);
        saveButton.setVisible(show);
        cancelButton.setVisible(show);
    }

    public void updateFrequency(TunnelFrequency frequency) {
        this.currFrequency = frequency;
        nameEditBox.setValue(frequency.getName());
    }
}
