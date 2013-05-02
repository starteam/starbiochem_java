package molecule.ui.protein.primary.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface ProteinPrimarySelectionRaiser extends Raiser
{
	String[] getSelection();
	int[] getSelectedIndices();
	boolean isFromProteinPrimarySelectionRaiser();
}
