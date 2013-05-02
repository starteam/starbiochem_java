package molecule.ui.protein.quaternary;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import molecule.interfaces.Molecule;
import molecule.ui.AbstractMoleculeUIListPanel;
import star.annotations.SignalComponent;
import app.Messages;

@star.annotations.Preferences
@SignalComponent(extend = AbstractMoleculeUIListPanel.class)
public class ProteinQuaternaryListPanel extends ProteinQuaternaryListPanel_generated
{
	private static final long serialVersionUID = 1L;

	public String quaternary_select_string = Messages.getString("ProteinQuaternaryListPanel.0"); //$NON-NLS-1$
	public String quaternary_list_title = Messages.getString("ProteinQuaternaryListPanel.1"); //$NON-NLS-1$
	
	transient private Molecule molecule = null;
	
	public ProteinQuaternaryListPanel(Molecule molecule)
	{
		super();
		this.molecule = molecule;
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
		loadPreferences("protein"); //$NON-NLS-1$
		
		setLayout(new BorderLayout());
		setBorder(new TitledBorder(quaternary_select_string));

		list = new ProteinQuaternaryList(this.molecule);
		JScrollPane srp = new JScrollPane(); 
		srp.setViewportView(list);
		srp.setViewportBorder(new TitledBorder(quaternary_list_title));
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
				((ProteinQuaternaryList)list).initTree();
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
				((ProteinQuaternaryList)list).reset();
				inReset = false;
			}
		}
	}
	
	public String[] getDefaultSelection()
	{
		return ((ProteinQuaternaryList)list).getDefaultSelection();	
	}

	protected void loadPreferences(String preferencesName)
	{
		quaternary_list_title = getPreferences(preferencesName).get("quaternary_list_title", quaternary_list_title).trim(); //$NON-NLS-1$
		quaternary_select_string = getPreferences(preferencesName).get("quaternary_select_string", quaternary_select_string).trim(); //$NON-NLS-1$
	}

}

