package nuclearscience.common.tile;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.common.block.VoxelShapes;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.settings.Constants;

public class TileFusionReactorCore extends GenericTile {
	public int deuterium;
	public int tritium;
	private int timeLeft = 0;

	public TileFusionReactorCore(BlockPos pos, BlockState state) {
		super(DeferredRegisters.TILE_FUSIONREACTORCORE.get(), pos, state);
		addComponent(new ComponentTickable().tickServer(this::tickServer));
		addComponent(new ComponentPacketHandler().customPacketReader(this::readCustomPacket).customPacketWriter(this::writeCustomPacket));
		addComponent(new ComponentElectrodynamic(this).input(Direction.DOWN).input(Direction.UP).maxJoules(Constants.FUSIONREACTOR_USAGE_PER_TICK * 20.0).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * 4));
	}

	public void tickServer(ComponentTickable tick) {
		ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
		if (level.getLevelData().getDayTime() % 20 == 0) {
			this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendCustomPacket();
		}
		if (tritium > 0 && deuterium > 0 && timeLeft <= 0 && electro.getJoulesStored() > Constants.FUSIONREACTOR_USAGE_PER_TICK) {
			deuterium -= 1;
			tritium -= 1;
			timeLeft = 15 * 20;
		}
		if (timeLeft > 0 && electro.getJoulesStored() > Constants.FUSIONREACTOR_USAGE_PER_TICK) {
			for (Direction dir : Direction.values()) {
				if (dir != Direction.UP && dir != Direction.DOWN) {
					BlockPos offset = worldPosition.relative(dir);
					BlockState state = level.getBlockState(offset);
					if (state.getBlock() == DeferredRegisters.blockPlasma) {
						BlockEntity tile = level.getBlockEntity(offset);
						if (tile instanceof TilePlasma plasma && plasma.ticksExisted > 30) {
							plasma.ticksExisted = 0;
						}
					} else if (state.getBlock() == Blocks.AIR) {
						level.setBlockAndUpdate(offset, DeferredRegisters.blockPlasma.defaultBlockState());
					}
				}
			}
			electro.joules(electro.getJoulesStored() - Constants.FUSIONREACTOR_USAGE_PER_TICK);
		}
		timeLeft--;
	}

	@Override
	public void saveAdditional(CompoundTag compound) {
		writeCustomPacket(compound);
		super.saveAdditional(compound);
	}

	@Override
	public void load(CompoundTag compound) {
		super.load(compound);
		readCustomPacket(compound);
	}

	public void writeCustomPacket(CompoundTag nbt) {
		nbt.putInt("deuterium", deuterium);
		nbt.putInt("tritium", tritium);
	}

	public void readCustomPacket(CompoundTag nbt) {
		deuterium = nbt.getInt("deuterium");
		tritium = nbt.getInt("tritium");
	}

	static {
		VoxelShape shape = Shapes.empty();
		shape = Shapes.join(shape, Shapes.box(0, 0.453125, 0.4375, 1, 0.578125, 0.5625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0, 0.484375, 0.46875, 1, 0.546875, 0.53125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0, 0.484375, 0.46875, 1, 0.546875, 0.53125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0, 0.484375, 0.46875, 1, 0.546875, 0.53125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0, 0.484375, 0.46875, 1, 0.546875, 0.53125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.4375, 0.453125, 0, 0.5625, 0.578125, 1), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0, 0.484375, 0.46875, 1, 0.546875, 0.53125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.46875, 0.484375, 0, 0.53125, 0.546875, 1), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.46875, 0.484375, 0, 0.53125, 0.546875, 1), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.46875, 0.484375, 0, 0.53125, 0.546875, 1), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.46875, 0.484375, 0, 0.53125, 0.546875, 1), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.46875, 0.484375, 0, 0.53125, 0.546875, 1), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.441569375, 0.2484375, 0.20625, 0.558430625, 0.7515625, 0.79375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.43784, 0.25, 0.1875, 0.56216, 0.75, 0.8125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.441569375, 0.2484375, 0.20625, 0.558430625, 0.7515625, 0.79375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.43784, 0.25, 0.1875, 0.56216, 0.75, 0.8125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.441569375, 0.2484375, 0.20625, 0.558430625, 0.7515625, 0.79375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.1875, 0.25, 0.43784, 0.8125, 0.75, 0.56216), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.20625, 0.2484375, 0.441569375, 0.79375, 0.7515625, 0.558430625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.1875, 0.25, 0.43784, 0.8125, 0.75, 0.56216), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.453255625, 0.1234375, 0.265, 0.546744375, 0.8765625, 0.735), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.450271875, 0.125, 0.25, 0.549728125, 0.875, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.453255625, 0.1234375, 0.265, 0.546744375, 0.8765625, 0.735), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.450271875, 0.125, 0.25, 0.549728125, 0.875, 0.75), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.453255625, 0.1234375, 0.265, 0.546744375, 0.8765625, 0.735), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.25, 0.125, 0.450271875, 0.75, 0.875, 0.549728125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.265, 0.1234375, 0.453255625, 0.735, 0.8765625, 0.546744375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.25, 0.125, 0.450271875, 0.75, 0.875, 0.549728125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.46270375, 0.0625, 0.3125, 0.53729625, 0.9375, 0.6875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.46270375, 0.0625, 0.3125, 0.53729625, 0.9375, 0.6875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.46270375, 0.0625, 0.3125, 0.53729625, 0.9375, 0.6875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.46270375, 0.0625, 0.3125, 0.53729625, 0.9375, 0.6875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.46270375, 0.0625, 0.3125, 0.53729625, 0.9375, 0.6875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.3125, 0.0625, 0.46270375, 0.6875, 0.9375, 0.53729625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.3125, 0.0625, 0.46270375, 0.6875, 0.9375, 0.53729625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.3125, 0.0625, 0.46270375, 0.6875, 0.9375, 0.53729625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.429883125, 0.3734375, 0.1475, 0.570116875, 0.6265625, 0.8525), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.425408125, 0.375, 0.125, 0.574591875, 0.625, 0.875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.429883125, 0.3734375, 0.1475, 0.570116875, 0.6265625, 0.8525), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.425408125, 0.375, 0.125, 0.574591875, 0.625, 0.875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.429883125, 0.3734375, 0.1475, 0.570116875, 0.6265625, 0.8525), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.125, 0.375, 0.425408125, 0.875, 0.625, 0.574591875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.1475, 0.3734375, 0.429883125, 0.8525, 0.6265625, 0.570116875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.125, 0.375, 0.425408125, 0.875, 0.625, 0.574591875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.47513625, 0, 0.375, 0.52486375, 1, 0.625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.47513625, 0, 0.375, 0.52486375, 1, 0.625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.47513625, 0, 0.375, 0.52486375, 1, 0.625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.47513625, 0, 0.375, 0.52486375, 1, 0.625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.47513625, 0, 0.375, 0.52486375, 1, 0.625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.375, 0, 0.47513625, 0.625, 1, 0.52486375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.375, 0, 0.47513625, 0.625, 1, 0.52486375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.375, 0, 0.47513625, 0.625, 1, 0.52486375), BooleanOp.OR);
		VoxelShapes.registerShape(DeferredRegisters.blockFusionReactorCore, shape, Direction.EAST);
	}
}
