package pdb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

public class PDBRecord implements Serializable
{

	// Appendix 6: Field Formats (This information is repeated from the Introduction.) Each
	// record type is presented in a table which contains the division of the records into fields by column number, defined data type, field name or a quoted
	// string which must appear in the field, and field definition. Any column not specified must be left blank. Each field contains an identified data type
	// which can be validated by a program.
	//	 
	// These are:
	// DATA TYPE DESCRIPTION
	// ---------------------------------------------------------------------------------
	// * AChar An alphabetic character (A-Z, a-z).
	// * Atom Atom name which follow the naming rules in Appendix 3.
	// * Character Any non-control character in the ASCII character set or a space.
	// * Continuation A two-character field that is either blank (for the first record of a set) or contains a two digit number
	// right-justified and blank-filled which counts continuation records starting with 2. The continuation number must be followed by a blank.
	// * Date A 9 character string in the form dd-mmm-yy where DD is the day of the month, zero-filled on the left (e.g., 04); MMM is the common English
	// 3-letter abbreviation of the month; and YY is a year in the 20th century. This must represent a valid date.
	// * IDcode A PDB identification code which consists of 4 characters, the first of which is a digit in the range 0 - 9; the remaining 3 are alpha-numeric,
	// and letters are upper case only. Entries with a 0 as the first character do not contain coordinate data.
	// * Integer Right-justified blank-filled integer filterOptions. Token A sequence of non-space characters followed by a colon and a space.
	// * List A String that is composed of text separated with commas.
	// * LString A literal string of characters. All spacing is significant and must be preserved.
	// * LString(n) An LString with exactly n characters.
	// * Real(n,m) Real (floating point) number in the FORTRAN format Fn.m.
	// * Record name The name of the record: 6 characters, left-justified and blank-filled.
	// * Residue name One of the standard amino acid or nucleic acids, as listed below, or the non-standard group designation as defined in the HET dictionary.
	// * Field is right-justified.
	// * SList A String that is composed of text separated with semi-colons.
	// * Specification A String composed of a token and its associated filterOptions separated by a colon.
	// * Specification A sequence of Specifications, separated by semi-colons.
	// * list String A sequence of characters. These characters may have arbitrary spacing, but should be interpreted as directed below.
	// * String(n) A String with exactly n characters. SymOP An integer field of from 4 to 6 digits, right-justified, of the form nnnMMM where nnn is the
	// symmetry operator number and MMM is the translation vector. See details in Appendix 1. To interpret a String, concatenate the contents of all continued fields
	// together, collapse all sequences of multiple blanks to a single blank, and remove any leading and trailing blanks. This permits very long strings to be
	// properly reconstructed.

	private static final long serialVersionUID = 1L;

	protected String recname = ""; //$NON-NLS-1$

	public String getRecname()
	{
		return this.recname;
	}

	public static final String SPACES = "                    "; // TWENTY spaces //$NON-NLS-1$

