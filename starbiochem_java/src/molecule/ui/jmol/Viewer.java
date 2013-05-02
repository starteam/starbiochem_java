package molecule.ui.jmol;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JPanel;

import molecule.interfaces.Bond;
import molecule.interfaces.RenderingInfo;

import org.jmol.adapter.smarter.SmarterJmolAdapter;
import org.jmol.api.JmolSelectionListener;
import org.jmol.api.JmolViewer;
import org.jmol.modelset.ModelSet;

import pdb.PDBRemark;
import star.annotations.Handles;
import star.annotations.SignalComponent;
import app.StarBiochemException;
import app.signal.FileSaveImageRaiser;
import file.SaveImage;

@SignalComponent(extend = JPanel.class, raises={})
public class Viewer extends Viewer_generated
{
	private static final String EMPTY_STRING = "";  //$NON-NLS-1$
    private static final String TERMINATOR_DOT = "."; //$NON-NLS-1$
    
	private static final long serialVersionUID = 1L;

	private static final String FILEOPEN_FORMAT =  "fileopen_messageformat"; //$NON-NLS-1$
	private static final String PDBOPEN_FORMAT = "pdbopen_messageformat"; //$NON-NLS-1$
	private static final String IMPORTOPEN_FORMAT = "importopen_messageformat"; //$NON-NLS-1$
	private static final String BACKGROUND_FORMAT = "background_messageformat"; //$NON-NLS-1$
	private static final String INIT_WATER_SCRIPT = "init_water_script"; //$NON-NLS-1$
	private static final String CALC_HYDROGENS_REMARK = "calc_hydrogens_remark"; //$NON-NLS-1$
	
	private static final String GET_HETERO_SCRIPT = "gethetero_messageformat"; //$NON-NLS-1$
	private static final String GET_NUCLEIC_SCRIPT = "getnucleic_messageformat"; //$NON-NLS-1$
	private static final String GET_PROTEIN_SCRIPT = "getprotein_messageformat"; //$NON-NLS-1$
	private static final String GET_WATER_SCRIPT = "getwater_messageformat"; //$NON-NLS-1$
	
	private static final String GET_HBONDS_SCRIPT = "gethbonds_messageformat"; //$NON-NLS-1$
	
	private static final String GET_SSBONDS_SCRIPT = "getssbonds_messageformat"; //$NON-NLS-1$
	private static final String IS_QUATERNARY_SSBONDS_SCRIPT = "is_quaternary_ssbonds_script"; //$NON-NLS-1$
	private static final String IS_TERTIARY_SSBONDS_SCRIPT = "is_tertiary_ssbonds_script"; //$NON-NLS-1$

	private static final String JMOL_BRIGHTNESS_FORMAT = "jmol_brightness_format"; //$NON-NLS-1$
	private static final String JMOL_DIFFUSE_FORMAT = "jmol_diffuse_format"; //$NON-NLS-1$
	private static final String JMOL_SEGMENT_FORMAT = "jmol_segment_format"; //$NON-NLS-1$
	private static final String JMOL_SPECULAR_FORMAT = "jmol_specular_format"; //$NON-NLS-1$
	
    transient private JmolViewer jmolViewer = null;
    private JmolViewer getJmolViewer() throws StarBiochemException
    {
    	if(null == jmolViewer)
    	{
			jmolViewer = JmolViewer.allocateViewer(this,new SmarterJmolAdapter(),null,null,null,null,null);
			if(null == jmolViewer)
			{
				Thread.dumpStack();
				throw new StarBiochemException(StarBiochemException.STARBIOCHEM_OUT_OF_MEMORY);
			}
			init();
    	}
    	return jmolViewer;
    }

    private void init()
    {
    }
    
