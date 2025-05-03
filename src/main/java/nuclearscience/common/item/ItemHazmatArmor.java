package nuclearscience.common.item;

import java.util.function.Supplier;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import nuclearscience.NuclearScience;
import voltaic.common.item.gear.ItemVoltaicArmor;
import voltaic.common.tags.VoltaicTags;

public class ItemHazmatArmor extends ItemVoltaicArmor {

	public ItemHazmatArmor(ArmorMaterial materialIn, EquipmentSlot slot, Properties properties, Supplier<CreativeModeTab> creativeTab) {
		super(materialIn, slot, properties, creativeTab);
	}

	public ItemHazmatArmor(EquipmentSlot slot, Properties properties, Supplier<CreativeModeTab> creativeTab) {
		this(ArmorMaterialHazmat.hazmat, slot, properties, creativeTab);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		return NuclearScience.ID + ":textures/block/model/" + (material == ArmorMaterialHazmat.hazmat ? "" : "reinforced") + "hazmatarmor.png";
	}

	public enum ArmorMaterialHazmat implements ArmorMaterial {
		hazmat,
		reinforcedhazmat;

		@Override
		public int getDurabilityForSlot(EquipmentSlot type) {
			return this == hazmat ? 37500 : 37500 * 5;
		}

		@Override
		public int getDefenseForSlot(EquipmentSlot type) {
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
}
