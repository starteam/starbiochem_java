package samples;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.TreeSet;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import star.annotations.SignalComponent;
import utils.ActionJList;
import utils.FileUtils;
import utils.UIHelpers;
import app.StarBiochemMain;
import app.signal.SampleOpenRaiser;
import app.Messages;


@star.annotations.Preferences
@SignalComponent(extend = JDialog.class, raises = {SampleOpenRaiser.class})
public class SamplesDialog extends SamplesDialog_generated implements ActionListener
{
	// VM arguments: -Xmx999m -Xdock:name=StarBiochem -Dapple.laf.useScreenMenuBar=true
	// -Xmx999m -Xdock:name=StarBiochem -XX:+UseConcMarkSweepGC -verbose:gc
	private static final long serialVersionUID = 1L;
	
	private static final String SAMPLES_DELIMITER = ","; //$NON-NLS-1$
	private static final String EMPTY_STRING = ""; //$NON-NLS-1$

	private JPanel buttonPane = null;
	private JButton open = null;
	private JButton cancel = null;
	private String sampleId = null;
	private Object[] sampleIds = null;
	private String samplesJarLocation = SAMPLES_LOCATION;
	private JTabbedPane samplesTabbedPane = null;
	private JFrame frame = null;
	private Properties samplesUIproperties = null;
	
	public SamplesDialog(JFrame frame, String string, boolean b)
	{
		super(frame, string, b);
		this.frame = frame ;
	}

	public void addNotify()
	{
		super.addNotify();
		init();
	}

	public void removeNotify()
	{
		super.removeNotify();
	}

	private void init()
	{
		open = new JButton(OPEN);
		cancel = new JButton(CANCEL);
		open.addActionListener(this);
		cancel.addActionListener(this);
		buttonPane = new JPanel();
		buttonPane.add(BorderLayout.CENTER, open);
		buttonPane.add(BorderLayout.CENTER, cancel);
	    add(BorderLayout.SOUTH, buttonPane);

	    this.samplesTabbedPane = createSamplesUI();
	    if(null != samplesTabbedPane)
	    {
	    	add(BorderLayout.CENTER, samplesTabbedPane);
	    	pack();
	    }
		setLocationRelativeTo(frame);		
	}
	
	public Dimension getPreferredSize()
	{
		Dimension size = super.getPreferredSize();
		size.width = (int)(size.width * 1.1);
		size.height = (int)(size.height*1.5);
		return size;
	}

	public synchronized void actionPerformed(final ActionEvent evt)
	{
		Object source = evt.getSource();
		if(null != source)
		{
			if(source.equals(this.open))
			{
				this.sampleIds = getSelectedSamples(this.samplesTabbedPane);
				if((null != this.sampleIds) && (0 != this.sampleIds.length))
				{
					this.setVisible(false);
					openSamples();
				}
			}
			else if(source.equals(cancel))
			{
				this.sampleIds = null;
				this.setVisible(false);
			}
			else if(source instanceof ActionJList)
			{
				this.sampleIds = getSelectedSamples(this.samplesTabbedPane);
				if(null != this.sampleIds)
				{
					this.setVisible(false);
					openSamples();
				}
			}
		}
	}

	private JTabbedPane createSamplesUI()
	{
		this.samplesUIproperties = getSamplesUIProperties(SAMPLES_LOCATION);
		if(null != samplesUIproperties)
		{
			String samples_tabs = samplesUIproperties.getProperty(SAMPLES_TABS);
			return createSamplesTabbedPane(null, samplesUIproperties, samples_tabs);
		}
		return null;
	}

	private JTabbedPane createSamplesTabbedPane(JComponent parent, Properties properties, String samples_tabs)
	{
		if((null != properties) && (null != samples_tabs))
		{
			String tabsnames = samples_tabs;
			String[] tabsText = tabsnames.split(SAMPLES_DELIMITER);
			if(null != tabsText && tabsText.length != 0)
			{
				return createSamplesTabbedPane(null, properties, tabsText);
			}
			else
			{			
				System.err.println("Malformed tab list"); //$NON-NLS-1$
			}
		}
		return null;
	}
	
