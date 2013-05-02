package molecule;

public class AdjustSelectionInfo implements molecule.interfaces.AdjustSelectionInfo
{
    private static final long serialVersionUID = 1L;
    transient private String[] selections = null;
    transient private int filterOptions = 0;

	public AdjustSelectionInfo(String[]  selections, int filterOptions)
	{
		this.selections = selections;
		this.filterOptions = filterOptions;
	}
	
	public int getFilterOptions()
	{
		return this.filterOptions;
	}

	public String toString()
	{
		return "\nAdjustSelectionInfo\nselections count: " + (int)((selections != null) ? selections.length : 0)
		+ "\nfilterOptions: " + filterOptions;
	}

	public String[] getSelections()
    {
	    return this.selections;
    }

}
