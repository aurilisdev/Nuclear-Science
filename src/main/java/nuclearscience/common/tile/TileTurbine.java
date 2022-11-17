package nuclearscience.common.tile;

import electrodynamics.api.sound.SoundAPI;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyType;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.ElectricityUtils;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import nuclearscience.common.block.BlockTurbine;
import nuclearscience.registers.NuclearScienceBlockTypes;
import nuclearscience.registers.NuclearScienceSounds;

public class TileTurbine extends GenericTile {

	public static final int MAX_STEAM = 3000000;
	public Property<Integer> spinSpeed = property(new Property<Integer>(PropertyType.Integer, "spinSpeed")).set(0);
	public Property<Boolean> hasCore = property(new Property<Boolean>(PropertyType.Boolean, "hasCore")).set(false).save();
	public Property<Boolean> isCore = property(new Property<Boolean>(PropertyType.Boolean, "isCore")).set(false).save();
	public Property<BlockPos> coreLocation = property(new Property<BlockPos>(PropertyType.BlockPos, "coreLocation")).set(BlockPos.ZERO).save();
	protected CachedTileOutput output;
	protected int currentVoltage = 0;
	protected int steam;
	protected int wait = 30;

	@Override
	public AABB getRenderBoundingBox() {
		return isCore.get() ? super.getRenderBoundingBox().inflate(1, 0, 1) : super.getRenderBoundingBox();
	}

	public TileTurbine(BlockPos pos, BlockState state) {
		super(NuclearScienceBlockTypes.TILE_TURBINE.get(), pos, state);
		addComponent(new ComponentTickable().tickServer(this::tickServer).tickClient(this::tickClient));
		addComponent(new ComponentPacketHandler());
		addComponent(new ComponentElectrodynamic(this).output(Direction.UP).setCapabilityTest(() -> (!hasCore.get() || isCore.get())));
	}

	public void constructStructure() {
		int radius = 1;
		for (int i = -radius; i <= radius; i++) {
			for (int j = -radius; j <= radius; j++) {
				if (i != 0 || j != 0) {
					BlockEntity tile = level.getBlockEntity(new BlockPos(worldPosition.getX() + i, worldPosition.getY(), worldPosition.getZ() + j));
					if (tile instanceof TileTurbine turbine ? turbine.hasCore.get() : true) {
						return;
					}
				}
			}
		}
		isCore.set(true);
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
		if (isCore.get()) {
			int radius = 1;
			for (int i = -radius; i <= radius; i++) {
				for (int j = -radius; j <= radius; j++) {
					if (i != 0 || j != 0) {
						BlockPos offset = new BlockPos(worldPosition.getX() + i, worldPosition.getY(), worldPosition.getZ() + j);
						BlockEntity tile = level.getBlockEntity(offset);
						if (tile instanceof TileTurbine turbine) {
							turbine.hasCore.set(false);
							turbine.coreLocation.set(new BlockPos(0, 0, 0));
							BlockState state = level.getBlockState(offset);
							if (state.hasProperty(BlockTurbine.RENDER)) {
								level.setBlockAndUpdate(offset, state.setValue(BlockTurbine.RENDER, true));
							}
						}
					}
				}
			}
			isCore.set(false);
			hasCore.set(false);
			coreLocation.set(BlockPos.ZERO);
			BlockState state = getBlockState();
			if (state.hasProperty(BlockTurbine.RENDER)) {
				level.setBlockAndUpdate(worldPosition, getBlockState().setValue(BlockTurbine.RENDER, true));
			}
		} else if (hasCore.get()) {
			TileTurbine core = (TileTurbine) level.getBlockEntity(coreLocation.get());
			if (core != null) {
				core.deconstructStructure();
			}
		}

	}

	protected void addToStructure(TileTurbine core) {
		coreLocation.set(core.worldPosition);
		hasCore.set(true);
	}

	public void addSteam(int steam, int temp) {
		this.steam = Math.min(MAX_STEAM * (isCore.get() ? 9 : 1), this.steam + steam);
		if (temp < 4300) {
			currentVoltage = 120;
		} else if (temp < 6000) {
			currentVoltage = 240;
		} else {
			currentVoltage = 480;
		}
		if (!isCore.get() && hasCore.get()) {
			BlockEntity core = level.getBlockEntity(coreLocation.get());
			if (core instanceof TileTurbine turbine && ((TileTurbine) core).isCore.get()) {
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
		spinSpeed.set(currentVoltage / 120);
		output.update(worldPosition.relative(Direction.UP));
		if (hasCore.get() && !isCore.get()) {
			currentVoltage = 0;
			return;
		}
		if (steam > 0 && currentVoltage > 0) {
			wait = 30;
			if (output.valid()) {
				TransferPack transfer = TransferPack.joulesVoltage(steam * (hasCore.get() ? 1.111 : 1), currentVoltage);
				ElectricityUtils.receivePower(output.getSafe(), Direction.DOWN, transfer, false);
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
		if (spinSpeed.get() > 0 && tickable.getTicks() % 200 == 0) {
			SoundAPI.playSound(NuclearScienceSounds.SOUND_TURBINE.get(), SoundSource.BLOCKS, 1, 1, worldPosition);
		}
	}
}
