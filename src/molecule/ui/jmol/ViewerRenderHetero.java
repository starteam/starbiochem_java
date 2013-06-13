package molecule.ui.jmol;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.PropertyResourceBundle;

import molecule.interfaces.AdjustSelectionInfo;
import molecule.interfaces.RenderingInfo;
import molecule.ui.signal.RenderingInfoRaiser;
import app.StarBiochemException;

public class ViewerRenderHetero 
{
	private static final String EMPTY_STRING = ""; //$NON-NLS-1$

    private static final String LOGICAL_OR = " or "; //$NON-NLS-1$
	
    private static final String SET_VARIABLE_FORMAT = "set_variable_format"; //$NON-NLS-1$
	private static final String SELECTION = "selection"; //$NON-NLS-1$
    private static final String HETERO_TRANSLUCENCY_FORMAT = "hetero_translucency_format"; //$NON-NLS-1$
	private static final String HETERO_BONDTRANSLUCENCY_FORMAT = "hetero_bondtranslucency_format"; //$NON-NLS-1$
	private static final String HETERO_SPACEFILL_FORMAT = "hetero_spacefill_format"; //$NON-NLS-1$
    
	
	private static final String HETERO_HYDROGEN_SELECTION_FORMAT = "hetero_hydrogen_filtered_selection_format"; //$NON-NLS-1$
	private static final String HETERO_SELECTION_FORMAT = "hetero_selection_format"; //$NON-NLS-1$
	private static final String HETERO_BOND_SELECTION_FORMAT = "hetero_bond_selection_format"; //$NON-NLS-1$
	private static final String HETERO_FILTERED_SELECTION_FORMAT = "hetero_filtered_selection_format"; //$NON-NLS-1$
	private static final String HETERO_HBOND_FILTERED_SELECTION_FORMAT = "hetero_hbond_filtered_selection_format"; //$NON-NLS-1$
	
	private static final String HBOND_SIZE_FORMAT = "hbond_size_format"; //$NON-NLS-1$
	private static final String HBOND_TRANSLUCENCY_FORMAT = "hbond_translucency_format"; //$NON-NLS-1$

	private static final String ADJUST_HETERO_FILTERED_SELECTION_FORMAT = "adjust_hetero_filtered_selection_format"; //$NON-NLS-1$
	
	private static final String BOND_MODE_OR = "bond_mode_or"; //$NON-NLS-1$
	private static final String bondFormat = getPrb().getString(BOND_MODE_OR);
	
