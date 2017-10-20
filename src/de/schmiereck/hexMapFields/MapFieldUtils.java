/*
 * Copyright (c) schmiereck.de, 2017
 */
package de.schmiereck.hexMapFields;

import java.util.List;
import java.util.Vector;

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
	// Constants:
	
	private final static List<StateNode> EMPTY_InStateNodes = new Vector<>();
	static 
	{
		EMPTY_InStateNodes.add(Main.s0MapFieldStateNode);
	}
	
	//**********************************************************************************************
	// Functions:
	

	public static State extractABInnerState(final PropInnerStateNode probInnerStateNode)
	{
		//==========================================================================================
		final State abState;
		final StateNode caStateNode = probInnerStateNode.getInnerStateNode();
		if (caStateNode != null)
		{
			final StateNode bcStateNode = caStateNode.getParentNode();
			final StateNode abStateNode = bcStateNode.getParentNode();
			abState = abStateNode.getState();
		}
		else
		{
			abState = null;
		}
		//==========================================================================================
		return abState;
	}
	
//	/**
//	 * @param mapField
//	 * 			ist the Map-Field.
//	 * @return
//	 * 			the AB-State of the Neighbour AB-Field of given Map-Field.
//	 */
//	public static State extractABStateNodeState(final MapField mapField)
//	{
//		//==========================================================================================
//		final State abInState;
//		final StateNode abInStateNode = MapFieldUtils.extractABStateNode(mapField);
//		if (abInStateNode != null)
//		{
//			abInState = abInStateNode.getState();
//		}
//		else
//		{
//			abInState = null;
//		}
//		//==========================================================================================
//		return abInState;
//	}

//	/**
//	 * 			ist the Map-Field.
//	 * @param mapField
//	 * @return
//	 * 			the In-StateNode of the Neighbour AB-Field of given Map-Field.
//	 */
//	public static StateNode extractABInStateNode(final MapField mapField)
//	{
//		//==========================================================================================
//		final MapField abOutField = mapField.getABOutField();
//		final StateNode abInStateNode = abOutField.getInStateNode();
//
//		//==========================================================================================
//		return abInStateNode;
//	}

	public static State extractBCInnerState(final PropInnerStateNode probInnerStateNode)
	{
		//==========================================================================================
		final State bcState;
		final StateNode caStateNode = probInnerStateNode.getInnerStateNode();
		if (caStateNode != null)
		{
			final StateNode bcStateNode = caStateNode.getParentNode();
			bcState = bcStateNode.getState();
		}
		else
		{
			bcState = null;
		}
		//==========================================================================================
		return bcState;
	}

//	/**
//	 * @param mapField
//	 * 			ist the Map-Field.
//	 * @return
//	 * 			the CB-State of the Neighbour CB-Field of given Map-Field.
//	 */
//	public static State extractBCStateNodeState(final MapField mapField)
//	{
//		//==========================================================================================
//		final State bcInState;
//		final StateNode bcInStateNode = MapFieldUtils.extractBCStateNode(mapField);
//		if (bcInStateNode != null)
//		{
//			bcInState = bcInStateNode.getState();
//		}
//		else
//		{
//			bcInState = null;
//		}
//		//==========================================================================================
//		return bcInState;
//	}

//	/**
//	 * @param mapField
//	 * 			ist the Map-Field.
//	 * @return
//	 * 			the In-StateNode of the Neighbour CB-Field of given Map-Field.
//	 */
//	public static List<StateNode> extractBCInStateNode(final MapField mapField)
//	{
//		//==========================================================================================
//		List<StateNode> bcInStateNodes = null;
//		
//		final MapField bcOutField = mapField.getBCOutField();
//
//		final List<PropNextStateNode> nextStateNodes = bcOutField.getNextStateNodes();
//		
//		for (final PropNextStateNode nextStateNode : nextStateNodes)
//		{
//			final StateNode stateNode = nextStateNode.getStateNode();
//
//			if (stateNode != null)
//			{
//				if (bcInStateNodes == null)
//				{
//					bcInStateNodes = new Vector<>();
//				}
//				bcInStateNodes.add(stateNode.getParentNode());
//			}
//
//		//==========================================================================================
//		return bcInStateNodes;
//	}

	public static State extractCAInnerState(final PropInnerStateNode probInnerStateNode)
	{
		//==========================================================================================
		final State caState;
		final StateNode caStateNode = probInnerStateNode.getInnerStateNode();
		if (caStateNode != null)
		{
			caState = caStateNode.getState();
		}
		else
		{
			caState = null;
		}
		//==========================================================================================
		return caState;
	}

//	/**
//	 * @param mapField
//	 * 			ist the Map-Field.
//	 * @return
//	 * 			the CA-State of the Neighbour CA-Field of given Map-Field.
//	 */
//	public static State extractCAStateNodeState(final MapField mapField)
//	{
//		//==========================================================================================
//		final State caInState;
//		final StateNode caInStateNode = MapFieldUtils.extractCAStateNode(mapField);
//		if (caInStateNode != null)
//		{
//			caInState = caInStateNode.getState();
//		}
//		else
//		{
//			caInState = null;
//		}
//		//==========================================================================================
//		return caInState;
//	}
//	
//	/**
//	 * @param mapField
//	 * 			ist the Map-Field.
//	 * @return
//	 * 			the CA-StateNode of the Neighbour CA-Field of given Map-Field.
//	 */
//	public static StateNode extractCAInStateNode(final MapField mapField)
//	{
//		//==========================================================================================
//		final MapField caOutField = mapField.getCAOutField();
//		final StateNode caInStateNode = caOutField.getInStateNode();
//
//		//==========================================================================================
//		return caInStateNode;
//	}
}
