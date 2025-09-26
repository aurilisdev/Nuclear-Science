package nuclearscience.common.tile;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import nuclearscience.api.turbine.ISteamReceiver;
import nuclearscience.common.tags.NuclearScienceTags;
import nuclearscience.registers.NuclearScienceFluids;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.common.network.utils.FluidUtilities;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentFluidHandlerSimple;
import voltaic.prefab.tile.components.type.ComponentPacketHandler;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;

public class TileSteamFunnel extends GenericTile implements ISteamReceiver {

    public static final int INTERNAL_CAPACITY = 10000;
    public static final int MAX_TEMPERATURE = 20000;
    public static final int MAX_PRESSURE = 10;

    public TileSteamFunnel() {
        super(NuclearScienceTiles.TILE_STEAMFUNNEL.get());

        addComponent(new ComponentTickable(this).tickServer(this::tickServer).tickClient(this::tickClient));
        addComponent(new ComponentPacketHandler(this));
        addComponent(new ComponentFluidHandlerSimple(INTERNAL_CAPACITY, this, "storedsteam").setInputDirections(BlockEntityUtils.MachineDirection.BOTTOM).setOutputDirections(BlockEntityUtils.MachineDirection.TOP).setValidFluidTags(NuclearScienceTags.Fluids.STEAM));
    }

    private void tickServer(ComponentTickable tickable) {
        ComponentFluidHandlerSimple handler = getComponent(IComponentType.FluidHandler);
        if (handler.isEmpty()) {
            return;
        }
        TileEntity blockentity = level.getBlockEntity(getBlockPos().above()); 
        if (blockentity instanceof ISteamReceiver) {
            handler.drain(((ISteamReceiver) blockentity).receiveSteam(4200, handler.getFluidAmount()), FluidAction.EXECUTE);
        }
        FluidUtilities.outputToPipe(this, handler.asArray(), Direction.UP);
    }

    private void tickClient(ComponentTickable tickable) {
        ComponentFluidHandlerSimple handler = getComponent(IComponentType.FluidHandler);
        BlockPos above = getBlockPos().above();
        if (!handler.isEmpty() && level.getBlockEntity(above) instanceof ISteamReceiver && level.random.nextInt(3) == 0) {
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
        return ((ComponentFluidHandlerSimple) getComponent(IComponentType.FluidHandler)).fill(new FluidStack(NuclearScienceFluids.FLUID_STEAM.get(), amount), FluidAction.EXECUTE);
    }

    @Override
    public boolean isStillValid() {
        return isRemoved();
    }
    
    @Override
    public ActionResultType use(PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
    	return ActionResultType.PASS;
    }

}
