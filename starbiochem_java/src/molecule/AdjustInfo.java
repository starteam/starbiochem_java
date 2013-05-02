package molecule;

import java.util.Map;

import molecule.interfaces.AdjustSelectionInfo;

public class AdjustInfo implements molecule.interfaces.AdjustInfo
{
    private static final long serialVersionUID = 1L;
    transient private Map<String, AdjustSelectionInfo>  selectionInfo = null;
    transient private String source = null;
    transient private int radius = 0;

	public AdjustInfo(String source, Map<String, AdjustSelectionInfo>  selectionInfo, int radius)
	{
		this.source = source;
		this.selectionInfo = selectionInfo;
		this.radius = radius;
	}
	
	public String getSource()
	{
		return this.source;
	}

	public Map<String, AdjustSelectionInfo> getSelectionsInfo()
    {
	    return this.selectionInfo;
    }

	public int getRadius()
    {
	    return this.radius;
    }

	public String toString()
	{
		return "\nselectionInfo count: " + (int)((selectionInfo != null) ? selectionInfo.size() : 0)
		+ "\nsource: " + source
		+ "\nradius: " + radius;
	}

}
