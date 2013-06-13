package molecule;

public class AdditionalStructuresInfo implements molecule.interfaces.AdditionalStructuresInfo
{
    private static final long serialVersionUID = 1L;
    transient private boolean isHydrogens = false;

	public AdditionalStructuresInfo(boolean isHydrogens )
	{
		this.isHydrogens = isHydrogens;
	}
	
	public boolean isHydrogens()
	{
		return this.isHydrogens;
	}

	public String toString()
	{
		return "\nAddionalStructuresInfo\nisHydrogens: " + isHydrogens ;
	}

}
