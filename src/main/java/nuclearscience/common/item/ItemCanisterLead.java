package nuclearscience.common.item;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.common.item.gear.tools.ItemCanister;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import nuclearscience.registers.NuclearScienceItems;
import voltaic.api.radiation.RadiationSystem;
import voltaic.api.radiation.SimpleRadiationSource;
import voltaic.api.radiation.util.RadioactiveObject;
import voltaic.common.reloadlistener.RadioactiveFluidRegister;
import voltaic.prefab.utilities.ItemUtils;

public class ItemCanisterLead extends ItemCanister {

    public static final int RAD_RANGE = 10;

    public static List<ResourceLocation> TAG_NAMES = new ArrayList<>();

    public ItemCanisterLead(Properties oroperties, Holder<CreativeModeTab> creativeTab) {
        super(oroperties, creativeTab);
        // The regular canister now emits radiation if it has radioactive fluids in it
        INVENTORY_TICK_CONSUMERS.add((stack, world, entity, slot, isSelected) -> {

            if (ItemUtils.testItems(stack.getItem(), NuclearScienceItems.ITEM_CANISTERLEAD.get())) {
                return;
            }

            IFluidHandlerItem cap = stack.getCapability(Capabilities.FluidHandler.ITEM);

            if (cap == null) {
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

            RadiationSystem.addRadiationSource(world, new SimpleRadiationSource(radiation.amount() * radiationMultiplier, radiation.strength(), RAD_RANGE, true, 0, entity.getOnPos(), false, false));

        });
    }

    @Override
    public void addCreativeModeItems(CreativeModeTab group, List<ItemStack> items) {
        items.add(new ItemStack(this));
    }
}
