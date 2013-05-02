package app;

import imports.ImportsDialog;
import imports.ImportsSource;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;

import molecule.interfaces.RenderingInfo;
import molecule.ui.MoleculeContainer;
import molecule.ui.MoleculesContainer;
import molecule.ui.signal.MoleculeJmolRenderingPropertiesRaiser;
import molecule.ui.signal.RenderingInfoRaiser;
import samples.SamplesDialog;
import star.annotations.SignalComponent;
import star.ui.feedback.FeedbackDialog;
import star.version.VersionChecker;
import utils.UIHelpers;
import app.signal.CloseAllMoleculesRaiser;
import app.signal.CloseMoleculeRaiser;
import app.signal.FileOpenRaiser;
import app.signal.FileSaveImageRaiser;
import app.signal.ImportOpenRaiser;
import app.signal.MoleculesBackgroundColorRaiser;
import app.signal.ResetAllMoleculesRaiser;
import app.signal.ResetMoleculeRaiser;
import app.signal.ResetSceneRaiser;
import app.signal.TrackGlassPaneRaiser;
import app.signal.ViewBackgroundColorRaiser;
import app.signal.ViewForegroundColorRaiser;
import app.signal.ViewHydrogensRaiser;
import app.signal.ViewRegionAndCenterRaiser;
import app.signal.ViewStructureOptionsRaiser;
import app.Messages;

@star.annotations.Preferences
@SignalComponent(extend = JFrame.class, raises = {ViewStructureOptionsRaiser.class, MoleculesBackgroundColorRaiser.class, RenderingInfoRaiser.class,
		FileOpenRaiser.class, ImportOpenRaiser.class, CloseMoleculeRaiser.class, CloseAllMoleculesRaiser.class, FileSaveImageRaiser.class, ResetSceneRaiser.class,
        ResetMoleculeRaiser.class, ResetAllMoleculesRaiser.class, MoleculeJmolRenderingPropertiesRaiser.class, TrackGlassPaneRaiser.class, ViewRegionAndCenterRaiser.class,
        ViewBackgroundColorRaiser.class, ViewForegroundColorRaiser.class, ViewHydrogensRaiser.class})
public class StarBiochemMain extends StarBiochemMain_generated implements ActionListener
{
	// VM arguments: -Xmx999m -Xdock:name=StarBiochem -Dapple.laf.useScreenMenuBar=true
	// -Xmx999m -Xdock:name=StarBiochem -XX:+UseConcMarkSweepGC -verbose:gc
	private static final long serialVersionUID = 1L;

	private static JFrame myFrame = null;
	public static String[] arguments;

	public static final JFrame getFrame()
	{
		return myFrame;
	}

	private static StarBiochemMain me = null;
	
	private String[] args;
	
