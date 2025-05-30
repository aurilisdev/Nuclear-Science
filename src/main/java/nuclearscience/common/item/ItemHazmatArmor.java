package nuclearscience.common.item;

import java.util.function.Supplier;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.common.Tags;
import nuclearscience.NuclearScience;
import voltaic.api.radiation.util.IHazmatSuit;
import voltaic.common.item.gear.ItemVoltaicArmor;
import voltaic.common.tags.VoltaicTags;

public class ItemHazmatArmor extends ItemVoltaicArmor implements IHazmatSuit {
	
	private final double radiationProtection;
	private final double radiationStrengthProtection;

	public ItemHazmatArmor(IArmorMaterial materialIn, EquipmentSlotType slot, Properties properties, double radiationProtection, double radiationStrengthRating, Supplier<ItemGroup> creativeTab) {
		super(materialIn, slot, properties, creativeTab);
		this.radiationProtection = radiationProtection;
		this.radiationStrengthProtection = radiationStrengthRating;
	}

	public ItemHazmatArmor(EquipmentSlotType slot, Properties properties, double radiationProtection, double radiationStrengthRating, Supplier<ItemGroup> creativeTab) {
		this(ArmorMaterialHazmat.hazmat, slot, properties, radiationProtection, radiationStrengthRating, creativeTab);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return NuclearScience.ID + ":textures/block/model/" + (material == ArmorMaterialHazmat.hazmat ? "" : "reinforced") + "hazmatarmor.png";
	}

	public enum ArmorMaterialHazmat implements IArmorMaterial {
		hazmat,
		reinforcedhazmat;

		@Override
		public int getDurabilityForSlot(EquipmentSlotType type) {
			return this == hazmat ? 37500 : 37500 * 5;
		}

		@Override
		public int getDefenseForSlot(EquipmentSlotType type) {
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
			return Ingredient.of(this == hazmat ? Tags.Items.LEATHER : VoltaicTags.Items.PLATE_LEAD);
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
