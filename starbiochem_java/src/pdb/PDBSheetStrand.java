package pdb;

public class PDBSheetStrand extends PDBRecord
{
	// SHEET
	//
	// Overview
	// SHEET records are used to identify the position of sheets in the molecule. Sheets are both named and numbered.
	// The residues where the sheet begins and ends are noted.
	//
	// Record Format
	// COLUMNS DATA TYPE FIELD DEFINITION
	// ----------------------------------------------------------------------------------
	// 1 - 6 Record name "SHEET "
	// 8 - 10 Integer strand Strand number which starts at 1 for each strand within a sheet and increases by one.
	// 12 - 14 LString(3) sheetID Sheet identifier.
	// 15 - 16 Integer numStrands Number of strands in sheet.
	// 18 - 20 Residue name initResName Residue name of initial residue.
	// 22 Character initChainID Chain identifier of initial residue in strand.
	// 23 - 26 Integer initSeqNum Sequence number of initial residue in strand.
	// 27 AChar initICode Insertion code of initial residue in strand.
	// 29 - 31 Residue name endResName Residue name of terminal residue.
	// 33 Character endChainID Chain identifier of terminal residue.
	// 34 - 37 Integer endSeqNum Sequence number of terminal residue.
	// 38 AChar endICode Insertion code of terminal residue.
	// 39 - 40 Integer sense Sense of strand with respect to previous strand in the sheet. 0 if first strand, 1 if parallel, -1 if anti-parallel.
	// 42 - 45 Atom curAtom Registration. Atom name in current strand.
	// 46 - 48 Residue name curResName Registration. Residue name in current strand.
	// 50 Character curChainId Registration. Chain identifier in current strand.
	// 51 - 54 Integer curResSeq Registration. Residue sequence number in current strand.
	// 55 AChar curICode Registration. Insertion code in current strand.
	// 57 - 60 Atom prevAtom Registration. Atom name in previous strand.
	// 61 - 63 Residue name prevResName Registration. Residue name in previous strand.
	// 65 Character prevChainId Registration. Chain identifier in previous strand.
	// 66 - 69 Integer prevResSeq Registration. Residue sequence number in previous strand.
	// 70 AChar prevICode Registration. Insertion code in previous strand.
	//	
	// Details
	// * The initial residue for a strand is its N-terminus. Strand registration information is provided in columns 39 -
	// 70. Strands are listed starting with one edge of the sheet and continuing to the spatially adjacent strand.
	// * The sense in columns 39 - 40 indicates whether strand n is parallel (sense = 1) or anti-parallel (sense = -1) to
	// strand n-1. Sense is equal to zero (0) for the first strand of a sheet.
	// * The registration (columns 42 - 70) of strand n to strand n-1 may be specified by one hydrogen bond between
	// each such pair of strands. This is done by providing the hydrogen bonding between the current and previous
	// strands. No registration information should be provided for the first strand.
	// * For structures which form a closed sheet (beta-barrel), the first strand is repeated as the last strand. An
	// explanatory remark is included in the REMARK section.
	// * Split strands, or strands with two or more runs of residues from discontinuous parts of the amino acid
	// sequence, are explicitly listed. Provide a description to be included in the REMARK section.
	// Verification/Validation/Value Authority Control
	// SHEET records are now being generated automatically by PDB using the Kabsch and Sander algorithm [Kabsch
	// and Sander, Biopolymers 22: 2577-2637 (1983)], although they may be provided by the depositor instead. PDB
	// verifies that named residues exist in the ATOM records.
	// Relationships to Other Record Types
	// If the entry contains bifurcated sheets or beta-barrels, the relevant REMARK records must be provided. See the
	// REMARK section for details.
	//	
	// Example
	// 1 2 3 4 5 6 7
	// 1234567890123456789012345678901234567890123456789012345678901234567890
	// SHEET 1 A 5 THR A 107 ARG A 110 0
	// SHEET 2 A 5 ILE A 96 THR A 99 -1 N LYS A 98 O THR A 107
	// SHEET 3 A 5 ARG A 87 SER A 91 -1 N LEU A 89 O TYR A 97
	// SHEET 4 A 5 TRP A 71 ASP A 75 -1 N ALA A 74 O ILE A 88
	// SHEET 5 A 5 GLY A 52 PHE A 56 -1 N PHE A 56 O TRP A 71
	// SHEET 1 B 5 THR B 107 ARG B 110 0
	// SHEET 2 B 5 ILE B 96 THR B 99 -1 N LYS B 98 O THR B 107
	// SHEET 3 B 5 ARG B 87 SER B 91 -1 N LEU B 89 O TYR B 97
	// SHEET 4 B 5 TRP B 71 ASP B 75 -1 N ALA B 74 O ILE B 88
	// SHEET 5 B 5 GLY B 52 ILE B 55 -1 N ASP B 54 O GLU B 73
	//
	// The sheet presented as BS1 below is an eight-stranded beta-barrel. This is represented by a nine-stranded sheet
	// in which the first and last strands are identical.
	// SHEET 1 BS1 9 VAL 13 ILE 17 0
	// SHEET 2 BS1 9 ALA 70 ILE 73 1 O TRP 72 N ILE 17
	// SHEET 3 BS1 9 LYS 127 PHE 132 1 O ILE 129 N ILE 73
	// SHEET 4 BS1 9 GLY 221 ASP 225 1 O GLY 221 N ILE 130
	// SHEET 5 BS1 9 VAL 248 GLU 253 1 O PHE 249 N ILE 222
	// SHEET 6 BS1 9 LEU 276 ASP 278 1 N LEU 277 O GLY 252
	// SHEET 7 BS1 9 TYR 310 THR 318 1 O VAL 317 N ASP 278
	// SHEET 8 BS1 9 VAL 351 TYR 356 1 O VAL 351 N THR 318
	// SHEET 9 BS1 9 VAL 13 ILE 17 1 N VAL 14 O PRO 352
	//
	// The sheet structure of this example is bifurcated. In order to represent this feature, two sheets are defined.
	// Strands 2 and 3 of BS7 and BS8 are identical.
	// SHEET 1 BS7 3 HIS 662 THR 665 0
	// SHEET 2 BS7 3 LYS 639 LYS 648 -1 N PHE 643 O HIS 662
	// SHEET 3 BS7 3 ASN 596 VAL 600 -1 N TYR 598 O ILE 646
	// SHEET 1 BS8 3 ASN 653 TRP 656 0
	// SHEET 2 BS8 3 LYS 639 LYS 648 -1 N LYS 647 O THR 655
	// SHEET 3 BS8 3 ASN 596 VAL 600 -1 N TYR 598 O ILE 646

