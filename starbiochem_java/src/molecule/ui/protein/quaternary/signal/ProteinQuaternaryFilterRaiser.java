package molecule.ui.protein.quaternary.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface ProteinQuaternaryFilterRaiser extends Raiser
{
	int getValue();
}
