package molecule.ui.adjust.center.protein.tertiary.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface CenterProteinTertiaryFilterRaiser extends Raiser
{
	int getFilterOptions();
}
