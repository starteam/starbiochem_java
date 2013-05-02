package molecule.ui.adjust.center.protein.secondary;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import molecule.interfaces.Molecule;
import molecule.ui.AbstractMoleculeUIListPanel;
import star.annotations.SignalComponent;
import app.Messages;

@star.annotations.Preferences
@SignalComponent(extend = AbstractMoleculeUIListPanel.class)
public class CenterProteinSecondaryListPanel extends CenterProteinSecondaryListPanel_generated
{
	private static final long serialVersionUID = 1L;
	
	transient public String proteinsecondary_select_string = Messages.getString("CenterProteinSecondaryListPanel.0"); //$NON-NLS-1$
	transient public String proteinsecondary_list_title = Messages.getString("CenterProteinSecondaryListPanel.1"); //$NON-NLS-1$

	private String proteinsecondary_string = Messages.getString("CenterProteinSecondaryListPanel.2"); //$NON-NLS-1$
	public String getTitle()
	{
		return proteinsecondary_string;
	}
	
	transient private Molecule molecule = null;
	
	public CenterProteinSecondaryListPanel(molecule.interfaces.Molecule molecule)
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
		loadPreferences("adjust_secondary"); //$NON-NLS-1$

		setLayout(new BorderLayout());
		setBorder(new TitledBorder(proteinsecondary_select_string));

		list = new CenterProteinSecondaryList(this.molecule);
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

	private boolean inInitTree = false;
	public void initTree()
	{
		if(null != list)
		{
			if(!inInitTree)
			{
				inInitTree = true;
				((CenterProteinSecondaryList)list).initTree();
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
				((CenterProteinSecondaryList)list).reset();
				inReset = false;
			}
		}
	}
	
	public String[] getDefaultSelection()
	{
		if(null != list)
		{
			return ((CenterProteinSecondaryList)list).getDefaultSelection();
		}
		return null;
	}
	
	protected void loadPreferences(String preferencesName)
	{
		proteinsecondary_list_title =getPreferences(preferencesName).get("proteinsecondary_list_title", proteinsecondary_list_title).trim(); //$NON-NLS-1$
		proteinsecondary_select_string = getPreferences(preferencesName).get("proteinsecondary_select_string", proteinsecondary_select_string).trim(); //$NON-NLS-1$
		proteinsecondary_string = getPreferences(preferencesName).get("proteinsecondary_string", proteinsecondary_string).trim(); //$NON-NLS-1$
	}

	public static CenterProteinSecondaryListPanel getDefaultCenterProteinSecondaryListPanel(Molecule molecule)
	{
		if(null != molecule)
		{
			String[] array = molecule.getProteinArray();
			if((null != array) && (0 != array.length))
			{
				return new CenterProteinSecondaryListPanel(molecule);
			}
		}
		return null;
	}

}

