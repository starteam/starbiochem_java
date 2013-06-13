package molecule.ui.protein.quaternary;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.border.TitledBorder;

import app.Messages;

import molecule.ui.AbstractAtomRenderingProperties;
import molecule.ui.protein.quaternary.properties.ProteinQuaternarySizePropertyControls;
import molecule.ui.protein.quaternary.properties.ProteinQuaternaryTranslucencyPropertyControls;
import star.annotations.SignalComponent;

@SignalComponent(extend=AbstractAtomRenderingProperties.class, raises={})
public class ProteinQuaternaryRenderingProperties extends ProteinQuaternaryRenderingProperties_generated
{
	private static final long serialVersionUID = 1L;
	private static final String PROTEIN = "Selected amino acids"; //$NON-NLS-1$

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

		setBorder(new TitledBorder(Messages.getString("ProteinQuaternaryRenderingProperties.0"))); //$NON-NLS-1$
		
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		setLayout(gridbag);

		constraints.fill = GridBagConstraints.BOTH;

		constraints.weighty = 1.0;
		constraints.gridheight = 1;

		constraints.gridy = 0;
		size = new ProteinQuaternarySizePropertyControls(size_string, minsize, maxsize, selectedSize);
		addPropertyControls(size, constraints);

		constraints.gridy = 1;
		translucency = new ProteinQuaternaryTranslucencyPropertyControls(translucency_string, minscale, maxscale, this.selectedTranslucency);
		addPropertyControls(translucency, constraints);

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

	private ProteinQuaternarySizePropertyControls size = null;
	private ProteinQuaternaryTranslucencyPropertyControls translucency = null;
	
	private boolean inInitTree = false;
	public void initTree()
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
	
	private void initLocalVariables()
	{
		selectedTranslucency = defaultTranslucency;
		selectedSize = defaultSize;
	}
	
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
		selectedTranslucency = defaultTranslucency;
		selectedSize = defaultSize;
	}
	
	public int getDefaultSize()
	{
		return defaultSize;
	}

	public int getDefaultTranslucency()
	{
		return defaultTranslucency;
	}

}
