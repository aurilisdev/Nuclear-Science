package nuclearscience.client.particle.smoke;

import javax.annotation.Nullable;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.util.Mth;

public class ParticleSmoke extends TextureSheetParticle {
    private final SpriteSet sprites;

    public ParticleSmoke(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed,
	    ParticleOptionSmoke options, SpriteSet set) {
	super(level, x, y, z, 0.0, 0.0, 0.0);
	friction = 0.96F;
	gravity = options.gravity;
	speedUpWhenYMotionIsBlocked = true;
	sprites = set;
	xd = xSpeed;
	yd = ySpeed;
	zd = zSpeed;
	rCol = options.r;
	gCol = options.g;
	bCol = options.b;
	quadSize = options.scale;
	lifetime = options.lifetime;
	setSpriteFromAge(sprites);
	hasPhysics = options.hasPhysics;
    }

    @Override
    public ParticleRenderType getRenderType() {
	return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public float getQuadSize(float scaleFactor) {
	return quadSize * Mth.clamp((age + scaleFactor) / lifetime * 32.0F, 0.0F, 1.0F);
    }

    @Override
    public void tick() {
	super.tick();
	setSprite(sprites.get(level.getRandom()));
    }

    public static class Factory implements ParticleProvider<ParticleOptionSmoke>,
	    ParticleEngine.SpriteParticleRegistration<ParticleOptionSmoke> {

	private final SpriteSet sprites;

	public Factory(SpriteSet sprites) {
	    this.sprites = sprites;
	}

	@Override
	public @Nullable Particle createParticle(ParticleOptionSmoke type, ClientLevel level, double x, double y,
		double z, double xSpeed, double ySpeed, double zSpeed) {
	    return new ParticleSmoke(level, x, y, z, xSpeed, ySpeed, zSpeed, type, sprites);
	}

	@Override
	public ParticleProvider<ParticleOptionSmoke> create(SpriteSet sprites) {
	    return new ParticleSmoke.Factory(sprites);
	}

    }
}
