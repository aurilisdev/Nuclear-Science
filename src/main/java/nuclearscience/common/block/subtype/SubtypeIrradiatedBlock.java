package nuclearscience.common.block.subtype;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.Blocks;
import voltaic.api.ISubtype;

public enum SubtypeIrradiatedBlock implements ISubtype {
    soil(Properties.copy(Blocks.DIRT).randomTicks()),
    grass(Properties.copy(Blocks.GRASS_BLOCK).randomTicks()),
    petrifiedwood(Properties.copy(Blocks.OAK_WOOD).randomTicks());

    public final AbstractBlock.Properties properties;

    private SubtypeIrradiatedBlock(AbstractBlock.Properties properties) {
        this.properties = properties;
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
