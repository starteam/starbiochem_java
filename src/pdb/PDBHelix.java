package pdb;

public class PDBHelix extends PDBRecord
{
	// HELIX
	//	
	// Overview
	// HELIX records are used to identify the position of helices in the molecule. Helices are named and
	// numbered. The residues where the helix begins and ends are noted, as well as the total length.
	//
	// Record Format
	// COLUMNS DATA TYPE FIELD DEFINITION
	// --------------------------------------------------------------------------
	// 1 - 6 Record name "HELIX "
	// 8 - 10 Integer serNum Serial number of the helix. This starts at 1 and increases incrementally.
	// 12 - 14 LString(3) helixID Helix identifier. In addition to a serial number, each helix is given an alphanumeric character helix identifier.
	// 16 - 18 Residue name initResName Name of the initial residue.
	// 20 Character initChainID Chain identifier for the chain containing this helix.
	// 22 - 25 Integer initSeqNum Sequence number of the initial residue.
	// 26 AChar initICode Insertion code of the initial residue.
	// 28 - 30 Residue name endResName Name of the terminal residue of the helix.
	// 32 Character endChainID Chain identifier for the chain containing this helix.
	// 34 - 37 Integer endSeqNum Sequence number of the terminal residue.
	// 38 AChar endICode Insertion code of the terminalresidue.
	// 39 - 40 Integer helixClass Helix class (see below).
	// 41 - 70 String comment Comment about this helix.
	// 72 - 76 Integer length Length of this helix.
	//
	// Details
	// * Additional HELIX records with different serial numbers and identifiers occur if more than one helix is present.
	// * The initial residue is the N-terminal residue of the helix.
	// * Helices are classified as follows:
	//
	// TYPE OF HELIX CLASS NUMBER (COLUMNS 39 - 40)
	// --------------------------------------------------------------
	// Right-handed alpha (default) 1
	// Right-handed omega 2
	// Right-handed pi 3
	// Right-handed gamma 4
	// Right-handed 310 5
	// Left-handed alpha 6
	// Left-handed omega 7
	// Left-handed gamma 8
	// 27 ribbon/helix 9
	// Polyproline 10
	//
	// Relationships to Other Record Types
	// There may be related information in the REMARKs.
	//
	// Example
	// 1 2 3 4 5 6 7
	// 1234567890123456789012345678901234567890123456789012345678901234567890123456
	// HELIX 1 HA GLY A 86 GLY A 94 1 9
	// HELIX 2 HB GLY B 86 GLY B 94 1 9

	private static final long serialVersionUID = 1L;

	public static final String RECNAME = "HELIX "; //$NON-NLS-1$

	// 1 - 6 Record name "HELIX "
	private static final int START_RECNAME = 1;
	private static final int FINISH_RECNAME = 6;

	// 8 - 10 Integer serNum
	private static final int START_SERNUM = 8;
	private static final int FINISH_SERNUM = 10;

	// 12 - 14 LString(3) helixID
	private static final int START_HELIXID = 12;
	private static final int FINISH_HELIXID = 14;

	// 16 - 18 Residue name initResName
	private static final int START_INITRESNAME = 16;
	private static final int FINISH_INITRESNAME = 18;

	// 20 Character initChainID
	private static final int START_INITCHAINID = 20;
	private static final int FINISH_INITCHAINID = 20;

	// 22 - 25 Integer initSeqNum
	private static final int START_INITSEQNUM = 22;
	private static final int FINISH_INITSEQNUM = 25;

	// 26 AChar initICode
	private static final int START_INITICODE = 26;
	private static final int FINISH_INITICODE = 26;

	// 28 - 30 Residue name endResName
	private static final int START_ENDRESNAME = 28;
	private static final int FINISH_ENDRESNAME = 30;

	// 32 Character endChainID
	private static final int START_ENDCHAINID = 32;
	private static final int FINISH_ENDCHAINID = 32;

	// 34 - 37 Integer endSeqNum
	private static final int START_ENDSEQNUM = 34;
	private static final int FINISH_ENDSEQNUM = 37;

	// 38 AChar endICode
	private static final int START_ENDICODE = 38;
	private static final int FINISH_ENDICODE = 38;

	// 39 - 40 Integer helixClass
	private static final int START_HELIXCLASS = 39;
	private static final int FINISH_HELIXCLASS = 40;

