package molecule.ui.adjust.center.protein.secondary;

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
import molecule.ui.adjust.center.protein.secondary.signal.CenterProteinSecondaryFilterRaiser;
import molecule.ui.signal.RenderingInfoRaiser;
import star.annotations.SignalComponent;

@star.annotations.Preferences
@SignalComponent(extend = AbstractFiltersUI.class, raises = {CenterProteinSecondaryFilterRaiser.class})
public class CenterProteinSecondaryFilters extends CenterProteinSecondaryFilters_generated  implements ItemListener, ActionListener
{
	private static final long serialVersionUID = 1L;

	JButton proteinsecondary_all = null;
	JCheckBox proteinsecondary_alphahelix = null;
	JCheckBox proteinsecondary_betasheet = null;
	JCheckBox proteinsecondary_coil = null;

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
		loadPreferences("adjust_secondary"); //$NON-NLS-1$
		
		proteinsecondary_all = new JButton(proteinsecondary_all_string);
		proteinsecondary_all.setMnemonic(proteinsecondary_all_vkey);

		proteinsecondary_alphahelix = new JCheckBox(proteinsecondary_alphahelix_string);
		proteinsecondary_alphahelix.setMnemonic(proteinsecondary_alphahelix_vkey);
		proteinsecondary_alphahelix.setSelected(proteinsecondary_alphahelix_isselected);
	
		proteinsecondary_betasheet = new JCheckBox(proteinsecondary_betasheet_string);
		proteinsecondary_betasheet.setMnemonic(proteinsecondary_betasheet_vkey);
		proteinsecondary_betasheet.setSelected(proteinsecondary_betasheet_isselected);
	
		proteinsecondary_coil = new JCheckBox(proteinsecondary_coil_string);
		proteinsecondary_coil.setMnemonic(proteinsecondary_coil_vkey);
		proteinsecondary_coil.setSelected(proteinsecondary_coil_isselected);

        JPanel parts = new JPanel();
		parts.setLayout(new FlowLayout());
		
		parts.add(proteinsecondary_all);
		parts.add(proteinsecondary_alphahelix);
		parts.add(proteinsecondary_betasheet);
		parts.add(proteinsecondary_coil);

