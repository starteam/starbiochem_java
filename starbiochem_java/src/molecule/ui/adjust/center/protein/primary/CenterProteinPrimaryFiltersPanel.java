package molecule.ui.adjust.center.protein.primary;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import star.annotations.SignalComponent;
import app.Messages;

@star.annotations.Preferences
@SignalComponent(extend = JPanel.class)
public class CenterProteinPrimaryFiltersPanel extends CenterProteinPrimaryFiltersPanel_generated
{
	private static final long serialVersionUID = 1L;

	public String centerproteinprimaryfilter_string = Messages.getString("CenterProteinPrimaryFiltersPanel.0"); //$NON-NLS-1$
	
	private CenterProteinPrimaryFilters filters = null;
	
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
		loadPreferences("adjust_primary"); //$NON-NLS-1$
		setLayout(new BorderLayout());
		setBorder(new TitledBorder(centerproteinprimaryfilter_string));

		filters = new CenterProteinPrimaryFilters();
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
	
	protected void loadPreferences(String preferencesName)
	{
		centerproteinprimaryfilter_string = getPreferences(preferencesName).get("centerproteinprimaryfilter_string", centerproteinprimaryfilter_string).trim(); //$NON-NLS-1$
	}

}

