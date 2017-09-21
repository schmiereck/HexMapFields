/*
 * Copyright (c) schmiereck.de, 2017
 */
package de.schmiereck.hexMapFields;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Vector;

/**
 * <p>
 *	State-Node.
 * </p>
 * <p>
 * 	Bilden Binärbaum Listen von States (Inner-States und Eingangs-States) als Key.<br/>
 * 	die Ausgangs-Zustände können daraus ausgelesen werden.
 * </p>
 * <p>
 * 	Binärbaum aus<br/>
 * 	State-Nodes: abState, bcState, caState<br/>
 * 	Next-State-Nodes: abState, bcState, caState, abInState, bcInState, caInState
 * </p>
 * 
 * @author smk
 * @version <p>03.06.2017:	created, smk</p>
 */
public class StateNode
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Die Child-Ebene des Binärbaum.
	 */
	private final HashMap<State, StateNode> states = new HashMap<>();
	
	/**
	 * Is the State of this State-Node.
	 */
	private State state = null;
	
	/**
	 * Is the State-Node of a Next-State-Node.
	 */
	private List<PropNextStateNode> propNextStateNodes = null;
	
	private final StateNode parentNode;
	
	/**
	 * Energie of all Parent-States and the {@link #state}.
	 */
	private int energie;
	
	private long usedCnt = 0;
	
	private final RuleSet ruleSet;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * Constructor.
	 *
	 */
	public StateNode(final StateNode parentNode, 
	                 final State state, 
	                 final RuleSet ruleSet)
	{
		//==========================================================================================
		this.parentNode = parentNode;
		this.state = state;
		
//		if (this.state == null) throw new RuntimeException("State is null.");
		
		final int energieParentNode;
		
		if (parentNode != null)
		{
			energieParentNode = parentNode.getEnergie();
		}
		else
		{
			energieParentNode = 0;
		}
		
		final int energieState;
		
		if (state != null)
		{
			energieState = state.getEnergie();
		}
		else
		{
			energieState = 0;
		}
		
		this.energie = energieParentNode + energieState;
		this.ruleSet = ruleSet;
		
		//==========================================================================================
	}
	
	public void add(final StateNode stateNode)
	{
		//==========================================================================================
		final State state = stateNode.getState();
		
		this.states.put(state, stateNode);

		//==========================================================================================
	}

	public StateNode get(final State metaState)
	{
		//==========================================================================================
		final StateNode stateNode;
		
		stateNode = this.states.get(metaState);

		//==========================================================================================
		return stateNode;
	}
	
	/**
	 * @return 
	 *			the value of attribute {@link #state}.
	 */
	public State getState()
	{
		return this.state;
	}
	
//	/**
//	 * @param state 
//	 * 			used to set the value of attribute {@link #state}.
//	 */
//	public void setState(final State state)
//	{
//		this.state = state;
//	}
	
	/**
	 * @return 
	 *			the value of attribute {@link #parentNode}.
	 */
	public StateNode getParentNode()
	{
		return this.parentNode;
	}

	
	/**
	 * @return 
	 *			the value of attribute {@link #propNextStateNodes}.
	 */
	public List<PropNextStateNode> getNextStateNodes()
	{
		return this.propNextStateNodes;
	}

	
	/**
	 * @param nextStateNode 
	 * 			used to set the value of attribute {@link #propNextStateNodes}.
	 */
	public void setNextStateNode(final StateNode nextStateNode, final long probability)
	{
		if (this.propNextStateNodes == null)
		{
			this.propNextStateNodes = new Vector<>();
		}
		this.propNextStateNodes.add(new PropNextStateNode(nextStateNode, probability));
	}

	/**
	 * @return 
	 *			the value of attribute {@link #energie}.
	 */
	public int getEnergie()
	{
		return this.energie;
	}

	public void addUsedCnt(final long runCnt)
	{
		//==========================================================================================
		this.usedCnt++;
		this.ruleSet.addUsedCnt(runCnt);
		
		//==========================================================================================
	}
	
	/**
	 * @return 
	 *			the value of attribute {@link #usedCnt}.
	 */
	public long getUsedCnt()
	{
		return this.usedCnt;
	}

	/**
	 * @param usedCnt 
	 * 			used to set the value of attribute {@link #usedCnt}.
	 */
	public void setUsedCnt(long usedCnt)
	{
		this.usedCnt = usedCnt;
	}

	
	/**
	 * @return 
	 *			the value of attribute {@link #ruleSet}.
	 */
	public RuleSet getRuleSet()
	{
		return this.ruleSet;
	}

	public boolean remove(final RuleSet removedRuleSet)
	{
		//==========================================================================================
		boolean ret = false;
		
		Iterator<Entry<State, StateNode>> it = this.states.entrySet().iterator();
		  
		while (it.hasNext()) 
		{
			Entry<State, StateNode> pair = it.next();
		        
			final StateNode stateNode = pair.getValue();
			
			if (stateNode.getRuleSet() == removedRuleSet)
			{
//				stateNode.state = null;
//				stateNode.nextStateNode = null;
//				stateNode.parentNode = null;
//				stateNode.ruleSet = null;
				
				it.remove(); // avoids a ConcurrentModificationException
			}
			
			stateNode.remove(removedRuleSet);
		}
		//==========================================================================================
		return ret;
		
	}
	
}
