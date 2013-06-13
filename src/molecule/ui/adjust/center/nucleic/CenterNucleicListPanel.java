package molecule.ui.adjust.center.nucleic;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import molecule.interfaces.Molecule;
import molecule.ui.AbstractMoleculeUIListPanel;
import star.annotations.SignalComponent;
import app.Messages;

@star.annotations.Preferences
@SignalComponent(extend = AbstractMoleculeUIListPanel.class)
public class CenterNucleicListPanel extends CenterNucleicListPanel_generated
{
	private static final long serialVersionUID = 1L;

	public String select_string = Messages.getString("CenterNucleicListPanel.0");  //$NON-NLS-1$
	public String list_title = Messages.getString("CenterNucleicListPanel.1"); //$NON-NLS-1$

	private String nucleic_string = Messages.getString("CenterNucleicListPanel.2"); //$NON-NLS-1$
	public String getTitle()
	{
		return nucleic_string;
	}

	private String[] nucleics = null;
	
	private CenterNucleicListPanel(String[] nucleics)
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
		loadPreferences("adjust_nucleic"); //$NON-NLS-1$

		setLayout(new BorderLayout());
		setBorder(new TitledBorder(select_string));

		list = new CenterNucleicList(this.nucleics);
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

	private boolean inInitTree = false;
	public void initTree()
	{
		if(null != list)
		{
			if(!inInitTree)
			{
				inInitTree = true;
				((CenterNucleicList)list).initTree();
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
				((CenterNucleicList)list).reset();
				inReset = false;
			}
		}
	}
	
	public String[] getDefaultSelection()
	{
		if(null != list)
		{
			return ((CenterNucleicList)list).getDefaultSelection();
		}
		return null;
	}
	
	protected void loadPreferences(String preferencesName)
	{
		list_title = getPreferences(preferencesName).get("list_title", list_title).trim(); //$NON-NLS-1$
		select_string = getPreferences(preferencesName).get("select_string", select_string).trim(); //$NON-NLS-1$
		nucleic_string = getPreferences(preferencesName).get("centernucleic_string", nucleic_string).trim();		 //$NON-NLS-1$
	}

	public static CenterNucleicListPanel getDefaultCenterNucleicListPanel(Molecule molecule)
	{
		if(null != molecule)
		{
			String[] array = molecule.getNucleicArray();
			if((null != array) && (0 != array.length))
			{
				return new CenterNucleicListPanel(array);
			}
		}
		return null;
	}

}

