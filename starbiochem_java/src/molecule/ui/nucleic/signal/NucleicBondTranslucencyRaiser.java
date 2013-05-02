package molecule.ui.nucleic.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface NucleicBondTranslucencyRaiser extends Raiser
{
	int getValue();
}
