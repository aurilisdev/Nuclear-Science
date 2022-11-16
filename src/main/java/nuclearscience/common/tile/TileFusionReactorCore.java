package nuclearscience.common.tile;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.common.settings.Constants;
import nuclearscience.registers.NuclearScienceBlockTypes;
import nuclearscience.registers.NuclearScienceBlocks;

public class TileFusionReactorCore extends GenericTile {
	public Property<Integer> deuterium = property(new Property<Integer>(PropertyType.Integer, "deuterium")).set(0).save();
	public Property<Integer> tritium = property(new Property<Integer>(PropertyType.Integer, "tritium")).set(0).save();
	private int timeLeft = 0;

	public TileFusionReactorCore(BlockPos pos, BlockState state) {
		super(NuclearScienceBlockTypes.TILE_FUSIONREACTORCORE.get(), pos, state);
		addComponent(new ComponentDirection());
		addComponent(new ComponentTickable().tickServer(this::tickServer));
		addComponent(new ComponentPacketHandler());
		addComponent(new ComponentElectrodynamic(this).input(Direction.DOWN).input(Direction.UP).maxJoules(Constants.FUSIONREACTOR_USAGE_PER_TICK * 20.0).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * 4));
	}

	public void tickServer(ComponentTickable tick) {
		ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
		if (level.getLevelData().getDayTime() % 20 == 0) {
			this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendCustomPacket();
		}
		if (tritium.get() > 0 && deuterium.get() > 0 && timeLeft <= 0 && electro.getJoulesStored() > Constants.FUSIONREACTOR_USAGE_PER_TICK) {
			deuterium.set(deuterium.get() - 1);
			tritium.set(tritium.get() - 1);
			timeLeft = 15 * 20;
		}
		if (timeLeft > 0 && electro.getJoulesStored() > Constants.FUSIONREACTOR_USAGE_PER_TICK) {
			for (Direction dir : Direction.values()) {
				if (dir != Direction.UP && dir != Direction.DOWN) {
					BlockPos offset = worldPosition.relative(dir);
					BlockState state = level.getBlockState(offset);
					if (state.getBlock() == NuclearScienceBlocks.blockPlasma) {
						BlockEntity tile = level.getBlockEntity(offset);
						if (tile instanceof TilePlasma plasma && plasma.ticksExisted > 30) {
							plasma.ticksExisted = 0;
						}
					} else if (state.getBlock() == Blocks.AIR) {
						level.setBlockAndUpdate(offset, NuclearScienceBlocks.blockPlasma.defaultBlockState());
					}
				}
			}
			electro.joules(electro.getJoulesStored() - Constants.FUSIONREACTOR_USAGE_PER_TICK);
		}
		timeLeft--;
	}
}
