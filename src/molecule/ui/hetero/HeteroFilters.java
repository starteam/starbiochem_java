package molecule.ui.hetero;

import java.awt.BorderLayout;

import molecule.ui.AbstractFiltersUI;
import molecule.ui.hetero.signal.HeteroFilterRaiser;
import molecule.ui.signal.RenderingInfoRaiser;
import star.annotations.SignalComponent;

@star.annotations.Preferences
@SignalComponent(extend = AbstractFiltersUI.class, raises = {HeteroFilterRaiser.class})
public class HeteroFilters extends HeteroFilters_generated
{
	private static final long serialVersionUID = 1L;

	HeteroRenderingModePanel modes = null;
	
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
		modes = new HeteroRenderingModePanel();
		setLayout(new BorderLayout());
		add(BorderLayout.EAST, modes);
	}
	
	private void end()
	{
		removeAll();
		modes = null;
	}

	public void initTree()
	{
		if(null != modes)
		{
			modes.initTree();
		}
	}

	public void reset()
	{
		if(null != modes)
		{
			modes.reset();
		}
	}

	private final int value = RenderingInfoRaiser.ALL_HETERO_VISIBLE;
	public int getValue()
	{
		return this.value;
	}
	
	public int getDefaultFilter()
	{
		return RenderingInfoRaiser.ALL_HETERO_VISIBLE;
	}

	public boolean isDefaultAutomaticallyRendered()
	{
		if(null != modes)
		{
			return modes.isDefaultAutomaticallyRendered();
		}
		return true;
	}
}
