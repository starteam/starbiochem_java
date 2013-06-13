package molecule.ui.water;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.border.TitledBorder;

import pdb.PDBRemark;

import molecule.interfaces.Molecule;
import molecule.ui.AbstractAtomRenderingProperties;
import molecule.ui.water.properties.WaterTranslucencyPropertyControls;
import molecule.ui.water.properties.WaterSizePropertyControls;

import star.annotations.SignalComponent;
import app.Messages;

@SignalComponent(extend=AbstractAtomRenderingProperties.class, raises={})
public class WaterAtomsRenderingPropertiesPanel extends WaterAtomsRenderingPropertiesPanel_generated
{
	private static final long serialVersionUID = 1L;
	private static final String WATER = "Selected water"; //$NON-NLS-1$

	private Molecule molecule = null;
	public WaterAtomsRenderingPropertiesPanel(Molecule molecule)
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
		setBorder(new TitledBorder(Messages.getString("WaterAtomsRenderingPropertiesPanel.1"))); //$NON-NLS-1$
		overridePreferences(molecule);
		
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		setLayout(gridbag);

		constraints.fill = GridBagConstraints.BOTH;

		constraints.weighty = 1.0;
		constraints.gridheight = 1;

		constraints.gridy = 0;
		size = new WaterSizePropertyControls(size_string, minsize, maxsize, selectedSize);
		addPropertyControls(size, constraints);

		constraints.gridy = 1;
		translucency = new WaterTranslucencyPropertyControls(translucency_string, minscale, maxscale, this.selectedTranslucency);
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

	private WaterSizePropertyControls size = null;
	private WaterTranslucencyPropertyControls translucency = null;
	
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
	   					this.defaultSize = 25;
	   					this.selectedSize = 25;
	   					this.defaultTranslucency = 0;
	   					this.selectedTranslucency = 0;
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

	public int getDefaultSize()
	{
		return defaultSize;
	}
	
	public int getDefaultTranslucency()
	{
		return defaultTranslucency;
	}
	
}
