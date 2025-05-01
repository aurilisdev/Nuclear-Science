package nuclearscience.common.block.states.facing;

import java.util.Locale;

import net.minecraft.util.StringRepresentable;

public enum FacingDirection implements StringRepresentable {
	NONE,
	LEFT,
	RIGHT;

	@Override
	public String getSerializedName() {
		return name().toLowerCase(Locale.ROOT);
	}
}
