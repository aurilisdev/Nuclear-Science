package nuclearscience.common.tile;

import electrodynamics.api.sound.SoundAPI;
import electrodynamics.api.utilities.object.CachedTileOutput;
import electrodynamics.api.utilities.object.TransferPack;
import electrodynamics.common.network.ElectricityUtilities;
import electrodynamics.prefab.tile.GenericTileTicking;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import nuclearscience.DeferredRegisters;
import nuclearscience.SoundRegister;
import nuclearscience.common.block.BlockTurbine;

public class TileTurbine extends GenericTileTicking {

    public static final int MAX_STEAM = 3000000;
    public int spinSpeed = 0;
    public boolean isCore;
    protected CachedTileOutput output;
    protected int currentVoltage = 0;
    protected int steam;
    protected int wait = 30;
    protected boolean hasCore;
    protected BlockPos coreLocation = BlockPos.ZERO;

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
	return isCore ? super.getRenderBoundingBox().grow(1, 0, 1) : super.getRenderBoundingBox();
    }

    public TileTurbine() {
	super(DeferredRegisters.TILE_TURBINE.get());
	addComponent(new ComponentTickable().tickServer(this::tickServer).tickClient(this::tickClient));
	addComponent(new ComponentPacketHandler().customPacketWriter(this::writeCustomPacket).customPacketReader(this::readCustomPacket));
	addComponent(new ComponentElectrodynamic(this).output(Direction.UP).setCapabilityTest(() -> (!hasCore || isCore)));
    }

    public void constructStructure() {
	int radius = 1;
	for (int i = -radius; i <= radius; i++) {
	    for (int j = -radius; j <= radius; j++) {
		if (i != 0 || j != 0) {
		    TileEntity tile = world.getTileEntity(new BlockPos(pos.getX() + i, pos.getY(), pos.getZ() + j));
		    if (!(tile instanceof TileTurbine)) {
			return;
		    }
		    TileTurbine turbine = (TileTurbine) tile;
		    if (turbine.hasCore) {
			return;
		    }
		}
	    }
	}
	isCore = true;
	for (int i = -radius; i <= radius; i++) {
	    for (int j = -radius; j <= radius; j++) {
		BlockPos offset = new BlockPos(pos.getX() + i, pos.getY(), pos.getZ() + j);
		((TileTurbine) world.getTileEntity(offset)).addToStructure(this);
		BlockState state = world.getBlockState(offset);
		world.setBlockState(offset, state.with(BlockTurbine.RENDER, false));
	    }
	}
    }

    public void deconstructStructure() {
	if (isCore) {
	    int radius = 1;
	    for (int i = -radius; i <= radius; i++) {
		for (int j = -radius; j <= radius; j++) {
		    if (i != 0 || j != 0) {
			BlockPos offset = new BlockPos(pos.getX() + i, pos.getY(), pos.getZ() + j);
			TileEntity tile = world.getTileEntity(offset);
			if (tile instanceof TileTurbine) {
			    TileTurbine turbine = (TileTurbine) tile;
			    turbine.hasCore = false;
			    turbine.coreLocation = new BlockPos(0, 0, 0);
			    BlockState state = world.getBlockState(offset);
			    if (state.hasProperty(BlockTurbine.RENDER)) {
				world.setBlockState(offset, state.with(BlockTurbine.RENDER, true));
			    }
			}
		    }
		}
	    }
	    isCore = false;
	    hasCore = false;
	    coreLocation = new BlockPos(0, 0, 0);
	    BlockState state = getBlockState();
	    if (state.hasProperty(BlockTurbine.RENDER)) {
		world.setBlockState(pos, getBlockState().with(BlockTurbine.RENDER, true));
	    }
	} else if (hasCore) {
	    TileTurbine core = (TileTurbine) world.getTileEntity(coreLocation);
	    if (core != null) {
		core.deconstructStructure();
	    }
	}
	this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendCustomPacket();

    }

    protected void addToStructure(TileTurbine core) {
	coreLocation = core.pos;
	hasCore = true;
	this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendCustomPacket();
    }

    public void addSteam(int steam, int temp) {
	this.steam = Math.min(MAX_STEAM * (isCore ? 9 : 1), this.steam + steam);
	if (temp < 4300) {
	    currentVoltage = 120;
	} else if (temp < 6000) {
	    currentVoltage = 240;
	} else {
	    currentVoltage = 480;
	}
	if (!isCore && hasCore) {
	    TileEntity core = world.getTileEntity(coreLocation);
	    if (core instanceof TileTurbine && ((TileTurbine) core).isCore) {
		TileTurbine turbine = (TileTurbine) core;
		turbine.addSteam(this.steam, temp);
		this.steam = 0;
	    }
	}
    }

    public void tickServer(ComponentTickable tickable) {
	this.<ComponentElectrodynamic>getComponent(ComponentType.Electrodynamic).voltage(currentVoltage);
	if (output == null) {
	    output = new CachedTileOutput(world, pos.offset(Direction.UP));
	}
	if (tickable.getTicks() % 30 == 0) {
	    this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendCustomPacket();
	    spinSpeed = currentVoltage / 120;
	    output.update();
	}
	if (hasCore && !isCore) {
	    currentVoltage = 0;
	    return;
	}
	if (steam > 0 && currentVoltage > 0) {
	    wait = 30;
	    if (output.valid()) {
		TransferPack transfer = TransferPack.joulesVoltage(steam * (hasCore ? 1.111 : 1), currentVoltage);
		ElectricityUtilities.receivePower(output.getSafe(), Direction.DOWN, transfer, false);
		steam = Math.max(steam - Math.max(75, steam), 0);
	    }
	} else {
	    if (wait <= 0) {
		currentVoltage = 0;
		wait = 30;
	    }
	    wait--;
	}

    }

    public void tickClient(ComponentTickable tickable) {
	if (spinSpeed > 0 && tickable.getTicks() % 200 == 0) {
	    SoundAPI.playSound(SoundRegister.SOUND_TURBINE.get(), SoundCategory.BLOCKS, 1, 1, pos);
	}
    }

    public void writeCustomPacket(CompoundNBT tag) {
	tag.putInt("spinSpeed", spinSpeed);
	tag.putBoolean("hasCore", hasCore);
	tag.putBoolean("isCore", isCore);
    }

    public void readCustomPacket(CompoundNBT nbt) {
	spinSpeed = nbt.getInt("spinSpeed");
	hasCore = nbt.getBoolean("hasCore");
	isCore = nbt.getBoolean("isCore");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
	compound.putBoolean("hasCore", hasCore);
	compound.putBoolean("isCore", isCore);
	compound.putInt("coreX", coreLocation.getX());
	compound.putInt("coreY", coreLocation.getY());
	compound.putInt("coreZ", coreLocation.getZ());
	return super.write(compound);
    }

    @Override
    public void read(BlockState state, CompoundNBT compound) {
	super.read(state, compound);
	hasCore = compound.getBoolean("hasCore");
	isCore = compound.getBoolean("isCore");
	coreLocation = new BlockPos(compound.getInt("coreX"), compound.getInt("coreY"), compound.getInt("coreZ"));
    }

}
