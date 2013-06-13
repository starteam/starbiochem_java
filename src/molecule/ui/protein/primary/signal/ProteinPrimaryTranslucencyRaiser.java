package molecule.ui.protein.primary.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface ProteinPrimaryTranslucencyRaiser extends Raiser
{
	int getValue();
	boolean isFromProteinPrimaryTranslucencyRaiser();
}
