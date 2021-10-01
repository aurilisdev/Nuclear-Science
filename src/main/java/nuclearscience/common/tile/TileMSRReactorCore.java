package nuclearscience.common.tile;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.prefab.tile.GenericTileTicking;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.object.Location;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import nuclearscience.DeferredRegisters;
import nuclearscience.api.network.moltensalt.IMoltenSaltPipe;
import nuclearscience.api.radiation.RadiationSystem;
import nuclearscience.common.inventory.container.ContainerMSRReactorCore;
import nuclearscience.common.network.MoltenSaltNetwork;

//TODO: Rename from bloody MSRReactor everywhere to MSReactor as MSRReactor now means Molten-Salt-ReactorReactor
public class TileMSRReactorCore extends GenericTileTicking {
    public static final int MELTDOWN_TEMPERATURE = 900;
    public static final double FUEL_CAPACITY = 1000;
    public static final double FUEL_USAGE_RATE = 0.01;
    public double temperature = TileReactorCore.AIR_TEMPERATURE;
    public int ticksOverheating = 0;
    public double currentFuel = 0;

    public TileMSRReactorCore() {
	super(DeferredRegisters.TILE_MSRREACTORCORE.get());
	addComponent(new ComponentDirection());
	addComponent(new ComponentTickable().tickServer(this::tickServer).tickClient(this::tickClient));
	addComponent(new ComponentPacketHandler().customPacketReader(this::readCustomPacket).customPacketWriter(this::writeCustomPacket)
		.guiPacketReader(this::readCustomPacket).guiPacketWriter(this::writeCustomPacket));
	addComponent(new ComponentContainerProvider("container.msrreactorcore")
		.createMenu((id, player) -> new ContainerMSRReactorCore(id, player, null, getCoordsArray())));
    }

    protected void writeCustomPacket(CompoundNBT tag) {
	tag.putDouble("temperature", temperature);
	tag.putDouble("currentFuel", currentFuel);
    }

    protected void readCustomPacket(CompoundNBT nbt) {
	temperature = nbt.getDouble("temperature");
	currentFuel = nbt.getDouble("currentFuel");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
	writeCustomPacket(compound);
	return super.write(compound);
    }

    @Override
    public void read(BlockState state, CompoundNBT compound) {
	readCustomPacket(compound);
	super.read(state, compound);
    }

    protected void tickServer(ComponentTickable tick) {
	if (currentFuel > 0) {
	    double change = (temperature - TileReactorCore.AIR_TEMPERATURE) / 3000.0 + (temperature - TileReactorCore.AIR_TEMPERATURE) / 5000.0;
	    if (change != 0) {
		temperature -= change < 0.001 && change > 0 ? 0.001 : change > -0.001 && change < 0 ? -0.001 : change;
	    }
	    int insertion = 0;
	    for (Direction dir : Direction.values()) {
		if (dir != Direction.UP && dir != Direction.DOWN) {
		    TileEntity tile = world.getTileEntity(getPos().offset(dir));
		    if (tile instanceof TileControlRodAssembly) {
			TileControlRodAssembly control = (TileControlRodAssembly) tile;
			if (control.dir == dir.getOpposite()) {
			    insertion += control.insertion;
			}
		    }
		}
	    }
	    if (world.getWorldInfo().getGameTime() % 10 == 0) {
		Location source = new Location(pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f);
		double totstrength = temperature * 7
			* Math.pow(2, Math.pow(temperature / (MELTDOWN_TEMPERATURE), temperature > MELTDOWN_TEMPERATURE ? 5 : 4));
		double range = Math.sqrt(totstrength) / (5 * Math.sqrt(2)) * 2;
		AxisAlignedBB bb = AxisAlignedBB.withSizeAtOrigin(range, range, range);
		bb = bb.offset(new Vector3d(source.x(), source.y(), source.z()));
		List<LivingEntity> list = world.getEntitiesWithinAABB(LivingEntity.class, bb);
		for (LivingEntity living : list) {
		    RadiationSystem.applyRadiation(living, source, totstrength);
		}
	    }
	    double insertDecimal = (100 - insertion) / 100.0;
	    currentFuel -= FUEL_USAGE_RATE * insertDecimal * Math.pow(2, Math.pow(temperature / (MELTDOWN_TEMPERATURE - 100), 4));
	    temperature += (MELTDOWN_TEMPERATURE * insertDecimal * (1.2 + world.rand.nextDouble() / 5.0) - temperature) / 200;
	    TileEntity above = world.getTileEntity(pos.up());
	    if (above instanceof IMoltenSaltPipe) {
		MoltenSaltNetwork net = (MoltenSaltNetwork) ((IMoltenSaltPipe) above).getNetwork();
		net.emit(temperature, new ArrayList<>(), false);
	    }
	    if (tick.getTicks() % 3 == 0) {
		this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
	    }
	} else if (tick.getTicks() % 50 == 0) {
	    this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
	}
    }

    protected void tickClient(ComponentTickable tick) {
    }
}