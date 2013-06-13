package molecule.ui.protein;

import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import molecule.interfaces.Molecule;
import molecule.ui.protein.primary.ProteinPrimaryPanel;
import molecule.ui.protein.quaternary.ProteinQuaternaryPanel;
import molecule.ui.protein.secondary.ProteinSecondaryPanel;
import molecule.ui.protein.tertiary.ProteinTertiaryPanel;
import star.annotations.SignalComponent;
import app.Messages;

@star.annotations.Preferences
@SignalComponent(extend = JTabbedPane.class)
public class ProteinPanel extends ProteinPanel_generated
{
	private static final long serialVersionUID = 1L;

	private String protein_string = Messages.getString("ProteinPanel.0"); //$NON-NLS-1$

	transient private Molecule molecule = null;
	transient private ProteinPrimaryPanel proteinPrimaryPanel = null;
	transient private ProteinSecondaryPanel proteinSecondaryPanel = null;
	transient private ProteinTertiaryPanel proteinTertiaryPanel = null;
	transient private ProteinQuaternaryPanel proteinQuaternaryPanel = null;
	
	private ProteinPanel(Molecule molecule)
	{
		super();
		this.molecule = molecule;
		loadPreferences("protein"); //$NON-NLS-1$
	}
	
	public String getTitle()
	{
		return protein_string;
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

			proteinPrimaryPanel = new ProteinPrimaryPanel(molecule);
			proteinSecondaryPanel = new ProteinSecondaryPanel(molecule);
			proteinTertiaryPanel = new ProteinTertiaryPanel(molecule);
			proteinQuaternaryPanel = new ProteinQuaternaryPanel(molecule);

			addTab(proteinPrimaryPanel.getTitle(), proteinPrimaryPanel);
			addTab(proteinSecondaryPanel.getTitle(), proteinSecondaryPanel);
			addTab(proteinTertiaryPanel.getTitle(), proteinTertiaryPanel);
			addTab(proteinQuaternaryPanel.getTitle(), proteinQuaternaryPanel);
		}
	}

	private void end()
	{
		removeAll();
		proteinPrimaryPanel = null;
		proteinSecondaryPanel = null;
		proteinTertiaryPanel = null;
		proteinQuaternaryPanel = null;
	}
	
	public synchronized void initTree()
	{
		if(null != proteinPrimaryPanel)
		{
			proteinPrimaryPanel.initTree();
		}
		if(null != proteinSecondaryPanel)
		{
			proteinSecondaryPanel.initTree();
		}
		if(null != proteinTertiaryPanel)
		{
			proteinTertiaryPanel.initTree();
		}
		if(null != proteinQuaternaryPanel)
		{
			proteinQuaternaryPanel.initTree();
		}
	}

	public synchronized void reset()
	{
		if(null != proteinPrimaryPanel)
		{
			proteinPrimaryPanel.reset();
		}
		if(null != proteinSecondaryPanel)
		{
			proteinSecondaryPanel.reset();
		}
		if(null != proteinTertiaryPanel)
		{
			proteinTertiaryPanel.reset();
		}
		if(null != proteinQuaternaryPanel)
		{
			proteinQuaternaryPanel.reset();
		}
	}

	protected void loadPreferences(String preferencesName)
	{
		protein_string = getPreferences(preferencesName).get("protein_string", protein_string).trim(); //$NON-NLS-1$
	}
	
	public static ProteinPanel getDefaultProteinPanel(Molecule molecule)
	{
		if(null != molecule)
		{
			String[] array = molecule.getProteinArray();
			if((null != array) && (0 != array.length))
			{
				return new ProteinPanel(molecule);
			}
		}
		return null;
	}

}