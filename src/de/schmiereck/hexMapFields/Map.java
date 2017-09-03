/*
 * Copyright (c) schmiereck.de, 2017
 */
package de.schmiereck.hexMapFields;

import java.util.List;
import java.util.Vector;

/**
 * <p>
 *	Map of {@link MapField}.
 * </p>
 * 
 * @author smk
 * @version <p>08.05.2017:	created, smk</p>
 */
public class Map
{
	//**********************************************************************************************
	// Constants:
	
	public final int fieldEdges = 3;

	/**
	 * <code><pre>
	 *        A-----B
	 *       / \ D / \
	 *      /RD \ / LD\
	 *     B-----C-----A 
	 *      \RT / \ LT/
	 *       \ / T \ /
	 *        A-----B
	 * </pre></code>
	 */
	public enum Orientation 
	{
		/** 0: Top 0 */
		T0,
		/** 1: Left-Top 0 */
		LT0,
		/** 2:	Right-Down 0 */
		RD0,
		/** 3:	Down 0 */
		D0,
		/** 4:	Left-Down 0 */
		LD0,
		/** 5:	Right-Top 0 */
		RT0,
		/** 6:	Down 1 */
		D1,
		/** 7:	Left-Down */
		LD1,
		/** 8:	Right-Top */
		RT1,
		/** 9:	Top 1 */
		T1,
		/** 10:	Left-Top 1 */
		LT1,
		/** 11:	Right-Down 1 */
		RD1
	}
	
	//      t
	//     /|\
	//    / | \
	//  s/  |h \s
	//  /   |   \
	// l---------r
	//      s s2
	
	public static final double sTri = 1.0D;
	public static final double s2Tri = sTri / 2.0D;
	public static final double hTri = Math.sqrt((sTri * sTri) - (s2Tri * s2Tri));

	public static class Tri
	{
		public final double xa;
		public final double ya;
		public final double xb;
		public final double yb;
		public final double xc;
		public final double yc;

		public final double xab;
		public final double yab;
		
		public final double xbc;
		public final double ybc;
		
		public final double xca;
		public final double yca;
		
		public Tri(final double xa, final double ya,
		           final double xb, final double yb,
		           final double xc, final double yc,
		           final double xab, final double yab,
		           final double xbc, final double ybc,
		           final double xca, final double yca)
		{
			this.xa = xa;
			this.ya = ya;
			this.xb = xb;
			this.yb = yb;
			this.xc = xc;
			this.yc = yc;

			this.xab = xab;
			this.yab = yab;
			this.xbc = xbc;
			this.ybc = ybc;
			this.xca = xca;
			this.yca = yca;
		}
	}

	// l is (0,0) origin.
	public static final double xlTri = 0;
	public static final double ylTri = 0;
	public static final double xrTri = sTri;
	public static final double yrTri = 0;
	public static final double xtTri = s2Tri;
	public static final double ytTri = hTri;
	
	//----------------------------------------------------------------------------------------------
	// x = (x1 + x2 + x3) / 3
	// y = (y1 + y2 + y3) / 3
	// (x,y) Mittelpunkt Innenkreis
	public static final double xmTri = (xlTri + xrTri + xtTri) / 3.0D;
	public static final double ymTri = (ylTri + yrTri + ytTri) / 3.0D;
	// From Middle to Top (t).
	public static final double ymtTri = (hTri - ymTri);

	//----------------------------------------------------------------------------------------------
	// Middle is (0,0) origin.
	public static final double xlmTri = xlTri - s2Tri;
	public static final double ylmTri = ylTri - ymTri;
	public static final double xrmTri = xrTri - s2Tri;
	public static final double yrmTri = yrTri - ymTri;
	public static final double xtmTri = xtTri - s2Tri;
	public static final double ytmTri = ytTri - ymTri;
	
	//----------------------------------------------------------------------------------------------
	// Out-Points:
	// Origin is middle Point:
	public static final double xlrTri = 0.0D;
	public static final double ylrTri = -ymTri;
	
