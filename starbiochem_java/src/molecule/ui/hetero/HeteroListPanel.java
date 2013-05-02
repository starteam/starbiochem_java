package molecule.ui.hetero;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import molecule.ui.AbstractMoleculeUIListPanel;
import star.annotations.SignalComponent;
import app.Messages;

@star.annotations.Preferences
@SignalComponent(extend = AbstractMoleculeUIListPanel.class)
public class HeteroListPanel extends HeteroListPanel_generated
{
	private static final long serialVersionUID = 1L;

	public String hetero_select_string = Messages.getString("HeteroListPanel.0"); //$NON-NLS-1$
	public String hetero_list_title = Messages.getString("HeteroListPanel.1"); //$NON-NLS-1$

	private String[] heteros = null;
	
	public HeteroListPanel(String[] heteros)
	{
		super();
		this.heteros = heteros;
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
		loadPreferences("hetero"); //$NON-NLS-1$

		setLayout(new BorderLayout());
		setBorder(new TitledBorder(hetero_select_string));

		list = new HeteroList(this.heteros);
		JScrollPane srp = new JScrollPane(); 
		srp.setViewportView(list);
		srp.setViewportBorder(new TitledBorder(hetero_list_title));
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
				((HeteroList)list).initTree();
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
				((HeteroList)list).reset();
				inReset = false;
			}
		}
	}
	
	public String[] getDefaultSelection()
	{
		if(null != list)
		{
			return ((HeteroList)list).getDefaultSelection();
		}
		return null;
	}
	
	protected void loadPreferences(String preferencesName)
	{
		hetero_list_title = getPreferences(preferencesName).get("list_title", hetero_list_title).trim(); //$NON-NLS-1$
		hetero_select_string = getPreferences(preferencesName).get("select_string", hetero_select_string).trim(); //$NON-NLS-1$
	}

}

