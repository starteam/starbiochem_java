package molecule.ui.jmol;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import star.annotations.SignalComponent;

@SignalComponent(extend = JPanel.class, raises={})
public class MoleculeJmolUIContainer extends MoleculeJmolUIContainer_generated 
{
	private static final long serialVersionUID = 1L;
	public MoleculeJmolUIContainer(Viewer viewer)
	{
		super();
		this.viewer = viewer;
	}

	MoleculeJmolContainer mJmolContainer = null;
	Viewer viewer = null;
	public void addNotify()
	{
		super.addNotify();
		setBackground(getParent().getBackground());
		setLayout(new BorderLayout());
		if(null != viewer)
		{
			mJmolContainer = new MoleculeJmolContainer(viewer);
			add(BorderLayout.CENTER, mJmolContainer);
		}
	}
	
	public void removeNotify()
	{
		removeAll();
		super.removeNotify();
	}
	
	public void resetScene()
	{
		if(null != mJmolContainer)
		{
			mJmolContainer.resetScene();
		}
	}
	
	public void reset()
	{
		if(null != mJmolContainer)
		{
			mJmolContainer.reset();
		}
	}

	public void initTree()
	{
		if(null != mJmolContainer)
		{
			mJmolContainer.initTree();
		}
	}
}
