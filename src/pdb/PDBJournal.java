package pdb;

import java.io.BufferedReader;

public class PDBJournal extends PDBRecord{
	private static final long serialVersionUID = 1L;
	/***************************************************************************
	 * http://www.wwpdb.org/documentation/format32/sect2.html#SOURCE
	 * 
JRNL (updated)

Overview

The JRNL record contains the primary literature citation that describes the experiment which resulted in the deposited coordinate set. There is at most one JRNL reference per entry. If there is no primary reference, then there is no JRNL reference. Other references are given in REMARK 1.

Record Format

COLUMNS       DATA TYPE      FIELD         DEFINITION                  
-----------------------------------------------------------------------
 1 -  6       Record name    "JRNL"                                  
13 - 79       LString        text          See Details below.           

Details

    * The following tables are used to describe the sub-record types of the JRNL record.
    * The AUTH sub-record is mandatory in JRNL. This is followed by TITL, EDIT, REF, PUBL, REFN, PMID and DOI sub- record types. REF and REFN are also mandatory in JRNL. EDIT and PUBL may appear only if the reference is to a non-journal.

1. AUTH

    * AUTH contains the list of authors associated with the cited article or contribution to a larger work (i.e., AUTH is not used for the editor of a book).
    * The author list is formatted similarly to the AUTHOR record. It is a comma-separated list of names. Spaces at the end of a sub-record are not significant; all other spaces are significant. See the AUTHOR record for full details.
    * The authorList field of continuation sub-records in JRNL differs from that in AUTHOR by leaving no leading blank in column 20 of any continuation lines.
    * One author's name, consisting of the initials and family name, cannot be split across two lines. If there are continuation sub-records, then all but the last sub-record must end in a comma.

COLUMNS       DATA  TYPE     FIELD               DEFINITION                         
-------------------------------------------------------------------------------
 1 -  6       Record name    "REMARK"                                          
10            LString(1)     "1"                                               
13 - 16       LString(4)     "AUTH"              Appears on all continuation records.
17 - 18       Continuation   continuation        Allows  a long list of authors.     
20 - 79       List           authorList          List of the authors.               

2. TITL

    * TITL specifies the title of the reference. This is used for the title of a journal article, chapter, or part of a book. The TITL line is omitted if the author(s) listed in authorList wrote the entire book (or other work) listed in REF and no section of the book is being cited.
    * If an article is in a language other than English and is printed with an alternate title in English, the English language title is given, followed by a space and then the name of the language (in its English form, in square brackets) in which the article is written.
    * If the title of an article is in a non-Roman alphabet the title is transliterated.
    * The actual title cited is reconstructed in a manner identical to other continued records, i.e., trailing blanks are discarded and the continuation line is concatenated with a space inserted.
    * A line cannot end with a hyphen. A compound term (two elements connected by a hyphen) or chemical names which include a hyphen must appear on a single line, unless they are too long to fit on one line, in which case the split is made at a normally-occurring hyphen. An individual word cannot be hyphenated at the end of a line and put on two lines. An exception is when there is a repeating compound term where the second element is omitted, e.g., "DOUBLE- AND TRIPLE-RESONANCE". In such a case the non-completed word "DOUBLE-" could end a line and not alter reconstruction of the title.

COLUMNS       DATA  TYPE     FIELD               DEFINITION                         
-----------------------------------------------------------------------------------
 1 -  6       Record name    "REMARK"                                          
10            LString(1)     "1"                                               
13 - 16       LString(4)     "TITL"              Appears on all continuation records.
17 - 18       Continuation   continuation        Permits long titles.               
20 - 79       LString        title               Title of the article. 

3. EDIT

    * EDIT appears if editors are associated with a non-journal reference. The editor list is formatted and concatenated in the same way that author lists are.

COLUMNS       DATA  TYPE     FIELD               DEFINITION                         
-----------------------------------------------------------------------------------
1 -  6        Record name    "REMARK"                                          
10            LString(1)     "1"                                               
13 - 16       LString(4)     "TITL"              Appears  on all continuation records.
17 - 18       Continuation   continuation        Permits long titles.               
20 - 79       LString        title               Title of the article.

4. REF

    * REF is a group of fields that contain either the publication status or the name of the publication (and any supplement and/or report information), volume, page, and year. There are two forms of this sub-record group, depending upon the citation's publication status.

      4a. If the reference has not been published yet, the sub-record type group has the form:

COLUMNS       DATA  TYPE     FIELD               DEFINITION
--------------------------------------------------------------------------------
 1 -  6       Record name    "JRNL  "
13 - 16       LString(3)     "REF"
20 - 34       LString(15)    "TO BE PUBLISHED"

    * Publication name (first item in pubName field):

      If the publication is a serial (i.e., a journal, an annual, or other non-book or non-monographic item issued in parts and intended to be continued indefinitely), use the abbreviated name of the publication as listed in PubMed with periods.

      If the publication is a book, monograph, or other non-serial item, use its full name according to the Anglo-American Cataloguing Rules, 2nd Revised Edition; (AACR2R). (Non-serial items include theses, videos, computer programs, and anything that is complete in one or a finite number of parts.) If there is a sub-title, verifiable in an online catalog, it will be included using the same punctuation as in the source of verification. Preference will be given to verification using cataloging of the Library of Congress, the National Library of Medicine, and the British Library, in that order.

      If a book is part of a monographic series: the full name of the book (according to the AACR2R) is listed first, followed by the name of the series in which it was published. The series information is given within parentheses and the series name is preceded by "IN:" and a space. The series name should be listed in full unless the series has an accepted ISO abbreviation. If applicable, the series name should be followed, after a comma and a space, by a volume (V.) and/or number (NO.) and/or part (PT.) indicator and its number and/or letter in the series.
    * Supplement (follows publication name in pubName field):

      If a reference is in a supplement to the volume listed, or if information about a "part" is needed to distinguish multiple parts with the same page numbering, such information should be put in the REF sub-record.

      A supplement indication should follow the name of the publication and should be preceded by a comma and a space. Supplement should be abbreviated as "SUPPL." If there is a supplement number or letter, it should follow "SUPPL." without an intervening space. A part indication should also follow the name of the publication and be preceded by a comma and a space. A part should be abbreviated as "PT.", and the number or letter should follow without an intervening space.

      If there is both a supplement and a part, their order should reflect the order printed on the work itself.
    * Report (follows publication name and any supplement or part information in pubName field):

      If a book has a report designation, the report information should follow the title and precede series information. The name and number of the report is given in parentheses, and the name is  preceded by "REPORT:" and a space.
    * Reconstruction of publication name:

      The name of the publication is reconstructed by removing any trailing blanks in the pubName field, and concatenating all of the pubName fields from the continuation lines with an intervening space. There are two conditions where no intervening space is added between lines: when the pubName field on a line ends with a hyphen or a period, or when the line ends with a hyphen (-). When the line ends with a period (.), add a space if this is the only period in the entire pubName field; do not add a space if there are two or more periods throughout the pubName field, excluding any periods after the designations "SUPPL", "V", "NO", or "PT".
    * Volume, page, and year (volume, first page, year fields respectively):

      The REF sub-record type group also contains information about volume, page, and year when applicable.

      In the case of a monograph with multiple volumes which is also in a numbered series, the number in the volume field represents the number of the book, not the series. (The volume number of the series is in parentheses with the name of the series, as described above under publication name.)

COLUMNS       DATA  TYPE     FIELD              DEFINITION
---------------------------------------------------------------------------------------
 1 -  6       Record name    "JRNL  "
13 - 16       LString(3)     "REF "
17 - 18       Continuation   continuation       Allows long publication names.
20 - 47       LString        pubName            Name  of the publication including section
                                                or series designation. This is the only
                                                field of this sub-record which may be
                                                continued on  successive sub-records.
50 - 51       LString(2)     "V."               Appears in the first sub-record only,
                                                and  only if column 55 is non-blank.
52 - 55       String         volume             Right-justified blank-filled volume
                                                information; appears in the first
                                                sub-record only.
57 - 61       String         page               First page of the article; appears in 
                                                the first sub-record only.
63 - 66       Integer        year               Year of publication; first sub-record only.

5. PUBL

    * PUBL contains the name of the publisher and place of publication if the reference is to a book or other non-journal publication. If the non-journal has not yet been published or released, this sub-record is absent.
    * The place of publication is listed first, followed by a space, a colon, another space, and then the name of the publisher/issuer. This arrangement is based on the ISBD(M) International Standard Bibliographic Description for Monographic Publications (Rev.Ed., 1987) and the AACR2R, and is used in public online catalogs in libraries. Details on the contents of PUBL are given below. Place of publication:

      Give the place of publication. If the name of the country, state, province, etc. is considered necessary to distinguish the place of publication from others of the same name, or for identification, then follow the city with a comma, a space, and the name of the larger geographic area.

      If there is more than one place of publication, only the first listed will be used. If an online catalog record is used to verify the item, the first place listed there will be used, omitting any brackets. Preference will be given to the cataloging done by the Library of Congress, the National Library of Medicine, and the British Library, in that order.
    * Publisher's name (or name of other issuing entity):

      Give the name of the publisher in the shortest form in which it can be understood and identified internationally, according to AACR2R rule 1.4D.

      If there is more than one publisher listed in the publication, only the first will be used in the PDB file. If an online catalog record is used to verify the item, the first place listed there will be used for the name of the publisher. Preference will be given to the cataloging of the Library of Congress, the National Library of Medicine, and the British Library, in that order.
    * Ph.D. and other theses:

      Theses are presented in the PUBL record if the degree has been granted and the thesis made available for public consultation by the degree-granting institution. The name of the degree-granting institution (the issuing agency) is followed by a space and "(THESIS)".
    * Reconstruction of place and publisher:

      The PUBL sub-record type can be reconstructed by removing all trailing blanks in the pub field and concatenating all of the pub fields from the continuation lines with an intervening space. Continued lines do not begin with a space.

COLUMNS       DATA  TYPE     FIELD              DEFINITION
--------------------------------------------------------------------------------------
 1 -  6       Record name    "JRNL  "
13 - 16       LString(4)     "PUBL"
17 - 18       Continuation   continuation       Allows long publisher and place names.
20 - 70       LString        pub                City  of publication and name of the
                                                publisher/institution.


6. REFN

    * REFN is a group of fields that contain encoded references to the citation. No continuation lines are possible. Each piece of coded information has a designated field.
    * There are two forms of this sub-record type group, depending upon the publication status.

6a. This form of the REFN sub-record type group is used if the citation has not been published.

COLUMNS       DATA  TYPE     FIELD              DEFINITION
--------------------------------------------------------------------------------
 1 -  6       Record  name   "JRNL  "
13 - 16       LString(4)     "REFN"

6b. This form of the REFN sub-record type group is used if the citation has been published.

COLUMNS       DATA TYPE      FIELD              DEFINITION
-------------------------------------------------------------------------------
 1 -  6       Record name    "JRNL  "
13 - 16       LString(4)     "REFN"
36 - 39       LString(4)     "ISSN" or          International Standard Serial Number or 
                             "ESSN"             Electronic Standard Serial Number.
41 - 65       LString        issn               ISSN number (final digit may be a
                                                letter and may contain one or 
                                                more dashes).

7. PMID

    * PMID lists the PubMed unique accession number of the publication related to the entry.

COLUMNS       DATA  TYPE     FIELD              DEFINITION
--------------------------------------------------------------------------------
 1 -  6       Record  name   "JRNL  "
13 - 16       LString(4)     "PMID"
20 - 79       Integer        continuation       unique PubMed identifier number assigned to 
                                                the publication  describing the experiment.
                                                Allows  for a long PubMed ID number.

8. DOI

    * DOI is the Digital Object Identifier for the related electronic publication ("e-pub"), if applicable.
    * Every DOI consists of a publisher prefix, a fore-slash ("/"), and then a suffix which can be any length and may include a combination of numbers and alphabets. For example: 10.1073/PNAS.0712393105

COLUMNS       DATA  TYPE     FIELD              DEFINITION
--------------------------------------------------------------------------------
 1 -  6       Record  name   "JRNL  "
13 - 16       LString(4)     "DOI "
20 - 79       LString        continuation       Unique DOI assigned to the publication
                                                describing the experiment.
                                                Allows for a long DOI string.

Verification/Validation/Value Authority Control

wwPDB verifies that this record is correctly formatted.

Citations appearing in JRNL may not also appear in REMARK 1.

Relationships to Other Record Types

The publication cited as the JRNL record may not be repeated in REMARK 1.

Example

         1         2         3         4         5         6         7         8
12345678901234567890123456789012345678901234567890123456789012345678901234567890
JRNL        AUTH   G.FERMI,M.F.PERUTZ,B.SHAANAN,R.FOURME                        
JRNL        TITL   THE CRYSTAL STRUCTURE OF  HUMAN DEOXYHAEMOGLOBIN AT           
JRNL        TITL 2 1.74 A RESOLUTION                                            
JRNL        REF    J.MOL.BIOL.                   V. 175   159 1984               
JRNL        REFN                   ISSN 0022-2836                               
JRNL        PMID   6726807                                                      
JRNL        DOI    10.1016/0022-2836(84)90472-8                                 

Known Problems

    * Interchange of bibliographic information and linking with other databases is hampered by the lack of labels or specific locations for certain types of information or by more than one type of information being in a particular location. This is most likely to occur with books, series, and reports. Some of the points below provide details about the variations and/or blending of information.
    * Titles of the publications that require more than 28 characters on the REF line must be continued on subsequent lines. There is some awkwardness due to volume, page, and year appearing on the first REF line, thereby splitting up the title.
    * Information about a supplement and its number/letter is presented in the publication's title field (on the REF lines in columns 20 - 47).
    * When series information for a book is presented, it is added to the REF line. The number of REF lines can become large in some cases because of the 28-column limit for title information in REF.
    * Books that are issued in more than one series are not accommodated.
    * Pagination is limited to the beginning page.
*/
	
