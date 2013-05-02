package molecule.ui.jmol;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PropertyResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import molecule.interfaces.AdjustSelectionInfo;
import molecule.interfaces.Bond;
import molecule.interfaces.ChainColors;
import molecule.interfaces.RenderingInfo;
import molecule.ui.signal.RenderingInfoRaiser;
import utils.JmolColors;
import app.StarBiochemException;

public class ViewerRenderProteinQuaternaryStructure 
{
	private static final String EMPTY_STRING = ""; //$NON-NLS-1$
    private static final String LOGICAL_AND = " and "; //$NON-NLS-1$

    private static final String COLON = ":"; //$NON-NLS-1$
    private static final String OPEN_CURLEY_BRACKET = "{"; //$NON-NLS-1$
    private static final String CLOSE_CURLEY_BRACKET = "}"; //$NON-NLS-1$
    private static final String CLOSE_PAREN = ")"; //$NON-NLS-1$
    private static final String CYS = "([CYS]:"; //$NON-NLS-1$
    private static final String CONNECT = "{connected"; //$NON-NLS-1$
    private static final String COMMA = ", " ; //$NON-NLS-1$
    private static final String SEMI = "; " ; //$NON-NLS-1$
    
	private static final String BOND_MODE_AND = "bond_mode_and"; //$NON-NLS-1$
	private static final String PROTEIN_QUATERNARY_HBOND_SELECTION_FORMAT = "protein_quaternary_hbond_selection_format"; //$NON-NLS-1$
	private static final String PROTEIN_QUATERNARY_FILTERED_SELECTION_FORMAT = "protein_quaternary_filtered_selection_format"; //$NON-NLS-1$
	private static final String PROTEIN_QUATERNARY_FILTERED_SELECTION_TRANSLUCENCY_FORMAT = "protein_quaternary_filtered_selection_translucency_format"; //$NON-NLS-1$
	private static final String PROTEIN_QUATERNARY_FILTERED_SELECTION_PREFIX_FORMAT = "protein_quaternary_filtered_selection_prefix_format"; //$NON-NLS-1$
	private static final String PROTEIN_QUATERNARY_FILTERED_SELECTION_SUFFIX_FORMAT = "protein_quaternary_filtered_selection_suffix_format"; //$NON-NLS-1$
	private static final String HBOND_SIZE_FORMAT = "hbond_size_format"; //$NON-NLS-1$
	private static final String HBOND_TRANSLUCENCY_FORMAT = "hbond_translucency_format"; //$NON-NLS-1$
	
	private static final String SSBOND_SIZE_FORMAT = "ssbond_size_format"; //$NON-NLS-1$
	private static final String SSBOND_TRANSLUCENCY_FORMAT = "ssbond_translucency_format"; //$NON-NLS-1$
    
	private static final String ADJUST_PROTEIN_QUATERNARY_FILTERED_SELECTION_FORMAT = "adjust_protein_quaternary_filtered_selection_format"; //$NON-NLS-1$
	private static final String ADJUST_PROTEIN_QUATERNARY_FILTERED_SELECTION_CHAIN_FORMAT1 = "adjust_protein_quaternary_filtered_selection_chain_format1"; //$NON-NLS-1$
	private static final String ADJUST_PROTEIN_QUATERNARY_FILTERED_SELECTION_CHAIN_FORMAT2 = "adjust_protein_quaternary_filtered_selection_chain_format2"; //$NON-NLS-1$

	private static final String bondFormat = getPrb().getString(BOND_MODE_AND);

