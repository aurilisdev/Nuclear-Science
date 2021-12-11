package nuclearscience.common.tile;

import java.util.List;

import electrodynamics.api.capability.electrodynamic.CapabilityElectrodynamic;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.item.ItemFrequencyCard;

public class TileTeleporter extends GenericTile {
    public int xCoord;
    public int yCoord;
    public int zCoord;
    public int cooldown = 0;
    public String world;

    public TileTeleporter(BlockPos pos, BlockState state) {
	super(DeferredRegisters.TILE_TELEPORTER.get(), pos, state);
	addComponent(new ComponentDirection());
	addComponent(new ComponentTickable().tickServer(this::tickServer));
	addComponent(new ComponentPacketHandler());
	addComponent(
		new ComponentElectrodynamic(this).maxJoules(5000000).voltage(CapabilityElectrodynamic.DEFAULT_VOLTAGE * 4).input(Direction.DOWN));

    }

    @Override
    public AABB getRenderBoundingBox() {
	return super.getRenderBoundingBox().inflate(3);
    }

    protected void tickServer(ComponentTickable tickable) {
	ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);
	if (tickable.getTicks() % (electro.getJoulesStored() == 0 ? 40 : 15) == 0) {
	    this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
	}
	if (cooldown <= 0) {
	    cooldown = 20;
	    if (electro.getJoulesStored() == electro.getMaxJoulesStored()) {
		AABB aabb = new AABB(getBlockPos(), getBlockPos().offset(1, 2, 1));
		List<Player> player = getLevel().getEntities(EntityType.PLAYER, aabb, en -> true);
		if (!player.isEmpty()) {
		    ServerLevel serverWorld = ItemFrequencyCard.getFromNBT((ServerLevel) getLevel(), world);
		    if (serverWorld == player.get(0).getCommandSenderWorld()) {
			player.get(0).teleportToWithTicket(xCoord, yCoord + 1.0, zCoord);
			cooldown = 80;
			electro.joules(0);
		    }
		}

	    }
	}
	cooldown--;
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
	if (world != null) {
	    compound.putInt("xCoord", xCoord);
	    compound.putInt("yCoord", yCoord);
	    compound.putInt("zCoord", zCoord);
	    compound.putString("world", world);
	}
	super.saveAdditional(compound);
    }

    @Override
    public void load(CompoundTag compound) {
	super.load(compound);
	if (compound.contains("world")) {
	    xCoord = compound.getInt("xCoord");
	    yCoord = compound.getInt("yCoord");
	    zCoord = compound.getInt("zCoord");
	    world = compound.getString("world");
	}
    }
}
