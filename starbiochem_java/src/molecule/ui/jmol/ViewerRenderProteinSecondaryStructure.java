package molecule.ui.jmol;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.PropertyResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import molecule.interfaces.AdjustSelectionInfo;
import molecule.interfaces.Bond;
import molecule.interfaces.RenderingInfo;
import molecule.ui.signal.RenderingInfoRaiser;
import app.Messages;
import app.StarBiochemException;

public class ViewerRenderProteinSecondaryStructure
{
	private static final String EMPTY_STRING = ""; //$NON-NLS-1$
	private static final String OPEN_SQUARE_BRACKET = "["; //$NON-NLS-1$
	private static final String OPEN_CURLEY_BRACKET = "{"; //$NON-NLS-1$
    private static final String CLOSE_CURLEY_BRACKET = "}"; //$NON-NLS-1$
    private static final String LOGICAL_OR = " or "; //$NON-NLS-1$
    
    private static final String SELECTOR_HELIX = "Molecule.2"; //$NON-NLS-1$
    private static final String SELECTOR_SHEET = "Molecule.3"; //$NON-NLS-1$
    private static final String SELECTOR_COIL = "Molecule.1"; //$NON-NLS-1$
    private static final String SELECTOR_TURN = "TURN"; //$NON-NLS-1$
    private static final String CARTOONS = "cartoons"; //$NON-NLS-1$

	private static final String BOND_MODE_AND = "bond_mode_and"; //$NON-NLS-1$

	private static final String HBOND_SIZE_FORMAT = "hbond_size_format"; //$NON-NLS-1$
	private static final String HBOND_TRANSLUCENCY_FORMAT = "hbond_translucency_format"; //$NON-NLS-1$
    
    private static final String PROTEIN_SECONDARY_SPACEFILL_FORMAT = "protein_secondary_spacefill_format"; //$NON-NLS-1$
	private static final String PROTEIN_SECONDARY_FILTERED_SELECTION_FORMAT = "protein_secondary_filtered_selection_format"; //$NON-NLS-1$
	private static final String PROTEIN_SECONDARY_FILTERED_HBOND_SELECTION_FORMAT = "protein_secondary_filtered_hbond_selection_format"; //$NON-NLS-1$

	private static final String ADJUST_PROTEIN_SECONDARY_FILTERED_SELECTION_FORMAT = "adjust_protein_secondary_filtered_selection_format"; //$NON-NLS-1$

	transient private static PropertyResourceBundle prb = null;

	private static String getTranslated(String key)
	{
		return Messages.getString(key).toUpperCase();
	}
	private static PropertyResourceBundle getPrb()
	{
		if(null == prb)
		{
			InputStream inputStream = ViewerRenderProteinSecondaryStructure.class.getResourceAsStream("./JmolScripts/StarBiochem.properties"); //$NON-NLS-1$
			if( inputStream == null )
			{
				inputStream = ViewerRenderProteinSecondaryStructure.class.getResourceAsStream("/JmolScripts/StarBiochem.properties" );  //$NON-NLS-1$
			}
			if(null != inputStream)
			{
				try
		        {
		            prb = new PropertyResourceBundle(inputStream);
		        }
		        catch (IOException e)
		        {
		            e.printStackTrace();
		        }
			}
		}
		return prb;
	}
	
	protected static void renderProteinSecondaryStructure(Viewer viewer, RenderingInfo ri) throws StarBiochemException
	{
		if((null != viewer) && (null != ri))
		{
			int filterOptions = ri.getFilterOptions();
			if(0 != (filterOptions & (RenderingInfoRaiser.ALPHAHELIX_VISIBLE | RenderingInfoRaiser.BETASHEET_VISIBLE | RenderingInfoRaiser.COIL_VISIBLE)))
			{
				String propertiesScript = getSecondaryPropertiesScript(ri);
				if((null != propertiesScript) && !EMPTY_STRING.equals(propertiesScript))
				{
					ArrayList<String> filteredSelectionScripts = getProteinSecondaryFilteredSelectionScripts(ri);
					if((null != filteredSelectionScripts) && !filteredSelectionScripts.isEmpty())
					{
						Iterator<String> scriptIterator = filteredSelectionScripts.iterator();
						if(null != scriptIterator)
						{
							while(scriptIterator.hasNext())
							{
								String script = scriptIterator.next();
								if((null != script) && (0 != script.length()))
								{
									script += propertiesScript;
									viewer.script(script + "subset;"); //$NON-NLS-1$
								}
							}
						}
					}
				}
			}
		}
	}
	
