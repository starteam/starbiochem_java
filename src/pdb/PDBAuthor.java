package pdb;

public class PDBAuthor extends PDBRecord{
	private static final long serialVersionUID = 1L;

	/***************************************************************************
	 * http://www.rcsb.org/pdb/docs/format/pdbguide2.2/guide2.2_frame.html
    AUTHOR
    Overview 

    The AUTHOR record contains the names of the people responsible for the contents of the entry. 

    Record Format 


    COLUMNS       DATA TYPE      FIELD         DEFINITION
    ----------------------------------------------------------------------------------
    1 -  6       Record name    "AUTHOR"

    9 - 10       Continuation   continuation  Allows concatenation of multiple
                                           records.

    11 - 70       List           authorList    List of the author names, separated
                                           by commas.


    Details 

    * The authorList field lists author names separated by commas with no subsequent spaces. 

    * Representation of personal names: 

    - First and middle names are indicated by initials, each followed by a period, and precede the surname. 
    - Only the surname (family or last name) of the author is given in full. 
    - Hyphens can be used if they are part of the author's name. 
    - Apostrophes are allowed in surnames. 
    - The word Junior is not abbreviated. 
    - Umlauts and other character modifiers are not given. 
     *  Structure of personal names: 

    - There is no space after any initial and its following period. 
    - Blank spaces are used in a name only if properly part of the surname (e.g., J.VAN DORN), or between surname and Junior, II, or III. 
    - Abbreviations that are part of a surname, such as St. or Ste., are followed by a period and a space before the next part of the surname. 
    * Representation of corporate names: 

    - Group names used for one or all of the authors should be spelled out in full. 
    - The name of the larger group comes before the name of a subdivision, e.g., University of Somewhere Department of Chemistry. 
    * Structure of list: 

    - Line breaks between multiple lines in the authorList occur only after a comma. 
    - Personal names are not split across two lines. 
    * Special cases: 

    - Names are given in English if there is an accepted English version; otherwise in the native language, transliterated if necessary. 
    - "ET AL." may be used when all authors are not individually listed. 
    Verification/Validation/Value Authority Control 

    The verification program checks that the authorList field is correctly formatted. It does not perform any spelling checks or name verification. 

    Relationships to Other Record Types 

    The format of the names in the AUTHOR record is the same as in JRNL and REMARK 1 references. 

    Example 

             1         2         3         4         5         6         7
    1234567890123456789012345678901234567890123456789012345678901234567890
    AUTHOR    M.B.BERRY,B.MEADOR,T.BILDERBACK,P.LIANG,M.GLASER,
    AUTHOR   2 G.N.PHILLIPS JUNIOR,T.L.ST. STEVENS

    AUTHOR    C.-I.BRANDEN,C.J.BIRKETT-CLEWS,L.RIVA DI SANSAVERINO

    *
    */
	public static final String RECNAME = "AUTHOR";   //$NON-NLS-1$

	//1 -  6        Record name     "AUTHOR"
//	private static final int START_RECNAME = 1;
//	private static final int FINISH_RECNAME = 6; 
//	
	//9 - 10        Continuation    continuation   
	private static final int START_CONTINUATION = 9;
	private static final int FINISH_CONTINUATION = 10; 
   	
	//11 - 70       List           authorList    List of the author names, separated
    //by commas.
	private static final int START_AUTHORLIST = 11;
	private static final int FINISH_AUTHORLIST = 70; 	
	
	protected Integer continuation = null;
	public Integer getContinuation() { return this.continuation; }
	public void setContinuation(Integer continuation) { this.continuation = continuation; }

	protected String  authorList= ""; //$NON-NLS-1$
	public String getAuthorList() { return this.authorList; }
	public void setAuthorList(String authorList) { this.authorList = authorList; }

	public static PDBAuthor extractAuthor(String pdbAuthorRecord) {
		if(pdbAuthorRecord.startsWith(RECNAME)) {
			PDBAuthor author = new PDBAuthor();
			author.setContinuation( extractRInteger(pdbAuthorRecord, START_CONTINUATION, FINISH_CONTINUATION) );
	        author.setAuthorList( extractString(pdbAuthorRecord, START_AUTHORLIST, FINISH_AUTHORLIST) );
			return author;
		}
		return null;
	}	

}
