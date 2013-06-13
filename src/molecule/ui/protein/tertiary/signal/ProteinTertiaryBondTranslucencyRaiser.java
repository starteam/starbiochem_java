package molecule.ui.protein.tertiary.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface ProteinTertiaryBondTranslucencyRaiser extends Raiser
{
	int getValue();
	boolean isFromProteinTertiaryBondTranslucencyRaiser();
}
