/* Generated by star.annotations.GeneratedClass, all changes will be lost */

package molecule.ui.hetero.signal;

public class HeteroFilterEvent extends star.event.Event
{
	private static final long serialVersionUID = 1L;

	public  HeteroFilterEvent(molecule.ui.hetero.signal.HeteroFilterEvent event)
	{
		super( event ) ;
	}
	 
	public  HeteroFilterEvent(molecule.ui.hetero.signal.HeteroFilterRaiser raiser)
	{
		super( raiser ) ;
	}
	 
	public  HeteroFilterEvent(star.event.Raiser raiser, boolean valid)
	{
		super( raiser , valid ) ;
	}
	 
	public void raise()
	{
		raiseImpl();
	}
	 
}