    public void initTree() throws StarBiochemException
    {
		setViewerBackground(getBackground());
    	getJmolViewer().setJmolDefaults();
		String error = getJmolViewer().getErrorMessage();
		if(null != error && !error.equals(EMPTY_STRING))
		{
			Thread.dumpStack();
			throw new StarBiochemException(StarBiochemException.JMOL_OUT_OF_MEMORY);
		}
    }
    	
	public void reset() throws StarBiochemException
	{
		setViewerBackground(getBackground());
		getJmolViewer().setJmolDefaults();
		String error = getJmolViewer().getErrorMessage();
		if(null != error && !error.equals(EMPTY_STRING))
		{
			Thread.dumpStack();
			throw new StarBiochemException(StarBiochemException.JMOL_OUT_OF_MEMORY);
		}
	}

    transient private PropertyResourceBundle prb = null;
	private PropertyResourceBundle getPrb()
	{
		if(null == this.prb)
		{
			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("./JmolScripts/StarBiochem.properties"); //$NON-NLS-1$
			if( inputStream == null )
			{
				inputStream = this.getClass().getResourceAsStream("/JmolScripts/StarBiochem.properties" );  //$NON-NLS-1$
			}
			if(null != inputStream)
			{
				try
		        {
		            this.prb = new PropertyResourceBundle(inputStream);
		        }
		        catch (IOException e)
		        {
		            e.printStackTrace();
		        }
			}
		}
		return this.prb;
	}

	public void addNotify()
	{
		super.addNotify();
		init();
	}
	
	public void removeNotify()
	{
		this.removeAll();
		super.removeNotify();
	}

	public String getFileOpenScript(String filename)
	{
		if((null != filename) && !EMPTY_STRING.equals(filename) && (null != getPrb()))
		{
			String myFilename = filename;
			String format = getPrb().getString(FILEOPEN_FORMAT);
			if((null != format) && !EMPTY_STRING.equals(format))
			{
				String load =  String.format(format, myFilename);
				String script = load;
				return script;
			}
		}
		return null;
	}
	
	public String getPdbOpenScript(String location, String id)
	{
		if((null != location) && !EMPTY_STRING.equals(location) && (null != id) && !EMPTY_STRING.equals(id) && (null != getPrb()))
		{
			String format = getPrb().getString(PDBOPEN_FORMAT);
			if((null != format) && !EMPTY_STRING.equals(format))
			{
				String load = (String.format(format, location,  id));
				String script = load;
				return script;
			}
		}
		return null;
	}
	
    public String getRemarkScript(PDBRemark[] remark)
    {
    	String script = EMPTY_STRING;
    	if(null != remark && remark.length != 0)
    	{
    		String calculateHydrogens = EMPTY_STRING;
    		String initWater = getPrb().getString(INIT_WATER_SCRIPT);
    		
    		for(int i = 0; remark.length != i; i++)
    		{
    			PDBRemark myRemark = remark[i];
   				if(myRemark.isWaterOn())
   				{
   					initWater = EMPTY_STRING; 
    			}
   				if(myRemark.hasHydrogens())
   				{
   					calculateHydrogens = getPrb().getString(CALC_HYDROGENS_REMARK);
   				}
    		}
    		script += (initWater + calculateHydrogens);
    	}
    	return script;
    }
    
	public String getImportOpenScript(String source)
	{
		if((null != source) && !EMPTY_STRING.equals(source) && (null != getPrb()))
		{
			String format = getPrb().getString(IMPORTOPEN_FORMAT);
			if((null != format) && !EMPTY_STRING.equals(format))
			{
				String load = String.format(format, source);
				String script = load;
				return script;
			}
		}
		return null;
	}
	
	public void setViewerBackground(Color background) throws StarBiochemException
	{
		if(null != background)
		{
		    String red = Integer.toString(background.getRed());
		    String green = Integer.toString(background.getGreen());
		    String blue = Integer.toString(background.getBlue());
		    String format = getPrb().getString(BACKGROUND_FORMAT);
			if((null != format) && !EMPTY_STRING.equals(format))
			{
				String backgroundColorScript = String.format(format, red, green, blue);
			    if((null != backgroundColorScript) && !EMPTY_STRING.equals(backgroundColorScript))
			    {
			    	script(backgroundColorScript);
			    }
			}
		}
	}

