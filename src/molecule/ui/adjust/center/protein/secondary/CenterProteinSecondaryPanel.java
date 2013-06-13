package molecule.ui.adjust.center.protein.secondary;

import molecule.interfaces.Molecule;
import molecule.ui.AbstractMoleculeUI;
import molecule.ui.adjust.center.protein.secondary.signal.CenterProteinSecondaryFilterRaiser;
import molecule.ui.adjust.center.protein.secondary.signal.CenterProteinSecondaryRaiser;
import molecule.ui.adjust.center.protein.secondary.signal.CenterProteinSecondarySelectionRaiser;
import star.annotations.Handles;
import star.annotations.SignalComponent;
import app.Messages;

@star.annotations.Preferences
@SignalComponent(extend = AbstractMoleculeUI.class, raises = {CenterProteinSecondaryRaiser.class})
public class CenterProteinSecondaryPanel extends CenterProteinSecondaryPanel_generated
{
	private static final long serialVersionUID = 1L;

	private final String SECONDARY = Messages.getString("CenterProteinSecondaryPanel.0"); //$NON-NLS-1$

	private String proteinsecondary_string = SECONDARY;
	
	transient private Molecule molecule = null;
	transient private CenterProteinSecondaryFiltersPanel filters = null;
	transient private CenterProteinSecondaryListPanel list = null;
	
	public CenterProteinSecondaryPanel(Molecule molecule)
	{
		super();
		this.molecule = molecule;
		loadPreferences("adjust_secondary"); //$NON-NLS-1$
	}
	
	public String getTitle()
	{
		return proteinsecondary_string;
	}

	protected void initialize()
	{
		filters = new CenterProteinSecondaryFiltersPanel();
		if(null != this.molecule)
		{
			list = new CenterProteinSecondaryListPanel(this.molecule);
		}
		initialize(filters, list, null);
	}

	protected void cleanup()
	{
		filters = null;
		list = null;
	}
	
	boolean inInitTree = false;
	public synchronized void initTree()
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
	
	boolean inReset = false;
	public synchronized void reset()
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
		this.filterOptions = 0;
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
	protected void handleCenterProteinSecondarySelectionRaiser(CenterProteinSecondarySelectionRaiser raiser)
	{
		this.selection = raiser.getSelection();
		this.raise_CenterProteinSecondaryEvent();
	}

	@Handles(raises = {})
	protected void handleCenterProteinSecondaryFilterRaiser(CenterProteinSecondaryFilterRaiser raiser)
	{
		this.filterOptions = raiser.getFilterOptions();
		this.raise_CenterProteinSecondaryEvent();
	}

	@Override
    protected void raiseRenderEvent()
    {
    }

	protected void loadPreferences(String preferencesName)
	{
		proteinsecondary_string = getPreferences(preferencesName).get("proteinsecondary_string", proteinsecondary_string).trim(); //$NON-NLS-1$
	}
	
}