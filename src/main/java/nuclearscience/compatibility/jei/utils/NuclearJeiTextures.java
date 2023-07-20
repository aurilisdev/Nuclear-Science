package nuclearscience.compatibility.jei.utils;

import electrodynamics.api.screen.ITexture;
import net.minecraft.resources.ResourceLocation;
import nuclearscience.References;
import nuclearscience.client.ClientRegister;

public enum NuclearJeiTextures implements ITexture {

	GASCENTRIFUGE_ARROW_OFF(47, 54, 0, 0, 47, 54, new ResourceLocation(References.ID + ":textures/screen/jei/gascentrifugearrowoff.png")),
	GASCENTRIFUGE_ARROW_ON(47, 54, 0, 0, 47, 54, new ResourceLocation(References.ID + ":textures/screen/jei/gascentrifugearrowon.png")),
	GASCENTRIFUGE_ARROW_STATIC(43, 53, 0, 0, 43, 53, new ResourceLocation(References.ID + ":textures/screen/jei/gascentrifugearrowstatic.png")),

	PARTICLEACCELERATOR_AMARROW_OFF(82, 47, 0, 0, 82, 47, new ResourceLocation(References.ID + ":textures/screen/jei/particleaccelerator_amarrowoff.png")),
	PARTICLEACCELERATOR_AMARROW_ON(82, 47, 0, 0, 82, 47, new ResourceLocation(References.ID + ":textures/screen/jei/particleaccelerator_amarrowon.png")),

	PARTICLEACCELERATOR_DMARROWOFF_LEFT(34, 72, 0, 0, 34, 72, new ResourceLocation(References.ID + ":textures/screen/jei/particleaccelerator_dmarrowoff_left.png")),
	PARTICLEACCELERATOR_DMARROWOFF_RIGHT(34, 72, 0, 0, 34, 72, new ResourceLocation(References.ID + ":textures/screen/jei/particleaccelerator_dmarrowoff_right.png")),
	PARTICLEACCELERATOR_DMARROWON_LEFT(34, 72, 0, 0, 34, 72, new ResourceLocation(References.ID + ":textures/screen/jei/particleaccelerator_dmarrowon_left.png")),
	PARTICLEACCELERATOR_DMARROWON_RIGHT(34, 72, 0, 0, 34, 72, new ResourceLocation(References.ID + ":textures/screen/jei/particleaccelerator_dmarrowon_right.png")),
	PARTICLEACCELERATOR_DMATOM(16, 16, 0, 0, 16, 16, new ResourceLocation(References.ID + ":textures/screen/jei/particleaccelerator_dmatom.png")),
	PARTICLEACCELERATOR_DMBLACKHOLE(77, 76, 0, 0, 77, 76, ClientRegister.TEXTURE_JEIBLACKHOLE);

	private final int textureWidth;
	private final int textureHeight;
	private final int textureU;
	private final int textureV;
	private final int imageWidth;
	private final int imageHeight;
	private final ResourceLocation loc;

	private NuclearJeiTextures(int textureWidth, int textureHeight, int textureU, int textureV, int imageWidth, int imageHeight, ResourceLocation loc) {

		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
		this.textureU = textureU;
		this.textureV = textureV;
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
		this.loc = loc;
	}

	@Override
	public ResourceLocation getLocation() {
		return loc;
	}

	@Override
	public int imageHeight() {
		return imageHeight;
	}

	@Override
	public int imageWidth() {
		return imageWidth;
	}

	@Override
	public int textureHeight() {
		return textureHeight;
	}

	@Override
	public int textureU() {
		return textureU;
	}

	@Override
	public int textureV() {
		return textureV;
	}

	@Override
	public int textureWidth() {
		return textureWidth;
	}

}
