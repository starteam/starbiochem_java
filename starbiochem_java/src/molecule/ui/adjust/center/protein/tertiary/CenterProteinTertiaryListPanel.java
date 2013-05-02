package molecule.ui.adjust.center.protein.tertiary;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import molecule.interfaces.Molecule;
import molecule.ui.AbstractMoleculeUIListPanel;
import star.annotations.SignalComponent;
import app.Messages;

@star.annotations.Preferences
@SignalComponent(extend = AbstractMoleculeUIListPanel.class)
public class CenterProteinTertiaryListPanel extends CenterProteinTertiaryListPanel_generated
{
	private static final long serialVersionUID = 1L;

	public String proteintertiary_select_string = Messages.getString("CenterProteinTertiaryListPanel.0"); //$NON-NLS-1$
	public String proteintertiary_list_title = Messages.getString("CenterProteinTertiaryListPanel.1"); //$NON-NLS-1$

	private String proteintertiary_string = Messages.getString("CenterProteinTertiaryListPanel.2"); //$NON-NLS-1$
	public String getTitle()
	{
		return proteintertiary_string;
	}

	private Molecule molecule = null;
	
	private CenterProteinTertiaryListPanel(Molecule molecule)
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
		loadPreferences("adjust_tertiary"); //$NON-NLS-1$
		
		setLayout(new BorderLayout());
		setBorder(new TitledBorder(proteintertiary_select_string));

		list = new CenterProteinTertiaryList(this.molecule);
		JScrollPane srp = new JScrollPane(); 
		srp.setViewportView(list);
		srp.setViewportBorder(new TitledBorder(proteintertiary_list_title));
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
				((CenterProteinTertiaryList)list).initTree();
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
				((CenterProteinTertiaryList)list).reset();
				inReset = false;
			}
		}
	}
	
	public String[] getDefaultSelection()
	{
		if(null != list)
		{
			return ((CenterProteinTertiaryList)list).getDefaultSelection();	
		}
		return null;
	}
	
	protected void loadPreferences(String preferencesName)
	{
		proteintertiary_list_title = getPreferences(preferencesName).get("list_title", proteintertiary_list_title ).trim(); //$NON-NLS-1$
		proteintertiary_select_string = getPreferences(preferencesName).get("select_string", proteintertiary_select_string).trim(); //$NON-NLS-1$
		proteintertiary_string = getPreferences(preferencesName).get("proteintertiary_string", proteintertiary_string).trim(); //$NON-NLS-1$
	}
	
	public static CenterProteinTertiaryListPanel getDefaultCenterProteinTertiaryListPanel(Molecule molecule)
	{
		if(null != molecule)
		{
			String[] array = molecule.getProteinArray();
			if((null != array) && (0 != array.length))
			{
				return new CenterProteinTertiaryListPanel(molecule);
			}
		}
		return null;
	}

}

