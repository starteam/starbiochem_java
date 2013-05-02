package molecule.ui.nucleic.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface NucleicHBondTranslucencyRaiser extends Raiser
{
	int getValue();
}
