/* Generated by star.annotations.GeneratedClass, all changes will be lost */

package molecule.ui.adjust.center.protein.primary.signal;

public class CenterProteinPrimaryFilterEvent extends star.event.Event
{
	private static final long serialVersionUID = 1L;

	public  CenterProteinPrimaryFilterEvent(molecule.ui.adjust.center.protein.primary.signal.CenterProteinPrimaryFilterEvent event)
	{
		super( event ) ;
	}
	 
	public  CenterProteinPrimaryFilterEvent(molecule.ui.adjust.center.protein.primary.signal.CenterProteinPrimaryFilterRaiser raiser)
	{
		super( raiser ) ;
	}
	 
	public  CenterProteinPrimaryFilterEvent(star.event.Raiser raiser, boolean valid)
	{
		super( raiser , valid ) ;
	}
	 
	public void raise()
	{
		raiseImpl();
	}
	 
}