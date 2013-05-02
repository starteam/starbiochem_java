package molecule.ui.adjust.center.nucleic;

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

import molecule.ui.AbstractFiltersUI;
import molecule.ui.adjust.center.nucleic.signal.CenterNucleicFilterRaiser;
import molecule.ui.signal.RenderingInfoRaiser;
import star.annotations.SignalComponent;
import app.Messages;

@star.annotations.Preferences
@SignalComponent(extend = AbstractFiltersUI.class, raises = {CenterNucleicFilterRaiser.class})
public class CenterNucleicFilters extends CenterNucleicFilters_generated  implements ItemListener, ActionListener
{
    private static final long serialVersionUID = 1L;

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
		loadPreferences("adjust_nucleic"); //$NON-NLS-1$
		
		nucleic_all = new JButton(filters_centernucleic_nucleic_all_string);
		nucleic_all.setMnemonic(filters_centernucleic_nucleic_all_vkey);

		baseatoms = new JCheckBox(filters_centernucleic_base_string);
		baseatoms.setMnemonic(filters_centernucleic_base_vkey);
		baseatoms.setSelected(filters_centernucleic_base_isselected);

		phosphateatoms = new JCheckBox(filters_centernucleic_phosphate_string);
		phosphateatoms.setMnemonic(filters_centernucleic_phosphate_vkey);
		phosphateatoms.setSelected(filters_centernucleic_phosphate_isselected);

		sugaratoms = new JCheckBox(filters_centernucleic_sugaratoms_string);
		sugaratoms.setMnemonic(filters_centernucleic_sugaratoms_vkey);
		sugaratoms.setSelected(filters_centernucleic_sugaratoms_isselected);

		JPanel filters = new JPanel();
		filters.setLayout(new FlowLayout());

		filters.add(nucleic_all);
		filters.add(baseatoms);
		filters.add(phosphateatoms);
		filters.add(sugaratoms);

