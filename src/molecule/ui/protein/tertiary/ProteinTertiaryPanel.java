package molecule.ui.protein.tertiary;

import molecule.interfaces.Molecule;
import molecule.interfaces.RenderingInfo;
import molecule.ui.AbstractMoleculeUI;
import molecule.ui.protein.primary.signal.ProteinPrimaryFilterRaiser;
import molecule.ui.protein.primary.signal.ProteinPrimaryRenderingModeRaiser;
import molecule.ui.protein.tertiary.signal.ProteinTertiaryApplyRenderingRaiser;
import molecule.ui.protein.tertiary.signal.ProteinTertiaryBondTranslucencyRaiser;
import molecule.ui.protein.tertiary.signal.ProteinTertiaryFilterRaiser;
import molecule.ui.protein.tertiary.signal.ProteinTertiaryHBondSizeRaiser;
import molecule.ui.protein.tertiary.signal.ProteinTertiaryHBondTranslucencyRaiser;
import molecule.ui.protein.tertiary.signal.ProteinTertiaryRenderingModeRaiser;
import molecule.ui.protein.tertiary.signal.ProteinTertiarySSBondSizeRaiser;
import molecule.ui.protein.tertiary.signal.ProteinTertiarySSBondTranslucencyRaiser;
import molecule.ui.protein.tertiary.signal.ProteinTertiarySelectionRaiser;
import molecule.ui.protein.tertiary.signal.ProteinTertiarySizeRaiser;
import molecule.ui.protein.tertiary.signal.ProteinTertiaryTranslucencyRaiser;
import molecule.ui.signal.RenderingInfoRaiser;
import star.annotations.Handles;
import star.annotations.SignalComponent;
import app.Messages;

@star.annotations.Preferences
@SignalComponent(extend = AbstractMoleculeUI.class, raises = {RenderingInfoRaiser.class})
public class ProteinTertiaryPanel extends ProteinTertiaryPanel_generated
{
	private static final long serialVersionUID = 1L;
	
	transient private String tertiary_string = Messages.getString("ProteinTertiaryPanel.0"); //$NON-NLS-1$
	
	transient private Molecule molecule = null;
	transient private ProteinTertiaryFiltersPanel filters = null;
	transient private ProteinTertiaryRenderingPanel properties = null;
	transient private ProteinTertiaryListPanel list = null;
	boolean hasSSBonds = true;
	
	public ProteinTertiaryPanel(Molecule molecule)
	{
		super();
		this.molecule = molecule;
		loadPreferences("protein"); //$NON-NLS-1$
	}
	
	public String getTitle()
	{
		return tertiary_string;
	}

	protected void initialize()
	{
		filters = new ProteinTertiaryFiltersPanel();
		properties = new ProteinTertiaryRenderingPanel(molecule);
		if(null != this.molecule)
		{
			list = new ProteinTertiaryListPanel(this.molecule);
		}
		initialize(filters, list, properties);
		hasSSBonds = properties.bondsProperties.ssbondProperties.isEnabledSSBonds;
	}

	protected void cleanup()
	{
		filters = null;
		properties = null;
		list = null;
	}
	
