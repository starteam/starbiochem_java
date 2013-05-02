package molecule.ui.jmol.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface SSBondsEnableRaiser extends Raiser
{
	boolean getBValue();
}
