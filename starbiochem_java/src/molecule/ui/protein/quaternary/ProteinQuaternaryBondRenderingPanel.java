package molecule.ui.protein.quaternary;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import molecule.interfaces.Molecule;
import star.annotations.SignalComponent;
import app.Messages;

@star.annotations.Preferences
@SignalComponent(extend = JPanel.class)
public class ProteinQuaternaryBondRenderingPanel extends ProteinQuaternaryBondRenderingPanel_generated
{
	private static final long serialVersionUID = 1L;
	
	transient private Molecule molecule = null;

	public ProteinQuaternaryBondRenderingPanel(Molecule molecule)
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
		setBorder(new TitledBorder(Messages.getString("ProteinQuaternaryBondRenderingPanel.0"))); //$NON-NLS-1$
		
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		setLayout(gridbag);

		constraints.fill = GridBagConstraints.BOTH;

		constraints.weighty = 1.0;
		constraints.gridheight = 1;
		constraints.insets = new Insets(0, 0, 12, 0);
		constraints.weightx = 12;
		
		constraints.gridy = 0;
		hbondProperties = new ProteinQuaternaryHBondRenderingProperties(molecule);
		add(hbondProperties, constraints);
		
		constraints.gridy = 1;
		constraints.insets = new Insets(0, 0, 0, 0);
		ssbondProperties = new ProteinQuaternarySSBondRenderingProperties(molecule);
		add(ssbondProperties, constraints);
		
		invalidate();
		validateTree();
	}

	private void end()
	{
		removeAll();

		ssbondProperties = null;
		hbondProperties = null;
	}
	private ProteinQuaternarySSBondRenderingProperties ssbondProperties = null;
	private ProteinQuaternaryHBondRenderingProperties hbondProperties = null;
	
	private boolean inInitTree = false;
	public void initTree()
	{
		if(!inInitTree)
		{
			inInitTree = true;
			if(null != ssbondProperties)
			{
				ssbondProperties.reset();
			}
			if(null != hbondProperties)
			{
				hbondProperties.initTree();
			}
			inInitTree = false;
		}
	}
	
	private boolean inReset = false;
	public void reset()
	{
		if(!inReset)
		{
			inReset = true;
			if(null != ssbondProperties)
			{
				ssbondProperties.reset();
			}
			if(null != hbondProperties)
			{
				hbondProperties.reset();
			}
			inReset = false;
		}
	}
	
	public int getDefaultSSBondTranslucency()
	{
		return ssbondProperties.getDefaultTranslucency();
	}
	
	public int getDefaultSSBondSize()
	{
		return ssbondProperties.getDefaultSSBondSize();
	}
	
	public int getDefaultHBondTranslucency()
	{
		return hbondProperties.getDefaultHBondTranslucency();
	}
	
	public int getDefaultHBondSize()
	{
		return hbondProperties.getDefaultHBondSize();
	}

}
