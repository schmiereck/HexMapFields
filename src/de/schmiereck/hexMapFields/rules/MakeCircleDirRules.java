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
		final State s0EdgeState = Main.s0EdgeState;				// Energie: 1

		final State s1EdgeState = Main.s1EdgeState;				// Energie: 2

		final State s3EdgeState = new State(s1EdgeState);		// Energie: 3
		ruleSet.addState(s3EdgeState);
		
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
					                          s0EdgeState, s0EdgeState, s3EdgeState);
	
		}
		final StateNode rbMapFieldStateNode; 
		{
			// AB & CA active, others inactive
			rbMapFieldStateNode = 
					MainService.makeStateNode(stateNodes, ruleSet,
					                          s1EdgeState, s0EdgeState, s1EdgeState);
	
		}
		//------------------------------------------------------------------------------------------
		// RG: Circle
		
		// B: Richtungsanzeiger:
		{
			// inB -> RG
			final StateNode stateNode = 
					MainService.makeStateNode(stateNodes, ruleSet,
					                          s0EdgeState, s0EdgeState, s0EdgeState,
					                          s0EdgeState, s0EdgeState, s3EdgeState);
			
			stateNode.setNextStateNode(rgMapFieldStateNode);
		}
		{
			// RG -> s0
			final StateNode stateNode = 
					MainService.makeStateNode(stateNodes, ruleSet,
					                          s1EdgeState, s1EdgeState, s0EdgeState,
					                          s0EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.setNextStateNode(s0MapFieldStateNode);
		}

		{
			// inRG -> B
			final StateNode stateNode = 
					MainService.makeStateNode(stateNodes, ruleSet,
					                          s0EdgeState, s0EdgeState, s0EdgeState,
					                          s1EdgeState, s1EdgeState, s0EdgeState);
			
			stateNode.setNextStateNode(bMapFieldStateNode);
		}
		{
			// B -> s0
			final StateNode stateNode = 
					MainService.makeStateNode(stateNodes, ruleSet,
					                          s0EdgeState, s0EdgeState, s1EdgeState,
					                          s0EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.setNextStateNode(s0MapFieldStateNode);
		}
		
		// Cirlcle:
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
		{
			// R -> s0
			final StateNode stateNode = 
					MainService.makeStateNode(stateNodes, ruleSet,
					                            s1EdgeState, s0EdgeState, s0EdgeState,
					                            s0EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.setNextStateNode(s0MapFieldStateNode);
		}
		//------------------------------------------------------------------------------------------
		ruleSet.setInitStateNode(bMapFieldStateNode);
		
		//==========================================================================================
		return ruleSet;
	}

}
