package nuclearscience.api.radiation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import electrodynamics.prefab.utilities.math.MathUtils;
import electrodynamics.prefab.utilities.object.Location;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import nuclearscience.References;
import nuclearscience.common.item.ItemGeigerCounter;
import nuclearscience.common.item.ItemHazmatArmor;
import nuclearscience.registers.NuclearScienceBlocks;

@EventBusSubscriber(modid = References.ID, bus = Bus.FORGE)
public class RadiationSystem {
	public static ThreadLocal<HashMap<PlayerEntity, Double>> radiationMap = ThreadLocal.withInitial(HashMap::new);

	private static double getRadiationModifier(World world, Location source, Location end) {
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
				lastHard = hard = (state.getBlock() == NuclearScienceBlocks.blocklead ? 20000 : state.getDestroySpeed(world, curr)) / (world.getFluidState(curr).isEmpty() ? 1 : 50.0);
			}
			modifier += hard / 4.5f;
			checks--;
		}
		return modifier;
	}

	public static double getRadiation(World world, Location source, Location end, double strength) {
		double distance = 1 + source.distance(end);
		return strength / (getRadiationModifier(world, source, end) * distance * distance);
	}

	public static void applyRadiation(LivingEntity entity, Location source, double strength) {
		int protection = 1;
		boolean isPlayerEntity = entity instanceof PlayerEntity;
		if (isPlayerEntity) {
			PlayerEntity player = (PlayerEntity) entity;
			if (!player.isCreative()) {
				for (int i = 0; i < player.inventory.armor.size(); i++) {
					ItemStack next = player.inventory.armor.get(i);
					if (next.getItem() instanceof ItemHazmatArmor) {
						protection++;
						float damage = (float) (strength * 2.15f) / 2169.9975f;
						if (Math.random() < damage) {
							int integerDamage = Math.round(damage);
							if (next.getDamageValue() > next.getMaxDamage() || next.hurt(integerDamage, entity.level.random, player instanceof ServerPlayerEntity ? (ServerPlayerEntity) player : null)) {
								player.inventory.setItem(i, ItemStack.EMPTY);
							}
						}
					}
				}
			}
		}
		Location end = new Location(entity.position().add(0, entity.getEyeHeight() / 2.0, 0));
		double radiation = 0;
		if (entity instanceof PlayerEntity && (((PlayerEntity) entity).getItemBySlot(EquipmentSlotType.MAINHAND).getItem() instanceof ItemGeigerCounter || ((PlayerEntity) entity).getItemBySlot(EquipmentSlotType.OFFHAND).getItem() instanceof ItemGeigerCounter)) {
			double already = radiationMap.get().containsKey(entity) ? radiationMap.get().get(entity) : 0;
			radiation = getRadiation(entity.level, source, end, strength);
			radiationMap.get().put((PlayerEntity) entity, already + radiation);
		}
		if (!(entity instanceof PlayerEntity && ((PlayerEntity) entity).isCreative()) && protection < 5 && radiationMap.get().getOrDefault(entity, 11.0) > 4) {
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
			entity.addEffect(new EffectInstance(EffectRadiation.INSTANCE, time, Math.min(40, amplitude), false, true));
		}
	}

	public static void emitRadiationFromLocation(World level, Location source, double radius, double strength) {
		AxisAlignedBB bb = MathUtils.ofSize(new Vector3d(source.x(), source.y(), source.z()), radius * 2, radius * 2, radius * 2);
		List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, bb);
		for (LivingEntity living : list) {
			RadiationSystem.applyRadiation(living, source, strength);
		}
	}

	@SubscribeEvent
	public static void onTick(ServerTickEvent event) {
		if (event.side == LogicalSide.SERVER && event.phase == Phase.START) {
			radiationMap.get().clear();
		}
	}

	private static int tick = 0;

	@SubscribeEvent
	public static void onTickC(ClientTickEvent event) {
		if (event.side == LogicalSide.CLIENT && event.phase == Phase.END) {
			tick++;
			if (tick % 20 == 0) {
				for (Map.Entry<PlayerEntity, Double> en : ((HashMap<PlayerEntity, Double>) radiationMap.get().clone()).entrySet()) {
					radiationMap.get().put(en.getKey(), en.getValue() * 0.3);
				}
				tick = 0;
			}
		}
	}
}