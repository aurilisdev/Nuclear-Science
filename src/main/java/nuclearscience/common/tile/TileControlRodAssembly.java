package nuclearscience.common.tile;

import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import nuclearscience.common.tile.msreactor.TileMSReactorCore;
import nuclearscience.registers.NuclearScienceBlockTypes;

public class TileControlRodAssembly extends GenericTile {

	public static final int MAX_EXTENSION = 100;
	public static final int EXTENSION_PER_CLICK = 10;

	public static final Direction[] HORIZONTAL_DIRECTIONS = { Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST };

	public final Property<Integer> direction = property(new Property<>(PropertyType.Integer, "direction", Direction.DOWN.ordinal()));
	public final Property<Integer> insertion = property(new Property<>(PropertyType.Integer, "insertion", 0));
	public final Property<Boolean> isMSR = property(new Property<>(PropertyType.Boolean, "isMSR", false));

	public TileControlRodAssembly() {
		super(NuclearScienceBlockTypes.TILE_CONTROLRODASSEMBLY.get());
		addComponent(new ComponentTickable(this));
		addComponent(new ComponentPacketHandler(this));
	}

	@Override
	public void onNeightborChanged(BlockPos neighbor, boolean blockStateTrigger) {
		if (level.isClientSide) {
			return;
		}
		isMSR.set(false);
		for (Direction dir : HORIZONTAL_DIRECTIONS) {
			TileEntity tile = level.getBlockEntity(getBlockPos().relative(dir));
			if (tile instanceof TileMSReactorCore) {
				isMSR.set(true);
				direction.set(dir.ordinal());
				break;
			}

		}

	}

	@Override
	public void onPlace(BlockState oldState, boolean isMoving) {
		if (level.isClientSide) {
			return;
		}
		isMSR.set(false);
		for (Direction dir : HORIZONTAL_DIRECTIONS) {
			TileEntity tile = level.getBlockEntity(getBlockPos().relative(dir));
			if (tile instanceof TileMSReactorCore) {
				isMSR.set(true);
				direction.set(dir.ordinal());
				break;
			}

		}
	}

	@Override
	public int getComparatorSignal() {
		return (int) (((double) insertion.get() / (double) MAX_EXTENSION) * 15);
	}

	@Override
	public ActionResultType use(PlayerEntity player, Hand hand, BlockRayTraceResult result) {

		if (level.isClientSide()) {
			return ActionResultType.CONSUME;
		}

		if (player.isShiftKeyDown()) {
			insertion.set(insertion.get() - TileControlRodAssembly.EXTENSION_PER_CLICK);
			if (insertion.get() < 0) {
				insertion.set(TileControlRodAssembly.MAX_EXTENSION);
			}
		} else {
			insertion.set(insertion.get() + TileControlRodAssembly.EXTENSION_PER_CLICK);
			if (insertion.get() > TileControlRodAssembly.MAX_EXTENSION) {
				insertion.set(0);
			}
		}

		return ActionResultType.CONSUME;
	}

}