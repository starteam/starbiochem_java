package molecule.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import molecule.RenderingInfo;
import molecule.interfaces.Molecule;
import molecule.ui.adjust.AdjustMoleculeUIContainer;
import molecule.ui.adjust.signal.RestrictRadiusEvent;
import molecule.ui.hetero.signal.HeteroApplyRenderingEvent;
import molecule.ui.hetero.signal.HeteroBondTranslucencyEvent;
import molecule.ui.hetero.signal.HeteroFilterEvent;
import molecule.ui.hetero.signal.HeteroHBondSizeEvent;
import molecule.ui.hetero.signal.HeteroHBondTranslucencyEvent;
import molecule.ui.hetero.signal.HeteroRenderingModeEvent;
import molecule.ui.hetero.signal.HeteroSelectionEvent;
import molecule.ui.hetero.signal.HeteroSizeEvent;
import molecule.ui.hetero.signal.HeteroTranslucencyEvent;
import molecule.ui.jmol.MoleculeJmolUIContainer;
import molecule.ui.jmol.Viewer;
import molecule.ui.jmol.signal.MoleculeJmolUIBrightnessEvent;
import molecule.ui.jmol.signal.MoleculeJmolUIDiffuseEvent;
import molecule.ui.jmol.signal.MoleculeJmolUISegmentsEvent;
import molecule.ui.jmol.signal.MoleculeJmolUISpecularEvent;
import molecule.ui.nucleic.signal.NucleicApplyRenderingEvent;
import molecule.ui.nucleic.signal.NucleicBondTranslucencyEvent;
import molecule.ui.nucleic.signal.NucleicFilterEvent;
import molecule.ui.nucleic.signal.NucleicHBondSizeEvent;
import molecule.ui.nucleic.signal.NucleicHBondTranslucencyEvent;
import molecule.ui.nucleic.signal.NucleicSelectionEvent;
import molecule.ui.nucleic.signal.NucleicSizeEvent;
import molecule.ui.nucleic.signal.NucleicTranslucencyEvent;
import molecule.ui.protein.primary.signal.ProteinPrimaryApplyRenderingEvent;
import molecule.ui.protein.primary.signal.ProteinPrimaryBondTranslucencyEvent;
import molecule.ui.protein.primary.signal.ProteinPrimaryFilterEvent;
import molecule.ui.protein.primary.signal.ProteinPrimaryRenderingModeEvent;
import molecule.ui.protein.primary.signal.ProteinPrimarySelectionEvent;
import molecule.ui.protein.primary.signal.ProteinPrimarySizeEvent;
import molecule.ui.protein.primary.signal.ProteinPrimaryTranslucencyEvent;
import molecule.ui.protein.quaternary.signal.ProteinQuaternaryApplyRenderingEvent;
import molecule.ui.protein.quaternary.signal.ProteinQuaternaryFilterEvent;
import molecule.ui.protein.quaternary.signal.ProteinQuaternaryHBondSizeEvent;
import molecule.ui.protein.quaternary.signal.ProteinQuaternaryHBondTranslucencyEvent;
import molecule.ui.protein.quaternary.signal.ProteinQuaternaryRenderingModeEvent;
import molecule.ui.protein.quaternary.signal.ProteinQuaternarySelectionEvent;
import molecule.ui.protein.quaternary.signal.ProteinQuaternarySizeEvent;
import molecule.ui.protein.quaternary.signal.ProteinQuaternaryTranslucencyEvent;
import molecule.ui.protein.secondary.signal.ProteinSecondaryApplyRenderingEvent;
import molecule.ui.protein.secondary.signal.ProteinSecondaryFilterEvent;
import molecule.ui.protein.secondary.signal.ProteinSecondaryHBondSizeEvent;
import molecule.ui.protein.secondary.signal.ProteinSecondaryHBondTranslucencyEvent;
import molecule.ui.protein.secondary.signal.ProteinSecondaryRenderingModeEvent;
import molecule.ui.protein.secondary.signal.ProteinSecondarySelectionEvent;
import molecule.ui.protein.secondary.signal.ProteinSecondarySizeEvent;
import molecule.ui.protein.tertiary.signal.ProteinTertiaryApplyRenderingEvent;
import molecule.ui.protein.tertiary.signal.ProteinTertiaryBondTranslucencyEvent;
import molecule.ui.protein.tertiary.signal.ProteinTertiaryFilterEvent;
import molecule.ui.protein.tertiary.signal.ProteinTertiaryRenderingModeEvent;
import molecule.ui.protein.tertiary.signal.ProteinTertiarySelectionEvent;
import molecule.ui.protein.tertiary.signal.ProteinTertiarySizeEvent;
import molecule.ui.protein.tertiary.signal.ProteinTertiaryTranslucencyEvent;
import molecule.ui.signal.RenderingInfoEvent;
import molecule.ui.signal.RenderingInfoRaiser;
import molecule.ui.structureoptions.ViewerOptionsPanel;
import molecule.ui.water.signal.WaterApplyRenderingEvent;
import molecule.ui.water.signal.WaterBondTranslucencyEvent;
import molecule.ui.water.signal.WaterFilterEvent;
import molecule.ui.water.signal.WaterRenderingModeEvent;
import molecule.ui.water.signal.WaterSelectionEvent;
import molecule.ui.water.signal.WaterSizeEvent;
import molecule.ui.water.signal.WaterTranslucencyEvent;
import star.annotations.Handles;
import star.annotations.SignalComponent;
import star.event.UnhandledExceptionHandlerComponent;
import utils.UIHelpers;
import app.StarBiochemException;
import app.StarBiochemMain;
import app.UIDialog;
import app.signal.CloseMoleculeRaiser;
import app.signal.ViewHydrogensRaiser;
import app.signal.RenderAllRaiser;
import app.signal.TrackGlassPaneRaiser;
import app.signal.ViewBackgroundColorEvent;
import app.signal.ViewBackgroundColorRaiser;
import app.signal.ViewForegroundColorEvent;
import app.signal.ViewForegroundColorRaiser;
import app.signal.ViewStructureOptionsRaiser;
import app.Messages;