	private static final long serialVersionUID = 1L;

	public static final String RECNAME = "SHEET "; //$NON-NLS-1$

	// 1 - 6 Record name "SHEET "
	private static final int START_RECNAME = 1;
	private static final int FINISH_RECNAME = 6;

	// 8 - 10 Integer strand Strand number which starts at 1 for each strand within a sheet and increases by one.
	private static final int START_STRAND = 8;
	private static final int FINISH_STRAND = 10;

	// 12 - 14 LString(3) sheetID Sheet identifier.
	private static final int START_SHEETID = 12;
	private static final int FINISH_SHEETID = 14;

	// 15 - 16 Integer numStrands Number of strands in sheet.
	private static final int START_NUMSTRANDS = 15;
	private static final int FINISH_NUMSTRANDS = 16;

	// 18 - 20 Residue name initResName Residue name of initial residue.
	private static final int START_INITRESNAME = 18;
	private static final int FINISH_INITRESNAME = 20;

	// 22 Character initChainID Chain identifier of initial residue in strand.
	private static final int START_INITCHAINID = 22;
	private static final int FINISH_INITCHAINID = 22;

	// 23 - 26 Integer initSeqNum Sequence number of initial residue in strand.
	private static final int START_INITSEQNUM = 23;
	private static final int FINISH_INITSEQNUM = 26;

	// 27 AChar initICode Insertion code of initial residue in strand.
	private static final int START_INITICODE = 27;
	private static final int FINISH_INITICODE = 27;

	// 29 - 31 Residue name endResName Residue name of terminal residue.
	private static final int START_ENDRESNAME = 29;
	private static final int FINISH_ENDRESNAME = 31;

	// 33 Character endChainID Chain identifier of terminal residue.
	private static final int START_ENDCHAINID = 33;
	private static final int FINISH_ENDCHAINID = 33;

	// 34 - 37 Integer endSeqNum Sequence number of terminal residue.
	private static final int START_ENDSEQNUM = 34;
	private static final int FINISH_ENDSEQNUM = 37;

