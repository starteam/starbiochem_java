/* Generated by star.annotations.GeneratedClass, all changes will be lost */

package molecule.ui.protein.quaternary;

public abstract class ProteinQuaternaryBondRenderingPanel_generated extends javax.swing.JPanel implements star.event.EventController
{
	private star.event.Adapter adapter;
	private java.util.prefs.Preferences preferences = null;
	private static final long serialVersionUID = 1L;

	public  ProteinQuaternaryBondRenderingPanel_generated()
	{
		super();
	}
	 
	public  ProteinQuaternaryBondRenderingPanel_generated(boolean boolean0)
	{
		super( boolean0);
	}
	 
	public  ProteinQuaternaryBondRenderingPanel_generated(java.awt.LayoutManager layoutManager, boolean boolean0)
	{
		super( layoutManager,boolean0);
	}
	 
	public  ProteinQuaternaryBondRenderingPanel_generated(java.awt.LayoutManager layoutManager)
	{
		super( layoutManager);
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
	 
	public java.util.prefs.Preferences getPreferences(java.lang.String name)
	{
		try
		{
			plugin.preferences.Preferences pref = (plugin.preferences.Preferences) plugin.Loader.getDefaultLoader().getPlugin(plugin.preferences.Preferences.class.getName(), plugin.preferences.PreferencesImplementation.class.getName());
			this.preferences = pref.getPreferences(name);
		}
		catch( plugin.PluginException ex )
		{
			ex.printStackTrace();
		}
		return preferences;
	}
	 
	public java.util.prefs.Preferences getPreferences()
	{
		if( preferences == null )
		{
			try
			{
				plugin.preferences.Preferences pref = (plugin.preferences.Preferences) plugin.Loader.getDefaultLoader().getPlugin(plugin.preferences.Preferences.class.getName(), plugin.preferences.PreferencesImplementation.class.getName());
				this.preferences = pref.getPreferences("molecule.ui.protein.quaternary.ProteinQuaternaryBondRenderingPanel");
			}
			catch( plugin.PluginException ex )
			{
				ex.printStackTrace();
			}
		}
		return preferences;
	}
	 
	public void removeNotify()
	{
		super.removeNotify();
	}
	 
}