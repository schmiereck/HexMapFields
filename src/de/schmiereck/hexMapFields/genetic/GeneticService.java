/*
 * Copyright (c) schmiereck.de, 2017
 */
package de.schmiereck.hexMapFields.genetic;

import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.Vector;

import de.schmiereck.hexMapFields.MainService;
import de.schmiereck.hexMapFields.Map;
import de.schmiereck.hexMapFields.MapHolder;
import de.schmiereck.hexMapFields.RuleSet;
import de.schmiereck.hexMapFields.State;
import de.schmiereck.hexMapFields.StateNode;
import de.schmiereck.hexMapFields.StateNodes;

/**
 * <p>
 *	Genetic Service.
 * </p>
 * 
 * @author smk
 * @version <p>13.06.2017:	created, smk</p>
 */
public class GeneticService
{
	//**********************************************************************************************
	// Constants:
	
	private static final long MAX_DIFF_START_RUN_CNT = 	1000L;
	private static final long MAX_DIFF_LAST_RUN_CNT = 	2000L;

	private boolean dbgOnlyCreateNewStateNodes = true;//false;//
	
	//**********************************************************************************************
	// Fields:
	
	/**
	 * Max States per Rule-Set.
	 */
	private int maxStates = 20;
	
	/**
	 * The first Rule-Set (pos = 0) is the special Empty-Rule-Set.
	 */
	private final List<RuleSet> ruleSets = new Vector<>();
	
	private final Stack<Map> backupMaps = new Stack<>();
	
	private final Random random = new Random(2342L);
	
	//**********************************************************************************************
	// Functions:
	
	public GeneticService(final Map map)
	{
		this.backupMaps.push(map);
	}
	
	public void addRuleSet(final RuleSet ruleSet)
	{
		//==========================================================================================
		this.ruleSets.add(ruleSet);
		
		//==========================================================================================
	}
	
	public void calc(final MapHolder mapHolder, final StateNodes stateNodes)
	{
		//==========================================================================================
		final Map map = mapHolder.getMap();
		
		final int energie = map.getEnergie();
		final int nextEnergie = map.getNextEnergie();
		
//		System.out.println("energie \"" + energie + "\" <==> nextEnergie \"" + nextEnergie + "\"");
		
		//------------------------------------------------------------------------------------------
		// 0.	Remove not used Elements (RuleSets, StatedNodes, States, ...).
		// Count using of Elements
		// Remove unused Elements.
		// States, StateNodes, RuleSets

		if (this.rndPercent(75) == true)
		{
			long minUsedCnt = Long.MAX_VALUE;
			int minUsedRuleSetPos = -1;
			
			final long runCnt = MainService.getRunCnt();

			// Inspect only the older half size.
			final int size = this.ruleSets.size() / 2;
			
	//		for (final RuleSet ruleSet : this.ruleSets)
			for (int ruleSetPos = 1; ruleSetPos < size; ruleSetPos++)
			{
				final RuleSet ruleSet = this.ruleSets.get(ruleSetPos);
				
				final long startRunCnt = ruleSet.getStartRunCnt();
				final long lastRunCnt = ruleSet.getLastRunCnt();
				final long diffStartRunCnt = runCnt - startRunCnt;
				final long diffLastRunCnt = runCnt - lastRunCnt;
				
				// RuleSet is not very young and (never used or unused a long time)?
				if ((diffStartRunCnt > MAX_DIFF_START_RUN_CNT) &&
					((lastRunCnt == -1L) ||
					 (diffLastRunCnt > MAX_DIFF_LAST_RUN_CNT)))
				{
					// Check if we should remove it.
					final long usedCnt = ruleSet.getUsedCnt();
					
					if (usedCnt < minUsedCnt)
					{
						minUsedCnt = usedCnt;
						minUsedRuleSetPos = ruleSetPos;
					}
				}
			}
			
			if (minUsedRuleSetPos > 0)
			{
				final RuleSet removedRuleSet = this.ruleSets.remove(minUsedRuleSetPos);
				
				// TODO: Remove also the StatedNodes from StateNodes-DB.
				//stateNodes.remove(removedRuleSet);
			}
		}		
		//------------------------------------------------------------------------------------------
		// 1.	Is Energie is differen from previouse calc?
		if (energie != nextEnergie)
		{
			System.out.println("runCnt:" + MainService.getRunCnt() + ", removeRuleSet: energie \"" + energie + "\" <> nextEnergie \"" + nextEnergie + "\", backupMaps.size:" + this.backupMaps.size() + ", ruleSets.size:" + this.ruleSets.size());
			
//			// 1.1.	Role back Map to previouse Backup.
//			if (this.backupMaps.size() > 0)
//			{
//				final Map backupMap = this.backupMaps.pop();
//				
//				mapHolder.setMap(backupMap);
//			}
			// 1.2.	Remove last created RuleSet.
//			this.ruleSets.remove(this.ruleSets.size() - 1);
		}
		//------------------------------------------------------------------------------------------
		// 2.	Should create a new muteted RuleSet?
		if (this.rndPercent(25) == true)
		{
//			// 2.1.	Backup the current Map to Stack.
//			final Map cloneMap = new Map(map);
//			
//			this.backupMaps.push(cloneMap);
//			
//			mapHolder.setMap(cloneMap);
			
			// 2.2.	Create a new muteted RuleSet (excluding the Empty-RuleSet).
			final int ruleSetPos = this.random.nextInt(this.ruleSets.size() - 1) + 1;
			
			final RuleSet ruleSet = this.ruleSets.get(ruleSetPos);
			
			final RuleSet mutatedRuleSet = this.mutate(stateNodes, ruleSet);
			
			this.addRuleSet(mutatedRuleSet);

			//System.out.println("runCnt:" + MainService.getRunCnt() + ", addRuleSet: ruleSets.size:" + this.ruleSets.size());
		}
		//------------------------------------------------------------------------------------------
		// 3.	Add initial State-Nodes from existing Rule-Sets.
		//		TODO: Ohne das Energie-Gesetz zu verletzen...?!
		
		//==========================================================================================
	}

