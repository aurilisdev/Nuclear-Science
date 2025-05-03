package nuclearscience.client.particle.smoke;

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

    public ParticleSmoke(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, ParticleOptionSmoke options, SpriteSet set) {
        super(level, x, y, z, 0.0, 0.0, 0.0);
        this.friction = 0.96F;
        this.gravity = options.gravity;
        this.speedUpWhenYMotionIsBlocked = true;
        this.sprites = set;
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;
        this.rCol = options.r;
        this.gCol = options.g;
        this.bCol = options.b;
        this.quadSize = options.scale;
        this.lifetime = options.lifetime;
        this.setSpriteFromAge(sprites);
        this.hasPhysics = options.hasPhysics;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public float getQuadSize(float scaleFactor) {
        return this.quadSize * Mth.clamp((this.age + scaleFactor) / this.lifetime * 32.0F, 0.0F, 1.0F);
    }

    @Override
    public void tick() {
        super.tick();
        setSprite(sprites.get(level.getRandom()));
    }

    public static class Factory implements ParticleProvider<ParticleOptionSmoke>, ParticleEngine.SpriteParticleRegistration<ParticleOptionSmoke> {

        private final SpriteSet sprites;

        public Factory(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(ParticleOptionSmoke type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new ParticleSmoke(level, x, y, z, xSpeed, ySpeed, zSpeed, type, sprites);
        }

        @Override
        public ParticleProvider<ParticleOptionSmoke> create(SpriteSet sprites) {
            return new ParticleSmoke.Factory(sprites);
        }

    }
}


