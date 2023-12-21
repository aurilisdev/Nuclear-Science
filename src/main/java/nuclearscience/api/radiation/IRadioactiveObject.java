package nuclearscience.api.radiation;

public interface IRadioactiveObject {
	
	public static final IRadioactiveObject NULL = new IRadioactiveObject() {
		
		@Override
		public double getRadiationStrength() {
			return 0;
		}
	};
	
	double getRadiationStrength();
	
	default boolean isNull() {
		return this == NULL;
	}
}