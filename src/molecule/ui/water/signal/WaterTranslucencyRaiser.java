package molecule.ui.water.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface WaterTranslucencyRaiser extends Raiser
{
	int getValue();
}
