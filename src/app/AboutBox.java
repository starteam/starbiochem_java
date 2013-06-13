package app;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jmol.api.JmolViewer;

import app.Messages;

public class AboutBox extends JPanel
{

	private static final long serialVersionUID = 1L;

	String getBuild()
	{
		return Version.getBuildDate().toString();
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
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(new JLabel(ABOUT_TITLE));
		add(new JLabel(ABOUT_WEBSITE));
		add(new JLabel(ABOUT_BUILD + " " + getBuild())); //$NON-NLS-1$
		add(new JLabel(ABOUT_BUG_REPORTING));
		add(new JLabel(ABOUT_JAVA_VERSION + " " + System.getProperty("java.version") + " " + System.getProperty("java.vendor"))); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		add(new JLabel(ABOUT_OS_VERSION + " " + System.getProperty("os.name") + " " + System.getProperty("os.arch") + " " + System.getProperty("os.version"))); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
		add(new JLabel("")); //$NON-NLS-1$
		add(new JLabel(Messages.getString("AboutBox.12"))); //$NON-NLS-1$
		add(new JLabel(Messages.getString("AboutBox.13"))); //$NON-NLS-1$
		add(new JLabel(Messages.getString("AboutBox.14") + JmolViewer.getJmolVersion())); //$NON-NLS-1$
		add(new JLabel());
		add(new JLabel(Messages.getString("AboutBox.15"))); //$NON-NLS-1$
		add(new JLabel(Messages.getString("AboutBox.16"))); //$NON-NLS-1$
		add(new JLabel(Messages.getString("AboutBox.17"))); //$NON-NLS-1$
		add(new JLabel(Messages.getString("AboutBox.18"))); //$NON-NLS-1$
		add(new JLabel(Messages.getString("AboutBox.19"))); //$NON-NLS-1$
	}

	private void end()
	{
		removeAll();
	}
	
	private String ABOUT_TITLE = "<html><body><font size='+1'>StarBiochem<font></html></body>"; //$NON-NLS-1$
	private String ABOUT_WEBSITE = "<html><body>" + Messages.getString("AboutBox.24") + " <a href='http://web.mit.edu/star/biochem/'>http://web.mit.edu/star/biochem/</a></body></html>"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	private String ABOUT_BUILD = Messages.getString("AboutBox.26"); //$NON-NLS-1$
	private String ABOUT_BUG_REPORTING = "<html><body>" + Messages.getString("AboutBox.28") + " <a href='mailto:star@mit.edu'>star@mit.edu</a></body></html>"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	private String ABOUT_JAVA_VERSION = Messages.getString("AboutBox.30"); //$NON-NLS-1$
	private String ABOUT_OS_VERSION = Messages.getString("AboutBox.31"); //$NON-NLS-1$

}
