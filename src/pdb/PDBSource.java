package pdb;

public class PDBSource extends PDBRecord{
	private static final long serialVersionUID = 1L;

	/***************************************************************************
	 * http://www.wwpdb.org/documentation/format32/sect2.html#SOURCE
	 * 
SOURCE

Overview

The SOURCE record specifies the biological and/or chemical source of each biological molecule in the entry. Some cases where the entry contains a standalone drug or inhibitor, the source information of this molecule will appear in this record. Sources are described by both the common name and the scientific name, e.g., genus and species. Strain and/or cell-line for immortalized cells are given when they help to uniquely identify the biological entity studied.

Record Format

COLUMNS      DATA  TYPE     FIELD          DEFINITION                        
--------------------------------------------------------------------------------------
 1 -  6      Record name    "SOURCE"       
 8 - 10      Continuation   continuation   Allows concatenation of multiple records.
11 - 79      Specification  srcName        Identifies the source of the
             List                          macromolecule in a  token: filterOptions format.

Details

TOKEN                                VALUE  DEFINITION                        
--------------------------------------------------------------------------------------
MOL_ID                               Numbers each  molecule. Same as appears in COMPND.
SYNTHETIC                            Indicates a  chemically-synthesized source.  
FRAGMENT                             A domain or  fragment of the molecule may be 
                                     specified.                                  
ORGANISM_SCIENTIFIC                  Scientific name of the  organism.            
ORGANISM_COMMON                      Common name of the  organism. 
ORGANISM_TAXID                       NCBI Taxonomy ID number  of the organism.    
STRAIN                               Identifies the  strain.                      
VARIANT                              Identifies the  variant.                     
CELL_LINE                            The specific line of  cells used in the experiment.
ATCC                                 American Type  Culture Collection tissue     
                                     culture  number.                             
ORGAN                                Organized group of  tissues that carries on  
                                     a specialized function.                     
TISSUE                               Organized group  of cells with a common     
                                     function and  structure.                     
CELL                                 Identifies the  particular cell type.        
ORGANELLE                            Organized structure  within a cell.          
SECRETION                            Identifies the secretion, such as  saliva, urine,
                                     or venom,  from which the molecule was isolated.
CELLULAR_LOCATION                    Identifies the location  inside/outside the cell.
PLASMID                              Identifies the plasmid  containing the gene. 
GENE                                 Identifies the  gene.                        
EXPRESSION_SYSTEM                    Scientific name of the organism in  which the
                                     molecule was expressed.
EXPRESSION_SYSTEM_COMMON             Common name of the organism in  which the molecule
                                     was  expressed.
EXPRESSION_SYSTEM_TAXID              NCBI Taxonomy ID of the organism  used as the
                                     expression  system.
EXPRESSION_SYSTEM_STRAIN             Strain of the organism in which  the molecule
                                     was  expressed.                              
EXPRESSION_SYSTEM_VARIANT            Variant of the organism used as the 
                                     expression  system.
EXPRESSION_SYSTEM_CELL_LINE          The specific line of cells used as  the 
                                     expression  system.
EXPRESSION_SYSTEM_ATCC_NUMBER        Identifies the ATCC number of the  expression system.
EXPRESSION_SYSTEM_ORGAN              Specific organ which expressed  the molecule.
EXPRESSION_SYSTEM_TISSUE             Specific tissue which expressed  the molecule.
EXPRESSION_SYSTEM_CELL               Specific cell type which  expressed the molecule.
EXPRESSION_SYSTEM_ORGANELLE          Specific organelle which expressed  the molecule.
EXPRESSION_SYSTEM_CELLULAR_LOCATION  Identifies the location inside or outside 
                                     the cell  which expressed the molecule.
EXPRESSION_SYSTEM_VECTOR_TYPE        Identifies the type of vector used,  i.e., 
                                     plasmid,  virus, or cosmid.
EXPRESSION_SYSTEM_VECTOR             Identifies the vector used.
EXPRESSION_SYSTEM_PLASMID            Plasmid used in the recombinant experiment. 
EXPRESSION_SYSTEM_GENE               Name of the gene used in  recombinant experiment.
OTHER_DETAILS                        Used to present  information on the source which 
                                     is not  given elsewhere.              

    * The srcName is a list of tokens: filterOptions pairs describing each biological component of the entry.
    * As in COMPND, the order is not specified except that MOL_ID or FRAGMENT indicates subsequent specifications are related to that molecule or fragment of the molecule.
    * Only the relevant tokens need to appear in an entry.
    * Molecules prepared by purely chemical synthetic methods are described by the specification SYNTHETIC followed by "YES" or an optional filterOptions, such as NON-BIOLOGICAL SOURCE or BASED ON THE NATURAL SEQUENCE. ENGINEERED must appear in the COMPND record.
    * In the case of a chemically synthesized molecule using a biologically functional sequence (nucleic or amino acid), SOURCE reflects the biological origin of the sequence and COMPND reflects its synthetic nature by inclusion of the token ENGINEERED. The token SYNTHETIC appears in SOURCE.
    * If made from a synthetic gene, ENGINEERED appears in COMPND and the expression system is described in SOURCE (SYNTHETIC does NOT appear in SOURCE).
    * If the molecule was made using recombinant techniques, ENGINEERED appears in COMPND and the system is described in SOURCE.
    * When multiple macromolecules appear in the entry, each MOL_ID, as given in the COMPND record, must be repeated in the SOURCE record along with the source information for the corresponding molecule.
    * Hybrid molecules prepared by fusion of genes are treated as multi-molecular systems for the purpose of specifying the source. The token FRAGMENT is used to associate the source with its corresponding fragment.

      - When necessary to fully describe hybrid molecules, tokens may appear more than once 
        for a given MOL_ID.
      - All relevant token: filterOptions pairs that taken together fully describe each fragment are 
        grouped following the appropriate FRAGMENT.
      - Descriptors relative to the full system appear before the FRAGMENT (see third example 
        below).

    * ORGANISM_SCIENTIFIC provides the Latin genus and species. Virus names are listed as the scientific name.
    * Cellular origin is described by giving cellular compartment, organelle, cell, tissue, organ, or body part from which the molecule was isolated.
    * CELLULAR_LOCATION may be used to indicate where in the organism the compound was found. Examples are: extracellular, periplasmic, cytosol.
    * Entries containing molecules prepared by recombinant techniques are described as follows:

      - The expression system is described.
      - The organism and cell location given are for the source of the gene used in 
        the cloning experiment.
      - Transgenic organisms, such as mouse producing human proteins, are treated as 
        expression systems.

    * The organism and cell location given are for the source of the gene used in the cloning experiment.
    * New tokens may be added by the wwPDB.

Verification/Validation/Value Authority Control

The biological source is compared to that found in the sequence databases. The Tax ID is identified and the corresponding scientific and common names for the organism is matched to a standard taxonomy database (such as NCBI).

Relationships to Other Record Types

Each macromolecule listed in COMPND must have a corresponding source.

Examples

         1         2         3         4         5         6         7         8
12345678901234567890123456789012345678901234567890123456789012345678901234567890
SOURCE    MOL_ID: 1;
SOURCE   2 ORGANISM_SCIENTIFIC: AVIAN SARCOMA VIRUS;
SOURCE   3 ORGANISM_TAXID: 11876
SOURCE   4 STRAIN:  SCHMIDT-RUPPIN B;
SOURCE   5 EXPRESSION_SYSTEM: ESCHERICHIA COLI                                                                                                                                                  
SOURCE   6 EXPRESSION_SYSTEM_TAXID: 562
SOURCE   7 EXPRESSION_SYSTEM_PLASMID: PRC23IN

SOURCE    MOL_ID: 1;
SOURCE   2 ORGANISM_SCIENTIFIC: GALLUS GALLUS;
SOURCE   3 ORGANISM_COMMON: CHICKEN;
SOURCE   3 ORGANISM_TAXID: 9031
SOURCE   4 ORGAN: HEART;
SOURCE   5 TISSUE: MUSCLE

For a Chimera protein:

SOURCE    MOL_ID: 1;                                                            
SOURCE   2 ORGANISM_SCIENTIFIC: MUS MUSCULUS, HOMO  SAPIENS;                     
SOURCE   3 ORGANISM_COMMON: MOUSE, HUMAN;          
SOURCE   3 ORGANISM_TAXID: 10090, 9606                         
SOURCE   5 EXPRESSION_SYSTEM: ESCHERICHIA COLI;   
SOURCE   6 EXPRESSION_SYSTEM_TAXID: 344601                              
SOURCE   6 EXPRESSION_SYSTEM_STRAIN: B171;                                     
SOURCE   7 EXPRESSION_SYSTEM_VECTOR_TYPE:  PLASMID;                              
SOURCE   8 EXPRESSION_SYSTEM_PLASMID: P4XH-M13;     

*/
	
