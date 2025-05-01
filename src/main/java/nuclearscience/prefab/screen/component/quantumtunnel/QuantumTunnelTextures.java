package nuclearscience.prefab.screen.component.quantumtunnel;

import net.minecraft.resources.ResourceLocation;
import nuclearscience.NuclearScience;
import voltaic.api.screen.ITexture;

public enum QuantumTunnelTextures implements ITexture {
    FREQUENCY(18, 18, 0, 0, 18, 18, NuclearScience.rl("textures/screen/component/quantumtunnel/frequency.png")),
    FREQUENCY_SELECTED(18, 18, 0, 0, 18, 18, NuclearScience.rl("textures/screen/component/quantumtunnel/frequencyselected.png"));

    private final int textureWidth;
    private final int textureHeight;
    private final int textureU;
    private final int textureV;
    private final int imageWidth;
    private final int imageHeight;
    private final ResourceLocation loc;

    private QuantumTunnelTextures(int textureWidth, int textureHeight, int textureU, int textureV, int imageWidth, int imageHeight, ResourceLocation loc) {
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
        return this.loc;
    }

    @Override
    public int imageHeight() {
        return this.imageHeight;
    }

    @Override
    public int imageWidth() {
        return this.imageWidth;
    }

    @Override
    public int textureHeight() {
        return this.textureHeight;
    }

    @Override
    public int textureU() {
        return this.textureU;
    }

    @Override
    public int textureV() {
        return this.textureV;
    }

    @Override
    public int textureWidth() {
        return this.textureWidth;
    }

}
