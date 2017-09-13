/*
 * Copyright (c) schmiereck.de, 2017
 */
package de.schmiereck.hexMapFields.metaDB;

import de.schmiereck.hexMapFields.MapField;
import de.schmiereck.hexMapFields.StateNode;

/**
 * <p>
 *	Meta-Entry in der {@link MetaDB}.<br/>
 * 	Ausgangszustand ist immer ein Entry mit den angrenzenden Zuständen.<br/>
 * 	Ab da läuft eine Verkettung mit Folgezuständen.
 * </p>
 * 
 * 	MetaEntry in den StateNodes verknüpfen und diese darüber finden?
 * 
 * @author thoma
 * @version <p>24.08.2017:	created, thoma</p>
 */
public class MetaEntry
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Neighbour Meta-Entries.
	 * 0: AB
	 * 1: BC
	 * 2: CA
	 */
	private final MetaEntry[] neighbourMetaEntries = new MetaEntry[MapField.NEIGHBOURS_CNT];
	
	/**
	 * In- and Inner-State-Node of Field.
	 */
	private final StateNode inStateNode;
	
	/**
	 * Next-MetaEntry of Field.
	 */
	private MetaEntry nextMetaEntry = null;
	
	/**
	 * Previous-MetaEntry of Field.
	 */
	private MetaEntry previousMetaEntry;
	
	private final MetaLevel metaLevel;
	
	private int levelNo = 0;

	//**********************************************************************************************
	// Fields:
	
	public MetaEntry(final MetaLevel metaLevel, final StateNode inStateNode)
	{
		this.metaLevel = metaLevel;
		this.inStateNode = inStateNode;
	}
	
	/**
	 * @return 
	 *			the value of attribute {@link #metaLevel}.
	 */
	public MetaLevel getMetaLevel()
	{
		return this.metaLevel;
	}
	
	/**
	 * @param pos
	 * 			ist the given pos.
	 * @return 
	 *			the value of attribute {@link #neighbourMetaEntries} at given pos.
	 */
	public MetaEntry getNeighbourMetaEntry(final int pos)
	{
		return this.neighbourMetaEntries[pos];
	}
	
	/**
	 * @return 
	 *			the value of attribute {@link #previousMetaEntry}.
	 */
	public MetaEntry getPreviousMetaEntry()
	{
		return this.previousMetaEntry;
	}

	/**
	 * @param previousMetaEntry 
	 * 			used to set the value of attribute {@link #previousMetaEntry}.
	 */
	public void setPreviousMetaEntry(MetaEntry previousMetaEntry)
	{
		this.levelNo = previousMetaEntry.getLevelNo() + 1;
		this.previousMetaEntry = previousMetaEntry;
	}

	/**
	 * @return 
	 *			the value of attribute {@link #levelNo}.
	 */
	public int getLevelNo()
	{
		//==========================================================================================
		return this.levelNo;
	}
	
	/**
	 * @return 
	 *			the value of attribute {@link #inStateNode}.
	 */
	public StateNode getInStateNode()
	{
		//==========================================================================================
		return this.inStateNode;
	}
	
	/**
	 * @return 
	 *			the value of attribute {@link #nextMetaEntry}.
	 */
	public MetaEntry getNextMetaEntry()
	{
		//==========================================================================================
		return this.nextMetaEntry;
	}

	/**
	 * @param nextMetaEntry 
	 * 			used to set the value of attribute {@link #nextMetaEntry}.
	 */
	public void setNextMetaEntry(MetaEntry nextMetaEntry)
	{
		//==========================================================================================
		this.nextMetaEntry = nextMetaEntry;
		//==========================================================================================
	}
}
