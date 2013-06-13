package molecule.ui.adjust.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface SelectionRaiser extends Raiser
{
	String[] getSelection();
}
