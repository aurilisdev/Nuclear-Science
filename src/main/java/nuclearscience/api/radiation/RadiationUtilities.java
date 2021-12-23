package nuclearscience.api.radiation;

import java.util.List;

import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.utilities.object.Location;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class RadiationUtilities {
	
	public static void emitRadiationFromTile(GenericTile tile, double radiusBlocks, int strength) {
		Location source = new Location(tile.getBlockPos().getX() + 0.5f, tile.getBlockPos().getY() + 0.5f, tile.getBlockPos().getZ() + 0.5f);
		AABB bb = AABB.ofSize(new Vec3(source.x(), source.y(), source.z()), radiusBlocks * 2, radiusBlocks * 2, radiusBlocks * 2);
		List<LivingEntity> list = tile.getLevel().getEntitiesOfClass(LivingEntity.class, bb);
		for (LivingEntity living : list) {
			RadiationSystem.applyRadiation(living, source, strength);
		}
	}

}
