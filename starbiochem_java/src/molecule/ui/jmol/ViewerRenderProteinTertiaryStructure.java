package molecule.ui.jmol;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.PropertyResourceBundle;

import molecule.interfaces.AdjustSelectionInfo;
import molecule.interfaces.Bond;
import molecule.interfaces.RenderingInfo;
import molecule.ui.signal.RenderingInfoRaiser;
import utils.TertiaryStringsAndColors;
import app.StarBiochemException;

public class ViewerRenderProteinTertiaryStructure 
{
	private static final String EMPTY_STRING = ""; //$NON-NLS-1$
	
	private static final String JMOL = "JMOL"; //$NON-NLS-1$
    private static final String OPEN_CURLEY_BRACKET = "{"; //$NON-NLS-1$
    private static final String CLOSE_CURLEY_BRACKET = "}"; //$NON-NLS-1$
    private static final String OPEN_PAREN = "("; //$NON-NLS-1$
    private static final String CLOSE_PAREN = ")"; //$NON-NLS-1$
    private static final String COMMA = ","; //$NON-NLS-1$

    private static final String LOGICAL_OR = " or "; //$NON-NLS-1$
	
	private static final String SELECTOR_PROTEIN = "PROTEIN"; //$NON-NLS-1$
    private static final String SELECTOR_BACKBONE = "BACKBONE"; //$NON-NLS-1$
    private static final String SELECTOR_SIDECHAIN = "SIDECHAIN"; //$NON-NLS-1$
    
    private static final String SET_VARIABLE_FORMAT = "set_variable_format"; //$NON-NLS-1$
    private static String SELECTION = "selection"; //$NON-NLS-1$
	
    private static final String BOND_MODE_AND = "bond_mode_and"; //$NON-NLS-1$
    private static final String HBOND_SIZE_FORMAT = "hbond_size_format"; //$NON-NLS-1$
	private static final String HBOND_TRANSLUCENCY_FORMAT = "hbond_translucency_format"; //$NON-NLS-1$
    private static final String SSBOND_SIZE_FORMAT = "ssbond_size_format"; //$NON-NLS-1$
	private static final String SSBOND_TRANSLUCENCY_FORMAT = "ssbond_translucency_format"; //$NON-NLS-1$
	
	private static final String PROTEIN_TERTIARY_PROPERTIES_FORMAT = "protein_tertiary_properties_format"; //$NON-NLS-1$
	private static final String PROTEIN_TERTIARY_PEPTIDE_BOND_PROPERTIES_FORMAT = "protein_tertiary_peptide_bond_properties_format"; //$NON-NLS-1$
	private static final String PROTEIN_TERTIARY_FILTERED_SELECTOR_FORMAT = "protein_tertiary_filtered_selector_format"; //$NON-NLS-1$
	private static final String PROTEIN_TERTIARY_FILTERED_BACKBONE_SELECTOR_FORMAT = "protein_tertiary_filtered_backbone_selector_format"; //$NON-NLS-1$
	private static final String PROTEIN_TERTIARY_FILTERED_SIDECHAIN_SELECTOR_FORMAT = "protein_tertiary_filtered_sidechain_selector_format"; //$NON-NLS-1$
	private static final String PROTEIN_TERTIARY_FILTERED_SELECTION_FORMAT = "protein_tertiary_filtered_selection_format"; //$NON-NLS-1$
	private static final String PROTEIN_TERTIARY_SELECTION_FORMAT = "protein_tertiary_selection_format"; //$NON-NLS-1$
	
	private static final String PROTEIN_TERTIARY_HBOND_SELECTION_FORMAT = "protein_tertiary_hbond_selection_format"; //$NON-NLS-1$

	private static final String ADJUST_PROTEIN_TERTIARY_FILTERED_SELECTION_FORMAT = "adjust_protein_tertiary_filtered_selection_format"; //$NON-NLS-1$

	private static final String PROTEIN_PRIMARY_FILTERED_BACKBONE_SELECTOR_FORMAT = "protein_primary_filtered_backbone_selector_format"; //$NON-NLS-1$
	private static final String PROTEIN_PRIMARY_FILTERED_SIDECHAIN_SELECTOR_FORMAT = "protein_primary_filtered_sidechain_selector_format"; //$NON-NLS-1$
	private static final String PROTEIN_PRIMARY_FILTERED_SELECTION_FORMAT = "protein_primary_filtered_selection_format"; //$NON-NLS-1$
	private static final String PROTEIN_PRIMARY_FILTERED_SELECTOR_FORMAT = "protein_primary_filtered_selector_format"; //$NON-NLS-1$

