package molecule.ui;

import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import molecule.interfaces.Molecule;
import molecule.ui.hetero.HeteroPanel;
import molecule.ui.nucleic.NucleicPanel;
import molecule.ui.protein.ProteinPanel;
import molecule.ui.water.WaterPanel;
import star.annotations.SignalComponent;
import app.Messages;

@star.annotations.Preferences
@SignalComponent(extend = JTabbedPane.class)
public class StructurePanel extends StructurePanel_generated
{
	private static final long serialVersionUID = 1L;
	
	private Molecule molecule = null;
	private NucleicPanel nucleicPanel = null;
	private HeteroPanel heteroPanel = null;
	private ProteinPanel proteinPanel = null;
	private WaterPanel waterPanel = null;

	private String structure_string = Messages.getString("StructurePanel.0"); //$NON-NLS-1$
	public String getTitle()
	{
		return structure_string;
	}
	
	public StructurePanel(Molecule molecule)
	{
		super(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT);
		this.molecule = molecule;
		loadPreferences("protein"); //$NON-NLS-1$
	}
	
	private boolean isInitialized = false;
	public void addNotify()
	{
		super.addNotify();
		if(!isInitialized)
		{
			init();
			isInitialized = true;
		}
	}

	public void removeNotify()
	{
		end();
		isInitialized = false;
		super.removeNotify();
	}

	private void init()
	{
		if(null != molecule)
		{
			this.setBorder(new EmptyBorder(0,0,0,0));
			proteinPanel = ProteinPanel.getDefaultProteinPanel(molecule);
			if(null != proteinPanel)
			{
				addTab(proteinPanel.getTitle(), proteinPanel);
			}
			heteroPanel = HeteroPanel.getDefaultHeteroPanel(molecule);
			if(null != heteroPanel)
			{
				addTab(heteroPanel.getTitle(), heteroPanel);
			}
			nucleicPanel = NucleicPanel.getDefaultNucleicPanel(molecule);
			if(null != nucleicPanel)
			{
				addTab(nucleicPanel.getTitle(), nucleicPanel);
			}
			waterPanel = WaterPanel.getDefaultWaterPanel(molecule);
			if(null != waterPanel)
			{
				addTab(waterPanel.getTitle(), waterPanel);
			}
		}
	}

	private void end()
	{
		removeAll();
		proteinPanel = null;
		heteroPanel = null;
		nucleicPanel = null;
		waterPanel = null;
		structure_string = null;
	}
	
	public void initTree()
	{
		if(null != heteroPanel)
		{
			heteroPanel.initTree();
		}
		if(null != nucleicPanel)
		{
			nucleicPanel.initTree();
		}
		if(null != proteinPanel)
		{
			proteinPanel.initTree();
		}
		if(null != waterPanel)
		{
			waterPanel.initTree();
		}
	}
	
	public void reset()
	{
		if(null != heteroPanel)
		{
			heteroPanel.reset();
		}
		if(null != nucleicPanel)
		{
			nucleicPanel.reset();
		}
		if(null != proteinPanel)
		{
			proteinPanel.reset();
		}
		if(null != waterPanel)
		{
			waterPanel.reset();
		}
	}

	protected void loadPreferences(String preferencesName)
	{
		structure_string = getPreferences(preferencesName).get("structure_string", structure_string).trim(); //$NON-NLS-1$
	}
	
	public static StructurePanel getDefaultStructurePanel(Molecule molecule)
	{
		if(null != molecule)
		{
			String[] hetero = molecule.getHeteroNotWaterNotNucleicArray();
			String[] nucleic = molecule.getNucleicArray();
			String[] protein = molecule.getProteinArray();
			String[] water = molecule.getWaterArray();
			boolean hasHetero = ((null != hetero) && (0 != hetero.length));
			boolean hasNucleic = ((null != nucleic) && (0 != nucleic.length));
			boolean hasProtein = ((null != protein) && (0 != protein.length));
			boolean hasWater = ((null != water) && (0 != water.length));
			if(hasHetero || hasNucleic || hasProtein || hasWater)
			{
				return new StructurePanel(molecule);
			}
		}
		return null;
	}

}
