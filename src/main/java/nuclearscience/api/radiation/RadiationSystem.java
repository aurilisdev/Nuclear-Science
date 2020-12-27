package nuclearscience.api.radiation;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;

public class RadiationSystem {

	public static double getRadiation(World world, Vector3f source, Vector3f end, double strength) {
		double radiation = 0;
		double distance = 1 + Math.sqrt(Math.pow(source.getX() - end.getX(), 2) + Math.pow(source.getY() - end.getY(), 2) + Math.pow(source.getZ() - end.getZ(), 2));
		Vector3f clone = end.copy();
		double modifier = 1;
		Vector3f newSource = source.copy();
		clone.sub(source);
		clone.normalize();
		clone.mul(0.33f);
		int checks = (int) Math.floor(distance * 3);
		BlockPos curr = new BlockPos(newSource.getX(), newSource.getY(), newSource.getZ());
		double lastHard = 0;
		while (checks > 0) {
			newSource.add(clone);
			double hard = lastHard;
			BlockPos next = new BlockPos(newSource.getX(), newSource.getY(), newSource.getZ());
			if (!curr.equals(next)) {
				curr = next;
				lastHard = hard = world.getBlockState(curr).getBlockHardness(world, curr);
			}
			modifier += hard / 4.5f;
			checks--;
		}
		radiation = strength / (modifier * distance * distance);
		return radiation;
	}
}
