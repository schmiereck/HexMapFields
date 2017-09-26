/*
 * Copyright (c) schmiereck.de, 2017
 */
package de.schmiereck.hexMapFields;

import de.schmiereck.hexMapFields.fields.Field;
import de.schmiereck.hexMapFields.rules.MakeBlinkerRules;
import de.schmiereck.hexMapFields.rules.MakeCircleBig1StaticRules;
import de.schmiereck.hexMapFields.rules.MakeCircleDirRules;
import de.schmiereck.hexMapFields.rules.MakeCircleStaticRules;
import de.schmiereck.hexMapFields.rules.MakeDirFieldRules;
import de.schmiereck.hexMapFields.rules.MakeRunnerRules;
import de.schmiereck.hexMapFields.rules.MakeStaticRules;
import de.schmiereck.hexMapFields.rules.RulesService;

/**
 * <p>
 *	Main.
 * </p>
 * 
 * @author smk
 * @version <p>08.05.2017:	created, smk</p>
 */
public class Main
{
	//**********************************************************************************************
	// Constants:
	
	public static boolean useGenetic = false;
	
	//----------------------------------------------------------------------------------------------
	// States können kombiniert werden und zu neuen States zusammengesetzt werden
	// dies pro Seite gesetzt werden können.
	
	// Für verscheidene Felder verschiendene States anlegen.
	
	public static final State emptyState = null;//new State();				// Empty-State (Energie 0), Vacuum
	
	public static final State s0EdgeState = null;//new State(emptyState);	// inactive (Energie 1)
	public static final State s1EdgeState = null;//new State(s0EdgeState);	// active (Energie 2)

//	public static final State e0EdgeState = null;//new State(emptyState);	// inactive (Energie 1)
	public static final State e1EdgeState = null;//new State(emptyState);	// active (Energie 1)

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	private static final StateNodes stateNodes = new StateNodes();

	//------------------------------------------------------------------------------------------
	// Jedes MapField hat drei verschiedene Seiten in den "Farben".
	// Die Orientierung usw. sind für den Zustand unwichtig,
	// die Nachbar-MapFields haben immer an der jeweiligen Nachbarseite die gleiche "Farbe".
	private static final RuleSet emptyRuleSet = new RuleSet(MainService.getRunCnt());
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	static
	{
//		emptyRuleSet.addState(Main.emptyState);
		//emptyRuleSet.addState(Main.s0EdgeState);
		//emptyRuleSet.addState(Main.s1EdgeState);
		//emptyRuleSet.addState(Main.e1EdgeState);
	}
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//	private static final StateNode emptyMapFieldStateNode; 
//	{
//		// All empty
//		emptyMapFieldStateNode = 
//				MainService.makeStateNode(stateNodes, emptyRuleSet,
//				                          emptyState, emptyState, emptyState);
//	}
	public static final StateNode s0MapFieldStateNode; 
	static
	{
		// All inactive
		s0MapFieldStateNode = 
				RulesService.makeStateNode(stateNodes, emptyRuleSet,
				                           s0EdgeState, s0EdgeState, s0EdgeState);
	}
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
//	// In-States:
//	private static final StateNode emptyInStateNode;
//	{
//		// In-State: All empty
//		emptyInStateNode = 
//				MainService.makeStateNode(stateNodes, emptyRuleSet,
//				                          Main.emptyState, Main.emptyState, Main.emptyState,
//				                          Main.emptyState, Main.emptyState, Main.emptyState);
//		
//		emptyInStateNode.setNextStateNode(emptyMapFieldStateNode);
//	}
	public static final StateNode s0InStateNode;
	static
	{
		// In-State: All inactive
		s0InStateNode = 
				RulesService.makeStateNode(stateNodes, emptyRuleSet,
				                           Main.s0EdgeState, Main.s0EdgeState, Main.s0EdgeState,
				                           Main.s0EdgeState, Main.s0EdgeState, Main.s0EdgeState);
		
		s0InStateNode.addNextStateNode(s0MapFieldStateNode, PropNextStateNode.MAX_probability, "s0InStateNode");
	}
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	static
	{
		emptyRuleSet.setInitStateNode(s0MapFieldStateNode);
	}
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	public static final Field emptyField = new Field();
	
	//**********************************************************************************************
	// Functions:
	
