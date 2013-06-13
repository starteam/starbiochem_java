package molecule.ui.protein.quaternary;

import javax.swing.JList;

import molecule.interfaces.Molecule;
import molecule.interfaces.RenderingInfo;
import molecule.ui.AbstractMoleculeUI;
import molecule.ui.protein.quaternary.signal.ProteinQuaternaryApplyRenderingRaiser;
import molecule.ui.protein.quaternary.signal.ProteinQuaternaryFilterRaiser;
import molecule.ui.protein.quaternary.signal.ProteinQuaternaryRenderingModeRaiser;
import molecule.ui.protein.quaternary.signal.ProteinQuaternarySSBondSizeRaiser;
import molecule.ui.protein.quaternary.signal.ProteinQuaternarySSBondTranslucencyRaiser;
import molecule.ui.protein.quaternary.signal.ProteinQuaternarySelectionRaiser;
import molecule.ui.protein.quaternary.signal.ProteinQuaternarySizeRaiser;
import molecule.ui.protein.quaternary.signal.ProteinQuaternaryTranslucencyRaiser;
import molecule.ui.protein.quaternary.signal.ProteinQuaternaryHBondSizeRaiser;
import molecule.ui.protein.quaternary.signal.ProteinQuaternaryHBondTranslucencyRaiser;
import molecule.ui.signal.RenderingInfoRaiser;
import star.annotations.Handles;
import star.annotations.SignalComponent;
import app.Messages;

@star.annotations.Preferences
@SignalComponent(extend = AbstractMoleculeUI.class, raises = {RenderingInfoRaiser.class})
public class ProteinQuaternaryPanel extends ProteinQuaternaryPanel_generated
{
	private static final long serialVersionUID = 1L;

	transient private String quaternary_string = Messages.getString("ProteinQuaternaryPanel.0"); //$NON-NLS-1$
	
	transient private Molecule molecule = null;
	transient private ProteinQuaternaryFiltersPanel filters = null;
	transient private ProteinQuaternaryRenderingPanel properties = null;
	transient private ProteinQuaternaryListPanel list = null;
	
	public ProteinQuaternaryPanel(Molecule molecule)
	{
		super();
		this.molecule = molecule;
		loadPreferences("protein"); //$NON-NLS-1$
	}
	
	public String getTitle()
	{
		return quaternary_string;
	}

