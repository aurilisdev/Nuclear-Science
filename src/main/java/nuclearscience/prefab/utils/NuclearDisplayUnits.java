package nuclearscience.prefab.utils;

import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import voltaic.api.electricity.formatting.IDisplayUnit;

public class NuclearDisplayUnits {

    public static final IDisplayUnit SPEEDOFLIGHT = new IDisplayUnit() {
        @Override
        public IFormattableTextComponent getSymbol() {
            return NuclearTextUtils.gui("displayunit.speedoflightsymbol");
        }

        @Override
        public IFormattableTextComponent getName() {
            return NuclearTextUtils.gui("displayunit.speedoflightname");
        }

        @Override
        public IFormattableTextComponent getNamePlural() {
            return NuclearTextUtils.gui("displayunit.speedoflightnameplural");
        }

        @Override
        public IFormattableTextComponent getDistanceFromValue() {
            return new StringTextComponent(" ");
        }
    };

}
