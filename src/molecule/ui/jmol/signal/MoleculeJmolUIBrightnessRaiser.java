package molecule.ui.jmol.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface MoleculeJmolUIBrightnessRaiser extends Raiser
{
	int getValue();
}
