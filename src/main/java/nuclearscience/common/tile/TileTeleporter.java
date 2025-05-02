package nuclearscience.common.tile;

import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import nuclearscience.common.inventory.container.ContainerTeleporter;
import nuclearscience.common.settings.NuclearConstants;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.tile.components.type.ComponentInventory;
import voltaic.prefab.tile.components.type.ComponentPacketHandler;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.registers.VoltaicCapabilities;

public class TileTeleporter extends GenericTile {

	public final SingleProperty<BlockPos> destination = property(new SingleProperty<>(PropertyTypes.BLOCK_POS, "location", getBlockPos()));
	public final SingleProperty<Integer> cooldown = property(new SingleProperty<>(PropertyTypes.INTEGER, "cooldown", 0));
	public final SingleProperty<ResourceLocation> dimension = property(new SingleProperty<>(PropertyTypes.RESOURCE_LOCATION, "dimension", Level.OVERWORLD.location()));

	public TileTeleporter(BlockPos pos, BlockState state) {
		super(NuclearScienceTiles.TILE_TELEPORTER.get(), pos, state);

		addComponent(new ComponentTickable(this).tickServer(this::tickServer));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentElectrodynamic(this, false, true).maxJoules(NuclearConstants.TELEPORTER_USAGE_PER_TELEPORT * 20).voltage(VoltaicCapabilities.DEFAULT_VOLTAGE * 4).setInputDirections(BlockEntityUtils.MachineDirection.BOTTOM));
		addComponent(new ComponentInventory(this, ComponentInventory.InventoryBuilder.newInv().inputs(1)));
		addComponent(new ComponentContainerProvider("teleporter", this).createMenu((id, player) -> new ContainerTeleporter(id, player, getComponent(IComponentType.Inventory), getCoordsArray())));

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

		AABB entityCheckArea = AABB.encapsulatingFullBlocks(getBlockPos(), getBlockPos().offset(1, 2, 1));

		List<Player> players = getLevel().getEntities(EntityType.PLAYER, entityCheckArea, en -> true);

		if (players.isEmpty()) {
			cooldown.setValue(5);
			return;
		}

		ServerLevel destinationLevel = getDestinationLevel();

		Player player = players.get(0);

		BlockPos destPos = destination.getValue();

		player.changeDimension(new DimensionTransition(destinationLevel, new Vec3(destPos.getX(), destPos.getY(), destPos.getZ()), Vec3.ZERO, player.getXRot(), player.getYRot(), false, DimensionTransition.PLACE_PORTAL_TICKET));

		cooldown.setValue(80);

		electro.joules(electro.getJoulesStored() - NuclearConstants.TELEPORTER_USAGE_PER_TELEPORT);

	}

	private ServerLevel getDestinationLevel() {
		ServerLevel level = ServerLifecycleHooks.getCurrentServer().getLevel(ResourceKey.create(Registries.DIMENSION, dimension.getValue()));
		if (level == null) {
			return (ServerLevel) getLevel();
		}
		return level;
	}


}
