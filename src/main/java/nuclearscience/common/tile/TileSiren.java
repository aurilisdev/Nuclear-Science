package nuclearscience.common.tile;

import electrodynamics.api.sound.SoundAPI;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.DeferredRegisters;
import nuclearscience.SoundRegister;

public class TileSiren extends GenericTile {

	public TileSiren(BlockPos worldPos, BlockState blockState) {
		super(DeferredRegisters.TILE_SIREN.get(), worldPos, blockState);
		addComponent(new ComponentTickable().tickClient(this::tickClient));
	}

	public void tickClient(ComponentTickable tick) {
		if (tick.getTicks() % 30 == 0) {
			if (level.getDirectSignalTo(worldPosition) > 0) {
				int volume = 2;
				for (Direction dir : Direction.values()) {
					if (level.getBlockEntity(worldPosition.offset(dir.getNormal())) instanceof TileSiren) {
						volume += 2;
					}
				}
				SoundAPI.playSound(SoundRegister.SOUND_SIREN.get(), SoundSource.BLOCKS, volume, 1, worldPosition);
			}
		}
	}

}
