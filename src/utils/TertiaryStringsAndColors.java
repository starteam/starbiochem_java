package utils;


public class TertiaryStringsAndColors implements molecule.interfaces.TertiaryStringsAndColors{
	private static final long serialVersionUID = 1L;

	/**
	 * Tertiary COLOR TABLE 
	 */
	private String[] myTertiaryColorTable =
	{
 		"HYDROPHOBIC",					"[255,209,35]", //$NON-NLS-1$ //$NON-NLS-2$
		"NOT HYDROPHOBIC",				"[92,184,209]", //$NON-NLS-1$ //$NON-NLS-2$
		
		"BURIED",						"[161,54,212]",	   //$NON-NLS-1$ //$NON-NLS-2$
		"SURFACE",						"[0,201,0]", //$NON-NLS-1$ //$NON-NLS-2$
		
		"BASIC",						"[180,180,255]", //$NON-NLS-1$ //$NON-NLS-2$
		"ACIDIC",						"[255,128,128]", //$NON-NLS-1$ //$NON-NLS-2$
		"NEUTRAL",						"[143,255,199]" //$NON-NLS-1$ //$NON-NLS-2$
	};

	private String[] acidic = { "ASP", "GLU" }; //$NON-NLS-1$ //$NON-NLS-2$
	private String[] basic = { "ARG", "HIS", "LYS" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	private String[] buried = { "ALA", "CYS", "ILE", "LEU", "MET", "PHE", "TRP", "VAL" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
	private String[] hydrophobic = { "ALA", "GLY", "ILE", "LEU", "MET", "PHE", "PRO", "TRP", "TYR", "VAL" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$
	
	
	
	private TertiaryStringsAndColors()
	{
	}
	
	public boolean isAcidic(String residue)
	{
		for(int i=0; acidic.length != i; i++)
		{
			if(acidic[i].equals(residue.toUpperCase()))
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean isBasic(String residue)
	{
		for(int i=0; basic.length != i; i++)
		{
			if(basic[i].equals(residue.toUpperCase()))
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean isNeutral(String residue)
	{
		return !isAcidic(residue) && !isBasic(residue);
	}
	
	public boolean isBuried(String residue)
	{
		for(int i=0; buried.length != i; i++)
		{
			if(buried[i].equals(residue.toUpperCase()))
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean isSurface(String residue)
	{
		return !isBuried(residue);
	}
	
	public boolean isHydrophobic(String residue)
	{
		for(int i=0; hydrophobic.length != i; i++)
		{
			if(hydrophobic[i].equals(residue.toUpperCase()))
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean isHydrophilic(String residue)
	{
		return !isHydrophobic(residue);
	}
	
	public String getBuriedColor()
	{
		return myTertiaryColorTable[2*2 + 1];
    }

	public String getBuriedString()
	{
		return myTertiaryColorTable[2*2];
    }

	public String getNegativelyChargedColor()
	{
		return myTertiaryColorTable[5*2 + 1];
	}

	public String getNegativelyChargedString()
	{
		return myTertiaryColorTable[5*2];
    }

	public String getNeutralColor()
	{
		return myTertiaryColorTable[6*2 + 1];
	}

	public String getNeutralString()
	{
		return myTertiaryColorTable[6*2];
	}

	public String getNonpolarHydrophobicColor()
	{
		return myTertiaryColorTable[0*2 + 1];
	}

	public String getNonpolarHydrophobicString()
	{
		return myTertiaryColorTable[0*2];
	}

	public String getPolarHydrophilicColor()
	{
		return myTertiaryColorTable[1*2 + 1];
	}

	public String getPolarHydrophilicString()
	{
		return myTertiaryColorTable[1*2];
	}

	public String getPositivelyChargedColor()
	{
		return myTertiaryColorTable[4*2 + 1];
	}

	public String getPositivelyChargedString()
	{
		return myTertiaryColorTable[4*2];
	}

	public String getSurfaceColor()
	{
		return myTertiaryColorTable[3*2 + 1];
	}

	public String getSurfaceString()
	{
		return myTertiaryColorTable[3*2];
	}

	private static TertiaryStringsAndColors tertiaryStringsAndColors = null;
	public static TertiaryStringsAndColors getDefaultTertiaryStringsAndColors()
	{
		if(null == tertiaryStringsAndColors)
		{
			tertiaryStringsAndColors = new TertiaryStringsAndColors();
		}
		return tertiaryStringsAndColors;
	}
}
