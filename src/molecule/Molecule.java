package molecule;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import app.Messages;

import molecule.interfaces.Bond;

import pdb.PDBAuthor;
import pdb.PDBCompnd;
import pdb.PDBHeader;
import pdb.PDBRemark;
import pdb.PDBHelix;
import pdb.PDBJournal;
import pdb.PDBSheetStrand;
import pdb.PDBSource;
import pdb.PDBTitle;

public class Molecule implements molecule.interfaces.Molecule
{
    private static final long serialVersionUID = 1L;
    ArrayList<Bond> tertiaryHbondArray = new ArrayList<Bond>();
	ArrayList<Bond> quaternaryHbondArray = new ArrayList<Bond>();
	Bond[] backBoneHbonds = null;
	Bond[] nonTertHbonds = null;

    public Molecule(
			String url,
			String[] heteroNotWaterNotNucleicArray,
			String[] nucleicArray,
			String[] proteinArray,
			String[] waterArray,
			boolean quaternarySSBonds,
			boolean tertiarySSBonds,
			PDBHelix[] helixArray,
			PDBSheetStrand[] sheetStrandArray,
			PDBAuthor[] authorArray,
			PDBCompnd[] compndArray,
			PDBHeader[] headerArray,
			PDBRemark[] remarkArray,
			PDBJournal[] journalArray,
			PDBSource[] sourceArray,
			PDBTitle[] titleArray,
			Bond[] hbonds
			)
	{
		this.url = url;
		this.heteroNotWaterNotNucleicArray = heteroNotWaterNotNucleicArray;
		this.nucleicArray = nucleicArray;
		this.proteinArray = proteinArray;
		this.waterArray = waterArray;
		this.quaternarySSBonds = quaternarySSBonds;
		this.tertiarySSBonds = tertiarySSBonds;
		this.helixArray = helixArray;
		this.sheetStrandArray = sheetStrandArray;
		this.authorArray = authorArray;
		this.compndArray = compndArray;
		this.headerArray = headerArray;
		this.remarkArray = remarkArray;
		this.journalArray = journalArray;
		this.sourceArray = sourceArray;
		this.titleArray = titleArray;
		this.hbonds = hbonds;
		
		this.structureProteinArray = this.getStructureProtienArrayLocal();
	    this.sortProteinHbonds();
	    this.tertiaryHBonds = this.getTertiaryHbondsLocal();
	    this.quaternaryHBonds = this.getQuaternaryHbondsLocal();
	    this.secondaryHBonds = this.getSecondaryHbondsLocal();

	}
			
	transient private String url;
	public String getUrl()
    {
	    return url;
    }
	
	transient private String[] heteroNotWaterNotNucleicArray;
	public String[] getHeteroNotWaterNotNucleicArray()
    {
	    return heteroNotWaterNotNucleicArray;
    }
	
	transient private String[] nucleicArray;
	public String[] getNucleicArray()
    {
	    return nucleicArray;
    }

	transient private String[] proteinArray;
	public String[] getProteinArray()
    {
	    return proteinArray;
    }

	transient private String[] waterArray;
	public String[] getWaterArray()
    {
	    return waterArray;
    }
	
	transient private String[] structureProteinArray;
	public String[] getStructureProteinArray()
    {
	    return structureProteinArray;
    }
	
	transient private Bond[] hbonds;
	public Bond[] getHbonds()
	{
		return hbonds;
	}
	
	transient private Bond[] quaternaryHBonds;
	public Bond[] getQuaternaryHBonds()
	{
		return quaternaryHBonds;
	}
	
	transient private Bond[] tertiaryHBonds;
	public Bond[] getTertiaryHBonds()
	{
		return tertiaryHBonds;
	}
	
	transient private Bond[] secondaryHBonds;
	public Bond[] getSecondaryHBonds()
	{
		return secondaryHBonds;
	}
	
	transient private PDBHelix[] helixArray = null;
	public PDBHelix[] getHelixArray()
    {
	    return helixArray;
    }
	
	transient private PDBSheetStrand[] sheetStrandArray;
	public PDBSheetStrand[] getSheetStrandArray()
    {
	    return sheetStrandArray;
    }
	