	private JTabbedPane createSamplesTabbedPane(JComponent parent, Properties properties, String[] tabsText)
	{
		if((null != properties) && (null != tabsText) && (0 != tabsText.length))
		{	
			JTabbedPane samplesTabbedPane = new JTabbedPane();
			samplesTabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
			for(int i=0; tabsText.length != i; i++)
			{
				String tabText = tabsText[i].trim();

				if((null != tabText) && !tabText.equals(EMPTY_STRING))
				{
					if(tabText.startsWith(Messages.getString("SamplesDialog.0"))) //$NON-NLS-1$
					{
						TreeSet<String> filenames = new TreeSet<String>( new Comparator<String>()
							{
								public int compare(String o1, String o2) 
								{
									return o1.toLowerCase().compareTo(o2.toLowerCase());
								}
							});
						filenames.addAll(properties.stringPropertyNames());
						ArrayList<String> myFilenames = new ArrayList<String>();
						Iterator<String> iterator = filenames.iterator();
						while(iterator.hasNext())
						{
							String file = iterator.next().trim();
							if(file.endsWith("jmol")) //$NON-NLS-1$
							{
								myFilenames.add(file);
							}
						}
						
						if(!myFilenames.isEmpty())
						{
							ActionJList addTabActionJList = new ActionJList(myFilenames.toArray(new String[0]));
							if(null != addTabActionJList)
							{
								JScrollPane scrollPane = new JScrollPane(addTabActionJList);
								samplesTabbedPane.addTab(tabText, scrollPane);
								addTabActionJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
								addTabActionJList.addActionListener(this);
							}
						}
					}
					else
					{
						String tab_list = samplesUIproperties.getProperty(tabText + "_list"); //$NON-NLS-1$
						if((null != tab_list) && !tab_list.equals(EMPTY_STRING))
						{
							ActionJList tabActionJList = createTabActionJList(properties, tab_list);
							if(null != tabActionJList)
							{
								if(0 != tabActionJList.getComponentCount())
								{
									JScrollPane scrollPane = new JScrollPane(tabActionJList);
									samplesTabbedPane.addTab(tabText, scrollPane);
									tabActionJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
									tabActionJList.addActionListener(this);
									
									if(tabText.startsWith(Messages.getString("SamplesDialog.1"))) //$NON-NLS-1$
									{
										int tabIndex = samplesTabbedPane.indexOfTab(tabText);
										samplesTabbedPane.setSelectedIndex(tabIndex);
									}
								}
							}
						}
						else
						{
							String tab_tabs = samplesUIproperties.getProperty(tabText + "_tabs"); //$NON-NLS-1$
							if((null != tab_tabs) && !tab_tabs.equals(EMPTY_STRING))
							{
								JTabbedPane subtabbedpane = createSamplesTabbedPane(samplesTabbedPane, properties, tab_tabs);
								if(null != samplesTabbedPane)
								{
									samplesTabbedPane.addTab(tabText, subtabbedpane);
									if(tabText.startsWith(Messages.getString("SamplesDialog.2"))) //$NON-NLS-1$
									{
										int tabIndex = samplesTabbedPane.indexOfTab(tabText);
										samplesTabbedPane.setSelectedIndex(tabIndex);
									}
								}
							}
						}
					}
				}
			}
			if(0 != samplesTabbedPane.getComponentCount())
			{
				return samplesTabbedPane;
			}
		}
		return null;
	}
	
	private ActionJList createTabActionJList(Properties properties, String tab_list)
	{
		if((null != properties) && (null != tab_list) && (!tab_list.equals(EMPTY_STRING)))
		{
			String[] pdbnames = tab_list.split(SAMPLES_DELIMITER);
			if((null != pdbnames) && (0 != pdbnames.length))
			{
				ArrayList<String> pdbStringArray = new ArrayList<String>();
				for(int i=0; pdbnames.length != i; i++)
				{
					String pdbname = pdbnames[i];
					if((null != pdbname) && !pdbname.equals(EMPTY_STRING))
					{
						String pdbNameProperty = properties.getProperty(pdbname.trim());
						pdbStringArray.add(pdbNameProperty);
					}
				}
				if(!pdbStringArray.isEmpty())
				{
					String[] pdbStrings = pdbStringArray.toArray(new String[0]);
					return new ActionJList(pdbStrings);
				}
			}
		}
		return null;
	}
	
