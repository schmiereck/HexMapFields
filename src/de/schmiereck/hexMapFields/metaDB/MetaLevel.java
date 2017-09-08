/*
 * Copyright (c) schmiereck.de, 2017
 */
package de.schmiereck.hexMapFields.metaDB;

/**
 * <p>
 *	Meta-Level.
 * </p>
 * <p>
 *	Verwaltet die zusammenhängende Entwicklung eines Ausgangs-Zustandes.<br/>
 *	Ziele:
 *	=>	Alle Verweise aus der Map löschen können, wenn eine Spuerdisposition aufgelößt wird.
 *	=>	Alles ist Zyklisch, also sollte sich jeder Zustand durch eine hierarchisch Verkettung
 *		von Zuständen in der MetaDB abbilden lassen, ohne alzuviel Speicher zu belegn.
 *	=>	Ziel muss es sein, dass wenn ein Feld refernziert wird, 
 *		dieses Anhand von seiner relativen Position zum Ausgangszustand 
 *		seinen Zustand ermitteln kann.
 * </p>
 * 	=>	Zyklische Vorwärtsbewegung
 * 		->	Der Endzustand verweist auf den Anfangszustand.
 * 
 * @author thoma
 * @version <p>23.08.2017:	created, thoma</p>
 */
public class MetaLevel
{
	
//	/**
//	 * Eine Map aus {@link MetaEntry} des aktuellen Zustandes beliebiger Ausdehnung 
//	 * mit Verweis auf die jeweiligen Nachbarfelder.<br/>
//	 */
//	private MetaEntry startMetaEntry = null;
//	
//	/**
//	 * @return 
//	 *			the value of attribute {@link #startMetaEntry}.
//	 */
//	public MetaEntry getStartMetaEntry()
//	{
//		return this.startMetaEntry;
//	}
//	
//	/**
//	 * @param startMetaEntry 
//	 * 			used to set the value of attribute {@link #startMetaEntry}.
//	 */
//	public void setStartMetaEntry(MetaEntry startMetaEntry)
//	{
//		this.startMetaEntry = startMetaEntry;
//	}
}
