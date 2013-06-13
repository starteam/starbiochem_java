package pdb;

import java.io.BufferedReader;

public class PDBCompnd extends PDBRecord{
	private static final long serialVersionUID = 1L;
	/***************************************************************************
	 * http://www.wwpdb.org/documentation/format32/sect2.html#COMPND
	 * 
COMPND

Overview

The COMPND record describes the macromolecular contents of an entry. Some cases where the entry contains a standalone drug or inhibitor, the name of the non-polymeric molecule will appear in this record. Each macromolecule found in the entry is described by a set of token: filterOptions pairs, and is referred to as a COMPND record component. Since the concept of a molecule is difficult to specify exactly, staff may exercise editorial judgment in consultation with depositors in assigning these names.

Record Format

COLUMNS       DATA TYPE       FIELD         DEFINITION 
----------------------------------------------------------------------------------
 1 -  6       Record name     "COMPND"   
 8 - 10       Continuation    continuation  Allows concatenation of multiple records.
11 - 80       Specification   compound      Description of the molecular components.
              list   

Details

    * The compound record is a Specification list. The specifications, or tokens, that may be used are listed below:

TOKEN                  VALUE DEFINITION
-------------------------------------------------------------------------
MOL_ID                 Numbers each component; also used in  SOURCE to associate
                       the information.
MOLECULE               Name of the macromolecule.
CHAIN                  Comma-separated list of chain  identifier(s). 
FRAGMENT               Specifies a domain or region of the  molecule.
SYNONYM                Comma-separated list of synonyms for  the MOLECULE.
EC                     The Enzyme Commission number associated  with the molecule.
                       If there is more than one EC number,  they are presented
                       as a comma-separated list.
ENGINEERED             Indicates that the molecule was  produced using 
                       recombinant technology or by purely  chemical synthesis.
MUTATION               Indicates if there is a mutation.
OTHER_DETAILS          Additional comments.

    * In the case of synthetic molecules, the depositor will provide the description.
    * For chimeric proteins, the protein name is comma-separated and may refer to the presence of a linker (protein_1, linker, protein_2).
    * Asterisks in nucleic acid names (in MOLECULE) are for ease of reading.
    * No specific rules apply to the ordering of the tokens, except that the occurrence of MOL_ID or FRAGMENT indicates that the subsequent tokens are related to that specific molecule or fragment of the molecule.
    * When insertion codes are given as part of the residue name, they must be given within square brackets, i.e., H57[A]N. This might occur when listing residues in FRAGMENT or OTHER_DETAILS.
    * For multi-chain molecules, e.g., the hemoglobin tetramer, a comma-separated list of CHAIN identifiers is used.

Verification/Validation/Value Authority Control

CHAIN must match the chain identifiers(s) of the molecule(s). EC numbers are also checked.

Relationships to Other Record Types

In the case of mutations, the SEQADV records will present differences from the reference molecule. REMARK records may further describe the contents of the entry. Also see verification above.

Examples

         1         2         3         4         5         6         7         8
12345678901234567890123456789012345678901234567890123456789012345678901234567890
COMPND    MOL_ID:  1;                                                            
COMPND   2 MOLECULE:  HEMOGLOBIN ALPHA CHAIN;                                    
COMPND   3 CHAIN: A,  C;                                                         
COMPND   4 SYNONYM:  DEOXYHEMOGLOBIN ALPHA CHAIN;                                
COMPND   5 ENGINEERED: YES;                                                     
COMPND   6 MUTATION:  YES;                                                       
COMPND   7 MOL_ID:  2;                                                           
COMPND   8 MOLECULE:  HEMOGLOBIN BETA CHAIN;                                     
COMPND   9 CHAIN: B,  D;                                                         
COMPND  10 SYNONYM:  DEOXYHEMOGLOBIN BETA CHAIN;                                 
COMPND  11 ENGINEERED: YES;                                                     
COMPND  12 MUTATION:  YES   
       
COMPND    MOL_ID: 1;                                                            
COMPND   2 MOLECULE:  COWPEA CHLOROTIC MOTTLE VIRUS;                             
COMPND   3 CHAIN: A,  B, C;                                                      
COMPND   4 SYNONYM:  CCMV;                                                       
COMPND   5 MOL_ID:  2;                                                           
COMPND   6 MOLECULE:  RNA (5'-(*AP*UP*AP*U)-3');                                 
COMPND   7 CHAIN: D,  F;                                                         
COMPND   8 ENGINEERED: YES;                                                     
COMPND   9 MOL_ID:  3;                                                           
COMPND  10 MOLECULE:  RNA (5'-(*AP*U)-3');                                       
COMPND  11 CHAIN: E;                                                            
COMPND  12 ENGINEERED: YES     
                                                 
COMPND    MOL_ID:  1;                                                             
COMPND   2 MOLECULE:  HEVAMINE A;                                                 
COMPND   3 CHAIN:  A;                                                            
COMPND   4 EC:  3.2.1.14, 3.2.1.17;                                               
COMPND   5 OTHER_DETAILS: PLANT ENDOCHITINASE/LYSOZYME                           

*/
	
	public static final String RECNAME = "COMPND";   //$NON-NLS-1$
	public static final String MOLECULE = "MOLECULE";   //$NON-NLS-1$

	//1 -  6        Record name     "COMPND"
	
	//8 - 10        Continuation String
	
	//12 - 22       "MOLECULE:  "
	private static final int START_MOLECULE = 12;
	private static final int FINISH_MOLECULE = START_MOLECULE + MOLECULE.length();
	
	//12 - 80       Continuation String
	
	//23 - 80        String(57)      molecule name
	private static final int START_MOLECULENAME = 22;
	private static final int FINISH_MOLECULENAME = 80;  
	
	protected String moleculeName = ""; //$NON-NLS-1$
	public String getMoleculeName() { return this.moleculeName; }
	public void setMoleculeName(String moleculeName) { this.moleculeName = moleculeName; }

	public static String extractCompnd(PDBCompnd pdbCompnd, BufferedReader br, String pdbCompndRecord)
	{
		if((null != pdbCompnd) && (null != br) && (null != pdbCompndRecord))
		{
			try
			{
				if(pdbCompndRecord.startsWith(RECNAME))
				{
					if(pdbCompndRecord.substring(START_MOLECULE - 1, FINISH_MOLECULE).startsWith(MOLECULE))
					{
						pdbCompnd.setMoleculeName( extractString(pdbCompndRecord, START_MOLECULENAME, FINISH_MOLECULENAME).trim() );
						String line = br.readLine();
						if(null != line)
						{
							while(-1 == line.indexOf(":")) //$NON-NLS-1$
							{
								pdbCompnd.setMoleculeName(pdbCompnd.getMoleculeName().trim().concat( extractString(line, START_MOLECULE, FINISH_MOLECULENAME)));
								line = br.readLine();
							}
						}
						return line;
					}
				}
				return br.readLine();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return null;
	}
	
}