	public static final String RECNAME = "SOURCE";   //$NON-NLS-1$

	//1 -  6        Record name     "SOURCE"
//	private static final int START_RECNAME = 1;
//	private static final int FINISH_RECNAME = 6;  
	
	//8 - 10        Continuation
//	private static final int START_CONTINUE = 8;
//	private static final int FINISH_CONTINUE = 10;
	
	// 12 - 32      "ORGANISM_SCIENTIFIC: "
	private static final int START_SCIENTIFIC = 12;
	private static final int FINISH_SCIENTIFIC = 32;

	//33 - 80        String(47)      scientific name
	private static final int START_SCIENTIFICNAME = 33;
	private static final int FINISH_SCIENTIFICNAME = 80;  
	
	//12 - 28       "ORGANISM_COMMON: "
	private static final int START_COMMON = 12;
	private static final int FINISH_COMMON = 28;
	
	//29 - 80       String(51)        common name
	private static final int START_COMMONNAME = 29;
	private static final int FINISH_COMMONNAME = 80;
	
	protected String scientificName = ""; //$NON-NLS-1$
	public String getScientificName() { return this.scientificName; }
	public void setScientificName(String scientificName) { this.scientificName = scientificName; }

	protected String commonName = ""; //$NON-NLS-1$
	public String getCommonName() { return this.commonName; }
	public void setCommonName(String commonName) { this.commonName = commonName; }

	public static PDBSource extractSource(String pdbSourceRecord) {
		if(pdbSourceRecord.startsWith(RECNAME)) {
			PDBSource source = new PDBSource();
			String scientific = pdbSourceRecord.substring(START_SCIENTIFIC - 1,  FINISH_SCIENTIFIC);
			String common = pdbSourceRecord.substring(START_COMMON - 1,  FINISH_COMMON);
			if(scientific.equals("ORGANISM_SCIENTIFIC: ")) //$NON-NLS-1$
			{
				int index = pdbSourceRecord.lastIndexOf(";"); //$NON-NLS-1$
				index = (-1 != index) ? index : FINISH_SCIENTIFICNAME;
				String scientificName = extractString(pdbSourceRecord, START_SCIENTIFICNAME, index);
				source.setScientificName(scientificName);
			}
			else if(common.equals("ORGANISM_COMMON: ")) //$NON-NLS-1$
			{
				int index = pdbSourceRecord.lastIndexOf(";"); //$NON-NLS-1$
				index = (-1 != index) ? index : FINISH_COMMONNAME;
				String commonName = extractString(pdbSourceRecord, START_COMMONNAME, index);
				source.setCommonName(commonName);
			}
			return source;
		}
		return null;
	}
	
}
