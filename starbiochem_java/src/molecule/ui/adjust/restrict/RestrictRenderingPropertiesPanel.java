package molecule.ui.adjust.restrict;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.border.TitledBorder;

import molecule.ui.AbstractLogRenderingProperties;
import molecule.ui.adjust.properties.RestrictRadiusPropertyControls;
import star.annotations.SignalComponent;
import app.Messages;

@star.annotations.Preferences
@SignalComponent(extend=AbstractLogRenderingProperties.class, raises={})
public class RestrictRenderingPropertiesPanel extends RestrictRenderingPropertiesPanel_generated
{
	private static final long serialVersionUID = 1L;
	private String TITLE = Messages.getString("RestrictRenderingPropertiesPanel.0"); //$NON-NLS-1$
	
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
		loadPreferences("adjust"); //$NON-NLS-1$

		setBorder(new TitledBorder(getTitle()));
			
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints constraints = new GridBagConstraints();
		setLayout(gridbag);

		constraints.fill = GridBagConstraints.BOTH;

		constraints.weighty = 1.0;
		constraints.gridheight = 1;

		constraints.gridy = 0;
		radius = new RestrictRadiusPropertyControls(size_string, selectedSize);
		addPropertyLogControls(radius, constraints);

		invalidate();
		validateTree();
	}
	
	public Insets getInsets()
	{
		return new Insets(25,0,15,0);
	}

	public Insets getInsets(Insets insets)
	{
		return new Insets(25,0,15,0);
	}	


	private void end()
	{
		removeAll();
		this.radius = null;
	}
	
	public String getTitle()
	{
		return TITLE;
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

	private RestrictRadiusPropertyControls radius = null;
	
	private boolean inInitTree = false;
	public void initTree()
	{
		if(!inInitTree)
		{
			inInitTree = true;
			initLocalVariables();
			if(null != radius)
			{
				radius.initTree(this.defaultSize);
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
			resetLocalVariables();
			if(null != radius)
			{
				radius.reset(this.defaultSize);
			}
			inReset = false;
		}
	}

	private void resetLocalVariables()
	{
		selectedSize = defaultSize;
		selectedTranslucency = defaultTranslucency;
	}

	private void initLocalVariables()
	{
		selectedSize = defaultSize;
		selectedTranslucency = defaultTranslucency;
	}

	public int getDefaultSize()
	{
		return defaultSize;
	}
	
	
}
