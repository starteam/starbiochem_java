/* Generated by star.annotations.GeneratedClass, all changes will be lost */

package molecule.ui.hetero.signal;

public class HeteroHBondTranslucencyEvent extends star.event.Event
{
	private static final long serialVersionUID = 1L;

	public  HeteroHBondTranslucencyEvent(molecule.ui.hetero.signal.HeteroHBondTranslucencyEvent event)
	{
		super( event ) ;
	}
	 
	public  HeteroHBondTranslucencyEvent(molecule.ui.hetero.signal.HeteroHBondTranslucencyRaiser raiser)
	{
		super( raiser ) ;
	}
	 
	public  HeteroHBondTranslucencyEvent(star.event.Raiser raiser, boolean valid)
	{
		super( raiser , valid ) ;
	}
	 
	public void raise()
	{
		raiseImpl();
	}
	 
}