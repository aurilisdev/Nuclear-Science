package nuclearscience.common.item;

import java.util.function.Supplier;

import electrodynamics.common.item.subtype.SubtypePlate;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import nuclearscience.NuclearScience;
import voltaic.api.radiation.util.IHazmatSuit;
import voltaic.common.item.gear.ItemVoltaicArmor;

public class ItemHazmatArmor extends ItemVoltaicArmor implements IHazmatSuit {
	
	private final double radiationProtection;
	private final double radiationStrengthProtection;

	public ItemHazmatArmor(ArmorMaterial materialIn, Type slot, Properties properties, double radiationProtection, double radiationStrengthRating, Supplier<CreativeModeTab> creativeTab) {
		super(materialIn, slot, properties, creativeTab);
		this.radiationProtection = radiationProtection;
		this.radiationStrengthProtection = radiationStrengthRating;
	}

	public ItemHazmatArmor(Type slot, Properties properties, double radiationProtection, double radiationStrengthRating, Supplier<CreativeModeTab> creativeTab) {
		this(ArmorMaterialHazmat.hazmat, slot, properties, radiationProtection, radiationStrengthRating, creativeTab);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		return NuclearScience.ID + ":textures/block/model/" + (material == ArmorMaterialHazmat.hazmat ? "" : "reinforced") + "hazmatarmor.png";
	}

	public enum ArmorMaterialHazmat implements ArmorMaterial {
		hazmat,
		reinforcedhazmat;

		@Override
		public int getDurabilityForType(Type type) {
			return this == hazmat ? 37500 : 37500 * 5;
		}

		@Override
		public int getDefenseForType(Type type) {
			return this == hazmat ? 2 : 4;
		}

		@Override
		public int getEnchantmentValue() {
			return 0;
		}

		@Override
		public SoundEvent getEquipSound() {
			return SoundEvents.ARMOR_EQUIP_LEATHER;
		}

		@Override
		public Ingredient getRepairIngredient() {
			return Ingredient.of(this == hazmat ? Items.LEATHER : ElectrodynamicsItems.ITEMS_PLATE.getValue(SubtypePlate.lead));
		}

		@Override
		public String getName() {
			return super.name();
		}

		@Override
		public float getToughness() {
			return 0;
		}

		@Override
		public float getKnockbackResistance() {
			return 0;
		}

	}

	@Override
	public double getRadResistance() {
		return radiationProtection;
	}

	@Override
	public double getRadStrengthProtection() {
		return radiationStrengthProtection;
	}
}
