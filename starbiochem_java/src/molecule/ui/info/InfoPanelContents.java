package molecule.ui.info;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.HyperlinkEvent.EventType;

import molecule.interfaces.Molecule;
import pdb.PDBAuthor;
import pdb.PDBCompnd;
import pdb.PDBHeader;
import pdb.PDBRemark;
import pdb.PDBJournal;
import pdb.PDBSource;
import pdb.PDBTitle;
import star.annotations.SignalComponent;
import utils.UIHelpers;
import app.Messages;

@star.annotations.Preferences
@SignalComponent(extend = JEditorPane.class, raises = {})
public class InfoPanelContents extends InfoPanelContents_generated
{
	private static final long serialVersionUID = 1L;
	String pdbid = null;

	private Molecule molecule = null;

	private InfoPanelContents(Molecule molecule)
	{
		super("text/html", ""); //$NON-NLS-1$ //$NON-NLS-2$
		this.molecule = molecule;
		loadPreferences("info"); //$NON-NLS-1$
	}

	@Override
	public Dimension getPreferredSize()
	{
	    return super.getMinimumSize();
	}
	
	public void addNotify()
	{
		super.addNotify();
		setLayout(new BorderLayout());
		Font font = getFont();
		String fontFamily = font.getFamily();
		String headerformat = "<h3 style=\"font-family: " + fontFamily + ";\">%1$s:</h3>"; //$NON-NLS-1$ //$NON-NLS-2$
		String paragraphformat = "%1$s "; //$NON-NLS-1$

		String text = ""; //$NON-NLS-1$
		pdbid = getPDBID(molecule);
		boolean ismodremark = getModremark(molecule);
		if((null != pdbid) && !"".equals(pdbid)) //$NON-NLS-1$
		{
			text += String.format(headerformat, Messages.getString("InfoPanelContents.8")); //$NON-NLS-1$
			String pdbidformat = "<a href='http://www.rcsb.org/pdb/explore/explore.do?structureId=%1$s'>%1$s</a><br>"; //$NON-NLS-1$
			if (ismodremark != false)
			{	
				text += String.format(pdbidformat, pdbid) + (mod_remark);
			}	
			else
			{
				text += String.format(pdbidformat, pdbid);
			}	
		}
		PDBHeader[] headerArray = molecule.getHeaderArray();
		if((null != headerArray) && (0 != headerArray.length))
		{
			String classification = headerArray[0].getClassification().trim();
			if (!"".equals(classification)) //$NON-NLS-1$
			{	
				text += String.format(headerformat, Messages.getString("InfoPanelContents.11")); //$NON-NLS-1$
				for (int i=0; headerArray.length != i; i++)
				{
					classification = headerArray[i].getClassification();
					text += String.format(paragraphformat, classification);
				}
			}	
		}
		PDBCompnd[] compndArray = molecule.getCompndArray();
		if ((null != compndArray) && (0 != compndArray.length))
		{
			text += String.format(headerformat, Messages.getString("InfoPanelContents.12")); //$NON-NLS-1$
			for (int i = 0; compndArray.length != i; i++)
			{
			if(0!=i)
			{
				text += "<br>"; //$NON-NLS-1$
			}
			String fiftycols = ""; //$NON-NLS-1$
			String structureName = new String(compndArray[i].getMoleculeName());
			
			while(structureName.length() > 0)
			{
				if(structureName.length() < 50)
				{
					fiftycols += String.format(paragraphformat, structureName);
					structureName =""; //$NON-NLS-1$
				}	
				else
				{
					fiftycols += structureName.substring(0,49) + "<br>"; //$NON-NLS-1$
					structureName = structureName.substring(50);
				}
			}
			text += fiftycols;
			}
		}
		PDBSource[] sourceArray = molecule.getSourceArray();
		if((null != sourceArray) && (0 != sourceArray.length))
		{
			text += String.format(headerformat, Messages.getString("InfoPanelContents.17")); //$NON-NLS-1$
			for (int i=0; sourceArray.length != i; i++)
			{
				String scientificName = sourceArray[i].getScientificName();
				text += String.format(paragraphformat, scientificName);
			}
			text += String.format(headerformat, Messages.getString("InfoPanelContents.18")); //$NON-NLS-1$
			for (int i=0; sourceArray.length != i; i++)
			{
				String commonName = sourceArray[i].getCommonName();
				text += String.format(paragraphformat, commonName);
			}
		}
		PDBTitle[] titleArray = molecule.getTitleArray();
		if((null != titleArray) && (0 != titleArray.length))
		{
			text += String.format(headerformat, Messages.getString("InfoPanelContents.19")); //$NON-NLS-1$
			for (int i=0; titleArray.length != i; i++)
			{
				String title = titleArray[i].getTitle();
				text += String.format(paragraphformat, title);
			}
		}
		PDBAuthor[] authorArray = molecule.getAuthorArray();
		if((null != authorArray) && (0 != authorArray.length))
		{
			text += String.format(headerformat, Messages.getString("InfoPanelContents.20")); //$NON-NLS-1$
			for (int i=0; authorArray.length != i; i++)
			{
				String title = authorArray[i].getAuthorList();
				text += String.format(paragraphformat, title);
			}
		}
		PDBJournal[] journalArray = molecule.getJournalArray();
		if((null != journalArray) && (0 != journalArray.length))
		{
			String journalFormat = "(%1$s) %2$s <b>%3$s:</b> %4$s "; //$NON-NLS-1$
			String pubmedIDFormat = "<a href='http://www.ncbi.nlm.nih.gov/pubmed/%1$s?dopt=Abstract'>%1$s</a><br>"; //$NON-NLS-1$
			for (int i=0; journalArray.length != i; i++)
			{
				text += String.format(headerformat, Messages.getString("InfoPanelContents.23")); //$NON-NLS-1$
				String year = journalArray[i].getYear();
				String pubName = journalArray[i].getPubName();
				String volume = journalArray[i].getVolume();
				String page = journalArray[i].getPage();
				text += String.format(journalFormat, year, pubName, volume, page);
				String pubmedID = journalArray[i].getPubmedID();
				text += String.format(headerformat, Messages.getString("InfoPanelContents.24")); //$NON-NLS-1$
				text += String.format(pubmedIDFormat, pubmedID);
			}
		}
		text = text.replaceAll("class=\"[^\"]*\"", ""); //$NON-NLS-1$ //$NON-NLS-2$
		setText("<html><body>" + text + "</body></html>"); //$NON-NLS-1$ //$NON-NLS-2$
		setEditable(false);

		addHyperlinkListener(new HyperlinkListener()
		{
			public void hyperlinkUpdate(HyperlinkEvent e)
			{
				if (e.getEventType() == EventType.ACTIVATED)
				{
					try
					{
						final URL url = getURL(e);
						if (url != null)
						{
							UIHelpers.openWebBrowser(url.toExternalForm());
						}
					}
					catch(MalformedURLException me)
					{
						JOptionPane.showMessageDialog(null, Messages.getString("InfoPanelContents.29")); //$NON-NLS-1$
					}
				}
			}

		});
	}