	public void setHydrogensVisible(boolean on) throws StarBiochemException
    {
		if (on)
		{
			script("set showHydrogens TRUE;"); //$NON-NLS-1$
		}
		else
		{
			script("set showHydrogens FALSE;"); //$NON-NLS-1$
		}
    }

	public void paint(Graphics g)
	{
		super.paint(g);
		if(null != g)
		{
			Dimension dimSize = this.getSize();
			Rectangle rect = g.getClipBounds();
			if(null != this.jmolViewer)
			{
				jmolViewer.renderScreenImage(g, dimSize, rect);
			}
		}
	}
	
    @Handles(raises = {})
	protected void handleFileSaveImageRaiser(FileSaveImageRaiser raiser)
    {
    	if(null != raiser)
    	{
    		if(this.isShowing())
    		{
    			if(null != jmolViewer)
    			{
    				SaveImage.saveImage(this, jmolViewer);
    			}
    		}
    	}
    }

	public String script(String script) throws StarBiochemException
	{
		if((null != script) && !EMPTY_STRING.equals(script) && (null != getJmolViewer()))
		{
			try
			{
				
				String ret = getJmolViewer().scriptWait("save STATE \"scriptState\";"+script); //$NON-NLS-1$
				String error = getJmolViewer().getErrorMessage();
				if(null != error && !error.equals(EMPTY_STRING))
				{
					throw new StarBiochemException(StarBiochemException.JMOL_OUT_OF_MEMORY);
				}
				return ret;
			}
			catch(OutOfMemoryError e)
			{
				throw new StarBiochemException(StarBiochemException.JMOL_OUT_OF_MEMORY);
			}
		}
		return null;
	}
	
	
	public String getErrorMessage() throws StarBiochemException
	{
		if(null != getJmolViewer())
		{
			return getJmolViewer().getErrorMessageUntranslated();
		}
		return null;
	}
	
	public String[] getNucleic() throws StarBiochemException
	{
		if(null != getPrb())
		{
			String script = getPrb().getString(GET_NUCLEIC_SCRIPT);
			if((null != script) && !EMPTY_STRING.equals(script))
			{
				return getMoleculeElements(script);
			}
		}
		return null;
	}

	public String[] getProtein() throws StarBiochemException
	{
		if(null != getPrb())
		{
			String script = getPrb().getString(GET_PROTEIN_SCRIPT);
			if((null != script) && !EMPTY_STRING.equals(script))
			{
				return getMoleculeElements(script);
			}
		}
		return null;
	}

	public String[] getHetero() throws StarBiochemException
	{
		if(null != getPrb())
		{
			String script = getPrb().getString(GET_HETERO_SCRIPT);
			if((null != script) && !EMPTY_STRING.equals(script))
			{
				return getMoleculeElements(script);
			}
		}
		return null;
	}

	public String[] getWater() throws StarBiochemException
	{
		if(null != getPrb())
		{
			String script = getPrb().getString(GET_WATER_SCRIPT);
			if((null != script) && !EMPTY_STRING.equals(script))
			{
				return getMoleculeElements(script);
			}
		}
		return null;
	}
	
	public Bond[] getHbonds() throws StarBiochemException
	{
		if(null != getPrb())
		{
			String script = getPrb().getString(GET_HBONDS_SCRIPT);
			if((null != script) && !EMPTY_STRING.equals(script))
			{
				return getHbonds(script);
			}
		}
		return null;
	}
	
