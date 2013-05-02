package utils;

import java.awt.Color;

public abstract class ChainColors implements molecule.interfaces.ChainColors{
	private static final long serialVersionUID = 1L;
	private static final String EMPTY_STRING = ""; //$NON-NLS-1$

	/**
	 * CHAIN COLOR TABLE 
	 */
 	protected String[] chainColorTable = null;
 	
 	protected Color[] chainColors = new Color[26];
 	
 	protected Color defaultColor = new Color(0xFFFFFF);

	public Color getChainColor(String chainId){
		return getChainColor(chainId, (byte) 0x1f);
	}
	
	public Color getChainColor(String chainId, byte transparency) {
		if(null != this.chainColorTable) {
			if((null != chainId) && !EMPTY_STRING.equals( chainId.trim() )) {
				for(int i=0; this.chainColorTable.length != i; i+=3) {
					String colorTableChainId = this.chainColorTable[i];
					if((null != colorTableChainId) && !EMPTY_STRING.equals( colorTableChainId )) {
						if(colorTableChainId.trim().toUpperCase().equals(chainId.trim().substring(0,1).toUpperCase())) {
							String alpha = Integer.toHexString(0xff & transparency);
							String redInHex = this.chainColorTable[i + 1].substring(0,2);
							String greenInHex = this.chainColorTable[i + 1].substring(2,4);
							String blueInHex = this.chainColorTable[i + 1].substring(4);
							Color color =  new Color(Integer.parseInt(redInHex, 16),Integer.parseInt(greenInHex, 16),Integer.parseInt(blueInHex, 16), Integer.parseInt(alpha, 16));
							return color;
						}
					}
				}
			}
		}
		return defaultColor;
	}

}
