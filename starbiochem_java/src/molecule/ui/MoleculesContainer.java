package molecule.ui;

import java.awt.Component;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import molecule.PDBExtraDataImpl;
import molecule.interfaces.Bond;
import molecule.interfaces.Molecule;
import molecule.interfaces.PDBExtraData;
import molecule.ui.jmol.Viewer;
import pdb.PDBAuthor;
import pdb.PDBCompnd;
import pdb.PDBHeader;
import pdb.PDBHelix;
import pdb.PDBJournal;
import pdb.PDBRemark;
import pdb.PDBSheetStrand;
import pdb.PDBSource;
import pdb.PDBTitle;
import star.annotations.Handles;
import star.annotations.SignalComponent;
import app.StarBiochemException;
import app.signal.CloseAllMoleculesRaiser;
import app.signal.CloseMoleculeRaiser;
import app.signal.FileOpenRaiser;
import app.signal.ImportOpenRaiser;
import app.signal.ResetAllMoleculesRaiser;
import app.signal.ResetMoleculeRaiser;
import app.signal.ResetSceneRaiser;
import app.signal.SampleOpenRaiser;
import app.Messages;

@SignalComponent(extend = JTabbedPane.class, raises = {CloseMoleculeRaiser.class})
public class MoleculesContainer extends MoleculesContainer_generated
{
	private static final long serialVersionUID = 1L;
	
	StarBiochemException starBiochemException = null;
	public StarBiochemException getStarBiochemException() {
		return starBiochemException;
	}
    

