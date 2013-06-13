package molecule.ui.hetero;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.border.TitledBorder;

import molecule.interfaces.Molecule;
import molecule.ui.AbstractAtomRenderingProperties;
import molecule.ui.hetero.properties.HeteroBondTranslucencyPropertyControls;
import star.annotations.SignalComponent;
import app.Messages;

@SignalComponent(extend=AbstractAtomRenderingProperties.class, raises={})
public class HeteroBondsRenderingProperties extends HeteroBondsRenderingProperties_generated
{
	private static final long serialVersionUID = 1L;
	private static final String HETERO = "Selected hetero"; //$NON-NLS-1$
	transient private Molecule molecule = null;
	private HeteroBondTranslucencyPropertyControls bondtranslucency = null;
	private HeteroHBondRenderingProperties hBondProperties = null;

	
	public HeteroBondsRenderingProperties(Molecule molecule)
	{
		super();
		this.molecule = molecule;
		loadPreferences("hetero"); //$NON-NLS-1$
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
		loadPreferences("hetero"); //$NON-NLS-1$

		setBorder(new TitledBorder(Messages.getString("HeteroBondsRenderingProperties.2"))); //$NON-NLS-1$

		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		setLayout(gridbag);

		constraints.fill = GridBagConstraints.BOTH;

		constraints.gridx = 0;
		constraints.weighty = 1.0;

		constraints.gridy = 0;
		bondtranslucency = new HeteroBondTranslucencyPropertyControls(bondTranslucency_string, minscale, maxscale, selectedBondTranslucency);
		addPropertyControls(bondtranslucency, constraints);
		
		constraints.gridy = 1;
		hBondProperties = new HeteroHBondRenderingProperties(molecule);
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
		return HETERO;
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
		if(!inInitTree)
		{
			inInitTree = true;
			initLocalVariables();
			if(null != bondtranslucency)
			{
				bondtranslucency.initTree(this.defaultBondTranslucency);
				hBondProperties.initTree();
			}
			inInitTree = false;
		}
	}	

	private void initLocalVariables()
	{
		selectedBondTranslucency = defaultBondTranslucency;
		selectedSize = getDefaultHBondSize();
		selectedTranslucency = getDefaultHBondTranslucency();
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
	
	public int getDefaultHBondTranslucency()
	{
		if(null != hBondProperties)
		{
			return hBondProperties.getDefaultHBondTranslucency();
		}
		return 0;
	}
	
	public int getDefaultHBondSize()
	{
		if(null != hBondProperties)
		{
			return hBondProperties.getDefaultHBondSize();
		}
		return 0;
	}
}
