/*
 * Copyright (c) schmiereck.de, 2017
 */
package de.schmiereck.hexMapFields.metaDB;

import de.schmiereck.hexMapFields.Map;
import de.schmiereck.hexMapFields.MapField;
import de.schmiereck.hexMapFields.MapFieldUtils;
import de.schmiereck.hexMapFields.State;
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
	
	public void calcMetaEntries(final Map map, final StateNodes stateNodes)
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

				final StateNode newInStateNode = mapField.getInStateNode();
				
				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				// Den (neuen) MapEntry berechnen:
				
				// Den MetaEntry des (neuen) In-StateNode holen.
				final MetaEntry inStateNodeMetaEntry;
				{
					// Wenn dieser noch keinen hate einen neuen MetaEntry mit neuem MetaLevel anlegen und dem StateNode zuweisen.
					final MetaEntry localMetaEntry = newInStateNode.getMetaEntry();
	 				
					// Hat der inStateNode keinen MetaEntry?
					if (localMetaEntry == null)
					{
						// Erzeuge einen neuen MetaEntry:

						// Meta-Level bauen:
						final MetaLevel metaLevel = new MetaLevel();
						
						// Meta-Entry bauen:
						inStateNodeMetaEntry = new MetaEntry(metaLevel, newInStateNode);
			
						newInStateNode.setMetaEntry(inStateNodeMetaEntry);
					}
					else
					{
						inStateNodeMetaEntry = localMetaEntry;
					}
				}
				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				// Den (alten) MapEntry holen.
				final MetaEntry metaEntry = mapField.getMetaEntry();
				
				// War schon einer gesetzt?
				if (metaEntry != null)
				{
					// Dann bei diesen den Nachfolger eintragen:
					final MetaEntry nextMetaEntry = metaEntry.getNextMetaEntry();
					
					if (nextMetaEntry == null)
					{
						metaEntry.setNextMetaEntry(inStateNodeMetaEntry);
					}
					else
					{
						if (nextMetaEntry != inStateNodeMetaEntry)
						{
//							throw new RuntimeException("Another NextMetaEntry already exist.");
						}
					}
					
					final MetaEntry previousMetaEntry = inStateNodeMetaEntry.getPreviousMetaEntry();
					
					if (previousMetaEntry == null)
					{
						inStateNodeMetaEntry.setPreviousMetaEntry(metaEntry);
					}
					else
					{
						if (previousMetaEntry != metaEntry)
						{
//							throw new RuntimeException("Another PreviousMetaEntry already exist.");
						}
					}
				}
				
				final Field field = mapField.getField();

				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				// Den (neuen) MapEntry setzen.
				mapField.setMetaEntry(inStateNodeMetaEntry);
				
				//----------------------------------------------------------------------------------
			}
		}
		//==========================================================================================
	}

	/**
	 * @param inStateNode
	 * 			is the given In-State-Node.
	 * @return
	 * 			the Meta-Level of given In-State-Node or<br/>
	 * 			<code>null</code> if no Meta-Entry assigned.
	 */
	public MetaLevel extractMetaLevel(final StateNode inStateNode)
	{
		//==========================================================================================
		final MetaLevel metaLevel;

		final MetaEntry metaEntry = inStateNode.getMetaEntry();
		
		if (metaEntry != null)
		{
			metaLevel = metaEntry.getMetaLevel();
		}
		else
		{
			metaLevel = null;
		}
		//==========================================================================================
		return metaLevel;
	}

	private MetaEntry findInStateMetaEntry(final MapField mapField, final StateNode inStateNode)
	{
		//==========================================================================================
		final MetaEntry inStateNodeMetaEntry;
		
		// Erst mal sinnlos. Wenn der inStateNode keinen hat, gibt es auch keinen.
		inStateNodeMetaEntry = null;
		
		//==========================================================================================
		return inStateNodeMetaEntry;
	}
	
	public void calcMetaLevels(final Map map, final StateNodes stateNodes)
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
				
				// Den MetaEntry des (neuen) In-StateNode holen.
				final MetaEntry inStateNodeMetaEntry = inStateNode.getMetaEntry();

				final Field field = mapField.getField();

				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				// Prüfen ob es Kombinationen mit den jeweiligen Nachbarfeldern gibt
				// ->	die noch nicht als Nachbarn de MetaEntry eingetragen sind und
				//		>	im gleiche RuleSet sind?
				//		>	im gleiche Field sind?
				//		>	ein EmptyField sind?

				for (int mapFieldPos = 0; mapFieldPos < MapField.NEIGHBOURS_CNT; mapFieldPos++)
				{
					final MapField outField = mapField.getOutField(mapFieldPos);
					
					// Benachbarter In-State-Node:
					final StateNode outFieldInStateNode = outField.getInStateNode();
					
					final MetaEntry outFieldMetaEntry = outFieldInStateNode.getMetaEntry();
					
					final MetaEntry neighbourMetaEntry = inStateNodeMetaEntry.getNeighbourMetaEntry(mapFieldPos);
					
					if (outFieldMetaEntry != neighbourMetaEntry)
					{
						if (neighbourMetaEntry != null)
						{
							final StateNode neighbourInStateNode = neighbourMetaEntry.getInStateNode();
							
							if (outFieldInStateNode.getRuleSet() == neighbourInStateNode.getRuleSet())
							{
								// Dann eintragen und die MetaLevel zusammenfassen.
	
								// Eigentlich auch Quatsch, wozu soll das nutzen?
								// Wichtig ist die Beziehung zu den nachfolgenden MapEntries, nicht zu den benachbarten.
								// Damit will ich die Zyklen erkennen.
								// Kann natürlich auch von Interesse sein, wenn eben im gleichen Field, oder so...
							}
						}
					}
				}
				//----------------------------------------------------------------------------------
			}
		}
		//==========================================================================================
	}
	
	public void calcNextMetaEntries(final Map map, final StateNodes stateNodes)
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

				// Neuen Zustand des MapField als Nachfolger eintragen.
				// Prüfen ob Nachbarfelder 
				//		>	im gleiche RuleSet sind?
				//		>	im gleiche Field sind?
				// Diese dann als Nachfolger eintragen.
				
				//----------------------------------------------------------------------------------
			}
		}
		//==========================================================================================
	}
	
	// Wenn alle StateNodes des StateNode des inStateNode vom gleichen MetaEntry sind,
	// wird er in diesem gesucht, gefunden oder erzeugt.
	
	// Automatisches finden von MetaLeveln die zyklisch zusammen hängen.
	//	Zusammenfassen dieser MetaLevel zu einem MetaLevel.
	//		Zuordnen des Previous-MetaEntry zu dem neuen LevelEntry
	//	Die Ausgangszustände in der MetaDB bleiben bestehen mit ihren Parents bleibt bestehen.
	//	Der Parent jedes Zustandes ist also nicht der eigene MetaLevel, sondern ein abstrakter.
	//		Dieser wird wiederum als Parent gesetzt, wenn ein neuer mit anderen Warscheinlichkeiten auftaucht?
	
	// In Zukunft wird kein neues Field mehr erzeugt, wenn soch ein Zyklus durchlaufen wird.
	
}
