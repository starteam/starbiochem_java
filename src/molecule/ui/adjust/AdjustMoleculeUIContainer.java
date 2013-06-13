package molecule.ui.adjust;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import pdb.PDBTitle;

import molecule.interfaces.Molecule;
import molecule.ui.adjust.center.CenterPanel;
import molecule.ui.adjust.restrict.RestrictRenderingPropertiesPanel;

import molecule.interfaces.AdjustInfo;
import molecule.interfaces.AdjustSelectionInfo;
import molecule.ui.adjust.center.hetero.signal.CenterHeteroSelectionEvent;
import molecule.ui.adjust.center.hetero.signal.CenterHeteroSelectionRaiser;
import molecule.ui.adjust.center.nucleic.signal.CenterNucleicEvent;
import molecule.ui.adjust.center.nucleic.signal.CenterNucleicFilterEvent;
import molecule.ui.adjust.center.nucleic.signal.CenterNucleicRaiser;
import molecule.ui.adjust.center.nucleic.signal.CenterNucleicSelectionEvent;
import molecule.ui.adjust.center.protein.primary.signal.CenterProteinPrimaryEvent;
import molecule.ui.adjust.center.protein.primary.signal.CenterProteinPrimaryFilterEvent;
import molecule.ui.adjust.center.protein.primary.signal.CenterProteinPrimaryRaiser;
import molecule.ui.adjust.center.protein.primary.signal.CenterProteinPrimarySelectionEvent;
import molecule.ui.adjust.center.protein.quaternary.signal.CenterProteinQuaternaryEvent;
import molecule.ui.adjust.center.protein.quaternary.signal.CenterProteinQuaternaryRaiser;
import molecule.ui.adjust.center.protein.quaternary.signal.CenterProteinQuaternarySelectionEvent;
import molecule.ui.adjust.center.protein.secondary.signal.CenterProteinSecondaryEvent;
import molecule.ui.adjust.center.protein.secondary.signal.CenterProteinSecondaryFilterEvent;
import molecule.ui.adjust.center.protein.secondary.signal.CenterProteinSecondaryRaiser;
import molecule.ui.adjust.center.protein.secondary.signal.CenterProteinSecondarySelectionEvent;
import molecule.ui.adjust.center.protein.tertiary.signal.CenterProteinTertiaryEvent;
import molecule.ui.adjust.center.protein.tertiary.signal.CenterProteinTertiaryFilterEvent;
import molecule.ui.adjust.center.protein.tertiary.signal.CenterProteinTertiaryRaiser;
import molecule.ui.adjust.center.protein.tertiary.signal.CenterProteinTertiarySelectionEvent;
import molecule.ui.adjust.center.water.signal.CenterWaterSelectionEvent;
import molecule.ui.adjust.center.water.signal.CenterWaterSelectionRaiser;
import molecule.ui.adjust.signal.RestrictRadiusEvent;
import molecule.ui.adjust.signal.RestrictRadiusRaiser;
import molecule.ui.adjust.signal.SelectionEvent;
import molecule.ui.signal.AdjustInfoRaiser;

import star.annotations.Handles;
import star.annotations.SignalComponent;
import app.Messages;

@star.annotations.Preferences
@SignalComponent(extend = JPanel.class, raises = {AdjustInfoRaiser.class})
public class AdjustMoleculeUIContainer extends AdjustMoleculeUIContainer_generated
{
	private static final long serialVersionUID = 1L;
	transient private Molecule molecule = null;
	transient private RestrictRenderingPropertiesPanel renderingPanel = null;
	transient private CenterPanel centerPanel = null;
	
	public AdjustMoleculeUIContainer(Molecule molecule)
	{
		super();
		this.molecule = molecule;
		loadPreferences("adjust"); //$NON-NLS-1$
	}
	
	private boolean isInitialized = false;
	public void addNotify()
	{
		super.addNotify();
		if(!isInitialized)
		{
			init();
			isInitialized = true;
		}
	}

	public void removeNotify()
	{
		end();
		isInitialized = false;
		super.removeNotify();
	}

	private void init()
	{
		initEventContainment();
		setLayout(new BorderLayout());
		renderingPanel = new RestrictRenderingPropertiesPanel();
		if(null != renderingPanel)
		{
			add(BorderLayout.SOUTH, renderingPanel);
		}
		centerPanel = new CenterPanel(molecule);
		if(null != centerPanel)
		{
			add(BorderLayout.CENTER, centerPanel);
		}
	}

	private void end()
	{
		removeAll();
		renderingPanel = null;
		centerPanel = null;
	}

	private boolean inInitTree = false;
	public void initTree()
	{
		if(!inInitTree)
		{
			inInitTree = true;
			initLocalVariables();
			if(null != renderingPanel)
			{
				renderingPanel.initTree();
			}
			if(null != centerPanel)
			{
				centerPanel.initTree();
			}
			inInitTree = false;
		}
	}
	
	private boolean inReset = false;
	public void reset()
	{
		if(!inReset)
		{
			inReset = true;
			resetLocalVariables();
			if(null != renderingPanel)
			{
				renderingPanel.reset();
			}
			if(null != centerPanel)
			{
				centerPanel.reset();
			}
			inReset = false;
		}
	}
	
	public static AdjustMoleculeUIContainer getDefaultAdjustPanel(Molecule molecule)
	{
		if (null != molecule)
		{
			PDBTitle[] titleArray = molecule.getTitleArray();
			if ((null != titleArray) && (0 != titleArray.length))
			{
				return new AdjustMoleculeUIContainer(molecule);
			}
		}
		return null;
	}
	
