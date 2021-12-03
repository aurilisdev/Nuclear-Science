package nuclearscience.common.tile;

import electrodynamics.api.sound.SoundAPI;
import electrodynamics.common.network.ElectricityUtilities;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import nuclearscience.DeferredRegisters;
import nuclearscience.SoundRegister;
import nuclearscience.common.block.BlockTurbine;

public class TileTurbine extends GenericTile {

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
    public AABB getRenderBoundingBox() {
	return isCore ? super.getRenderBoundingBox().inflate(1, 0, 1) : super.getRenderBoundingBox();
    }

    public TileTurbine(BlockPos pos, BlockState state) {
	super(DeferredRegisters.TILE_TURBINE.get(), pos, state);
	addComponent(new ComponentTickable().tickServer(this::tickServer).tickClient(this::tickClient));
	addComponent(new ComponentPacketHandler().customPacketWriter(this::writeCustomPacket).customPacketReader(this::readCustomPacket));
	addComponent(new ComponentElectrodynamic(this).output(Direction.UP).setCapabilityTest(() -> (!hasCore || isCore)));
    }

    public void constructStructure() {
	int radius = 1;
	for (int i = -radius; i <= radius; i++) {
	    for (int j = -radius; j <= radius; j++) {
		if (i != 0 || j != 0) {
		    BlockEntity tile = level.getBlockEntity(new BlockPos(worldPosition.getX() + i, worldPosition.getY(), worldPosition.getZ() + j));
		    if (tile instanceof TileTurbine turbine ? turbine.hasCore : true) {
			return;
		    }
		}
	    }
	}
	isCore = true;
	for (int i = -radius; i <= radius; i++) {
	    for (int j = -radius; j <= radius; j++) {
		BlockPos offset = new BlockPos(worldPosition.getX() + i, worldPosition.getY(), worldPosition.getZ() + j);
		((TileTurbine) level.getBlockEntity(offset)).addToStructure(this);
		BlockState state = level.getBlockState(offset);
		level.setBlockAndUpdate(offset, state.setValue(BlockTurbine.RENDER, false));
	    }
	}
    }

    public void deconstructStructure() {
	if (isCore) {
	    int radius = 1;
	    for (int i = -radius; i <= radius; i++) {
		for (int j = -radius; j <= radius; j++) {
		    if (i != 0 || j != 0) {
			BlockPos offset = new BlockPos(worldPosition.getX() + i, worldPosition.getY(), worldPosition.getZ() + j);
			BlockEntity tile = level.getBlockEntity(offset);
			if (tile instanceof TileTurbine turbine) {
			    turbine.hasCore = false;
			    turbine.coreLocation = new BlockPos(0, 0, 0);
			    BlockState state = level.getBlockState(offset);
			    if (state.hasProperty(BlockTurbine.RENDER)) {
				level.setBlockAndUpdate(offset, state.setValue(BlockTurbine.RENDER, true));
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
		level.setBlockAndUpdate(worldPosition, getBlockState().setValue(BlockTurbine.RENDER, true));
	    }
	} else if (hasCore) {
	    TileTurbine core = (TileTurbine) level.getBlockEntity(coreLocation);
	    if (core != null) {
		core.deconstructStructure();
	    }
	}
	this.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendCustomPacket();

    }

    protected void addToStructure(TileTurbine core) {
	coreLocation = core.worldPosition;
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
	    BlockEntity core = level.getBlockEntity(coreLocation);
	    if (core instanceof TileTurbine turbine && ((TileTurbine) core).isCore) {
		turbine.addSteam(this.steam, temp);
		this.steam = 0;
	    }
	}
    }

    public void tickServer(ComponentTickable tickable) {
	this.<ComponentElectrodynamic>getComponent(ComponentType.Electrodynamic).voltage(currentVoltage);
	if (output == null) {
	    output = new CachedTileOutput(level, worldPosition.relative(Direction.UP));
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
	    SoundAPI.playSound(SoundRegister.SOUND_TURBINE.get(), SoundSource.BLOCKS, 1, 1, worldPosition);
	}
    }

    public void writeCustomPacket(CompoundTag tag) {
	tag.putInt("spinSpeed", spinSpeed);
	tag.putBoolean("hasCore", hasCore);
	tag.putBoolean("isCore", isCore);
    }

    public void readCustomPacket(CompoundTag nbt) {
	spinSpeed = nbt.getInt("spinSpeed");
	hasCore = nbt.getBoolean("hasCore");
	isCore = nbt.getBoolean("isCore");
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
	compound.putBoolean("hasCore", hasCore);
	compound.putBoolean("isCore", isCore);
	compound.putInt("coreX", coreLocation.getX());
	compound.putInt("coreY", coreLocation.getY());
	compound.putInt("coreZ", coreLocation.getZ());
	super.saveAdditional(compound);
    }

    @Override
    public void load(CompoundTag compound) {
	super.load(compound);
	hasCore = compound.getBoolean("hasCore");
	isCore = compound.getBoolean("isCore");
	coreLocation = new BlockPos(compound.getInt("coreX"), compound.getInt("coreY"), compound.getInt("coreZ"));
    }

}
