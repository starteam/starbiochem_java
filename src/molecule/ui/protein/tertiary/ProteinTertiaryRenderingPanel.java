package molecule.ui.protein.tertiary;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import molecule.interfaces.Molecule;

import star.annotations.SignalComponent;

@star.annotations.Preferences
@SignalComponent(extend = JPanel.class)
public class ProteinTertiaryRenderingPanel extends ProteinTertiaryRenderingPanel_generated
{
	private static final long serialVersionUID = 1L;

	public String render_string = "RENDER"; //$NON-NLS-1$
	transient private Molecule molecule = null;

	
	private ProteinTertiaryAtomsRenderingPropertiesPanel atomsProperties = null;
	public ProteinTertiaryBondsRenderingPropertiesPanel bondsProperties = null;
	
	public ProteinTertiaryRenderingPanel(Molecule molecule)
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
		
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		setLayout(gridbag);

		constraints.fill = GridBagConstraints.BOTH;

		constraints.weighty = 1.0;
		constraints.gridheight = 1;
		constraints.weightx = 10;
		
		constraints.gridy = 0;
		atomsProperties = new ProteinTertiaryAtomsRenderingPropertiesPanel();
		add(atomsProperties, constraints);
		
		constraints.gridy = 1;
		bondsProperties = new ProteinTertiaryBondsRenderingPropertiesPanel(molecule);
		add(bondsProperties, constraints);
	}
	
	

	private void end()
	{
		removeAll();
		this.atomsProperties = null;
		this.bondsProperties = null;
	}
	
	public void initTree()
	{
		if(null != atomsProperties)
		{
			atomsProperties.initTree();
		}
		if(null != bondsProperties)
		{
			bondsProperties.initTree();
		}	
	}
	
	public void reset()
	{
		if(null != atomsProperties)
		{
			atomsProperties.reset();
		}
		if(null != bondsProperties)
		{
			bondsProperties.reset();
		}	
	}
	
	public int getDefaultSize()
	{
		if(null != atomsProperties)
		{
			return atomsProperties.getDefaultSize();
		}
		return 20;
	}
	
	public int getDefaultTranslucency()
	{
		if(null != atomsProperties)
		{
			return atomsProperties.getDefaultTranslucency();
		}
		return 0;
	}

	public int getDefaultBondTranslucency()
	{
		if(null != bondsProperties)
		{
			return bondsProperties.getDefaultBondTranslucency();
		}
		return 0;
	}
	
	public int getDefaultSSBondSize()
	{
		if(null != bondsProperties)
		{
			return bondsProperties.getDefaultSSBondSize();
		}
		return 0;
	}
	
	public int getDefaultSSBondTranslucency()
	{
		if(null != bondsProperties)
		{
			return bondsProperties.getDefaultSSBondTranslucency();
		}
		return 0;
	}
		
	public int getDefaultHBondSize()
	{
		if(null != bondsProperties)
		{
			return bondsProperties.getDefaultHBondSize();
		}
		return 0;
	}
	
	public int getDefaultHBondTranslucency()
	{
		if(null != bondsProperties)
		{
			return bondsProperties.getDefaultHBondTranslucency();
		}
		return 0;
	}

	protected void loadPreferences(String preferencesName)
	{
		render_string = getPreferences(preferencesName).get("water_render_string", render_string).trim(); //$NON-NLS-1$
	}

}
