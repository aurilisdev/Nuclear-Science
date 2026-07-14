package nuclearscience.client.particle.smoke;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager.IParticleMetaFactory;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;

public class ParticleSmoke extends SpriteTexturedParticle {
    private final IAnimatedSprite sprites;

    public ParticleSmoke(ClientWorld level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, ParticleOptionSmoke options, IAnimatedSprite set) {
        super(level, x, y, z, 0.0, 0.0, 0.0);
        this.gravity = options.gravity;
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
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public float getQuadSize(float scaleFactor) {
        return this.quadSize * MathHelper.clamp((this.age + scaleFactor) / this.lifetime * 32.0F, 0.0F, 1.0F);
    }

    @Override
    public void tick() {
        super.tick();
        setSprite(sprites.get(level.getRandom()));
    }

    public static class Factory implements IParticleFactory<ParticleOptionSmoke>, IParticleMetaFactory<ParticleOptionSmoke> {

        private final IAnimatedSprite sprites;

        public Factory(IAnimatedSprite sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(ParticleOptionSmoke type, ClientWorld level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new ParticleSmoke(level, x, y, z, xSpeed, ySpeed, zSpeed, type, sprites);
        }

        @Override
        public IParticleFactory<ParticleOptionSmoke> create(IAnimatedSprite sprites) {
            return new ParticleSmoke.Factory(sprites);
        }

    }
}


