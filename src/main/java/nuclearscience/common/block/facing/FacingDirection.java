package nuclearscience.common.block.facing;

import java.util.Locale;

import net.minecraft.util.IStringSerializable;

public enum FacingDirection implements IStringSerializable {
	NONE,
	LEFT,
	RIGHT;

	@Override
	public String getSerializedName() {
		return name().toLowerCase(Locale.ROOT);
	}
}
