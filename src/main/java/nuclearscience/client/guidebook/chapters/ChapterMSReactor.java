package nuclearscience.client.guidebook.chapters;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import nuclearscience.NuclearScience;
import nuclearscience.common.block.subtype.SubtypeMoltenSaltPipe;
import nuclearscience.common.block.subtype.SubtypeNuclearMachine;
import nuclearscience.common.tile.reactor.moltensalt.TileMoltenSaltSupplier;
import nuclearscience.prefab.utils.NuclearTextUtils;
import nuclearscience.registers.NuclearScienceItems;
import voltaic.client.guidebook.utils.components.Chapter;
import voltaic.client.guidebook.utils.components.Module;
import voltaic.client.guidebook.utils.pagedata.graphics.ImageWrapperObject;
import voltaic.client.guidebook.utils.pagedata.graphics.ItemWrapperObject;
import voltaic.client.guidebook.utils.pagedata.text.TextWrapperObject;

public class ChapterMSReactor extends Chapter {

    private static final ItemWrapperObject LOGO = new ItemWrapperObject(7, 10, 32, 32, 32, 2.0F, NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.msreactorcore));

    public ChapterMSReactor(Module module) {
        super(module);
    }

    @Override
    public ItemWrapperObject getLogo() {
        return LOGO;
    }

    @Override
    public MutableComponent getTitle() {
        return NuclearTextUtils.guidebook("chapter.msreactor");
    }

    @Override
    public void addData() {
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.msreactor.l1")).setIndentions(1));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook(
                //
                "chapter.msreactor.l2.1",
                //
                NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.msreactorcore).getDescription().copy().withStyle(ChatFormatting.BOLD),
                //
                NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.freezeplug).getDescription().copy().withStyle(ChatFormatting.BOLD),
                //
                NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.moltensaltsupplier).getDescription().copy().withStyle(ChatFormatting.BOLD)
                //
        )).setIndentions(1).setSeparateStart());
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, NuclearScience.rl("textures/screen/guidebook/msreactor1.png")));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.msreactor.l2.2")).setSeparateStart());
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, NuclearScience.rl("textures/screen/guidebook/msreactor2.png")));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.msreactor.l2.3")).setSeparateStart());
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, NuclearScience.rl("textures/screen/guidebook/msreactor3.png")));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.msreactor.l3")).setIndentions(1).setSeparateStart());
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, NuclearScience.rl("textures/screen/guidebook/msreactor4-1.png")));
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, NuclearScience.rl("textures/screen/guidebook/msreactor4-2.png")));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.msreactor.l4", TileMoltenSaltSupplier.AMT_PER_SALT)).setIndentions(1).setSeparateStart());
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, NuclearScience.rl("textures/screen/guidebook/msreactor5-1.png")));
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, NuclearScience.rl("textures/screen/guidebook/msreactor5-2.png")));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook(
                //
                "chapter.msreactor.l5",
                //
                NuclearScienceItems.ITEMS_MOLTENSALTPIPTE.getValue(SubtypeMoltenSaltPipe.vanadiumsteelceramic).getDescription().copy().withStyle(ChatFormatting.BOLD),
                //
                NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.heatexchanger).getDescription().copy().withStyle(ChatFormatting.BOLD)
                //
        )).setIndentions(1).setSeparateStart());
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, NuclearScience.rl("textures/screen/guidebook/msreactor6.png")));
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, NuclearScience.rl("textures/screen/guidebook/msreactor7.png")));
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, NuclearScience.rl("textures/screen/guidebook/msreactor8.png")));
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, NuclearScience.rl("textures/screen/guidebook/msreactor9.png")));
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, NuclearScience.rl("textures/screen/guidebook/msreactor10.png")));
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, NuclearScience.rl("textures/screen/guidebook/msreactor11.png")));
        pageData.add(new TextWrapperObject(NuclearTextUtils.guidebook("chapter.msreactor.l6", NuclearScienceItems.ITEMS_NUCLEARMACHINE.getValue(SubtypeNuclearMachine.mscontrolrod).getDescription().copy().withStyle(ChatFormatting.BOLD))).setIndentions(1).setSeparateStart());
        pageData.add(new ImageWrapperObject(0, 0, 0, 0, 150, 75, 150, 75, NuclearScience.rl("textures/screen/guidebook/msreactor12.png")));

    }

}
