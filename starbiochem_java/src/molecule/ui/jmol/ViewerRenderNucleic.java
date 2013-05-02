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

public class ViewerRenderNucleic 
{
	private static final String EMPTY_STRING = ""; //$NON-NLS-1$
	
    private static final String OPEN_CURLEY_BRACKET = "{"; //$NON-NLS-1$
    private static final String CLOSE_CURLEY_BRACKET = "}"; //$NON-NLS-1$

    private static final String LOGICAL_OR = " or "; //$NON-NLS-1$
    private static final String LOGICAL_XOR = " xor "; //$NON-NLS-1$
    
    private static final String ALL_SUFFIX = ".*"; //$NON-NLS-1$
    private static final String BASE_N1_SUFFIX = ".N1"; //$NON-NLS-1$
    private static final String BASE_N_SUFFIX = ".N?"; //$NON-NLS-1$
    private static final String BASE_C_SUFFIX = ".C?"; //$NON-NLS-1$
    private static final String BASE_H_SUFFIX = ".H???"; //$NON-NLS-1$

    private static final String PHOSPHATE_P_SUFFIX = ".P?"; //$NON-NLS-1$
    private static final String PHOSPHATE_OTHER_SUFFIX = ".?P?"; //$NON-NLS-1$
    private static final String PHOSPHATE_OXYGEN_SUFFIX = ".O??";			 //$NON-NLS-1$
    private static final String OTHER_OXYGEN_SUFFIX = ".O?";			 //$NON-NLS-1$
    private static final String SUGAR_OXYGEN_SUFFIX = ".O?'";			 //$NON-NLS-1$

    private static final String SUGAR_SUFFIX = ".??'"; //$NON-NLS-1$
    private static final String SUGAR_HYDRO_SUFFIX = ".H??'"; //$NON-NLS-1$
    private static final String SUGAR_CARBON1_SUFFIX = ".C1'"; //$NON-NLS-1$
    private static final String SUGAR_OXYGEN3_SUFFIX = ".O3'"; //$NON-NLS-1$
    private static final String SUGAR_OXYGEN5_SUFFIX = ".O5'"; //$NON-NLS-1$
    
    private static final String SET_VARIABLE_FORMAT = "set_variable_format"; //$NON-NLS-1$
	private static final String SELECTION = "selection"; //$NON-NLS-1$
    private static final String NUCLEIC_TRANSLUCENCY_FORMAT = "nucleic_translucency_format"; //$NON-NLS-1$
	private static final String NUCLEIC_BONDTRANSLUCENCY_FORMAT = "nucleic_bondtranslucency_format"; //$NON-NLS-1$
	private static final String NUCLEIC_SPACEFILL_FORMAT = "nucleic_spacefill_format"; //$NON-NLS-1$

    private static final String NUCLEIC_FILTERED_SELECTION_FORMAT = "nucleic_filtered_selection_format"; //$NON-NLS-1$
	private static final String NUCLEIC_HBOND_FILTERED_SELECTION_FORMAT = "nucleic_hbond_filtered_selection_format"; //$NON-NLS-1$
    
    private static final String HBOND_SIZE_FORMAT = "hbond_size_format"; //$NON-NLS-1$
	private static final String HBOND_TRANSLUCENCY_FORMAT = "hbond_translucency_format"; //$NON-NLS-1$
	
    private static final String NUCLEIC_HYDROGEN_SELECTION_FORMAT = "nucleic_hydrogen_selection_format"; //$NON-NLS-1$
	private static final String NUCLEIC_SELECTION_FORMAT = "nucleic_selection_format"; //$NON-NLS-1$
	private static final String ADJUST_NUCLEIC_FILTERED_SELECTION_FORMAT = "adjust_nucleic_filtered_selection_format"; //$NON-NLS-1$
	
	private static final String BOND_MODE_OR = "bond_mode_or"; //$NON-NLS-1$
	private static final String bondFormat = getPrb().getString(BOND_MODE_OR);

