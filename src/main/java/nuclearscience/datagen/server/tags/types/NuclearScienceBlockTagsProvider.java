package nuclearscience.datagen.server.tags.types;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import nuclearscience.NuclearScience;
import nuclearscience.common.block.subtype.SubtypeIrradiatedBlock;
import nuclearscience.common.block.subtype.SubtypeNuclearMachine;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.registers.NuclearScienceBlocks;
import voltaic.common.block.BlockMachine;

public class NuclearScienceBlockTagsProvider extends BlockTagsProvider {

    public NuclearScienceBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, NuclearScience.ID, existingFileHelper);
    }

    @Override
    protected void addTags(Provider provider) {

        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(NuclearScienceBlocks.BLOCKS_RADIATION_SHIELDING.getAllValuesArray(new Block[]{}))
                //
                .add(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getAllValuesArray(new BlockMachine[0]))
                //
                .add(NuclearScienceBlocks.BLOCKS_ELECTROMAGENT.getAllValuesArray(new Block[0]))
                //
                .add(
                        //
                        NuclearScienceBlocks.BLOCK_ELECTORMAGNETICBOOSTER.get(),
                        //
                        NuclearScienceBlocks.BLOCK_ELECTROMAGNETICSWITCH.get(),
                        //
                        NuclearScienceBlocks.BLOCK_MELTEDREACTOR.get(),
                        //
                        NuclearScienceBlocks.BLOCK_TURBINE.get());

        tag(BlockTags.NEEDS_STONE_TOOL).add(NuclearScienceBlocks.BLOCKS_RADIATION_SHIELDING.getAllValuesArray(new Block[]{}))
                //
                .add(NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getAllValuesArray(new BlockMachine[0]))
                //
                .add(NuclearScienceBlocks.BLOCKS_ELECTROMAGENT.getAllValuesArray(new Block[0]))
                //
                .add(
                        //
                        NuclearScienceBlocks.BLOCK_ELECTORMAGNETICBOOSTER.get(),
                        //
                        NuclearScienceBlocks.BLOCK_ELECTROMAGNETICSWITCH.get(),
                        //
                        NuclearScienceBlocks.BLOCK_MELTEDREACTOR.get(),
                        //
                        NuclearScienceBlocks.BLOCK_TURBINE.get());

        tag(BlockTags.MINEABLE_WITH_SHOVEL).add(NuclearScienceBlocks.BLOCKS_IRRADIATED.getValue(SubtypeIrradiatedBlock.soil), NuclearScienceBlocks.BLOCKS_IRRADIATED.getValue(SubtypeIrradiatedBlock.grass));

        tag(BlockTags.MINEABLE_WITH_AXE).add(NuclearScienceBlocks.BLOCKS_IRRADIATED.getValue(SubtypeIrradiatedBlock.petrifiedwood));

        tag(Tags.Blocks.NEEDS_WOOD_TOOL).add(NuclearScienceBlocks.BLOCKS_IRRADIATED.getValue(SubtypeIrradiatedBlock.soil), NuclearScienceBlocks.BLOCKS_IRRADIATED.getValue(SubtypeIrradiatedBlock.grass), NuclearScienceBlocks.BLOCKS_IRRADIATED.getValue(SubtypeIrradiatedBlock.petrifiedwood));

        tag(NuclearScienceTags.Blocks.PARTICLE_CONTAINMENT)
                //
                .add(NuclearScienceBlocks.BLOCKS_ELECTROMAGENT.getAllValuesArray(new Block[0]))
                //
                .add(
                        //
                        NuclearScienceBlocks.BLOCKS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.particleinjector),
                        //
                        NuclearScienceBlocks.BLOCK_ELECTORMAGNETICBOOSTER.get(),
                        //
                        NuclearScienceBlocks.BLOCK_ELECTROMAGNETICGATEWAY.get(),
                        //
                        NuclearScienceBlocks.BLOCK_ELECTROMAGNETICDIODE.get(),
                        //
                        NuclearScienceBlocks.BLOCK_ELECTROMAGNETICSWITCH.get()
                        //
                );

        tag(NuclearScienceTags.Blocks.FUSION_CONTAINMENT).add(NuclearScienceBlocks.BLOCKS_ELECTROMAGENT.getAllValuesArray(new Block[0]));

    }

}
