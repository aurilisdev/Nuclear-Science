package nuclearscience.prefab.screen.component.quantumtunnel;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Player;
import nuclearscience.api.quantumtunnel.FrequencyType;
import nuclearscience.client.screen.ScreenQuantumTunnel;
import nuclearscience.common.packet.NetworkHandler;
import nuclearscience.common.packet.type.server.PacketCreateNewFreqeuency;
import nuclearscience.prefab.screen.component.NuclearIconTypes;
import nuclearscience.prefab.utils.NuclearTextUtils;
import voltaic.prefab.screen.component.button.ScreenComponentButton;
import voltaic.prefab.screen.component.editbox.ScreenComponentEditBox;
import voltaic.prefab.screen.component.types.ScreenComponentSimpleLabel;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentGuiTab;
import voltaic.prefab.utilities.math.Color;

public class WrapperNewFrequency {


    public ScreenComponentButton<?> button;

    private ScreenComponentSimpleLabel titleLabel;
    private ScreenComponentSimpleLabel typeLabel;
    private ScreenComponentButton<?> publicButton;
    private ScreenComponentButton<?> privateButton;

    private ScreenComponentSimpleLabel nameLabel;
    public ScreenComponentEditBox nameEditBox;
    private ScreenComponentButton<?> createButton;
    private ScreenComponentButton<?> cancelButton;

    public WrapperNewFrequency(ScreenQuantumTunnel screen, int tabX, int tabY, int x, int y) {

        screen.addComponent(button = (ScreenComponentButton<?>) new ScreenComponentButton<>(ScreenComponentGuiTab.GuiInfoTabTextures.REGULAR, tabX, tabY).setOnPress(button -> {
            //
            button.isPressed = !button.isPressed;

            if (button.isPressed) {

                screen.frequencyWrapper.updateVisibility(false);
                screen.ioWrapper.updateVisibility(false);
                screen.ioWrapper.button.isPressed = false;
                screen.editFrequencyWrapper.updateVisibility(false);

                updateVisibility(true);

                screen.slider.setVisible(false);

            } else {

                screen.frequencyWrapper.updateVisibility(true);
                screen.ioWrapper.updateVisibility(false);
                screen.ioWrapper.button.isPressed = false;
                screen.editFrequencyWrapper.updateVisibility(false);

                updateVisibility(false);

                screen.slider.setVisible(true);

            }

        }).onTooltip((poseStack, but, xAxis, yAxis) -> {
            //
            ScreenComponentButton<?> button = (ScreenComponentButton<?>) but;
            List<FormattedCharSequence> tooltips = new ArrayList<>();
            tooltips.add(NuclearTextUtils.tooltip("quantumtunnel.createnew").withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
            if (!button.isPressed) {
                tooltips.add(ElectroTextUtils.tooltip("inventoryio.presstoshow").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY).getVisualOrderText());
            } else {
                tooltips.add(ElectroTextUtils.tooltip("inventoryio.presstohide").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY).getVisualOrderText());
            }

            screen.displayTooltips(poseStack, tooltips, xAxis, yAxis);

        }).setIcon(NuclearIconTypes.CREATENEW));

        screen.addComponent(titleLabel = new ScreenComponentSimpleLabel(x + 15, y + 20, 10, Color.TEXT_GRAY, NuclearTextUtils.gui("quantumtunnel.newfrequency")));

        screen.addComponent(typeLabel = new ScreenComponentSimpleLabel(x + 15, y + 40, 10, Color.TEXT_GRAY, NuclearTextUtils.gui("quantumtunnel.frequencytype")));
        screen.addComponent(privateButton = new ScreenComponentButton<>(x + 13, y + 55, 70, 20).setOnPress(button -> {

            privateButton.isPressed = true;
            publicButton.isPressed = false;

        }).setLabel(NuclearTextUtils.gui("quantumtunnel.private")));

        screen.addComponent(publicButton = new ScreenComponentButton<>(x + 93, y + 55, 70, 20).setOnPress(button -> {

            privateButton.isPressed = false;
            publicButton.isPressed = true;

        }).setLabel(NuclearTextUtils.gui("quantumtunnel.public")));


        screen.addComponent(nameLabel = new ScreenComponentSimpleLabel(x + 15, y + 80, 10, Color.TEXT_GRAY, NuclearTextUtils.gui("quantumtunnel.name")));
        screen.addEditBox(nameEditBox = new ScreenComponentEditBox(x + 15, y + 90, 120, 15, screen.getFontRenderer()).setTextColor(Color.WHITE).setTextColorUneditable(Color.WHITE).setMaxLength(50));

        screen.addComponent(createButton = new ScreenComponentButton<>(x + 13, y + 120, 70, 20).setOnPress(button -> {

            if(nameEditBox.getValue().isEmpty() || nameEditBox.getValue().isBlank()) {
                return;
            }

            Player player = Minecraft.getInstance().player;

            if(player == null) {
                return;
            }

            FrequencyType type;

            if(privateButton.isPressed) {
                type = FrequencyType.PRIVATE;
            } else if (publicButton.isPressed) {
                type = FrequencyType.PUBLIC;
            } else {
                return;
            }

            NetworkHandler.CHANNEL.sendToServer(new PacketCreateNewFreqeuency(player.getUUID(), nameEditBox.getValue(), type));

            updateVisibility(false);

            nameEditBox.setValue("");
            this.button.isPressed = false;


            screen.frequencyWrapper.updateVisibility(true);
            screen.slider.setVisible(true);

        }).setLabel(NuclearTextUtils.gui("quantumtunnel.create")));

        screen.addComponent(cancelButton = new ScreenComponentButton<>(x + 93, y + 120, 70, 20).setOnPress(button -> {

            screen.frequencyWrapper.updateVisibility(true);

            updateVisibility(false);

            nameEditBox.setValue("");
            this.button.isPressed = false;

            screen.slider.setVisible(true);

        }).setLabel(NuclearTextUtils.gui("quantumtunnel.cancel")));

        updateVisibility(false);
    }

    public void updateVisibility(boolean show) {
        titleLabel.setVisible(show);
        typeLabel.setVisible(show);
        publicButton.setVisible(show);
        publicButton.isPressed = false;
        privateButton.setVisible(show);
        privateButton.isPressed = false;
        nameLabel.setVisible(show);
        nameEditBox.setVisible(show);
        nameEditBox.setActive(show);
        createButton.setVisible(show);
        cancelButton.setVisible(show);
        nameEditBox.setValue("");
    }
}
