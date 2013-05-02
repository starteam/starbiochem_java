package molecule.ui.nucleic.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface NucleicHBondSizeRaiser extends Raiser
{
	int getValue();
}
