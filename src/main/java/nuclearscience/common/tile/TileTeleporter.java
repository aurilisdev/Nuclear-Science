package nuclearscience.common.tile;

import java.util.List;

import electrodynamics.api.electricity.CapabilityElectrodynamic;
import electrodynamics.prefab.tile.GenericTileTicking;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import nuclearscience.DeferredRegisters;
import nuclearscience.common.item.ItemFrequencyCard;

public class TileTeleporter extends GenericTileTicking {
    public int xCoord;
    public int yCoord;
    public int zCoord;
    public int cooldown = 0;
    public String world;

    public TileTeleporter() {
	super(DeferredRegisters.TILE_TELEPORTER.get());
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
	    System.out.println(electro.getJoulesStored());
	    if (electro.getJoulesStored() == electro.getMaxJoulesStored()) {
		AABB BB = new AABB(getBlockPos(), getBlockPos().offset(1, 2, 1));
		List<Player> player = getLevel().getEntities(EntityType.PLAYER, BB, en -> true);
		if (!player.isEmpty()) {
		    ServerLevel serverWorld = ItemFrequencyCard.getFromNBT((ServerLevel) getLevel(), world);
		    if (serverWorld == player.get(0).getCommandSenderWorld()) {
			player.get(0).teleportToWithTicket(xCoord, yCoord + 1, zCoord);
			cooldown = 80;
			electro.joules(0);
		    }
		}

	    }
	}
	cooldown--;
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
	if (world != null) {
	    compound.putInt("xCoord", xCoord);
	    compound.putInt("yCoord", yCoord);
	    compound.putInt("zCoord", zCoord);
	    compound.putString("world", world);
	}
	return super.save(compound);
    }

    @Override
    public void load(BlockState state, CompoundTag compound) {
	super.load(state, compound);
	if (compound.contains("world")) {
	    xCoord = compound.getInt("xCoord");
	    yCoord = compound.getInt("yCoord");
	    zCoord = compound.getInt("zCoord");
	    world = compound.getString("world");
	}
    }
}
