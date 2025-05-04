package nuclearscience.common.block.states;

import nuclearscience.common.block.states.facing.FacingDirection;
import nuclearscience.common.block.states.facing.FacingDirectionProperty;

public class NuclearScienceBlockStates {

    public static void init() {

    }

    public static final FacingDirectionProperty FACINGDIRECTION = FacingDirectionProperty.create("side", FacingDirection.values());

}
