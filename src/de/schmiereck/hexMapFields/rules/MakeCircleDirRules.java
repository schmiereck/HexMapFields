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

		final State s1EdgeState = new State(s0EdgeState);		// Energie: 1
		ruleSet.addState(s1EdgeState);

		final State s2EdgeState = new State(s0EdgeState);		// Energie: 1
		ruleSet.addState(s2EdgeState);
		
		final State s3EdgeState = new State(s0EdgeState);		// Energie: 1
		ruleSet.addState(s3EdgeState);
		
		final State s4EdgeState = new State(s0EdgeState);		// Energie: 1
		ruleSet.addState(s4EdgeState);
		
		//------------------------------------------------------------------------------------------
		final StateNode r1MapFieldStateNode; 
		{
			// AB active, others inactive
			r1MapFieldStateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                           s1EdgeState, s0EdgeState, s0EdgeState);
		}
		final StateNode r2MapFieldStateNode; 
		{
			// AB active, others inactive
			r2MapFieldStateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                           s2EdgeState, s0EdgeState, s0EdgeState);
		}
		final StateNode r3MapFieldStateNode; 
		{
			// AB active, others inactive
			r3MapFieldStateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                           s3EdgeState, s0EdgeState, s0EdgeState);
		}
		final StateNode r4MapFieldStateNode; 
		{
			// AB active, others inactive
			r4MapFieldStateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                           s4EdgeState, s0EdgeState, s0EdgeState);
		}

		final StateNode g1MapFieldStateNode; 
		{
			// BC active, others inactive
			g1MapFieldStateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                           s0EdgeState, s1EdgeState, s0EdgeState);
		}
		final StateNode g2MapFieldStateNode; 
		{
			// BC active, others inactive
			g2MapFieldStateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                           s0EdgeState, s2EdgeState, s0EdgeState);
		}
		final StateNode g3MapFieldStateNode; 
		{
			// BC active, others inactive
			g3MapFieldStateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                           s0EdgeState, s3EdgeState, s0EdgeState);
		}
		final StateNode g4MapFieldStateNode; 
		{
			// BC active, others inactive
			g4MapFieldStateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                           s0EdgeState, s4EdgeState, s0EdgeState);
		}

		final StateNode b1MapFieldStateNode; 
		{
			// CA active, others inactive
			b1MapFieldStateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                           s0EdgeState, s0EdgeState, s1EdgeState);
		}
		final StateNode b2MapFieldStateNode; 
		{
			// CA active, others inactive
			b2MapFieldStateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                           s0EdgeState, s0EdgeState, s2EdgeState);
		}

		final StateNode g2b2MapFieldStateNode; 
		{
			// BC & CA active, others inactive
			g2b2MapFieldStateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                           s0EdgeState, s2EdgeState, s2EdgeState);
		}
		final StateNode r2b2MapFieldStateNode; 
		{
			// AB & CA active, others inactive
			r2b2MapFieldStateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                           s2EdgeState, s0EdgeState, s2EdgeState);
		}

		final StateNode r2g2MapFieldStateNode; 
		{
			// AB & BC active, others inactive
			r2g2MapFieldStateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                           s2EdgeState, s2EdgeState, s0EdgeState);
		}
		final StateNode r3g3MapFieldStateNode; 
		{
			// AB & BC active, others inactive
			r3g3MapFieldStateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                           s3EdgeState, s3EdgeState, s0EdgeState);
		}
		final StateNode r4g4MapFieldStateNode; 
		{
			// AB & BC active, others inactive
			r4g4MapFieldStateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                           s4EdgeState, s4EdgeState, s0EdgeState);
		}
		//------------------------------------------------------------------------------------------
		// RG: Circle
		
		// B: Richtungsanzeiger:
		{
			// inB1 -> R2G2
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                           s0EdgeState, s0EdgeState, s0EdgeState,
					                           s0EdgeState, s0EdgeState, s1EdgeState);
			
			stateNode.addNextStateNode(r2g2MapFieldStateNode, PropNextStateNode.MAX_probability);
		}
		{
			// R2G2 -> s0
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                           s2EdgeState, s2EdgeState, s0EdgeState,
					                           s0EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.addNextStateNode(s0MapFieldStateNode, PropNextStateNode.MAX_probability);
		}

		{
			// inR2 -> G3
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                           s0EdgeState, s0EdgeState, s0EdgeState,
					                           s2EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.addNextStateNode(g3MapFieldStateNode, PropNextStateNode.MAX_probability);
		}
		{
			// G3 -> s0
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                           s0EdgeState, s3EdgeState, s0EdgeState,
					                           s0EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.addNextStateNode(s0MapFieldStateNode, PropNextStateNode.MAX_probability);
		}
		
		{
			// inG2 -> R3
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                           s0EdgeState, s0EdgeState, s0EdgeState,
					                           s0EdgeState, s2EdgeState, s0EdgeState);
			
			stateNode.addNextStateNode(r3MapFieldStateNode, PropNextStateNode.MAX_probability);
		}
		{
			// R3 -> s0
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                           s3EdgeState, s0EdgeState, s0EdgeState,
					                           s0EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.addNextStateNode(s0MapFieldStateNode, PropNextStateNode.MAX_probability);
		}
		
		{
			// inR3 -> G4
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                           s0EdgeState, s0EdgeState, s0EdgeState,
					                           s3EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.addNextStateNode(g4MapFieldStateNode, PropNextStateNode.MAX_probability);
		}
		{
			// G4 -> s0
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                           s0EdgeState, s4EdgeState, s0EdgeState,
					                           s0EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.addNextStateNode(s0MapFieldStateNode, PropNextStateNode.MAX_probability);
		}
		
		{
			// inG3 -> R4
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                           s0EdgeState, s0EdgeState, s0EdgeState,
					                           s0EdgeState, s3EdgeState, s0EdgeState);
			
			stateNode.addNextStateNode(r4MapFieldStateNode, PropNextStateNode.MAX_probability);
		}
		{
			// R4 -> s0
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                           s4EdgeState, s0EdgeState, s0EdgeState,
					                           s0EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.addNextStateNode(s0MapFieldStateNode, PropNextStateNode.MAX_probability);
		}

		{
			// inR4G4 -> R4G4
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                           s0EdgeState, s0EdgeState, s0EdgeState,
					                           s4EdgeState, s4EdgeState, s0EdgeState);
			
			stateNode.addNextStateNode(r4g4MapFieldStateNode, PropNextStateNode.MAX_probability);
		}
		{
			// R4G4 -> s0
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                           s4EdgeState, s4EdgeState, s0EdgeState,
					                           s0EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.addNextStateNode(s0MapFieldStateNode, PropNextStateNode.MAX_probability);
		}
		
		{
			// R4G4 -> B1
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                           s4EdgeState, s4EdgeState, s0EdgeState,
					                           s0EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.addNextStateNode(b1MapFieldStateNode, PropNextStateNode.MAX_probability);
		}
		{
			// B1 -> s0
			final StateNode stateNode = 
					RulesService.makeStateNode(stateNodes, ruleSet,
					                           s0EdgeState, s0EdgeState, s1EdgeState,
					                           s0EdgeState, s0EdgeState, s0EdgeState);
			
			stateNode.addNextStateNode(s0MapFieldStateNode, PropNextStateNode.MAX_probability);
		}
		
		//------------------------------------------------------------------------------------------
		ruleSet.setInitStateNode(b1MapFieldStateNode);
		
		//==========================================================================================
		return ruleSet;
	}

}
