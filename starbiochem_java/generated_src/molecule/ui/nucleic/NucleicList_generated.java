/* Generated by star.annotations.GeneratedClass, all changes will be lost */

package molecule.ui.nucleic;

public abstract class NucleicList_generated extends javax.swing.JList implements molecule.ui.nucleic.signal.NucleicSelectionRaiser, star.event.EventController
{
	private star.event.Adapter adapter;
	private static final long serialVersionUID = 1L;

	public  NucleicList_generated()
	{
		super();
	}
	 
	public  NucleicList_generated(java.lang.Object[] object)
	{
		super( object);
	}
	 
	public  NucleicList_generated(java.util.Vector vector)
	{
		super( vector);
	}
	 
	public  NucleicList_generated(javax.swing.ListModel listModel)
	{
		super( listModel);
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
	 
	public void raise_NucleicSelectionEvent()
	{
		(new molecule.ui.nucleic.signal.NucleicSelectionEvent(this)).raise();
	}
	 
	public void removeNotify()
	{
		super.removeNotify();
	}
	 
}