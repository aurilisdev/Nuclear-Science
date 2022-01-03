package nuclearscience.client.screen;

import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.ScreenComponentFluid;
import electrodynamics.prefab.screen.component.ScreenComponentInfo;
import electrodynamics.prefab.screen.component.ScreenComponentProgress;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.tile.components.utils.AbstractFluidHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import nuclearscience.common.inventory.container.ContainerChemicalExtractor;
import nuclearscience.common.tile.TileChemicalExtractor;

@OnlyIn(Dist.CLIENT)
public class ScreenChemicalExtractor extends GenericScreen<ContainerChemicalExtractor> {
	public ScreenChemicalExtractor(ContainerChemicalExtractor container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);
		components.add(new ScreenComponentProgress(() -> {
			GenericTile furnace = container.getHostFromIntArray();
			if (furnace != null) {
				ComponentProcessor processor = furnace.getComponent(ComponentType.Processor);
				if (processor.operatingTicks > 0) {
					return Math.min(1.0, processor.operatingTicks / (processor.requiredTicks / 2.0));
				}
			}
			return 0;
		}, this, 42, 30));
		components.add(new ScreenComponentProgress(() -> {
			GenericTile furnace = container.getHostFromIntArray();
			if (furnace != null) {
				ComponentProcessor processor = furnace.getComponent(ComponentType.Processor);
				if (processor.operatingTicks > processor.requiredTicks / 2.0) {
					return Math.min(1.0, (processor.operatingTicks - processor.requiredTicks / 2.0) / (processor.requiredTicks / 2.0));
				}
			}
			return 0;
		}, this, 98, 30));
		components.add(new ScreenComponentProgress(() -> 0, this, 46, 50).left());
		components.add(new ScreenComponentFluid(() -> {
			TileChemicalExtractor boiler = container.getHostFromIntArray();
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
		components.add(new ScreenComponentElectricInfo(this, -ScreenComponentInfo.SIZE + 1, 2));
	}

}