	// 38 AChar endICode Insertion code of terminal residue.
	private static final int START_ENDICODE = 38;
	private static final int FINISH_ENDICODE = 38;

	// 39 - 40 Integer sense Sense of strand with respect to previous strand in the sheet. 0 if first strand, 1 if parallel, -1 if anti-parallel.
	private static final int START_SENSE = 39;
	private static final int FINISH_SENSE = 40;

	// 42 - 45 Atom curAtom Registration. Atom name in current strand.
	private static final int START_CURATOM = 42;
	private static final int FINISH_CURATOM = 45;

	// 46 - 48 Residue name curResName Registration. Residue name in current strand.
	private static final int START_CURRESNAME = 46;
	private static final int FINISH_CURRESNAME = 48;

	// 50 Character curChainId Registration. Chain identifier in current strand.
	private static final int START_CURCHAINID = 50;
	private static final int FINISH_CURCHAINID = 50;

	// 51 - 54 Integer curResSeq Registration. Residue sequence number in current strand.
	private static final int START_CURRESSEQ = 51;
	private static final int FINISH_CURRESSEQ = 54;

	// 55 AChar curICode Registration. Insertion code in current strand.
	private static final int START_CURICODE = 55;
	private static final int FINISH_CURICODE = 55;

	// 57 - 60 Atom prevAtom Registration. Atom name in previous strand.
	private static final int START_PREVATOM = 57;
	private static final int FINISH_PREVATOM = 60;

	// 61 - 63 Residue name prevResName Registration. Residue name in previous strand.
	private static final int START_PREVRESNAME = 61;
	private static final int FINISH_PREVRESNAME = 63;

	// 65 Character prevChainId Registration. Chain identifier in previous strand.
	private static final int START_PREVCHAINID = 65;
	private static final int FINISH_PREVCHAINID = 65;

	// 66 - 69 Integer prevResSeq Registration. Residue sequence number in previous strand.
	private static final int START_PREVRESSEQ = 66;
	private static final int FINISH_PREVRESSEQ = 69;

	// 70 AChar prevICode Registration. Insertion code in previous strand.
	private static final int START_PREVICODE = 70;
	private static final int FINISH_PREVICODE = 70;

	protected String initChain = ""; //$NON-NLS-1$

	public String getInitChain()
	{
		return this.initChain;
	}

	protected String initICode = ""; //$NON-NLS-1$

	public String getInitICode()
	{
		return this.initICode;
	}

	protected String initResname = ""; //$NON-NLS-1$

	public String getInitResname()
	{
		return this.initResname;
	}

	protected int initSeqNum = 0;

	public int getInitSeqNum()
	{
		return this.initSeqNum;
	}

	protected String prevChain = ""; //$NON-NLS-1$

	public String getPrevChain()
	{
		return this.prevChain;
	}

	protected String prevICode = ""; //$NON-NLS-1$

	public String getPrevICode()
	{
		return this.prevICode;
	}

	protected String prevResname = ""; //$NON-NLS-1$

	public String getPrevResname()
	{
		return this.prevResname;
	}

	protected int prevResSeq = 0;

	public int getPrevResSeq()
	{
		return this.prevResSeq;
	}

	protected String curChain = ""; //$NON-NLS-1$

	public String getCurChain()
	{
		return this.curChain;
	}

	protected String curICode = ""; //$NON-NLS-1$

	public String getCurICode()
	{
		return this.curICode;
	}

	protected String curResname = ""; //$NON-NLS-1$

	public String getCurResname()
	{
		return this.curResname;
	}

	protected int curResSeq = -1;

	public int getCurResSeq()
	{
		return this.curResSeq;
	}

	protected String endChain = ""; //$NON-NLS-1$

	public String getEndChain()
	{
		return this.endChain;
	}

	protected String endICode = ""; //$NON-NLS-1$

	public String getEndICode()
	{
		return this.endICode;
	}

	protected String endResname = ""; //$NON-NLS-1$

	public String getEndResname()
	{
		return this.endResname;
	}

	protected int endSeqNum = -1;

	public int getEndSeqNum()
	{
		return this.endSeqNum;
	}

	protected String prevAtom = ""; //$NON-NLS-1$

	public String getPrevAtom()
	{
		return this.prevAtom;
	}

