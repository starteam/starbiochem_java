package molecule;

import molecule.interfaces.Bond;

public class RenderingInfo implements molecule.interfaces.RenderingInfo
{
    private static final long serialVersionUID = 1L;
    transient private String url = null;
    transient private String source = null;
    transient private String[] selection = null;
    transient private Bond[] hbondSelection = null;
    transient private int bondTranslucency = 0;
    transient private int brightness = 0;
    transient private int diffuse = 0;
    transient private int filterOptions = 0;
    transient private int translucency = 0;
    transient private int segments = 0;
    transient private int size = 0;
    transient private int specular = 0;
    transient private int ssBondSize = 0;
    transient private int ssBondTranslucency = 0;
    transient private int hBondSize = 0;
    transient private int hBondTranslucency = 0;

    
	public RenderingInfo(String url, String source, String[] selection, Bond[] hbondSelection, int bondTranslucency, int brightness, int diffuse, int filterOptions, int translucency, int segments, int size, int specular, int rotationCenterX, int rotationCenterY, int rotationCenterZ, int ssBondSize, int ssBondTranslucency, int hBondSize, int hBondTranslucency)
	{
		this.url = url;
		this.source = source;
		this.selection = selection;
		this.hbondSelection = hbondSelection;
		this.bondTranslucency = bondTranslucency;
		this.brightness = brightness;
		this.diffuse = diffuse;
		this.filterOptions = filterOptions;
		this.translucency = translucency;
		this.segments = segments;
		this.size = size;
		this.ssBondSize = ssBondSize;
		this.ssBondTranslucency = ssBondTranslucency;
		this.hBondSize = hBondSize;
		this.hBondTranslucency = hBondTranslucency;
		this.specular = specular;
	}
	
	public int getBondTranslucency()
	{
		return this.bondTranslucency;
	}

	public int getBrightness()
	{
		return this.brightness;
	}

	public int getDiffuse()
	{
		return this.diffuse;
	}

	public int getFilterOptions()
	{
		return this.filterOptions;
	}

	public int getTranslucency()
	{
		return this.translucency;
	}

	public int getSegments()
	{
		return this.segments;
	}

	public String[] getSelection()
	{
		return this.selection;
	}
	
	public Bond[] getStructureHbonds()
	{
		return this.hbondSelection;
	}

	public int getSize()
	{
		return this.size;
	}
	
	public String getSource()
	{
		return this.source;
	}

	public int getSpecular()
	{
		return this.specular;
	}

	public String getUrl()
	{
		return this.url;
	}
	
	public int getSSbondSize()
	{
		return this.ssBondSize;
	}
	
	public int getSSbondTranslucency()
	{
		return this.ssBondTranslucency;
	}
	
	public int getHBondSize() 
	{
		return this.hBondSize;
	}
	
	public int getHBondTranslucency()
	{
		return this.hBondTranslucency;
	}
	
	public String toString()
	{
		return "\nurl:" + url
		+ "\nsource: " + source
		+ "\nselection count: " + (int)((selection != null) ? selection.length : 0)
		+ "\nbondTranslucency: " + bondTranslucency
		+ "\nbrightness: " + brightness
		+ "\ndiffuse: " + diffuse 
		+ "\nfilterOptions: " + filterOptions 
		+ "\ntranslucency: " + translucency
		+ "\nsegments: "+ segments
		+ "\nsize: " + size
		+ "\nssBondSize" + ssBondSize
		+ "\nssBondTranslucency" + ssBondTranslucency
		+ "\nspecular: " + specular
		+ "\nhBondSize" + hBondSize
		+ "\nhBondTranslucency" + hBondTranslucency;
	}
}
