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
		final State s0EdgeState = Main.s0EdgeState;

		final State s1EdgeState = Main.e1EdgeState;
		
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
			// G -> s0
			final StateNode stateNode = 
					MainService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s1EdgeState, s0EdgeState,
					                            s0EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.setNextStateNode(s0MapFieldStateNode);
		}
		{
			// B -> s0
			final StateNode stateNode = 
					MainService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s0EdgeState, s1EdgeState,
					                            s0EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.setNextStateNode(s0MapFieldStateNode);
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
			// GB -> s0
			final StateNode stateNode = 
					MainService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s1EdgeState, s1EdgeState,
					                            s0EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.setNextStateNode(s0MapFieldStateNode);
		}
		{
			// RB -> s0
			final StateNode stateNode = 
					MainService.makeStateNode(stateNodes, ruleSet,
					                            s1EdgeState, s0EdgeState, s1EdgeState,
					                            s0EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.setNextStateNode(s0MapFieldStateNode);
		}
		
		{
			// inR -> GB
			final StateNode stateNode = 
					MainService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s0EdgeState, s0EdgeState,
					                            s1EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.setNextStateNode(gbMapFieldStateNode);
		}
		
		{
			// inG -> RB
			final StateNode stateNode = 
					MainService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s0EdgeState, s0EdgeState,
					                            s0EdgeState, s1EdgeState, s0EdgeState);
			
			stateNode.setNextStateNode(rbMapFieldStateNode);
		}
		{
			// inB -> RG
			final StateNode stateNode = 
					MainService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s0EdgeState, s0EdgeState,
					                            s0EdgeState, s0EdgeState, s1EdgeState);
			
			stateNode.setNextStateNode(rgMapFieldStateNode);
		}
		
		{
			// inRB -> B
			final StateNode stateNode = 
					MainService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s0EdgeState, s0EdgeState,
					                            s1EdgeState, s1EdgeState, s0EdgeState);
			
			stateNode.setNextStateNode(bMapFieldStateNode);
		}
		{
			// inGB -> R
			final StateNode stateNode = 
					MainService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s0EdgeState, s0EdgeState,
					                            s0EdgeState, s1EdgeState, s1EdgeState);
			
			stateNode.setNextStateNode(rMapFieldStateNode);
		}
		{
			// inRB -> G
			final StateNode stateNode = 
					MainService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s0EdgeState, s0EdgeState,
					                            s1EdgeState, s0EdgeState, s1EdgeState);
			
			stateNode.setNextStateNode(gMapFieldStateNode);
		}
		{
			// inRGB -> s0
			final StateNode stateNode = 
					MainService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s0EdgeState, s0EdgeState,
					                            s1EdgeState, s1EdgeState, s1EdgeState);
			
			stateNode.setNextStateNode(s0MapFieldStateNode);
		}
		//------------------------------------------------------------------------------------------
		ruleSet.setInitStateNode(bMapFieldStateNode);
		
		//==========================================================================================
		return ruleSet;
	}

}
