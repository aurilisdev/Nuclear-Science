package nuclearscience.common.item;

import electrodynamics.prefab.utilities.object.Location;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import nuclearscience.api.radiation.RadiationRegister;
import nuclearscience.api.radiation.RadiationSystem;

public class ItemRadioactive extends Item {

	public ItemRadioactive(Properties properties) {
		super(properties);
	}

	@Override
	public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
		World world = entity.getCommandSenderWorld();
		if (world.getLevelData().getGameTime() % 10 == 0) {
			double totstrength = stack.getCount() * RadiationRegister.get(stack.getItem()).getRadiationStrength();
			double range = Math.sqrt(totstrength) / (5 * Math.sqrt(2)) * 1.25;
			RadiationSystem.emitRadiationFromLocation(world, new Location(entity), range, totstrength);
		}
		return super.onEntityItemUpdate(stack, entity);
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
		super.inventoryTick(stack, world, entity, itemSlot, isSelected);
		if (world.getLevelData().getGameTime() % 10 == 0) {
			double totstrength = stack.getCount() * RadiationRegister.get(stack.getItem()).getRadiationStrength();
			double range = Math.sqrt(totstrength) / (5 * Math.sqrt(2)) * 1.25;
			RadiationSystem.emitRadiationFromLocation(world, new Location(entity), range, totstrength);
		}
	}
}