	public static final double xrtTri = (s2Tri / 2.0D);
	public static final double yrtTri = (hTri / 2.0D) - ymTri;
	
	public static final double xtlTri = -(s2Tri / 2.0D);
	public static final double ytlTri = (hTri / 2.0D) - ymTri;
	
	//----------------------------------------------------------------------------------------------
	//        c (t)
	//       /|\
	//      / | \
	// (l) a-----b (r)
	// Middle is (0,0) origin.
	public static final Tri[] oTri = new Tri[12];
	static
	{
		Map.oTri[0] = new Tri	// Top
		(
		 	xlmTri, ylmTri,	// LD
		 	xrmTri, yrmTri,	// RD
		 	xtmTri, ytmTri,	// T
		 	xlrTri, ylrTri,	// D
		 	xrtTri, yrtTri,	// RT
		 	xtlTri, ytlTri	// LT
		);
		Map.oTri[1] = new Tri	// Left-Top: : a=l b=r c=t
		(
		 	xrmTri, -yrmTri,
		 	xtmTri, -ytmTri,
		 	xlmTri, -ylmTri,
		 	xrtTri, -yrtTri,	// RD
		 	xtlTri, -ytlTri,		// LD
		 	xlrTri, -ylrTri	// T
		);
		Map.oTri[2] = new Tri	// Right-Down
		(
		 	xtmTri, ytmTri,	// D
		 	xlmTri, ylmTri,
		 	xrmTri, yrmTri,
		 	xtlTri, ytlTri,	// LT
		 	xlrTri, ylrTri,	// D
		 	xrtTri, yrtTri	// RT
		);
		Map.oTri[3] = new Tri	// Down
		(
		 	xlmTri, -ylmTri,
		 	xrmTri, -yrmTri,
		 	xtmTri, -ytmTri,
		 	xlrTri, -ylrTri,	// T
		 	xrtTri, -yrtTri,		// RD
		 	xtlTri, -ytlTri	// LD
		);
		// Left-Down
		Map.oTri[4] = new Tri
		(
			xrmTri, yrmTri,
			xtmTri, ytmTri,
		 	xlmTri, ylmTri,
		 	xrtTri, yrtTri,	// RT
		 	xtlTri, ytlTri,	// LT
		 	xlrTri, ylrTri	// D
		);
		// Top-Right
		Map.oTri[5] = new Tri
		(
		 	xtmTri, -ytmTri,
		 	xlmTri, -ylmTri,
		 	xrmTri, -yrmTri,
		 	xtlTri, -ytlTri,	// LD
		 	xlrTri, -ylrTri,	// T
		 	xrtTri, -yrtTri		// RD
		);
		//------------------------------------------------------------------------------------------
		// Down
		Map.oTri[6] = new Tri
		(
		 	xlmTri, -ylmTri,	// DL
		 	xrmTri, -yrmTri,	// DR
		 	xtmTri, -ytmTri,	// T
		 	xlrTri, -ylrTri,	// T
		 	xrtTri, -yrtTri,		// RD
		 	xtlTri, -ytlTri	// LD
		);
		// Left-Down
		Map.oTri[7] = new Tri
		(
			xrmTri, yrmTri,
			xtmTri, ytmTri,
		 	xlmTri, ylmTri,
		 	xrtTri, yrtTri,		// RT
		 	xtlTri, ytlTri,		// LT
		 	xlrTri, ylrTri		// D
		);
		// Right-Top
		Map.oTri[8] = new Tri
		(
		 	xtmTri, -ytmTri,
		 	xlmTri, -ylmTri,
		 	xrmTri, -yrmTri,
		 	xtlTri, -ytlTri,	// LD
		 	xlrTri, -ylrTri,		// T
		 	xrtTri, -yrtTri	// RD
		);
		// Top
		Map.oTri[9] = new Tri
		(
		 	xlmTri, ylmTri,
			xrmTri, yrmTri,
			xtmTri, ytmTri,
		 	xlrTri, ylrTri,	// D
		 	xrtTri, yrtTri,	// RT
		 	xtlTri, ytlTri	// LT
		);
		// Left-Top
		Map.oTri[10] = new Tri
		(
		 	xrmTri, -yrmTri,
		 	xtmTri, -ytmTri,
		 	xlmTri, -ylmTri,
		 	xrtTri, -yrtTri,	// RT
		 	xtlTri, -ytlTri,		// LD
		 	xlrTri, -ylrTri	// T
		);
		// Right-Down
		Map.oTri[11] = new Tri
		(
		 	xtmTri, ytmTri,
		 	xlmTri, ylmTri,
		 	xrmTri, yrmTri,
		 	xtlTri, ytlTri,
		 	xlrTri, ylrTri,	// D
		 	xrtTri, yrtTri
		);
	}
	