	transient private static PropertyResourceBundle prb = null;
	private static PropertyResourceBundle getPrb()
	{
		if(null == prb)
		{
			InputStream inputStream = ViewerRenderNucleic.class.getResourceAsStream("./JmolScripts/StarBiochem.properties"); //$NON-NLS-1$
			if( inputStream == null )
			{
				inputStream = ViewerRenderNucleic.class.getResourceAsStream("/JmolScripts/StarBiochem.properties" );  //$NON-NLS-1$
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
	
	protected static void renderNucleicAtoms(Viewer viewer, RenderingInfo ri) throws StarBiochemException
	{
		if((null != viewer) && (null != ri))
		{
			int filterOptions = ri.getFilterOptions();
			if(0 != (filterOptions & (RenderingInfoRaiser.SUGAR_VISIBLE | RenderingInfoRaiser.BASE_VISIBLE | RenderingInfoRaiser.PHOSPHATE_VISIBLE)))
			{
				String propertiesScript = getNucleicPropertiesScript(ri);
				if((null != propertiesScript) && !EMPTY_STRING.equals(propertiesScript))
				{
					String script = EMPTY_STRING;
					String filteredSelectionScript = getNucleicFilteredSelectionScript(viewer, ri);
					String format = getPrb().getString(NUCLEIC_SELECTION_FORMAT);
					if((null != filteredSelectionScript) && !filteredSelectionScript.isEmpty())
					{	
						script += (String.format(format, filteredSelectionScript, propertiesScript));
					}
					viewer.script(String.format(bondFormat, script));
				}
			}
		}
	}
		
	protected static void renderNucleicBonds(Viewer viewer, RenderingInfo ri) throws StarBiochemException
	{
		if((null != viewer) && (null != ri))
		{
			int filterOptions = ri.getFilterOptions();
			if(0 != (filterOptions & (RenderingInfoRaiser.SUGAR_VISIBLE | RenderingInfoRaiser.BASE_VISIBLE | RenderingInfoRaiser.PHOSPHATE_VISIBLE)))
			{
				String propertiesScript = getNucleicBondTranslucencyPropertyScript(ri);
				if((null != propertiesScript) && !EMPTY_STRING.equals(propertiesScript))
				{
					viewer.script(String.format(bondFormat, propertiesScript));
				}
			}
		}
	}
		
	protected static void renderNucleicHBond(Viewer viewer, RenderingInfo ri) throws StarBiochemException
	{
		if((null != viewer) && (null != ri))
		{
			int filterOptions = ri.getFilterOptions();
			if(0 != (filterOptions & (RenderingInfoRaiser.SUGAR_VISIBLE | RenderingInfoRaiser.BASE_VISIBLE | RenderingInfoRaiser.PHOSPHATE_VISIBLE)))
			{
				String propertiesScript = getHBondPropertiesScript(ri);
				if((null != propertiesScript) && !EMPTY_STRING.equals(propertiesScript))
				{
					ArrayList<String> filteredSelectionScripts = getNucleicHBondFilteredSelectionScripts(ri);
					if((null != filteredSelectionScripts) && !filteredSelectionScripts.isEmpty())
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
	private static String getNucleicPropertiesScript(RenderingInfo ri)
	{
		if((null != ri) && (null != getPrb()))
		{
			String translucencyScript = ((-1 == ri.getTranslucency()) ? EMPTY_STRING : (String.format(getPrb().getString(NUCLEIC_TRANSLUCENCY_FORMAT),Float.toString(ri.getTranslucency()/100.f))));
			String spacefillScript = ((-1 == ri.getSize())  ? EMPTY_STRING : (String.format(getPrb().getString(NUCLEIC_SPACEFILL_FORMAT),Integer.toString(ri.getSize()))));
			
			String propertiesScript = translucencyScript + spacefillScript;
			return propertiesScript;
		}
		return EMPTY_STRING;
	}
	
	private static String getNucleicBondTranslucencyPropertyScript(RenderingInfo ri)
	{
		if(null != ri)
		{
			if(-1 != ri.getBondTranslucency())
			{
				String bondTranslucency = Float.toString(ri.getBondTranslucency()/100.f);
				int filterOptions = ri.getFilterOptions();
				if(-1 != filterOptions)
				{
					String[] selection = ri.getSelection();
					if((null != selection) && (0 != selection.length))
					{
						String format = getPrb().getString(NUCLEIC_BONDTRANSLUCENCY_FORMAT); 
						if(null != format)
						{
							if((0 != (filterOptions & RenderingInfoRaiser.BASE_VISIBLE))
							&& (0 != (filterOptions & RenderingInfoRaiser.SUGAR_VISIBLE))
							&& (0 != (filterOptions & RenderingInfoRaiser.PHOSPHATE_VISIBLE)))
							{
								String allNucleic = createSelector(selection, EMPTY_STRING);
								if((null != allNucleic) && !EMPTY_STRING.equals(allNucleic))
								{
									return String.format(format, allNucleic, bondTranslucency);
								}
							}
							else if ((0 != (filterOptions & RenderingInfoRaiser.BASE_VISIBLE))
							&& (0 != (filterOptions & RenderingInfoRaiser.SUGAR_VISIBLE)))
							{
								String basesSugars = createNucleicSugarBaseBondSelector(selection);
								if((null != basesSugars) && !EMPTY_STRING.equals(basesSugars))
								{
									return String.format(format, basesSugars, bondTranslucency);
								}
							}
							else if ((0 != (filterOptions & RenderingInfoRaiser.SUGAR_VISIBLE))
							&& (0 != (filterOptions & RenderingInfoRaiser.PHOSPHATE_VISIBLE)))
							{
								String sugarsPhosphates = createNucleicSugarPhosphateBondSelector(selection);
								if((null != sugarsPhosphates) && !EMPTY_STRING.equals(sugarsPhosphates))
								{
									return String.format(format, sugarsPhosphates, bondTranslucency);
								}
							}
							else if ((0 != (filterOptions & RenderingInfoRaiser.BASE_VISIBLE))
							&& (0 != (filterOptions & RenderingInfoRaiser.PHOSPHATE_VISIBLE)))
							{
								String basesPhosphates = createNucleicPhosphateBaseBondSelector(selection);
								if((null != basesPhosphates) && !EMPTY_STRING.equals(basesPhosphates))
								{
									return String.format(format, basesPhosphates, bondTranslucency);
								}
							}
							else if(0 != (filterOptions & RenderingInfoRaiser.BASE_VISIBLE))
							{
								String bases = createNucleicBaseBondSelector(selection);
								if((null != bases) && !EMPTY_STRING.equals(bases))
								{
									return String.format(format, bases, bondTranslucency);
								}
							}
							else if(0 != (filterOptions & RenderingInfoRaiser.SUGAR_VISIBLE))
							{
								String allsugar = createNucleicSugarBondSelector(selection);
								if((null != allsugar) && !EMPTY_STRING.equals(allsugar))
								{
									return String.format(format, allsugar, bondTranslucency);
								}
							}
							else if(0 != (filterOptions & RenderingInfoRaiser.PHOSPHATE_VISIBLE))
							{
								String phosphate = createNucleicPhosphateBondSelector(selection);
								if((null != phosphate) && !EMPTY_STRING.equals(phosphate))
								{
									return String.format(format, phosphate, bondTranslucency);
								}
							}
						}
					}
				}
			}
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
		String hBondPropertiesScript = hBondSizeScript + hBondTranslucencyScript;

		return hBondPropertiesScript;
	}
	
	private static ArrayList<String> getNucleicHBondFilteredSelectionScripts(RenderingInfo ri)
	{
		if((null != getPrb()) && (null != ri) && (null != ri.getSelection()))
		{
			String[] selections = ri.getSelection();
			if(null != selections)
			{
				ArrayList<String> selectionScripts = new ArrayList<String>();
				for(int i=0; selections.length != i; i++)
				{
					String selectionScript = getNucleicHBondFilteredSelectionScript(selections[i], ri.getFilterOptions());
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

	private static String getNucleicHBondFilteredSelectionScript(String selection, int filterOptions)
	{
		if((null != getPrb()) && (null != selection) && (-1 != filterOptions))
		{
			String format = getPrb().getString(NUCLEIC_HBOND_FILTERED_SELECTION_FORMAT); 
			if(null != format)
			{
				if((0 != (filterOptions & RenderingInfoRaiser.BASE_VISIBLE))
				&& (0 != (filterOptions & RenderingInfoRaiser.SUGAR_VISIBLE))
				&& (0 != (filterOptions & RenderingInfoRaiser.PHOSPHATE_VISIBLE)))
				{
					return String.format(format, selection);
				}
				else if ((0 != (filterOptions & RenderingInfoRaiser.BASE_VISIBLE))
				&& (0 != (filterOptions & RenderingInfoRaiser.SUGAR_VISIBLE)))
				{
					String baseSugar = createNucleicBaseSugarSelector(selection);
					if(null != baseSugar)
					{
						return String.format(format, baseSugar);
					}
				}
				else if ((0 != (filterOptions & RenderingInfoRaiser.SUGAR_VISIBLE))
				&& (0 != (filterOptions & RenderingInfoRaiser.PHOSPHATE_VISIBLE)))
				{
					String sugarPhosphate = createNucleicSugarPhosphateSelector(selection);
					if(null != sugarPhosphate)
					{
						return String.format(format, sugarPhosphate);
					}
				}
				else if ((0 != (filterOptions & RenderingInfoRaiser.BASE_VISIBLE))
				&& (0 != (filterOptions & RenderingInfoRaiser.PHOSPHATE_VISIBLE)))
				{
					String basePhosphate = createNucleicBasePhosphateSelector(selection);
					if(null != basePhosphate)
					{
						return String.format(format, basePhosphate);
					}
				}
				else if(0 != (filterOptions & RenderingInfoRaiser.BASE_VISIBLE))
				{
					String base = createNucleicBaseSelector(selection);
					if(null != base)
					{
						return String.format(format, base);
					}
				}
				else if(0 != (filterOptions & RenderingInfoRaiser.SUGAR_VISIBLE))
				{
					String sugar = createNucleicSugarSelector(selection);
					if(null != sugar)
					{
						return String.format(format, sugar);
					}
				}
				else if(0 != (filterOptions & RenderingInfoRaiser.PHOSPHATE_VISIBLE))
				{
					String phosphate = createNucleicPhosphateSelector(selection);
					if(null != phosphate)
					{
						return String.format(format, phosphate);
					}
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
		
	private static String createNucleicSugarBondSelector(String[] selections)
	{
		if(null != selections)
		{
			String selector = EMPTY_STRING;
			for(int i=0; selections.length != i; i++)
			{
				if(0 == i)
				{
					selector = OPEN_CURLEY_BRACKET + OPEN_CURLEY_BRACKET + selections[0] + PHOSPHATE_OTHER_SUFFIX + CLOSE_CURLEY_BRACKET + LOGICAL_OR 
					+ OPEN_CURLEY_BRACKET + selections[0] + SUGAR_SUFFIX + CLOSE_CURLEY_BRACKET + LOGICAL_OR 
					+ OPEN_CURLEY_BRACKET + selections[0] + SUGAR_HYDRO_SUFFIX + CLOSE_CURLEY_BRACKET + LOGICAL_OR 
					+ OPEN_CURLEY_BRACKET + selections[0] + SUGAR_OXYGEN3_SUFFIX + CLOSE_CURLEY_BRACKET + LOGICAL_OR  
					+ OPEN_CURLEY_BRACKET + selections[0] + SUGAR_OXYGEN5_SUFFIX + CLOSE_CURLEY_BRACKET + LOGICAL_OR
					+ OPEN_CURLEY_BRACKET + selections[0] + BASE_N1_SUFFIX + CLOSE_CURLEY_BRACKET;
				}
				else
				{
					selector += LOGICAL_OR + OPEN_CURLEY_BRACKET + selections[i] + PHOSPHATE_OTHER_SUFFIX + CLOSE_CURLEY_BRACKET + LOGICAL_OR 
					+ OPEN_CURLEY_BRACKET + selections[i] + SUGAR_SUFFIX + CLOSE_CURLEY_BRACKET + LOGICAL_OR
					+ OPEN_CURLEY_BRACKET + selections[i] + SUGAR_HYDRO_SUFFIX + CLOSE_CURLEY_BRACKET + LOGICAL_OR
					+ OPEN_CURLEY_BRACKET + selections[0] + SUGAR_OXYGEN3_SUFFIX + CLOSE_CURLEY_BRACKET + LOGICAL_OR  
					+ OPEN_CURLEY_BRACKET + selections[0] + SUGAR_OXYGEN5_SUFFIX + CLOSE_CURLEY_BRACKET + LOGICAL_OR
					+ OPEN_CURLEY_BRACKET +  selections[i] + BASE_N1_SUFFIX + CLOSE_CURLEY_BRACKET;
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
	
	private static String createNucleicSugarPhosphateBondSelector(String[] selections)
	{
		if(null != selections)
		{
			String selector = EMPTY_STRING;
			for(int i=0; selections.length != i; i++)
			{
				if(0 == i)
				{
					selector = OPEN_CURLEY_BRACKET + OPEN_CURLEY_BRACKET + selections[0] + PHOSPHATE_OTHER_SUFFIX + CLOSE_CURLEY_BRACKET + LOGICAL_OR 
					+ OPEN_CURLEY_BRACKET + selections[0] + PHOSPHATE_P_SUFFIX + CLOSE_CURLEY_BRACKET + LOGICAL_OR 
					+ OPEN_CURLEY_BRACKET + selections[0] + SUGAR_SUFFIX + CLOSE_CURLEY_BRACKET + LOGICAL_OR 
					+ OPEN_CURLEY_BRACKET + selections[0] + SUGAR_OXYGEN3_SUFFIX + CLOSE_CURLEY_BRACKET + LOGICAL_OR  
					+ OPEN_CURLEY_BRACKET + selections[0] + SUGAR_OXYGEN5_SUFFIX + CLOSE_CURLEY_BRACKET + LOGICAL_OR
					+ OPEN_CURLEY_BRACKET + selections[0] + BASE_N1_SUFFIX + CLOSE_CURLEY_BRACKET;
				}
				else
				{
					selector += LOGICAL_OR + OPEN_CURLEY_BRACKET + selections[i] + PHOSPHATE_OTHER_SUFFIX + CLOSE_CURLEY_BRACKET + LOGICAL_OR 
					+ OPEN_CURLEY_BRACKET + selections[i] + PHOSPHATE_P_SUFFIX + CLOSE_CURLEY_BRACKET + LOGICAL_OR 
					+ OPEN_CURLEY_BRACKET + selections[i] + SUGAR_SUFFIX + CLOSE_CURLEY_BRACKET + LOGICAL_OR 
					+ OPEN_CURLEY_BRACKET + selections[i] + SUGAR_OXYGEN3_SUFFIX + CLOSE_CURLEY_BRACKET + LOGICAL_OR  
					+ OPEN_CURLEY_BRACKET + selections[i] + SUGAR_OXYGEN5_SUFFIX + CLOSE_CURLEY_BRACKET + LOGICAL_OR
					+ OPEN_CURLEY_BRACKET + selections[i] + BASE_N1_SUFFIX + CLOSE_CURLEY_BRACKET;
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
	
	private static String createNucleicSugarBaseBondSelector(String[] selections)
	{
		if(null != selections)
		{
			String selector = EMPTY_STRING;
			for(int i=0; selections.length != i; i++)
			{
				if(0 == i)
				{
					selector = OPEN_CURLEY_BRACKET + OPEN_CURLEY_BRACKET + selections[i] + BASE_C_SUFFIX + LOGICAL_OR 
							+ OPEN_CURLEY_BRACKET + selections[0] + BASE_H_SUFFIX + CLOSE_CURLEY_BRACKET + LOGICAL_OR 
							+ OPEN_CURLEY_BRACKET + selections[0] + BASE_N_SUFFIX + CLOSE_CURLEY_BRACKET + LOGICAL_OR 
							+ OPEN_CURLEY_BRACKET + selections[0] + SUGAR_SUFFIX + CLOSE_CURLEY_BRACKET + LOGICAL_OR 
							+ OPEN_CURLEY_BRACKET + selections[0] + SUGAR_HYDRO_SUFFIX + CLOSE_CURLEY_BRACKET + LOGICAL_OR 
							+ OPEN_CURLEY_BRACKET + selections[0] + SUGAR_OXYGEN3_SUFFIX + CLOSE_CURLEY_BRACKET + LOGICAL_OR  
							+ OPEN_CURLEY_BRACKET + selections[0] + SUGAR_OXYGEN5_SUFFIX + CLOSE_CURLEY_BRACKET + LOGICAL_XOR
							+ selections[i] + PHOSPHATE_P_SUFFIX + LOGICAL_XOR 
							+ selections[i] + PHOSPHATE_OXYGEN_SUFFIX + LOGICAL_XOR 
							+ selections[i] + PHOSPHATE_OTHER_SUFFIX + CLOSE_CURLEY_BRACKET;
				}
				else
				{
					selector += LOGICAL_OR + OPEN_CURLEY_BRACKET + selections[i] + BASE_C_SUFFIX + LOGICAL_OR 
							+ OPEN_CURLEY_BRACKET + selections[0] + BASE_H_SUFFIX + CLOSE_CURLEY_BRACKET + LOGICAL_OR 
							+ OPEN_CURLEY_BRACKET + selections[0] + BASE_N_SUFFIX + CLOSE_CURLEY_BRACKET + LOGICAL_OR 
							+ OPEN_CURLEY_BRACKET + selections[0] + SUGAR_SUFFIX + CLOSE_CURLEY_BRACKET + LOGICAL_OR 
							+ OPEN_CURLEY_BRACKET + selections[0] + SUGAR_HYDRO_SUFFIX + CLOSE_CURLEY_BRACKET + LOGICAL_OR 
							+ OPEN_CURLEY_BRACKET + selections[0] + SUGAR_OXYGEN3_SUFFIX + CLOSE_CURLEY_BRACKET + LOGICAL_OR  
							+ OPEN_CURLEY_BRACKET + selections[0] + SUGAR_OXYGEN5_SUFFIX + CLOSE_CURLEY_BRACKET + LOGICAL_XOR
							+ selections[i] + PHOSPHATE_P_SUFFIX + LOGICAL_XOR 
							+ selections[i] + PHOSPHATE_OXYGEN_SUFFIX + LOGICAL_XOR 
							+ selections[i] + PHOSPHATE_OTHER_SUFFIX + CLOSE_CURLEY_BRACKET;
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
	
	private static String createNucleicPhosphateBondSelector(String[] selections)
	{
		if(null != selections)
		{
			String selector = EMPTY_STRING;
			for(int i=0; selections.length != i; i++)
			{
				if(0 == i)
				{
					selector = OPEN_CURLEY_BRACKET + OPEN_CURLEY_BRACKET + selections[0] + PHOSPHATE_P_SUFFIX + CLOSE_CURLEY_BRACKET + LOGICAL_OR 
					+ OPEN_CURLEY_BRACKET + selections[0] + PHOSPHATE_OTHER_SUFFIX + CLOSE_CURLEY_BRACKET + LOGICAL_OR 
					+ OPEN_CURLEY_BRACKET + selections[0] + SUGAR_OXYGEN3_SUFFIX + CLOSE_CURLEY_BRACKET + LOGICAL_OR 
					+ OPEN_CURLEY_BRACKET + selections[0] + SUGAR_OXYGEN5_SUFFIX + CLOSE_CURLEY_BRACKET;
				}
				else
				{
					selector += LOGICAL_OR + OPEN_CURLEY_BRACKET + selections[i] + PHOSPHATE_P_SUFFIX + CLOSE_CURLEY_BRACKET + LOGICAL_OR 
					+ OPEN_CURLEY_BRACKET + selections[i] + PHOSPHATE_OTHER_SUFFIX + CLOSE_CURLEY_BRACKET + LOGICAL_OR
					+ OPEN_CURLEY_BRACKET + selections[i] + SUGAR_OXYGEN3_SUFFIX + CLOSE_CURLEY_BRACKET + LOGICAL_OR 
					+ OPEN_CURLEY_BRACKET +selections[i] + SUGAR_OXYGEN5_SUFFIX + CLOSE_CURLEY_BRACKET;
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
	
	private static String createNucleicPhosphateBaseBondSelector(String[] selections)
	{
		if(null != selections)
		{
			String selector = EMPTY_STRING;
			for(int i=0; selections.length != i; i++)
			{
				if(0 == i)
				{
					selector = OPEN_CURLEY_BRACKET + OPEN_CURLEY_BRACKET + selections[i] + ALL_SUFFIX + LOGICAL_XOR + 
							selections[i] + SUGAR_SUFFIX + CLOSE_CURLEY_BRACKET + LOGICAL_OR + OPEN_CURLEY_BRACKET + 
							selections[i] + SUGAR_CARBON1_SUFFIX + CLOSE_CURLEY_BRACKET;
				}
				else
				{
					selector += LOGICAL_OR + OPEN_CURLEY_BRACKET + selections[i] + ALL_SUFFIX + LOGICAL_XOR + 
							selections[i] + SUGAR_SUFFIX + CLOSE_CURLEY_BRACKET + LOGICAL_OR + OPEN_CURLEY_BRACKET + 
							selections[i] + SUGAR_CARBON1_SUFFIX + CLOSE_CURLEY_BRACKET;
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
	
	private static String createNucleicBaseBondSelector(String[] selections)
	{
		if(null != selections)
		{
			String selector = EMPTY_STRING;
			for(int i=0; selections.length != i; i++)
			{
				if(0 == i)
				{
					selector = OPEN_CURLEY_BRACKET + OPEN_CURLEY_BRACKET + selections[i] + ALL_SUFFIX + LOGICAL_XOR 
							+ selections[i] + PHOSPHATE_P_SUFFIX + LOGICAL_XOR 
							+ selections[i] + PHOSPHATE_OTHER_SUFFIX + LOGICAL_XOR 
							+ selections[i] + PHOSPHATE_OXYGEN_SUFFIX + LOGICAL_XOR 
							+ selections[i] + SUGAR_SUFFIX + CLOSE_CURLEY_BRACKET + LOGICAL_OR + OPEN_CURLEY_BRACKET  
							+ selections[i] + SUGAR_CARBON1_SUFFIX + CLOSE_CURLEY_BRACKET;
				}
				else
				{
					selector += LOGICAL_OR + OPEN_CURLEY_BRACKET  
					+ selections[i] + ALL_SUFFIX + LOGICAL_XOR  
					+ selections[i] + PHOSPHATE_P_SUFFIX + LOGICAL_XOR  
					+ selections[i] + PHOSPHATE_OTHER_SUFFIX + LOGICAL_XOR 
					+ selections[i] + PHOSPHATE_OXYGEN_SUFFIX + LOGICAL_XOR  
					+ selections[i] + SUGAR_SUFFIX + CLOSE_CURLEY_BRACKET + LOGICAL_OR + OPEN_CURLEY_BRACKET  
					+ selections[i] + SUGAR_CARBON1_SUFFIX + CLOSE_CURLEY_BRACKET;
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
	
	private static String createNucleicSugarSelector(String selection)
	{
		if(null != selection)
		{
			String selector = OPEN_CURLEY_BRACKET + selection + SUGAR_SUFFIX + LOGICAL_OR 
					+ selection + SUGAR_HYDRO_SUFFIX + CLOSE_CURLEY_BRACKET;
			return selector;
		}
		return null;
	}
	
	private static String createNucleicSugarPhosphateSelector(String selection)
	{
		if(null != selection)
		{
			String selector = OPEN_CURLEY_BRACKET + "(" + selection + PHOSPHATE_P_SUFFIX + LOGICAL_OR  //$NON-NLS-1$
					+ selection + PHOSPHATE_OTHER_SUFFIX + LOGICAL_OR 
					+ selection + PHOSPHATE_OXYGEN_SUFFIX + LOGICAL_OR 
					+ selection + SUGAR_HYDRO_SUFFIX + LOGICAL_OR 
					+ selection + SUGAR_SUFFIX + ") and not (" //$NON-NLS-1$
					+ selection + OTHER_OXYGEN_SUFFIX + ")" + CLOSE_CURLEY_BRACKET; //$NON-NLS-1$
			return selector;
		}
		return null;
	}
	
	private static String createNucleicPhosphateSelector(String selection)
	{
		if(null != selection)
		{
			String selector = OPEN_CURLEY_BRACKET + "(" +selection + PHOSPHATE_P_SUFFIX + LOGICAL_OR  //$NON-NLS-1$
					+ selection + PHOSPHATE_OTHER_SUFFIX + LOGICAL_OR 
					+ selection + PHOSPHATE_OXYGEN_SUFFIX + ") and not ("  //$NON-NLS-1$
					+ selection + OTHER_OXYGEN_SUFFIX + LOGICAL_OR 
					+ selection + SUGAR_OXYGEN_SUFFIX + ")" + CLOSE_CURLEY_BRACKET; //$NON-NLS-1$
			return selector;
		}
		return null;
	}
	
	private static String createNucleicBaseSelector(String selection)
	{
		if(null != selection)
		{
			String selector = OPEN_CURLEY_BRACKET + "(" + selection + BASE_C_SUFFIX + LOGICAL_OR  //$NON-NLS-1$
					+ selection + BASE_H_SUFFIX + LOGICAL_OR 
					+ selection + OTHER_OXYGEN_SUFFIX + LOGICAL_OR 
					+ selection + BASE_N_SUFFIX  + ") and not (" //$NON-NLS-1$
					+ selection + SUGAR_SUFFIX  + LOGICAL_OR 
					+ selection + SUGAR_HYDRO_SUFFIX  + LOGICAL_OR 
					+ selection + SUGAR_OXYGEN3_SUFFIX + LOGICAL_OR  
					+ selection + SUGAR_OXYGEN5_SUFFIX + LOGICAL_OR
					+ selection + PHOSPHATE_P_SUFFIX + LOGICAL_OR 
					+ selection + PHOSPHATE_OTHER_SUFFIX + ")" + CLOSE_CURLEY_BRACKET; //$NON-NLS-1$
			return selector;
		}
		return null;
	}
	
	private static String createNucleicBaseSugarSelector(String selection)
	{
		if(null != selection)
		{
			String selector = OPEN_CURLEY_BRACKET + "(" + selection + BASE_C_SUFFIX + LOGICAL_OR  //$NON-NLS-1$
					+ selection + BASE_H_SUFFIX + LOGICAL_OR 
					+ selection + BASE_N_SUFFIX  + LOGICAL_OR 
					+ selection + OTHER_OXYGEN_SUFFIX  + LOGICAL_OR 
					+ selection + SUGAR_SUFFIX  + LOGICAL_OR 
					+ selection + SUGAR_HYDRO_SUFFIX  + LOGICAL_OR 
					+ selection + SUGAR_OXYGEN3_SUFFIX + LOGICAL_OR  
					+ selection + SUGAR_OXYGEN5_SUFFIX + ") and not (" //$NON-NLS-1$
					+ selection + PHOSPHATE_P_SUFFIX + LOGICAL_OR 
					+ selection + PHOSPHATE_OTHER_SUFFIX + ")" + CLOSE_CURLEY_BRACKET; //$NON-NLS-1$
			return selector;
		}
		return null;
	}
	
	private static String createNucleicBasePhosphateSelector(String selection)
	{
		if(null != selection)
		{
			String selector = OPEN_CURLEY_BRACKET + "(" + selection + BASE_C_SUFFIX + LOGICAL_OR  //$NON-NLS-1$
					+ selection + BASE_H_SUFFIX + LOGICAL_OR 
					+ selection + BASE_N_SUFFIX  + LOGICAL_OR 
					+ selection + PHOSPHATE_P_SUFFIX + LOGICAL_OR 
					+ selection + PHOSPHATE_OXYGEN_SUFFIX + LOGICAL_OR 
					+ selection + PHOSPHATE_OTHER_SUFFIX + ") and not (" //$NON-NLS-1$
					+ selection + SUGAR_SUFFIX  + LOGICAL_OR 
					+ selection + SUGAR_HYDRO_SUFFIX  + LOGICAL_OR 
					+ selection + SUGAR_OXYGEN3_SUFFIX + LOGICAL_OR  
					+ selection + SUGAR_OXYGEN5_SUFFIX + ")" + CLOSE_CURLEY_BRACKET; //$NON-NLS-1$
			return selector;
		}
		return null;
	}
	
	private static void setNucleicSelectionVar(Viewer viewer, RenderingInfo ri) throws StarBiochemException
	{
		if((null != getPrb()) && (null != ri) && (null != ri.getSelection()) && (0 != ri.getSelection().length))
		{
			String[] selections = ri.getSelection();
			if((-1 != ri.getFilterOptions()))
			{
				String selectionScript = EMPTY_STRING;
				for(int i=0; selections.length != i; i++)
				{
					selectionScript += getNucleicFilteredSelectionScript(selections[i], ri.getFilterOptions());
					if(i < (selections.length-1))
					{
						selectionScript += " or "; //$NON-NLS-1$
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
	
	private static String getNucleicFilteredSelectionScript(Viewer viewer, RenderingInfo ri) throws StarBiochemException
	{
		if((null != getPrb()) && (null != ri))
		{
			String format = getPrb().getString(NUCLEIC_HYDROGEN_SELECTION_FORMAT);
			setNucleicSelectionVar(viewer, ri);
			if((null != format) && !EMPTY_STRING.equals(format))
			{
				return String.format(format, SELECTION);
			}
		}
		return null;
	}
	
	private static String getNucleicFilteredSelectionScript(String selection, int filterOptions)
	{
		if((null != getPrb()) && (null != selection) && (-1 != filterOptions))
		{
			String nucleic_filtered_selection_string = null;
			String format = getPrb().getString(NUCLEIC_FILTERED_SELECTION_FORMAT); 
			if(null != format)
			{
				if((0 != (filterOptions & RenderingInfoRaiser.BASE_VISIBLE))
				&& (0 != (filterOptions & RenderingInfoRaiser.SUGAR_VISIBLE))
				&& (0 != (filterOptions & RenderingInfoRaiser.PHOSPHATE_VISIBLE)))
				{
					nucleic_filtered_selection_string = String.format(format, selection);
				}
				else if ((0 != (filterOptions & RenderingInfoRaiser.BASE_VISIBLE))
				&& (0 != (filterOptions & RenderingInfoRaiser.SUGAR_VISIBLE)))
				{
					String baseSugar = createNucleicBaseSugarSelector(selection);
					if(null != baseSugar)
					{
						nucleic_filtered_selection_string = String.format(format, baseSugar);
					}
				}
				else if ((0 != (filterOptions & RenderingInfoRaiser.SUGAR_VISIBLE))
				&& (0 != (filterOptions & RenderingInfoRaiser.PHOSPHATE_VISIBLE)))
				{
					String sugarPhosphate = createNucleicSugarPhosphateSelector(selection);
					if(null != sugarPhosphate)
					{
						nucleic_filtered_selection_string = String.format(format, sugarPhosphate);
					}
				}
				else if ((0 != (filterOptions & RenderingInfoRaiser.BASE_VISIBLE))
				&& (0 != (filterOptions & RenderingInfoRaiser.PHOSPHATE_VISIBLE)))
				{
					String basePhosphate = createNucleicBasePhosphateSelector(selection);
					if(null != basePhosphate)
					{
						nucleic_filtered_selection_string = String.format(format, basePhosphate);
					}
				}
				else if(0 != (filterOptions & RenderingInfoRaiser.BASE_VISIBLE))
				{
					String base = createNucleicBaseSelector(selection);
					if(null != base)
					{
						nucleic_filtered_selection_string = String.format(format, base);
					}
				}
				else if(0 != (filterOptions & RenderingInfoRaiser.SUGAR_VISIBLE))
				{
					String sugar = createNucleicSugarSelector(selection);
					if(null != sugar)
					{
						nucleic_filtered_selection_string = String.format(format, sugar);
					}
				}
				else if(0 != (filterOptions & RenderingInfoRaiser.PHOSPHATE_VISIBLE))
				{
					String phosphate = createNucleicPhosphateSelector(selection);
					if(null != phosphate)
					{
						nucleic_filtered_selection_string = String.format(format, phosphate);
					}
				}
			}
			if(null != nucleic_filtered_selection_string)
			{
				return nucleic_filtered_selection_string;
			}
		}
		return null;
	}

	protected static String getAdjustNucleicFilteredSelectionsScript(AdjustSelectionInfo asi, Viewer viewer) throws StarBiochemException
	{
		if((null != getPrb()) && (null != asi))
		{
			String selectionScript = getAdjustNucleicSelectionVar(viewer, asi);
			if((null != selectionScript) && !EMPTY_STRING.equals(selectionScript))
			{
				String format = getPrb().getString(ADJUST_NUCLEIC_FILTERED_SELECTION_FORMAT);
				if((null != format) && !EMPTY_STRING.equals(format))
				{
					return String.format(format, selectionScript);
				}
			}
		}
		return null;
	}
	
	private static String getAdjustNucleicFilteredSelectionScript(String selection, int filterOptions)
	{
		if((null != getPrb()) && (null != selection) && (-1 != filterOptions))
		{
			String format = getPrb().getString(NUCLEIC_FILTERED_SELECTION_FORMAT);
			if(null != format)
			{
				if((0 != (filterOptions & RenderingInfoRaiser.BASE_VISIBLE))
				&& (0 != (filterOptions & RenderingInfoRaiser.SUGAR_VISIBLE))
				&& (0 != (filterOptions & RenderingInfoRaiser.PHOSPHATE_VISIBLE)))
				{
					return String.format(format, selection);
				}
				else if ((0 != (filterOptions & RenderingInfoRaiser.BASE_VISIBLE))
				&& (0 != (filterOptions & RenderingInfoRaiser.SUGAR_VISIBLE)))
				{
					String base_sugar_selector = createNucleicBaseSugarSelector(selection);
					if((null != base_sugar_selector) && !EMPTY_STRING.equals(base_sugar_selector))
					{
						return String.format(format, base_sugar_selector);
					}
				}
				else if ((0 != (filterOptions & RenderingInfoRaiser.SUGAR_VISIBLE))
				&& (0 != (filterOptions & RenderingInfoRaiser.PHOSPHATE_VISIBLE)))
				{
					String sugar_phosphate_selector = createNucleicSugarPhosphateSelector(selection);
					if((null != sugar_phosphate_selector) && !EMPTY_STRING.equals(sugar_phosphate_selector))
					{
						return String.format(format, sugar_phosphate_selector);
					}
				}
				else if ((0 != (filterOptions & RenderingInfoRaiser.BASE_VISIBLE))
				&& (0 != (filterOptions & RenderingInfoRaiser.PHOSPHATE_VISIBLE)))
				{
					String base_phosphate_selector = createNucleicBasePhosphateSelector(selection);
					if((null != base_phosphate_selector) && !EMPTY_STRING.equals(base_phosphate_selector))
					{
						return String.format(format, base_phosphate_selector);
					}
				}
				else if(0 != (filterOptions & RenderingInfoRaiser.BASE_VISIBLE))
				{
					String base_selector = createNucleicBaseSelector(selection);
					if((null != base_selector) && !EMPTY_STRING.equals(base_selector))
					{
						return String.format(format, base_selector);
					}
				}
				else if(0 != (filterOptions & RenderingInfoRaiser.SUGAR_VISIBLE))
				{
					String sugar_selector = createNucleicSugarSelector(selection);
					if((null != sugar_selector) && !EMPTY_STRING.equals(sugar_selector))
					{
						return String.format(format, sugar_selector);
					}
				}
				else if(0 != (filterOptions & RenderingInfoRaiser.PHOSPHATE_VISIBLE))
				{
					String phosphate_selector = createNucleicPhosphateSelector(selection);
					if((null != phosphate_selector) && !EMPTY_STRING.equals(phosphate_selector))
					{
						return String.format(format, phosphate_selector);
					}
				}
			}
		}
		return null;
	}
	
	private static String getAdjustNucleicSelectionVar(Viewer viewer, AdjustSelectionInfo asi) throws StarBiochemException
	{
		if((null != getPrb()) && (null != asi) && (null != asi.getSelections()) && (0 != asi.getSelections().length))
		{
			String[] selections = asi.getSelections();
			if(null != selections)
			{
				if((-1 != asi.getFilterOptions()))
				{
					String selectionScript = EMPTY_STRING;
					for(int i=0; selections.length != i; i++)
					{
						selectionScript += getAdjustNucleicFilteredSelectionScript(selections[i], asi.getFilterOptions());
						if(i < (selections.length-1))
						{
							selectionScript += " or "; //$NON-NLS-1$
						}	
					}
					if(!selectionScript.equals(EMPTY_STRING))
					{
						String adjustNucleicVar = "adjustNucleicVar"; //$NON-NLS-1$
						String format = getPrb().getString(SET_VARIABLE_FORMAT);
						viewer.script(String.format(format, adjustNucleicVar, selectionScript));
						return adjustNucleicVar;
					}
				}
			}
		}
		return null;
	}

}
