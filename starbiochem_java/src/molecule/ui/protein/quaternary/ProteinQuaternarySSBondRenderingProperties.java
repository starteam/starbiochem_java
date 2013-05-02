package molecule.ui.protein.quaternary;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import molecule.interfaces.Molecule;
import molecule.ui.AbstractAtomRenderingProperties;
import molecule.ui.protein.quaternary.properties.ProteinQuaternarySSBondTranslucencyPropertyControls;
import molecule.ui.protein.quaternary.properties.ProteinQuaternarySSBondSizePropertyControls;
import star.annotations.SignalComponent;

@SignalComponent(extend=AbstractAtomRenderingProperties.class, raises={})
public class ProteinQuaternarySSBondRenderingProperties extends ProteinQuaternarySSBondRenderingProperties_generated
{
	private static final long serialVersionUID = 1L;
	private static final String PROTEIN = "Selected amino acids"; //$NON-NLS-1$
	
	transient private Molecule molecule = null;

	public ProteinQuaternarySSBondRenderingProperties(Molecule molecule)
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

	private void init()
	{
		String preferencesName = "protein"; //$NON-NLS-1$
		loadPreferences(preferencesName);
		
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		setLayout(gridbag);

		constraints.fill = GridBagConstraints.BOTH;

		constraints.weighty = 1.0;
		constraints.gridheight = 1;

		constraints.gridy = 0;
		size = new ProteinQuaternarySSBondSizePropertyControls(ssbond_size_string, minsize, maxsize, selectedSize);
		addPropertyControls(size, constraints);
		
		constraints.gridy = 1;
		translucency = new ProteinQuaternarySSBondTranslucencyPropertyControls(ssbond_translucency_string, minscale, maxscale, this.selectedTranslucency);
		addPropertyControls(translucency, constraints);

		if(!molecule.hasQuaternarySSBonds())
		{
			size.disableSizeSlider();
			translucency.disableTranslucencySlider();
		}
		
		invalidate();
		validateTree();
	}

	private void end()
	{
		removeAll();
		size = null;
		translucency = null;
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

	private ProteinQuaternarySSBondSizePropertyControls size = null;
	private ProteinQuaternarySSBondTranslucencyPropertyControls translucency = null;

	private boolean inReset = false;
	public void reset()
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

	private void resetLocalVariables()
	{
		selectedSize = defaultSize;
		selectedTranslucency = defaultBondTranslucency;
	}

	public int getDefaultTranslucency()
	{
		return defaultBondTranslucency;
	}
	
	public int getDefaultSSBondSize()
	{
		return defaultSize;
	}
	
}
