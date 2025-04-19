package nuclearscience.prefab.screen.component.quantumtunnel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import nuclearscience.api.quantumtunnel.TunnelFrequency;
import nuclearscience.client.screen.ScreenQuantumTunnel;
import nuclearscience.common.packet.type.server.PacketDeleteFrequency;
import nuclearscience.common.tile.TileQuantumTunnel;
import nuclearscience.prefab.screen.component.NuclearIconTypes;
import nuclearscience.prefab.utils.NuclearTextUtils;
import voltaic.prefab.screen.component.button.ScreenComponentButton;
import voltaic.prefab.screen.component.types.ScreenComponentFillArea;
import voltaic.prefab.screen.component.types.ScreenComponentSimpleLabel;
import voltaic.prefab.screen.component.types.ScreenComponentVerticalSlider;
import voltaic.prefab.utilities.math.Color;

public class WrapperQuantumTunnelFrequencies {

    private final ScreenQuantumTunnel screen;

    private ScreenComponentFillArea box;
    private ScreenComponentSimpleLabel frequencyLabel;

    private ScreenComponentButton<?> enable;
    private ScreenComponentButton<?> disable;

    private ButtonTunnelFrequency[] frequencyButtons = new ButtonTunnelFrequency[5];
    private ScreenComponentButton[] editButtons = new ScreenComponentButton[5];
    private ScreenComponentButton[] deleteButtons = new ScreenComponentButton[5];

    private ScreenComponentButton<?> publicSelector;
    private ScreenComponentButton<?> privateSelector;

    private int topRowIndex = 0;
    private int lastRowCount = 0;

    private boolean isPrivate = true;

    private TunnelFrequency selectedFrequency = null;

    private static final int BUTTON_COUNT = 5;

