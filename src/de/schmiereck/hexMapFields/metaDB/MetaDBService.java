/*
 * Copyright (c) schmiereck.de, 2017
 */
package de.schmiereck.hexMapFields.metaDB;

import de.schmiereck.hexMapFields.Map;
import de.schmiereck.hexMapFields.MapField;
import de.schmiereck.hexMapFields.StateNode;
import de.schmiereck.hexMapFields.StateNodes;
import de.schmiereck.hexMapFields.fields.Field;

/**
 * <p>
 *	Meta-DB Service.
 * </p>
 * Hash mit Liste der Ausgangszustände von {@link MetaEntry}.<br/>
 * Es müssen immer die angrenzenden Zustände mit betrachtet werden, 
 * da die Folgezustände ermittelt werden sollen.
 * 
 * @author thoma
 * @version <p>23.08.2017:	created, thoma</p>
 */
public class MetaDBService
{
	//**********************************************************************************************
	// Functions:
	
	public void calcMetaStates(final Map map, final StateNodes stateNodes)
	{
		//==========================================================================================
		final int xSize = map.getXSize();
		final int ySize = map.getYSize();
		
		for (int yPos = 0; yPos < ySize; yPos++)
		{
			for (int xPos = 0; xPos < xSize; xPos++)
			{
				//----------------------------------------------------------------------------------
				final MapField mapField = map.getMapField(xPos, yPos);

//				final StateNode stateNode = mapField.getStateNode();
				final StateNode inStateNode = mapField.getInStateNode();
				
				final MetaEntry inStateNodeMetaEntry;
				{
					final MetaEntry metaEntry = inStateNode.getMetaEntry();
					
					if (metaEntry != null)
					{
						inStateNodeMetaEntry = metaEntry;
					}
					else
					{
						inStateNodeMetaEntry = this.createMetaEntry(inStateNode);
					}
				}

				final Field field = mapField.getField();

				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				
				//----------------------------------------------------------------------------------
			}
		}
		//==========================================================================================
		
	}

	private MetaEntry createMetaEntry(final StateNode inStateNode)
	{
		//==========================================================================================
		final MetaEntry inStateNodeMetaEntry;
		
		// Wenn alle StateNodes des StateNode des inStateNode vom gleichen MetaEntry sind,
		// wird er in diesem gesucht, gefunden oder erzeugt.
		
		// Finde einen MetaLevel mit diesem Anfangszustand.
		// 	Wenn nicht gefunden erzeuge einen neuen MetaLevel mit diesem als Parent-MetaLevel
		
		// Automatisches finden von MetaLeveln die zyklisch zusammen hängen.
		//	Zusammenfassen dieser MetaLevel zu einem MetaLevel.
		//		Zuordnen des Previous-MetaEntry zu dem neuen LevelEntry
		//	Die Ausgangszustände in der MetaDB bleiben bestehen mit ihren Parents bleibt bestehen.
		//	Der Parent jedes Zustandes ist also nicht der eigene MetaLevel, sondern ein abstrakter.
		//		Dieser wird wiederum als Parent gesetzt, wenn ein neuer mit anderen Warscheinlichkeiten auftaucht?
		
		// In Zukunft wird kein neues Field mehr erzeugt, wenn soch ein Zyklus durchlaufen wird.
		
		final MetaLevel metaLevel = new MetaLevel();
		
		inStateNodeMetaEntry = new MetaEntry(metaLevel);
		
		inStateNode.setMetaEntry(inStateNodeMetaEntry);

		//==========================================================================================
		return inStateNodeMetaEntry;
	}
}
