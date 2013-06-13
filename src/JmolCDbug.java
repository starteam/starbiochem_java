

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.BitSet;

import javax.swing.JFrame;

import org.jmol.adapter.smarter.SmarterJmolAdapter;
import org.jmol.api.JmolSelectionListener;
import org.jmol.api.JmolViewer;

public class JmolCDbug extends JFrame
{
    private static final long serialVersionUID = 1L;

    transient private JmolViewer viewer = null;

    public JmolCDbug()
    {
		viewer = JmolViewer.allocateViewer(this, new SmarterJmolAdapter(),null,null,null,null,null);
	    viewer.setJmolDefaults();
	    this.setSize(800, 600);
	    setVisible(true);
    }
    
	public void test()
	{
		if(null != viewer)
		{
		    viewer.scriptWait("background [255,255,0];");
			viewer.scriptWait("load 1crn.pdb;");

//			viewer.scriptWait("load 1cf7.pdb");
//			viewer.scriptWait("load APPEND \"1bl8.pdb\";");
//			viewer.scriptWait("frame NEXT");
//			viewer.scriptWait("frame PREVIOUS");
//			viewer.scriptWait("zap file=2");
//			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%");
//			viewer.scriptWait("select structureId=\"2\";");
//			System.out.println(viewer.scriptWait("show structure;"));
//			Hashtable info = (Hashtable)viewer.getProperty(null, "PolymerInfo", "*");
//			Enumeration infoEnum = info.keys();
//			while(infoEnum.hasMoreElements())
//			{
//				String modelsId = (String) infoEnum.nextElement();
//				Vector models = (Vector) info.get(modelsId);
//				Enumeration modelsEnum = ((Vector) models).elements();
//				while(modelsEnum.hasMoreElements())
//				{
//					Hashtable modelInfo = (Hashtable) modelsEnum.nextElement();
//					Enumeration modelInfoKeys = modelInfo.keys();
//					while(modelInfoKeys.hasMoreElements())
//					{
//						Object modelInfoKey = modelInfoKeys.nextElement();
//						Object modelInfoValue = modelInfo.get(modelInfoKey);
//						System.out.println("modelInfoKey type: " + modelInfoKey.getClass() + ", modelInfoKey: " + modelInfoKey);
//						System.out.println("modelInfoValue type: " + modelInfoValue.getClass() + ", modelInfoValue: " + modelInfoValue);
//						if(modelInfoKey.equals("polymers"))
//						{
//							System.out.println("->polymers: ");
//							Enumeration polymers = ((Vector) modelInfoValue).elements();
//							while(polymers.hasMoreElements())
//							{
//								System.out.println("--->polymer: ");
//								Hashtable polymer = (Hashtable) polymers.nextElement();
//								Enumeration polymerKeys = polymer.keys();
//								while(polymerKeys.hasMoreElements())
//								{
//									Object elementKey = polymerKeys.nextElement();
//									Object elementValue = polymer.get(elementKey);
//									if(elementKey.equals("monomers"))
//									{
//										System.out.println("----->monomers: ");
//										Enumeration monomers = ((Vector) elementValue).elements();
//										while(monomers.hasMoreElements())
//										{
//											System.out.println("------->monomer: ");
//											Hashtable monomer = (Hashtable) monomers.nextElement();
//											Enumeration monomerKeys = monomer.keys();
//											while(monomerKeys.hasMoreElements())
//											{
//												Object monomerElementKey = monomerKeys.nextElement();
//												Object monomerElementValue = monomer.get(monomerElementKey);
//												System.out.println(monomerElementKey + ":\t" + monomerElementValue);
//											}
//										}
//									}
//									else if(elementKey.equals("structures"))
//									{
//										System.out.println("----->structures: ");
//										Enumeration structures = ((Vector) elementValue).elements();
//										while(structures.hasMoreElements())
//										{
//											System.out.println("------->structure: ");
//											Hashtable structure = (Hashtable) structures.nextElement();
//											Enumeration structureKeys = structure.keys();
//											while(structureKeys.hasMoreElements())
//											{
//												Object structureElementKey = structureKeys.nextElement();
//												Object structureElementValue = structure.get(structureElementKey);
//												System.out.println(structureElementKey + ":\t" + structureElementValue);
//												if(structureElementKey.equals("leadAtomIndices"))
//												{
//													int[] leadAtomIndices = (int[])structureElementValue;
//													for(int i=0; leadAtomIndices.length != i; i++)
//													{
//														System.out.println("leadAtomIndex: " + leadAtomIndices[i]);
//													}
//												}
//											}
//										}
//									}
//									else if(elementKey.equals("sequence"))
//									{
//										System.out.println("----->sequence: ");
//										String sequence = (String) elementValue;
//										System.out.println("------->sequence: " + sequence);
//									}
//									else
//									{
//										System.out.println("----->" + elementKey + ", elementValue Type: " + elementValue.getClass() + ", elementValue: " + elementValue);
//									}
//								}
//							}
//						}
//					}
//					String structure = viewer.scriptWait("show structure;");
//					StringTokenizer st = new StringTokenizer(structure, "\"");
//					while(st.hasMoreTokens())
//					{
//						String token = st.nextToken();
//						if(token.startsWith("HELIX") || token.startsWith("SHEET"))
//						{
//							StringTokenizer ster = new StringTokenizer(token, "|");
//							while(ster.hasMoreTokens())
//							{
//								String sterToken = ster.nextToken();
//								System.out.println(sterToken.trim());
//								int index = sterToken.trim().indexOf("= ");
//								String s = sterToken.trim().substring(0,6) + " " + sterToken.trim().substring(index+2);
//								System.out.println(s);
//								viewer.scriptWait("select {22-35:A}; structure HELIX ; cartoon 0.5");
//								viewer.scriptWait("select {43-49:A}; structure HELIX ; cartoon 0.75");
//								viewer.scriptWait("select {60-65:A}; structure HELIX ; color cartoon structure; cartoon 2.0;");
//								viewer.scriptWait("select {78-81:A}; structure SHEET ; color CARTOON STRUCTURE; cartoon 2.0;");
//								viewer.scriptWait("select {36-42:A}; structure TURN ; color RIBBON STRUCTURE; ribbon 2.0;");
//								viewer.scriptWait("select none; select {*:B} ;  set selectionHalos ON;");
//								viewer.scriptWait("select none; select strucno=4; ribbon 2.0; color RIBBON STRUCTURE;");
//								viewer.scriptWait("select none; select {*:A}; spacefill 50 %; color atoms TRANSLUCENT 0.2;");
//							}
//						}
//					}
//				}
//			}
//			
//			
//		    viewer.scriptWait("background [255,255,0];");
//			String[] aminoAcids = getAminoAcids();
//			String selectString = "select protein; subset ";
//			for(int i=0; aminoAcids.length != i; i++)
//			{
//				if(0 == i)
//				{
//					selectString += aminoAcids[0];
//				}
//				else
//				{
//					selectString +=  " or " + aminoAcids[i];
//				}
//			}
//			selectString += ";";
//			viewer.scriptWait(selectString);
		}
	}

//	private String[] getAminoAcids()
//	{
//		if(null != viewer)
//		{
//			MySelectionListener mySelectionListener = new MySelectionListener();
//		    viewer.addSelectionListener(mySelectionListener);
//		    
//		    viewer.scriptWait("select none; select protein;");
//	
//			int atomCount = viewer.getSelectionCount();
//			BitSet bitSet = mySelectionListener.getSelectionBitSet();
//		    	    
//			viewer.removeSelectionListener(mySelectionListener);
//	
//			return extractArray(atomCount, bitSet, ".*");		
//		}
//		return null;
//	}
//
	public String[] extractArray(int count, BitSet bitset, String terminator)
	{
		ArrayList<String> arrayList = new ArrayList<String>();
		String current = null;
		int bitIndex = 0;
		for(int i=0; count != i; i++)
		{
			bitIndex = bitset.nextSetBit(bitIndex);
			String atomInfo = viewer.getAtomInfo(bitIndex++);
			int index = atomInfo.indexOf(terminator);
			if(-1 != index)
			{
				String temp = atomInfo.substring(0,index);
				if(!temp.equals(current))
				{
					arrayList.add(temp);
					current = temp;
				}
			}
		}
		return arrayList.toArray(new String[0]);
	}
	