@SignalComponent(extend = JPanel.class , raises = {RenderingInfoRaiser.class, RenderAllRaiser.class, CloseMoleculeRaiser.class})
public class MoleculeContainer extends MoleculeContainer_generated implements ActionListener 
{
	private static final long serialVersionUID = 1L;
	transient private Viewer viewer = null;
	transient private Molecule molecule = null;
	transient private MoleculeUIContainer mUIContainer = null;
	transient private MoleculeJmolUIContainer mJmolUIContainer = null;
	transient private AdjustMoleculeUIContainer structureOptionsPanel = null;
	transient private UIDialog viewStructureOptions = null;
	transient private UIDialog viewerOptions;
	transient private ViewerOptionsPanel viewerOptionsPanel;
	
	private String title = null ;
	
	public MoleculeContainer(Viewer viewer, Molecule molecule, String title)
	{
		super();
		this.viewer = viewer;
		this.molecule = molecule;
		this.title = title;
	}
	
	private boolean isInitialized = false;
	public void addNotify()
	{
		super.addNotify();

		if(!isInitialized)
		{
			init();
			isInitialized = true;
			UIHelpers.track("Open/" + title ) ; //$NON-NLS-1$
		}
	}

	public void removeNotify()
	{
		end();
		isInitialized = false;
		super.removeNotify();
		UIHelpers.track("Close/" + title ) ; //$NON-NLS-1$
	}

	private void init()
	{
		initEventContainment();
		initStructureOptions();
		initViewerOptions();
		mUIContainer = new MoleculeUIContainer(molecule);
		mJmolUIContainer = new MoleculeJmolUIContainer(viewer);
		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mUIContainer, mJmolUIContainer);
		split.setResizeWeight(0);
		split.setOneTouchExpandable(true);
		split.setContinuousLayout(true);