	//**********************************************************************************************
	// Fields:
	
	private final int xSize;
	private final int ySize;
	
	private final List<MapField> mapFields;
	
	private int energie = 0;
	private int nextEnergie = 0;

	//**********************************************************************************************
	// Functions:
	
	/**
	 * Constructor.
	 */
	public Map(final int xSize, final int ySize)
	{
		//==========================================================================================
		this.xSize = xSize;
		this.ySize = ySize;
		
		this.mapFields = new Vector<>(xSize * ySize);

		for (int yPos = 0; yPos < this.ySize; yPos++)
		{
			for (int xPos = 0; xPos < this.xSize; xPos++)
			{
				final int mapPos = yPos * this.xSize + xPos;

				final MapField mapField = new MapField();
				
				this.mapFields.add(mapPos, mapField);
			}
		}
		//----------------------------------------------------------------------------------------------
		for (int yPos = 0; yPos < ySize; yPos++)
		{
			for (int xPos = 0; xPos < xSize; xPos++)
			{
				final MapField mapField = this.getMapField(xPos, yPos);
				
				final Orientation orientation;
				final double x;
				final double y;
				final int xm = xPos % 6;
				final int ym = yPos % 2;
					
				x = (xPos * xmTri) + xmTri;
				
				switch (xm)
				{
					case 0:
					{
						switch (ym)
						{
							case 0:
							{
								orientation = Orientation.T0;
								y = (yPos * hTri) + ymTri;
								break;
							}
							case 1:
							{
								orientation = Orientation.D1;
								y = (yPos * hTri) + ymtTri;
								break;
							}
							default:
							{
								throw new RuntimeException("Unexpected orientation xPos \"" + xPos + "\", yPos \"" + yPos + "\".");
							}
						}
						break;
					}
					case 1:
					{
						if (ym == 0)
						{
							// Up:
							orientation = Orientation.LT0;
							y = (yPos * hTri) + ymtTri;
						}
						else
						{
							orientation = Orientation.LD1;
							y = (yPos * hTri) + ymTri;
						}
						break;
					}
					case 2:
					{
						if (ym == 0)
						{
							orientation = Orientation.RD0;
							y = (yPos * hTri) + ymTri;
						}
						else
						{
							orientation = Orientation.RT1;
							y = (yPos * hTri) + ymtTri;
						}
						break;
					}
					case 3:
					{
						if (ym == 0)
						{
							orientation = Orientation.D0;
							y = (yPos * hTri) + ymtTri;
						}
						else
						{
							orientation = Orientation.T1;
							y = (yPos * hTri) + ymTri;
						}
						break;
					}
					case 4:
					{
						if (ym == 0)
						{
							orientation = Orientation.LD0;
							y = (yPos * hTri) + ymTri;
						}
						else
						{
							orientation = Orientation.LT1;
							y = (yPos * hTri) + ymtTri;
						}
						break;
					}
					case 5:
					{
						if (ym == 0)
						{
							orientation = Orientation.RT0;
							y = (yPos * hTri) + ymtTri;
						}
						else
						{
							orientation = Orientation.RD1;
							y = (yPos * hTri) + ymTri;
						}
						break;
					}
					default:
					{
						throw new RuntimeException("Unexpected orientation xPos \"" + xPos + "\", yPos \"" + yPos + "\".");
					}
				}
				
				mapField.setOrientation(orientation);
				mapField.setXPos(x);
				mapField.setYPos(y);
				
				this.addOutFields(mapField, xPos, yPos);
			}
		}
		//==========================================================================================
	}
	
