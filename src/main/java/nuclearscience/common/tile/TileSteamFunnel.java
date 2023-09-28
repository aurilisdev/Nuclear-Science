package nuclearscience.common.tile;

import electrodynamics.api.gas.Gas;
import electrodynamics.api.gas.GasAction;
import electrodynamics.api.gas.GasStack;
import electrodynamics.common.network.utils.GasUtilities;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentGasHandlerSimple;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
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
import nuclearscience.registers.NuclearScienceBlockTypes;

public class TileSteamFunnel extends GenericTile implements ISteamReceiver {

	public static final double INTERNAL_CAPACITY = 10000;
	public static final double MAX_TEMPERATURE = 20000;
	public static final int MAX_PRESSURE = 10;

	public TileSteamFunnel(BlockPos worldPos, BlockState blockState) {
		super(NuclearScienceBlockTypes.TILE_STEAMFUNNEL.get(), worldPos, blockState);
		addComponent(new ComponentDirection(this));
		addComponent(new ComponentTickable(this).tickServer(this::tickServer).tickClient(this::tickClient));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentGasHandlerSimple(this, "storedsteam", INTERNAL_CAPACITY, MAX_TEMPERATURE, MAX_PRESSURE).setInputDirections(Direction.DOWN).setOutputDirections(Direction.UP).setValidFluidTags(ElectrodynamicsTags.Gases.STEAM));
	}

	private void tickServer(ComponentTickable tickable) {
		ComponentGasHandlerSimple handler = getComponent(ComponentType.GasHandler);
		if (handler.isEmpty()) {
			return;
		}
		if (level.getBlockEntity(getBlockPos().above()) instanceof ISteamReceiver receiver) {
			handler.drain(receiver.receiveSteam(handler.getGas().getTemperature(), handler.getGasAmount()), GasAction.EXECUTE);
		}
		GasUtilities.outputToPipe(this, handler.asArray(), Direction.UP);
	}

	private void tickClient(ComponentTickable tickable) {
		ComponentGasHandlerSimple handler = getComponent(ComponentType.GasHandler);
		BlockPos above = getBlockPos().above();
		if (!handler.isEmpty() && level.getBlockEntity(above) instanceof ISteamReceiver receiver && level.random.nextInt(3) == 0) {
			double offsetFX = above.getX() + level.random.nextDouble() / 2.0 * (level.random.nextBoolean() ? -1 : 1);
			double offsetFY = above.getY() + level.random.nextDouble() / 2.0 * (level.random.nextBoolean() ? -1 : 1);
			double offsetFZ = above.getZ() + level.random.nextDouble() / 2.0 * (level.random.nextBoolean() ? -1 : 1);

			level.addParticle(ParticleTypes.SMOKE, offsetFX + 0.5D, offsetFY + 0.5D, offsetFZ + 0.5D, 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public double receiveSteam(double temperature, double amount) {
		if (level.isClientSide()) {
			return 0;
		}
		return ((ComponentGasHandlerSimple) getComponent(ComponentType.GasHandler)).fill(new GasStack(ElectrodynamicsGases.STEAM.get(), amount, temperature, Gas.PRESSURE_AT_SEA_LEVEL), GasAction.EXECUTE);
	}

	@Override
	public boolean isStillValid() {
		return isRemoved();
	}

	@Override
	public InteractionResult use(Player arg0, InteractionHand arg1, BlockHitResult arg2) {
		return InteractionResult.FAIL;
	}

}
