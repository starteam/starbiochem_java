package pdb;

public class PDBRemark extends PDBRecord
{
	// REMARK
	// Overview
	// REMARK records present experimental details, annotations, comments, and information not included
	// in other records. In a number of cases, REMARKs are used to expand the contents of other record
	// types. A new level of structure is being used for some REMARK records. This is expected to facilitate
	// searching and will assist in the conversion to a relational database.
	// The very first line of every set of REMARK records is used as a spacer to aid in reading.
	// COLUMNS DATA TYPE FIELD DEFINITION
	// ---------------------------------------------------------------------------------
	// 1 - 6 Record name "REMARK"
	// 8 - 10 Integer remarkNum Remark number. It is not an error
	// for remark n to exist in an entry
	// when remark n-1 does not.
	// 12 - 70 LString empty Left as white space in first line of
	// each new remark.
	//	
	// REMARK 0
	// REMARK 0 identifies entries in which a re-refinement has been performed using the
	// data from existing entry.
	// Template
	// REMARK 0
	// REMARK 0 THIS ENTRY yyyy REFLECTS AN ALTERNATIVE MODELING OF THE
	// REMARK 0 STRUCTURAL DATA IN RxxxxSF (or xxxx.MR).
	// REMARK 0 IN PDB ENTRY YYYY INFORMATION IN REMARK 200 (or 210)
	// REMARK 0 SERIES IS BASED ON THE EXPERIMENT DESCRIBED IN PDB ENTRY xxxx
	// REMARK 0 ORIGINAL DATA DETERMINED BY AUTHOR:
	// REMARK 0 AUTHOR INITIALS, AUTHOR LAST NAME
	// Note: In entries where REMARK 0 is included as described above, remarks
	//
	// REMARK 1, REMARK 200/210 and REMARK 900 will also reflect the reuse of
	// existing experimental data.
	//
	// REMARK 2
	// REMARK 2 states the highest resolution, in Angstroms, that was used in building the model. As with
	// all the remarks, the first REMARK 2 record is empty and is used as a spacer.
	// Record Format and Details
	// * The second REMARK 2 record has one of two formats. The first is used for diffraction studies, the
	// second for other types of experiments in which resolution is not relevant, e.g., NMR and theoretical
	// modeling.
	// * For diffraction experiments:
	// COLUMNS DATA TYPE FIELD DEFINITION
	// --------------------------------------------------------------------------------
	// 1 - 6 Record name "REMARK"
	// 10 LString(1) "2"
	// 12 - 22 LString(11) "RESOLUTION."
	// 23 - 27 Real(5.2) resolution Resolution.
	// 29 - 38 LString(10) "ANGSTROMS."
	// * REMARK 2 when not a diffraction experiment:
	// COLUMNS DATA TYPE FIELD DEFINITION
	// --------------------------------------------------------------------------------
	// 1 - 6 Record name "REMARK"
	// 10 LString(1) "2"
	// 12 - 38 LString(28) "RESOLUTION. NOT APPLICABLE."
	// 41 - 70 String comment Comment.
	// * Additional explanatory text may be included starting with the third line of the REMARK 2 record. For
	// example, depositors may wish to qualify the resolution value provided due to unusual experimental
	// conditions.
	// COLUMNS DATA TYPE FIELD DEFINITION
	// -------------------------------------------------------------------------------
	// PDB File Format v. 2.3 Page 56
	// 1 - 6 Record name "REMARK"
	// 10 LString(1) "2"
	// 12 - 22 LString(11) "RESOLUTION."
	// 24 - 70 String comment Comment.
	// Example
	// 1 2 3 4 5 6 7
	// 1234567890123456789012345678901234567890123456789012345678901234567890
	// REMARK 2
	// REMARK 2 RESOLUTION. 1.74 ANGSTROMS.
	// REMARK 2
	// REMARK 2 RESOLUTION. NOT APPLICABLE.
	// REMARK 2
	// REMARK 2 RESOLUTION. NOT APPLICABLE.
	// REMARK 2 THIS EXPERIMENT WAS CARRIED OUT USING FLUORESCENCE TRANSFER
	// REMARK 2 AND THEREFORE NO RESOLUTION CAN BE CALCULATED.
	//
	// REMARK 3
	// Overview
	// REMARK 3 presents information on refinement program(s) used and the related statistics. For nondiffraction
	// studies, REMARK 3 is used to describe any refinement done, but its format in those cases
	// is mostly free text.
	// If more than one refinement package was used, they may be named in "OTHER REFINEMENT
	// REMARKS". However, Remark 3 statistics are given for the final refinement run.
	// The format of this remark changes with the evolution of refinement software. Selected representative
	// templates or examples are provided here.
	// Details
	// * The value "NULL" is given when there is no data available for a particular token. REMARK 4
	// Remark 4 indicates the version of the PDB file format used to generate the file.
	// Version 3.1 files include a version remark like the following:
	// 1 2 3 4 5 6 7
	// 1234567890123456789012345678901234567890123456789012345678901234567890
	//	
	// REMARK 4
	// REMARK 4 1ABC COMPLIES WITH FORMAT V. 3.1, 1-AUG-2007
	// Version 3.0 files include a version remark like the following:
	// REMARK 4
	// REMARK 4 1ABC COMPLIES WITH FORMAT V. 3.0, 1-DEC-2006
	// REMARK 4
	// REMARK 4 THIS IS THE REMEDIATED VERSION OF THIS PDB ENTRY.
	// REMARK 4 REMEDIATED DATA FILE REVISION 3.XXX (YYYY-MM-DD)
	//
	// REMARK 40
	// Pre-submission validation and similar software used before deposition, may be listed in a
	// REMARK 40. This remark will list the software name, authors and function of the program.
	// Results of these software methods will not be listed. This free text remark is optional.
	// Template
	// REMARK 40 FREE TEXT DESCRIPTION
	// REMARKs 102-199 Nucleic Acids
	// The text remarks for nucleic acids will reflect the standardization of nomenclature for
	// the polymer nucleotides described in later sections. In particular, the polymeric
	// deoxyribonucleotides are represented by 2-letter codes DC, DG, DA, and DT to
	// distinguish these from their ribonucletide counterparts. The asterisk character in the
	// in saccharide atom names is replaced by the single prime character. The text of
	//
	// REMARK 105 is correspondingly changed as follows.
	// REMARK 105
	// Remark 105 is mandatory if nucleic acids exist in an entry.
	// Template
	// 1 2 3 4 5 6 7
	// 1234567890123456789012345678901234567890123456789012345678901234567890
	// REMARK 105
	// REMARK 105 THE PROTEIN DATA BANK HAS ADOPTED THE SACCHARIDE CHEMISTS
	// REMARK 105 NOMENCLATURE FOR ATOMS OF THE DEOXYRIBOSE/RIBOSE MOIETY
	// REMARK 105 RATHER THAN THAT OF THE NUCLEOSIDE CHEMISTS. THE RING
	// REMARK 105 OXYGEN ATOM IS LABELLED O4 INSTEAD OF O1.
	// REMARK 200, X-ray Diffraction Experimental Details
	// The following example illustrates the how REMARK 200 will be used in cases in
	// which multiple data collections are described. In this example, data items
	// corresponding to different data collection sessions are separated by semi-colons.
	// Multiple data values within a single session (e.g wavelength) are separated by
	// commas.
	//
	// REMARK 200
	// REMARK 200 EXPERIMENTAL DETAILS
	// REMARK 200 EXPERIMENT TYPE : X-RAY DIFFRACTION
	// REMARK 200 DATE OF DATA COLLECTION : 17-MAR-2002; 17-MAR-2002
	// REMARK 200 TEMPERATURE (KELVIN) : 100; 100
	// REMARK 200 PH : 8.00
	// REMARK 200 NUMBER OF CRYSTALS USED : 2
	// REMARK 200
	// REMARK 200 SYNCHROTRON (Y/N) : Y; Y
	// REMARK 200 RADIATION SOURCE : APS ; APS
	// REMARK 200 BEAMLINE : 17ID; 17ID
	// REMARK 200 X-RAY GENERATOR MODEL : NULL
	// REMARK 200 MONOCHROMATIC OR LAUE (M/L) : M; M
	// REMARK 200 WAVELENGTH OR RANGE (A) : 1.5545; 1.0720, 1.0723,
	// REMARK 200 1.0543
	// REMARK 200 MONOCHROMATOR : SI (111); SI (111)
	// REMARK 200 OPTICS : NULL
	// REMARK 200
	// REMARK 200 DETECTOR TYPE : CCD; CCD
	// REMARK 200 DETECTOR MANUFACTURER : ADSC QUANTUM 210; ADSC
	// REMARK 200 QUANTUM 210
	// REMARK 200 INTENSITY-INTEGRATION SOFTWARE : DENZO
	// REMARK 200 DATA SCALING SOFTWARE : HKL
	// REMARK 200
	// REMARK 200 NUMBER OF UNIQUE REFLECTIONS : 29132
	// REMARK 200 RESOLUTION RANGE HIGH (A) : 1.900
	// REMARK 200 RESOLUTION RANGE LOW (A) : 30.000
	// REMARK 200 REJECTION CRITERIA (SIGMA(I)) : 0.000
	// REMARK 200
	// REMARK 200 OVERALL.
	// REMARK 200 COMPLETENESS FOR RANGE (%) : 98.3
	// REMARK 200 DATA REDUNDANCY : 19.800
	// REMARK 200 R MERGE (I) : NULL
	// REMARK 200 R SYM (I) : 0.07500
	// REMARK 200 <I/SIGMA(I)> FOR THE DATA SET : 17.0000
	// REMARK 200
	// REMARK 200 IN THE HIGHEST RESOLUTION SHELL.
	// REMARK 200 HIGHEST RESOLUTION SHELL, RANGE HIGH (A) : 1.90
	// REMARK 200 HIGHEST RESOLUTION SHELL, RANGE LOW (A) : 1.97
	// REMARK 200 COMPLETENESS FOR SHELL (%) : 83.4
	// REMARK 200 DATA REDUNDANCY IN SHELL : 3.00
	// REMARK 200 R MERGE FOR SHELL (I) : NULL
	// REMARK 200 R SYM FOR SHELL (I) : 0.65000
	// REMARK 200 <I/SIGMA(I)> FOR SHELL : 1.500
	// REMARK 200
	// REMARK 200 DIFFRACTION PROTOCOL: SINGLE WAVELENGTH; MAD
	// REMARK 200 METHOD USED TO DETERMINE THE STRUCTURE: MAD
	// REMARK 200 SOFTWARE USED: SOLVE 2.02
	// REMARK 200 STARTING MODEL: NULL
	// REMARK 200
	// REMARK 200 REMARK: NULL
	//
	// REMARK 300, Biomolecule
	// Description of the biologically functional molecule (biomolecule) in free text.
	// Remark 300 is mandatory if Remark 350 is provided.
	// Template
	// 1 2 3 4 5 6 7
	// 1234567890123456789012345678901234567890123456789012345678901234567890
	// REMARK 300
	// REMARK 300 BIOMOLECULE: 1
	// REMARK 300 SEE REMARK 350 FOR THE AUTHOR PROVIDED AND PROGRAM
	// REMARK 300 GENERATED ASSEMBLY INFORMATION FOR THE STRUCTURE IN
	// REMARK 300 THIS ENTRY. THE REMARK MAY ALSO PROVIDE INFORMATION ON
	// REMARK 300 BURIED SURFACE AREA.
	// REMARK 300 free text 
	// Example - Icosahedral virus
	// REMARK 300
	// REMARK 300 BIOMOLECULE: 1
	// REMARK 300 SEE REMARK 350 FOR THE AUTHOR PROVIDED AND PROGRAM
	// REMARK 300 GENERATED ASSEMBLY INFORMATION FOR THE STRUCTURE IN
	// REMARK 300 THIS ENTRY. THE REMARK MAY ALSO PROVIDE INFORMATION ON
	// REMARK 300 BURIED SURFACE AREA.
	// REMARK 300 DETAILS: THE ASSEMBLY REPRESENTED IN THIS ENTRY HAS REGULAR
	// REMARK 300 ICOSAHEDRAL POINT SYMMETRY (SCHOENFLIES SYMBOL = I).
	// Example - Helical viruses
	// REMARK 300 BIOMOLECULE: 1
	// REMARK 300 SEE REMARK 350 FOR THE AUTHOR PROVIDED AND PROGRAM
	// REMARK 300 GENERATED ASSEMBLY INFORMATION FOR THE STRUCTURE IN
	// REMARK 300 THIS ENTRY. THE REMARK MAY ALSO PROVIDE INFORMATION ON
	// REMARK 300 BURIED SURFACE AREA.
	// REMARK 300 DETAILS: THE ASSEMBLY REPRESENTED IN THIS ENTRY HAS REGULAR
	// REMARK 300 HELICAL SYMMETRY WITH THE FOLLOWING PARAMETERS:
	// REMARK 300 ROTATION PER SUBUNIT (TWIST) = -33.23 DEGREES
	// REMARK 300 RISE PER SUBUNIT (HEIGHT) = 16.00 ANGSTROMS
	// REMARK 300 IN ADDITION, THERE IS 5-FOLD CIRCULAR
	// REMARK 300 SYMMETRY AROUND THE HELIX AXIS
	//
	// REMARK 350, Generating the Biomolecule
	// Remark 350 presents all transformations, both crystallographic and noncrystallographic,
	// needed to generate the biomolecule. These transformations
	// operate on the coordinates in the entry. Both author and computational descriptions
	// of assemblies are provided if these are available.
	// Example  Author and computed assembly predictions agree
	// REMARK 350 COORDINATES FOR A COMPLETE MULTIMER REPRESENTING THE KNOWN
	// REMARK 350 BIOLOGICALLY SIGNIFICANT OLIGOMERIZATION STATE OF THE
	// REMARK 350 MOLECULE CAN BE GENERATED BY APPLYING BIOMT TRANSFORMATIONS
	// REMARK 350 GIVEN BELOW. BOTH NON-CRYSTALLOGRAPHIC AND
	// REMARK 350 CRYSTALLOGRAPHIC OPERATIONS ARE GIVEN.
	// REMARK 350
	// REMARK 350 BIOMOLECULE: 1
	// REMARK 350 AUTHOR DETERMINED BIOLOGICAL UNIT: DODECAMERIC
	// REMARK 350 SOFTWARE DETERMINED QUATERNARY STRUCTURE: DODECAMERIC
	// REMARK 350 SOFTWARE USED: PISA
	// REMARK 350 AVERAGE BURIED SURFACE AREA: 2990 ANGSTROM**2
	// REMARK 350 APPLY THE FOLLOWING TO CHAINS: A, B, C, D, E, F, G, H, I,
	// REMARK 350 J, K, L
	// REMARK 350 BIOMT1 1 1.000000 0.000000 0.000000 0.00000
	// REMARK 350 BIOMT2 1 0.000000 1.000000 0.000000 0.00000
	// REMARK 350 BIOMT3 1 0.000000 0.000000 1.000000 0.00000
	// Note: The value for the average buried surface area will be round to the nearest 10.
	// Example  Author and computed assembly predictions differ
	// REMARK 350 COORDINATES FOR A COMPLETE MULTIMER REPRESENTING THE KNOWN
	// REMARK 350 BIOLOGICALLY SIGNIFICANT OLIGOMERIZATION STATE OF THE
	// REMARK 350 MOLECULE CAN BE GENERATED BY APPLYING BIOMT TRANSFORMATIONS
	// REMARK 350 GIVEN BELOW. BOTH NON-CRYSTALLOGRAPHIC AND
	// REMARK 350 CRYSTALLOGRAPHIC OPERATIONS ARE GIVEN.
	// REMARK 350
	// REMARK 350 BIOMOLECULE: 1
	// REMARK 350 AUTHOR DETERMINED BIOLOGICAL UNIT: HEXAMERIC
	// REMARK 350 APPLY THE FOLLOWING TO CHAINS: A, B, C, D, E, F
	// REMARK 350 BIOMT1 1 1.000000 0.000000 0.000000 0.00000
	// REMARK 350 BIOMT2 1 0.000000 1.000000 0.000000 0.00000
	// REMARK 350 BIOMT3 1 0.000000 0.000000 1.000000 0.00000
	// REMARK 350
	// REMARK 350 BIOMOLECULE: 2
	// REMARK 350 AUTHOR DETERMINED BIOLOGICAL UNIT: HEXAMERIC
	// REMARK 350 APPLY THE FOLLOWING TO CHAINS: G, H, I, J, K, L
	// REMARK 350 BIOMT1 1 1.000000 0.000000 0.000000 0.00000
	// REMARK 350 BIOMT2 1 0.000000 1.000000 0.000000 0.00000
	// REMARK 350 BIOMT3 1 0.000000 0.000000 1.000000 0.00000
	// REMARK 350
	// REMARK 350 BIOMOLECULE: 3
	// REMARK 350 SOFTWARE DETERMINED QUATERNARY STRUCTURE: DODECAMERIC
	// REMARK 350 SOFTWARE USED: PISA
	// REMARK 350 AVERAGE BURIED SURFACE AREA: 2990 ANGSTROM**2
	// REMARK 350 APPLY THE FOLLOWING TO CHAINS: A, B, C, D, E, F, G, H, I,
	// REMARK 350 J, K, L
	// REMARK 350 BIOMT1 1 1.000000 0.000000 0.000000 0.00000
	// REMARK 350 BIOMT2 1 0.000000 1.000000 0.000000 0.00000
	// REMARK 350 BIOMT3 1 0.000000 0.000000 1.000000 0.00000
	// Example  No experimental information about assembly provided
	// REMARK 350 COORDINATES FOR A COMPLETE MULTIMER REPRESENTING
	// REMARK 350 OLIGOMERIZATION STATE(S) OF THE
	// REMARK 350 MOLECULE CAN BE GENERATED BY APPLYING BIOMT TRANSFORMATIONS
	// REMARK 350 GIVEN BELOW. BOTH NON-CRYSTALLOGRAPHIC AND
	// REMARK 350 CRYSTALLOGRAPHIC OPERATIONS ARE GIVEN.
	// REMARK 350
	// REMARK 350 BIOMOLECULE: 1
	// REMARK 350 SOFTWARE DETERMINED QUATERNARY STRUCTURE: MONOMERIC
	// REMARK 350 SOFTWARE USED: PISA
	// REMARK 350 APPLY THE FOLLOWING TO CHAINS: A
	// REMARK 350 BIOMT1 1 1.000000 0.000000 0.000000 0.00000
	// REMARK 350 BIOMT2 1 0.000000 1.000000 0.000000 0.00000
	// REMARK 350 BIOMT3 1 0.000000 0.000000 1.000000 0.00000
	// REMARK 350
	//
	// REMARK 475, Residues modeled with zero occupancy
	// REMARK 475 enumerates residues modeled with zero occupancy.
	// Example 
	// REMARK 475
	// REMARK 475 ZERO OCCUPANCY RESIDUES
	// REMARK 475 THE FOLLOWING RESIDUES WERE MODELED WITH ZERO OCCUPANCY.
	// REMARK 475 THE LOCATION AND PROPERTIES OF THESE RESIDUES MAY NOT
	// REMARK 475 BE RELIABLE. (M=MODEL NUMBER; RES=RESIDUE NAME;
	// REMARK 475 C=CHAIN IDENTIFIER; SSEQ=SEQUENCE NUMBER; I=INSERTION CODE)
	// REMARK 475 M RES C SSEQI
	// REMARK 475 DG D 4
	//
	// REMARK 480, Polymer atoms modeled with zero occupancy
	// REMARK 480 enumerates atoms in residues modeled with zero occupancy.
	// Example 
	// REMARK 480
	// REMARK 480 ZERO OCCUPANCY ATOM
	// REMARK 480 THE FOLLOWING RESIDUES HAVE ATOMS MODELED WITH ZERO
	// REMARK 480 OCCUPANCY. THE LOCATION AND PROPERTIES OF THESE ATOMS
	// REMARK 480 MAY NOT BE RELIABLE. (M=MODEL NUMBER; RES=RESIDUE NAME;
	// REMARK 480 C=CHAIN IDENTIFIER; SSEQ=SEQUENCE NUMBER; I=INSERTION CODE):
	// REMARK 480 M RES C SSEQI ATOMS
	// REMARK 480 DC D 3 C4' O4' C1' C3' O3'
	//
	// REMARK 500, Geometry and Stereochemistry
	// Remark 500 provides further details on the stereochemistry of the structure. This
	// remark is generated by PDB and may incorporate comments provided by the author.
	// This remark is currently divided into the subtopics:
	// CLOSE CONTACTS IN SAME ASYMMETRIC UNIT,
	// CLOSE CONTACTS,
	// COVALENT BOND LENGTHS,
	// COVALENT BOND ANGLES,
	// TORSION ANGLES,
	// NON-CIS & NON-TRANS,
	// PLANAR GROUPS,
	// MAIN CHAIN PLANARITY,
	// CHIRAL CENTERS.
	// Additional subtopics may be added as needed. The calculation of bond and angle
	// deviations for protein entries will be based on the updated Engh & Huber amino acid
	// target values1. For nucleic acids, the Parkinson et al., statistics will be used for these
	// calculations2. All bonds and angles that deviate more than 6 times from their
	// standard target values will be flagged as a deviation.
	// Template
	// 1 2 3 4 5 6 7
	// 1234567890123456789012345678901234567890123456789012345678901234567890
	// REMARK 500
	// REMARK 500 GEOMETRY AND STEREOCHEMISTRY
	// 1 Structure quality and target parameters. R. A. Engh and R. Huber. International Tables for Crystallography (2006). Vol. F, ch.
	// 18.3, pp. 382-392
	// 2 "New Parameters for the Refinement of Nucleic Acid Containing Structures." Gary Parkinson, Jaroslav Vojtechovsky, Lester
	// Clowney, Axel Brunger*, and Helen M. Berman. (1996) Acta Cryst. D 52, 57-64
	// REMARK 500 SUBTOPIC:
	// REMARK 500
	// REMARK 500 FREE TEXT GOES HERE.
	// Example  Close Contacts in the same asymmetric unit
	// REMARK 500
	// REMARK 500 GEOMETRY AND STEREOCHEMISTRY
	// REMARK 500 SUBTOPIC: CLOSE CONTACTS IN SAME ASYMMETRIC UNIT
	// REMARK 500
	// REMARK 500 THE FOLLOWING ATOMS ARE IN CLOSE CONTACT.
	// REMARK 500
	// REMARK 500 ATM1 RES C SSEQI ATM2 RES C SSEQI DISTANCE
	// REMARK 500 N PHE 1 8 - OD2 ASP 1 31 2.17
	// REMARK 500 OD2 ASP 1 31 - N PHE 1 8 2.17
	// REMARK 500
	// REMARK 500 THIS ENTRY HAS 104 CLOSE CONTACTS
	// REMARK 500
	// REMARK 500 REMARK: NULL
	// Example  Close Contacts
	// REMARK 500
	// REMARK 500 GEOMETRY AND STEREOCHEMISTRY
	// REMARK 500 SUBTOPIC: CLOSE CONTACTS
	// REMARK 500
	// REMARK 500 THE FOLLOWING ATOMS THAT ARE RELATED BY CRYSTALLOGRAPHIC
	// REMARK 500 SYMMETRY ARE IN CLOSE CONTACT. AN ATOM LOCATED WITHIN 0.15
	// REMARK 500 ANGSTROMS OF A SYMMETRY RELATED ATOM IS ASSUMED TO BE ON A
	// REMARK 500 SPECIAL POSITION AND IS, THEREFORE, LISTED IN REMARK 375
	// REMARK 500 INSTEAD OFREMARK 500. ATOMS WITH NON-BLANK ALTERNATE
	// REMARK 500 LOCATION INDICATORS ARE NOT INCLUDED IN THE CALCULATIONS.
	// REMARK 500
	// REMARK 500 DISTANCE CUTOFF:
	// REMARK 500 2.2 ANGSTROMS FOR CONTACTS NOT INVOLVING HYDROGEN ATOMS
	// REMARK 500 1.6 ANGSTROMS FOR CONTACTS INVOLVING HYDROGEN ATOMS
	// REMARK 500
	// REMARK 500 ATM1 RES C SSEQI ATM2 RES C SSEQI SSYMOP DISTANCE
	// REMARK 500
	// REMARK 500 OH TYR 1 90 O HOH 343 1566 2.09
	// REMARK 500 OE1 GLU 1 134 CB LYS 2 135 1556 2.18
	// REMARK 500
	// REMARK 500 THIS ENTRY HAS 64 SYMMETRY CONTACTS
	// REMARK 500
	// REMARK 500 REMARK: NULL
	// Example  Covalent bond lengths
	// REMARK 500
	// REMARK 500 GEOMETRY AND STEREOCHEMISTRY
	// REMARK 500 SUBTOPIC: COVALENT BOND LENGTHS
	// REMARK 500
	// REMARK 500 THE STEREOCHEMICAL PARAMETERS OF THE FOLLOWING RESIDUES
	// REMARK 500 HAVE VALUES WHICH DEVIATE FROM EXPECTED VALUES BY MORE
	// REMARK 500 THAN 6*RMSD (M=MODEL NUMBER; RES=RESIDUE NAME; C=CHAIN
	// REMARK 500 IDENTIFIER; SSEQ=SEQUENCE NUMBER; I=INSERTION CODE).
	// REMARK 500
	// REMARK 500 STANDARD TABLE:
	// REMARK 500 FORMAT: (10X,I3,1X,A3,1X,A1,I4,A1,1X,2(A4,A1,3X),12X,F5.3)
	// REMARK 500
	// REMARK 500 EXPECTED VALUES PROTEIN: ENGH AND HUBER, 1999
	// REMARK 500 EXPECTED VALUES NUCELIC ACID: CLOWNEY ET AL 1996
	// REMARK 500
	// REMARK 500 M RES CSSEQI ATM1 RES CSSEQI ATM2 DEVIATION
	// REMARK 500 ASP B 117 CB ASP B 117 CG -0.129
	// REMARK 500 CYS J 29 CB CYS J 29 SG -0.111
	// REMARK 500
	// REMARK 500 REMARK: NULL
	// Example  Covalent bond angles
	// REMARK 500
	// REMARK 500 GEOMETRY AND STEREOCHEMISTRY
	// REMARK 500 SUBTOPIC: COVALENT BOND ANGLES
	// REMARK 500
	// REMARK 500 THE STEREOCHEMICAL PARAMETERS OF THE FOLLOWING RESIDUES
	// REMARK 500 HAVE VALUES WHICH DEVIATE FROM EXPECTED VALUES BY MORE
	// REMARK 500 THAN 6*RMSD (M=MODEL NUMBER; RES=RESIDUE NAME; C=CHAIN
	// REMARK 500 IDENTIFIER; SSEQ=SEQUENCE NUMBER; I=INSERTION CODE).
	// REMARK 500
	// REMARK 500 STANDARD TABLE:
	// REMARK 500 FORMAT: (10X,I3,1X,A3,1X,A1,I4,A1,3(1X,A4,2X),12X,F5.1)
	// REMARK 500
	// REMARK 500 EXPECTED VALUES: ENGH AND HUBER, 1999
	// REMARK 500
	// REMARK 500 M RES CSSEQI ATM1 ATM2 ATM3
	// REMARK 500 VAL A 124 CB - CA - C ANGL. DEV. = -12.0 DEGREES
	// REMARK 500 ARG B 70 NE - CZ - NH1 ANGL. DEV. = -3.0 DEGREES
	// REMARK 500
	// REMARK 500 REMARK: NULL
	// Example  Torsion angles
	// REMARK 500
	// REMARK 500 GEOMETRY AND STEREOCHEMISTRY
	// REMARK 500 SUBTOPIC: TORSION ANGLES
	// REMARK 500
	// REMARK 500 TORSION ANGLES OUTSIDE THE EXPECTED RAMACHANDRAN REGIONS:
	// REMARK 500 (M=MODEL NUMBER; RES=RESIDUE NAME; C=CHAIN IDENTIFIER;
	// REMARK 500 SSEQ=SEQUENCE NUMBER; I=INSERTION CODE).
	// REMARK 500 STANDARD TABLE:
	// REMARK 500 FORMAT:(10X,I3,1X,A3,1X,A1,I4,A1,4X,F7.2,3X,F7.2)
	// REMARK 500
	// REMARK 500 M RES CSSEQI PSI PHI
	// REMARK 500 ASN A 100 -110.87 -163.72
	// REMARK 500 ILE A 166 -28.81 -31.64
	// REMARK 500
	// REMARK 500 REMARK: NULL
	// Example  Cis/Trans geometry
	// REMARK 500
	// REMARK 500 GEOMETRY AND STEREOCHEMISTRY
	// REMARK 500 SUBTOPIC: NON-CIS, NON-TRANS
	// REMARK 500
	// REMARK 500 THE FOLLOWING PEPTIDE BONDS DEVIATE SIGNIFICANTLY FROM BOTH
	// REMARK 500 CIS AND TRANS CONFORMATION. CIS BONDS, IF ANY, ARE LISTED
	// REMARK 500 ON CISPEP RECORDS. TRANS IS DEFINED AS 180 +/- 30 AND
	// REMARK 500 CIS IS DEFINED AS 0 +/- 30 DEGREES.
	// REMARK 500 MODEL OMEGA
	// REMARK 500 ARG A 413 ASP A 414 0 147.84
	// REMARK 500 ALA B 288 ASN B 289 0 -39.12
	// REMARK 500
	// REMARK 500 REMARK: NULL
	// Example  Planar groups
	// REMARK 500
	// REMARK 500 GEOMETRY AND STEREOCHEMISTRY
	// REMARK 500 SUBTOPIC: PLANAR GROUPS
	// REMARK 500
	// REMARK 500 PLANAR GROUPS IN THE FOLLOWING RESIDUES HAVE A TOTAL
	// REMARK 500 RMS DISTANCE OF ALL ATOMS FROM THE BEST-FIT PLANE
	// REMARK 500 BY MORE THAN AN EXPECTED VALUE OF 6*RMSD, WITH AN
	// REMARK 500 RMSD 0.02 ANGSTROMS, OR AT LEAST ONE ATOM HAS
	// REMARK 500 AN RMSD GREATER THAN THIS VALUE
	// REMARK 500 (M=MODEL NUMBER; RES=RESIDUE NAME; C=CHAIN IDENTIFIER;
	// REMARK 500 SSEQ=SEQUENCE NUMBER; I=INSERTION CODE).
	// REMARK 500
	// REMARK 500 M RES CSSEQI RMS TYPE
	// REMARK 500 TYR A 36 0.08 SIDE CHAIN
	// REMARK 500 TYR A 104 0.08 SIDE CHAIN
	// REMARK 500
	// REMARK 500 REMARK: NULL
	// Example  Main chain planarity
	// REMARK 500
	// REMARK 500 GEOMETRY AND STEREOCHEMISTRY
	// REMARK 500 SUBTOPIC: MAIN CHAIN PLANARITY
	// REMARK 500
	// REMARK 500 THE FOLLOWING RESIDUES HAVE A PSEUDO PLANARITY
	// REMARK 500 TORSION, C(I) - CA(I) - N(I+1) - O(I), GREATER
	// REMARK 500 10.0 DEGREES. (M=MODEL NUMBER; RES=RESIDUE NAME;
	// REMARK 500 C=CHAIN IDENTIFIER; SSEQ=SEQUENCE NUMBER;
	// REMARK 500 I=INSERTION CODE).
	// REMARK 500
	// REMARK 500 M RES CSSEQI ANGLE
	// REMARK 500
	// REMARK 500 REMARK: NULL
	// Example  Chiral centers
	// REMARK 500
	// REMARK 500 GEOMETRY AND STEREOCHEMISTRY
	// REMARK 500 SUBTOPIC: CHIRAL CENTERS
	// REMARK 500
	// REMARK 500 UNEXPECTED CONFIGURATION OF THE FOLLOWING CHIRAL
	// REMARK 500 CENTER(S) (M=MODEL NUMBER; RES=RESIDUE NAME; C=CHAIN
	// REMARK 500 IDENTIFIER; SSEQ=SEQUENCE NUMBER; I=INSERTION CODE).
	// REMARK 500
	// REMARK 500 STANDARD TABLE:
	// REMARK 500 FORMAT: (10X,I3,1X,A3,1X,A1,I4,A1,6X,A12)
	// REMARK 500
	// REMARK 500 M RES CSSEQI OMEGA
	// REMARK 500 ASP B 405 ALPHA-CARBON 150.48
	// REMARK 500
	// REMARK 500 REMARK: NULL
	//
	// REMARK 525  Distant Solvent Atoms
	// REMARK 525 lists solvent atoms more than 5 Angstroms from any polymer chain.
	// Example 
	// REMARK 525
	// REMARK 525 SOLVENT
	// REMARK 525
	// REMARK 525 THE SOLVENT MOLECULES HAVE CHAIN IDENTIFIERS THAT
	// REMARK 525 INDICATE THE POLYMER CHAIN WITH WHICH THEY ARE MOST
	// REMARK 525 CLOSELY ASSOCIATED. THE REMARK LISTS ALL THE SOLVENT
	// REMARK 525 MOLECULES WHICH ARE MORE THAN 5A AWAY FROM THE
	// REMARK 525 NEAREST POLYMER CHAIN (M=MODEL NUMBER;
	// REMARK 525 RES=RESIDUE NAME; C=CHAIN IDENTIFIER; SSEQ=SEQUENCE
	// REMARK 525 NUMBER; I=INSERTION CODE):
	// REMARK 525
	// REMARK 525 M RES CSSEQI
	// REMARK 525 HOH B 89 DISTANCE = 6.29 ANGSTROMS
	// REMARK 525 HOH B 94 DISTANCE = 5.58 ANGSTROMS
	//
	// REMARK 610  Non-polymer residues with missing atoms
	// REMARK 610 enumerates non-polymer residues with missing atoms.
	// Example -
	// REMARK 610
	// REMARK 610 MISSING HETEROATOM
	// REMARK 610 THE FOLLOWING RESIDUES HAVE MISSING ATOMS (M=MODEL NUMBER;
	// REMARK 610 RES=RESIDUE NAME; C=CHAIN IDENTIFIER; SSEQ=SEQUENCE NUMBER;
	// REMARK 610 I=INSERTION CODE):
	// REMARK 610 M RES C SSEQI
	// REMARK 610 PPI 438
	//
	// REMARK 615  Non-polymer residues containing atoms with zero occupancy
	// REMARK 615 enumerates non-polymer residues containing atoms modeled with
	// zero occupancy.
	// Example 
	// REMARK 615
	// REMARK 615 ZERO OCCUPANCY ATOM
	// REMARK 615 THE FOLLOWING RESIDUES HAVE ATOMS MODELED WITH ZERO
	// REMARK 615 OCCUPANCY. THE LOCATION AND PROPERTIES OF THESE ATOMS
	// REMARK 615 MAY NOT BE RELIABLE. (M=MODEL NUMBER; RES=RESIDUE NAME;
	// REMARK 615 C=CHAIN IDENTIFIER; SSEQ=SEQUENCE NUMBER; I=INSERTION CODE):
	// REMARK 615 M RES C SSEQI
	// REMARK 615 PPI 438
	//
	// REMARK 620  Metal Coordination
	// Details of metal coordination are provided in REMARK 620. Coordination angles for
	// any metal coordination and surrounding residues (if present) will be provided this
	// remark.
	// Template:
	// REMARK 620
	// REMARK 620 METAL COORDINATION
	// REMARK 620 formatted text
	// Example -
	// REMARK 620
	// REMARK 620 METAL COORDINATION
	// REMARK 620 (M=MODEL NUMBER; RES=RESIDUE NAME; C=CHAIN IDENTIFIER;
	// REMARK 620 SSEQ=SEQUENCE NUMBER; I=INSERTION CODE):
	// REMARK 620
	// REMARK 620 COORDINATION ANGLES FOR: M RES CSSEQI METAL
	// REMARK 620 F3S A 107 FE1
	// REMARK 620 N RES CSSEQI ATOM
	// REMARK 620 1 CYS A 39 SG
	// REMARK 620 2 F3S A 107 FE3 142.2
	// REMARK 620 3 F3S A 107 FE4 154.3 59.7
	// REMARK 620 4 F3S A 107 S1 120.2 53.8 55.7
	// REMARK 620 5 F3S A 107 S2 113.0 103.5 54.3 106.6
	// REMARK 620 6 F3S A 107 S3 103.8 53.0 101.7 103.2 109.2
	// REMARK 620 N 1 2 3 4 5
	// REMARK 620
	// REMARK 620 COORDINATION ANGLES FOR: M RES CSSEQI METAL
	// REMARK 620 F3S A 107 FE3
	// REMARK 620 N RES CSSEQI ATOM
	// REMARK 620 1 F3S A 107 FE1
	// REMARK 620 2 F3S A 107 FE4 59.0
	// REMARK 620 3 F3S A 107 S1 52.7 55.1
	// REMARK 620 4 F3S A 107 S3 52.9 101.0 102.1
	// REMARK 620 5 CYS A 45 SG 146.5 146.2 115.6 112.8
	// REMARK 620 6 F3S A 107 S4 103.5 54.5 106.3 109.6 110.0
	// REMARK 620 N 1 2 3 4 5
	// REMARK 620
	// REMARK 620 COORDINATION ANGLES FOR: M RES CSSEQI METAL
	// REMARK 620 F3S A 107 FE4
	// REMARK 620 N RES CSSEQI ATOM
	// REMARK 620 1 F3S A 107 FE1
	// REMARK 620 2 F3S A 107 FE3 61.3
	// REMARK 620 3 F3S A 107 S1 53.4 53.9
	// REMARK 620 4 F3S A 107 S2 54.4 105.0 104.5
	//	REMARK 620 5 CYS A 20 SG 142.7 140.2 109.0 114.5
	//	REMARK 620 6 F3S A 107 S4 105.1 54.1 104.8 111.7 111.6
	//	REMARK 620 N 1 2 3 4 5
	//	REMARK 620
	//	REMARK 620 COORDINATION ANGLES FOR: M RES CSSEQI METAL
	//	REMARK 620 F3S A 108 FE1
	//	REMARK 620 N RES CSSEQI ATOM
	//	REMARK 620 1 F3S A 108 S3
	//	REMARK 620 2 CYS A 16 SG 120.1
	//	REMARK 620 3 F3S A 108 FE3 51.4 145.9
	//	REMARK 620 4 F3S A 108 FE4 54.3 148.5 59.9
	//	REMARK 620 5 F3S A 108 S1 98.3 110.0 50.6 101.5
	//	REMARK 620 6 F3S A 108 S2 104.2 109.4 104.5 53.3 114.7
	//	REMARK 620 N 1 2 3 4 5
	//	REMARK 620

