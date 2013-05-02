package pdb;

import java.util.GregorianCalendar;

public class PDBHeader extends PDBRecord{
	private static final long serialVersionUID = 1L;

	/***************************************************************************
	 * http://www.rcsb.org/pdb/docs/format/pdbguide2.2/guide2.2_frame.html
	 * 
     Overview 

     The HEADER record uniquely identifies a PDB entry through the idCode field. This record also provides a classification for the entry. Finally, it contains the date the coordinates were deposited at the PDB. 

     Record Format 

     COLUMNS        DATA TYPE       FIELD           DEFINITION
     ----------------------------------------------------------------------------------
     1 -  6        Record name     "HEADER"

     11 - 50        String(40)      classification  Classifies the molecule(s)

     51 - 59        Date            depDate         Deposition date.  This is the date
                                                    the coordinates were received by
                                                    the PDB

     63 - 66        IDcode          idCode          This identifier is unique within PDB

	 64 - 80		Notes			notes			This is content added to a modified file
	 
     Details 

     * The classification string is left-justified and exactly matches one of a collection of strings. See the class list available from the WWW site. In the case of macromolecular complexes, the classification field must present a class for each macromolecule present. Due to the limited length of the classification field, strings must sometimes be abbreviated. In these cases, the full terms are given in KEYWDS. 

     * Classification may be based on function, metabolic role, molecule type, cellular location, etc. In the case of a molecule having a dual function, both may be presented here. 

       Verification/Validation/Value Authority Control 

       The verification program checks that the deposition date is a legitimate date and that the ID code is well-formed. PDB coordinate entry ID codes do not begin with 0, as this is used to identify the NOC files which are bibliographic only, not structural entries. The status and deposition date of an entry are checked against the PDB SYBASE tables, which provide a definitive list of existing ID codes. 

       Relationships to Other Record Types 

       The classification found in HEADER also appears in KEYWDS, unabbreviated and in no strict order. 

       Example 

                1         2         3         4         5         6         7
       1234567890123456789012345678901234567890123456789012345678901234567890
       HEADER    MUSCLE PROTEIN                          02-JUN-93   1MYS

       HEADER    HYDROLASE (CARBOXYLIC ESTER)            08-APR-93   2PHI

       HEADER    COMPLEX (LECTIN/TRANSFERRIN)            07-JAN-94   1LGB
       */
	
	public static final String RECNAME = "HEADER ";   //$NON-NLS-1$

	//1 -  6        Record name     "HEADER"
//	private static final int START_RECNAME = 1;
//	private static final int FINISH_RECNAME = 6;  
	
	//11 - 50        String(40)      classification  Classifies the molecule(s)
	private static final int START_CLASSIFICATION = 11;
	private static final int FINISH_CLASSIFICATION = 50;  
	
	//51 - 59        Date            depDate         Deposition date.	
	private static final int START_DATE = 51;
	private static final int FINISH_DATE = 59;  

	//63 - 66        IDcode          idCode          This identifier is unique within PDB
	private static final int START_IDCODE = 63;
	private static final int FINISH_IDCODE = 66; 
	
	//69 - 80		Notes			notes			This is content added to a modified file
	private static final int START_NOTES = 69;
	private static final int FINISH_NOTES = 80;

	protected String classification = ""; //$NON-NLS-1$
	public String getClassification() { return this.classification; }
	public void setClassification(String classification) { this.classification = classification; }

	protected GregorianCalendar depDate = null;
	public GregorianCalendar getDepDate() { return this.depDate; }
	public void setDepDate(GregorianCalendar depDate) { this.depDate = depDate; }
	
	protected String iDcode = ""; //$NON-NLS-1$
	public String getIdCode() { return this.iDcode; }
	public void setIdCode(String iDcode) { this.iDcode = iDcode; }
	
	protected String notes = ""; //$NON-NLS-1$
	public String getNotes(){ return this.notes;}
	public void setNotes(String notes){this.notes = notes;}
	
	public static PDBHeader extractHeader(String pdbHeaderRecord) {
		if(pdbHeaderRecord.startsWith(RECNAME)) {
			PDBHeader header = new PDBHeader();
			header.setClassification( extractString(pdbHeaderRecord, START_CLASSIFICATION, FINISH_CLASSIFICATION) );
			header.setDepDate( extractDate(pdbHeaderRecord, START_DATE, FINISH_DATE) );
	        header.setIdCode( extractString(pdbHeaderRecord, START_IDCODE, FINISH_IDCODE) );
	        header.setNotes( extractString(pdbHeaderRecord, START_NOTES, FINISH_NOTES) );
			return header;
		}
		return null;
	}

}
