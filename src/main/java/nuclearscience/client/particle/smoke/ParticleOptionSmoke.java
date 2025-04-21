package nuclearscience.client.particle.smoke;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import nuclearscience.registers.NuclearScienceParticles;
import voltaic.prefab.utilities.CodecUtils;

public class ParticleOptionSmoke extends ParticleType<ParticleOptionSmoke> implements ParticleOptions {

    public static final MapCodec<ParticleOptionSmoke> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.FLOAT.fieldOf("r").forGetter(instance0 -> instance0.r),
                    Codec.FLOAT.fieldOf("g").forGetter(instance0 -> instance0.g),
                    Codec.FLOAT.fieldOf("b").forGetter(instance0 -> instance0.b),
                    Codec.FLOAT.fieldOf("scale").forGetter(instance0 -> instance0.scale),
                    Codec.FLOAT.fieldOf("gravity").forGetter(instance0 -> instance0.gravity),
                    Codec.INT.fieldOf("lifetime").forGetter(instance0 -> instance0.lifetime),
                    Codec.BOOL.fieldOf("physics").forGetter(instance0 -> instance0.hasPhysics)
            ).apply(instance, (r, g, b, scale, gravity, lifetime, physics) -> new ParticleOptionSmoke().setParameters(r, g, b, scale, gravity, lifetime, physics)));

    public static final StreamCodec<RegistryFriendlyByteBuf, ParticleOptionSmoke> STREAM_CODEC = CodecUtils.composite(
            ByteBufCodecs.FLOAT, instance0 -> instance0.r,
            ByteBufCodecs.FLOAT, instance0 -> instance0.g,
            ByteBufCodecs.FLOAT, instance0 -> instance0.b,
            ByteBufCodecs.FLOAT, instance0 -> instance0.scale,
            ByteBufCodecs.FLOAT, instance0 -> instance0.gravity,
            ByteBufCodecs.INT,instance0 -> instance0.lifetime,
            ByteBufCodecs.BOOL,instance0 -> instance0.hasPhysics,
            (r, g, b, scale, gravity, lifetime, physics) -> new ParticleOptionSmoke().setParameters(r, g, b, scale, gravity, lifetime, physics)
    );

    public float r;
    public float g;
    public float b;
    public float scale;
    public float gravity;
    public int lifetime;
    public boolean hasPhysics;


    public ParticleOptionSmoke() {
        super(false);
    }

    public ParticleOptionSmoke setParameters(float r, float g, float b, float scale, float gravity, int lifetime, boolean physics) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.scale = scale;
        this.gravity = gravity;
        this.lifetime = lifetime;
        this.hasPhysics = physics;
        return this;
    }

    @Override
    public ParticleType<?> getType() {
        return NuclearScienceParticles.PARTICLE_SMOKE.get();
    }

    @Override
    public MapCodec<ParticleOptionSmoke> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<? super RegistryFriendlyByteBuf, ParticleOptionSmoke> streamCodec() {
        return STREAM_CODEC;
    }
}
