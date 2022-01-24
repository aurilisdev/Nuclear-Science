package nuclearscience.common.tile;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
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
}
