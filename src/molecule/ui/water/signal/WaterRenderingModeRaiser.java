package molecule.ui.water.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface WaterRenderingModeRaiser extends Raiser
{
	boolean isAutomaticallyRendered();
}
