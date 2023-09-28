package nuclearscience.prefab.utils;

import static electrodynamics.prefab.utilities.ElectroTextUtils.ADVANCEMENT_BASE;
import static electrodynamics.prefab.utilities.ElectroTextUtils.BLOCK_BASE;
import static electrodynamics.prefab.utilities.ElectroTextUtils.CREATIVE_TAB;
import static electrodynamics.prefab.utilities.ElectroTextUtils.GAS_BASE;
import static electrodynamics.prefab.utilities.ElectroTextUtils.GUIDEBOOK_BASE;
import static electrodynamics.prefab.utilities.ElectroTextUtils.GUI_BASE;
import static electrodynamics.prefab.utilities.ElectroTextUtils.JEI_BASE;
import static electrodynamics.prefab.utilities.ElectroTextUtils.JEI_INFO_FLUID;
import static electrodynamics.prefab.utilities.ElectroTextUtils.JEI_INFO_ITEM;
import static electrodynamics.prefab.utilities.ElectroTextUtils.MESSAGE_BASE;
import static electrodynamics.prefab.utilities.ElectroTextUtils.TOOLTIP_BASE;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import nuclearscience.References;

/**
 * I will see about condensing this into a single class later, for now this works
 * 
 * @author skip999
 *
 */
public class NuclearTextUtils {

	public static MutableComponent tooltip(String key, Object... additional) {
		return translated(TOOLTIP_BASE, key, additional);
	}

	public static MutableComponent guidebook(String key, Object... additional) {
		return translated(GUIDEBOOK_BASE, key, additional);
	}

	public static MutableComponent gui(String key, Object... additional) {
		return translated(GUI_BASE, key, additional);
	}

	public static MutableComponent chatMessage(String key, Object... additional) {
		return translated(MESSAGE_BASE, key, additional);
	}

	public static MutableComponent jeiTranslated(String key, Object... additional) {
		return Component.translatable(JEI_BASE + "." + key, additional);
	}

	public static MutableComponent jeiItemTranslated(String key, Object... additional) {
		return jeiTranslated(JEI_INFO_ITEM + "." + key, additional);
	}

	public static MutableComponent jeiFluidTranslated(String key, Object... additional) {
		return jeiTranslated(JEI_INFO_FLUID + "." + key, additional);
	}

	public static MutableComponent block(String key, Object... additional) {
		return translated(BLOCK_BASE, key, additional);
	}

	public static MutableComponent gas(String key, Object... additional) {
		return translated(GAS_BASE, key, additional);
	}

	public static MutableComponent advancement(String key, Object... additional) {
		return translated(ADVANCEMENT_BASE, key, additional);
	}

	public static MutableComponent creativeTab(String key, Object... additional) {
		return translated(CREATIVE_TAB, key, additional);
	}

	public static MutableComponent translated(String base, String key, Object... additional) {
		return Component.translatable(base + "." + References.ID + "." + key, additional);
	}

	public static boolean guiExists(String key) {
		return translationExists(GUI_BASE, key);
	}

	public static boolean tooltipExists(String key) {
		return translationExists(TOOLTIP_BASE, key);
	}

	public static boolean translationExists(String base, String key) {
		return I18n.exists(base + "." + References.ID + "." + key);
	}

}
