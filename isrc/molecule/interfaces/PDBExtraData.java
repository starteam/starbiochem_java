package molecule.interfaces;

import java.io.Serializable;

import pdb.PDBAuthor;
import pdb.PDBCompnd;
import pdb.PDBHeader;
import pdb.PDBRemark;
import pdb.PDBHelix;
import pdb.PDBJournal;
import pdb.PDBSheetStrand;
import pdb.PDBSource;
import pdb.PDBTitle;

public interface PDBExtraData extends Serializable
{
	PDBHelix[] getHelixArray();
	PDBSheetStrand[] getSheetStrandArray();
	PDBAuthor[] getAuthorArray();
	PDBCompnd[] getCompndArray();
	PDBHeader[] getHeaderArray();
	PDBRemark[] getRemarkArray();
	PDBSource[] getSourceArray();
	PDBJournal[] getJournalArray();
	PDBTitle[] getTitleArray();	
}
