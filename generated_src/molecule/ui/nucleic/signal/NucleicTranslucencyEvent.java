/* Generated by star.annotations.GeneratedClass, all changes will be lost */

package molecule.ui.nucleic.signal;

public class NucleicTranslucencyEvent extends star.event.Event
{
	private static final long serialVersionUID = 1L;

	public  NucleicTranslucencyEvent(molecule.ui.nucleic.signal.NucleicTranslucencyEvent event)
	{
		super( event ) ;
	}
	 
	public  NucleicTranslucencyEvent(molecule.ui.nucleic.signal.NucleicTranslucencyRaiser raiser)
	{
		super( raiser ) ;
	}
	 
	public  NucleicTranslucencyEvent(star.event.Raiser raiser, boolean valid)
	{
		super( raiser , valid ) ;
	}
	 
	public void raise()
	{
		raiseImpl();
	}
	 
}