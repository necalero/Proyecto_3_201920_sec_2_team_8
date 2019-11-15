package model.data_structures;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class TravelTime implements Comparable<TravelTime>
{
	//--------------------------------------
	// Atributos
	//--------------------------------------
	private  int trimestre;
	private  int sourceid;
	private int distid;
	private int hod;
	private double mean_travel_time;
	private double standard_deviation_travel_time;
	

	//----------------------------------------
	//constructor 
	//----------------------------------------

	public TravelTime()
	{
		double valorAleatorio = Math.random();
	    trimestre = (int) (1+(valorAleatorio*2));
	    valorAleatorio = Math.random();
	    sourceid = (int) (valorAleatorio*5);
	    valorAleatorio = Math.random();
	    distid = (int) (valorAleatorio*5);
	    valorAleatorio = Math.random();
	    hod = (int) (valorAleatorio*24);
	    valorAleatorio = Math.random();
	    
	    mean_travel_time = (valorAleatorio*5000);
	    standard_deviation_travel_time = (valorAleatorio+1);
	    
	    DecimalFormat df = new DecimalFormat("#.##");
	    df.setRoundingMode(RoundingMode.DOWN);
	    
	    mean_travel_time = Double.parseDouble((df.format(mean_travel_time)));
	    
	}
	
	
	public TravelTime(int pTrimestre, int pSourceid,  int pDistid, int pHod,	double pMean_travel_time, double pStandard_deviation_travel_time)
	{
		int trimestre = pTrimestre;
		int sourceid = pSourceid;
		int distid = pDistid;
		int hod = pHod;
		double mean_travel_time = pMean_travel_time;
		double standard_deviation_travel_time = pStandard_deviation_travel_time;
	}
	
	



	public int compareTo(TravelTime o) 
	{
		
		TravelTime TimeTravel1 = new TravelTime();
		int r = 0;
		if (TimeTravel1.compareTo(o)>0)
		{
			r = +1;
		}
		else
			if (TimeTravel1.compareTo(o)<0)
			{
				r = -1;
			}
		return r;
	}

}
