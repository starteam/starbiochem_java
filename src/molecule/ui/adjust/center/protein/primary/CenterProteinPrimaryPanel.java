package molecule.ui.adjust.center.protein.primary;

import molecule.interfaces.Molecule;
import molecule.ui.AbstractMoleculeUI;
import molecule.ui.adjust.center.protein.primary.signal.CenterProteinPrimaryFilterRaiser;
import molecule.ui.adjust.center.protein.primary.signal.CenterProteinPrimaryRaiser;
import molecule.ui.adjust.center.protein.primary.signal.CenterProteinPrimarySelectionRaiser;
import star.annotations.Handles;
import star.annotations.SignalComponent;
import app.Messages;


@star.annotations.Preferences
@SignalComponent(extend = AbstractMoleculeUI.class, raises = {CenterProteinPrimaryRaiser.class})
public class CenterProteinPrimaryPanel extends CenterProteinPrimaryPanel_generated
{
	private static final long serialVersionUID = 1L;

	private String proteinprimary_string = Messages.getString("CenterProteinPrimaryPanel.0"); //$NON-NLS-1$
	
	transient private Molecule myMolecule = null;
	transient private CenterProteinPrimaryFiltersPanel filters = null;
	transient private CenterProteinPrimaryListPanel list = null;
	
	public CenterProteinPrimaryPanel(Molecule molecule)
	{
		super();
		this.myMolecule = molecule;
		loadPreferences("adjust_primary"); //$NON-NLS-1$
	}
	
	public String getTitle()
	{
		return proteinprimary_string;
	}

	protected void initialize()
	{
		filters = new CenterProteinPrimaryFiltersPanel();
		if(null != this.myMolecule)
		{
			list = CenterProteinPrimaryListPanel.getDefaultCenterProteinPrimaryListPanel(this.myMolecule);
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
		this.selection = null;
		this.filterOptions = 0;
        if (this.filters_proteinprimary_backbone_isselected)
        {
        	this.filterOptions |= molecule.ui.signal.RenderingInfoRaiser.BACKBONE_VISIBLE;
        }
        if (this.filters_proteinprimary_sidechain_isselected)
        {
        	this.filterOptions |= molecule.ui.signal.RenderingInfoRaiser.SIDECHAIN_VISIBLE;
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
	protected void handleCenterProteinPrimarySelectionRaiser(CenterProteinPrimarySelectionRaiser raiser)
	{
		if(null != raiser)
		{
			this.selection = raiser.getSelection();
			this.raise_CenterProteinPrimaryEvent();
		}
	}

	@Handles(raises = {})
	protected void handleCenterProteinPrimaryFilterRaiser(CenterProteinPrimaryFilterRaiser raiser)
	{
		if(null != raiser)
		{
			this.filterOptions = raiser.getFilterOptions();
			this.raise_CenterProteinPrimaryEvent();
		}
	}

	@Override
    protected void raiseRenderEvent()
    {
    }

	protected boolean filters_proteinprimary_backbone_isselected = true;
	protected boolean filters_proteinprimary_sidechain_isselected = true;
	
	protected void loadPreferences(String preferencesName)
	{
		String s = getPreferences(preferencesName).get("filters_proteinprimary_backbone_isselected", Boolean.toString(filters_proteinprimary_backbone_isselected)); //$NON-NLS-1$
		filters_proteinprimary_backbone_isselected = Boolean.parseBoolean(s);

		s = getPreferences(preferencesName).get("filters_proteinprimary_sidechain_isselected", Boolean.toString(filters_proteinprimary_sidechain_isselected)); //$NON-NLS-1$
		filters_proteinprimary_sidechain_isselected = Boolean.parseBoolean(s);

	}
}