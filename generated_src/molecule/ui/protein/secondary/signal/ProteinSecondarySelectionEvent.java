/* Generated by star.annotations.GeneratedClass, all changes will be lost */

package molecule.ui.protein.secondary.signal;

public class ProteinSecondarySelectionEvent extends star.event.Event
{
	private static final long serialVersionUID = 1L;

	public  ProteinSecondarySelectionEvent(molecule.ui.protein.secondary.signal.ProteinSecondarySelectionEvent event)
	{
		super( event ) ;
	}
	 
	public  ProteinSecondarySelectionEvent(molecule.ui.protein.secondary.signal.ProteinSecondarySelectionRaiser raiser)
	{
		super( raiser ) ;
	}
	 
	public  ProteinSecondarySelectionEvent(star.event.Raiser raiser, boolean valid)
	{
		super( raiser , valid ) ;
	}
	 
	public void raise()
	{
		raiseImpl();
	}
	 
}