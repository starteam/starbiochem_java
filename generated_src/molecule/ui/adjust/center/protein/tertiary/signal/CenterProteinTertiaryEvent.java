/* Generated by star.annotations.GeneratedClass, all changes will be lost */

package molecule.ui.adjust.center.protein.tertiary.signal;

public class CenterProteinTertiaryEvent extends molecule.ui.adjust.signal.SelectionEvent
{
	private static final long serialVersionUID = 1L;

	public  CenterProteinTertiaryEvent(molecule.ui.adjust.center.protein.tertiary.signal.CenterProteinTertiaryEvent event)
	{
		super( event ) ;
	}
	 
	public  CenterProteinTertiaryEvent(molecule.ui.adjust.center.protein.tertiary.signal.CenterProteinTertiaryRaiser raiser)
	{
		super( raiser ) ;
	}
	 
	public  CenterProteinTertiaryEvent(star.event.Raiser raiser, boolean valid)
	{
		super( raiser , valid ) ;
	}
	 
	public void raise()
	{
		raiseImpl();
	}
	 
}