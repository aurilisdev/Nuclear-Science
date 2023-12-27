package nuclearscience.datagen.server;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import nuclearscience.References;

public class NuclearScienceBlockTagsProvider extends BlockTagsProvider {

	public NuclearScienceBlockTagsProvider(DataGenerator pGenerator, ExistingFileHelper existingFileHelper) {
		super(pGenerator, References.ID, existingFileHelper);
	}

	@Override
	protected void addTags() {

	}

}
