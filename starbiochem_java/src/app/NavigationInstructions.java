package app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import app.Messages;


//public class NavigationInstructions extends UIFrame implements ActionListener
public class NavigationInstructions extends JDialog implements ActionListener
{
    private static final long serialVersionUID = 1L;
    private static final JFrame frame = app.StarBiochemMain.getFrame();
    
	JButton close = null;
	JLabel viewNavigationInstructions = null;
	JPanel pButton = null;
	
	public NavigationInstructions(String title, GraphicsConfiguration graphicsConfiguration)
	{
		super(frame, title, false, graphicsConfiguration);
	}
	
	private void closeViewStructureInstruction() 
	{
		setVisible(false);
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
		Dimension myDimension = new Dimension(Integer.parseInt(DIALOG_WIDTH), Integer.parseInt(DIALOG_HEIGHT));
		close = new JButton(CLOSE_BUTTON);
		viewNavigationInstructions = new JLabel(HELP_NAVIGATION_INSTRUCTIONS, (int) CENTER_ALIGNMENT);
		pButton = new JPanel();
		close.addActionListener(this);
		
		add(BorderLayout.CENTER, viewNavigationInstructions);
		pButton.add(BorderLayout.CENTER, close);
		add(BorderLayout.SOUTH, pButton);
		setSize(myDimension);
		setLocationRelativeTo(frame);
	}

	private void end()
	{
		removeAll();
		close.removeActionListener(this);	
		close = null;
		viewNavigationInstructions = null;
		pButton = null;
	}
	
	public void actionPerformed(ActionEvent evt) 
	{
		if (evt.getSource() == close)
		{
			closeViewStructureInstruction();
		}
	}
		
	private static String DIALOG_WIDTH = "500"; //$NON-NLS-1$
	private static String DIALOG_HEIGHT = "300"; //$NON-NLS-1$
	private String CLOSE_BUTTON = Messages.getString("NavigationInstructions.3"); //$NON-NLS-1$
	
	private String HELP_NAVIGATION_INSTRUCTIONS = "<html><head><style type=" + "'text/css'" + "> body {margin-left: 10%; margin-right: 10%; margin-top: 10%; margin-bottom: 10%; }h1 { margin-left: -8%;}h2,h3,h4,h5,h6 { margin-left: -4%; }h1,h2,h3,h4,h5,p,ul { font-family: sans-serif; }</style></head><body><p>" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	+Messages.getString("NavigationInstructions.4") + "</p><br><table border="+ "'1'" + "<tr><th>" + Messages.getString("NavigationInstructions.6") + "</th><td>" + Messages.getString("NavigationInstructions.9") + "</td></tr><tr><th>" + Messages.getString("NavigationInstructions.11") + "</th><td>" + Messages.getString("NavigationInstructions.13") + "</td></tr><tr><th>" + Messages.getString("NavigationInstructions.15") + "</th><td>" + Messages.getString("NavigationInstructions.17") + Messages.getString("NavigationInstructions.2")  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$ //$NON-NLS-12$ //$NON-NLS-13$ //$NON-NLS-14$ //$NON-NLS-15$ //$NON-NLS-16$
			+ Messages.getString("NavigationInstructions.19") + "</th><td><i>" + Messages.getString("NavigationInstructions.21") + "</i> " + Messages.getString("NavigationInstructions.0") + "<hr>*<i>Mac:</i> " + Messages.getString("NavigationInstructions.25") + "</td></tr><tr><th>" + Messages.getString("NavigationInstructions.27") + "</th><td>" + Messages.getString("NavigationInstructions.29") + ";</td></tr></table><p><font size=2>*<i>"  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$ //$NON-NLS-12$
	+ Messages.getString("NavigationInstructions.1") + "</font></p></body></html>"; //$NON-NLS-1$ //$NON-NLS-2$
	
}