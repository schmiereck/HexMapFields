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
 *	Make Circle-Big1-Static Rules.
 * </p>
 * 
 * @author thoma
 * @version <p>11.08.2017:	created, thoma</p>
 */
public class MakeCircleBig1StaticRules
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
		
		final State s2EdgeState = new State(Main.s1EdgeState);	// Energie: 2
		ruleSet.addState(s2EdgeState);
		
		//------------------------------------------------------------------------------------------
		// AB (R), BC (G), CA (B)
		
		final StateNode r1MapFieldStateNode; 
		{
			// r1-AB active, others inactive
			r1MapFieldStateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                          s1EdgeState, s0EdgeState, s0EdgeState);
		}
		final StateNode r2MapFieldStateNode; 
		{
			// r2-AB active, others inactive
			r2MapFieldStateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                          s2EdgeState, s0EdgeState, s0EdgeState);
		}
		
		final StateNode g1MapFieldStateNode; 
		{
			// g1-BC active, others inactive
			g1MapFieldStateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                          s0EdgeState, s1EdgeState, s0EdgeState);
		}
		final StateNode g2MapFieldStateNode; 
		{
			// g2-BC active, others inactive
			g2MapFieldStateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                          s0EdgeState, s2EdgeState, s0EdgeState);
		}
		
		final StateNode b1MapFieldStateNode; 
		{
			// b1-CA active, others inactive
			b1MapFieldStateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                          s0EdgeState, s0EdgeState, s1EdgeState);
		}
		final StateNode b2MapFieldStateNode; 
		{
			// b2-CA active, others inactive
			b2MapFieldStateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                          s0EdgeState, s0EdgeState, s2EdgeState);
		}
		//------------------------------------------------------------------------------------------
		// R:
		{
			// R1 -> s0
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                          s1EdgeState, s0EdgeState, s0EdgeState,
					                          s0EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.addNextStateNode(s0MapFieldStateNode, PropNextStateNode.MAX_probability);
		}
		{
			// R2 -> s0
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                          s2EdgeState, s0EdgeState, s0EdgeState,
					                          s0EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.addNextStateNode(s0MapFieldStateNode, PropNextStateNode.MAX_probability);
		}

		{
			// inR1 -> G1
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s0EdgeState, s0EdgeState,
					                            s1EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.addNextStateNode(g1MapFieldStateNode, PropNextStateNode.MAX_probability);
		}
		{
			// inR2 -> B2
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s0EdgeState, s0EdgeState,
					                            s2EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.addNextStateNode(b2MapFieldStateNode, PropNextStateNode.MAX_probability);
		}
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		// G:
		{
			// G1 -> s0
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s1EdgeState, s0EdgeState,
					                            s0EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.addNextStateNode(s0MapFieldStateNode, PropNextStateNode.MAX_probability);
		}
		{
			// G2 -> s0
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s2EdgeState, s0EdgeState,
					                            s0EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.addNextStateNode(s0MapFieldStateNode, PropNextStateNode.MAX_probability);
		}

		{
			// inG1 -> B1
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s0EdgeState, s0EdgeState,
					                            s0EdgeState, s1EdgeState, s0EdgeState);
			
			stateNode.addNextStateNode(b1MapFieldStateNode, PropNextStateNode.MAX_probability);
		}
		{
			// inG2 -> R2
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s0EdgeState, s0EdgeState,
					                            s0EdgeState, s2EdgeState, s0EdgeState);
			
			stateNode.addNextStateNode(r2MapFieldStateNode, PropNextStateNode.MAX_probability);
		}
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		// B:
		{
			// B1 -> s0
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s0EdgeState, s1EdgeState,
					                            s0EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.addNextStateNode(s0MapFieldStateNode, PropNextStateNode.MAX_probability);
		}
		{
			// B2 -> s0
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s0EdgeState, s2EdgeState,
					                            s0EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.addNextStateNode(s0MapFieldStateNode, PropNextStateNode.MAX_probability);
		}

		{
			// inB1 -> G2
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s0EdgeState, s0EdgeState,
					                            s0EdgeState, s0EdgeState, s1EdgeState);
			
			stateNode.addNextStateNode(g2MapFieldStateNode, PropNextStateNode.MAX_probability);
		}
		{
			// inB2 -> R1
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s0EdgeState, s0EdgeState,
					                            s0EdgeState, s0EdgeState, s2EdgeState);
			
			stateNode.addNextStateNode(r1MapFieldStateNode, PropNextStateNode.MAX_probability);
		}
		//------------------------------------------------------------------------------------------
		ruleSet.setInitStateNode(g1MapFieldStateNode);
		
		//==========================================================================================
		return ruleSet;
	}

}
