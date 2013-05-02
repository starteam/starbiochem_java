package molecule.ui.water.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface WaterBondTranslucencyRaiser extends Raiser
{
	int getValue();
}
