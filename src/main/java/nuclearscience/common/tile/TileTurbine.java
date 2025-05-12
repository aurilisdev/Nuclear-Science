package nuclearscience.common.tile;

import javax.annotation.Nonnull;

import electrodynamics.prefab.utilities.ElectricityUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import nuclearscience.api.turbine.ISteamReceiver;
import nuclearscience.common.block.BlockTurbine;
import nuclearscience.registers.NuclearScienceSounds;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.sound.ITickableSound;
import voltaic.prefab.sound.SoundBarrierMethods;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.tile.components.type.ComponentPacketHandler;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;
import voltaic.prefab.utilities.object.CachedTileOutput;
import voltaic.prefab.utilities.object.TransferPack;

public class TileTurbine extends GenericTile implements ITickableSound, ISteamReceiver {

	public static final int MAX_STEAM = 3000000;
	public SingleProperty<Integer> spinSpeed = property(new SingleProperty<>(PropertyTypes.INTEGER, "spinSpeed", 0));
	public SingleProperty<Boolean> hasCore = property(new SingleProperty<>(PropertyTypes.BOOLEAN, "hasCore", false));
	public SingleProperty<Boolean> isCore = property(new SingleProperty<>(PropertyTypes.BOOLEAN, "isCore", false));
	public SingleProperty<BlockPos> coreLocation = property(new SingleProperty<>(PropertyTypes.BLOCK_POS, "coreLocation", BlockEntityUtils.OUT_OF_REACH));
	public SingleProperty<Integer> currentVoltage = property(new SingleProperty<>(PropertyTypes.INTEGER, "turbinecurvoltage", 0));
	public SingleProperty<Integer> steam = property(new SingleProperty<>(PropertyTypes.INTEGER, "steam", 0));
	public SingleProperty<Integer> wait = property(new SingleProperty<>(PropertyTypes.INTEGER, "wait", 30));
	protected CachedTileOutput output;

	private boolean isSoundPlaying = false;

	private boolean destroyed = false;


	public TileTurbine() {
		super(NuclearScienceTiles.TILE_TURBINE.get());
		addComponent(new ComponentTickable(this).tickServer(this::tickServer).tickClient(this::tickClient));
		addComponent(new ComponentPacketHandler(this));
		addComponent(new ComponentElectrodynamic(this, true, false).setOutputDirections(BlockEntityUtils.MachineDirection.TOP).setCapabilityTest(() -> (!hasCore.getValue() || isCore.getValue())));
	}

	public void constructStructure() {
		int radius = 1;
		for (int i = -radius; i <= radius; i++) {
			for (int j = -radius; j <= radius; j++) {
				if (i != 0 || j != 0) {
					TileEntity tile = level.getBlockEntity(new BlockPos(worldPosition.getX() + i, worldPosition.getY(), worldPosition.getZ() + j));
					if (tile instanceof TileTurbine ? ((TileTurbine) tile).hasCore.getValue() : true) {
						return;
					}
				}
			}
		}
		isCore.setValue(true);
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
		if (isCore.getValue()) {
			int radius = 1;
			for (int i = -radius; i <= radius; i++) {
				for (int j = -radius; j <= radius; j++) {
					if (i != 0 || j != 0) {
						BlockPos offset = new BlockPos(worldPosition.getX() + i, worldPosition.getY(), worldPosition.getZ() + j);
						TileEntity tile = level.getBlockEntity(offset);
						if (tile instanceof TileTurbine) {
							TileTurbine turbine = (TileTurbine) tile;
							turbine.hasCore.setValue(false);
							turbine.coreLocation.setValue(new BlockPos(0, 0, 0));
							BlockState state = level.getBlockState(offset);
							if (state.hasProperty(BlockTurbine.RENDER)) {
								level.setBlockAndUpdate(offset, state.setValue(BlockTurbine.RENDER, true));
							}
						}
					}
				}
			}
			isCore.setValue(false);
			hasCore.setValue(false);
			coreLocation.setValue(BlockEntityUtils.OUT_OF_REACH);
			BlockState state = getBlockState();
			if (state.hasProperty(BlockTurbine.RENDER) && !destroyed) {
				level.setBlockAndUpdate(worldPosition, getBlockState().setValue(BlockTurbine.RENDER, true));
			}
		} else if (hasCore.getValue()) {
			TileTurbine core = (TileTurbine) level.getBlockEntity(coreLocation.getValue());
			if (core != null) {
				core.deconstructStructure();
			}
		}

	}

