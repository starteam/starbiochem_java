package molecule.ui.nucleic.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface NucleicSelectionRaiser extends Raiser
{
	String[] getSelection();
}
