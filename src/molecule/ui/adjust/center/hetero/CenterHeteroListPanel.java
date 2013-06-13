package molecule.ui.adjust.center.hetero;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import molecule.interfaces.Molecule;
import molecule.ui.AbstractMoleculeUIListPanel;
import star.annotations.SignalComponent;
import app.Messages;

@star.annotations.Preferences
@SignalComponent(extend = AbstractMoleculeUIListPanel.class)
public class CenterHeteroListPanel extends CenterHeteroListPanel_generated
{
	private static final long serialVersionUID = 1L;

	public String select_string = Messages.getString("CenterHeteroListPanel.0");  //$NON-NLS-1$
	public String list_title = Messages.getString("CenterHeteroListPanel.1"); //$NON-NLS-1$
	private String hetero_string = Messages.getString("CenterHeteroListPanel.2"); //$NON-NLS-1$
	private String[] heteros = null;
	
	public CenterHeteroListPanel(String[] heteros)
	{
		super();
		this.heteros = heteros;
		loadPreferences("adjust_hetero"); //$NON-NLS-1$
	}
	
	public String getTitle()
	{
		return hetero_string;
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
		setLayout(new BorderLayout());
		setBorder(new TitledBorder(select_string));

		list = new CenterHeteroList(this.heteros);
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
				((CenterHeteroList)list).initTree();
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
				((CenterHeteroList)list).reset();
				inReset = false;
			}
		}
	}
	
	public String[] getDefaultSelection()
	{
		if(null != list)
		{
			return ((CenterHeteroList)list).getDefaultSelection();
		}
		return null;
	}
	
	protected void loadPreferences(String preferencesName)
	{
		list_title = getPreferences(preferencesName).get("list_title", list_title).trim(); //$NON-NLS-1$
		select_string = getPreferences(preferencesName).get("select_string", select_string).trim(); //$NON-NLS-1$
		hetero_string = getPreferences(preferencesName).get("hetero_string", hetero_string).trim();		 //$NON-NLS-1$
	}

	public static CenterHeteroListPanel getDefaultCenterHeteroListPanel(Molecule molecule)
	{
		if(null != molecule)
		{
			String[] array = molecule.getHeteroNotWaterNotNucleicArray();
			if((null != array) && (0 != array.length))
			{
				return new CenterHeteroListPanel(array);
			}
		}
		return null;
	}

}

