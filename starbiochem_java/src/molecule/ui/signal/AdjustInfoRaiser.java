package molecule.ui.signal;

import molecule.interfaces.AdjustInfo;
import star.event.Raiser;

@star.annotations.Raiser
public interface AdjustInfoRaiser extends Raiser
{
	public final static String ADJUST = "ADJUST"; //$NON-NLS-1$

	AdjustInfo getAdjustInfo();
}
