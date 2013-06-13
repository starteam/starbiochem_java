package molecule.ui.adjust.center.protein.secondary.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface CenterProteinSecondaryFilterRaiser extends Raiser
{
	int getFilterOptions();
}
