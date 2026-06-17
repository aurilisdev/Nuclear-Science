package nuclearscience.prefab.screen.component;

import net.minecraft.resources.ResourceLocation;
import nuclearscience.NuclearScience;
import voltaic.api.screen.ITexture;

public enum NuclearIconTypes implements ITexture {
    PELLET_DARK(0, 0, 14, 14, 14, 14, NuclearScience.rl("textures/screen/component/icon/pelletdark.png")),
    FUEL_CELL_DARK(0, 0, 12, 12, 12, 12, NuclearScience.rl("textures/screen/component/icon/fuelcelldark.png")),
    PENCIL(0, 0, 10, 10, 10, 10, NuclearScience.rl("textures/screen/component/icon/pencil.png")),
    ERASER(0, 0, 14, 14, 14, 14, NuclearScience.rl("textures/screen/component/icon/eraser.png")),
    ENABLE(0, 0, 14, 11, 14, 11, NuclearScience.rl("textures/screen/component/icon/enable.png")),
    DISABLE(0, 0, 10, 10, 10, 10, NuclearScience.rl("textures/screen/component/icon/disable.png")),
    DELETE(0, 0, 8, 8, 8, 8, NuclearScience.rl("textures/screen/component/icon/delete.png")),
    IOCONFIG(0, 0, 13, 13, 13, 13, NuclearScience.rl("textures/screen/component/icon/ioconfig.png")),
    CREATENEW(0, 0, 16, 16, 16, 16, NuclearScience.rl("textures/screen/component/icon/createnew.png")),
    BUFFER(0, 0, 10, 11, 10, 11, NuclearScience.rl("textures/screen/component/icon/buffer.png")),
    LINK(0, 0, 17, 15, 17, 15, NuclearScience.rl("textures/screen/component/icon/link.png")),
    GATEWAY(0, 0, 16, 16, 16, 16, NuclearScience.rl("textures/screen/component/icon/gateway.png")),
    PARTICLEACCELERATOR_DMATOM(16, 16, 0, 0, 16, 16,
	    NuclearScience.rl("textures/screen/jei/particleaccelerator_dmatom.png"));

    private final int textU;
    private final int textV;
    private final int textWidth;
    private final int textHeight;
    private final int imgWidth;
    private final int imgHeight;
    private final ResourceLocation loc;

    private NuclearIconTypes(int textU, int textV, int textWidth, int textHeight, int imgWidth, int imgHeight,
	    ResourceLocation loc) {
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
