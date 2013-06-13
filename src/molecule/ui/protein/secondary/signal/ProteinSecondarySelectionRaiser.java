package molecule.ui.protein.secondary.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface ProteinSecondarySelectionRaiser extends Raiser
{
	String[] getSelection();
}
