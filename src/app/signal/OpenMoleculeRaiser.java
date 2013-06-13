package app.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface OpenMoleculeRaiser extends Raiser
{
	molecule.interfaces.Molecule getMolecule();
}
