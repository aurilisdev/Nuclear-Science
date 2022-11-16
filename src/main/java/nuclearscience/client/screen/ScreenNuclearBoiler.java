package nuclearscience.client.screen;

import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.ScreenComponentFluid;
import electrodynamics.prefab.screen.component.ScreenComponentInfo;
import electrodynamics.prefab.screen.component.ScreenComponentProgress;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.generic.AbstractFluidHandler;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import nuclearscience.common.inventory.container.ContainerNuclearBoiler;
import nuclearscience.common.tile.TileNuclearBoiler;

@OnlyIn(Dist.CLIENT)
public class ScreenNuclearBoiler extends GenericScreen<ContainerNuclearBoiler> {
	public ScreenNuclearBoiler(ContainerNuclearBoiler container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);
		components.add(new ScreenComponentProgress(() -> {
			GenericTile furnace = container.getHostFromIntArray();
			if (furnace != null) {
				ComponentProcessor processor = furnace.getComponent(ComponentType.Processor);
				if (processor.operatingTicks.get() > 0) {
					return Math.min(1.0, processor.operatingTicks.get() / (processor.requiredTicks.get() / 2.0));
				}
			}
			return 0;
		}, this, 42, 30));
		components.add(new ScreenComponentProgress(() -> {
			GenericTile furnace = container.getHostFromIntArray();
			if (furnace != null) {
				ComponentProcessor processor = furnace.getComponent(ComponentType.Processor);
				if (processor.operatingTicks.get() > processor.requiredTicks.get() / 2.0) {
					return Math.min(1.0, (processor.operatingTicks.get() - processor.requiredTicks.get() / 2.0) / (processor.requiredTicks.get() / 2.0));
				}
			}
			return 0;
		}, this, 98, 30));
		components.add(new ScreenComponentProgress(() -> 0, this, 42, 50).left());
		components.add(new ScreenComponentFluid(() -> {
			TileNuclearBoiler boiler = container.getHostFromIntArray();
			if (boiler != null) {
				AbstractFluidHandler<?> handler = boiler.getComponent(ComponentType.FluidHandler);
				for (Fluid fluid : handler.getValidInputFluids()) {
					FluidTank tank = handler.getTankFromFluid(fluid, true);
					if (tank.getFluidAmount() > 0) {
						return tank;
					}
				}
			}
			return null;
		}, this, 21, 18));
		components.add(new ScreenComponentFluid(() -> {
			TileNuclearBoiler boiler = container.getHostFromIntArray();
			if (boiler != null) {
				AbstractFluidHandler<?> handler = boiler.getComponent(ComponentType.FluidHandler);
				for (Fluid fluid : handler.getValidOutputFluids()) {
					FluidTank tank = handler.getTankFromFluid(fluid, false);
					if (tank.getFluidAmount() > 0) {
						return tank;
					}
				}
			}
			return null;
		}, this, 127, 18));
		components.add(new ScreenComponentElectricInfo(this, -ScreenComponentInfo.SIZE + 1, 2));
	}

}