package molecule.ui.hetero;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import star.annotations.SignalComponent;
import app.Messages;

@star.annotations.Preferences
@SignalComponent(extend = JPanel.class)
public class HeteroFiltersPanel extends HeteroFiltersPanel_generated
{
	private static final long serialVersionUID = 1L;

	public String hetero_filter_string = Messages.getString("HeteroFiltersPanel.0"); //$NON-NLS-1$
	
	private HeteroFilters filters = null;
	
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
		setLayout(new BorderLayout());
		setBorder(new TitledBorder(hetero_filter_string));

		filters = new HeteroFilters();
		add(BorderLayout.CENTER, filters);
	}

	private void end()
	{
		removeAll();
		filters = null;
	}

	public void initTree()
	{
		if(null != filters)
		{
			filters.initTree();
		}
	}
	
	public void reset()
	{
		if(null != filters)
		{
			filters.reset();
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
		hetero_filter_string = getPreferences(preferencesName).get("restrict_filter_string", hetero_filter_string).trim(); //$NON-NLS-1$
	}

}

