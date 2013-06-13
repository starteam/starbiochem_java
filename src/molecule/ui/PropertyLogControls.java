package molecule.ui;

import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.event.ChangeListener;

import star.annotations.SignalComponent;

@SignalComponent(extend=JPanel.class)
public abstract class PropertyLogControls extends PropertyLogControls_generated implements ChangeListener
{
    private static final long serialVersionUID = 1L;
	private PropertyLabel propertyLabel = null;
	public PropertyLabel getPropertyLabel() { return propertyLabel; }
	
	private PropertyValue propertyValue = null;		
	public PropertyValue getPropertyValue() { return propertyValue; }
	
	private PropertyLogSlider propertyLogSlider = null;
	public PropertyLogSlider getPropertyLogSlider() { return propertyLogSlider; }
	
	private boolean showAdjusting = false;
	public boolean showAdjusting()
	{
		return showAdjusting;
	}
	public void setShowAdjusting(boolean showAdjusting)
	{
		this.showAdjusting = showAdjusting;
	}
	
	final int userMIN = 1;
	final int userMAX = 1000;
	final double logUserMin = Math.log10(userMIN);
	final double logUserMax = Math.log10(userMAX);
	final double logUserSpan = logUserMax - logUserMin;
	
	final int sliderResolution = 1000;
	final int sliderMin = 0;
	final int sliderMax = sliderResolution - 1;

	double sliderScale = sliderResolution/logUserSpan;
	
	public PropertyLogControls(String name, int value) {
		this.propertyLabel = new PropertyLabel(name);
		if((value>=userMIN) && (userMAX>value))
		{
			this.propertyValue = new PropertyValue(value);
			int sliderValue = (int) Math.round(sliderScale * (Math.log10(value) - logUserMin)); 
			this.propertyLogSlider = new PropertyLogSlider(sliderMin, sliderMax, sliderValue);
		}
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
		int value = 0;
		if(null != this.propertyLogSlider)
		{
			int sliderValue = this.propertyLogSlider.getValue();
			double u = Math.pow(10, sliderValue/sliderScale); 
			value = (int) Math.round(u);
		}
		return value;
    }

	public void setValue(int value)
    {
		if(null != this.propertyLogSlider)
		{
			this.propertyValue.setValue(value);
			int sliderValue = (int) Math.round(sliderScale * (Math.log10(value) - logUserMin)); 
			this.getPropertyLogSlider().setValue(sliderValue);
		}
    }
	
	public void initTree()
	{
		this.propertyLogSlider.addChangeListener(this);
	}

}
