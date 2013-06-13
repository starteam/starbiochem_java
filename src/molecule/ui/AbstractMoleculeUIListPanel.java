package molecule.ui;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import star.annotations.SignalComponent;

import molecule.interfaces.Molecule;

@SuppressWarnings("unused")
@star.annotations.Preferences
@SignalComponent(extend = JPanel.class)
abstract public class AbstractMoleculeUIListPanel extends AbstractMoleculeUIListPanel_generated
{
	private static final long serialVersionUID = 1L;

	protected JList list = null;
	public JList getList()
	{
		return this.list;
	}
	
	abstract public void reset();
}

