package molecule.interfaces;

import java.io.Serializable;
import java.util.Map;

public interface AdjustInfo extends Serializable
{
	Map<String, AdjustSelectionInfo> getSelectionsInfo();
	int getRadius();
}
