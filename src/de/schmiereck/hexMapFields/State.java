/*
 * Copyright (c) schmiereck.de, 2017
 */
package de.schmiereck.hexMapFields;

import java.util.List;
import java.util.Vector;

/**
 * <p>
 *	Zustände der Felder in einem Raumbereich.
 * </p>
 * Jede Seite eines MapFields hat einen aktuellen State.
 * Dieser wird durch eine Liste von States beschrieben.
 * 
 * Aus diesen States und den States der umgebenden Fields
 * wird der neue State des MapFields gebildet.
 * 
 * Der "Grundzustand" jedes MapFields weißt jeder Seite schon einmal eines State (Farbe) zu.
 * Die anderen möglichen Eingangszustände und Ausgangszustände kommen dann hinzu.
 * 
 * @author smk
 * @version <p>01.06.2017:	created, smk</p>
 */
public class State
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Inner-State.
	 * Für jede Seite die aktuellen Farb(-Kombinationen).
	 */
	private final State parentState;
	
	private final List<State> childStates = new Vector<>();
	
	private final int no;
	
	private final int energie;
	
	//**********************************************************************************************
	// Functions:
	
	/**
	 * Constructor.
	 *
	 */
	public State()
	{
		//==========================================================================================
		this.parentState = null;
		this.no = 0;
		this.energie = 0;
		//==========================================================================================
	}

	/**
	 * Constructor.
	 *
	 */
	public State(final State parentState)
	{
		//==========================================================================================
		this.parentState = parentState;
		
		this.no = this.parentState.childStates.size();
		
		this.parentState.childStates.add(this);
				
		this.energie = parentState.getEnergie() + 1;
		//==========================================================================================
	}

	/**
	 * @return 
	 *			the value of attribute {@link #parentState}.
	 */
	public State getParentState()
	{
		//==========================================================================================
		return this.parentState;
	}
	
	/**
	 * @return 
	 *			the value of attribute {@link #energie}.
	 */
	public int getEnergie()
	{
		//==========================================================================================
		return this.energie;
	}
	
	/**
	 * @return 
	 *			the value of attribute {@link #no}.
	 */
	public int getNo()
	{
		return this.no;
	}

	public String toString()
	{
		final StringBuffer retStrBuf = new StringBuffer();
		
		State state = this;
		
		while (state != null)
		{
			retStrBuf.append(state.no);

			state = state.getParentState();

			if (state != null)
				retStrBuf.append('.');
		}
		
		return retStrBuf.toString();
	}
}