	public RuleSet mutate(final StateNodes stateNodes, final RuleSet ruleSet)
	{
		//==========================================================================================
		final RuleSet mutatedRuleSet = new RuleSet(ruleSet, MainService.getRunCnt());
		
		final List<State> states = mutatedRuleSet.getStates();

		// 1.	Add randomly new State.
		if ((states.size() < this.maxStates) && (this.rndPercent(5) == true))
		{
			this.mutateState(mutatedRuleSet, states);
		}
		// 2.	Add ramdomly a muteted (Next)StateNode.
//		if (this.rndPercent(25) == true)
		{
			final List<StateNode> stateNodeList = mutatedRuleSet.getStateNodes();
			
			// Parent RuleSet.
			final RuleSet parentRuleSet = ruleSet.getParentRuleSet();
			
			final List<StateNode> parentStateNodes = parentRuleSet.getStateNodes();
			
			// Select only one of the Parent-State-Nodes.
			final int stateNodesPos = this.random.nextInt(stateNodeList.size() - parentStateNodes.size()) + parentStateNodes.size();
		
			final StateNode stateNode = stateNodeList.get(stateNodesPos);
			
			// Use only State Nodes that do not use only Empty-States for inputs.
			
			final StateNode mutatedStateNode = 
					this.mutate(mutatedRuleSet,
					            stateNodes, states, stateNode, mutatedRuleSet);
		}
		//==========================================================================================
		return mutatedRuleSet;
	}

	private void mutateState(final RuleSet mutatedRuleSet, final List<State> states)
	{
		final int statesPos = this.random.nextInt(states.size() - 1) + 1;
		
		final State state = states.get(statesPos);
		
		final State mutateState = this.mutate(state);
		
		mutatedRuleSet.addState(mutateState);
	}

