/* Generated by star.annotations.GeneratedClass, all changes will be lost */

package molecule.ui.water.properties;

public abstract class WaterHBondSizePropertyControls_generated extends molecule.ui.PropertyControls implements molecule.ui.water.signal.WaterHBondSizeRaiser, star.event.EventController
{
	private static final long serialVersionUID = 1L;

	public  WaterHBondSizePropertyControls_generated(java.lang.String string, int int0, int int1, int int2)
	{
		super( string,int0,int1,int2);
	}
	 
	public void addNotify()
	{
		super.addNotify();
	}
	 
	public void raise_WaterHBondSizeEvent()
	{
		(new molecule.ui.water.signal.WaterHBondSizeEvent(this)).raise();
	}
	 
	public void removeNotify()
	{
		super.removeNotify();
	}
	 
}