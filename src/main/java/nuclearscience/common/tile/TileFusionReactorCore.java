package nuclearscience.common.tile;

import electrodynamics.api.electricity.CapabilityElectrodynamic;
import electrodynamics.prefab.tile.GenericTileTicking;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.settings.Constants;

public class TileFusionReactorCore extends GenericTileTicking {
    public int deuterium;
    public int tritium;
    private int timeLeft = 0;

    public TileFusionReactorCore() {
	super(DeferredRegisters.TILE_FUSIONREACTORCORE.get());
	addComponent(new ComponentTickable().tickServer(this::tickServer));
	addComponent(new ComponentPacketHandler().customPacketReader(this::readCustomPacket).customPacketWriter(this::writeCustomPacket));
	addComponent(new ComponentElectrodynamic(this).input(Direction.DOWN).input(Direction.UP)
		.maxJoules(Constants.FUSIONREACTOR_USAGE_PER_TICK * 20.0).voltage(CapabilityElectrodynamic.DEFAULT_VOLTAGE * 4));
    }

    public void tickServer(ComponentTickable tick) {
	ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
	if (world.getWorldInfo().getDayTime() % 20 == 0) {
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
		    BlockPos offset = pos.offset(dir);
		    BlockState state = world.getBlockState(offset);
		    if (state.getBlock() == DeferredRegisters.blockPlasma) {
			TileEntity tile = world.getTileEntity(offset);
			if (tile instanceof TilePlasma && ((TilePlasma) tile).ticksExisted > 30) {
			    ((TilePlasma) tile).ticksExisted = 0;
			}
		    } else if (state.getBlock() == Blocks.AIR) {
			world.setBlockState(offset, DeferredRegisters.blockPlasma.getDefaultState());
		    }
		}
	    }
	    electro.joules(electro.getJoulesStored() - Constants.FUSIONREACTOR_USAGE_PER_TICK);
	}
	timeLeft--;
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
	writeCustomPacket(compound);
	return super.write(compound);
    }

    @Override
    public void read(BlockState state, CompoundNBT compound) {
	super.read(state, compound);
	readCustomPacket(compound);
    }

    public void writeCustomPacket(CompoundNBT nbt) {
	nbt.putInt("deuterium", deuterium);
	nbt.putInt("tritium", tritium);
    }

    public void readCustomPacket(CompoundNBT nbt) {
	deuterium = nbt.getInt("deuterium");
	tritium = nbt.getInt("tritium");
    }
}
