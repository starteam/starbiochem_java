package molecule.ui.adjust.center.protein;

import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import molecule.interfaces.Molecule;
import molecule.ui.adjust.center.protein.primary.CenterProteinPrimaryPanel;
import molecule.ui.adjust.center.protein.primary.signal.CenterProteinPrimaryRaiser;
import molecule.ui.adjust.center.protein.quaternary.CenterProteinQuaternaryPanel;
import molecule.ui.adjust.center.protein.quaternary.signal.CenterProteinQuaternaryRaiser;
import molecule.ui.adjust.center.protein.secondary.CenterProteinSecondaryPanel;
import molecule.ui.adjust.center.protein.secondary.signal.CenterProteinSecondaryRaiser;
import molecule.ui.adjust.center.protein.tertiary.CenterProteinTertiaryPanel;
import molecule.ui.adjust.center.protein.tertiary.signal.CenterProteinTertiaryRaiser;
import molecule.ui.adjust.signal.SelectionRaiser;
import star.annotations.Handles;
import star.annotations.SignalComponent;
import app.Messages;


@star.annotations.Preferences
@SignalComponent(extend = JTabbedPane.class)
public class CenterProteinPanel extends CenterProteinPanel_generated
{
	private static final long serialVersionUID = 1L;

	public String protein_string = Messages.getString("CenterProteinPanel.0"); //$NON-NLS-1$
	
	transient private Molecule molecule = null;
	transient private CenterProteinPrimaryPanel proteinPrimaryPanel = null;
	transient private CenterProteinSecondaryPanel proteinSecondaryPanel = null;
	transient private CenterProteinTertiaryPanel proteinTertiaryPanel = null;
	transient private CenterProteinQuaternaryPanel proteinQuaternaryPanel = null;
	transient private ImageIcon icon = null;
	
	private CenterProteinPanel(Molecule molecule)
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

			proteinPrimaryPanel = new CenterProteinPrimaryPanel(molecule);
			if(null != proteinPrimaryPanel)
			{
				addTab(proteinPrimaryPanel.getTitle(), proteinPrimaryPanel);
			}

			proteinSecondaryPanel = new CenterProteinSecondaryPanel(molecule);
			if(null != proteinSecondaryPanel)
			{
				addTab(proteinSecondaryPanel.getTitle(), proteinSecondaryPanel);
			}

			proteinTertiaryPanel = new CenterProteinTertiaryPanel(molecule);
			if(null != proteinTertiaryPanel)
			{
				addTab(proteinTertiaryPanel.getTitle(), proteinTertiaryPanel);
			}

			proteinQuaternaryPanel = new CenterProteinQuaternaryPanel(molecule);
			if(null != proteinQuaternaryPanel)
			{
				addTab(proteinQuaternaryPanel.getTitle(), proteinQuaternaryPanel);
			}

		}
	}

	private void end()
	{
		removeAll();
		proteinPrimaryPanel = null;
		proteinSecondaryPanel = null;
		proteinTertiaryPanel = null;
		proteinQuaternaryPanel = null;
		icon = null;
	}
	
	transient private boolean inInitTree = false;
	public synchronized void initTree()
	{
		if(!inInitTree)
		{
			inInitTree = true;
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
			inInitTree = false;
		}
	}

	transient private boolean inReset = false;
	public synchronized void reset()
	{
		if(!inReset)
		{
			inReset = true;
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
			inReset = false;
		}
	}

	private void setTabIcon(SelectionRaiser raiser)
	{
		if((null != raiser) && (this.getSelectedComponent().equals(raiser) || (this.getSelectedComponent().equals(raiser.getAdapter().getParent()))))
		{
			int index = this.getSelectedIndex();
			String[] selection = raiser.getSelection();
			if((null != selection) && (selection.length > 0))
			{
				this.setIconAt(index, icon);
			}
			else
			{
				this.setIconAt(index, null);
			}
			invalidate();
			validate();
		}
	}
	
	@Handles(raises = {})
	protected void handleCenterProteinPrimaryRaiser(CenterProteinPrimaryRaiser raiser)
	{
		setTabIcon(raiser);
	}

	@Handles(raises = {})
	protected void handleCenterProteinSecondaryRaiser(CenterProteinSecondaryRaiser raiser)
	{
		setTabIcon(raiser);
	}

	@Handles(raises = {})
	protected void handleCenterProteinTertiaryRaiser(CenterProteinTertiaryRaiser raiser)
	{
		setTabIcon(raiser);
	}

	@Handles(raises = {})
	protected void handleCenterProteinQuaternaryRaiser(CenterProteinQuaternaryRaiser raiser)
	{
		setTabIcon(raiser);
	}


	protected void loadPreferences(String preferencesName)
	{
		protein_string = getPreferences(preferencesName).get("protein_string", protein_string).trim(); //$NON-NLS-1$
	}
	
	public static CenterProteinPanel getDefaultCenterProteinPanel(Molecule molecule)
	{
		if(null != molecule)
		{
			String[] array = molecule.getProteinArray();
			if((null != array) && (0 != array.length))
			{
				return new CenterProteinPanel(molecule);
			}
		}
		return null;
	}

}