	protected static void renderProteinSecondaryHBondStructure(Viewer viewer, RenderingInfo ri) throws StarBiochemException
	{		
		if((null != viewer) && (null != ri))
		{								
			int filterOptions = ri.getFilterOptions();
			if(0 != (filterOptions & (RenderingInfoRaiser.ALPHAHELIX_VISIBLE | RenderingInfoRaiser.BETASHEET_VISIBLE | RenderingInfoRaiser.COIL_VISIBLE)))
			{
				String format = getPrb().getString(BOND_MODE_AND);
				String filteredSelectionScripts = getProteinSecondaryFilteredHBondSelectionScripts(ri);
				if((null != filteredSelectionScripts) && !filteredSelectionScripts.isEmpty())
				{
					viewer.script(String.format(format, filteredSelectionScripts));
				}
			}
		}
	}
	
	private static String getSecondaryPropertiesScript(RenderingInfo ri)
	{
		if((null != ri) && (null != getPrb()))
		{
			String spacefillScript = ((-1 == ri.getSize())  ? EMPTY_STRING : (String.format(getPrb().getString(PROTEIN_SECONDARY_SPACEFILL_FORMAT),Integer.toString(ri.getSize()))));
			
			String propertiesScript = spacefillScript;

			return propertiesScript;
		}
		return EMPTY_STRING;
	}
	
	private static ArrayList<String> getProteinSecondaryFilteredSelectionScripts(RenderingInfo ri)
	{
		ArrayList<String> scripts = new ArrayList<String>();
		if((null != getPrb()) && (null != ri) && (-1 != ri.getSize()) && (null != ri.getSource()))
		{
			float size = (float)ri.getSize()/50.f;
			int filterOptions = ri.getFilterOptions();
			if(-1 != filterOptions)
			{
				String formatID = null;
				if(RenderingInfoRaiser.PROTEIN_SECONDARY_STRUCTURE.equals( ri.getSource() ))
				{
					formatID = PROTEIN_SECONDARY_FILTERED_SELECTION_FORMAT;
				}
				if((null != formatID) && !EMPTY_STRING.equals(formatID))
				{
					String format = getPrb().getString(formatID); 
					if((null != format) && !EMPTY_STRING.equals(format))
					{
						if(0 != (filterOptions & RenderingInfoRaiser.ALPHAHELIX_VISIBLE))
						{
							String selector = createSecondaryFilteredSelector(ri, getTranslated(SELECTOR_HELIX));
							if((null != selector) && !EMPTY_STRING.equals(selector))
							{
								scripts.add(String.format(format, selector, "HELIX", CARTOONS, size));
							}
						}
						if(0 != (filterOptions & RenderingInfoRaiser.BETASHEET_VISIBLE))
						{
							String selector = createSecondaryFilteredSelector(ri, getTranslated(SELECTOR_SHEET));
							if((null != selector) && !EMPTY_STRING.equals(selector))
							{
								scripts.add(String.format(format, selector, "SHEET", CARTOONS, size));
							}
						}
						if(0 != (filterOptions & RenderingInfoRaiser.COIL_VISIBLE))
						{
							String selector = createSecondaryFilteredSelector(ri, getTranslated(SELECTOR_COIL));
							if((null != selector) && !EMPTY_STRING.equals(selector))
							{
								float coilSize = (float)(size/4.5);
								scripts.add(String.format(format, selector, SELECTOR_TURN, CARTOONS, coilSize));
							}
						}
					}
				}
			}
		}		
		return scripts;
	}
	