	private StateNode mutate(final RuleSet mutatedRuleSet,
	                         final StateNodes stateNodes, 
	                         final List<State> states, 
	                         final StateNode stateNode, 
	                         final RuleSet ruleSet)
	{
		//==========================================================================================
		StateNode retStateNode = null;
		
		// Es muß ein neuer StateNode erzeugt werden.
		// From Last to Root (without Root-Node).
		List<State> newNodeStates;
		
		while (true)
		{
			// From last (NextStateNode) to Root-StateNode (without Root-Node).
			final List<State> nodeStates = new Vector<>();
			
			StateNode actStateNode = stateNode;
			
			// Not Root-State-Node?
			while (actStateNode.getState() != null)
			{
				final State state;
				
				if (this.rndPercent(5) == true)
				{
					final int statePos = this.random.nextInt(states.size() - 1) + 1;
					
					state = states.get(statePos);
				}
				else
				{
					state = actStateNode.getState();
				}
	
				nodeStates.add(state);
				
				actStateNode = actStateNode.getParentNode();
			}
			
			// Only create new StateNodes
			if (dbgOnlyCreateNewStateNodes == true)
			{
				final StateNode foundStateNode = MainService.searchStateNode(stateNodes, nodeStates);
				
				// StateNode is a new one?
				if (foundStateNode == null)
				{
					// Search are finish.
					newNodeStates = nodeStates;
					break;
				}
				else
				{
					// No new StateNode found, then begin to create new States.
					
					if (this.rndPercent(5) == true)
					{
						this.mutateState(mutatedRuleSet, states);
					}
				}
			}
			else
			{
				newNodeStates = nodeStates;
				break;
			}
		}
		//------------------------------------------------------------------------------------------
		retStateNode = stateNodes.getRootNode();
		
		for (int statePos = newNodeStates.size() - 1; statePos >= 0; statePos--)
		{
			final State state = newNodeStates.get(statePos);
			
			if (state == null)
			{
				throw new RuntimeException("State is null.");
			}
			
			retStateNode = stateNodes.makeNode(retStateNode, state, ruleSet);
		}
		//------------------------------------------------------------------------------------------
		// Mutate NextStateNode:
		
		final StateNode nextStateNode = stateNode.getNextStateNode();
		
		if (nextStateNode != null)
		{
//			if (this.rndPercent(5) == true)
			{
				final StateNode mutatedNextStateNode = 
						this.mutateNextStateNode(mutatedRuleSet,
						                         stateNodes, states, nextStateNode, ruleSet);

				retStateNode.setNextStateNode(mutatedNextStateNode);
			}
//			else
//			{
//				retStateNode.setNextStateNode(nextStateNode);
//			}
		}

		mutatedRuleSet.addStateNode(retStateNode);
		
		//==========================================================================================
		return retStateNode;
	}

	private StateNode mutateNextStateNode(final RuleSet mutatedRuleSet,
	                                      final StateNodes stateNodes, 
	                                      final List<State> states, 
	                                      final StateNode stateNode, 
	                                      final RuleSet ruleSet)
	{
		//==========================================================================================
		StateNode retStateNode = null;
		
		// From the Last (with NextStateNode) to Root (without Root-Node).
		final List<State> nodeStates = new Vector<>();
		
		StateNode actStateNode = stateNode;
		
		// Not Root-State-Node?
		while (actStateNode.getState() != null)
		{
			final State state;
			
			if (this.rndPercent(25) == true)
			{
				final int statePos = this.random.nextInt(states.size() - 1) + 1;
				
				state = states.get(statePos);
			}
			else
			{
				state = actStateNode.getState();
			}

			nodeStates.add(state);
			
			actStateNode = actStateNode.getParentNode();
		}
		//------------------------------------------------------------------------------------------
		final StateNode foundStateNode = MainService.searchStateNode(stateNodes, nodeStates);
		
		// StateNode is a new one?
		if (foundStateNode == null)
		{
			retStateNode = stateNodes.getRootNode();
			
			for (int statePos = nodeStates.size() - 1; statePos >= 0; statePos--)
			{
				final State state = nodeStates.get(statePos);
				
				if (state == null)
				{
					throw new RuntimeException("State is null.");
				}
				
				retStateNode = stateNodes.makeNode(retStateNode, state, ruleSet);
			}
			
			mutatedRuleSet.addStateNode(retStateNode);
		}
		else
		{
			retStateNode = foundStateNode;
		}
		
		//==========================================================================================
		return retStateNode;
	}

	private boolean rndPercent(int percent)
	{
		//==========================================================================================
		final boolean ret;
		
		final int p = this.random.nextInt(101);
		
		if (p <= percent)
		{
			ret = true;
		}
		else
		{
			ret = false;
		}
		//==========================================================================================
		return ret;
	}

	private State mutate(final State state)
	{
		//==========================================================================================
		final State retState = new State(state);
		
		//==========================================================================================
		return retState;
	}

	
	/**
	 * @return 
	 *			the value of attribute {@link #ruleSets}.
	 */
	public List<RuleSet> getRuleSets()
	{
		return this.ruleSets;
	}

}