	public StarBiochemMain(String[] args)
	{
		super();
		this.args = args;
		me = this;

		try
		{
			UIManager.setLookAndFeel(javax.swing.plaf.metal.MetalLookAndFeel.class.getName());
			System.setProperty("apple.awt.draggableWindowBackground", Boolean.TRUE.toString()); //$NON-NLS-1$
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (InstantiationException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		}
		catch (OutOfMemoryError e)
		{
			JOptionPane.showMessageDialog(this, e + "\n" + Messages.getString("StarBiochemMain.0")); //$NON-NLS-1$ //$NON-NLS-2$
			System.exit(1);
		}
		setVisible(true);
		
		pack();
	}
	
	private void executeCommandLineArgs(String[] args)
	{
		final String[] myargs = args;
		SwingUtilities.invokeLater(
			new Runnable()
			{
				public void run()
				{
					if(!(load(myargs) || create(myargs))) 
					{
						showUsage();
					}
				}
			});
	}

	private void showUsage()
	{
		System.out.println("Usage: StarBiochemMain <option>" + //$NON-NLS-1$
				"\n" + "where option can be:" + //$NON-NLS-1$ //$NON-NLS-2$
				"\n\t" + LOAD_OPTION + "<source_pathname>" + //$NON-NLS-1$ //$NON-NLS-2$
				"\n\t" + CREATE_OPTION + " <source_pathname> <target_pathname>" //$NON-NLS-1$ //$NON-NLS-2$
				);
		System.exit(1);
	}
	
	private void initMoleculesContainer()
	{
		if (null == moleculesContainer)
		{
			if (null != splashScreen)
			{
				remove(splashScreen);
			}
			moleculesContainer = new MoleculesContainer();
			add(moleculesContainer);
		}
	}

	final static String LOAD_OPTION = "-load"; //$NON-NLS-1$
	private boolean load(String[] args)
	{
		if((null != args) && (2 == args.length))
		{
			String load_option = args[0].toLowerCase().trim();
			if(LOAD_OPTION.equals(load_option))
			{
				String source_pathname = args[1].trim();
				File source = new File(source_pathname);
				if(null != source)
				{
					if(source.isFile())
					{
						return loadFile(source.getAbsolutePath(), "From_commandline_load_file"); //$NON-NLS-1$
					}
					else if(source.isDirectory())
					{
						String[] filenames = source.list();
						if(null != filenames)
						{
							boolean isGood = false;
							for(String source_filename: filenames)
							{
								File f = new File(source, source_filename);
								if(f.isFile())
								{
									isGood |= loadFile(f.getAbsolutePath(), "From_commandline_load_file"); //$NON-NLS-1$
								}
							}
							return isGood;
						}
					}
				}
			}
		}
		return false;
	}
	
	private boolean loadFile(String source_filename, String trackstr)
	{
		if(null != source_filename)
		{
			initMoleculesContainer();
			this.filename = source_filename;
			this.raise_FileOpenEvent();
			if(null != trackstr)
			{
				UIHelpers.track(trackstr);
			}
			return true;
		}
		return false;
	}

	final static String CREATE_OPTION = "-create"; //$NON-NLS-1$
	private boolean create(String[] args)
	{
		if((null != args) && ((2 == args.length) || (3 == args.length)))
		{
			String create_option = args[0].toLowerCase().trim();
			if(CREATE_OPTION.equals(create_option))
			{
				String target_pathname = ((2 == args.length) ? args[1].trim() : args[2].trim());
				File target = new File(target_pathname);
				if((null != target) && target.isFile())
				{
					target_pathname = target.getParent();
					target = new File(target_pathname);
				}
				if(!target.exists())
				{
					target.mkdir();
				}
				if((null != target) && target.isDirectory())
				{
					boolean isGood = false;
					String source_pathname = args[1].trim();
					File source = new File(source_pathname);
					if(null != source)
					{
						if(source.isFile())
						{
							return createJmolFile(source.getAbsolutePath(), target, "From_commandline_create_file"); //$NON-NLS-1$
						}
						else if(source.isDirectory())
						{
							String[] filenames = source.list();
							if(null != filenames)
							{
								for(String source_filename: filenames)
								{
									File f = new File(source, source_filename);
									if(f.isFile())
									{
										isGood |= createJmolFile(f.getAbsolutePath(), target, "From_commandline_create_file"); //$NON-NLS-1$
									}
								}
								return isGood;
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	private boolean createJmolFile(String source_filename, File target, String trackstr)
	{
		if((null != source_filename)  && (null != target))
		{
			isCreatingJmolZip = true;
			this.isCreatingJmolZip = true;
			this.target = target;
			loadFile(source_filename, trackstr);
			isCreatingJmolZip = false;
			return true;
		}
		return false;
	}
		
	SplashScreen splashScreen = null;

	private boolean isInitialized = false;
	public void addNotify()
	{
		super.addNotify();
		getAdapter().addComponent(new ErrorDialogHandler());
		if(!isInitialized)
		{
			init();
			isInitialized = true;
			if((null != args) && (1 < args.length))
			{
				executeCommandLineArgs(args);
			}
		}
	}

	public void removeNotify()
	{
		end();
		isInitialized = false;
		super.removeNotify();
	}

	ImageIcon currentIcon;
	private void init()
	{
		myFrame = this;
		setTitle(TITLE);
		addActions();
		setPreferredSize(new Dimension(Integer.parseInt(DEFAULT_WIDTH), Integer.parseInt(DEFAULT_HEIGHT)));
		if (null == splashScreen)
		{
			splashScreen = new SplashScreen();
			add(splashScreen);
		}

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent arg0)
			{
				quit();
			}
		});
	}

	private void end()
	{
		removeAll();
	}
	
	private void addActions()
	{
		JMenuBar menuBar = new JMenuBar();
		if (null != menuBar)
		{
			JMenu file = getFileMenu();
			if (null != file)
			{
				menuBar.add(file);
			}

			JMenu samples = getSamplesMenu();
			if (null != samples)
			{
				menuBar.add(samples);
			}
			
			boolean islocalOnly = (null != args) && (1 == args.length) && args[0].toLowerCase().equals("-onlylocal"); //$NON-NLS-1$
			if(!islocalOnly)
			{
				JMenu imports = getImportsMenu();
				if (null != imports)
				{
					menuBar.add(imports);
				}
			}
			
			JMenu reset = getResetMenu();
			if (null != reset)
			{
				menuBar.add(reset);
			}	

			JMenu view = getViewMenu();
			if (null != view)
			{
				menuBar.add(view);
			}

			JMenu help = getHelpMenu();
			if (null != help)
			{
				menuBar.add(help);
			}
			
			JMenu language = new LanguageSelector();
			if (null != language)
			{
				menuBar.add(language);
			}
			this.setJMenuBar(menuBar);
		}
	}

	private JMenu getFileMenu()
	{
		JMenu file = new JMenu(FILE);
		JMenuItem fileOpen = new JMenuItem(FILE_OPEN);
		fileOpen.addActionListener(this);
		file.add(fileOpen);
		JMenuItem fileClose = new JMenuItem(FILE_CLOSE);
		fileClose.addActionListener(this);
		file.add(fileClose);
		JMenuItem fileCloseAll = new JMenuItem(FILE_CLOSE_ALL);
		fileCloseAll.addActionListener(this);
		file.add(fileCloseAll);
		JMenu fileSaveAs = new JMenu(FILE_SAVE_AS);
		file.add(fileSaveAs);
		JMenuItem fileSaveAs_Image = new JMenuItem(new String(FILE_SAVE_AS_IMAGE));
		fileSaveAs_Image.addActionListener(this);
		fileSaveAs.add(fileSaveAs_Image);
		JMenuItem quit = new JMenuItem(QUIT);
		quit.addActionListener(this);
		file.add(quit);
		return file;
	}

	SamplesDialog samplesDialog = null;
	private JMenu getSamplesMenu()
	{
		JMenu samplesMenu = new JMenu(SAMPLES);
		JMenuItem sampleList = new JMenuItem(SAMPLES_SELECT);
		sampleList.addActionListener(this);
		samplesMenu.add(sampleList);		
		return samplesMenu;	
	}
	
	private void samples_open()
	{
		try
		{
			if (null == samplesDialog)
			{
				samplesDialog = new SamplesDialog(myFrame, SAMPLES, true);
			}
			samplesDialog.setVisible(true);
		}
		catch (OutOfMemoryError e)
		{
			JOptionPane.showMessageDialog(this, e + "\n" + Messages.getString("StarBiochemMain.3")); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}
	
	private JMenu getImportsMenu()
	{
		JMenu imports = new JMenu(IMPORT);
		JMenuItem importPDB = new JMenuItem(IMPORT_RCSB);
		importPDB.addActionListener(this);
		imports.add(importPDB);
		return imports;
	}
	
	private JMenu getResetMenu()
	{
		JMenu reset = new JMenu(RESET);
		JMenuItem resetCamera = new JMenuItem(RESET_CAMERA);
		resetCamera.addActionListener(this);
		reset.add(resetCamera);
		JMenuItem resetMolecule = new JMenuItem(RESET_STRUCTURE);
		resetMolecule.addActionListener(this);
		reset.add(resetMolecule);
		JMenuItem resetAllMolecules = new JMenuItem(RESET_ALL_STRUCTURES);
		resetAllMolecules.addActionListener(this);
		reset.add(resetAllMolecules);
		
		return reset;
	}	

	private JMenu getViewMenu()
	{
		JMenu view = new JMenu(VIEW);
		
		JMenuItem showHydrogen = new JMenuItem(VIEWER_TOGGLE_HYDROGENS);
		showHydrogen.addActionListener(this);
		view.add(showHydrogen);
		
		JMenuItem viewStructureOptions = new JMenuItem(VIEW_STRUCTURE_OPTIONS);
		viewStructureOptions.addActionListener(this);
		view.add(viewStructureOptions);
		
		JMenuItem backgroundColor = new JMenuItem(VIEWER_BACKGROUND_COLOR);
		backgroundColor.addActionListener(this);
		view.add(backgroundColor);
		
		JMenuItem foregroundColor = new JMenuItem(VIEWER_FOREGROUND_COLOR);
		foregroundColor.addActionListener(this);
		view.add(foregroundColor);
		
		return view;
	}

	private JMenu getHelpMenu()
	{
		JMenu help = new JMenu(HELP);
		JMenuItem about = new JMenuItem(HELP_ABOUT);
		about.addActionListener(this);
		help.add(about);
		JMenuItem help_navigation = new JMenuItem(HELP_NAVIGATION);
		help_navigation.addActionListener(this);
		help.add(help_navigation);
		return help;
	}

	private static Cursor oldCursor = null;
	
	static boolean glassPaneShowing = false;
	public boolean isGlassPaneShowing()
	{
		return glassPaneShowing;
	}
	
	static Thread myThread;
	static GlassPane glassPane = new GlassPane();

	static class GlassPane implements Runnable
	{
		boolean visible = false;
		boolean painted = true;
		boolean inRun = false;

		void showGlassPane()
		{
			Graphics g = getFrame().getGraphics();
			g.setColor(new java.awt.Color(.5f, .5f, .5f, .5f));
			g.fillRect(0, 0, getFrame().getWidth(), getFrame().getHeight());

			Font oldfont = getFrame().getFont();
			Font font = oldfont.deriveFont(Font.BOLD, 20);
			FontMetrics fm = getFrame().getFontMetrics(font);
			Rectangle rect = fm.getStringBounds(PLEASE_WAIT, g).getBounds();
			int x = (int) ((getFrame().getWidth() + rect.x) / 2);
			int y = (int) ((getFrame().getHeight() + rect.y) / 2);

			g.setColor(Color.white);
			g.fillRect(x, y, rect.width, rect.height);
			g.setColor(Color.black);
			g.setFont(font);
			g.drawString(PLEASE_WAIT, x, y + rect.height - fm.getMaxDescent());
			g.setFont(oldfont);

			g.dispose();
			painted = false;
		}

		public void run()
		{
			if (!inRun)
			{
				inRun = true;
				try
				{
					Thread.sleep(800);
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
				if (visible && painted)
				{
					showGlassPane();
				}
				inRun = false;
			}
		}
	}

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		if (glassPane != null)
		{
			glassPane.painted = true;
			if (glassPane.visible)
			{
				glassPane.showGlassPane();
			}
		}
	}

	public synchronized static void setGlassPaneVisible(boolean b)
	{
		if (glassPaneShowing == b)
		{
			return;
		}
		
		if (b)
		{
			oldCursor = getFrame().getCursor();
			getFrame().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			getFrame().setEnabled(false);
			glassPane.visible = true;
			getFrame().invalidate();
			myThread = new Thread(glassPane);
			myThread.start();
		}
		else if (null != oldCursor)
		{
			EventQueue eq = Toolkit.getDefaultToolkit().getSystemEventQueue();
            while( eq.peekEvent() != null && (eq.peekEvent() instanceof KeyEvent || eq.peekEvent() instanceof MouseEvent ))
            {
                try
                {
                	eq.getNextEvent() ;
                }
                catch( Exception e)
                {
                }
            }
			glassPane.visible = false;
			getFrame().setCursor(oldCursor);
			getFrame().setEnabled(true);
			getFrame().invalidate();
			getFrame().repaint();
		}
		glassPaneShowing = b;
		me.raise_TrackGlassPaneEvent();
	}

	MoleculesContainer moleculesContainer = null;

	public synchronized void actionPerformed(final ActionEvent evt)
	{
		final String command = evt.getActionCommand();
		if (evt.getActionCommand().compareToIgnoreCase(EXIT) == 0)
		{
			quit();
		}
		final StarBiochemMain self = this;
		if (null == moleculesContainer)
		{
			if (null != splashScreen)
			{
				remove(splashScreen);
			}
			moleculesContainer = new MoleculesContainer();
			add(moleculesContainer);
		}

		if (command.compareToIgnoreCase(FILE_OPEN) == 0)
		{
			file_open();
		}
		if (command.compareToIgnoreCase(FILE_CLOSE) == 0)
		{
			file_close();
		}
		if (command.compareToIgnoreCase(FILE_CLOSE_ALL) == 0)
		{
			file_close_all();
		}
		if (command.compareToIgnoreCase(FILE_SAVE_AS_IMAGE) == 0)
		{
			file_save_as_image();
		}
		else if (command.compareToIgnoreCase(QUIT) == 0)
		{
			quit();
		}
		else if(command.compareToIgnoreCase(SAMPLES_SELECT) == 0)
		{
			samples_open();
		}	
		else if (command.compareToIgnoreCase(IMPORT_RCSB) == 0)
		{
			imports();
		}
		else if (command.compareToIgnoreCase(HELP_NAVIGATION) == 0)
		{
			help_navigation_instructions();
		}
		else if (command.compareToIgnoreCase(RESET_CAMERA) == 0)
		{
			viewResetCamera();
		}
		else if (command.compareToIgnoreCase(RESET_STRUCTURE) == 0)
		{
			viewResetStructure();
		}
		else if (command.compareToIgnoreCase(RESET_ALL_STRUCTURES) == 0)
		{
			viewResetAllStructures();
		}
		else if (evt.getActionCommand().compareToIgnoreCase(VIEW_STRUCTURE_OPTIONS) == 0)
		{
			viewStructureOptions();
		}
		else if (evt.getActionCommand().compareToIgnoreCase(VIEWER_BACKGROUND_COLOR) == 0)
		{
			viewBackgroundColorOptions();
		}
		else if (evt.getActionCommand().compareToIgnoreCase(VIEWER_FOREGROUND_COLOR)==0)
		{
			viewForegroundColorOptions();
		}
		else if (evt.getActionCommand().compareToIgnoreCase(VIEWER_TOGGLE_HYDROGENS) == 0)
		{
			viewToggleHygrogens();
		}
		else if (evt.getActionCommand().compareToIgnoreCase(HELP_ABOUT) == 0)
		{
			JOptionPane.showMessageDialog(self, new AboutBox(), MessageFormat.format(Messages.getString("AboutBox.1"), JOptionPane.DEFAULT_OPTION), JOptionPane.PLAIN_MESSAGE); // (self, new AboutBox());
		}
		if (!hasMoleculeContainerComponents(this.moleculesContainer))
		{
			remove(this.moleculesContainer);
			this.moleculesContainer = null;
			if (null != splashScreen)
			{
				add(splashScreen);
			}
			invalidate();
			validate();
		}	
	}

	private boolean hasMoleculeContainerComponents(MoleculesContainer moleculesContainer)
	{
		if (null != moleculesContainer)
		{
			Component[] components = moleculesContainer.getComponents();
			if (null != components)
			{
				for (int i = 0; components.length != i; i++)
				{
					if (components[i] instanceof MoleculeContainer)
					{
						return true;
					}
				}
			}
		}
		return false;
	}

	private void quit()
	{
		QuitDialog d = new QuitDialog(myFrame, Messages.getString("StarBiochemMain.16")); //$NON-NLS-1$
		int result = d.get();
		if( result == JOptionPane.YES_OPTION )
		{
			myFrame.dispose();
			System.exit(0);
		}
	}

	private String filename = null;

	public String getFileName()
	{
		return filename;
	}

	private void file_open()
	{
		String wd = System.getProperty("user.home"); //$NON-NLS-1$
		JFileChooser fc = new JFileChooser(wd);
		FileFilter ff = new FileFilter()
		{
			@Override
			public boolean accept(File f)
			{
				if (f.isDirectory())
				{
					return true;
				}
				String name = f.getName();
				return (name.toLowerCase().endsWith(".pdb") || name.toLowerCase().endsWith(".jmol")); //$NON-NLS-1$ //$NON-NLS-2$
			}
			@Override
			public String getDescription()
			{
				return "PDB/Jmol files"; //$NON-NLS-1$
			}
		};
		
		fc.setFileFilter(ff);
		fc.setAcceptAllFileFilterUsed(false);
		fc.addChoosableFileFilter(ff);
		fc.setFileHidingEnabled(true);
		fc.setMultiSelectionEnabled(true);
		
		int rc = fc.showDialog(this, Messages.getString("StarOptionPane.4"));
		if (rc == JFileChooser.APPROVE_OPTION)
		{
			File[] files = fc.getSelectedFiles();
			if (null != files)
			{
				try
				{
					StarBiochemMain.setGlassPaneVisible(true);

					for (int i = 0; files.length != i; i++)
					{
						this.filename = files[i].getCanonicalPath();
						{
							this.raise_FileOpenEvent();
							UIHelpers.track( "From_File"); //$NON-NLS-1$
						}
					}
					this.raise_MoleculesBackgroundColorEvent();

					invalidate();
					validate();

					SwingUtilities.invokeLater(
							new Runnable()
							{
								public void run()
								{
									StarBiochemMain.setGlassPaneVisible(false);
								}
							});
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	private void file_close()
	{
		StarBiochemMain.setGlassPaneVisible(true);
		raise_CloseMoleculeEvent();
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				StarBiochemMain.setGlassPaneVisible(false);
			}
		});
	}

	private void file_close_all()
	{
		raise_CloseAllMoleculesEvent();
	}

	private void file_save_as_image()
	{
		this.raise_FileSaveImageEvent();
	}

	private String importURL = null;

	public String getImportURL()
	{
		return this.importURL;
	}

	private String importURI = null;

	public String getImportURI()
	{
		return this.importURI;
	}

	private void imports()
	{
		try
		{
			ImportsDialog importsDialog = new ImportsDialog(this, true);
			String[] ids = importsDialog.getIds();
			if ((null != ids) && (0 != ids.length))
			{
					StarBiochemMain.setGlassPaneVisible(true);
	
					for (int i = 0; ids.length != i; i++)
					{
						this.importURL = ImportsSource.getImportURL(ids[i]);
						this.raise_ImportOpenEvent();
						UIHelpers.track( "From_Import" ); //$NON-NLS-1$
					}
					this.raise_MoleculesBackgroundColorEvent();
	
					SwingUtilities.invokeLater(new Runnable()
					{
						public void run()
						{
							StarBiochemMain.setGlassPaneVisible(false);
						}
					});
	
					invalidate();
					validate();
				}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	NavigationInstructions navigationInstructions = null;
	private void help_navigation_instructions()
	{	
		if (null == navigationInstructions)
		{
			navigationInstructions = new NavigationInstructions(HELP_NAVIGATION_TITLE, this.getGraphicsConfiguration());
		}	
		navigationInstructions.setVisible(true);
	}

	private void viewResetCamera()
	{
		StarBiochemMain.setGlassPaneVisible(true);

		this.raise_ResetSceneEvent();

		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				StarBiochemMain.setGlassPaneVisible(false);
			}
		});
	}

	private void viewResetStructure()
	{
		StarBiochemMain.setGlassPaneVisible(true);

		this.raise_ResetMoleculeEvent();
		this.raise_MoleculesBackgroundColorEvent();

		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				StarBiochemMain.setGlassPaneVisible(false);
			}
		});
	}

	private void viewResetAllStructures()
	{
		StarBiochemMain.setGlassPaneVisible(true);

		this.raise_ResetAllMoleculesEvent();
		this.raise_MoleculesBackgroundColorEvent();

		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				StarBiochemMain.setGlassPaneVisible(false);
			}
		});
	}