	class MySelectionListener implements JmolSelectionListener
	{
		BitSet selectionBitSet = null;
		public BitSet getSelectionBitSet()
		{
			return this.selectionBitSet;
		}
		
		public synchronized void selectionChanged(BitSet selectionBitSet)
	    {
			this.selectionBitSet = (BitSet) selectionBitSet.clone();
	    }
		
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		Dimension dimSize = this.getSize();
		Rectangle rect = g.getClipBounds();
		if(null != viewer)
		{
			viewer.renderScreenImage(g, dimSize, rect);
		}
	}

	public static void main(String[] args)
	{
		JmolCDbug jmolCDbug = new JmolCDbug();
		jmolCDbug.test();
	}

//	private BitSet getBitSet(String script)
//	{
//		if((null != script) && (null != viewer))
//		{
//			MySelectionListener mySelectionListener = new MySelectionListener();
//		    viewer.addSelectionListener(mySelectionListener);
//	
//		    viewer.scriptWait(script);
//		    
//			BitSet bitSet = mySelectionListener.getSelectionBitSet();
//		    
//			viewer.removeSelectionListener(mySelectionListener);
//	
//			return bitSet;
//		}
//		return null;
//	}
//
//	public ArrayList<String> extractArray(ArrayList<String> elements, int count, BitSet bitset, String terminator)
//	{
//		ArrayList<String> arrayList = elements;
//		if(null == arrayList)
//		{
//			arrayList = new ArrayList<String>();
//		}
//		if(null != arrayList)
//		{
//			String current = null;
//			int bitIndex = 0;
//			for(int i=0; count != i; i++)
//			{
//				bitIndex = bitset.nextSetBit(bitIndex);
//				String atomInfo = viewer.getAtomInfo(bitIndex++);
//				int index = atomInfo.indexOf(terminator);
//				if(-1 != index)
//				{
//					String temp = atomInfo.substring(0,index);
//					if(!temp.equals(current))
//					{
//						arrayList.add(temp);
//						current = temp;
//					}
//				}
//			}
//			return arrayList;
//		}
//		return null;
//	}
//	
//	public synchronized BitSet getHelix()
//	{
//		if(null != prb)
//		{
//			return getBitSet(prb.getString(GET_HELIX_SCRIPT));
//		}
//		return null;
//	}
//
//	public synchronized BitSet getSheet()
//	{
//		if(null != prb)
//		{
//			return getBitSet(prb.getString(GET_SHEET_SCRIPT));
//		}
//		return null;
//	}
//
//	public synchronized BitSet getTurn()
//	{
//		if(null != prb)
//		{
//			return getBitSet(prb.getString(GET_TURN_SCRIPT));
//		}
//		return null;
//	}
//	
//	public synchronized String[] getNucleic()
//	{
//		if(null != prb)
//		{
//			return getMoleculeElements(prb.getString(GET_NUCLEIC_SCRIPT));
//		}
//		return null;
//	}
//
//	public synchronized String[] getHeteroNotWater()
//	{
//		if(null != prb)
//		{
//			return getMoleculeElements(prb.getString(GET_HETERO_SCRIPT));
//		}
//		return null;
//	}
//
//	public synchronized String[] getWater()
//	{
//		if(null != prb)
//		{
//			return getMoleculeElements(prb.getString(GET_WATER_SCRIPT));
//		}
//		return null;
//	}
//	
//	public synchronized Map<String, ArrayList<String>> getProteinSecondaryStructure()
//	{
//		if(null != prb)
//		{
//			TreeMap<String, ArrayList<String>> map = null;
//			map = addToMap(map, getHelix(), "HELIX");
//			map = addToMap(map, getSheet(), "SHEET");
//			map = addToMap(map, getTurn(), "TURN");
//			if((null != map) && !map.isEmpty())
//			{
//				return map;
//			}
//		}
//		return null;
//	}
//
//	private synchronized TreeMap<String, ArrayList<String>> addToMap(TreeMap<String, ArrayList<String>> map, BitSet bs, String name)
//	{
//		if((null != bs) && !bs.isEmpty() && (null != viewer))
//		{
//			TreeMap<String, ArrayList<String>> tempMap = map;
//			if(null == tempMap)
//			{
//				tempMap = new TreeMap<String, ArrayList<String>>();
//			}
//			ArrayList<String> value = null;
//			String current = null;
//			int keySeqNum = 1;
//			int seqNum = 0;
//			String chain = null;
//			for(int i= bs.nextSetBit(0); -1 != i; i = bs.nextSetBit(i+1))
//			{
//				String info = viewer.getAtomInfo(i);
//				int iseqStart = info.indexOf("]");
//				if(-1 != iseqStart)
//				{
//					iseqStart++;
//					int iseqLen = info.substring(iseqStart).indexOf(":");
//					if(-1 != iseqLen)
//					{
//						int index = info.indexOf(".");
//						if(-1 != index)
//						{
//							String temp = info.substring(0,index);
//							int tempSeqNum = Integer.parseInt(info.substring(iseqStart, iseqStart + iseqLen));
//							String tempChain = info.substring(iseqStart + iseqLen, index);
//							if(0 == seqNum)
//							{
//								seqNum = tempSeqNum;
//							}
//							if(null == chain)
//							{
//								chain = tempChain;
//							}
//							
//							if(!chain.equals(tempChain) || (tempSeqNum - seqNum) > 1)
//							{
//								String keyseq = String.format(" %1$5d ", keySeqNum++);
//								String key = name + keyseq + value.get(0) + " ... " + value.get(value.size() - 1);
//								tempMap.put(key, value);
//								current = null;
//								value = null;
//								chain = tempChain;
//							}
//							seqNum = tempSeqNum;
//							if((null == current) || !temp.equals(current))
//							{
//								current = temp;
//								if(null == value)
//								{
//									value = new ArrayList<String>();
//								}
//								value.add(current);
//							}
//						}
//					}
//				}
//			}
//			if(null != chain)
//			{
//				String keyseq = String.format(" %1$5d ", keySeqNum++);
//				String key = name + keyseq + value.get(0) + " ... " + value.get(value.size() - 1);
//				tempMap.put(key, value);
//				current = null;
//				value = null;
//			}
//			if((null != tempMap) && !tempMap.isEmpty())
//			{
//				return tempMap;
//			}
//		}	
//		return map;
//	}
//
//	String getNucleicPropertiesScript(RenderingInfo ri)
//	{
//		if(null != prb)
//		{
//			String translucencyScript = ((-1 == ri.getTranslucency()) ? "" : (String.format(prb.getString(NUCLEIC_TRANSLUCENCY_FORMAT),Float.toString(ri.getTranslucency()/100.f))));
//			String bondTranslucencyScript = getNucleicBondTranslucencyPropertyScript(ri);
//			String brightnessScript = ((-1 == ri.getBrightness())  ? "" : (String.format(prb.getString(NUCLEIC_BRIGHTNESS_FORMAT),Integer.toString(ri.getBrightness()))));
//			String diffuseScript = ((-1 == ri.getDiffuse())  ? "" : (String.format(prb.getString(NUCLEIC_DIFFUSE_FORMAT),Integer.toString(ri.getDiffuse()))));
//			String segmentScript = ((-1 == ri.getSegments())  ? "" : (String.format(prb.getString(NUCLEIC_SEGMENT_FORMAT),Integer.toString(ri.getSegments()))));
//			String spacefillScript = ((-1 == ri.getSize())  ? "" : (String.format(prb.getString(NUCLEIC_SPACEFILL_FORMAT),Integer.toString(ri.getSize()))));
//			String specularScript = ((-1 == ri.getSpecular())  ? "" : (String.format(prb.getString(NUCLEIC_SPECULAR_FORMAT),Integer.toString(ri.getSpecular()/10))));
//			
//			String propertiesScript = translucencyScript + bondTranslucencyScript + brightnessScript + diffuseScript + segmentScript + spacefillScript + specularScript;
//			return propertiesScript;
//		}
//		return null;
//	}
//
//	String getNucleicBondTranslucencyPropertyScript(RenderingInfo ri)
//	{
//		if(null != ri)
//		{
//			if(-1 != ri.getBondTranslucency())
//			{
//				String bondTranslucency = Float.toString(ri.getBondTranslucency()/100.f);
//				int filterOptions = ri.getFilterOptions();
//				if(-1 != filterOptions)
//				{
//					String[] selection = ri.getSelection();
//					if(null != selection)
//					{
//						String format = prb.getString(NUCLEIC_BONDTRANSLUCENCY_FORMAT); 
//						if(null != format)
//						{
//							if(0 != (filterOptions & RenderingInfoRaiser.NUCLEIC_VISIBLE))
//							{
//								String nucleic = createSelector(selection, ".*");
//								if(null != nucleic)
//								{
//									return String.format(format, nucleic, bondTranslucency);
//								}
//							}
//							else if(0 != (filterOptions & RenderingInfoRaiser.SUGAR_VISIBLE))
//							{
//								String allsugar = createNucleicAllSugarBondSelector(selection);
//								if(null != allsugar)
//								{
//									return String.format(format, allsugar, bondTranslucency);
//								}
//							}
//							else if(0 != (filterOptions & RenderingInfoRaiser.FIVEPRIME_VISIBLE))
//							{
//								String c5sugar = createNucleic5PrimeSugarBondSelector(selection);
//								if(null != c5sugar)
//								{
//									return String.format(format, c5sugar, bondTranslucency);
//								}
//							}
//							else if(0 != (filterOptions & RenderingInfoRaiser.THREEPRIME_VISIBLE))
//							{
//								String c3sugar = createNucleic3PrimeSugarBondSelector(selection);
//								if(null != c3sugar)
//								{
//									return String.format(format, c3sugar, bondTranslucency);
//								}
//							}
//							else if(0 != (filterOptions & RenderingInfoRaiser.PHOSPHATE_VISIBLE))
//							{
//								String phosphateatoms = createNucleicPhosphateBondSelector(selection);
//								if(null != phosphateatoms)
//								{
//									return String.format(format, phosphateatoms, bondTranslucency);
//								}
//							}
//							else if(0 != (filterOptions & RenderingInfoRaiser.BASE_VISIBLE))
//							{
//								String bases = createNucleicBaseBondSelector(selection);
//								if(null != bases)
//								{
//									return String.format(format, bases, bondTranslucency);
//								}
//							}
//						}
//					}
//				}
//			}
//		}
//		return "";
//	}
//
//	String getWaterPropertiesScript(RenderingInfo ri)
//	{
//		String translucencyScript = ((-1 == ri.getTranslucency()) ? "" : (String.format(prb.getString(WATER_TRANSLUCENCY_FORMAT),Float.toString(ri.getTranslucency()/100.f))));
//		String bondTranslucency = Float.toString(ri.getBondTranslucency()/100.f);
//		String bondTranslucencyScript = ((-1 == ri.getBondTranslucency()) ? "" : (String.format(prb.getString(WATER_BONDTRANSLUCENCY_FORMAT), bondTranslucency)));
//		String brightnessScript = ((-1 == ri.getBrightness())  ? "" : (String.format(prb.getString(WATER_BRIGHTNESS_FORMAT),Integer.toString(ri.getBrightness()))));
//		String diffuseScript = ((-1 == ri.getDiffuse())  ? "" : (String.format(prb.getString(WATER_DIFFUSE_FORMAT),Integer.toString(ri.getDiffuse()))));
//		String segmentScript = ((-1 == ri.getSegments())  ? "" : (String.format(prb.getString(WATER_SEGMENT_FORMAT),Integer.toString(ri.getSegments()))));
//		String spacefillScript = ((-1 == ri.getSize())  ? "" : (String.format(prb.getString(WATER_SPACEFILL_FORMAT),Integer.toString(ri.getSize()))));
//		String specularScript = ((-1 == ri.getSpecular())  ? "" : (String.format(prb.getString(WATER_SPECULAR_FORMAT),Integer.toString(ri.getSpecular()/10))));
//		
//		String propertiesScript = translucencyScript + bondTranslucencyScript + brightnessScript + diffuseScript + segmentScript + spacefillScript + specularScript;
//		return propertiesScript;
//	}
//
//	String getPrimaryPropertiesScript(RenderingInfo ri)
//	{
//		String translucencyScript = ((-1 == ri.getTranslucency()) ? "" : (String.format(prb.getString(PROTEIN_PRIMARY_TRANSLUCENCY_FORMAT),Float.toString(ri.getTranslucency()/100.f))));
//		String bondTranslucency = Float.toString(ri.getBondTranslucency()/100.f);
//		String bondTranslucencyScript = ((-1 == ri.getBondTranslucency()) ? "" : (String.format(prb.getString(PROTEIN_PRIMARY_BONDTRANSLUCENCY_FORMAT), bondTranslucency)));
//		String brightnessScript = ((-1 == ri.getBrightness())  ? "" : (String.format(prb.getString(PROTEIN_PRIMARY_BRIGHTNESS_FORMAT),Integer.toString(ri.getBrightness()))));
//		String diffuseScript = ((-1 == ri.getDiffuse())  ? "" : (String.format(prb.getString(PROTEIN_PRIMARY_DIFFUSE_FORMAT),Integer.toString(ri.getDiffuse()))));
//		String segmentScript = ((-1 == ri.getSegments())  ? "" : (String.format(prb.getString(PROTEIN_PRIMARY_SEGMENT_FORMAT),Integer.toString(ri.getSegments()))));
//		String spacefillScript = ((-1 == ri.getSize())  ? "" : (String.format(prb.getString(PROTEIN_PRIMARY_SPACEFILL_FORMAT),Integer.toString(ri.getSize()))));
//		String specularScript = ((-1 == ri.getSpecular())  ? "" : (String.format(prb.getString(PROTEIN_PRIMARY_SPECULAR_FORMAT),Integer.toString(ri.getSpecular()/10))));
//				
//		String propertiesScript = translucencyScript + bondTranslucencyScript + brightnessScript + diffuseScript + segmentScript + spacefillScript + specularScript;
//		return propertiesScript;
//	}
//
//	String getSecondaryPropertiesScript(RenderingInfo ri)
//	{
//		String translucencyScript = ((-1 == ri.getTranslucency()) ? "" : (String.format(prb.getString(PROTEIN_SECONDARY_TRANSLUCENCY_FORMAT),Float.toString(ri.getTranslucency()/100.f))));
////		String bondTranslucency = Float.toString(ri.getBondTranslucency()/100.f);
////		String bondTranslucencyScript = ((-1 == ri.getBondTranslucency()) ? "" : (String.format(prb.getString(PROTEIN_SECONDARY_BONDTRANSLUCENCY_FORMAT), bondTranslucency)));
//		String brightnessScript = ((-1 == ri.getBrightness())  ? "" : (String.format(prb.getString(PROTEIN_SECONDARY_BRIGHTNESS_FORMAT),Integer.toString(ri.getBrightness()))));
//		String diffuseScript = ((-1 == ri.getDiffuse())  ? "" : (String.format(prb.getString(PROTEIN_SECONDARY_DIFFUSE_FORMAT),Integer.toString(ri.getDiffuse()))));
//		String segmentScript = ((-1 == ri.getSegments())  ? "" : (String.format(prb.getString(PROTEIN_SECONDARY_SEGMENT_FORMAT),Integer.toString(ri.getSegments()))));
//		String spacefillScript = ((-1 == ri.getSize())  ? "" : (String.format(prb.getString(PROTEIN_SECONDARY_SPACEFILL_FORMAT),Integer.toString(ri.getSize()))));
//		String specularScript = ((-1 == ri.getSpecular())  ? "" : (String.format(prb.getString(PROTEIN_SECONDARY_SPECULAR_FORMAT),Integer.toString(ri.getSpecular()/10))));
//		
//		String propertiesScript = spacefillScript + translucencyScript + brightnessScript + diffuseScript + segmentScript + specularScript;
//		return propertiesScript;
//	}
//
//	String getTertiaryPropertiesScript(RenderingInfo ri)
//	{
//		String translucencyScript = ((-1 == ri.getTranslucency()) ? "" : (String.format(prb.getString(PROTEIN_TERTIARY_TRANSLUCENCY_FORMAT),Float.toString(ri.getTranslucency()/100.f))));
//		String bondTranslucency = Float.toString(ri.getBondTranslucency()/100.f);
//		String bondTranslucencyScript = ((-1 == ri.getBondTranslucency()) ? "" : (String.format(prb.getString(PROTEIN_TERTIARY_BONDTRANSLUCENCY_FORMAT), bondTranslucency)));
//		String brightnessScript = ((-1 == ri.getBrightness())  ? "" : (String.format(prb.getString(PROTEIN_TERTIARY_BRIGHTNESS_FORMAT),Integer.toString(ri.getBrightness()))));
//		String diffuseScript = ((-1 == ri.getDiffuse())  ? "" : (String.format(prb.getString(PROTEIN_TERTIARY_DIFFUSE_FORMAT),Integer.toString(ri.getDiffuse()))));
//		String segmentScript = ((-1 == ri.getSegments())  ? "" : (String.format(prb.getString(PROTEIN_TERTIARY_SEGMENT_FORMAT),Integer.toString(ri.getSegments()))));
//		String spacefillScript = ((-1 == ri.getSize())  ? "" : (String.format(prb.getString(PROTEIN_TERTIARY_SPACEFILL_FORMAT),Integer.toString(ri.getSize()))));
//		String specularScript = ((-1 == ri.getSpecular())  ? "" : (String.format(prb.getString(PROTEIN_TERTIARY_SPECULAR_FORMAT),Integer.toString(ri.getSpecular()/10))));
//		
//		String propertiesScript = translucencyScript + bondTranslucencyScript + brightnessScript + diffuseScript + segmentScript + spacefillScript + specularScript;
//		return propertiesScript;
//	}
//
//	String getQuatenaryPropertiesScript(RenderingInfo ri)
//	{
//		String translucencyScript = ((-1 == ri.getTranslucency()) ? "" : (String.format(prb.getString(PROTEIN_QUATENARY_TRANSLUCENCY_FORMAT),Float.toString(ri.getTranslucency()/100.f))));
//		String bondTranslucency = Float.toString(ri.getBondTranslucency()/100.f);
//		String bondTranslucencyScript = ((-1 == ri.getBondTranslucency()) ? "" : (String.format(prb.getString(PROTEIN_QUATENARY_BONDTRANSLUCENCY_FORMAT), bondTranslucency)));
//		String brightnessScript = ((-1 == ri.getBrightness())  ? "" : (String.format(prb.getString(PROTEIN_QUATENARY_BRIGHTNESS_FORMAT),Integer.toString(ri.getBrightness()))));
//		String diffuseScript = ((-1 == ri.getDiffuse())  ? "" : (String.format(prb.getString(PROTEIN_QUATENARY_DIFFUSE_FORMAT),Integer.toString(ri.getDiffuse()))));
//		String segmentScript = ((-1 == ri.getSegments())  ? "" : (String.format(prb.getString(PROTEIN_QUATENARY_SEGMENT_FORMAT),Integer.toString(ri.getSegments()))));
//		String spacefillScript = ((-1 == ri.getSize())  ? "" : (String.format(prb.getString(PROTEIN_QUATENARY_SPACEFILL_FORMAT),Integer.toString(ri.getSize()))));
//		String specularScript = ((-1 == ri.getSpecular())  ? "" : (String.format(prb.getString(PROTEIN_QUATENARY_SPECULAR_FORMAT),Integer.toString(ri.getSpecular()/10))));
//		
//		String propertiesScript = translucencyScript + bondTranslucencyScript + brightnessScript + diffuseScript + segmentScript + spacefillScript + specularScript;
//		return propertiesScript;
//	}
//
//	String getProteinPrimaryFilteredSelectionScript(RenderingInfo ri)
//	{
//		if(null != ri)
//		{
//			int filterOptions = ri.getFilterOptions();
//			if(-1 != filterOptions)
//			{
//				String selector = createSelector(ri.getSelection(), ".*");
//				if(null != selector)
//				{
//					if(null != prb)
//					{
//						String format = prb.getString(PROTEIN_PRIMARY_FILTERED_SELECTION_FORMAT);
//						if(null != format)
//						{
//							if(0 != (filterOptions & RenderingInfoRaiser.ALL_PROTEIN_VISIBLE))
//							{
//								return String.format(format, "AMINO", selector);
//							}
//							else if(0 != (filterOptions & RenderingInfoRaiser.BACKBONE_VISIBLE))
//							{
//								return String.format(format, "BACKBONE", selector);
//							}
//							else if(0 != (filterOptions & RenderingInfoRaiser.SIDECHAIN_VISIBLE))
//							{
//								return String.format(format, "SIDECHAIN", selector);
//							}
//							else if(0 != (filterOptions & RenderingInfoRaiser.ALPHA_VISIBLE))
//							{
//								return String.format(format, "ALPHA",selector);
//							}
//						}
//					}
//				}
//			}
//		}
//		return null;
//	}
//
//	String getProteinSecondaryFilteredSelectionScript(RenderingInfo ri)
//	{
//		if((null != ri) && (-1 != ri.getSize()))
//		{
//			float size = (float)ri.getSize()/50.f;
//			int filterOptions = ri.getFilterOptions();
//			if(-1 != filterOptions)
//			{
//				String selector = createSelector(ri.getSelection(), ".CA");
//				if(null != selector)
//				{
//					if(null != prb)
//					{
//						String format = prb.getString(PROTEIN_SECONDARY_FILTERED_SELECTION_FORMAT); 
//						if(null != format)
//						{
//							if(0 != (filterOptions & RenderingInfoRaiser.ALPHAHELIX_VISIBLE))
//							{
//								return String.format(format, "HELIX", selector, "cartoons", size);
//							}
//							if(0 != (filterOptions & RenderingInfoRaiser.BETASHEET_VISIBLE))
//							{
//								return String.format(format, "SHEET", selector, "cartoons", size);
//							}
//							if(0 != (filterOptions & RenderingInfoRaiser.COIL_VISIBLE))
//							{
//								return String.format(format, "AMINO xor SHEET xor HELIX", selector, "ribbons", size);
//							}
//							if(0 != (filterOptions & RenderingInfoRaiser.RIBBON_VISIBLE))
//							{
//								return String.format(format, "AMINO", selector, "ribbons", size);
//							}
//						}
//					}
//				}
//			}
//		}
//		return null;
//	}
//
//	private String createSelector(String[] selections, String suffix)
//	{
//		if(null != selections)
//		{
//			String selector = "";
//			for(int i=0; selections.length != i; i++)
//			{
//				if(0 == i)
//				{
//					selector = "{ " + selections[0] + suffix;
//				}
//				else
//				{
//					selector += " or " + selections[i] + suffix;
//				}
//			}
//			if(!selector.equals(""))
//			{
//				selector += " }";
//				return selector;
//			}
//		}
//		return null;
//	}
//	
//	private String createNucleicAllSugarBondSelector(String[] selections)
//	{
//		if(null != selections)
//		{
//			String selector = "";
//			for(int i=0; selections.length != i; i++)
//			{
//				if(0 == i)
//				{
//					selector = "{ {" + selections[0] + ".?P?} or {" + selections[0] + ".??'} or {" + selections[0] + ".N1}";
//				}
//				else
//				{
//					selector += " or {" + selections[i] + ".?P?} or {" + selections[i] + ".??'} or {" + selections[i] + ".N1}";
//				}
//			}
//			if(!selector.equals(""))
//			{
//				selector += " }";
//				return selector;
//			}
//		}
//		return null;
//	}
//	
//	private String createNucleic5PrimeSugarBondSelector(String[] selections)
//	{
//		if(null != selections)
//		{
//			String selector = "";
//			for(int i=0; selections.length != i; i++)
//			{
//				if(0 == i)
//				{
//					selector = "{ {" + selections[0] + ".?P?} or {" + selections[0] + ".C5'}";
//				}
//				else
//				{
//					selector += " or {" + selections[i] + ".?P?} or {" + selections[i] + ".C5'}";
//				}
//			}
//			if(!selector.equals(""))
//			{
//				selector += " }";
//				return selector;
//			}
//		}
//		return null;
//	}
//	
//	private String createNucleic3PrimeSugarBondSelector(String[] selections)
//	{
//		if(null != selections)
//		{
//			String selector = "";
//			for(int i=0; selections.length != i; i++)
//			{
//				if(0 == i)
//				{
//					selector = "{ {" + selections[0] + ".?P?} or {" + selections[0] + ".C3'}";
//				}
//				else
//				{
//					selector += " or {" + selections[i] + ".?P?} or {" + selections[i] + ".C3'}";
//				}
//			}
//			if(!selector.equals(""))
//			{
//				selector += " }";
//				return selector;
//			}
//		}
//		return null;
//	}
//	
//	private String createNucleicPhosphateBondSelector(String[] selections)
//	{
//		if(null != selections)
//		{
//			String selector = "";
//			for(int i=0; selections.length != i; i++)
//			{
//				if(0 == i)
//				{
//					selector = "{ {" + selections[0] + ".P} or {" + selections[0] + ".?P?} or {" + selections[0] + ".O3'} or {" + selections[0] + ".O5'}";
//				}
//				else
//				{
//					selector += " or {" + selections[i] + ".P} or {" + selections[i] + ".?P?} or {" + selections[0] + ".O3'} or {" + selections[0] + ".O5'}";
//				}
//			}
//			if(!selector.equals(""))
//			{
//				selector += " }";
//				return selector;
//			}
//		}
//		return null;
//	}
//	
//	private String createNucleicBaseBondSelector(String[] selections)
//	{
//		if(null != selections)
//		{
//			String selector = "";
//			for(int i=0; selections.length != i; i++)
//			{
//				if(0 == i)
//				{
//					selector = "{ {" + selections[i] + ".* xor " + selections[i] + ".P xor " + selections[i] + ".?P? xor " + selections[i] + ".??'} or {" + selections[i] + ".C1'}";
//				}
//				else
//				{
//					selector += " or {" + selections[i] + ".* xor " + selections[i] + ".P xor " + selections[i] + ".?P? xor " + selections[i] + ".??'} or {" + selections[i] + ".C1'}";
//				}
//			}
//			if(!selector.equals(""))
//			{
//				selector += " }";
//				return selector;
//			}
//		}
//		return null;
//	}
//	
//	private String createNucleicSugarSelector(String[] selections, String suffix)
//	{
//		if(null != selections)
//		{
//			String selector = "";
//			for(int i=0; selections.length != i; i++)
//			{
//				if(0 == i)
//				{
//					selector = "{ " + selections[0] + suffix;
//				}
//				else
//				{
//					selector += " or " + selections[i] + suffix;
//				}
//			}
//			if(!selector.equals(""))
//			{
//				selector += " }";
//				return selector;
//			}
//		}
//		return null;
//	}
//	
//	private String createNucleicPhosphateSelector(String[] selections)
//	{
//		if(null != selections)
//		{
//			String selector = "";
//			for(int i=0; selections.length != i; i++)
//			{
//				if(0 == i)
//				{
//					selector = "{ {" + selections[0] + ".P or " + selections[0] + ".?P?}";
//				}
//				else
//				{
//					selector += " or {" + selections[i] + ".P or " + selections[i] + ".?P?}";
//				}
//			}
//			if(!selector.equals(""))
//			{
//				selector += " }";
//				return selector;
//			}
//		}
//		return null;
//	}
//	
//	private String createNucleicBaseSelector(String[] selections)
//	{
//		if(null != selections)
//		{
//			String selector = "";
//			for(int i=0; selections.length != i; i++)
//			{
//				if(0 == i)
//				{
//					selector = "{ {" + selections[i] + ".* xor " + selections[i] + ".P xor " + selections[i] + ".?P? xor " + selections[i] + ".??'}";
//				}
//				else
//				{
//					selector += " or {" + selections[i] + ".* xor " + selections[i] + ".P xor " + selections[i] + ".?P? xor " + selections[i] + ".??'}";
//				}
//			}
//			if(!selector.equals(""))
//			{
//				selector += " }";
//				return selector;
//			}
//		}
//		return null;
//	}
//	
//	String getProteinTertiaryFilteredSelectionScript(RenderingInfo ri)
//	{
//		if(null != ri)
//		{
//			int filterOptions = ri.getFilterOptions();
//			if(-1 != filterOptions)
//			{
//				String selector = createSelector(ri.getSelection(), ".*");
//				if(null != selector)
//				{
//					if(null != prb)
//					{
//						String format = prb.getString(PROTEIN_TERTIARY_FILTERED_SELECTION_FORMAT); 
//						if(null != format)
//						{
//							if(0 != (filterOptions & RenderingInfoRaiser.ACIDIC_VISIBLE))
//							{
//								return String.format(format, "ACIDIC", selector);
//							}
//							if(0 != (filterOptions & RenderingInfoRaiser.BASIC_VISIBLE))
//							{
//								return String.format(format, "BASIC", selector);
//							}
//							if(0 != (filterOptions & RenderingInfoRaiser.HYDROPHOBIC_VISIBLE))
//							{
//								return String.format(format, "HYDROPHOBIC", selector);
//							}
//							if(0 != (filterOptions & RenderingInfoRaiser.POLAR_VISIBLE))
//							{
//								return String.format(format, "POLAR", selector);
//							}
//						}
//					}
//				}
//			}
//		}
//		return null;
//	}
//
//	String getProteinQuatenaryFilteredSelectionScript(RenderingInfo ri)
//	{
//		if(null != ri)
//		{
//			int filterOptions = ri.getFilterOptions();
//			if(-1 != filterOptions)
//			{
//				String selector = createSelector(ri.getSelection(), ".*");
//				if(null != selector)
//				{
//					String format = prb.getString(PROTEIN_QUATENARY_FILTERED_SELECTION_FORMAT); 
//					if(null != format)
//					{
//						if(0 != (filterOptions & RenderingInfoRaiser.CHAIN_VISIBLE))
//						{
//							return String.format(format, "AMINO", selector);
//						}
//					}
//				}
//			}
//		}
//		return null;
//	}
//
//	String getNucleicFilteredSelectionScripts(RenderingInfo ri)
//	{
//		if(null != ri)
//		{
//			int filterOptions = ri.getFilterOptions();
//			if(-1 != filterOptions)
//			{
//				String[] selection = ri.getSelection();
//				if(null != selection)
//				{
//					String format = prb.getString(NUCLEIC_FILTERED_SELECTION_FORMAT); 
//					if(null != format)
//					{
//						if(0 != (filterOptions & RenderingInfoRaiser.NUCLEIC_VISIBLE))
//						{
//							String nucleic = createSelector(selection, ".*");
//							if(null != nucleic)
//							{
//								return String.format(format, "NUCLEIC", nucleic);
//							}
//						}
//						else if(0 != (filterOptions & RenderingInfoRaiser.SUGAR_VISIBLE))
//						{
//							String allsugar = createNucleicSugarSelector(selection, ".??'");
//							if(null != allsugar)
//							{
//								return String.format(format, "NUCLEIC", allsugar);
//							}
//						}
//						else if(0 != (filterOptions & RenderingInfoRaiser.FIVEPRIME_VISIBLE))
//						{
//							String c5sugar = createNucleicSugarSelector(selection, ".C5'");
//							if(null != c5sugar)
//							{
//								return String.format(format, "NUCLEIC", c5sugar);
//							}
//						}
//						else if(0 != (filterOptions & RenderingInfoRaiser.THREEPRIME_VISIBLE))
//						{
//							String c3sugar = createNucleicSugarSelector(selection, ".C3'");
//							if(null != c3sugar)
//							{
//								return String.format(format, "NUCLEIC", c3sugar);
//							}
//						}
//						else if(0 != (filterOptions & RenderingInfoRaiser.PHOSPHATE_VISIBLE))
//						{
//							String phosphateatoms = createNucleicPhosphateSelector(selection);
//							if(null != phosphateatoms)
//							{
//								return String.format(format, "NUCLEIC", phosphateatoms);
//							}
//						}
//						else if(0 != (filterOptions & RenderingInfoRaiser.BASE_VISIBLE))
//						{
//							String bases = createNucleicBaseSelector(selection);
//							if(null != bases)
//							{
//								return String.format(format, "NUCLEIC", bases);
//							}
//						}
//					}
//				}
//			}
//		}
//		return null;
//	}
//
//	String getHeteroFilteredSelectionScript(RenderingInfo ri)
//	{
//		String selector = createSelector(ri.getSelection(), "");
//		if(null != selector)
//		{
//			return "select none; select hetero xor water; subset " + selector + ";";
//		}
//		return null;
//	}
//
//	String getWaterFilteredSelectionScript(RenderingInfo ri)
//	{
//		String selector = createSelector(ri.getSelection(), "");
//		if(null != selector)
//		{
//			return "select none;select water; subset " + selector + ";";
//		}
//		return null;
//	}
}