	transient private PDBAuthor[] authorArray;
	public PDBAuthor[] getAuthorArray()
    {
	    return authorArray;
    }
	
	transient private PDBCompnd[] compndArray;
	public PDBCompnd[] getCompndArray()
    {
	    return compndArray;
    }
	
	transient private PDBHeader[] headerArray;
	public PDBHeader[] getHeaderArray()
    {
	    return headerArray;
    }
	
	transient private PDBRemark[] remarkArray;
	public PDBRemark[] getRemarkArray()
    {
	    return remarkArray;
    }
	
	transient private PDBJournal[] journalArray;
	public PDBJournal[] getJournalArray()
    {
	    return journalArray;
    }
	
	transient private PDBSource[] sourceArray;
	public PDBSource[] getSourceArray()
    {
	    return sourceArray;
    }
	
	transient private PDBTitle[] titleArray;
	public PDBTitle[] getTitleArray()
    {
	    return titleArray;
    }
	
	transient private boolean quaternarySSBonds;
	public boolean hasQuaternarySSBonds() 
	{
		return quaternarySSBonds;
	}
	
	transient private boolean tertiarySSBonds;
	public boolean hasTertiarySSBonds()
	{
		return tertiarySSBonds;
	}
	
	private void sortProteinHbonds()
	{
		if(null != hbonds && 0 != hbonds.length)
		{
			ArrayList<Bond> backBoneHbondArray = new ArrayList<Bond>();
			for(int i = 0; hbonds.length != i; i++)
			{
				String atom1 = hbonds[i].getAtom1();
				String atom2 = hbonds[i].getAtom2();
				
				if ((atom1 != null) && (atom2 != null))
				{	
					if(null != structureProteinArray && 0 != structureProteinArray.length)
					{
						int count = 0;
						for(int j = 0; structureProteinArray.length != j; j++)
						{
							String myProtein = getHbondRes(structureProteinArray[j]);
							if(null != myProtein)
							{
								if(atom1.startsWith(myProtein))
								{
									count++;
									atom1 = getHbondStructureId(structureProteinArray[j]) + " " + atom1; //$NON-NLS-1$
								}
								if(atom2.startsWith(myProtein))
								{
									count++;
									atom2 = getHbondStructureId(structureProteinArray[j]) + " " + atom2; //$NON-NLS-1$
								}
							}
						}
						if(count == 2)
						{
							boolean isBackBone = isBackBoneHbonds(atom1, atom2);
							if((isBackBone) && ((null != atom2) && (null != atom1)))
							{ 
								Bond bond = new molecule.Bond(atom1, atom2);
								backBoneHbondArray.add(bond);
							}
							if(!isBackBone)
							{
								String chain1 = getChainId(atom1);
								String chain2 = getChainId(atom2);
								atom1 = getHbondRes(atom1);
								atom2 = getHbondRes(atom2);
								if(((null != chain1) && (null != chain2)) && ((null != atom1) && (null != atom2)))
								{
									if(chain1.equals(chain2))
									{
										Bond bond = new molecule.Bond(atom1, atom2);
										tertiaryHbondArray.add(bond);
									}
								}
								if(!chain1.equals(chain2))
								{
									Bond bond = new molecule.Bond(atom1, atom2);
									quaternaryHbondArray.add(bond);
								}
							}
						}
					}
				}
			}
			if(!backBoneHbondArray.isEmpty())
			{
				backBoneHbonds = backBoneHbondArray.toArray(new Bond[0]);
			}
		}
	}

	private boolean isBackBoneHbonds(String atom1, String atom2)
	{
		boolean isBackBone = false;
		if((null != atom1) && (null != atom2))
		{
			int count = 0;
			String nitro = "N"; //$NON-NLS-1$
			String oxygen = "O"; //$NON-NLS-1$
			String hydro = "H"; //$NON-NLS-1$
			
			String atomID1 = atom1.substring(atom1.length()-1).toString();
			String atomID2 = atom2.substring(atom2.length()-1).toString();

			if(atomID1.equals(nitro) || atomID1.equals(oxygen) || atomID1.equals(hydro)) count++;
			if(atomID2.equals(nitro) || atomID2.equals(oxygen) || atomID2.equals(hydro)) count++;

			if(count == 2)
			{
				isBackBone = true;
			}
		}
		return isBackBone;
	}
	
