package nuclearscience.client.guidebook;

import electrodynamics.client.guidebook.utils.components.Module;
import electrodynamics.client.guidebook.utils.pagedata.ImageWrapperObject;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import nuclearscience.References;
import nuclearscience.client.guidebook.chapters.ChapterFissionReactor;
import nuclearscience.client.guidebook.chapters.ChapterFusionReactor;
import nuclearscience.client.guidebook.chapters.ChapterGasCentrifuge;
import nuclearscience.client.guidebook.chapters.ChapterMSReactor;
import nuclearscience.client.guidebook.chapters.ChapterMisc;
import nuclearscience.client.guidebook.chapters.ChapterOtherMachines;
import nuclearscience.client.guidebook.chapters.ChapterParticleAccelerator;
import nuclearscience.client.guidebook.chapters.ChapterRadiation;
import nuclearscience.client.guidebook.chapters.ChapterRadioGenerator;
import nuclearscience.client.guidebook.chapters.ChapterTurbines;
import nuclearscience.prefab.utils.TextUtils;

public class ModuleNuclearScience extends Module {

	private static final ImageWrapperObject LOGO = new ImageWrapperObject(0, 0, 0, 0, 32, 32, 32, 32, new ResourceLocation(References.ID, "textures/screen/guidebook/nuclearsciencelogo.png"));

	@Override
	public ImageWrapperObject getLogo() {
		return LOGO;
	}

	@Override
	public MutableComponent getTitle() {
		return TextUtils.guidebook(References.ID);
	}

	@Override
	public void addChapters() {
		chapters.add(new ChapterRadiation(this));
		chapters.add(new ChapterTurbines(this));
		chapters.add(new ChapterGasCentrifuge(this));
		chapters.add(new ChapterFissionReactor(this));
		chapters.add(new ChapterRadioGenerator(this));
		chapters.add(new ChapterMSReactor(this));
		chapters.add(new ChapterFusionReactor(this));
		chapters.add(new ChapterParticleAccelerator(this));
		chapters.add(new ChapterOtherMachines(this));
		chapters.add(new ChapterMisc(this));
	}

}
