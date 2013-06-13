package molecule.ui.water.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface WaterHBondTranslucencyRaiser extends Raiser
{
	int getValue();
}