	private Bond[] getQuaternaryHbondsLocal()
	{
		if(null != nonTertHbonds && 0 != nonTertHbonds.length)
		{
			for(int i = 0; nonTertHbonds.length != i; i++)
			{
				String atom1 = nonTertHbonds[i].getAtom1();
				String atom2 = nonTertHbonds[i].getAtom2();
				if ((atom1 != null) && (atom2 != null))
				{	
					String hbondChain1 = getChainId(atom1);
					String hbondChain2 = getChainId(atom2);
					if (((null != hbondChain1) && (null != hbondChain2)) && !hbondChain1.equals(hbondChain2))
					{
						atom1 = getHbondRes(atom1);
						atom2 = getHbondRes(atom2);
						Bond bond = new molecule.Bond(atom1, atom2);
						quaternaryHbondArray.add(bond);
					}
				}
			}
			if(!quaternaryHbondArray.isEmpty())
			{
				quaternaryHBonds = quaternaryHbondArray.toArray(new Bond[0]);
			}
		}
		return quaternaryHBonds;		
	}

	private Bond[] getTertiaryHbondsLocal()
	{
		Bond[] tertiaryHbonds = null;
		ArrayList<Bond> nonTertHbondArray = new ArrayList<Bond>();
		if(null != backBoneHbonds && 0 != backBoneHbonds.length)
		{
			for(int i=0; backBoneHbonds.length != i; i++)
			{
				Bond bond = null;
				boolean isTertiary = false;
				String atom1 = backBoneHbonds[i].getAtom1();
				String atom2 = backBoneHbonds[i].getAtom2();
				if ((atom1 != null) && (atom2 != null))
				{
					String hbondChain1 = getChainId(atom1);
					String hbondChain2 = getChainId(atom2);
					
					if(((null != hbondChain1) && (null != hbondChain2)) && (hbondChain1.equals(hbondChain2)))
					{
						String structureId1 = getHbondStructureId(atom1);
						String structureId2 = getHbondStructureId(atom2);
						if((null != structureId1) && (null != structureId2))
						{
							if(!structureId1.equals(structureId2))
							{
								isTertiary = true;
							}
						}
					}
					atom1 = getHbondRes(atom1);
					atom2 = getHbondRes(atom2);
					if(!isTertiary)
					{
						bond = new molecule.Bond(atom1, atom2);
						nonTertHbondArray.add(bond);
					}
					if(isTertiary)
					{
						bond = new molecule.Bond(atom1, atom2);
						tertiaryHbondArray.add(bond);
					}
				}
			}
		}
		if(tertiaryHbondArray.size() != 0)
		{
			tertiaryHbonds = tertiaryHbondArray.toArray(new Bond[0]);
		}
		if(nonTertHbondArray.size() != 0)
		{
			nonTertHbonds = nonTertHbondArray.toArray(new Bond[0]);
		}
		return tertiaryHbonds;
	}
	
	private String getHbondStructureId(String atom)
	{
		String structureId = null;
		if(null != atom)
		{
			Pattern p = Pattern.compile("([^\\[]+)\\[");  //$NON-NLS-1$
			Matcher m = p.matcher(atom);
			boolean match = m.find();
			if(match)
			{
				structureId = m.group(1).trim();
			}
		}
		return structureId;
	}

	private String getHbondRes(String atom)
	{
		String hbondRes = null;
		if(null != atom)
		{
			Pattern p = Pattern.compile("(\\[[^ ]+)");  //$NON-NLS-1$
			Matcher m = p.matcher(atom);
			boolean match = m.find();
			if(match)
			{
				hbondRes = m.group(1).trim();
			}
		}
		return hbondRes;
	}
	
