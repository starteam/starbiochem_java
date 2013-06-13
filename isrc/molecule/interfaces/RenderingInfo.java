package molecule.interfaces;

import java.io.Serializable;

public interface RenderingInfo extends Serializable
{
	String getUrl();
	String getSource();
	String[] getSelection();
	Bond[] getStructureHbonds();
	int getBondTranslucency();
	int getBrightness();
	int getDiffuse();
	int getFilterOptions();
	int getTranslucency();
	int getSegments();
	int getSize();
	int getSpecular();
	int getSSbondSize();
	int getSSbondTranslucency();
	int getHBondSize();
	int getHBondTranslucency();
}
