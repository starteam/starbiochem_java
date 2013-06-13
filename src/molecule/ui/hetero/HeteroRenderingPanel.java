package molecule.ui.hetero;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import molecule.interfaces.Molecule;

import star.annotations.SignalComponent;
import app.Messages;

@star.annotations.Preferences
@SignalComponent(extend = JPanel.class, raises={})
public class HeteroRenderingPanel extends HeteroRenderingPanel_generated
{
	private static final long serialVersionUID = 1L;

	public String render_string = Messages.getString("HeteroRenderingPanel.0"); //$NON-NLS-1$
	transient private Molecule molecule = null;
	private HeteroAtomsRenderingProperties atomsProperties = null;
	private HeteroBondsRenderingProperties bondsProperties = null;

	public HeteroRenderingPanel(Molecule molecule)
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
		loadPreferences("hetero"); //$NON-NLS-1$

		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		setLayout(gridbag);

		constraints.fill = GridBagConstraints.BOTH;

		constraints.weighty = 1.0;
		constraints.gridheight = 1;
		constraints.weightx = 10;
		
		constraints.gridy = 0;
		atomsProperties = new HeteroAtomsRenderingProperties(molecule);
		add(atomsProperties, constraints);

		constraints.gridy = 1;
		bondsProperties = new HeteroBondsRenderingProperties(molecule);
		add(bondsProperties, constraints);
	}

	private void end()
	{
		removeAll();
		atomsProperties = null;
		bondsProperties = null;
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
	
	public int getDefaultHBondSize()
	{
		if(null != bondsProperties)
		{
			return bondsProperties.getDefaultHBondSize();
		}
		return 0;
	}
	
	public int getDefaultHBondTransluceny()
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
