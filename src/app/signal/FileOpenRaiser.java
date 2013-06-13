package app.signal;

import java.io.File;

import star.event.Raiser;

@star.annotations.Raiser
public interface FileOpenRaiser extends Raiser
{
	String getFileName();
	boolean isCreatingJmolZip();
	File getTarget();
}
