package nuclearscience.registers;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import nuclearscience.NuclearScience;
import nuclearscience.common.item.ItemHazmatArmor;
import voltaic.common.tags.VoltaicTags;

public class NuclearScienceArmorMaterials {

    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, NuclearScience.ID);

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> HAZMAT_BASE = register("hazmat_base", ItemHazmatArmor.DEFENSE_MAP_BASE, 0, 0, 0, SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(Tags.Items.LEATHERS), NuclearScience.rl("hazmatarmor"));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> HAZMAT_REINFORCED = register("hazmat_reinforced", ItemHazmatArmor.DEFENSE_MAP_REINFORCED, 0, 0, 0, SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(VoltaicTags.Items.PLATE_LEAD), NuclearScience.rl("reinforcedhazmatarmor"));

    private static DeferredHolder<ArmorMaterial, ArmorMaterial> register(String name, Map<ArmorItem.Type, Integer> slotMap, int enchantValue, float toughness, float knockbackResistance, Holder<SoundEvent> sound, Supplier<Ingredient> repairIngredient, ResourceLocation texture) {
        return ARMOR_MATERIALS.register(name, () -> new ArmorMaterial(slotMap, enchantValue, sound, repairIngredient, List.of(new ArmorMaterial.Layer(texture)), toughness, knockbackResistance));
    }

}
