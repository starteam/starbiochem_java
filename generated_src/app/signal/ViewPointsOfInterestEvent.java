/* Generated by star.annotations.GeneratedClass, all changes will be lost */

package app.signal;

public class ViewPointsOfInterestEvent extends star.event.Event
{
	private static final long serialVersionUID = 1L;

	public  ViewPointsOfInterestEvent(app.signal.ViewPointsOfInterestEvent event)
	{
		super( event ) ;
	}
	 
	public  ViewPointsOfInterestEvent(app.signal.ViewRegionAndCenterRaiser raiser)
	{
		super( raiser ) ;
	}
	 
	public  ViewPointsOfInterestEvent(star.event.Raiser raiser, boolean valid)
	{
		super( raiser , valid ) ;
	}
	 
	public void raise()
	{
		raiseImpl();
	}
	 
}