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
				
				final MetaEntry inStateNodeMetaEntry = this.findOrCreateInStateMetaEntry(mapField, inStateNode);

				final Field field = mapField.getField();

				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//				mapField.setMetaEntry(inStateNodeMetaEntry);
				
				//----------------------------------------------------------------------------------
			}
		}
		//==========================================================================================
		
	}

	private MetaEntry findOrCreateInStateMetaEntry(final MapField mapField, final StateNode inStateNode)
	{
		//==========================================================================================
		final MetaEntry inStateNodeMetaEntry;
		
		// Wenn alle StateNodes des StateNode des inStateNode vom gleichen MetaEntry sind,
		// wird er in diesem gesucht, gefunden oder erzeugt.

		{
			final MetaEntry metaEntry = inStateNode.getMetaEntry();
			
			// Hat der inStateNode keinen MetaEntry?
			if (metaEntry == null)
			{
				// Finde einen MetaLevel mit diesem Anfangszustand:
				final MetaEntry foundMetaEntry = this.findInStateMetaEntry(mapField, inStateNode);
				
				// Nicht gefunden?
				if (foundMetaEntry == null)
				{
					// Erzeuge einen neuen MetaEntry.
					inStateNodeMetaEntry = this.createInStateMetaEntry(mapField, inStateNode);
				}
				else
				{
					inStateNodeMetaEntry = foundMetaEntry;
				}
				inStateNode.setMetaEntry(inStateNodeMetaEntry);
			}
			else
			{
				inStateNodeMetaEntry = metaEntry;
			}
		}
		
		// Automatisches finden von MetaLeveln die zyklisch zusammen hängen.
		//	Zusammenfassen dieser MetaLevel zu einem MetaLevel.
		//		Zuordnen des Previous-MetaEntry zu dem neuen LevelEntry
		//	Die Ausgangszustände in der MetaDB bleiben bestehen mit ihren Parents bleibt bestehen.
		//	Der Parent jedes Zustandes ist also nicht der eigene MetaLevel, sondern ein abstrakter.
		//		Dieser wird wiederum als Parent gesetzt, wenn ein neuer mit anderen Warscheinlichkeiten auftaucht?
		
		// In Zukunft wird kein neues Field mehr erzeugt, wenn soch ein Zyklus durchlaufen wird.
		
		//==========================================================================================
		return inStateNodeMetaEntry;
	}

	private MetaEntry createInStateMetaEntry(final MapField mapField, final StateNode inStateNode)
	{
		//==========================================================================================
		final MetaEntry inStateNodeMetaEntry;
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		final MetaLevel abMetaLevel;
		{
			final StateNode abInStateNode = MapFieldUtils.extractABInStateNode(mapField);
			abMetaLevel = this.extractMetaLevel(abInStateNode);
		}		
		final MetaLevel bcMetaLevel;
		{
			final StateNode bcInStateNode = MapFieldUtils.extractBCInStateNode(mapField);
			bcMetaLevel = this.extractMetaLevel(bcInStateNode);
		}
		final MetaLevel caMetaLevel;
		{
			final StateNode caInStateNode = MapFieldUtils.extractCAInStateNode(mapField);
			caMetaLevel = this.extractMetaLevel(caInStateNode);
		}
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		final MetaLevel metaLevel;
		
		// Alle Meta-Level gleich?
		if ((abMetaLevel == bcMetaLevel) && (bcMetaLevel == caMetaLevel))
		{
			// Alle nicht gesetzt?
			if (abMetaLevel == null)
			{
				// Erzeuge einen neuen MetaLevel mit diesem als Parent-MetaLevel.
				metaLevel = new MetaLevel();
			}
			else
			{
				metaLevel = abMetaLevel;
			}
		}
		else
		{
			// AB und BC Meta-Level gleich?
			if (abMetaLevel == bcMetaLevel)
			{
				// Erzeuge einen neuen MetaLevel mit diesem als Parent-MetaLevel.
				metaLevel = new MetaLevel();
			}
			else
			{
				// BC und CA Meta-Level gleich?
				if (bcMetaLevel == caMetaLevel)
				{
					// Erzeuge einen neuen MetaLevel mit diesem als Parent-MetaLevel.
					metaLevel = new MetaLevel();
				}
				else
				{
					// CA und AB Meta-Level gleich?
					if (caMetaLevel == abMetaLevel)
					{
						// Erzeuge einen neuen MetaLevel mit diesem als Parent-MetaLevel.
						metaLevel = new MetaLevel();
					}
					else
					{
						// Alle verschieden.
						// Erzeuge einen neuen MetaLevel mit diesem als Parent-MetaLevel.
						metaLevel = new MetaLevel();
					}
				}
			}
		}
		
		inStateNodeMetaEntry = new MetaEntry(metaLevel);

		//==========================================================================================
		return inStateNodeMetaEntry;
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
}
