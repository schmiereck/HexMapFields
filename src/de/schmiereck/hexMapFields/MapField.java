/*
 * Copyright (c) schmiereck.de, 2017
 */
package de.schmiereck.hexMapFields;

import de.schmiereck.hexMapFields.Map.Orientation;
import de.schmiereck.hexMapFields.fields.Field;

/**
 * <p>
 *	Map Field.
 * </p>
 * 
 * @author smk
 * @version <p>08.05.2017:	created, smk</p>
 */
public class MapField
{
	//**********************************************************************************************
	// Constants:
	
	/**
	 * Anzahl Nachbarfelder.
	 */
	public static final int NEIGHBOURS_CNT = 3;
	
	//**********************************************************************************************
	// Fields:
	
	private double xPos = 0.0D;
	private double yPos = 0.0D;
	
	private Orientation orientation = Orientation.T0;

	/**
	 * 0: AB
	 * 1: BC
	 * 2: CA
	 */
	private final MapField[] outFields = new MapField[NEIGHBOURS_CNT];

	/**
	 * Der Inner-State für jede Seite:
	 * 0: AB (R)
	 * 1: BC (G)
	 * 2: CA (B)
	 */
	private StateNode stateNode = null;

	/**
	 * Der Inner- and In-State für jede Seite with the resulting Next-State:
	 * 0: AB (R)
	 * 1: BC (G)
	 * 2: CA (B)
	 * 3: inAB (inR)
	 * 4: inBC (inG)
	 * 5: inCA (inB)
	 */
	private StateNode inStateNode = null;
	
	/**
	 * Field.
	 */
	private Field field = null;
	
	//**********************************************************************************************
	// Functions:
	
	/**
	 * Constructor.
	 */
	public MapField()
	{
		//==========================================================================================
		//==========================================================================================
	}
	
	/**
	 * Copy Constructor.
	 */
	public MapField(final MapField cloneMapField)
	{
		//==========================================================================================
		this.xPos = cloneMapField.xPos;
		this.yPos = cloneMapField.yPos;
		
		this.orientation = cloneMapField.orientation;
		for (int pos = 0; pos < cloneMapField.outFields.length; pos++)
		{
			this.outFields[pos] = cloneMapField.outFields[pos];
		}
		this.stateNode = cloneMapField.stateNode;
		this.inStateNode = cloneMapField.inStateNode;
		
		//==========================================================================================
	}
	
	/**
	 * @return 
	 *			the value of attribute {@link #orientation}.
	 */
	public Orientation getOrientation()
	{
		return this.orientation;
	}
	
	/**
	 * @param orientation 
	 * 			used to set the value of attribute {@link #orientation}.
	 */
	public void setOrientation(Orientation orientation)
	{
		this.orientation = orientation;
	}
	
	/**
	 * @return 
	 *			the value of attribute {@link #xPos}.
	 */
	public double getXPos()
	{
		return this.xPos;
	}
	
	/**
	 * @param xPos 
	 * 			used to set the value of attribute {@link #xPos}.
	 */
	public void setXPos(double xPos)
	{
		this.xPos = xPos;
	}
	
	/**
	 * @return 
	 *			the value of attribute {@link #yPos}.
	 */
	public double getYPos()
	{
		return this.yPos;
	}

	/**
	 * @param yPos 
	 * 			used to set the value of attribute {@link #yPos}.
	 */
	public void setYPos(double yPos)
	{
		this.yPos = yPos;
	}
	
//	/**
//	 * @return 
//	 *			the value of attribute {@link #outFields}.
//	 */
//	public MapField[] getOutFields()
//	{
//		return this.outFields;
//	}
	
	/**
	 * @param pos
	 * 			is the pos.
	 * @return 
	 *			the value of attribute {@link #outFields} on given pos.
	 */
	public MapField getOutField(final int pos)
	{
		return this.outFields[pos];
	}
	
	/**
	 * @param pos
	 * 			is the pos.
	 * @param abOutField 
	 * 			used to set the value of attribute {@link #outFields} on given pos.
	 */
	public void setOutField(final int pos, final MapField abOutField)
	{
		this.outFields[pos] = abOutField;
	}
	
	/**
	 * @return 
	 *			the value of attribute {@link #outFields[0]}.
	 */
	public MapField getABOutField()
	{
		return this.outFields[0];
	}
	
	/**
	 * @param abOutField 
	 * 			used to set the value of attribute {@link #outFields[0]}.
	 */
	public void setABOutField(MapField abOutField)
	{
		this.outFields[0] = abOutField;
	}
	
	/**
	 * @return 
	 *			the value of attribute {@link #outFields[1]}.
	 */
	public MapField getBCOutField()
	{
		return this.outFields[1];
	}
	
	/**
	 * @param bcOutField 
	 * 			used to set the value of attribute {@link #outFields[1]}.
	 */
	public void setBCOutField(MapField bcOutField)
	{
		this.outFields[1] = bcOutField;
	}
	
	/**
	 * @return 
	 *			the value of attribute {@link #outFields[2]}.
	 */
	public MapField getCAOutField()
	{
		return this.outFields[2];
	}
	
	/**
	 * @param caOutField 
	 * 			used to set the value of attribute {@link #outFields[2]}.
	 */
	public void setCAOutField(MapField caOutField)
	{
		this.outFields[2] = caOutField;
	}

	/**
	 * @return 
	 *			the value of attribute {@link #stateNode}.
	 */
	public StateNode getStateNode()
	{
		return this.stateNode;
	}
	
	/**
	 * @param stateNode 
	 * 			used to set the value of attribute {@link #stateNode}.
	 */
	public void setStateNode(final StateNode stateNode)
	{
		this.stateNode = stateNode;
	}
	
	/**
	 * @return 
	 *			the value of attribute {@link #inStateNode}.
	 */
	public StateNode getInStateNode()
	{
		return this.inStateNode;
	}
	
	/**
	 * @param inStateNode
	 * 			used to set the value of attribute {@link #inStateNode}.
	 */
	public void setInStateNode(StateNode inStateNode)
	{
		this.inStateNode = inStateNode;
	}
	
	/**
	 * @return 
	 *			the value of attribute {@link #field}.
	 */
	public Field getField()
	{
		return this.field;
	}
	
	/**
	 * @param field 
	 * 			used to set the value of attribute {@link #field}.
	 */
	public void setField(Field field)
	{
		this.field = field;
	}

}
