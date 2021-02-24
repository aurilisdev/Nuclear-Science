package nuclearscience.common.item;

import java.util.List;

import electrodynamics.api.math.Location;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import nuclearscience.api.radiation.RadiationRegister;
import nuclearscience.api.radiation.RadiationSystem;

public class ItemRadioactive extends Item {

    public ItemRadioactive(Properties properties) {
	super(properties);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
	World world = entity.getEntityWorld();
	if (world.getWorldInfo().getGameTime() % 10 == 0) {
	    Location source = new Location(entity.getPosX(), entity.getPosY(), entity.getPosZ());
	    double totstrength = stack.getCount() * RadiationRegister.get(stack.getItem()).getRadiationStrength();
	    double range = Math.sqrt(totstrength) / (5 * Math.sqrt(2)) * 1.25;
	    AxisAlignedBB bb = AxisAlignedBB.withSizeAtOrigin(range, range, range);
	    bb = bb.offset(new Vector3d(source.x, source.y, source.z));
	    List<LivingEntity> list = world.getEntitiesWithinAABB(LivingEntity.class, bb);
	    for (LivingEntity living : list) {
		RadiationSystem.applyRadiation(living, source, totstrength);
	    }
	}
	return super.onEntityItemUpdate(stack, entity);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
	super.inventoryTick(stack, world, entityIn, itemSlot, isSelected);
	if (entityIn instanceof LivingEntity) {
	    if (world.getWorldInfo().getGameTime() % 10 == 0) {
		Location source = new Location(entityIn.getPosX(), entityIn.getPosY(), entityIn.getPosZ());
		double totstrength = stack.getCount() * RadiationRegister.get(stack.getItem()).getRadiationStrength();
		RadiationSystem.applyRadiation((LivingEntity) entityIn, source, totstrength);
	    }
	}
    }
}
