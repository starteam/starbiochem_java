package molecule.ui.protein.primary.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface ProteinPrimaryFilterRaiser extends Raiser
{
	int getValue();
}
