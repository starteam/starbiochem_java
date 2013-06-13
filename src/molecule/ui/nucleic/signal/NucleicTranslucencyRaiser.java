package molecule.ui.nucleic.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface NucleicTranslucencyRaiser extends Raiser
{
	int getValue();
}
