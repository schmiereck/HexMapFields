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
		final State s0EdgeState = Main.s0EdgeState;

		final State s1EdgeState = new State(Main.s1EdgeState);
		ruleSet.addState(s1EdgeState);
		
		final State s2EdgeState = new State(Main.s1EdgeState);
		ruleSet.addState(s2EdgeState);
		
		//------------------------------------------------------------------------------------------
		// AB (R), BC (G), CA (B)
		
		final StateNode r1MapFieldStateNode; 
		{
			// r1-AB active, others inactive
			r1MapFieldStateNode = 
					MainService.makeStateNode(stateNodes, ruleSet,
					                          s1EdgeState, s0EdgeState, s0EdgeState);
		}
		final StateNode r2MapFieldStateNode; 
		{
			// r2-AB active, others inactive
			r2MapFieldStateNode = 
					MainService.makeStateNode(stateNodes, ruleSet,
					                          s2EdgeState, s0EdgeState, s0EdgeState);
		}
		
		final StateNode g1MapFieldStateNode; 
		{
			// g1-BC active, others inactive
			g1MapFieldStateNode = 
					MainService.makeStateNode(stateNodes, ruleSet,
					                          s0EdgeState, s1EdgeState, s0EdgeState);
		}
		final StateNode g2MapFieldStateNode; 
		{
			// g2-BC active, others inactive
			g2MapFieldStateNode = 
					MainService.makeStateNode(stateNodes, ruleSet,
					                          s0EdgeState, s2EdgeState, s0EdgeState);
		}
		
		final StateNode b1MapFieldStateNode; 
		{
			// b1-CA active, others inactive
			b1MapFieldStateNode = 
					MainService.makeStateNode(stateNodes, ruleSet,
					                          s0EdgeState, s0EdgeState, s1EdgeState);
		}
		final StateNode b2MapFieldStateNode; 
		{
			// b2-CA active, others inactive
			b2MapFieldStateNode = 
					MainService.makeStateNode(stateNodes, ruleSet,
					                          s0EdgeState, s0EdgeState, s2EdgeState);
		}
		//------------------------------------------------------------------------------------------
		// R:
		{
			// R1 -> s0
			final StateNode stateNode = 
					MainService.makeStateNode(stateNodes, ruleSet,
					                          s1EdgeState, s0EdgeState, s0EdgeState,
					                          s0EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.setNextStateNode(s0MapFieldStateNode);
		}
		{
			// R2 -> s0
			final StateNode stateNode = 
					MainService.makeStateNode(stateNodes, ruleSet,
					                          s2EdgeState, s0EdgeState, s0EdgeState,
					                          s0EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.setNextStateNode(s0MapFieldStateNode);
		}

		{
			// inR1 -> G1
			final StateNode stateNode = 
					MainService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s0EdgeState, s0EdgeState,
					                            s1EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.setNextStateNode(g1MapFieldStateNode);
		}
		{
			// inR2 -> B2
			final StateNode stateNode = 
					MainService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s0EdgeState, s0EdgeState,
					                            s2EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.setNextStateNode(b2MapFieldStateNode);
		}
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		// G:
		{
			// G1 -> s0
			final StateNode stateNode = 
					MainService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s1EdgeState, s0EdgeState,
					                            s0EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.setNextStateNode(s0MapFieldStateNode);
		}
		{
			// G2 -> s0
			final StateNode stateNode = 
					MainService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s2EdgeState, s0EdgeState,
					                            s0EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.setNextStateNode(s0MapFieldStateNode);
		}

		{
			// inG1 -> B1
			final StateNode stateNode = 
					MainService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s0EdgeState, s0EdgeState,
					                            s0EdgeState, s1EdgeState, s0EdgeState);
			
			stateNode.setNextStateNode(b1MapFieldStateNode);
		}
		{
			// inG2 -> R2
			final StateNode stateNode = 
					MainService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s0EdgeState, s0EdgeState,
					                            s0EdgeState, s2EdgeState, s0EdgeState);
			
			stateNode.setNextStateNode(r2MapFieldStateNode);
		}
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
		// B:
		{
			// B1 -> s0
			final StateNode stateNode = 
					MainService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s0EdgeState, s1EdgeState,
					                            s0EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.setNextStateNode(s0MapFieldStateNode);
		}
		{
			// B2 -> s0
			final StateNode stateNode = 
					MainService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s0EdgeState, s2EdgeState,
					                            s0EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.setNextStateNode(s0MapFieldStateNode);
		}

		{
			// inB1 -> G2
			final StateNode stateNode = 
					MainService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s0EdgeState, s0EdgeState,
					                            s0EdgeState, s0EdgeState, s1EdgeState);
			
			stateNode.setNextStateNode(g2MapFieldStateNode);
		}
		{
			// inB2 -> R1
			final StateNode stateNode = 
					MainService.makeStateNode(stateNodes, ruleSet,
					                            s0EdgeState, s0EdgeState, s0EdgeState,
					                            s0EdgeState, s0EdgeState, s2EdgeState);
			
			stateNode.setNextStateNode(r1MapFieldStateNode);
		}
		//------------------------------------------------------------------------------------------
		ruleSet.setInitStateNode(g1MapFieldStateNode);
		
		//==========================================================================================
		return ruleSet;
	}

}
