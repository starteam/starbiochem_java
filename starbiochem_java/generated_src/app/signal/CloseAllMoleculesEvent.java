/* Generated by star.annotations.GeneratedClass, all changes will be lost */

package app.signal;

public class CloseAllMoleculesEvent extends star.event.Event
{
	private static final long serialVersionUID = 1L;

	public  CloseAllMoleculesEvent(app.signal.CloseAllMoleculesEvent event)
	{
		super( event ) ;
	}
	 
	public  CloseAllMoleculesEvent(app.signal.CloseAllMoleculesRaiser raiser)
	{
		super( raiser ) ;
	}
	 
	public  CloseAllMoleculesEvent(star.event.Raiser raiser, boolean valid)
	{
		super( raiser , valid ) ;
	}
	 
	public void raise()
	{
		raiseImpl();
	}
	 
}