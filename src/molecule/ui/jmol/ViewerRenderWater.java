package molecule.ui.jmol;

import java.io.IOException;
import java.io.InputStream;
import java.util.PropertyResourceBundle;

import molecule.interfaces.AdjustSelectionInfo;
import molecule.interfaces.RenderingInfo;
import molecule.ui.signal.RenderingInfoRaiser;
import app.StarBiochemException;

public class ViewerRenderWater
{
	private static final String EMPTY_STRING = ""; //$NON-NLS-1$

    private static final String OPEN_CURLEY_BRACKET = "{"; //$NON-NLS-1$
    private static final String CLOSE_CURLEY_BRACKET = "}"; //$NON-NLS-1$
    
    private static final String LOGICAL_OR = " or "; //$NON-NLS-1$
	
    private static final String SET_VARIABLE_FORMAT = "set_variable_format"; //$NON-NLS-1$

	private static final String WATER_TRANSLUCENCY_FORMAT = "water_translucency_format"; //$NON-NLS-1$
	private static final String WATER_BONDTRANSLUCENCY_FORMAT = "water_bondtranslucency_format"; //$NON-NLS-1$
	private static final String WATER_SPACEFILL_FORMAT = "water_spacefill_format"; //$NON-NLS-1$
	
	private static final String HBOND_SIZE_FORMAT = "hbond_size_format"; //$NON-NLS-1$
	private static final String HBOND_TRANSLUCENCY_FORMAT = "hbond_translucency_format"; //$NON-NLS-1$
	
	private static final String WATER_FILTERED_SELECTION_FORMAT = "water_filtered_selection_format"; //$NON-NLS-1$
	private static final String WATER_HYDROGEN_SELECTION_FORMAT = "water_hydrogen_selection_format"; //$NON-NLS-1$
	private static final String WATER_SELECTION_FORMAT = "water_selection_format"; //$NON-NLS-1$
	private static final String WATER_BOND_SELECTION_FORMAT = "water_bond_selection_format"; //$NON-NLS-1$
	private static final String WATER_HBOND_SELECTION_FORMAT = "water_hbond_selection_format"; //$NON-NLS-1$
	private static final String ADJUST_WATER_FILTERED_SELECTION_FORMAT = "adjust_water_filtered_selection_format"; //$NON-NLS-1$
	
	private static final String BOND_MODE_OR = "bond_mode_or"; //$NON-NLS-1$
	private static final String bondFormat = getPrb().getString(BOND_MODE_OR);
	
