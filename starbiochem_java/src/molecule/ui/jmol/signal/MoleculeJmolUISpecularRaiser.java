package molecule.ui.jmol.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface MoleculeJmolUISpecularRaiser extends Raiser
{
	int getValue();
}
