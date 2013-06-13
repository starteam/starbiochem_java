package molecule.ui.protein.quaternary;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import app.Messages;

import molecule.interfaces.Molecule;

import star.annotations.SignalComponent;

@star.annotations.Preferences
@SignalComponent(extend = JPanel.class)
public class ProteinQuaternaryRenderingPanel extends ProteinQuaternaryRenderingPanel_generated
{
	private static final long serialVersionUID = 1L;

	public String quaternary_render_string = Messages.getString("ProteinQuaternaryRenderingPanel.0"); //$NON-NLS-1$
	
	private ProteinQuaternaryRenderingProperties structureProperties = null;
	private ProteinQuaternaryBondRenderingPanel bondProperties = null;
	transient private Molecule molecule = null;

	public ProteinQuaternaryRenderingPanel(Molecule molecule)
	{
		super();
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
		loadPreferences("protein"); //$NON-NLS-1$

		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		setLayout(gridbag);

		constraints.fill = GridBagConstraints.BOTH;

		constraints.weighty = 1.0;
		constraints.gridheight = 1;
		constraints.weightx = 10;

		
		constraints.gridy = 0;
		structureProperties = new ProteinQuaternaryRenderingProperties();
		add(structureProperties, constraints);
		
		constraints.gridy = 1;
		bondProperties = new ProteinQuaternaryBondRenderingPanel(molecule);
		add(bondProperties, constraints);
	}

	private void end()
	{
		removeAll();
		structureProperties = null;
		bondProperties = null;
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
				if(null != bondProperties)
				{
					bondProperties.initTree();
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
				if(null != bondProperties)
				{
					bondProperties.reset();
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

	public int getDefaultTranslucency()
	{
		if(null != structureProperties)
		{
			return structureProperties.getDefaultTranslucency();
		}
		return 0;
	}
	
	public int getDefaultSSBondSize()
	{
		if(null != bondProperties)
		{
			return bondProperties.getDefaultSSBondSize();
		}
		return 0;
	}
	
	public int getDefaultSSSondTranslucency()
	{
		if(null != bondProperties)
		{
			bondProperties.getDefaultSSBondTranslucency();
		}
		return 0;
	}

	public int getDefaultHBondSize()
	{
		if(null != bondProperties)
		{
			return bondProperties.getDefaultHBondSize();
		}
		return 0;
	}
	
	public int getDefaultHBondTranslucency()
	{
		if(null != bondProperties)
		{
			return bondProperties.getDefaultHBondTranslucency();
		}
		return 0;
	}

	protected void loadPreferences(String preferencesName)
	{
		quaternary_render_string = getPreferences(preferencesName).get("quaternary_render_string", quaternary_render_string).trim(); //$NON-NLS-1$
	}

}
