package molecule.ui.protein.tertiary;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.border.TitledBorder;

import molecule.interfaces.Molecule;
import molecule.ui.AbstractAtomRenderingProperties;
import molecule.ui.protein.tertiary.properties.ProteinTertiaryBondTranslucencyPropertyControls;

import star.annotations.SignalComponent;
import app.Messages;

@SignalComponent(extend=AbstractAtomRenderingProperties.class, raises={})
public class ProteinTertiaryBondsRenderingPropertiesPanel extends ProteinTertiaryBondsRenderingPropertiesPanel_generated
{
	private static final long serialVersionUID = 1L;
	transient private Molecule molecule = null;
	private static final String PROTEIN = "Selected amino acids"; //$NON-NLS-1$
	
	public ProteinTertiaryBondsRenderingPropertiesPanel(Molecule molecule)
	{
		super();
		this.molecule = molecule;
		loadPreferences("protein"); //$NON-NLS-1$
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
		loadPreferences("protein"); //$NON-NLS-1$

		setBorder(new TitledBorder(Messages.getString("ProteinTertiaryBondsRenderingPropertiesPanel.2"))); //$NON-NLS-1$
		
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		setLayout(gridbag);

		constraints.fill = GridBagConstraints.BOTH;

		constraints.weighty = 1.0;
		constraints.gridheight = 1;
		constraints.insets = new Insets(0, 0, 12, 0);
		
		constraints.gridy = 0;
		bondTranslucency = new ProteinTertiaryBondTranslucencyPropertyControls(bondTranslucency_string, minscale, maxscale, selectedBondTranslucency);
		addPropertyControls(bondTranslucency, constraints);
			
		constraints.gridy = 1;
		constraints.insets = new Insets(0, 0, 12, 0);
		hBondProperties = new ProteinTertiaryHBondRenderingProperties(molecule);
		add(hBondProperties, constraints);
		
		constraints.gridy = 2;
		constraints.insets = new Insets(0, 0, 0, 0);
		ssbondProperties = new ProteinTertiarySSBondRenderingPropertiesPanel(molecule);
		add(ssbondProperties, constraints);
		
		invalidate();
		validateTree();
	}

	private void end()
	{
		removeAll();
		bondTranslucency = null;
		ssbondProperties = null;
		hBondProperties = null;
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

	private ProteinTertiaryBondTranslucencyPropertyControls bondTranslucency = null;
	public ProteinTertiarySSBondRenderingPropertiesPanel ssbondProperties = null;
	private ProteinTertiaryHBondRenderingProperties hBondProperties = null;
	
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
			if(null != ssbondProperties)
			{
				ssbondProperties.initTree();
			}
			if(null != hBondProperties)
			{
				hBondProperties.initTree();
			}
			
			inInitTree = false;
		}
	}
	
	private void initLocalVariables()
	{
		selectedBondTranslucency = defaultBondTranslucency;
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
			if(null != ssbondProperties)
			{
				ssbondProperties.reset();
			}
			if(null != hBondProperties)
			{
				hBondProperties.reset();
			}
			inReset = false;
		}
	}
	
	private void resetLocalVariables()
	{
		selectedBondTranslucency = defaultBondTranslucency;
	}
	
	public int getDefaultBondTranslucency()
	{
		return defaultBondTranslucency;
	}
	
	public int getDefaultSSBondTranslucency()
	{
		return ssbondProperties.getDefaultSSBondTranslucency();
	}
	
	public int getDefaultSSBondSize()
	{
		return ssbondProperties.getDefaultSSBondSize();
	}
	
	public int getDefaultHBondSize()
	{
		return hBondProperties.getDefaultHBondSize();
	}
	
	public int getDefaultHBondTranslucency()
	{
		return hBondProperties.getDefaultHBondTranslucency();
	}
	
	public void setEnabled(boolean enabled)
	{
		super.setEnabled(enabled);
		bondTranslucency.setEnabled(enabled);
	}

}
