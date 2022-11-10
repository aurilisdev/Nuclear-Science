package nuclearscience.registers;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import nuclearscience.References;
import nuclearscience.common.entity.EntityParticle;

public class NuclearScienceEntities {
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, References.ID);

	public static final RegistryObject<EntityType<EntityParticle>> ENTITY_PARTICLE = ENTITIES.register("particle", () -> EntityType.Builder.<EntityParticle>of(EntityParticle::new, MobCategory.MISC).clientTrackingRange(8).build(References.ID + ".particle"));

}
