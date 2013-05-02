package molecule.ui.water;

import app.signal.RenderAllRaiser;
import molecule.interfaces.Molecule;
import molecule.interfaces.RenderingInfo;
import molecule.ui.AbstractNonProteinMoleculeUI;
import molecule.ui.signal.RenderingInfoRaiser;
import molecule.ui.water.signal.WaterApplyRenderingRaiser;
import molecule.ui.water.signal.WaterBondTranslucencyRaiser;
import molecule.ui.water.signal.WaterFilterRaiser;
import molecule.ui.water.signal.WaterHBondSizeRaiser;
import molecule.ui.water.signal.WaterHBondTranslucencyRaiser;
import molecule.ui.water.signal.WaterRenderingModeRaiser;
import molecule.ui.water.signal.WaterSelectionRaiser;
import molecule.ui.water.signal.WaterSizeRaiser;
import molecule.ui.water.signal.WaterTranslucencyRaiser;
import star.annotations.Handles;
import star.annotations.SignalComponent;
import app.Messages;

@star.annotations.Preferences
@SignalComponent(extend = AbstractNonProteinMoleculeUI.class, raises = {RenderingInfoRaiser.class})
public class WaterPanel extends WaterPanel_generated
{
	private static final long serialVersionUID = 1L;

	public String water_string = Messages.getString("WaterPanel.0"); //$NON-NLS-1$
	
	transient private Molecule molecule = null;
	transient private WaterFiltersPanel filters = null;
	transient private WaterRenderingPanel properties = null;
	transient private WaterListPanel list = null;
	
	private WaterPanel(Molecule molecule)
	{
		super();
		this.molecule = molecule;
		loadPreferences("water"); //$NON-NLS-1$
	}
	
	public String getTitle()
	{
		return water_string;
	}

	protected void initialize()
	{
		filters = new WaterFiltersPanel();
		properties = new WaterRenderingPanel(molecule);
		if(null != this.molecule)
		{
			list = new WaterListPanel(this.molecule.getWaterArray());
		}
		initialize(filters, properties, list);
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
		
		bondTranslucency = 100;
		translucency = 0;
		size = 0;
		hBondSize = 0;
		hBondTranslucency = 0;
		if(null != properties)
		{
			bondTranslucency = properties.getDefaultBondTranslucency();
			translucency = properties.getDefaultTranslucency();
			size = properties.getDefaultSize();
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
		
		bondTranslucency = 100;
		translucency = 0;
		size = 0;
		hBondSize = 0;
		hBondTranslucency = 0;
		if(null != properties)
		{
			bondTranslucency = properties.getDefaultBondTranslucency();
			translucency = properties.getDefaultTranslucency();
			size = properties.getDefaultSize();
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
	protected void handleWaterRenderingModeRaiser(WaterRenderingModeRaiser raiser)
	{
		this.isAutomaticallyRendered = raiser.isAutomaticallyRendered();
		checkRaiseRenderEvent();
		raiseCovalentBondRenderEvent();
		raiseHbondRenderEvent();
	}

	@Handles(raises = {})
	protected void handleWaterApplyRenderingRaiser(WaterApplyRenderingRaiser raiser)
	{
		raiseRenderEvent();
		raiseCovalentBondRenderEvent();
		raiseHbondRenderEvent();
	}

	@Handles(raises = {})
	protected void handleWaterFilterRaiser(WaterFilterRaiser raiser)
	{
		this.filter = raiser.getValue();
		checkRaiseRenderEvent();
		checkRaiseCovalentBondRenderEvent();
		checkRaiseHbondRenderEvent();
	}

	@Handles(raises = {})
	protected void handleWaterTranslucencyRaiser(WaterTranslucencyRaiser raiser)
	{
		this.translucency = raiser.getValue();
		checkRaiseRenderEvent();
	}

	@Handles(raises = {})
	protected void handleWaterBondTranslucencyRaiser(WaterBondTranslucencyRaiser raiser)
	{
		this.bondTranslucency = raiser.getValue();
		checkRaiseCovalentBondRenderEvent();
	}

	@Handles(raises = {})
	protected void handleWaterSelectionRaiser(WaterSelectionRaiser raiser)
	{
		this.selection = raiser.getSelection();
		checkRaiseRenderEvent();
		checkRaiseCovalentBondRenderEvent();
		checkRaiseHbondRenderEvent();
	}

	@Handles(raises = {})
	protected void handleWaterSizeRaiser(WaterSizeRaiser raiser)
	{
		this.size = raiser.getValue();
		checkRaiseRenderEvent();
	}
	
	@Handles(raises = {})
	protected void handleRenderAllRaiser(RenderAllRaiser raiser)
	{
		raiseRenderEvent();
		raiseCovalentBondRenderEvent();
		raiseHbondRenderEvent();
	}

	@Handles(raises = {})
	void handleWaterHBondSizeRaiser(WaterHBondSizeRaiser raiser) 
	{
		this.hBondSize = raiser.getValue();
		checkRaiseHbondRenderEvent();
	}

	@Handles(raises = {})
	void handleWaterHBondTranslucencyRaiser(WaterHBondTranslucencyRaiser raiser) 
	{
		this.hBondTranslucency = raiser.getValue();
		checkRaiseHbondRenderEvent();		
	}
	
	transient private boolean isAutomaticallyRendered = true;
	transient private int bondTranslucency = 0;
	transient private int filter = 0;
	transient private int translucency = 0;
	transient private String[] selection = null;
	transient private int size = 0;
	transient private int hBondSize = 0;
	transient private int hBondTranslucency = 0;

	protected void loadPreferences(String preferencesName)
	{
		water_string = getPreferences(preferencesName).get("water_string", water_string).trim(); //$NON-NLS-1$
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
			renderingInfo = new molecule.RenderingInfo(molecule.getUrl(), RenderingInfoRaiser.WATER,
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
			renderingInfo = new molecule.RenderingInfo(molecule.getUrl(), RenderingInfoRaiser.WATER_COVALENT_BONDS,
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
	
	boolean inRaiseHbondRenderEvent = false;
	protected void raiseHbondRenderEvent()
	{
		if(!inRaiseHbondRenderEvent && !inReset)
		{
			inRaiseHbondRenderEvent = true;

			renderingInfo = new molecule.RenderingInfo(molecule.getUrl(),RenderingInfoRaiser.WATER_HBOND_STRUCTURE,
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
	
	public static WaterPanel getDefaultWaterPanel(Molecule molecule)
	{
		if(null != molecule)
		{
			String[] array = molecule.getWaterArray();
			if((null != array) && (0 != array.length))
			{
				return new WaterPanel(molecule);
			}
		}
		return null;
	}

}