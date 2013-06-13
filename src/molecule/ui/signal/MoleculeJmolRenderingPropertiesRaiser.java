package molecule.ui.signal;

import molecule.interfaces.RenderingInfo;
import star.event.Raiser;

@star.annotations.Raiser
public interface MoleculeJmolRenderingPropertiesRaiser extends Raiser 
{
	RenderingInfo getRenderingInfo();
}
