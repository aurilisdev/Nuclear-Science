package nuclearscience.prefab.utils;

import java.text.DecimalFormat;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import nuclearscience.References;

import static electrodynamics.prefab.utilities.TextUtils.*;

/**
 * I will see about condensing this into a single class later, for now this works
 * 
 * @author skip999
 *
 */
public class TextUtils {
	
	public static final DecimalFormat FORMATTER = new DecimalFormat("0.0##");

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
