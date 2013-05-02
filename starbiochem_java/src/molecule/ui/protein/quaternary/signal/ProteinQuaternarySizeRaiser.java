package molecule.ui.protein.quaternary.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface ProteinQuaternarySizeRaiser extends Raiser
{
	int getValue();
}
