/* Generated by star.annotations.GeneratedClass, all changes will be lost */

package molecule.ui.structureoptions.center.foreground.properties;

public abstract class MoleculeJmolUIBrightnessPropertyControls_generated extends molecule.ui.PropertyControls implements molecule.ui.jmol.signal.MoleculeJmolUIBrightnessRaiser, star.event.EventController
{
	private static final long serialVersionUID = 1L;

	public  MoleculeJmolUIBrightnessPropertyControls_generated(java.lang.String string, int int0, int int1, int int2)
	{
		super( string,int0,int1,int2);
	}
	 
	public void addNotify()
	{
		super.addNotify();
	}
	 
	public void raise_MoleculeJmolUIBrightnessEvent()
	{
		(new molecule.ui.jmol.signal.MoleculeJmolUIBrightnessEvent(this)).raise();
	}
	 
	public void removeNotify()
	{
		super.removeNotify();
	}
	 
}