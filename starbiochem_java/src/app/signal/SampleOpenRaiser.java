package app.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface SampleOpenRaiser extends Raiser
{
	String getSamplesJarLocation();
	String getSampleId();
}
