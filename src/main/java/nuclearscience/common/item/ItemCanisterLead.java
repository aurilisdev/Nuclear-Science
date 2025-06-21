package nuclearscience.common.item;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import electrodynamics.common.item.gear.tools.ItemCanister;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import nuclearscience.registers.NuclearScienceItems;
import voltaic.api.radiation.RadiationSystem;
import voltaic.api.radiation.SimpleRadiationSource;
import voltaic.api.radiation.util.RadioactiveObject;
import voltaic.common.reloadlistener.RadioactiveFluidRegister;
import voltaic.prefab.utilities.CapabilityUtils;
import voltaic.prefab.utilities.ItemUtils;

public class ItemCanisterLead extends ItemCanister {

	public static final int RAD_RANGE = 10;

	public static List<ResourceLocation> TAG_NAMES = new ArrayList<>();

	public ItemCanisterLead(Properties oroperties, Supplier<ItemGroup> creativeTab) {
		super(oroperties, creativeTab);
		// The regular canister now emits radiation if it has radioactive fluids in it
		INVENTORY_TICK_CONSUMERS.add((stack, world, entity, slot, isSelected) -> {

			if (ItemUtils.testItems(stack.getItem(), NuclearScienceItems.ITEM_CANISTERLEAD.get())) {
				return;
			}

			IFluidHandlerItem cap = (IFluidHandlerItem) stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(CapabilityUtils.EMPTY_FLUID_ITEM);

			if (cap == CapabilityUtils.EMPTY_FLUID_ITEM) {
				return;
			}

			FluidStack fluidStack = cap.getFluidInTank(0);

            if (fluidStack.isEmpty()) {
                return;
            }

            RadioactiveObject radiation = RadioactiveFluidRegister.getValue(fluidStack.getFluid());

            if (radiation.amount() <= 0) {
                return;
            }

            double radiationMultiplier = (double) fluidStack.getAmount() / (double) cap.getTankCapacity(0);

            RadiationSystem.addRadiationSource(world, new SimpleRadiationSource(radiation.amount() * radiationMultiplier, radiation.strength(), RAD_RANGE, true, 0, entity.blockPosition(), false, false));

		});
	}

}
