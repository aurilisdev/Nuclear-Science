package nuclearscience.common.tile;

import electrodynamics.prefab.tile.GenericTile;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;
import nuclearscience.registers.NuclearScienceBlockTypes;

public class TileElectromagneticSwitch extends GenericTile {
	public Direction lastDirection;

	public TileElectromagneticSwitch() {
		super(NuclearScienceBlockTypes.TILE_ELECTROMAGNETICSWITCH.get());
	}

	@Override
	public CompoundNBT save(CompoundNBT compound) {
		if (lastDirection != null) {
			compound.putInt("lastDirectionOrdinal", lastDirection.ordinal());
		}
		return super.save(compound);
	}

	@Override
	public void load(BlockState state, CompoundNBT compound) {
		super.load(state, compound);
		if (compound.contains("lastDirectionOrdinal")) {
			lastDirection = Direction.from3DDataValue(compound.getInt("lastDirectionOrdinal"));
		}
	}
	
	@Override
	public ActionResultType use(PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		return ActionResultType.PASS;
	}
}
