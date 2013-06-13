package molecule.ui.jmol.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface HydrogensEnableRaiser extends Raiser
{
	boolean getBValue();
}
