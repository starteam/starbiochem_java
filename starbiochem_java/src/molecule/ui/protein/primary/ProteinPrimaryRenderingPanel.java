package molecule.ui.protein.primary;

import java.awt.GridLayout;

import javax.swing.JPanel;


import star.annotations.SignalComponent;

@star.annotations.Preferences
@SignalComponent(extend = JPanel.class)
public class ProteinPrimaryRenderingPanel extends ProteinPrimaryRenderingPanel_generated
{
	private static final long serialVersionUID = 1L;

	public String render_string = "RENDER"; //$NON-NLS-1$
	
	private ProteinPrimaryAtomsRenderingPropertiesPanel atomsProperties = null;
	private ProteinPrimaryBondsRenderingPropertiesPanel bondsProperties = null;
	
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

		setLayout(new GridLayout(2,1));
		
		atomsProperties = new ProteinPrimaryAtomsRenderingPropertiesPanel();
		bondsProperties = new ProteinPrimaryBondsRenderingPropertiesPanel();
		
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
		return 25;
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

	protected void loadPreferences(String preferencesName)
	{
		render_string = getPreferences(preferencesName).get("water_render_string", render_string).trim(); //$NON-NLS-1$
	}

}
