package nuclearscience.client.screen;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.ElectricUnit;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.ScreenComponentInfo;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import nuclearscience.common.inventory.container.ContainerFreezePlug;
import nuclearscience.common.settings.Constants;

@OnlyIn(Dist.CLIENT)
public class ScreenFreezePlug extends GenericScreen<ContainerFreezePlug> {

    public ScreenFreezePlug(ContainerFreezePlug container, PlayerInventory playerInventory, ITextComponent title) {
	super(container, playerInventory, title);
	components.add(new ScreenComponentElectricInfo(this::getEnergyInformation, this, -ScreenComponentInfo.SIZE + 1, 2));
    }

    private List<? extends ITextProperties> getEnergyInformation() {
	ArrayList<ITextProperties> list = new ArrayList<>();
	GenericTile box = container.getHostFromIntArray();
	if (box != null) {
	    ComponentElectrodynamic electro = box.getComponent(ComponentType.Electrodynamic);
	    list.add(new TranslationTextComponent("gui.freezeplug.usage",
		    new StringTextComponent(ChatFormatter.getElectricDisplayShort(Constants.FREEZEPLUG_USAGE_PER_TICK * 20, ElectricUnit.WATT))
			    .mergeStyle(TextFormatting.GRAY)).mergeStyle(TextFormatting.DARK_GRAY));
	    list.add(new TranslationTextComponent("gui.freezeplug.voltage",
		    new StringTextComponent(ChatFormatter.getElectricDisplayShort(electro.getVoltage(), ElectricUnit.VOLTAGE))
			    .mergeStyle(TextFormatting.GRAY)).mergeStyle(TextFormatting.DARK_GRAY));
	}
	return list;
    }
}