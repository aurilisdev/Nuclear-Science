package nuclearscience.common.block.facing;

import net.minecraft.util.IStringSerializable;

public enum FacingDirection implements IStringSerializable {
    NONE, LEFT, RIGHT;

    @Override
    public String getString() {
	return name().toLowerCase();
    }
}
