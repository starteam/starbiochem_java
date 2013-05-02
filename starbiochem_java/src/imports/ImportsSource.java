package imports;

import java.io.IOException;
import java.util.List;


public class ImportsSource
{
	private static final String DEFAULT_RCSB_SERVICELOCATION = "http://www.rcsb.org/pdb/rest/search"; //$NON-NLS-1$
	private static final String DEFAULT_RCSB_PDB_URL_PREFIX = "http://pdb.rcsb.org/pdb/files/"; //$NON-NLS-1$
	private static final String DEFAULT_RCSB_PDB_URI_PREFIX = "file://pdb.rcsb.org/pdb/files/"; //$NON-NLS-1$
	private static final String DEFAULT_RCSB_PDB_SUFFIX = ".pdb"; //$NON-NLS-1$
	private static final String DEFAULT_RCSB_ICON_PREFIX = "http://pdb.rcsb.org/pdb/images/"; //$NON-NLS-1$
	private static final String DEFAULT_RCSB_ICON_SUFFIX = "_bio_r_80.jpg?getBest=true"; //$NON-NLS-1$
	private static final String DEFAULT_RCSB_PDB_HEADER_PREFIX = "http://pdb.rcsb.org/pdb/files/"; //$NON-NLS-1$
	private static final String DEFAULT_RCSB_PDB_HEADER_SUFFIX = ".pdb?headerOnly=YES"; //$NON-NLS-1$
	
	public static List<String> searchPdbs(String[] keywords) throws IOException
	{
		return ImportsSearchRCSB.keywordSearch(RCSB_SERVICELOCATION, keywords);
		
	}

	public static String getImportURL(String id)
	{
		return RCSB_PDB_URL_PREFIX + id + RCSB_PDB_SUFFIX;
	}
	
	static String RCSB_SERVICELOCATION = DEFAULT_RCSB_SERVICELOCATION;
	static String RCSB_PDB_URL_PREFIX = DEFAULT_RCSB_PDB_URL_PREFIX;
	static String RCSB_PDB_URI_PREFIX = DEFAULT_RCSB_PDB_URI_PREFIX;
	static String RCSB_PDB_SUFFIX = DEFAULT_RCSB_PDB_SUFFIX;
	static String RCSB_ICON_PREFIX = DEFAULT_RCSB_ICON_PREFIX;
	static String RCSB_ICON_SUFFIX = DEFAULT_RCSB_ICON_SUFFIX;
	static String RCSB_PDB_HEADER_PREFIX = DEFAULT_RCSB_PDB_HEADER_PREFIX;
	static String RCSB_PDB_HEADER_SUFFIX = DEFAULT_RCSB_PDB_HEADER_SUFFIX;

}