	protected void initialize()
	{
		filters = new ProteinQuaternaryFiltersPanel();
		properties = new ProteinQuaternaryRenderingPanel(molecule);
		if(null != this.molecule)
		{
			list = new ProteinQuaternaryListPanel(this.molecule);
		}
		initialize(filters, list, properties);
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
		
		translucency = 0;
		ssBondSize = 50;
		ssBondTranslucency = 0;
		hBondSize = 0;
		hBondTranslucency = 0;
		if(null != properties)
		{
			translucency = properties.getDefaultTranslucency();
			ssBondSize = properties.getDefaultSSBondSize();
			ssBondTranslucency = properties.getDefaultSSSondTranslucency();
			hBondSize = properties.getDefaultHBondSize();
			hBondTranslucency = properties.getDefaultHBondTranslucency();
			int mysize = properties.getDefaultSize();
			this.size = (mysize <= MINIMUMSIZE) ? 0 : mysize;
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
		
		translucency = 0;
		ssBondSize = 50;
		ssBondTranslucency = 0;
		hBondSize = 0;
		hBondTranslucency = 0;
		if(null != properties)
		{
			translucency = properties.getDefaultTranslucency();
			ssBondSize = properties.getDefaultSSBondSize();
			ssBondTranslucency = properties.getDefaultSSSondTranslucency();
			hBondSize = properties.getDefaultHBondSize();
			hBondTranslucency = properties.getDefaultHBondTranslucency();
			int mysize = properties.getDefaultSize();
			this.size = (mysize <= MINIMUMSIZE) ? 0 : mysize;
		}
		selection = null;
		if(null != list)
		{
			selection = list.getDefaultSelection();
		}
	}
	
	@Handles(raises = {})
	protected void handleProteinQuaternaryRenderingModeRaiser(ProteinQuaternaryRenderingModeRaiser raiser)
	{
		this.isAutomaticallyRendered = raiser.isAutomaticallyRendered();
		if(this.isAutomaticallyRendered)
		{
			raiseRenderEvent();
			raiseSSBondRenderEvent();
			raiseHbondRenderEvent();
		}
	}

	@Handles(raises = {})
	protected void handleProteinQuaternaryApplyRenderingRaiser(ProteinQuaternaryApplyRenderingRaiser raiser)
	{
		raiseRenderEvent();
		raiseSSBondRenderEvent();
		raiseHbondRenderEvent();
	}

	@Handles(raises = {})
	protected void handleProteinQuaternaryFilterRaiser(ProteinQuaternaryFilterRaiser raiser)
	{
		this.filter = raiser.getValue();
		checkRaiseRenderEvent();
		checkRaiseSSBondRenderEvent();
		checkRaiseHbondRenderEvent();
	}

	@Handles(raises = {})
	protected void handleProteinQuaternaryTranslucencyRaiser(ProteinQuaternaryTranslucencyRaiser raiser)
	{
		this.translucency = raiser.getValue();
		if(this.isAutomaticallyRendered)
		{
			raiseRenderTranslucencyEvent();
		}
	}

	@Handles(raises = {})
	protected void handleProteinQuaternarySelectionRaiser(ProteinQuaternarySelectionRaiser raiser)
	{
		String[] mySelection = raiser.getSelection();
		if(null != mySelection)
		{
			JList myJList = list.getList();
			if(null != myJList)
			{
				this.selection = mySelection;
				checkRaiseRenderEvent();
				checkRaiseSSBondRenderEvent();
				checkRaiseHbondRenderEvent();
			}
		}
	}

	@Handles(raises = {})
	protected void handleProteinQuaternarySizeRaiser(ProteinQuaternarySizeRaiser raiser)
	{
		int mysize = raiser.getValue();
		this.size = (mysize <= MINIMUMSIZE) ? 0 : mysize;
		checkRaiseRenderEvent();
	}
	
	@Handles (raises = {})
	protected void handleProteinQuaternarySSBondSizeRaiser(ProteinQuaternarySSBondSizeRaiser raiser)
	{
		this.ssBondSize = raiser.getValue();
		checkRaiseSSBondRenderEvent();
	}

	@Handles (raises = {})
	protected void handleProteinQuaternarySSBondTranslucencyRaiser(ProteinQuaternarySSBondTranslucencyRaiser raiser)
	{
		this.ssBondTranslucency = raiser.getValue();
		checkRaiseSSBondRenderEvent();	
	}
	
	@Handles(raises = {})
	protected void handleProteinQuaternaryHBondSizeRaiser(ProteinQuaternaryHBondSizeRaiser raiser) 
	{
		this.hBondSize = raiser.getValue();
		checkRaiseHbondRenderEvent();
		}

	@Handles(raises = {})
	protected void handleProteinQuaternaryHBondTranslucencyRaiser(ProteinQuaternaryHBondTranslucencyRaiser raiser) 
	{
		this.hBondTranslucency = raiser.getValue();
		checkRaiseHbondRenderEvent();
	}

	transient final static private int MINIMUMSIZE = 50;
		
	transient private boolean isAutomaticallyRendered = true;
	transient private int filter = 0;
	transient private int translucency = 0;
	transient private String[] selection = null;
	transient private int size = 0;
	transient private int ssBondSize = 50;
	transient private int ssBondTranslucency = 0;
	transient private int hBondSize = 0;
	transient private int hBondTranslucency = 0;

	protected void loadPreferences(String preferencesName)
	{
		quaternary_string = getPreferences(preferencesName).get("quaternary_string", quaternary_string).trim(); //$NON-NLS-1$
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
	
	private void checkRaiseSSBondRenderEvent()
	{
		if(this.isAutomaticallyRendered)
		{
			raiseSSBondRenderEvent();
		}
	}
	
	private void checkRaiseHbondRenderEvent()
	{
		if(this.isAutomaticallyRendered)
		{
			raiseHbondRenderEvent();
		}
	}

	boolean inRaiseRenderEvent = false;
	protected void raiseRenderEvent()
	{
		if(!inRaiseRenderEvent && !inReset)
		{
			inRaiseRenderEvent = true;
			renderingInfo = new molecule.RenderingInfo(molecule.getUrl(), RenderingInfoRaiser.PROTEIN_QUATERNARY_STRUCTURE,
					this.selection,
					null,
					-1,
					-1,
					-1,
					this.filter,
					((this.size != 0) ? this.translucency : 100),
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
	
	boolean inRaiseSSBondRenderEvent = false;
	protected void raiseSSBondRenderEvent()
	{
		if(!inRaiseSSBondRenderEvent && !inReset)
		{
			inRaiseSSBondRenderEvent = true;
			renderingInfo = new molecule.RenderingInfo(molecule.getUrl(), RenderingInfoRaiser.PROTEIN_QUATERNARY_SSBOND_STRUCTURE,
					this.selection,
					null,
					 0,
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
					this.ssBondSize,
					((this.ssBondSize != 0) ? this.ssBondTranslucency : 100),
					-1,
					-1);
			this.raise_RenderingInfoEvent();
			inRaiseSSBondRenderEvent = false;		
		}
	}

	protected void raiseRenderTranslucencyEvent()
	{
		if(!inRaiseRenderEvent && !inReset)
		{
			inRaiseRenderEvent = true;
			renderingInfo = new molecule.RenderingInfo(molecule.getUrl(), RenderingInfoRaiser.PROTEIN_QUATERNARY_STRUCTURE_TRANSLUCENCY,
					this.selection,
					null,
					-1,
					-1,
					-1,
					this.filter,
					((this.size != 0) ? this.translucency : 100),
					-1,
					this.size,
					-1,
					0,
					0,
					0,
					-1,
					-1,-1,
					-1);
			this.raise_RenderingInfoEvent();
			inRaiseRenderEvent = false;
		}
	}
	
	boolean inRaiseHbondRenderEvent = false;
	protected void raiseHbondRenderEvent()
	{
		if(!inRaiseHbondRenderEvent && !inReset)
		{
			inRaiseHbondRenderEvent = true;
			renderingInfo = new molecule.RenderingInfo(molecule.getUrl(),RenderingInfoRaiser.PROTEIN_QUATERNARY_HBOND_STRUCTURE,
					this.selection, 
					molecule.getQuaternaryHBonds(),
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