/*
 * Copyright (c) schmiereck.de, 2017
 */
package de.schmiereck.hexMapFields.metaDB;

import de.schmiereck.hexMapFields.MapField;
import de.schmiereck.hexMapFields.State;
import de.schmiereck.hexMapFields.StateNode;

/**
 * <p>
 *	Zustand eines Feldes in der {@link MetaDB}.<br/>
 *	{@link MetaEntry} sind daraus zusammen gesetzt.
 * </p>
 * 
 * @author thoma
 * @version <p>30.08.2017:	created, thoma</p>
 */
public class MetaField
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Zustand eines {@link MapField} in einem {@link MetaEntry}.
	 * 0: AB (R)
	 * 1: BC (G)
	 * 2: CA (B)
	 */
	private State[] states = new State[MapField.NEIGHBOURS_CNT];
	
	/**
	 * Inner-State-Node of Field.
	 */
	private StateNode innerStateNode;
	
	/**
	 * Verweis auf den {@link MetaEntry}.
	 */
	private MetaEntry metaEntry;
}