	private Properties getSamplesUIProperties(String samplesLocation)
	{
		final int BUFFER = 512 * 1024;
		Locale locale = Locale.getDefault();
		try
		{
			File jarFile = loadJarFile(samplesLocation);
			if(null != jarFile)
			{
				this.samplesJarLocation = jarFile.getAbsolutePath();
				JarInputStream jis = new JarInputStream(new BufferedInputStream(new FileInputStream(jarFile), BUFFER));
				JarEntry en = null;
				while ((en = jis.getNextJarEntry()) != null)
				{
					if (en.getName().equals(Messages.getString("SamplesDialog.4", locale)))
					{
						Properties properties = new Properties();
						String line = null;
						BufferedReader br = new BufferedReader(new InputStreamReader(jis, "ISO-8859-1"));
						while(null != (line = br.readLine()))
						{
							String[] keyvalue = line.split("="); //$NON-NLS-1$
							if(2 == keyvalue.length)
							{
								String key = keyvalue[0].trim();
								String value = keyvalue[1].trim();
								properties.put(key, value);
							}
						}
						return properties;
					}
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}
	
	private synchronized File loadJarFile(String samples)
	{
		try
		{
			java.io.File file = java.io.File.createTempFile(SAMPLES, ".star"); //$NON-NLS-1$
			file.deleteOnExit();
			FileUtils.copy(this.getClass().getResourceAsStream(SAMPLES_LOCATION), new java.io.FileOutputStream(file));
			return file;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	private void openSamples()
	{
		if(null != sampleIds)
		{
			for(int i=0; sampleIds.length != i; i++)
			{
				String tempId = sampleIds[i].toString();
				if(null != samplesUIproperties.keys())
				{
					Enumeration<Object> keys = samplesUIproperties.keys();
					if(null != keys)
					{
						while(keys.hasMoreElements())
						{
							Object key = keys.nextElement();
							if(key.toString().trim().equals(tempId.trim()))
							{
								String sampleId = key.toString();
								openSample(sampleId);
								continue;
							}
							else
							{
								Object value = samplesUIproperties.get(key);
								if(value.toString().trim().equals(tempId.trim()))
								{
									String sampleId = key.toString();
									openSample(sampleId);
									continue;
								}
							}
						}
					}
				}
			}
		}
	}
	
	private Object[] getSelectedSamples(JTabbedPane tabbedPane)
	{
		if(null != tabbedPane)
		{
			int tabcount = tabbedPane.getTabCount();
			for(int i=0; tabcount != i; i++)
			{
				Component component = tabbedPane.getComponentAt(i);
				if((null != component) && ((component instanceof JScrollPane) || (component instanceof JTabbedPane)) && component.isShowing())
				{
					if(component instanceof JScrollPane)
					{
						JScrollPane sp = (JScrollPane)component;
						JViewport viewport = sp.getViewport();
						if(null != viewport)
						{
							Component view = viewport.getView();
							if((null != view) && (view instanceof ActionJList))
							{
								ActionJList actionJList = (ActionJList) view;
								return actionJList.getSelectedValues();
							}
						}
					}
					else if(component instanceof JTabbedPane)
					{
						Object[] selectedSamples = getSelectedSamples((JTabbedPane)component);
						if(null != selectedSamples)
						{
							return selectedSamples;
						}
					}
				}
			}
		}
		return null;
	}
		
	public String getSamplesJarLocation()
	{
		return this.samplesJarLocation;
	}
	
	public String getSampleId()
	{
		return this.sampleId;
	}
	
	public void openSample(Object id)
	{
		StarBiochemMain.setGlassPaneVisible(true);

		if(null != id)
		{
			this.sampleId = id.toString();
			this.raise_SampleOpenEvent();
			UIHelpers.track("From_Samples"); //$NON-NLS-1$
			invalidate();
			validate();
	
			SwingUtilities.invokeLater(new Runnable()
			{
				public void run()
				{
					StarBiochemMain.setGlassPaneVisible(false);
				}
			});
		}
	}
	
	private String OPEN = Messages.getString("SamplesDialog.12"); //$NON-NLS-1$
	private String CANCEL = Messages.getString("SamplesDialog.13"); //$NON-NLS-1$

	private String SAMPLES = Messages.getString("SamplesDialog.14"); //$NON-NLS-1$
	private static String SAMPLES_LOCATION = "/samples.jar"; //$NON-NLS-1$
	private static String SAMPLES_TABS = "samples_tabs"; //$NON-NLS-1$

}
