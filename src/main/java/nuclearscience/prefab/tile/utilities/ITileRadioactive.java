package nuclearscience.prefab.tile.utilities;

/*
 * The idea is you add this interface to a tile if you want it to emit radiation 
 * Then you add the method call in RadiationUtilities to the tickServer method and pass
 * in the outputs of the methods
 */
public interface ITileRadioactive {

	double getStrength();

	double getBlocksRadius();

}
