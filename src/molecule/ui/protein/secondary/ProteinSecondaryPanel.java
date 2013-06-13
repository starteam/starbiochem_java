package molecule.ui.protein.secondary;

import javax.swing.JPanel;

import molecule.interfaces.Molecule;
import molecule.interfaces.RenderingInfo;
import molecule.ui.AbstractMoleculeUI;
import molecule.ui.protein.secondary.signal.ProteinSecondaryHBondSizeRaiser;
import molecule.ui.protein.secondary.signal.ProteinSecondaryHBondTranslucencyRaiser;
import molecule.ui.protein.secondary.signal.ProteinSecondaryApplyRenderingRaiser;
import molecule.ui.protein.secondary.signal.ProteinSecondaryFilterRaiser;
import molecule.ui.protein.secondary.signal.ProteinSecondaryRenderingModeRaiser;
import molecule.ui.protein.secondary.signal.ProteinSecondarySelectionRaiser;
import molecule.ui.protein.secondary.signal.ProteinSecondarySizeRaiser;
import molecule.ui.signal.RenderingInfoRaiser;
import star.annotations.Handles;
import star.annotations.SignalComponent;
import app.Messages;

@star.annotations.Preferences
@SignalComponent(extend = AbstractMoleculeUI.class, raises = { RenderingInfoRaiser.class })
public class ProteinSecondaryPanel extends ProteinSecondaryPanel_generated 
{
	private static final long serialVersionUID = 1L;
	private final String SECONDARY = Messages.getString("ProteinSecondaryPanel.0"); //$NON-NLS-1$

	private String proteinsecondary_string = SECONDARY;

	transient private Molecule molecule = null;
	transient private ProteinSecondaryFiltersPanel filters = null;
	transient private ProteinSecondaryRenderingPanel properties = null;
	transient private ProteinSecondaryListPanel list = null;

	public ProteinSecondaryPanel(Molecule molecule) 
	{
		super();
		this.molecule = molecule;
		loadPreferences("protein"); //$NON-NLS-1$
	}

	public String getTitle() {
		return proteinsecondary_string;
	}

	protected void initialize() {
		filters = new ProteinSecondaryFiltersPanel();
		properties = new ProteinSecondaryRenderingPanel(molecule);
		if (null != this.molecule) {
			list = new ProteinSecondaryListPanel(this.molecule);
		}
		initialize((JPanel) filters, list, properties);
	}

	protected void cleanup() {
		filters = null;
		properties = null;
		list = null;
	}

	private boolean inInitTree = false;

	public synchronized void initTree() {
		if (!inInitTree) {
			inInitTree = true;

			if (null != filters) {
				filters.initTree();
			}
			if (null != properties) {
				properties.initTree();
			}
			if (null != list) {
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
		if (null != filters) 
		{
			filter = filters.getDefaultFilter();
			isAutomaticallyRendered = filters.isDefaultAutomaticallyRendered();
		}
		size = 0;
		if (null != properties) 
		{
			size = properties.getDefaultSize();
		}
		hBondSize = 0;
		if (null != properties) 
		{
			hBondSize = properties.getDefaultHBondSize();
		}
		hBondTranslucency = 0;
		if (null != properties) 
		{
			hBondTranslucency = properties.getDefaultHBondTranslucency();
		}
		selection = null;
		if (null != list) 
		{
			selection = list.getDefaultSelection();
		}
	}

	private boolean inReset = false;

	public synchronized void reset() {
		if (!inReset) {
			inReset = true;

			if (null != filters) {
				filters.reset();
			}
			if (null != properties) {
				properties.reset();
			}
			if (null != list) {
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
		if (null != filters) 
		{
			filter = filters.getDefaultFilter();
			isAutomaticallyRendered = filters.isDefaultAutomaticallyRendered();
		}
		size = 0;
		hBondSize = 0;
		hBondTranslucency = 0;
		if (null != properties) 
		{
			size = properties.getDefaultSize();
			hBondSize = properties.getDefaultHBondSize();
			hBondTranslucency = properties.getDefaultHBondTranslucency();
		}
		
		selection = null;
		if (null != list) 
		{
			selection = list.getDefaultSelection();
		}
		
	}

	@Handles(raises = {})
	protected void handleProteinSecondaryRenderingModeRaiser(ProteinSecondaryRenderingModeRaiser raiser) 
	{
		this.isAutomaticallyRendered = raiser.isAutomaticallyRendered();
		if (this.isAutomaticallyRendered) {
			raiseRenderEvent();
			raiseHbondRenderEvent();
		}
	}

	@Handles(raises = {})
	protected void handleProteinSecondaryApplyRenderingRaiser(ProteinSecondaryApplyRenderingRaiser raiser) 
	{
		raiseRenderEvent();
		raiseHbondRenderEvent();
	}

	@Handles(raises = {})
	protected void handleProteinSecondaryFilterRaiser(ProteinSecondaryFilterRaiser raiser) 
	{
		this.filter = raiser.getValue();
		checkRaiseRenderEvent();
		checkRaiseHbondRenderEvent();
	}

	@Handles(raises = {})
	protected void handleProteinSecondarySelectionRaiser(ProteinSecondarySelectionRaiser raiser) 
	{
		this.selection = raiser.getSelection();
		checkRaiseRenderEvent();
		checkRaiseHbondRenderEvent();
	}

	@Handles(raises = {})
	protected void handleProteinSecondarySizeRaiser(ProteinSecondarySizeRaiser raiser) 
	{
		this.size = raiser.getValue();
		checkRaiseRenderEvent();
	}

	@Handles(raises = {})
	protected void handleProteinSecondaryHBondSizeRaiser(ProteinSecondaryHBondSizeRaiser raiser) 
	{
		this.hBondSize = raiser.getValue();
		checkRaiseHbondRenderEvent();
		}

	@Handles(raises = {})
	protected void handleProteinSecondaryHBondTranslucencyRaiser(ProteinSecondaryHBondTranslucencyRaiser raiser) 
	{
		this.hBondTranslucency = raiser.getValue();
		checkRaiseHbondRenderEvent();
	}

	transient private boolean isAutomaticallyRendered = true;
	transient private int filter = 0;
	transient private String[] selection = null;
	transient private int size = 0;
	transient private int hBondSize = 0;
	transient private int hBondTranslucency = 0;

	protected void loadPreferences(String preferencesName) 
	{
		proteinsecondary_string = getPreferences(preferencesName).get(
				"proteinsecondary_string", proteinsecondary_string).trim(); //$NON-NLS-1$
	}

	transient private RenderingInfo renderingInfo = null;

	public RenderingInfo getRenderingInfo() {
		return this.renderingInfo;
	}

	private void checkRaiseRenderEvent() {
		if (this.isAutomaticallyRendered) {
			raiseRenderEvent();
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

	protected void raiseRenderEvent() {
		if (!inRaiseRenderEvent && !inReset) {
			inRaiseRenderEvent = true;
			renderingInfo = new molecule.RenderingInfo(molecule.getUrl(),RenderingInfoRaiser.PROTEIN_SECONDARY_STRUCTURE,
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
					-1,
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
			renderingInfo = new molecule.RenderingInfo(molecule.getUrl(),RenderingInfoRaiser.PROTEIN_SECONDARY_HBOND_STRUCTURE,
					this.selection, 
					molecule.getSecondaryHBonds(),
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