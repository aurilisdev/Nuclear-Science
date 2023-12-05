package nuclearscience.client.screen;

import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.types.ScreenComponentGeneric;
import electrodynamics.prefab.screen.component.types.ScreenComponentSimpleLabel;
import electrodynamics.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.types.wrapper.InventoryIOWrapper;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import nuclearscience.common.inventory.container.ContainerParticleInjector;
import nuclearscience.common.settings.Constants;
import nuclearscience.common.tile.TileParticleInjector;
import nuclearscience.prefab.screen.component.NuclearArrows;
import nuclearscience.prefab.utils.NuclearTextUtils;

@OnlyIn(Dist.CLIENT)
public class ScreenParticleInjector extends GenericScreen<ContainerParticleInjector> {

	public ScreenParticleInjector(ContainerParticleInjector container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);

		imageHeight += 10;
		inventoryLabelY += 10;

		addComponent(new ScreenComponentGeneric(NuclearArrows.PARTICLE_INJECTOR_ARROWS, 44, 24));
		addComponent(new ScreenComponentSimpleLabel(titleLabelX, titleLabelY + 20, 10, 4210752, NuclearTextUtils.gui("particleinjector.matter")));
		addComponent(new ScreenComponentSimpleLabel(titleLabelX, titleLabelY + 56, 10, 4210752, NuclearTextUtils.gui("particleinjector.cells")));
		addComponent(new ScreenComponentSimpleLabel(titleLabelX, titleLabelY + 38, 10, 4210752, () -> {
			TileParticleInjector injector = menu.getHostFromIntArray();
			if (injector == null) {
				return ElectroTextUtils.empty();
			}
			ComponentElectrodynamic electro = injector.getComponent(IComponentType.Electrodynamic);
			return NuclearTextUtils.gui("particleinjector.charge", ChatFormatter.getChatDisplayShort((int) (electro.getJoulesStored() / Constants.PARTICLEINJECTOR_USAGE_PER_PARTICLE * 100.0), DisplayUnit.PERCENTAGE));
		}));
		addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2).wattage(Constants.PARTICLEINJECTOR_USAGE_PER_PARTICLE));

		new InventoryIOWrapper(this, -AbstractScreenComponentInfo.SIZE + 1, AbstractScreenComponentInfo.SIZE + 2, 75, 82, 8, 72);
	}

}