/* Generated by star.annotations.GeneratedClass, all changes will be lost */

package molecule.ui.structureoptions.center.backgroundcolor;

public abstract class BackgroundColorChooser_generated extends javax.swing.JColorChooser implements app.signal.MoleculesBackgroundColorRaiser, star.event.EventController
{
	private star.event.Adapter adapter;
	private static final long serialVersionUID = 1L;

	public  BackgroundColorChooser_generated()
	{
		super();
	}
	 
	public  BackgroundColorChooser_generated(java.awt.Color color)
	{
		super( color);
	}
	 
	public  BackgroundColorChooser_generated(javax.swing.colorchooser.ColorSelectionModel colorSelectionModel)
	{
		super( colorSelectionModel);
	}
	 
	public void addNotify()
	{
		super.addNotify();
	}
	 
	public star.event.Adapter getAdapter()
	{
		if( adapter == null )
		{
			adapter = new star.event.Adapter(this);
		}
		return adapter;
	}
	 
	public void raise_MoleculesBackgroundColorEvent()
	{
		(new app.signal.MoleculesBackgroundColorEvent(this)).raise();
	}
	 
	public void removeNotify()
	{
		super.removeNotify();
	}
	 
}