	public static final String RECNAME = "JRNL";   //$NON-NLS-1$
	public static final String REF = "REF"; //$NON-NLS-1$
	public static final String VDOT = "V."; //$NON-NLS-1$
	public static final String PMID = "PMID"; //$NON-NLS-1$
	

//	 1 -  6       Record name    "JRNL  "
//	 13 - 16       LString(3)     "REF "
	private static final int START_REF = 12;
	private static final int FINISH_REF = START_REF + REF.length();
	
//	 17 - 18       Continuation   continuation       Allows long publication names.

//	 20 - 47       LString        pubName            Name  of the publication including section
//	                                                 or series designation. This is the only
//	                                                 field of this sub-record which may be
//	                                                 continued on  successive sub-records.
	private static final int START_PUBNAME = 19;
	private static final int FINISH_PUBNAME = START_PUBNAME + (47-20);
	
//	 50 - 51       LString(2)     "V."               Appears in the first sub-record only,
//	                                                 and  only if column 55 is non-blank.
//	 52 - 55       String         volume             Right-justified blank-filled volume
//	                                                 information; appears in the first
//	                                                 sub-record only.
	private static final int START_VOLUME = 52;
	private static final int FINISH_VOLUME = START_VOLUME + 4;
	
//	 57 - 61       String         page               First page of the article; appears in 
//	                                                 the first sub-record only.
	private static final int START_PAGE = 56;
	private static final int FINISH_PAGE = START_PAGE + 5;
	
//	 63 - 66       Integer        year               Year of publication; first sub-record only.
	private static final int START_YEAR = 62;
	private static final int FINISH_YEAR = START_YEAR + 4;
	
//	13 - 16       LString(4)     "PMID"
	private static final int START_PMID = 12;
	private static final int FINISH_PMID = START_PMID + PMID.length();
	
//	20 - 79       Integer        continuation       unique PubMed identifier number assigned to 
//                                                  the publication  describing the experiment.
	private static final int START_PUBMEDID = 19;
	private static final int FINISH_PUBMEDID = START_PUBMEDID + (79-20);