	private static final String bondFormat = getPrb().getString(BOND_MODE_AND);

	transient private static PropertyResourceBundle prb = null;
	private static PropertyResourceBundle getPrb()
	{
		if(null == prb)
		{
			InputStream inputStream = ViewerRenderProteinTertiaryStructure.class.getResourceAsStream("./JmolScripts/StarBiochem.properties"); //$NON-NLS-1$
			if( inputStream == null )
			{
				inputStream = ViewerRenderProteinTertiaryStructure.class.getResourceAsStream("/JmolScripts/StarBiochem.properties" );  //$NON-NLS-1$
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
	
	protected static void renderProteinTertiaryStructure(Viewer viewer, RenderingInfo ri) throws StarBiochemException
	{
		if((null != viewer) && (null != ri))
		{
			int filterOptions = ri.getFilterOptions();
			if(0 != (filterOptions
				& (RenderingInfoRaiser.BACKBONE_VISIBLE | RenderingInfoRaiser.SIDECHAIN_VISIBLE)
				& (RenderingInfoRaiser.NONPOLAR_HYDROPHOBIC_VISIBLE
				| RenderingInfoRaiser.POLAR_HYDROPHILIC_VISIBLE
				| RenderingInfoRaiser.NOTCHARGED_NEUTRAL_VISIBLE
				| RenderingInfoRaiser.POSITIVELYCHARGED_BASIC_VISIBLE
				| RenderingInfoRaiser.NEGATIVELYCHARGED_ACIDIC_VISIBLE
				| RenderingInfoRaiser.BURIED_VISIBLE
				| RenderingInfoRaiser.SURFACE_VISIBLE
				| RenderingInfoRaiser.TERTIARY_NONE
				| RenderingInfoRaiser.BACKBONE_VISIBLE
				| RenderingInfoRaiser.SIDECHAIN_VISIBLE
				)))
			{
				boolean isRenderAtom = true;
				String propertiesScript = getProteinTertiaryPropertiesScript(ri);
				ArrayList<String> filteredSelectionScript = getProteinTertiaryFilteredSelectionScripts(ri, viewer, isRenderAtom);
				if((null != filteredSelectionScript) && !EMPTY_STRING.equals(filteredSelectionScript))
				{
					String script = EMPTY_STRING;
					for(int i = 0; filteredSelectionScript.size() != i; i++)
					{
						script += filteredSelectionScript.get(i) + propertiesScript;
					}
					script += "subset;"; //$NON-NLS-1$
					viewer.script(String.format(bondFormat, script));
				}
			}
		}
	}
	
	protected static void renderProteinTertiaryCovalentBonds(Viewer viewer, RenderingInfo ri) throws StarBiochemException
	{
		if((null != viewer) && (null != ri))
		{
			int filterOptions = ri.getFilterOptions();
			if(0 != (filterOptions
				& (RenderingInfoRaiser.BACKBONE_VISIBLE | RenderingInfoRaiser.SIDECHAIN_VISIBLE)
				& (RenderingInfoRaiser.NONPOLAR_HYDROPHOBIC_VISIBLE
				| RenderingInfoRaiser.POLAR_HYDROPHILIC_VISIBLE
				| RenderingInfoRaiser.NOTCHARGED_NEUTRAL_VISIBLE
				| RenderingInfoRaiser.POSITIVELYCHARGED_BASIC_VISIBLE
				| RenderingInfoRaiser.NEGATIVELYCHARGED_ACIDIC_VISIBLE
				| RenderingInfoRaiser.BURIED_VISIBLE
				| RenderingInfoRaiser.SURFACE_VISIBLE
				| RenderingInfoRaiser.TERTIARY_NONE
				| RenderingInfoRaiser.BACKBONE_VISIBLE
				| RenderingInfoRaiser.SIDECHAIN_VISIBLE
				)))
			{
				boolean isRenderAtom = false;
				String peptideBondProperties = getPeptideBondPropertiesScript(ri);
				String ssbondProperties = getSSBondPropertiesScript(ri);
				ArrayList<String> filteredSelectionScript = getProteinTertiaryFilteredSelectionScripts(ri, viewer, isRenderAtom);
				if((null != filteredSelectionScript) && !EMPTY_STRING.equals(filteredSelectionScript))
				{
					String script = EMPTY_STRING;
					for(int i = 0; filteredSelectionScript.size() != i; i++)
					{
						script += filteredSelectionScript.get(i) + peptideBondProperties + ssbondProperties;
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
	
	protected static void renderProteinTertiaryHbonds(Viewer viewer, RenderingInfo ri) throws StarBiochemException
	{
		if((null != viewer) && (null != ri))
		{
			int filterOptions = ri.getFilterOptions();
			if(0 != (filterOptions
				& (RenderingInfoRaiser.BACKBONE_VISIBLE | RenderingInfoRaiser.SIDECHAIN_VISIBLE)
				& (RenderingInfoRaiser.NONPOLAR_HYDROPHOBIC_VISIBLE
				| RenderingInfoRaiser.POLAR_HYDROPHILIC_VISIBLE
				| RenderingInfoRaiser.NOTCHARGED_NEUTRAL_VISIBLE
				| RenderingInfoRaiser.POSITIVELYCHARGED_BASIC_VISIBLE
				| RenderingInfoRaiser.NEGATIVELYCHARGED_ACIDIC_VISIBLE
				| RenderingInfoRaiser.BURIED_VISIBLE
				| RenderingInfoRaiser.SURFACE_VISIBLE
				| RenderingInfoRaiser.TERTIARY_NONE
				| RenderingInfoRaiser.BACKBONE_VISIBLE
				| RenderingInfoRaiser.SIDECHAIN_VISIBLE
				)))
			{
				String hbondsProperties = getHBondPropertiesScript(ri);
				if(null != hbondsProperties && !EMPTY_STRING.equals(hbondsProperties))
				{
					ArrayList<String> filteredHbondSelection = getProteinTertiaryFilteredHbondSelectionArray(ri);
					if((null != filteredHbondSelection) && (!filteredHbondSelection.isEmpty()))
					{
						String string = EMPTY_STRING;
						Iterator<String> scriptIterator = filteredHbondSelection.iterator();
						while(scriptIterator.hasNext())
						{
							string += scriptIterator.next();
							if((null != string) && (0 != string.length()))
							{
								string += hbondsProperties + "\n"; //$NON-NLS-1$
							}
						}
						String script = (String.format(bondFormat, string));
						viewer.script(script);
					}
				}
			}
		}
	}
	
	private static String getProteinTertiaryPropertiesScript(RenderingInfo ri)
	{
		if((null != getPrb()) && (null != ri))
		{
			int filterOptions = ri.getFilterOptions();
			if(-1 != filterOptions)
			{
				String color = JMOL;
				TertiaryStringsAndColors tsac = TertiaryStringsAndColors.getDefaultTertiaryStringsAndColors();
				if(null != tsac)
				{
					if(0 != (filterOptions & RenderingInfoRaiser.NONPOLAR_HYDROPHOBIC_VISIBLE))
					{
						color = tsac.getNonpolarHydrophobicColor();
					}
					else if(0 != (filterOptions & RenderingInfoRaiser.POLAR_HYDROPHILIC_VISIBLE))
					{
						color = tsac.getPolarHydrophilicColor();
					}
					else if(0 != (filterOptions & RenderingInfoRaiser.BURIED_VISIBLE))
					{
						color = tsac.getBuriedColor();
					}
					else if(0 != (filterOptions & RenderingInfoRaiser.SURFACE_VISIBLE))
					{
						color = tsac.getSurfaceColor();
					}
					else if(0 != (filterOptions & RenderingInfoRaiser.POSITIVELYCHARGED_BASIC_VISIBLE))
					{
						color = tsac.getPositivelyChargedColor();
					}
					else if(0 != (filterOptions & RenderingInfoRaiser.NEGATIVELYCHARGED_ACIDIC_VISIBLE))
					{
						color = tsac.getNegativelyChargedColor();
					}
					else if(0 != (filterOptions & RenderingInfoRaiser.NOTCHARGED_NEUTRAL_VISIBLE))
					{
						color = tsac.getNeutralColor();
					}
				}

				String format = getPrb().getString(PROTEIN_TERTIARY_PROPERTIES_FORMAT);
				if((null != format) && !EMPTY_STRING.equals(format))
				{
					String size = (-1 == ri.getSize()) ? EMPTY_STRING : Integer.toString(ri.getSize());
					String translucency = (-1 == ri.getTranslucency()) ? EMPTY_STRING : Float.toString(ri.getTranslucency()/100.f);
					return String.format(format, color, translucency, size);
				}
			}
		}
		return EMPTY_STRING;
	}
	
	private static ArrayList<String> getProteinTertiaryFilteredSelectionScripts(RenderingInfo ri, Viewer viewer, boolean isRenderAtom) throws StarBiochemException
	{
		if((null != getPrb()) && (null != ri) && (null != ri.getSelection()))
		{
			ArrayList<String> proteinTertiaryFilteredSelectionScripts = new ArrayList<String>();
			String type = getProteinTertiaryType(ri);
			if((null != type) && !EMPTY_STRING.equals(type))
			{
				String format = getProteinTertiaryFormat(ri);
				if((null != format) && !EMPTY_STRING.equals(format))
				{
					String[] filteredSelection = filterProteinTertiarySelections(ri.getSelection(), format, type);
					String[] selectionVars = getSelectionVariables(filteredSelection, viewer, isRenderAtom);
					ArrayList<String> selectors = createTertiaryFilteredSelectors(selectionVars, format);
					if((null != selectors) && !selectors.isEmpty())
					{
						Iterator<String> selectorIterator = selectors.iterator();
						while(selectorIterator.hasNext())
						{
							String selector = selectorIterator.next();
							if((null != selector) && !EMPTY_STRING.equals(selector))
							{
								format = getPrb().getString(PROTEIN_TERTIARY_FILTERED_SELECTION_FORMAT);
								proteinTertiaryFilteredSelectionScripts.add(String.format(format, selector));
							}
						}
						if((null != proteinTertiaryFilteredSelectionScripts) && !proteinTertiaryFilteredSelectionScripts.isEmpty())
						{
							return proteinTertiaryFilteredSelectionScripts;
						}
					}
				}
			}
			else
			{
				return getProteinPrimaryFilteredSelectionScript(ri, viewer, isRenderAtom);
			}
		}
		return null;
	}

	private static String getProteinTertiaryFormat(RenderingInfo ri)
	{
		String format = EMPTY_STRING;
		int filterOptions = ri.getFilterOptions();
		if(-1 != filterOptions)
		{
			if((0 != (filterOptions & RenderingInfoRaiser.BACKBONE_VISIBLE)) && (0 != (filterOptions & RenderingInfoRaiser.SIDECHAIN_VISIBLE)))
			{
				format = getPrb().getString(PROTEIN_TERTIARY_FILTERED_SELECTOR_FORMAT);
			}
			else if((0 != (filterOptions & RenderingInfoRaiser.BACKBONE_VISIBLE)) && (0 == (filterOptions & RenderingInfoRaiser.SIDECHAIN_VISIBLE)))
			{
				format = getPrb().getString(PROTEIN_TERTIARY_FILTERED_BACKBONE_SELECTOR_FORMAT);
			}
			else if((0 == (filterOptions & RenderingInfoRaiser.BACKBONE_VISIBLE)) && (0 != (filterOptions & RenderingInfoRaiser.SIDECHAIN_VISIBLE)))
			{
				format = getPrb().getString(PROTEIN_TERTIARY_FILTERED_SIDECHAIN_SELECTOR_FORMAT);
			}
		}
		return format;
	}
	
	private static String getProteinTertiaryType(RenderingInfo ri)
	{
		String type = EMPTY_STRING;
		if((null != getPrb()) && (null != ri) && (null != ri.getSelection()))
		{
			int filterOptions = ri.getFilterOptions();
			if(-1 != filterOptions)
			{
				TertiaryStringsAndColors tsac = TertiaryStringsAndColors.getDefaultTertiaryStringsAndColors();
				if(null != tsac)
				{
					if(0 != (filterOptions & RenderingInfoRaiser.NONPOLAR_HYDROPHOBIC_VISIBLE))
					{
						type = tsac.getNonpolarHydrophobicString();
					}
					else if(0 != (filterOptions & RenderingInfoRaiser.POLAR_HYDROPHILIC_VISIBLE))
					{
						type = tsac.getPolarHydrophilicString();
					}
					else if(0 != (filterOptions & RenderingInfoRaiser.BURIED_VISIBLE))
					{
						type = tsac.getBuriedString();
					}
					else if(0 != (filterOptions & RenderingInfoRaiser.SURFACE_VISIBLE))
					{
						type = tsac.getSurfaceString();
					}
					else if(0 != (filterOptions & RenderingInfoRaiser.POSITIVELYCHARGED_BASIC_VISIBLE))
					{
						type = tsac.getPositivelyChargedString();
					}
					else if(0 != (filterOptions & RenderingInfoRaiser.NEGATIVELYCHARGED_ACIDIC_VISIBLE))
					{
						type = tsac.getNegativelyChargedString();
					}
					else if(0 != (filterOptions & RenderingInfoRaiser.NOTCHARGED_NEUTRAL_VISIBLE))
					{
						type = tsac.getNeutralString();
					}
				}
			}
		}
		return type;
	}

	private static String[] filterProteinTertiarySelections(String[] selections, String format, String tertiaryType)
	{
		ArrayList<String> filteredSelections = new ArrayList<String>();
		if ((null != selections) && (null != format) && !EMPTY_STRING.equals(format) && (null != tertiaryType) && !EMPTY_STRING.equals(tertiaryType))
		{
			TertiaryStringsAndColors tsac = TertiaryStringsAndColors.getDefaultTertiaryStringsAndColors();
			if(null != tsac)
			{
				for (int i = 0; selections.length != i; i++)
				{
					String residue = selections[i];
					residue = residue.substring(1,4);
					if(tsac.isAcidic(residue) && tertiaryType.equals(tsac.getNegativelyChargedString()))
					{
						filteredSelections.add(selections[i]);
					}
					else if(tsac.isBasic(residue) && tertiaryType.equals(tsac.getPositivelyChargedString()))
					{
						filteredSelections.add(selections[i]);
					}
					else if(tsac.isHydrophobic(residue) && tertiaryType.equals(tsac.getNonpolarHydrophobicString()))
					{
						filteredSelections.add(selections[i]);
					}
					else if(tsac.isHydrophilic(residue) && tertiaryType.equals(tsac.getPolarHydrophilicString()))
					{
						filteredSelections.add(selections[i]);
					}
					else if(tsac.isBuried(residue) && tertiaryType.equals(tsac.getBuriedString()))
					{						
						filteredSelections.add(selections[i]);
					}
					else if(tsac.isSurface(residue) && tertiaryType.equals(tsac.getSurfaceString()))
					{
						filteredSelections.add(selections[i]);
					}
					else if(tsac.isNeutral(residue) && tertiaryType.equals(tsac.getNeutralString()))
					{
						filteredSelections.add(selections[i]);
					}
				}
			}
		}
		return filteredSelections.toArray(new String[0]);
	}

	
	private static String[] getSelectionVariables(String[] selections, Viewer viewer, boolean isRenderAtom) throws StarBiochemException
	{
		if((null != getPrb()) && (null != selections))
		{
			ArrayList<String> selectionArray = new ArrayList<String>();
			String format = getPrb().getString(PROTEIN_TERTIARY_SELECTION_FORMAT);
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
						viewer.script(String.format(format, SELECTION, selector));
						selectionArray.add(SELECTION);
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
		return null;
	}
	
	private static String[] getSelectionByChain(String[] selections, Viewer viewer, String format) throws StarBiochemException
	{
		if((null != selections) && (!selections.equals(EMPTY_STRING)))
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
	private static ArrayList<String> createTertiaryFilteredSelectors(String[] selections, String format)
	{
		ArrayList<String> filteredSelections = new ArrayList<String>();
		if ((null != selections) && (null != format) && !EMPTY_STRING.equals(format))
		{
			for (int i = 0; selections.length != i; i++)
			{
				String filteredSelection = String.format(format, selections[i]);
				filteredSelections.add(filteredSelection);
			}
		}
		return filteredSelections;
	}
	
	private static String getPeptideBondPropertiesScript(RenderingInfo ri)
	{
		String peptideBondTransluceny = (-1 == ri.getBondTranslucency()) ? EMPTY_STRING : Float.toString(ri.getBondTranslucency()/100.f);
		String format = getPrb().getString(PROTEIN_TERTIARY_PEPTIDE_BOND_PROPERTIES_FORMAT);
		
		return String.format(format, peptideBondTransluceny);
	}
	
	private static String getSSBondPropertiesScript(RenderingInfo ri)
	{
		if(null != ri && 0 != ri.getSelection().length)
		{
			int size = ri.getSSbondSize();
			double ssbondSize = (size * 0.01);
			String ssBondSizeScript = ((-1 == size)  ? EMPTY_STRING : (String.format(getPrb().getString(SSBOND_SIZE_FORMAT),Double.toString(ssbondSize))));
			
			String ssBondTranslucency = Float.toString(ri.getSSbondTranslucency()/100.f);
			String ssBondTranslucencyScript = ((-1 == ri.getSSbondTranslucency())  ? EMPTY_STRING : (String.format(getPrb().getString(SSBOND_TRANSLUCENCY_FORMAT),ssBondTranslucency)));
			String ssBondPropertiesScript = "set bondModeOr False; " + ssBondSizeScript + ssBondTranslucencyScript + "set bondModeOr TRUE; "; //$NON-NLS-1$ //$NON-NLS-2$
			return ssBondPropertiesScript;
		}
		return EMPTY_STRING;
	}
	
	private static String getHBondPropertiesScript(RenderingInfo ri)
	{
		int size = ri.getHBondSize();
		double hbondSize = (size * 0.003);
		String hBondSizeScript = ((-1 == ri.getHBondSize())  ? EMPTY_STRING : (String.format(getPrb().getString(HBOND_SIZE_FORMAT),Double.toString(hbondSize))));

		String hBondTranslucency = Float.toString(ri.getHBondTranslucency()/100.f);
		String hBondTranslucencyScript = ((-1 == ri.getHBondTranslucency())  ? EMPTY_STRING : (String.format(getPrb().getString(HBOND_TRANSLUCENCY_FORMAT),hBondTranslucency)));
		String hBondScript = hBondSizeScript + hBondTranslucencyScript;

		return hBondScript;
	}
	
	private static String[] createProteinTertiaryHbondSelection(RenderingInfo ri)
	{
		String[] tertiaryHbondSelection = null;
		if((null != ri) && (null != ri.getSelection()) && (0 != ri.getSelection().length))
		{
			ArrayList<String> tertiaryHbondSelectionArray = new ArrayList<String>();

			String[] selections = ri.getSelection();
			if((null != ri.getStructureHbonds()) && (0 != ri.getStructureHbonds().length))
			{
				Bond[] hbonds = ri.getStructureHbonds();
				String initSelection = OPEN_CURLEY_BRACKET + OPEN_PAREN;
				String endSelection = CLOSE_PAREN +CLOSE_CURLEY_BRACKET;
				
				for(int i = 0; hbonds.length != i; i++)
				{
					String atom1 = hbonds[i].getAtom1();
					String atom2 = hbonds[i].getAtom2();
					int count = 0;
					
					for(int j = 0; selections.length != j; j++)
					{
						String mySelection = selections[j].toString();
						if(atom1.startsWith(mySelection))
						{
							count++;
						}
						if(atom2.startsWith(mySelection))
						{
							count++;
						}
					}
					if(count == 2)
					{
						String hbond = (initSelection + atom1 + COMMA + atom2 + endSelection);
						tertiaryHbondSelectionArray.add(hbond);
					}
				}
				if(!tertiaryHbondSelectionArray.isEmpty())
				{
					tertiaryHbondSelection = tertiaryHbondSelectionArray.toArray(new String[0]);
				}
			}
		}
		return tertiaryHbondSelection;
	}
	
	private static ArrayList<String> getProteinTertiaryFilteredHbondSelectionArray(RenderingInfo ri)
	{
		ArrayList<String> tertiaryFilteredHbondSelectionArray = new ArrayList<String>();
		
		String[] tertiaryHbondSelection = createProteinTertiaryHbondSelection(ri);
		if(null != tertiaryHbondSelection && 0 != tertiaryHbondSelection.length)
		{
			int filterOptions = ri.getFilterOptions();
			if(-1 != filterOptions)
			{
				TertiaryStringsAndColors tsac = TertiaryStringsAndColors.getDefaultTertiaryStringsAndColors();
				if(null != tsac)
				{
					String type = null;				
					if(0 != (filterOptions & RenderingInfoRaiser.NONPOLAR_HYDROPHOBIC_VISIBLE))
					{
						type = tsac.getNonpolarHydrophobicString();
					}
					else if(0 != (filterOptions & RenderingInfoRaiser.POLAR_HYDROPHILIC_VISIBLE))
					{
						type = tsac.getPolarHydrophilicString();
					}
					else if(0 != (filterOptions & RenderingInfoRaiser.BURIED_VISIBLE))
					{
						type = tsac.getBuriedString();
					}
					else if(0 != (filterOptions & RenderingInfoRaiser.SURFACE_VISIBLE))
					{
						type = tsac.getSurfaceString();
					}
					else if(0 != (filterOptions & RenderingInfoRaiser.POSITIVELYCHARGED_BASIC_VISIBLE))
					{
						type = tsac.getPositivelyChargedString();
					}
					else if(0 != (filterOptions & RenderingInfoRaiser.NEGATIVELYCHARGED_ACIDIC_VISIBLE))
					{
						type = tsac.getNegativelyChargedString();
					}
					else if(0 != (filterOptions & RenderingInfoRaiser.NOTCHARGED_NEUTRAL_VISIBLE))
					{
						type = tsac.getNeutralString();
					}
					else if(0 != (filterOptions & RenderingInfoRaiser.TERTIARY_NONE))
					{
						type = "PROTEIN"; //$NON-NLS-1$
					}
					
					if(null != type)
					{
						String format = getPrb().getString(PROTEIN_TERTIARY_HBOND_SELECTION_FORMAT);
						if((null != format) && !EMPTY_STRING.equals(format))
						{	
							type = "and " + type + CLOSE_CURLEY_BRACKET; //$NON-NLS-1$
							for(int i = 0; tertiaryHbondSelection.length != i; i++)
							{
								String script = String.format(format, tertiaryHbondSelection[i].toString(), type);
								tertiaryFilteredHbondSelectionArray.add(script);
							}
						}
					}
				}
			}
		}	
		return tertiaryFilteredHbondSelectionArray;
	}

	private static String[] filterAdjustTertiarySelections(AdjustSelectionInfo asi, Viewer viewer)
	{
		if(null != asi && null != asi.getSelections() && asi.getSelections().length != 0)
		{
			ArrayList<String> filteredSelections = new ArrayList<String>();
			String[] selections = asi.getSelections();
			int filterOptions = asi.getFilterOptions();
			if(-1 != filterOptions)
			{
				TertiaryStringsAndColors tsac = TertiaryStringsAndColors.getDefaultTertiaryStringsAndColors();
				if(null != tsac)
				{
					for (int i = 0; selections.length != i; i++)
					{
						String residue = selections[i];
						residue = residue.substring(1,4);
						if(tsac.isAcidic(residue) && (0 != (filterOptions & RenderingInfoRaiser.NEGATIVELYCHARGED_ACIDIC_VISIBLE)))
						{
							filteredSelections.add(selections[i]);
						}
						else if(tsac.isBasic(residue) && (0 != (filterOptions & RenderingInfoRaiser.POSITIVELYCHARGED_BASIC_VISIBLE)))
						{
							filteredSelections.add(selections[i]);
						}
						else if(tsac.isHydrophobic(residue) && (0 != (filterOptions & RenderingInfoRaiser.NONPOLAR_HYDROPHOBIC_VISIBLE)))
						{
							filteredSelections.add(selections[i]);
						}
						else if(tsac.isHydrophilic(residue) && (0 != (filterOptions & RenderingInfoRaiser.POLAR_HYDROPHILIC_VISIBLE)))
						{
							filteredSelections.add(selections[i]);
						}
						else if(tsac.isBuried(residue) && (0 != (filterOptions & RenderingInfoRaiser.BURIED_VISIBLE)))
						{						
							filteredSelections.add(selections[i]);
						}
						else if(tsac.isSurface(residue) && (0 != (filterOptions & RenderingInfoRaiser.SURFACE_VISIBLE)))
						{
							filteredSelections.add(selections[i]);
						}
						else if(tsac.isNeutral(residue) && (0 != (filterOptions & RenderingInfoRaiser.NOTCHARGED_NEUTRAL_VISIBLE)))
						{
							filteredSelections.add(selections[i]);
						}
						else if(0 != (filterOptions & RenderingInfoRaiser.TERTIARY_NONE))
						{
							filteredSelections.add(selections[i]);
						}
					}
				}
			}
			String[] selection = filteredSelections.toArray(new String[0]);
			if((null != selection) && (0 != selection.length))
			{
				return selection; 
			}
		}
		return null;
	}

	protected static String getAdjustProteinTertiarySelection(AdjustSelectionInfo asi, Viewer viewer) throws StarBiochemException
	{
		String filteredSelectionScript = ""; //$NON-NLS-1$
		if((null != asi) && (null != asi.getSelections()))
		{
			if(null != getPrb())
			{
				String format = getPrb().getString(ADJUST_PROTEIN_TERTIARY_FILTERED_SELECTION_FORMAT);
				
				if(null != format)
				{
					int filterOptions = asi.getFilterOptions();
					if(-1 != filterOptions)
					{
						String[] selections = filterAdjustTertiarySelections(asi, viewer);
						if(null != selections && 0 != selections.length)
						{
							String selection = getAdjustSelectionVariables(selections, viewer);
							if((null != selection) && !selection.equals(EMPTY_STRING))
							{
								String temp = null;
								if((0 != (filterOptions & RenderingInfoRaiser.BACKBONE_VISIBLE)) && (0 != (filterOptions & RenderingInfoRaiser.SIDECHAIN_VISIBLE)))
								{
									temp = String.format(format, selection, SELECTOR_PROTEIN);
								}
								else if((0 != (filterOptions & RenderingInfoRaiser.BACKBONE_VISIBLE)) && (0 == (filterOptions & RenderingInfoRaiser.SIDECHAIN_VISIBLE)))
								{
									temp = String.format(format, selection, SELECTOR_BACKBONE);
								}
								else if((0 == (filterOptions & RenderingInfoRaiser.BACKBONE_VISIBLE)) && (0 != (filterOptions & RenderingInfoRaiser.SIDECHAIN_VISIBLE)))
								{
									temp = String.format(format, selection, SELECTOR_SIDECHAIN);
								}
								if((0 == (filterOptions & RenderingInfoRaiser.BACKBONE_VISIBLE)) && (0 == (filterOptions & RenderingInfoRaiser.SIDECHAIN_VISIBLE)))
								{
									temp = String.format(format, selection, " not " + SELECTOR_BACKBONE + " and not " + SELECTOR_SIDECHAIN); //$NON-NLS-1$ //$NON-NLS-2$
								}
								if((null != temp) && (!EMPTY_STRING.equals(temp)))
								{
									filteredSelectionScript = temp; 
								}
							}
						}
					}
				}
			}
		}
		return filteredSelectionScript;
	}

	private static String getAdjustSelectionVariables(String[] selections, Viewer viewer) throws StarBiochemException
	{
		if((null != getPrb()) && (null != selections))
		{
			String format = getPrb().getString(PROTEIN_TERTIARY_SELECTION_FORMAT);
			String selector = EMPTY_STRING;
			String selection = "adjustTertiarySelection"; //$NON-NLS-1$
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
					viewer.script(String.format(format, selection, selector));
					return selection;
				}
			}
		}
		return null;
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
					String[] selectionVars = getSelectionVariables(ri.getSelection(), viewer, isRenderAtom);
					if((null != selectionVars) && (0 != selectionVars.length))
					{
						ArrayList<String> selectors = createPrimaryFilteredSelectors(format, selectionVars);
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
		}
		return null;
	}

		private static ArrayList<String> createPrimaryFilteredSelectors(String format, String[] selections)
		{
			if ((null != selections) && (null != format))
			{
				ArrayList<String> chainSelectors = new ArrayList<String>();
				String selector = EMPTY_STRING;
				for (int i = 0; selections.length != i; i++)
				{
					selector = String.format(format, selections[i]);
					chainSelectors.add(selector);
				}
				if(!chainSelectors.isEmpty())
				{
					return chainSelectors;
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
	
}
