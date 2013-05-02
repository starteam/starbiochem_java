package molecule.ui.info;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import molecule.interfaces.Molecule;
import pdb.PDBTitle;
import star.annotations.SignalComponent;
import app.Messages;

@star.annotations.Preferences
@SignalComponent(extend = JPanel.class, raises = {})
public class InfoPanel extends InfoPanel_generated
{
	private static final long serialVersionUID = 1L;

	private Molecule molecule = null;

	private InfoPanel(Molecule molecule)
	{
		super();
		this.molecule = molecule;
		loadPreferences("info"); //$NON-NLS-1$
	}

	public void addNotify()
	{
		super.addNotify();
		InfoPanelContents ipc = InfoPanelContents.getDefaultInfoPanelContents(molecule);
		setLayout( new BorderLayout());
		add( new JScrollPane(ipc));
		invalidate();
		validate();
	}

	public static InfoPanel getDefaultInfoPanel(Molecule molecule)
	{
		if (null != molecule)
		{
			PDBTitle[] titleArray = molecule.getTitleArray();
			if ((null != titleArray) && (0 != titleArray.length))
			{
				return new InfoPanel(molecule);
			}
		}
		return null;
	}

	public void initTree()
	{
	}
	
	public void reset()
	{
	}
	
	public Dimension getMinimumSize()
	{
		return getPreferredSize();
	}
	
	public String info_string = Messages.getString("InfoPanel.1"); //$NON-NLS-1$

	public String getTitle()
	{
		return info_string;
	}

	protected void loadPreferences(String preferencesName)
	{
		info_string = getPreferences(preferencesName).get("info_string", info_string).trim(); //$NON-NLS-1$
	}

}