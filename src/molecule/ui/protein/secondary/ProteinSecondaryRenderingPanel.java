package molecule.ui.protein.secondary;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import molecule.interfaces.Molecule;

import star.annotations.SignalComponent;

@star.annotations.Preferences
@SignalComponent(extend = JPanel.class)
public class ProteinSecondaryRenderingPanel extends ProteinSecondaryRenderingPanel_generated
{
	private static final long serialVersionUID = 1L;

	public String proteinsecondary_render_string = "RENDER"; //$NON-NLS-1$
	private ProteinSecondaryRenderingProperties structureProperties = null;
	private ProteinSecondaryHBondRenderingProperties hBondProperties = null;
	transient private Molecule molecule = null;

	public ProteinSecondaryRenderingPanel(Molecule molecule)
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
		
		constraints.gridy = 0;;
		structureProperties = new ProteinSecondaryRenderingProperties();
		add(structureProperties, constraints);
		
		constraints.gridy = 1;;
		hBondProperties = new ProteinSecondaryHBondRenderingProperties(molecule);
		add(hBondProperties, constraints);
	}

	private void end()
	{
		removeAll();
		structureProperties = null;
		hBondProperties = null;
	}
	
	transient private boolean inInitTree = false;
	public void initTree()
	{
		if(null != structureProperties)
		{
			if(!inInitTree)
			{
				inInitTree = true;
				if(null != structureProperties)
				{
					structureProperties.initTree();
				}
				inInitTree = false;
			}
		}
		if(null != hBondProperties)
		{
			if(!inInitTree)
			{
				inInitTree = true;
				if(null != hBondProperties)
				{
					hBondProperties.initTree();
				}
				inInitTree = false;
			}
		}
	}
	
	transient private boolean inReset = false;
	public void reset()
	{
		if(null != structureProperties)
		{
			if(!inReset)
			{
				inReset = true;
				if(null != structureProperties)
				{
					structureProperties.reset();
				}
				inReset = false;
			}
		}
		if(null != hBondProperties)
		{
			if(!inReset)
			{
				inReset = true;
				if(null != hBondProperties)
				{
					hBondProperties.reset();
				}
				inReset = false;
			}
		}
	}
	
	public int getDefaultSize()
	{
		if(null != structureProperties)
		{
			return structureProperties.getDefaultSize();
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
	
	public int getDefaultHBondTranslucency()
	{
		if(null != hBondProperties)
		{
			return hBondProperties.getDefaultHBondTranslucency();
		}
		return 0;
	}
	
protected void loadPreferences(String preferencesName)
	{
		proteinsecondary_render_string = getPreferences(preferencesName).get("proteinsecondary_render_string", proteinsecondary_render_string).trim(); //$NON-NLS-1$
	}

}
