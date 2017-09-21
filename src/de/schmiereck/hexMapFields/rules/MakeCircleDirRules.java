/*
 * Copyright (c) schmiereck.de, 2017
 */
package de.schmiereck.hexMapFields.rules;

import de.schmiereck.hexMapFields.Main;
import de.schmiereck.hexMapFields.MainService;
import de.schmiereck.hexMapFields.PropNextStateNode;
import de.schmiereck.hexMapFields.RuleSet;
import de.schmiereck.hexMapFields.State;
import de.schmiereck.hexMapFields.StateNode;
import de.schmiereck.hexMapFields.StateNodes;

/**
 * <p>
 *	Make Circle-Dir-Rules.
 * </p>
 * 
 * @author smk
 * @version <p>09.06.2017:	created, smk</p>
 */
public class MakeCircleDirRules
{
	//**********************************************************************************************
	// Functions:
	
	/**
	 * Init with B goes to this direction.
	 * Init with R od G cicles endles.
	 */
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

		final State s2EdgeState = new State(s1EdgeState);		// Energie: 2
		ruleSet.addState(s2EdgeState);
		
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
					                          s0EdgeState, s0EdgeState, s2EdgeState);
	
		}
		final StateNode rbMapFieldStateNode; 
		{
			// AB & CA active, others inactive
			rbMapFieldStateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                          s1EdgeState, s0EdgeState, s1EdgeState);
	
		}
		//------------------------------------------------------------------------------------------
		// RG: Circle
		
		// B: Richtungsanzeiger:
		{
			// inB -> RG
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                          s0EdgeState, s0EdgeState, s0EdgeState,
					                          s0EdgeState, s0EdgeState, s2EdgeState);
			
			stateNode.setNextStateNode(rgMapFieldStateNode, PropNextStateNode.MAX_probability);
		}
		{
			// RG -> s0
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                          s1EdgeState, s1EdgeState, s0EdgeState,
					                          s0EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.setNextStateNode(s0MapFieldStateNode, PropNextStateNode.MAX_probability);
		}

		{
			// inRG -> B
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                          s0EdgeState, s0EdgeState, s0EdgeState,
					                          s1EdgeState, s1EdgeState, s0EdgeState);
			
			stateNode.setNextStateNode(bMapFieldStateNode, PropNextStateNode.MAX_probability);
		}
		{
			// B -> s0
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                          s0EdgeState, s0EdgeState, s1EdgeState,
					                          s0EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.setNextStateNode(s0MapFieldStateNode, PropNextStateNode.MAX_probability);
		}
		
		// Cirlcle:
		{
			// inR -> G
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                          s0EdgeState, s0EdgeState, s0EdgeState,
					                          s1EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.setNextStateNode(gMapFieldStateNode, PropNextStateNode.MAX_probability);
		}
		{
			// G -> s0
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                          s0EdgeState, s1EdgeState, s0EdgeState,
					                          s0EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.setNextStateNode(s0MapFieldStateNode, PropNextStateNode.MAX_probability);
		}
		{
			// inG -> R
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                          s0EdgeState, s0EdgeState, s0EdgeState,
					                          s0EdgeState, s1EdgeState, s0EdgeState);
			
			stateNode.setNextStateNode(rMapFieldStateNode, PropNextStateNode.MAX_probability);
		}
		{
			// R -> s0
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                            s1EdgeState, s0EdgeState, s0EdgeState,
					                            s0EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.setNextStateNode(s0MapFieldStateNode, PropNextStateNode.MAX_probability);
		}
		//------------------------------------------------------------------------------------------
		ruleSet.setInitStateNode(bMapFieldStateNode);
		
		//==========================================================================================
		return ruleSet;
	}

}
