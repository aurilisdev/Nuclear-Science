package nuclearscience.client.particle.smoke;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.registries.ForgeRegistries;
import nuclearscience.registers.NuclearScienceParticles;
import voltaic.api.codec.StreamCodec;

public class ParticleOptionSmoke extends ParticleType<ParticleOptionSmoke> implements ParticleOptions {

    public static final Codec<ParticleOptionSmoke> CODEC = RecordCodecBuilder.create(instance -> instance
	    .group(Codec.FLOAT.fieldOf("r").forGetter(instance0 -> instance0.r),
		    Codec.FLOAT.fieldOf("g").forGetter(instance0 -> instance0.g),
		    Codec.FLOAT.fieldOf("b").forGetter(instance0 -> instance0.b),
		    Codec.FLOAT.fieldOf("scale").forGetter(instance0 -> instance0.scale),
		    Codec.FLOAT.fieldOf("gravity").forGetter(instance0 -> instance0.gravity),
		    Codec.INT.fieldOf("lifetime").forGetter(instance0 -> instance0.lifetime),
		    Codec.BOOL.fieldOf("physics").forGetter(instance0 -> instance0.hasPhysics))
	    .apply(instance, (r, g, b, scale, gravity, lifetime, physics) -> new ParticleOptionSmoke().setParameters(r,
		    g, b, scale, gravity, lifetime, physics)));

    public static final StreamCodec<ByteBuf, ParticleOptionSmoke> STREAM_CODEC = new StreamCodec<>() {

	@Override
	public void encode(ByteBuf buffer, ParticleOptionSmoke particle) {
	    StreamCodec.FLOAT.encode(buffer, particle.r);
	    StreamCodec.FLOAT.encode(buffer, particle.g);
	    StreamCodec.FLOAT.encode(buffer, particle.b);
	    StreamCodec.FLOAT.encode(buffer, particle.scale);
	    StreamCodec.FLOAT.encode(buffer, particle.gravity);
	    StreamCodec.INT.encode(buffer, particle.lifetime);
	    StreamCodec.BOOL.encode(buffer, particle.hasPhysics);
	}

	@Override
	public ParticleOptionSmoke decode(ByteBuf buffer) {
	    return new ParticleOptionSmoke().setParameters(StreamCodec.FLOAT.decode(buffer),
		    StreamCodec.FLOAT.decode(buffer), StreamCodec.FLOAT.decode(buffer),
		    StreamCodec.FLOAT.decode(buffer), StreamCodec.FLOAT.decode(buffer), StreamCodec.INT.decode(buffer),
		    StreamCodec.BOOL.decode(buffer));
	}
    };

    public static final ParticleOptions.Deserializer<ParticleOptionSmoke> DESERIALIZER = new ParticleOptions.Deserializer<>() {

	@Override
	public ParticleOptionSmoke fromCommand(ParticleType<ParticleOptionSmoke> pParticleType, StringReader reader)
		throws CommandSyntaxException {
	    ParticleOptionSmoke particle = new ParticleOptionSmoke();

	    reader.expect(' ');
	    float r = reader.readFloat();

	    reader.expect(' ');
	    float g = reader.readFloat();

	    reader.expect(' ');
	    float b = reader.readFloat();

	    reader.expect(' ');
	    float scale = reader.readFloat();

	    reader.expect(' ');
	    float gravity = reader.readFloat();

	    reader.expect(' ');
	    int lifetime = reader.readInt();

	    reader.expect(' ');
	    boolean physics = reader.readBoolean();

	    return particle.setParameters(r, g, b, scale, gravity, lifetime, physics);
	}

	@Override
	public ParticleOptionSmoke fromNetwork(ParticleType<ParticleOptionSmoke> pParticleType,
		FriendlyByteBuf pBuffer) {
	    return STREAM_CODEC.decode(pBuffer);
	}
    };

    public float r;
    public float g;
    public float b;
    public float scale;
    public float gravity;
    public int lifetime;
    public boolean hasPhysics;

    public ParticleOptionSmoke() {
	super(false, DESERIALIZER);
    }

    public ParticleOptionSmoke setParameters(float r, float g, float b, float scale, float gravity, int lifetime,
	    boolean physics) {
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
    public void writeToNetwork(FriendlyByteBuf pBuffer) {
	STREAM_CODEC.encode(pBuffer, this);
    }

    @Override
    public String writeToString() {
	return ForgeRegistries.PARTICLE_TYPES.getKey(getType()).toString() + ", r: " + r + ", g: " + g + ", b: " + b
		+ ", scale: " + scale + ", gravity: " + gravity + ", lifetime: " + lifetime + ", physics: "
		+ hasPhysics;
    }

    @Override
    public Codec<ParticleOptionSmoke> codec() {
	return CODEC;
    }
}
