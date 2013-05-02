package molecule.ui.nucleic.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface NucleicFilterRaiser extends Raiser
{
	int getValue();
}
