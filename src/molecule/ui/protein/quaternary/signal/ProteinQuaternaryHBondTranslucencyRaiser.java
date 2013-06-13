package molecule.ui.protein.quaternary.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface ProteinQuaternaryHBondTranslucencyRaiser extends Raiser
{
	int getValue();
}