	transient private static PropertyResourceBundle prb = null;
	private static PropertyResourceBundle getPrb()
	{
		if(null == prb)
		{
			InputStream inputStream = ViewerRenderHetero.class.getResourceAsStream("./JmolScripts/StarBiochem.properties"); //$NON-NLS-1$
			if( inputStream == null )
			{
				inputStream = ViewerRenderHetero.class.getResourceAsStream("/JmolScripts/StarBiochem.properties" );  //$NON-NLS-1$
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
	
	protected static void renderHeteroAtoms(Viewer viewer, RenderingInfo ri) throws StarBiochemException
	{
		if((null != viewer) && (null != ri))
		{
			int filterOptions = ri.getFilterOptions();
			if(0 != (filterOptions & (RenderingInfoRaiser.ALL_HETERO_VISIBLE)))
			{
				String propertiesScript = getHeteroAtomPropertiesScript(ri);
				if((null != propertiesScript) && (!EMPTY_STRING.equals(propertiesScript)))
				{
					
					String filteredSelectionScripts = getHeteroFilteredSelectionScripts(viewer, ri);
					String format = getPrb().getString(HETERO_SELECTION_FORMAT);
					if((null != filteredSelectionScripts) && (!filteredSelectionScripts.isEmpty()))
					{
						String script = String.format(format, filteredSelectionScripts, propertiesScript);
						viewer.script(String.format(bondFormat, script));
					}
				}
			}
		}
	}


	protected static void renderHeteroBonds(Viewer viewer, RenderingInfo ri) throws StarBiochemException
	{
		
		if((null != viewer) && (null != ri))
		{
			int filterOptions = ri.getFilterOptions();
			if(0 != (filterOptions & (RenderingInfoRaiser.ALL_HETERO_VISIBLE)))
			{
				String propertiesScript = getHeteroBondPropertiesScript(ri);
				String format = getPrb().getString(HETERO_BOND_SELECTION_FORMAT);
				if((null != propertiesScript) && (!EMPTY_STRING.equals(propertiesScript)))
				{
					String filteredSelectionScripts = getHeteroFilteredSelectionScripts(viewer, ri);
					if((null != filteredSelectionScripts) && (!filteredSelectionScripts.isEmpty()))
					{
						String script = String.format(format, filteredSelectionScripts, propertiesScript);
						viewer.script(String.format(bondFormat, script));					}
				}
			}
		}
	}
	
	protected static void renderHeteroHBond(Viewer viewer, RenderingInfo ri) throws StarBiochemException
	{
		if((null != viewer) && (null != ri))
		{
			int filterOptions = ri.getFilterOptions();
			if(0 != (filterOptions & (RenderingInfoRaiser.ALL_HETERO_VISIBLE)))
			{
				String propertiesScript = getHeteroHBondPropertiesScript(ri);
				if((null != propertiesScript) && !EMPTY_STRING.equals(propertiesScript))
				{
					ArrayList<String> filteredSelectionScripts = getHeteroHBondFilteredSelectionScripts(ri);
					if((null != filteredSelectionScripts) && (!filteredSelectionScripts.isEmpty()))
					{
						String script = EMPTY_STRING;
						Iterator<String> scriptIterator = filteredSelectionScripts.iterator();
						while(scriptIterator.hasNext())
						{
							script += scriptIterator.next();
							if((null != script) && (0 != script.length()))
							{
								script += propertiesScript;
							}
						}
						viewer.script(String.format(bondFormat, script));
					}
				}
			}
		}
	}
	
	private static String getHeteroAtomPropertiesScript(RenderingInfo ri)
	{
		if((null != ri) && (null != getPrb()))
		{
			String translucencyScript = ((-1 == ri.getTranslucency()) ? EMPTY_STRING : (String.format(getPrb().getString(HETERO_TRANSLUCENCY_FORMAT),Float.toString(ri.getTranslucency()/100.f))));
			String spacefillScript = ((-1 == ri.getSize())  ? EMPTY_STRING : (String.format(getPrb().getString(HETERO_SPACEFILL_FORMAT),Integer.toString(ri.getSize()))));
			
			String propertiesScript = translucencyScript + spacefillScript;
			return propertiesScript;
		}
		return EMPTY_STRING;
	}

	private static String getHeteroBondPropertiesScript(RenderingInfo ri)
	{
		if((null != ri) && (null != getPrb()))
		{
			String bondTranslucency = Float.toString(ri.getBondTranslucency()/100.f);
			String bondTranslucencyScript = ((-1 == ri.getBondTranslucency()) ? EMPTY_STRING : (String.format(getPrb().getString(HETERO_BONDTRANSLUCENCY_FORMAT), bondTranslucency)));
			return bondTranslucencyScript;
		}
		return EMPTY_STRING;
	}

	private static void setHeteroJmolSelectionVar(Viewer viewer, RenderingInfo ri, String filterFormat) throws StarBiochemException
	{
		if((null != getPrb()) && (null != ri) && (null != ri.getSelection() )&& (-1 != ri.getFilterOptions()) && !EMPTY_STRING.equals(filterFormat))
 		{
			String[] selections = ri.getSelection();
			if((-1 != ri.getFilterOptions()))
			{
				String selectionScript = EMPTY_STRING;
				for(int i=0; selections.length != i; i++)
				{
					selectionScript += String.format(filterFormat, selections[i]);
					if(i < (selections.length-1))
					{
						selectionScript += LOGICAL_OR;
					}	
				}
				if(!selectionScript.equals(EMPTY_STRING))
				{
					String format = getPrb().getString(SET_VARIABLE_FORMAT);
					viewer.script(String.format(format, SELECTION, selectionScript));
				}
			}
		}
	}
	
	private static String getHeteroFilteredSelectionScripts(Viewer viewer, RenderingInfo ri) throws StarBiochemException
	{
		if((null != getPrb()) && (null != ri))
		{
			String filteredFormat = getPrb().getString(HETERO_FILTERED_SELECTION_FORMAT);
			if((null != filteredFormat) && !EMPTY_STRING.equals(filteredFormat))
			{
				setHeteroJmolSelectionVar(viewer, ri, filteredFormat);
				String format = getPrb().getString(HETERO_HYDROGEN_SELECTION_FORMAT);
				if((null != format) && !EMPTY_STRING.equals(format))
				{
					return String.format(format, SELECTION);
				}
			}
		}
		return null;
	}
	
	private static String getHeteroHBondPropertiesScript(RenderingInfo ri)
	{
		if((null != ri) && (null != getPrb()))
		{
			String hBondPropertiesScript = getHBondPropertiesScript(ri);
			String translucencyScript = ((-1 == ri.getTranslucency()) ? EMPTY_STRING : (String.format(getPrb().getString(HETERO_TRANSLUCENCY_FORMAT),Float.toString(ri.getTranslucency()/100.f))));
			String bondTranslucency = Float.toString(ri.getBondTranslucency()/100.f);
			String bondTranslucencyScript = ((-1 == ri.getBondTranslucency()) ? EMPTY_STRING : (String.format(getPrb().getString(HETERO_BONDTRANSLUCENCY_FORMAT), bondTranslucency)));
			String spacefillScript = ((-1 == ri.getSize())  ? EMPTY_STRING : (String.format(getPrb().getString(HETERO_SPACEFILL_FORMAT),Integer.toString(ri.getSize()))));
			
			String propertiesScript = translucencyScript + spacefillScript + bondTranslucencyScript + "set bondMode OR; " + hBondPropertiesScript + "set bondMode AND; "; //$NON-NLS-1$ //$NON-NLS-2$
			return propertiesScript;
		}
		return EMPTY_STRING;
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
	
	private static ArrayList<String> getHeteroHBondFilteredSelectionScripts(RenderingInfo ri)
	{
		if((null != getPrb()) && (null != ri) && (null != ri.getSelection()))
		{
			String[] selections = ri.getSelection();
			if(null != selections)
			{
				ArrayList<String> selectionScripts = new ArrayList<String>();
				for(int i=0; selections.length != i; i++)
				{
					String selectionScript = getHeteroHBondFilteredSelectionScript(selections[i], ri.getFilterOptions());
					if((null != selectionScript) && !EMPTY_STRING.equals(selectionScript))
					{
						selectionScripts.add(selectionScript);
					}
				}
				if(!selectionScripts.isEmpty())
				{
					return selectionScripts;
				}
			}
		}
		return null;
	}
	
	private static String getHeteroHBondFilteredSelectionScript(String selection, int filterOptions)
	{
		if((null != getPrb()) && (null != selection) && (-1 != filterOptions))
		{
			String format = getPrb().getString(HETERO_HBOND_FILTERED_SELECTION_FORMAT); 
			if(null != format)
			{
				return String.format(format, selection);
			}
		}
		return null;
	}
	
	protected static String getAdjustHeteroFilteredSelectionScript(AdjustSelectionInfo asi, Viewer viewer) throws StarBiochemException
	{
		if((null != asi) && (null != asi.getSelections()))
		{
			if(null != getPrb())
			{
				String format = prb.getString(ADJUST_HETERO_FILTERED_SELECTION_FORMAT);
				if((null != format) && !EMPTY_STRING.equals(format))
				{
					String adjustHeteroVar = getAdjustHeteroSelectionVar(viewer, asi);
					if((null != adjustHeteroVar) && !EMPTY_STRING.equals(adjustHeteroVar))
					{
						String filteredSelectionScript = String.format(format, adjustHeteroVar);
						return filteredSelectionScript;
					}
				}
			}
		}
		return null;
	}
	
	private static String getAdjustHeteroSelectionVar(Viewer viewer, AdjustSelectionInfo asi) throws StarBiochemException
	{
		if((null != getPrb()) && (null != asi) && (null != asi.getSelections()))
		{
			String[] selections = asi.getSelections();
			String selectionScript = EMPTY_STRING;
			for(int i=0; selections.length != i; i++)
			{
 				if(i < (selections.length-1))
				{
					selectionScript += selections[i] + LOGICAL_OR;
				}
				if(i == (selections.length-1))
				{
					selectionScript +=  selections[i];
				}
			}
			if(!selectionScript.equals(EMPTY_STRING))
			{
				String adjustHeteroVar = "adjustHeteroVar"; //$NON-NLS-1$
				String format = getPrb().getString(SET_VARIABLE_FORMAT);
				viewer.script(String.format(format, adjustHeteroVar, selectionScript));
				return adjustHeteroVar;
			}
		}
		return null;
	}
	
}