	private String getPDBID(Molecule molecule)
	{
		if(null != molecule)
		{
			PDBHeader[] pdbHeaderArray = molecule.getHeaderArray();
			if((null != pdbHeaderArray) && (0 != pdbHeaderArray.length))
			{
				PDBHeader pdbHeader = pdbHeaderArray[0];
				pdbid = pdbHeader.getIdCode();
				return pdbid;
			}	
		}
		return ""; //$NON-NLS-1$
	}
	
	private boolean getModremark(Molecule molecule)
	{		
		if(null != molecule)
		{
			PDBRemark[] pdbRemarkArray = molecule.getRemarkArray();
			if ((null != pdbRemarkArray) && (0 != pdbRemarkArray.length))
			{
				for (int i = 0; pdbRemarkArray.length != i; i++)
				{	
					PDBRemark modRemark = pdbRemarkArray[i];
					
					if( modRemark != null )
					{
						boolean ismodremark = modRemark.isModremark();
						if (ismodremark)
						{	
						return ismodremark;
						}
					}
				}	
			}
		}
		return false;
	}
	
	private URL getURL(HyperlinkEvent e) throws MalformedURLException
	{
		URL ret = e.getURL();
		if (ret == null)
		{
			ret = new URL(e.getDescription());
		}
		return ret;
	}

	public static InfoPanelContents getDefaultInfoPanelContents(Molecule molecule)
	{
		return new InfoPanelContents(molecule);
	}

	public String info_string = Messages.getString("InfoPanelContents.31"); //$NON-NLS-1$
	String mod_remark = Messages.getString("InfoPanelContents.32"); //$NON-NLS-1$

	protected void loadPreferences(String preferencesName)
	{
		info_string = getPreferences(preferencesName).get("info_string", info_string).trim(); //$NON-NLS-1$
		mod_remark = getPreferences(preferencesName).get("pdb_note", mod_remark).trim(); //$NON-NLS-1$
	}

}