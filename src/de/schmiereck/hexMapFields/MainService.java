/*
 * Copyright (c) schmiereck.de, 2017
 */
package de.schmiereck.hexMapFields;

import java.util.Collection;
import java.util.List;

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
	// Constants:
	
	/**
	 * 0.0D is working.
	 */
	private static final double PROP_MIN_NEXT = 0.0D;

	//**********************************************************************************************
	// Fields:
	
	private static long fpsView = 1L;
	
	private static boolean asapCalc = false;
	
	private static boolean runCalc = false;
	
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

			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			try
			{
				if (MainService.asapCalc == false)
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
			// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
			if (MainService.runCalc == true)
			{
				calcInStates(map, stateNodes);
				
				// Wegen des Views, dort werden die Inner-States ausgelesen.
				synchronized (map)
				{
					calcInnerStates(map, stateNodes);
				}
	//			calcMetaStates(map, states);
	
				runCnt++;
			}
			//--------------------------------------------------------------------------------------
		}
		//==========================================================================================
	}

	/**
	 * Für jeden Inner-StateNode und jeden der Nachbar Inner-StateNodes 
	 * einen In-StateNode ermitteln und
	 * als diesen als In-StateNode hinzufügen.
	 */
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
				mapField.resetPropInStateNodes();

				final Collection<PropInnerStateNode> probInnerStateNodes = mapField.getPropInnerStateNodes();
				
				if (probInnerStateNodes.size() > 0)
				{
					for (final PropInnerStateNode probInnerStateNode : probInnerStateNodes)
					{
						final State abState = MapFieldUtils.extractABInnerState(probInnerStateNode);
						final State bcState = MapFieldUtils.extractBCInnerState(probInnerStateNode);
						final State caState = MapFieldUtils.extractCAInnerState(probInnerStateNode);
						final long innerProbabilityR = PropNextStateNode.MIN_probability;//probInnerStateNode.getProbability(0);
						final long innerProbabilityG = PropNextStateNode.MIN_probability;//probInnerStateNode.getProbability(1);
						final long innerProbabilityB = PropNextStateNode.MIN_probability;//probInnerStateNode.getProbability(2);
						
						calcInStates(stateNodes, mapField, abState, bcState, caState, 
						             innerProbabilityR, innerProbabilityG, innerProbabilityB);
					}
				}
				else
				{
					final State abState = null;
					final State bcState = null;
					final State caState = null;
					final long innerProbability = PropNextStateNode.MIN_probability;//PropNextStateNode.MAX_probability;

					calcInStates(stateNodes, mapField, abState, bcState, caState, 
					             innerProbability, innerProbability, innerProbability);
				}
				//----------------------------------------------------------------------------------
			}
		}
		//==========================================================================================
	}

	public static void calcInStates(final StateNodes stateNodes, final MapField mapField,
	                                final State abInnerState, final State bcInnerState, final State caInnerState,
	                                final long innerProbabilityR, final long innerProbabilityG, final long innerProbabilityB)
	{
		//==========================================================================================
		final Collection<PropInnerStateNode> abPropInnerStateNodes;
		{
			final MapField abOutField = mapField.getABOutField();
			abPropInnerStateNodes = abOutField.getNotEmptyPropInnerStateNodes();
		}
		for (final PropInnerStateNode abPropInnerStateNode : abPropInnerStateNodes)
		{
			final State abInState;
			{
				final StateNode abInnerStateNode = abPropInnerStateNode.getInnerStateNode();
				final StateNode abInStateNode = abInnerStateNode.getParentNode().getParentNode();
				abInState = abInStateNode.getState();
			}			
			final MapField bcOutField = mapField.getBCOutField();
			final Collection<PropInnerStateNode> bcPropInnerStateNodes = bcOutField.getNotEmptyPropInnerStateNodes();
			
			boolean found = false;
			
			for (final PropInnerStateNode bcPropInnerStateNode : bcPropInnerStateNodes)
			{
				final State bcInState;
				{
					final StateNode bcInnerStateNode = bcPropInnerStateNode.getInnerStateNode();
					final StateNode bcInStateNode = bcInnerStateNode.getParentNode();
					bcInState = bcInStateNode.getState();
				}
				final MapField caOutField = mapField.getCAOutField();
				final Collection<PropInnerStateNode> caPropInnerStateNodes = caOutField.getNotEmptyPropInnerStateNodes();
				
				for (final PropInnerStateNode caPropInnerStateNode : caPropInnerStateNodes)
				{
					final State caInState;
					{
						final StateNode caInnerStateNode = caPropInnerStateNode.getInnerStateNode();
						final StateNode caInStateNode = caInnerStateNode;
						caInState = caInStateNode.getState();
					}
					//------------------------------------------------------------------------------
					final StateNode inStateNode = 
							searchInStateNode(stateNodes,
							                  abInnerState, bcInnerState, caInnerState, 
							                  abInState, bcInState, caInState);
//					if (inStateNode == null) 
//					{
//						System.out.println("XX");
//					}
					if ((inStateNode != null) && (inStateNode != Main.s0InStateNode))
					{
						final long probabilityR;
						final long probabilityG;
						final long probabilityB;
						
						if (abInState != Main.s0EdgeState)
						{
							probabilityR = innerProbabilityR + abPropInnerStateNode.getProbability(0);
						}
						else
						{
							probabilityR = PropNextStateNode.MIN_probability;
						}
						
						if (bcInState != Main.s0EdgeState)
						{
							probabilityG = innerProbabilityG + bcPropInnerStateNode.getProbability(1);
						}
						else
						{
							probabilityG = PropNextStateNode.MIN_probability;
						}
						
						if (caInState != Main.s0EdgeState)
						{
							probabilityB = innerProbabilityB + caPropInnerStateNode.getProbability(2);
						}						
						else
						{
							probabilityB = PropNextStateNode.MIN_probability;
						}
						
//		                if (probabilityR > PropNextStateNode.MIN_probability)
//		                {
//		                	System.out.printf("probabilityR:%.20f\n ",probabilityR);
//		                }
//		                if (probabilityG > PropNextStateNode.MAX_probability / 2L)
//		                {
//		                	System.out.println("probabilityG:"+probabilityG);
//		                }
						final PropInStateNode newPropInStateNode = 
								new PropInStateNode(inStateNode, //PropNextStateNode.MAX_probability);
								                    probabilityR, probabilityG, probabilityB);
								                    
						mapField.addPropInStateNode(newPropInStateNode);
						
						inStateNode.addUsedCnt(runCnt);
						found = true;
						break;
					}
					else
					{
						// Katastrophe!
						// Irgend etwas unvorhergesehenes ist passiert!
					}
					//------------------------------------------------------------------------------
				}
				if (found == true)
				{
					break;
				}
			}
			if (found == true)
			{
				break;
			}
		}
		//==========================================================================================
	}