	protected void addToStructure(TileTurbine core) {
		coreLocation.setValue(core.worldPosition);
		hasCore.setValue(true);
	}

	public void tickServer(ComponentTickable tickable) {
		this.<ComponentElectrodynamic>getComponent(IComponentType.Electrodynamic).voltage(currentVoltage.getValue());
		if (output == null) {
			output = new CachedTileOutput(level, worldPosition.relative(Direction.UP));
		}
		spinSpeed.setValue(currentVoltage.getValue() / 120);
		output.update(worldPosition.relative(Direction.UP));
		if (hasCore.getValue() && !isCore.getValue()) {
			currentVoltage.setValue(0);
			return;
		}
		if (steam.getValue() > 0 && currentVoltage.getValue() > 0) {
			wait.setValue(30);
			if (output.valid()) {
				TransferPack transfer = TransferPack.joulesVoltage(steam.getValue() * (hasCore.getValue() ? 1.111 : 1), currentVoltage.getValue());
				ElectricityUtils.receivePower(output.getSafe(), Direction.DOWN, transfer, false);
				steam.setValue(Math.max(steam.getValue() - Math.max(75, steam.getValue()), 0));
			}
		} else {
			if (wait.getValue() <= 0) {
				currentVoltage.setValue(0);
				wait.setValue(30);
			}
			wait.setValue(wait.getValue() - 1);
		}

	}

	public void tickClient(ComponentTickable tickable) {
		if (!isSoundPlaying && shouldPlaySound()) {
			isSoundPlaying = true;
			SoundBarrierMethods.playTileSound(NuclearScienceSounds.SOUND_TURBINE.get(), this, true);
		}
	}

	@Override
	public void setNotPlaying() {
		isSoundPlaying = false;
	}

	@Override
	public boolean shouldPlaySound() {
		return spinSpeed.getValue() > 0;
	}
	
	@Override
	public ActionResultType use(PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
		return ActionResultType.PASS;
	}

	@Override
	public int receiveSteam(int temperature, int amount) {
		int room = MAX_STEAM * (isCore.getValue() ? 9 : 1) - steam.getValue();
		int accepted = room < amount ? room : amount;
		this.steam.setValue(steam.getValue() + accepted);
		if (temperature < 4300) {
			currentVoltage.setValue(120);
		} else if (temperature < 6000) {
			currentVoltage.setValue(240);
		} else {
			currentVoltage.setValue(480);
		}
		if (!isCore.getValue() && hasCore.getValue()) {
			TileEntity core = level.getBlockEntity(coreLocation.getValue());
			if (core instanceof TileTurbine && ((TileTurbine) core).isCore.getValue()) {
				accepted = ((TileTurbine) core).receiveSteam(temperature, amount);
				this.steam.setValue(0);
			}
		}
		return accepted;
	}

	@Override
	public boolean isStillValid() {
		return isRemoved();
	}

	@Override
	public void onBlockDestroyed() {
		super.onBlockDestroyed();
		if (level.isClientSide) {
			return;
		}
		destroyed = true;
		deconstructStructure();

	}
	
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
		if(cap == CapabilityEnergy.ENERGY && !getBlockState().getValue(BlockTurbine.RENDER) && !isCore.getValue()) {
			return LazyOptional.empty();
		}
		return super.getCapability(cap, side);
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return isCore.getValue() ? super.getRenderBoundingBox().inflate(1, 0, 1) : super.getRenderBoundingBox();
	}

}
