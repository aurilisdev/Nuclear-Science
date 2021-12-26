package nuclearscience.prefab.screen.component;

import java.awt.Rectangle;
import java.util.function.DoubleSupplier;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.screen.IScreenWrapper;
import electrodynamics.prefab.screen.component.ScreenComponent;
import electrodynamics.prefab.utilities.RenderingUtils;
import net.minecraft.resources.ResourceLocation;
import nuclearscience.References;

public class ScreenComponentGasCentrifuge extends ScreenComponent {

	private static final ResourceLocation TEXTURE = new ResourceLocation(References.ID + ":textures/screen/component/nsprocessingarrows.png");

	private static final int WIDTH = 92;
	private static final int HEIGHT = 54;

	private static final int BULB_WIDTH = 13;
	private static final int BULB_HEIGHT = 48;
	private static final int BULB_Y_OFFSET = 57;

	private static final int ARROW_WIDTH = 20;
	private static final int ARROW_HEIGHT = 13;
	private static final int ARROW_Y_OFFSET = 105;

	private final DoubleSupplier progressInfoHandlerBulbs;
	private final DoubleSupplier progressInfoHandlerProgressTop;
	private final DoubleSupplier progressInfoHandlerProgressMiddle;
	private final DoubleSupplier progressInfoHandlerProgressBottom;

	public ScreenComponentGasCentrifuge(final DoubleSupplier progressInfoHandlerBulbs, final DoubleSupplier progressInfoHandlerProgressTop,
			final DoubleSupplier progressInfoHandlerProgressMiddle, DoubleSupplier progressInfoHandlerProgressBottom, final IScreenWrapper gui,
			final int x, final int y) {
		super(TEXTURE, gui, x, y);
		this.progressInfoHandlerBulbs = progressInfoHandlerBulbs;
		this.progressInfoHandlerProgressTop = progressInfoHandlerProgressTop;
		this.progressInfoHandlerProgressMiddle = progressInfoHandlerProgressMiddle;
		this.progressInfoHandlerProgressBottom = progressInfoHandlerProgressBottom;
	}

	@Override
	public Rectangle getBounds(final int guiWidth, final int guiHeight) {
		return new Rectangle(guiWidth + xLocation, guiHeight + yLocation, WIDTH, HEIGHT);
	}

	@Override
	public void renderBackground(PoseStack stack, final int xAxis, final int yAxis, final int guiWidth, final int guiHeight) {
		RenderingUtils.bindTexture(resource);
		gui.drawTexturedRect(stack, guiWidth + xLocation, guiHeight + yLocation, 0, 0, WIDTH, HEIGHT);
		int progressBulbs = (int) (progressInfoHandlerBulbs.getAsDouble() * BULB_WIDTH);
		int topProgress = (int) (progressInfoHandlerProgressTop.getAsDouble() * ARROW_WIDTH);
		int middleProgress = (int) (progressInfoHandlerProgressMiddle.getAsDouble() * ARROW_WIDTH);
		int bottomProgress = (int) (progressInfoHandlerProgressBottom.getAsDouble() * ARROW_WIDTH);
		gui.drawTexturedRect(stack, guiWidth + xLocation, guiHeight + yLocation + 3, 0, BULB_Y_OFFSET, progressBulbs, BULB_HEIGHT);
		gui.drawTexturedRect(stack, guiWidth + xLocation + 72, guiHeight + yLocation, 0, ARROW_Y_OFFSET, topProgress, ARROW_HEIGHT);
		gui.drawTexturedRect(stack, guiWidth + xLocation + 72, guiHeight + yLocation + 20, 0, ARROW_Y_OFFSET, middleProgress, ARROW_HEIGHT);
		gui.drawTexturedRect(stack, guiWidth + xLocation + 72, guiHeight + yLocation + 41, 0, ARROW_Y_OFFSET, bottomProgress, ARROW_HEIGHT);
	}

}