	Color moleculesBackgroundColor = null;

	public Color getMoleculesBackgroundColor()
	{
		return this.moleculesBackgroundColor;
	}

	RenderingInfo ri = null;

	public RenderingInfo getRenderingInfo()
	{
		return this.ri;
	}

	private void viewStructureOptions()
    {
		raise_ViewStructureOptionsEvent();
    }
	
	private void viewBackgroundColorOptions()
    {
		raise_ViewBackgroundColorEvent();
    }
	
	private void viewForegroundColorOptions()
    {
		raise_ViewForegroundColorEvent();
    }
	
	private void viewToggleHygrogens()
	{
		raise_ViewHydrogensEvent();
	}
	
	private static String DEFAULT_WIDTH = "1000"; //$NON-NLS-1$
	private static String DEFAULT_HEIGHT = "700"; //$NON-NLS-1$

	private String TITLE = Messages.getString("StarBiochemMain.4"); //$NON-NLS-1$

	private String EXIT = Messages.getString("StarBiochemMain.27"); //$NON-NLS-1$

	private String FILE = Messages.getString("StarBiochemMain.28"); //$NON-NLS-1$
	private String FILE_OPEN = Messages.getString("StarBiochemMain.29"); //$NON-NLS-1$
	private String FILE_CLOSE = Messages.getString("StarBiochemMain.30"); //$NON-NLS-1$
	private String FILE_CLOSE_ALL = Messages.getString("StarBiochemMain.31"); //$NON-NLS-1$
	private String FILE_SAVE_AS = Messages.getString("StarBiochemMain.32"); //$NON-NLS-1$
	private String FILE_SAVE_AS_IMAGE = Messages.getString("StarBiochemMain.33"); //$NON-NLS-1$
	private String QUIT = Messages.getString("StarBiochemMain.34"); //$NON-NLS-1$
	
