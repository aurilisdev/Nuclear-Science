package nuclearscience.common.block.facing;

import net.minecraft.util.StringRepresentable;

public enum FacingDirection implements StringRepresentable {
    NONE, LEFT, RIGHT;

    @Override
    public String getSerializedName() {
	return name().toLowerCase();
    }
}
