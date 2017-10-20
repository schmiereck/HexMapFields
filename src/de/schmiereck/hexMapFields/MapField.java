/*
 * Copyright (c) schmiereck.de, 2017
 */
package de.schmiereck.hexMapFields;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

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
	
	public static final PropNextStateNode EMPTY_NextStateNode = new PropNextStateNode(Main.s0InStateNode, 
	                                                                                  PropNextStateNode.MIN_probability,//PropNextStateNode.MAX_probability, 
	                                                                                  "EMPTY_NextStateNode");
	
//	public static final List<PropNextStateNode> EMPTY_NextStateNodes = new Vector<>();
//	static
//	{
//		EMPTY_NextStateNodes.add(EMPTY_NextStateNode);
//	}
	
	public static final PropInnerStateNode EMPTY_InnerStateNode = new PropInnerStateNode(Main.s0MapFieldStateNode, 
	                                                                                     PropNextStateNode.MIN_probability);//PropNextStateNode.MAX_probability);
	
	public static final List<PropInnerStateNode> EMPTY_InnerStateNodes = new Vector<>();
	static
	{
		EMPTY_InnerStateNodes.add(EMPTY_InnerStateNode);
	}
	
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
	 * Inner-StateNode is the Key.
	 * Der Inner-State für jede Seite:
	 * 0: AB (R)
	 * 1: BC (G)
	 * 2: CA (B)
	 */
	private HashMap<StateNode, PropInnerStateNode> probInnerStateNodes = new HashMap<>();
//	private List<PropInnerStateNode> probInnerStateNodes = new Vector<>();
	
	private List<PropInStateNode> probInStateNodes = new Vector<>();

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
		this.resetPropInnerStateNodes();
		
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
	 *			the value of attribute {@link #probInnerStateNodes}.
	 */
	public Collection<PropInnerStateNode> getPropInnerStateNodes()
	{
		return this.probInnerStateNodes.values();
//		return this.probInnerStateNodes;
	}

	/**
	 * @return 
	 *			the value of attribute {@link #probInnerStateNodes}.
	 */
	public Collection<PropInnerStateNode> getNotEmptyPropInnerStateNodes()
	{
		//==========================================================================================
		final Collection<PropInnerStateNode> retPropInnerStateNodes;
		
		if (this.probInnerStateNodes.size() > 0)
		{
			retPropInnerStateNodes = this.probInnerStateNodes.values();
//			retPropInnerStateNodes = this.probInnerStateNodes;
		}
		else
		{
			retPropInnerStateNodes = EMPTY_InnerStateNodes;
		}
		//==========================================================================================
		return retPropInnerStateNodes;
	}
	
	/**
	 * @return 
	 *			the value of attribute {@link #probInStateNodes}.
	 */
	public List<PropInStateNode> getPropInStateNodes()
	{
		return this.probInStateNodes;
	}
	
	/**
	 * @param probInStateNode 
	 * 			used to add the value of attribute {@link #probInStateNodes}.
	 */
	public void addPropInStateNode(final PropInStateNode probInStateNode)
	{
		this.probInStateNodes.add(probInStateNode);
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

	/**
	 * @param probInStateNodes 
	 * 			used to set the value of attribute {@link #probInStateNodes}.
	 */
	public void setInStateNodes(List<PropInStateNode> probInStateNodes)
	{
		this.probInStateNodes = probInStateNodes;
	}
	
	/**
	 * @param probInnerStateNode 
	 * 			used to add the value of attribute {@link #probInnerStateNodes}.
	 */
	public void addPropInnerStateNode(final PropInnerStateNode probInnerStateNode)
	{
		this.probInnerStateNodes.put(probInnerStateNode.getInnerStateNode(), 
		                             probInnerStateNode);
//		this.probInnerStateNodes.add(probInnerStateNode);
	}

	public void addPropInnerStateNode(final StateNode nextInStateNode, 
	                                  final long probabilityR, final long probabilityG, final long probabilityB)
	{
		//==========================================================================================
		// Gleiche States eines Map-Field zusammenfassen.
		final PropInnerStateNode probInnerStateNode = this.probInnerStateNodes.get(nextInStateNode);
		
		if (probInnerStateNode == null)
		{
			final PropInnerStateNode newPropInnerStateNode;
			
			newPropInnerStateNode =
					new PropInnerStateNode(nextInStateNode, 
					                       probabilityR, probabilityG, probabilityB);
			
			this.probInnerStateNodes.put(nextInStateNode,
			                             newPropInnerStateNode);
		}
		else
		{
			// Das mit der höchsten Wahrscheinlichkeit gewinnt.
//			probInnerStateNode.setProbability(Math.max(probInnerStateNode.getProbability(), probability));
			probInnerStateNode.setProbability(probInnerStateNode.getProbability(0) + probabilityR,
											  probInnerStateNode.getProbability(1) + probabilityG,
											  probInnerStateNode.getProbability(2) + probabilityB);
//			probInnerStateNode.setProbability((probInnerStateNode.getProbability() * probability) / PropNextStateNode.MAX_probability);
//			probInnerStateNode.setProbability((probInnerStateNode.getProbability() + probability) / 2.0D);
//			probInnerStateNode.setProbability(Math.min(probInnerStateNode.getProbability() + probability, PropNextStateNode.MAX_probability));
		}
		
//		final PropInnerStateNode newPropInnerStateNode;
//		
//		newPropInnerStateNode =
//				new PropInnerStateNode(nextInStateNode, 
//				                       probabilityR, probabilityG, probabilityB);
//		
//		this.probInnerStateNodes.add(newPropInnerStateNode);

		//==========================================================================================
		
	}

	/**
	 * Clear {@link #getPropInStateNodes()} and add an {@link #EMPTY_NextStateNode}.
	 */
	public void resetPropInStateNodes()
	{
		//==========================================================================================
		this.probInStateNodes.clear();
//		this.probInStateNodes.add(EMPTY_NextStateNode);
		
		//==========================================================================================
		
	}

	/**
	 * Clear {@link #probInnerStateNodes} and add an {@link #EMPTY_InnerStateNode}.
	 */
	public void resetPropInnerStateNodes()
	{
		//==========================================================================================
		this.probInnerStateNodes.clear();
		this.probInnerStateNodes.put(EMPTY_InnerStateNode.getInnerStateNode(),
		                             EMPTY_InnerStateNode);
//		this.probInnerStateNodes.add(EMPTY_InnerStateNode);
		
		//==========================================================================================
		
	}

}
