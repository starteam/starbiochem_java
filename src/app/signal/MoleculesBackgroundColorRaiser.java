package app.signal;

import java.awt.Color;

import star.event.Raiser;

@star.annotations.Raiser
public interface MoleculesBackgroundColorRaiser extends Raiser
{
	Color getMoleculesBackgroundColor();
}
