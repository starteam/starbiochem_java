/* Generated by star.annotations.GeneratedClass, all changes will be lost */

package molecule.ui.jmol.signal;

public class TurnOnSSBondsEvent extends star.event.Event
{
	private static final long serialVersionUID = 1L;

	public  TurnOnSSBondsEvent(molecule.ui.jmol.signal.TurnOnSSBondsEvent event)
	{
		super( event ) ;
	}
	 
	public  TurnOnSSBondsEvent(molecule.ui.jmol.signal.SSBondsEnableRaiser raiser)
	{
		super( raiser ) ;
	}
	 
	public  TurnOnSSBondsEvent(star.event.Raiser raiser, boolean valid)
	{
		super( raiser , valid ) ;
	}
	 
	public void raise()
	{
		raiseImpl();
	}
	 
}