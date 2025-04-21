package nuclearscience.common.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import nuclearscience.registers.NuclearScienceSounds;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.api.sound.SoundAPI;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.type.ComponentTickable;

public class TileSiren extends GenericTile {

	public TileSiren(BlockPos worldPos, BlockState blockState) {
		super(NuclearScienceTiles.TILE_SIREN.get(), worldPos, blockState);
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
				SoundAPI.playSound(NuclearScienceSounds.SOUND_SIREN.get(), SoundSource.BLOCKS, volume, 1, worldPosition);
			}
		}
	}

	@Override
	public InteractionResult useWithoutItem(Player player, BlockHitResult hit) {
		return InteractionResult.PASS;
	}

	@Override
	public ItemInteractionResult useWithItem(ItemStack used, Player player, InteractionHand hand, BlockHitResult hit) {
		return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	}


}
