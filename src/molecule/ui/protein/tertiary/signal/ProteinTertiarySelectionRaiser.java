package molecule.ui.protein.tertiary.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface ProteinTertiarySelectionRaiser extends Raiser
{
	String[] getSelection();
	int[] getSelectedIndices();
	boolean isFromProteinTertiarySelectionRaiser();
}
