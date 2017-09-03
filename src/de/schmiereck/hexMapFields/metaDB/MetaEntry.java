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
	 */
	private MetaEntry[] neighbourMetaEntries = new MetaEntry[MapField.NEIGHBOURS_CNT];
	
	/**
	 * In- and Inner-State-Node of Field.
	 */
	private StateNode inStateNode;
	
	/**
	 * Next-MetaEntry of Field.
	 */
	private MetaEntry nextMetaEntry;
	
	/**
	 * Previous-MetaEntry of Field.
	 */
	private MetaEntry previousMetaEntry;
	
	private final MetaLevel metaLevel;

	//**********************************************************************************************
	// Fields:
	
	public MetaEntry(final MetaLevel metaLevel)
	{
		this.metaLevel = metaLevel;
	}
	
	/**
	 * @return 
	 *			the value of attribute {@link #metaLevel}.
	 */
	public MetaLevel getMetaLevel()
	{
		return this.metaLevel;
	}
}