	// AChar An alphabetic character (A-Z, a-z, space).
	static char[] AChars = { ' ', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

	protected static String extractAChar(String pdbRecord, int start, int finish)
	{
		char c = pdbRecord.substring(start - 1, finish).charAt(0);
		if (Arrays.binarySearch(AChars, c) >= 0)
		{
			return String.valueOf(c);
		}
		return null;
	}

	// Atom Atom name which follow the naming rules in Appendix 3.
	/*
	 * Appendix 3: Atom Names Amino Acids The following rules are used in assigning atom names. Greek letter remoteness codes are transliterated as follows:
	 * alpha = A, beta = B, gamma = G, delta = D, epsilon = E, zeta = Z, eta = H, etc. Atoms for which some ambiguity exists in the crystallographic results are
	 * designated A. This usually applies only to the terminal atoms of asparagine and glutamine and to the ring atoms of histidine. The extra oxygen atom of
	 * the carboxy terminal amino acid is designated OXT. Six characters (columns) are reserved for atom names, assigned as follows. COLUMN VALUE
	 * ----------------------------------------------------------------------- 13 - 14 Chemical symbol - right justified, except for hydrogen atoms 15
	 * Remoteness indicator (alphabetic) 16 Branch designator (numeric) 77 - 78 Element symbol, right-justified Columns 73 - 76 identify specific segments of
	 * the molecule. The segment may consist of a complete chain or a portion of a chain. The importance of this new field can be appreciated if one considers
	 * an antibody structure having two molecules in the asymmetric unit. Since each chain must have a unique chain identifier, the two heavy all and two
	 * light all cannot currently be labeled to indicate their nature. Segment id's of CH, VH1, VH2, VH3, CL, and VL would clearly identify regions of the
	 * all and the relationship between them. Users of X-PLOR will be familiar with SEGID as used in the refinement application of X-PLOR. See the ATOM
	 * record for more details on atom naming. Nucleic Acids Atom names employed for polynucleotides generally follow the precedent set for mononucleotides. The
	 * following points should be noted. The asterisk (*) is used in place of the prime character (') for naming atoms of the sugar group. The prime was avoided
	 * historically because of non-uniformity of its external representation. The ring oxygen of the ribose is denoted O4 rather than O1. The extra oxygen atom
	 * at the free 5' and 3' termini are designated O5T and O3T, respectively.
	 */
	protected static String extractAtom(String pdbRecord, int start, int finish)
	{
		// TODO: check the record for the above definition and throw exception if check fails
		return pdbRecord.substring(start - 1, finish).trim();
	}

	// Continuation A two-character field that is either blank (for the first record of a set)
	// or contains a two digit number right-justified and blank-filled which counts
	// continuation records starting with 2. The continuation number must be followed by a blank.
	protected static String extractContinuation(String pdbRecord, int start, int finish)
	{
		// TODO: check the record for the above definition and throw exception if check fails
		return pdbRecord.substring(start - 1, finish).trim();
	}

	// Date A 9 character string in the form dd-mmm-yy where DD is the day of the month,
	// zero-filled on the left (e.g., 04); MMM is the common English 3-letter
	// abbreviation of the month; and YY is a year in the 20th century.
	// This must represent a valid date.
	private static final String[] MONTHS = { "-JAN-", "-FEB-", "-MAR-", "-APR-", "-MAY-", "-JUN-", "-JUL-", "-AUG-", "-SEP-", "-OCT-", "-NOV-", "-DEC-" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$ //$NON-NLS-12$

	protected static GregorianCalendar extractDate(String pdbRecord, int start, int finish)
	{
		// TODO: check the record for the above definition and throw exception if check fails
		try
		{
			String s = pdbRecord.substring(start - 1, finish);
			int date = Integer.parseInt(s.substring(0, 2));
			int year = Integer.parseInt(s.substring(7, 9));
			String monthStr = s.substring(2, 7);
			int month = 0;
			for (int i = 0; MONTHS.length != i; i++)
			{
				if (monthStr.equals(MONTHS[i]))
				{
					month = i + 1;
					break;
				}
			}
			GregorianCalendar gc = new GregorianCalendar(year, month - 1, date);
			return gc;
		}
		catch (Throwable ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

	// IDcode A PDB identification code which consists of 4 characters, the first of which
	// is a digit in the range 0 - 9; the remaining 3 are alpha-numeric, and letters
	// are upper case only. Entries with a 0 as the first character do not contain
	// coordinate data.
	protected static String extractIDcode(String pdbRecord, int start, int finish)
	{
		// TODO: check the record for the above definition and throw exception if check fails
		return pdbRecord.substring(start - 1, finish).trim();
	}

	// Integer Right-justified blank-filled integer filterOptions.
	protected static int extractRInteger(String pdbRecord, int start, int finish)
	{
		String sub = pdbRecord.substring(start - 1, finish).trim();
		if (sub.length() == 0)
		{
			return 0;
		}
		else
		{
			return Integer.valueOf(sub).intValue();
		}
	}

	// Token A sequence of non-space characters followed by a colon and a space.
	protected static String extractToken(String pdbRecord, int start, int finish)
	{
		// TODO: check the record for the above definition and throw exception if check fails
		return pdbRecord.substring(start - 1, finish).trim();
	}

	// List A String that is composed of text separated with commas.
	protected static ArrayList<String> extractList(String pdbRecord, int start, int finish)
	{
		// TODO: check the record for the above definition and throw exception if check fails
		ArrayList<String> al = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(pdbRecord.substring(start - 1, finish), ","); //$NON-NLS-1$
		while (st.hasMoreTokens())
		{
			al.add(st.nextToken().trim());
		}
		return al;
	}

	// LString A literal string of characters. All spacing is significant and must be preserved.
	protected static String extractLString(String pdbRecord, int start, int finish)
	{
		// TODO: check the record for the above definition and throw exception if check fails
		return pdbRecord.substring(start - 1, finish).trim();
	}

	// LString(n) An LString with exactly n characters.
	protected static String extractLStringN(String pdbRecord, int start, int finish)
	{
		// TODO: check the record for the above definition and throw exception if check fails
		return pdbRecord.substring(start - 1, finish).trim();
	}

	// Real(n,m) Real (floating point) number in the FORTRAN format Fn.m.
	protected static float extractRealNM(String pdbRecord, int start, int finish, int n, int m)
	{
		if (finish > pdbRecord.length())
		{
			return 0.f;
		}
		else
			return new Float(pdbRecord.substring(start - 1, finish)).floatValue();
	}

	// Record name The name of the record: 6 characters, left-justified and blank-filled.
	protected static String extractRecname(String pdbRecord, int start, int finish)
	{
		if (finish > pdbRecord.length())
		{
			return ""; //$NON-NLS-1$
		}
		else
		{
			return pdbRecord.substring(start - 1, finish).trim();
		}
	}

	// Residue name One of the standard amino acid or nucleic acids, as listed below, or the
	// non-standard group designation as defined in the HET dictionary. Field is right-justified.
	protected static String extractResname(String pdbRecord, int start, int finish)
	{
		// TODO: check the record for the above definition and throw exception if check fails
		return pdbRecord.substring(start - 1, finish).trim();
	}

	// SList A String that is composed of text separated with semi-colons.
	protected static ArrayList<String> extractSList(String pdbRecord, int start, int finish)
	{
		// TODO: check the record for the above definition and throw exception if check fails
		ArrayList<String> al = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(pdbRecord.substring(start - 1, finish), ";"); //$NON-NLS-1$
		while (st.hasMoreTokens())
		{
			al.add(st.nextToken().trim());
		}
		return al;
	}

	// Specification A String composed of a token and its associated filterOptions separated by a colon.
	protected static String[] extractSpecification(String pdbRecord, int start, int finish)
	{
		// TODO: check the record for the above definition and throw exception if check fails
		String[] strs = { "", "" }; //$NON-NLS-1$ //$NON-NLS-2$
		StringTokenizer st = new StringTokenizer(pdbRecord.substring(start - 1, finish), ":"); //$NON-NLS-1$
		if (st.hasMoreTokens())
		{
			strs[0] = st.nextToken().trim();
			if (st.hasMoreTokens())
			{
				strs[1] = st.nextToken().trim();
			}
		}
		return strs;
	}

	// Specification A sequence of Specifications, separated by semi-colons.
	// list
	protected static java.util.Properties extractSpecificationList(String pdbRecord, int start, int finish)
	{
		// TODO: check the record for the above definition and throw exception if check fails
		java.util.Properties sl = new java.util.Properties();
		StringTokenizer st = new StringTokenizer(pdbRecord, ";"); //$NON-NLS-1$
		while (st.hasMoreTokens())
		{
			String spec = st.nextToken();
			String[] specification = extractSpecification(spec, 1, spec.length());
			sl.put(specification[0].trim(), specification[1].trim());
		}
		return sl;
	}

	// String A sequence of characters. These characters may have arbitrary spacing, but should be interpreted as directed below.
	// TODO: check the record for the above definition and throw exception if check fails
	protected static String extractString(String pdbRecord, int start, int finish)
	{
		return pdbRecord.substring(start - 1, finish).trim();
	}

	// String(n) A String with exactly n characters.
	// TODO: check the record for the above definition and throw exception if check fails
	protected static String extractStringN(String pdbRecord, int start, int finish)
	{
		if (pdbRecord.length() > start)
		{
			if (pdbRecord.length() > finish)
			{
				return (pdbRecord + SPACES).substring(start - 1, finish).trim();
			}
			else if (pdbRecord.length() == finish)
			{
				return pdbRecord.substring(start - 1).trim();
			}
			else if (pdbRecord.length() >= finish)
			{
				return pdbRecord.substring(start - 1, finish).trim();
			}
			else
			{
				SPACES.substring(0, finish - start + 1).trim();
			}
		}
		return SPACES.substring(0, finish - start + 1).trim();
	}

	// SymOP An integer field of from 4 to 6 digits, right-justified, of the form nnnMMM where nnn is the symmetry operator number and MMM is the translation
	// vector. See details in Appendix 1.
	/**
	 * Appendix 1: Symmetry Operations The data type SymOP is used to succinctly describe crystallographic symmetry operations that may be performed on
	 * ATOM/HETATM coordinates. Symmetry operators applicable to a given entry are presented in REMARK 290. Each operator is assigned a serial number. The SymOP
	 * is a number of up to six (6) digits that indicates the serial number of the symmetry operator and the cell translations along the x, y, and z axes. The
	 * SymOP data type is of the form nnnMMM where 'n' is the serial number of the symmetry operator, and 'MMM' is the concatenated cell translations along x,
	 * y, z with respect to the baseatoms number 555. Symmetry operators listed in REMARK 290 operate on orthogonal crystallographic coordinates that appear in the
	 * entry.. The FORTRAN I3 I3 format statement can be used to interpret nnnMMM. As an example, the SymOP 2456 indicates that the second symmetry operation as
	 * listed in REMARK 290 is applied with translation of -1 on x, and +1 on z. A program will be made available shortly that converts SymOP data into
	 * transformations that operate in the coordinate frame used in the entry. The SymOP data type is used in SSBOND, LINK, HYDBND, SLTBRG and REMARKs. Template
	 * 1 2 3 4 5 6 7 1234567890123456789012345678901234567890123456789012345678901234567890 REMARK 290 REMARK 290 CRYSTALLOGRAPHIC SYMMETRY REMARK 290 SYMMETRY
	 * OPERATORS FOR SPACE GROUP: P 21 21 21 REMARK 290 REMARK 290 SYMOP SYMMETRY REMARK 290 NNNMMM OPERATOR REMARK 290 1555 X,Y,Z REMARK 290 2555
	 * 1/2-X,-Y,1/2+Z REMARK 290 3555 -X,1/2+Y,1/2-Z REMARK 290 4555 1/2+X,1/2-Y,-Z REMARK 290 REMARK 290 WHERE NNN -> OPERATOR NUMBER REMARK 290 MMM ->
	 * TRANSLATION VECTOR REMARK 290 REMARK 290 CRYSTALLOGRAPHIC SYMMETRY TRANSFORMATIONS REMARK 290 THE FOLLOWING TRANSFORMATIONS OPERATE ON THE ATOM/HETATM
	 * REMARK 290 RECORDS IN THIS ENTRY TO PRODUCE CRYSTALLOGRAPHICALLY REMARK 290 RELATED MOLECULES. REMARK 290 SMTRY1 1 1.000000 0.000000 0.000000 0.00000
	 * REMARK 290 SMTRY2 1 0.000000 1.000000 0.000000 0.00000 REMARK 290 SMTRY3 1 0.000000 0.000000 1.000000 0.00000 REMARK 290 SMTRY1 2 -1.000000 0.000000
	 * 0.000000 36.30027 REMARK 290 SMTRY2 2 0.000000 -1.000000 0.000000 0.00000 REMARK 290 SMTRY3 2 0.000000 0.000000 1.000000 59.50256 REMARK 290 SMTRY1 3
	 * -1.000000 0.000000 0.000000 0.00000 REMARK 290 SMTRY2 3 0.000000 1.000000 0.000000 46.45545 REMARK 290 SMTRY3 3 0.000000 0.000000 -1.000000 59.50256
	 * REMARK 290 SMTRY1 4 1.000000 0.000000 0.000000 36.30027 REMARK 290 SMTRY2 4 0.000000 -1.000000 0.000000 46.45545 REMARK 290 SMTRY3 4 0.000000 0.000000
	 * -1.000000 0.00000 REMARK 290 REMARK 290 REMARK: NULL
	 */
	protected static String extractSymOP(String pdbRecord, int start, int finish)
	{
		// TODO: proteinsecondary_coil the extracted string into a SymOP class that contains a operatornumber and a tuple
		return extractString(pdbRecord, start, finish).trim();
	}

	protected static float extractCharge(String pdbRecord, int start, int finish)
	{
		if (pdbRecord.length() >= finish)
		{
			String chargeStr = pdbRecord.substring(start - 1, finish).trim();
			if (chargeStr.length() == 2 && (chargeStr.substring(1).equals("-") || chargeStr.substring(1).equals("+"))) //$NON-NLS-1$ //$NON-NLS-2$
			{
				int chargeInt = (chargeStr.substring(1).equals("-") ? -1 : 1); //$NON-NLS-1$
				chargeInt *= Integer.parseInt(chargeStr.substring(0, 1));
				String str = "" + chargeInt; //$NON-NLS-1$
				return Float.valueOf(str).floatValue();
			}
		}
		return 0.f;
	}

	// ChainId
	protected static String extractChainId(String pdbRecord, int start, int finish)
	{
		if (finish > pdbRecord.length())
		{
			return ""; //$NON-NLS-1$
		}
		else
		{
			return pdbRecord.substring(start - 1, finish).trim();
		}
	}

	// Resid
	protected static int extractResid(String pdbRecord, int start, int finish)
	{
		return extractRInteger(pdbRecord, start, finish);
	}

}
