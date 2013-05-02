package molecule.ui.hetero.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface HeteroBondTranslucencyRaiser extends Raiser
{
	int getValue();
}
