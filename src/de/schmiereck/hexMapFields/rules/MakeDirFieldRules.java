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
 *	Make Dir-Field-Rules
 * </p>
 * Elektrische Ladung:
 * https://de.wikipedia.org/wiki/Quark_(Physik)
 * Die elektrische Ladung der Quarks ist entweder −1/3 oder +2/3 der Elementarladung.
 * Wäre an einer Seite rein, an den zwei anderen heraus.
 * 
 * @author smk
 * @version <p>09.06.2017:	created, smk</p>
 */
public class MakeDirFieldRules
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

		final State s1EdgeState = new State(Main.e1EdgeState);	// Energie: 1
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
			
			stateNode.addNextStateNode(s0MapFieldStateNode, PropNextStateNode.MAX_probability);
		}
		{
			// G -> s0
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s1EdgeState, s0EdgeState,
					                            s0EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.addNextStateNode(s0MapFieldStateNode, PropNextStateNode.MAX_probability);
		}
		{
			// B -> s0
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s0EdgeState, s1EdgeState,
					                            s0EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.addNextStateNode(s0MapFieldStateNode, PropNextStateNode.MAX_probability);
		}
		{
			// RG -> s0
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                            s1EdgeState, s1EdgeState, s0EdgeState,
					                            s0EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.addNextStateNode(s0MapFieldStateNode, PropNextStateNode.MAX_probability);
		}
		{
			// GB -> s0
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s1EdgeState, s1EdgeState,
					                            s0EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.addNextStateNode(s0MapFieldStateNode, PropNextStateNode.MAX_probability);
		}
		{
			// RB -> s0
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                            s1EdgeState, s0EdgeState, s1EdgeState,
					                            s0EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.addNextStateNode(s0MapFieldStateNode, PropNextStateNode.MAX_probability);
		}
		
		{
			// inR -> GB
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s0EdgeState, s0EdgeState,
					                            s1EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.addNextStateNode(gbMapFieldStateNode, PropNextStateNode.MAX_probability);
		}
		
		{
			// inG -> RB
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s0EdgeState, s0EdgeState,
					                            s0EdgeState, s1EdgeState, s0EdgeState);
			
			stateNode.addNextStateNode(rbMapFieldStateNode, PropNextStateNode.MAX_probability);
		}
		{
			// inB -> RG
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s0EdgeState, s0EdgeState,
					                            s0EdgeState, s0EdgeState, s1EdgeState);
			
			stateNode.addNextStateNode(rgMapFieldStateNode, PropNextStateNode.MAX_probability);
		}
		
		{
			// inRB -> B
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s0EdgeState, s0EdgeState,
					                            s1EdgeState, s1EdgeState, s0EdgeState);
			
			stateNode.addNextStateNode(bMapFieldStateNode, PropNextStateNode.MAX_probability);
		}
		{
			// inGB -> R
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s0EdgeState, s0EdgeState,
					                            s0EdgeState, s1EdgeState, s1EdgeState);
			
			stateNode.addNextStateNode(rMapFieldStateNode, PropNextStateNode.MAX_probability);
		}
		{
			// inRB -> G
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s0EdgeState, s0EdgeState,
					                            s1EdgeState, s0EdgeState, s1EdgeState);
			
			stateNode.addNextStateNode(gMapFieldStateNode, PropNextStateNode.MAX_probability);
		}
		{
			// inRGB -> s0
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s0EdgeState, s0EdgeState,
					                            s1EdgeState, s1EdgeState, s1EdgeState);
			
			stateNode.addNextStateNode(s0MapFieldStateNode, PropNextStateNode.MAX_probability);
		}
		//------------------------------------------------------------------------------------------
		ruleSet.setInitStateNode(bMapFieldStateNode);
		
		//==========================================================================================
		return ruleSet;
	}

}