    public WrapperQuantumTunnelFrequencies(ScreenQuantumTunnel screen, int x, int y) {
        this.screen = screen;

        box = new ScreenComponentFillArea(x + 5, y + 24, 120, 10, new Color(112, 112, 112, 255), new Color(28, 28, 28, 255));

        frequencyLabel = new ScreenComponentSimpleLabel(x + 6, y + 25, 10, Color.WHITE, () -> {
            TileQuantumTunnel tile = screen.getMenu().getSafeHost();
            Component frequencyName;
            if (selectedFrequency != null) {
                frequencyName = Component.literal(selectedFrequency.getName());
            } else if (tile == null || tile.frequency.getValue().equals(TunnelFrequency.NO_FREQUENCY)) {
                frequencyName = NuclearTextUtils.gui("quantumtunnel.none");
            } else {
                frequencyName = Component.literal(tile.frequency.getValue().getName());
            }

            return frequencyName;

        });

        enable = (ScreenComponentButton<?>) new ScreenComponentButton<>(x + 127, y + 19,20, 20).setOnPress(but -> {
            TileQuantumTunnel tile = screen.getMenu().getSafeHost();
            if(tile == null || selectedFrequency == null || tile.frequency.getValue().equals(selectedFrequency)) {
                return;
            }
            tile.frequency.setValue(selectedFrequency);

        }).onTooltip((graphics, button, xAxis, yAxis) -> graphics.renderTooltip(screen.getFontRenderer(), NuclearTextUtils.gui("quantumtunnel.enable"), xAxis, yAxis)).setIcon(NuclearIconTypes.ENABLE);

        disable = (ScreenComponentButton<?>) new ScreenComponentButton<>(x + 150, y + 19,20, 20).setOnPress(but -> {
            TileQuantumTunnel tile = screen.getMenu().getSafeHost();
            if(tile == null) {
                return;
            }
            tile.frequency.setValue(TunnelFrequency.NO_FREQUENCY);

        }).onTooltip((graphics, button, xAxis, yAxis) -> graphics.renderTooltip(screen.getFontRenderer(), NuclearTextUtils.gui("quantumtunnel.disable"), xAxis, yAxis)).setIcon(NuclearIconTypes.DISABLE);

        publicSelector = new ScreenComponentButton<>(x + 16, y + 44, 70, 15).setOnPress(but -> {
            ScreenComponentButton<?> button = but;
            button.isPressed = true;
            privateSelector.isPressed = false;
            isPrivate = false;
        }).setLabel(NuclearTextUtils.gui("quantumtunnel.public"));

        privateSelector = new ScreenComponentButton<>(x + 70 + 16 + 5, y + 44, 70, 15).setOnPress(but -> {
            ScreenComponentButton<?> button = but;
            button.isPressed = true;
            publicSelector.isPressed = false;
            isPrivate = true;
        }).setLabel(NuclearTextUtils.gui("quantumtunnel.private"));

        int butOffX = 19;
        int butOffY = 64;

        for (int i = 0; i < BUTTON_COUNT; i++) {
            frequencyButtons[i] = new ButtonTunnelFrequency(x + butOffX, y + butOffY + 25 * i,110, 25).setOnPress(but -> {
                ButtonTunnelFrequency button = (ButtonTunnelFrequency) but;

                TileQuantumTunnel tile = screen.getMenu().getSafeHost();
                if(tile == null || button.getFrequency() == null) {
                    selectedFrequency = null;
                    return;
                }

                selectedFrequency = button.getFrequency();

            });
        }

        for (int i = 0; i < BUTTON_COUNT; i++) {

            final int index = i;

            editButtons[i] = (ScreenComponentButton) new ScreenComponentButton<>(x + butOffX + 111, y + 2 + butOffY + 25 * i,20, 20).setOnPress(but -> {

                ButtonTunnelFrequency tunnel = frequencyButtons[index];

                if(tunnel.getFrequency() == null) {
                    return;
                }

                Player player = Minecraft.getInstance().player;

                if(player == null || !tunnel.getFrequency().getCreatorId().equals(player.getUUID())) {
                    return;
                }

                screen.editFrequencyWrapper.updateFrequency(tunnel.getFrequency());
                screen.editFrequencyWrapper.updateVisibility(true);
                screen.slider.setVisible(false);
                updateVisibility(false);



            }).onTooltip((graphics, button, xAxis, yAxis) -> graphics.renderTooltip(screen.getFontRenderer(), NuclearTextUtils.gui("quantumtunnel.edit"), xAxis, yAxis)).setIcon(NuclearIconTypes.PENCIL);
        }

        for (int i = 0; i < BUTTON_COUNT; i++) {

            final int index = i;

            deleteButtons[i] = (ScreenComponentButton) new ScreenComponentButton<>(x + butOffX + 132, y + 2 + butOffY + 25 * i,20, 20).setOnPress(but -> {

                ButtonTunnelFrequency tunnel = frequencyButtons[index];

                TileQuantumTunnel tile = screen.getMenu().getSafeHost();

                Player player = Minecraft.getInstance().player;

                if(player == null || tunnel.getFrequency() == null || tile == null) {
                    return;
                }

                TunnelFrequency frequency = tunnel.getFrequency();

                if(selectedFrequency != null && selectedFrequency.equals(frequency) && selectedFrequency.getCreatorId().equals(player)) {
                    selectedFrequency = null;
                }

                if(tile.frequency.getValue().equals(frequency)) {
                    tile.frequency.setValue(TunnelFrequency.NO_FREQUENCY);
                }

                PacketDistributor.sendToServer(new PacketDeleteFrequency(player.getUUID(), frequency));



            }).onTooltip((graphics, button, xAxis, yAxis) -> graphics.renderTooltip(screen.getFontRenderer(), NuclearTextUtils.gui("quantumtunnel.delete"), xAxis, yAxis)).setIcon(NuclearIconTypes.DELETE);
        }



        screen.addComponent(box);
        screen.addComponent(frequencyLabel);

        screen.addComponent(enable);
        screen.addComponent(disable);

        screen.addComponent(publicSelector);
        screen.addComponent(privateSelector);

        for (int i = 0; i < 5; i++) {
            screen.addComponent(frequencyButtons[i]);
        }
        for (int i = 0; i < 5; i++) {
            screen.addComponent(editButtons[i]);
        }
        for (int i = 0; i < 5; i++) {
            screen.addComponent(deleteButtons[i]);
        }

        privateSelector.isPressed = true;

    }

