package nuclearscience.common.item;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import nuclearscience.registers.NuclearScienceAttachmentTypes;
import voltaic.common.item.ItemVoltaic;

public class ItemAntimatter extends ItemVoltaic {
    public ItemAntimatter(Properties properties, Holder<CreativeModeTab> creativeTab) {
        super(properties, creativeTab);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {

        int time = entity.getData(NuclearScienceAttachmentTypes.ANTIMATTER_TIMEONGROUND);

        time = time - 1;

        if(time <= 0) {

            if(!entity.level().isClientSide()) {
                entity.level().explode(entity, entity.getX(), entity.getY(), entity.getZ(), 2F, Level.ExplosionInteraction.BLOCK);
                entity.remove(Entity.RemovalReason.DISCARDED);
            }

            return true;
        }

        entity.setData(NuclearScienceAttachmentTypes.ANTIMATTER_TIMEONGROUND, time);

        return super.onEntityItemUpdate(stack, entity);
    }
}
