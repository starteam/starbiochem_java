package molecule.ui.protein.quaternary.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface ProteinQuaternarySelectionRaiser extends Raiser
{
	String[] getSelection();
	String[] getAminoAcids();
}
