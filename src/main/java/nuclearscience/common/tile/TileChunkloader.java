package nuclearscience.common.tile;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.world.ForgeChunkManager;
import nuclearscience.NuclearScience;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.Voltaic;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.type.ComponentPacketHandler;
import voltaic.prefab.tile.components.type.ComponentTickable;

public class TileChunkloader extends GenericTile {
    public TileChunkloader() {
        super(NuclearScienceTiles.TILE_CHUNKLOADER.get());
        addComponent(new ComponentPacketHandler(this));
        addComponent(new ComponentTickable(this).tickServer(this::tickServer));
    }

    public void tickServer(ComponentTickable tickable) {
        //TODO if we want to make it use power or something
    }

    private void updateChunks(boolean load, World world, BlockPos pos) {

        int offset = 1;
        ChunkPos currChunkPos = world.getChunk(pos).getPos();
        int lowerXOffset = currChunkPos.x - offset;
        int lowerZOffset = currChunkPos.z - offset;

        int delta = currChunkPos.x + offset - lowerXOffset;

        BlockPos[][] ownerPos = new BlockPos[delta + 1][delta + 1];

        BlockPos bottomLeft = pos.offset(-16 * offset, 0, -16 * offset);

        for (int i = 0; i <= delta; i++) {
            for (int j = 0; j <= delta; j++) {
                ownerPos[i][j] = bottomLeft.offset(16 * i, 0, 16 * j);
            }
        }

        for (int i = 0; i <= delta; i++) {
            for (int j = 0; j <= delta; j++) {
                ForgeChunkManager.forceChunk((ServerWorld) world, NuclearScience.ID, ownerPos[i][j], lowerXOffset + i, lowerZOffset + j, load, true);
                String action = load ? "loading" : "unloading";
                Voltaic.LOGGER.info(action + " chunk at " + lowerXOffset + i + "," + lowerZOffset + j);
            }
        }

    }

    @Override
    public void onBlockDestroyed() {
        super.onBlockDestroyed();
        if(!level.isClientSide()) {
            updateChunks(false, getLevel(), getBlockPos());
        }
    }

    @Override
    public void onPlace(BlockState oldState, boolean isMoving) {
        super.onPlace(oldState, isMoving);
        if(!level.isClientSide()) {
            updateChunks(true, getLevel(), getBlockPos());
        }
    }
}