	private static String PLEASE_WAIT = "Please wait ...\n" + "  Rete tann souple..."; //$NON-NLS-1$

	private String SAMPLES = Messages.getString("StarBiochemMain.36"); //$NON-NLS-1$
	private String SAMPLES_SELECT = Messages.getString("StarBiochemMain.37"); //$NON-NLS-1$

	private String IMPORT = Messages.getString("StarBiochemMain.38"); //$NON-NLS-1$
	private String IMPORT_RCSB = Messages.getString("StarBiochemMain.39"); //$NON-NLS-1$

	private String RESET = Messages.getString("StarBiochemMain.40"); //$NON-NLS-1$
	private String RESET_CAMERA = Messages.getString("StarBiochemMain.41"); //$NON-NLS-1$
	private String RESET_STRUCTURE = Messages.getString("StarBiochemMain.42"); //$NON-NLS-1$
	private String RESET_ALL_STRUCTURES = Messages.getString("StarBiochemMain.43"); //$NON-NLS-1$

	private String VIEW = Messages.getString("StarBiochemMain.44"); //$NON-NLS-1$
	private String VIEWER_BACKGROUND_COLOR = Messages.getString("StarBiochemMain.45"); //$NON-NLS-1$
	private String VIEWER_FOREGROUND_COLOR = Messages.getString("StarBiochemMain.46"); //$NON-NLS-1$
	private String VIEWER_TOGGLE_HYDROGENS = Messages.getString("StarBiochemMain.47"); //$NON-NLS-1$

