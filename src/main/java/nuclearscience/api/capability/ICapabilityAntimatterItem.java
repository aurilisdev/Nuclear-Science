package nuclearscience.api.capability;

import net.minecraft.nbt.CompoundNBT;

public interface ICapabilityAntimatterItem {
	
	int getTime();
	
	void incrementTime();
	
	CompoundNBT toTag();
	
	void fromTag(CompoundNBT tag);

}
