package nuclearscience.client.guidebook;

import java.util.ArrayList;
import java.util.List;

import electrodynamics.client.guidebook.utils.ImageWrapperObject;
import electrodynamics.client.guidebook.utils.components.Chapter;
import electrodynamics.client.guidebook.utils.components.Module;
import nuclearscience.References;
import nuclearscience.client.guidebook.chapters.ChapterFissionReactor;
import nuclearscience.client.guidebook.chapters.ChapterFusionReactor;
import nuclearscience.client.guidebook.chapters.ChapterMSReactor;
import nuclearscience.client.guidebook.chapters.ChapterMisc;
import nuclearscience.client.guidebook.chapters.ChapterOtherMachines;
import nuclearscience.client.guidebook.chapters.ChapterParticleAccelerator;
import nuclearscience.client.guidebook.chapters.ChapterRadiation;

public class ModuleNuclearScience extends Module {

	private static final ImageWrapperObject LOGO = new ImageWrapperObject(10, 38, 0, 0, 32, 32, 32, 32, References.ID + ":textures/screen/guidebook/nuclearsciencelogo.png");

	@Override
	public ImageWrapperObject getLogo() {
		return LOGO;
	}

	@Override
	public String getTitleCat() {
		return References.ID;
	}

	@Override
	protected List<Chapter> genChapters() {
		List<Chapter> chapters = new ArrayList<>();
		chapters.add(new ChapterRadiation());
		chapters.add(new ChapterFissionReactor());
		chapters.add(new ChapterMSReactor());
		chapters.add(new ChapterFusionReactor());
		chapters.add(new ChapterParticleAccelerator());
		chapters.add(new ChapterOtherMachines());
		chapters.add(new ChapterMisc());
		return chapters;
	}

	@Override
	public boolean isFirst() {
		return false;
	}

}
