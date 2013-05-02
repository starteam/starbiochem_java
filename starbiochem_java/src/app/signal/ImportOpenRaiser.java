package app.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface ImportOpenRaiser extends Raiser
{
	String getImportURL();
}
