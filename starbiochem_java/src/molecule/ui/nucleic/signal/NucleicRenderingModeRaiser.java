package molecule.ui.nucleic.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface NucleicRenderingModeRaiser extends Raiser
{
	boolean isAutomaticallyRendered();
}