	private Bond[] getHbonds(String script) throws StarBiochemException
	{
		Bond[] bonds = null;
		if((null != script) && (null != getJmolViewer()))
		{
			MySelectionListener mySelectionListener = new MySelectionListener();
		    getJmolViewer().addSelectionListener(mySelectionListener);
		    
		    getJmolViewer().scriptWait(script);
	
			int selectionCount = getJmolViewer().getSelectionCount();
			if(0 != selectionCount)
			{
				BitSet bitSet = mySelectionListener.getSelectionBitSet();
				
				bonds =  extractHbonds(bitSet);		
			}
			getJmolViewer().removeSelectionListener(mySelectionListener);
		}
		return bonds;
	}

	public Bond[] extractHbonds(BitSet bitSet) throws StarBiochemException
	{
		Bond[] bonds = null;
		if(getJmolViewer() instanceof org.jmol.viewer.Viewer)
		{
			org.jmol.viewer.Viewer viewer = (org.jmol.viewer.Viewer)getJmolViewer();
			ModelSet ms = viewer.getModelSet(); 
			if(null != ms)
			{
				List<Map<String,Object>> bondInfoList = ms.getAllBondInfo(bitSet);
				if(null != bondInfoList && !bondInfoList.isEmpty())
				{
					Iterator<Map<String,Object>> bondInfoListIterator = bondInfoList.iterator();
					if(null != bondInfoListIterator)
					{
						ArrayList<Bond> bondArrayList = new ArrayList<Bond>();
						while(bondInfoListIterator.hasNext())
						{
							Bond bond = extractHbond(bondInfoListIterator.next());
							if(null != bond)
							{
								bondArrayList.add(bond);
							}
						}
						if(!bondArrayList.isEmpty())
						{
							bonds = bondArrayList.toArray(new Bond[0]);
						}
					}
				}
			}
		}
		return bonds;
	}
					
	private Bond extractHbond(Object bondInfo)
	{
		Bond bond = null;
		if((null != bondInfo) && (bondInfo instanceof Hashtable))
		{
			Hashtable<?,?> htBondInfo = (Hashtable<?,?>)bondInfo;
			Enumeration<?> keys = htBondInfo.keys();
			if(null != keys)
			{
				String atom1 = null;
				String atom2 = null;
				while(keys.hasMoreElements())
				{
					Object key = keys.nextElement();
					if("atom1".equals(key)) //$NON-NLS-1$
					{
						atom1 = getHBondAtom(htBondInfo, key);
					}
					else if("atom2".equals(key)) //$NON-NLS-1$
					{
						atom2 = getHBondAtom(htBondInfo, key);
					}					
				}
				if((atom1 != null) && (atom2 != null))
				{
					bond = new molecule.Bond(atom1, atom2);
				}
			}
		}
		return bond;
	}
	
	private String getHBondAtom(Hashtable<?,?> htBondInfo, Object key)
	{
		Object atomObj = htBondInfo.get(key);
		if(atomObj instanceof Hashtable)
		{
			Hashtable<?,?> atomHtable = (Hashtable<?,?>) atomObj;
			Enumeration<?> enumHtable = atomHtable.keys();
			if(null != enumHtable)
			{
				while(enumHtable.hasMoreElements())
				{	
					Object atomInfoKey = enumHtable.nextElement();
					if("info".equals(atomInfoKey)) //$NON-NLS-1$
					{
						Object atomInfoObject = atomHtable.get(atomInfoKey);
						if((null != atomInfoObject) && (atomInfoObject instanceof String))
						{
							String atomInfo = getAtomId((String) atomInfoObject);
							
							if(atomInfo != null)
							{
								return atomInfo;
							}
						}
					}
				}
			}
		}
		return null;
	}
	
	private String getAtomId(String atom)
	{
		String id = null;
		if(null != atom)
		{
			Pattern p = Pattern.compile("([^\\/]+)\\/");  //$NON-NLS-1$
			Matcher m = p.matcher(atom);
			boolean match = m.find();
			if(match)
			{
				id = m.group(1).trim();
			}
			if(!match)
			{
				Pattern myP = Pattern.compile("([^\\#]+)\\#");  //$NON-NLS-1$
				Matcher myM = myP.matcher(atom);
				boolean myMatch = myM.find();
				if(myMatch)
				{
					id = myM.group(1).trim();
				}
			}	
		}
		return id;
	}
	
