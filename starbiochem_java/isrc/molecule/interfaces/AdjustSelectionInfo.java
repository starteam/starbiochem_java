package molecule.interfaces;

import java.io.Serializable;

public interface AdjustSelectionInfo extends Serializable
{
	String[] getSelections();
	int getFilterOptions();
}
