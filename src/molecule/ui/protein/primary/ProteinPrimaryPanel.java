package molecule.ui.protein.primary;

import molecule.interfaces.Molecule;
import molecule.interfaces.RenderingInfo;
import molecule.ui.AbstractMoleculeUI;
import molecule.ui.protein.primary.signal.ProteinPrimaryFilterRaiser;
import molecule.ui.protein.primary.signal.ProteinPrimaryApplyRenderingRaiser;
import molecule.ui.protein.primary.signal.ProteinPrimaryBondTranslucencyRaiser;
import molecule.ui.protein.primary.signal.ProteinPrimaryRenderingModeRaiser;
import molecule.ui.protein.primary.signal.ProteinPrimarySelectionRaiser;
import molecule.ui.protein.primary.signal.ProteinPrimarySizeRaiser;
import molecule.ui.protein.primary.signal.ProteinPrimaryTranslucencyRaiser;
import molecule.ui.protein.tertiary.signal.ProteinTertiaryFilterRaiser;
import molecule.ui.protein.tertiary.signal.ProteinTertiaryRenderingModeRaiser;
import molecule.ui.protein.tertiary.signal.ProteinTertiarySSBondSizeRaiser;
import molecule.ui.protein.tertiary.signal.ProteinTertiarySSBondTranslucencyRaiser;
import molecule.ui.signal.RenderingInfoRaiser;
import star.annotations.Handles;
import star.annotations.SignalComponent;
import app.Messages;

@star.annotations.Preferences
@SignalComponent(extend = AbstractMoleculeUI.class, raises = {RenderingInfoRaiser.class})
public class ProteinPrimaryPanel extends ProteinPrimaryPanel_generated
{
	private static final long serialVersionUID = 1L;

	private String proteinprimary_string = Messages.getString("ProteinPrimaryPanel.0"); //$NON-NLS-1$
	
	transient private Molecule myMolecule = null;
	transient private ProteinPrimaryFiltersPanel filters = null;
	transient private ProteinPrimaryRenderingPanel properties = null;
	transient private ProteinPrimaryListPanel list = null;
	transient private boolean hasSSBonds = true;
	
	public ProteinPrimaryPanel(Molecule molecule)
	{
		super();
		this.myMolecule = molecule;
		loadPreferences("protein"); //$NON-NLS-1$
	}
	
	public String getTitle()
	{
		return proteinprimary_string;
	}

	protected void initialize()
	{
		filters = new ProteinPrimaryFiltersPanel();
		properties = new ProteinPrimaryRenderingPanel();
		if(null != this.myMolecule)
		{
			list = new ProteinPrimaryListPanel(this.myMolecule.getProteinArray());
		}
		initialize(filters, list, properties);
		hasSSBonds = hasSSBonds();
	}

	protected void cleanup()
	{
		filters = null;
		properties = null;
		list = null;
	}
	
