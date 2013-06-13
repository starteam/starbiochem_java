package molecule.ui.water.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface WaterSelectionRaiser extends Raiser
{
	String[] getSelection();
}
