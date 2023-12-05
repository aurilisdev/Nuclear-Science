package nuclearscience.common.item;

import electrodynamics.prefab.utilities.object.Location;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import nuclearscience.api.radiation.RadiationRegister;
import nuclearscience.api.radiation.RadiationSystem;

public class ItemRadioactive extends Item {

	public ItemRadioactive(Properties properties) {
		super(properties);
	}

	@Override
	public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
		Level world = entity.getCommandSenderWorld();
		if (world.getLevelData().getGameTime() % 10 == 0) {
			double totstrength = stack.getCount() * RadiationRegister.get(stack.getItem()).getRadiationStrength();
			double range = Math.sqrt(totstrength) / (5 * Math.sqrt(2)) * 1.25;
			RadiationSystem.emitRadiationFromLocation(world, new Location(entity), range, totstrength);
		}
		return super.onEntityItemUpdate(stack, entity);
	}

	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int itemSlot, boolean isSelected) {
		super.inventoryTick(stack, world, entity, itemSlot, isSelected);
		if (world.getLevelData().getGameTime() % 10 == 0) {
			double totstrength = stack.getCount() * RadiationRegister.get(stack.getItem()).getRadiationStrength();
			double range = Math.sqrt(totstrength) / (5 * Math.sqrt(2)) * 1.25;
			RadiationSystem.emitRadiationFromLocation(world, new Location(entity), range, totstrength);
		}
	}
}