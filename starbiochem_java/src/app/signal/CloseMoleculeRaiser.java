package app.signal;

import app.StarBiochemException;
import star.event.Raiser;

@star.annotations.Raiser
public interface CloseMoleculeRaiser extends Raiser
{
	StarBiochemException getStarBiochemException();
}
