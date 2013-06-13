package molecule.ui.jmol;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.PropertyResourceBundle;

import molecule.interfaces.AdjustSelectionInfo;
import molecule.interfaces.RenderingInfo;
import molecule.ui.signal.RenderingInfoRaiser;
import app.StarBiochemException;

public class ViewerRenderProteinPrimaryStructure 
{
	private static final String EMPTY_STRING = ""; //$NON-NLS-1$
	
	private static final String OPEN_CURLEY_BRACKET = "{"; //$NON-NLS-1$
    private static final String CLOSE_CURLEY_BRACKET = "}"; //$NON-NLS-1$
    private static final String LOGICAL_OR = " or "; //$NON-NLS-1$
	
	private static final String SELECTOR_PROTEIN = "PROTEIN"; //$NON-NLS-1$
    private static final String SELECTOR_BACKBONE = "BACKBONE"; //$NON-NLS-1$
    private static final String SELECTOR_SIDECHAIN = "SIDECHAIN"; //$NON-NLS-1$
    
    private static final String SET_VARIABLE_FORMAT = "set_variable_format"; //$NON-NLS-1$
    private static String SELECTION = "selection"; //$NON-NLS-1$
    
    private static final String PROTEIN_PRIMARY_TRANSLUCENCY_FORMAT = "protein_primary_translucency_format"; //$NON-NLS-1$
	private static final String PROTEIN_PRIMARY_BONDTRANSLUCENCY_FORMAT = "protein_primary_bondtranslucency_format"; //$NON-NLS-1$
	private static final String PROTEIN_PRIMARY_SPACEFILL_FORMAT = "protein_primary_spacefill_format"; //$NON-NLS-1$
	private static final String PROTEIN_PRIMARY_FILTERED_SELECTOR_FORMAT = "protein_primary_filtered_selector_format"; //$NON-NLS-1$
	
	private static final String SSBOND_SIZE_FORMAT = "ssbond_size_format"; //$NON-NLS-1$
	private static final String SSBOND_TRANSLUCENCY_FORMAT = "ssbond_translucency_format"; //$NON-NLS-1$
	
	private static final String PROTEIN_PRIMARY_FILTERED_BACKBONE_SELECTOR_FORMAT = "protein_primary_filtered_backbone_selector_format"; //$NON-NLS-1$
	private static final String PROTEIN_PRIMARY_FILTERED_SIDECHAIN_SELECTOR_FORMAT = "protein_primary_filtered_sidechain_selector_format"; //$NON-NLS-1$
	private static final String PROTEIN_PRIMARY_FILTERED_SELECTION_FORMAT = "protein_primary_filtered_selection_format"; //$NON-NLS-1$
	
	private static final String PROTEIN_PRIMARY_SELECTION_FORMAT = "protein_primary_selection_format"; //$NON-NLS-1$
	
	private static final String ADJUST_PROTEIN_PRIMARY_FILTERED_SELECTION_FORMAT = "adjust_protein_primary_filtered_selection_format"; //$NON-NLS-1$

	private static final String BOND_MODE_AND = "bond_mode_and"; //$NON-NLS-1$
	private static final String bondFormat = getPrb().getString(BOND_MODE_AND);

