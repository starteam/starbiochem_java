package molecule.ui.water;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import molecule.ui.water.signal.WaterApplyRenderingRaiser;
import molecule.ui.water.signal.WaterRenderingModeRaiser;
import star.annotations.SignalComponent;
import app.Messages;

@star.annotations.Preferences
@SignalComponent(extend = JPanel.class, raises = {WaterRenderingModeRaiser.class, WaterApplyRenderingRaiser.class})
public class WaterRenderingModePanel extends WaterRenderingModePanel_generated  implements ItemListener, ActionListener
{
	private static final long serialVersionUID = 1L;

	JRadioButton water_rendering_automatic_mode = null;
	JRadioButton water_rendering_button_mode = null;
	JButton water_apply_rendering_button = null;
	
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

	private void init()
	{
		loadPreferences("water"); //$NON-NLS-1$
		
		setBorder(new TitledBorder("")); //$NON-NLS-1$

		water_rendering_automatic_mode = new JRadioButton(water_rendering_automatic_mode_string);
		water_rendering_automatic_mode.setMnemonic(water_rendering_automatic_mode_vkey);
		water_rendering_automatic_mode.setSelected(water_rendering_automatic_mode_isselected);

		water_rendering_button_mode = new JRadioButton(water_rendering_button_mode_string);
		water_rendering_button_mode.setMnemonic(water_rendering_button_mode_vkey);
		water_rendering_button_mode.setSelected(water_rendering_button_mode_isselected);

		water_apply_rendering_button = new JButton(water_apply_rendering_button_string);
		water_apply_rendering_button.setMnemonic(water_apply_rendering_button_vkey);
		
		ButtonGroup group = new ButtonGroup();
		group.add(water_rendering_automatic_mode);
		group.add(water_rendering_button_mode);

		setLayout(new GridLayout(3,1));
		add(water_rendering_automatic_mode);
		add(water_rendering_button_mode);
		add(water_apply_rendering_button);
	}
	
	private void end()
	{
		removeAll();
		water_rendering_automatic_mode.removeItemListener(this);
		water_rendering_button_mode.removeItemListener(this);
		water_apply_rendering_button.removeActionListener(this);
		water_rendering_automatic_mode = null;
		water_rendering_button_mode = null;
		water_apply_rendering_button = null;
	}
	
	public void actionPerformed(ActionEvent arg0)
    {
        this.raise_WaterApplyRenderingEvent();
    }

	boolean inItemStateChanged = false;
	public void itemStateChanged(ItemEvent e)
    {
		if(!inItemStateChanged && !inReset)
		{
			inItemStateChanged = true;
			Object source = e.getSource();
	        if (source.equals(water_rendering_automatic_mode))
	        {
	        	this.water_rendering_automatic_mode_isselected = water_rendering_automatic_mode.isSelected();
	        	if(this.water_rendering_automatic_mode_isselected)
	        	{
	        		this.water_apply_rendering_button.setEnabled(false);
	        	}
	        }
	        if (source.equals(water_rendering_button_mode))
	        {
		        this.water_rendering_button_mode_isselected = water_rendering_button_mode.isSelected();
		        if(this.water_rendering_button_mode_isselected)
		        {
		        	this.water_apply_rendering_button.setEnabled(true);
		        }
	        }
	        this.raise_WaterRenderingModeEvent();
	        inItemStateChanged = false;
		}
    }

	private boolean inInitTree = false;
	public void initTree()
	{
		if(!inItemStateChanged && !inInitTree)
		{
			inInitTree = true;
			initLocalVariables();
			if(null != water_rendering_automatic_mode)
			{
				water_rendering_automatic_mode.setSelected(true);
				water_rendering_automatic_mode.addItemListener(this);
			}
			if(null != water_apply_rendering_button)
			{
				water_apply_rendering_button.setEnabled(false);
				water_apply_rendering_button.addActionListener(this);
			}
			if(null != water_rendering_button_mode)
			{
				water_rendering_button_mode.addItemListener(this);
			}
			inInitTree = false;
		}
	}
	
	private void initLocalVariables()
	{
		water_rendering_automatic_mode_isselected = true;
		water_rendering_button_mode_isselected = false;
	}

	private boolean inReset = false;
	public void reset()
	{
		if(!inItemStateChanged && !inReset)
		{
			inReset = true;
			resetLocalVariables();
			if(null != water_rendering_automatic_mode)
			{
				water_rendering_automatic_mode.setSelected(true);
			}
			if(null != water_apply_rendering_button)
			{
				water_apply_rendering_button.setEnabled(false);
			}
			inReset = false;
		}
	}
	
	private void resetLocalVariables()
	{
		water_rendering_automatic_mode_isselected = true;
		water_rendering_button_mode_isselected = false;
	}

	protected boolean default_water_rendering_automatic_mode_isselected = true;
	protected boolean water_rendering_automatic_mode_isselected = true;
	protected boolean water_rendering_button_mode_isselected = false;
	
	protected String water_rendering_automatic_mode_string = Messages.getString("WaterRenderingModePanel.2"); //$NON-NLS-1$
	protected String water_rendering_button_mode_string = Messages.getString("WaterRenderingModePanel.3"); //$NON-NLS-1$
	protected String water_apply_rendering_button_string = Messages.getString("WaterRenderingModePanel.4"); //$NON-NLS-1$
	
	protected int water_rendering_automatic_mode_vkey = KeyEvent.VK_0;
	protected int water_rendering_button_mode_vkey = KeyEvent.VK_1;
	protected int water_apply_rendering_button_vkey = KeyEvent.VK_1;
	
	protected void loadPreferences(String preferencesName)
	{
		String s = getPreferences(preferencesName).get("water_rendering_button_mode_isselected", Boolean.toString(water_rendering_button_mode_isselected)); //$NON-NLS-1$
		water_rendering_button_mode_isselected = Boolean.parseBoolean(s);

		s = getPreferences(preferencesName).get("water_rendering_automatic_mode_isselected", Boolean.toString(water_rendering_automatic_mode_isselected)); //$NON-NLS-1$
		default_water_rendering_automatic_mode_isselected = water_rendering_automatic_mode_isselected = Boolean.parseBoolean(s);

		water_rendering_button_mode_string = getPreferences(preferencesName).get("water_rendering_button_mode_string", water_rendering_button_mode_string); //$NON-NLS-1$
		water_rendering_automatic_mode_string = getPreferences(preferencesName).get("water_rendering_automatic_mode_string", water_rendering_automatic_mode_string); //$NON-NLS-1$
		water_apply_rendering_button_string = getPreferences(preferencesName).get("water_apply_rendering_button_string",water_apply_rendering_button_string); //$NON-NLS-1$

		s = getPreferences(preferencesName).get("water_rendering_button_mode_vkey", Integer.toString(water_rendering_button_mode_vkey)); //$NON-NLS-1$
		water_rendering_button_mode_vkey = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("water_rendering_automatic_mode_vkey", Integer.toString(water_rendering_automatic_mode_vkey)); //$NON-NLS-1$
		water_rendering_automatic_mode_vkey = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("water_apply_rendering_button_vkey", Integer.toString(water_apply_rendering_button_vkey)); //$NON-NLS-1$
		water_rendering_automatic_mode_vkey = Integer.parseInt(s);
	}

	
	public boolean isAutomaticallyRendered()
    {
		return water_rendering_automatic_mode_isselected;
    }

	public boolean isDefaultAutomaticallyRendered()
    {
		return default_water_rendering_automatic_mode_isselected;
    }

}