	transient private static PropertyResourceBundle prb = null;
	private static PropertyResourceBundle getPrb()
	{
		if(null == prb)
		{
			InputStream inputStream = ViewerRenderWater.class.getResourceAsStream("./JmolScripts/StarBiochem.properties"); //$NON-NLS-1$
			if( inputStream == null )
			{
				inputStream = ViewerRenderWater.class.getResourceAsStream("/JmolScripts/StarBiochem.properties" );  //$NON-NLS-1$
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
	
	protected static void renderWater(Viewer viewer, RenderingInfo ri) throws StarBiochemException
	{
		if((null != viewer) && (null != ri))
		{
			int filterOptions = ri.getFilterOptions();
			if(0 != (filterOptions & (RenderingInfoRaiser.ALL_WATER_VISIBLE)))
			{
				String propertiesScript = getWaterPropertiesScript(ri);
				if((null != propertiesScript) && !EMPTY_STRING.equals(propertiesScript))
				{
					String filteredSelectionScript = getWaterFilteredSelectionScript(viewer, ri);
					if((null != filteredSelectionScript) && !EMPTY_STRING.equals(filteredSelectionScript))
					{
						String format = getPrb().getString(WATER_SELECTION_FORMAT);
						if((null != format) && !EMPTY_STRING.equals(format))
						{
							String script = String.format(format, filteredSelectionScript, propertiesScript);
							viewer.script(String.format(bondFormat, script));
						}
					}
				}
			}
		}
	}
	
	protected static void renderWaterBonds(Viewer viewer, RenderingInfo ri) throws StarBiochemException
	{
		if((null != viewer) && (null != ri))
		{
			int filterOptions = ri.getFilterOptions();
			if(0 != (filterOptions & (RenderingInfoRaiser.ALL_WATER_VISIBLE)))
			{
				String propertiesScript = getWaterBondPropertiesScript(ri);
				if((null != propertiesScript) && !EMPTY_STRING.equals(propertiesScript))
				{
					String filteredSelectionScript = getWaterFilteredSelectionScript(viewer, ri);
					String format = getPrb().getString(WATER_BOND_SELECTION_FORMAT);
					if((null != filteredSelectionScript) && !filteredSelectionScript.isEmpty())
					{
						String script = String.format(format, filteredSelectionScript, propertiesScript);
						viewer.script(String.format(bondFormat, script));
					}
				}
			}
		}
	}
	
	protected static void renderWaterHBonds(Viewer viewer, RenderingInfo ri) throws StarBiochemException
	{
		if((null != viewer) && (null != ri))
		{
			int filterOptions = ri.getFilterOptions();
			if(0 != (filterOptions & (RenderingInfoRaiser.ALL_WATER_VISIBLE)))
			{
				String propertiesScript = getHBondPropertiesScript(ri);
				if((null != propertiesScript) && !EMPTY_STRING.equals(propertiesScript))
				{
					String filteredSelectionScript = getWaterFilteredSelectionScript(viewer, ri);
					String format = getPrb().getString(WATER_HBOND_SELECTION_FORMAT);
					if((null != filteredSelectionScript) && !filteredSelectionScript.isEmpty())
					{
						String script = String.format(format, filteredSelectionScript, propertiesScript);
						viewer.script(String.format(bondFormat, script));
					}
				}
			}
		}
	}
	
	private static String getWaterPropertiesScript(RenderingInfo ri)
	{
		if((null != ri) && (null != getPrb()))
		{
			String translucencyScript = ((-1 == ri.getTranslucency()) ? EMPTY_STRING : (String.format(prb.getString(WATER_TRANSLUCENCY_FORMAT),Float.toString(ri.getTranslucency()/100.f))));
			String spacefillScript = ((-1 == ri.getSize())  ? EMPTY_STRING : (String.format(prb.getString(WATER_SPACEFILL_FORMAT),Integer.toString(ri.getSize()))));
			String propertiesScript = spacefillScript + translucencyScript;
			return propertiesScript;
		}
		return EMPTY_STRING;
	}

	private static String getWaterBondPropertiesScript(RenderingInfo ri)
	{
		if((null != ri) && (null != getPrb()))
		{
			String bondTranslucency = Float.toString(ri.getBondTranslucency()/100.f);
			String bondTranslucencyScript = ((-1 == ri.getBondTranslucency()) ? EMPTY_STRING : (String.format(prb.getString(WATER_BONDTRANSLUCENCY_FORMAT), bondTranslucency)));
			return bondTranslucencyScript;
		}
		return EMPTY_STRING;
	}
	
	private static String getWaterFilteredSelectionScript(Viewer viewer, RenderingInfo ri) throws StarBiochemException
	{
		if((null != getPrb()) && (null != ri))
		{
			String format = getPrb().getString(WATER_HYDROGEN_SELECTION_FORMAT);
			if((null != format) && !EMPTY_STRING.equals(format))
			{
				String selectionVar = getWaterSelectionVariable(viewer, ri);
				if((null != selectionVar) && (!EMPTY_STRING.equals(selectionVar)))
				{
					return String.format(format, selectionVar);
				}
			}
		}
		return null;
	}
	
	private static String getWaterSelection(RenderingInfo ri)
	{
		if((null != getPrb()) && (null != ri) && (null != ri.getSelection()))
		{
			String[] selections = ri.getSelection();
			if((-1 != ri.getFilterOptions()))
			{
				String selectionScript = EMPTY_STRING;
				String format = getPrb().getString(WATER_FILTERED_SELECTION_FORMAT); 
				if((null != format) && !EMPTY_STRING.equals(format))
				{
					for(int i=0; selections.length != i; i++)
					{
						selectionScript += String.format(format, selections[i]);
						if(i < (selections.length-1))
						{
							selectionScript += " or "; //$NON-NLS-1$
						}	
					}
				}
				if(!EMPTY_STRING.equals(selectionScript))
				{
					return selectionScript;
				}
			}
		}
		return null;
	}
	
	private static String getWaterSelectionVariable(Viewer viewer, RenderingInfo ri) throws StarBiochemException
	{
		if((null != getPrb()) && (null != ri))
		{
			String format = getPrb().getString(SET_VARIABLE_FORMAT);
			if((null != format) && !EMPTY_STRING.equals(format))
			{
				String selection = getWaterSelection(ri);
				if((null != selection) && (!EMPTY_STRING.equals(selection)))
				{
					String waterSelection = "waterSelection"; //$NON-NLS-1$
					String script = String.format(format, waterSelection, selection);
					viewer.script(script);
					return waterSelection;
				}
			}
		}
		return null;
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
		
	protected static String getAdjustWaterFilteredSelectionScript(AdjustSelectionInfo asi, Viewer viewer) throws StarBiochemException
	{
		if((null != asi) && (null != asi.getSelections()))
		{
			if(null != getPrb())
			{
				String format = getPrb().getString(ADJUST_WATER_FILTERED_SELECTION_FORMAT);
				if((null != format) && !EMPTY_STRING.equals(format))
				{
					String selection = createSelector(asi.getSelections());
					if((null != selection) && !EMPTY_STRING.equals(selection))
					{
						String adjustWaterVar = getAdjustWaterSelectionVariable(viewer, selection);
						if((null != adjustWaterVar) && !EMPTY_STRING.equals(adjustWaterVar))
						{
							String filteredSelectionScript = String.format(format, adjustWaterVar);
							return filteredSelectionScript;
						}
					}
				}
			}
		}
		return null;
	}
	
	private static String getAdjustWaterSelectionVariable(Viewer viewer, String selection) throws StarBiochemException
	{
		if((null != getPrb()))
		{
			String format = getPrb().getString(SET_VARIABLE_FORMAT);
			if((null != format) && !EMPTY_STRING.equals(format))
			{
				if((null != selection) && (!EMPTY_STRING.equals(selection)))
				{
					String waterSelection = "waterSelection"; //$NON-NLS-1$
					String script = String.format(format, waterSelection, selection);
					if((null != script) && !EMPTY_STRING.equals(script))
					{
						viewer.script(script);
						return waterSelection;
					}
				}
			}
		}
		return null;
	}
	
	private static String createSelector(String[] selections)
	{
		if((null != selections) && (0 != selections.length))
		{
			String selector = EMPTY_STRING;
			for(int i=0; selections.length != i; i++)
			{
				if(0 == i)
				{
					selector = OPEN_CURLEY_BRACKET + selections[0];
				}
				else
				{
					selector += LOGICAL_OR + selections[i];
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
