package nuclearscience.prefab.screen.component;

import electrodynamics.api.screen.ITexture;
import net.minecraft.resources.ResourceLocation;
import nuclearscience.References;

public enum NuclearIconTypes implements ITexture {
	
	PELLET_DARK(0, 0, 14, 14, 14, 14, new ResourceLocation(References.ID, "textures/screen/component/icon/pelletdark.png")),
	FUEL_CELL_DARK(0, 0, 12, 12, 12, 12, new ResourceLocation(References.ID, "textures/screen/component/icon/fuelcelldark.png"));

	private final int textU;
	private final int textV;
	private final int textWidth;
	private final int textHeight;
	private final int imgWidth;
	private final int imgHeight;
	private final ResourceLocation loc;

	private NuclearIconTypes(int textU, int textV, int textWidth, int textHeight, int imgWidth, int imgHeight, ResourceLocation loc) {
		this.textU = textU;
		this.textV = textV;
		this.textWidth = textWidth;
		this.textHeight = textHeight;
		this.imgWidth = imgWidth;
		this.imgHeight = imgHeight;
		this.loc = loc;
	}

	@Override
	public ResourceLocation getLocation() {
		return loc;
	}

	@Override
	public int imageHeight() {
		return imgHeight;
	}

	@Override
	public int imageWidth() {
		return imgWidth;
	}

	@Override
	public int textureHeight() {
		return textHeight;
	}

	@Override
	public int textureU() {
		return textU;
	}

	@Override
	public int textureV() {
		return textV;
	}

	@Override
	public int textureWidth() {
		return textWidth;
	}

}
