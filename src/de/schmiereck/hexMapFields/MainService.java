/*
 * Copyright (c) schmiereck.de, 2017
 */
package de.schmiereck.hexMapFields;

import java.util.List;
import java.util.Vector;

/**
 * <p>
 *	Main Service.
 * </p>
 * 
 * @author smk
 * @version <p>08.05.2017:	created, smk</p>
 */
public class MainService
{
	//**********************************************************************************************
	// Fields:
	
	private static long fpsView = 4L;
	
	private static boolean asapCalc = false;
	
	private static long runCnt = 0L;
	
	//**********************************************************************************************
	// Functions:
	
	public static void calc(final Map map, 
	                        final MainView mainView, 
	                        final StateNodes stateNodes)
	{
		//==========================================================================================
		final MapHolder mapHolder = new MapHolder();
		
		mapHolder.setMap(map);
		
		while (true)
//		for (int cnt = 0; cnt < 9000; cnt++)
		{
			//--------------------------------------------------------------------------------------
			mainView.repaint();
			
			try
			{
				if (asapCalc == false)
				{
					Thread.sleep(1024L / MainService.fpsView);
				}
				else
				{
					Thread.sleep(0L, 1000);
					//Thread.yield();
				}
			}
			catch (InterruptedException ex)
			{
				throw new RuntimeException(ex);
			}

			calcInStates(map, stateNodes);
			
			calcNextStates(map, stateNodes);

//			calcMetaStates(map, states);

			runCnt++;
			
			//--------------------------------------------------------------------------------------
		}
		//==========================================================================================
	}

	private static void calcInStates(final Map map, 
	                                final StateNodes stateNodes)
	{
		//==========================================================================================
		// Not final, calculating in the loops.
		final int xSize = map.getXSize();
		final int ySize = map.getYSize();
		
		for (int yPos = 0; yPos < ySize; yPos++)
		{
			for (int xPos = 0; xPos < xSize; xPos++)
			{
				//----------------------------------------------------------------------------------
				final MapField mapField = map.getMapField(xPos, yPos);

				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				final List<NextStateNode> nextStateNodes = mapField.getNextStateNodes();
				hier kommen keine nodes zurück,
				muss auch über so eine leere Dummmy-liste gelöst werden
				
				if (nextStateNodes != null)
				{
					for (NextStateNode nextStateNode : nextStateNodes)
					{
						final State caState;
						final State bcState;
						final State abState;
						
	//				final StateNode caStateNode = mapField.getStateNode();
	//				caState = caStateNode.getState();
	//				
	//				final StateNode bcStateNode = caStateNode.getParentNode();
	//				bcState = bcStateNode.getState();
	//				
	//				final StateNode abStateNode = bcStateNode.getParentNode();
	//				abState = abStateNode.getState();
						
						final StateNode caStateNode = nextStateNode.getStateNode();
						if (caStateNode != null)
						{
							caState = caStateNode.getState();
							
							final StateNode bcStateNode = caStateNode.getParentNode();
							bcState = bcStateNode.getState();
							
							final StateNode abStateNode = bcStateNode.getParentNode();
							abState = abStateNode.getState();
						}
						else
						{
							caState = null;
							bcState = null;
							abState = null;
						}
						// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
						final List<StateNode> abInStateNodes = MapFieldUtils.extractABInStateNodes(mapField);
	
						final List<StateNode> bcInStateNodes = MapFieldUtils.extractBCInStateNodes(mapField);
						
						final List<StateNode> caInStateNodes = MapFieldUtils.extractCAInStateNodes(mapField);
						
						for (final StateNode abInStateNode : abInStateNodes)
						{
							final State abInState = abInStateNode.getState();
							
							for (final StateNode bcInStateNode : bcInStateNodes)
							{
								final State bcInState = bcInStateNode.getState();
								
								for (final StateNode caInStateNode : caInStateNodes)
								{
									final State caInState = caInStateNode.getState();
									
									// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
									final StateNode inStateNode = 
											searchInStateNode(stateNodes,
											                  abState, bcState, caState, 
											                  abInState, bcInState, caInState);
									
									if (inStateNode != null)
									{
										final StateNode oldInStateNode = nextStateNode.getInStateNode();
										
										// Nur hochzählen, wenn sich der In State auch ändert.
										if (oldInStateNode != inStateNode)
										{
											nextStateNode.setInStateNode(inStateNode);
											inStateNode.addUsedCnt(runCnt);
										}
									}
								}
							}
						}
					}
				}
				//----------------------------------------------------------------------------------
			}
		}
		//==========================================================================================
	}

