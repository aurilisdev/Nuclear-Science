package nuclearscience.common.tile;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockRayTraceResult;
import nuclearscience.registers.NuclearScienceSounds;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.api.sound.SoundAPI;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.type.ComponentTickable;

public class TileSiren extends GenericTile {

	public TileSiren() {
		super(NuclearScienceTiles.TILE_SIREN.get());
		addComponent(new ComponentTickable(this).tickClient(this::tickClient));
	}

	public void tickClient(ComponentTickable tick) {
		if (tick.getTicks() % 30 == 0) {
			if (isPoweredByRedstone()) {
				int volume = 2;
				for (Direction dir : Direction.values()) {
					if (level.getBlockEntity(worldPosition.offset(dir.getNormal())) instanceof TileSiren) {
						volume += 2;
					}
				}
				SoundAPI.playSound(NuclearScienceSounds.SOUND_SIREN.get(), SoundCategory.BLOCKS, volume, 1, worldPosition);
			}
		}
	}
	
	@Override
	public ActionResultType use(PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		return ActionResultType.PASS;
	}


}
