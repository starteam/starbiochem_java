package imports;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

public class ImportsSearchRCSB {

	private static final String EMPTY_STRING = ""; //$NON-NLS-1$
	private static final int PDBID_LENGTH = 4;

	/**
	 * keywordSearch RESTful RCSB web service for keywords
	 * 
	 * @param servicelocation
	 *            for RESTful RCSB web service
	 * @param keywords
	 * @return a list of PDB ids.
	 */
	public static List<String> keywordSearch(String servicelocation,
			String[] keywords) throws IOException {
		if ((null != servicelocation) && !EMPTY_STRING.equals(servicelocation)
				&& (null != keywords) && (0 != keywords.length)) {
			
				threadStarter(keywords, servicelocation);
				return treeToArray();
		}
		return null;
	}

	private static String createXMLfromKeywords(String[] keywords) {
		if ((null != keywords) && (0 != keywords.length)) {
			String keywordString = EMPTY_STRING;
			for (String keyword : keywords) {
				if ((null != keyword) && !EMPTY_STRING.equals(keyword)) {
					keywordString = " " + keyword; //$NON-NLS-1$
				}
			}
			keywordString = keywordString.trim();
			if (!EMPTY_STRING.equals(keywordString)) {
				String format = "<orgPdbQuery>" //$NON-NLS-1$
						+ " <queryType>org.pdb.query.simple.AdvancedKeywordQuery</queryType>" //$NON-NLS-1$
						+ " <description>Text Search for: %1$s</description>" //$NON-NLS-1$
						+ " <keywords>%1$s</keywords>" + "</orgPdbQuery>"; //$NON-NLS-1$ //$NON-NLS-2$
				return String.format(format, keywordString);
			}
		}
		return null;
	}
	
	private static String createPdbSearchXMLfromKeywords(String[] keywords) {
		if ((null != keywords) && (0 != keywords.length)) {
			String keywordString = EMPTY_STRING;
			for (String keyword : keywords) {
				if ((null != keyword) && !EMPTY_STRING.equals(keyword)) {
					keywordString = " " + keyword; //$NON-NLS-1$
				}
			}
			keywordString = keywordString.trim();
			if (!EMPTY_STRING.equals(keywordString)) {
				String format = "<orgPdbQuery>" //$NON-NLS-1$
						+ " <queryType>org.pdb.query.simple.StructureIdQuery</queryType>" //$NON-NLS-1$
						+ " <description>Simple query for a list of PDB IDs (1 IDs) : %1$s</description>" //$NON-NLS-1$
						+ " <structureIdList>%1$s</structureIdList>" + "</orgPdbQuery> "; //$NON-NLS-1$ //$NON-NLS-2$
				return String.format(format, keywordString);
			}
		}
		return null;
	}
	
	private static String createTitleSearchXMLfromKeywords(String[] keywords) {
		if ((null != keywords) && (0 != keywords.length)) {
			String keywordString = EMPTY_STRING;
			for (String keyword : keywords) {
				if ((null != keyword) && !EMPTY_STRING.equals(keyword)) {
					keywordString = " " + keyword; //$NON-NLS-1$
				}
			}
			keywordString = keywordString.trim();
			if (!EMPTY_STRING.equals(keywordString)) {
				String format = "<orgPdbQuery>" //$NON-NLS-1$
						+ " <queryType>org.pdb.query.simple.StructTitleQuery</queryType>" //$NON-NLS-1$
						+ " <description>StructTitleQuery: struct.title.comparator=contains struct.title.value=%1$s </description>" //$NON-NLS-1$
						+ " <struct.title.comparator>contains</struct.title.comparator>" //$NON-NLS-1$
						+ " <struct.title.value>%1$s</struct.title.value>" + "</orgPdbQuery> "; //$NON-NLS-1$ //$NON-NLS-2$
				return String.format(format, keywordString);
			}
		}
		return null;
	}

	final static TreeSet<String> tree = new TreeSet<String>();
		private static Thread postQuery(final String servicelocation,
			final String xml) throws IOException {
		if ((null != servicelocation) && !EMPTY_STRING.equals(servicelocation)
				&& (null != xml) && (!EMPTY_STRING.equals(xml))) 
		{
			Thread t = new Thread(new Runnable()
			{
				public void run()
				{
					try{
					final URL url = new URL(servicelocation);
					final String encodedXML = URLEncoder.encode(xml, "UTF-8");	 //$NON-NLS-1$
					final InputStream in = doPOST(url, encodedXML);
					if (null != in) {
						BufferedReader rd = new BufferedReader(
								new InputStreamReader(in));
						if (null != rd) {
							String line;
							while ((line = rd.readLine()) != null) {
								if (PDBID_LENGTH == line.length()) {
									tree.add(line);
								}
							}
							rd.close();
							}
						}
						in.close();
					}
					catch(Exception e){
						e.printStackTrace();
					}				
				}
			});
			return t;
		}
		return null;
	}
	
	private static void threadStarter(String[] keywords, String servicelocation)throws IOException
	{
		String pdbXml = createPdbSearchXMLfromKeywords(keywords);
		String textXml = createXMLfromKeywords(keywords);
		String titleXml = createTitleSearchXMLfromKeywords(keywords);
		
		Thread pdb = postQuery(servicelocation, pdbXml);
		Thread text = postQuery(servicelocation, textXml);
		Thread title = postQuery(servicelocation, titleXml);
		
		pdb.start();
		text.start();
		title.start();
		
		try{
			pdb.join();
			text.join();
			title.join();
		}
		catch(Exception e){}
		}
	
	private static List<String> treeToArray()
	{
		final List<String> treeList = new ArrayList<String>();

		if (!tree.isEmpty()) {
			Iterator<String> itr = tree.iterator();
			while(itr.hasNext())
			{
				String myItr = itr.next().toString();
				treeList.add(myItr);
			}
		}
		tree.clear();
		if(treeList != null && !treeList.isEmpty())
		{
			return treeList;
		}
		return null;
	}


	private static InputStream doPOST(final URL url, final String data)
			throws IOException {
		if ((null != url) && (null != data) && !EMPTY_STRING.equals(data)) {
			URLConnection connection = url.openConnection();
			if (null != connection) {
				connection.setDoOutput(true);
				OutputStream os = connection.getOutputStream();
				if (null != os) {
					OutputStreamWriter writer = new OutputStreamWriter(
							connection.getOutputStream());
					if (null != writer) {
						writer.write(data);
						writer.flush();
						writer.close();
					}
					os.close();
					return connection.getInputStream();
				}
			}
		}
		return null;
	}

	public static void main(String[] args) {

		String xml = "<orgPdbQuery>" //$NON-NLS-1$
				+ " <queryType>org.pdb.query.simple.AdvancedKeywordQuery</queryType>" //$NON-NLS-1$
				+ " <description>Text Search for: hemoglobin 1a3n</description>" //$NON-NLS-1$
				+ " <keywords>hemoglobin 1a3n</keywords>" + "</orgPdbQuery>"; //$NON-NLS-1$ //$NON-NLS-2$

		try {
			Thread t = ImportsSearchRCSB.postQuery(
					"http://www.rcsb.org/pdb/rest/search", xml); //$NON-NLS-1$
			t.start();
			t.join();
			List<String> pdbIds = treeToArray();

			for (String string : pdbIds) {
			System.out.println(string);
			}
				
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

}
