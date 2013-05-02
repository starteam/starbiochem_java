package molecule.ui.protein.secondary.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface ProteinSecondaryHBondTranslucencyRaiser extends Raiser
{
	int getValue();
}
