package nuclearscience.common.tile;

import java.util.List;
import java.util.function.Function;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import electrodynamics.prefab.utilities.NBTUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.server.ServerLifecycleHooks;
import nuclearscience.registers.NuclearScienceBlockTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TileTeleporter extends GenericTile {

	private static final DimensionManager MANAGER = new DimensionManager();
	
	public final Property<BlockPos> destination = property(new Property<>(PropertyType.BlockPos, "location", getBlockPos()));
	public final Property<Integer> cooldown = property(new Property<>(PropertyType.Integer, "cooldown", 0));

	public ResourceKey<Level> dimension = getLevel().dimension();

	public TileTeleporter(BlockPos pos, BlockState state) {
		super(NuclearScienceBlockTypes.TILE_TELEPORTER.get(), pos, state);
		addComponent(new ComponentDirection(this));
		addComponent(new ComponentTickable(this).tickServer(this::tickServer));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentElectrodynamic(this).maxJoules(5000000).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * 4).input(Direction.DOWN));

	}

	@Override
	public AABB getRenderBoundingBox() {
		return super.getRenderBoundingBox().inflate(3);
	}

	protected void tickServer(ComponentTickable tickable) {

		ComponentElectrodynamic electro = getComponent(ComponentType.Electrodynamic);

		boolean powered = electro.getJoulesStored() > 0;

		if (BlockEntityUtils.isLit(this) ^ powered) {
			BlockEntityUtils.updateLit(this, powered);
		}

		if (destination.get().equals(getBlockPos()) || electro.getJoulesStored() < electro.getMaxJoulesStored()) {
			return;
		}

		if (cooldown.get() > 0) {
			cooldown.set(cooldown.get() - 1);
			return;
		}

		AABB entityCheckArea = new AABB(getBlockPos(), getBlockPos().offset(1, 2, 1));

		List<Player> players = getLevel().getEntities(EntityType.PLAYER, entityCheckArea, en -> true);

		if (players.isEmpty()) {
			cooldown.set(5);
			return;
		}

		ServerLevel destinationLevel = getDestinationLevel();
		
		Player player = players.get(0);
			
		player.changeDimension(destinationLevel, MANAGER);
		
		BlockPos destPos = destination.get();
		
		player.teleportToWithTicket(destPos.getX() + 0.5, destPos.getY() + 1.0, destPos.getZ() + 0.5);	
			
		cooldown.set(80);
		
		electro.joules(0);

	}
	
	private ServerLevel getDestinationLevel() {
		ServerLevel level = ServerLifecycleHooks.getCurrentServer().getLevel(dimension);
		if(level == null) {
			return (ServerLevel) getLevel();
		}
		return level;
	}

	@Override
	public void saveAdditional(@NotNull CompoundTag compound) { 
		compound.put(NBTUtils.DIMENSION, NBTUtils.writeDimensionToTag(dimension));
		super.saveAdditional(compound);
	}

	@Override
	public void load(@NotNull CompoundTag compound) {
		dimension = NBTUtils.readDimensionFromTag(compound.getCompound(NBTUtils.DIMENSION));
		super.load(compound);
	}

	private static final class DimensionManager implements ITeleporter {

		@Override
		public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
			return repositionEntity.apply(false);
		}

		@Override
		public @Nullable PortalInfo getPortalInfo(Entity entity, ServerLevel destWorld, Function<ServerLevel, PortalInfo> defaultPortalInfo) {
			return new PortalInfo(entity.position(), Vec3.ZERO, entity.getYRot(), entity.getXRot());
		}

		@Override
		public boolean isVanilla() {
			return false;
		}

		@Override
		public boolean playTeleportSound(ServerPlayer player, ServerLevel sourceWorld, ServerLevel destWorld) {
			return false;
		}

	}
}
