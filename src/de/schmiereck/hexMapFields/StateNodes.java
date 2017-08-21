/*
 * Copyright (c) schmiereck.de, 2017
 */
package de.schmiereck.hexMapFields;

/**
 * <p>
 *	States.
 * </p>
 * 
 * @author smk
 * @version <p>01.06.2017:	created, smk</p>
 */
public class StateNodes
{
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Liste der mit den State-Nodes (Inner-States und Eingangs-States) als Key und
	 * die Ausgangs-Zustände können daraus ausgelesen werden.
	 * 
	 * Binärbaum aus: state, abState, bcState, caState
	 */
	private final StateNode rootNode = new StateNode(null, null, null);
	
	//**********************************************************************************************
	// Functions:

	public StateNode makeNode(final StateNode parentStateNode, final State state, final RuleSet ruleSet)
	{
		//==========================================================================================
		final StateNode retStateNode;
		
		final StateNode localStateNode = parentStateNode.get(state);

		if (localStateNode == null)
		{
			retStateNode = new StateNode(parentStateNode, state, ruleSet);
			
//			retStateNode.setState(state);
			
			parentStateNode.add(retStateNode);
		}
		else
		{
			retStateNode = localStateNode;
		}
		
		//==========================================================================================
		return retStateNode;
	}

	public StateNode searchNode(final StateNode stateNode, final State state)
	{
		//==========================================================================================
		final StateNode retStateNode;
		
		final StateNode localStateNode = stateNode.get(state);

		if (localStateNode == null)
		{
//			retStateNode = new StateNode(stateNode);
//			
//			retStateNode.setState(state);
//			
//			stateNode.add(state, retStateNode);

			retStateNode = localStateNode;
		}
		else
		{
			retStateNode = localStateNode;
		}
		
		//==========================================================================================
		return retStateNode;
	}

	/**
	 * @return 
	 *			the value of attribute {@link #rootNode}.
	 */
	public StateNode getRootNode()
	{
		return this.rootNode;
	}

	public void remove(final RuleSet removedRuleSet)
	{
		//==========================================================================================
		this.rootNode.remove(removedRuleSet);
		
		//==========================================================================================
		
	}

}
