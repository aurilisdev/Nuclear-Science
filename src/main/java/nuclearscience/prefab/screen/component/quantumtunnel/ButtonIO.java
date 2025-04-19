package nuclearscience.prefab.screen.component.quantumtunnel;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FormattedCharSequence;
import nuclearscience.common.inventory.container.ContainerQuantumTunnel;
import nuclearscience.common.tile.TileQuantumTunnel;
import nuclearscience.prefab.utils.NuclearTextUtils;
import voltaic.prefab.screen.GenericScreen;
import voltaic.prefab.screen.component.button.ScreenComponentButton;
import voltaic.prefab.screen.component.types.ScreenComponentInventoryIO;
import voltaic.prefab.utilities.math.Color;

public class ButtonIO extends ScreenComponentButton<ButtonIO> {
    public static final int DRAWING_AREA_SIZE = 24;

    private static final Color SLOT_GRAY = new Color(150, 150, 150, 255);
    public static final Color INPUT = new Color(167, 223, 248, 255);
    public static final Color OUTPUT = new Color(255, 120, 46, 255);

    private final Direction side;

    public ButtonIO(int x, int y, Direction side) {
        super(ScreenComponentInventoryIO.InventoryIOTextures.DEFAULT, x, y);
        onTooltip((graphics, component, xAxis, yAxis) -> {
            List<FormattedCharSequence> tooltips = new ArrayList<>();
            tooltips.add(getLabelFromDir().getVisualOrderText());
            tooltips.add(getModeForSide().getVisualOrderText());
            graphics.renderTooltip(gui.getFontRenderer(), tooltips, xAxis, yAxis);
        });
        setOnPress(button -> {
            GenericScreen<?> screen = (GenericScreen<?>) gui;

            ContainerQuantumTunnel container = (ContainerQuantumTunnel) screen.getMenu();

            TileQuantumTunnel tile = container.getSafeHost();

            if (tile == null) {
                return;
            }

            boolean in = tile.readInputDirections().contains(side);
            boolean out = tile.readOutputDirections().contains(side);

            if(in) {
                tile.removeInputDirection(side);
                tile.writeOutputDirection(side);
            } else if (out) {
                tile.removeInputDirection(side);
                tile.removeOutputDirection(side);
            } else {
                tile.writeInputDirection(side);
            }


        });
        this.side = side;
    }

    @Override
    public void renderBackground(GuiGraphics graphics, int xAxis, int yAxis, int guiWidth, int guiHeight) {
        super.renderBackground(graphics, xAxis, yAxis, guiWidth, guiHeight);

        GenericScreen<?> screen = (GenericScreen<?>) gui;

        ContainerQuantumTunnel container = (ContainerQuantumTunnel) screen.getMenu();

        TileQuantumTunnel tile = container.getSafeHost();

        if (tile == null) {
            return;
        }

        Color ioColor;

        if(tile.readInputDirections().contains(side)) {
            ioColor = INPUT;
        } else if (tile.readOutputDirections().contains(side)) {
            ioColor = OUTPUT;
        } else {
            ioColor = SLOT_GRAY;
        }

        graphics.fill(xLocation + guiWidth + 1, yLocation + guiHeight + 1, xLocation + guiWidth + 1 + DRAWING_AREA_SIZE, yLocation + guiHeight + 1 + DRAWING_AREA_SIZE, ioColor.color());

    }

    private MutableComponent getLabelFromDir() {
        MutableComponent component = null;
        switch (side) {
            default:
            case DOWN:
                component = ElectroTextUtils.tooltip("inventoryio.bottom");
                break;
            case UP:
                component = ElectroTextUtils.tooltip("inventoryio.top");
                break;
            case EAST:
                component = ElectroTextUtils.tooltip("inventoryio.left");
                break;
            case WEST:
                component = ElectroTextUtils.tooltip("inventoryio.right");
                break;
            case NORTH:
                component = ElectroTextUtils.tooltip("inventoryio.front");
                break;
            case SOUTH:
                component = ElectroTextUtils.tooltip("inventoryio.back");
                break;
        }
//		return component.append(": " + StringUtils.capitalize(side.name().toLowerCase()));
        // TODO: Add some dynamic face direction here for the slots.
        return component;
    }

    private MutableComponent getModeForSide() {

        GenericScreen<?> screen = (GenericScreen<?>) gui;

        ContainerQuantumTunnel container = (ContainerQuantumTunnel) screen.getMenu();

        TileQuantumTunnel tile = container.getSafeHost();

        if (tile == null) {
            return Component.empty();
        }

        boolean in = tile.readInputDirections().contains(side);
        boolean out = tile.readOutputDirections().contains(side);

        if(in) {
            return NuclearTextUtils.tooltip("quantumtunnel.input");
        } else if (out) {
            return NuclearTextUtils.tooltip("quantumtunnel.output");
        } else {
            return NuclearTextUtils.tooltip("quantumtunnel.none");
        }

    }


}
