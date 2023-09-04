package nuclearscience.datagen.server.tags.types;

import java.util.concurrent.CompletableFuture;

import electrodynamics.api.References;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import nuclearscience.registers.NuclearScienceDamageTypes;

public class NuclearScienceDamageTagsProvider extends DamageTypeTagsProvider {

	public NuclearScienceDamageTagsProvider(PackOutput output, CompletableFuture<Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, References.ID, existingFileHelper);
	}
	
	@Override
	protected void addTags(Provider provider) {
		tag(DamageTypeTags.BYPASSES_ARMOR).add(NuclearScienceDamageTypes.PLASMA, NuclearScienceDamageTypes.RADIATION);
		tag(DamageTypeTags.BYPASSES_EFFECTS).add(NuclearScienceDamageTypes.PLASMA, NuclearScienceDamageTypes.RADIATION); //bypasses magic
	}

}
