package nuclearscience.common.block.subtype;

import electrodynamics.api.ISubtype;

public enum SubtypeMoltenSaltPipe implements ISubtype {
	vanadiumsteelceramic(10000);

	public final long maxTransfer;

	SubtypeMoltenSaltPipe(long maxTransfer) {
		this.maxTransfer = maxTransfer;
	}

	@Override
	public String tag() {
		return "moltensaltpipe" + name();
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