//	private static double calcProp(final double p0, final double p1,
//	                               final double p2, final double p3)
//	{
//		//==========================================================================================
////		final double retProp = Math.min(p0, Math.min(p1, Math.min(p2, p3)));
////		final double retProp = (p0 * p1 * p2 * p3) / (PropNextStateNode.MAX_probability * PropNextStateNode.MAX_probability * PropNextStateNode.MAX_probability);
//		final double retProp = (p0 + p1 + p2 + p3);
//		
//		//==========================================================================================
//		return retProp;
//	}

	/** 
	 * Aus dem In-StateNodes
	 * die nächsten Inner-StateNodes auslesen und setzen.
	 */
	private static void calcInnerStates(final Map map, 
	                                    final StateNodes stateNodes)
	{
		//==========================================================================================
		final int xSize = map.getXSize();
		final int ySize = map.getYSize();
		
		for (int yPos = 0; yPos < ySize; yPos++)
		{
			for (int xPos = 0; xPos < xSize; xPos++)
			{
				//----------------------------------------------------------------------------------
				final MapField mapField = map.getMapField(xPos, yPos);
			
				// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
				mapField.resetPropInnerStateNodes();
				
				final List<PropInStateNode> probInStateNodes = mapField.getPropInStateNodes();
				
				for (final PropInStateNode probInStateNode : probInStateNodes)
				{
					final StateNode inStateNode = probInStateNode.getInStateNode();
					final long inStateNodeProbabilityR = probInStateNode.getProbability(0);
					final long inStateNodeProbabilityG = probInStateNode.getProbability(1);
					final long inStateNodeProbabilityB = probInStateNode.getProbability(2);
					
					if (inStateNode != null)
					{
						final List<PropNextStateNode> inNextStateNodes = inStateNode.getNextStateNodes();
						
						if (inNextStateNodes != null)
						{
							for (final PropNextStateNode inNextStateNode : inNextStateNodes)
							{
								final StateNode nextInStateNode = inNextStateNode.getNextStateNode();
								final long nextInStateNodeProbability = inNextStateNode.getProbability();
								
								if (nextInStateNode != null)
								{
//									final long probability = 
//										(nextInStateNodeProbability);
//										(inStateNodeProbability * nextInStateNodeProbability);
									final long probabilityR = 
											(inStateNodeProbabilityR * nextInStateNodeProbability) / PropNextStateNode.MAX_probability;
									final long probabilityG = 
											(inStateNodeProbabilityG * nextInStateNodeProbability) / PropNextStateNode.MAX_probability;
									final long probabilityB = 
											(inStateNodeProbabilityB * nextInStateNodeProbability) / PropNextStateNode.MAX_probability;
//										(inStateNodeProbability + nextInStateNodeProbability);
					                
									// Nur Probs von In-States von nicht Empty-States berücksichtigen!
									final long prob = ((probabilityR + probabilityG + probabilityB));
									
									//Und das jetzt auf die nicht Empty Inner-States verteilen?
									// Nur Probs setzen, deren Inner-State nicht Empty ist.
									final long probR;
									final long probG;
									final long probB;
									
									//						In-B			In-G			In-R			Inner-B			Inner-G			Inner-R
//									final State inRState = nextInStateNode.getParentNode().getParentNode().getParentNode().getParentNode().getParentNode().getState();
//									final State inGState = nextInStateNode.getParentNode().getParentNode().getParentNode().getParentNode().getState();
//									final State inBState = nextInStateNode.getParentNode().getParentNode().getParentNode().getState();
									//								Inner-B			Inner-G			Inner-R
									final State nextInnerRState = nextInStateNode.getParentNode().getParentNode().getState();
									final State nextInnerGState = nextInStateNode.getParentNode().getState();
									final State nextInnerBState = nextInStateNode.getState();
									
//									probR = prob;
//									probG = prob;
//									probB = prob;
//									probR = probabilityR;
//									probG = probabilityG;
//									probB = probabilityB;
									if (nextInnerRState != Main.s0EdgeState)
									{
										probR = prob;//probabilityR;
									}
									else
									{
										probR = PropNextStateNode.MIN_probability;
									}
										
									if (nextInnerGState != Main.s0EdgeState)
									{
										probG = prob;//probabilityG;
									}
									else
									{
										probG = PropNextStateNode.MIN_probability;
									}
									
									if (nextInnerBState != Main.s0EdgeState)
									{
										probB = prob;//probabilityB;
									}
									else
									{
										probB = PropNextStateNode.MIN_probability;
									}
										
//					                if (prob < 0L)
//					                {
//					                	System.out.printf("prob:%.20f\n ",prob);
////					                	System.out.println("probability:"+prob);
//					                }
					                if (prob > PROP_MIN_NEXT)
					                {
										mapField.addPropInnerStateNode(nextInStateNode,
										                               probR, probG, probB);
										                               //inStateNodeProbabilityR, inStateNodeProbabilityG, inStateNodeProbabilityB);
										
										nextInStateNode.addUsedCnt(runCnt);
					                }
								}
	//							else
	//							{
	//								throw new RuntimeException("No Next-State-Node found for this In-State-Node:\n" + inStateNodeToString(inStateNode));
	//							}
							}
						}
					}
					else
					{
						throw new RuntimeException("No In-State-Node found.");
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
			// Nicht final, wird in der Schleife gesetzt.
			StateNode parentStateNode = null;
			
//			NUR wenn Parent-States berücksichtigt werden sollen:
//			// Nicht final, wird in der Schleife durchlaufen.
//			State parentState = state;
//			
//			while (parentStateNode == null)
//			{
//				if (parentState != null)
//				{
//					parentState = parentState.getParentState();
//					
//					if (parentState != null)
//					{
//						parentStateNode = 
//								searchInStateNodeRecursive(stateNodes, 
//								                           stateNode, 
//								                           stateArr, stateArrPos, 
//								                           parentState);
//						
////						if ((parentStateNode != null) && (parentStateNode.getState() == null))
////						{
////							throw new RuntimeException("parentStateNode State is null.");
////						}
//					}
//					else
//					{
//						break;
//					}
//				}
//				else
//				{
//					parentStateNode = null;
//					break;
//				}
//			}
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
	 * 			from last (PropNextStateNode) to Root-StateNode (without Root-Node).
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

	public static void switchStopCalc()
	{
		//==========================================================================================
		MainService.runCalc = !MainService.runCalc;
		
		//==========================================================================================
		
	}

	/**
	 * @return 
	 *			the value of attribute {@link #runCalc}.
	 */
	public static boolean getRunCalc()
	{
		return runCalc;
	}

}
