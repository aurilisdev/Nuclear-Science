package nuclearscience.common.block.states.facing;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import net.minecraft.world.level.block.state.properties.EnumProperty;

public class FacingDirectionProperty extends EnumProperty<FacingDirection> {

	protected FacingDirectionProperty(String name, Collection<FacingDirection> values) {
		super(name, FacingDirection.class, values);
	}

	public static FacingDirectionProperty create(String name, Predicate<FacingDirection> filter) {
		return create(name, Arrays.stream(FacingDirection.values()).filter(filter).collect(Collectors.toList()));
	}

	public static FacingDirectionProperty create(String name, FacingDirection... directions) {
		return create(name, Lists.newArrayList(directions));
	}

	public static FacingDirectionProperty create(String name, Collection<FacingDirection> values) {
		return new FacingDirectionProperty(name, values);
	}
}
