package molecule.ui.nucleic;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.border.TitledBorder;

import molecule.interfaces.Molecule;
import molecule.ui.AbstractAtomRenderingProperties;
import molecule.ui.nucleic.properties.NucleicBondTranslucencyPropertyControls;
import star.annotations.SignalComponent;
import app.Messages;

@SignalComponent(extend=AbstractAtomRenderingProperties.class, raises={})
public class NucleicBondsRenderingProperties extends NucleicBondsRenderingProperties_generated
{
	private static final long serialVersionUID = 1L;
	private static final String NUCLEIC = "Selected nucleic"; //$NON-NLS-1$
	transient private Molecule molecule = null;

	public NucleicBondsRenderingProperties(Molecule molecule)
	{
		super();
		this.molecule = molecule;
		loadPreferences("nucleic"); //$NON-NLS-1$
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
		loadPreferences("nucleic"); //$NON-NLS-1$
		setBorder(new TitledBorder(nucleic_bond_render_string));

		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		setLayout(gridbag);

		constraints.fill = GridBagConstraints.BOTH;

		constraints.weighty = 1.0;
		constraints.gridheight = 1;

		constraints.gridy = 0;
		bondProperties = new NucleicBondTranslucencyPropertyControls(bondTranslucency_string, minscale, maxscale, selectedBondTranslucency);
		addPropertyControls(bondProperties, constraints);

		constraints.gridy = 1;
		hBondProperties = new NucleicHBondRenderingProperties(molecule);
		add(hBondProperties, constraints);
		
		invalidate();
		validateTree();
	}
	
	private void end()
	{
		removeAll();
		bondProperties = null;
	}
	
	public String getTitle()
	{
		return NUCLEIC;
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

	private NucleicBondTranslucencyPropertyControls bondProperties = null;
	private NucleicHBondRenderingProperties hBondProperties = null;
	
	String nucleic_bond_render_string = Messages.getString("NucleicBondsRenderingProperties.2"); //$NON-NLS-1$
	
	private boolean inInitTree = false;
	public void initTree()
	{
		if(!inInitTree)
		{
			inInitTree = true;
			initLocalVariables();
			if(null != bondProperties)
			{
				bondProperties.initTree(this.defaultBondTranslucency);
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
			if(null != bondProperties)
			{
				bondProperties.reset(this.defaultBondTranslucency);
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
