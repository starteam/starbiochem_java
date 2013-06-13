package molecule.ui.water.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface WaterFilterRaiser extends Raiser
{
	int getValue();
}
