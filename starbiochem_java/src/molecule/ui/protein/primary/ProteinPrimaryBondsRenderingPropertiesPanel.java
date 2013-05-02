package molecule.ui.protein.primary;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.border.TitledBorder;

import molecule.ui.AbstractAtomRenderingProperties;
import molecule.ui.protein.primary.properties.ProteinPrimaryBondTranslucencyPropertyControls;

import star.annotations.SignalComponent;
import app.Messages;

@SignalComponent(extend=AbstractAtomRenderingProperties.class, raises={})
public class ProteinPrimaryBondsRenderingPropertiesPanel extends ProteinPrimaryBondsRenderingPropertiesPanel_generated
{
	private static final long serialVersionUID = 1L;
	private static final String PROTEIN = Messages.getString("ProteinPrimaryBondsRenderingPropertiesPanel.0"); //$NON-NLS-1$
	
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
		loadPreferences("protein");
		setBorder(new TitledBorder(Messages.getString("ProteinPrimaryBondsRenderingPropertiesPanel.1"))); //$NON-NLS-1$
		
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		setLayout(gridbag);

		constraints.fill = GridBagConstraints.BOTH;

		constraints.weighty = 1.0;
		constraints.gridheight = 1;

		constraints.gridy = 0;
		bondTranslucency = new ProteinPrimaryBondTranslucencyPropertyControls(bondTranslucency_string, minscale, maxscale, selectedBondTranslucency);
		addPropertyControls(bondTranslucency, constraints);

		invalidate();
		validateTree();
	}

	private void end()
	{
		removeAll();
		bondTranslucency = null;
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

	private ProteinPrimaryBondTranslucencyPropertyControls bondTranslucency = null;
		
	private boolean inInitTree = false;
	public void initTree()
	{
		if(!inInitTree)
		{
			inInitTree = true;
			initLocalVariables();
			if(null != bondTranslucency)
			{
				bondTranslucency.initTree(this.defaultBondTranslucency);
			}
			inInitTree = false;
		}
	}

	private void initLocalVariables()
	{
		selectedBondTranslucency = defaultBondTranslucency;
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
			if(null != bondTranslucency)
			{
				bondTranslucency.reset(this.defaultBondTranslucency);
			}
			inReset = false;
		}
	}

	private void resetLocalVariables()
	{
		selectedBondTranslucency = defaultBondTranslucency;
		selectedSize = defaultSize;
		selectedTranslucency = defaultTranslucency;
	}
	
	public int getDefaultBondTranslucency()
	{
		return defaultBondTranslucency;
	}
	
	public int getDefaultSSBondTranslucency()
	{
		return defaultTranslucency;
	}
	
	public int getDefaultSSBondSize()
	{
		return defaultSize;
	}
}
