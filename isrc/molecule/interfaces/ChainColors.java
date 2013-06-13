package molecule.interfaces;

import java.awt.Color;
import java.io.Serializable;

public interface ChainColors extends Serializable {
	
	String getColorType();
	Color getChainColor(String chainId);
	Color getChainColor(String chainId, byte transparencey);

}
