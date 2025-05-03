package nuclearscience.registers;

import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import nuclearscience.NuclearScience;
import nuclearscience.client.particle.smoke.ParticleOptionSmoke;

public class NuclearScienceParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, NuclearScience.ID);

    public static final RegistryObject<ParticleOptionSmoke> PARTICLE_SMOKE = PARTICLES.register("smoke", ParticleOptionSmoke::new);

}
