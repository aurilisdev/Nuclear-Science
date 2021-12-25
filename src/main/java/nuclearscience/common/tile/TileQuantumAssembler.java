package nuclearscience.common.tile;

import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.DeferredRegisters;
import nuclearscience.api.radiation.RadiationRegister;
import nuclearscience.common.inventory.container.ContainerQuantumAssembler;
import nuclearscience.common.settings.Constants;

public class TileQuantumAssembler extends GenericTile {

	public TileQuantumAssembler(BlockPos pos, BlockState state) {
		super(DeferredRegisters.TILE_QUANTUMASSEMBLER.get(), pos, state);
		addComponent(new ComponentTickable().tickServer(this::tickServer));
		addComponent(new ComponentPacketHandler());
		addComponent(new ComponentElectrodynamic(this).voltage(Constants.QUANTUMASSEMBLER_VOLTAGE).input(Direction.DOWN));
		addComponent(new ComponentInventory(this).size(8).slotFaces(0, Direction.values())
				.valid((slot, stack, i) -> RadiationRegister.get(stack.getItem()) != RadiationRegister.NULL));
		addComponent(new ComponentContainerProvider("container.quantumassembler")
				.createMenu((id, player) -> new ContainerQuantumAssembler(id, player, getComponent(ComponentType.Inventory), getCoordsArray())));
	}

	public void tickServer(ComponentTickable tickable) {
	}
}
