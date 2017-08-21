/*
 * Copyright (c) schmiereck.de, 2017
 */
package de.schmiereck.hexMapFields;

import java.util.List;

import de.schmiereck.hexMapFields.genetic.GeneticService;

/**
 * <p>
 *	View Service.
 * </p>
 * 
 * @author thoma
 * @version <p>11.07.2017:	created, thoma</p>
 */
public class ViewService
{
	//**********************************************************************************************
	// Fields:
	
	private final GeneticService geneticService;

	//**********************************************************************************************
	// Functions:
	
	/**
	 * Constructor.
	 *
	 */
	public ViewService(final GeneticService geneticService)
	{
		//==========================================================================================
		this.geneticService = geneticService;

		//==========================================================================================
	}

	public int getRuleSetsSize()
	{
		//==========================================================================================
		final int sizeRuleSets;
		
		if (this.geneticService != null)
		{
			final List<RuleSet> ruleSets = this.geneticService.getRuleSets();
			
			sizeRuleSets = ruleSets.size();
		}
		else
		{
			sizeRuleSets = 0;
		}
		//==========================================================================================
		return sizeRuleSets;
	}

	public long getRunCnt()
	{
		//==========================================================================================
		return MainService.getRunCnt();
	}
}
