package molecule.ui.adjust.center.protein.tertiary;

import molecule.interfaces.Molecule;
import molecule.ui.AbstractMoleculeUI;
import molecule.ui.adjust.center.protein.tertiary.signal.CenterProteinTertiaryFilterRaiser;
import molecule.ui.adjust.center.protein.tertiary.signal.CenterProteinTertiaryRaiser;
import molecule.ui.adjust.center.protein.tertiary.signal.CenterProteinTertiarySelectionRaiser;
import star.annotations.Handles;
import star.annotations.SignalComponent;
import app.Messages;

@star.annotations.Preferences
@SignalComponent(extend = AbstractMoleculeUI.class, raises = {CenterProteinTertiaryRaiser.class})
public class CenterProteinTertiaryPanel extends CenterProteinTertiaryPanel_generated
{
	private static final long serialVersionUID = 1L;
	private final String TERTIARY = Messages.getString("CenterProteinTertiaryPanel.0"); //$NON-NLS-1$

	transient private String proteintertiary_string = TERTIARY;
	
	transient private Molecule molecule = null;
	transient private CenterProteinTertiaryFiltersPanel filters = null;
	transient private CenterProteinTertiaryListPanel list = null;
	
	public CenterProteinTertiaryPanel(Molecule molecule)
	{
		super();
		this.molecule = molecule;
	}
	
	public String getTitle()
	{
		return proteintertiary_string;
	}

	protected void initialize()
	{
		filters = new CenterProteinTertiaryFiltersPanel();
		if(null != this.molecule)
		{
			list = CenterProteinTertiaryListPanel.getDefaultCenterProteinTertiaryListPanel(molecule);
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
			if(null != filters)
			{
				filters.initTree();
				this.filterOptions = filters.getDefaultFilter();
			}
			if(null != list)
			{
				list.initTree();
				this.selection = list.getDefaultSelection();
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
			if(null != filters)
			{
				filters.reset();
				this.filterOptions = filters.getDefaultFilter();
			}
			if(null != list)
			{
				list.reset();
				this.selection = list.getDefaultSelection();
			}
			inReset = false;
		}
	}
	
	private String[] selection = null;
	public String[] getSelection()
	{
		return selection;
	}
	
	private int filterOptions = 0;
	public int getFilterOptions()
	{
		return this.filterOptions;
	}
	
	@Handles(raises = {})
	protected void handleCenterProteinTertiarySelectionRaiser(CenterProteinTertiarySelectionRaiser raiser)
	{
		if(null != raiser)
		{
			this.selection = raiser.getSelection();
			this.raise_CenterProteinTertiaryEvent();
		}
	}

	@Handles(raises = {})
	protected void handleCenterProteinTertiaryFilterRaiser(CenterProteinTertiaryFilterRaiser raiser)
	{
		if(null != raiser)
		{
			this.filterOptions = raiser.getFilterOptions();
			this.raise_CenterProteinTertiaryEvent();
		}
	}

	@Override
    protected void raiseRenderEvent()
    {
	    // TODO Auto-generated method stub  
    }
	
}