	transient private boolean inInitTree = false;
	public void initTree()
	{
		if(!inInitTree)
		{
			inInitTree = true;
			initLocalVariables();
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
		ssBondSize = 50;
		ssBondTranslucency = 0;
		hBondSize = 0;
		hBondTranslucency = 0;
		if(null != properties)
		{
			bondTranslucency = properties.getDefaultBondTranslucency();
			translucency = properties.getDefaultTranslucency();
			size = properties.getDefaultSize();
			ssBondSize = properties.getDefaultSSBondSize();
			ssBondTranslucency = properties.getDefaultSSBondTranslucency();
			hBondSize = properties.getDefaultHBondSize();
			hBondTranslucency = properties.getDefaultHBondTranslucency();
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
			resetLocalVariables();
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
		ssBondSize = 50;
		ssBondTranslucency = 0;
		hBondSize = 0;
		hBondTranslucency = 0;
		if(null != properties)
		{
			bondTranslucency = properties.getDefaultBondTranslucency();
			translucency = properties.getDefaultTranslucency();
			size = properties.getDefaultSize();
			ssBondSize = properties.getDefaultSSBondSize();
			ssBondTranslucency = properties.getDefaultSSBondTranslucency();
			hBondSize = properties.getDefaultHBondSize();
			hBondTranslucency = properties.getDefaultHBondTranslucency();
		}
		
		selection = null;
		if(null != list)
		{
			selection = list.getDefaultSelection();
		}
	}
	
	@Handles(raises = {})
	protected void handleProteinTertiaryRenderingModeRaiser(ProteinTertiaryRenderingModeRaiser raiser)
	{
		this.isAutomaticallyRendered = raiser.isAutomaticallyRendered();
		checkRaiseRenderEvent();
		checkRaiseCovalentBondRenderEvent();
		checkRaiseHbondRenderEvent();
	}

	@Handles(raises = {})
	protected void handleProteinPrimaryRenderingModeRaiser(ProteinPrimaryRenderingModeRaiser raiser)
	{
		// just track state -- don't raise render event
		this.isAutomaticallyRendered = raiser.isAutomaticallyRendered();
	}

	@Handles(raises = {})
	protected void handleProteinTertiaryApplyRenderingRaiser(ProteinTertiaryApplyRenderingRaiser raiser)
	{
		raiseRenderEvent();
		raiseCovalentBondRenderEvent();
		raiseHbondRenderEvent();
	}

	@Handles(raises = {})
	protected void handleProteinTertiaryFilterRaiser(ProteinTertiaryFilterRaiser raiser)
	{
		this.filter = raiser.getFilterOptions();
		checkRaiseRenderEvent();
		checkRaiseCovalentBondRenderEvent();
		checkRaiseHbondRenderEvent();
	}
	
    static final int backsideMASK = BACKBONE_VISIBLE | SIDECHAIN_VISIBLE;
	transient private boolean inHandleProteinPrimaryFilterRaiser = false;
	@Handles(raises = {})
	protected void handleProteinPrimaryFilterRaiser(ProteinPrimaryFilterRaiser raiser)
	{
		// just track state - don't raise render event
		if(!inHandleProteinPrimaryFilterRaiser && !inReset)
		{
			inHandleProteinPrimaryFilterRaiser = true;
    		int value = raiser.getValue();
    		this.filter = this.filter & ~backsideMASK;
    		this.filter = this.filter | value;
    		inHandleProteinPrimaryFilterRaiser = false;
		}
	}

	@Handles(raises = {})
	protected void handleProteinTertiarySelectionRaiser(ProteinTertiarySelectionRaiser raiser)
	{
		this.selection = raiser.getSelection();
		if(raiser.isFromProteinTertiarySelectionRaiser())
		{
			checkRaiseRenderEvent();
			checkRaiseCovalentBondRenderEvent();
			checkRaiseHbondRenderEvent();
		}
	}

	@Handles(raises = {})
	protected void handleProteinTertiaryBondTranslucencyRaiser(ProteinTertiaryBondTranslucencyRaiser raiser)
	{
		this.bondTranslucency = raiser.getValue();
		if(raiser.isFromProteinTertiaryBondTranslucencyRaiser())
		{
			checkRaiseCovalentBondRenderEvent();
		}
	}

	@Handles(raises = {})
	protected void handleProteinTertiaryTranslucencyRaiser(ProteinTertiaryTranslucencyRaiser raiser)
	{
		this.translucency = raiser.getValue();
		if(raiser.isFromProteinTertiaryTranslucencyRaiser())
		{
			checkRaiseRenderEvent();
		}
	}

	@Handles(raises = {})
	protected void handleProteinTertiarySizeRaiser(ProteinTertiarySizeRaiser raiser)
	{
		this.size = raiser.getValue();
		if(raiser.isFromProteinTertiarySizeRaiser())
		{
			checkRaiseRenderEvent();
		}
	}

	@Handles (raises = {})
	protected void handleProteinTertiarySSBondSizeRaiser(ProteinTertiarySSBondSizeRaiser raiser)
	{
		this.ssBondSize = raiser.getValue();
		checkRaiseCovalentBondRenderEvent();
	}

	@Handles (raises = {})
	protected void handleProteinTertiarySSBondTranslucencyRaiser(ProteinTertiarySSBondTranslucencyRaiser raiser)
	{
		this.ssBondTranslucency = raiser.getValue();
		checkRaiseCovalentBondRenderEvent();	
	}	
	
	@Handles(raises = {})
	protected void handleProteinTertiaryHBondSizeRaiser(ProteinTertiaryHBondSizeRaiser raiser) 
	{
		this.hBondSize = raiser.getValue();
		checkRaiseHbondRenderEvent();
		}

	@Handles(raises = {})
	protected void handleProteinTertiaryHBondTranslucencyRaiser(ProteinTertiaryHBondTranslucencyRaiser raiser) 
	{
		this.hBondTranslucency = raiser.getValue();
		checkRaiseHbondRenderEvent();
	}
	
	transient private boolean isAutomaticallyRendered = true;
	transient private int bondTranslucency = 0;
	transient private int filter = 0;
	transient private int translucency = 0;
	transient private String[] selection = null;
	transient private int size = 20;
	transient private int ssBondSize = 25;
	transient private int ssBondTranslucency = 0;
	transient private int hBondSize = 0;
	transient private int hBondTranslucency = 0;

	protected void loadPreferences(String preferencesName)
	{
		tertiary_string = getPreferences(preferencesName).get("tertiary_string", tertiary_string).trim(); //$NON-NLS-1$
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
	
	private void checkRaiseHbondRenderEvent()
	{
		if(this.isAutomaticallyRendered)
		{
			raiseHbondRenderEvent();
		}
	}
	
	transient private boolean inRaiseRenderEvent = false;
	protected synchronized void raiseRenderEvent()
	{
		if(!inRaiseRenderEvent && !inReset)
		{
			inRaiseRenderEvent = true;
			renderingInfo = new molecule.RenderingInfo(molecule.getUrl(), RenderingInfoRaiser.PROTEIN_TERTIARY_STRUCTURE,
					this.selection,
					null,
					-1,
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
			inRaiseRenderEvent = false;
		}
	}
		
	boolean inRaiseCovalentBondRenderEvent = false;
	protected void raiseCovalentBondRenderEvent()
	{
		if(!inRaiseCovalentBondRenderEvent && !inReset)
		{
			inRaiseCovalentBondRenderEvent = true;
			renderingInfo = new molecule.RenderingInfo(molecule.getUrl(), RenderingInfoRaiser.PROTEIN_TERTIARY_COVALENT_BONDS,
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
	
	boolean inRaiseHbondRenderEvent = false;
	protected void raiseHbondRenderEvent()
	{
		if(!inRaiseHbondRenderEvent && !inReset)
		{
			inRaiseHbondRenderEvent = true;
			renderingInfo = new molecule.RenderingInfo(molecule.getUrl(),RenderingInfoRaiser.PROTEIN_TERTIARY_HBOND_STRUCTURE,
					this.selection, 
					molecule.getTertiaryHBonds(),
					-1,
					-1, 
					-1,
					this.filter, 
					-1, 
					-1, 
					this.size, 
					-1, 
					0, 
					0, 
					0,
					-1,
					-1,
					this.hBondSize,
					this.hBondTranslucency);
			this.raise_RenderingInfoEvent();
			inRaiseHbondRenderEvent = false;
		}
	}
	
}