	private Bond[] getSecondaryHbondsLocal()
	{
		Bond[] secondaryHBonds = null;
		if((null != nonTertHbonds) && (0 != nonTertHbonds.length))
		{
			ArrayList<Bond> secondadryHbondArray = new ArrayList<Bond>();

			for(int i=0; backBoneHbonds.length != i; i++)
			{
				Bond bond = null;
				String atom1 = backBoneHbonds[i].getAtom1();
				String atom2 = backBoneHbonds[i].getAtom2();
				
				if ((atom1 != null) && (atom2 != null))
				{
					String structureId1 = getHbondStructureId(atom1);
					String structureId2 = getHbondStructureId(atom2);
					if((null != structureId1) && (null != structureId2))
					{
						if(structureId1.equals(structureId2))
						{
							atom1 = getHbondRes(atom1);
							atom2 = getHbondRes(atom2);	
							bond = new molecule.Bond(atom1, atom2);
							secondadryHbondArray.add(bond);
						}
					}
				}
			}
			if(secondadryHbondArray.size() != 0)
			{
				secondaryHBonds = secondadryHbondArray.toArray(new Bond[0]);
			}
		}
		return secondaryHBonds;
	}
	
	private String getChainId(String atom)
	{	
		String chain = null;
		if(null != atom)
		{
			Pattern p = Pattern.compile("\\:([^\\:]+)\\.");  //$NON-NLS-1$
			Matcher m = p.matcher(atom);
			boolean match = m.find();
			if(match)
			{
				chain = m.group(1).trim();
			}
		}	
		return chain;
	}
	
	private String[] getStructureProtienArrayLocal()
	{
		ArrayList<String> aminoAcids = new ArrayList<String>();
		if(null != aminoAcids)
		{
			String[] residues = proteinArray;
			if(null != residues)
			{
				for(int i=0; residues.length != i; i++)
				{
					aminoAcids.add(getSecondaryStructureString(residues[i]));
				}
			}
			if(!aminoAcids.isEmpty())
			{
				structureProteinArray = aminoAcids.toArray(new String[0]);
			}	
		}
		return structureProteinArray;
	}
	
	int myCount = 0;
	int coilId = 0;
	private String getSecondaryStructureString(String residue)
	{
		String prefixedResidue = getHelixPrefixedResidue(residue);
		if(null != prefixedResidue)
		{
			myCount = 0;
			return prefixedResidue;
		}
		prefixedResidue = getSheetPrefixedResidue(residue);
		if(null != prefixedResidue)
		{
			myCount = 0;
			return prefixedResidue;
		}
		if(myCount == 0)
		{
			coilId++;
			prefixedResidue = Messages.getString("Molecule.1") + coilId + "  " + residue; //$NON-NLS-1$ //$NON-NLS-2$
			myCount++;
		}
		if(myCount != 0)
		{
			prefixedResidue = Messages.getString("Molecule.1") + coilId + " " + residue; //$NON-NLS-1$ //$NON-NLS-2$
		}
		return prefixedResidue;
	}
	
