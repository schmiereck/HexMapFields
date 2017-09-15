/*
 * Copyright (c) schmiereck.de, 2017
 */
package de.schmiereck.hexMapFields;

/**
 * <p>
 *	{@link MapField} Utils.
 * </p>
 * 
 * @author thoma
 * @version <p>05.09.2017:	created, thoma</p>
 */
public class MapFieldUtils
{
	//**********************************************************************************************
	// Functions:
	
	/**
	 * @param mapField
	 * 			ist the Map-Field.
	 * @return
	 * 			the AB-StateNode of the Neighbour AB-Field of given Map-Field.
	 */
	public static StateNode extractABStateNode(final MapField mapField)
	{
		//==========================================================================================
		final StateNode abInStateNode;
		
		final MapField abOutField = mapField.getABOutField();
		final StateNode stateNode = abOutField.getStateNode();
		if (stateNode != null)
		{
			abInStateNode = abOutField.getStateNode().getParentNode().getParentNode();
		}
		else
		{
			abInStateNode = null;
		}
		//==========================================================================================
		return abInStateNode;
	}
	
	/**
	 * @param mapField
	 * 			ist the Map-Field.
	 * @return
	 * 			the AB-State of the Neighbour AB-Field of given Map-Field.
	 */
	public static State extractABStateNodeState(final MapField mapField)
	{
		//==========================================================================================
		final State abInState;
		final StateNode abInStateNode = MapFieldUtils.extractABStateNode(mapField);
		if (abInStateNode != null)
		{
			abInState = abInStateNode.getState();
		}
		else
		{
			abInState = null;
		}
		//==========================================================================================
		return abInState;
	}

	/**
	 * 			ist the Map-Field.
	 * @param mapField
	 * @return
	 * 			the In-StateNode of the Neighbour AB-Field of given Map-Field.
	 */
	public static StateNode extractABInStateNode(final MapField mapField)
	{
		//==========================================================================================
		final MapField abOutField = mapField.getABOutField();
		final StateNode abInStateNode = abOutField.getInStateNode();

		//==========================================================================================
		return abInStateNode;
	}

	/**
	 * @param mapField
	 * 			ist the Map-Field.
	 * @return
	 * 			the CB-StateNode of the Neighbour CB-Field of given Map-Field.
	 */
	public static StateNode extractBCStateNode(final MapField mapField)
	{
		//==========================================================================================
		final StateNode bcInStateNode;
		
		final MapField bcOutField = mapField.getBCOutField();
		final StateNode stateNode = bcOutField.getStateNode();
		if (stateNode != null)
		{
			bcInStateNode = stateNode.getParentNode();
		}
		else
		{
			bcInStateNode = null;
		}
		//==========================================================================================
		return bcInStateNode;
	}

	/**
	 * @param mapField
	 * 			ist the Map-Field.
	 * @return
	 * 			the CB-State of the Neighbour CB-Field of given Map-Field.
	 */
	public static State extractBCStateNodeState(final MapField mapField)
	{
		//==========================================================================================
		final State bcInState;
		final StateNode bcInStateNode = MapFieldUtils.extractBCStateNode(mapField);
		if (bcInStateNode != null)
		{
			bcInState = bcInStateNode.getState();
		}
		else
		{
			bcInState = null;
		}
		//==========================================================================================
		return bcInState;
	}

	/**
	 * @param mapField
	 * 			ist the Map-Field.
	 * @return
	 * 			the In-StateNode of the Neighbour CB-Field of given Map-Field.
	 */
	public static StateNode extractBCInStateNode(final MapField mapField)
	{
		//==========================================================================================
		final MapField bcOutField = mapField.getBCOutField();
		final StateNode bcInStateNode = bcOutField.getInStateNode();

		//==========================================================================================
		return bcInStateNode;
	}

	/**
	 * @param mapField
	 * 			ist the Map-Field.
	 * @return
	 * 			the CA-StateNode of the Neighbour CA-Field of given Map-Field.
	 */
	public static StateNode extractCAStateNode(final MapField mapField)
	{
		//==========================================================================================
		final MapField caOutField = mapField.getCAOutField();
		final StateNode caInStateNode = caOutField.getStateNode();

		//==========================================================================================
		return caInStateNode;
	}

	/**
	 * @param mapField
	 * 			ist the Map-Field.
	 * @return
	 * 			the CA-State of the Neighbour CA-Field of given Map-Field.
	 */
	public static State extractCAStateNodeState(final MapField mapField)
	{
		//==========================================================================================
		final State caInState;
		final StateNode caInStateNode = MapFieldUtils.extractCAStateNode(mapField);
		if (caInStateNode != null)
		{
			caInState = caInStateNode.getState();
		}
		else
		{
			caInState = null;
		}
		//==========================================================================================
		return caInState;
	}
	
	/**
	 * @param mapField
	 * 			ist the Map-Field.
	 * @return
	 * 			the CA-StateNode of the Neighbour CA-Field of given Map-Field.
	 */
	public static StateNode extractCAInStateNode(final MapField mapField)
	{
		//==========================================================================================
		final MapField caOutField = mapField.getCAOutField();
		final StateNode caInStateNode = caOutField.getInStateNode();

		//==========================================================================================
		return caInStateNode;
	}
}
