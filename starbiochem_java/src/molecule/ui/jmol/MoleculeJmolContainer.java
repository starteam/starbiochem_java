package molecule.ui.jmol;

import java.awt.BorderLayout;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import molecule.interfaces.AdjustInfo;
import molecule.interfaces.AdjustSelectionInfo;
import molecule.interfaces.RenderingInfo;
import molecule.ui.adjust.center.hetero.signal.CenterHeteroSelectionRaiser;
import molecule.ui.adjust.center.nucleic.signal.CenterNucleicRaiser;
import molecule.ui.adjust.center.protein.primary.signal.CenterProteinPrimaryRaiser;
import molecule.ui.adjust.center.protein.quaternary.signal.CenterProteinQuaternaryRaiser;
import molecule.ui.adjust.center.protein.secondary.signal.CenterProteinSecondaryRaiser;
import molecule.ui.adjust.center.protein.tertiary.signal.CenterProteinTertiaryRaiser;
import molecule.ui.adjust.center.water.signal.CenterWaterSelectionRaiser;
import molecule.ui.signal.AdjustInfoRaiser;
import molecule.ui.signal.RenderingInfoRaiser;
import star.annotations.Handles;
import star.annotations.SignalComponent;
import app.StarBiochemException;
import app.StarBiochemMain;
import app.signal.CloseMoleculeRaiser;
import app.Messages;

@SignalComponent(extend = JPanel.class, raises={CloseMoleculeRaiser.class})
public class MoleculeJmolContainer extends MoleculeJmolContainer_generated 
{
	private static final long serialVersionUID = 1L;
	
	private static final String EMPTY_STRING = ""; //$NON-NLS-1$
	
	public MoleculeJmolContainer(Viewer viewer)
	{
		super();
		this.viewer = viewer;
	}

	Viewer viewer = null;
	public void addNotify()
	{
		super.addNotify();
		init();
	}
	
	public void removeNotify()
	{
		removeAll();
		super.removeNotify();
	}
	
	private void init()
	{
		try
		{
			setBackground(getParent().getBackground());
			setLayout(new BorderLayout());
			if(null != viewer)
			{
				viewer.script("save ORIENTATION \"orientation\"; save COORDINATES \"coordinates\"; save STATE \"state\"; set disablePopupmenu TRUE;"); //$NON-NLS-1$
				add(BorderLayout.CENTER,viewer);
			}
		}
		catch(StarBiochemException e)
		{
			this.closeException = e;
			this.raise_CloseMoleculeEvent();
		}

	}
	
