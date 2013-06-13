package molecule.ui.protein.tertiary;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.border.TitledBorder;

import molecule.ui.AbstractAtomRenderingProperties;
import molecule.ui.protein.tertiary.properties.ProteinTertiarySizePropertyControls;
import molecule.ui.protein.tertiary.properties.ProteinTertiaryTranslucencyPropertyControls;
import star.annotations.SignalComponent;
import app.Messages;

@SignalComponent(extend=AbstractAtomRenderingProperties.class, raises={})
public class ProteinTertiaryAtomsRenderingPropertiesPanel extends ProteinTertiaryAtomsRenderingPropertiesPanel_generated
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
		loadPreferences("protein"); //$NON-NLS-1$

		setBorder(new TitledBorder(Messages.getString("ProteinTertiaryAtomsRenderingPropertiesPanel.1"))); //$NON-NLS-1$
		
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		setLayout(gridbag);

		constraints.fill = GridBagConstraints.BOTH;

		constraints.weighty = 1.0;
		constraints.gridheight = 1;

		constraints.gridy = 0;
		size = new ProteinTertiarySizePropertyControls(size_string, minsize, maxsize, selectedSize);
		addPropertyControls(size, constraints);

		constraints.gridy = 1;
		translucency = new ProteinTertiaryTranslucencyPropertyControls(translucency_string, minscale, maxscale, this.selectedTranslucency);
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

	private ProteinTertiarySizePropertyControls size = null;
	private ProteinTertiaryTranslucencyPropertyControls translucency = null;
	
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
		selectedSize = defaultSize;
		selectedTranslucency = defaultTranslucency;
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
		selectedSize = defaultSize;
		selectedTranslucency = defaultTranslucency;
	}
	
	public int getDefaultTranslucency()
	{
		return defaultTranslucency;
	}
	
	public int getDefaultSize()
	{
		return defaultSize;
	}
	
	public void setEnabled(boolean enabled)
	{
		super.setEnabled(enabled);
		size.setEnabled(enabled);
		translucency.setEnabled(enabled);
	}
}
