package molecule.ui.hetero.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface HeteroRenderingModeRaiser extends Raiser
{
	boolean isAutomaticallyRendered();
}
