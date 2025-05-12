package nuclearscience.common.item;

import java.util.function.Supplier;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.Explosion.Mode;
import nuclearscience.api.capability.ICapabilityAntimatterItem;
import nuclearscience.common.settings.NuclearConstants;
import nuclearscience.prefab.utils.NuclearCapabilityUtils;
import nuclearscience.registers.NuclearScienceCapabilities;
import voltaic.common.item.ItemVoltaic;

public class ItemAntimatter extends ItemVoltaic {
    public ItemAntimatter(Properties properties, Supplier<ItemGroup> creativeTab) {
        super(properties, creativeTab);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {

    	ICapabilityAntimatterItem cap = entity.getCapability(NuclearScienceCapabilities.CAPABILITY_ANTIMATTERITEM).orElse(NuclearCapabilityUtils.EMPTY_ANTIMATTERITEM);
    	
    	if(cap == NuclearCapabilityUtils.EMPTY_ANTIMATTERITEM) {
    		return super.onEntityItemUpdate(stack, entity);
    	}
    	
        int time = cap.getTime();

        if(time >= NuclearConstants.ANTIMATTER_TICKS_ON_GROUND) {

            if(!entity.level.isClientSide()) {
                entity.level.explode(entity, entity.getX(), entity.getY(), entity.getZ(), 2F, Mode.BREAK);
                entity.remove(false);
            }

            return true;
        }

        cap.incrementTime();

        return super.onEntityItemUpdate(stack, entity);
    }
}
