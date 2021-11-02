package nuclearscience.common.item;

import java.util.List;

import electrodynamics.prefab.utilities.object.Location;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import nuclearscience.api.radiation.RadiationRegister;
import nuclearscience.api.radiation.RadiationSystem;

import net.minecraft.world.item.Item.Properties;

public class ItemRadioactive extends Item {

    public ItemRadioactive(Properties properties) {
	super(properties);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
	Level world = entity.getCommandSenderWorld();
	if (world.getLevelData().getGameTime() % 10 == 0) {
	    Location source = new Location(entity.getX(), entity.getY(), entity.getZ());
	    double totstrength = stack.getCount() * RadiationRegister.get(stack.getItem()).getRadiationStrength();
	    double range = Math.sqrt(totstrength) / (5 * Math.sqrt(2)) * 1.25;
	    AABB bb = AABB.ofSize(range, range, range);
	    bb = bb.move(new Vec3(source.x(), source.y(), source.z()));
	    List<LivingEntity> list = world.getEntitiesOfClass(LivingEntity.class, bb);
	    for (LivingEntity living : list) {
		RadiationSystem.applyRadiation(living, source, totstrength);
	    }
	}
	return super.onEntityItemUpdate(stack, entity);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entityIn, int itemSlot, boolean isSelected) {
	super.inventoryTick(stack, world, entityIn, itemSlot, isSelected);
	if (entityIn instanceof LivingEntity && world.getLevelData().getGameTime() % 10 == 0) {
	    Location source = new Location(entityIn.getX(), entityIn.getY(), entityIn.getZ());
	    double totstrength = stack.getCount() * RadiationRegister.get(stack.getItem()).getRadiationStrength();
	    RadiationSystem.applyRadiation((LivingEntity) entityIn, source, totstrength);
	}
    }
}
