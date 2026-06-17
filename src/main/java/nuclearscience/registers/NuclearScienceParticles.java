package nuclearscience.registers;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import nuclearscience.NuclearScience;
import nuclearscience.client.particle.smoke.ParticleOptionSmoke;

public class NuclearScienceParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(Registries.PARTICLE_TYPE,
	    NuclearScience.ID);

    public static final DeferredHolder<ParticleType<?>, ParticleOptionSmoke> PARTICLE_SMOKE = PARTICLES
	    .register("smoke", ParticleOptionSmoke::new);

}
