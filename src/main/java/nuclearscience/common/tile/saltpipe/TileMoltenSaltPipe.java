package nuclearscience.common.tile.saltpipe;

import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import nuclearscience.common.block.connect.BlockMoltenSaltPipe;
import nuclearscience.common.block.subtype.SubtypeMoltenSaltPipe;
import nuclearscience.registers.NuclearScienceBlockTypes;

public class TileMoltenSaltPipe extends GenericTileMoltenSaltPipe {
	public Property<Double> transmit = property(new Property<>(PropertyType.Double, "transmit", 0.0));

	public TileMoltenSaltPipe() {
		super(NuclearScienceBlockTypes.TILE_MOLTENSALTPIPE.get());
	}

	public SubtypeMoltenSaltPipe pipe = null;

	@Override
	public SubtypeMoltenSaltPipe getPipeType() {
		if (pipe == null) {
			pipe = ((BlockMoltenSaltPipe) getBlockState().getBlock()).pipe;
		}
		return pipe;
	}

	@Override
	public CompoundNBT save(CompoundNBT compound) {
		compound.putInt("ord", getPipeType().ordinal());
		return super.save(compound);
	}

	@Override
	public void load(BlockState state, CompoundNBT compound) {
		super.load(state, compound);
		pipe = SubtypeMoltenSaltPipe.values()[compound.getInt("ord")];
	}
}