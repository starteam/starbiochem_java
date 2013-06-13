package molecule.ui.water;

import java.awt.GridLayout;

import javax.swing.JPanel;

import molecule.interfaces.Molecule;

import star.annotations.SignalComponent;

@star.annotations.Preferences
@SignalComponent(extend = JPanel.class)
public class WaterRenderingPanel extends WaterRenderingPanel_generated
{
	private static final long serialVersionUID = 1L;

	public String water_render_string = "RENDER"; //$NON-NLS-1$
	
	private Molecule molecule = null;
	public WaterRenderingPanel(Molecule molecule)
	{
		this.molecule = molecule;
	}
	
	private WaterAtomsRenderingPropertiesPanel atomsProperties = null;
	private WaterBondsRenderingPropertiesPanel bondsProperties = null;
	
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

		setLayout(new GridLayout(2,1));
		
		atomsProperties = new WaterAtomsRenderingPropertiesPanel(molecule);
		bondsProperties = new WaterBondsRenderingPropertiesPanel(molecule);

		add(atomsProperties);
		add(bondsProperties);
	}

	private void end()
	{
		removeAll();
		atomsProperties = null;
		bondsProperties = null;
	}
	
	transient private boolean inInitTree = false;
	public void initTree()
	{
		if(!inInitTree)
		{
			inInitTree = true;
			if(null != atomsProperties)
			{
				atomsProperties.initTree();
			}
			if(null != bondsProperties)
			{
				bondsProperties.initTree();
			}
			inInitTree = false;
		}
	}
	
	transient private boolean inReset = false;
	public void reset()
	{
		if(!inReset)
		{
			inReset = true;
			if(null != atomsProperties)
			{
				atomsProperties.reset();
			}
			if(null != bondsProperties)
			{
				bondsProperties.reset();
			}
			inReset = false;
		}
	}
	
	public int getDefaultSize()
	{
		if(null != atomsProperties)
		{
			return atomsProperties.getDefaultSize();
		}
		return 0;
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
	
	public int getDefaultHBondTranslucency()
	{
		if(null != bondsProperties)
		{
			return bondsProperties.getDefaultHBondTranslucency();
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
	
	protected void loadPreferences(String preferencesName)
	{
		water_render_string = getPreferences(preferencesName).get("water_render_string", water_render_string).trim(); //$NON-NLS-1$
	}

}
