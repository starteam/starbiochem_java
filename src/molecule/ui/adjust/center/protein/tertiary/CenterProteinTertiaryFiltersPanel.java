package molecule.ui.adjust.center.protein.tertiary;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import star.annotations.SignalComponent;
import app.Messages;

@star.annotations.Preferences
@SignalComponent(extend = JPanel.class)
public class CenterProteinTertiaryFiltersPanel extends CenterProteinTertiaryFiltersPanel_generated
{
	private static final long serialVersionUID = 1L;

	public String tertiaryfilter_string = Messages.getString("CenterProteinTertiaryFiltersPanel.0"); //$NON-NLS-1$
	
	private CenterProteinTertiaryFilters filters = null;
	
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
		loadPreferences("adjust_tertiary"); //$NON-NLS-1$
		setLayout(new BorderLayout());
		setBorder(new TitledBorder(tertiaryfilter_string));

		filters = new CenterProteinTertiaryFilters();
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
		tertiaryfilter_string = getPreferences(preferencesName).get("tertiaryfilter_string", tertiaryfilter_string).trim(); //$NON-NLS-1$
	}

}