	private static final long serialVersionUID = 1L;
	// PDB File format numbering is for 80 columns with the first colunm being 1. END_XXX column is included in the field.
	public static final String REMARKNUMBER = "999"; //$NON-NLS-1$
	public static final String RECNAME = "REMARK"; //$NON-NLS-1$
//	public static final String RECNAME = NAME + REMARKNUMBER;
	public static final String STARBIOCHEM = "STARBIOCHEM"; //$NON-NLS-1$
	public static final String IS_WATER_ON = "IS_WATER_ON"; //$NON-NLS-1$
	public static final String HASHYDROGEN = "HASHYDROGEN"; //$NON-NLS-1$

	//8 - 10        Remark number   
	private static final int START_REMARKNUMBER = 8;
	private static final int FINISH_REMARKNUMBER = 10; 
   	
	//12 - 23       Star ID           EXCEPTIONID
	private static final int START_STARID = 11;
	private static final int FINISH_STARID = 22; 	

	protected boolean ismodremark = false;
	public boolean isModremark() { return this.ismodremark; }
	public void setModremark(boolean ismodremark){ this.ismodremark = ismodremark; }

	protected boolean iswateron = false;
	public boolean isWaterOn() { return this.iswateron; }
	public void setWaterOn(boolean iswateron){ this.iswateron = iswateron; }
	
