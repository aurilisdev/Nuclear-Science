package nuclearscience.common.item;

import java.util.EnumMap;

import org.jetbrains.annotations.Nullable;

import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import nuclearscience.NuclearScience;
import voltaic.api.radiation.util.IHazmatSuit;
import voltaic.common.item.gear.ItemVoltaicArmor;

public class ItemHazmatArmor extends ItemVoltaicArmor implements IHazmatSuit {

    public static final EnumMap<Type, Integer> DEFENSE_MAP_BASE = Util.make(new EnumMap<>(ArmorItem.Type.class),
	    map -> {
		map.put(Type.HELMET, 2);
		map.put(Type.CHESTPLATE, 2);
		map.put(Type.LEGGINGS, 2);
		map.put(Type.BOOTS, 2);
	    });

    public static final EnumMap<Type, Integer> DEFENSE_MAP_REINFORCED = Util.make(new EnumMap<>(ArmorItem.Type.class),
	    map -> {
		map.put(Type.HELMET, 4);
		map.put(Type.CHESTPLATE, 4);
		map.put(Type.LEGGINGS, 4);
		map.put(Type.BOOTS, 4);
	    });

    private final double radiationProtection;
    private final double radiationStrengthProtection;

    private final ResourceLocation armorTexture;

    public ItemHazmatArmor(Holder<ArmorMaterial> materialIn, Type slot, Properties properties,
	    Holder<CreativeModeTab> creativeTab, double radiationProtection, double radiationStrengthRating,
	    String armorTexture) {
	super(materialIn, slot, properties, creativeTab);
	this.radiationProtection = radiationProtection;
	this.radiationStrengthProtection = radiationStrengthRating;
	this.armorTexture = NuclearScience.rl("textures/models/armor/" + armorTexture + ".png");
    }

    @Override
    public double getRadResistance() {
	return radiationProtection;
    }

    @Override
    public double getRadStrengthProtection() {
	return radiationStrengthProtection;
    }

    @Override
    public @Nullable ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot,
	    ArmorMaterial.Layer layer, boolean innerModel) {
	return armorTexture;
    }

    /*
     * 
     * @Override public String getArmorTexture(ItemStack stack, Entity entity,
     * EquipmentSlot slot, String type) {
     * 
     * }
     * 
     * public enum ArmorMaterialHazmat implements ArmorMaterial { hazmat,
     * reinforcedhazmat;
     * 
     * @Override public int getDurabilityForType(Type type) { return this == hazmat
     * ? 37500 : 37500 * 5; }
     * 
     * @Override public int getDefenseForType(Type type) { return this == hazmat ? 2
     * : 4; }
     * 
     * @Override public int getEnchantmentValue() { return 0; }
     * 
     * @Override public SoundEvent getEquipSound() { return
     * SoundEvents.ARMOR_EQUIP_LEATHER; }
     * 
     * @Override public Ingredient getRepairIngredient() { return Ingredient.of(this
     * == hazmat ? Items.LEATHER :
     * ElectrodynamicsItems.SUBTYPEITEMREGISTER_MAPPINGS.get(SubtypePlate.lead).get(
     * )); }
     * 
     * @Override public String getName() { return super.name(); }
     * 
     * @Override public float getToughness() { return 0; }
     * 
     * @Override public float getKnockbackResistance() { return 0; }
     * 
     * }
     * 
     */
}