	protected String curAtom = ""; //$NON-NLS-1$

	public String getCurAtom()
	{
		return this.curAtom;
	}

	protected int strand = -1;

	public int getStrand()
	{
		return this.strand;
	}

	protected String sheetID = ""; //$NON-NLS-1$

	public String getSheetID()
	{
		return this.sheetID;
	}

	protected int sense = -1;

	public int getSense()
	{
		return this.sense;
	}

	protected int numStrands = -1;

	public int getNumStrands()
	{
		return this.numStrands;
	}

	protected int resid = -1;

	public int getResid()
	{
		return this.resid;
	}

	public static PDBSheetStrand extractSheetStrand(String pdbSheetRecord)
	{
		if (pdbSheetRecord.startsWith(RECNAME))
		{
			PDBSheetStrand sheetStrand = new PDBSheetStrand(pdbSheetRecord);
			return sheetStrand;
		}
		return null;
	}

	public PDBSheetStrand(String pdbSheetRecord)
	{
		if (pdbSheetRecord.startsWith(RECNAME))
		{
			this.recname = extractRecname(pdbSheetRecord, START_RECNAME, FINISH_RECNAME);
			this.sheetID = extractString(pdbSheetRecord, START_SHEETID, FINISH_SHEETID);
			if(null == this.sheetID)
			{
				this.sheetID = "UNK"; //$NON-NLS-1$
			}
			this.initChain = extractString(pdbSheetRecord, START_INITCHAINID, FINISH_INITCHAINID);
			this.initICode = extractString(pdbSheetRecord, START_INITICODE, FINISH_INITICODE);
			this.initResname = extractResname(pdbSheetRecord, START_INITRESNAME, FINISH_INITRESNAME);
			this.initSeqNum = extractRInteger(pdbSheetRecord, START_INITSEQNUM, FINISH_INITSEQNUM);

			this.prevChain = extractString(pdbSheetRecord, START_PREVCHAINID, FINISH_PREVCHAINID);
			this.prevICode = extractString(pdbSheetRecord, START_PREVICODE, FINISH_PREVICODE);
			this.prevResname = extractResname(pdbSheetRecord, START_PREVRESNAME, FINISH_PREVRESNAME);
			this.prevResSeq = extractRInteger(pdbSheetRecord, START_PREVRESSEQ, FINISH_PREVRESSEQ);

			this.curChain = extractString(pdbSheetRecord, START_CURCHAINID, FINISH_CURCHAINID);
			this.curICode = extractString(pdbSheetRecord, START_CURICODE, FINISH_CURICODE);
			this.curResname = extractResname(pdbSheetRecord, START_CURRESNAME, FINISH_CURRESNAME);
			this.curResSeq = extractRInteger(pdbSheetRecord, START_CURRESSEQ, FINISH_CURRESSEQ);

			this.endChain = extractString(pdbSheetRecord, START_ENDCHAINID, FINISH_ENDCHAINID);
			this.endICode = extractString(pdbSheetRecord, START_ENDICODE, FINISH_ENDICODE);
			this.endResname = extractResname(pdbSheetRecord, START_ENDRESNAME, FINISH_ENDRESNAME);
			this.endSeqNum = extractRInteger(pdbSheetRecord, START_ENDSEQNUM, FINISH_ENDSEQNUM);

			this.prevAtom = extractAtom(pdbSheetRecord, START_PREVATOM, FINISH_PREVATOM);
			this.curAtom = extractAtom(pdbSheetRecord, START_CURATOM, FINISH_CURATOM);

			this.strand = extractRInteger(pdbSheetRecord, START_STRAND, FINISH_STRAND);
			this.sense = extractRInteger(pdbSheetRecord, START_SENSE, FINISH_SENSE);

			this.numStrands = extractRInteger(pdbSheetRecord, START_NUMSTRANDS, FINISH_NUMSTRANDS);

		}
	}

	public String initResString()
	{
		return "[" + this.initResname + "]" + initSeqNum + ":" + this.initChain; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
	
	public String endResString()
	{
		return "[" + this.endResname + "]" + endSeqNum + ":" + this.endChain; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
	
	public String toString()
	{
		return this.recname + "STRAND." + this.sheetID + "." + this.initChain + "." + this.strand + " " + initResString() + " ... " + endResString(); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$

	}
	
}
