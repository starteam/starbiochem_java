/* Generated by star.annotations.GeneratedClass, all changes will be lost */

package molecule.ui.protein.quaternary;

public abstract class ProteinQuaternaryPanel_generated extends molecule.ui.AbstractMoleculeUI implements molecule.ui.signal.RenderingInfoRaiser, star.event.EventController, star.event.Listener
{
	private java.util.prefs.Preferences preferences = null;
	private static final long serialVersionUID = 1L;

	public  ProteinQuaternaryPanel_generated()
	{
		super();
	}
	 
	public void addNotify()
	{
		super.addNotify();
		getAdapter().addHandled(molecule.ui.protein.quaternary.signal.ProteinQuaternarySelectionEvent.class);
		getAdapter().addHandled(molecule.ui.protein.quaternary.signal.ProteinQuaternaryApplyRenderingEvent.class);
		getAdapter().addHandled(molecule.ui.protein.quaternary.signal.ProteinQuaternaryHBondSizeEvent.class);
		getAdapter().addHandled(molecule.ui.protein.quaternary.signal.ProteinQuaternaryRenderingModeEvent.class);
		getAdapter().addHandled(molecule.ui.protein.quaternary.signal.ProteinQuaternaryFilterEvent.class);
		getAdapter().addHandled(molecule.ui.protein.quaternary.signal.ProteinQuaternarySizeEvent.class);
		getAdapter().addHandled(molecule.ui.protein.quaternary.signal.ProteinQuaternaryHBondTranslucencyEvent.class);
		getAdapter().addHandled(molecule.ui.protein.quaternary.signal.ProteinQuaternarySSBondTranslucencyEvent.class);
		getAdapter().addHandled(molecule.ui.protein.quaternary.signal.ProteinQuaternaryTranslucencyEvent.class);
		getAdapter().addHandled(molecule.ui.protein.quaternary.signal.ProteinQuaternarySSBondSizeEvent.class);
	}
	 
	public void eventRaised(final star.event.Event event)
	{
		eventRaisedHandles(event);
	}
	 