	boolean hasSSBonds()
	{
		boolean ssbonds = myMolecule.hasTertiarySSBonds();
		
		return ssbonds;
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
			}
			if(null != properties)
			{
				properties.initTree();
			}
			if(null != list)
			{
				list.initTree();
			}
			initLocalVariables();
			inInitTree = false;
		}
	}
	
	private void initLocalVariables()
	{
		filter = 0;
		isAutomaticallyRendered = true;
		if(null != filters)
		{
			filter = filters.getDefaultFilter();
			isAutomaticallyRendered = filters.isDefaultAutomaticallyRendered();
		}
		
		bondTranslucency = 0;
		translucency = 0;
		size = 25;
		if(null != properties)
		{
			bondTranslucency = properties.getDefaultBondTranslucency();
			translucency = properties.getDefaultTranslucency();
			size = properties.getDefaultSize();
			ssBondSize = properties.getDefaultSSBondSize();
			ssBondTranslucency = properties.getDefaultSSBondTranslucency();
		}
		
		selection = null;
		if(null != list)
		{
			selection = list.getDefaultSelection();
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
			}
			if(null != properties)
			{
				properties.reset();
			}
			if(null != list)
			{
				list.reset();
			}
			resetLocalVariables();
			inReset = false;
		}
	}
	
	private void resetLocalVariables()
	{
		filter = 0;
		isAutomaticallyRendered = true;
		if(null != filters)
		{
			filter = filters.getDefaultFilter();
			isAutomaticallyRendered = filters.isDefaultAutomaticallyRendered();
		}
		
		bondTranslucency = 0;
		translucency = 0;
		size = 25;
		if(null != properties)
		{
			bondTranslucency = properties.getDefaultBondTranslucency();
			translucency = properties.getDefaultTranslucency();
			size = properties.getDefaultSize();
			ssBondSize = properties.getDefaultSSBondSize();
			ssBondTranslucency = properties.getDefaultSSBondTranslucency();
		}
		
		selection = null;
		if(null != list)
		{
			selection = list.getDefaultSelection();
		}
	}
	
	@Handles(raises = {})
	protected void handleProteinPrimaryRenderingModeRaiser(ProteinPrimaryRenderingModeRaiser raiser)
	{
		this.isAutomaticallyRendered = raiser.isAutomaticallyRendered();
		checkRaiseRenderEvent();
		checkRaiseCovalentBondRenderEvent();
	}

	@Handles(raises = {})
	protected void handleProteinTertiaryRenderingModeRaiser(ProteinTertiaryRenderingModeRaiser raiser)
	{
		// just track state -- don't render
		this.isAutomaticallyRendered = raiser.isAutomaticallyRendered();
	}

	@Handles(raises = {})
	protected void handleProteinPrimaryApplyRenderingRaiser(ProteinPrimaryApplyRenderingRaiser raiser)
	{
		raiseRenderEvent();
		raiseCovalentBondRenderEvent();
	}

	@Handles(raises = {})
	protected void handleProteinPrimaryFilterRaiser(ProteinPrimaryFilterRaiser raiser)
	{
		this.filter = raiser.getValue();
		checkRaiseRenderEvent();
		checkRaiseCovalentBondRenderEvent();
	}

    static final int backsideMASK = molecule.ui.signal.RenderingInfoRaiser.BACKBONE_VISIBLE | molecule.ui.signal.RenderingInfoRaiser.SIDECHAIN_VISIBLE;
	transient private boolean inHandleProteinTertiaryFilterRaiser = false;
	@Handles(raises = {})
	protected void handleProteinTertiaryFilterRaiser(ProteinTertiaryFilterRaiser raiser)
	{
		// just track state - don't raise render event
		if(!inHandleProteinTertiaryFilterRaiser && !inReset)
		{
			inHandleProteinTertiaryFilterRaiser = true;
    		int value = raiser.getFilterOptions();
    		this.filter = this.filter & ~backsideMASK;
    		this.filter = this.filter | value;
    		inHandleProteinTertiaryFilterRaiser = false;
		}
	}

	@Handles(raises = {})
	protected void handleProteinPrimarySelectionRaiser(ProteinPrimarySelectionRaiser raiser)
	{
		this.selection = raiser.getSelection();
		if(raiser.isFromProteinPrimarySelectionRaiser())
		{
			checkRaiseRenderEvent();
			checkRaiseCovalentBondRenderEvent();
		}
	}

	@Handles(raises = {})
	protected void handleProteinPrimaryBondTranslucencyRaiser(ProteinPrimaryBondTranslucencyRaiser raiser)
	{
		this.bondTranslucency = raiser.getValue();
		if(raiser.isFromProteinPrimaryBondTranslucencyRaiser())
		{
			checkRaiseCovalentBondRenderEvent();
		}
	}

	@Handles(raises = {})
	protected void handleProteinPrimaryTranslucencyRaiser(ProteinPrimaryTranslucencyRaiser raiser)
	{
		this.translucency = raiser.getValue();
		if(raiser.isFromProteinPrimaryTranslucencyRaiser())
		{
			checkRaiseRenderEvent();
		}
	}

	@Handles(raises = {})
	protected void handleProteinPrimarySizeRaiser(ProteinPrimarySizeRaiser raiser)
	{
		this.size = raiser.getValue();
		if(raiser.isFromProteinPrimarySizeRaiser())
		{
			checkRaiseRenderEvent();
		}
	}
	
	@Handles (raises = {})
	protected void handleProteinTertiarySSBondSizeRaiser(ProteinTertiarySSBondSizeRaiser raiser)
	{
		this.ssBondSize = raiser.getValue();
	}

	@Handles (raises = {})
	protected void handleProteinTertiarySSBondTranslucencyRaiser(ProteinTertiarySSBondTranslucencyRaiser raiser)
	{
		this.ssBondTranslucency = raiser.getValue();
	}	
	
	transient private boolean isAutomaticallyRendered = true;
	transient private int bondTranslucency = 0;
	transient private int filter = 0;
	transient private int translucency = 0;
	transient private String[] selection = null;
	transient private int size = 25;
	transient private int ssBondSize = 25;
	transient private int ssBondTranslucency = 0;

	protected void loadPreferences(String preferencesName)
	{
		proteinprimary_string = getPreferences(preferencesName).get("proteinprimary_string", proteinprimary_string).trim(); //$NON-NLS-1$
	}
	
	transient private RenderingInfo renderingInfo = null;
	public RenderingInfo getRenderingInfo()
	{
		return this.renderingInfo;
	}
	
	private void checkRaiseRenderEvent()
	{
		if(this.isAutomaticallyRendered)
		{
			raiseRenderEvent();
		}
	}
	private void checkRaiseCovalentBondRenderEvent()
	{
		if(this.isAutomaticallyRendered)
		{
			raiseCovalentBondRenderEvent();
		}
	}
	
	transient private boolean inRaiseRenderEvent = false;
	protected synchronized void raiseRenderEvent()
	{
		if(!inRaiseRenderEvent && !inReset)
		{
			inRaiseRenderEvent = true;
			renderingInfo = new molecule.RenderingInfo(myMolecule.getUrl(), RenderingInfoRaiser.PROTEIN_PRIMARY_STRUCTURE,
					this.selection,
					null,
					this.bondTranslucency,
					-1,
					-1,
					this.filter,
					this.translucency,
					-1,
					this.size,
					-1,
					0,
					0,
					0,
					-1,
					-1,
					-1,
					-1);
			this.raise_RenderingInfoEvent();
		}
		inRaiseRenderEvent = false;
	}
	
	boolean inRaiseCovalentBondRenderEvent = false;
	protected void raiseCovalentBondRenderEvent()
	{
		if(!inRaiseCovalentBondRenderEvent && !inReset)
		{
			inRaiseCovalentBondRenderEvent = true;
			renderingInfo = new molecule.RenderingInfo(myMolecule.getUrl(), RenderingInfoRaiser.PROTEIN_PRIMARY_COVALENT_BONDS,
					this.selection,
					null,
					this.bondTranslucency,
					-1,
					-1,
					this.filter,
					-1,
					-1,
					-1,
					-1,
					0,
					0,
					0,
					hasSSBonds ? this.ssBondSize : -1,
					hasSSBonds ? this.ssBondTranslucency : -1,
					-1,
					-1);
			this.raise_RenderingInfoEvent();
			inRaiseCovalentBondRenderEvent = false;
		}
	}
}