	// 41 - 70 String comment
	private static final int START_COMMENT = 41;
	private static final int FINISH_COMMENT = 70;

	// 72 - 76 Integer length number of residues in helix
	private static final int START_LENGTH = 72;
	private static final int FINISH_LENGTH = 76;

	Integer serNum = new Integer(0);

	public Integer getSerNum()
	{
		return this.serNum;
	}

	String helixID = ""; //$NON-NLS-1$

	public String getHelixID()
	{
		return this.helixID;
	}

	Integer resid = new Integer(0);

	public Integer getResid()
	{
		return this.resid;
	}

	String initResName = ""; //$NON-NLS-1$

	public String getInitResName()
	{
		return this.initResName;
	}

	String initChain = ""; //$NON-NLS-1$

	public String getInitChain()
	{
		return this.initChain;
	}

	Integer initSeqNum = new Integer(0);

	public Integer getInitSeqNum()
	{
		return this.initSeqNum;
	}

	String initICode = ""; //$NON-NLS-1$

	public String getInitICode()
	{
		return this.initICode;
	}

	String endResName = ""; //$NON-NLS-1$

	public String getEndResName()
	{
		return this.endResName;
	}

	String endChain = ""; //$NON-NLS-1$

	public String getEndChain()
	{
		return this.endChain;
	}

	Integer endSeqNum = new Integer(0);

	public Integer getEndSeqNum()
	{
		return this.endSeqNum;
	}

	String endICode = ""; //$NON-NLS-1$

	public String getEndICode()
	{
		return this.endICode;
	}

	Integer helixClass = new Integer(0);

	public Integer getHelixClass()
	{
		return this.helixClass;
	}

	String comment = ""; //$NON-NLS-1$

	public String getComment()
	{
		return this.comment;
	}

	Integer length = new Integer(0);

	public Integer getLength()
	{
		return this.length;
	}

	public static PDBHelix extractHelix(String pdbHelixRecord)
	{
		if (pdbHelixRecord.startsWith(RECNAME))
		{
			PDBHelix helix = new PDBHelix(pdbHelixRecord);
			return helix;
		}
		return null;
	}

	public PDBHelix(String pdbHelixRecord)
	{
		if (pdbHelixRecord.startsWith(RECNAME))
		{
			this.recname = extractRecname(pdbHelixRecord, START_RECNAME, FINISH_RECNAME);
			this.serNum = extractRInteger(pdbHelixRecord, START_SERNUM, FINISH_SERNUM);
			this.helixID = extractString(pdbHelixRecord, START_HELIXID, FINISH_HELIXID);

			this.initResName = extractResname(pdbHelixRecord, START_INITRESNAME, FINISH_INITRESNAME);
			this.initChain = extractString(pdbHelixRecord, START_INITCHAINID, FINISH_INITCHAINID);
			this.initSeqNum = extractRInteger(pdbHelixRecord, START_INITSEQNUM, FINISH_INITSEQNUM);
			this.initICode = extractString(pdbHelixRecord, START_INITICODE, FINISH_INITICODE);
			
			this.endResName = extractResname(pdbHelixRecord, START_ENDRESNAME, FINISH_ENDRESNAME);
			this.endChain = extractString(pdbHelixRecord, START_ENDCHAINID, FINISH_ENDCHAINID);
			this.endSeqNum = extractRInteger(pdbHelixRecord, START_ENDSEQNUM, FINISH_ENDSEQNUM);
			this.endICode = extractString(pdbHelixRecord, START_ENDICODE, FINISH_ENDICODE);
			
			this.comment = extractString(pdbHelixRecord, START_COMMENT, FINISH_COMMENT);
			this.helixClass = extractRInteger(pdbHelixRecord, START_HELIXCLASS, FINISH_HELIXCLASS);
			this.length = extractRInteger(pdbHelixRecord, START_LENGTH, FINISH_LENGTH);
			
		}
	}

	public String initResString()
	{
		return "[" + this.initResName + "]" + initSeqNum + ":" + this.initChain; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
	
	public String endResString()
	{
		return "[" + this.endResName + "]" + endSeqNum + ":" + this.endChain; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
	
	public String toString()
	{
		return this.recname + "." + this.helixID + "." + this.initChain + " " + initResString() + " ... " + endResString(); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	}
}