	public String[] getSSBonds() throws StarBiochemException
	{
		if(null != getPrb())
		{
			String script = getPrb().getString(GET_SSBONDS_SCRIPT);
			if((null != script) && !EMPTY_STRING.equals(script))
			{
				return getMoleculeElements(script);			
			}
		}
		return null;
	}
	
	public boolean hasQuaternarySSBonds() throws StarBiochemException
	{
		int count = 0;
		String[] ssbonds = getSSBonds();
	    if((null != ssbonds) && (0 != ssbonds.length))
	    {
			ArrayList<String> chains = new ArrayList<String>();
			for(int i = 0; ssbonds.length != i; i++)
		    {
		    	String myChainID = null;
				String chain = getChainId(ssbonds[i].toString());
				if(!chain.equals(myChainID))
				{
					myChainID = chain;
					chains.add(chain);
				}
		    }

			String format = getPrb().getString(IS_QUATERNARY_SSBONDS_SCRIPT);
			if((null != format) && !EMPTY_STRING.equals(format))
			{
		    	String[] chainArray = chains.toArray(new String[0]);
				for(int i = 0; chainArray.length != i; i++)
				{
					String selectionString = chainArray[i].toString();
					for(int j = i+1; chainArray.length != j; j++)
					{
						String connectedString = chainArray[j].toString();
						if(!selectionString.equals(connectedString))
						{
							String script = String.format(format, selectionString, connectedString);
						    script(script);
					
							int selectioncount = getJmolViewer().getSelectionCount();
							String error = getJmolViewer().getErrorMessage();
							if(null != error && !error.equals(EMPTY_STRING))
							{
								Thread.dumpStack();
								throw new StarBiochemException(StarBiochemException.JMOL_OUT_OF_MEMORY);
							}
							count += selectioncount;
						}
						if(count != 0)
						{
							return true;
						}
					}
				}
			}
	    }
		return false;
	}
	
	public boolean hasTertiarySSBonds() throws StarBiochemException
	{
		int count = 0;
		boolean hasTertiarySSBonds = false;
		String[] ssbonds = getSSBonds();
	    if((null != ssbonds) && (0 != ssbonds.length))
	    {
	    	String myChainID = null;
			String format = getPrb().getString(IS_TERTIARY_SSBONDS_SCRIPT);
			for(int i = 0; ssbonds.length != i; i++)
		    {
				String chain = getChainId(ssbonds[i].toString());
				if(!chain.equals(myChainID))
				{
					String script = String.format(format, chain);

				    script(script);
			
					int selectioncount = getJmolViewer().getSelectionCount();
					String error = getJmolViewer().getErrorMessage();
					if(null != error && !error.equals(EMPTY_STRING))
					{
						Thread.dumpStack();
						throw new StarBiochemException(StarBiochemException.JMOL_OUT_OF_MEMORY);
					}
					count += selectioncount;
					myChainID = chain;
		    	}
				if(count != 0)
				{
					hasTertiarySSBonds = true;
					return hasTertiarySSBonds;
				}
		    }
	    }
		return hasTertiarySSBonds;
	}
	
	private String getChainId(String atom)
	{	
		String chain = null;
		if(null != atom)
		{
			chain = atom.substring(atom.length()-1);				
		}		
		return chain;
	}
	
