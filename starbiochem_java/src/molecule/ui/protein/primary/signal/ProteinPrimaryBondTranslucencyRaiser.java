package molecule.ui.protein.primary.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface ProteinPrimaryBondTranslucencyRaiser extends Raiser
{
	int getValue();
	boolean isFromProteinPrimaryBondTranslucencyRaiser();
}
