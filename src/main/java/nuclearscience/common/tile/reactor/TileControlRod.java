package nuclearscience.common.tile.reactor;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import nuclearscience.common.tile.reactor.fission.IFissionControlRod;
import nuclearscience.common.tile.reactor.moltensalt.IMSControlRod;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.type.ComponentPacketHandler;

public abstract class TileControlRod extends GenericTile {

    public static final int MAX_EXTENSION = 100;
    public static final int EXTENSION_PER_CLICK = 10;

    public final SingleProperty<Integer> insertion = property(new SingleProperty<>(PropertyTypes.INTEGER, "insertion", 0));

    public TileControlRod(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        addComponent(new ComponentPacketHandler(this));
    }

    @Override
    public int getComparatorSignal() {
        return (int) (((double) insertion.getValue() / (double) MAX_EXTENSION) * 15);
    }

    @Override
    public InteractionResult useWithoutItem(Player player, BlockHitResult hit) {
        if (level.isClientSide()) {
            return InteractionResult.CONSUME;
        }

        if (player.isShiftKeyDown()) {
            insertion.setValue(insertion.getValue() - TileFissionControlRod.EXTENSION_PER_CLICK);
            if (insertion.getValue() < 0) {
                insertion.setValue(TileFissionControlRod.MAX_EXTENSION);
            }
        } else {
            insertion.setValue(insertion.getValue() + TileFissionControlRod.EXTENSION_PER_CLICK);
            if (insertion.getValue() > TileFissionControlRod.MAX_EXTENSION) {
                insertion.setValue(0);
            }
        }

        return InteractionResult.CONSUME;
    }

    public static class TileFissionControlRod extends TileControlRod implements IFissionControlRod {

        public TileFissionControlRod(BlockPos pos, BlockState state) {
            super(NuclearScienceTiles.TILE_FISSIONCONTROLROD.get(), pos, state);
        }

        @Override
        public int getInsertion() {
            return insertion.getValue();
        }
    }

    public static class TileMSControlRod extends TileControlRod implements IMSControlRod {

        public TileMSControlRod(BlockPos pos, BlockState state) {
            super(NuclearScienceTiles.TILE_MSCONTROLROD.get(), pos, state);
        }

        @Override
        public int getInsertion() {
            return insertion.getValue();
        }

        @Override
        public Direction facingDir() {
            return getFacing().getOpposite();
        }
    }


}
