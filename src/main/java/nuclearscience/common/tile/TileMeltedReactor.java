package nuclearscience.common.tile;

import electrodynamics.prefab.tile.GenericTileTicking;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import nuclearscience.DeferredRegisters;

public class TileMeltedReactor extends GenericTileTicking {
    public static final float RADIATION_RADIUS = 30;
    public static final float START_RADIATION = 8766000f * 5f;
    public int radiation = (int) START_RADIATION;
    public int temperature = 6000;

    public TileMeltedReactor() {
	super(DeferredRegisters.TILE_MELTEDREACTOR.get());
	addComponent(new ComponentTickable().tickServer(this::tickServer));
    }

    protected void tickServer(ComponentTickable tickable) {
	long ticks = tickable.getTicks();
	if (ticks % 10 == 0) {
	    BlockState state = world.getBlockState(pos.down());
	    if (state.getMaterial() == Material.AIR || state.getBlock() instanceof FlowingFluidBlock) {
		world.setBlockState(pos.down(), getBlockState());
		world.setBlockState(pos, Blocks.AIR.getDefaultState());
		TileEntity tile = world.getTileEntity(pos.down());
		if (tile instanceof TileMeltedReactor) {
		    TileMeltedReactor newTile = (TileMeltedReactor) tile;
		    newTile.radiation = radiation;
		    newTile.radiation = radiation;
		}
		return;
	    }
	}
	if (temperature > 0) {
	    temperature--;
	    double x2 = pos.getX() + 0.5 + (world.rand.nextDouble() - 0.5) * RADIATION_RADIUS / 2;
	    double y2 = pos.getY() + 0.5 + (world.rand.nextDouble() - 0.5) * RADIATION_RADIUS / 2;
	    double z2 = pos.getZ() + 0.5 + (world.rand.nextDouble() - 0.5) * RADIATION_RADIUS / 2;
	    double d3 = pos.getX() - x2;
	    double d4 = pos.getY() - y2;
	    double d5 = pos.getZ() - z2;
	    double distanceSq = d3 * d3 + d4 * d4 + d5 * d5;
	    if (distanceSq < RADIATION_RADIUS * RADIATION_RADIUS && world.rand.nextDouble() > distanceSq / (RADIATION_RADIUS * RADIATION_RADIUS)) {
		BlockPos p = new BlockPos(Math.floor(x2), Math.floor(y2), Math.floor(z2));
		BlockState st = world.getBlockState(p);
		Block block = st.getBlock();
		if (st.getMaterial() == Material.AIR) {
		    if (world.getBlockState(p.down()).getMaterial() != Material.AIR) {
			world.setBlockState(p, Blocks.AIR.getDefaultState());
		    }
		} else if (block == Blocks.STONE) {
		    if (temperature > 2100) {
			world.setBlockState(p, Blocks.COBBLESTONE.getDefaultState());
		    }
		} else if (block == Blocks.COBBLESTONE) {
		    world.setBlockState(p, Blocks.LAVA.getDefaultState());
		} else if (block == Blocks.WATER) {
		    world.setBlockState(p, Blocks.AIR.getDefaultState());
		} else if (block == Blocks.SAND) {
		    world.setBlockState(p, Blocks.GLASS.getDefaultState());
		}
	    }
	}
	if (radiation > 0) {
	    radiation--;
	    double x2 = pos.getX() + 0.5 + (world.rand.nextDouble() - 0.5) * RADIATION_RADIUS / 2;
	    double y2 = pos.getY() + 0.5 + (world.rand.nextDouble() - 0.5) * RADIATION_RADIUS / 2;
	    double z2 = pos.getZ() + 0.5 + (world.rand.nextDouble() - 0.5) * RADIATION_RADIUS / 2;
	    double d3 = pos.getX() - x2;
	    double d4 = pos.getY() - y2;
	    double d5 = pos.getZ() - z2;
	    double distanceSq = d3 * d3 + d4 * d4 + d5 * d5;
	    if (distanceSq < RADIATION_RADIUS * RADIATION_RADIUS && world.rand.nextDouble() > distanceSq / (RADIATION_RADIUS * RADIATION_RADIUS)) {
		BlockPos p = new BlockPos(Math.floor(x2), Math.floor(y2), Math.floor(z2));
		BlockState st = world.getBlockState(p);
		Block block = st.getBlock();
		if (block == Blocks.GRASS_BLOCK || block == Blocks.DIRT) {
		    world.setBlockState(p, DeferredRegisters.blockRadioactiveSoil.getDefaultState());
		}
	    }
	}
    }
}
