/*
 * Copyright (c) schmiereck.de, 2017
 */
package de.schmiereck.hexMapFields.rules;

import de.schmiereck.hexMapFields.RuleSet;
import de.schmiereck.hexMapFields.State;
import de.schmiereck.hexMapFields.StateNode;
import de.schmiereck.hexMapFields.StateNodes;

/**
 * <p>
 *	Creating Rules Service.
 * </p>
 * 
 * @author thoma
 * @version <p>03.09.2017:	created, thoma</p>
 */
public class RulesService
{
	//**********************************************************************************************
	// Functions:
	
	//==========================================================================================
	public static StateNode
	makeStateNode(final StateNodes stateNodes, final RuleSet ruleSet,
	              final State abState, final State bcState, final State caState)
	{
		//==========================================================================================
		final StateNode retStateNode;
		
		// Suche in einem Binärbaum.
		final StateNode rootNode = stateNodes.getRootNode();
		
		//------------------------------------------------------------------------------------------
		final StateNode abStateNode = stateNodes.makeNode(rootNode, abState, ruleSet);
		
		final StateNode bcStateNode = stateNodes.makeNode(abStateNode, bcState, ruleSet);
		
		final StateNode caStateNode = stateNodes.makeNode(bcStateNode, caState, ruleSet);

		//------------------------------------------------------------------------------------------
		retStateNode = caStateNode;
		
		ruleSet.addStateNode(retStateNode);
		
		//==========================================================================================
		return retStateNode;
	}

	public static StateNode
	makeStateNode(final StateNodes stateNodes, final RuleSet ruleSet,
	              final State abState, final State bcState, final State caState,
	              final State abInState, final State bcInState, final State caInState)
	{
		//==========================================================================================
		final StateNode retStateNode;
		
		// Suche in einem Binärbaum.
		final StateNode rootStateNode = stateNodes.getRootNode();
		
		//------------------------------------------------------------------------------------------
		final StateNode abStateNode = stateNodes.makeNode(rootStateNode, abState, ruleSet);
		
		final StateNode bcStateNode = stateNodes.makeNode(abStateNode, bcState, ruleSet);
		
		final StateNode caStateNode = stateNodes.makeNode(bcStateNode, caState, ruleSet);
		
		//------------------------------------------------------------------------------------------
		final StateNode abInStateNode = stateNodes.makeNode(caStateNode, abInState, ruleSet);
		
		final StateNode bcInStateNode = stateNodes.makeNode(abInStateNode, bcInState, ruleSet);
		
		final StateNode caInStateNode = stateNodes.makeNode(bcInStateNode, caInState, ruleSet);
		
		//------------------------------------------------------------------------------------------
		retStateNode = caInStateNode;

		ruleSet.addStateNode(retStateNode);
		
		//==========================================================================================
		return retStateNode;
	}

}
