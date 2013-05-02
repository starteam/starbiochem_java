package molecule.ui.adjust.center.water;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import molecule.interfaces.Molecule;
import molecule.ui.AbstractMoleculeUIListPanel;
import star.annotations.SignalComponent;
import app.Messages;

@star.annotations.Preferences
@SignalComponent(extend = AbstractMoleculeUIListPanel.class)
public class CenterWaterListPanel extends CenterWaterListPanel_generated
{
	private static final long serialVersionUID = 1L;

	public String water_select_string = Messages.getString("CenterWaterListPanel.0"); //$NON-NLS-1$
	public String water_list_title = Messages.getString("CenterWaterListPanel.1"); //$NON-NLS-1$

	private String water_string = Messages.getString("CenterWaterListPanel.2"); //$NON-NLS-1$
	public String getTitle()
	{
		return water_string;
	}

	private String[] waters = null;
	
	private CenterWaterListPanel(String[] waters)
	{
		super();
		this.waters = waters;
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

	private void init()
	{
		loadPreferences("water"); //$NON-NLS-1$

		setLayout(new BorderLayout());
		setBorder(new TitledBorder(water_select_string));

		list = new CenterWaterList(waters); 
		JScrollPane srp = new JScrollPane(); 
		srp.setViewportView(list);
		srp.setViewportBorder(new TitledBorder(water_list_title));
		add(BorderLayout.CENTER, srp);
	}

	private void end()
	{
		removeAll();
		list = null;
	}

	private boolean inInitTree = false;
	public void initTree()
	{
		if(null != list)
		{
			if(!inInitTree)
			{
				inInitTree = true;
				((CenterWaterList)list).initTree();
				inInitTree = false;
			}
		}
	}
	
	private boolean inReset = false;
	public void reset()
	{
		if(null != list)
		{
			if(!inReset)
			{
				inReset = true;
				((CenterWaterList)list).reset();
				inReset = false;
			}
		}
	}
	
	public String[] getDefaultSelection()
	{
		if(null != list)
		{
			return ((CenterWaterList)list).getDefaultSelection();
		}
		return null;
	}
	
	protected void loadPreferences(String preferencesName)
	{
		water_list_title = getPreferences(preferencesName).get("list_title", water_list_title).trim(); //$NON-NLS-1$
		water_select_string = getPreferences(preferencesName).get("select_string", water_select_string).trim(); //$NON-NLS-1$
		water_string = getPreferences(preferencesName).get("water_string", water_string).trim(); //$NON-NLS-1$
	}

	public static CenterWaterListPanel getDefaultCenterWaterListPanel(Molecule molecule)
	{
		if(null != molecule)
		{
			String[] array = molecule.getWaterArray();
			if((null != array) && (0 != array.length))
			{
				return new CenterWaterListPanel(array);
			}
		}
		return null;
	}

}