	private String getHelixPrefixedResidue(String residue)
	{
		if((null != residue))
		{
			PDBHelix[] helices = getHelixArray();
			if(null != helices)
			{
				for(int i=0; helices.length != i; i++)
				{
					pdb.PDBHelix helix = helices[i];
					if(null != helix)
					{
						int initSeqNum = -1;
						String initChain = null;
						String initString = helix.initResString();
						if(null != initString)
						{
							int initCloseBracket = initString.indexOf("]"); //$NON-NLS-1$
							if(-1 != initCloseBracket)
							{
								int initColon = initString.indexOf(":"); //$NON-NLS-1$
								if(-1 != initColon)
								{
									initSeqNum = Integer.parseInt(initString.substring(initCloseBracket + 1, initColon));
									initChain = initString.substring(initColon + 1);
								}
							}
						}

						int endSeqNum = -1;
						String endString = helix.endResString();
						if(null != endString)
						{
							int endCloseBracket = endString.indexOf("]"); //$NON-NLS-1$
							if(-1 != endCloseBracket)
							{
								int endColon = endString.indexOf(":"); //$NON-NLS-1$
								if(-1 != endColon)
								{
									endSeqNum = Integer.parseInt(endString.substring(endCloseBracket + 1, endColon));
								}
							}
						}
						
						String helixID = helix.getHelixID();
						int resSeqNum = -1;
						String resChain = null;
						int resCloseBracket = residue.indexOf("]"); //$NON-NLS-1$
						if(-1 != resCloseBracket)
						{
							int resColon = residue.indexOf(":"); //$NON-NLS-1$
							if(-1 != resColon)
							{
								resChain = residue.substring(resColon + 1);
								
								int resCaret = residue.indexOf("^"); //$NON-NLS-1$
								try
								{
									if(-1 != resCaret)
									{
										resSeqNum = Integer.parseInt(residue.substring(resCloseBracket + 1, resCaret));
									}
									else
									{
										resSeqNum = Integer.parseInt(residue.substring(resCloseBracket + 1, resColon));
									}
									if(resChain.equals(initChain) && (resSeqNum >= initSeqNum) && (resSeqNum <= endSeqNum))
									{
										return (Messages.getString("Molecule.2") + helixID + "  " + residue); //$NON-NLS-1$ //$NON-NLS-2$
									}
								}
								catch(NumberFormatException nfe)
								{
									System.err.println(this.getClass().getName() + ".getHelixPrefixedResidue residue: " + residue); //$NON-NLS-1$
								}
							}
						}
					}
				}
			}
		}
		return null;
	}
	
	private String getSheetPrefixedResidue(String residue)
	{
		if((null != residue))
		{
			PDBSheetStrand[] sheetstrands = getSheetStrandArray();
			if(null != sheetstrands)
			{
				for(int i=0; sheetstrands.length != i; i++)
				{
					pdb.PDBSheetStrand sheetstrand = sheetstrands[i];
					if(null != sheetstrand)
					{
						int initSeqNum = -1;
						String initChain = null;
						String initString = sheetstrand.initResString();
						if(null != initString)
						{
							int initCloseBracket = initString.indexOf("]"); //$NON-NLS-1$
							if(-1 != initCloseBracket)
							{
								int initColon = initString.indexOf(":"); //$NON-NLS-1$
								if(-1 != initColon)
								{
									initSeqNum = Integer.parseInt(initString.substring(initCloseBracket + 1, initColon));
									initChain = initString.substring(initColon + 1);
								}
							}
						}

						int endSeqNum = -1;
						String endString = sheetstrand.endResString();
						if(null != endString)
						{
							int endCloseBracket = endString.indexOf("]"); //$NON-NLS-1$
							if(-1 != endCloseBracket)
							{
								int endColon = endString.indexOf(":"); //$NON-NLS-1$
								if(-1 != endColon)
								{
									endSeqNum = Integer.parseInt(endString.substring(endCloseBracket + 1, endColon));
								}
							}
						}

						String sheetID = sheetstrand.getSheetID();
						int resSeqNum = -1;
						String resChain = null;
						int resCloseBracket = residue.indexOf("]"); //$NON-NLS-1$
						if(-1 != resCloseBracket)
						{
							int resColon = residue.indexOf(":"); //$NON-NLS-1$
							if(-1 != resColon)
							{
								resChain = residue.substring(resColon + 1);
								
								int resCaret = residue.indexOf("^"); //$NON-NLS-1$
								try
								{
									if(-1 != resCaret)
									{
										resSeqNum = Integer.parseInt(residue.substring(resCloseBracket + 1, resCaret));
									}
									else
									{
										resSeqNum = Integer.parseInt(residue.substring(resCloseBracket + 1, resColon));
									}
									if(resChain.equals(initChain) && ((resSeqNum >= initSeqNum) && (resSeqNum <= endSeqNum)))
									{
										return (Messages.getString("Molecule.3") + sheetID + "  " + residue); //$NON-NLS-1$ //$NON-NLS-2$
									}
								}
								catch(NumberFormatException nfe)
								{
									System.err.println(this.getClass().getName() + ".getSheetPrefixedResidue residue: " + residue); //$NON-NLS-1$
								}
							}
						}
					}
				}
			}
		}
		return null;
	}
	
}