	public static StateNode
	searchInStateNode(final StateNodes stateNodes,
	                  final State abState, final State bcState, final State caState,
	                  final State abInState, final State bcInState, final State caInState)
	{
		//==========================================================================================
		final StateNode retInStateNode;
		
		// Suche in einem Binärbaum.

		final StateNode rootNode = stateNodes.getRootNode();
		
		final State[] stateArr = new State[6];
		
		stateArr[0] = abState;
		stateArr[1] = bcState;
		stateArr[2] = caState;
		stateArr[3] = abInState;
		stateArr[4] = bcInState;
		stateArr[5] = caInState;
		
//		dbgCheckArrNullState(stateArr);
		
		final StateNode stateNode = searchInStateNodeRecursive(stateNodes, rootNode, stateArr, 0);
		
//		//------------------------------------------------------------------------------------------
//		final StateNode abStateNode = states.searchNode(rootNode, abState);
//		
//		final StateNode bcStateNode = states.searchNode(abStateNode, bcState);
//		
//		final StateNode caStateNode = states.searchNode(bcStateNode, caState);
//		
//		//------------------------------------------------------------------------------------------
//		final StateNode abInStateNode = states.searchNode(caStateNode, abInState);
//		
//		final StateNode bcInStateNode = states.searchNode(abInStateNode, bcInState);
//		
//		final StateNode caInStateNode = states.searchNode(bcInStateNode, caInState);
//		
//		final StateNode stateNode = caInStateNode;
		//------------------------------------------------------------------------------------------
//		final State localState = stateNode.getState();
		
		if (stateNode != null)
		{
			retInStateNode = stateNode;
		}
		else
		{
//			throw new RuntimeException("No In-State-Node found.");
			retInStateNode = null;
		}
		//==========================================================================================
		return retInStateNode;
	}

	private static StateNode 
	searchInStateNodeRecursive(final StateNodes stateNodes, 
	                           final StateNode stateNode, 
	                           final State[] stateArr, final int stateArrPos)
	{
		//==========================================================================================
		final StateNode retStateNode;
		
		final State state = stateArr[stateArrPos];
		
		final StateNode foundStateNode = 
				searchInStateNodeRecursive(stateNodes, 
				                           stateNode, 
				                           stateArr, stateArrPos, 
				                           state);

		if (foundStateNode != null)
		{
			retStateNode = foundStateNode;
		}
		else
		{
			// Nicht final, wird in der Schleife durchlaufen.
			State parentState = state;
			// Nicht final, wird in der Schleife gesetzt.
			StateNode parentStateNode = null;

			while (parentStateNode == null)
			{
				parentState = parentState.getParentState();
				
				if (parentState != null)
				{
					parentStateNode = 
							searchInStateNodeRecursive(stateNodes, 
							                           stateNode, 
							                           stateArr, stateArrPos, 
							                           parentState);
					
					if ((parentStateNode != null) && (parentStateNode.getState() == null))
					{
						throw new RuntimeException("parentStateNode State is null.");
					}
				}
				else
				{
					break;
				}
			}
			retStateNode = parentStateNode;
		}
		//==========================================================================================
		return retStateNode;
	}

