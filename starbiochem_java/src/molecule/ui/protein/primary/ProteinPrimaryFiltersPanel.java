package molecule.ui.protein.primary;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import app.Messages;

import star.annotations.SignalComponent;

@star.annotations.Preferences
@SignalComponent(extend = JPanel.class)
public class ProteinPrimaryFiltersPanel extends ProteinPrimaryFiltersPanel_generated
{
	private static final long serialVersionUID = 1L;

	public String proteinprimaryfilter_string = Messages.getString("ProteinPrimaryFiltersPanel.0"); //$NON-NLS-1$
	
	private ProteinPrimaryFilters filters = null;
	
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

		setLayout(new BorderLayout());
		setBorder(new TitledBorder(proteinprimaryfilter_string));

		filters = new ProteinPrimaryFilters();
		add(BorderLayout.CENTER, filters);
	}
	
	private void end()
	{
		removeAll();
		filters = null;
	}
	
	transient private boolean inInitTree = false;
	public void initTree()
	{
		if(null != filters)
		{
			if(!inInitTree)
			{
				inInitTree = true;
				filters.initTree();
				inInitTree = false;
			}
		}
	}
	
	transient private boolean inReset = false;
	public void reset()
	{
		if(null != filters)
		{
			if(!inReset)
			{
				inReset = true;
				filters.reset();
				inReset = false;
			}
		}
	}
	
	public int getDefaultFilter()
	{
		if(null != filters)
		{
			return filters.getDefaultFilter();
		}
		return 0;
	}
	
	public boolean isDefaultAutomaticallyRendered()
	{
		if(null != filters)
		{
			return filters.isDefaultAutomaticallyRendered();
		}
		return true;
	}
	
	protected void loadPreferences(String preferencesName)
	{
		proteinprimaryfilter_string = getPreferences(preferencesName).get("proteinprimaryfilter_string", proteinprimaryfilter_string).trim(); //$NON-NLS-1$
	}

}

