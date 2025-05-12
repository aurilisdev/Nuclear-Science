package nuclearscience.datagen.server;

import net.minecraft.block.Block;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import nuclearscience.NuclearScience;
import nuclearscience.common.block.subtype.SubtypeNuclearMachine;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.registers.NuclearScienceBlocks;

public class NuclearScienceBlockTagsProvider extends BlockTagsProvider {

	public NuclearScienceBlockTagsProvider(DataGenerator pGenerator, ExistingFileHelper existingFileHelper) {
		super(pGenerator, NuclearScience.ID, existingFileHelper);
	}

	@Override
	protected void addTags() {

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
