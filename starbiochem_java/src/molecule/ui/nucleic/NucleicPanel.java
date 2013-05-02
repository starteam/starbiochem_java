package molecule.ui.nucleic;

import molecule.interfaces.Molecule;
import molecule.interfaces.RenderingInfo;
import molecule.ui.AbstractNonProteinMoleculeUI;
import molecule.ui.nucleic.signal.NucleicApplyRenderingRaiser;
import molecule.ui.nucleic.signal.NucleicBondTranslucencyRaiser;
import molecule.ui.nucleic.signal.NucleicFilterRaiser;
import molecule.ui.nucleic.signal.NucleicRenderingModeRaiser;
import molecule.ui.nucleic.signal.NucleicSelectionRaiser;
import molecule.ui.nucleic.signal.NucleicSizeRaiser;
import molecule.ui.nucleic.signal.NucleicTranslucencyRaiser;
import molecule.ui.nucleic.signal.NucleicHBondSizeRaiser;
import molecule.ui.nucleic.signal.NucleicHBondTranslucencyRaiser;
import molecule.ui.signal.RenderingInfoRaiser;
import star.annotations.Handles;
import star.annotations.SignalComponent;
import app.Messages;

@star.annotations.Preferences
@SignalComponent(extend = AbstractNonProteinMoleculeUI.class, raises = {RenderingInfoRaiser.class})
public class NucleicPanel extends NucleicPanel_generated
{
	
	private static final long serialVersionUID = 1L;

	public String nucleic_string = Messages.getString("NucleicPanel.0"); //$NON-NLS-1$
	
	transient private Molecule molecule = null;
	transient private NucleicFiltersPanel filters = null;
	transient private NucleicRenderingPanel properties = null;
	transient private NucleicListPanel list = null;
	
	private NucleicPanel(Molecule molecule)
	{
		super();
		this.molecule = molecule;
		loadPreferences("nucleic"); //$NON-NLS-1$
	}
	
	public String getTitle()
	{
		return nucleic_string;
	}

	protected void initialize()
	{
		filters = new NucleicFiltersPanel();
		properties = new NucleicRenderingPanel(molecule);
		if(null != this.molecule)
		{
			list = new NucleicListPanel(this.molecule.getNucleicArray());
		}
		initialize(filters, properties, list);
	}

	protected void cleanup()
	{
		filters = null;
		properties = null;
		list = null;
	}
	
	transient private boolean iniInitTree = false;
	public void initTree()
	{
		if(!iniInitTree)
		{
			iniInitTree = true;
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
			iniInitTree = false;
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
		hBondSize =0;
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
		hBondSize =0;
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
	protected void handleNucleicRenderingModeRaiser(NucleicRenderingModeRaiser raiser)
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
	protected void handleNucleicApplyRenderingRaiser(NucleicApplyRenderingRaiser raiser)
	{
		raiseRenderEvent();
		raiseCovalentBondRenderEvent();
		raiseHbondRenderEvent();
	}

	@Handles(raises = {})
	protected void handleNucleicBondTranslucencyRaiser(NucleicBondTranslucencyRaiser raiser)
	{
		this.bondTranslucency = raiser.getValue();
		checkRaiseCovalentBondRenderEvent();
	}

	@Handles(raises = {})
	protected void handleNucleicFilterRaiser(NucleicFilterRaiser raiser)
	{
		this.filter = raiser.getValue();
		checkRaiseRenderEvent();
		checkRaiseCovalentBondRenderEvent();
		checkRaiseHbondRenderEvent();
	}

	@Handles(raises = {})
	protected void handleNucleicTranslucencyRaiser(NucleicTranslucencyRaiser raiser)
	{
		this.translucency = raiser.getValue();
		checkRaiseRenderEvent();
	}

	@Handles(raises = {})
	protected void handleNucleicSelectionRaiser(NucleicSelectionRaiser raiser)
	{
		this.selection = raiser.getSelection();
		checkRaiseRenderEvent();
		checkRaiseCovalentBondRenderEvent();
		checkRaiseHbondRenderEvent();
	}

	@Handles(raises = {})
	protected void handleNucleicSizeRaiser(NucleicSizeRaiser raiser)
	{
		this.size = raiser.getValue();
		checkRaiseRenderEvent();
	}
	
	@Handles(raises = {})
	protected void handleNucleicHBondSizeRaiser(NucleicHBondSizeRaiser raiser) 
	{
		this.hBondSize = raiser.getValue();
		checkRaiseHbondRenderEvent();
		}

	@Handles(raises = {})
	protected void handleNucleicHBondTranslucencyRaiser(NucleicHBondTranslucencyRaiser raiser) 
	{
		this.hBondTranslucency = raiser.getValue();
		checkRaiseHbondRenderEvent();
	}

	boolean isAutomaticallyRendered = true;
	int bondTranslucency = 0;
	int filter = 0;
	int translucency = 0;
	String[] selection = null;
	int size = 25;
	transient private int hBondSize = 0;
	transient private int hBondTranslucency = 0;

	protected void loadPreferences(String preferencesName)
	{
		nucleic_string = getPreferences(preferencesName).get("nucleic_string", nucleic_string).trim(); //$NON-NLS-1$
	}

	transient RenderingInfo renderingInfo = null;
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
	
	transient boolean inRaiseRenderEvent = false;
	protected void raiseRenderEvent()
	{
		if(!inRaiseRenderEvent && !inReset)
		{
			inRaiseRenderEvent = true;
			renderingInfo = new molecule.RenderingInfo(molecule.getUrl(), RenderingInfoRaiser.NUCLEIC,
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
			renderingInfo = new molecule.RenderingInfo(molecule.getUrl(), RenderingInfoRaiser.NUCLEIC_COVALENT_BONDS,
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
			renderingInfo = new molecule.RenderingInfo(molecule.getUrl(),RenderingInfoRaiser.NUCLEIC_HBOND_STRUCTURE,
					this.selection, 
					null,
					-1,
					-1, 
					-1,
					this.filter, 
					-1, 
					-1, 
					0, 
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
	
	public static NucleicPanel getDefaultNucleicPanel(Molecule molecule)
	{
		if(null != molecule)
		{
			String[] array = molecule.getNucleicArray();
			if((null != array) && (0 != array.length))
			{
				return new NucleicPanel(molecule);
			}
		}
		return null;
	}
}