	/**
	 * Copy Constructor.
	 *
	 */
	public Map(final Map cloneMap)
	{
		//==========================================================================================
		this.xSize = cloneMap.xSize;
		this.ySize = cloneMap.ySize;
		
		this.energie = cloneMap.energie;
		this.nextEnergie = cloneMap.nextEnergie;

		//------------------------------------------------------------------------------------------
		this.mapFields = new Vector<>(this.xSize * this.ySize);

		for (int yPos = 0; yPos < this.ySize; yPos++)
		{
			for (int xPos = 0; xPos < this.xSize; xPos++)
			{
				final int mapPos = yPos * this.xSize + xPos;
				
				final MapField cloneMapField = cloneMap.mapFields.get(mapPos);
				
				final MapField mapField = new MapField(cloneMapField);
				
				this.mapFields.add(mapPos, mapField);
			}
		}
		//==========================================================================================
	}

	private void addOutFields(final MapField mapField, final int xPos, final int yPos)
	{
		//==========================================================================================
		final Orientation orientation = mapField.getOrientation();
		
		//        c (t)
		//       /|\
		//      / | \
		// (l) a-----b (r)
		switch (orientation)
		{
			case T0:		// 0: Top 0
			{
				mapField.setABOutField(this.getMapField(xPos,     yPos - 1));
				mapField.setBCOutField(this.getMapField(xPos + 1, yPos));
				mapField.setCAOutField(this.getMapField(xPos - 1, yPos));
				break;
			}
			case LT0:		// 1: Left-Top 0
			{
//				mapField.setABOutField(this.getMapField(xPos + 1, yPos));
//				mapField.setBCOutField(this.getMapField(xPos,     yPos + 1));
//				mapField.setCAOutField(this.getMapField(xPos - 1, yPos));
				mapField.setABOutField(this.getMapField(xPos + 1, yPos));
				mapField.setBCOutField(this.getMapField(xPos - 1, yPos));
				mapField.setCAOutField(this.getMapField(xPos,     yPos + 1));
				break;
			}
			case RD0:		// 2: Right-Down 0
			{
				mapField.setABOutField(this.getMapField(xPos - 1, yPos));
				mapField.setBCOutField(this.getMapField(xPos,     yPos - 1));
				mapField.setCAOutField(this.getMapField(xPos + 1, yPos));
				break;
			}
			case D0:		// 3: Down 0
			{
				mapField.setABOutField(this.getMapField(xPos,     yPos + 1));
				mapField.setBCOutField(this.getMapField(xPos + 1, yPos));
				mapField.setCAOutField(this.getMapField(xPos - 1, yPos));
				break;
			}
			case LD0:		// 4: Left-Down 0
			{
				mapField.setABOutField(this.getMapField(xPos + 1, yPos));
				mapField.setBCOutField(this.getMapField(xPos - 1, yPos));
				mapField.setCAOutField(this.getMapField(xPos,     yPos - 1));
				break;
			}
			case RT0:		// 5: Right-Top 0
			{
				mapField.setABOutField(this.getMapField(xPos - 1, yPos));
				mapField.setBCOutField(this.getMapField(xPos,     yPos + 1));
				mapField.setCAOutField(this.getMapField(xPos + 1, yPos));
				break;
			}
			case D1:		// 6: Down 1
			{
				mapField.setABOutField(this.getMapField(xPos,     yPos + 1));
				mapField.setBCOutField(this.getMapField(xPos + 1, yPos));
				mapField.setCAOutField(this.getMapField(xPos - 1, yPos));
				break;
			}
			case LD1:		// 7: Left-Down 1
			{
				mapField.setABOutField(this.getMapField(xPos + 1, yPos));
				mapField.setBCOutField(this.getMapField(xPos - 1, yPos));
				mapField.setCAOutField(this.getMapField(xPos,     yPos - 1));
				break;
			}
			case RT1:		// 8: Right-Top 1
			{
				mapField.setABOutField(this.getMapField(xPos - 1, yPos));
				mapField.setBCOutField(this.getMapField(xPos,     yPos + 1));
				mapField.setCAOutField(this.getMapField(xPos + 1, yPos));
				break;
			}
			case T1:		// 9: Top 1
			{
				mapField.setABOutField(this.getMapField(xPos,     yPos - 1));
				mapField.setBCOutField(this.getMapField(xPos + 1, yPos));
				mapField.setCAOutField(this.getMapField(xPos - 1, yPos));
				break;
			}
			case LT1:	// 10: Left-Top 1
			{
				mapField.setABOutField(this.getMapField(xPos + 1, yPos));
				mapField.setBCOutField(this.getMapField(xPos - 1, yPos));
				mapField.setCAOutField(this.getMapField(xPos,     yPos + 1));
				break;
			}
			case RD1:	// 11: Right-Down 1
			{
				mapField.setABOutField(this.getMapField(xPos - 1, yPos));
				mapField.setBCOutField(this.getMapField(xPos,     yPos - 1));
				mapField.setCAOutField(this.getMapField(xPos + 1, yPos));
				break;
			}
			default:
			{
				throw new RuntimeException("Unexpected orientation \"" + orientation + "\".");
			}
		}
		//==========================================================================================
	}