	private static StateNode 
	searchInStateNodeRecursive(final StateNodes stateNodes, 
	                           final StateNode stateNode, 
	                           final State[] stateArr, final int stateArrPos,
	                           final State state)
	{
		//==========================================================================================
		final StateNode retStateNode;
		
		final StateNode foundStateNode = stateNodes.searchNode(stateNode, state);
		
		if (foundStateNode != null)
		{
			final int nextStateArrPos = stateArrPos + 1;
			
			if (nextStateArrPos < stateArr.length)
			{
				retStateNode  = 
						searchInStateNodeRecursive(stateNodes, 
						                           foundStateNode, 
						                           stateArr, nextStateArrPos);
			}
			else
			{
				retStateNode = foundStateNode;
			}
		}
		else
		{
			retStateNode = null;
		}
		//==========================================================================================
		return retStateNode;
	}

	private static void calcNextStates(final Map map, 
	                                  final StateNodes stateNodes)
	{
		//==========================================================================================
		final int xSize = map.getXSize();
		final int ySize = map.getYSize();
		
		for (int yPos = 0; yPos < ySize; yPos++)
		{
			for (int xPos = 0; xPos < xSize; xPos++)
			{
				final MapField mapField = map.getMapField(xPos, yPos);
			
				List<NextStateNode> newNextStateNodes = null;
				final List<NextStateNode> nextStateNodes = mapField.getNextStateNodes();
				
				if (nextStateNodes != null)
				{
					for (final NextStateNode nextStateNode : nextStateNodes)
					{
						final StateNode inStateNode = nextStateNode.getInStateNode();
						final long inStateNodeProbability = nextStateNode.getProbability();
						
						if (inStateNode != null)
						{
							final List<NextStateNode> inNextStateNodes = inStateNode.getNextStateNodes();
							
							for (final NextStateNode inNextStateNode : inNextStateNodes)
							{
								final StateNode nextInStateNode = inNextStateNode.getInStateNode();
								final long nextInStateNodeProbability = inNextStateNode.getProbability();
								
								if (nextInStateNode != null)
								{
									final StateNode oldStateNode = nextStateNode.getStateNode();
									
									dbgCheckFirst3NullState(nextInStateNode);
									
									// Nur hochzählen, wenn sich der State auch ändert.
									if (oldStateNode != nextInStateNode)
									{
										final NextStateNode newNextStateNode =
												new NextStateNode(nextInStateNode, 
												                  (inStateNodeProbability +
												                   nextInStateNodeProbability) / 2L);
										
										if (newNextStateNodes == null)
										{
											newNextStateNodes = new Vector<>();
										}
										
										newNextStateNodes.add(newNextStateNode);
										nextInStateNode.addUsedCnt(runCnt);
									}
								}
	//							else
	//							{
	//								throw new RuntimeException("No Next-State-Node found for this In-State-Node:\n" + inStateNodeToString(inStateNode));
	//							}
							}
						}
						else
						{
							throw new RuntimeException("No In-State-Node found.");
						}
					}
				}

				mapField.setNextStateNodes(newNextStateNodes);
			}
		}
		//==========================================================================================
	}

	public static void dbgCheckFirst3NullState(final StateNode nextStateNode)
	{
		//==========================================================================================
		StateNode stateNode = nextStateNode;
		
		for (int pos = 0; pos < 3; pos++)
		{
			State state = stateNode.getState();
			if (state == null)
			{
				throw new RuntimeException("First3: State is null.");
			}
			stateNode = stateNode.getParentNode();
		}
		//==========================================================================================
	}

	private static void dbgCheckArrNullState(State[] stateArr)
	{
		//==========================================================================================
		for (int pos = 0; pos < stateArr.length; pos++)
		{
			State state = stateArr[pos];
			if (state == null)
			{
				throw new RuntimeException("StateArr: State is null.");
			}
		}
		//==========================================================================================
		
	}

