package molecule.ui.protein.secondary.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface ProteinSecondarySizeRaiser extends Raiser
{
	int getValue();
}
