package nuclearscience.common.tile.accelerator;

import net.minecraft.core.BlockPos;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.level.block.state.BlockState;
import nuclearscience.common.entity.EntityParticle;
import nuclearscience.common.inventory.container.ContainerElectromagneticGateway;
import nuclearscience.registers.NuclearScienceTiles;
import voltaic.prefab.properties.types.PropertyTypes;
import voltaic.prefab.properties.variant.SingleProperty;
import voltaic.prefab.tile.GenericTile;
import voltaic.prefab.tile.components.type.ComponentContainerProvider;
import voltaic.prefab.tile.components.type.ComponentTickable;

public class TileElectromagneticGateway extends GenericTile {

    public final SingleProperty<Float> targetSpeed = property(new SingleProperty<>(PropertyTypes.FLOAT, "targetspeed", 0.0F));

    public TileElectromagneticGateway(BlockPos worldPos, BlockState blockState) {
        super(NuclearScienceTiles.TILE_ELECTROMAGNETICGATEWAY.get(), worldPos, blockState);
        addComponent(new ComponentTickable(this));
        addComponent(new ComponentContainerProvider("electromagneticgateway", this).createMenu((id, player) -> new ContainerElectromagneticGateway(id, player, new SimpleContainer(0), getCoordsArray())));
    }

    public boolean mayPassThrough(float speed) {
        return speed >= getActualSpeed(targetSpeed.getValue());
    }

    public static float getActualSpeed(float lightSpeedPerc) {
        return lightSpeedPerc / 100.0F * EntityParticle.MAX_SPEED;
    }

    public static float getLightSpeedPerc(float actualSpeed) {
        return actualSpeed / EntityParticle.MAX_SPEED * 100.0F;
    }

}
