package nuclearscience.common.tile;

import java.util.List;
import java.util.function.Function;

import javax.annotation.Nullable;

import net.minecraft.block.PortalInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import nuclearscience.common.inventory.container.ContainerTeleporter;
import nuclearscience.common.settings.NuclearConstants;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.*;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.registers.VoltaicCapabilities;

public class TileTeleporter extends GenericTile {
	
	private static final DimensionManager MANAGER = new DimensionManager();

	public final SingleProperty<BlockPos> destination = property(new SingleProperty<>(PropertyTypes.BLOCK_POS, "location", BlockEntityUtils.OUT_OF_REACH));
	public final SingleProperty<Integer> cooldown = property(new SingleProperty<>(PropertyTypes.INTEGER, "cooldown", 0));
	public final SingleProperty<ResourceLocation> dimension = property(new SingleProperty<>(PropertyTypes.RESOURCE_LOCATION, "dimension", World.OVERWORLD.location()));

	public TileTeleporter() {
		super(NuclearScienceTiles.TILE_TELEPORTER.get());

		addComponent(new ComponentTickable(this).tickServer(this::tickServer));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentElectrodynamic(this, false, true).maxJoules(NuclearConstants.TELEPORTER_USAGE_PER_TELEPORT * 20).voltage(VoltaicCapabilities.DEFAULT_VOLTAGE * 4).setInputDirections(BlockEntityUtils.MachineDirection.BOTTOM));
		addComponent(new ComponentInventory(this, ComponentInventory.InventoryBuilder.newInv().inputs(1)));
		addComponent(new ComponentContainerProvider("teleporter", this).createMenu((id, player) -> new ContainerTeleporter(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));

	}
	
	@Override
	public void setLevelAndPosition(World world, BlockPos pos) {
		super.setLevelAndPosition(world, pos);
		destination.setValue(pos);
	}

	protected void tickServer(ComponentTickable tickable) {

		ComponentElectrodynamic electro = getComponent(IComponentType.Electrodynamic);

		boolean powered = electro.getJoulesStored() > NuclearConstants.TELEPORTER_USAGE_PER_TELEPORT;

		if (BlockEntityUtils.isLit(this) ^ powered) {
			BlockEntityUtils.updateLit(this, powered);
		}

		if (destination.getValue().equals(getBlockPos()) || electro.getJoulesStored() < electro.getMaxJoulesStored()) {
			return;
		}

		if (cooldown.getValue() > 0) {
			cooldown.setValue(cooldown.getValue() - 1);
			return;
		}

		AxisAlignedBB entityCheckArea = new AxisAlignedBB(getBlockPos(), getBlockPos().offset(1, 2, 1));

		List<PlayerEntity> players = getLevel().getEntities(EntityType.PLAYER, entityCheckArea, en -> true);

		if (players.isEmpty()) {
			cooldown.setValue(5);
			return;
		}

		ServerWorld destinationLevel = getDestinationLevel();

		PlayerEntity player = players.get(0);
		
		player.changeDimension(destinationLevel, MANAGER);

		BlockPos destPos = destination.getValue();

		player.teleportToWithTicket(destPos.getX() + 0.5, destPos.getY() + 1.0, destPos.getZ() + 0.5);
		
		cooldown.setValue(80);

		electro.joules(electro.getJoulesStored() - NuclearConstants.TELEPORTER_USAGE_PER_TELEPORT);

	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return super.getRenderBoundingBox().inflate(3);
	}

	private ServerWorld getDestinationLevel() {
		ServerWorld level = ServerLifecycleHooks.getCurrentServer().getLevel(RegistryKey.create(Registry.DIMENSION_REGISTRY, dimension.getValue()));
		if (level == null) {
			return (ServerWorld) getLevel();
		}
		return level;
	}
	
	private static final class DimensionManager implements ITeleporter {

		@Override
		public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
			return repositionEntity.apply(false);
		}

		@Override
		public @Nullable PortalInfo getPortalInfo(Entity entity, ServerWorld destWorld, Function<ServerWorld, PortalInfo> defaultPortalInfo) {
			return new PortalInfo(entity.position(), Vector3d.ZERO, entity.yRot, entity.xRot);
		}

		@Override
		public boolean isVanilla() {
			return false;
		}

		@Override
		public boolean playTeleportSound(ServerPlayerEntity player, ServerWorld sourceWorld, ServerWorld destWorld) {
			return false;
		}

	}


}