    public void tick() {
        TileQuantumTunnel tile = screen.getMenu().getSafeHost();
        if(tile == null) {
            return;
        }

        Player player = Minecraft.getInstance().player;

        if(player == null) {
            return;
        }

        List<TunnelFrequency> frequencies = new ArrayList<>();

        if(isPrivate) {
            frequencies.addAll(tile.clientFrequencies.get(player.getUUID()));
        } else {
           frequencies.addAll(tile.clientFrequencies.get(TunnelFrequency.PUBLIC_ID));
        }

        lastRowCount = frequencies.size();

        for(int i = 0; i < BUTTON_COUNT; i++) {

            ButtonTunnelFrequency button = frequencyButtons[i];

            int index = topRowIndex + i;

            if (index < frequencies.size()) {
                button.setFrequency(frequencies.get(index));
            } else {
                button.setFrequency(null);
            }

        }

        ScreenComponentVerticalSlider slider = screen.slider;
        if (lastRowCount > BUTTON_COUNT) {
            slider.updateActive(true);
            if (!slider.isSliderHeld()) {
                int moveRoom = screen.slider.height - 15 -2;

                // int moveRoom = 102 - 2;
                double moved = topRowIndex / (lastRowCount - (double) BUTTON_COUNT);
                slider.setSliderYOffset((int) (moveRoom * moved));
            }
        } else {
            slider.updateActive(false);
            slider.setSliderYOffset(0);
            topRowIndex = 0;
        }

    }

    // pos for down, neg for up
    public void handleMouseScroll(int dir) {
        if (Screen.hasControlDown()) {
            dir *= 4;
        }
        int lastRowIndex = lastRowCount - 1;
        if (lastRowCount > BUTTON_COUNT) {
            // check in case something borked
            if (topRowIndex >= lastRowCount) {
                topRowIndex = lastRowIndex - (BUTTON_COUNT - 1);
            }
            topRowIndex = Mth.clamp(topRowIndex += dir, 0, lastRowIndex - (BUTTON_COUNT - 1));
        } else {
            topRowIndex = 0;
        }
    }

    public Consumer<Integer> getSliderClickedConsumer() {
        return (mouseY) -> {
            ScreenComponentVerticalSlider slider = screen.slider;
            if (slider.isSliderActive()) {
                int sliderY = slider.yLocation;
                int sliderHeight = slider.height;
                int mouseHeight = mouseY - sliderY;
                if (mouseHeight >= sliderHeight - 2 - 15) {
                    topRowIndex = lastRowCount - BUTTON_COUNT;
                    slider.setSliderYOffset(sliderHeight - 2 - 15);
                } else if (mouseHeight <= 2) {
                    topRowIndex = 0;
                    slider.setSliderYOffset(0);
                } else {
                    double heightRatio = (double) mouseHeight / (double) sliderHeight;
                    topRowIndex = (int) Math.round((lastRowCount - BUTTON_COUNT) * heightRatio);
                    int moveRoom = slider.height - 15 - 2;
                    double moved = topRowIndex / (lastRowCount - (double)BUTTON_COUNT);
                    slider.setSliderYOffset((int) (moveRoom * moved));
                }
            }
        };
    }

    public Consumer<Integer> getSliderDraggedConsumer() {
        return (mouseY) -> {
            ScreenComponentVerticalSlider slider = screen.slider;
            if (slider.isSliderActive()) {
                int sliderY = slider.yLocation;
                int sliderHeight = slider.height;
                if (mouseY <= sliderY + 2) {
                    topRowIndex = 0;
                    slider.setSliderYOffset(0);
                } else if (mouseY >= sliderY + sliderHeight - 2 - 15) {
                    topRowIndex = lastRowCount - BUTTON_COUNT;
                    slider.setSliderYOffset(sliderHeight - 2 - 15);
                } else {
                    int mouseHeight = mouseY - sliderY;
                    slider.setSliderYOffset(mouseHeight);
                    double heightRatio = (double) mouseHeight / (double) sliderHeight;
                    topRowIndex = (int) Math.round((lastRowCount - BUTTON_COUNT) * heightRatio);
                }
            }
        };
    }

    public void updateVisibility(boolean show) {
        box.setVisible(show);
        frequencyLabel.setVisible(show);

        enable.setVisible(show);
        disable.setVisible(show);

        for(ButtonTunnelFrequency button : frequencyButtons) {
            button.setVisible(show);
        }

        for(ScreenComponentButton<?> button : editButtons) {
            button.setVisible(show);
        }

        for(ScreenComponentButton<?> button : deleteButtons) {
            button.setVisible(show);
        }

        publicSelector.setVisible(show);
        privateSelector.setVisible(show);
    }

}
