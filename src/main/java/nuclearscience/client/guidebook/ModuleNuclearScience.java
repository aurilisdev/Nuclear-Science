package nuclearscience.client.guidebook;

import net.minecraft.network.chat.MutableComponent;
import nuclearscience.NuclearScience;
import nuclearscience.client.guidebook.chapters.ChapterFissionReactor;
import nuclearscience.client.guidebook.chapters.ChapterFusionReactor;
import nuclearscience.client.guidebook.chapters.ChapterGasCentrifuge;
import nuclearscience.client.guidebook.chapters.ChapterLogisticsNetwork;
import nuclearscience.client.guidebook.chapters.ChapterMSReactor;
import nuclearscience.client.guidebook.chapters.ChapterMisc;
import nuclearscience.client.guidebook.chapters.ChapterOtherMachines;
import nuclearscience.client.guidebook.chapters.ChapterParticleAccelerator;
import nuclearscience.client.guidebook.chapters.ChapterQuantumTunnel;
import nuclearscience.client.guidebook.chapters.ChapterRadiation;
import nuclearscience.client.guidebook.chapters.ChapterRadioGenerator;
import nuclearscience.client.guidebook.chapters.ChapterSteamFunnel;
import nuclearscience.client.guidebook.chapters.ChapterTurbines;
import nuclearscience.prefab.utils.NuclearTextUtils;
import voltaic.client.guidebook.utils.pagedata.graphics.ImageWrapperObject;

public class ModuleNuclearScience extends voltaic.client.guidebook.utils.components.Module {

	private static final ImageWrapperObject LOGO = new ImageWrapperObject(0, 0, 0, 0, 32, 32, 32, 32, NuclearScience.rl("textures/screen/guidebook/nuclearsciencelogo.png"));

	@Override
	public ImageWrapperObject getLogo() {
		return LOGO;
	}

	@Override
	public MutableComponent getTitle() {
		return NuclearTextUtils.guidebook(NuclearScience.ID);
	}

	@Override
	public void addChapters() {
		chapters.add(new ChapterRadiation(this));
		chapters.add(new ChapterTurbines(this));
		chapters.add(new ChapterSteamFunnel(this));
		chapters.add(new ChapterGasCentrifuge(this));
		chapters.add(new ChapterFissionReactor(this));
		chapters.add(new ChapterRadioGenerator(this));
		chapters.add(new ChapterMSReactor(this));
		chapters.add(new ChapterFusionReactor(this));
		chapters.add(new ChapterLogisticsNetwork(this));
		chapters.add(new ChapterParticleAccelerator(this));
		chapters.add(new ChapterQuantumTunnel(this));
		chapters.add(new ChapterOtherMachines(this));
		chapters.add(new ChapterMisc(this));
	}

}
