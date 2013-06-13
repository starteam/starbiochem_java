package molecule.ui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import star.annotations.SignalComponent;

@SignalComponent(extend = JPanel.class, raises={})
public abstract class AbstractNonProteinMoleculeUI extends AbstractMoleculeUI_generated
{
	private static final long serialVersionUID = 1L;

	private boolean isInitialized = false;
	public void addNotify()
	{
		super.addNotify();
		if(!isInitialized)
		{
			initialize();
			isInitialized = true;
		}
	}

	public void removeNotify()
	{
		removeAll();
		cleanup();
		isInitialized = false;
		super.removeNotify();
	}

	public abstract String getTitle();
	
	protected abstract void initialize();
	protected abstract void cleanup();
	protected abstract void raiseRenderEvent();
	
	protected void initialize(JPanel filters, JPanel properties, AbstractMoleculeUIListPanel listPanel)
	{
		setLayout(new BorderLayout());
		if((null != filters) || (null != listPanel))
		{
			JPanel filtersRenderingPanel = new JPanel();
			filtersRenderingPanel.setLayout(new BorderLayout());
			String borderLayoutConstraint = BorderLayout.NORTH;
			if(null != filters)
			{
				filtersRenderingPanel.add(borderLayoutConstraint, filters);
				borderLayoutConstraint = BorderLayout.SOUTH;
			}
			if(null != properties)
			{
				filtersRenderingPanel.add(borderLayoutConstraint, properties);
			}
			add(BorderLayout.NORTH, filtersRenderingPanel);
		}
		if(null != listPanel)
		{
			add(BorderLayout.CENTER, listPanel);
		}
	}


}