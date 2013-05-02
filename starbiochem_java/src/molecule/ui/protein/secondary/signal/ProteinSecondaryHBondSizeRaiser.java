package molecule.ui.protein.secondary.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface ProteinSecondaryHBondSizeRaiser extends Raiser
{
	int getValue();
}
