/* Generated by star.annotations.GeneratedClass, all changes will be lost */

package molecule.ui.hetero.signal;

public class HeteroTranslucencyEvent extends star.event.Event
{
	private static final long serialVersionUID = 1L;

	public  HeteroTranslucencyEvent(molecule.ui.hetero.signal.HeteroTranslucencyEvent event)
	{
		super( event ) ;
	}
	 
	public  HeteroTranslucencyEvent(molecule.ui.hetero.signal.HeteroTranslucencyRaiser raiser)
	{
		super( raiser ) ;
	}
	 
	public  HeteroTranslucencyEvent(star.event.Raiser raiser, boolean valid)
	{
		super( raiser , valid ) ;
	}
	 
	public void raise()
	{
		raiseImpl();
	}
	 
}