package molecule.ui.water;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JPanel;

import molecule.ui.AbstractFiltersUI;
import molecule.ui.signal.RenderingInfoRaiser;
import molecule.ui.water.signal.WaterFilterRaiser;
import star.annotations.SignalComponent;

@star.annotations.Preferences
@SignalComponent(extend = AbstractFiltersUI.class, raises = {WaterFilterRaiser.class})
public class WaterFilters extends WaterFilters_generated
{
    private static final long serialVersionUID = 1L;

	WaterRenderingModePanel modes = null;
	
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
		loadPreferences("water"); //$NON-NLS-1$
		
		JPanel water_notes = new JPanel();
		water_notes.setLayout(new FlowLayout());
		
		
		modes = new WaterRenderingModePanel();
		
		setLayout(new BorderLayout());
		add(BorderLayout.CENTER, water_notes);
		add(BorderLayout.EAST, modes);
	}
	
	private void end()
	{
		removeAll();
		modes = null;
	}

	private boolean inInitTree = false;
	public void initTree()
	{
		if(!inInitTree)
		{
			inInitTree = true;
			if(null != modes)
			{
				modes.initTree();
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
			if(null != modes)
			{
				modes.reset();
			}
			inReset = false;
		}
	}

	private final int value = RenderingInfoRaiser.ALL_WATER_VISIBLE;
	public int getValue()
	{
		return this.value;
	}

	public int getDefaultFilter()
	{
		return (RenderingInfoRaiser.ALL_WATER_VISIBLE);
	}

	public boolean isDefaultAutomaticallyRendered()
	{
		if(null != modes)
		{
			return modes.isDefaultAutomaticallyRendered();
		}
		return true;
	}

	protected void loadPreferences(String preferencesName)
	{
	}

}
