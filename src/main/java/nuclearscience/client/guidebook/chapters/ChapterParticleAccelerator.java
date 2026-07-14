package nuclearscience.client.guidebook.chapters;

import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import nuclearscience.NuclearScience;
import nuclearscience.common.block.subtype.SubtypeNuclearMachine;
import nuclearscience.common.entity.EntityParticle;
import nuclearscience.common.settings.NuclearConstants;
import nuclearscience.common.tile.accelerator.TileElectromagneticGateway;
import nuclearscience.prefab.utils.NuclearTextUtils;
import nuclearscience.registers.NuclearScienceItems;
import voltaic.api.electricity.formatting.ChatFormatter;
import voltaic.api.electricity.formatting.DisplayUnits;
import voltaic.client.guidebook.utils.components.Chapter;
import voltaic.client.guidebook.utils.components.Module;
import voltaic.client.guidebook.utils.pagedata.graphics.ImageWrapperObject;
import voltaic.client.guidebook.utils.pagedata.graphics.ItemWrapperObject;
import voltaic.client.guidebook.utils.pagedata.text.TextWrapperObject;

public class ChapterParticleAccelerator extends Chapter {

    private static final ItemWrapperObject LOGO = new ItemWrapperObject(7, 10, 32, 32, 32, 2.0F, NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.particleinjector));

    public ChapterParticleAccelerator(Module module) {
        super(module);
    }

    @Override
    public ItemWrapperObject getLogo() {
        return LOGO;
    }

    @Override
    public IFormattableTextComponent getTitle() {
        return NuclearTextUtils.guidebook("chapter.particleaccelerator");
    }

    @Override
    public void addData() {
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.particleaccelerator.l1")).setIndentions(1));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.particleaccelerator.formula")).setIndentions(1).setSeparateStart());
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.particleaccelerator.l2")).setSeparateStart());
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.particleaccelerator.l3",
                //
                NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.particleinjector).getDescription().copy().withStyle(TextFormatting.BOLD),
                //
                NuclearScienceItems.ITEM_CELLELECTROMAGNETIC.get().getDescription().copy().withStyle(TextFormatting.BOLD),
                //
                ChatFormatter.getChatDisplayShort(NuclearConstants.PARTICLEINJECTOR_USAGE_PER_PARTICLE, DisplayUnits.JOULES).withStyle(TextFormatting.BOLD),
                //
                ChatFormatter.getChatDisplayShort(NuclearConstants.PARTICLEINJECTOR_USAGE_PER_PARTICLE * 2.0, DisplayUnits.JOULES).withStyle(TextFormatting.BOLD)
                //
        )).setIndentions(1).setSeparateStart());
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, NuclearScience.rl("textures/screen/guidebook/particleaccelerator1.png")));
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, NuclearScience.rl("textures/screen/guidebook/particleaccelerator2.png")));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.particleaccelerator.l4",
                //
                NuclearScienceItems.ITEM_ELECTROMAGNETICBOOSTER.get().getDescription().copy().withStyle(TextFormatting.BOLD),
                //
                ChatFormatter.getChatDisplayShort(TileElectromagneticGateway.getLightSpeedPerc(EntityParticle.STRAIGHT_SPEED_INCREMENT), DisplayUnits.PERCENTAGE).withStyle(TextFormatting.BOLD),
                //
                ChatFormatter.getChatDisplayShort(TileElectromagneticGateway.getLightSpeedPerc(1.0F - EntityParticle.TURN_SPEED_PENALTY), DisplayUnits.PERCENTAGE).withStyle(TextFormatting.BOLD)
                //
        )).setIndentions(1).setSeparateStart());
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.particleaccelerator.l5",
                //
                new StringTextComponent(NuclearConstants.PARTICLE_SURVIVAL_TICKS + "").withStyle(TextFormatting.BOLD)
                //
        )).setIndentions(1).setSeparateStart());
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.particleaccelerator.l6")).setIndentions(1).setSeparateStart());
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, NuclearScience.rl("textures/screen/guidebook/particleaccelerator3.png")));
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, NuclearScience.rl("textures/screen/guidebook/particleaccelerator4.png")));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.particleaccelerator.l7",
                //
                NuclearScienceItems.ITEM_ELECTROMAGNETICSWITCH.get().getDescription().copy().withStyle(TextFormatting.BOLD)
                //
        )).setIndentions(1).setSeparateStart());
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, NuclearScience.rl("textures/screen/guidebook/particleaccelerator5.png")));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.particleaccelerator.l8")).setIndentions(1).setSeparateStart());
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, NuclearScience.rl("textures/screen/guidebook/particleaccelerator6.png")));
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, NuclearScience.rl("textures/screen/guidebook/particleaccelerator7.png")));

        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.particleaccelerator.l9")).setIndentions(1).setSeparateStart());
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, NuclearScience.rl("textures/screen/guidebook/particleaccelerator8.png")));
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, NuclearScience.rl("textures/screen/guidebook/particleaccelerator9.png")));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.particleaccelerator.l10")).setSeparateStart());

        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.particleaccelerator.l11",
                //
                NuclearScienceItems.ITEM_ELECTROMAGNETICGATEWAY.get().getDescription().copy().withStyle(TextFormatting.BOLD),
                //
                NuclearScienceItems.ITEM_ELECTROMAGNETICDIODE.get().getDescription().copy().withStyle(TextFormatting.BOLD)
                //
        )).setIndentions(1).setSeparateStart());

        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, NuclearScience.rl("textures/screen/guidebook/particleaccelerator10.png")));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.particleaccelerator.l12")).setIndentions(1).setSeparateStart());
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, NuclearScience.rl("textures/screen/guidebook/particleaccelerator11.png")));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.particleaccelerator.l13")).setIndentions(1).setSeparateStart());
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, NuclearScience.rl("textures/screen/guidebook/particleaccelerator12.png")));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.particleaccelerator.l14")).setIndentions(1).setSeparateStart());
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, NuclearScience.rl("textures/screen/guidebook/particleaccelerator13.png")));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.particleaccelerator.l15")).setIndentions(1).setSeparateStart());
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, NuclearScience.rl("textures/screen/guidebook/particleaccelerator14.png")));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.particleaccelerator.l16")).setIndentions(1).setSeparateStart());
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, NuclearScience.rl("textures/screen/guidebook/particleaccelerator15.png")));
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, NuclearScience.rl("textures/screen/guidebook/particleaccelerator16.png")));

    }

}
