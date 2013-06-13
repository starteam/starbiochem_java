package molecule.ui.hetero.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface HeteroHBondSizeRaiser extends Raiser
{
	int getValue();
}
