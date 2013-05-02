package molecule.ui.protein.secondary;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import molecule.interfaces.Molecule;
import molecule.ui.AbstractMoleculeUIListPanel;
import star.annotations.SignalComponent;
import app.Messages;

@star.annotations.Preferences
@SignalComponent(extend = AbstractMoleculeUIListPanel.class)
public class ProteinSecondaryListPanel extends ProteinSecondaryListPanel_generated
{
	private static final long serialVersionUID = 1L;
	transient public String proteinsecondary_list_title = Messages.getString("ProteinSecondaryListPanel.0"); //$NON-NLS-1$

	transient public String proteinsecondary_select_string = Messages.getString("ProteinSecondaryListPanel.1"); //$NON-NLS-1$

	transient private Molecule molecule = null;
	
	public ProteinSecondaryListPanel(molecule.interfaces.Molecule molecule)
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
		setBorder(new TitledBorder(proteinsecondary_select_string));

		list = new ProteinSecondaryList(this.molecule);
		JScrollPane srp = new JScrollPane(); 
		srp.setViewportView(list);
		srp.setViewportBorder(new TitledBorder(proteinsecondary_list_title));
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
				((ProteinSecondaryList)list).initTree();
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
				((ProteinSecondaryList)list).reset();
				inReset = false;
			}
		}
	}
	
	public String[] getDefaultSelection()
	{
		return ((ProteinSecondaryList)list).getDefaultSelection();	
	}
	
	protected void loadPreferences(String preferencesName)
	{
		proteinsecondary_select_string = getPreferences(preferencesName).get("proteinsecondary_select_string", proteinsecondary_select_string).trim(); //$NON-NLS-1$
		proteinsecondary_list_title = getPreferences(preferencesName).get("proteinsecondary_list_title", proteinsecondary_list_title).trim(); //$NON-NLS-1$
	}

}