	private String VIEW_STRUCTURE_OPTIONS = Messages.getString("StarBiochemMain.48"); //$NON-NLS-1$

	private String HELP = Messages.getString("StarBiochemMain.49"); //$NON-NLS-1$
	private String HELP_ABOUT = Messages.getString("StarBiochemMain.50"); //$NON-NLS-1$
	private String HELP_NAVIGATION = Messages.getString("StarBiochemMain.51"); //$NON-NLS-1$
	private String HELP_NAVIGATION_TITLE = Messages.getString("StarBiochemMain.52"); //$NON-NLS-1$

	
	public static void main(String[] args)
	{
		try
		{
			final String[] myargs = args;
			final String PROJECT = "StarBiochem2"; //$NON-NLS-1$
			if(!VersionChecker.processVersionArguments(PROJECT, Version.getProject(), Version.getBuildDate(), args))
			{
				UIHelpers.addTracking(PROJECT);
				Messages.updateBundle(Locale.getDefault());				
				// ivan's fix - don't delete next line
				javax.swing.JPopupMenu.setDefaultLightWeightPopupEnabled(true);
				if((null != myargs) && ((1 == myargs.length) || (2 == myargs.length) || (3 == myargs.length)))
				{
					SwingUtilities.invokeAndWait(new Runnable()
					{
						public void run()
						{
							String option = myargs[0].toLowerCase().trim();
							if(CREATE_OPTION.equals(option))
							{
								String target_pathname = ((2 == myargs.length) ? myargs[1].trim() : myargs[2].trim());
								if((null != target_pathname) && (0 != target_pathname.length()))
								{
									String source_pathname = myargs[1].trim();
									File source = new File(source_pathname);
									if(source.isDirectory())
									{
										String[] filenames = source.list();
										if(null != filenames)
										{
											for(String source_filename: filenames)
											{
												File f = new File(source, source_filename);
												if(f.isFile())
												{
													String[] singleSourceArgs = new String[3];
													singleSourceArgs[0] = myargs[0];
													singleSourceArgs[1] = f.getAbsolutePath();
													singleSourceArgs[2] = target_pathname;
													new StarBiochemMain(singleSourceArgs);
												}
											}
										}
									}
									else
									{
										new StarBiochemMain(myargs);
									}
								}
							}
							else
							{
								new StarBiochemMain(myargs);
							}

						}
					});
				}
				else
				{
					JFrame frame = new StarBiochemMain(myargs);
					VersionChecker.invokeLater(PROJECT, Version.getProject(), Version.getBuildDate(), frame);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void main_restart()
	{
		main(arguments);
	}
	
	public JFrame getDialogFrame()
    {
	    return null;
    }

	public StarBiochemException getStarBiochemException() {
		return null;
	}

	boolean isCreatingJmolZip = false;
	public boolean isCreatingJmolZip() {
		return isCreatingJmolZip;
	}

	File target = null;
	public File getTarget() {
		return target;
	}
}
