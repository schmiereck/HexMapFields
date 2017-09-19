/*
 * Copyright (c) schmiereck.de, 2017
 */
package de.schmiereck.hexMapFields.rules;

import de.schmiereck.hexMapFields.Main;
import de.schmiereck.hexMapFields.MainService;
import de.schmiereck.hexMapFields.NextStateNode;
import de.schmiereck.hexMapFields.RuleSet;
import de.schmiereck.hexMapFields.State;
import de.schmiereck.hexMapFields.StateNode;
import de.schmiereck.hexMapFields.StateNodes;

/**
 * <p>
 *	Make Runner-Rules.
 * </p>
 * 
 * @author smk
 * @version <p>09.06.2017:	created, smk</p>
 */
public class MakeRunnerRules
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
		final State s0EdgeState = Main.s0EdgeState;				// Energie: 0

		final State s1EdgeState = new State(Main.s1EdgeState);	// Energie: 1
		ruleSet.addState(s1EdgeState);
		
		//------------------------------------------------------------------------------------------
		final StateNode rMapFieldStateNode; 
		{
			// AB active, others inactive
			rMapFieldStateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                            s1EdgeState, s0EdgeState, s0EdgeState);
	
		}
		final StateNode rgMapFieldStateNode; 
		{
			// AB & BC active, others inactive
			rgMapFieldStateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                            s1EdgeState, s1EdgeState, s0EdgeState);
	
		}
		final StateNode gMapFieldStateNode; 
		{
			// BC active, others inactive
			gMapFieldStateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s1EdgeState, s0EdgeState);
	
		}
		final StateNode gbMapFieldStateNode; 
		{
			// BC & CA active, others inactive
			gbMapFieldStateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s1EdgeState, s1EdgeState);
	
		}
		final StateNode bMapFieldStateNode; 
		{
			// CA active, others inactive
			bMapFieldStateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s0EdgeState, s1EdgeState);
	
		}
		final StateNode rbMapFieldStateNode; 
		{
			// AB & CA active, others inactive
			rbMapFieldStateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                            s1EdgeState, s0EdgeState, s1EdgeState);
	
		}
		//------------------------------------------------------------------------------------------
		{
			// R -> s0
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                            s1EdgeState, s0EdgeState, s0EdgeState,
					                            s0EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.setNextStateNode(s0MapFieldStateNode, NextStateNode.MAX_probability);
		}
		{
			// inR -> G
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s0EdgeState, s0EdgeState,
					                            s1EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.setNextStateNode(gMapFieldStateNode, NextStateNode.MAX_probability);
		}
		{
			// G -> s0
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s1EdgeState, s0EdgeState,
					                            s0EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.setNextStateNode(s0MapFieldStateNode, NextStateNode.MAX_probability);
		}
		{
			// inG -> B
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s0EdgeState, s0EdgeState,
					                            s0EdgeState, s1EdgeState, s0EdgeState);
			
			stateNode.setNextStateNode(bMapFieldStateNode, NextStateNode.MAX_probability);
		}
		{
			// B -> s0
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s0EdgeState, s1EdgeState,
					                            s0EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.setNextStateNode(s0MapFieldStateNode, NextStateNode.MAX_probability);
		}
		{
			// inB -> R
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s0EdgeState, s0EdgeState,
					                            s0EdgeState, s0EdgeState, s1EdgeState);
			
			stateNode.setNextStateNode(rMapFieldStateNode, NextStateNode.MAX_probability);
		}
		//------------------------------------------------------------------------------------------
		ruleSet.setInitStateNode(bMapFieldStateNode);
		
		//==========================================================================================
		return ruleSet;
	}
}
