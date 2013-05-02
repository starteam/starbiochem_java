package molecule.ui;

import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.event.ChangeListener;

import star.annotations.SignalComponent;

@SignalComponent(extend=JPanel.class)
public abstract class PropertyControls extends PropertyControls_generated implements ChangeListener
{
    private static final long serialVersionUID = 1L;
	private PropertyLabel propertyLabel = null;
	public PropertyLabel getPropertyLabel() { return propertyLabel; }
	
	private PropertyValue propertyValue = null;		
	public PropertyValue getPropertyValue() { return propertyValue; }
	
	private PropertySlider propertySlider = null;
	public PropertySlider getPropertySlider() { return propertySlider; }
	
	private boolean showAdjusting = false;
	public boolean showAdjusting()
	{
		return showAdjusting;
	}
	public void setShowAdjusting(boolean showAdjusting)
	{
		this.showAdjusting = showAdjusting;
	}
	
	public PropertyControls(String name, int min, int max, int value) {
		this.propertyLabel = new PropertyLabel(name);
		this.propertyValue = new PropertyValue(value);
		this.propertySlider = new PropertySlider(min, max, value);
	}

	public Insets getInsets()
	{
		return new Insets(0,0,0,0);
	}

	public Insets getInsets(Insets insets)
	{
		return new Insets(0,0,0,0);
	}

	public int getValue()
    {
		return this.getPropertySlider().getValue();
    }

	public void initTree()
	{
		this.propertySlider.addChangeListener(this);
	}
}
