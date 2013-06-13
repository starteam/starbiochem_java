package molecule.ui.protein.tertiary.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface ProteinTertiarySizeRaiser extends Raiser
{
	int getValue();
	boolean isFromProteinTertiarySizeRaiser();
}
