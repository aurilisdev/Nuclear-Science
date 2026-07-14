package nuclearscience.common.tile.reactor;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;
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

    public final SingleProperty<Integer> insertion = property(new SingleProperty<>(PropertyTypes.INTEGER, "insertion", 0)).setShouldUpdateOnChange();

    public TileControlRod(TileEntityType<?> type) {
        super(type);
        addComponent(new ComponentPacketHandler(this));
    }

    @Override
    public int getComparatorSignal() {
        return (int) ((double) insertion.getValue() / (double) MAX_EXTENSION * 15);
    }
    
    @Override
    public ActionResultType use(PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
    	if (level.isClientSide()) {
            return ActionResultType.CONSUME;
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

        return ActionResultType.CONSUME;
    }

    public static class TileFissionControlRod extends TileControlRod implements IFissionControlRod {

        public TileFissionControlRod() {
            super(NuclearScienceTiles.TILE_FISSIONCONTROLROD.get());
        }

        @Override
        public int getInsertion() {
            return insertion.getValue();
        }
    }

    public static class TileMSControlRod extends TileControlRod implements IMSControlRod {

        public TileMSControlRod() {
            super(NuclearScienceTiles.TILE_MSCONTROLROD.get());
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
