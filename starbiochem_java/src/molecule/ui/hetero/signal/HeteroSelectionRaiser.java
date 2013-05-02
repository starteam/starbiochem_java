package molecule.ui.hetero.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface HeteroSelectionRaiser extends Raiser
{
	String[] getSelection();
}
