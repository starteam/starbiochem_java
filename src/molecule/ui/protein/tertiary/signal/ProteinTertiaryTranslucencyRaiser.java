package molecule.ui.protein.tertiary.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface ProteinTertiaryTranslucencyRaiser extends Raiser
{
	int getValue();
	boolean isFromProteinTertiaryTranslucencyRaiser();
}