	protected String pubName = ""; //$NON-NLS-1$
	public String getPubName() { return this.pubName; }
	public void setPubName(String pubName) { this.pubName = pubName; }

	protected String volume = ""; //$NON-NLS-1$
	public String getVolume() { return this.volume; }
	public void setVolume(String volume) { this.volume = volume; }

	protected String page = ""; //$NON-NLS-1$
	public String getPage() { return this.page; }
	public void setPage(String page) { this.page = page; }

	protected String year = ""; //$NON-NLS-1$
	public String getYear() { return this.year; }
	public void setYear(String year) { this.year = year; }

	protected String pubmedID = ""; //$NON-NLS-1$
	public String getPubmedID() { return this.pubmedID; }
	public void setPubmedID(String pubmedid) { this.pubmedID = pubmedid; }

	public static String extractJournal(PDBJournal pdbJournal, BufferedReader br, String pubNameRecord)
	{
		if((null != pdbJournal) && (null != br) && (null != pubNameRecord))
		{
			try
			{
				String line = pubNameRecord;
				while(line.startsWith(RECNAME))
				{
					if(line.substring(START_REF, FINISH_REF).startsWith(REF))
					{
						pdbJournal.setPubName( extractString(line, START_PUBNAME, FINISH_PUBNAME).trim() );
						pdbJournal.setVolume( extractString(line, START_VOLUME, FINISH_VOLUME).trim() );
						pdbJournal.setPage( extractString(line, START_PAGE, FINISH_PAGE).trim() );
						pdbJournal.setYear( extractString(line, START_YEAR, FINISH_YEAR).trim() );
						line = br.readLine();
					}
					if(line.substring(START_PMID, FINISH_PMID).startsWith(PMID))
					{
						pdbJournal.setPubmedID( extractString(line, START_PUBMEDID, FINISH_PUBMEDID).trim() );
						line = br.readLine();
					}
					else
					{
						line = br.readLine();
					}
				}
				return line;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return null;
	}
	
}
