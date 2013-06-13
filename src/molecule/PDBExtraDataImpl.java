package molecule;

import java.io.BufferedReader;
import java.util.ArrayList;

import pdb.PDBAuthor;
import pdb.PDBCompnd;
import pdb.PDBHeader;
import pdb.PDBHelix;
import pdb.PDBJournal;
import pdb.PDBRemark;
import pdb.PDBSheetStrand;
import pdb.PDBSource;
import pdb.PDBTitle;

public class PDBExtraDataImpl implements molecule.interfaces.PDBExtraData
{
	private static final long serialVersionUID = 1L;

	public PDBExtraDataImpl(BufferedReader bufferedReader)
	{
		try
		{
            if(null != bufferedReader)
            {
	        	String line = bufferedReader.readLine();
		        while(true)
		        {
		        	if(null != line)
		        	{		        		
		        		if(line.startsWith(PDBHelix.RECNAME))
		        		{
		        			PDBHelix helix = PDBHelix.extractHelix(line);
		        			addHelix(helix);
		        			line = bufferedReader.readLine();
		        		}
		        		else if(line.startsWith(PDBSheetStrand.RECNAME))
		        		{
		        			PDBSheetStrand sheetStrand = PDBSheetStrand.extractSheetStrand(line);
		        			addSheetStrand(sheetStrand);
		        			line = bufferedReader.readLine();
		        		}
		        		else if(line.startsWith(PDBTitle.RECNAME))
		        		{
		        			PDBTitle titleline = PDBTitle.extractTitle(line);
		        			addTitleline(titleline);
		        			line = bufferedReader.readLine();
		        		}
		        		else if(line.startsWith(PDBHeader.RECNAME))
		        		{
		        			PDBHeader headerline = PDBHeader.extractHeader(line);
		        			addHeaderline(headerline);
		        			line = bufferedReader.readLine();
		        		}
		        		else if(line.startsWith(PDBRemark.RECNAME))
		        		{
		        			PDBRemark remarkline = PDBRemark.extractRemark(line);
		        			addRemarkline(remarkline);
		        			line = bufferedReader.readLine();
		        		}
		        		else if(line.startsWith(PDBAuthor.RECNAME))
		        		{
		        			PDBAuthor authorline = PDBAuthor.extractAuthor(line);
		        			addAuthorline(authorline);
		        			line = bufferedReader.readLine();
		        		}
		        		else if(line.startsWith(PDBCompnd.RECNAME))
		        		{
		        			PDBCompnd compnd = new PDBCompnd();
		        			line = PDBCompnd.extractCompnd(compnd, bufferedReader, line);
		           			if((null != compnd) && !"".equals(compnd.getMoleculeName()))
		           			 	        			{
			        			addCompndline(compnd);
		        			}
		        		}
		        		else if(line.startsWith(PDBSource.RECNAME))
		        		{
		        			PDBSource source = PDBSource.extractSource(line);
		        			if((null != source)  && (!"".equals(source.getScientificName())  || !"".equals(source.getCommonName())))
		        			{
			        			addSourceline(source);
		        			}
		        			line = bufferedReader.readLine();
		        		}
		        		else if(line.startsWith(PDBJournal.RECNAME))
		        		{
		        			PDBJournal jrnl = new PDBJournal();
		        			if(null != jrnl)
		        			{
		        				line = PDBJournal.extractJournal(jrnl, bufferedReader, line);
		        				if(!"".equals(jrnl.getPubName()))
		        				{
		        					addJournalline(jrnl);
		        				}
		        			}
		        		}
		        		else
		        		{
		        			line = bufferedReader.readLine();
		        		}
		        	}
		        	else
		        	{
		        		break;
		        	}
		        }
            }
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	ArrayList<PDBHelix> helices = null;
	private void addHelix(PDBHelix helix)
	{
		if(null == helices)
		{
			helices = new ArrayList<PDBHelix>();
		}
		helices.add(helix);
	}
	public PDBHelix[] getHelixArray()
	{
		if(null != helices)
		{
			return helices.toArray(new PDBHelix[0]);
		}
		return null;
	}

	ArrayList<PDBSheetStrand> sheetStrands = null;
	private void addSheetStrand(PDBSheetStrand sheetStrand)
	{
		if(null == sheetStrands)
		{
			sheetStrands = new ArrayList<PDBSheetStrand>();
		}
		sheetStrands.add(sheetStrand);
	}
	public PDBSheetStrand[] getSheetStrandArray()
	{
		if(null != sheetStrands)
		{
			return sheetStrands.toArray(new PDBSheetStrand[0]);
		}
		return null;
	}

	ArrayList<PDBTitle> titlelines = null;
	private void addTitleline(PDBTitle titleline)
	{
		if(null == titlelines)
		{
			titlelines = new ArrayList<PDBTitle>();
		}
		titlelines.add(titleline);
	}
	public PDBTitle[] getTitleArray()
	{
		if(null != titlelines)
		{
			return titlelines.toArray(new PDBTitle[0]);
		}
		return null;
	}

	ArrayList<PDBHeader> headerlines = null;
	private void addHeaderline(PDBHeader headerline)
	{
		if(null == headerlines)
		{
			headerlines = new ArrayList<PDBHeader>();
		}
		headerlines.add(headerline);
	}
	public PDBHeader[] getHeaderArray()
	{
		if(null != headerlines)
		{
			return headerlines.toArray(new PDBHeader[0]);
		}
		return null;
	}

	ArrayList<PDBRemark> remarklines = null;
	private void addRemarkline(PDBRemark remarkline)
	{
		if(null == remarklines)
		{
			remarklines = new ArrayList<PDBRemark>();
		}
		remarklines.add(remarkline);
	}
	public PDBRemark[] getRemarkArray()
	{
		if(null != remarklines)
		{
			return remarklines.toArray(new PDBRemark[0]);
		}
		return null;
	}
	ArrayList<PDBAuthor> authorlines = null;
	private void addAuthorline(PDBAuthor authorline)
	{
		if(null == authorlines)
		{
			authorlines = new ArrayList<PDBAuthor>();
		}
		authorlines.add(authorline);
	}
	public PDBAuthor[] getAuthorArray()
	{
		if(null != authorlines)
		{
			return authorlines.toArray(new PDBAuthor[0]);
		}
		return null;
	}

	ArrayList<PDBCompnd> compnd = null;
	private void addCompndline(PDBCompnd compndline)
	{
		if(null == compnd)
		{
			compnd = new ArrayList<PDBCompnd>();
		}
		compnd.add(compndline);
	}
	public PDBCompnd[] getCompndArray()
	{
		if(null != compnd)
		{
			return compnd.toArray(new PDBCompnd[0]);
		}
		return null;
	}

	ArrayList<PDBSource> source = null;
	private void addSourceline(PDBSource sourceline)
	{
		if(null == source)
		{
			source = new ArrayList<PDBSource>();
		}
		source.add(sourceline);
	}
	public PDBSource[] getSourceArray()
	{
		if(null != source)
		{
			return source.toArray(new PDBSource[0]);
		}
		return null;
	}

	ArrayList<PDBJournal> jrnl = null;
	private void addJournalline(PDBJournal journalline)
	{
		if(null == jrnl)
		{
			jrnl = new ArrayList<PDBJournal>();
		}
		jrnl.add(journalline);
	}
	public PDBJournal[] getJournalArray()
	{
		if(null != jrnl)
		{
			return jrnl.toArray(new PDBJournal[0]);
		}
		return null;
	}
	
}
