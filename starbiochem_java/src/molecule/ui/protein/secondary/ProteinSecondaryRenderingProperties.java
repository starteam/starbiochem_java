package molecule.ui.protein.secondary;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.border.TitledBorder;

import molecule.ui.AbstractAtomRenderingProperties;
import molecule.ui.protein.secondary.properties.ProteinSecondarySizePropertyControls;
import star.annotations.SignalComponent;
import app.Messages;

@SignalComponent(extend=AbstractAtomRenderingProperties.class, raises={})
public class ProteinSecondaryRenderingProperties extends ProteinSecondaryRenderingProperties_generated
{
	private static final long serialVersionUID = 1L;
	private static final String PROTEIN = "Selected amino acids"; //$NON-NLS-1$

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
		String preferencesName = "protein"; //$NON-NLS-1$
		loadPreferences(preferencesName);

		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		setLayout(gridbag);
		setBorder(new TitledBorder(Messages.getString("ProteinSecondaryRenderingProperties.1"))); //$NON-NLS-1$


		constraints.fill = GridBagConstraints.BOTH;

		constraints.weighty = 1.0;
		constraints.gridheight = 1;

		constraints.gridy = 0;
		size = new ProteinSecondarySizePropertyControls(size_string, minsize, maxsize, selectedSize);
		addPropertyControls(size, constraints);
	}

	private void end()
	{
		removeAll();
		size = null;
	}
	
	public String getTitle()
	{
		return PROTEIN;
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

	private ProteinSecondarySizePropertyControls size = null;
	
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
			inInitTree = false;
		}
	}
	
	private void initLocalVariables()
	{
		selectedSize = defaultSize;
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
			inReset = false;
		}
	}
	
	private void resetLocalVariables()
	{
		selectedSize = defaultSize;
	}

	public int getDefaultSize()
	{
		return defaultSize;
	}
	
}