		setLayout(new BorderLayout());
		add(BorderLayout.CENTER, filters);
	}
	
	private void end()
	{
		if(null != baseatoms)
		{
	        baseatoms.removeItemListener(this);
		}
		if(null != phosphateatoms)
		{
	        phosphateatoms.removeItemListener(this);
		}
		if(null != sugaratoms)
		{
	        sugaratoms.removeItemListener(this);
		}
		if(null != nucleic_all)
		{
			nucleic_all.removeActionListener(this);
		}
		removeAll();
		nucleic_all = null;
		baseatoms = null;
		phosphateatoms = null;
		sugaratoms = null;
	}

	int filterOptions = 0;
	public int getFilterOptions()
	{
		return this.filterOptions;
	}
	private void setFilterOptions()
	{
		this.filterOptions = 0;
        if (this.filters_centernucleic_base_isselected)
        {
        	this.filterOptions |= molecule.ui.signal.RenderingInfoRaiser.BASE_VISIBLE;
        }
        if (this.filters_centernucleic_phosphate_isselected)
        {
        	this.filterOptions |= molecule.ui.signal.RenderingInfoRaiser.PHOSPHATE_VISIBLE;
	    }
        if (this.filters_centernucleic_sugaratoms_isselected)
        {
        	this.filterOptions |= molecule.ui.signal.RenderingInfoRaiser.SUGAR_VISIBLE;
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
		        this.filters_centernucleic_base_isselected = baseatoms.isSelected();
	        }
	        if (source.equals(phosphateatoms))
	        {
	        	this.filters_centernucleic_phosphate_isselected = phosphateatoms.isSelected();
	        }
	        if (source.equals(sugaratoms))
	        {
	        	this.filters_centernucleic_sugaratoms_isselected = sugaratoms.isSelected();
	        }
		        
			setFilterOptions();
			this.raise_CenterNucleicFilterEvent();
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
			inInitTree = false;
		}
	}

	private void initLocalVariables()
	{
		filters_centernucleic_base_isselected = true;
		filters_centernucleic_phosphate_isselected = true;
		filters_centernucleic_sugaratoms_isselected = true;
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
			inReset = false;
		}
	}

	private void resetLocalVariables()
	{
		filters_centernucleic_base_isselected = true;
		filters_centernucleic_phosphate_isselected = true;
		filters_centernucleic_sugaratoms_isselected = true;
	}
	
	public int getDefaultFilter()
	{
		return (RenderingInfoRaiser.BASE_VISIBLE | RenderingInfoRaiser.PHOSPHATE_VISIBLE | RenderingInfoRaiser.SUGAR_VISIBLE);
	}

	public boolean isDefaultAutomaticallyRendered()
	{
		return true;
	}

	protected boolean filters_centernucleic_base_isselected = false;
	protected boolean filters_centernucleic_phosphate_isselected = false;
	protected boolean filters_centernucleic_sugaratoms_isselected = false;

	protected String filters_centernucleic_nucleic_all_string = Messages.getString("CenterNucleicFilters.1"); //$NON-NLS-1$
	protected String filters_centernucleic_base_string = Messages.getString("CenterNucleicFilters.2"); //$NON-NLS-1$
	protected String filters_centernucleic_phosphate_string = Messages.getString("CenterNucleicFilters.3"); //$NON-NLS-1$
	protected String filters_centernucleic_sugaratoms_string = Messages.getString("CenterNucleicFilters.4"); //$NON-NLS-1$
	
	protected int filters_centernucleic_nucleic_all_vkey = KeyEvent.VK_A;
	protected int filters_centernucleic_base_vkey = KeyEvent.VK_B;
	protected int filters_centernucleic_phosphate_vkey = KeyEvent.VK_P;
	protected int filters_centernucleic_sugaratoms_vkey = KeyEvent.VK_S;
	
	protected void loadPreferences(String preferencesName)
	{
		String s = getPreferences(preferencesName).get("filters_centernucleic_base_isselected", Boolean.toString(filters_centernucleic_base_isselected)); //$NON-NLS-1$
		filters_centernucleic_base_isselected = Boolean.parseBoolean(s);

		s = getPreferences(preferencesName).get("filters_centernucleic_phosphate_isselected", Boolean.toString(filters_centernucleic_phosphate_isselected)); //$NON-NLS-1$
		filters_centernucleic_phosphate_isselected = Boolean.parseBoolean(s);

		s = getPreferences(preferencesName).get("filters_centernucleic_allsugaratoms_isselected", Boolean.toString(filters_centernucleic_sugaratoms_isselected)); //$NON-NLS-1$
		filters_centernucleic_sugaratoms_isselected = Boolean.parseBoolean(s);

		filters_centernucleic_nucleic_all_string = getPreferences(preferencesName).get("filters_centernucleic_nucleic_all_string", filters_centernucleic_nucleic_all_string); //$NON-NLS-1$
		filters_centernucleic_base_string = getPreferences(preferencesName).get("filters_centernucleic_base_string", filters_centernucleic_base_string); //$NON-NLS-1$
		filters_centernucleic_phosphate_string = getPreferences(preferencesName).get("filters_centernucleic_phosphate_string", filters_centernucleic_phosphate_string); //$NON-NLS-1$
		filters_centernucleic_sugaratoms_string = getPreferences(preferencesName).get("filters_centernucleic_sugaratoms_string", filters_centernucleic_sugaratoms_string); //$NON-NLS-1$

		s = getPreferences(preferencesName).get("filters_centernucleic_nucleic_all_vkey", Integer.toString(filters_centernucleic_nucleic_all_vkey)); //$NON-NLS-1$
		filters_centernucleic_nucleic_all_vkey = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("filters_centernucleic_base_vkey", Integer.toString(filters_centernucleic_base_vkey)); //$NON-NLS-1$
		filters_centernucleic_base_vkey = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("filters_centernucleic_phosphate_vkey", Integer.toString(filters_centernucleic_phosphate_vkey)); //$NON-NLS-1$
		filters_centernucleic_phosphate_vkey = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("filters_centernucleic_allsugaratoms_vkey", Integer.toString(filters_centernucleic_sugaratoms_vkey)); //$NON-NLS-1$
		filters_centernucleic_sugaratoms_vkey = Integer.parseInt(s);
	}

}
