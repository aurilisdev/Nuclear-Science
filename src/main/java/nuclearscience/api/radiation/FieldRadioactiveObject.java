package nuclearscience.api.radiation;

public class FieldRadioactiveObject implements IRadioactiveObject {
	private double strength;

	public FieldRadioactiveObject(double strength) {
		this.strength = strength;
	}

	@Override
	public double getRadiationStrength() {
		return strength;
	}

}