	/**
	 * @return 
	 *			the value of attribute {@link #xSize}.
	 */
	public int getXSize()
	{
		return this.xSize;
	}
	
	/**
	 * @return 
	 *			the value of attribute {@link #ySize}.
	 */
	public int getYSize()
	{
		return this.ySize;
	}
	
	/**
	 * @return 
	 *			the value of attribute {@link #fieldEdges}.
	 */
	public int getFieldEdges()
	{
		return this.fieldEdges;
	}

	/**
	 * @param xPos
	 * 			is the x-Pos.
	 * @param yPos
	 * 			is the x-Pos.
	 * @return
	 * 			the Map-Field.
	 */
	public MapField getMapField(final int xPos, final int yPos)
	{
		//==========================================================================================
		final int x;
		final int y;
		
		x = makeNormSize(xPos, this.xSize);
		y = makeNormSize(yPos, this.ySize);
		
		final MapField mapField = this.mapFields.get((y * this.xSize) + x);

		//==========================================================================================
		return mapField;
	}

	private int makeNormSize(final int pos, final int size)
	{
		//==========================================================================================
		final int p;

		if (pos >= 0)
		{
			if (pos < size)
			{
				p = pos;
			}
			else
			{
				p = pos % size;
			}
		}
		else
		{
			p = (size + (pos % size));
		}
		//==========================================================================================
		return p;
	}
	
	/**
	 * @return 
	 *			the value of attribute {@link #mapFields}.
	 */
	public List<MapField> getMapFields()
	{
		return this.mapFields;
	}
	
	
	/**
	 * @return 
	 *			the value of attribute {@link #energie}.
	 */
	public int getEnergie()
	{
		return this.energie;
	}
	
	/**
	 * @param energie 
	 * 			used to set the value of attribute {@link #energie}.
	 */
	public void setEnergie(int energie)
	{
		this.energie = energie;
	}
	
	/**
	 * @return 
	 *			the value of attribute {@link #nextEnergie}.
	 */
	public int getNextEnergie()
	{
		return this.nextEnergie;
	}

	/**
	 * @param nextEnergie 
	 * 			used to set the value of attribute {@link #nextEnergie}.
	 */
	public void setNextEnergie(int nextEnergie)
	{
		this.nextEnergie = nextEnergie;
	}
}
