/* Generated by star.annotations.GeneratedClass, all changes will be lost */

package molecule.ui.adjust.center.nucleic.signal;

public class CenterNucleicEvent extends molecule.ui.adjust.signal.SelectionEvent
{
	private static final long serialVersionUID = 1L;

	public  CenterNucleicEvent(molecule.ui.adjust.center.nucleic.signal.CenterNucleicEvent event)
	{
		super( event ) ;
	}
	 
	public  CenterNucleicEvent(molecule.ui.adjust.center.nucleic.signal.CenterNucleicRaiser raiser)
	{
		super( raiser ) ;
	}
	 
	public  CenterNucleicEvent(star.event.Raiser raiser, boolean valid)
	{
		super( raiser , valid ) ;
	}
	 
	public void raise()
	{
		raiseImpl();
	}
	 
}