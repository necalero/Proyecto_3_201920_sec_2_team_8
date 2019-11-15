package model.data_structures;

public class UBERTrip implements Comparable<UBERTrip>
{
	private double sourceid;
	private double dstid;
	private double date;
	private double mean_travel_time;
	private double standard_deviation_travel_time;
	private double geometric_standard_deviation_travel_time;
	private double geometric_mean_travel_time;
	private String tipoDate;


	public UBERTrip(String Psourceid, String Pdstid,String Pdate, String Pmean_travel_time, String Pstandard_deviation_travel_time, String Pgeometric_standard_deviation_travel_time,String Pgeometric_mean_travel_time, String pTipoDate )
	{
		sourceid=Double.parseDouble(Psourceid);
		dstid = Double.parseDouble(Pdstid);
		date =Double.parseDouble(Pdate);
		mean_travel_time =Double.parseDouble(Pmean_travel_time);
		standard_deviation_travel_time =Double.parseDouble(Pstandard_deviation_travel_time);
		geometric_mean_travel_time = Double.parseDouble(Pgeometric_mean_travel_time);
		geometric_standard_deviation_travel_time = Double.parseDouble(Pgeometric_standard_deviation_travel_time);
		tipoDate = pTipoDate;
		

	}
	public String darTipoDate()
	{
		return tipoDate;
	}
	
	public double darSourceid() 
	{
		return sourceid;
	}

	public void cambiarSourceid(double sourceid) 
	{
		this.sourceid = sourceid;
	}

	public double darDstid() 
	{
		return dstid;
	}

	public void cambiarDstid(double dstid) 
	{
		this.dstid = dstid;
	}

	public double darDate() 
	{
		return date;
	}

	public void cambiarDate(double date) 
	{
		this.date = date;
	}

	public double darMean_travel_time() 
	{
		return mean_travel_time;
	}

	public void cambiarMeantravelTime(double mean_travel_time) 
	{
		this.mean_travel_time = mean_travel_time;
	}

	public double darStandadDeviationTravelTime() 
	{
		return standard_deviation_travel_time;
	}

	public void cambiarStandardDeviationTravelTime(double standard_deviation_travel_time) 
	{
		this.standard_deviation_travel_time = standard_deviation_travel_time;
	}

	public double darGeometric_mean_travel_time() 
	{
		return geometric_mean_travel_time;
	}

	public void cambiarGeometricMeanTravelTime(double geometric_mean_travel_time) 
	{
		this.geometric_mean_travel_time = geometric_mean_travel_time;
	}

	public double darGeometricStandardDeviationTravelTime() 
	{
		return geometric_standard_deviation_travel_time;
	}

	public void cambiarGeometricStandardDeviationTravelTime(double geometric_standard_deviation_travel_time) 
	{
		this.geometric_standard_deviation_travel_time = geometric_standard_deviation_travel_time;
	}
	
	public int compareTo(UBERTrip aComparar) 
	{
		if(this.mean_travel_time>aComparar.mean_travel_time)
		{
			return 1;
		}
		else if(this.mean_travel_time>aComparar.mean_travel_time)
		{
			return -1;
		}
		else
		{
			return 0;
		}
	}

}