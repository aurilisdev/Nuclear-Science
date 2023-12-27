package nuclearscience.common.tile;

import java.util.List;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.server.ServerWorld;
import nuclearscience.common.item.ItemFrequencyCard;
import nuclearscience.registers.NuclearScienceBlockTypes;

public class TileTeleporter extends GenericTile {
	public int xCoord;
	public int yCoord;
	public int zCoord;
	public int cooldown = 0;
	public String world;

	public TileTeleporter() {
		super(NuclearScienceBlockTypes.TILE_TELEPORTER.get());

		addComponent(new ComponentTickable(this).tickServer(this::tickServer));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentElectrodynamic(this, false, true).maxJoules(5000000).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * 4).setInputDirections(Direction.DOWN));

	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return super.getRenderBoundingBox().inflate(3);
	}

	protected void tickServer(ComponentTickable tickable) {
		ComponentElectrodynamic electro = getComponent(IComponentType.Electrodynamic);
		boolean powered = electro.getJoulesStored() > 0;
		if (BlockEntityUtils.isLit(this) ^ powered) {
			BlockEntityUtils.updateLit(this, powered);
		}
		if (cooldown <= 0) {
			cooldown = 20;
			if (electro.getJoulesStored() == electro.getMaxJoulesStored()) {
				AxisAlignedBB aabb = new AxisAlignedBB(getBlockPos(), getBlockPos().offset(1, 2, 1));
				List<PlayerEntity> player = getLevel().getEntities(EntityType.PLAYER, aabb, en -> true);
				if (!player.isEmpty()) {
					ServerWorld serverWorld = ItemFrequencyCard.getFromNBT((ServerWorld) getLevel(), world);
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
	public CompoundNBT save(CompoundNBT compound) { // TODO: Maybe sent information to the client?
		if (world != null) {
			compound.putInt("xCoord", xCoord);
			compound.putInt("yCoord", yCoord);
			compound.putInt("zCoord", zCoord);
			compound.putString("world", world);
		}
		return super.save(compound);
	}

	@Override
	public void load(BlockState state, CompoundNBT compound) {
		super.load(state, compound);
		if (compound.contains("world")) {
			xCoord = compound.getInt("xCoord");
			yCoord = compound.getInt("yCoord");
			zCoord = compound.getInt("zCoord");
			world = compound.getString("world");
		}
	}
}