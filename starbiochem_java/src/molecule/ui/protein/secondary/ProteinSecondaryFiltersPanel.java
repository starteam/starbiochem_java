package molecule.ui.protein.secondary;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import app.Messages;

import star.annotations.SignalComponent;

@star.annotations.Preferences
@SignalComponent(extend = JPanel.class)
public class ProteinSecondaryFiltersPanel extends ProteinSecondaryFiltersPanel_generated
{
	private static final long serialVersionUID = 1L;

	public String proteinsecondaryfilter_string = Messages.getString("ProteinSecondaryFiltersPanel.0"); //$NON-NLS-1$
	
	private ProteinSecondaryFilters filters = null;
	
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
		setBorder(new TitledBorder(proteinsecondaryfilter_string));

		filters = new ProteinSecondaryFilters();
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
		proteinsecondaryfilter_string = getPreferences(preferencesName).get("proteinsecondaryfilter_string", proteinsecondaryfilter_string).trim(); //$NON-NLS-1$
	}

}

