package com.xebia.util;

import com.xebia.beans.Herd;
import com.xebia.beans.LabYak;

public class Helper {
	
	public static double calculateMilk(double beginAge, long daysPassed) //beginAge is age in number of days
	{
		double quantity = 0.0;
		double dailyAmount = 0.0;
		double presentAge = beginAge;
		for(int i=0; i<=daysPassed-1; i++)
		{
			presentAge = beginAge + i;
			if(presentAge < Constants.MAX_AGE*100)
			{
				dailyAmount = 50 - (presentAge*0.03);
				quantity += dailyAmount;
			}
		}
		//System.out.println(quantity);
		return quantity;
	}
	
	public static boolean canShave(double beginAge, long i)	//beginAge is age in number of days
	{
		if((beginAge+i) >= 100 && (beginAge+i) < 1000)
			{
				if(i==0)
				{
					//System.out.println("true for day: "+i);
					return true;
				}				
				if((8+(beginAge*0.01)) < i)
				{
					//System.out.println("true for day: "+i);
					return true;
				}
			}
		return false;
	}
	
	public static int calculateSkins(double beginAge, long daysPassed) //beginAge is age in number of days
	{
		int skins = 0;
		for(int i=0; i<=daysPassed-1; i++)
		{
			if(canShave(beginAge, i) == true)
				skins += 1;
		}
		return skins;
	}
	
	public static double calculateMilkStock(Herd herd, long daysElapsed){

		double milk = 0; 
		if(herd != null && herd.getLabYaks().size()>0)
		{
			for(LabYak yak:herd.getLabYaks())
			{
				milk += calculateMilk(yak.getAge()*100, daysElapsed);
			}
		}
		return milk;
	}
	
	public static int calculateSkinsStock(Herd herd, long daysElapsed){

		int skins = 0; 
		if(herd != null && herd.getLabYaks().size()>0)
		{
			for(LabYak yak:herd.getLabYaks())
			{
				skins += Helper.calculateSkins(yak.getAge()*100, daysElapsed);
			}
		}
		return skins;
	}

}
