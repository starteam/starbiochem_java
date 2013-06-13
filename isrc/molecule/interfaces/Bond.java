package molecule.interfaces;

import java.io.Serializable;

public interface Bond extends Serializable
{
	String getAtom1();
	String getAtom2();
}
