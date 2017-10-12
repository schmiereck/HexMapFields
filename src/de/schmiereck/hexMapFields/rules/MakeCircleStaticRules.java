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
 *	Make Circle-Static-Rules.
 * </p>
 * 
 * @author smk
 * @version <p>09.06.2017:	created, smk</p>
 */
public class MakeCircleStaticRules
{
	//**********************************************************************************************
	// Constants:
	
	/**
	 * 1.0D is working
	 */
	private static final long MAX_PROB = PropNextStateNode.MAX_NEXT_probability;//1.0D;

	/**
	 * 0.1D is working.
	 */
	private static final long MIN_PROB = PropNextStateNode.MIN_NEXT_probability;//100L;//0.1D;

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
			// inR -> G
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                           s0EdgeState, s0EdgeState, s0EdgeState,
					                           s1EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.addNextStateNode(rMapFieldStateNode, MIN_PROB);
			stateNode.addNextStateNode(gMapFieldStateNode, MAX_PROB);
			stateNode.addNextStateNode(bMapFieldStateNode, MIN_PROB);
		}
		{
			// R -> s0
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                           s1EdgeState, s0EdgeState, s0EdgeState,
					                           s0EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.addNextStateNode(s0MapFieldStateNode, MAX_PROB);
		}
		
		{
			// inG -> R
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                           s0EdgeState, s0EdgeState, s0EdgeState,
					                           s0EdgeState, s1EdgeState, s0EdgeState);
			
			stateNode.addNextStateNode(rMapFieldStateNode, MAX_PROB);
			stateNode.addNextStateNode(gMapFieldStateNode, MIN_PROB);
			stateNode.addNextStateNode(bMapFieldStateNode, MIN_PROB);
		}
		{
			// G -> s0
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                           s0EdgeState, s1EdgeState, s0EdgeState,
					                           s0EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.addNextStateNode(s0MapFieldStateNode, MAX_PROB);
		}
		
		{
			// inB -> G
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                           s0EdgeState, s0EdgeState, s0EdgeState,
					                           s0EdgeState, s0EdgeState, s1EdgeState);
			
			stateNode.addNextStateNode(rMapFieldStateNode, MIN_PROB);
			stateNode.addNextStateNode(gMapFieldStateNode, MAX_PROB);
			stateNode.addNextStateNode(bMapFieldStateNode, MIN_PROB);
		}
		{
			// B -> s0
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                           s0EdgeState, s0EdgeState, s1EdgeState,
					                           s0EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.addNextStateNode(s0MapFieldStateNode, MAX_PROB);
		}
		//------------------------------------------------------------------------------------------
		ruleSet.setInitStateNode(rMapFieldStateNode);
		
		//==========================================================================================
		return ruleSet;
	}

}
