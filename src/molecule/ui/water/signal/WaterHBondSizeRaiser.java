package molecule.ui.water.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface WaterHBondSizeRaiser extends Raiser
{
	int getValue();
}
