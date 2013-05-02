package molecule.ui.protein.secondary.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface ProteinSecondaryFilterRaiser extends Raiser
{
	int getValue();
}
