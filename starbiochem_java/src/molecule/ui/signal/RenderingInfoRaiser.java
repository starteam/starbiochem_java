package molecule.ui.signal;

import molecule.interfaces.RenderingInfo;
import star.event.Raiser;

@star.annotations.Raiser
public interface RenderingInfoRaiser extends Raiser
{
	public final static String NUCLEIC = "NUCLEIC"; //$NON-NLS-1$
	public final static String NUCLEIC_COVALENT_BONDS = "NUCLEIC_COVALENT_BONDS"; //$NON-NLS-1$
	public final static String NUCLEIC_HBOND_STRUCTURE = "NUCLEIC_HBOND_STRUCTURE"; //$NON-NLS-1$

	public final static int BASE_VISIBLE =        0x002;
	public final static int PHOSPHATE_VISIBLE =   0x004;
	public final static int SUGAR_VISIBLE =    0x008;
	
	public final static String PROTEIN_PRIMARY_STRUCTURE = "PROTEIN_PRIMARY_STRUCTURE"; //$NON-NLS-1$
	public final static String PROTEIN_PRIMARY_COVALENT_BONDS = "PROTEIN_PRIMARY_COVALENT_BONDS"; //$NON-NLS-1$
	public final static int BACKBONE_VISIBLE =      0x020;
	public final static int SIDECHAIN_VISIBLE =     0x040;
	
	public final static String PROTEIN_SECONDARY_STRUCTURE = "PROTEIN_SECONDARY_STRUCTURE"; //$NON-NLS-1$
	public final static String PROTEIN_SECONDARY_HBOND_STRUCTURE = "PROTEIN_SECONDARY_HBOND_STRUCTURE"; //$NON-NLS-1$

	public final static int ALPHAHELIX_VISIBLE =    0x0200;
	public final static int BETASHEET_VISIBLE =     0x0400;
	public final static int COIL_VISIBLE =          0x0800;
	
	public final static String PROTEIN_TERTIARY_STRUCTURE = "PROTEIN_TERTIARY_STRUCTURE"; //$NON-NLS-1$
	public final static String PROTEIN_TERTIARY_COVALENT_BONDS = "PROTEIN_TERTIARY_COVALENT_BONDS"; //$NON-NLS-1$
	public final static String PROTEIN_TERTIARY_HBOND_STRUCTURE = "PROTEIN_TERTIARY_HBOND_STRUCTURE"; //$NON-NLS-1$

	public final static int TERTIARY_NONE =                    0x01000;
	public final static int NOTCHARGED_NEUTRAL_VISIBLE =       0x02000;
	public final static int POSITIVELYCHARGED_BASIC_VISIBLE =  0x04000;
	public final static int NEGATIVELYCHARGED_ACIDIC_VISIBLE = 0x08000;
	public final static int NONPOLAR_HYDROPHOBIC_VISIBLE =     0x10000;
	public final static int POLAR_HYDROPHILIC_VISIBLE =        0x20000;
	public final static int BURIED_VISIBLE =                   0x40000;
	public final static int SURFACE_VISIBLE =                  0x80000;
	
	public final static String PROTEIN_QUATERNARY_STRUCTURE = "PROTEIN_QUATERNARY_STRUCTURE"; //$NON-NLS-1$
	public final static String PROTEIN_QUATERNARY_SSBOND_STRUCTURE = "PROTEIN_QUATERNARY_SSBOND_STRUCTURE"; //$NON-NLS-1$
	public final static String PROTEIN_QUATERNARY_HBOND_STRUCTURE = "PROTEIN_QUATERNARY_HBOND_STRUCTURE"; //$NON-NLS-1$
	public final static String PROTEIN_QUATERNARY_STRUCTURE_TRANSLUCENCY = "PROTEIN_QUATERNARY_STRUCTURE_TRANSLUCENCY"; //$NON-NLS-1$
	public final static int ALL_CHAINS_VISIBLE =         0x100000;
	
	public final static String HETERO = "HETERO"; //$NON-NLS-1$
	public final static String HETERO_COVALENT_BONDS = "HETERO_COVALENT_BONDS"; //$NON-NLS-1$
	public final static String HETERO_HBOND_STRUCTURE = "HETERO_HBOND_STRUCTURE"; //$NON-NLS-1$
	public final static int ALL_HETERO_VISIBLE =       	0x1000000;

	public final static String WATER = "WATER"; //$NON-NLS-1$
	public final static String WATER_COVALENT_BONDS = "WATER_COVALENT_BONDS"; //$NON-NLS-1$
	public final static String WATER_HBOND_STRUCTURE = "WATER_HBOND_STRUCTURE"; //$NON-NLS-1$
	public final static int ALL_WATER_VISIBLE =       	0x10000000;

	public final static String ADJUST = "ADJUST"; //$NON-NLS-1$

	public final static String JMOLPROPERTIES = "JMOLPROPERTIES"; //$NON-NLS-1$
	public final static String JMOLLOCATIONPROPERTIES = "JMOLLOCATIONPROPERTIES"; //$NON-NLS-1$
	public final static String JMOLCENTERPROPERTIES = "JMOLCENTERPROPERTIES"; //$NON-NLS-1$
	
	RenderingInfo getRenderingInfo();
}
