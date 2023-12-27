package nuclearscience.prefab.screen.component;

import electrodynamics.api.screen.ITexture;
import net.minecraft.util.ResourceLocation;
import nuclearscience.References;

public enum NuclearArrows implements ITexture {
	
	FISSION_REACTOR_ARROW_LR(0, 0, 54, 19, 54, 19, new ResourceLocation(References.ID, "textures/screen/component/fissionreactorarrow_lr.png")),
	FISSION_REACTOR_ARROW_DOWN(0, 0, 14, 13, 14, 13, new ResourceLocation(References.ID, "textures/screen/component/fissionreactorarrow_down.png")),
	PARTICLE_INJECTOR_ARROWS(0, 0, 86, 48, 86, 48, new ResourceLocation(References.ID, "textures/screen/component/particleinjectorarrows.png"));

	private final int textU;
	private final int textV;
	private final int textWidth;
	private final int textHeight;
	private final int imgWidth;
	private final int imgHeight;
	private final ResourceLocation loc;

	private NuclearArrows(int textU, int textV, int textWidth, int textHeight, int imgWidth, int imgHeight, ResourceLocation loc) {
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