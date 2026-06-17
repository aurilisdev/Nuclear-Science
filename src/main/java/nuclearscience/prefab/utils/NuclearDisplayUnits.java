package nuclearscience.prefab.utils;

import net.minecraft.network.chat.Component;
import voltaic.api.electricity.formatting.IDisplayUnit;

public class NuclearDisplayUnits {

    public static final IDisplayUnit SPEEDOFLIGHT = new IDisplayUnit() {
	@Override
	public Component getSymbol() {
	    return NuclearTextUtils.gui("displayunit.speedoflightsymbol");
	}

	@Override
	public Component getName() {
	    return NuclearTextUtils.gui("displayunit.speedoflightname");
	}

	@Override
	public Component getNamePlural() {
	    return NuclearTextUtils.gui("displayunit.speedoflightnameplural");
	}

	@Override
	public Component getDistanceFromValue() {
	    return Component.literal(" ");
	}
    };

}
