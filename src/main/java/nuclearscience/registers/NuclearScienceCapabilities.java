package nuclearscience.registers;

import electrodynamics.common.item.gear.tools.ItemCanister;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import nuclearscience.NuclearScience;
import voltaic.api.fluid.RestrictedFluidHandlerItemStack;
import voltaic.prefab.tile.GenericTile;
import voltaic.registers.VoltaicCapabilities;

@EventBusSubscriber(modid = NuclearScience.ID, bus = EventBusSubscriber.Bus.MOD)
public class NuclearScienceCapabilities {

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {

	NuclearScienceTiles.BLOCK_ENTITY_TYPES.getEntries().forEach(entry -> {
	    event.registerBlockEntity(VoltaicCapabilities.CAPABILITY_ELECTRODYNAMIC_BLOCK,
		    (BlockEntityType<? extends GenericTile>) entry.get(),
		    GenericTile::getElectrodynamicCapability);
	    event.registerBlockEntity(Capabilities.FluidHandler.BLOCK,
		    (BlockEntityType<? extends GenericTile>) entry.get(),
		    GenericTile::getFluidHandlerCapability);
	    event.registerBlockEntity(VoltaicCapabilities.CAPABILITY_GASHANDLER_BLOCK,
		    (BlockEntityType<? extends GenericTile>) entry.get(),
		    GenericTile::getGasHandlerCapability);
	    event.registerBlockEntity(Capabilities.ItemHandler.BLOCK,
		    (BlockEntityType<? extends GenericTile>) entry.get(),
		    GenericTile::getItemHandlerCapability);
	});

	event.registerItem(Capabilities.FluidHandler.ITEM,
		(itemStack, context) -> new RestrictedFluidHandlerItemStack.SwapEmpty(itemStack, itemStack,
			ItemCanister.MAX_FLUID_CAPACITY),
		NuclearScienceItems.ITEM_CANISTERLEAD.get());

    }

}
