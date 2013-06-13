package molecule.ui.jmol.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface MoleculeJmolUISegmentsRaiser extends Raiser
{
	int getValue();
}
