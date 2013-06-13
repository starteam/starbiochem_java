package app;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import utils.GlassPane;

public class UIDialog extends JDialog
{
	private static final long serialVersionUID = 1L;
	private JPanel glassPane = null;

	public UIDialog(JFrame frame, String arg1, boolean arg2)
	{
		super(frame, arg1, arg2);
		setLocationRelativeTo(frame);
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
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
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
