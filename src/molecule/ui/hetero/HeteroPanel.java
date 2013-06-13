package molecule.ui.hetero;

import molecule.interfaces.Molecule;
import molecule.interfaces.RenderingInfo;
import molecule.ui.AbstractNonProteinMoleculeUI;
import molecule.ui.hetero.signal.HeteroApplyRenderingRaiser;
import molecule.ui.hetero.signal.HeteroBondTranslucencyRaiser;
import molecule.ui.hetero.signal.HeteroFilterRaiser;
import molecule.ui.hetero.signal.HeteroHBondSizeRaiser;
import molecule.ui.hetero.signal.HeteroHBondTranslucencyRaiser;
import molecule.ui.hetero.signal.HeteroRenderingModeRaiser;
import molecule.ui.hetero.signal.HeteroSelectionRaiser;
import molecule.ui.hetero.signal.HeteroSizeRaiser;
import molecule.ui.hetero.signal.HeteroTranslucencyRaiser;
import molecule.ui.signal.RenderingInfoRaiser;
import star.annotations.Handles;
import star.annotations.SignalComponent;
import app.Messages;

@star.annotations.Preferences
@SignalComponent(extend = AbstractNonProteinMoleculeUI.class, raises = {RenderingInfoRaiser.class})
public class HeteroPanel extends HeteroPanel_generated
{
	private static final long serialVersionUID = 1L;

	public String hetero_string = Messages.getString("HeteroPanel.0"); //$NON-NLS-1$

	transient private Molecule molecule = null;
	transient private HeteroFiltersPanel filters = null;
	transient private HeteroRenderingPanel properties = null;
	transient private HeteroListPanel list = null;
	
	private HeteroPanel(Molecule molecule)
	{
		super();
		this.molecule = molecule;
		loadPreferences("hetero"); //$NON-NLS-1$
	}
	
	public String getTitle()
	{
		return hetero_string;
	}

	protected void initialize()
	{
		filters = new HeteroFiltersPanel();
		properties = new HeteroRenderingPanel(molecule);
		if(null != this.molecule)
		{
			list = new HeteroListPanel(this.molecule.getHeteroNotWaterNotNucleicArray());
		}
		initialize(filters, properties, list);
	}

	protected void cleanup()
	{
		filters = null;
		properties = null;
		list = null;
	}
	
	boolean inInitTree = false;
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
		hBondSize = 0;
		hBondTranslucency = 0;
		if(null != properties)
		{
			bondTranslucency = properties.getDefaultBondTranslucency();
			hBondSize = properties.getDefaultHBondSize();
			hBondTranslucency = properties.getDefaultHBondTransluceny();
			translucency = properties.getDefaultTranslucency();
			size = properties.getDefaultSize();
		}
		selection = null;
		if(null != list)
		{
			selection = list.getDefaultSelection();
		}
	}
	
	boolean inReset = false;
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
		hBondSize = 0;
		hBondTranslucency = 0;
		if(null != properties)
		{
			bondTranslucency = properties.getDefaultBondTranslucency();
			hBondSize = properties.getDefaultHBondSize();
			hBondTranslucency = properties.getDefaultHBondTransluceny();
			translucency = properties.getDefaultTranslucency();
			size = properties.getDefaultSize();
		}
		selection = null;
		if(null != list)
		{
			selection = list.getDefaultSelection();
		}
	}
	
	@Handles(raises = {})
	protected void handleHeteroRenderingModeRaiser(HeteroRenderingModeRaiser raiser)
	{
		this.isAutomaticallyRendered = raiser.isAutomaticallyRendered();
		if(this.isAutomaticallyRendered)
		{
			raiseRenderEvent();
			raiseCovalentBondRenderEvent();
			raiseHbondRenderEvent();
		}
	}

	@Handles(raises = {})
	protected void handleHeteroApplyRenderingRaiser(HeteroApplyRenderingRaiser raiser)
	{
		raiseRenderEvent();
		raiseCovalentBondRenderEvent();
		raiseHbondRenderEvent();
	}

	@Handles(raises = {})
	protected void handleHeteroBondTranslucencyRaiser(HeteroBondTranslucencyRaiser raiser)
	{
		this.bondTranslucency = raiser.getValue();
		checkRaiseCovalentBondRenderEvent();
	}

	@Handles(raises = {})
	protected void handleHeteroFilterRaiser(HeteroFilterRaiser raiser)
	{
		this.filter = raiser.getValue();
		checkRaiseRenderEvent();
		checkRaiseCovalentBondRenderEvent();
		checkRaiseHbondRenderEvent();
	}

	@Handles(raises = {})
	protected void handleHeteroTranslucencyRaiser(HeteroTranslucencyRaiser raiser)
	{
		this.translucency = raiser.getValue();
		checkRaiseRenderEvent();
	}

	@Handles(raises = {})
	protected void handleHeteroSelectionRaiser(HeteroSelectionRaiser raiser)
	{	
		this.selection = raiser.getSelection();
		checkRaiseRenderEvent();
		checkRaiseCovalentBondRenderEvent();
		checkRaiseHbondRenderEvent();
	}

	@Handles(raises = {})
	protected void handleHeteroSizeRaiser(HeteroSizeRaiser raiser)
	{
		this.size = raiser.getValue();
		checkRaiseRenderEvent();
	}
	
	@Handles(raises = {})
	void handleHeteroHBondSizeRaiser(HeteroHBondSizeRaiser raiser) 
	{
		this.hBondSize = raiser.getValue();
		checkRaiseHbondRenderEvent();
	}

	@Handles(raises = {})
	void handleHeteroHBondTranslucencyRaiser(HeteroHBondTranslucencyRaiser raiser) 
	{
		this.hBondTranslucency = raiser.getValue();
		checkRaiseHbondRenderEvent();		
	}

	transient private boolean isAutomaticallyRendered = true;
	transient private int bondTranslucency = 0;
	transient private int filter = 0;
	transient private int translucency = 0;
	transient private String[] selection = null;
	transient private int size = 25;
	transient private int hBondSize = 0;
	transient private int hBondTranslucency = 0;
	
	protected void loadPreferences(String preferencesName)
	{
		hetero_string = getPreferences(preferencesName).get("hetero_string", hetero_string).trim();		 //$NON-NLS-1$
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
	protected void raiseRenderEvent()
	{
		if(!inRaiseRenderEvent && !inReset)
		{
			inRaiseRenderEvent = true;
			renderingInfo = new molecule.RenderingInfo(molecule.getUrl(), RenderingInfoRaiser.HETERO,
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
			renderingInfo = new molecule.RenderingInfo(molecule.getUrl(), RenderingInfoRaiser.HETERO_COVALENT_BONDS,
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
					-1,
					-1,
					-1,
					-1);
			this.raise_RenderingInfoEvent();
			inRaiseCovalentBondRenderEvent = false;
		}
	}
	
	public static HeteroPanel getDefaultHeteroPanel(Molecule molecule)
	{
		if(null != molecule)
		{
			String[] array = molecule.getHeteroNotWaterNotNucleicArray();
			if((null != array) && (0 != array.length))
			{
				return new HeteroPanel(molecule);
			}
		}
		return null;
	}
	boolean inRaiseHbondRenderEvent = false;
	protected void raiseHbondRenderEvent()
	{
		if(!inRaiseHbondRenderEvent && !inReset)
		{
			inRaiseHbondRenderEvent = true;

			renderingInfo = new molecule.RenderingInfo(molecule.getUrl(),RenderingInfoRaiser.HETERO_HBOND_STRUCTURE,
					this.selection, 
					null,
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