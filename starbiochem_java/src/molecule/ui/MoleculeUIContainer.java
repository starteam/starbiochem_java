package molecule.ui;

import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import molecule.interfaces.Molecule;
import molecule.ui.info.InfoPanel;
import star.annotations.SignalComponent;

@SignalComponent(extend = JTabbedPane.class)
public class MoleculeUIContainer extends MoleculeUIContainer_generated
{
	private static final long serialVersionUID = 1L;
	
	private Molecule molecule = null;
	private StructurePanel structurePanel = null;
	private InfoPanel infoPanel = null;
	
	public MoleculeUIContainer(Molecule molecule)
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

	private void init()
	{
		this.setBorder(new EmptyBorder(0,0,0,0));
		if(null != molecule)
		{
			structurePanel = StructurePanel.getDefaultStructurePanel(molecule);
			if(null != structurePanel)
			{
				addTab(structurePanel.getTitle(), structurePanel);
				infoPanel = InfoPanel.getDefaultInfoPanel(molecule);
				if(null != infoPanel)
				{
					addTab(infoPanel.getTitle(), infoPanel);
				}
			}
			
		}
	}

	private void end()
	{
		removeAll();
	}
	
	public void initTree()
	{
		if(null != structurePanel)
		{
			structurePanel.initTree();
		}
		if(null != infoPanel)
		{
			infoPanel.initTree();
		}
	}

	public void reset()
	{
		if(null != structurePanel)
		{
			structurePanel.reset();
		}
		if(null != infoPanel)
		{
			infoPanel.reset();
		}
	}

}