	private static String getProteinSecondaryFilteredHBondSelection(RenderingInfo ri, String[] selector, String formatID)
	{
		String selection = null;

		if((null != ri) && (null != selector) && (0 != selector.length) && (null != formatID))
		{
			String propertiesScript = getHBondPropertiesScript(ri);
			String format = getPrb().getString(formatID); 
			if((null != format) && !EMPTY_STRING.equals(format))
			{
				for(int i = 0; selector.length != i; i++)
				{
					if(null != selection)
					{
						selection += String.format(format, selector[i]) + propertiesScript;
					}
					if(null == selection)
					{
						selection = String.format(format, selector[i]) + propertiesScript;
					}
				}
			}
		}
		return selection;
	}
	private static String getProteinSecondaryFilteredHBondSelectionScripts(RenderingInfo ri)
	{
		String hbondScript = EMPTY_STRING;
		if((null != getPrb()) && (null != ri) && (null != ri.getSource()))
		{
			int filterOptions = ri.getFilterOptions();
			if(-1 != filterOptions)
			{
				String formatID = null;
				if(RenderingInfoRaiser.PROTEIN_SECONDARY_HBOND_STRUCTURE.equals( ri.getSource() ))
				{
					formatID = PROTEIN_SECONDARY_FILTERED_HBOND_SELECTION_FORMAT;
				}
				if((null != formatID) && !EMPTY_STRING.equals(formatID))
				{
					String format = getPrb().getString(formatID); 
					if((null != format) && !EMPTY_STRING.equals(format))
					{
						if(0 != (filterOptions & RenderingInfoRaiser.ALPHAHELIX_VISIBLE))
						{
							String filter[] = filterSelectedHbonds(ri, getTranslated(SELECTOR_HELIX));
							if((null != filter) && (filter.length != 0))
							{
								hbondScript += (getProteinSecondaryFilteredHBondSelection(ri, filter, formatID));
							}
						}
						if(0 != (filterOptions & RenderingInfoRaiser.BETASHEET_VISIBLE))
						{
							String[] filter = filterSelectedHbonds(ri, getTranslated(SELECTOR_SHEET));
							if((null != filter) && !EMPTY_STRING.equals(filter))
							{
								hbondScript += (getProteinSecondaryFilteredHBondSelection(ri, filter, formatID));
							}
						}
						if(0 != (filterOptions & RenderingInfoRaiser.COIL_VISIBLE))
						{
							String[] filter = filterSelectedHbonds(ri, getTranslated(SELECTOR_COIL));
							if((null != filter) && !EMPTY_STRING.equals(filter))
							{
								hbondScript += (getProteinSecondaryFilteredHBondSelection(ri, filter, formatID));
							}
						}
					}
				}
				
			}
		}
		if(!hbondScript.equals(EMPTY_STRING))
		{
			return hbondScript;
		}
		return null;
	}

	private static	String createSecondaryFilteredSelector(RenderingInfo ri, String structure)
	{
		if((null != getPrb()) && (null != ri) && (null != ri.getSelection()))
		{
			String[] selections = ri.getSelection();
			if(null != selections)
			{
				String selector = EMPTY_STRING;
				boolean start = true;
				for(int i=0; selections.length != i; i++)
				{
					String selection = selections[i].trim();
					if(null != selection)
					{
						int index = selection.indexOf(OPEN_SQUARE_BRACKET);
						if(-1 != index)
						{
							String struct = selection.substring(0, index).toUpperCase();
							selection = selection.substring(index);
							if(start)
							{
								if(struct.startsWith(structure))
								{
									selector = selection;
									start = false;
								}
							}
							else
							{
								if(struct.startsWith(structure))
								{
									selector += LOGICAL_OR + selection;
								}
							}
						}
					}
				}
				if(!EMPTY_STRING.equals( selector ))
				{
					selector = OPEN_CURLEY_BRACKET + selector + CLOSE_CURLEY_BRACKET;
					return selector;
				}
			}
		}
		return null;
	}
	
	private static String[] filterSelectedHbonds(RenderingInfo ri, String structure)
	{
		if((null != getPrb()) && (null != ri) && (null != ri.getSelection()))
		{
			ArrayList<String> arrayList = new ArrayList<String>();
			String[] selections = ri.getSelection();
			if((null != ri.getStructureHbonds()) && (0 != ri.getStructureHbonds().length))
			{
				Bond[] hbonds = ri.getStructureHbonds();
				for(int i = 0; hbonds.length != i; i++)
				{
					String atom1 = hbonds[i].getAtom1();
					String atom2 = hbonds[i].getAtom2();
					int count = 0;
					String mySelection = null;
					for(int j=0; selections.length != j; j++)
					{
						if(count < 2)
						{
							mySelection= selections[j];
							String selection = getAminoAcid(mySelection);
							if((null != selection))
							{
								if(atom1.startsWith(selection))
								{
									count++;
								}
								if(atom2.startsWith(selection))
								{
									count++;								
								}
							}
						}	
					}
					if(count == 2)
					{
						if(mySelection != null)
						{
							boolean myStruct = isSecondaryHbondFilter(structure, mySelection);
							
							if(myStruct == true)
							{
								if((atom1 != null) && (atom2 != null))
								{
									arrayList.add(atom1 + " or " + atom2); //$NON-NLS-1$
								}
							}
						}
					}
				}
			}
			if(!arrayList.isEmpty())
			{

				return arrayList.toArray(new String[0]);
			}
		}
		return null;
	}
	