	private static String inStateNodeToString(final StateNode inStateNode)
	{
		//==========================================================================================
		final String retStr;
		
		final StateNode caInStateNode = inStateNode;
		final State caInState = caInStateNode.getState();
		
		final StateNode bcInStateNode = caInStateNode.getParentNode();
		final State bcInState = bcInStateNode.getState();
		
		final StateNode abInStateNode = bcInStateNode.getParentNode();
		final State abInState = abInStateNode.getState();
		
		
		final StateNode caStateNode = abInStateNode.getParentNode();
		final State caState = caStateNode.getState();

		final StateNode bcStateNode = caStateNode.getParentNode();
		final State bcState = bcStateNode.getState();
		
		final StateNode abStateNode = bcStateNode.getParentNode();
		final State abState = abStateNode.getState();
		
		retStr = inStateNodeToString(caState, bcState, abState, 
		                             caInState, bcInState, abInState);
		
		//==========================================================================================
		return retStr;
	}

	private static String inStateNodeToString(final State caState, final State bcState, final State abState,
	                                          final State caInState, final State bcInState, final State abInState)
	{
		//==========================================================================================
		final String retStr;

		retStr = 
			"AB:" + (abState.toString()) + "-" + (abInState.toString()) + " " +
			"BC:" + (bcState.toString()) + "-" + (bcInState.toString()) + " " +
			"CA:" + (caState.toString()) + "-" + (caInState.toString()) 
				;
		//==========================================================================================
		return retStr;
	}

	private static String stateNodeToString(final State caState, final State bcState, final State abState)
	{
		//==========================================================================================
		final String retStr;

//		retStr = 
//				"AB:" + (abState == Main.s1EdgeState ? "1" : "0") + " " +
//				"BC:" + (bcState == Main.s1EdgeState ? "1" : "0") + " " +
//				"CA:" + (caState == Main.s1EdgeState ? "1" : "0") 
//					;
		retStr = 
			"AB:" + (abState.toString()) + " " +
			"BC:" + (bcState.toString()) + " " +
			"CA:" + (caState.toString()) 
				;
		//==========================================================================================
		return retStr;
	}

	public static StateNode
	searchNextStateNode(final StateNodes stateNodes,
	                    final State abState, final State bcState, final State caState,
	                    final State abInState, final State bcInState, final State caInState)
	{
		//==========================================================================================
		final StateNode retNextStateNode;
		
		// Suche in einem Binärbaum.
		// TODO Including Orientation?

		final StateNode rootNode = stateNodes.getRootNode();
		
		//------------------------------------------------------------------------------------------
		final StateNode abStateNode = stateNodes.searchNode(rootNode, abState);
		
		final StateNode bcStateNode = stateNodes.searchNode(abStateNode, bcState);
		
		final StateNode caStateNode = stateNodes.searchNode(bcStateNode, caState);
		
		//------------------------------------------------------------------------------------------
		final StateNode abInStateNode = stateNodes.searchNode(caStateNode, abInState);
		
		final StateNode bcInStateNode = stateNodes.searchNode(abInStateNode, bcInState);
		
		final StateNode caInStateNode = stateNodes.searchNode(bcInStateNode, caInState);
		
		//------------------------------------------------------------------------------------------
//		final State localNextState = caStateNode.getState();
//		
//		if (localNextState == null)
//		{
//			// TODO Calc next State.
//			final State newNextState = abState;
//			
//			caStateNode.setState(newNextState);
//			
//			retNextStateNode = caInStateNode;
//		}
//		else
//		{
			retNextStateNode = caInStateNode;
//		}
		//==========================================================================================
		return retNextStateNode;
	}

