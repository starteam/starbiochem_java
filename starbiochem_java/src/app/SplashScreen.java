package app;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class SplashScreen extends JPanel
{
    private static final long serialVersionUID = 1L;
    
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
		setLayout(new BorderLayout());
		add(BorderLayout.CENTER, getCenter());
	}
	
	private void end()
	{
		removeAll();
	}
	
	private JPanel getCenter()
	{
		JPanel welcome = new JPanel();
		welcome.setLayout(new FlowLayout());
		JLabel html = new JLabel(WELCOME);

		welcome.add(html);
		return welcome;
	}

	private String WELCOME = "<html><head><style type=" + "'text/css'" + "> body { margin-left: 10%; margin-right: 10%; margin-top: 10%; margin-bottom: 10%;}; h1 { margin-left: 0%;}; h2,h3,h4,h5,h6 { margin-left: -4%; }; h1,h2,h3,h4,h5,p,ul { font-family: sans-serif; } </style></head><table width="  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			 + "'100%'" + " border=" + "'0'" + "><tr><td>&nbsp;</td></tr><tr><td>&nbsp;</td></tr><tr><td>&nbsp;</td></tr><tr><th><h1 align=" + "'left'" + ">" + Messages.getString("SplashScreen.9") + "</h1></th><tr><td>&nbsp;</td></tr><tr><td><strong><i>"   //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
					+ Messages.getString("SplashScreen.11") + "</i></strong></td></tr><tr><td>&nbsp;</td></tr><tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + Messages.getString("SplashScreen.13") + "<strong><i>" + Messages.getString("SplashScreen.15") + "<i></strong> " + Messages.getString("SplashScreen.17") + "<i>" + Messages.getString("SplashScreen.19")   //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$
							+ "</i></td></tr><tr><td>&nbsp;</td></tr><tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + Messages.getString("SplashScreen.21") + "<strong><i>" + Messages.getString("SplashScreen.23") + "</i></strong>" + Messages.getString("SplashScreen.25") + "<i>" + Messages.getString("SplashScreen.27")  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
					+ "</i></td></tr><tr><td>&nbsp;</td></tr><tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + Messages.getString("SplashScreen.28") + "<strong><i>" + Messages.getString("SplashScreen.30") + "</strong></i> -> <strong><i>" + Messages.getString("SplashScreen.32") + "</strong></i> " + Messages.getString("SplashScreen.34") + "</td></tr><tr><td>&nbsp;</td></tr></table>"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$


}
