package molecule.ui.protein.tertiary.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface ProteinTertiaryRenderingModeRaiser extends Raiser
{
	boolean isAutomaticallyRendered();
}