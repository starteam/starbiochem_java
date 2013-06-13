package molecule.ui.adjust.center.protein.primary;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import molecule.interfaces.Molecule;
import molecule.ui.AbstractMoleculeUIListPanel;
import star.annotations.SignalComponent;
import app.Messages;


@star.annotations.Preferences
@SignalComponent(extend = AbstractMoleculeUIListPanel.class)
public class CenterProteinPrimaryListPanel extends CenterProteinPrimaryListPanel_generated
{
	private static final long serialVersionUID = 1L;

	public String proteinprimary_select_string = Messages.getString("CenterProteinPrimaryListPanel.0"); //$NON-NLS-1$
	public String proteinprimary_list_title = Messages.getString("CenterProteinPrimaryListPanel.1"); //$NON-NLS-1$
	private String proteinprimary_string = Messages.getString("CenterProteinPrimaryListPanel.2"); //$NON-NLS-1$
	public String getTitle()
	{
		return proteinprimary_string;
	}
	
	private String[] aminoAcids = null;
	
	private CenterProteinPrimaryListPanel(String[] aminoAcids)
	{
		super();
		this.aminoAcids = aminoAcids;
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
		loadPreferences("adjust_primary"); //$NON-NLS-1$
		
		setLayout(new BorderLayout());
		setBorder(new TitledBorder(proteinprimary_select_string));

		list = new CenterProteinPrimaryList(this.aminoAcids);
		JScrollPane srp = new JScrollPane(); 
		srp.setViewportView(list);
		srp.setViewportBorder(new TitledBorder(proteinprimary_list_title));
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
				((CenterProteinPrimaryList)list).initTree();
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
				((CenterProteinPrimaryList)list).reset();
				inReset = false;
			}
		}
	}
	
	public String[] getDefaultSelection()
	{
		if(null != list)
		{
			return ((CenterProteinPrimaryList)list).getDefaultSelection();
		}
		return null;
	}
	
	protected void loadPreferences(String preferencesName)
	{
		proteinprimary_list_title = getPreferences(preferencesName).get("proteinprimary_list_title", proteinprimary_list_title).trim(); //$NON-NLS-1$
		proteinprimary_select_string = getPreferences(preferencesName).get("proteinprimary_select_string", proteinprimary_select_string).trim(); //$NON-NLS-1$
		proteinprimary_string = getPreferences(preferencesName).get("proteinprimary_string", proteinprimary_string).trim(); //$NON-NLS-1$
	}

	public static CenterProteinPrimaryListPanel getDefaultCenterProteinPrimaryListPanel(Molecule molecule)
	{
		if(null != molecule)
		{
			String[] array = molecule.getProteinArray();
			if((null != array) && (0 != array.length))
			{
				return new CenterProteinPrimaryListPanel(array);
			}
		}
		return null;
	}

}

