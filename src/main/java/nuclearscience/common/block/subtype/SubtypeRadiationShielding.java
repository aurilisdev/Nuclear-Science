package nuclearscience.common.block.subtype;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import voltaic.api.ISubtype;

public enum SubtypeRadiationShielding implements ISubtype {
    base(Blocks.NETHERITE_BLOCK.properties().strength(5.0f, 3.0f).sound(SoundType.METAL).requiresCorrectToolForDrops()),
    glass(Blocks.GRASS_BLOCK.properties().strength(5.0f, 3.0f).requiresCorrectToolForDrops()),
    door(Blocks.NETHERITE_BLOCK.properties().strength(5.0f, 3.0f).sound(SoundType.METAL).requiresCorrectToolForDrops()),
    trapdoor(Blocks.NETHERITE_BLOCK.properties().strength(5.0f, 3.0f).sound(SoundType.METAL).requiresCorrectToolForDrops().noOcclusion());

    public final BlockBehaviour.Properties properties;

    private SubtypeRadiationShielding(BlockBehaviour.Properties properties) {
        this.properties = properties;
    }

    @Override
    public String tag() {
        return "radiationshielding" + name();
    }

    @Override
    public String forgeTag() {
        return "radiationshielding/" + name();
    }

    @Override
    public boolean isItem() {
        return false;
    }
}
