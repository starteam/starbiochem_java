package molecule.ui.nucleic;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import app.Messages;

import molecule.ui.AbstractFiltersUI;
import molecule.ui.nucleic.signal.NucleicFilterRaiser;
import molecule.ui.signal.RenderingInfoRaiser;
import star.annotations.SignalComponent;

@star.annotations.Preferences
@SignalComponent(extend = AbstractFiltersUI.class, raises = {NucleicFilterRaiser.class})
public class NucleicFilters extends NucleicFilters_generated  implements ItemListener, ActionListener
{
    private static final long serialVersionUID = 1L;

    NucleicRenderingModePanel modes = null;
    JButton nucleic_all = null;
    JCheckBox baseatoms = null;
    JCheckBox phosphateatoms = null;
    JCheckBox sugaratoms = null;
	
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
		loadPreferences("nucleic"); //$NON-NLS-1$
		
		nucleic_all = new JButton(nucleic_all_string);
		nucleic_all.setMnemonic(nucleic_all_vkey);

		baseatoms = new JCheckBox(base_string);
		baseatoms.setMnemonic(base_vkey);
		baseatoms.setSelected(base_isselected);

		phosphateatoms = new JCheckBox(phosphate_string);
		phosphateatoms.setMnemonic(phosphate_vkey);
		phosphateatoms.setSelected(phosphate_isselected);

		sugaratoms = new JCheckBox(sugaratoms_string);
		sugaratoms.setMnemonic(sugaratoms_vkey);
		sugaratoms.setSelected(sugaratoms_isselected);

		JPanel filters = new JPanel();
		filters.setLayout(new FlowLayout());

		filters.add(nucleic_all);
		filters.add(baseatoms);
		filters.add(phosphateatoms);
		filters.add(sugaratoms);

		setLayout(new BorderLayout());
		add(BorderLayout.CENTER, filters);
		
