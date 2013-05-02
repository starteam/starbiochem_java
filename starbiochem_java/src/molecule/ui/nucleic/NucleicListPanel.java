package molecule.ui.nucleic;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import molecule.ui.AbstractMoleculeUIListPanel;
import star.annotations.SignalComponent;
import app.Messages;

@star.annotations.Preferences
@SignalComponent(extend = AbstractMoleculeUIListPanel.class)
public class NucleicListPanel extends NucleicListPanel_generated
{
	private static final long serialVersionUID = 1L;

	public String nucleic_select_string = Messages.getString("NucleicListPanel.0"); //$NON-NLS-1$
	public String nucleic_list_title = Messages.getString("NucleicListPanel.1"); //$NON-NLS-1$

	private String[] nucleics = null;
	
	public NucleicListPanel(String[] nucleics)
	{
		super();
		this.nucleics = nucleics;
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
		loadPreferences("nucleic"); //$NON-NLS-1$

		setLayout(new BorderLayout());
		setBorder(new TitledBorder(nucleic_select_string));

		list = new NucleicList(this.nucleics);
		JScrollPane srp = new JScrollPane(); 
		srp.setViewportView(list);
		srp.setViewportBorder(new TitledBorder(nucleic_list_title));
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
				((NucleicList)list).initTree();
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
				((NucleicList)list).reset();
				inReset = false;
			}
		}
	}
	
	public String[] getDefaultSelection()
	{
		if(null != list)
		{
			return ((NucleicList)list).getDefaultSelection();
		}
		return null;
	}
	
	protected void loadPreferences(String preferencesName)
	{
		nucleic_list_title = getPreferences(preferencesName).get("list_title", nucleic_list_title).trim(); //$NON-NLS-1$
		nucleic_select_string = getPreferences(preferencesName).get("select_string", nucleic_select_string).trim(); //$NON-NLS-1$
	}

}

