/*
 * Copyright (c) schmiereck.de, 2017
 */
package de.schmiereck.hexMapFields.fields;

/**
 * <p>
 *	Field.<br/>
 *	Konkrete Entwicklung eines Feldes von einem Ausgangszustand 
 *	über die jeweiligen {@link MetaLevel}.<br/>
 *	Zustand des Feldes zu einem Zeitpunkt.
 * </p>
 * 	Wenn ein Field aufgelöst wirde, wird einfach sein Zustand auf den vorherigen
 * 	Zurück gesetzt (oder als Tot deklariert) und fertig.
 * 	Somit sind alle verknüpften MapField wieder zurückgesetzt.
 * 
 * 	Jedes MapField hält einen Verweis auf das Field und seinen aktuellen MetaEntry.
 * 
 * @author thoma
 * @version <p>31.08.2017:	created, thoma</p>
 */
public class Field
{
	/**
	 * MapField des Ausgangszustandes.<br/>
	 * Wichtig zur Festlegung der Position des MetaLevel.
	 * 
	 * Wie mit zyklischen Bewegungen verfahren?
	 * Brauch es den Vereis überhaupt?
	 * Vielleicht wegen zyklischer Vorwärtsbewegung?
	 */

	/**
	 * Flag das anzeigt, ob das Feld noch "lebt", 
	 * oder sein Superposition "tot" ist (dann können alle Verweise auf das Feld gelöscht werden).
	 */
}
