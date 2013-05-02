package molecule.ui.structureoptions;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;import javax.swing.JPanel;
import molecule.ui.structureoptions.center.backgroundcolor.BackgroundColorChooser;
import molecule.ui.structureoptions.center.foreground.MoleculeJmolRenderingPropertiesPanel;
import star.annotations.SignalComponent;


@star.annotations.Preferences
@SignalComponent(extend = JPanel.class)
public class ViewerOptionsPanel extends ViewerOptionsPanel_generated  
{
	private static final long serialVersionUID = 1L;
	transient private BackgroundColorChooser background;
	transient private MoleculeJmolRenderingPropertiesPanel foreground;
	CardLayout cl;

	public ViewerOptionsPanel()
	{
		super();
	}
	
	private boolean isInitialized = false;
	public void addNotify()
	{
		super.addNotify();
		if(!isInitialized)
		{
			init();
			isInitialized = true;
		}
	}

	public void removeNotify()
	{
		end();
		isInitialized = false;
		super.removeNotify();
	}

	public void init()
	{
		background = new BackgroundColorChooser(this.getBackground());
		foreground = new MoleculeJmolRenderingPropertiesPanel();
		
		cl = new CardLayout();
		setLayout(cl);
		add(background, "background"); //$NON-NLS-1$
		add(foreground, "foreground"); //$NON-NLS-1$

		invalidate();
		validate();
	}
	
	public void showBackground()
	{
		if(null != background)
		{
			cl.show(this, "background"); //$NON-NLS-1$
		}
	}
	public void showForeground()
	{
		if(null != foreground)
		{
			cl.show(this, "foreground"); //$NON-NLS-1$
		}
	}
	
	public Color getMyColor()
	{
		Color myColor = background.moleculesBackgroundColor;
		return myColor;
	}

	private void end()
	{
		removeAll();
		background = null;
		foreground = null;
	}

	public Dimension getMinimumSize()
	{
		return getPreferredSize();
	}
	
}