	public MoleculesContainer()
	{
		super(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
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
		this.setBorder(new EmptyBorder(0,0,0,0));
	}

	private void end()
	{
		this.removeAll();
	}

	public void addMolecule(Viewer viewer, Molecule molecule)
	{
		String title = getTitle(molecule.getUrl());
		String tooltip = molecule.getUrl();
		int tabCount = getTabCount();
		try
		{
			if((null != viewer) && (null != molecule))
			{
				MoleculeContainer moleculeContainer = new MoleculeContainer(viewer, molecule, title);
				this.addTab(title, null, moleculeContainer, tooltip);
				moleculeContainer.initTree();
				this.setSelectedComponent(moleculeContainer);
			}
		}
		catch (StarBiochemException ex)
		{
			try
			{
				JOptionPane.showMessageDialog(this, ex.getMessage() + "\n" + Messages.getString("MoleculesContainer.1") + tooltip + ".\n" + Messages.getString("MoleculesContainer.3")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				Component component = this.getTabComponentAt(tabCount);
				this.remove(component);
				this.removeTabAt(tabCount);
			}
			catch(IndexOutOfBoundsException e1)
			{
				ex.printStackTrace();
			}
		}
		catch (OutOfMemoryError ex)
		{
			try
			{
				JOptionPane.showMessageDialog(this, ex.getMessage() + "\n" + Messages.getString("MoleculesContainer.5") + tooltip + ".\n" + Messages.getString("MoleculesContainer.7")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				Component component = this.getTabComponentAt(tabCount);
				this.remove(component);
				this.removeTabAt(tabCount);
			}
			catch(IndexOutOfBoundsException e1)
			{
				ex.printStackTrace();
			}
		}
	}
	
	private String getTitle(String url)
	{
		String title = url;
		try
        {
	        URL myURL = new URL(url);
	        String filename = myURL.getFile();
	        if(null != filename)
	        {
	        	try
	        	{
	        		File myFile = new File(filename);
	        		title = myFile.getName();
	        	}
	        	catch(Exception exi)
	        	{
	        	}
	        }
        }
        catch (MalformedURLException e)
        {
        	try
        	{
        		File myFile = new File(url);
        		title = myFile.getName();
        	}
        	catch(Exception exi)
        	{
        	}
        }
		int index = title.lastIndexOf("."); //$NON-NLS-1$
		if(-1 != index)
		{
			title = title.substring(0, index);
		}
        return title;
	}
	
	@Handles(raises = {})
	protected void handlePdbOpenRaiser(SampleOpenRaiser raiser)
	{
		if(null != raiser)
		{
		    try
	        {
		    	Viewer viewer = new Viewer();
		    	String source = raiser.getSampleId();
		    	String location = raiser.getSamplesJarLocation();
		        JarFile aJar = new JarFile(location);
		        if(null != aJar)
		        {
		        	JarEntry aJarEntry = aJar.getJarEntry(source);
		        	if(null != aJarEntry)
		        	{
		        		BufferedInputStream bufferedInputStream = new BufferedInputStream(aJar.getInputStream(aJarEntry));
		        		if(null != bufferedInputStream)
		        		{
			        		String name = aJarEntry.getName();
			        		if(name.toLowerCase().endsWith(".pdb")) //$NON-NLS-1$
			        		{
			        			openPdbJarEntry(viewer, location, source, new BufferedReader( new InputStreamReader( bufferedInputStream) ) );
			        		}
						    else if(name.toLowerCase().endsWith(".jmol")) //$NON-NLS-1$
						    {
						    	File jmolFile = createJmolFileFromJar(source, bufferedInputStream);
						    	openJmolFile(jmolFile.getAbsolutePath());
						    	jmolFile.deleteOnExit();
						    }
		        		}
		        	}
		        	else
		        	{
		        		System.err.println( Messages.getString("MoleculesContainer.11") + source ) ; //$NON-NLS-1$
		        	}
		        }
	        }
		    catch (OutOfMemoryError e)
			{
				JOptionPane.showMessageDialog(this, e.getMessage());
			}
	        catch (Exception e)
	        {
	        	e.printStackTrace();
				JOptionPane.showMessageDialog(this, e.getMessage());
	        }
		}
	}

	private File createJmolFileFromJar(String source, BufferedInputStream bufferedInputStream) throws IOException
	{
		File jmolFile = File.createTempFile(source,""); //$NON-NLS-1$
		if(jmolFile.exists() && jmolFile.canWrite())
		{
			FileOutputStream jmolWriter = new FileOutputStream(jmolFile);
			if(null != jmolWriter)
			{
				int size = 512 * 1024;
				byte[] buffer = new byte[size];
				int count = 0;
				while(-1 != (count = bufferedInputStream.read(buffer)))
				{
					jmolWriter.write(buffer, 0, count);
				}
				jmolWriter.flush();
				jmolWriter.close();
				return jmolFile;
			}
		}
		return null;
	}

	private void openPdbJarEntry(Viewer viewer, String location, String source, BufferedReader bufferedReaderForOpenRunnable)
	{
	    PDBExtraData r = new PDBExtraDataImpl(bufferedReaderForOpenRunnable);
	    if(null != r)
	    {
			try
			{
			    String urllocationsource = "jar:file://" + location + "!/" + source; //$NON-NLS-1$ //$NON-NLS-2$
			    String script = viewer.getPdbOpenScript(location, source);
		        Molecule molecule = createMolecule(viewer, urllocationsource, script, r);
		        if(null != molecule)
		        {
		        	addMolecule(viewer, molecule);
		        }
			}
			catch (StarBiochemException e)
			{
				e.printStackTrace();
			}
	    }
	}
	
	@Handles(raises = {})
	protected void handleImportOpenRaiser(ImportOpenRaiser raiser)
	{
	    try
        {
	    	Viewer viewer = new Viewer();
			String source = raiser.getImportURL();
		    String script = viewer.getImportOpenScript(source);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((new URL(source)).openStream()));
		    PDBExtraData pdbExtraData = new PDBExtraDataImpl(bufferedReader);
		    Molecule molecule = createMolecule(viewer, source, script, pdbExtraData);
			addMolecule(viewer, molecule);
        }
	    catch (StarBiochemException e)
	    {
			JOptionPane.showMessageDialog(this, e.getMessage() + "\n" + Messages.getString("MoleculesContainer.16")); //$NON-NLS-1$ //$NON-NLS-2$
	    }
		catch (OutOfMemoryError e)
		{
			JOptionPane.showMessageDialog(this, e.getMessage() + "\n" + Messages.getString("MoleculesContainer.18")); //$NON-NLS-1$ //$NON-NLS-2$
		}
        catch (Exception e)
        {
			JOptionPane.showMessageDialog(this, e.getMessage() + "\n" + Messages.getString("MoleculesContainer.20")); //$NON-NLS-1$ //$NON-NLS-2$
        }
	}
	@Handles(raises = {})
	protected void handleFileOpenRaiser(FileOpenRaiser raiser)
	{
	    try
        {
			String filename = raiser.getFileName();
			if(filename.toLowerCase().endsWith(".pdb")) //$NON-NLS-1$
			{
				openPdbFile(filename, raiser.isCreatingJmolZip(), raiser.getTarget());
			}
			else if(filename.toLowerCase().endsWith(".jmol")) //$NON-NLS-1$
			{
				openJmolFile(filename);
			}
        }
	    catch (StarBiochemException e)
		{
			JOptionPane.showMessageDialog(this, e.getMessage() + "\n" + Messages.getString("MoleculesContainer.24")); //$NON-NLS-1$ //$NON-NLS-2$
		}
	    catch (OutOfMemoryError e)
		{
			JOptionPane.showMessageDialog(this, e.getMessage() + "\n" + Messages.getString("MoleculesContainer.26")); //$NON-NLS-1$ //$NON-NLS-2$
		}
        catch (Exception e)
        {
			JOptionPane.showMessageDialog(this, e.getMessage() + "\n" + Messages.getString("MoleculesContainer.28")); //$NON-NLS-1$ //$NON-NLS-2$
        }
	}
	
	private void openPdbFile(String filename, boolean isCreatingJmolZip, File target) throws StarBiochemException
	{
		if(filename.endsWith(".pdb")) //$NON-NLS-1$
		{
			try {
				PDBExtraData pdbExtraData = new PDBExtraDataImpl(new BufferedReader(new FileReader(filename)));
				String urlfileName = "file://" + filename; //$NON-NLS-1$
		
				Viewer viewer = new Viewer();
			    String script = viewer.getFileOpenScript(filename);
		
			    Molecule molecule = createMolecule(viewer, urlfileName, script, pdbExtraData);
				addMolecule(viewer, molecule);
				if(isCreatingJmolZip)
			    {
					String prefix;
					prefix = target.isDirectory() ? target.getCanonicalPath() : target.getParent();
					String title = getTitle(molecule.getUrl());
					String jmolZipFilename = prefix + "/" + title + ".jmol"; //$NON-NLS-1$ //$NON-NLS-2$
			    	createJmolZip(jmolZipFilename, viewer);
			    }
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void openJmolFile(String filename) throws StarBiochemException, IOException
	{
		String urlfileName = "file://" + filename; //$NON-NLS-1$

		Viewer viewer = new Viewer();
	    String script = viewer.getFileOpenScript(filename);
	    
		ZipInputStream uiZIS = new ZipInputStream(new FileInputStream(filename));
		if(null != uiZIS)
		{
			PDBExtraData r = extractOpenRunnablefromJmolFile(uiZIS);
			if(null != r)
			{
				Molecule molecule = createMolecule(viewer, urlfileName, script, r);
				if(null != molecule)
				{
					addMolecule(viewer, molecule);
				}
			}
			uiZIS.close();
		}
	}

	private PDBExtraData extractOpenRunnablefromJmolFile(ZipInputStream uiZIS)
	{
		PDBExtraData pdbExtraData = null;
		if(null != uiZIS)
		{
			try {
				ZipEntry uiZIE;
				while(null != (uiZIE = uiZIS.getNextEntry()))
				{
					String name = uiZIE.getName();
					if(name.toLowerCase().endsWith(".pdb")) //$NON-NLS-1$
					{
			        	BufferedReader bis = new BufferedReader(new InputStreamReader(uiZIS));
			        	if(null != bis)
			        	{
				        	pdbExtraData = new PDBExtraDataImpl(bis);
			        	}
					}
					uiZIS.closeEntry();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return pdbExtraData;
	}
	
	private void createJmolZip(final String jmolZipFilename, final Viewer viewer)
	{
		if((null != jmolZipFilename) && (0 != jmolZipFilename.length()) && (null != viewer))
		{
				SwingUtilities.invokeLater(new Runnable () {
				public void run()
				{
					try {
						String myscript = "write JMOL \"" + jmolZipFilename + "\";"; //$NON-NLS-1$ //$NON-NLS-2$
						viewer.script(myscript);
					} catch (StarBiochemException e) {
						e.printStackTrace();
					}
				}
			});
		}		
	}
	
	private Molecule createMolecule(Viewer viewer, String source, String script, PDBExtraData pdbExtraData) throws StarBiochemException
	{
		String[] hetero = null;
	    String[] nucleic = null;
		PDBHelix[] helix = null;
	    PDBSheetStrand[] sheetStrand = null;
	    String[] protein = null;
	    String[] water = null;
	    PDBAuthor[] author = null;
	    PDBCompnd[] compnd = null;
	    PDBHeader[] header = null;
	    PDBRemark[] remark = null;
	    PDBJournal[] journal = null;
	    PDBSource[] src = null;
	    PDBTitle[] title = null;
	    Bond[] hbonds = null;

	    remark = pdbExtraData.getRemarkArray();
		if(null != remark && remark.length != 0)
		{
			script += viewer.getRemarkScript(remark);
		}
	    viewer.script(script);
	    
	    hetero = viewer.getHetero();
	    nucleic = viewer.getNucleic();
	    protein = viewer.getProtein();
	    water = viewer.getWater();
	    boolean quaternarySSBonds = viewer.hasQuaternarySSBonds();
	    boolean tertiarySSBonds = viewer.hasTertiarySSBonds();
	    helix = pdbExtraData.getHelixArray();
	    sheetStrand = pdbExtraData.getSheetStrandArray();
	    author = pdbExtraData.getAuthorArray();
	    compnd = pdbExtraData.getCompndArray();
	    header = pdbExtraData.getHeaderArray();
	    journal = pdbExtraData.getJournalArray();
	    src = pdbExtraData.getSourceArray();
	    title = pdbExtraData.getTitleArray();
	    hbonds = viewer.getHbonds();
	    
	    return new molecule.Molecule(source, hetero, nucleic, protein, water, quaternarySSBonds, tertiarySSBonds, helix, sheetStrand, author, compnd, header, remark, journal, src, title, hbonds);
	}
	
	@Handles(raises = {})
	protected void handleCloseMoleculeRaiser(CloseMoleculeRaiser raiser)
	{
		if((null != raiser) && (null != raiser.getStarBiochemException()))
		{
			StarBiochemException ex = raiser.getStarBiochemException();
			JOptionPane.showMessageDialog(this, ex);
		}
		this.remove(this.getSelectedComponent());
		invalidate();
	}

	@Handles(raises = {})
	protected void handleCloseAllMoleculesRaiser(CloseAllMoleculesRaiser raiser)
	{
		this.removeAll();
		invalidate();
	}

	@Handles(raises = {})
	protected void handleResetSceneRaiser(ResetSceneRaiser raiser)
	{
		MoleculeContainer moleculeContainer = (MoleculeContainer) this.getSelectedComponent();
		if(null != moleculeContainer)
		{
			moleculeContainer.resetScene();
			invalidate();
			validate();
		}
	}

	@Handles(raises = {})
	protected void handleResetMoleculeRaiser(ResetMoleculeRaiser raiser)
	{
		MoleculeContainer moleculeContainer = (MoleculeContainer) this.getSelectedComponent();
		if(null != moleculeContainer)
		{
			try
			{
				moleculeContainer.reset();
				invalidate();
				validate();
			}
			catch(StarBiochemException ex)
			{
				this.starBiochemException = ex;
				JOptionPane.showMessageDialog(this, ex.getMessage() + "\n" + Messages.getString("MoleculesContainer.38") + moleculeContainer.getToolTipText() + Messages.getString("MoleculesContainer.39")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				this.raise_CloseMoleculeEvent();
			}
		}
	}

	@Handles(raises = {})
	protected void handleResetAllMoleculesRaiser(ResetAllMoleculesRaiser raiser)
	{
		Component[] components = this.getComponents();
		if(null != components)
		{
			for(int i=0; components.length != i; i++)
			{
				if(components[i] instanceof MoleculeContainer)
				{
					MoleculeContainer moleculeContainer = (MoleculeContainer) components[i];
					if(null != moleculeContainer)
					{
						try
						{
							moleculeContainer.reset();
						}
						catch(StarBiochemException ex)
						{
							this.starBiochemException = ex;
							JOptionPane.showMessageDialog(this, ex.getMessage() + "\n" + Messages.getString("MoleculesContainer.41") + moleculeContainer.getToolTipText() + Messages.getString("MoleculesContainer.42")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
							this.setSelectedIndex(i);
							this.raise_CloseMoleculeEvent();
						}
					}
				}
			}
			invalidate();
			validate();
		}
	}

}
