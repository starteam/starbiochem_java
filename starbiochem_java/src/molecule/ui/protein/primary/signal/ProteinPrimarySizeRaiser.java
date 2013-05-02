package molecule.ui.protein.primary.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface ProteinPrimarySizeRaiser extends Raiser
{
	int getValue();
	boolean isFromProteinPrimarySizeRaiser();
}
