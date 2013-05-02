package molecule.ui.nucleic;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import app.Messages;

import star.annotations.SignalComponent;

@star.annotations.Preferences
@SignalComponent(extend = JPanel.class)
public class NucleicFiltersPanel extends NucleicFiltersPanel_generated
{
	private static final long serialVersionUID = 1L;

	public String nucleic_filter_string = Messages.getString("NucleicFiltersPanel.0"); //$NON-NLS-1$
	
	private NucleicFilters filters = null;
	
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
		loadPreferences("nucleic"); //$NON-NLS-1$
		
		setLayout(new BorderLayout());
		setBorder(new TitledBorder(nucleic_filter_string));

		filters = new NucleicFilters();
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
		nucleic_filter_string = getPreferences(preferencesName).get("nucleic_filter_string", nucleic_filter_string).trim(); //$NON-NLS-1$
	}

}

