package nuclearscience.common.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.world.chunk.RegisterTicketControllersEvent;
import net.neoforged.neoforge.common.world.chunk.TicketController;
import nuclearscience.NuclearScience;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.Voltaic;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.type.ComponentPacketHandler;
import voltaic.prefab.tile.components.type.ComponentTickable;

public class TileChunkloader extends GenericTile {
    public TileChunkloader(BlockPos worldPos, BlockState blockState) {
	super(NuclearScienceTiles.TILE_CHUNKLOADER.get(), worldPos, blockState);
	addComponent(new ComponentPacketHandler(this));
	addComponent(new ComponentTickable(this).tickServer(this::tickServer));
    }

    public void tickServer(ComponentTickable tickable) {
	// TODO if we want to make it use power or something
    }

    private static void updateChunks(boolean load, Level world, BlockPos pos) {

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
		ChunkloaderManager.TICKET_CONTROLLER.forceChunk((ServerLevel) world, ownerPos[i][j], lowerXOffset + i,
			lowerZOffset + j, load, true);
		String action = load ? "loading" : "unloading";
		Voltaic.LOGGER.info(action + " chunk at " + lowerXOffset + i + "," + lowerZOffset + j);
	    }
	}

    }

    @Override
    public void onBlockDestroyed() {
	super.onBlockDestroyed();
	if (!level.isClientSide()) {
	    updateChunks(false, getLevel(), getBlockPos());
	}
    }

    @Override
    public void onPlace(BlockState oldState, boolean isMoving) {
	super.onPlace(oldState, isMoving);
	if (!level.isClientSide()) {
	    updateChunks(true, getLevel(), getBlockPos());
	}
    }

    @EventBusSubscriber(modid = NuclearScience.ID, bus = EventBusSubscriber.Bus.MOD)
    private static final class ChunkloaderManager {

	private static final TicketController TICKET_CONTROLLER = new TicketController(
		NuclearScience.rl("chunkloadercontroller"));

	@SubscribeEvent
	public static void register(RegisterTicketControllersEvent event) {
	    event.register(TICKET_CONTROLLER);
	}

    }
}
