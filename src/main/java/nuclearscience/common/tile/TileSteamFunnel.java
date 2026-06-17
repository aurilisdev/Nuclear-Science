package nuclearscience.common.tile;

import electrodynamics.registers.ElectrodynamicsGases;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import nuclearscience.api.turbine.ISteamReceiver;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.api.gas.Gas;
import voltaic.api.gas.GasAction;
import voltaic.api.gas.GasStack;
import voltaic.common.network.utils.GasUtilities;
import voltaic.common.tags.VoltaicTags;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentGasHandlerSimple;
import voltaic.prefab.tile.components.type.ComponentPacketHandler;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;

public class TileSteamFunnel extends GenericTile implements ISteamReceiver {

    public static final int INTERNAL_CAPACITY = 10000;
    public static final int MAX_TEMPERATURE = 20000;
    public static final int MAX_PRESSURE = 10;

    public TileSteamFunnel(BlockPos worldPos, BlockState blockState) {
	super(NuclearScienceTiles.TILE_STEAMFUNNEL.get(), worldPos, blockState);

	addComponent(new ComponentTickable(this).tickServer(this::tickServer).tickClient(this::tickClient));
	addComponent(new ComponentPacketHandler(this));
	addComponent(
		new ComponentGasHandlerSimple(this, "storedsteam", INTERNAL_CAPACITY, MAX_TEMPERATURE, MAX_PRESSURE)
			.setInputDirections(BlockEntityUtils.MachineDirection.BOTTOM)
			.setOutputDirections(BlockEntityUtils.MachineDirection.TOP)
			.setValidFluidTags(VoltaicTags.Gases.STEAM));
    }

    private void tickServer(ComponentTickable tickable) {
	ComponentGasHandlerSimple handler = getComponent(IComponentType.GasHandler);
	if (handler.isEmpty()) {
	    return;
	}
	if (level.getBlockEntity(getBlockPos().above()) instanceof ISteamReceiver receiver) {
	    handler.drain(receiver.receiveSteam(handler.getGas().getTemperature(), handler.getGasAmount()),
		    GasAction.EXECUTE);
	}
	GasUtilities.outputToPipe(this, handler.asArray(), Direction.UP);
    }

    private void tickClient(ComponentTickable tickable) {
	ComponentGasHandlerSimple handler = getComponent(IComponentType.GasHandler);
	BlockPos above = getBlockPos().above();
	if (!handler.isEmpty() && level.getBlockEntity(above) instanceof ISteamReceiver
		&& level.random.nextInt(3) == 0) {
	    double offsetFX = above.getX() + level.random.nextDouble() / 2.0 * (level.random.nextBoolean() ? -1 : 1);
	    double offsetFY = above.getY() + level.random.nextDouble() / 2.0 * (level.random.nextBoolean() ? -1 : 1);
	    double offsetFZ = above.getZ() + level.random.nextDouble() / 2.0 * (level.random.nextBoolean() ? -1 : 1);

	    level.addParticle(ParticleTypes.SMOKE, offsetFX + 0.5D, offsetFY + 0.5D, offsetFZ + 0.5D, 0.0D, 0.0D, 0.0D);
	}
    }

    @Override
    public int receiveSteam(int temperature, int amount) {
	if (level.isClientSide()) {
	    return 0;
	}
	return ((ComponentGasHandlerSimple) getComponent(IComponentType.GasHandler)).fill(
		new GasStack(ElectrodynamicsGases.STEAM.get(), amount, temperature, Gas.PRESSURE_AT_SEA_LEVEL),
		GasAction.EXECUTE);
    }

    @Override
    public boolean isStillValid() {
	return isRemoved();
    }

    @Override
    public InteractionResult use(Player player, InteractionHand hand, BlockHitResult hit) {
	return InteractionResult.PASS;
    }

}