	transient private static PropertyResourceBundle prb = null;
	private static PropertyResourceBundle getPrb()
	{
		if(null == prb)
		{
			InputStream inputStream = ViewerRenderProteinPrimaryStructure.class.getResourceAsStream("./JmolScripts/StarBiochem.properties"); //$NON-NLS-1$
			if( inputStream == null )
			{
				inputStream = ViewerRenderProteinPrimaryStructure.class.getResourceAsStream("/JmolScripts/StarBiochem.properties" );  //$NON-NLS-1$
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
	
	protected static void renderProteinPrimaryStructure(Viewer viewer, RenderingInfo ri) throws StarBiochemException
	{
		if((null != viewer) && (null != ri))
		{
			int filterOptions = ri.getFilterOptions();
			if(0 != (filterOptions & (RenderingInfoRaiser.BACKBONE_VISIBLE | RenderingInfoRaiser.SIDECHAIN_VISIBLE)))
			{	
				String propertiesScript = getPrimaryPropertiesScript(ri);
				if((null != propertiesScript) && !EMPTY_STRING.equals(propertiesScript))
				{
					boolean isRenderAtom = true;
					ArrayList<String> filteredSelectionScript = getProteinPrimaryFilteredSelectionScript(ri, viewer, isRenderAtom);
					if((null != filteredSelectionScript) && (!filteredSelectionScript.isEmpty()))
					{
						String script = EMPTY_STRING;
						for(int i=0; filteredSelectionScript.size() != i; i++)
						{
							script += filteredSelectionScript.get(i) + propertiesScript;
						}
						if(!EMPTY_STRING.equals(script))
						{
							script += "subset;"; //$NON-NLS-1$
							viewer.script(String.format(bondFormat, script));
						}
					}
				}
			}
		}
	}
	
	protected static void renderProteinPrimaryCovalentBonds(Viewer viewer, RenderingInfo ri) throws StarBiochemException
	{
		if((null != viewer) && (null != ri))
		{
			int filterOptions = ri.getFilterOptions();
			if(0 != (filterOptions & (RenderingInfoRaiser.BACKBONE_VISIBLE | RenderingInfoRaiser.SIDECHAIN_VISIBLE)))
			{	
				String ssBondPropertiesScript = getSSBondsPropertiesScript(ri);
				String bondPropertiesScript = getPrimaryBondPropertiesScript(ri);

				if((null != bondPropertiesScript) && !EMPTY_STRING.equals(bondPropertiesScript))
				{
					boolean isRenderAtom = false;
					ArrayList<String> filteredSelectionScript = getProteinPrimaryFilteredSelectionScript(ri, viewer, isRenderAtom);
					if((null != filteredSelectionScript) && (!filteredSelectionScript.isEmpty()))
					{
						String script = EMPTY_STRING;
						for(int i=0; filteredSelectionScript.size() != i; i++)
						{
							script += filteredSelectionScript.get(i) + bondPropertiesScript + ssBondPropertiesScript;
						}
						if(!EMPTY_STRING.equals(script))
						{
							script += "subset;"; //$NON-NLS-1$
							viewer.script(String.format(bondFormat, script));
						}
					}
				}
			}
		}
	}
	
	private static String getSSBondsPropertiesScript(RenderingInfo ri)
	{
		String ssBondsPropertiesScript = null;
		if((null != getPrb()) && (null != ri) && (null != ri.getSelection()) && (ri.getSelection().length != 0))
		{
			int size = ri.getSSbondSize();
			double ssbondSize = (size * 0.01);
			String ssBondSizeScript = ((-1 == size)  ? EMPTY_STRING : (String.format(getPrb().getString(SSBOND_SIZE_FORMAT),Double.toString(ssbondSize))));
			
			String ssBondTranslucency = Float.toString(ri.getSSbondTranslucency()/100.f);
			String ssBondTranslucencyScript = ((-1 == ri.getSSbondTranslucency())  ? EMPTY_STRING : (String.format(getPrb().getString(SSBOND_TRANSLUCENCY_FORMAT),ssBondTranslucency)));
			
			ssBondsPropertiesScript = ssBondSizeScript + ssBondTranslucencyScript;
		}
		return ssBondsPropertiesScript;
	}

	private static String getPrimaryPropertiesScript(RenderingInfo ri)
	{
		if((null != ri) && (null != getPrb()))
		{
			String translucencyScript = ((-1 == ri.getTranslucency()) ? EMPTY_STRING : (String.format(getPrb().getString(PROTEIN_PRIMARY_TRANSLUCENCY_FORMAT),Float.toString(ri.getTranslucency()/100.f))));
			String spacefillScript = ((-1 == ri.getSize())  ? EMPTY_STRING : (String.format(getPrb().getString(PROTEIN_PRIMARY_SPACEFILL_FORMAT),Integer.toString(ri.getSize()))));
					
			String propertiesScript = spacefillScript + translucencyScript; 
			return propertiesScript;
		}
		return EMPTY_STRING;
	}

	private static String getPrimaryBondPropertiesScript(RenderingInfo ri)
	{
		if((null != ri) && (null != getPrb()))
		{
			String[] selections = ri.getSelection();
			if((null != selections) && (0 != selections.length))
			{
				String bondTranslucency = Float.toString(ri.getBondTranslucency()/100.f);
				String bondTranslucencyScript = ((-1 == ri.getBondTranslucency()) ? EMPTY_STRING : (String.format(getPrb().getString(PROTEIN_PRIMARY_BONDTRANSLUCENCY_FORMAT), bondTranslucency)));
				String propertiesScript = bondTranslucencyScript;
				return propertiesScript;
			}
		}
		return EMPTY_STRING;
	}

	private static ArrayList<String> getProteinPrimaryFilteredSelectionScript(RenderingInfo ri, Viewer viewer, boolean isRenderAtom) throws StarBiochemException
	{
		if((null != getPrb()) && (null != ri) && (null != ri.getSelection()))
		{
			int filterOptions = ri.getFilterOptions();
			if(-1 != filterOptions)
			{
				String format = EMPTY_STRING;
				if((0 != (filterOptions & RenderingInfoRaiser.BACKBONE_VISIBLE)) && (0 != (filterOptions & RenderingInfoRaiser.SIDECHAIN_VISIBLE)))
				{
					format = getPrb().getString(PROTEIN_PRIMARY_FILTERED_SELECTOR_FORMAT);
				}
				else if((0 != (filterOptions & RenderingInfoRaiser.BACKBONE_VISIBLE)) && (0 == (filterOptions & RenderingInfoRaiser.SIDECHAIN_VISIBLE)))
				{
					format = getPrb().getString(PROTEIN_PRIMARY_FILTERED_BACKBONE_SELECTOR_FORMAT);
				}
				else if((0 == (filterOptions & RenderingInfoRaiser.BACKBONE_VISIBLE)) && (0 != (filterOptions & RenderingInfoRaiser.SIDECHAIN_VISIBLE)))
				{
					format = getPrb().getString(PROTEIN_PRIMARY_FILTERED_SIDECHAIN_SELECTOR_FORMAT);
				}
				if((null != format) && !EMPTY_STRING.equals(format))
				{
					String[] getSelectionVar = getSelectionVariables(viewer, ri.getSelection(), isRenderAtom);
					ArrayList<String> selectors = createPrimaryFilteredSelectors(format, getSelectionVar);
					if((null != selectors) && !selectors.isEmpty())
					{
						format = getPrb().getString(PROTEIN_PRIMARY_FILTERED_SELECTION_FORMAT);
						if((null != format) && !EMPTY_STRING.equals(format))
						{
							ArrayList<String> filteredSelectors = new ArrayList<String>();
							for(int i=0; selectors.size() != i; i++)
							{
								filteredSelectors.add(String.format(format, selectors.get(i)));
							}
							return filteredSelectors;
						}
					}
				}
			}
		}
		return null;
	}
	
	private static String[] getSelectionVariables(Viewer viewer, String[] selections, boolean isRenderAtom) throws StarBiochemException
	{
		if((null != getPrb()) && (null != selections))
		{
			ArrayList<String> selectionArray = new ArrayList<String>();
			String format = getPrb().getString(PROTEIN_PRIMARY_SELECTION_FORMAT);
			if(null != format)
			{
				String selector = EMPTY_STRING;
				if(isRenderAtom)
				{
					for(int i = 0; selections.length != i; i++)
					{
						selector += String.format(format, selections[i]);
						if(i < selections.length-1)
						{
							selector += " or "; //$NON-NLS-1$
						}
						if(i == selections.length-1)
						{
							format = getPrb().getString(SET_VARIABLE_FORMAT);
							if(null != format)
							{
								viewer.script(String.format(format, SELECTION, selector));
								selectionArray.add(SELECTION);
							}
						}
					}
					if(!selectionArray.isEmpty())
					{
						return selectionArray.toArray(new String[0]);
					}
				}
				if(!isRenderAtom)
				{
					String[] chainSelections = getSelectionByChain(selections, viewer, format);
					if((null != chainSelections) && (0 != chainSelections.length))
					{
						return chainSelections;
					}
				}			
			}
		}
		return null;
	}
	
	private static String[] getSelectionByChain(String[] selections, Viewer viewer, String format) throws StarBiochemException
	{
		if(null != selections)
		{
			ArrayList<String> selectionArray = new ArrayList<String>();
			String selector = EMPTY_STRING;
			String chainSelection = null;
			String chainFormat = getPrb().getString(SET_VARIABLE_FORMAT);
			String chainID = null;
			int index = 0;
			int count = 0;
			for (int i = 0; selections.length != i; i++)
			{
				if(chainID != null)
				{
					if(!getChainId(selections[i]).equals(chainID))
					{
						chainSelection = SELECTION + Integer.toString(count);
						viewer.script(String.format(chainFormat, chainSelection, selector));
						selectionArray.add(chainSelection);
						
						count++;
						selector = EMPTY_STRING;
						chainID = getChainId(selections[i]);
						index = 0;
					}
				}
				if(chainID == null)
				{
					chainID = getChainId(selections[i]);
				}
				if (0 == index)
				{
					selector = String.format(format, selections[i]);
					index++;
				}
				else
				{
					selector += LOGICAL_OR + String.format(format, selections[i]);
				}
				
				if(i == selections.length-1)
				{
					chainSelection = SELECTION + Integer.toString(count);
					viewer.script(String.format(chainFormat, chainSelection, selector));
					selectionArray.add(chainSelection);
					count++;
				}
			}
			return selectionArray.toArray(new String[0]);
		}
		return null;
	}

	private static ArrayList<String> createPrimaryFilteredSelectors(String format, String[] selections)
	{
		if ((null != selections) && (null != format))
		{
			ArrayList<String> selectors = new ArrayList<String>();
			String selector = EMPTY_STRING;
			for (int i = 0; selections.length != i; i++)
			{
					selector = OPEN_CURLEY_BRACKET + String.format(format, selections[i]) + CLOSE_CURLEY_BRACKET;
					selectors.add(selector);
			}
			if(!selectors.isEmpty())
			{
				return selectors;
			}
		}
		return null;
	}
	
	private static String getChainId(String atom)
	{	
		String chain = null;
		if(null != atom)
		{
			chain = atom.substring(atom.length()-1);				
		}		
		return chain;
	}
	
	protected static String getAdjustProteinPrimaryFilteredSelectionScript(AdjustSelectionInfo asi, Viewer viewer) throws StarBiochemException
	{
		if((null != getPrb()) && (null != asi) && (null != asi.getSelections()))
		{
			int filterOptions = asi.getFilterOptions();
			if(-1 != filterOptions)
			{
				String script = EMPTY_STRING;
				if((0 != (filterOptions & RenderingInfoRaiser.BACKBONE_VISIBLE)) && (0 != (filterOptions & RenderingInfoRaiser.SIDECHAIN_VISIBLE)))
				{
					script = getAdjustProteinPrimarySelection(asi, SELECTOR_PROTEIN, viewer);
				}
				else if((0 != (filterOptions & RenderingInfoRaiser.BACKBONE_VISIBLE)) && (0 == (filterOptions & RenderingInfoRaiser.SIDECHAIN_VISIBLE)))
				{
					script = getAdjustProteinPrimarySelection(asi, SELECTOR_BACKBONE, viewer);
				}	
				else if((0 != (filterOptions & RenderingInfoRaiser.SIDECHAIN_VISIBLE)) && (0 == (filterOptions & RenderingInfoRaiser.BACKBONE_VISIBLE)))
				{
					script = getAdjustProteinPrimarySelection(asi, SELECTOR_SIDECHAIN, viewer);
				}
				if((0 == (filterOptions & RenderingInfoRaiser.BACKBONE_VISIBLE)) && (0 == (filterOptions & RenderingInfoRaiser.SIDECHAIN_VISIBLE)))
				{
					script = getAdjustProteinPrimarySelection(asi, " not " + SELECTOR_BACKBONE + " and not " + SELECTOR_SIDECHAIN , viewer); //$NON-NLS-1$ //$NON-NLS-2$
				}
				if(!EMPTY_STRING.equals( script ))
				{
					return script;
				}
			}
		}
		return null;
	}
	
	private static String getAdjustProteinPrimarySelection(AdjustSelectionInfo asi, String structure, Viewer viewer) throws StarBiochemException
	{
		String filteredSelectionScript = ""; //$NON-NLS-1$
		if((null != asi) && (null != asi.getSelections()) && (null != structure))
		{
			if(null != getPrb())
			{
				String format = getPrb().getString(ADJUST_PROTEIN_PRIMARY_FILTERED_SELECTION_FORMAT);
				if(null != format)
				{
					int filterOptions = asi.getFilterOptions();
					if(-1 != filterOptions)
					{
						String[] selections = asi.getSelections();
						if(null != selections)
						{
							String selection = getAdjustSelectionVariables(viewer, selections);
							if(null != selection && !selection.equals(EMPTY_STRING))
							{
								filteredSelectionScript = String.format(format, selection, structure);
								return filteredSelectionScript;
							}
						}
					}
				}
			}
		}
		return null;
	}
	
	private static String getAdjustSelectionVariables(Viewer viewer, String[] selections) throws StarBiochemException
	{
		if((null != getPrb()) && (null != selections) && (0 != selections.length))
		{
			String adjustPrimarySelection = "adjustPrimarySelection"; //$NON-NLS-1$
			String format = getPrb().getString(PROTEIN_PRIMARY_SELECTION_FORMAT);
			String selector = EMPTY_STRING;

			for(int i = 0; selections.length != i; i++)
			{
				selector += String.format(format, selections[i]);
				if(i < selections.length-1)
				{
					selector += " or "; //$NON-NLS-1$
				}
				if(i == selections.length-1)
				{
					format = getPrb().getString(SET_VARIABLE_FORMAT);
					viewer.script(String.format(format, adjustPrimarySelection, selector));
				}
			}
			return selector;
		}
		return null;
	}
	
}
