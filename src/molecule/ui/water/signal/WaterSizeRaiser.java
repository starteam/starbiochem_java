package molecule.ui.water.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface WaterSizeRaiser extends Raiser
{
	int getValue();
}
