package molecule.ui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import star.annotations.SignalComponent;

@SignalComponent(extend = JPanel.class, raises={})
public abstract class AbstractMoleculeUI extends AbstractMoleculeUI_generated
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
	
	protected void initialize(JPanel filters, AbstractMoleculeUIListPanel listPanel, JPanel renderingPanel)
	{
		setLayout(new BorderLayout());
		if((null != filters) && (null != renderingPanel))
		{
			JPanel myPanel = new JPanel();
			myPanel.setLayout(new BorderLayout());
			myPanel.add(BorderLayout.NORTH, filters);
			myPanel.add(BorderLayout.SOUTH, renderingPanel);
			add(BorderLayout.NORTH, myPanel);
		}
		else if(null != filters)
		{
			add(BorderLayout.NORTH, filters);
		}
		else if(null != renderingPanel)
		{
			add(BorderLayout.NORTH, renderingPanel);
		}
		if(null != listPanel)
		{
			add(listPanel);
		}
		getLayout().minimumLayoutSize(this);
	}

}