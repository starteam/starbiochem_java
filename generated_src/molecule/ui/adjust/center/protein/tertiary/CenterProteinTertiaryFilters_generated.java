/* Generated by star.annotations.GeneratedClass, all changes will be lost */

package molecule.ui.adjust.center.protein.tertiary;

public abstract class CenterProteinTertiaryFilters_generated extends molecule.ui.AbstractFiltersUI implements molecule.ui.adjust.center.protein.tertiary.signal.CenterProteinTertiaryFilterRaiser, star.event.EventController
{
	private java.util.prefs.Preferences preferences = null;
	private static final long serialVersionUID = 1L;

	public  CenterProteinTertiaryFilters_generated()
	{
		super();
	}
	 
	public void addNotify()
	{
		super.addNotify();
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
				this.preferences = pref.getPreferences("molecule.ui.adjust.center.protein.tertiary.CenterProteinTertiaryFilters");
			}
			catch( plugin.PluginException ex )
			{
				ex.printStackTrace();
			}
		}
		return preferences;
	}
	 
	public void raise_CenterProteinTertiaryFilterEvent()
	{
		(new molecule.ui.adjust.center.protein.tertiary.signal.CenterProteinTertiaryFilterEvent(this)).raise();
	}
	 
	public void removeNotify()
	{
		super.removeNotify();
	}
	 
}