package nuclearscience.common.block.voxelshapes;

import java.util.stream.Stream;

import electrodynamics.common.block.voxelshapes.VoxelShapes;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import nuclearscience.registers.NuclearScienceBlocks;

public class NuclearScienceVoxelShapeRegistry {

	/**
	 * By convention this will be in alphabetical order
	 */
	public static void init() {

		/* FISSION REACTOR CORE */

		VoxelShape fissionreactorcore = Shapes.empty();

		fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.40625, 0.115675, 0.40625, 0.59375, 0.7088875, 0.59375), BooleanOp.OR);
		fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.25, 0.06565, 0.125, 0.75, 0.1148875, 0.1875), BooleanOp.OR);
		fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.84375, 0.7878, 0.25, 0.90625, 0.8370375, 0.75), BooleanOp.OR);
		fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.8125, 0.06565, 0.25, 0.875, 0.1148875, 0.75), BooleanOp.OR);
		fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.0625, 0.72215, 0.125, 0.125, 0.7878, 0.875), BooleanOp.OR);
		fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.1875, 0.06565, 0.875, 0.25, 0.72215, 0.9375), BooleanOp.OR);
		fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.1875, 0.06565, 0.0625, 0.25, 0.72215, 0.125), BooleanOp.OR);
		fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.25, 0.06565, 0.25, 0.3125, 0.72215, 0.3125), BooleanOp.OR);
		fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.25, 0.06565, 0.6875, 0.3125, 0.72215, 0.75), BooleanOp.OR);
		fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.6875, 0.06565, 0.25, 0.75, 0.72215, 0.3125), BooleanOp.OR);
		fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.6875, 0.06565, 0.6875, 0.75, 0.72215, 0.75), BooleanOp.OR);
		fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.75, 0.06565, 0.875, 0.8125, 0.72215, 0.9375), BooleanOp.OR);
		fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.75, 0.06565, 0.0625, 0.8125, 0.72215, 0.125), BooleanOp.OR);
		fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.0625, 0.06565, 0.75, 0.125, 0.72215, 0.8125), BooleanOp.OR);
		fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.0625, 0.06565, 0.1875, 0.125, 0.72215, 0.25), BooleanOp.OR);
		fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.875, 0.06565, 0.75, 0.9375, 0.72215, 0.8125), BooleanOp.OR);
		fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.875, 0.06565, 0.1875, 0.9375, 0.72215, 0.25), BooleanOp.OR);
		fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.0625, 0, 0.125, 0.125, 0.06565, 0.875), BooleanOp.OR);
		fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.09375, 0.7878, 0.25, 0.15625, 0.8370375, 0.75), BooleanOp.OR);
		fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.125, 0.06565, 0.25, 0.1875, 0.1148875, 0.75), BooleanOp.OR);
		fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.875, 0, 0.125, 0.9375, 0.06565, 0.875), BooleanOp.OR);
		fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.125, 0, 0.0625, 0.875, 0.06565, 0.9375), BooleanOp.OR);
		fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.125, 0.72215, 0.0625, 0.875, 0.7878, 0.9375), BooleanOp.OR);
		fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.15625, 0.7878, 0.15625, 0.84375, 0.8370375, 0.84375), BooleanOp.OR);
		fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.1875, 0.06565, 0.1875, 0.8125, 0.1148875, 0.8125), BooleanOp.OR);
		fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.1875, 0.8370375, 0.1875, 0.8125, 0.886275, 0.8125), BooleanOp.OR);
		fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.25, 0.886275, 0.25, 0.75, 0.9355125, 0.75), BooleanOp.OR);
		fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.25, 0.7878, 0.84375, 0.75, 0.8370375, 0.90625), BooleanOp.OR);
		fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.25, 0.06565, 0.8125, 0.75, 0.1148875, 0.875), BooleanOp.OR);
		fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.875, 0.72215, 0.125, 0.9375, 0.7878, 0.875), BooleanOp.OR);
		fissionreactorcore = Shapes.join(fissionreactorcore, Shapes.box(0.25, 0.7878, 0.09375, 0.75, 0.8370375, 0.15625), BooleanOp.OR);
		VoxelShapes.registerShape(NuclearScienceBlocks.blockFissionReactorCore, fissionreactorcore, Direction.NORTH);

		/* FREEZE PLUG */

		VoxelShape freezeplug = Shapes.empty();
		freezeplug = Shapes.join(freezeplug, Shapes.box(0.25, 0.15625, 0.25, 0.75, 0.28125, 0.75), BooleanOp.OR);
		freezeplug = Shapes.join(freezeplug, Shapes.box(0.3125, 0.25, 0.3125, 0.6875, 0.8125, 0.6875), BooleanOp.OR);
		freezeplug = Shapes.join(freezeplug, Shapes.box(0.125, 0.9375, 0.8125, 0.875, 1, 0.875), BooleanOp.OR);
		freezeplug = Shapes.join(freezeplug, Shapes.box(0.125, 0.9375, 0.125, 0.875, 1, 0.1875), BooleanOp.OR);
		freezeplug = Shapes.join(freezeplug, Shapes.box(0.1875, 0.875, 0.75, 0.8125, 0.9375, 0.8125), BooleanOp.OR);
		freezeplug = Shapes.join(freezeplug, Shapes.box(0.25, 0.8125, 0.25, 0.75, 0.875, 0.75), BooleanOp.OR);
		freezeplug = Shapes.join(freezeplug, Shapes.box(0.1875, 0.875, 0.1875, 0.8125, 0.9375, 0.25), BooleanOp.OR);
		freezeplug = Shapes.join(freezeplug, Shapes.box(0.1875, 0.875, 0.25, 0.25, 0.9375, 0.75), BooleanOp.OR);
		freezeplug = Shapes.join(freezeplug, Shapes.box(0.75, 0.875, 0.25, 0.8125, 0.9375, 0.75), BooleanOp.OR);
		freezeplug = Shapes.join(freezeplug, Shapes.box(0.8125, 0.9375, 0.1875, 0.875, 1, 0.8125), BooleanOp.OR);
		freezeplug = Shapes.join(freezeplug, Shapes.box(0.125, 0.9375, 0.1875, 0.1875, 1, 0.8125), BooleanOp.OR);
		freezeplug = Shapes.join(freezeplug, Shapes.box(0.296875, 0.625, 0.296875, 0.703125, 0.6875, 0.703125), BooleanOp.OR);
		freezeplug = Shapes.join(freezeplug, Shapes.box(0.28125, 0.5, 0.28125, 0.71875, 0.5625, 0.71875), BooleanOp.OR);
		freezeplug = Shapes.join(freezeplug, Shapes.box(0.296875, 0.375, 0.296875, 0.703125, 0.4375, 0.703125), BooleanOp.OR);
		freezeplug = Shapes.join(freezeplug, Shapes.box(0.078125, 0, 0.296875, 0.15625, 0.3125, 0.390625), BooleanOp.OR);
		freezeplug = Shapes.join(freezeplug, Shapes.box(0.078125, 0, 0.453125, 0.15625, 0.3125, 0.546875), BooleanOp.OR);
		freezeplug = Shapes.join(freezeplug, Shapes.box(0.078125, 0, 0.609375, 0.15625, 0.3125, 0.703125), BooleanOp.OR);
		freezeplug = Shapes.join(freezeplug, Shapes.box(0.296875, 0, 0.84375, 0.390625, 0.3125, 0.921875), BooleanOp.OR);
		freezeplug = Shapes.join(freezeplug, Shapes.box(0.453125, 0, 0.84375, 0.546875, 0.3125, 0.921875), BooleanOp.OR);
		freezeplug = Shapes.join(freezeplug, Shapes.box(0.609375, 0, 0.84375, 0.703125, 0.3125, 0.921875), BooleanOp.OR);
		freezeplug = Shapes.join(freezeplug, Shapes.box(0.84375, 0, 0.609375, 0.921875, 0.3125, 0.703125), BooleanOp.OR);
		freezeplug = Shapes.join(freezeplug, Shapes.box(0.84375, 0, 0.453125, 0.921875, 0.3125, 0.546875), BooleanOp.OR);
		freezeplug = Shapes.join(freezeplug, Shapes.box(0.84375, 0, 0.296875, 0.921875, 0.3125, 0.390625), BooleanOp.OR);
		freezeplug = Shapes.join(freezeplug, Shapes.box(0.609375, 0, 0.078125, 0.703125, 0.3125, 0.15625), BooleanOp.OR);
		freezeplug = Shapes.join(freezeplug, Shapes.box(0.453125, 0, 0.078125, 0.546875, 0.3125, 0.15625), BooleanOp.OR);
		freezeplug = Shapes.join(freezeplug, Shapes.box(0.296875, 0, 0.078125, 0.390625, 0.3125, 0.15625), BooleanOp.OR);
		freezeplug = Shapes.join(freezeplug, Shapes.box(0.625, 0.1875, 0.15625, 0.6875, 0.25, 0.25), BooleanOp.OR);
		freezeplug = Shapes.join(freezeplug, Shapes.box(0.46875, 0.1875, 0.15625, 0.53125, 0.25, 0.25), BooleanOp.OR);
		freezeplug = Shapes.join(freezeplug, Shapes.box(0.3125, 0.1875, 0.15625, 0.375, 0.25, 0.25), BooleanOp.OR);
		freezeplug = Shapes.join(freezeplug, Shapes.box(0.15625, 0.1875, 0.3125, 0.25, 0.25, 0.375), BooleanOp.OR);
		freezeplug = Shapes.join(freezeplug, Shapes.box(0.15625, 0.1875, 0.46875, 0.25, 0.25, 0.53125), BooleanOp.OR);
		freezeplug = Shapes.join(freezeplug, Shapes.box(0.15625, 0.1875, 0.625, 0.25, 0.25, 0.6875), BooleanOp.OR);
		freezeplug = Shapes.join(freezeplug, Shapes.box(0.3125, 0.1875, 0.75, 0.375, 0.25, 0.84375), BooleanOp.OR);
		freezeplug = Shapes.join(freezeplug, Shapes.box(0.46875, 0.1875, 0.75, 0.53125, 0.25, 0.84375), BooleanOp.OR);
		freezeplug = Shapes.join(freezeplug, Shapes.box(0.625, 0.1875, 0.75, 0.6875, 0.25, 0.84375), BooleanOp.OR);
		freezeplug = Shapes.join(freezeplug, Shapes.box(0.75, 0.1875, 0.625, 0.84375, 0.25, 0.6875), BooleanOp.OR);
		freezeplug = Shapes.join(freezeplug, Shapes.box(0.75, 0.1875, 0.46875, 0.84375, 0.25, 0.53125), BooleanOp.OR);
		freezeplug = Shapes.join(freezeplug, Shapes.box(0.75, 0.1875, 0.3125, 0.84375, 0.25, 0.375), BooleanOp.OR);
		freezeplug = Shapes.join(freezeplug, Shapes.box(0.3125, 0, 0.3125, 0.6875, 0.1875, 0.6875), BooleanOp.OR);

		VoxelShapes.registerShape(NuclearScienceBlocks.blockFreezePlug, freezeplug, Direction.EAST);

		/* FUEL REPROCESSOR */

		VoxelShape fuelreprocessor = Shapes.empty();
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.875, 0, 0.9375, 1, 1, 1), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.875, 0, 0, 1, 1, 0.0625), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0, 0, 0, 0.125, 1, 0.0625), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0, 0, 0.9375, 0.125, 1, 1), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0, 0, 0.875, 0.0625, 1, 0.9375), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0, 0, 0.0625, 0.0625, 1, 0.125), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.9375, 0, 0.0625, 1, 1, 0.125), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.9375, 0, 0.875, 1, 1, 0.9375), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.0625, 0.4375, 0.8125, 0.1875, 0.5625, 0.9375), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.125, 0.4375, 0.75, 0.25, 0.5625, 0.875), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.1875, 0.4375, 0.6875, 0.25, 0.5625, 0.75), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.25, 0.4375, 0.75, 0.3125, 0.5625, 0.8125), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.0625, 0.4375, 0.0625, 0.1875, 0.5625, 0.1875), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.125, 0.4375, 0.125, 0.25, 0.5625, 0.25), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.25, 0.4375, 0.1875, 0.3125, 0.5625, 0.25), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.1875, 0.4375, 0.25, 0.25, 0.5625, 0.3125), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.8125, 0.4375, 0.8125, 0.9375, 0.5625, 0.9375), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.75, 0.4375, 0.75, 0.875, 0.5625, 0.875), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.75, 0.4375, 0.6875, 0.8125, 0.5625, 0.75), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.6875, 0.4375, 0.75, 0.75, 0.5625, 0.8125), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.8125, 0.4375, 0.0625, 0.9375, 0.5625, 0.1875), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.75, 0.4375, 0.125, 0.875, 0.5625, 0.25), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.6875, 0.4375, 0.1875, 0.75, 0.5625, 0.25), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.75, 0.4375, 0.25, 0.8125, 0.5625, 0.3125), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.1875, 0.125, 0.3125, 0.8125, 0.875, 0.6875), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.25, 0.125, 0.6875, 0.3125, 0.875, 0.75), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.25, 0.125, 0.25, 0.3125, 0.875, 0.3125), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.6875, 0.125, 0.25, 0.75, 0.875, 0.3125), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.6875, 0.125, 0.6875, 0.75, 0.875, 0.75), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.3125, 0.125, 0.6875, 0.6875, 0.875, 0.8125), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.3125, 0.125, 0.1875, 0.6875, 0.875, 0.3125), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.25, 0.9375, 0.25, 0.75, 1, 0.75), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.3125, 0.875, 0.3125, 0.6875, 0.9375, 0.6875), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.9375, 0.25, 0.25, 1, 0.75, 0.75), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.8125, 0.3125, 0.3125, 0.9375, 0.6875, 0.6875), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.3125, 0.1875, 0.125, 0.6875, 0.25, 0.1875), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.8125, 0.1875, 0.3125, 0.875, 0.25, 0.6875), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.75, 0.1875, 0.6875, 0.8125, 0.25, 0.75), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.6875, 0.1875, 0.75, 0.75, 0.25, 0.8125), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.6875, 0.1875, 0.1875, 0.75, 0.25, 0.25), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.75, 0.1875, 0.25, 0.8125, 0.25, 0.3125), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.1875, 0.1875, 0.25, 0.25, 0.25, 0.3125), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.25, 0.1875, 0.1875, 0.3125, 0.25, 0.25), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.25, 0.1875, 0.75, 0.3125, 0.25, 0.8125), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.1875, 0.1875, 0.6875, 0.25, 0.25, 0.75), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.125, 0.1875, 0.3125, 0.1875, 0.25, 0.6875), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.3125, 0.1875, 0.8125, 0.6875, 0.25, 0.875), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.3125, 0.75, 0.125, 0.6875, 0.8125, 0.1875), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.8125, 0.75, 0.3125, 0.875, 0.8125, 0.6875), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.75, 0.75, 0.6875, 0.8125, 0.8125, 0.75), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.6875, 0.75, 0.75, 0.75, 0.8125, 0.8125), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.6875, 0.75, 0.1875, 0.75, 0.8125, 0.25), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.75, 0.75, 0.25, 0.8125, 0.8125, 0.3125), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.1875, 0.75, 0.25, 0.25, 0.8125, 0.3125), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.25, 0.75, 0.1875, 0.3125, 0.8125, 0.25), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.25, 0.75, 0.75, 0.3125, 0.8125, 0.8125), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.1875, 0.75, 0.6875, 0.25, 0.8125, 0.75), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.125, 0.75, 0.3125, 0.1875, 0.8125, 0.6875), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.3125, 0.75, 0.8125, 0.6875, 0.8125, 0.875), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.125, 0.25, 0.375, 0.1875, 0.75, 0.4375), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.5625, 0.25, 0.125, 0.625, 0.75, 0.1875), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.375, 0.25, 0.125, 0.4375, 0.75, 0.1875), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.375, 0.25, 0.8125, 0.4375, 0.75, 0.875), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.5625, 0.25, 0.8125, 0.625, 0.75, 0.875), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.125, 0.25, 0.5625, 0.1875, 0.75, 0.625), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.8125, 0.6875, 0.5625, 0.875, 0.75, 0.625), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.8125, 0.6875, 0.375, 0.875, 0.75, 0.4375), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.8125, 0.25, 0.375, 0.875, 0.3125, 0.4375), BooleanOp.OR);
		fuelreprocessor = Shapes.join(fuelreprocessor, Shapes.box(0.8125, 0.25, 0.5625, 0.875, 0.3125, 0.625), BooleanOp.OR);
		VoxelShapes.registerShape(NuclearScienceBlocks.blockFuelReprocessor, fuelreprocessor, Direction.WEST);

		/* GAS CENTRIFUGE */

		VoxelShape gascentriguge = Shapes.empty();
		gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.5625, 0.921875, 0.26875, 1.0234375, 1.03125, 0.73125), BooleanOp.OR);
		gascentriguge = Shapes.join(gascentriguge, Shapes.box(0, 0.0625, 0, 1, 0.125, 1), BooleanOp.OR);
		gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.25, 0, 0.25, 0.75, 0.0625, 0.75), BooleanOp.OR);
		gascentriguge = Shapes.join(gascentriguge, Shapes.box(0, 0, 0, 0.0625, 0.0625, 1), BooleanOp.OR);
		gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.0625, 0, 0, 0.9375, 0.0625, 0.0625), BooleanOp.OR);
		gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.0625, 0, 0.9375, 0.9375, 0.0625, 1), BooleanOp.OR);
		gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.9375, 0, 0, 1, 0.0625, 1), BooleanOp.OR);
		gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.125, 0.125, 0.125, 0.875, 0.1875, 0.8715), BooleanOp.OR);
		gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.75, 0.125, 0.28125, 1, 0.8125, 0.71875), BooleanOp.OR);
		gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.234375, 0.125, 0.25, 0.75, 0.3125, 0.75), BooleanOp.OR);
		gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.390625, 0.875, 0.375, 0.5890625, 0.984375, 0.625), BooleanOp.OR);
		gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.7765625, 0.3125, 0.5625, 0.8390625, 0.34375, 0.671875), BooleanOp.OR);
		gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.2453125, 0.3125, 0.609375, 0.3078125, 0.34375, 0.75), BooleanOp.OR);
		gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.2453125, 0.3125, 0.25, 0.3078125, 0.34375, 0.390625), BooleanOp.OR);
		gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.7765625, 0.3125, 0.328125, 0.8390625, 0.34375, 0.4375), BooleanOp.OR);
		gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.7765625, 0.125, 0.5625, 0.8390625, 0.3125, 0.671875), BooleanOp.OR);
		gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.7765625, 0.125, 0.328125, 0.8390625, 0.3125, 0.4375), BooleanOp.OR);
		gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.2453125, 0.125, 0.609375, 0.3078125, 0.3125, 0.75), BooleanOp.OR);
		gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.2453125, 0.125, 0.25, 0.3078125, 0.3125, 0.390625), BooleanOp.OR);
		gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.375, 0.3125, 0.5625, 0.4375, 0.4375, 0.625), BooleanOp.OR);
		gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.5625, 0.3125, 0.5625, 0.625, 0.4375, 0.625), BooleanOp.OR);
		gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.4375, 0.3125, 0.4375, 0.5625, 1, 0.5625), BooleanOp.OR);
		gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.5625, 0.3125, 0.375, 0.625, 0.4375, 0.4375), BooleanOp.OR);
		gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.34375, 0.3125, 0.46875, 0.40625, 0.4375, 0.53125), BooleanOp.OR);
		gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.46875, 0.3125, 0.59375, 0.53125, 0.4375, 0.65625), BooleanOp.OR);
		gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.46875, 0.3125, 0.34375, 0.53125, 0.4375, 0.40625), BooleanOp.OR);
		gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.375, 0.3125, 0.375, 0.4375, 0.4375, 0.4375), BooleanOp.OR);
		gascentriguge = Shapes.join(gascentriguge, Shapes.box(0.59375, 0.3125, 0.46875, 0.65625, 0.4375, 0.53125), BooleanOp.OR);
		VoxelShapes.registerShape(NuclearScienceBlocks.blockGasCentrifuge, gascentriguge, Direction.WEST);

		/* HEAT EXCHANGER */

		VoxelShape heatexchanger = Shapes.empty();
		heatexchanger = Shapes.join(heatexchanger, Block.box(0, 1, 0, 16, 3, 16), BooleanOp.OR);
		heatexchanger = Shapes.join(heatexchanger, Block.box(1, 4, 1, 15, 16, 15), BooleanOp.OR);
		heatexchanger = Shapes.join(heatexchanger, Block.box(0.5, 3, 0.5, 15.5, 4, 15.5), BooleanOp.OR);
		heatexchanger = Shapes.join(heatexchanger, Block.box(4, 0, 4, 12, 1, 12), BooleanOp.OR);

		VoxelShapes.registerShape(NuclearScienceBlocks.blockHeatExchanger, heatexchanger, Direction.WEST);

		/* MOLTEN SALT REACTOR CORE */

		VoxelShape msreactorcore = Shapes.empty();
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.125, 0, 0.125, 0.875, 0.8125, 0.875), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.0625, 0.125, 0.1875, 0.125, 0.75, 0.8125), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.875, 0.125, 0.1875, 0.9375, 0.75, 0.8125), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.1875, 0.125, 0.0625, 0.8125, 0.75, 0.125), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.1875, 0.125, 0.875, 0.8125, 0.75, 0.9375), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0, 0.1875, 0.25, 0.0625, 0.6875, 0.75), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.1875, 0.8125, 0.1875, 0.8125, 0.875, 0.8125), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.3125, 0.875, 0.3125, 0.6875, 1, 0.6875), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.25, 0.875, 0.375, 0.3125, 1.125, 0.4375), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.25, 0.875, 0.5625, 0.3125, 1.125, 0.625), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.6875, 0.875, 0.5625, 0.75, 1.125, 0.625), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.6875, 0.875, 0.375, 0.75, 1.125, 0.4375), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.5625, 0.875, 0.25, 0.625, 1.125, 0.3125), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.375, 0.875, 0.25, 0.4375, 1.125, 0.3125), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.375, 0.875, 0.6875, 0.4375, 1.125, 0.75), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.5625, 0.875, 0.6875, 0.625, 1.125, 0.75), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0, 0.25, 0.1875, 0.0625, 0.3125, 0.25), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0, 0.40625, 0.1875, 0.0625, 0.46875, 0.25), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0, 0.5625, 0.1875, 0.0625, 0.625, 0.25), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0, 0.5625, 0.75, 0.0625, 0.625, 0.8125), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0, 0.40625, 0.75, 0.0625, 0.46875, 0.8125), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0, 0.25, 0.75, 0.0625, 0.3125, 0.8125), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.0625, 0.5625, 0.8125, 0.125, 0.625, 0.875), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.0625, 0.40625, 0.8125, 0.125, 0.46875, 0.875), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.0625, 0.25, 0.8125, 0.125, 0.3125, 0.875), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.0625, 0.5625, 0.125, 0.125, 0.625, 0.1875), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.0625, 0.40625, 0.125, 0.125, 0.46875, 0.1875), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.0625, 0.25, 0.125, 0.125, 0.3125, 0.1875), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.875, 0.40625, 0.125, 0.9375, 0.46875, 0.1875), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.875, 0.25, 0.125, 0.9375, 0.3125, 0.1875), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.875, 0.5625, 0.125, 0.9375, 0.625, 0.1875), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.875, 0.5625, 0.8125, 0.9375, 0.625, 0.875), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.875, 0.40625, 0.8125, 0.9375, 0.46875, 0.875), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.875, 0.25, 0.8125, 0.9375, 0.3125, 0.875), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.9375, 0.5625, 0.1875, 1, 0.625, 0.8125), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.9375, 0.40625, 0.1875, 1, 0.46875, 0.8125), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.9375, 0.25, 0.1875, 1, 0.3125, 0.8125), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.8125, 0.5625, 0.875, 0.875, 0.625, 0.9375), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.8125, 0.40625, 0.875, 0.875, 0.46875, 0.9375), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.8125, 0.25, 0.875, 0.875, 0.3125, 0.9375), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.125, 0.5625, 0.875, 0.1875, 0.625, 0.9375), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.125, 0.40625, 0.875, 0.1875, 0.46875, 0.9375), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.125, 0.25, 0.875, 0.1875, 0.3125, 0.9375), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.125, 0.5625, 0.0625, 0.1875, 0.625, 0.125), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.125, 0.40625, 0.0625, 0.1875, 0.46875, 0.125), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.125, 0.25, 0.0625, 0.1875, 0.3125, 0.125), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.8125, 0.5625, 0.0625, 0.875, 0.625, 0.125), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.8125, 0.40625, 0.0625, 0.875, 0.46875, 0.125), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.8125, 0.25, 0.0625, 0.875, 0.3125, 0.125), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.1875, 0.5625, 0, 0.8125, 0.625, 0.0625), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.1875, 0.40625, 0, 0.8125, 0.46875, 0.0625), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.1875, 0.25, 0, 0.8125, 0.3125, 0.0625), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.1875, 0.5625, 0.9375, 0.8125, 0.625, 1), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.1875, 0.40625, 0.9375, 0.8125, 0.46875, 1), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.1875, 0.25, 0.9375, 0.8125, 0.3125, 1), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.0625, -0.125, 0.6875, 0.125, 0.125, 0.75), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.25, -0.125, 0.875, 0.3125, 0.125, 0.9375), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.25, -0.0625, 0.75, 0.75, 0, 0.8125), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.6875, -0.125, 0.875, 0.75, 0.125, 0.9375), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.875, -0.125, 0.6875, 0.9375, 0.125, 0.75), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.875, -0.125, 0.25, 0.9375, 0.125, 0.3125), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.0625, -0.125, 0.25, 0.125, 0.125, 0.3125), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.1875, -0.0625, 0.1875, 0.25, 0, 0.8125), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.25, -0.0625, 0.1875, 0.75, 0, 0.25), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.75, -0.0625, 0.1875, 0.8125, 0, 0.8125), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.25, -0.125, 0.0625, 0.3125, 0.125, 0.125), BooleanOp.OR);
		msreactorcore = Shapes.join(msreactorcore, Shapes.box(0.6875, -0.125, 0.0625, 0.75, 0.125, 0.125), BooleanOp.OR);
		VoxelShapes.registerShape(NuclearScienceBlocks.blockMSReactorCore, msreactorcore, Direction.WEST);

		/* MOLTEN SALT SUPPLIER */

		VoxelShape moltensaltsupplier = Shapes.empty();
		moltensaltsupplier = Shapes.join(moltensaltsupplier, Shapes.box(0.0625, 0.125, 0.25, 0.9375, 0.75, 0.75), BooleanOp.OR);
		moltensaltsupplier = Shapes.join(moltensaltsupplier, Shapes.box(0.0625, 0.1875, 0.1875, 0.9375, 0.6875, 0.25), BooleanOp.OR);
		moltensaltsupplier = Shapes.join(moltensaltsupplier, Shapes.box(0.0625, 0.1875, 0.75, 0.9375, 0.6875, 0.8125), BooleanOp.OR);
		moltensaltsupplier = Shapes.join(moltensaltsupplier, Shapes.box(0, 0.1875, 0.25, 0.0625, 0.6875, 0.75), BooleanOp.OR);
		moltensaltsupplier = Shapes.join(moltensaltsupplier, Shapes.box(0.9375, 0.1875, 0.25, 1, 0.6875, 0.75), BooleanOp.OR);
		moltensaltsupplier = Shapes.join(moltensaltsupplier, Shapes.box(0.25, 0.0625, 0.25, 0.75, 0.125, 0.75), BooleanOp.OR);
		moltensaltsupplier = Shapes.join(moltensaltsupplier, Shapes.box(0.3125, 0, 0.3125, 0.6875, 0.0625, 0.6875), BooleanOp.OR);
		moltensaltsupplier = Shapes.join(moltensaltsupplier, Shapes.box(0.8125, 0.125, 0.1875, 0.875, 0.1875, 0.25), BooleanOp.OR);
		moltensaltsupplier = Shapes.join(moltensaltsupplier, Shapes.box(0.8125, 0.125, 0.75, 0.875, 0.1875, 0.8125), BooleanOp.OR);
		moltensaltsupplier = Shapes.join(moltensaltsupplier, Shapes.box(0.8125, 0.1875, 0.125, 0.875, 0.6875, 0.1875), BooleanOp.OR);
		moltensaltsupplier = Shapes.join(moltensaltsupplier, Shapes.box(0.8125, 0.1875, 0.8125, 0.875, 0.6875, 0.875), BooleanOp.OR);
		moltensaltsupplier = Shapes.join(moltensaltsupplier, Shapes.box(0.8125, 0.0625, 0.25, 0.875, 0.125, 0.75), BooleanOp.OR);
		moltensaltsupplier = Shapes.join(moltensaltsupplier, Shapes.box(0.75, 0.0625, 0.3125, 0.8125, 0.125, 0.375), BooleanOp.OR);
		moltensaltsupplier = Shapes.join(moltensaltsupplier, Shapes.box(0.75, 0.0625, 0.625, 0.8125, 0.125, 0.6875), BooleanOp.OR);
		moltensaltsupplier = Shapes.join(moltensaltsupplier, Shapes.box(0.8125, 0.75, 0.25, 0.875, 0.8125, 0.75), BooleanOp.OR);
		moltensaltsupplier = Shapes.join(moltensaltsupplier, Shapes.box(0.8125, 0.6875, 0.75, 0.875, 0.75, 0.8125), BooleanOp.OR);
		moltensaltsupplier = Shapes.join(moltensaltsupplier, Shapes.box(0.8125, 0.6875, 0.1875, 0.875, 0.75, 0.25), BooleanOp.OR);
		moltensaltsupplier = Shapes.join(moltensaltsupplier, Shapes.box(0.625, 0.125, 0.1875, 0.6875, 0.1875, 0.25), BooleanOp.OR);
		moltensaltsupplier = Shapes.join(moltensaltsupplier, Shapes.box(0.625, 0.125, 0.75, 0.6875, 0.1875, 0.8125), BooleanOp.OR);
		moltensaltsupplier = Shapes.join(moltensaltsupplier, Shapes.box(0.625, 0.1875, 0.125, 0.6875, 0.6875, 0.1875), BooleanOp.OR);
		moltensaltsupplier = Shapes.join(moltensaltsupplier, Shapes.box(0.625, 0.1875, 0.8125, 0.6875, 0.6875, 0.875), BooleanOp.OR);
		moltensaltsupplier = Shapes.join(moltensaltsupplier, Shapes.box(0.625, 0.75, 0.25, 0.6875, 0.8125, 0.75), BooleanOp.OR);
		moltensaltsupplier = Shapes.join(moltensaltsupplier, Shapes.box(0.625, 0.6875, 0.75, 0.6875, 0.75, 0.8125), BooleanOp.OR);
		moltensaltsupplier = Shapes.join(moltensaltsupplier, Shapes.box(0.625, 0.6875, 0.1875, 0.6875, 0.75, 0.25), BooleanOp.OR);
		moltensaltsupplier = Shapes.join(moltensaltsupplier, Shapes.box(0.3125, 0.125, 0.1875, 0.375, 0.1875, 0.25), BooleanOp.OR);
		moltensaltsupplier = Shapes.join(moltensaltsupplier, Shapes.box(0.3125, 0.125, 0.75, 0.375, 0.1875, 0.8125), BooleanOp.OR);
		moltensaltsupplier = Shapes.join(moltensaltsupplier, Shapes.box(0.3125, 0.1875, 0.125, 0.375, 0.6875, 0.1875), BooleanOp.OR);
		moltensaltsupplier = Shapes.join(moltensaltsupplier, Shapes.box(0.3125, 0.1875, 0.8125, 0.375, 0.6875, 0.875), BooleanOp.OR);
		moltensaltsupplier = Shapes.join(moltensaltsupplier, Shapes.box(0.3125, 0.75, 0.25, 0.375, 0.8125, 0.75), BooleanOp.OR);
		moltensaltsupplier = Shapes.join(moltensaltsupplier, Shapes.box(0.3125, 0.6875, 0.75, 0.375, 0.75, 0.8125), BooleanOp.OR);
		moltensaltsupplier = Shapes.join(moltensaltsupplier, Shapes.box(0.3125, 0.6875, 0.1875, 0.375, 0.75, 0.25), BooleanOp.OR);
		moltensaltsupplier = Shapes.join(moltensaltsupplier, Shapes.box(0.125, 0.125, 0.1875, 0.1875, 0.1875, 0.25), BooleanOp.OR);
		moltensaltsupplier = Shapes.join(moltensaltsupplier, Shapes.box(0.125, 0.125, 0.75, 0.1875, 0.1875, 0.8125), BooleanOp.OR);
		moltensaltsupplier = Shapes.join(moltensaltsupplier, Shapes.box(0.125, 0.1875, 0.125, 0.1875, 0.6875, 0.1875), BooleanOp.OR);
		moltensaltsupplier = Shapes.join(moltensaltsupplier, Shapes.box(0.125, 0.1875, 0.8125, 0.1875, 0.6875, 0.875), BooleanOp.OR);
		moltensaltsupplier = Shapes.join(moltensaltsupplier, Shapes.box(0.125, 0.0625, 0.25, 0.1875, 0.125, 0.75), BooleanOp.OR);
		moltensaltsupplier = Shapes.join(moltensaltsupplier, Shapes.box(0.125, 0.75, 0.25, 0.1875, 0.8125, 0.75), BooleanOp.OR);
		moltensaltsupplier = Shapes.join(moltensaltsupplier, Shapes.box(0.125, 0.6875, 0.75, 0.1875, 0.75, 0.8125), BooleanOp.OR);
		moltensaltsupplier = Shapes.join(moltensaltsupplier, Shapes.box(0.125, 0.6875, 0.1875, 0.1875, 0.75, 0.25), BooleanOp.OR);
		moltensaltsupplier = Shapes.join(moltensaltsupplier, Shapes.box(0.1875, 0.0625, 0.3125, 0.25, 0.125, 0.375), BooleanOp.OR);
		moltensaltsupplier = Shapes.join(moltensaltsupplier, Shapes.box(0.1875, 0.0625, 0.625, 0.25, 0.125, 0.6875), BooleanOp.OR);
		VoxelShapes.registerShape(NuclearScienceBlocks.blockMoltenSaltSupplier, moltensaltsupplier, Direction.EAST);

		/* PARTICLE INJECTOR */

		VoxelShape particleinjector = Stream.of(
				//
				Block.box(15, 4, 4, 16, 12, 12),
				//
				Block.box(12, 0, 0, 15, 16, 16),
				//
				Block.box(15, 0, 15, 16, 16, 16),
				//
				Block.box(15, 0, 0, 16, 16, 1),
				//
				Block.box(15, 15, 1, 16, 16, 15),
				//
				Block.box(15, 0, 1, 16, 1, 15),
				//
				Block.box(9, 1, 1, 12, 15, 15),
				//
				Block.box(4, 1, 1, 5, 15, 15),
				//
				Block.box(1.5, 3, 3, 4, 13, 13),
				//
				Block.box(0, 0, 0, 0.5, 16, 16),
				//
				Block.box(0.5, 0.5, 0.5, 1, 15.5, 15.25),
				//
				Block.box(1, 1.5, 1.5, 1.5, 14.5, 14.25),
				//
				Block.box(5, 0, 0, 9, 16, 16)
		//
		).reduce((v1, v2) -> Shapes.or(v1, v2)).get();

		VoxelShapes.registerShape(NuclearScienceBlocks.blockParticleInjector, particleinjector, Direction.WEST);

	}

}
