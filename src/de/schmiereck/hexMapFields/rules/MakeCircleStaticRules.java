/*
 * Copyright (c) schmiereck.de, 2017
 */
package de.schmiereck.hexMapFields.rules;

import de.schmiereck.hexMapFields.Main;
import de.schmiereck.hexMapFields.MainService;
import de.schmiereck.hexMapFields.RuleSet;
import de.schmiereck.hexMapFields.State;
import de.schmiereck.hexMapFields.StateNode;
import de.schmiereck.hexMapFields.StateNodes;

/**
 * <p>
 *	Make Circle-Static-Rules.
 * </p>
 * 
 * @author smk
 * @version <p>09.06.2017:	created, smk</p>
 */
public class MakeCircleStaticRules
{
	//**********************************************************************************************
	// Functions:
	
	public static RuleSet 
	makeRules(final StateNodes stateNodes,  final RuleSet emptyRuleSet,
	                 final StateNode s0MapFieldStateNode)
	{
		//==========================================================================================
		final RuleSet ruleSet = new RuleSet(emptyRuleSet, MainService.getRunCnt());
		
		//------------------------------------------------------------------------------------------
		final State s0EdgeState = Main.s0EdgeState;

		final State s1EdgeState = new State(Main.s1EdgeState);
		ruleSet.addState(s1EdgeState);
		
		//------------------------------------------------------------------------------------------
		final StateNode rMapFieldStateNode; 
		{
			// AB active, others inactive
			rMapFieldStateNode = 
					MainService.makeStateNode(stateNodes, ruleSet,
					                            s1EdgeState, s0EdgeState, s0EdgeState);
	
		}
		final StateNode rgMapFieldStateNode; 
		{
			// AB & BC active, others inactive
			rgMapFieldStateNode = 
					MainService.makeStateNode(stateNodes, ruleSet,
					                            s1EdgeState, s1EdgeState, s0EdgeState);
	
		}
		final StateNode gMapFieldStateNode; 
		{
			// BC active, others inactive
			gMapFieldStateNode = 
					MainService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s1EdgeState, s0EdgeState);
	
		}
		final StateNode gbMapFieldStateNode; 
		{
			// BC & CA active, others inactive
			gbMapFieldStateNode = 
					MainService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s1EdgeState, s1EdgeState);
	
		}
		final StateNode bMapFieldStateNode; 
		{
			// CA active, others inactive
			bMapFieldStateNode = 
					MainService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s0EdgeState, s1EdgeState);
	
		}
		final StateNode rbMapFieldStateNode; 
		{
			// AB & CA active, others inactive
			rbMapFieldStateNode = 
					MainService.makeStateNode(stateNodes, ruleSet,
					                            s1EdgeState, s0EdgeState, s1EdgeState);
	
		}
		//------------------------------------------------------------------------------------------
		{
			// R -> s0
			final StateNode stateNode = 
					MainService.makeStateNode(stateNodes, ruleSet,
					                            s1EdgeState, s0EdgeState, s0EdgeState,
					                            s0EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.setNextStateNode(s0MapFieldStateNode);
		}
		{
			// inR -> G
			final StateNode stateNode = 
					MainService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s0EdgeState, s0EdgeState,
					                            s1EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.setNextStateNode(gMapFieldStateNode);
		}
		{
			// G -> s0
			final StateNode stateNode = 
					MainService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s1EdgeState, s0EdgeState,
					                            s0EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.setNextStateNode(s0MapFieldStateNode);
		}
		{
			// inG -> R
			final StateNode stateNode = 
					MainService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s0EdgeState, s0EdgeState,
					                            s0EdgeState, s1EdgeState, s0EdgeState);
			
			stateNode.setNextStateNode(rMapFieldStateNode);
		}
		//------------------------------------------------------------------------------------------
		ruleSet.setInitStateNode(rMapFieldStateNode);
		
		//==========================================================================================
		return ruleSet;
	}

}
