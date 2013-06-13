package pdb;

public class PDBTitle extends PDBRecord{
	private static final long serialVersionUID = 1L;

	/***************************************************************************
	 * http://www.rcsb.org/pdb/docs/format/pdbguide2.2/guide2.2_frame.html
	 TITLE
     Overview 

     The TITLE record contains a title for the experiment or analysis that is represented in the entry. It should identify an entry in the PDB in the same way that a title identifies a paper. 

     Record Format 

     COLUMNS        DATA TYPE       FIELD          DEFINITION
     ----------------------------------------------------------------------------------
     1 -  6        Record name     "TITLE "

     9 - 10        Continuation    continuation   Allows concatenation of multiple
                                                  records.

     11 - 70        String          title          Title of the experiment.


     Details 

     * The title of the entry is free text and should describe the contents of the entry and any procedures or conditions that distinguish this entry from similar entries. It presents an opportunity for the depositor to emphasize the underlying purpose of this particular experiment. 

     * Some items that may be included in TITLE are: 

     - Experiment type. 
     - Description of the mutation. 
     - The fact that only alpha carbon coordinates have been provided in the entry. 
       Verification/Validation/Value Authority Control 

     This record is free text so no verification of format is required. The title is supplied by the depositor, but PDB staff may exercise editorial judgment in consultation with depositors in assigning the title. 

     Relationships to Other Record Types 

     COMPND, SOURCE, EXPDTA, and REMARKs provide information that may also be found in TITLE. You may think of the title as describing the experiment, and the compound record as describing the molecule(s). 

     Example 

              1         2         3         4         5         6         7
     1234567890123456789012345678901234567890123456789012345678901234567890
     TITLE     RHIZOPUSPEPSIN COMPLEXED WITH REDUCED PEPTIDE INHIBITOR

     TITLE     BETA-GLUCOSYLTRANSFERASE, ALPHA CARBON COORDINATES ONLY

     TITLE     NMR STUDY OF OXIDIZED THIOREDOXIN MUTANT (C62A,C69A,C73A)
     TITLE    2 MINIMIZED AVERAGE STRUCTURE 
	 * */

	public static final String RECNAME = "TITLE";   //$NON-NLS-1$

	//1 -  6        Record name     "TITLE"
//	private static final int START_RECNAME = 1;
//	private static final int FINISH_RECNAME = 6; 
	
	//9 - 10        Continuation    continuation   
	private static final int START_CONTINUATION = 9;
	private static final int FINISH_CONTINUATION = 10; 
   	
	//11 - 70        String          title          Title of the experiment.
	private static final int START_TITLE = 11;
	private static final int FINISH_TITLE = 70; 	
	
	protected Integer continuation = null;
	public Integer getContinuation() { return this.continuation; }
	public void setContinuation(Integer continuation) { this.continuation = continuation; }

	protected String title = ""; //$NON-NLS-1$
	public String getTitle() { return this.title; }
	public void setTitle(String title) { this.title = title; }

	public static PDBTitle extractTitle(String pdbTitleRecord) {
		if(pdbTitleRecord.startsWith(RECNAME)) {
			PDBTitle title = new PDBTitle();
			title.setContinuation( extractRInteger(pdbTitleRecord, START_CONTINUATION, FINISH_CONTINUATION) );
	        title.setTitle( extractString(pdbTitleRecord, START_TITLE, FINISH_TITLE) );
			return title;
		}
		return null;
	}
}
