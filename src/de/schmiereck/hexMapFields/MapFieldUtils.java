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
		final MapField abOutField = mapField.getABOutField();
		final StateNode abInStateNode = abOutField.getStateNode().getParentNode().getParentNode();

		//==========================================================================================
		return abInStateNode;
	}

	/**
	 * @param mapField
	 * 			ist the Map-Field.
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
		final MapField bcOutField = mapField.getBCOutField();
		final StateNode bcInStateNode = bcOutField.getStateNode().getParentNode();

		//==========================================================================================
		return bcInStateNode;
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
