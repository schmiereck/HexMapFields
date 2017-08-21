/*
 * Copyright (c) schmiereck.de, 2017
 */
package de.schmiereck.hexMapFields;

import java.util.List;
import java.util.Vector;

/**
 * <p>
 *	Rule-Set.<br/>
 *	Liste von {@link State} die erzeugt werden dürfen.<br/>
 *	Liste von {@link StateNode} mit Result-State.
 * </p>
 * 
 * @author smk
 * @version <p>23.05.2017:	created, smk</p>
 */
public class RuleSet
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Liste von {@link State} die vom Rule-Set erzeugt werden dürfen.
	 */
	private final List<State> states = new Vector<>();
	
	/**
	 * Liste von {@link StateNode} mit Result-State.
	 */
	private final List<StateNode> stateNodes = new Vector<>();
	
	private StateNode initStateNode = null;

	/**
	 * Using Counter.
	 */
	private long usedCnt = 0L;
	
	/**
	 * RunCnt last use this RuleSet.
	 */
	private long lastRunCnt = -1L;
	
	/**
	 * RunCnt creating this RuleSet.
	 */
	private final long startRunCnt;
	
	private final RuleSet parentRuleSet;
	
	//**********************************************************************************************
	// Functions:
	
	/**
	 * Constructor.
	 */
	public RuleSet(final long startRunCnt)
	{
		//==========================================================================================
		this.startRunCnt = startRunCnt;
		this.parentRuleSet = null;
		
		//==========================================================================================
	}
	
	/**
	 * Copy Constructor.
	 */
	public RuleSet(final RuleSet cloneRuleSet, final long startRunCnt)
	{
		//==========================================================================================
		for (final State cloneState : cloneRuleSet.states)
		{
			this.states.add(cloneState);
		}

		for (final StateNode stateNode : cloneRuleSet.stateNodes)
		{
			this.stateNodes.add(stateNode);
		}

		this.initStateNode = cloneRuleSet.initStateNode;
		this.startRunCnt = startRunCnt;

		this.parentRuleSet = cloneRuleSet;
		
		//==========================================================================================
	}

	public void addState(final State state)
	{
		//==========================================================================================
		this.states.add(state);

		//==========================================================================================
	}

	public void addStateNode(final StateNode stateNode)
	{
		//==========================================================================================
		this.stateNodes.add(stateNode);
		
		//==========================================================================================
	}

	public StateNode getInitStateNode()
	{
		//==========================================================================================
		return this.initStateNode;
	}

	public void setInitStateNode(final StateNode initStateNode)
	{
		//==========================================================================================
		this.initStateNode = initStateNode;
		//==========================================================================================
	}
	
	/**
	 * @return 
	 *			the value of attribute {@link #states}.
	 */
	public List<State> getStates()
	{
		return this.states;
	}

	/**
	 * @return 
	 *			the value of attribute {@link #stateNodes}.
	 */
	public List<StateNode> getStateNodes()
	{
		return this.stateNodes;
	}

	public void addUsedCnt(final long runCnt)
	{
		//==========================================================================================
		this.usedCnt++;
		this.lastRunCnt = runCnt;
		
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
	 * @return 
	 *			the value of attribute {@link #lastRunCnt}.
	 */
	public long getLastRunCnt()
	{
		return this.lastRunCnt;
	}

	/**
	 * @return 
	 *			the value of attribute {@link #startRunCnt}.
	 */
	public long getStartRunCnt()
	{
		return this.startRunCnt;
	}

	/**
	 * @return 
	 *			the value of attribute {@link #parentRuleSet}.
	 */
	public RuleSet getParentRuleSet()
	{
		return this.parentRuleSet;
	}
}