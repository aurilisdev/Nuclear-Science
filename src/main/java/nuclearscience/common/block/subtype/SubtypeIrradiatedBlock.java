package nuclearscience.common.block.subtype;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import voltaic.api.ISubtype;

public enum SubtypeIrradiatedBlock implements ISubtype {
    soil(Properties.copy(Blocks.DIRT).randomTicks()), grass(Properties.copy(Blocks.GRASS_BLOCK).randomTicks()),
    petrifiedwood(Properties.copy(Blocks.OAK_WOOD).randomTicks(), true);

    public final BlockBehaviour.Properties properties;

    private boolean burnable = false;

    private SubtypeIrradiatedBlock(BlockBehaviour.Properties properties) {
	this.properties = properties;
    }

    private SubtypeIrradiatedBlock(BlockBehaviour.Properties properties, boolean burnable) {
	this(properties);
	this.burnable = burnable;
    }

    public boolean burnable() {
	return burnable;
    }

    @Override
    public String tag() {
	return "irradiatedblock" + name();
    }

    @Override
    public String forgeTag() {
	return "irradiatedblock/" + name();
    }

    @Override
    public boolean isItem() {
	return false;
    }
}
