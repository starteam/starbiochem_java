/* Generated by star.annotations.GeneratedClass, all changes will be lost */

package app.signal;

public class ResetSceneEvent extends star.event.Event
{
	private static final long serialVersionUID = 1L;

	public  ResetSceneEvent(app.signal.ResetSceneEvent event)
	{
		super( event ) ;
	}
	 
	public  ResetSceneEvent(app.signal.ResetSceneRaiser raiser)
	{
		super( raiser ) ;
	}
	 
	public  ResetSceneEvent(star.event.Raiser raiser, boolean valid)
	{
		super( raiser , valid ) ;
	}
	 
	public void raise()
	{
		raiseImpl();
	}
	 
}