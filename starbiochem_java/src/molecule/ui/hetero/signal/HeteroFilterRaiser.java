package molecule.ui.hetero.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface HeteroFilterRaiser extends Raiser
{
	int getValue();
}
