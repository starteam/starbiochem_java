package app;

import java.awt.GraphicsConfiguration;

import javax.swing.JFrame;
import javax.swing.JPanel;

import utils.GlassPane;

public class UIFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JPanel glassPane = null;

	public UIFrame(String arg0, GraphicsConfiguration arg1)
	{
		super(arg0, arg1);
	}

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
		if(null == glassPane)
		{
			glassPane = new GlassPane();
		}
		this.setGlassPane(glassPane);
		glassPane.setVisible(false);
	}

	private void end()
	{
		removeAll();
		glassPane = null;
	}

	public void addGlassPane()
	{
		if(null != glassPane)
		{
			glassPane.setVisible(true);
			this.paintAll(getGraphics());
		}
	}

	public void removeGlassPane()
	{
		if(null != glassPane)
		{
			glassPane.setVisible(false);
			this.paintAll(getGraphics());
		}
	}

}
