package nuclearscience.datagen.server.tags.types;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import nuclearscience.NuclearScience;
import nuclearscience.registers.NuclearScienceDamageTypes;

public class NuclearScienceDamageTagsProvider extends DamageTypeTagsProvider {

    public NuclearScienceDamageTagsProvider(PackOutput output, CompletableFuture<Provider> lookupProvider,
	    ExistingFileHelper existingFileHelper) {
	super(output, lookupProvider, NuclearScience.ID, existingFileHelper);
    }

    @Override
    protected void addTags(Provider provider) {
	tag(DamageTypeTags.BYPASSES_ARMOR).add(NuclearScienceDamageTypes.PLASMA);
	tag(DamageTypeTags.BYPASSES_EFFECTS).add(NuclearScienceDamageTypes.PLASMA); // bypasses magic
	tag(DamageTypeTags.NO_KNOCKBACK).add(NuclearScienceDamageTypes.PLASMA);
    }

}
