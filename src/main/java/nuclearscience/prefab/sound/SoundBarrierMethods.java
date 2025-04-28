package nuclearscience.prefab.sound;

import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundSource;
import nuclearscience.common.entity.EntityParticle;
import nuclearscience.registers.NuclearScienceSounds;

public class SoundBarrierMethods {

    public static void playParticleSound(EntityParticle particle) {
        Minecraft.getInstance().getSoundManager().play(new TickableSoundParticle(NuclearScienceSounds.SOUND_PARTICLE.get(), SoundSource.NEUTRAL, particle));
    }

}
