package molecule.ui.water;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import molecule.ui.AbstractMoleculeUIListPanel;
import star.annotations.SignalComponent;
import app.Messages;

@star.annotations.Preferences
@SignalComponent(extend = AbstractMoleculeUIListPanel.class)
public class WaterListPanel extends WaterListPanel_generated
{
	private static final long serialVersionUID = 1L;

	public String select_string = Messages.getString("WaterListPanel.0"); //$NON-NLS-1$
	public String list_title = Messages.getString("WaterListPanel.1"); //$NON-NLS-1$

	private String[] waters = null;
	
	public WaterListPanel(String[] waters)
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
		setBorder(new TitledBorder(select_string));

		list = new WaterList(waters); 
		JScrollPane srp = new JScrollPane(); 
		srp.setViewportView(list);
		srp.setViewportBorder(new TitledBorder(list_title));
		add(BorderLayout.CENTER, srp);
	}

	private void end()
	{
		removeAll();
		list = null;
	}

	transient private boolean inInitTree = false;
	public void initTree()
	{
		if(null != list)
		{
			if(!inInitTree)
			{
				inInitTree = true;
				((WaterList)list).initTree();
				inInitTree = false;
			}
		}
	}
	
	transient private boolean inReset = false;
	public void reset()
	{
		if(null != list)
		{
			if(!inReset)
			{
				inReset = true;
				((WaterList)list).reset();
				inReset = false;
			}
		}
	}
	
	public String[] getDefaultSelection()
	{
		if(null != list)
		{
			return ((WaterList)list).getDefaultSelection();
		}
		return null;
	}
	
	protected void loadPreferences(String preferencesName)
	{
		list_title = getPreferences(preferencesName).get("list_title", list_title).trim(); //$NON-NLS-1$
		select_string = getPreferences(preferencesName).get("select_string", select_string).trim(); //$NON-NLS-1$
	}

}