	@Handles(raises = {})
	protected void handleRenderingInfoRaiser(final RenderingInfoRaiser raiser)
	{
		final RenderingInfo ri = raiser.getRenderingInfo();
		final MoleculeJmolContainer self = this;
		if(null != ri)
		{
			StarBiochemMain.setGlassPaneVisible(true);
			try
			{
				if(RenderingInfoRaiser.PROTEIN_PRIMARY_STRUCTURE.equals(ri.getSource()))
				{
					ViewerRenderProteinPrimaryStructure.renderProteinPrimaryStructure(viewer, ri);
				}
				if(RenderingInfoRaiser.PROTEIN_PRIMARY_COVALENT_BONDS.equals(ri.getSource()))
				{
					ViewerRenderProteinPrimaryStructure.renderProteinPrimaryCovalentBonds(viewer, ri);
				}
				else if(RenderingInfoRaiser.PROTEIN_SECONDARY_STRUCTURE.equals(ri.getSource()))
				{
					ViewerRenderProteinSecondaryStructure.renderProteinSecondaryStructure(viewer, ri);
				}
				else if(RenderingInfoRaiser.PROTEIN_SECONDARY_HBOND_STRUCTURE.equals(ri.getSource()))
				{
					ViewerRenderProteinSecondaryStructure.renderProteinSecondaryHBondStructure(viewer, ri);
				}
				else if(RenderingInfoRaiser.PROTEIN_TERTIARY_STRUCTURE.equals(ri.getSource()))
				{
					ViewerRenderProteinTertiaryStructure.renderProteinTertiaryStructure(viewer, ri);
				}
				else if(RenderingInfoRaiser.PROTEIN_TERTIARY_COVALENT_BONDS.equals(ri.getSource()))
				{
					ViewerRenderProteinTertiaryStructure.renderProteinTertiaryCovalentBonds(viewer, ri);
				}
				else if(RenderingInfoRaiser.PROTEIN_TERTIARY_HBOND_STRUCTURE.equals(ri.getSource()))
				{
					ViewerRenderProteinTertiaryStructure.renderProteinTertiaryHbonds(viewer, ri);
				}
				else if(RenderingInfoRaiser.PROTEIN_QUATERNARY_STRUCTURE.equals(ri.getSource()))
				{
					SwingUtilities.invokeLater(new Runnable()
	                {
		                public void run()
		                {
		        			try
		        			{
		        				ViewerRenderProteinQuaternaryStructure.renderProteinQuaternaryStructure(viewer, ri);		                
		        			}
		        			catch (StarBiochemException e1)
		        			{
		        				closeException = e1;
		        				self.raise_CloseMoleculeEvent();
		        			}
		                }
	                });
				}
				else if(RenderingInfoRaiser.PROTEIN_QUATERNARY_SSBOND_STRUCTURE.equals(ri.getSource()))
				{
					ViewerRenderProteinQuaternaryStructure.renderProteinQuaternarySSBonds(viewer, ri);
				}
				else if(RenderingInfoRaiser.PROTEIN_QUATERNARY_HBOND_STRUCTURE.equals(ri.getSource()))
				{
					ViewerRenderProteinQuaternaryStructure.renderProteinQuaternaryHbonds(viewer, ri);
				}
				else if(RenderingInfoRaiser.PROTEIN_QUATERNARY_STRUCTURE_TRANSLUCENCY.equals(ri.getSource()))
				{
		    		ViewerRenderProteinQuaternaryStructure.renderProteinQuaternaryStructureTranslucency(viewer, ri);		                
				}
				else if(RenderingInfoRaiser.NUCLEIC.equals(ri.getSource()))
				{
					ViewerRenderNucleic.renderNucleicAtoms(viewer, ri);
				}
				else if(RenderingInfoRaiser.NUCLEIC_COVALENT_BONDS.equals(ri.getSource()))
				{
					ViewerRenderNucleic.renderNucleicBonds(viewer, ri);
				}
				else if(RenderingInfoRaiser.NUCLEIC_HBOND_STRUCTURE.equals(ri.getSource()))
				{
					ViewerRenderNucleic.renderNucleicHBond(viewer, ri);
				}
				else if(RenderingInfoRaiser.HETERO.equals(ri.getSource()))
				{
					ViewerRenderHetero.renderHeteroAtoms(viewer, ri);
				}
				else if(RenderingInfoRaiser.HETERO_COVALENT_BONDS.equals(ri.getSource()))
				{
					ViewerRenderHetero.renderHeteroBonds(viewer, ri);
				}
				else if(RenderingInfoRaiser.HETERO_HBOND_STRUCTURE.equals(ri.getSource()))
				{
					ViewerRenderHetero.renderHeteroHBond(viewer, ri);
				}
				else if(RenderingInfoRaiser.WATER.equals(ri.getSource()))
				{
					ViewerRenderWater.renderWater(viewer, ri);
				}
				else if(RenderingInfoRaiser.WATER_COVALENT_BONDS.equals(ri.getSource()))
				{
					ViewerRenderWater.renderWaterBonds(viewer, ri);
				}
				else if(RenderingInfoRaiser.WATER_HBOND_STRUCTURE.equals(ri.getSource()))
				{
					ViewerRenderWater.renderWaterHBonds(viewer, ri);
				}
				else if(RenderingInfoRaiser.JMOLPROPERTIES.equals(ri.getSource()))
				{
					renderJmolProperties(ri);
				}
				StarBiochemMain.setGlassPaneVisible(false);
			}
			catch (StarBiochemException e1)
			{
				this.closeException = e1;
				this.raise_CloseMoleculeEvent();
			}
		}
	}

	@Handles(raises = {})
	protected void handleAdjustInfoRaiser(final AdjustInfoRaiser raiser)
	{
		if (this.isShowing() && (null != raiser))
		{
			final AdjustInfo ai = raiser.getAdjustInfo();
			if(null != ai)
			{
				StarBiochemMain.setGlassPaneVisible(true);
				renderAdjust(ai);
				StarBiochemMain.setGlassPaneVisible(false);
			}
		}	
	}

	private void renderAdjust(AdjustInfo ai)
	{
		try
		{
			if(null != ai)
			{
				String script = getAdjustScript(ai);
				if((null != script) && !EMPTY_STRING.equals(script))
				{
					viewer.script(script);
				}
			}
		}
		catch(StarBiochemException e)
		{
			JOptionPane.showMessageDialog(this, e.getMessage() + "\n" + Messages.getString("MoleculeJmolContainer.3")); //$NON-NLS-1$ //$NON-NLS-2$
			try {
				if(null != viewer)
				{
					viewer.script("restore STATE \"scriptState\";"); //$NON-NLS-1$
				}
			} catch (StarBiochemException e1) {
				this.closeException = e;
				this.raise_CloseMoleculeEvent();
			}
		}
	}
	
