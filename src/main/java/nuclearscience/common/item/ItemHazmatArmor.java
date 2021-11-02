package nuclearscience.common.item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import nuclearscience.References;

import net.minecraft.world.item.Item.Properties;

public class ItemHazmatArmor extends ArmorItem {

    public ItemHazmatArmor(ArmorMaterial materialIn, EquipmentSlot slot, Properties builderIn) {
	super(materialIn, slot, builderIn);
    }

    public ItemHazmatArmor(EquipmentSlot slot, Properties builderIn) {
	this(ArmorMaterialHazmat.hazmat, slot, builderIn);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
	return References.ID + ":textures/model/hazmatarmor.png";
    }

    public enum ArmorMaterialHazmat implements ArmorMaterial {
	hazmat;

	@Override
	public int getDurabilityForSlot(EquipmentSlot slotIn) {
	    return 37500;
	}

	@Override
	public int getDefenseForSlot(EquipmentSlot slotIn) {
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
