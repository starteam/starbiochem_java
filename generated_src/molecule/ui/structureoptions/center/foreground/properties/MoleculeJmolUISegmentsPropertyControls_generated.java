/* Generated by star.annotations.GeneratedClass, all changes will be lost */

package molecule.ui.structureoptions.center.foreground.properties;

public abstract class MoleculeJmolUISegmentsPropertyControls_generated extends molecule.ui.PropertyControls implements molecule.ui.jmol.signal.MoleculeJmolUISegmentsRaiser, star.event.EventController
{
	private static final long serialVersionUID = 1L;

	public  MoleculeJmolUISegmentsPropertyControls_generated(java.lang.String string, int int0, int int1, int int2)
	{
		super( string,int0,int1,int2);
	}
	 
	public void addNotify()
	{
		super.addNotify();
	}
	 
	public void raise_MoleculeJmolUISegmentsEvent()
	{
		(new molecule.ui.jmol.signal.MoleculeJmolUISegmentsEvent(this)).raise();
	}
	 
	public void removeNotify()
	{
		super.removeNotify();
	}
	 
}