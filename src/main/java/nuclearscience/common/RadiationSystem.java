package nuclearscience.common;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;

public class RadiationSystem {

	public static float getRadiation(World world, Vector3f source, Vector3f end, float strength) {
		float radiation = 0;
		float distance = (float) Math.sqrt(Math.pow(source.getX() - end.getX(), 2)
				+ Math.pow(source.getY() - end.getY(), 2) + Math.pow(source.getZ() - end.getZ(), 2));
		float maxDistance = strength / 1000.0f;
		if (distance < maxDistance) {
			Vector3f clone = end.copy();
			float modifier = 1;
			clone.sub(source);
			clone.normalize();
			clone.mul(0.33f);
			int checks = (int) Math.floor(distance * 3);
			while (checks > 0) {
				source.add(clone);
				BlockPos pos = new BlockPos(source.getX(), source.getY(), source.getZ());
				float hard = world.getBlockState(pos).getBlockHardness(world, pos);
				modifier += hard / 4.5f;
				checks--;
			}
			radiation = strength / (modifier * distance * distance);
		}
		return radiation;
	}
}