	private static boolean isSecondaryHbondFilter(String structure, String mySelection)
	{
		boolean myStruct = false;

		if((null != structure) && (null != mySelection))
		{
			String struct = structure.toLowerCase();
		
			if(mySelection.startsWith(struct))
			{
				myStruct = true;
			}
		}
		return myStruct;
	}
	
	private static String getAminoAcid(String selection)
	{
		String amino = null;
		if(null != selection)
		{
			Pattern p = Pattern.compile("(\\[[^ ]+)");  //$NON-NLS-1$
			Matcher m = p.matcher(selection);
			boolean match = m.find();
			if(match)
			{
				amino = m.group(1).trim();
			}
		}
		
		return amino;
	}
	private static String getHBondPropertiesScript(RenderingInfo ri)
	{
		int hBondSize = ri.getHBondSize();
		double myHBondSize = (hBondSize * 0.003);
		String hBondSizeScript = ((-1 == ri.getHBondSize())  ? EMPTY_STRING : (String.format(getPrb().getString(HBOND_SIZE_FORMAT),Double.toString(myHBondSize))));

		String hBondTranslucency = Float.toString(ri.getHBondTranslucency()/100.f);
		String hBondTranslucencyScript = ((-1 == ri.getHBondTranslucency())  ? EMPTY_STRING : (String.format(getPrb().getString(HBOND_TRANSLUCENCY_FORMAT),hBondTranslucency)));
		String hBondScript = hBondSizeScript + hBondTranslucencyScript;

		return hBondScript;
	}
	
	protected static String getAdjustProteinSecondaryFilteredSelectionScript(AdjustSelectionInfo asi)
	{
		if(null != asi)
		{
			if(-1 != asi.getFilterOptions())
			{
				int filterOptions = asi.getFilterOptions();
				if(-1 != filterOptions)
				{
					String filteredSelectionScript = null;
					boolean hasHelix = (0 != (filterOptions & RenderingInfoRaiser.ALPHAHELIX_VISIBLE));
					boolean hasSheet = (0 != (filterOptions & RenderingInfoRaiser.BETASHEET_VISIBLE));
					boolean hasCoil = (0 != (filterOptions & RenderingInfoRaiser.COIL_VISIBLE));
					if(hasHelix && hasSheet && hasCoil)
					{
						filteredSelectionScript = createAdjustSecondarySelectionScript(asi);
					}
					else if(hasHelix && hasSheet)
					{
						filteredSelectionScript = createAdjustSecondarySelectionScript(asi, getTranslated(SELECTOR_HELIX), getTranslated(SELECTOR_SHEET));
					}
					else if(hasHelix && hasCoil)
					{
						filteredSelectionScript = createAdjustSecondarySelectionScript(asi, getTranslated(SELECTOR_HELIX), getTranslated(SELECTOR_COIL));
					}
					else if(hasSheet && hasCoil)
					{
						filteredSelectionScript = createAdjustSecondarySelectionScript(asi, getTranslated(SELECTOR_SHEET), getTranslated(SELECTOR_COIL));
					}
					else if(hasHelix)
					{
						filteredSelectionScript = createAdjustSecondarySelectionScript(asi, getTranslated(SELECTOR_HELIX));
					}
					else if(hasCoil)
					{
						filteredSelectionScript = createAdjustSecondarySelectionScript(asi, getTranslated(SELECTOR_COIL));
					}
					else if(hasSheet)
					{
						filteredSelectionScript = createAdjustSecondarySelectionScript(asi, getTranslated(SELECTOR_SHEET));
					}
					return filteredSelectionScript;
				}
			}
		}
		return null;
	}
	
