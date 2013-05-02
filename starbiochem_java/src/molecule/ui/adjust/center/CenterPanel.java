package molecule.ui.adjust.center;

import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;

import molecule.interfaces.Molecule;
import molecule.ui.adjust.center.hetero.CenterHeteroListPanel;
import molecule.ui.adjust.center.hetero.signal.CenterHeteroSelectionRaiser;
import molecule.ui.adjust.center.nucleic.CenterNucleicPanel;
import molecule.ui.adjust.center.nucleic.signal.CenterNucleicSelectionRaiser;
import molecule.ui.adjust.center.protein.CenterProteinPanel;
import molecule.ui.adjust.center.water.CenterWaterListPanel;
import molecule.ui.adjust.center.water.signal.CenterWaterSelectionRaiser;
import molecule.ui.adjust.signal.SelectionRaiser;
import star.annotations.Handles;
import star.annotations.SignalComponent;
import app.Messages;

@star.annotations.Preferences
@SignalComponent(extend = JTabbedPane.class)
public class CenterPanel extends CenterPanel_generated
{
	private static final long serialVersionUID = 1L;
	
	private Molecule molecule = null;
	private CenterNucleicPanel nucleicPanel = null;
	private CenterHeteroListPanel heteroPanel = null;
	private CenterProteinPanel proteinPanel = null;
	private CenterWaterListPanel waterPanel = null;
	
	public CenterPanel(Molecule molecule)
	{
		super(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT);
		this.molecule = molecule;
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

	transient private ImageIcon icon = null;
	private void init()
	{
		loadPreferences("adjust"); //$NON-NLS-1$
		setBorder(new TitledBorder(adjustTitleBorder));
		initLocalVariables();
	}
	
	private void initLocalVariables()
	{
		if(null != molecule)
		{
			proteinPanel = CenterProteinPanel.getDefaultCenterProteinPanel(molecule);
			if(null != proteinPanel)
			{
				addTab(proteinPanel.getTitle(), proteinPanel);
			}
			heteroPanel = CenterHeteroListPanel.getDefaultCenterHeteroListPanel(molecule);
			if(null != heteroPanel)
			{
				addTab(heteroPanel.getTitle(), heteroPanel);
			}
			nucleicPanel = CenterNucleicPanel.getDefaultCenterNucleicPanel(molecule);
			if(null != nucleicPanel)
			{
				addTab(nucleicPanel.getTitle(), nucleicPanel);
			}
			waterPanel = CenterWaterListPanel.getDefaultCenterWaterListPanel(molecule);
			if(null != waterPanel)
			{
				addTab(waterPanel.getTitle(), waterPanel);
			}
		}
	}	
	
	public Insets getInsets()
	{
		return new Insets(20,3,10,3);
	}

	public Insets getInsets(Insets insets)
	{
		return new Insets(20,3,10,3);
	}	

	private void end()
	{
		removeAll();
		proteinPanel = null;
		heteroPanel = null;
		nucleicPanel = null;
		waterPanel = null;
	}
	
	private boolean inInitTree = false;
	public void initTree()
	{
		if(!inInitTree)
		{
			inInitTree = true;
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
			inInitTree = false;
		}
	}
	private boolean inReset = false;
	public void reset()
	{
		if(!inReset)
		{
			inReset = true;
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
	protected void handleCenterHeteroSelectionRaiser(CenterHeteroSelectionRaiser raiser)
	{
		setTabIcon(raiser);
	}

	@Handles(raises = {})
	protected void handleCenterNucleicSelectionRaiser(CenterNucleicSelectionRaiser raiser)
	{
		setTabIcon(raiser);
	}

	@Handles(raises = {})
	protected void handleCenterWaterSelectionRaiser(CenterWaterSelectionRaiser raiser)
	{
		setTabIcon(raiser);
	}
	
	String adjustTitleBorder = Messages.getString("CenterPanel.1"); //$NON-NLS-1$
	
	protected void loadPreferences(String preferencesName)
	{
		adjustTitleBorder = getPreferences(preferencesName).get("title_string", adjustTitleBorder.trim()); //$NON-NLS-1$
	}
	
}
