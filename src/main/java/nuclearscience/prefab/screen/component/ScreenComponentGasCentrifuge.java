package nuclearscience.prefab.screen.component;

import java.util.function.DoubleSupplier;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.screen.ITexture;
import electrodynamics.prefab.screen.component.types.ScreenComponentGeneric;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.resources.ResourceLocation;
import nuclearscience.References;


public class ScreenComponentGasCentrifuge extends ScreenComponentGeneric {

	private static final ResourceLocation TEXTURE = new ResourceLocation(References.ID + ":textures/screen/component/nsprocessingarrows.png");

	private final DoubleSupplier progressInfoHandlerBulbs;
	private final DoubleSupplier progressInfoHandlerProgressTop;
	private final DoubleSupplier progressInfoHandlerProgressMiddle;
	private final DoubleSupplier progressInfoHandlerProgressBottom;

	public ScreenComponentGasCentrifuge(DoubleSupplier progressInfoHandlerBulbs, DoubleSupplier progressInfoHandlerProgressTop, DoubleSupplier progressInfoHandlerProgressMiddle, DoubleSupplier progressInfoHandlerProgressBottom, int x, int y) {
		super(GasCentrifugeTextures.OFF, x, y);
		this.progressInfoHandlerBulbs = progressInfoHandlerBulbs;
		this.progressInfoHandlerProgressTop = progressInfoHandlerProgressTop;
		this.progressInfoHandlerProgressMiddle = progressInfoHandlerProgressMiddle;
		this.progressInfoHandlerProgressBottom = progressInfoHandlerProgressBottom;
	}

	@Override
	public void renderBackground(PoseStack stack, final int xAxis, final int yAxis, final int guiWidth, final int guiHeight) {
		super.renderBackground(stack, xAxis, yAxis, guiWidth, guiHeight);

		ITexture texture = GasCentrifugeTextures.BULB;
		
		RenderingUtils.bindTexture(TEXTURE);
		
		int progressBulbs = (int) (progressInfoHandlerBulbs.getAsDouble() * texture.textureWidth());
		blit(stack, guiWidth + xLocation, guiHeight + yLocation + 3, texture.textureU(), texture.textureV(), progressBulbs, texture.textureHeight(), texture.imageWidth(), texture.imageHeight());

		texture = GasCentrifugeTextures.ARROW;
		int topProgress = (int) (progressInfoHandlerProgressTop.getAsDouble() * texture.textureWidth());
		int middleProgress = (int) (progressInfoHandlerProgressMiddle.getAsDouble() * texture.textureWidth());
		int bottomProgress = (int) (progressInfoHandlerProgressBottom.getAsDouble() * texture.textureWidth());

		blit(stack, guiWidth + xLocation + 72, guiHeight + yLocation, texture.textureU(), texture.textureV(), topProgress, texture.textureHeight(), texture.imageWidth(), texture.imageHeight());
		blit(stack, guiWidth + xLocation + 72, guiHeight + yLocation + 20, texture.textureU(), texture.textureV(), middleProgress, texture.textureHeight(), texture.imageWidth(), texture.imageHeight());
		blit(stack, guiWidth + xLocation + 72, guiHeight + yLocation + 41, texture.textureU(), texture.textureV(), bottomProgress, texture.textureHeight(), texture.imageWidth(), texture.imageHeight());
		
		RenderingUtils.resetShaderColor();

	}

	public enum GasCentrifugeTextures implements ITexture {
		OFF(92, 54, 0, 0, 256, 256),
		BULB(13, 48, 0, 57, 256, 256),
		ARROW(20, 13, 0, 105, 256, 256);

		private final int textureWidth;
		private final int textureHeight;
		private final int textureU;
		private final int textureV;
		private final int imageWidth;
		private final int imageHeight;
		private final ResourceLocation loc;

		GasCentrifugeTextures(int textureWidth, int textureHeight, int textureU, int textureV, int imageWidth, int imageHeight) {
			this.textureWidth = textureWidth;
			this.textureHeight = textureHeight;
			this.textureU = textureU;
			this.textureV = textureV;
			this.imageWidth = imageWidth;
			this.imageHeight = imageHeight;
			loc = TEXTURE;
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

}