	private void initEventContainment()
	{
		getAdapter().addContained(CenterHeteroSelectionEvent.class); 
		getAdapter().addContained(CenterNucleicFilterEvent.class); 
		getAdapter().addContained(CenterNucleicEvent.class); 
		getAdapter().addContained(CenterNucleicSelectionEvent.class); 
		getAdapter().addContained(CenterProteinPrimaryFilterEvent.class); 
		getAdapter().addContained(CenterProteinPrimaryEvent.class); 
		getAdapter().addContained(CenterProteinPrimarySelectionEvent.class); 
		getAdapter().addContained(CenterProteinQuaternaryEvent.class); 
		getAdapter().addContained(CenterProteinQuaternarySelectionEvent.class); 
		getAdapter().addContained(CenterProteinSecondaryFilterEvent.class); 
		getAdapter().addContained(CenterProteinSecondaryEvent.class); 
		getAdapter().addContained(CenterProteinSecondarySelectionEvent.class); 
		getAdapter().addContained(CenterProteinTertiaryFilterEvent.class); 
		getAdapter().addContained(CenterProteinTertiaryEvent.class); 
		getAdapter().addContained(CenterProteinTertiarySelectionEvent.class); 
		getAdapter().addContained(CenterWaterSelectionEvent.class); 
		getAdapter().addContained(RestrictRadiusEvent.class); 
		getAdapter().addContained(SelectionEvent.class); 
	}
	
	@Handles(raises = {})
	protected void handleRestrictRadiusRaiser(RestrictRadiusRaiser raiser)
	{
		this.radius = raiser.getValue();
		raiseAdjustEvent();
	}
	
	@Handles(raises = {})
	protected void handleCenterHeteroSelectionRaiser(CenterHeteroSelectionRaiser raiser)
	{
		setAdjustInfoMap(CenterHeteroSelectionRaiser.class.getName(), raiser.getSelection(), 0);
		raiseAdjustEvent();
	}
	
	@Handles(raises = {})
	protected void handleCenterNucleicRaiser(CenterNucleicRaiser raiser)
	{
		setAdjustInfoMap(CenterNucleicRaiser.class.getName(), raiser.getSelection(), raiser.getFilterOptions());
		raiseAdjustEvent();
	}

	@Handles(raises = {})
	protected void handleCenterProteinPrimaryRaiser(CenterProteinPrimaryRaiser raiser)
	{
		setAdjustInfoMap(CenterProteinPrimaryRaiser.class.getName(), raiser.getSelection(), raiser.getFilterOptions());
		raiseAdjustEvent();
	}
	
	@Handles(raises = {})
	protected void handleCenterProteinSecondaryRaiser(CenterProteinSecondaryRaiser raiser)
	{
		setAdjustInfoMap(CenterProteinSecondaryRaiser.class.getName(), raiser.getSelection(), raiser.getFilterOptions());
		raiseAdjustEvent();
	}
	
	@Handles(raises = {})
	protected void handleCenterProteinTertiaryRaiser(CenterProteinTertiaryRaiser raiser)
	{
		setAdjustInfoMap(CenterProteinTertiaryRaiser.class.getName(), raiser.getSelection(), raiser.getFilterOptions());
		raiseAdjustEvent();
	}
	
	@Handles(raises = {})
	protected void handleCenterProteinQuaternaryRaiser(CenterProteinQuaternaryRaiser raiser)
	{
		setAdjustInfoMap(CenterProteinQuaternaryRaiser.class.getName(), raiser.getSelection(), 0);
		raiseAdjustEvent();
	}

	@Handles(raises = {})
	protected void handleCenterWaterSelectionRaiser(CenterWaterSelectionRaiser raiser)
	{
		setAdjustInfoMap(CenterWaterSelectionRaiser.class.getName(), raiser.getSelection(), 0);
		raiseAdjustEvent();
	}

	private void resetLocalVariables()
	{
		this.radius = 100;
		this.adjustInfoMap = null;
	}
	
	private void initLocalVariables()
	{
		this.radius = 100;
		this.adjustInfoMap = null;
	}
	
	transient private int radius = 0;

	transient private Map<String, AdjustSelectionInfo> adjustInfoMap = null;
	private void setAdjustInfoMap(final String key, final String[] value, int filterOptions)
	{
		if(null != key)
		{
			if(null == adjustInfoMap)
			{
				adjustInfoMap = new HashMap<String, AdjustSelectionInfo>();
			}
			if(null != adjustInfoMap)
			{
				AdjustSelectionInfo asi = new molecule.AdjustSelectionInfo(value, filterOptions);
				adjustInfoMap.put(key, asi);
			}
		}
	}
	
	transient private AdjustInfo adjustInfo = null;
	public AdjustInfo getAdjustInfo()
	{
		return this.adjustInfo;
	}
	
	transient private boolean inRaiseAdjustEvent = false;
	protected void raiseAdjustEvent()
	{
		if(!inRaiseAdjustEvent && !inReset)
		{
			inRaiseAdjustEvent = true;
			this.adjustInfo = new molecule.AdjustInfo(adjust_string, adjustInfoMap, radius);
			this.raise_AdjustInfoEvent();
			invalidate();
			this.validateTree();
			inRaiseAdjustEvent = false;
		}
	}
	
	public String adjust_string = Messages.getString("AdjustMoleculeUIContainer.1"); //$NON-NLS-1$

	public String getTitle()
	{
		return adjust_string;
	}

	protected void loadPreferences(String preferencesName)
	{
		adjust_string = getPreferences(preferencesName).get("adjust_string", adjust_string).trim(); //$NON-NLS-1$
	}

}