		setLayout(new BorderLayout());
		add(BorderLayout.CENTER, split);
		invalidate();
		validate();
	}

	private void end()
	{
		endStructureOptions();
		removeAll();
		mUIContainer = null;
		mJmolUIContainer = null;
	}
	
	void initStructureOptions()
	{
        viewStructureOptions = new UIDialog(StarBiochemMain.getFrame(), VIEW_STRUCTURE_OPTIONS, false);
        viewStructureOptions.setSize(500,500);
        viewStructureOptions.setLocationRelativeTo(StarBiochemMain.getFrame());
		structureOptionsPanel = AdjustMoleculeUIContainer.getDefaultAdjustPanel(molecule);
		if(null != structureOptionsPanel)
		{
			viewStructureOptions.add(structureOptionsPanel);
		}
		
		viewStructureOptions.setVisible(false);
		getAdapter().addComponent(structureOptionsPanel);
	}
	
	void initViewerOptions()
	{
		viewerOptions = new UIDialog(StarBiochemMain.getFrame(), Messages.getString("MoleculeContainer.2"), true); //$NON-NLS-1$
		viewerOptions.setSize(450, 407);
		viewerOptions.setLocationRelativeTo(getParent());
		viewerOptionsPanel = new ViewerOptionsPanel();
		
		JButton ok = new JButton(Messages.getString("MoleculeContainer.3")); //$NON-NLS-1$
		ok.setSize(4, 4);
		ok.addActionListener(this);
		
		if(null != viewerOptionsPanel)
		{
			viewerOptions.setLayout(new BorderLayout());
			viewerOptions.add(BorderLayout.CENTER, viewerOptionsPanel);
			viewerOptions.add(BorderLayout.SOUTH, ok);
		}
		viewerOptions.setVisible(false);
		getAdapter().addComponent(viewerOptionsPanel);
	}

	void endStructureOptions()
	{
		getAdapter().removeComponent(structureOptionsPanel);
		structureOptionsPanel = null;
		viewStructureOptions.dispose();
		viewStructureOptions = null;
	}
	
	void endViewerOptions()
	{
		getAdapter().removeComponent(viewerOptionsPanel);
		viewerOptionsPanel = null;
		viewStructureOptions.dispose();
		viewStructureOptions = null;
	}
	
	private void initEventContainment()
	{
		getAdapter().addContained(RenderingInfoEvent.class);
		
		getAdapter().addContained(HeteroApplyRenderingEvent.class);

		getAdapter().addContained(HeteroBondTranslucencyEvent.class);

		getAdapter().addContained(HeteroFilterEvent.class);

		getAdapter().addContained(HeteroRenderingModeEvent.class);

		getAdapter().addContained(HeteroSelectionEvent.class);

		getAdapter().addContained(HeteroSizeEvent.class);

		getAdapter().addContained(HeteroTranslucencyEvent.class);
		
		getAdapter().addContained(HeteroHBondSizeEvent.class);

		getAdapter().addContained(HeteroHBondTranslucencyEvent.class);

		getAdapter().addContained(NucleicApplyRenderingEvent.class);

		getAdapter().addContained(NucleicBondTranslucencyEvent.class);

		getAdapter().addContained(NucleicFilterEvent.class);

		getAdapter().addContained(NucleicSelectionEvent.class);

		getAdapter().addContained(NucleicSizeEvent.class);

		getAdapter().addContained(NucleicTranslucencyEvent.class);
		
		getAdapter().addContained(NucleicHBondSizeEvent.class);

		getAdapter().addContained(NucleicHBondTranslucencyEvent.class);

		getAdapter().addContained(ProteinPrimaryFilterEvent.class);

		getAdapter().addContained(ProteinPrimaryApplyRenderingEvent.class);

		getAdapter().addContained(ProteinPrimaryBondTranslucencyEvent.class);

		getAdapter().addContained(ProteinPrimaryRenderingModeEvent.class);

		getAdapter().addContained(ProteinPrimarySelectionEvent.class);

		getAdapter().addContained(ProteinPrimarySizeEvent.class);

		getAdapter().addContained(ProteinPrimaryTranslucencyEvent.class);

		getAdapter().addContained(ProteinQuaternaryApplyRenderingEvent.class);

		getAdapter().addContained(ProteinQuaternaryFilterEvent.class);

		getAdapter().addContained(ProteinQuaternaryRenderingModeEvent.class);

		getAdapter().addContained(ProteinQuaternarySelectionEvent.class);

		getAdapter().addContained(ProteinQuaternarySizeEvent.class);

		getAdapter().addContained(ProteinQuaternaryTranslucencyEvent.class);
		
		getAdapter().addContained(ProteinQuaternaryHBondSizeEvent.class);

		getAdapter().addContained(ProteinQuaternaryHBondTranslucencyEvent.class);

		getAdapter().addContained(ProteinSecondaryApplyRenderingEvent.class);

		getAdapter().addContained(ProteinSecondaryFilterEvent.class);

		getAdapter().addContained(ProteinSecondaryRenderingModeEvent.class);

		getAdapter().addContained(ProteinSecondarySelectionEvent.class);

		getAdapter().addContained(ProteinSecondarySizeEvent.class);
		
		getAdapter().addContained(ProteinSecondaryHBondSizeEvent.class);

		getAdapter().addContained(ProteinSecondaryHBondTranslucencyEvent.class);

		getAdapter().addContained(ProteinTertiaryFilterEvent.class);

		getAdapter().addContained(ProteinTertiaryApplyRenderingEvent.class);

		getAdapter().addContained(ProteinTertiaryBondTranslucencyEvent.class);

		getAdapter().addContained(ProteinTertiaryRenderingModeEvent.class);

		getAdapter().addContained(ProteinTertiarySelectionEvent.class);

		getAdapter().addContained(ProteinTertiarySizeEvent.class);

		getAdapter().addContained(ProteinTertiaryTranslucencyEvent.class);

		getAdapter().addContained(WaterApplyRenderingEvent.class);

		getAdapter().addContained(WaterBondTranslucencyEvent.class);
		
		getAdapter().addContained(WaterFilterEvent.class);

		getAdapter().addContained(WaterRenderingModeEvent.class);

		getAdapter().addContained(WaterSelectionEvent.class);

		getAdapter().addContained(WaterSizeEvent.class);

		getAdapter().addContained(WaterTranslucencyEvent.class);
		
		getAdapter().addContained(RestrictRadiusEvent.class);

		getAdapter().addContained(MoleculeJmolUIBrightnessEvent.class);

		getAdapter().addContained(MoleculeJmolUIDiffuseEvent.class);

		getAdapter().addContained(MoleculeJmolUISegmentsEvent.class);

		getAdapter().addContained(MoleculeJmolUISpecularEvent.class);
				
		getAdapter().addContained(ViewBackgroundColorEvent.class);
		
		getAdapter().addContained(ViewForegroundColorEvent.class);
		
		getAdapter().addComponent(new UnhandledExceptionHandlerComponent());

	}
		
	public void resetScene()
	{
		if(null != mJmolUIContainer)
		{
			mJmolUIContainer.resetScene();
		}
	}
	
	public void initTree() throws StarBiochemException
	{
		if(null != viewer)
		{
			viewer.initTree();
			if(null != mJmolUIContainer)
			{
				mJmolUIContainer.initTree();
				if(null != mUIContainer)
				{
					mUIContainer.initTree();
				}
				if(null != structureOptionsPanel)
				{
					structureOptionsPanel.initTree();
				}
			}
		}
	}
	
	public void reset() throws StarBiochemException
	{
		if(null != mJmolUIContainer)
		{
			mJmolUIContainer.reset();
		}
		if(null != mUIContainer)
		{
			mUIContainer.reset();
		}
		if(null != structureOptionsPanel)
		{
			structureOptionsPanel.reset();
		}
		if(null != viewer)
		{
			viewer.reset();
		}
	}
	
	RenderingInfo ri = null;

	public RenderingInfo getRenderingInfo()
	{
		return this.ri;
	}

	public void setVisible(boolean b)
    {
        if(!b)
		{
    		if(null != viewStructureOptions)
	        {
    			viewStructureOptions.setVisible(false);
	        }
		}
        super.setVisible(b);
    }
	
	
	@Handles(raises = {})
	protected void handleTrackGlassPaneRaiser(TrackGlassPaneRaiser raiser)
	{
		if(raiser.isGlassPaneShowing())
		{
			if((null != viewStructureOptions) && viewStructureOptions.isShowing())
			{
				viewStructureOptions.setEnabled(false);
				viewStructureOptions.addGlassPane();
			}
		}
		else
		{
			if( null != viewStructureOptions)
			{
				viewStructureOptions.removeGlassPane();
				viewStructureOptions.setEnabled(true);
			}
		}
	}
	
	@Handles(raises = {})
	protected void handleViewBackgroundColorRaiser(ViewBackgroundColorRaiser raiser)
	{
		if(this.isShowing())
		{
			UIHelpers.track("Viewer Options/" + title); //$NON-NLS-1$
			if(null != viewerOptionsPanel)
			{
				viewerOptionsPanel.showBackground();
				viewerOptions.setVisible(this.isShowing());
			}
		}
	}
	
	@Handles(raises = {})
	protected void handleViewForegroundColorRaiser(ViewForegroundColorRaiser raiser)
	{
		if(this.isShowing())
		{
			UIHelpers.track("Viewer Options/" + title); //$NON-NLS-1$
			if(null != viewerOptionsPanel)
			{
				viewerOptionsPanel.showForeground();
				viewerOptions.setVisible(this.isShowing());
			}
		}
	}
	
	private boolean isStructureOptionsVisible = false;
	@Handles(raises = {})
	protected void handleViewStructureOptionsRaiser(ViewStructureOptionsRaiser raiser)
	{
		if(null != viewStructureOptions)
		{
			viewStructureOptions.setVisible(this.isShowing());
			if (!isStructureOptionsVisible && this.isShowing())
			{
				UIHelpers.track( "Set_Center/"+title ); //$NON-NLS-1$
				isStructureOptionsVisible = true;
			}
		}
	}
	
	boolean hydrogensOn = true;
	@Handles(raises = {})
	protected void handleViewHydrogensRaiser(ViewHydrogensRaiser raiser)
	{
		try
		{
			if (this.isShowing())
			{	
				UIHelpers.track( "Set_Center/"+title );		 //$NON-NLS-1$
				int count = 0;
				if(null!= viewer)
				{
					if(hydrogensOn)
					{
						this.viewer.script("set showHydrogens FALSE; "); //$NON-NLS-1$
						hydrogensOn = false;
						count++;
					}
					if(!hydrogensOn && count == 0)
					{
						this.viewer.script("set showHydrogens TRUE; "); //$NON-NLS-1$
						hydrogensOn = true;
					}
				}
			}
		}
		catch(StarBiochemException e)
		{
			JOptionPane.showMessageDialog(this, e.getMessage() + "\n" + Messages.getString("MoleculeContainer.11")); //$NON-NLS-1$ //$NON-NLS-2$
			try {
				if(null != viewer)
				{
					viewer.script("restore STATE \"scriptState\";"); //$NON-NLS-1$
				}
			} catch (StarBiochemException e1) {
				JOptionPane.showMessageDialog(this, e.getMessage() + "\n" + Messages.getString("MoleculeContainer.14")); //$NON-NLS-1$ //$NON-NLS-2$
				this.closeException = e1;
				this.raise_CloseMoleculeEvent();
			}
		}
	}
	
	private String VIEW_STRUCTURE_OPTIONS = Messages.getString("MoleculeContainer.16"); //$NON-NLS-1$
	
	
	public void actionPerformed(ActionEvent e) 
	{
		try
		{
			if(this.isShowing())
			{
				Object source = e.getSource();
				if(source instanceof JButton)
				{
					JButton button = (JButton)source;
					if(viewerOptions.isAncestorOf(button))
					{
						Color myColor = viewerOptionsPanel.getMyColor();
						viewer.setBackground(myColor);
						viewer.setViewerBackground(myColor);
						viewerOptions.setVisible(false);
					}
				}
			}
		}
		catch(StarBiochemException ex)
		{
			JOptionPane.showMessageDialog(this, ex.getMessage() + "\n" + Messages.getString("MoleculeContainer.21")); //$NON-NLS-1$ //$NON-NLS-2$
			this.closeException = ex;
			this.raise_CloseMoleculeEvent();
		}
	}

	StarBiochemException closeException = null;
	public StarBiochemException getStarBiochemException() {
		return closeException;
	}
	
}
