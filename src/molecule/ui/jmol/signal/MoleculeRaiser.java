package molecule.ui.jmol.signal;

import molecule.interfaces.Molecule;
import star.event.Raiser;

@star.annotations.Raiser
public interface MoleculeRaiser extends Raiser
{
	Molecule getMolecule();
}
