package nuclearscience.prefab.screen.component.quantumtunnel;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.util.FormattedCharSequence;
import nuclearscience.client.screen.ScreenQuantumTunnel;
import nuclearscience.prefab.screen.component.NuclearIconTypes;
import nuclearscience.prefab.utils.NuclearTextUtils;
import voltaic.prefab.screen.component.button.ScreenComponentButton;
import voltaic.prefab.screen.component.types.ScreenComponentSimpleLabel;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentGuiTab;
import voltaic.prefab.utilities.math.Color;

public class WrapperIOEditor {

    public ScreenComponentButton<?> button;

    private ButtonIO[] ioArr = new ButtonIO[6];

    private ScreenComponentSimpleLabel label;

    public WrapperIOEditor(ScreenQuantumTunnel screen, int tabX, int tabY, int slotStartX, int slotStartY, int labelX, int labelY) {
        screen.addComponent(button = (ScreenComponentButton<?>) new ScreenComponentButton<>(ScreenComponentGuiTab.GuiInfoTabTextures.REGULAR, tabX, tabY).setOnPress(but -> {
            //
            ScreenComponentButton<?> button = but;
            button.isPressed = !button.isPressed;

            if (button.isPressed) {

                screen.frequencyWrapper.updateVisibility(false);
                screen.newFrequencyWrapper.updateVisibility(false);
                screen.newFrequencyWrapper.button.isPressed = false;
                screen.editFrequencyWrapper.updateVisibility(false);

                updateVisibility(true);

                screen.slider.setVisible(false);

            } else {

                screen.frequencyWrapper.updateVisibility(true);
                screen.newFrequencyWrapper.updateVisibility(false);
                screen.newFrequencyWrapper.button.isPressed = false;
                screen.editFrequencyWrapper.updateVisibility(false);

                updateVisibility(false);

                screen.slider.setVisible(true);

            }

        }).onTooltip((poseStack, but, xAxis, yAxis) -> {
            //
            ScreenComponentButton<?> button = (ScreenComponentButton<?>) but;
            List<FormattedCharSequence> tooltips = new ArrayList<>();
            tooltips.add(NuclearTextUtils.tooltip("quantumtunnel.ioconfig").withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
            if (!button.isPressed) {
                tooltips.add(ElectroTextUtils.tooltip("inventoryio.presstoshow").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY).getVisualOrderText());
            } else {
                tooltips.add(ElectroTextUtils.tooltip("inventoryio.presstohide").withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY).getVisualOrderText());
            }

            screen.displayTooltips(poseStack, tooltips, xAxis, yAxis);

        }).setIcon(NuclearIconTypes.IOCONFIG));

        screen.addComponent(label = new ScreenComponentSimpleLabel(labelX, labelY, 10, Color.TEXT_GRAY, ElectroTextUtils.tooltip("inventoryio.slotmap")));

        label.setVisible(false);

        screen.addComponent(ioArr[0] = new ButtonIO(slotStartX, slotStartY + 1, Direction.UP));
        screen.addComponent(ioArr[1] = new ButtonIO(slotStartX, slotStartY + 26, Direction.NORTH));
        screen.addComponent(ioArr[2] = new ButtonIO(slotStartX, slotStartY + 26 * 2 - 1, Direction.DOWN));
        screen.addComponent(ioArr[3] = new ButtonIO(slotStartX - 25, slotStartY + 26, Direction.EAST));
        screen.addComponent(ioArr[4] = new ButtonIO(slotStartX + 25, slotStartY + 26, Direction.WEST));
        screen.addComponent(ioArr[5] = new ButtonIO(slotStartX + 25, slotStartY + 26 * 2 - 1, Direction.SOUTH));

        for (ButtonIO io : ioArr) {
            io.setVisible(false);
        }
    }

    public void updateVisibility(boolean show) {
        for (ButtonIO io : ioArr) {
            io.setVisible(show);
        }

        label.setVisible(show);
    }

}
