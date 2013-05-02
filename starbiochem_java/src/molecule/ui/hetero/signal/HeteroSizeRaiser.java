package molecule.ui.hetero.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface HeteroSizeRaiser extends Raiser
{
	int getValue();
}
