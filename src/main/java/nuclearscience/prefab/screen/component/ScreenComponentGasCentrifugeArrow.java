package nuclearscience.prefab.screen.component;

import java.awt.Rectangle;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.screen.IScreenWrapper;
import electrodynamics.prefab.screen.component.ScreenComponent;
import electrodynamics.prefab.utilities.UtilitiesRendering;
import net.minecraft.resources.ResourceLocation;
import nuclearscience.References;

public class ScreenComponentGasCentrifugeArrow extends ScreenComponent {

	public static int WIDTH = 90;
	public static int HEIGHT = 54;

	public ScreenComponentGasCentrifugeArrow(IScreenWrapper gui, int x, int y) {
		super(new ResourceLocation(References.ID + ":textures/screen/component/nsprocessingarrows.png"), gui, x, y);
	}

	@Override
	public Rectangle getBounds(final int guiWidth, final int guiHeight) {
		return new Rectangle(guiWidth + xLocation, guiHeight + yLocation, WIDTH, HEIGHT);
	}

	@Override
	public void renderBackground(PoseStack stack, final int xAxis, final int yAxis, final int guiWidth, final int guiHeight) {
		UtilitiesRendering.bindTexture(resource);
		gui.drawTexturedRect(stack, guiWidth + xLocation, guiHeight + yLocation, 0, 0, WIDTH, HEIGHT);
	}

}