		setLayout(new BorderLayout());
		add(BorderLayout.CENTER, parts);
	}
	
	private void end()
	{
		proteinsecondary_all.removeActionListener(this);
        proteinsecondary_alphahelix.removeItemListener(this);
        proteinsecondary_betasheet.removeItemListener(this);
        proteinsecondary_coil.removeItemListener(this);
        removeAll();
		proteinsecondary_all = null;
		proteinsecondary_alphahelix = null;
		proteinsecondary_betasheet = null;
		proteinsecondary_coil = null;
	}

	int filterOptions = 0;
	public int getFilterOptions()
	{
		return this.filterOptions;
	}
	private void setFilterOptions()
	{
		this.filterOptions = 0;
        if (this.proteinsecondary_alphahelix_isselected)
        {
        	this.filterOptions |= molecule.ui.signal.RenderingInfoRaiser.ALPHAHELIX_VISIBLE;
        }
        if (this.proteinsecondary_betasheet_isselected)
        {
        	this.filterOptions |= molecule.ui.signal.RenderingInfoRaiser.BETASHEET_VISIBLE;
        }
        if (this.proteinsecondary_coil_isselected)
        {
        	this.filterOptions |= molecule.ui.signal.RenderingInfoRaiser.COIL_VISIBLE;
        }
	}
	
	public void actionPerformed(ActionEvent e)
    {
		Object source = e.getSource();
		boolean isSourceProteinSecondaryAll = (source.hashCode() == proteinsecondary_all.hashCode());
		if (isSourceProteinSecondaryAll)
		{
    		proteinsecondary_alphahelix_isselected = true;
    		proteinsecondary_betasheet_isselected = true;
    		proteinsecondary_coil_isselected = true;

    		proteinsecondary_alphahelix.setSelected(proteinsecondary_alphahelix_isselected);
    		proteinsecondary_betasheet.setSelected(proteinsecondary_betasheet_isselected);
    		proteinsecondary_coil.setSelected(proteinsecondary_coil_isselected);
       }
    }

	private boolean inItemStateChanged = false;
	public synchronized void itemStateChanged(ItemEvent e)
    {
		if(!inItemStateChanged && !inReset)
		{
			inItemStateChanged = true;
			Object source = e.getSource();
			boolean isSourceProteinSecondaryAlphahelix = (source.hashCode() == proteinsecondary_alphahelix.hashCode());
			boolean isSourceProteinSecondaryBetasheet = (source.hashCode() == proteinsecondary_betasheet.hashCode());
			boolean isSourceProteinSecondaryCoil = (source.hashCode() == proteinsecondary_coil.hashCode());
			if((source.equals(proteinsecondary_alphahelix)) || (source.equals(proteinsecondary_betasheet)) || (source.equals(proteinsecondary_coil)))
			{	        
				if (isSourceProteinSecondaryAlphahelix)
		        {
		        	proteinsecondary_alphahelix_isselected = proteinsecondary_alphahelix.isSelected();
		        }
				else if (isSourceProteinSecondaryBetasheet)
		        {
		        	proteinsecondary_betasheet_isselected = proteinsecondary_betasheet.isSelected();
		        }
				else if (isSourceProteinSecondaryCoil)
		        {
		        	proteinsecondary_coil_isselected = proteinsecondary_coil.isSelected();
		        }
				setFilterOptions();
		        this.raise_CenterProteinSecondaryFilterEvent();
			}
			inItemStateChanged = false;
		}
    }

	private boolean inInitTree = false;
	public void initTree()
	{
		if(!inItemStateChanged && !inInitTree)
		{
			inInitTree = true;
			resetLocalVariables();
			if(null != proteinsecondary_alphahelix)
			{
				proteinsecondary_alphahelix.setSelected(default_proteinsecondary_alphahelix_isselected);
		        proteinsecondary_alphahelix.addItemListener(this);
			}
			if(null != proteinsecondary_betasheet)
			{
				proteinsecondary_betasheet.setSelected(default_proteinsecondary_betasheet_isselected);
		        proteinsecondary_betasheet.addItemListener(this);
			}
			if(null != proteinsecondary_coil)
			{
				proteinsecondary_coil.setSelected(default_proteinsecondary_coil_isselected);
		        proteinsecondary_coil.addItemListener(this);
			}
			if(null != proteinsecondary_all)
			{
				proteinsecondary_all.addActionListener(this);
			}
			setFilterOptions();
			initLocalVariables();
			inInitTree = false;
		}
	}

	private void initLocalVariables()
	{
		proteinsecondary_alphahelix_isselected = default_proteinsecondary_alphahelix_isselected;
		proteinsecondary_betasheet_isselected = default_proteinsecondary_betasheet_isselected;
		proteinsecondary_coil_isselected = default_proteinsecondary_coil_isselected;
	}
	
	private boolean inReset = false;
	public void reset()
	{
		if(!inItemStateChanged && !inReset)
		{
			inReset = true;
			resetLocalVariables();
			if(null != proteinsecondary_alphahelix)
			{
				proteinsecondary_alphahelix.setSelected(default_proteinsecondary_alphahelix_isselected);
			}
			if(null != proteinsecondary_betasheet)
			{
				proteinsecondary_betasheet.setSelected(default_proteinsecondary_betasheet_isselected);
			}
			if(null != proteinsecondary_coil)
			{
				proteinsecondary_coil.setSelected(default_proteinsecondary_coil_isselected);
			}
			setFilterOptions();
			inReset = false;
		}
	}

	private void resetLocalVariables()
	{
		proteinsecondary_alphahelix_isselected = default_proteinsecondary_alphahelix_isselected;
		proteinsecondary_betasheet_isselected = default_proteinsecondary_betasheet_isselected;
		proteinsecondary_coil_isselected = default_proteinsecondary_coil_isselected;
	}
	
	public int getDefaultFilter()
	{
		return (RenderingInfoRaiser.ALPHAHELIX_VISIBLE | RenderingInfoRaiser.BETASHEET_VISIBLE | RenderingInfoRaiser.COIL_VISIBLE);
	}

	protected boolean default_proteinsecondary_alphahelix_isselected = false;
	protected boolean default_proteinsecondary_betasheet_isselected = false;
	protected boolean default_proteinsecondary_coil_isselected = false;
	
	protected boolean proteinsecondary_alphahelix_isselected = default_proteinsecondary_alphahelix_isselected;
	protected boolean proteinsecondary_betasheet_isselected = default_proteinsecondary_betasheet_isselected;
	protected boolean proteinsecondary_coil_isselected = default_proteinsecondary_coil_isselected;

	
	protected String proteinsecondary_all_string = Messages.getString("CenterProteinSecondaryFilters.0"); //$NON-NLS-1$
	protected String proteinsecondary_alphahelix_string = Messages.getString("CenterProteinSecondaryFilters.1"); //$NON-NLS-1$
	protected String proteinsecondary_betasheet_string = Messages.getString("CenterProteinSecondaryFilters.2"); //$NON-NLS-1$
	protected String proteinsecondary_coil_string = Messages.getString("CenterProteinSecondaryFilters.3"); //$NON-NLS-1$

	protected int proteinsecondary_all_vkey = KeyEvent.VK_A;
	protected int proteinsecondary_alphahelix_vkey = KeyEvent.VK_H;
	protected int proteinsecondary_betasheet_vkey = KeyEvent.VK_S;
	protected int proteinsecondary_coil_vkey = KeyEvent.VK_C;
	
	protected void loadPreferences(String preferencesName)
	{
		String s = getPreferences(preferencesName).get("proteinsecondary_alphahelix_isselected", Boolean.toString(proteinsecondary_alphahelix_isselected)); //$NON-NLS-1$
		default_proteinsecondary_alphahelix_isselected = proteinsecondary_alphahelix_isselected = Boolean.parseBoolean(s);

		s = getPreferences(preferencesName).get("proteinsecondary_betasheet_isselected", Boolean.toString(proteinsecondary_betasheet_isselected)); //$NON-NLS-1$
		default_proteinsecondary_betasheet_isselected = proteinsecondary_betasheet_isselected = Boolean.parseBoolean(s);

		s = getPreferences(preferencesName).get("proteinsecondary_coil_isselected", Boolean.toString(proteinsecondary_coil_isselected)); //$NON-NLS-1$
		default_proteinsecondary_coil_isselected = proteinsecondary_coil_isselected = Boolean.parseBoolean(s);


		proteinsecondary_all_string = getPreferences(preferencesName).get("proteinsecondary_all_string", proteinsecondary_all_string); //$NON-NLS-1$
		proteinsecondary_alphahelix_string = getPreferences(preferencesName).get("proteinsecondary_alphahelix_string", proteinsecondary_alphahelix_string); //$NON-NLS-1$
		proteinsecondary_betasheet_string = getPreferences(preferencesName).get("proteinsecondary_betasheet_string", proteinsecondary_betasheet_string); //$NON-NLS-1$
		proteinsecondary_coil_string = getPreferences(preferencesName).get("proteinsecondary_coil_string", proteinsecondary_coil_string); //$NON-NLS-1$

		s = getPreferences(preferencesName).get("proteinsecondary_all_vkey", Integer.toString(proteinsecondary_all_vkey)); //$NON-NLS-1$
		proteinsecondary_all_vkey = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("proteinsecondary_alphahelix_vkey", Integer.toString(proteinsecondary_alphahelix_vkey)); //$NON-NLS-1$
		proteinsecondary_alphahelix_vkey = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("proteinsecondary_betasheet_vkey", Integer.toString(proteinsecondary_betasheet_vkey)); //$NON-NLS-1$
		proteinsecondary_betasheet_vkey = Integer.parseInt(s);

		s = getPreferences(preferencesName).get("proteinsecondary_coil_vkey", Integer.toString(proteinsecondary_coil_vkey)); //$NON-NLS-1$
		proteinsecondary_coil_vkey = Integer.parseInt(s);	
	}

}
