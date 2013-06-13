package molecule.ui.protein.quaternary;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import molecule.interfaces.Molecule;
import molecule.ui.AbstractAtomRenderingProperties;
import molecule.ui.protein.quaternary.properties.ProteinQuaternaryHBondSizePropertyControls;
import molecule.ui.protein.quaternary.properties.ProteinQuaternaryHBondTranslucencyPropertyControls;
import star.annotations.SignalComponent;

@SignalComponent(extend=AbstractAtomRenderingProperties.class, raises={})
public class ProteinQuaternaryHBondRenderingProperties extends ProteinQuaternaryHBondRenderingProperties_generated
{
	private static final long serialVersionUID = 1L;
	private static final String PROTEIN = "Selected amino acids"; //$NON-NLS-1$
	transient private Molecule molecule = null;
	
	private ProteinQuaternaryHBondSizePropertyControls size = null;
	private ProteinQuaternaryHBondTranslucencyPropertyControls translucency = null;
	
	public ProteinQuaternaryHBondRenderingProperties(Molecule molecule)
	{
		super();
		this.molecule = molecule;
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

	public boolean isEnabledHBonds = true;
	private void init()
	{
		String preferencesName = "protein"; //$NON-NLS-1$
		loadPreferences(preferencesName);
			
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		setLayout(gridbag);
		
		constraints.fill = GridBagConstraints.BOTH;

		constraints.weighty = 2.0;
		constraints.gridheight = 1;
		
		constraints.gridy = 0;
		size = new ProteinQuaternaryHBondSizePropertyControls(hbond_size_string, minsize, maxsize, this.selectedSize);
		addPropertyControls(size, constraints);

		constraints.gridy = 1;
		translucency = new ProteinQuaternaryHBondTranslucencyPropertyControls(hbond_translucency_string, minscale, maxscale, this.selectedTranslucency);
		addPropertyControls(translucency, constraints);
		
		disableHBonds();

		invalidate();
		validateTree();
	}
	
	private void end()
	{
		removeAll();
		size = null;
		translucency = null;
	}
	
	private boolean disableHBonds()
	{
		if(molecule.getQuaternaryHBonds() == null)
		{
			size.disableSizeSlider();
			translucency.disableTranslucencySlider();
			isEnabledHBonds = false;
		}
		return isEnabledHBonds;
	}
	
	public String getTitle()
	{
		return PROTEIN;
	}

	private String propertyName = null;
	
	public String getPropertyName()
	{
		return this.propertyName;
	}
	
	public void setPropertyName(String propertyName)
	{
		this.propertyName = propertyName;
	}
	
	protected int propertyValue;
	
	public int getPropertyValue()
    {
		return this.propertyValue;
    }

	public void setPropertyValue(int propertyValue)
	{
		this.propertyValue = propertyValue;
	}
	
	private boolean inInitTree = false;
	public void initTree()
	{
		if(isEnabledHBonds)
		{
			if(!inInitTree)
			{
				inInitTree = true;
				initLocalVariables();
				if(null != size)
				{
					size.initTree(this.defaultSize);
				}
				if(null != translucency)
				{
					translucency.initTree(this.defaultTranslucency);
				}
				inInitTree = false;
			}
		}
	}

	private void initLocalVariables()
	{
		selectedSize = defaultSize;
		selectedTranslucency = defaultTranslucency;
	}
	
	private boolean inReset = false;
	public void reset()
	{
		if(isEnabledHBonds)
		{
			if(!inReset)
			{
				inReset = true;
				resetLocalVariables();
				if(null != size)
				{
					size.reset(this.defaultSize);
				}
				if(null != translucency)
				{
					translucency.reset(this.defaultTranslucency);
				}
				inReset = false;
			}
		}
	}

	private void resetLocalVariables()
	{
		selectedSize = defaultSize;
		selectedTranslucency = defaultTranslucency;
	}
	
	public int getDefaultHBondTranslucency()
	{
		return defaultTranslucency;
	}
	
	public int getDefaultHBondSize()
	{
		return defaultSize;
	}
	
}