	/**
	 * @param args
	 * 			are the command line arguments.
	 */
	public static void main(final String[] args)
	{
		//==========================================================================================
		// Farbladung:
		final RuleSet staticRuleSet = MakeStaticRules.makeRules(stateNodes, emptyRuleSet, s0MapFieldStateNode);
		final RuleSet blinkerRuleSet = MakeBlinkerRules.makeRules(stateNodes, emptyRuleSet, s0MapFieldStateNode);
		final RuleSet runnerRuleSet = MakeRunnerRules.makeRules(stateNodes, emptyRuleSet, s0MapFieldStateNode);
		final RuleSet circleStaticRuleSet = MakeCircleStaticRules.makeRules(stateNodes, emptyRuleSet, s0MapFieldStateNode);
		final RuleSet circleStaticBig1RuleSet = MakeCircleBig1StaticRules.makeRules(stateNodes, emptyRuleSet, s0MapFieldStateNode);
		
		// Elektrische Ladung:
		final RuleSet circleDirRuleSet = MakeCircleDirRules.makeRules(stateNodes, emptyRuleSet, s0MapFieldStateNode);
		final RuleSet dirFieldRuleSet = MakeDirFieldRules.makeRules(stateNodes, emptyRuleSet, s0MapFieldStateNode);
		
		//------------------------------------------------------------------------------------------
		final int xSizeView = 1024 * 2;
		final int ySizeView = 920 * 2;
		
		final int xRepeatMapFields = 1;
		final int yRepeatMapFields = 1;
		
		// minimale Metamap garantieren:
		final int xMetamap = xRepeatMapFields * 2*2*2*2;	// 16
		final int yMetamap = yRepeatMapFields * 3*2*2*2;	// 24
		
		// minimale Sechsecke garantieren:
		final int xHexagon = xMetamap * 6;
		final int yHexagon = yMetamap * 2;
		
		final Map map = new Map(xHexagon/8, yHexagon/8);	// 16*6=96 (12), 24*2=48 (6)
		
		//------------------------------------------------------------------------------------------
		final int xSize = map.getXSize();
		final int ySize = map.getYSize();
		
		for (int yPos = 0; yPos < ySize; yPos++)
		{
			for (int xPos = 0; xPos < xSize; xPos++)
			{
				final MapField mapField;// = map.getMapField(xPos, yPos);
				
				final StateNode stateNode;
				final Field field;
				
//				if ((xPos == 0) && (yPos == 0))
				if ((xPos == 6) && (yPos == 2))
				{
//					stateNode = emptyRuleSet.getInitStateNode();
					field = emptyField;
//					stateNode = staticRuleSet.getInitStateNode();
//					stateNode = blinkerRuleSet.getInitStateNode();
					stateNode = circleStaticRuleSet.getInitStateNode();
//					stateNode = circleStaticBig1RuleSet.getInitStateNode();
//					stateNode = circleDirRuleSet.getInitStateNode();
//					stateNode = dirFieldRuleSet.getInitStateNode();
//					stateNode = runnerRuleSet.getInitStateNode();
//					field = new Field();		// A inital new Field is born.
				}
				else
				{
					if ((xPos == 7) && (yPos == 3))
					{
						stateNode = null;//emptyRuleSet.getInitStateNode();
						field = null;//emptyField;
//						stateNode = blinkerRuleSet.getInitStateNode();
//						stateNode = circleStaticRuleSet.getInitStateNode();
//						stateNode = circleDirRuleSet.getInitStateNode();		// Error.
//						stateNode = dirFieldRuleSet.getInitStateNode();
//						stateNode = runnerRuleSet.getInitStateNode();
					}
					else
					{
						stateNode = null;//emptyRuleSet.getInitStateNode();
						field = null;//emptyField;
					}
				}

				if (stateNode != null)
				{
					mapField = map.getMapField(xPos, yPos);
	
					final double probability = PropNextStateNode.MAX_probability;
					
					mapField.addPropInnerStateNode(new PropInnerStateNode(stateNode, probability));
					mapField.setField(field);
				}
			}
		}
		//------------------------------------------------------------------------------------------
		final ViewService viewService = new ViewService();
		
		final MainView mainView = new MainView(map, xSizeView, ySizeView,
		                                       viewService);
		
		mainView.showView();
		
        //------------------------------------------------------------------------------------------
		MainService.calc(map, 
		                 mainView,
		                 stateNodes);
		
		//==========================================================================================
	}
}
