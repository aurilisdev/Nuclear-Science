package nuclearscience.common.item;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import nuclearscience.References;

public class ItemHazmatArmor extends ArmorItem {

	public ItemHazmatArmor(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builderIn) {
		super(materialIn, slot, builderIn);
	}

	public ItemHazmatArmor(EquipmentSlotType slot, Properties builderIn) {
		this(ArmorMaterialHazmat.hazmat, slot, builderIn);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		return References.ID + ":textures/model/hazmatarmor.png";
	}

	public enum ArmorMaterialHazmat implements IArmorMaterial {
		hazmat;

		@Override
		public int getDurabilityForSlot(EquipmentSlotType slotIn) {
			return 37500;
		}

		@Override
		public int getDefenseForSlot(EquipmentSlotType slotIn) {
			return 2;
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
			return Ingredient.of(Items.LEATHER);
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
