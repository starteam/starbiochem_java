package molecule.ui.protein.primary.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface ProteinPrimaryRenderingModeRaiser extends Raiser
{
	boolean isAutomaticallyRendered();
}
