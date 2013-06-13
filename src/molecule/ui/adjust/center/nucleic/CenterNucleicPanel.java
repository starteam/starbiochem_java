package molecule.ui.adjust.center.nucleic;

import molecule.interfaces.Molecule;
import molecule.ui.AbstractMoleculeUI;
import molecule.ui.signal.RenderingInfoRaiser;
import molecule.ui.adjust.center.nucleic.signal.CenterNucleicFilterRaiser;
import molecule.ui.adjust.center.nucleic.signal.CenterNucleicRaiser;
import molecule.ui.adjust.center.nucleic.signal.CenterNucleicSelectionRaiser;
import star.annotations.Handles;
import star.annotations.SignalComponent;
import app.Messages;

@star.annotations.Preferences
@SignalComponent(extend = AbstractMoleculeUI.class, raises = {CenterNucleicRaiser.class})
public class CenterNucleicPanel extends CenterNucleicPanel_generated
{
	private static final long serialVersionUID = 1L;

	private String centernucleic_string = Messages.getString("CenterNucleicPanel.0"); //$NON-NLS-1$
	
	transient private Molecule molecule = null;
	transient private CenterNucleicFiltersPanel filters = null;
	transient private CenterNucleicListPanel list = null;
	
	public CenterNucleicPanel(Molecule molecule)
	{
		super();
		this.molecule = molecule;
	}
	
	public String getTitle()
	{
		return centernucleic_string;
	}

	protected void initialize()
	{
		filters = new CenterNucleicFiltersPanel();
		if(null != this.molecule)
		{
			list = CenterNucleicListPanel.getDefaultCenterNucleicListPanel(this.molecule);
		}
		initialize(filters, list, null);
	}

    protected void cleanup()
    {
    	filters = null;
    	list = null;
    }

	transient private boolean inInitTree = false;
	public void initTree()
	{
		if(!inInitTree)
		{
			inInitTree = true;
			resetLocalVariables();
			if(null != filters)
			{
				filters.initTree();
			}
			if(null != list)
			{
				list.initTree();
			}
			inInitTree = false;
		}
	}

	transient private boolean inReset = false;
	public void reset()
	{
		if(!inReset)
		{
			inReset = true;
			resetLocalVariables();
			if(null != filters)
			{
				filters.reset();
			}
			if(null != list)
			{
				list.reset();
			}
			inReset = false;
		}
	}

	private void resetLocalVariables()
	{
		this.selection = null;;
		this.filterOptions = RenderingInfoRaiser.BASE_VISIBLE | RenderingInfoRaiser.PHOSPHATE_VISIBLE | RenderingInfoRaiser.SUGAR_VISIBLE ;
	}
	
	private String[] selection = null;
	public String[] getSelection()
	{
		return selection;
	}
	
	private int filterOptions = RenderingInfoRaiser.BASE_VISIBLE | RenderingInfoRaiser.PHOSPHATE_VISIBLE | RenderingInfoRaiser.SUGAR_VISIBLE ;
	public int getFilterOptions()
	{
		return this.filterOptions;
	}
	
	@Handles(raises = {})
	protected void handleCenterNucleicSelectionRaiser(CenterNucleicSelectionRaiser raiser)
	{
		if(null != raiser)
		{
			this.selection = raiser.getSelection();
			this.raise_CenterNucleicEvent();
		}
	}

	@Handles(raises = {})
	protected void handleCenterNucleicFilterRaiser(CenterNucleicFilterRaiser raiser)
	{
		if(null != raiser)
		{
			this.filterOptions = raiser.getFilterOptions();
			this.raise_CenterNucleicEvent();
		}
	}

	@Override
    protected void raiseRenderEvent()
    {
    }

	public static CenterNucleicPanel getDefaultCenterNucleicPanel(Molecule molecule)
	{
		if(null != molecule)
		{
			String[] array = molecule.getNucleicArray();
			if((null != array) && (0 != array.length))
			{
				return new CenterNucleicPanel(molecule);
			}
		}
		return null;
	}

}