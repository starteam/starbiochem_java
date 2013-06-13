package molecule.ui.adjust.center.protein.quaternary;

import molecule.interfaces.Molecule;
import molecule.ui.AbstractMoleculeUI;
import molecule.ui.adjust.center.protein.quaternary.signal.CenterProteinQuaternaryRaiser;
import molecule.ui.adjust.center.protein.quaternary.signal.CenterProteinQuaternarySelectionRaiser;
import star.annotations.Handles;
import star.annotations.SignalComponent;
import app.Messages;

@star.annotations.Preferences
@SignalComponent(extend = AbstractMoleculeUI.class, raises = {CenterProteinQuaternaryRaiser.class})
public class CenterProteinQuaternaryPanel extends CenterProteinQuaternaryPanel_generated
{
	private static final long serialVersionUID = 1L;

	private String proteinquaternary_string = Messages.getString("CenterProteinQuaternaryPanel.0"); //$NON-NLS-1$
	
	transient private Molecule molecule = null;
	transient private CenterProteinQuaternaryListPanel list = null;
	
	public CenterProteinQuaternaryPanel(Molecule molecule)
	{
		super();
		this.molecule = molecule;
	}
	
	public String getTitle()
	{
		return proteinquaternary_string;
	}

	protected void initialize()
	{
		if(null != this.molecule)
		{
			list = CenterProteinQuaternaryListPanel.getDefaultCenterProteinQuaternaryListPanel(this.molecule);
		}
		initialize(null, list, null);
	}

	protected void cleanup()
	{
		list = null;
	}
	
	transient private boolean inInitTree = false;
	public void initTree()
	{
		if(!inInitTree)
		{
			inInitTree = true;
			initLocalVariables();
			if(null != list)
			{
				list.initTree();
			}
			inInitTree = false;
		}
	}

	private void initLocalVariables()
	{
		this.selection = null;
	}
	
	transient private boolean inReset = false;
	public void reset()
	{
		if(!inReset)
		{
			inReset = true;
			resetLocalVariables();
			if(null != list)
			{
				list.reset();
			}
			inReset = false;
		}
	}

	private void resetLocalVariables()
	{
		this.selection = null;
	}
	
	private String[] selection = null;
	public String[] getSelection()
	{
		return selection;
	}
	
	@Handles(raises = {})
	protected void handleCenterProteinQuaternarySelectionRaiser(CenterProteinQuaternarySelectionRaiser raiser)
	{
		if(null != raiser)
		{
			this.selection = raiser.getSelection();
			this.raise_CenterProteinQuaternaryEvent();
		}
	}

	@Override
    protected void raiseRenderEvent()
    {
    }

}