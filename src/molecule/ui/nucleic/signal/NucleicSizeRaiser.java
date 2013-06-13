package molecule.ui.nucleic.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface NucleicSizeRaiser extends Raiser
{
	int getValue();
}
