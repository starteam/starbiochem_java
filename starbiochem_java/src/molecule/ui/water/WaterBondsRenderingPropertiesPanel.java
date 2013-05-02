package molecule.ui.water;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.border.TitledBorder;

import pdb.PDBRemark;

import molecule.interfaces.Molecule;
import molecule.ui.AbstractAtomRenderingProperties;
import molecule.ui.water.properties.WaterBondTranslucencyPropertyControls;
import star.annotations.SignalComponent;
import app.Messages;

@SignalComponent(extend=AbstractAtomRenderingProperties.class, raises={})
public class WaterBondsRenderingPropertiesPanel extends WaterBondsRenderingPropertiesPanel_generated
{
	private static final long serialVersionUID = 1L;
	private static final String WATER = "Selected water"; //$NON-NLS-1$

	private Molecule molecule = null;
	public WaterBondsRenderingPropertiesPanel(Molecule molecule)
	{
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
		loadPreferences("water"); //$NON-NLS-1$
		overridePreferences(molecule);
		
		setBorder(new TitledBorder(Messages.getString("WaterBondsRenderingPropertiesPanel.1"))); //$NON-NLS-1$

		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		setLayout(gridbag);

		constraints.fill = GridBagConstraints.BOTH;

		constraints.weighty = 1.0;
		constraints.gridheight = 1;
		constraints.insets = new Insets(0, 0, 12, 0);
		
		constraints.gridy = 0;
		bondtranslucency = new WaterBondTranslucencyPropertyControls(bondTranslucency_string, minscale, maxscale, this.selectedBondTranslucency);
		addPropertyControls(bondtranslucency, constraints);
		
		constraints.gridy = 1;
		constraints.insets = new Insets(0, 0, 0, 0);
		hBondProperties = new WaterHBondRenderingProperties(molecule);
		add(hBondProperties, constraints);
		
		invalidate();
		validateTree();
	}
	
	private void end()
	{
		removeAll();
		bondtranslucency = null;
		hBondProperties = null;
	}

	public String getTitle()
	{
		return WATER;
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

	private WaterBondTranslucencyPropertyControls bondtranslucency = null;
	private WaterHBondRenderingProperties hBondProperties = null;
	
	String water_bond_render_string = Messages.getString("WaterBondsRenderingPropertiesPanel.2"); //$NON-NLS-1$
	
	private void overridePreferences(Molecule molecule)
	{
		if(null != molecule)
		{
		    PDBRemark[] remarkArray = molecule.getRemarkArray();
			if(null != remarkArray)
			{
	    		for(PDBRemark remark : remarkArray)
	    		{
	   				if(remark.isWaterOn())
	   				{
	   					this.defaultBondTranslucency = 0;
	   					this.selectedBondTranslucency = 0;
	   					break;
	    			}
	    		}
			}
		}	
	}
	
	private boolean inInitTree = false;
	public void initTree()
	{
		if(!inInitTree)
		{
			inInitTree = true;
			initLocalVariables();
			if(null != bondtranslucency)
			{
				bondtranslucency.initTree(this.defaultBondTranslucency);
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
			if(null != bondtranslucency)
			{
				bondtranslucency.reset(this.defaultBondTranslucency);
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
	
	public int getDefaultHBondSize()
	{
		return hBondProperties.getDefaultHBondSize();
	}
	
	public int getDefaultHBondTranslucency()
	{
		return hBondProperties.getDefaultHBondTranslucency();
	}
}
