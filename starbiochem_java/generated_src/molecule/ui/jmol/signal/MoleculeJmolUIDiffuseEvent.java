/* Generated by star.annotations.GeneratedClass, all changes will be lost */

package molecule.ui.jmol.signal;

public class MoleculeJmolUIDiffuseEvent extends star.event.Event
{
	private static final long serialVersionUID = 1L;

	public  MoleculeJmolUIDiffuseEvent(molecule.ui.jmol.signal.MoleculeJmolUIDiffuseEvent event)
	{
		super( event ) ;
	}
	 
	public  MoleculeJmolUIDiffuseEvent(molecule.ui.jmol.signal.MoleculeJmolUIDiffuseRaiser raiser)
	{
		super( raiser ) ;
	}
	 
	public  MoleculeJmolUIDiffuseEvent(star.event.Raiser raiser, boolean valid)
	{
		super( raiser , valid ) ;
	}
	 
	public void raise()
	{
		raiseImpl();
	}
	 
}