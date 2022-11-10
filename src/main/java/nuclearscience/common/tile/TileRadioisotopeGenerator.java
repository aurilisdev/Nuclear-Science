package nuclearscience.common.tile;

import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.ElectricityUtils;
import electrodynamics.prefab.utilities.object.CachedTileOutput;
import electrodynamics.prefab.utilities.object.TransferPack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.api.radiation.IRadioactiveObject;
import nuclearscience.api.radiation.RadiationRegister;
import nuclearscience.common.inventory.container.ContainerRadioisotopeGenerator;
import nuclearscience.common.settings.Constants;
import nuclearscience.registers.NuclearScienceBlockTypes;

public class TileRadioisotopeGenerator extends GenericTile {

	protected CachedTileOutput output1;
	protected CachedTileOutput output2;

	public TileRadioisotopeGenerator(BlockPos pos, BlockState state) {
		super(NuclearScienceBlockTypes.TILE_RADIOISOTOPEGENERATOR.get(), pos, state);
		addComponent(new ComponentTickable().tickServer(this::tickServer));
		addComponent(new ComponentPacketHandler());
		addComponent(new ComponentElectrodynamic(this).voltage(Constants.RADIOISOTOPEGENERATOR_VOLTAGE).extractPower((x, y) -> TransferPack.EMPTY).output(Direction.UP).output(Direction.DOWN));
		addComponent(new ComponentInventory(this).size(1).slotFaces(0, Direction.values()).valid((slot, stack, i) -> RadiationRegister.get(stack.getItem()) != RadiationRegister.NULL));
		addComponent(new ComponentContainerProvider("container.radioisotopegenerator").createMenu((id, player) -> new ContainerRadioisotopeGenerator(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	}

	public void tickServer(ComponentTickable tickable) {
		if (output1 == null) {
			output1 = new CachedTileOutput(level, worldPosition.relative(Direction.UP));
		}
		if (output2 == null) {
			output2 = new CachedTileOutput(level, worldPosition.relative(Direction.DOWN));
		}
		if (tickable.getTicks() % 40 == 0) {
			output1.update(worldPosition.relative(Direction.UP));
			output2.update(worldPosition.relative(Direction.DOWN));
		}
		ItemStack in = this.<ComponentInventory>getComponent(ComponentType.Inventory).getItem(0);
		IRadioactiveObject rad = RadiationRegister.get(in.getItem());
		double currentOutput = in.getCount() * Constants.RADIOISOTOPEGENERATOR_OUTPUT_MULTIPLIER * rad.getRadiationStrength();
		if (currentOutput > 0) {
			TransferPack transfer = TransferPack.ampsVoltage(currentOutput / (Constants.RADIOISOTOPEGENERATOR_VOLTAGE * 2.0), Constants.RADIOISOTOPEGENERATOR_VOLTAGE);
			if (output1.valid()) {
				ElectricityUtils.receivePower(output1.getSafe(), Direction.DOWN, transfer, false);
			}
			if (output2.valid()) {
				ElectricityUtils.receivePower(output2.getSafe(), Direction.UP, transfer, false);
			}
		}
	}
}