	transient private static PropertyResourceBundle prb = null;
	private static PropertyResourceBundle getPrb()
	{
		if(null == prb)
		{
			InputStream inputStream = ViewerRenderProteinQuaternaryStructure.class.getResourceAsStream("./JmolScripts/StarBiochem.properties"); //$NON-NLS-1$
			if( inputStream == null )
			{
				inputStream = ViewerRenderProteinQuaternaryStructure.class.getResourceAsStream("/JmolScripts/StarBiochem.properties" );  //$NON-NLS-1$
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
	
	protected static void renderProteinQuaternaryStructure(Viewer viewer, RenderingInfo ri) throws StarBiochemException
	{
		if((null != viewer) && (null != ri))
		{
			int filterOptions = ri.getFilterOptions();
			if(0 != (filterOptions & RenderingInfoRaiser.ALL_CHAINS_VISIBLE))
			{
				String filteredSelectionScript = getProteinQuatenaryFilteredSelectionScript(ri);
				if((null != filteredSelectionScript) && !EMPTY_STRING.equals(filteredSelectionScript))
				{
					viewer.script(filteredSelectionScript + "subset;"); //$NON-NLS-1$
				}
			}
		}
	}
	
	protected static void renderProteinQuaternaryStructureTranslucency(Viewer viewer, RenderingInfo ri) throws StarBiochemException
	{
		if((null != viewer) && (null != ri))
		{
			int filterOptions = ri.getFilterOptions();
			if(0 != (filterOptions & RenderingInfoRaiser.ALL_CHAINS_VISIBLE))
			{
				String filteredSelectionScript = getProteinQuatenaryFilteredSelectionTranslucencyScript(ri);
				if((null != filteredSelectionScript) && !EMPTY_STRING.equals(filteredSelectionScript))
				{
					viewer.script(filteredSelectionScript);
				}
			}
		}
	}
		
	protected static void renderProteinQuaternarySSBonds(Viewer viewer, RenderingInfo ri) throws StarBiochemException
	{
		if((null != viewer) && (null != ri))
		{
			int filterOptions = ri.getFilterOptions();
			if(0 != (filterOptions & RenderingInfoRaiser.ALL_CHAINS_VISIBLE))
			{
				String ssBondPropertiesScript = getSSBondPropertiesScript(ri);
				ArrayList<String> ssBondSelection = getSSBondSelection(ri);
				if((null != ssBondSelection) && 0 != ssBondSelection.size())
				{
					String script = EMPTY_STRING;
					Iterator<String> scriptIterator = ssBondSelection.iterator();
					if(null != scriptIterator)
					{
						while(scriptIterator.hasNext())
						{
							String myScript = scriptIterator.next();
							if((null != myScript) && (0 != myScript.length()))
							{
								script += (myScript + ssBondPropertiesScript);
							}
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
	
	protected static void renderProteinQuaternaryHbonds(Viewer viewer, RenderingInfo ri) throws StarBiochemException
	{
		if((null != viewer) && (null != ri))
		{
			int filterOptions = ri.getFilterOptions();
			if(0 != (filterOptions & RenderingInfoRaiser.ALL_CHAINS_VISIBLE))
			{
				ArrayList<String> hBondSelectionScript = selectedHbondsToString(ri);
				if((null != hBondSelectionScript) && (0 != hBondSelectionScript.size()))
				{
					String format = getPrb().getString(PROTEIN_QUATERNARY_HBOND_SELECTION_FORMAT);
					String hbondProperties = getHBondPropertiesScript(ri);						
					if((null != hbondProperties) && !EMPTY_STRING.equals(hbondProperties))
					{
						String string = EMPTY_STRING;
						Iterator<String> scriptIterator = hBondSelectionScript.iterator();
						if(null != scriptIterator)
						{
							while(scriptIterator.hasNext())
							{
								String myScript = String.format(format, scriptIterator.next());
								if((null != myScript) && (0 != myScript.length()))
								{
									string += (myScript + hbondProperties +"\n"); //$NON-NLS-1$
								}
							}
						}
						String script = (String.format(bondFormat, string));
						viewer.script(script);
					}
				}
			}
		}
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
	
	private static ArrayList<String> selectedHbondsToString(RenderingInfo ri)
	{		
		ArrayList<String> hbondSelectionString = new ArrayList<String>();
		if((null != ri) && (null != ri.getSelection()) && (0 != ri.getSelection().length))
		{
			String[] selections = ri.getSelection();
			if((null != ri.getStructureHbonds()) && (0 != ri.getStructureHbonds().length))
			{
				Bond[] hbonds = ri.getStructureHbonds();
				
				int filterOptions = ri.getFilterOptions();
				if(-1 != filterOptions)
				{	
					for(int i = 0; hbonds.length != i; i++)
					{
						String atom1 = hbonds[i].getAtom1();
						String atom2 = hbonds[i].getAtom2();
						
						int count = 0;
						String atomChain1 = getChainId(atom1);
						String atomChain2 = getChainId(atom2);
						
						for(int j = 0; selections.length != j; j++)
						{
							String mySelection = selections[j].toString();
							if(mySelection.equals(atomChain1)) count++;
							if(mySelection.equals(atomChain2)) count++;
						}
						if(count == 2)
						{
							String bond = atom1 + COMMA + atom2 + SEMI;
							hbondSelectionString.add(bond);
						}
					}
				}
			}
		}
		return hbondSelectionString;
	}
	
	private static String getChainId(String atom)
	{	
		String chain = null;
		if(null != atom)
		{
			Pattern p = Pattern.compile("\\:([^\\:]+)\\.");  //$NON-NLS-1$
			Matcher m = p.matcher(atom);
			boolean match = m.find();
			if(match)
			{
				chain = m.group(1).trim();
			}
		}		
		return chain;
	}
		
	private static ArrayList<String> getSSBondSelection(RenderingInfo ri)
	{
		ArrayList<String> selectionArray = new ArrayList<String>();
		if(null != ri && null != ri.getSelection())
		{
			String[] selection = ri.getSelection();
			int filterOptions = ri.getFilterOptions();
			if((0 != filterOptions) && (null != selection) && selection.length != 0)
			{
				String selectionString = null;	
				String connectedString = null;
				String ssbondString = null;
				for(int i = 0; selection.length != i; i++)
				{
					selectionString = selection[i].toString();
					for(int j = i+1; selection.length != j; j++)
					{
						connectedString = selection[j].toString();
						if(!selectionString.equals(connectedString))
						{
							ssbondString = "select" + OPEN_CURLEY_BRACKET + CONNECT + CYS + selectionString + CLOSE_PAREN + CLOSE_CURLEY_BRACKET + LOGICAL_AND //$NON-NLS-1$
										   + CONNECT + CYS + connectedString + CLOSE_PAREN + CLOSE_CURLEY_BRACKET + CLOSE_CURLEY_BRACKET + "; ";  //$NON-NLS-1$
							selectionArray.add(ssbondString);
						}
					}
				}
			}
		}
		return selectionArray;
	}
	
	private static String getSSBondPropertiesScript(RenderingInfo ri)
	{
		if((null != getPrb()) && (null != ri))
		{
			int size = ri.getSSbondSize();
			double ssbondSize = (size * 0.01);
			String ssBondSizeScript = ((-1 == size)  ? EMPTY_STRING : (String.format(getPrb().getString(SSBOND_SIZE_FORMAT),Double.toString(ssbondSize))));
			
			String ssBondTranslucency = Float.toString(ri.getSSbondTranslucency()/100.f);
			String ssBondTranslucencyScript = ((-1 == ri.getSSbondTranslucency())  ? EMPTY_STRING : (String.format(getPrb().getString(SSBOND_TRANSLUCENCY_FORMAT),ssBondTranslucency)));
			String ssBondPropertiesScript = ssBondSizeScript + ssBondTranslucencyScript;
	
			return ssBondPropertiesScript;
		}
		return null;
	}

	private static String getProteinQuatenaryFilteredSelectionScript(RenderingInfo ri)
	{
		String script = EMPTY_STRING;
		if((null != ri) && (null != ri.getSelection()))
		{
			int filterOptions = ri.getFilterOptions();
			if(-1 != filterOptions)
			{
				String[] selections = ri.getSelection();
				if((null != selections) && (0 != selections.length))
				{
					if(null != getPrb())
					{
						String format = getPrb().getString(PROTEIN_QUATERNARY_FILTERED_SELECTION_FORMAT);
						if(null != format)
						{
							ChainColors chainColors = new JmolColors();
							HashSet<String> displayed = new HashSet<String>();
							for(int i=0; selections.length != i; i++)
							{
								String selection = selections[i];
								if((null != selection) && (0 != selection.length()) && !displayed.contains(selection))
								{
									Color chainColor = chainColors.getChainColor(selection);
									displayed.add(selection);
									script += String.format(format, selection, Integer.toString(ri.getSize()), Float.toString((float)(2.56f * ri.getTranslucency())), Integer.toString(chainColor.getRed()), Integer.toString(chainColor.getGreen()), Integer.toString(chainColor.getBlue()));
								}
							}
						}
						if((null != script) && !EMPTY_STRING.equals(script))
						{
							String prefix = getPrb().getString(PROTEIN_QUATERNARY_FILTERED_SELECTION_PREFIX_FORMAT);
							String suffix = getPrb().getString(PROTEIN_QUATERNARY_FILTERED_SELECTION_SUFFIX_FORMAT);
							script = prefix + script + suffix;
						}
					}
				}
			}
		} 
		return script;
	}

	private static String getProteinQuatenaryFilteredSelectionTranslucencyScript(RenderingInfo ri)
	{
		String script = EMPTY_STRING;
		if((null != ri) && (null != ri.getSelection()))
		{
			int filterOptions = ri.getFilterOptions();
			if(-1 != filterOptions)
			{
				String[] selections = ri.getSelection();
				if(null != selections)
				{
					if(null != getPrb())
					{
						String format = getPrb().getString(PROTEIN_QUATERNARY_FILTERED_SELECTION_TRANSLUCENCY_FORMAT); 
						if(null != format)
						{
							HashSet<String> displayed = new HashSet<String>();
							for(int i=0; selections.length != i; i++)
							{
								String selection = selections[i];
								if((null != selection) && !EMPTY_STRING.equals(selection))
								{
									if(!displayed.contains(selection))
									{
										displayed.add(selection);
										script += String.format(format, selection, Float.toString((float)(2.56f * ri.getTranslucency())));
									}
								}
							}
						}
					}
				}
			}
		}
		return script;
	}
	
	protected static String getAdjustProteinQuatenaryFilteredSelectionScript(AdjustSelectionInfo asi)
	{
		String script = EMPTY_STRING;
		if((null != asi) && (null != asi.getSelections()))
		{
			int filterOptions = asi.getFilterOptions();
			if(-1 != filterOptions)
			{
				String[] selections = asi.getSelections();
				if(null != selections)
				{
					if(null != getPrb())
					{
						String format = getPrb().getString(ADJUST_PROTEIN_QUATERNARY_FILTERED_SELECTION_FORMAT); 
						String format1 = getPrb().getString(ADJUST_PROTEIN_QUATERNARY_FILTERED_SELECTION_CHAIN_FORMAT1); 
						String format2 = getPrb().getString(ADJUST_PROTEIN_QUATERNARY_FILTERED_SELECTION_CHAIN_FORMAT2); 
						if((null != format) && (null != format1) && (null != format2))
						{
							HashSet<String> displayed = new HashSet<String>();
							String temp = null;
							boolean isFirst = true;
							for(int i=0; selections.length != i; i++)
							{
								String selection = selections[i];
								int index = selection.indexOf(COLON);
								temp = selection.substring(index+1);
								if((null != temp) && !displayed.contains(temp))
								{
									displayed.add(temp);
									String tempString = isFirst ? String.format(format1, temp) : String.format(format2, temp);
									isFirst = false;
									script += String.format(format, tempString);
								}
							}
						}
					}
				}
			}
		}
		return script;
	}
}
