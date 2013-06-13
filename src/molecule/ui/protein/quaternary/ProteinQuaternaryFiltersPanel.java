package molecule.ui.protein.quaternary;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import star.annotations.SignalComponent;

@star.annotations.Preferences
@SignalComponent(extend = JPanel.class)
public class ProteinQuaternaryFiltersPanel extends ProteinQuaternaryFiltersPanel_generated
{
	private static final long serialVersionUID = 1L;

	public String quaternaryfilter_string = ""; //$NON-NLS-1$
	
	private ProteinQuaternaryFilters filters = null;
	
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
		setBorder(new TitledBorder(quaternaryfilter_string));

		filters = new ProteinQuaternaryFilters();
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
		quaternaryfilter_string = getPreferences(preferencesName).get("quaternaryfilter_string", quaternaryfilter_string).trim(); //$NON-NLS-1$
	}

}

