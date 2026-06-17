package nuclearscience.common.block.subtype;

import voltaic.api.ISubtype;

public enum SubtypeElectromagent implements ISubtype {
    electromagnet(1.0), electromagneticglass(1.0);

    public final double fusionBonus;

    private SubtypeElectromagent(double fusionBonus) {
	this.fusionBonus = fusionBonus;
    }

    @Override
    public String tag() {
	return name();
    }

    @Override
    public String forgeTag() {
	return "electromagnet/" + name();
    }

    @Override
    public boolean isItem() {
	return false;
    }
}
