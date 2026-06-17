package nuclearscience.registers;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import nuclearscience.NuclearScience;
import nuclearscience.common.entity.EntityParticle;

public class NuclearScienceEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE,
	    NuclearScience.ID);

    public static final DeferredHolder<EntityType<?>, EntityType<EntityParticle>> ENTITY_PARTICLE = ENTITIES
	    .register("particle", () -> EntityType.Builder.<EntityParticle>of(EntityParticle::new, MobCategory.MISC)
		    .clientTrackingRange(8).build(NuclearScience.ID + ".particle"));

}
