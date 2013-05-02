package molecule.ui.hetero;

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

import molecule.ui.hetero.signal.HeteroApplyRenderingRaiser;
import molecule.ui.hetero.signal.HeteroRenderingModeRaiser;
import star.annotations.SignalComponent;
import app.Messages;

@star.annotations.Preferences
@SignalComponent(extend = JPanel.class, raises = {HeteroRenderingModeRaiser.class, HeteroApplyRenderingRaiser.class})
public class HeteroRenderingModePanel extends HeteroRenderingModePanel_generated  implements ItemListener, ActionListener
{
	private static final long serialVersionUID = 1L;

	JRadioButton hetero_rendering_automatic_mode = null;
	JRadioButton hetero_rendering_button_mode = null;
	JButton hetero_apply_rendering_button = null;
	
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
		loadPreferences("hetero"); //$NON-NLS-1$
		
		setBorder(new TitledBorder("")); //$NON-NLS-1$

		hetero_rendering_automatic_mode = new JRadioButton(hetero_rendering_automatic_mode_string);
		hetero_rendering_automatic_mode.setMnemonic(hetero_rendering_automatic_mode_vkey);
		hetero_rendering_automatic_mode.setSelected(hetero_rendering_automatic_mode_isselected);

		hetero_rendering_button_mode = new JRadioButton(hetero_rendering_button_mode_string);
		hetero_rendering_button_mode.setMnemonic(hetero_rendering_button_mode_vkey);
		hetero_rendering_button_mode.setSelected(hetero_rendering_button_mode_isselected);

		hetero_apply_rendering_button = new JButton(hetero_apply_rendering_button_string);
		hetero_apply_rendering_button.setMnemonic(hetero_apply_rendering_button_vkey);
		
		ButtonGroup group = new ButtonGroup();
		group.add(hetero_rendering_automatic_mode);
		group.add(hetero_rendering_button_mode);

		setLayout(new GridLayout(3,1));
		add(hetero_rendering_automatic_mode);
		add(hetero_rendering_button_mode);
		add(hetero_apply_rendering_button);
	}
	
	private void end()
	{
		removeAll();
		this.hetero_apply_rendering_button.removeItemListener(this);
		this.hetero_rendering_button_mode.removeItemListener(this);
		this.hetero_rendering_automatic_mode.removeActionListener(this);
		this.hetero_apply_rendering_button = null;
		this.hetero_rendering_button_mode = null;
		this.hetero_rendering_automatic_mode = null;
	}
	
	public void actionPerformed(ActionEvent arg0)
    {
        this.raise_HeteroApplyRenderingEvent();
    }

	boolean inItemStateChanged = false;
	public void itemStateChanged(ItemEvent e)
    {
		if(!inItemStateChanged && !inReset)
		{
			inItemStateChanged = true;
			Object source = e.getSource();
	        if (source.equals(hetero_rendering_automatic_mode))
	        {
	        	this.hetero_rendering_automatic_mode_isselected = hetero_rendering_automatic_mode.isSelected();
	        	if(this.hetero_rendering_automatic_mode_isselected)
	        	{
	        		this.hetero_apply_rendering_button.setEnabled(false);
	        	}
	        }
	        if (source.equals(hetero_rendering_button_mode))
	        {
		        this.hetero_rendering_button_mode_isselected = hetero_rendering_button_mode.isSelected();
		        if(this.hetero_rendering_button_mode_isselected)
		        {
		        	this.hetero_apply_rendering_button.setEnabled(true);
		        }
	        }
	        this.raise_HeteroRenderingModeEvent();
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
			hetero_rendering_automatic_mode.addItemListener(this);
			hetero_rendering_button_mode.addItemListener(this);
			hetero_apply_rendering_button.addActionListener(this);
			inInitTree = false;
		}
	}

	private void initLocalVariables()
	{
		hetero_rendering_automatic_mode_isselected = true;
		hetero_rendering_button_mode_isselected = false;
	}

	private boolean inReset = false;
	public void reset()
	{
		if(!inItemStateChanged && !inReset)
		{
			inReset = true;
			resetLocalVariables();
			inReset = false;
		}
	}

	private void resetLocalVariables()
	{
		hetero_rendering_automatic_mode_isselected = true;
		hetero_rendering_button_mode_isselected = false;
	}

	protected boolean default_hetero_rendering_automatic_mode_isselected = true;
	protected boolean hetero_rendering_automatic_mode_isselected = true;
	protected boolean hetero_rendering_button_mode_isselected = false;
	
	protected String hetero_rendering_automatic_mode_string = Messages.getString("HeteroRenderingModePanel.2"); //$NON-NLS-1$
	protected String hetero_rendering_button_mode_string = Messages.getString("HeteroRenderingModePanel.3"); //$NON-NLS-1$
	protected String hetero_apply_rendering_button_string = Messages.getString("HeteroRenderingModePanel.4"); //$NON-NLS-1$
	
	protected int hetero_rendering_automatic_mode_vkey = KeyEvent.VK_0;
	protected int hetero_rendering_button_mode_vkey = KeyEvent.VK_1;
	protected int hetero_apply_rendering_button_vkey = KeyEvent.VK_1;
	
	protected void loadPreferences(String preferencesName)
	{
		String s = getPreferences(preferencesName).get("hetero_rendering_button_mode_isselected", Boolean.toString(hetero_rendering_button_mode_isselected)); //$NON-NLS-1$
		hetero_rendering_button_mode_isselected = Boolean.parseBoolean(s);

		s = getPreferences(preferencesName).get("hetero_rendering_automatic_mode_isselected", Boolean.toString(hetero_rendering_automatic_mode_isselected)); //$NON-NLS-1$
		default_hetero_rendering_automatic_mode_isselected = hetero_rendering_automatic_mode_isselected = Boolean.parseBoolean(s);

		hetero_rendering_button_mode_string = getPreferences(preferencesName).get("hetero_rendering_button_mode_string", hetero_rendering_button_mode_string); //$NON-NLS-1$
		hetero_rendering_automatic_mode_string = getPreferences(preferencesName).get("hetero_rendering_automatic_mode_string", hetero_rendering_automatic_mode_string); //$NON-NLS-1$
		hetero_apply_rendering_button_string = getPreferences(preferencesName).get("hetero_apply_rendering_button_string",hetero_apply_rendering_button_string); //$NON-NLS-1$

		s = getPreferences(preferencesName).get("hetero_rendering_button_mode_vkey", Integer.toString(hetero_rendering_button_mode_vkey)); //$NON-NLS-1$
		hetero_rendering_button_mode_vkey = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("hetero_rendering_automatic_mode_vkey", Integer.toString(hetero_rendering_automatic_mode_vkey)); //$NON-NLS-1$
		hetero_rendering_automatic_mode_vkey = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("hetero_apply_rendering_button_vkey", Integer.toString(hetero_apply_rendering_button_vkey)); //$NON-NLS-1$
		hetero_rendering_automatic_mode_vkey = Integer.parseInt(s);
	}

	
	public boolean isAutomaticallyRendered()
    {
		return hetero_rendering_automatic_mode_isselected;
    }

	public boolean isDefaultAutomaticallyRendered()
    {
		return default_hetero_rendering_automatic_mode_isselected;
    }

}
