package molecule.ui.protein.quaternary.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface ProteinQuaternarySSBondSizeRaiser extends Raiser
{
	int getValue();
}
