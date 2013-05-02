package utils;

public class JmolColors extends ChainColors{

	/**
	 * Jmol CHAIN COLOR TABLE 
	 */
 	 protected static String[] myChainColorTable = {
// 		Chain
// 		ID    ATOM      HETATM
 		"A", "C0D0FF", "90A0CF", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
 		"B", "B0FFB0", "80CF98", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
 		"C", "FFC0C8", "CF90B0",	   //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
 		"D", "FFFF80", "CFCF70", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
 		"E", "FFC0FF", "CF90CF", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
 		"F", "B0F0F0", "80C0C0", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
 		"G", "FFD070", "CFA060", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
 		"H", "F08080", "C05070", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
 		"I", "F5DEB3", "C5AE83", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
 		"J", "00BFFF", "00A7CF", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
 		"K", "CD5C5C", "B54C4C", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
 		"L", "66CDAA", "56B592", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
 		"M", "9ACD32", "8AB52A", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
 		"N", "EE82EE", "BE72BE", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
 		"O", "00CED1", "00B6A1", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
 		"P", "00FF7F", "00CF6F", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
 		"Q", "3CB371", "349B61", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
 		"R", "00008B", "0000BB", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
 		"S", "BDB76B", "A59F5B", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
 		"T", "006400", "009400", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
 		"U", "800000", "B00000", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
 		"V", "808000", "B0B000", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
 		"W", "800080", "B000B0", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
 		"X", "008080", "00B0B0", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
 		"Y", "B8860B", "E8B613", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
 		"Z", "B22222", "C23232" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
 		//none/
 		//numeric	[255,255,255]	FFFFFF	  	 	[255,255,255]	FFFFFF	  
 	 };
  	public JmolColors() {
 		this.chainColorTable = myChainColorTable;
 	}
	private static final long serialVersionUID = 1L;

	public String getColorType() {
		return "JmolColors"; //$NON-NLS-1$
	}

}
