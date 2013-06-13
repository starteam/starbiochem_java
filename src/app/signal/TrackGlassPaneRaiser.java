package app.signal;

import star.event.Raiser;

@star.annotations.Raiser
public interface TrackGlassPaneRaiser extends Raiser
{
	abstract public boolean isGlassPaneShowing();
}