	private String getAdjustScript(AdjustInfo ai) throws StarBiochemException
	{
		if(null != ai)
		{
			Map<String, AdjustSelectionInfo> selectionsInfo = ai.getSelectionsInfo();
			if((null != selectionsInfo) && !selectionsInfo.isEmpty())
			{
				Set<String> keySet = selectionsInfo.keySet();
				if((null != keySet) && !keySet.isEmpty())
				{
					String selection = ""; //$NON-NLS-1$
					boolean isFirst = true;
					Iterator<String> keyIterator = keySet.iterator();
					while(keyIterator.hasNext())
					{
						String key = keyIterator.next();
						AdjustSelectionInfo asi = selectionsInfo.get(key);
						if(null != asi)
						{
							String temp = getAdjustInfoSelectionString(key, asi);
							if((null != temp) && (0 != temp.length()))
							{
								if(isFirst)
								{
									selection += temp;
									isFirst = false;
								}
								else
								{
									selection += " or " + temp; //$NON-NLS-1$
								}
							}
						}
					}
					int radius = ai.getRadius();
					if(!"".equals(selection)) //$NON-NLS-1$
					{
						String script = "subset; select {" + selection + "};hide not selected; display WITHIN(" + radius + ".0, selected); center selected; zoom 0;"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						return script;
					}
					else
					{
						return "subset; select all; hide not selected; display WITHIN(" + radius + ".0, selected); center selected; zoom 0; "; //$NON-NLS-1$ //$NON-NLS-2$
					}
				}
			}
		}
		return null;
	}

	private String getAdjustInfoSelectionString(String key, AdjustSelectionInfo asi) throws StarBiochemException
	{
		String aisStr = ""; //$NON-NLS-1$
		if((null != key) && (null != asi) && (null != asi.getSelections()))
		{
			if(key.equals(CenterHeteroSelectionRaiser.class.getName()))
			{
				aisStr = ViewerRenderHetero.getAdjustHeteroFilteredSelectionScript(asi, viewer);
			}
			else if(key.equals(CenterNucleicRaiser.class.getName()))
			{
				aisStr = ViewerRenderNucleic.getAdjustNucleicFilteredSelectionsScript(asi, viewer);
			}
			else if(key.equals(CenterProteinPrimaryRaiser.class.getName()))
			{
				aisStr = ViewerRenderProteinPrimaryStructure.getAdjustProteinPrimaryFilteredSelectionScript(asi, viewer);
			}
			else if(key.equals(CenterProteinSecondaryRaiser.class.getName()))
			{
				aisStr = ViewerRenderProteinSecondaryStructure.getAdjustProteinSecondaryFilteredSelectionScript(asi);
			}
			else if(key.equals(CenterProteinTertiaryRaiser.class.getName()))
			{
				aisStr = ViewerRenderProteinTertiaryStructure.getAdjustProteinTertiarySelection(asi, viewer);
			}
			else if(key.equals(CenterProteinQuaternaryRaiser.class.getName()))
			{
				aisStr = ViewerRenderProteinQuaternaryStructure.getAdjustProteinQuatenaryFilteredSelectionScript(asi);
			}
			else if(key.equals(CenterWaterSelectionRaiser.class.getName()))
			{
				aisStr = ViewerRenderWater.getAdjustWaterFilteredSelectionScript(asi, viewer);
			}
		}
		return aisStr;
	}
	
	private void renderJmolProperties(RenderingInfo ri)
	{
		try
		{
			if(null != ri)
			{
				String propertiesScript = viewer.getJmolPropertiesScript(ri);
				if((null != propertiesScript) && (0 != propertiesScript.length()))
				{
					String filteredSelectionScript = viewer.getJmolFilteredSelectionScript(ri);
					if((null != filteredSelectionScript) && (0 != filteredSelectionScript.length()))
					{
							String script = filteredSelectionScript + propertiesScript;
							viewer.script(script);
					}
				}
			}
		}
		catch(StarBiochemException e)
		{
			JOptionPane.showMessageDialog(this, e.getMessage() + "\n" + Messages.getString("MoleculeJmolContainer.15")); //$NON-NLS-1$ //$NON-NLS-2$
			try {
				if(null != viewer)
				{
					viewer.script("restore STATE \"scriptState\";"); //$NON-NLS-1$
				}
			} catch (StarBiochemException e1) {
				this.closeException = e;
				this.raise_CloseMoleculeEvent();
			}
		}
	}
	
	private boolean inResetScene = false;
	public void resetScene()
	{
		try
		{
			if(!inResetScene)
			{
				if(null != viewer)
				{
					inResetScene = true;
					viewer.script("restore ORIENTATION \"orientation\";"); //$NON-NLS-1$
					inResetScene = false;
				}
			}
		}
		catch(StarBiochemException e)
		{
			this.closeException = e;
			this.raise_CloseMoleculeEvent();
		}
	}
	
	private boolean inReset = false;
	public void reset()
	{
		try
		{
			if(!inReset)
			{
				if(null != viewer)
				{
					inReset = true;
					viewer.script("restore STATE \"state\";"); //$NON-NLS-1$
					inReset = false;
				}
			}
		}
		catch(StarBiochemException e)
		{
			this.closeException = e;
			this.raise_CloseMoleculeEvent();
		}
	}

	public void initTree()
	{
	}

	StarBiochemException closeException = null;
	public StarBiochemException getStarBiochemException() {
		return closeException;
	}

}
