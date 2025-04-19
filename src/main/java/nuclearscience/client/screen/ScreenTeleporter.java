package nuclearscience.client.screen;

import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import nuclearscience.common.inventory.container.ContainerTeleporter;
import nuclearscience.common.settings.NuclearConstants;
import nuclearscience.common.tile.TileTeleporter;
import nuclearscience.prefab.utils.NuclearTextUtils;
import voltaic.prefab.screen.GenericScreen;
import voltaic.prefab.screen.component.button.ScreenComponentButton;
import voltaic.prefab.screen.component.editbox.ScreenComponentEditBox;
import voltaic.prefab.screen.component.types.ScreenComponentSimpleLabel;
import voltaic.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import voltaic.prefab.screen.component.utils.AbstractScreenComponentInfo;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentInventory;
import voltaic.prefab.utilities.math.Color;
import voltaic.registers.VoltaicDataComponentTypes;

public class ScreenTeleporter extends GenericScreen<ContainerTeleporter> {

    private final ScreenComponentEditBox xBox;
    private final ScreenComponentEditBox yBox;
    private final ScreenComponentEditBox zBox;


    private boolean needsUpdate = true;

    public ScreenTeleporter(ContainerTeleporter container, Inventory inv, Component title) {
        super(container, inv, title);
        imageHeight += 50;
        inventoryLabelY += 50;

        addComponent(new ScreenComponentSimpleLabel(30, 20, 10, Color.TEXT_GRAY, () -> {
            TileTeleporter tile = getMenu().getSafeHost();
            if(tile == null) {
                return Component.empty();
            }

            ResourceKey<Level> dimension = ResourceKey.create(Registries.DIMENSION, tile.dimension.getValue());

            if(ElectroTextUtils.dimensionExists(dimension)) {
                return ElectroTextUtils.dimension(dimension);
            } else {
                return Component.literal(dimension.location().getPath());
            }
        }));

        addComponent(new ScreenComponentSimpleLabel(30, 33, 10, Color.TEXT_GRAY, NuclearTextUtils.gui("teleporter.x")));
        addEditBox(xBox = new ScreenComponentEditBox(40, 30, 60, 13, getFontRenderer()).setTextColor(Color.WHITE).setTextColorUneditable(Color.WHITE).setMaxLength(9).setResponder(this::updateX).setFilter(ScreenComponentEditBox.INTEGER));
        addComponent(new ScreenComponentSimpleLabel(30, 49, 10, Color.TEXT_GRAY, NuclearTextUtils.gui("teleporter.y")));
        addEditBox(yBox = new ScreenComponentEditBox(40, 46, 60, 13, getFontRenderer()).setTextColor(Color.WHITE).setTextColorUneditable(Color.WHITE).setMaxLength(9).setResponder(this::updateY).setFilter(ScreenComponentEditBox.INTEGER));
        addComponent(new ScreenComponentSimpleLabel(30, 65, 10, Color.TEXT_GRAY, NuclearTextUtils.gui("teleporter.z")));
        addEditBox(zBox = new ScreenComponentEditBox(40, 62, 60, 13, getFontRenderer()).setTextColor(Color.WHITE).setTextColorUneditable(Color.WHITE).setMaxLength(9).setResponder(this::updateZ).setFilter(ScreenComponentEditBox.INTEGER));

        addComponent(new ScreenComponentButton<>(50, 78, 100, 20).setOnPress(button -> {

            TileTeleporter tile = container.getSafeHost();
            if(tile == null) {
                return;
            }

            ItemStack input = tile.<ComponentInventory>getComponent(IComponentType.Inventory).getItem(0);

            if(input.isEmpty() || !input.has(VoltaicDataComponentTypes.BLOCK_POS)) {
                return;
            }

            tile.destination.setValue(input.get(VoltaicDataComponentTypes.BLOCK_POS));

            if(input.has(VoltaicDataComponentTypes.RESOURCE_LOCATION)) {
                tile.dimension.setValue(input.get(VoltaicDataComponentTypes.RESOURCE_LOCATION));
            }


        }).setLabel(NuclearTextUtils.gui("teleporter.import")));

        addComponent(new ScreenComponentButton<>(30, 100, 120, 20).setOnPress(button -> {

            TileTeleporter tile = container.getSafeHost();
            if(tile == null) {
                return;
            }

            tile.destination.setValue(tile.getBlockPos());
            tile.dimension.setValue(getMinecraft().level.OVERWORLD.location());

            xBox.setValue("" + menu.getSafeHost().destination.getValue().getX());
            yBox.setValue("" + menu.getSafeHost().destination.getValue().getY());
            zBox.setValue("" + menu.getSafeHost().destination.getValue().getZ());


        }).setLabel(NuclearTextUtils.gui("teleporter.reset")));

        addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2).wattage(NuclearConstants.TELEPORTER_USAGE_PER_TELEPORT / 20));

        xBox.setFocus(false);
        yBox.setFocus(false);
        zBox.setFocus(false);

    }

    private void updateX(String val) {
        xBox.setFocus(true);
        yBox.setFocus(false);
        zBox.setFocus(false);
        handleX(val);
    }

    private void updateY(String val) {
        xBox.setFocus(false);
        yBox.setFocus(true);
        zBox.setFocus(false);
        handleY(val);
    }

    private void updateZ(String val) {
        xBox.setFocus(false);
        yBox.setFocus(false);
        zBox.setFocus(true);
        handleZ(val);
    }

    private void handleX(String freq) {
        if (freq.isEmpty()) {
            return;
        }

        Integer x = 0;

        try {
            x = Integer.parseInt(xBox.getValue());
        } catch (Exception e) {
            // Not required
        }

        TileTeleporter tile = menu.getSafeHost();

        if (tile == null) {
            return;
        }

        BlockPos dest = tile.destination.getValue();

        tile.destination.setValue(new BlockPos(x, dest.getY(), dest.getZ()));

    }

    private void handleY(String out) {

        if (out.isEmpty()) {
            return;
        }

        Integer y = 0;
        try {
            y = Integer.parseInt(yBox.getValue());
        } catch (Exception e) {
            // Not required
        }

        TileTeleporter tile = menu.getSafeHost();

        if (tile == null) {
            return;
        }

        BlockPos dest = tile.destination.getValue();

        tile.destination.setValue(new BlockPos(dest.getX(), y, dest.getZ()));

    }

    private void handleZ(String out) {

        if (out.isEmpty()) {
            return;
        }

        Integer z = 0;
        try {
            z = Integer.parseInt(zBox.getValue());
        } catch (Exception e) {
            // Not required
        }

        TileTeleporter tile = menu.getSafeHost();

        if (tile == null) {
            return;
        }

        BlockPos dest = tile.destination.getValue();

        tile.destination.setValue(new BlockPos(dest.getX(), dest.getY(), z));

    }


    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        super.render(graphics, mouseX, mouseY, partialTicks);
        if (needsUpdate && menu.getSafeHost() != null) {
            needsUpdate = false;
            xBox.setValue("" + menu.getSafeHost().destination.getValue().getX());
            yBox.setValue("" + menu.getSafeHost().destination.getValue().getY());
            zBox.setValue("" + menu.getSafeHost().destination.getValue().getZ());
        }
    }
}
