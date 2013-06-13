package molecule.ui.jmol.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface MoleculeJmolUIDiffuseRaiser extends Raiser
{
	int getValue();
}