	public static StateNode
	searchStateNode(final StateNodes stateNodes,
	                final State abState, final State bcState, final State caState)
	{
		//==========================================================================================
		final StateNode retStateNode;
		
		// Suche in einem Binärbaum.
		final StateNode rootStateNode = stateNodes.getRootNode();
		
		//------------------------------------------------------------------------------------------
		final StateNode abStateNode = stateNodes.searchNode(rootStateNode, abState);
		
		if (abStateNode == null)
		{
			throw new RuntimeException("No State-Node found for AB:\n" + stateNodeToString(caState, bcState, abState));
		}

		final StateNode bcStateNode = stateNodes.searchNode(abStateNode, bcState);
		
		if (bcStateNode == null)
		{
			throw new RuntimeException("No State-Node found for BC:\n" + stateNodeToString(caState, bcState, abState));
		}
		
		final StateNode caStateNode = stateNodes.searchNode(bcStateNode, caState);

		if (caStateNode == null)
		{
			throw new RuntimeException("No State-Node found for CA:\n" + stateNodeToString(caState, bcState, abState));
		}
			
		//------------------------------------------------------------------------------------------
		retStateNode = caStateNode;
		
		//==========================================================================================
		return retStateNode;
	}

	/**
	 * @param nodeStates
	 * 			from last (NextStateNode) to Root-StateNode (without Root-Node).
	 * @return
	 * 			<code>null</code> if no State-Node found.
	 */
	public static StateNode searchStateNode(final StateNodes stateNodes, final List<State> nodeStates)
	{
		//==========================================================================================
		StateNode retStateNode;
		
		// Suche in einem Binärbaum.
		final StateNode rootStateNode = stateNodes.getRootNode();
		
		retStateNode = rootStateNode;
		
		//------------------------------------------------------------------------------------------
		for (int pos = nodeStates.size() - 1; pos >= 0; pos--)
		{
			final State searchedNodeState = nodeStates.get(pos);
			
			final StateNode foundStateNode = stateNodes.searchNode(retStateNode, searchedNodeState);
			
			if (foundStateNode == null)
			{
				retStateNode = null;
				break;
			}
			
			retStateNode = foundStateNode;
		}
		
		if (retStateNode != null)
			MainService.dbgCheckFirst3NullState(retStateNode);

		//==========================================================================================
		return retStateNode;
	}

	public static StateNode
	searchStateNode(final StateNodes stateNodes,
                    final State abState, final State bcState, final State caState,
                    final State abInState, final State bcInState, final State caInState)
	{
		//==========================================================================================
		final StateNode retStateNode;
		
		// Suche in einem Binärbaum.
		final StateNode rootNode = stateNodes.getRootNode();
		
		//------------------------------------------------------------------------------------------
		final StateNode abStateNode = stateNodes.searchNode(rootNode, abState);
		
		final StateNode bcStateNode = stateNodes.searchNode(abStateNode, bcState);
		
		final StateNode caStateNode = stateNodes.searchNode(bcStateNode, caState);
		
		//------------------------------------------------------------------------------------------
		final StateNode abInStateNode = stateNodes.searchNode(caStateNode, abInState);
		
		final StateNode bcInStateNode = stateNodes.searchNode(abInStateNode, bcInState);
		
		final StateNode caInStateNode = stateNodes.searchNode(bcInStateNode, caInState);
		
		//------------------------------------------------------------------------------------------
		retStateNode = caInStateNode;

		//==========================================================================================
		return retStateNode;
	}

	/**
	 * @return 
	 *			the value of attribute {@link #fpsView}.
	 */
	public static long getFpsView()
	{
		return MainService.fpsView;
	}
	
	/**
	 * @param fpsView 
	 * 			used to set the value of attribute {@link #fpsView}.
	 */
	public static void setFpsView(long fpsView)
	{
		MainService.fpsView = fpsView;
	}
	
	/**
	 * @return 
	 *			the value of attribute {@link #asapCalc}.
	 */
	public static boolean getAsapCalc()
	{
		return MainService.asapCalc;
	}
	
	/**
	 * @param asapCalc 
	 * 			used to set the value of attribute {@link #asapCalc}.
	 */
	public static void setAsapCalc(boolean asapCalc)
	{
		MainService.asapCalc = asapCalc;
	}
	
	/**
	 * @return 
	 *			the value of attribute {@link #runCnt}.
	 */
	public static long getRunCnt()
	{
		return MainService.runCnt;
	}

}