	protected boolean hashydrogens = false;
	public boolean hasHydrogens() { return this.hashydrogens; }
	public void hasHydrogens(boolean hashydrogens){ this.hashydrogens = hashydrogens; }
	
	public static PDBRemark extractRemark(String pdbRemarkRecord)
	{
		if (pdbRemarkRecord.startsWith(RECNAME))
		{
			PDBRemark remark = new PDBRemark();
			String remarknumber = extractString(pdbRemarkRecord, START_REMARKNUMBER, FINISH_REMARKNUMBER);
			if(null != remarknumber)
			{
				if(remarknumber.equals(REMARKNUMBER))
				{
					String starbiochem = extractString(pdbRemarkRecord, START_STARID, FINISH_STARID);
					if(null != starbiochem)
					{
						boolean ismodremark = (starbiochem.equals(STARBIOCHEM));
						if(ismodremark)
						{
							remark.setModremark(ismodremark);
						}
					}
					String iswater = extractString(pdbRemarkRecord, START_STARID, FINISH_STARID);
					if(null != iswater)
					{
						boolean iswateron = (iswater.equals(IS_WATER_ON));
						if(iswateron)
						{
							remark.setWaterOn(iswateron);
						}
					}
					String hashydrogenstring = extractString(pdbRemarkRecord, START_STARID, FINISH_STARID);
					if(null != hashydrogenstring)
					{
						boolean hashydrogens = (hashydrogenstring.equals(HASHYDROGEN));
						if(hashydrogens)
						{
							remark.hasHydrogens(hashydrogens);
						}
					}
				}
			}
			return remark;
		}
		return null;
	}
}
