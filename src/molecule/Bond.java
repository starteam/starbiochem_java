package molecule;


public class Bond implements molecule.interfaces.Bond
{
    private static final long serialVersionUID = 1L;

    public Bond(String atom1, String atom2)
	{
    	this.atom1 = atom1;
    	this.atom2 = atom2;
	}
			
	
	transient private String atom1;
	public String getAtom1()
	{
		return atom1;
	}
	
	transient private String atom2;
	public String getAtom2()
	{
		return atom2;
	}

}
