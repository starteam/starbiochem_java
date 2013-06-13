package molecule.interfaces;

import java.io.Serializable;

public interface TertiaryStringsAndColors extends Serializable {

	String getNonpolarHydrophobicString();
	String getPolarHydrophilicString();
	String getBuriedString();
	String getSurfaceString();
	String getPositivelyChargedString();
	String getNegativelyChargedString();
	String getNeutralString();

	String getNonpolarHydrophobicColor();
	String getPolarHydrophilicColor();
	String getBuriedColor();
	String getSurfaceColor();
	String getPositivelyChargedColor();
	String getNegativelyChargedColor();
	String getNeutralColor();
	
	boolean isAcidic(String residue);
	boolean isBasic(String residue);
	boolean isNeutral(String residue);
	boolean isBuried(String residue);
	boolean isSurface(String residue);
	boolean isHydrophobic(String residue);
	boolean isHydrophilic(String residue);

}
