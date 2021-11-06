package nuclearscience.api.radiation;

import java.util.HashMap;

import electrodynamics.prefab.utilities.object.Location;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import nuclearscience.DeferredRegisters;
import nuclearscience.References;
import nuclearscience.common.item.ItemGeigerCounter;
import nuclearscience.common.item.ItemHazmatArmor;

@EventBusSubscriber(modid = References.ID, bus = Bus.FORGE)
public class RadiationSystem {

    public static HashMap<Player, Double> radiationMap = new HashMap<>();

    public static double getRadiationModifier(Level world, Location source, Location end) {
	double distance = 1 + source.distance(end);
	Location clone = new Location(end);
	double modifier = 1;
	Location newSource = new Location(source);
	clone.add(-source.x(), -source.y(), -source.z()).normalize().mul(0.33f);
	int checks = (int) distance * 3;
	BlockPos curr = newSource.toBlockPos();
	double lastHard = 0;
	while (checks > 0) {
	    newSource.add(clone);
	    double hard = lastHard;
	    BlockPos next = newSource.toBlockPos();
	    if (!curr.equals(next)) {
		curr = next;
		BlockState state = world.getBlockState(curr);
		lastHard = hard = (state.getBlock() == DeferredRegisters.blocklead ? 20000 : state.getDestroySpeed(world, curr))
			/ (world.getFluidState(curr).isEmpty() ? 1 : 50.0);
	    }
	    modifier += hard / 4.5f;
	    checks--;
	}
	return modifier;
    }

    public static double getRadiation(Level world, Location source, Location end, double strength) {
	double distance = 1 + source.distance(end);
	return strength / (getRadiationModifier(world, source, end) * distance * distance);
    }

    public static void applyRadiation(LivingEntity entity, Location source, double strength) {
	int protection = 1;
	if (!entity.level.isClientSide) {
	    boolean isPlayer = entity instanceof Player;
	    if (isPlayer) {
		Player player = (Player) entity;
		if (!player.isCreative()) {
		    for (int i = 0; i < player.getInventory().armor.size(); i++) {
			ItemStack next = player.getInventory().armor.get(i);
			if (next.getItem() instanceof ItemHazmatArmor) {
			    protection++;
			    float damage = (float) (strength * 2.15f) / 2169.9975f;
			    if (Math.random() < damage) {
				int integerDamage = (int) Math.max(1, damage);
				if (next.getDamageValue() > next.getMaxDamage()
					|| next.hurt(integerDamage, entity.level.random, (ServerPlayer) player)) {
				    player.getInventory().armor.set(i, ItemStack.EMPTY);
				}
			    }
			}
		    }
		}
	    }
	    Location end = new Location(entity.position());
	    double radiation = 0;
	    if (entity instanceof Player pl && (pl.getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof ItemGeigerCounter
		    || pl.getItemBySlot(EquipmentSlot.OFFHAND).getItem() instanceof ItemGeigerCounter)) {
		double already = radiationMap.containsKey(entity) ? radiationMap.get(entity) : 0;
		radiation = getRadiation(entity.level, source, end, strength);
		radiationMap.put((Player) entity, already + radiation);
	    }
	    if (!(entity instanceof Player pl && pl.isCreative()) && protection < 5 && radiationMap.getOrDefault(entity, 11.0) > 4) {
		if (radiation == 0) {
		    radiation = getRadiation(entity.level, source, end, strength);
		}
		double distance = 1 + source.distance(end);
		double modifier = strength / (radiation * distance * distance);
		int amplitude = (int) Math.max(0, Math.min(strength / modifier / (distance * 4000.0), 9));
		int time = (int) (strength / modifier / ((amplitude + 1) * distance));
		if (amplitude == 0 && time <= 40) {
		    return;
		}
		entity.addEffect(new MobEffectInstance(EffectRadiation.INSTANCE, time, Math.min(40, amplitude), false, true));
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
