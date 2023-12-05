package nuclearscience.common.item;

import electrodynamics.common.item.subtype.SubtypePlate;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import nuclearscience.References;

public class ItemHazmatArmor extends ArmorItem {

	public ItemHazmatArmor(ArmorMaterial materialIn, EquipmentSlot slot, Properties builderIn) {
		super(materialIn, slot, builderIn);
	}

	public ItemHazmatArmor(EquipmentSlot slot, Properties builderIn) {
		this(ArmorMaterialHazmat.hazmat, slot, builderIn);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
		return References.ID + ":textures/model/" + (material == ArmorMaterialHazmat.hazmat ? "" : "reinforced") + "hazmatarmor.png";
	}

	public enum ArmorMaterialHazmat implements ArmorMaterial {
		hazmat,
		reinforcedhazmat;

		@Override
		public int getDurabilityForSlot(EquipmentSlot slotIn) {
			return this == hazmat ? 37500 : 37500 * 5;
		}

		@Override
		public int getDefenseForSlot(EquipmentSlot slotIn) {
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
			return Ingredient.of(this == hazmat ? Items.LEATHER : ElectrodynamicsItems.SUBTYPEITEMREGISTER_MAPPINGS.get(SubtypePlate.lead).get());
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