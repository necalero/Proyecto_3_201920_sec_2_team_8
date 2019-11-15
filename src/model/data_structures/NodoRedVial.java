package model.data_structures;

public class NodoRedVial {
	private int id;
	private double longitud;
	private double latitud;
	
	public NodoRedVial(int pid, int pLongitud, int pLatitud)
	{
		id = pid;
		longitud = pLongitud;
		latitud = pLatitud;
	}
	
	public NodoRedVial(String pid, String pLongitud, String pLatitud)
	{
		id = Integer.parseInt(pid);
		longitud = Double.parseDouble(pLongitud);
		latitud = Double.parseDouble(pLatitud);
	}
	
	public int darID()
	{
		return id;
		
	}
	
	public double darLongitud()
	{
		return longitud;
	}
	
	public double darLatitud()
	{
		return latitud;
	}

}