	private void eventRaisedHandles(final star.event.Event event)
	{
		if( event.getClass().getName().equals( "molecule.ui.protein.quaternary.signal.ProteinQuaternarySelectionEvent" ) && event.isValid() ) 
		{
			 utils.Runner.runOnThread(new Runnable() { public void run() { 
			 long start = System.nanoTime();
			handleProteinQuaternarySelectionRaiser( (molecule.ui.protein.quaternary.signal.ProteinQuaternarySelectionRaiser)event.getSource());
			 long end = System.nanoTime();
			 if( end - start > 500000000 ) { System.out.println( this.getClass().getName() + ".handleProteinQuaternarySelectionRaiser "  + ( end-start )/1000000 ); } }},this,2);
		}if( event.getClass().getName().equals( "molecule.ui.protein.quaternary.signal.ProteinQuaternaryApplyRenderingEvent" ) && event.isValid() ) 
		{
			 utils.Runner.runOnThread(new Runnable() { public void run() { 
			 long start = System.nanoTime();
			handleProteinQuaternaryApplyRenderingRaiser( (molecule.ui.protein.quaternary.signal.ProteinQuaternaryApplyRenderingRaiser)event.getSource());
			 long end = System.nanoTime();
			 if( end - start > 500000000 ) { System.out.println( this.getClass().getName() + ".handleProteinQuaternaryApplyRenderingRaiser "  + ( end-start )/1000000 ); } }},this,2);
		}if( event.getClass().getName().equals( "molecule.ui.protein.quaternary.signal.ProteinQuaternaryHBondSizeEvent" ) && event.isValid() ) 
		{
			 utils.Runner.runOnThread(new Runnable() { public void run() { 
			 long start = System.nanoTime();
			handleProteinQuaternaryHBondSizeRaiser( (molecule.ui.protein.quaternary.signal.ProteinQuaternaryHBondSizeRaiser)event.getSource());
			 long end = System.nanoTime();
			 if( end - start > 500000000 ) { System.out.println( this.getClass().getName() + ".handleProteinQuaternaryHBondSizeRaiser "  + ( end-start )/1000000 ); } }},this,2);
		}if( event.getClass().getName().equals( "molecule.ui.protein.quaternary.signal.ProteinQuaternaryRenderingModeEvent" ) && event.isValid() ) 
		{
			 utils.Runner.runOnThread(new Runnable() { public void run() { 
			 long start = System.nanoTime();
			handleProteinQuaternaryRenderingModeRaiser( (molecule.ui.protein.quaternary.signal.ProteinQuaternaryRenderingModeRaiser)event.getSource());
			 long end = System.nanoTime();
			 if( end - start > 500000000 ) { System.out.println( this.getClass().getName() + ".handleProteinQuaternaryRenderingModeRaiser "  + ( end-start )/1000000 ); } }},this,2);
		}if( event.getClass().getName().equals( "molecule.ui.protein.quaternary.signal.ProteinQuaternaryFilterEvent" ) && event.isValid() ) 
		{
			 utils.Runner.runOnThread(new Runnable() { public void run() { 
			 long start = System.nanoTime();
			handleProteinQuaternaryFilterRaiser( (molecule.ui.protein.quaternary.signal.ProteinQuaternaryFilterRaiser)event.getSource());
			 long end = System.nanoTime();
			 if( end - start > 500000000 ) { System.out.println( this.getClass().getName() + ".handleProteinQuaternaryFilterRaiser "  + ( end-start )/1000000 ); } }},this,2);
		}if( event.getClass().getName().equals( "molecule.ui.protein.quaternary.signal.ProteinQuaternarySizeEvent" ) && event.isValid() ) 
		{
			 utils.Runner.runOnThread(new Runnable() { public void run() { 
			 long start = System.nanoTime();
			handleProteinQuaternarySizeRaiser( (molecule.ui.protein.quaternary.signal.ProteinQuaternarySizeRaiser)event.getSource());
			 long end = System.nanoTime();
			 if( end - start > 500000000 ) { System.out.println( this.getClass().getName() + ".handleProteinQuaternarySizeRaiser "  + ( end-start )/1000000 ); } }},this,2);
		}if( event.getClass().getName().equals( "molecule.ui.protein.quaternary.signal.ProteinQuaternaryHBondTranslucencyEvent" ) && event.isValid() ) 
		{
			 utils.Runner.runOnThread(new Runnable() { public void run() { 
			 long start = System.nanoTime();
			handleProteinQuaternaryHBondTranslucencyRaiser( (molecule.ui.protein.quaternary.signal.ProteinQuaternaryHBondTranslucencyRaiser)event.getSource());
			 long end = System.nanoTime();
			 if( end - start > 500000000 ) { System.out.println( this.getClass().getName() + ".handleProteinQuaternaryHBondTranslucencyRaiser "  + ( end-start )/1000000 ); } }},this,2);
		}if( event.getClass().getName().equals( "molecule.ui.protein.quaternary.signal.ProteinQuaternarySSBondTranslucencyEvent" ) && event.isValid() ) 
		{
			 utils.Runner.runOnThread(new Runnable() { public void run() { 
			 long start = System.nanoTime();
			handleProteinQuaternarySSBondTranslucencyRaiser( (molecule.ui.protein.quaternary.signal.ProteinQuaternarySSBondTranslucencyRaiser)event.getSource());
			 long end = System.nanoTime();
			 if( end - start > 500000000 ) { System.out.println( this.getClass().getName() + ".handleProteinQuaternarySSBondTranslucencyRaiser "  + ( end-start )/1000000 ); } }},this,2);
		}if( event.getClass().getName().equals( "molecule.ui.protein.quaternary.signal.ProteinQuaternaryTranslucencyEvent" ) && event.isValid() ) 
		{
			 utils.Runner.runOnThread(new Runnable() { public void run() { 
			 long start = System.nanoTime();
			handleProteinQuaternaryTranslucencyRaiser( (molecule.ui.protein.quaternary.signal.ProteinQuaternaryTranslucencyRaiser)event.getSource());
			 long end = System.nanoTime();
			 if( end - start > 500000000 ) { System.out.println( this.getClass().getName() + ".handleProteinQuaternaryTranslucencyRaiser "  + ( end-start )/1000000 ); } }},this,2);
		}if( event.getClass().getName().equals( "molecule.ui.protein.quaternary.signal.ProteinQuaternarySSBondSizeEvent" ) && event.isValid() ) 
		{
			 utils.Runner.runOnThread(new Runnable() { public void run() { 
			 long start = System.nanoTime();
			handleProteinQuaternarySSBondSizeRaiser( (molecule.ui.protein.quaternary.signal.ProteinQuaternarySSBondSizeRaiser)event.getSource());
			 long end = System.nanoTime();
			 if( end - start > 500000000 ) { System.out.println( this.getClass().getName() + ".handleProteinQuaternarySSBondSizeRaiser "  + ( end-start )/1000000 ); } }},this,2);
		}
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
				this.preferences = pref.getPreferences("molecule.ui.protein.quaternary.ProteinQuaternaryPanel");
			}
			catch( plugin.PluginException ex )
			{
				ex.printStackTrace();
			}
		}
		return preferences;
	}
	 
	abstract void handleProteinQuaternaryApplyRenderingRaiser(molecule.ui.protein.quaternary.signal.ProteinQuaternaryApplyRenderingRaiser raiser);
	 
	abstract void handleProteinQuaternaryFilterRaiser(molecule.ui.protein.quaternary.signal.ProteinQuaternaryFilterRaiser raiser);
	 
	abstract void handleProteinQuaternaryHBondSizeRaiser(molecule.ui.protein.quaternary.signal.ProteinQuaternaryHBondSizeRaiser raiser);
	 
	abstract void handleProteinQuaternaryHBondTranslucencyRaiser(molecule.ui.protein.quaternary.signal.ProteinQuaternaryHBondTranslucencyRaiser raiser);
	 
	abstract void handleProteinQuaternaryRenderingModeRaiser(molecule.ui.protein.quaternary.signal.ProteinQuaternaryRenderingModeRaiser raiser);
	 
	abstract void handleProteinQuaternarySSBondSizeRaiser(molecule.ui.protein.quaternary.signal.ProteinQuaternarySSBondSizeRaiser raiser);
	 
	abstract void handleProteinQuaternarySSBondTranslucencyRaiser(molecule.ui.protein.quaternary.signal.ProteinQuaternarySSBondTranslucencyRaiser raiser);
	 
	abstract void handleProteinQuaternarySelectionRaiser(molecule.ui.protein.quaternary.signal.ProteinQuaternarySelectionRaiser raiser);
	 
	abstract void handleProteinQuaternarySizeRaiser(molecule.ui.protein.quaternary.signal.ProteinQuaternarySizeRaiser raiser);
	 
	abstract void handleProteinQuaternaryTranslucencyRaiser(molecule.ui.protein.quaternary.signal.ProteinQuaternaryTranslucencyRaiser raiser);
	 
	public void raise_RenderingInfoEvent()
	{
		(new molecule.ui.signal.RenderingInfoEvent(this)).raise();
	}
	 
	public void removeNotify()
	{
		super.removeNotify();
		getAdapter().removeHandled(molecule.ui.protein.quaternary.signal.ProteinQuaternarySelectionEvent.class);
		getAdapter().removeHandled(molecule.ui.protein.quaternary.signal.ProteinQuaternaryApplyRenderingEvent.class);
		getAdapter().removeHandled(molecule.ui.protein.quaternary.signal.ProteinQuaternaryHBondSizeEvent.class);
		getAdapter().removeHandled(molecule.ui.protein.quaternary.signal.ProteinQuaternaryRenderingModeEvent.class);
		getAdapter().removeHandled(molecule.ui.protein.quaternary.signal.ProteinQuaternaryFilterEvent.class);
		getAdapter().removeHandled(molecule.ui.protein.quaternary.signal.ProteinQuaternarySizeEvent.class);
		getAdapter().removeHandled(molecule.ui.protein.quaternary.signal.ProteinQuaternaryHBondTranslucencyEvent.class);
		getAdapter().removeHandled(molecule.ui.protein.quaternary.signal.ProteinQuaternarySSBondTranslucencyEvent.class);
		getAdapter().removeHandled(molecule.ui.protein.quaternary.signal.ProteinQuaternaryTranslucencyEvent.class);
		getAdapter().removeHandled(molecule.ui.protein.quaternary.signal.ProteinQuaternarySSBondSizeEvent.class);
	}
	 
}