	private static String createAdjustSecondarySelectionScript(AdjustSelectionInfo asi, String structure)
	{
		if((null != asi) && (null != structure))
		{
			if(null != getPrb())
			{
				String format = getPrb().getString(ADJUST_PROTEIN_SECONDARY_FILTERED_SELECTION_FORMAT); 
				if((null != format) && !EMPTY_STRING.equals(format))
				{
					String selector = null;
					String selector1 = createAdjustSecondarySelector(asi, structure);
					if((null != selector1) && !EMPTY_STRING.equals(selector1))
					{
						selector =  "{" + selector1 + "}"; //$NON-NLS-1$ //$NON-NLS-2$
					}
					if(null != selector)
					{
						return String.format(format, selector);
					}
				}
			}
		}
		return null;
	}

	private static String createAdjustSecondarySelectionScript(AdjustSelectionInfo asi, String structure1, String structure2)
	{
		if((null != asi) && (null != structure1) && (null != structure2))
		{
			if(null != getPrb())
			{
				String format = getPrb().getString(ADJUST_PROTEIN_SECONDARY_FILTERED_SELECTION_FORMAT); 
				if((null != format) && !EMPTY_STRING.equals(format))
				{
					String selector = null;
					String selector1 = createAdjustSecondarySelector(asi, structure1);
					if((null != selector1) && !EMPTY_STRING.equals(selector1))
					{
						selector = selector1;
					}
					String selector2 = createAdjustSecondarySelector(asi, structure2);
					if((null != selector2) && !EMPTY_STRING.equals(selector2))
					{
						if(null != selector)
						{
							selector = "{" + selector + " or " + selector2 + "}"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						}
						else
						{
							selector = selector2;
						}
					}
					if(null != selector)
					{
						return String.format(format, selector);
					}
				}
			}
		}
		return null;
	}

	private static String createAdjustSecondarySelectionScript(AdjustSelectionInfo asi)
	{
		if((null != asi) && (null != asi.getSelections()))
		{
			if(null != getPrb())
			{
				String format = getPrb().getString(ADJUST_PROTEIN_SECONDARY_FILTERED_SELECTION_FORMAT); 
				if((null != format) && !EMPTY_STRING.equals(format))
				{
					String[] values = asi.getSelections();
					if((null != values) && (0 != values.length))
					{
						String[] selection = new String[values.length];
						for (int i = 0; values.length != i; i++)
						{
							String temp = (String) values[i];
							int index = temp.indexOf("["); //$NON-NLS-1$
							selection[i] = (-1 != index) ? temp.substring(index) : ""; //$NON-NLS-1$
						}
						String selector = createSelector(selection, EMPTY_STRING);
						if(null != selector)
						{
							return String.format(format, selector);
						}
					}
				}
			}
		}
		return null;
	}

	private static String createAdjustSecondarySelector(AdjustSelectionInfo asi, String structure)
	{
		if((null != asi) && (null != asi.getSelections()) && (null != structure))
		{
			String[] selections = asi.getSelections();
			if((null != selections) && (0 != selections.length))
			{
				String selector = EMPTY_STRING;
				boolean start = true;
				for(int i=0; selections.length != i; i++)
				{
					String selection = selections[i].trim();
					int index = selection.indexOf(OPEN_SQUARE_BRACKET);
					if(-1 != index)
					{
						String struct = selection.substring(0, index).toUpperCase();
						selection = selection.substring(index);
						if(start)
						{
							if(struct.startsWith(structure))
							{
								selector = selection;
								start = false;
							}
						}
						else
						{
							if(struct.startsWith(structure))
							{
								selector += LOGICAL_OR + selection;
							}
						}
					}
				}
				if(!EMPTY_STRING.equals( selector ))
				{
					selector = OPEN_CURLEY_BRACKET + selector + CLOSE_CURLEY_BRACKET;
					return selector;
				}
			}
		}
		return null;
	}
	
	private static String createSelector(String[] selections, String suffix)
	{
		if((null != selections) && (null != suffix))
		{
			String selector = EMPTY_STRING;
			for(int i=0; selections.length != i; i++)
			{
				if(0 == i)
				{
					selector = OPEN_CURLEY_BRACKET + selections[0] + suffix;
				}
				else
				{
					selector += LOGICAL_OR + selections[i] + suffix;
				}
			}
			if(!EMPTY_STRING.equals( selector ))
			{
				selector += CLOSE_CURLEY_BRACKET;
				return selector;
			}
		}
		return null;
	}
	
}
