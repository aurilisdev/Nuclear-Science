package nuclearscience.common.item;

import java.util.List;

import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import nuclearscience.prefab.utils.NuclearTextUtils;
import voltaic.common.item.ItemVoltaic;
import voltaic.registers.VoltaicDataComponentTypes;

public class ItemFrequencyCard extends ItemVoltaic {

    public ItemFrequencyCard(Properties properties, Holder<CreativeModeTab> creativeTab) {
	super(properties.stacksTo(1), creativeTab);
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {

	Level level = context.getLevel();
	Player player = context.getPlayer();

	if (level.isClientSide || player == null)
	    return super.onItemUseFirst(stack, context);

	if (player.isShiftKeyDown()) {
	    stack.remove(VoltaicDataComponentTypes.RESOURCE_LOCATION);
	    stack.remove(VoltaicDataComponentTypes.BLOCK_POS);
	} else {
	    stack.set(VoltaicDataComponentTypes.BLOCK_POS, context.getClickedPos().above());
	    stack.set(VoltaicDataComponentTypes.RESOURCE_LOCATION, level.dimension().location());
	}

	return InteractionResult.SUCCESS;

    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltips,
	    TooltipFlag tooltipFlag) {
	super.appendHoverText(stack, context, tooltips, tooltipFlag);
	if (stack.has(VoltaicDataComponentTypes.RESOURCE_LOCATION)) {
	    BlockPos pos = stack.get(VoltaicDataComponentTypes.BLOCK_POS);
	    ResourceKey<Level> world = ResourceKey.create(Registries.DIMENSION,
		    stack.get(VoltaicDataComponentTypes.RESOURCE_LOCATION));

	    MutableComponent worldKey = ElectroTextUtils.dimensionExists(world) ? ElectroTextUtils.dimension(world)
		    : Component.literal(world.location().getPath());

	    tooltips.add(NuclearTextUtils.tooltip("frequencycard.linked", worldKey.append(pos.toShortString())));
	} else {
	    tooltips.add(NuclearTextUtils.tooltip("frequencycard.notag"));
	}
    }

}