	private String[] getMoleculeElements(String script) throws StarBiochemException
	{
		if((null != script) && (null != getJmolViewer()))
		{
			MySelectionListener mySelectionListener = new MySelectionListener();
		    getJmolViewer().addSelectionListener(mySelectionListener);
			String error = getJmolViewer().getErrorMessage();
			if(null != error && !error.equals(EMPTY_STRING))
			{
				Thread.dumpStack();
				throw new StarBiochemException(StarBiochemException.JMOL_OUT_OF_MEMORY);
			}
		    
		    script(script);
	
			int atomCount = getJmolViewer().getSelectionCount();
			error = getJmolViewer().getErrorMessage();
			if(null != error && !error.equals(EMPTY_STRING))
			{
				Thread.dumpStack();
				throw new StarBiochemException(StarBiochemException.JMOL_OUT_OF_MEMORY);
			}
			BitSet bitSet = mySelectionListener.getSelectionBitSet();
		    	    
			getJmolViewer().removeSelectionListener(mySelectionListener);
			error = getJmolViewer().getErrorMessage();
			if(null != error && !error.equals(EMPTY_STRING))
			{
				Thread.dumpStack();
				throw new StarBiochemException(StarBiochemException.JMOL_OUT_OF_MEMORY);
			}
	
			return extractArray(atomCount, bitSet, TERMINATOR_DOT);
		}
		return null;
	}

	public String[] extractArray(int count, BitSet bitset, String terminator) throws StarBiochemException
	{
		if((null != bitset) && (null != terminator))
		{
			ArrayList<String> arrayList = new ArrayList<String>();
			String current = null;
			int bitIndex = 0;
			for(int i=0; count != i; i++)
			{
				bitIndex = bitset.nextSetBit(bitIndex);
				String atomInfo = getJmolViewer().getAtomInfo(bitIndex++);
				String error = getJmolViewer().getErrorMessage();
				if(null != error && !error.equals(EMPTY_STRING))
				{
					Thread.dumpStack();
					throw new StarBiochemException(StarBiochemException.JMOL_OUT_OF_MEMORY);
				}

				int index = atomInfo.indexOf(terminator);
				if(-1 != index)
				{
					String temp = atomInfo.substring(0,index);
					if(!temp.equals(current))
					{
						if(!arrayList.contains(temp))
						{
							arrayList.add(temp);
						}
						current = temp;
					} 
				}
			}
			return arrayList.toArray(new String[0]);
		}
		return null;
	}
	
	class MySelectionListener implements JmolSelectionListener
	{
		BitSet selectionBitSet = null;
		public BitSet getSelectionBitSet()
		{
			return this.selectionBitSet;
		}
		
		public void selectionChanged(BitSet selectionBitSet)
	    {
			this.selectionBitSet = (selectionBitSet != null) ? (BitSet) selectionBitSet.clone() : null;
	    }
		
	}
	
	String getJmolPropertiesScript(RenderingInfo ri)
	{
		if((null != ri) && (null != getPrb()))
		{
			String brightnessScript = ((-1 == ri.getBrightness())  ? EMPTY_STRING : (String.format(getPrb().getString(JMOL_BRIGHTNESS_FORMAT),Integer.toString(ri.getBrightness()))));
			String diffuseScript = ((-1 == ri.getDiffuse())  ? EMPTY_STRING : (String.format(getPrb().getString(JMOL_DIFFUSE_FORMAT),Integer.toString(ri.getDiffuse()))));
			String segmentScript = ((-1 == ri.getSegments())  ? EMPTY_STRING : (String.format(getPrb().getString(JMOL_SEGMENT_FORMAT),Integer.toString(ri.getSegments()))));
			String specularScript = ((-1 == ri.getSpecular())  ? EMPTY_STRING : (String.format(getPrb().getString(JMOL_SPECULAR_FORMAT),Integer.toString(ri.getSpecular()))));
			
			String propertiesScript = brightnessScript + diffuseScript + segmentScript + specularScript;
			return propertiesScript;
		}
		return EMPTY_STRING;
	}

	String getJmolFilteredSelectionScript(RenderingInfo ri)
	{
		return "select none; select all;"; //$NON-NLS-1$
	}

}