		modes = new NucleicRenderingModePanel();
		add(BorderLayout.EAST,modes);
	}
	
	private void end()
	{
		nucleic_all.removeActionListener(this);
        baseatoms.removeItemListener(this);
        phosphateatoms.removeItemListener(this);
        sugaratoms.removeItemListener(this);
		removeAll();
		nucleic_all = null;
		baseatoms = null;
		phosphateatoms = null;
		sugaratoms = null;
		modes = null;
	}

	int value = 0;
	public int getValue()
	{
		return this.value;
	}
	private void setValue()
	{
		this.value = 0;
        if (this.base_isselected)
        {
        	this.value |= molecule.ui.signal.RenderingInfoRaiser.BASE_VISIBLE;
        }
        if (this.phosphate_isselected)
        {
        	this.value |= molecule.ui.signal.RenderingInfoRaiser.PHOSPHATE_VISIBLE;
	    }
        if (this.sugaratoms_isselected)
        {
        	this.value |= molecule.ui.signal.RenderingInfoRaiser.SUGAR_VISIBLE;
        }
	}

	public void actionPerformed(ActionEvent e)
    {
		Object source = e.getSource();
		if(source.equals(nucleic_all))
		{
			if(null != baseatoms)
			{
				baseatoms.setSelected(true);
			}
			if(null != phosphateatoms)
			{
				phosphateatoms.setSelected(true);
			}
			if(null != sugaratoms)
			{
				sugaratoms.setSelected(true);
			}
		}
    }

	private boolean inItemStateChanged = false;
	public void itemStateChanged(ItemEvent e)
    {
		if(!inItemStateChanged && !inReset)
		{
			inItemStateChanged = true;
			Object source = e.getSource();
	        if (source.equals(baseatoms))
	        {
		        this.base_isselected = baseatoms.isSelected();
	        }
	        if (source.equals(phosphateatoms))
	        {
	        	this.phosphate_isselected = phosphateatoms.isSelected();
	        }
	        if (source.equals(sugaratoms))
	        {
	        	this.sugaratoms_isselected = sugaratoms.isSelected();
	        }
		        
			setValue();
			this.raise_NucleicFilterEvent();
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
			if(null != baseatoms)
			{
				baseatoms.setSelected(true);
		        baseatoms.addItemListener(this);
			}
			if(null != phosphateatoms)
			{
				phosphateatoms.setSelected(true);
		        phosphateatoms.addItemListener(this);
			}
			if(null != sugaratoms)
			{
				sugaratoms.setSelected(true);
		        sugaratoms.addItemListener(this);
			}
			if(null != nucleic_all)
			{
				nucleic_all.addActionListener(this);
			}
			if(null != modes)
			{
				modes.initTree();
			}
			inInitTree = false;
		}
	}

	private void initLocalVariables()
	{
		base_isselected = true;
		phosphate_isselected = true;
		sugaratoms_isselected = true;
	}
	
	private boolean inReset = false;
	public void reset()
	{
		if(!inItemStateChanged && !inReset)
		{
			inReset = true;
			resetLocalVariables();
			if(null != baseatoms)
			{
				baseatoms.setSelected(true);
			}
			if(null != phosphateatoms)
			{
				phosphateatoms.setSelected(true);
			}
			if(null != sugaratoms)
			{
				sugaratoms.setSelected(true);
			}
			if(null != modes)
			{
				modes.reset();
			}
			inReset = false;
		}
	}

	private void resetLocalVariables()
	{
		base_isselected = true;
		phosphate_isselected = true;
		sugaratoms_isselected = true;
	}
	
	public int getDefaultFilter()
	{
		return (RenderingInfoRaiser.BASE_VISIBLE | RenderingInfoRaiser.PHOSPHATE_VISIBLE | RenderingInfoRaiser.SUGAR_VISIBLE);
	}

	public boolean isDefaultAutomaticallyRendered()
	{
		if(null != modes)
		{
			return modes.isDefaultAutomaticallyRendered();
		}
		return true;
	}

	protected boolean base_isselected = true;
	protected boolean phosphate_isselected = true;
	protected boolean sugaratoms_isselected = true;

	protected String nucleic_all_string = Messages.getString("NucleicFilters.1"); //$NON-NLS-1$
	protected String base_string = Messages.getString("NucleicFilters.0"); //$NON-NLS-1$
	protected String phosphate_string = Messages.getString("NucleicFilters.3"); //$NON-NLS-1$
	protected String sugaratoms_string = Messages.getString("NucleicFilters.2"); //$NON-NLS-1$
	
	protected int nucleic_all_vkey = KeyEvent.VK_A;
	protected int base_vkey = KeyEvent.VK_B;
	protected int phosphate_vkey = KeyEvent.VK_P;
	protected int sugaratoms_vkey = KeyEvent.VK_S;
	
	protected void loadPreferences(String preferencesName)
	{
		String s = getPreferences(preferencesName).get("filters_centernucleic_base_isselected", Boolean.toString(base_isselected)); //$NON-NLS-1$
		base_isselected = Boolean.parseBoolean(s);

		s = getPreferences(preferencesName).get("phosphate_isselected", Boolean.toString(phosphate_isselected)); //$NON-NLS-1$
		phosphate_isselected = Boolean.parseBoolean(s);

		s = getPreferences(preferencesName).get("allsugaratoms_isselected", Boolean.toString(sugaratoms_isselected)); //$NON-NLS-1$
		sugaratoms_isselected = Boolean.parseBoolean(s);

		nucleic_all_string = getPreferences(preferencesName).get("nucleic_all_string", nucleic_all_string); //$NON-NLS-1$
		base_string = getPreferences(preferencesName).get("filters_centernucleic_base_string", base_string); //$NON-NLS-1$
		phosphate_string = getPreferences(preferencesName).get("filters_centernucleic_phosphate_string", phosphate_string); //$NON-NLS-1$
		sugaratoms_string = getPreferences(preferencesName).get("filters_centernucleic_sugaratoms_string", sugaratoms_string); //$NON-NLS-1$

		s = getPreferences(preferencesName).get("filters_centernucleic_nucleic_all_vkey", Integer.toString(nucleic_all_vkey)); //$NON-NLS-1$
		nucleic_all_vkey = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("filters_centernucleic_base_vkey", Integer.toString(base_vkey)); //$NON-NLS-1$
		base_vkey = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("filters_centernucleic_phosphate_vkey", Integer.toString(phosphate_vkey)); //$NON-NLS-1$
		phosphate_vkey = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("allsugaratoms_vkey", Integer.toString(sugaratoms_vkey)); //$NON-NLS-1$
		sugaratoms_vkey = Integer.parseInt(s);
	}

}
