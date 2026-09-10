package nuclearscience.common.tile;

import javax.annotation.Nullable;

import electrodynamics.prefab.utilities.ElectricityUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import nuclearscience.api.turbine.ISteamReceiver;
import nuclearscience.common.block.BlockTurbine;
import nuclearscience.registers.NuclearScienceSounds;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.api.electricity.ICapabilityElectrodynamic;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.sound.ITickableSound;
import voltaic.prefab.sound.SoundBarrierMethods;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.IComponentType;
import voltaic.prefab.tile.components.type.ComponentElectrodynamic;
import voltaic.prefab.tile.components.type.ComponentTickable;
import voltaic.prefab.utilities.BlockEntityUtils;
// ...existing imports...
import voltaic.prefab.utilities.object.TransferPack;

public class TileTurbine extends GenericTile implements ITickableSound, ISteamReceiver {

    public static final int MAX_STEAM = 3000000;
    public SingleProperty<Integer> spinSpeed = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.INTEGER, "spinSpeed", 0));
    public SingleProperty<Boolean> hasCore = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.BOOLEAN, "hasCore", false));
    public SingleProperty<Boolean> isCore = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.BOOLEAN, "isCore", false));
    public SingleProperty<BlockPos> coreLocation = property(new SingleProperty<>(getPropertyManager(),
	    PropertyTypes.BLOCK_POS, "coreLocation", BlockEntityUtils.OUT_OF_REACH));
    public SingleProperty<Integer> currentVoltage = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.INTEGER, "turbinecurvoltage", 0));
    public SingleProperty<Integer> steam = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.INTEGER, "steam", 0));
    public SingleProperty<Integer> wait = property(
	    new SingleProperty<>(getPropertyManager(), PropertyTypes.INTEGER, "wait", 30));
    // cached output removed; directly query the block entity above when needed

    private boolean isSoundPlaying = false;

    private boolean destroyed = false;

    public TileTurbine(BlockPos pos, BlockState state) {
	super(NuclearScienceTiles.TILE_TURBINE.get(), pos, state);
	addComponent(new ComponentTickable(this).tickServer(this::tickServer).tickClient(this::tickClient));
	addComponent(new ComponentElectrodynamic(this, true, false)
		.setOutputDirections(BlockEntityUtils.MachineDirection.TOP)
		.setCapabilityTest(() -> (!hasCore.getValue() || isCore.getValue())));
    }

    public void constructStructure(Level level) {
	int radius = 1;

	for (int i = -radius; i <= radius; i++) {
	    for (int j = -radius; j <= radius; j++) {
		if (i == 0 && j == 0) {
		    continue;
		}

		BlockPos offset = worldPosition.offset(i, 0, j);
		BlockEntity tile = level.getBlockEntity(offset);

		if (!(tile instanceof TileTurbine turbine) || turbine.hasCore.getValue())
		    return;
	    }
	}

	isCore.setValue(true);

	for (int i = -radius; i <= radius; i++) {
	    for (int j = -radius; j <= radius; j++) {
		BlockPos offset = worldPosition.offset(i, 0, j);
		BlockEntity tile = level.getBlockEntity(offset);

		if (!(tile instanceof TileTurbine turbine))
		    return;

		turbine.addToStructure(this);

		BlockState state = level.getBlockState(offset);
		level.setBlockAndUpdate(offset, state.setValue(BlockTurbine.RENDER, false));
	    }
	}
    }

    public void deconstructStructure(Level level) {
	if (isCore.getValue()) {
	    int radius = 1;
	    for (int i = -radius; i <= radius; i++) {
		for (int j = -radius; j <= radius; j++) {
		    if (i != 0 || j != 0) {
			BlockPos offset = new BlockPos(worldPosition.getX() + i, worldPosition.getY(),
				worldPosition.getZ() + j);
			BlockEntity tile = level.getBlockEntity(offset);
			if (tile instanceof TileTurbine turbine) {
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
		core.deconstructStructure(level);
	    }
	}

    }

    protected void addToStructure(TileTurbine core) {
	coreLocation.setValue(core.worldPosition);
	hasCore.setValue(true);
    }

    public void tickServer(Level level, ComponentTickable tickable) {
	this.<ComponentElectrodynamic>requireComponent(IComponentType.Electrodynamic)
		.voltage(currentVoltage.getValue());
	spinSpeed.setValue(currentVoltage.getValue() / 120);
	BlockPos targetPos = worldPosition.relative(Direction.UP);
	if (hasCore.getValue() && !isCore.getValue()) {
	    currentVoltage.setValue(0);
	    return;
	}
	if (steam.getValue() > 0 && currentVoltage.getValue() > 0) {
	    wait.setValue(30);
	    BlockEntity out = level.getBlockEntity(targetPos);
	    if (out != null) {
		TransferPack transfer = TransferPack.joulesVoltage(steam.getValue() * (hasCore.getValue() ? 1.111 : 1),
			currentVoltage.getValue());
		ElectricityUtils.receivePower(out, Direction.DOWN, transfer, false);
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

    public void tickClient(Level level, ComponentTickable tickable) {
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
    public InteractionResult useWithoutItem(Level level, Player player, BlockHitResult hit) {
	return InteractionResult.PASS;
    }

    @Override
    public ItemInteractionResult useWithItem(Level level, ItemStack used, Player player, InteractionHand hand,
	    BlockHitResult hit) {
	return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    public int receiveSteam(int temperature, int amount) {
	Level level = this.level;
	if (level == null)
	    return 0;

	int room = MAX_STEAM * (isCore.getValue() ? 9 : 1) - steam.getValue();
	int accepted = room < amount ? room : amount;
	steam.setValue(steam.getValue() + accepted);
	if (temperature < 4300) {
	    currentVoltage.setValue(120);
	} else if (temperature < 6000) {
	    currentVoltage.setValue(240);
	} else {
	    currentVoltage.setValue(480);
	}
	if (!isCore.getValue() && hasCore.getValue()) {
	    BlockEntity core = level.getBlockEntity(coreLocation.getValue());
	    if (core instanceof TileTurbine turbine && ((TileTurbine) core).isCore.getValue()) {
		accepted = turbine.receiveSteam(temperature, amount);
		steam.setValue(0);
	    }
	}
	return accepted;
    }

    @Override
    public boolean isStillValid() {
	return isRemoved();
    }

    @Override
    public void onBlockDestroyed(Level level) {
	super.onBlockDestroyed(level);
	if (level.isClientSide)
	    return;

	destroyed = true;
	deconstructStructure(level);

    }

    @Override
    public @Nullable ICapabilityElectrodynamic getElectrodynamicCapability(@Nullable Direction side) {
	if (!getBlockState().getValue(BlockTurbine.RENDER) && !isCore.getValue())
	    return null;
	return super.getElectrodynamicCapability(side);
    }

}
