package molecule.interfaces;

import pdb.PDBAuthor;
import pdb.PDBCompnd;
import pdb.PDBHeader;
import pdb.PDBHelix;
import pdb.PDBJournal;
import pdb.PDBRemark;
import pdb.PDBSheetStrand;
import pdb.PDBSource;
import pdb.PDBTitle;
import java.io.Serializable;

public interface Molecule extends Serializable
{
	String getUrl();
	String[] getNucleicArray();
	String[] getHeteroNotWaterNotNucleicArray();
	String[] getProteinArray();
	String[] getWaterArray();
	String[] getStructureProteinArray();
	boolean hasQuaternarySSBonds();
	boolean hasTertiarySSBonds();
	Bond[] getQuaternaryHBonds();
	Bond[] getTertiaryHBonds();
	Bond[] getSecondaryHBonds();
	PDBHelix[] getHelixArray();
	PDBSheetStrand[] getSheetStrandArray();
	PDBAuthor[] getAuthorArray();
	PDBCompnd[] getCompndArray();
	PDBHeader[] getHeaderArray();
	PDBRemark[] getRemarkArray();
	PDBJournal[] getJournalArray();
	PDBSource[] getSourceArray();
	PDBTitle[] getTitleArray();
}
