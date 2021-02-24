package nuclearscience.api.radiation;

import java.util.HashMap;

import electrodynamics.api.math.Location;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import nuclearscience.References;
import nuclearscience.common.item.ItemGeigerCounter;
import nuclearscience.common.item.ItemHazmatArmor;

@EventBusSubscriber(modid = References.ID, bus = Bus.FORGE)
public class RadiationSystem {

    public static HashMap<PlayerEntity, Double> radiationMap = new HashMap<>();

    public static double getRadiationModifier(World world, Location source, Location end, double strength) {
	double distance = 1 + source.distance(end);
	Location clone = new Location(end);
	double modifier = 1;
	Location newSource = new Location(source);
	clone.add(-source.x, -source.y, -source.z).normalize(null).mul(0.33f);
	int checks = (int) distance * 3;
	BlockPos curr = newSource.toBlockPos();
	double lastHard = 0;
	while (checks > 0) {
	    newSource.add(clone);
	    double hard = lastHard;
	    BlockPos next = newSource.toBlockPos();
	    if (!curr.equals(next)) {
		curr = next;
		lastHard = hard = world.getBlockState(curr).getBlockHardness(world, curr)
			/ (world.getFluidState(curr).isEmpty() ? 1 : 50.0);
	    }
	    modifier += hard / 4.5f;
	    checks--;
	}
	return modifier;
    }

    public static double getRadiation(World world, Location source, Location end, double strength) {
	double distance = 1 + source.distance(end);
	return strength / (getRadiationModifier(world, source, end, strength) * distance * distance);
    }

    public static void applyRadiation(LivingEntity entity, Location source, double strength) {
	int protection = 1;
	if (!entity.world.isRemote) {
	    boolean isPlayer = entity instanceof PlayerEntity;
	    playerCheck: {
		if (isPlayer) {
		    PlayerEntity player = (PlayerEntity) entity;
		    if (player.isCreative()) {
			break playerCheck;
		    }
		    for (int i = 0; i < player.inventory.armorInventory.size(); i++) {
			ItemStack next = player.inventory.armorInventory.get(i);
			if (next.getItem() instanceof ItemHazmatArmor) {
			    protection++;
			    float damage = (float) (strength * 2.15f) / 2169.9975f;
			    if (Math.random() < damage) {
				int integerDamage = (int) Math.max(1, damage);
				if (next.getDamage() > next.getMaxDamage() | next.attemptDamageItem(integerDamage,
					entity.world.rand, (ServerPlayerEntity) player)) {
				    player.inventory.armorInventory.set(i, ItemStack.EMPTY);
				}
			    }
			}
		    }
		}
	    }
	    Location end = new Location(entity.getPositionVec().x, entity.getPositionVec().y,
		    entity.getPositionVec().z);
	    double radiation = 0;
	    if (entity instanceof PlayerEntity && (((PlayerEntity) entity)
		    .getItemStackFromSlot(EquipmentSlotType.MAINHAND).getItem() instanceof ItemGeigerCounter
		    || ((PlayerEntity) entity).getItemStackFromSlot(EquipmentSlotType.OFFHAND)
			    .getItem() instanceof ItemGeigerCounter)) {
		double already = radiationMap.containsKey(entity) ? radiationMap.get(entity) : 0;
		radiation = getRadiation(entity.world, source, end, strength);
		radiationMap.put((PlayerEntity) entity, already + radiation);
	    }
	    if (!(entity instanceof PlayerEntity && ((PlayerEntity) entity).isCreative()) && protection < 5) {
		if (radiation == 0) {
		    radiation = getRadiation(entity.world, source, end, strength);
		}
		double distance = 1 + source.distance(end);
		double modifier = strength / (radiation * distance * distance);
		int amplitude = (int) Math.max(0, Math.min((strength / modifier) / (distance * 4000.0), 9));
		int time = (int) ((strength / modifier) / ((amplitude + 1) * distance));
		if (amplitude == 0 && time <= 40) {
		    return;
		}
		entity.addPotionEffect(new EffectInstance(EffectRadiation.INSTANCE, time, amplitude, false, true));
	    }
	}
    }

    @SubscribeEvent
    public static void onTick(ServerTickEvent event) {
	if (event.side == LogicalSide.SERVER && event.phase == Phase.END) {
	    radiationMap.clear();
	}
    }
}
