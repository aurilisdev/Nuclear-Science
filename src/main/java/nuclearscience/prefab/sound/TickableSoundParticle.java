package nuclearscience.prefab.sound;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import nuclearscience.common.entity.EntityParticle;
import voltaic.prefab.utilities.WorldUtils;

public class TickableSoundParticle extends AbstractTickableSoundInstance {

    private static final float MIN_PITCH = 0.5F;
    private static final float MAXIMUM_DISTANCE = 100.0F;

    private final EntityParticle particle;
    private final float initialVolume;

    public TickableSoundParticle(SoundEvent event, SoundSource source, EntityParticle particle) {
	super(event, source, SoundInstance.createUnseededRandom());
	this.particle = particle;
	this.x = particle.getX();
	this.y = particle.getY();
	this.z = particle.getZ();
	initialVolume = 1.0F;
	this.volume = 1.0F;
	this.pitch = MIN_PITCH;
	looping = true;
	delay = 0;
	relative = true;
    }

    @Override
    public void tick() {
	float ratio = Math.abs(particle.speed / EntityParticle.MAX_SPEED) / EntityParticle.MAX_SPEED;

	pitch = MIN_PITCH + MIN_PITCH * ratio;

	this.x = particle.getX();
	this.y = particle.getY();
	this.z = particle.getZ();

	Player player = Minecraft.getInstance().player;

	float distance = (float) WorldUtils.distanceBetweenPositions(player.blockPosition(),
		new BlockPos((int) x, (int) y, (int) z));

	if (distance > 0 && distance <= MAXIMUM_DISTANCE) {

	    float distRatio = distance / MAXIMUM_DISTANCE;

	    volume = (1.0F - distRatio) * initialVolume;

	} else if (distance > MAXIMUM_DISTANCE) {

	    volume = 0;

	} else {

	    volume = initialVolume;

	}

    }

    @Override
    public boolean isStopped() {
	return particle == null || !particle.isAlive() || particle.isRemoved();
    }
}
