package nuclearscience.common.block.electromagneticbooster;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import net.minecraft.state.EnumProperty;

public class FacingDirectionProperty extends EnumProperty<FacingDirection> {
	protected FacingDirectionProperty(String name, Collection<FacingDirection> values) {
		super(name, FacingDirection.class, values);
	}

	public static FacingDirectionProperty create(String name, Predicate<FacingDirection> filter) {
		return create(name, Arrays.stream(FacingDirection.values()).filter(filter).collect(Collectors.toList()));
	}

	public static FacingDirectionProperty create(String p_196962_0_, FacingDirection... p_196962_1_) {
		return create(p_196962_0_, Lists.newArrayList(p_196962_1_));
	}

	public static FacingDirectionProperty create(String name, Collection<FacingDirection> values) {
		return new FacingDirectionProperty(name, values);
	}
}
