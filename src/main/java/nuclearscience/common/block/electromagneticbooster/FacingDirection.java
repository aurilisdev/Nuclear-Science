package nuclearscience.common.block.electromagneticbooster;

import net.minecraft.util.IStringSerializable;

public enum FacingDirection implements IStringSerializable {
	NONE, LEFT, RIGHT;

	@Override
	public String getString() {
		return name().toLowerCase();
	}
}
