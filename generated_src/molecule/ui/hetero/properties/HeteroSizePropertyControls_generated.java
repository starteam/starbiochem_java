/* Generated by star.annotations.GeneratedClass, all changes will be lost */

package molecule.ui.hetero.properties;

public abstract class HeteroSizePropertyControls_generated extends molecule.ui.PropertyControls implements molecule.ui.hetero.signal.HeteroSizeRaiser, star.event.EventController
{
	private static final long serialVersionUID = 1L;

	public  HeteroSizePropertyControls_generated(java.lang.String string, int int0, int int1, int int2)
	{
		super( string,int0,int1,int2);
	}
	 
	public void addNotify()
	{
		super.addNotify();
	}
	 
	public void raise_HeteroSizeEvent()
	{
		(new molecule.ui.hetero.signal.HeteroSizeEvent(this)).raise();
	}
	 
	public void removeNotify()
	{
		super.removeNotify();
	}
	 
}