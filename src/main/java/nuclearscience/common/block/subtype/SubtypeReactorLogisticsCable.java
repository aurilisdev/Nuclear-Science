package nuclearscience.common.block.subtype;


import voltaic.api.ISubtype;

public enum SubtypeReactorLogisticsCable implements ISubtype {
    base;

    @Override
    public String tag() {
        return "logisticscable" + name();
    }

    @Override
    public String forgeTag() {
        return tag();
    }

    @Override
    public boolean isItem() {
        return false;
    }
}
